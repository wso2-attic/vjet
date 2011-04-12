/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.group;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class GroupDependencyNode<T> {
	
	// groups in the classpath that this group depends on
	private Map<String, GroupDependencyNode<T>> m_dependencyGroups = new LinkedHashMap<String, GroupDependencyNode<T>>();
	
	// groups depending on this group (dependents of this group)
	private Map<String, GroupDependencyNode<T>> m_dependentGroups = new LinkedHashMap<String, GroupDependencyNode<T>>();;
	
	private Group<T> m_group;
	
	private String m_groupName;
	
	// Constructor, a wrapper until actual group is created and set
	//
	public GroupDependencyNode(final String name) {
		m_groupName = name;
		m_group = null;		
	}
	
	public void setGroup(Group<T> group) {
		m_group = group;
	}
	
	public Group<T> getGroup() {
		return m_group;
	}
	
	public String getGroupName() {
		return m_groupName;
	}
	
	public synchronized void addDependency(GroupDependencyNode<T> node) {
		m_dependencyGroups.put(node.getGroupName(), node);
	}

	public synchronized void addDependent(GroupDependencyNode<T> node) {
		m_dependentGroups.put(node.getGroupName(), node);
	}
	
	public synchronized Map<String,GroupDependencyNode<T>> getDependencies(){
		if (m_dependencyGroups == null || m_dependencyGroups.isEmpty()){
			return Collections.emptyMap();
		}
		else {
			return Collections.unmodifiableMap(m_dependencyGroups);
		}
	}
	
	public synchronized Map<String,GroupDependencyNode<T>> getDependents(){
		if (m_dependentGroups == null || m_dependentGroups.isEmpty()){
			return Collections.emptyMap();
		}
		else {
			return Collections.unmodifiableMap(m_dependentGroups);
		}
	}
	
	public synchronized void removeDependency(GroupDependencyNode<T> node){
		assert node != null : "node cannot be null";
		if (m_dependencyGroups != null){
			m_dependencyGroups.remove(node.getGroupName());
		}
	}
	
	public synchronized void removeDependent(GroupDependencyNode<T> node){
		assert node != null : "node cannot be null";
		if (m_dependentGroups != null){
			m_dependentGroups.remove(node.getGroupName());
		}
	}
}
