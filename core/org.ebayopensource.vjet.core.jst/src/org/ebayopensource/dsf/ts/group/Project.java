/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.group;

import java.util.List;

import org.ebayopensource.dsf.ts.graph.IDependencyCollector;

public class Project<T> extends Group<T> {
	
	private String m_path;
	
	// classpath for this project, list of projects, jars, libs etc.
	private List<String> m_classPath;
	
	private String m_srcPath;
	
	//
	// Constructor
	//
	public Project(final String name, final IDependencyCollector<T> builder){
		this(name, null, null, null, builder);
	}
	
	public Project(final String name, final String path, final IDependencyCollector<T> builder){
		this(name, path, null, null, builder);
	}
	
	public Project(final String name, final String path, final String srcPath, 
			final List<String> classPaths, 
			final IDependencyCollector<T> builder) {
		super(name, builder);
		m_path = path;
		m_srcPath = srcPath;
		m_classPath = classPaths;
	}
	
	//
	// API
	//
	public String getPath(){
		return m_path;
	}
	
	public String getSrcPath() {
		return m_srcPath;
	}
	
	// build all dependency groups based on the classpath
	//
	public void buildGroupDependency() {
		
		GroupDependencyNode thisProject = getTypeSpace().getGroupDependencyNode(getName());
			
		if (m_classPath != null && m_classPath.size() > 0) {
			
			for (String path : m_classPath) {
				
				addGroupDependency(thisProject, path);
			}			
		}
	}
	
	public void addGroupDependency(String dependency) {
		
		GroupDependencyNode thisProject = getTypeSpace().getGroupDependencyNode(getName());
		
		addGroupDependency(thisProject, dependency);
	}
	
	public void removeGroupDependency(String dependency) {
		
		GroupDependencyNode thisProject = getTypeSpace().getGroupDependencyNode(getName());
		
		GroupDependencyNode dependencyGroup = getTypeSpace().getGroupDependencyNode(dependency);
	
		removeDependency(thisProject, dependencyGroup);
	}
	
	public boolean isReadOnly() {
		return false;
	}
	
	private void addGroupDependency(GroupDependencyNode thisProj, String dependency) {
		
		//	get the proj or lib that this project depends on
		GroupDependencyNode dependencyNode = getTypeSpace().getGroupDependencyNode(dependency);
		
		if (thisProj == null){
			throw new RuntimeException("target project is null");
		}
		
		if (dependencyNode == null){
			throw new RuntimeException("dependency group is null");
		}
	
		thisProj.addDependency(dependencyNode);
		
		dependencyNode.addDependent(thisProj);
	}
	
	private void removeDependency(GroupDependencyNode thisProj, GroupDependencyNode dependency) {
		
		if (thisProj == null){
			throw new RuntimeException("target project is null");
		}
		
		if (dependency == null){
			throw new RuntimeException("dependency group is null");
		}
		
		thisProj.removeDependency(dependency);
		
		dependency.removeDependent(thisProj);
	}
}
