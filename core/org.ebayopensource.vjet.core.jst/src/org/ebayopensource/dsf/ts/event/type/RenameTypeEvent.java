/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.type;

import org.ebayopensource.dsf.ts.event.ISourceEvent;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.ts.type.TypeName;

public final class RenameTypeEvent extends TypeEvent {
	
	private String m_newTypeName;

	//
	// Constructors
	//
	public RenameTypeEvent(TypeName oldTypeName, String newTypeName){
		super(oldTypeName);
		if (oldTypeName == null){
			throw new AssertionError("oldTypeName cannot be null");
		}
		if (newTypeName == null){
			throw new AssertionError("newTypeName cannot be null");
		}
		m_newTypeName = newTypeName;
	}

	//
	// Satisfy IJstEvent
	//
	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener)
	 */
	public void dispatch(ISourceEventListener listener){
		if (listener == null || !isAppropriateListener(listener)){
			return;
		}
		((ITypeEventListener)listener).onTypeRenamed(this, null, null);
	}
	
	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener)
	 */
	public void dispatch(ISourceEventListener listener, IEventListenerHandle handle, ISourceEventCallback callback){
		if (listener == null || !isAppropriateListener(listener)){
			return;
		}
		((ITypeEventListener)listener).onTypeRenamed(this, handle, callback);
	}
	
	//
	// API
	//
	public String getNewTypeName(){
		return m_newTypeName;
	}
	
	public boolean shouldLock() { return true; }
}
