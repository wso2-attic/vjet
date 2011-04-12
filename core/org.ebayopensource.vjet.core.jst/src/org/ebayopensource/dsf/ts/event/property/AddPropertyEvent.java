/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.property;

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.ts.event.ISourceEvent;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.ts.property.PropertyName;


public final class AddPropertyEvent extends PropertyEvent {
	
	private IJstProperty m_pty;
	
	//
	// Constructors
	//
	public AddPropertyEvent(PropertyName ptyName, IJstProperty pty){
		super(ptyName, pty.isStatic());
		m_pty = pty;
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
		((IPropertyEventListener)listener).onPropertyAdded(this);
	}
	
	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener,IEventListenerHandle,ISourceEventCallback)
	 */
	public void dispatch(ISourceEventListener listener, IEventListenerHandle handle, ISourceEventCallback callback){
		if (listener == null){
			return;
		}
		((IPropertyEventListener)listener).onPropertyAdded(this);
	}
	
	//
	// API
	//
	public IJstProperty getProperty(){
		return m_pty;
	}
	
	public boolean shouldLock() { return true; }
}
