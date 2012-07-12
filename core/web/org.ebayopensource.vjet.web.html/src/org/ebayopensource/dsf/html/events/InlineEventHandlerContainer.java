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

import org.ebayopensource.dsf.html.dom.BaseHtmlElement;

public final class InlineEventHandlerContainer {
	private Map<DsfEventTarget, List<EventsToHandlerPair>> m_targets;

	//
	// API
	//
	public void add(
		final BaseHtmlElement elem, final IDomEventType event,final String handler)
	{
		DsfEventTarget target = getTarget(elem);
		if (target == null) {
			target = new DsfEventTarget();
			target.setElem(elem);
		}
		add(target, event, handler);
	}

	private DsfEventTarget getTarget(final BaseHtmlElement elem) {
		final Iterator<DsfEventTarget> iter = getElements().keySet().iterator();

		DsfEventTarget target = null;
		while (iter.hasNext()) {
			DsfEventTarget tmp = iter.next();
			if (tmp.getElem() != null && tmp.getElem().equals(elem)) {
				target = tmp;
				break;
			}
		}
		return target;
	}

	private EventsToHandlerPair getPair(
		final DsfEventTarget elem, IDomEventType eventType) {
		final List<EventsToHandlerPair> list = getElements().get(elem);
		final Iterator<EventsToHandlerPair> pairs = list.iterator();
		while (pairs.hasNext()) {
			EventsToHandlerPair pair = pairs.next();
			if (pair.getEventType() == eventType) {
				return pair;
			}
		}
		return null;
	}

	private void replacePair(
		final DsfEventTarget elem,
		EventsToHandlerPair oldPair,
		EventsToHandlerPair newPair)
	{
		final List<EventsToHandlerPair> list = getElements().get(elem);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(oldPair)) {
				list.set(i, newPair);
			}
		}
	}
	
	private void add(
		final DsfEventTarget elem, final IDomEventType eventType,final String handler)
	{
		final EventsToHandlerPair newPair 
			= new EventsToHandlerPair(eventType,handler);

		if (getElements().containsKey(elem)) {
			EventsToHandlerPair pair = getPair(elem, eventType); 
			if (pair == null) {
				getElements().get(elem).add(newPair);
			} 
			else {
				replacePair(elem,pair,newPair);
			}
		} 
		else {
			final List<EventsToHandlerPair> pairs 
				= new ArrayList<EventsToHandlerPair>(3);
			pairs.add(newPair);
			getElements().put(elem, pairs);
		}
	}

	public int size() {
		if (m_targets == null) {
			return 0;
		}
		return m_targets.size();
	}

	public void reset() {
		m_targets = null;
	}

	public Iterable<EventsToHandlerPair> iterable(
			final BaseHtmlElement elem) {
		return getElements().get(getTarget(elem));
	}

	/** If there are no elements, this will create an empty Map and return it.
	 * Call size().
	 * @return Returns the elements.
	 */
	public Map<DsfEventTarget, List<EventsToHandlerPair>> getElements() {
		if (m_targets == null) {
			m_targets = new LinkedHashMap<DsfEventTarget, List<EventsToHandlerPair>>(
					0);
		}
		return m_targets;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (getElements().isEmpty()) {
			return "No event handlers for this element";
		}

		// TODO: Put in a meaningul toString()
		final StringBuilder buffer = new StringBuilder();

		return buffer.toString();
	}

	//
	// Helper class(es)
	//
	/**
	 * Simple ReadOnly bean representing the tuple {EventType, IEventHandler}
	 */
	public static class EventsToHandlerPair {
		private final IDomEventType m_eventType;

		private final String m_handler;

		//
		// Constructor(s)
		//
		public EventsToHandlerPair(final IDomEventType event, final String handler) {
			m_eventType = event;
			m_handler = handler;
		}

		//
		// API
		//
		public IDomEventType getEventType() {
			return m_eventType;
		}

		public String getHandler() {
			return m_handler;
		}
	}
}
