/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect;

import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * This DLCReceiver makes connected() and received() calls async, which
 * will be invoked at different thread from the DLC server's selector
 * thread.
 */
class DLCReceiverInvocator implements IDLCReceiver {
		
	private final IDLCReceiver m_receiver;
	private int m_channelIndex = 0;
	private WeakHashMap<SocketChannel, ChannelBasedInvocator> m_invocators
		= new WeakHashMap<SocketChannel, ChannelBasedInvocator>();
	private final DLCFutureResultMgr m_dlcRequestMgr;
	
	DLCReceiverInvocator(IDLCReceiver receiver, DLCFutureResultMgr mgr) {
		m_receiver = receiver;
		m_dlcRequestMgr = mgr;
	}
	
	class ChannelBasedInvocator implements Runnable, IDLCReceiver {		
		private List<Runnable> m_fifo = new LinkedList<Runnable>();
		
		ChannelBasedInvocator(int channelIndex) {
			Thread t = new Thread(this, "DLCReceiverInvocator_" + m_channelIndex);
			t.setDaemon(true);
			t.start();
		}
		
		public void run() {
			while (true) {
				while (!m_fifo.isEmpty()) {
					m_dlcRequestMgr.waitForAllDone();
					m_fifo.remove(0).run();				
				}
				synchronized (m_fifo) {
					if (m_fifo.isEmpty()) {
						try {
							m_fifo.wait();
						} catch (InterruptedException e) {
							//KEEP-ME
						}
					}
				}
			}
		}
		
		public void connected(final SocketChannel channel) {
			Runnable cmd = new Runnable() {
				public void run() {
					m_receiver.connected(channel);
				}			
			};
			addCmd(cmd);
		}

		public void received(final SocketChannel channel, final String message) {
			Runnable cmd = new Runnable() {
				public void run() {
					m_receiver.received(channel, message);
				}			
			};
			addCmd(cmd);
		}
		
		public void closed(final SocketChannel channel) {
			Runnable cmd = new Runnable() {
				public void run() {
					m_receiver.closed(channel);
					m_invocators.remove(channel);
				}			
			};
			addCmd(cmd);			
		}

		public DLCHttpResponse get(final SocketChannel channel, DLCHttpRequest request) {
			return m_receiver.get(channel, request);
		}
		
		private void addCmd(Runnable cmd) {
			m_fifo.add(cmd);
			synchronized (m_fifo) {
				m_fifo.notifyAll();
			}
		}
	}

	public void connected(final SocketChannel channel) {
		getInvocator(channel).connected(channel);
	}

	public void received(final SocketChannel channel, final String message) {
		getInvocator(channel).received(channel, message);
	}
	
	public void closed(final SocketChannel channel) {
		getInvocator(channel).closed(channel);
	}

	public DLCHttpResponse get(final SocketChannel channel, final DLCHttpRequest request) {
		return getInvocator(channel).get(channel, request);
	}
	
	private synchronized ChannelBasedInvocator getInvocator(SocketChannel channel) {
		ChannelBasedInvocator invocator = m_invocators.get(channel);
		if (invocator == null) {
			invocator = new ChannelBasedInvocator(m_channelIndex++);
			m_invocators.put(channel, invocator);
		}
		return invocator;
	}
}
