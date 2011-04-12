/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event;

/**
 * Interface for all source related events that
 * Type Space needs to be notified..
 * 
 * 
 */
public interface ISourceEvent<H> {
	
	/**
	 * Answer whether the given listener is appropriate
	 * @param listener ISourceEventListener
	 * return boolean
	 */
	boolean isAppropriateListener(ISourceEventListener listener);
	
	/**
	 * Dispatch the event to the given listener in synchronous fashion
	 * @param listener ISourceEventListener
	 */
	void dispatch(ISourceEventListener listener);

	/**
	 * Dispatch the event to the given listener in asynchronous fashion. 
	 * @param listener ISourceEventListener
	 * @param handle H
	 * @param callback ISourceEventCallback
	 */
	void dispatch(ISourceEventListener listener, H handle, ISourceEventCallback callback);
	
	/**
	 * Indicate if event processing should be locked exclusively
	 * TRUE if processing is short
	 * FALSE if processing is long and lock should be done in smaller scope
	 * @return TRUE if event processing should be locked
	 */
	boolean shouldLock();
}
