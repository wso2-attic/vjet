/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.util.List;

import org.ebayopensource.dsf.active.client.AHtmlParser;
import org.ebayopensource.dsf.active.client.ANavigator;
import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.client.BrowserSupport;
import org.ebayopensource.dsf.active.client.WindowFactory;
import org.ebayopensource.dsf.active.dom.html.AHtmlDocument;
import org.ebayopensource.dsf.active.dom.html.AHtmlInternal;
import org.ebayopensource.dsf.active.dom.html.ANode;
import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.active.event.IDomChangeListener;
import org.ebayopensource.dsf.active.event.IDomEventBindingListener;
import org.ebayopensource.dsf.active.event.IDomEventPublisher;
import org.ebayopensource.dsf.active.util.WindowTask;
import org.ebayopensource.dsf.active.util.WindowTaskManager;
import org.ebayopensource.dsf.dap.api.util.DapEventHelper;
import org.ebayopensource.dsf.dap.event.DapEvent;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.html.dom.DHtmlCollection;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.DInput;
import org.ebayopensource.dsf.html.dom.DOption;
import org.ebayopensource.dsf.html.dom.DSelect;
import org.ebayopensource.dsf.html.dom.DTextArea;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.js.ActiveJsExecutionControlCtx;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;

/**
 * DapWindowManager manages scriptable window for DAP. 
 * It also provides API for window and DDOM update to sync up
 * with real or another emulated browser.
 * 
 * Note: currently it supports one document only.
 */
public final class DapWindowManager {
	
	private AWindow m_window;
	private WindowTaskManager m_taskMgr;
	
	//
	// Constructor
	//
	/**
	 * Constructor. It create AWindow for given html and setup listeners
	 * and bindings to the window.
	 * @param html String
	 * @param domChangelisteners List<IDomChangeListener>
	 * @param domEventPublisher IDomEventPublisher
	 * @param domEventBindingListener IDomEventBindingListener
	 * @param browserBinding IBrowserBinding
	 */
	DapWindowManager(final String html,
		final List<IDomChangeListener> domChangelisteners,
		final IDomEventPublisher domEventPublisher,
		final IDomEventBindingListener domEventBindingListener,
		final IBrowserBinding browserBinding){
		
		ActiveJsExecutionControlCtx.ctx().setExecuteJavaScript(true);
		ActiveJsExecutionControlCtx.ctx().setParseGeneratedContent(true);
		BrowserType browserType = BrowserType.IE_6P;
		if (browserBinding != null) {
			String userAgent = browserBinding.getNavigatorProperty(ANavigator.USER_AGENT);
			browserType = BrowserSupport.getType(userAgent);
			DapCtx.ctx().getDapConfig().getInfoCollector().log("******* " + userAgent);
		}
		
		m_taskMgr = new WindowTaskManager();
		m_window = (AWindow) WindowFactory.createWindow(browserType, m_taskMgr);
		
		// DapCtx		
		m_window.setBrowserBinding(browserBinding);
		DapHost dapHost = new DapHost(m_window);
		dapHost.initialize();
		DapCtx.ctx().setDapHost(dapHost);
		AHtmlDocument doc = (AHtmlDocument) m_window.getDocument();
		doc.setEventDispatcher(domEventPublisher);
		doc.setDomEventBindingListener(domEventBindingListener);
		// set DomChangeListener with disabled state
		if (domChangelisteners != null) {
			for (IDomChangeListener domChangelistener : domChangelisteners) {
				m_window.addDomListener(domChangelistener, false);
			}
		}		
		AHtmlParser.parse(html, null, m_window);
		// always enable listener after document is parsed
		m_window.enableDomChangeListener(true);
	}
	
	// TODO create window event replaceing string msg
	void updateWindow(final String msg){
		int index1 = msg.indexOf("(");
		int index2 = msg.indexOf(",", index1);
		int index3 = msg.indexOf(")", index2);
		
		String width = msg.substring(index1+1, index2);
		String height = msg.substring(index2+1, index3);
		
		m_window.getScreen().setWidth(Integer.parseInt(width));
		m_window.getScreen().setHeight(Integer.parseInt(height));
	}
	
	void updateDDom(final DapEvent event, final DLCEvent dlcEvent){
		
		if (event == null || dlcEvent.getValue() == null) {
			return;
		}
		
		String value = dlcEvent.getValue();

		ANode aSrc = (ANode)DapEventHelper.getInstance().getSrcNode(event);
		DNode src = ADomHelper.getDNode(aSrc);
		if(src == null){
			return;
		}
		
		String eventId = event.getType();
		if (EventType.CHANGE.getName().equals(eventId)){
			if (src instanceof DInput){
				updateElement((DInput)src, value);
			}
			else if (src instanceof DTextArea){
				updateElement((DTextArea)src, value);
			}
			else if (src instanceof DSelect){
				updateElement((DSelect)src, value);
			}
		}
		else if (EventType.CLICK.getName().equals(eventId)){
			if (src instanceof DInput){
				updateElement((DInput)src, value);
			}
		}
		else if (EventType.KEYUP.getName().equals(eventId)){
			if (src instanceof DInput){
				updateElement((DInput)src, value);
			}
			else if (src instanceof DTextArea){
				updateElement((DTextArea)src, value);
			}
		}
	}
	
	AWindow getWindow(){
		return m_window;
	}
	
	DHtmlDocument getDHtmlDocument(){
		return ADomHelper.getDHtmlDocument(getDocument());
	}
	
	AHtmlDocument getDocument() {
		return (AHtmlDocument) (m_window == null ? null : m_window.getDocument());
	}
	
	void close() {
		m_window.finialize();
	}
	
	//
	// Private
	//
	private void updateElement(final DInput input, final String value){
		if(input.getHtmlType().equalsIgnoreCase(DInput.TYPE_RADIO) 
				|| input.getHtmlType().equalsIgnoreCase(DInput.TYPE_CHECKBOX)){
			
			input.setHtmlChecked(Boolean.valueOf(value));
		}
		input.setHtmlValue(value);
	}
	
	private void updateElement(final DTextArea textArea, final String value){
		textArea.setHtmlExtValue(value);
	}
	
	private void updateElement(final DSelect select, final String value){
		if(!select.getHtmlMultiple()){
			select.setHtmlSelectedIndex(value);
		}
		else{
			DHtmlCollection options = select.getHtmlOptions();
			String[] idxs = value.split(",");
			//un-select all options first
			for(int i=0;i<options.getLength();i++){
				((DOption)options.item(i)).setHtmlSelected(false);
			}
			//select selected options
			for(int j=0;j<idxs.length;j++){
				int idx = Integer.parseInt(idxs[j]);
				((DOption)options.item(idx)).setHtmlSelected(true);
			}
		}
	}
	
	private static class ADomHelper extends AHtmlInternal {
		private static DHtmlDocument getDHtmlDocument(AHtmlDocument adoc){
			return AHtmlInternal.getInternalDocument(adoc);
		}
		private static DNode getDNode(ANode aNode){
			return getInternalNode(aNode);
		}
	}

	public void executeTask(int id) {
		WindowTask task = m_taskMgr.getTask(id);
		if(task != null) task.execute();
	}
}
