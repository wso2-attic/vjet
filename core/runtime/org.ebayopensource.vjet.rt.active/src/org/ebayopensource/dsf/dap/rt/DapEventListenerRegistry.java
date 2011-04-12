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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.event.listener.DapEventListenerHelper;
import org.ebayopensource.dsf.dap.event.listener.IDapEventListener;
import org.ebayopensource.dsf.dap.util.DapDomHelper;
import org.ebayopensource.dsf.html.ctx.HtmlCtx;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.html.events.EventHandlerContainer;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
import org.ebayopensource.dsf.html.events.EventHandlerContainer.EventsToHandlerPair;
import org.ebayopensource.dsf.html.js.IJsFunc;

/**
 * Runtime registry for DAP event handlers
 * 
 * 
 */
public final class DapEventListenerRegistry {
	
	private static final String BODY = "body";
	
	private Map<String,List<IDapEventListener>> m_listeners = 
		new LinkedHashMap<String,List<IDapEventListener>>();
	
	//
	// API
	//
	/**
	 * Add given listener to the body element for all event types
	 * that the listener implements handler interfaces for.
	 * @param listener IDapEventListener
	 */
	public void addBodyListener(final IDapEventListener listener) {
		addListener (BODY, listener);
	}
	
	/**
	 * Add given listener to the body element for given event type
	 * @param eventType EventType
	 * @param listener IDapEventListener
	 */
	public void addBodyListener(final EventType eventType, final IDapEventListener listener) {
		addListener (BODY, eventType, listener);
	}
	
	/**
	 * Add given listener to the given element for all event types
	 * that the listener implements handler interfaces for.
	 * @param elem BaseHtmlElement
	 * @param listener IDapEventListener
	 */
	public void addListener(final BaseHtmlElement elem, final IDapEventListener listener) {
		addListener(DapDomHelper.getId(elem), listener);
	}
	
	/**
	 * Add given listener to the element with given id for all event types
	 * that the listener implements handler interfaces for.
	 * @param elemId String
	 * @param listener IDapEventListener
	 */
	public void addListener(final String elemId, final IDapEventListener listener) {

		if (elemId == null){
			throw new AssertionError("elemId cannot be null");
		}
		
		if (listener == null){
			throw new AssertionError("listener cannot be null");
		}
		
		Class[] interfaces = listener.getClass().getInterfaces();
		for (Class<IDapEventListener> itf: interfaces) {
			//Class<IDapEventListener> is not truely typed, it is same as Class
			//so we still need following "if" check
			if (!IDapEventListener.class.isAssignableFrom(itf)) {
				continue;
			}
			for (EventType evtType: DapEventListenerHelper.getSupportedEventTypes(itf)){
				addListener(elemId, evtType, listener);
			}
		}
	}
	
	/**
	 * Add given listener to the given element for given event type.
	 * @param elem Element
	 * @param eventType EventType
	 * @param listener IDapEventListener
	 */
	public void addListener(
			final BaseHtmlElement elem, 
			final EventType eventType,
			final IDapEventListener listener) {
		
		addListener(DapDomHelper.getId(elem), eventType, listener);
	}
	
	/**
	 * Add given listener to the element with given id for given event type.
	 * @param elemId String
	 * @param eventType EventType
	 * @param listener IDapEventListener
	 */
	public void addListener(
			final String elemId, 
			final EventType eventType,
			final IDapEventListener listener) {
		
		if (elemId == null){
			throw new AssertionError("elemId cannot be null");
		}
		
		if (eventType == null){
			throw new AssertionError("eventType cannot be null");
		}
		
		if (listener == null){
			throw new AssertionError("listener cannot be null");
		}
		
		EventHandlerContainer container = HtmlCtx.ctx().getEventHandlerContainer();
		
		// Active Mode
		if (DapCtx.ctx().isActiveMode()){
			int index = add(elemId, listener);
			ISimpleJsEventHandler handler = listener.getEventHandlerAdapter(elemId, index);
			if (BODY.equals(elemId)){
				container.add(HtmlTypeEnum.BODY, eventType, handler);
			}
			else {
				container.add(elemId, eventType, handler);
			}
			return;
		}
		
		// Translate|Web Mode
		Map<EventType,IJsFunc> handlers = listener.getProxyEventHandlers();
		if (handlers != null){
			IJsFunc handler = getHandler(handlers, eventType);
			if (handler != null){
				container.add(elemId, eventType, listener.getEventHandlerAdapter(handler));
				return;
			}
		}

		throw new DsfRuntimeException("js proxy handlers are required for non-active modes:" +
				"mode=" + DapCtx.ctx().getExeMode() +
				", elem=" + elemId +
				", eventType=" + eventType +
				", listenerType=" + listener.getClass().getName());
	}
	
	/**
	 * Remove given listener from given element for given event type.
	 * @param elem Element
	 * @param eventType EventType
	 * @param listener IDapEventListener
	 */
	public void remove(
			final BaseHtmlElement elem, 
			final EventType eventType,
			final IDapEventListener listener) {
		
		EventHandlerContainer container = HtmlCtx.ctx().getEventHandlerContainer();
		String elemId = DapDomHelper.getId(elem);
		
		// Active Mode
		if (DapCtx.ctx().isActiveMode()){
			List<IDapEventListener> listeners = getListeners(elemId, true);
			if (listeners.contains(listener)){
				listeners.remove(listener);
			}
			container.removeHandler(elem, listener.getEventHandlerAdapter(elemId, listeners.indexOf(listener)));
			return;
		}
		
		// Translate|Web Mode
		if (listener.getProxyEventHandlers() != null){
			IJsFunc handler = getHandler(listener.getProxyEventHandlers(), eventType);
			removeHandler(elem, eventType, handler, container);
		}

		throw new DsfRuntimeException("js proxy handlers are required for non-active mode:" +
				"mode=" + DapCtx.ctx().getExeMode() +
				", elem=" + elemId +
				", eventType=" + eventType +
				", listenerType=" + listener.getClass().getName());
	}
	
	//
	// Package protected
	//
	List<IDapEventListener> getListeners(final String id){
		List<IDapEventListener> list = getListeners(id, false);
		if (list == null){
			return Collections.emptyList();
		}
		else {
			return Collections.unmodifiableList(list);
		}
	}
	
	synchronized Map<String,List<IDapEventListener>> getAllListeners(){
		if (m_listeners == null){
			return Collections.emptyMap();
		}
		else {
			return Collections.unmodifiableMap(m_listeners);
		}
	}
	
	//
	// Private
	//
	private synchronized int add(final String elemId, final IDapEventListener listener){
		
		if (elemId == null){
			return -1;
		}
		
		List<IDapEventListener> listeners = getListeners(elemId, true);
		if (!listeners.contains(listener)){
			listeners.add(listener);
			return listeners.size()-1;
		}
		else {
			return listeners.indexOf(listener);
		}
	}
	
	private synchronized List<IDapEventListener> getListeners(final String id, boolean create){
		if (id == null){
			return null;
		}
		List<IDapEventListener> list = m_listeners.get(id);
		if (list == null){
			if (!create){
				return null;
			}
			else {
				list = new ArrayList<IDapEventListener>(2);
				m_listeners.put(id, list);
			}
		}
		return list;
	}
	
	private IJsFunc getHandler(Map<EventType,IJsFunc> handlers, EventType eventType){
		 if (handlers == null){
			 return null;
		 }
		 for (Entry<EventType,IJsFunc> entry: handlers.entrySet()){
			 if (entry.getKey().equals(eventType)){
				 return entry.getValue();
			 }
		 }
		 return null;
	}
	
	private void removeHandler(
			final BaseHtmlElement elem, 
			final EventType eventType, 
			final IJsFunc handler,
			final EventHandlerContainer container){
		
		if (handler == null){
			return;
		}
		
		List<EventsToHandlerPair> pairs = container.getElements().get(elem);
		DapEventHandlerAdapter adapter;
		if (pairs == null){
			return;
		}
		
		for (EventsToHandlerPair pair: pairs){
			if (pair.getEventType() != eventType){
				continue;
			}
			if (!(pair.getHandler() instanceof DapEventHandlerAdapter)){
				continue;
			}
			adapter = (DapEventHandlerAdapter)pair.getHandler();
			if (adapter == null || adapter.getJsFunc() != handler){
				continue;
			}
			pairs.remove(pair);
			return;
		}
	}
}
