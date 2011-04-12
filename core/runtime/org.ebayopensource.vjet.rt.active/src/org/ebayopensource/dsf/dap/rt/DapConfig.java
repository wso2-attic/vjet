/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ebayopensource.dsf.dap.cnr.DapCaptureXmlSerializer;
import org.ebayopensource.dsf.dap.cnr.DapConsoleCaptureDumpHandler;
import org.ebayopensource.dsf.dap.cnr.DapConsoleReplayHandler;
import org.ebayopensource.dsf.dap.cnr.IDapCaptureListener;
import org.ebayopensource.dsf.dap.cnr.IDapCaptureSerializer;
import org.ebayopensource.dsf.dap.cnr.DapCaptureXmlSerializer.DapCaptureDataSubHandler;
import org.ebayopensource.dsf.dap.cnr.DapCaptureXmlSerializer.DapCaptureDataSubSerializer;
import org.ebayopensource.dsf.liveconnect.client.DLCJsAggregator;
import org.ebayopensource.dsf.liveconnect.client.DLCResourceHolder;
import org.ebayopensource.dsf.liveconnect.client.IDLCClient;
import org.ebayopensource.dsf.liveconnect.client.IDLCJsProvider;
import org.ebayopensource.dsf.liveconnect.client.IDLCResourceProvider;
import org.ebayopensource.dsf.liveconnect.client.simple.SimpleDLCClient;

/**
 * Configuration for DAP runtime. Currently it's shared across all DAP sessions.
 * Please be aware that emulator-level config should be set before emulator is 
 * created. While session-level config can be set/updated after emulator is created.
 */
public class DapConfig {
	
	private boolean m_enableCaptureReplay = false;
	private boolean m_enableConsoleLog = false;
	
	private int m_port = 8181;

	private IDLCClient m_dlcClient;
	private IDapHttpClient m_httpClient;

	private DLCJsAggregator m_jsAggr = new DLCJsAggregator();
	private DLCResourceHolder m_resourceHolder = new DLCResourceHolder();
	private DLCMsgHandlerRegistry m_handlerRegistry = new DLCMsgHandlerRegistry();
	
	private DapInfoCollector m_infoCollector;
	private IDapCaptureSerializer m_dapCaptureSerializer;
	
	private List<IDapConsoleHandler> m_consoleHandles
		= new ArrayList<IDapConsoleHandler>();
	
	private List<IDapCaptureListener> m_listeners;
	
	//
	// Constructor
	//
	public DapConfig(){
		this.addDlcMsgHandler(new DapTaskMsgHandler());
		enableCaptureReplay(true);
		enableConsoleLog(true);
	}

	//
	// API
	//
	@Deprecated // TODO remove after XMLHttpRequestImpl refactoring
	public int getPort() {
		return m_port;
	}

	@Deprecated // TODO remove after XMLHttpRequestImpl refactoring
	public void setPort(int port) {
		m_port = port;
	}
	
	/**
	 * Answer whether capture-replay is enabled.
	 * Called when new session is created.
	 * @return boolean
	 */
	public boolean isCaptureReplayEnabled() {
		return m_enableCaptureReplay;
	}

	/**
	 * Enable/disable capture-replay. The change takes effect to 
	 * new sessions only
	 */
	public void enableCaptureReplay(boolean enableCaptureReplay) {
		m_enableCaptureReplay = enableCaptureReplay;
		if (enableCaptureReplay){
			addConsoleHandler(DapConsoleReplayHandler.getInstance());
			addConsoleHandler(DapConsoleCaptureDumpHandler.getInstance());
			addConsoleHandler(DapConsoleCaptureHandler.getInstance());
		}
		else {
			removeConsoleHandler(DapConsoleReplayHandler.getInstance());
			removeConsoleHandler(DapConsoleCaptureDumpHandler.getInstance());
		}
	}

	public boolean isConsoleLogEnable() {
		return m_enableConsoleLog;
	}

	public void enableConsoleLog(boolean enableConsoleLog) {
		m_enableConsoleLog = enableConsoleLog;
	}
	
	/**
	 * Answer the instance of DLC Client
	 * @return IDLCClient
	 */
	public IDLCClient getDlcClient() {
		if (m_dlcClient == null){
			m_dlcClient = SimpleDLCClient.getInstance();
		}
		return m_dlcClient;
	}

	/**
	 * Set the instance of DLC client. It's recommended to
	 * set it up-front to avoid any undesired mis-matches
	 * @param dlcClient
	 */
	public void setDlcClient(IDLCClient dlcClient) {
		m_dlcClient = dlcClient;
	}
	
	/**
	 * Answer httpClient for Ajax calls in active mode.
	 * Default is an instance of <code>DapHttpClient</code>
	 * @return IDapHttpClient
	 */
	public IDapHttpClient getHttpClient() {
		if (m_httpClient == null){
			m_httpClient = new DapHttpClient();
		}
		return m_httpClient;
	}

	/**
	 * Set httpClient for Ajax calls in active mode
	 * @param httpClient IDapHttpClient
	 */
	public void setHttpClient(IDapHttpClient httpClient) {
		m_httpClient = httpClient;
	}
	
	/**
	 * Add console handles to support custom interactions via console
	 * @param handler IDapConsoleHandler
	 */
	public DapConfig addConsoleHandler(IDapConsoleHandler handler){
		if (handler == null){
			return this;
		}
		synchronized (this){
			if (!m_consoleHandles.contains(handler)){
				m_consoleHandles.add(handler);
			}
		}
		return this;
	}
	
	/**
	 * Remove console handler
	 * @param handler IDapConsoleHandler
	 */
	public synchronized void removeConsoleHandler(IDapConsoleHandler handler){
		m_consoleHandles.remove(handler);
	}
	
	/**
	 * Answer the full list of registered handlers
	 * @return List<? extends IDapConsoleHandler>
	 */
	public List<? extends IDapConsoleHandler> getConsoleHandlers(){
		return m_consoleHandles;
	}
	
	public void setInfoCollector(DapInfoCollector infoCollector){
		m_infoCollector = infoCollector;
	}
	
	public DapInfoCollector getInfoCollector(){
		if (!m_enableConsoleLog){
			return null;
		}
		if (m_infoCollector == null){
			m_infoCollector = new DapInfoCollector(null);
		}
		return m_infoCollector;
	}

	public final IDapCaptureSerializer getDapCaptureSerializer() {
		if (m_dapCaptureSerializer == null){
			m_dapCaptureSerializer = new DapCaptureXmlSerializer();
		}
		return m_dapCaptureSerializer;
	}

	public void setDapCaptureSerializer(IDapCaptureSerializer dapCaptureSerializer) {
		m_dapCaptureSerializer = dapCaptureSerializer;
	}
	
	public final List<IDapCaptureListener> getCaptureListeners(){
		if (m_listeners == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_listeners);
	}
	
	public DapConfig addCaptureListener(final IDapCaptureListener listener){
		if (listener == null){
			return this;
		}
		
		if (m_listeners == null){
			m_listeners = new ArrayList<IDapCaptureListener>();
		}
		m_listeners.add(listener);
		
		return this;
	}

	public void addDlcMsgHandler(IDLCMsgHandler handler) {
		m_handlerRegistry.add(handler);
	}

	public DLCMsgHandlerRegistry getDlcMsgHandlerRegistry() {
		return m_handlerRegistry;
	}

	public void addJsProvider(IDLCJsProvider provider) {
		m_jsAggr.addProvider(provider);
	}

	public DLCJsAggregator getJsAggregator() {
		return m_jsAggr;
	}

	public void addResourceProvider(IDLCResourceProvider provider) {
		m_resourceHolder.addProvider(provider);
	}

	public DLCResourceHolder getResourceHolder() {
		return m_resourceHolder;
	}

	//For DapCaptureData Xml Serialization Customization
	private Map<String, Class<? extends DapCaptureDataSubSerializer>> m_serRegistry = 
		new HashMap<String, Class<? extends DapCaptureDataSubSerializer>>();

	public Map<String, Class<? extends DapCaptureDataSubSerializer>> getDapCaptureDataSerializerRegistry() {
		return m_serRegistry;
	}
	
	private Map<String, Class<? extends DapCaptureDataSubHandler>> m_deserRegistry = 
		new HashMap<String, Class<? extends DapCaptureDataSubHandler>>();
	
	public Map<String, Class<? extends DapCaptureDataSubHandler>> getDapCaptureDataDeserializerRegistry(){
		return m_deserRegistry;
	}
	
	//For DapConsoelProxy
	private Map<String, IDapConsoleProxy> m_dapConsoleProxies = new ConcurrentHashMap<String, IDapConsoleProxy>();
	
	public void addDapConsoleProxy(String id, IDapConsoleProxy proxy){
		if(proxy != null){
			m_dapConsoleProxies.put(id, proxy);
		}
	}
	
	public void addDapConsoleProxy(IDapConsoleProxy proxy){
		if(proxy != null){
			addDapConsoleProxy(proxy.getClass().getSimpleName(), proxy);
		}
	}
	
	public Collection<IDapConsoleProxy> getDapConsoleProxies(){
		return m_dapConsoleProxies.values();
	}
	
	public IDapConsoleProxy findDapConsoleProxy(String id){
		return m_dapConsoleProxies.get(id);
	}
	
}
