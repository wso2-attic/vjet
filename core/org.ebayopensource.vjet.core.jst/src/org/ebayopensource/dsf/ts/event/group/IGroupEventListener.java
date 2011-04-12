/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.group;

import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;


/**
 * Listener for group events
 * 
 * 
 */
public interface IGroupEventListener<T> extends ISourceEventListener {

	/**
	 * Handles AddGroupEvent
	 * @param event
	 * @param handle
	 * @param callBack
	 * @return EventListenerStatus
	 */
	EventListenerStatus<T> onGroupAdded(AddGroupEvent event, IEventListenerHandle handle, ISourceEventCallback<T> callBack);
	
	/**
	 * Handles BatchGroupLoadingEvent during initial loading of types in all groups
	 * @param event
	 * @param handle
	 * @param callBack
	 *  @return EventListenerStatus
	 */
	EventListenerStatus<T> onBatchGroupLoaded(BatchGroupLoadingEvent event, IEventListenerHandle handle, ISourceEventCallback<T> callBack);
	
	/**
	 * Handles AddGroupDependencyEvent
	 * @param event
	 * @param handle
	 * @param callBack
	 * @return EventListenerStatus
	 */
	EventListenerStatus<T> onGroupAddDependency(AddGroupDependencyEvent event, IEventListenerHandle handle, ISourceEventCallback<T> callBack);
	
	
	/**
	 * 
	 * @param RemoveGroupDependencyEvent
	 * @param handle
	 * @param callBack
	 * @return
	 */
	EventListenerStatus<T> onGroupRemoveDependency(RemoveGroupDependencyEvent event, IEventListenerHandle handle, ISourceEventCallback<T> callBack);
	
	/**
	 * Handles RemoveGroupEvent
	 * @param event RemoveGroupEvent
	 */
	void onGroupRemoved(RemoveGroupEvent event);
}
