/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstProxyType;
import org.ebayopensource.dsf.ts.graph.IDependencyCollector;

public class JstTypeDependencyCollector implements IDependencyCollector<IJstType>{

	public Map<String,IJstType> getDependency(final IJstType jstType){
		
		if (jstType == null){
			return Collections.emptyMap();
		}
		
		Map<String,IJstType> map = new LinkedHashMap<String,IJstType>();
		if (jstType.getName() != null){
			addToDependency(jstType.getImports(), map);
			addToDependency(jstType.getExpects(), map);
			addToDependency(jstType.getExtends(), map);
			addToDependency(jstType.getInactiveImports(), map);
			addToDependency(jstType.getSatisfies(), map);
			//added by huzhou@ebay.com to support fully qualified name reference
			addToDependency(jstType.getFullyQualifiedImports(), map);
			addToMixDependency(jstType.getMixinsRef(), map);
		}
		
		return map;
	}
	public Map<String,IJstTypeReference> getDependencyRefs(final IJstType jstType){
		
		if (jstType == null){
			return Collections.emptyMap();
		}
		
		Map<String,IJstTypeReference> map = new LinkedHashMap<String,IJstTypeReference>();
		if (jstType.getName() != null){
			addToDependencyRef(jstType.getImportsRef(), map);
			addToDependencyRef(jstType.getExpectsRef(), map);
			addToDependencyRef(jstType.getExtendsRef(), map);
			addToDependencyRef(jstType.getInactiveImportsRef(), map);
			addToDependencyRef(jstType.getFullyQualifiedImportsRef(), map);
			addToDependencyRef(jstType.getSatisfiesRef(), map);
			addToDependencyRef(jstType.getMixinsRef(), map);
		}
		
		return map;
	}
	
	private void addToDependencyRef(List<? extends IJstTypeReference> dependencies, Map<String, IJstTypeReference> map) {
		if (dependencies != null) {
			for (IJstTypeReference t: dependencies) {
				IJstTypeReference dependency = t;
				map.put(dependency.getReferencedType().getName(), dependency);
			}
		}		
	}
	
	
	private void addToDependency(List<? extends IJstType> dependencies, Map<String, IJstType> map) {
		if (dependencies != null) {
			for (IJstType t: dependencies) {
				IJstType dependency = t;
				
				if (t instanceof JstProxyType) {
					dependency = t.getOwnerType();
				}
				map.put(dependency.getName(), dependency);
			}
		}		
	}
	
	private void addToMixDependency(List<? extends IJstTypeReference> dependencies, Map<String, IJstType> map) {
		if (dependencies != null) {
			for (IJstTypeReference ref: dependencies) {
				IJstType type = ref.getReferencedType();
				map.put(type.getName(), type);
			}
		}		
	}
}
