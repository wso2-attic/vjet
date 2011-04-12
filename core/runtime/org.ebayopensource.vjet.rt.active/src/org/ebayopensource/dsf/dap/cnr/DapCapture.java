/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.cnr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.dap.cnr.DapCaptureData.EventCapture;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.IDomChange;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.IEventCapture;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.ViewCapture;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.dap.rt.DapHttpRequest;
import org.ebayopensource.dsf.dap.rt.DapHttpResponse;
import org.ebayopensource.dsf.dap.rt.DapView;
import org.ebayopensource.dsf.dap.rt.IDapHttpClient.IDapHttpListener;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;

/**
 * Capture the browser events and dom changes during the active serve-side
 * browser emulation (with DLC enabled).
 * 
 * The captured event can be replayed later in normal unittests without
 * browser and resulting dom changes can be varified against the previously
 * captured data.
 */
public class DapCapture implements IDapCapture {
	
	private static final long serialVersionUID = 1L;
	
	private transient IEventCapture m_curEventCapture;
	private transient String m_curCaptureName;
	
	private DapCaptureData m_capturedData = new DapCaptureData();
	private ViewCapture m_curViewCapture;
	
	private long m_prevEventTime = -1;
	private CaptureState m_state;
	
	private List<IEventFilter> m_eventFilters = new ArrayList<IEventFilter>();
	private List<ITargetFilter> m_targetFilters = new ArrayList<ITargetFilter>();
	private List<IDapCaptureListener> m_captureListeners;
	
	//
	// Constructor
	//
	public DapCapture() {
		
		m_capturedData.setVersion(DapCaptureData.VERSION_1_0_0);

		m_curEventCapture = m_capturedData.new EventCapture();
		m_capturedData.setInitEventCapture((EventCapture)m_curEventCapture);
		
		m_state = CaptureState.started; // TODO should be configurable
		m_prevEventTime = -1;
		m_curCaptureName = getNextCaptureName();
	}
	
	//
	// Satisfy IDomChangeListener
	//
	public void onAppendChild(Node child) {
		if (isDomCaptureOn(child)) {
			addDomChange(DomChangeMessageFormater.onAppendChild(child));
		}
	}

	public void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, boolean value) {
		onAttrChange(elem, attr, String.valueOf(value));
	}

	public void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, String value) {
		if (isDomCaptureOn(elem)) {
			addDomChange(DomChangeMessageFormater.onAttrChange(elem, attr, value));
		}
	}
	
	public void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, int value) {
		onAttrChange(elem, attr, String.valueOf(value));
	}
	
	public void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, double value) {
		onAttrChange(elem, attr, String.valueOf(value));
	}

	public void onClassNameChange(BaseHtmlElement elem, String className) {
		if (isDomCaptureOn(elem)) {
			addDomChange(DomChangeMessageFormater.onClassNameChange(elem, className));
		}
	}

	public void onElementChange(BaseHtmlElement elem) {
		if (isDomCaptureOn(elem)) {
			addDomChange(DomChangeMessageFormater.onElementChange(elem));
		}
	}

	public void onHeightChange(BaseHtmlElement node, int height) {
		if (isDomCaptureOn(node)) {
			addDomChange(DomChangeMessageFormater.onHeightChange(node, height));
		}
	}

	public void onInsert(Node newNode, Node refNode, boolean insertBefore) {
		if (isDomCaptureOn(newNode)) {
			addDomChange(DomChangeMessageFormater.onInsert(newNode, refNode, insertBefore));
		}
	}

	public void onRemove(Node node) {
		if (isDomCaptureOn(node)) {
			addDomChange(DomChangeMessageFormater.onRemove(node));
		}
	}

	public void onStyleChange(BaseHtmlElement elem, String name, String value) {
		if (isDomCaptureOn(elem)) {
			addDomChange(DomChangeMessageFormater.onStyleChange(elem, name, value));
		}
	}

	public void onValueChange(BaseHtmlElement elem, String value) {
		if (isDomCaptureOn(elem)) {
			addDomChange(DomChangeMessageFormater.onValueChange(elem, value));
		}
	}

	public void onWidthChange(BaseHtmlElement node, int width) {
		if (isDomCaptureOn(node)) {
			addDomChange(DomChangeMessageFormater.onWidthChange(node, width));
		}
	}
	
	//
	// Satisfy IDapHttpListener
	//
	/**
	 * @see IDapHttpListener#onHttpRequest(DapHttpRequest)
	 */
	public void onHttpRequest(final DapHttpRequest req){
		if (isCaptureOn()) {
			m_curEventCapture.addHttpReq(req);
		}
	}
	
	/**
	 * @see IDapHttpListener#onHttpResponse(DapHttpRequest, DapHttpResponse)
	 */
	public void onHttpResponse(final DapHttpRequest req, final DapHttpResponse resp){
		if (isCaptureOn()) {
			m_curEventCapture.addHttpResp(req, resp);
		}
	}
	
	/**
	 * @see IDapHttpListener#onHttpTimedOut(DapHttpRequest)
	 */
	public void onHttpTimedOut(final DapHttpRequest req){
		// TODO
	}
	
	//
	// Satisfy IDLCDispatcherInfoCollector
	//
	public void request(String data) {
		if (isCaptureOn()) {
			resetCurrentEventCapture();
			m_curEventCapture.addDlcRequest(filterDynamicData(data));
		}
	}

	public void response(String data) {
		if (isCaptureOn()) {
			resetCurrentEventCapture();
			m_curEventCapture.addDlcResponse(data);
		}
	}

	public void send(String data) {
		if (isCaptureOn()) {
			resetCurrentEventCapture();
			m_curEventCapture.addDlcSend(filterDynamicData(data));
		}
	}
	
	//
	// Satisfy IDapCapture
	//

	@Override
	public CaptureState state() {
		return m_state;
	}

	@Override
	public String currentCaptureName() {
		return m_curCaptureName;
	}
	
	public void receiveEvent(DLCEvent event) {
		if (isEventCaptureOn(event)){
			long currentEventTime = System.currentTimeMillis();
			if (m_prevEventTime == -1) {
				m_prevEventTime = currentEventTime;
			}
			m_curEventCapture = m_capturedData.new EventCapture(event, (currentEventTime - m_prevEventTime));
			getViewCapture().addEventCapture(m_curCaptureName, m_curEventCapture);
			m_prevEventTime = currentEventTime;
		}
	}
	
	public void receiveTask(String msg) {
		if (isCaptureOn()){
			m_curEventCapture = m_capturedData.new TaskCapture(msg);
			getViewCapture().addEventCapture(m_curCaptureName, m_curEventCapture);
		}
	}
	
	private void resetCurrentEventCapture() {
		if(m_curViewCapture==null) return;
		List<IEventCapture> ecs = m_curViewCapture.getAllEventCaptures();
		int index = ecs.size() - 1;
		if(index > 0)
		    m_curEventCapture = ecs.get(index);
	}
	
	public void beginView(DapView view){
		if (!isCaptureOn()){
			return;
		}
		String url = (view == null) ? null : view.getUrl();
		m_curViewCapture = m_capturedData.new ViewCapture(url);
		m_capturedData.addViewCapture(m_curViewCapture);
	}
	
	public void endView(){
		m_curViewCapture = null;
	}
	
	public void start(String captureName){
		if (isCaptureOn()){
			stop();
		}
		m_state = CaptureState.started;
		m_prevEventTime = System.currentTimeMillis();
		if (captureName == null){
			m_curCaptureName = getNextCaptureName();
		}
		else {
			m_curCaptureName = captureName;
		}
	}
	
	public void pause(){
		if (!isCaptureOn()){
			return;
		}
		m_state = CaptureState.paused;
	}
	
	public void resume(){
		if (m_curCaptureName == null){
			return;
		}
		m_state = CaptureState.resumed;
		m_prevEventTime = System.currentTimeMillis();
	}
	
	public void stop(){
		m_state = CaptureState.stoped;
		m_curCaptureName = null;
	}
	
	public Map<String,List<IEventCapture>> getUserEventCaptures(){
		return getViewCapture().getEventCaptures();
	}
	
	public List<IEventCapture> getUserEventCapture(String captureName){
		return getViewCapture().getEventCapture(captureName);
	}
	
	public void addEventFilter(IEventFilter filter){
		if (filter == null){
			return;
		}
		if (!m_eventFilters.contains(filter)){
			m_eventFilters.add(filter);
		}
	}
	
	public void addTargetFilter(ITargetFilter filter){
		if (filter == null){
			return;
		}
		if (!m_targetFilters.contains(filter)){
			m_targetFilters.add(filter);
		}
	}
	
	public DapCaptureData getCapturedData(){
		return m_capturedData;
	}
	
	//
	// Private
	//
	private ViewCapture getViewCapture(){
		if (m_curViewCapture == null){
			beginView(null); 
		}
		return m_curViewCapture;
	}
	
	/**
	 * We don't capture the dynamic data which is for avoiding cache.
	 * If we capture these data, replay will find mismatch between expected & actual URLs.
	 */
	@Deprecated // TODO need general solution for this type of issues
	private String filterDynamicData(String data) {
		return data.replaceAll("&amp;_vrdm=[0-9]*", "");
	}
	
	private boolean isCaptureOn(){
		return m_state == CaptureState.started 
			|| m_state == CaptureState.resumed;
	}
	
	private boolean isEventCaptureOn(DLCEvent event){
		if (!isCaptureOn()){
			return false;
		}
		for (IEventFilter filter: m_eventFilters){
			if (filter.filter(event)){
				return false;
			}
		}
		return true;
	}

	private boolean isDomCaptureOn(Node target){
		if (!isCaptureOn()){
			return false;
		}
		for (ITargetFilter filter: m_targetFilters){
			if (filter.filter(target)){
				return false;
			}
		}
		return true;
	}
	
	private void addDomChange(IDomChange domChange){
		
		if (domChange == null) {
			return;
		}
		
		m_curEventCapture.addDomChange(domChange);
		
		if (m_captureListeners == null){
			m_captureListeners = DapCtx.ctx().getDapConfig().getCaptureListeners();
		}
		if (m_captureListeners.isEmpty()){
			return;
		}
		for (IDapCaptureListener listener: m_captureListeners){
			listener.onDomCapture(domChange);
		}
	}
	
	private static int s_captureCount = 0;
	private String getNextCaptureName(){
		return "Capture" + s_captureCount++;
	}
}
