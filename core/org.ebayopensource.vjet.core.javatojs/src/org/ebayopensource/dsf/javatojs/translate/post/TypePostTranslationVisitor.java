/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.post;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.traversal.JstVisitorAdapter;

public class TypePostTranslationVisitor extends JstVisitorAdapter {

	public boolean visit(IJstNode node){
		
		if (!(node instanceof JstType)){
			return true;
		}

		TranslateCtx ctx = TranslateCtx.ctx();

		JstType jstType = (JstType)node;
		IJstType outer = jstType.getOuterType();
		if (outer == null){
			// Remove non-needed imports
			List<IJstType> removeList = new ArrayList<IJstType>();
			for (IJstType type: jstType.getImports()){
				if (type.getOuterType() != null
					|| ctx.isMappedToJS(type)
					|| ctx.isMappedToVJO(type)
					|| ctx.isJavaOnly(type)
					|| ctx.isExcluded(type)){
					removeList.add(type);
				}
			}
			
			for (IJstType type: removeList){
				jstType.removeImport(type);
			}
			
			// Remove JavaOnly extends
			removeList.clear();
			for (IJstType extend: jstType.getExtends()){
				if (ctx.isJavaOnly(extend) || ctx.isExcluded(extend)){
					removeList.add(extend);
				}
			}
			
			for (IJstType type: removeList){
				jstType.removeExtend(type);
			}
			
			// Remove JavaOnly satisfies
			removeList.clear();
			for (IJstType satisfies: jstType.getSatisfies()){
				if (ctx.isJavaOnly(satisfies)){
					removeList.add(satisfies);
				}
			}
			
			for (IJstType type: removeList){
				jstType.removeSatisfy(type);
			}
			
			// Type Rename
			String newName = ctx.getNewName(jstType);
			if (newName != null && !ctx.isMappedToJS(jstType) && !ctx.isMappedToVJO(jstType)){
				jstType.setSimpleName(TranslateHelper.getShortName(newName));
				String pkgName = TranslateHelper.getPkgName(newName);
				if (pkgName == null){
						jstType.setPackage(null); //TODO decide default behavior
				}
				else if (jstType.getPackage() != null && !pkgName.equals(jstType.getPackage().getName())){
					jstType.setPackage(new JstPackage(pkgName));	
				}
			}
		}
		else if (outer instanceof JstType){
			if (ctx.isExcluded(jstType) || ctx.isJavaOnly(jstType) || ctx.isMappedToJS(jstType) || ctx.isMappedToVJO(jstType)){
				((JstType)outer).removeInnerType(jstType.getSimpleName());
				jstType.setParent(null);
			}
		}

		return true;
	}
}