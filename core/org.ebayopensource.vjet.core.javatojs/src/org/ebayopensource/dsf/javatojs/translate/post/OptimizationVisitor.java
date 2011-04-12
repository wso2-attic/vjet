/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.post;

import java.util.Set;

import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.traversal.JstVisitorAdapter;

public class OptimizationVisitor extends JstVisitorAdapter {

	//
	// Satisfy IJstVisitor
	//
	@Override
	public void postVisit(IJstNode node){
		
		if (!(node instanceof JstType)){
			return;
		}
		
		JstType jstType = (JstType)node;
		
		removeIndirectDependency(jstType);
	}
	
	//
	// Private
	//
	private void removeIndirectDependency(JstType jstType){
		for (String key : jstType.getImportsMap().keySet()) {
			IJstType itm = jstType.getImportsMap().get(key);
			if (!isInActiveImports(jstType, itm) 
					&& !(isInExtends(jstType, itm) || isInSatisfies(jstType, itm))
							) {
				//If the type is not directly used, not used as 'extends' or not used as 'satisfies'
				// then mark it inactive import
				jstType.addInactiveImport(key, itm);
				jstType.removeImport(itm);
			}
		}
	}

	private boolean isInActiveImports(JstType jstType, IJstType itm) {
		Set<IJstType> activeImports = TranslateCtx.ctx().getTranslateInfo(jstType).getActiveImports();
		if (activeImports.contains(itm)) {
			return true;
		}
		for (IJstType imp : activeImports) {
			if (imp!=null && imp.getName().equals(itm.getName())) {
				return true;
			}
		}
		return false;
	}
	
	
	private boolean isInExtends(JstType jstType, IJstType dependencyType) {
		for (IJstNode itm : jstType.getExtends()) {
			JstType type = null;
			if (itm instanceof JstType) {
				type = (JstType)itm;
			} else if (itm instanceof JstTypeReference) {
				type = (JstType)((JstTypeReference)itm).getReferencedType();
			} else if (itm instanceof JstTypeWithArgs) {
				type = (JstType)((JstTypeWithArgs)itm).getType();
			}
			if (type != null) {
				if (type.getOuterType() != null) {
					type = type.getOuterType();
				}
				if (type.equals(dependencyType)) {
					return true;
				}
			} 
		}
		//Check in inner types
		for (JstType itm : jstType.getEmbededTypes()) {
			if (isInExtends(itm, dependencyType)) {
				return true;
			}
		}
		return false;
	}

	private boolean isInSatisfies(JstType jstType, IJstType dependencyType) {
		for (IJstNode itm : jstType.getSatisfies()) {
			JstType type = null;
			if (itm instanceof JstType) {
				type = (JstType)itm;
			} else if (itm instanceof JstTypeReference) {
				type = (JstType)((JstTypeReference)itm).getReferencedType();
			} else if (itm instanceof JstTypeWithArgs) {
				type = (JstType)((JstTypeWithArgs)itm).getType();
			}
			if (type != null) {
				if (type.getOuterType() != null) {
					type = type.getOuterType();
				}
				if (type.equals(dependencyType)) {
					return true;
				}
			}
		}
		//Check in inner types
		for (JstType itm : jstType.getEmbededTypes()) {
			if (isInSatisfies(itm, dependencyType)) {
				return true;
			}
		}
		return false;
	}

}
