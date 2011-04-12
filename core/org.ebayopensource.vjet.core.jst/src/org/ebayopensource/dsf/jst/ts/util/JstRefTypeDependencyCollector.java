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

public class JstRefTypeDependencyCollector{

	public static Map<String,IJstTypeReference> getDependency(final IJstType jstType){
		
		if (jstType == null){
			return Collections.emptyMap();
		}
		
		Map<String,IJstTypeReference> map = new LinkedHashMap<String,IJstTypeReference>();
		if (jstType.getName() != null){
			addToDependency(jstType.getImportsRef(), map);
			addToDependency(jstType.getExtendRef(), map);
			addToDependency(jstType.getExtendsRef(), map);
			addToDependency(jstType.getSatisfiesRef(), map);
			addToDependency(jstType.getExpectsRef(), map);
			addToDependency(jstType.getInactiveImportsRef(), map);
			addToDependency(jstType.getMixinsRef(), map);
		
		}
		
		return map;
	}
	
	private static void addToDependency(IJstTypeReference dependency, Map<String, IJstTypeReference> map) {
		if (dependency != null) {
				map.put(dependency.getRootType().getName(), dependency);
		}		
	}
	private static void addToDependency(List<? extends IJstTypeReference> dependencies, Map<String, IJstTypeReference> map) {
		if (dependencies != null) {
			for (IJstTypeReference t: dependencies) {
				IJstTypeReference dependency = t;
				map.put(dependency.getReferencedType().getName(), dependency);
			}
		}		
	}
	
//	private void addToMixDependency(List<? extends IJstTypeReference> dependencies, Map<String, IJstType> map) {
//		if (dependencies != null) {
//			for (IJstTypeReference ref: dependencies) {
//				IJstType type = ref.getReferencedType();
//				map.put(type.getName(), type);
//			}
//		}		
//	}
}
