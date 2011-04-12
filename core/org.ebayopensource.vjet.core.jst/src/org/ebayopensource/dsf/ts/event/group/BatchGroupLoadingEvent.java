/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.group;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.ts.event.ISourceEvent;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;

public class BatchGroupLoadingEvent implements ISourceEvent<IEventListenerHandle> {
	
	private List<AddGroupEvent> m_groupList = new ArrayList<AddGroupEvent>();
	
	public BatchGroupLoadingEvent() {
		
	}

	public void dispatch(ISourceEventListener listener) {
		if (listener == null){
			return;
		}
		((IGroupEventListener)listener).onBatchGroupLoaded(this, null, null);

	}

	public void dispatch(ISourceEventListener listener, IEventListenerHandle handle, ISourceEventCallback callback) {
		if (listener == null){
			return;
		}
		((IGroupEventListener)listener).onBatchGroupLoaded(this, handle, callback);

	}

	public boolean isAppropriateListener(ISourceEventListener listener) {
		return listener instanceof IGroupEventListener;
	}
	
	public void addGroupEvent(AddGroupEvent event) {
		m_groupList.add(event);
		
	}
	
	public List<AddGroupEvent> getAllGroups() {
		return m_groupList;
	}
	
	public boolean shouldLock() { return false; }

}
