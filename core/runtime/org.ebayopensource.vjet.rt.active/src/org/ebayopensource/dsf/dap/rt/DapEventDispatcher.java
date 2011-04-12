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
import java.util.Map;

import org.w3c.dom.NamedNodeMap;

import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.client.ScriptExecutor;
import org.ebayopensource.dsf.active.dom.html.AHtmlAnchor;
import org.ebayopensource.dsf.active.dom.html.AHtmlDocument;
import org.ebayopensource.dsf.active.dom.html.AHtmlElement;
import org.ebayopensource.dsf.active.dom.html.AHtmlHelper;
import org.ebayopensource.dsf.active.dom.html.AHtmlImage;
import org.ebayopensource.dsf.active.dom.html.AHtmlInput;
import org.ebayopensource.dsf.active.dom.html.AHtmlInternal;
import org.ebayopensource.dsf.active.dom.html.AHtmlTextArea;
import org.ebayopensource.dsf.active.dom.html.AImage;
import org.ebayopensource.dsf.active.dom.html.AJavaScriptHandlerHolder;
import org.ebayopensource.dsf.active.dom.html.ANode;
import org.ebayopensource.dsf.active.dom.html.AJavaScriptHandlerHolder.JAVASCRIPT_HANDLER_TYPE;
import org.ebayopensource.dsf.active.event.IEventListenersCollector;
import org.ebayopensource.dsf.dap.api.util.DapEventHelper;
import org.ebayopensource.dsf.dap.event.DapEvent;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsnative.HtmlAnchor;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.ebayopensource.dsf.liveconnect.client.NativeEvent;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.IJsJavaProxy;
import org.mozilla.mod.javascript.Scriptable;

public final class DapEventDispatcher {
	
//	private static Logger s_logger = Logger.getInstance(DapEventDispatcher.class);
	
	//
	// Singleton
	private static DapEventDispatcher s_instance = new DapEventDispatcher();
	private DapEventDispatcher(){};
	private static final String JAVASCRIPT = "javascript:";
	private boolean m_anchorReturnValue = true;
	static DapEventDispatcher getInstance(){
		return s_instance;
	}
	
	//
	// Package Protected
	//
	boolean dispatchEvent(DapEvent event) {
		setAnchorReturnValue(true);
		if (event == null) {
			return false;
		}
		return executeJsHandler(event);
	}

	//
	// Private
	//
	private boolean executeJsHandler(final DapEvent event) {
		AWindow window = DapCtx.ctx().getWindow();
		if (window.getBrowserType().isIE()) {
			window.setEvent(event);
		}

		String script = getInlineEventCode(event);
		if (script == null) {
			AHtmlElement elem = (AHtmlElement)event.getTarget();
			//execute listeners 
			synchronized (window.getJsExcutionLock()) {
				boolean returnValue = executeListeners(elem, event, true);
				if(elem instanceof AHtmlAnchor){
					if(getAnchorReturnValue()){
						return executeHref((AHtmlAnchor)elem);
					}else{
						return false;
					}
				}
				return returnValue;
			}
		}else{
			return executeJavaScript(script);
		}			
	}
	
	private boolean executeHref(HtmlAnchor elem){
		String href = elem.getHref();
		int ind = href.indexOf(JAVASCRIPT);
		if(ind!=-1){
			String hrefScript = href.substring(ind+JAVASCRIPT.length());
			if(!"".equals(hrefScript)){
				executeJavaScript(hrefScript);
				return false;
			}
		}else if(href!=null && "".equals(href.trim())){
			return false;
		}
		return true;
	}
	
	private boolean executeJavaScript(String script){
		AWindow window = DapCtx.ctx().getWindow();
		DapCtx.ctx().getDapConfig().getInfoCollector().log("Rhino => " + script);
		Object ret = ScriptExecutor.executeScript(script, window);
		if (ret instanceof Boolean) {
			return ((Boolean)ret).booleanValue();
		}
		return true;
	}
	
	private final String ON = "on";
	private String getInlineEventCode(final DapEvent event) {
		String js = null;
		//getting the source 
		Node node = DapEventHelper.getInstance().getSrcNode(event);
		if(node!=null && node instanceof ANode){
			DNode dNode = ADomHelper.getDNode((ANode)node);

			//getting attributes
			NamedNodeMap map = dNode.getAttributes();
			String eventName = event.getType();
			if (eventName !=null && eventName.indexOf(ON) == -1) {
				eventName = ON + eventName;
			}
			if(map!=null){
				//getting event attributes. Example: onclick, ondblclick...etc
				org.w3c.dom.Node n1 = map.getNamedItem(eventName);
				if (n1 != null) {
					js = n1.getNodeValue();
				}
			}
		}
		return js;
	}
	
	private static class ADomHelper extends AHtmlInternal {
		private static DNode getDNode(ANode aNode){
			return getInternalNode(aNode);
		}
	}

	private boolean executeListeners(ANode elem, DapEvent evt, boolean enableBodyLoad){
		//get the event type
		if(!isValidEvent(evt)){
			return true;
		}
		String eventType = evt.getType();		
		boolean cancelBubble = false;
		boolean preventDefault = false;
		boolean isLoadEvent = eventType.equals(NativeEvent.load.toString());
		boolean bubbleUp = bubbleUpEvent(elem,eventType);
		//get element listeners
		Map<String, List<AJavaScriptHandlerHolder>> listeners = null;
		
		if(elem instanceof IEventListenersCollector){
			//get the listeners
			listeners = ((IEventListenersCollector)elem).getListeners();
		}

		//execute event listeners
		cancelBubble = executeListenersList(listeners,evt);

		//if it's document and event is load, execute document ready events
		if(isLoadEvent && elem instanceof AHtmlDocument){
			executeDocumentReadyListeners((AHtmlDocument)elem, evt);
		}
		
		if(!cancelBubble && !evt.isStopPropagation() && !preventDefault){
			//bubble up event till AHtmlDocument reached (except onload event)
			if(elem.getParentNode()!=null && bubbleUp){
				ANode parent = (ANode)elem.getParentNode();
				cancelBubble = !executeListeners(parent,evt,false);
			}
		}
		return !cancelBubble;
	}

	private boolean bubbleUpEvent(ANode elem,String eventType){
		if(eventType.equals(NativeEvent.load.toString()) && 
				(elem instanceof AHtmlImage || elem instanceof AImage)){
			return false;
		}
		return true;
	}
	
	private void executeDocumentReadyListeners(AHtmlDocument doc, DapEvent evt){
		String eventType = evt.getType();
		boolean isLoadEvent = eventType.equals("load");		
		if (isLoadEvent) {
			DapEvent readyEvent = new DapEvent();
			if (DapCtx.ctx().getWindow().getBrowserType().isIE()) {
				// IE specific
				readyEvent.setEventType(EventType.READYSTATECHANGE);
			} else {
				readyEvent.setEventType(EventType.DOMCONTENTLOADED);
			}
			readyEvent.setTarget(doc);
			Map<String, List<AJavaScriptHandlerHolder>> listeners = doc.getListeners();
			executeListenersList(listeners, readyEvent);
		}
	}
	
	private boolean isValidEvent(DapEvent evt){
		String eventType = evt.getType();		
		if(eventType==null || "".equals(eventType.trim())){
			return false;
		}
		return true;
	}
	
	private boolean executeListenersList(Map<String, List<AJavaScriptHandlerHolder>> listeners,DapEvent evt){
		AWindow win = DapCtx.ctx().getWindow();
		boolean cancelBubble = false;
		boolean preventDefault = false;
//		boolean cancelable = false;
		boolean returnValue = false;
		String eventType = evt.getType();
		EventTarget target = evt.getTarget();
		boolean isElement = target instanceof AHtmlElement;
		eventType = AHtmlHelper.getCorrectType(eventType);
		
		////////////////////////////////////////////////////////
		///if(eventType.equals(EventType.DOMCONTENTLOADED.getName())) {
		//	eventType = "load";
		//}
		////////////////////////////////////////////////////////
		
		//get the listeners and execute them
		if (listeners != null && listeners.get(eventType) != null) {
			List<AJavaScriptHandlerHolder> lst = listeners.get(eventType);
			for (AJavaScriptHandlerHolder handlerHolder : lst) {
				Object handler = handlerHolder.getHandler();
				if (handler instanceof IJsJavaProxy) {
					handler = ((IJsJavaProxy)handler).getJsNative();
				}
				if (handler instanceof Function) {
					Function f = (Function) handler;
					Object[] args = { evt };
					Scriptable this_ = win.getScope();
					Scriptable functionScope =  f.getParentScope();
					if(handlerHolder.getHandlerType().equals(JAVASCRIPT_HANDLER_TYPE.INLINE)){
						if(isElement){
							this_ = (AHtmlElement)target;
						}
					}
					Object ret = f.call(win.getContext(),functionScope, this_, args);
					if (ret instanceof Boolean && target instanceof AHtmlAnchor) {
						setAnchorReturnValue(((Boolean)ret).booleanValue());
					}
					cancelBubble = evt.getCancelBubble()||evt.isStopPropagation();
					preventDefault = evt.getDefaultPrevented();
//					cancelable = evt.getCancelable();
					returnValue = evt.getReturnValue();
					if (evt.isStopImmediatePropagation() && isElement) {
						return false;
					}
					if (cancelBubble && isElement) {
						break;
					} else {
						if(isElement){
							AHtmlElement elem = (AHtmlElement)target;
							if (!returnValue || preventDefault) {
								ANode parent = (ANode) elem.getParentNode();
								executeListeners(((AHtmlElement) parent), evt, false);
								/* prevent default for checkbox or radio */
								if (target instanceof AHtmlInput) {
									String tagName = ((AHtmlInput) elem).getType()
											.toLowerCase();
									if (tagName.equals("radio")
											|| tagName.equals("checkbox")) {
										AHtmlInput ele = (AHtmlInput) elem;
										ele.setChecked(!ele.getChecked());
									} else if (tagName.equals("text")
											|| tagName.equals("textarea")) {
										AHtmlInput ele = (AHtmlInput) elem;
										ele.setValue(ele.getDefaultValue());
									}
								} else if (elem instanceof AHtmlTextArea) {
									AHtmlTextArea ele = (AHtmlTextArea) elem;
									ele.setValue(ele.getDefaultValue());
								} else if (elem instanceof AHtmlAnchor) {
									setAnchorReturnValue(false);
								}
								return false;
							}
						}
					}
				} else {
					ScriptExecutor.executeScript(handler.toString(), win);
				}
			}
		}
		return cancelBubble;
	}

	private boolean getAnchorReturnValue() {
		return m_anchorReturnValue;
	}

	private void setAnchorReturnValue(boolean returnValue) {
		m_anchorReturnValue = returnValue;
	}
}