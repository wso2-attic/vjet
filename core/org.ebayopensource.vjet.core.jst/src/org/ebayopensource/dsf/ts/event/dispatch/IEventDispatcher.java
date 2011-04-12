/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.dispatch;

/**
 * Interface for event dispatcher which supports both synchronous and
 * asynchronous modes.
 * 
 * 
 *
 * @param <E> Event
 * @param <L> Listener
 * @param <H> handle
 * @param <C> Callback
 */
public interface IEventDispatcher<E,L,H,C> {
	
	/**
	 * Add event listener
	 * @param listener L
	 */
	void addListener(L listener);
	
	/**
	 * Remove event listener
	 * @param listener L
	 */
	void removeListener(L listener);
	
	/**
	 * Dispatch the event synchronously. The call won't come back
	 * until the event processing is complete, with success or failure.
	 * @param event E
	 */
	void dispatch(E event);
	
	/**
	 * Dispatch the event asynchronously. The call returns immediately
	 * with a handle to communicate with the event processing.
	 * @param event E
	 * @param callback C
	 * @return IDispatcherHandle
	 */
	H dispatch(E event, C callback);
}
