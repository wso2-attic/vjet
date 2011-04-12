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

import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;

public class RemoveGroupDependencyEvent extends GroupEvent {

	private List<RemoveGroupEvent> m_dependencyList; // list of dependency group names to be removed
	
	/**
	 * Constructor
	 * @param projName - project who will remove the dependency list
	 * @param dependencyList - list of dependencies including project name and library full path
	 */
	public RemoveGroupDependencyEvent(String projName, List<RemoveGroupEvent> dependencyList) {
		super(projName);
		m_dependencyList = dependencyList;	
	}
	
	/**
	 * Constructor
	 * @param projName - project who will remove the dependency
	 * @param dependency - project dependency group name to be removed 
	 */
	public RemoveGroupDependencyEvent(String projName, RemoveGroupEvent dependency) {
		super(projName);
		m_dependencyList = new ArrayList<RemoveGroupEvent>();
		
		if (dependency != null) {
			m_dependencyList.add(dependency);
		}
	}
	
	public void dispatch(ISourceEventListener listener) {
		if (listener == null){
			return;
		}

		((IGroupEventListener)listener).onGroupRemoveDependency(this, null, null);

	}

	public void dispatch(ISourceEventListener listener,
			IEventListenerHandle handle, ISourceEventCallback callback) {
		((IGroupEventListener)listener).onGroupRemoveDependency(this, handle, callback);

	}
	
	/**
	 * getDependencyList - get the dependency list to be removed from the project
	 * @return List<RemoveGroupEvent> - list of dependencies
	 */
	public List<RemoveGroupEvent> getDependencyList() {
		return m_dependencyList;
	}

	public boolean shouldLock() {
		return false;
	}

}
