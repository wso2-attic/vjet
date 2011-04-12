/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.liveconnect.client.DLCClientHelper;
import org.ebayopensource.dsf.liveconnect.client.DLCHttpResource;
import org.ebayopensource.dsf.liveconnect.client.DLCResourceHolder;
import org.ebayopensource.dsf.liveconnect.client.IDLCClient;
import org.ebayopensource.dsf.liveconnect.client.IDLCJsProvider;

/**
 * A DSF LiveConnect server to communicate with DSFLiveConnet flash
 * component embeded on DLC enabled web page.
 * 
 * This NIO server can handle requests/responses from/to multiple
 * browsers.
 */
public class DLCServer implements Runnable {
	private int m_port = 1028;
	private ServerSocketChannel m_serverChannel;
	private Selector m_selector;
	private ByteBuffer m_readBuffer = ByteBuffer.allocate(8192);
	private Map<SocketChannel, List<ByteBuffer>> m_pendingWrites 
		= new HashMap<SocketChannel, List<ByteBuffer>>();
	private Map<SocketChannel, byte[]> m_partialReads 
		= new HashMap<SocketChannel, byte[]>();
	private List<StateChange> m_pendingChanges = new ArrayList<StateChange>();
	private Set<SocketChannel> m_toBeClosed = new HashSet<SocketChannel>(2);
	private DLCFutureResultMgr m_futureResultMgr = new DLCFutureResultMgr();
	private final IDLCReceiver m_receiver;
	private IDLCClient m_dclClient = null;
	private IDLCJsProvider m_jsProvider = null;
	private DLCResourceHolder m_resourceHolder = null;
	
	private boolean m_shutdown = false;
	
	private static final String EOF = "\0";
	private static final String POLICY_REQUEST = "<policy-file-request/>";
	private static final String POLICY = 
		"<?xml version=\"1.0\"?><cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>";
	private static final String CONNECTED = "DLC_CONNECTED";
	private static final String DLC_REQUEST = "DLC_REQUEST:";
	private static final String DLC_RESPONSE = "DLC_RESPONSE:";
	private static final byte[] DLC_HTTP_REQUEST = "POST / HTTP/".getBytes();
	private static final byte[] DLC_HTTP_GET_REQUEST = "GET /".getBytes();
	public static final String DLC_CLIENT_FILE_NAME = "DLC_CLIENT_JS.js";
	public static final String DLC_FILE_NAME = "DLC_JS.js";
	private static final byte[] DLC_JS_REQUEST = ("GET /"+DLC_FILE_NAME+" HTTP/").getBytes();
	private static final byte[] DLC_CLIENT_JS_REQUEST = ("GET /"+DLC_CLIENT_FILE_NAME+" HTTP/").getBytes();
	private static final byte[] DLC_SWF_REQUEST = "GET /SWF HTTP/".getBytes();
	
	public DLCServer(IDLCReceiver receiver, IDLCClient dlcClient) throws IOException {
		this(receiver, dlcClient, null, null, 1028, true);
	}
	
	public DLCServer(IDLCReceiver receiver, IDLCClient dlcClient, int preferredPort) throws IOException {
		this(receiver, dlcClient, null, null, preferredPort, true);
	}
	
	public DLCServer(IDLCReceiver receiver, IDLCClient dlcClient, IDLCJsProvider jsProvider, DLCResourceHolder holder) throws IOException {
		this(receiver, dlcClient, jsProvider, holder, 1028, true);
	}
	
	public DLCServer(
		IDLCReceiver receiver,
		IDLCClient dlcClient,
		IDLCJsProvider jsProvider,
		DLCResourceHolder holder,
		int preferredPort,
		boolean isDaemon) throws IOException {
		m_dclClient = dlcClient;
		m_jsProvider = jsProvider;
		m_resourceHolder = holder;
		m_port = preferredPort;
		m_receiver = new DLCReceiverInvocator(receiver, m_futureResultMgr);
		m_serverChannel = ServerSocketChannel.open();
		m_serverChannel.configureBlocking(false);
		for (; m_port <= 65535; m_port++) {
			try {
				m_serverChannel.socket().bind(new InetSocketAddress(m_port));		   
				break;
			}
			catch (IOException e) {
				System.out.println("There is already an existing server on port " + m_port + ".");
			}
		}
		m_selector = SelectorProvider.provider().openSelector();
		m_serverChannel.register(m_selector, SelectionKey.OP_ACCEPT);
		System.out.println("DLC Server started at port " + m_port);
		Thread t = new Thread(this);
		t.setDaemon(isDaemon);
		t.start();
	}

	public DLCResourceHolder getResourceHolder() {
		return m_resourceHolder;
	}

	/**
	 * RPC call to simulate async request/response
	 */
	public DLCFutureResult request(SocketChannel channel, String message) {
		DLCFutureResult result = m_futureResultMgr.create();
		send(channel, DLC_REQUEST + result.getRequestId() + ":" + message);
		return result;
	}
	
	/**
	 * send out a message
	 */
	public void send(SocketChannel channel, String message) {
		if (!message.endsWith(EOF)) {
			message += EOF;
		}
		send(channel, message.getBytes());
	}
	
	void send(SocketChannel channel, byte[] data) {
		synchronized(m_pendingChanges) {
			synchronized(m_pendingWrites) {
				List<ByteBuffer> writeList = m_pendingWrites.get(channel);
				if (writeList == null) {
					writeList = new ArrayList<ByteBuffer>();
					m_pendingWrites.put(channel, writeList);
				}
				writeList.add(ByteBuffer.wrap(data));
			}
			m_pendingChanges.add(new StateChange(channel, SelectionKey.OP_WRITE));
			m_selector.wakeup();
		}
	}
	
	public int getPort() {
		return m_port;
	}
	
	public synchronized void shutdown() {
		m_shutdown = true;
		m_selector.wakeup();
	}

	public void run() {
		while (!m_shutdown) {
			try {
				synchronized(m_pendingChanges) {
					for (StateChange change : m_pendingChanges) {
						SelectionKey key = change.m_channel.keyFor(m_selector);
						if (key != null) {
							key.interestOps(change.m_ops);
						}
					}
					m_pendingChanges.clear();
				}
				m_selector.select();
				Iterator<SelectionKey> selectedKeys = m_selector.selectedKeys().iterator();
				while (selectedKeys.hasNext()) {
					SelectionKey key = selectedKeys.next();
					selectedKeys.remove();
					if (!key.isValid()) {
						continue;
					}
					if (key.isAcceptable()) {
						accept(key);
					}
					else if (key.isReadable()) {
						read(key);
					}
					else if (key.isWritable()) {
						write(key);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void accept(SelectionKey key) throws IOException {
		ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
		SocketChannel socketChannel = serverChannel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(m_selector, SelectionKey.OP_READ);
	}
	
	private void read(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel)key.channel();
			
		int numRead;
		do {
			m_readBuffer.clear();
			try {
				numRead = socketChannel.read(m_readBuffer);
				if (numRead > 0) {
					m_readBuffer.flip();
					processData(numRead, socketChannel);
				}
			}
			catch (IOException e) {
				key.cancel();
				socketChannel.close();
				return;
			}
		} while (numRead > 0);
		
		if (numRead == -1) {
			socketChannel.close();
			key.cancel();
			m_receiver.closed(socketChannel);
			return;
		}	
	}
	
	private void processData(int numRead, SocketChannel socketChannel) {
		int startIndex = 0;
		for (int i = startIndex; i < numRead; i++) {
			if (m_readBuffer.get(i) == 0) { //end of the message block from flash
				if (startIndex == 0 && i == numRead - 1) {
					//single message block
					informReceiver(socketChannel, m_readBuffer.array(), numRead);
				}
				else {
					//multiple message blocks
					byte[] data = new byte[i - startIndex + 1];
					m_readBuffer.get(data, 0, data.length);
					informReceiver(socketChannel, data, data.length);
				}
				startIndex = i + 1;
			}
		}
		if (startIndex < numRead) {
			//partial message from flash or HTTP request from browser directly
			byte[] data = new byte[numRead - startIndex];
			m_readBuffer.get(data, 0, data.length);
			
			if (isRequestOf(data, DLC_HTTP_REQUEST)) {
				DLCHttpResponse response = m_receiver
					.get(socketChannel, new DLCHttpRequest(new String(data)));
				m_toBeClosed.add(socketChannel);
				send(socketChannel, response.getHttpPayload());			
			} 
			else if (isRequestOf(data, DLC_JS_REQUEST)) {
				m_toBeClosed.add(socketChannel);
				send(socketChannel, getDlcJs().getHttpPayload());	
			}
			else if (isRequestOf(data, DLC_CLIENT_JS_REQUEST)) {
				m_toBeClosed.add(socketChannel);
				send(socketChannel, getDlcClientJs().getHttpPayload());	
			}
			else if (isRequestOf(data, DLC_SWF_REQUEST)) {
				m_toBeClosed.add(socketChannel);
				send(socketChannel, getDlcSwf().getHttpPayload());
			} else if (isRequestOf(data, DLC_HTTP_GET_REQUEST)) {
				DLCHttpRequest request = new DLCHttpRequest(new String(data));
				DLCHttpResponse response = m_receiver.get(socketChannel, request);
				
				if(response==null && m_resourceHolder!=null) {
					response = getDlcResource(request.getUri());
				}

				if(response!=null) {
					m_toBeClosed.add(socketChannel);
				    send(socketChannel, response.getHttpPayload());
				}
			}
			else {
				savePendingReads(socketChannel, data);
			}
		}
	}
	
	private static boolean isRequestOf(byte[] data, byte[] type) {
		if (data.length <= type.length) {
			return false;
		}
		for (int i = 0; i < type.length; i++) {
			if (type[i] != data[i]) {
				return false;
			}
		}
		return true;
	}
	
	private DLCHttpResponse getDlcJs() {
		DLCHttpResponse response = new DLCHttpResponse(DLCClientHelper.DLC_JS);
		response.setContentType("application/javascript");
		return response;
	}
	
	private DLCHttpResponse getDlcClientJs() {
		StringBuffer clientJs = new StringBuffer(1024);
		if(m_dclClient!=null)
		    clientJs.append(new String(m_dclClient.getClientJs()));
		if(m_jsProvider!=null)
		    clientJs.append(new String(m_jsProvider.getClientJs()));

		DLCHttpResponse response = new DLCHttpResponse(clientJs.toString().getBytes());
		response.setContentType("application/javascript");
		return response;
	}
	
	private DLCHttpResponse getDlcSwf() {
		DLCHttpResponse response = new DLCHttpResponse(DLCClientHelper.DLC_SWF);
		response.setContentType("application/x-shockwave-flash");
		return response;
	}
	
	private DLCHttpResponse getDlcResource(String uri) {
		if(m_resourceHolder==null) return null;
		DLCHttpResource resource = m_resourceHolder.getResource(uri);
		DLCHttpResponse response = new DLCHttpResponse(resource.getContent());
		response.setContentType(resource.getMimeType());
		return response;
	}
	
	private void savePendingReads(SocketChannel socketChannel, byte[] data) {
		byte[] pendingData = m_partialReads.get(socketChannel);
		if (pendingData == null) {
			m_partialReads.put(socketChannel, data);
		}
		else {
			byte[] total = new byte[pendingData.length + data.length];
			System.arraycopy(pendingData, 0, total, 0, pendingData.length);
			System.arraycopy(data, 0, total, pendingData.length, data.length);
			m_partialReads.put(socketChannel, total);
		}
	}
	
	private void informReceiver(SocketChannel socketChannel, byte[] data, int size) {
		byte[] total = data;
		byte[] pendingData = m_partialReads.get(socketChannel);
		if (pendingData != null) {
			total = new byte[pendingData.length + size];
			System.arraycopy(pendingData, 0, total, 0, pendingData.length);
			System.arraycopy(data, 0, total, pendingData.length, size);
			m_partialReads.put(socketChannel, null);
			size = total.length;
		}
		String message = new String(total, 0, size -1); //skipt EOF byte
		if (POLICY_REQUEST.equalsIgnoreCase(message)) {
			send(socketChannel, POLICY);
		}
		else if (CONNECTED.equalsIgnoreCase(message)) {
			m_receiver.connected(socketChannel);
		}
		else if (message.startsWith(DLC_RESPONSE)) {
			int endIndex = message.indexOf(":", DLC_RESPONSE.length());
			String requestId = message.substring(DLC_RESPONSE.length(), endIndex);
			m_futureResultMgr.setResult(requestId, message.substring(endIndex+1));
		}
		else {
			m_receiver.received(socketChannel, message);
		}
	}
	
	private void write(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel)key.channel();
		synchronized(m_pendingWrites) {
			List<ByteBuffer> dataList = m_pendingWrites.get(socketChannel);
			while (!dataList.isEmpty()) {
				ByteBuffer buf = dataList.get(0);
				socketChannel.write(buf);
				if (buf.remaining() > 0) {
					break;
				}
				dataList.remove(0);
			}
			if (dataList.isEmpty()) {
				if (m_toBeClosed.contains(socketChannel)) {
					m_toBeClosed.remove(socketChannel);
					socketChannel.close();
					key.cancel();
				}
				else {
					key.interestOps(SelectionKey.OP_READ);
				}
			}
		}
	}
	
	private static class StateChange {
		
		private final SocketChannel m_channel;
		private final int m_ops;
		
		private StateChange(SocketChannel channel, int ops) {
			m_channel = channel;
			m_ops = ops;
		}
	}
}
