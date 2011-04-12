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
 * Interface for all type space related events that
 * clients of Type Space need to be notified..
 * 
 * 
 */
public interface ITypeSpaceEvent {
	/**
	 * Answer whether the given listener is appropriate
	 * @param listener ITypeSpaceEventListener
	 * return boolean
	 */
	boolean isAppropriateListener(ITypeSpaceEventListener listener);

	/**
	 * Dispatch the event to the given listener with status
	 * @param listener ITypeSpaceEventListener
	 * @param status EventListenerStatus
	 */
	public void dispatch(ITypeSpaceEventListener listener, final EventListenerStatus status);
}
