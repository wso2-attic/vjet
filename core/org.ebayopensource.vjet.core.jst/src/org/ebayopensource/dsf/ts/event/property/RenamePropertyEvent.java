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
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.ts.property.PropertyName;


public final class RenamePropertyEvent extends PropertyEvent {
	
	private String m_newPtyName;

	//
	// Constructors
	//
	public RenamePropertyEvent(PropertyName oldPtyName, String newPtyName, boolean isStatic){
		super(oldPtyName, isStatic);
		assert newPtyName != null : "newPtyName cannot be null";
		m_newPtyName = newPtyName;
	}

	//
	// Satisfy IJstEvent
	//
	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener)
	 */
	public void dispatch(ISourceEventListener listener){
		if (listener == null){
			return;
		}
		((IPropertyEventListener)listener).onPropertyRenamed(this);
	}
	
	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener,IEventListenerHandle,ISourceEventCallback)
	 */
	public void dispatch(ISourceEventListener listener, IEventListenerHandle handle, ISourceEventCallback callback){
		if (listener == null){
			return;
		}
		((IPropertyEventListener)listener).onPropertyRenamed(this);
	}
	
	//
	// API
	//
	public String getNewPropertyName(){
		return m_newPtyName;
	}
	
	public boolean shouldLock() { return true; }
}
