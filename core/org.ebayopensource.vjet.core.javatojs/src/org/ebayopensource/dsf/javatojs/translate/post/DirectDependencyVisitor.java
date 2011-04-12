/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.post;

import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.JstVisitorAdapter;

/**
 * Please note that this visitor is NOT thread-safe.
 */
public class DirectDependencyVisitor extends JstVisitorAdapter {
	
	private int m_inStmt = 0;

	//
	// Satisfy IJstVisitor
	//
	@Override
	public boolean visit(IJstNode node){
		
		if (m_inStmt < 1 || node == null){
			return true;
		}
		
		IJstType rootType = node.getRootType();
		if (node instanceof ObjCreationExpr){
			addDirectDependency(((ObjCreationExpr)node).getResultType(), rootType);
			return true;
		}
		
		if ((node instanceof JstIdentifier)){
			JstIdentifier identifier = (JstIdentifier)node;
//			if (identifier.getQualifier() == null) {
//				return true;
//			}
			if (TranslateHelper.Type.getLocalVarType(identifier.getName(), node) != null){
				return true;
			} 
			IJstNode binding = identifier.getJstBinding();
			if (binding == null || !(binding instanceof JstType)){
				return true;
			}
			
			JstType jstType = (JstType)binding;
			String name = jstType.getSimpleName();
			if (name == null /*|| !name.equals(identifier.getName())*/){
				return true;
			}
			
			addDirectDependency(jstType, rootType);
			
			return true;
		}

		if (node instanceof JstProperty){
			JstProperty prop = (JstProperty)node;
			if (prop.getInitializer() != null && prop.getInitializer() instanceof AssignExpr) {
				AssignExpr aExpr = (AssignExpr)prop.getInitializer();
				if (aExpr.getExpr() instanceof ObjCreationExpr){
					addDirectDependency(((ObjCreationExpr)aExpr.getExpr()).getResultType(), rootType);
					return true;
				}
			}
			
			return true;
		}

		if (node instanceof JstType
				|| node instanceof JstTypeWithArgs
				|| node instanceof JstTypeReference){
			JstType type = null;
			if (node instanceof JstTypeWithArgs) {
				type = (JstType)((JstTypeWithArgs)node).getType();
			} else if (node instanceof JstTypeReference) {
				IJstType tmp = ((JstTypeReference)node).getReferencedType();
				if (tmp instanceof JstTypeWithArgs) {
					type = (JstType)((JstTypeWithArgs)tmp).getType();
				} else if (tmp instanceof JstType) {
					type = (JstType)tmp;
				}
			} else {
				type = (JstType)node;
			}
			if (isUsedInDispatch(type, rootType)) {
				addDirectDependency(type, rootType);
			}
			return true;
		}

		return true;
	}
	
	private boolean isUsedInDispatch(JstType type, IJstType currentType) {
		//TranslateInfo tInfo = TranslateCtx.ctx().getTranslateInfo(type);
		if (type == null || currentType.equals(type)) return false;
		for (IJstMethod mtd : currentType.getMethods()) {
			for (IJstMethod ovMtd : mtd.getOverloaded()) {
				if (ovMtd.getArgs().size() == 0) continue;
				for (JstArg arg : ovMtd.getArgs()) {
					if (isInImports(arg.getType(), currentType)) {
						return true;
					}
				}
			}
		}
		if (currentType.getConstructor() != null) {
			for (IJstMethod ovMtd : currentType.getConstructor().getOverloaded()) {
				if (ovMtd.getArgs().size() == 0) continue;
				for (JstArg arg : ovMtd.getArgs()) {
					if (isInImports(arg.getType(), currentType)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isInImports(IJstType type, IJstType currentType) {
		for (IJstType itm: currentType.getImports()) {
			if (type.getName().equals(itm.getName())) {
				return true;
			}
		}
		return false;
	}

	public void preVisit(IJstNode node){
		if (isProcess(node)){
			m_inStmt++;
		}
	}
	
	public void postVisit(IJstNode node){
		if (isProcess(node)){
			m_inStmt--;
		}
	}
	
	private boolean isProcess(IJstNode node) {
		return (node instanceof IStmt 
				|| node instanceof IJstProperty 
				|| node instanceof JstType
				|| node instanceof JstTypeWithArgs
				/*|| node instanceof JstTypeReference*/);
	}
	
	//
	// Private
	//
	private void addDirectDependency(IJstType jstType, IJstType targetType){
		
		if (jstType == null)
			return;
		IJstType type = jstType;
		while (type.getOuterType() != null) {
			type = type.getOuterType();
		}
		if (type instanceof JstTypeWithArgs) {
			type = ((JstTypeWithArgs)type).getType();
		}
		TranslateCtx.ctx().getTranslateInfo((JstType)targetType).addActiveImport(type);
	}
}
