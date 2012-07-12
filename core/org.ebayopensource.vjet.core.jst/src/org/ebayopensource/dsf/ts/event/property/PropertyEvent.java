/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.property;

import org.ebayopensource.dsf.ts.event.ISourceEvent;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.ts.property.PropertyName;
import org.ebayopensource.dsf.common.Z;

public abstract class PropertyEvent implements ISourceEvent<IEventListenerHandle> {
	
	private PropertyName m_ptyName;
	private boolean m_isStatic;
	
	//
	// Constructors
	//
	PropertyEvent(PropertyName ptyName, boolean isStatic){
		assert ptyName != null : "ptyName cannot be null";
		m_ptyName = ptyName;
		m_isStatic = isStatic;
	}

	//
	// Satisfy IJstEvent
	//
	public boolean isAppropriateListener(ISourceEventListener listener){
		return listener instanceof IPropertyEventListener;
	}

	//
	// API
	//
	public PropertyName getPropertyName(){
		return m_ptyName;
	}
	
	public boolean isPropertyStatic(){
		return m_isStatic;
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("eventType", getClass().getSimpleName());
		z.format("m_ptyName", m_ptyName);
		return z.toString();
	}
}
