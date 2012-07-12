/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.event;

import java.util.EventObject;

import org.ebayopensource.dsf.common.Z;

/**
 * Root of all DSF event objects.  The only framework requirement is for
 * concrete implementations to implement the public
 * @see #isAppropriateListener(IDsfEventListener) method
 * <p>
 * The framework uses a double-dispatch for event processing.  In this event
 * model each event is asked if it is "appropriate" for a passed in
 * @see IDsfListener.   
 **/
public abstract class DsfEvent<S, L extends IDsfEventListener> 
	extends EventObject
{
	private final Class<L> m_listenerType;
	
	//
	// Constructor(s)
	//
	protected DsfEvent(final S source, final Class<L> clz) {
		super(source);
		m_listenerType = clz;
	}
	
	@SuppressWarnings("unchecked")	// We can trust the <S> so its ok
	@Override
	public S getSource() {
		return (S)super.getSource() ;
	}
	
	//
	// Framework
	//
	public boolean isAppropriateListener(final IDsfEventListener listener) {
		// default impl says if you are the right type of lister you are ok
		return m_listenerType.isInstance(listener);
	}
	
	public abstract boolean dispatch(final L listener)
		throws AbortDsfEventProcessingException;

	public Class<L> getListenerType(){
		return m_listenerType;
	}
	
	//
	// Override(s) from Object
	//
	@Override
	public String toString(){
		Z z = new Z();
		
		z.format("source", source);
		z.format("listener", m_listenerType == null ? null : m_listenerType.getName());
		
		return z.toString();
	}
}
