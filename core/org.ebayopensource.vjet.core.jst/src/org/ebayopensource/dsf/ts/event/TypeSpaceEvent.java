/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event;

import org.ebayopensource.dsf.common.Z;

/**
 * Default implementation of ITypeSpaceEvent
 * 
 * @param T type of trigger
 * 
 * 
 */
public class TypeSpaceEvent<T> implements ITypeSpaceEvent {
	
	private EventId m_id;
	private T m_trigger;
	
	//
	// Constructors
	//
	/**
	 * Constructor
	 * @param id EventId
	 */
	public TypeSpaceEvent(EventId id){
		m_id = id;
	}
	
	/**
	 * Constructor
	 * @param id EventId
	 * @param trigger T
	 */
	public TypeSpaceEvent(EventId id, T trigger){
		m_id = id;
		m_trigger = trigger;
	}
	
	//
	// Satisfy ITypeSpaceEvent
	//
	/**
	 * @see ITypeSpaceEvent#isAppropriateListener(ITypeSpaceEventListener)
	 */
	public boolean isAppropriateListener(ITypeSpaceEventListener listener){
		return true;
	}
	
	/**
	 * @see ITypeSpaceEvent#dispatch(ITypeSpaceEventListener, EventListenerStatus)
	 */
	public void dispatch(ITypeSpaceEventListener listener, final EventListenerStatus status){
		if (listener == null){
			return;
		}
		if (m_id == EventId.Loaded){
			listener.onLoaded(this, status);
		}
		else if (m_id == EventId.Updated){
			listener.onUpdated(this, status);
		}
		else if (m_id == EventId.Unloaded){
			listener.onUnloaded(this, status);
		}
	}

	//
	// API
	//
	/**
	 * Answer the id of this event
	 * @return EventId
	 */
	public EventId getId(){
		return m_id;
	}
	
	public T getTrigger(){
		return m_trigger;
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("m_id", m_id);
		if (m_trigger != null){
			z.format("m_trigger", m_trigger.getClass().getSimpleName());
		}
		return z.toString();
	}
	
	//
	// Inner
	//
	public static enum EventId {
		Init, Loaded, Updated, Unloaded
	}
}
