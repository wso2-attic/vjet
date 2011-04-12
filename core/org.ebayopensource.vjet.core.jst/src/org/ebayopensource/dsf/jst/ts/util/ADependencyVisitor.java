/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.group.Group;

abstract class ADependencyVisitor<T> implements IJstVisitor {
	
	protected TypeSpace m_ts;

	protected Map<T,List<IJstNode>> m_dependencies = new LinkedHashMap<T,List<IJstNode>>();
	
	protected List<IJstNode> get_dep_list(T key) {
		List<IJstNode> dependencies = m_dependencies.get(key);
		if (dependencies == null){
			dependencies = new ArrayList<IJstNode>();
			m_dependencies.put(key, dependencies);
		}
		return dependencies;
	}
	
	protected void add_if_absent(T key, IJstNode d) {
		List<IJstNode> dependencies = m_dependencies.get(key);
		if (dependencies == null){
			dependencies = new ArrayList<IJstNode>();
			m_dependencies.put(key, dependencies);
		}
		else if (dependencies.contains(d)){
			return;
		}
		dependencies.add(d);
	}
	
	public void setTypeSpace(final TypeSpace ts) {
		m_ts = ts;
	}
	
	protected void addImplicitDependency(IJstNode dependent, final String dependencyGrp, 
			final IJstType dependency) {
		
		String thisGroupName = null;
		if (dependent.getOwnerType().getPackage() != null)
			thisGroupName = dependent.getOwnerType().getPackage().getGroupName();
		
		if (thisGroupName != null) {
			Group<IJstType> group = m_ts.getGroup(thisGroupName);
			
			if (group != null) {
				group.getGraph().addImplicitDependency(dependent.getOwnerType().getName(), 
						dependent.getOwnerType(), dependencyGrp, 
						dependency.getName(), dependency);
			}
		}
	}

}
