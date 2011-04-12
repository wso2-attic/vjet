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

public class AddGroupDependencyEvent extends GroupEvent {
	
	private List<AddGroupEvent> m_dependencyList; // list of dependency groups, e.g. project name, lib full path

	/**
	 * Constructor
	 * @param projName - project who will be added the dependency
	 * @param dependencyList - list of dependencies including project name and library full path
	 */
	public AddGroupDependencyEvent(String projName, List<AddGroupEvent> dependencyList) {
		super(projName);
		m_dependencyList = dependencyList;		
	}
	
	/**
	 * Constructor
	 * @param projName - project who will be added the dependency
	 * @param dependency - project name or library full path
	 */
	public AddGroupDependencyEvent(String projName, AddGroupEvent dependency) {
		super(projName);
		m_dependencyList = new ArrayList<AddGroupEvent>();
		if (dependency != null) {
			m_dependencyList.add(dependency);
		}
	}
	
	/**
	 * addDependency - add dependency to the project's dependency list
	 * @param dependency - project name or library full path
	 * @return
	 */
	public boolean addDependency(AddGroupEvent dependency) {
		if (m_dependencyList == null) {
			m_dependencyList = new ArrayList<AddGroupEvent>();
		}
		
		if (dependency != null) {
			return m_dependencyList.add(dependency);
		}
		
		return false;		
	}
	
	/**
	 * getDependencyList - get the dependency list of the project
	 * @return List<AddGroupEvent> - list of dependencies
	 */
	public List<AddGroupEvent> getDependencyList() {
		return m_dependencyList;
	}
	
	//
	// Satisfy IJstEvent
	//
	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener)
	 */	
	public void dispatch(ISourceEventListener listener) {
		if (listener == null){
			return;
		}

		((IGroupEventListener)listener).onGroupAddDependency(this, null, null);
	}

	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener,IEventListenerHandle,ISourceEventCallback)
	 */
	public void dispatch(ISourceEventListener listener,
			IEventListenerHandle handle, ISourceEventCallback callback) {
		if (listener == null){
			return;
		}
		
		((IGroupEventListener)listener).onGroupAddDependency(this, handle, callback);

	}

	public boolean shouldLock() {
		return false;
	}

}
