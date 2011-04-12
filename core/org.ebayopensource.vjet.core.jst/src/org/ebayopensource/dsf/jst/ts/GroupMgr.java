/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.dsf.ts.group.Group;
import org.ebayopensource.dsf.ts.group.Library;
import org.ebayopensource.dsf.ts.group.Project;

class GroupMgr {
	
	private JstTypeSpaceMgr m_tsMgr;
	
	//
	// Constructor
	//
	GroupMgr(JstTypeSpaceMgr tsMgr){
		assert tsMgr != null : "tsMgr cannot be null";
		m_tsMgr = tsMgr;
	}
	
	public Group<IJstType> addGroup(final String groupName, final String groupPath){
		
		return addGroup(groupName, groupPath, null, null);
	}
	
	public Group<IJstType> addGroup(final String groupName, final String groupPath, 
			final String srcPath, final List<String> classPaths){
		
		if (m_tsMgr.getTypeSpaceImpl().getGroup(groupName) != null){
			throw new RuntimeException("Group already exists:" + groupName);
		}
		
		boolean isLibrary = false;
		
		if (groupPath != null) {
			String path = groupPath.toLowerCase();
			if (path.endsWith(".zip") || path.endsWith(".jar")) {
				isLibrary = true;
			}
		}
		
		Group<IJstType> group = null;
		
		if (isLibrary) {
			group = new Library(groupName, groupPath, m_tsMgr.getTypeDependencyMgr().getTypeDependencyCollector());
		}
		else {
			group = new Project<IJstType>(groupName, groupPath, srcPath, classPaths,
					m_tsMgr.getTypeDependencyMgr().getTypeDependencyCollector());
		}
		
		m_tsMgr.getTypeSpaceImpl().addGroup(group);
		
		// Build the group dependencies only after adding the group to type space		
		if (group instanceof Project && classPaths != null) {
			((Project)group).buildGroupDependency();
		}
		return group;
		
	}
	
	public void addGroups(final List<AddGroupEvent> groups) {
		
		for (AddGroupEvent event : groups) {
			addGroup(event.getGroupName(), event.getGroupPath(), event.getSourcePath(), event.getClassPath());
		}
		
	}
	
	public void removeGroup(final String groupName){
		if (groupName == null){
			return;
		}
		m_tsMgr.getTypeSpaceImpl().removeGroup(groupName);
		// TODO synup dependents
	}
}
