/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.dom.DBody;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;

public class EventHandlerContainer {
	private Map<DsfEventTarget, List<EventsToHandlerPair>> m_targets ;
	private Map<DsfEventTarget, List<EventsToHandlerPair>> m_elementsAttached ;
	
	private EventsToHandlerPair getPair(
		final DsfEventTarget elem, final ISimpleJsEventHandler handler) 
	{
		final List<EventsToHandlerPair> list = getElements().get(elem) ;
		final Iterator<EventsToHandlerPair> pairs = list.iterator() ;
		while(pairs.hasNext()) {
			EventsToHandlerPair pair = pairs.next() ;
			if (pair.getHandler() == handler) {
				return pair ;
			}
		}
		return null ;
	}
		
	public void add(
		final String elementId, 
		final IDomEventType event,
		final ISimpleJsEventHandler handler)
	{
		DsfEventTarget target = getTarget(elementId);
		if (target == null) {
			target = new DsfEventTarget();
			target.setId(elementId);
		}
		add(target, event, handler);
	}
	
	public void add(
		final DElement elem, 
		final IDomEventType event,
		final ISimpleJsEventHandler handler)
	{
		if (elem instanceof DBody) {//fix for remote cases
			add(HtmlTypeEnum.BODY.getName(),event,handler);	
			return;
		}
		DsfEventTarget target = getTarget(elem);
		if (target == null) {
			target = new DsfEventTarget();
			target.setElem(elem);
		}
		add(target, event, handler);
	}
	
	public void add(
		final IDomType elem, 
		final IDomEventType event,
		final ISimpleJsEventHandler handler)
	{
		if (HtmlTypeEnum.BODY.equals(elem)) {//fix for remote cases
			add(HtmlTypeEnum.BODY.getName(),event,handler);
			return;
		}
		DsfEventTarget target = getTarget(elem);
		if (target == null) {
			target = new DsfEventTarget();
			target.setType(elem);
		}
		add(target, event, handler);
	}
	
	private void add(
		final DsfEventTarget elem, 
		final IDomEventType eventType, 
		final ISimpleJsEventHandler handler)
	{
		final EventsToHandlerPair newPair 
			= new EventsToHandlerPair(eventType, handler);
		
		if(getElements().containsKey(elem)) {
			if (getPair(elem, handler) == null) {
				getElements().get(elem).add(newPair) ;
			}
		} 
        else {
        	final List<EventsToHandlerPair> pairs 
        		= new ArrayList<EventsToHandlerPair>(3);
			pairs.add(newPair);
			getElements().put(elem, pairs);
		}		
	}
	
	public void addForAttached(
		final String elementId, 
		final IDomEventType event,
		final ISimpleJsEventHandler handler)
	{
		final DsfEventTarget target = new DsfEventTarget();
		target.setId(elementId);
		addForAttached(target, event, handler);
	}
	
	public void addForAttached(
		final DElement elem, 
		final IDomEventType event,
		final ISimpleJsEventHandler handler)
	{
		final DsfEventTarget target = new DsfEventTarget();
		target.setElem(elem);
		addForAttached(target, event, handler);
	}
	
	public void addForAttached(
		final IDomType elem, 
		final IDomEventType event,
		final ISimpleJsEventHandler handler)
	{
		final DsfEventTarget target = new DsfEventTarget();
		target.setType(elem);
		addForAttached(target, event, handler);
	}
	
	/**
	 * Add handlers for elements that have triggers already attached
	 * @param elem DSFEventTarget the element the handler is associated with
	 * @param events IDomEventType[] The events associated with the handler
	 * @param handler JsEventHandlerAdapter
	 */
	private void addForAttached(
		final DsfEventTarget elem, 
		final IDomEventType event, 
		final ISimpleJsEventHandler handler)
	{
		if( getElementsAttached().containsKey(elem)) {
			if(getPair(elem, handler) == null) {
				getElementsAttached().get(elem).add(
					new EventsToHandlerPair(event, handler));
			}
		} 
        else {
        	final List<EventsToHandlerPair> pairs 
        		= new ArrayList<EventsToHandlerPair>(3);
			pairs.add(new EventsToHandlerPair(event, handler));
			getElementsAttached().put(elem, pairs);
		}		
	}
	
//	public void add(DSFEventTarget elem, IEventHandler handler) {
//		
//		if(getElements().containsKey(elem)) {
//			if(!getElements().get(elem).contains(handler)) {
//				getElements().get(elem).add(handler);
//			}
//		} 
//        else {
//			List<IEventHandler> handlers = new ArrayList<IEventHandler>();
//			handlers.add(handler);
//			getElements().put(elem,handlers);
//		}
//	}
    
	public int size() {
		if (m_targets == null) {
			return 0;
		}
		return m_targets.size();
	}
    
	public boolean removeHandler(
		final DElement elem, final ISimpleJsEventHandler handler)
	{
		return removeHandler(getTarget(elem), handler);
	}
	
	public boolean removeHandler(
		final String elem, final ISimpleJsEventHandler handler)
	{
		return removeHandler(getTarget(elem), handler);
	}
	
	public boolean removeHandler(
		final IDomType elem, final ISimpleJsEventHandler handler)
	{
		return removeHandler(getTarget(elem), handler);
	}
	
	private boolean removeHandler(
		final DsfEventTarget elem, final ISimpleJsEventHandler handler)
	{
		if (getElements().containsKey(elem) == false) {
			return false ;
		}
		
		final EventsToHandlerPair pair = getPair(elem, handler) ;
		if (pair == null) {
			return false ;
		}
		
		return getElements().get(elem).remove(pair) ;
	}
	
	public void reset() {
		m_elementsAttached = null;
		m_targets = null;
	}
	
	public void removeHandlers(final String elem, final IDomEventType event) {
		removeHandlers(getTarget(elem), event);
	}

	public void removeHandlers(final DElement elem, final IDomEventType event) {
		removeHandlers(getTarget(elem), event);
		//removeHandlers(getTarget(elem.getHtmlId()), event);
	}

	public void removeHandlers(final IDomType elem, final IDomEventType event) {
		removeHandlers(getTarget(elem), event);
		//removeHandlers(getTarget(elem.getName()), event);
	}

	public void removeHandlers(final DsfEventTarget elem, final IDomEventType event) {
		if (elem==null) {
			return;
		}
		if (getElements().containsKey(elem) == false) {
			return;
		}
		final List<EventsToHandlerPair> list = getElements().get(elem);
		int len = list.size();
		for (int i=0; i < len; i++) {
			EventsToHandlerPair pair = list.get(i);
			if (pair.getEventType().equals(event)) {
				getElements().get(elem).remove(i);
				i--;
				len--;
			}
		}
	}
	
	public Iterable<EventsToHandlerPair> iterable(final DElement elem) {
		return getElements().get(getTarget(elem));
	}
	
	/** If there are no elements, this will create an empty Map and return it.
	 * Call size().
	 * @return Returns the elements.
	 */
	public Map<DsfEventTarget, List<EventsToHandlerPair>> getElements() {
		if (m_targets == null) {
// TODO: Why create size 0 map?
			 m_targets = new LinkedHashMap<
                DsfEventTarget, List<EventsToHandlerPair>>(0);
		}
		return m_targets;		
	}
	
	/**
	 * Answer the map of triggered-attached elements with their events/handler pairs
	 * @return Map<DSFEventTarget, List<EventsToHandlerPair>>
	 */
	// TODO -- this doesn't appear to do anything
	public Map<DsfEventTarget, List<EventsToHandlerPair>> getElementsAttached() {
		if(m_elementsAttached == null) {
// TODO: Why create size 0 map?
			m_elementsAttached = new LinkedHashMap<
                DsfEventTarget, List<EventsToHandlerPair>>(0);
		}
		return m_elementsAttached;		
	}
	
//	public Map<DSFEventTarget, List<IEventHandler>> getElements() {
//		if(m_elements == null) {
//			 m_elements = new LinkedHashMap<
//                 DSFEventTarget, List<IEventHandler>>(3);
//		}
//		return m_elements;
//	}

//	/**
//	 * @param elements The elements to set.
//	 */
//	public void setElements(
//            Map<DSFEventTarget, List<IEventHandler>> elements)
//    {
//		m_elements = elements;
//	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {		
		if(getElements().isEmpty()) {
			return "No event handlers for this element" ;
		}
        
		// TODO: Put in a meaningul toString()
        final StringBuilder buffer = new StringBuilder();

	    return buffer.toString();
	}

	public int getNumOfHandlers(final DElement elem) {
		return getNumOfHandlers(getTarget(elem));
	}
	
	public int getNumOfHandlers(final String elem) {
		return getNumOfHandlers(getTarget(elem));
	}
	
	public int getNumOfHandlers(final DsfEventTarget elem) {
		int num = 0;
		if(getElements().get(elem)!=null){
			num += getElements().get(elem).size();
		}
		if(getElementsAttached().get(elem)!=null){
			num += getElementsAttached().get(elem).size();
		}
		return num;
	}
	
	private DsfEventTarget getTarget(final DElement elem) {
		DsfEventTarget target = null;
		final Iterator<DsfEventTarget> iter = getElements().keySet().iterator();
		
		while (iter.hasNext()) {
			DsfEventTarget tmp = iter.next();
			if (tmp.getElem()!=null && tmp.getElem().equals(elem)) {
				target = tmp;
				break;
			}
		}
		return target;
	}
	
	private DsfEventTarget getTarget(final String elem) {
		DsfEventTarget target = null;
		final Iterator<DsfEventTarget> iter = getElements().keySet().iterator();
		
		while (iter.hasNext()) {
			DsfEventTarget tmp = iter.next();
			if (tmp.getId()!=null && tmp.getId().equals(elem)) {
				target = tmp;
				break;
			}
		}
		return target;
	}
	
	private DsfEventTarget getTarget(final IDomType elem) {
		DsfEventTarget target = null;
		final Iterator<DsfEventTarget> iter = getElements().keySet().iterator();
		
		while (iter.hasNext()) {
			DsfEventTarget tmp = iter.next();
			if (tmp.getType()!=null && tmp.getType().equals(elem)) {
				target = tmp;
				break;
			}
		}
		return target;
	}
	
//	private boolean containsElement(final Object key) {
//		DSFEventTarget target = null;
//		Iterator<DSFEventTarget> iter = getElements().keySet().iterator();
//		
//		while (iter.hasNext()) {
//			target = iter.next();
//			if (target.getType() == key || target.getId() == key
//					|| target.getType() == key)
//				return true;
//		}
//		return false;
//	}
	
	//
	// Helper class(es)
	//
	/**
	 * Simple ReadOnly bean representing the tuple {IDomEventType, IEventHandler}
	 */
	public static class EventsToHandlerPair {
		private final IDomEventType m_eventType ;
		private final ISimpleJsEventHandler m_handler ;
		
		//
		// Constructor(s)
		//
		public EventsToHandlerPair(
			final IDomEventType event, final ISimpleJsEventHandler handler)
		{
			m_eventType = event;
			m_handler = handler ;
		}
		
		//
		// API
		//
		public IDomEventType getEventType() { 
			return m_eventType ;
		}
		
		public ISimpleJsEventHandler getHandler() { 
			return m_handler ;
		}
		
		//
		// Override(s)
		//
		@Override
		public String toString() {
			return getEventType().toString() 
				+ " -> " + getHandler().asJsHandler();
		}
	}
}
