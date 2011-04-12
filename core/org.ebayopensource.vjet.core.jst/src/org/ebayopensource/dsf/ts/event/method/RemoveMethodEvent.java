/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.method;

import org.ebayopensource.dsf.ts.event.ISourceEvent;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.ts.method.MethodName;


public final class RemoveMethodEvent extends MethodEvent {

	//
	// Constructors
	//
	public RemoveMethodEvent(MethodName mtdName, boolean isStatic){
		super(mtdName, isStatic);
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
		((IMethodEventListener)listener).onMethodRemoved(this);
	}
	
	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener,IEventListenerHandle,ISourceEventCallback)
	 */
	public void dispatch(ISourceEventListener listener, IEventListenerHandle handle, ISourceEventCallback callback){
		if (listener == null){
			return;
		}
		((IMethodEventListener)listener).onMethodRemoved(this);
	}
	
	public boolean shouldLock() { return true; }
}
