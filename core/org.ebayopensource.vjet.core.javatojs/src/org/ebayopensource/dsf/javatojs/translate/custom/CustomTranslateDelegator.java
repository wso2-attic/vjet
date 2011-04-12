/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;

import org.ebayopensource.dsf.javatojs.translate.BaseTranslator;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * This is the entry-point for custom translation.
 * The execution is delegated to individual custom translators based on the order they are added.
 * It will stop the loop as soon as one custom translator returns a not-null value.
 */
public class CustomTranslateDelegator extends BaseTranslator implements ICustomTranslator {

	// 
	// Satisfy ICustomTranslator
	//
	/**
	 * @see ICustomTranslator#initialize(JstType)
	 */
	public void initialize(final JstType jstType){
		List<ICustomTranslator> cts = getCtx().getConfig().getCustomTranslators();
		for (ICustomTranslator t: cts){
			t.initialize(jstType);
		}
	}
	
	/**
	 * @see ICustomTranslator#processType(String, ASTNode, BaseJstNode)
	 */
	public IJstType processType(
			final String javaTypeName, 
			final ASTNode astNode,
			final BaseJstNode jstNode){
		
		List<ICustomTranslator> cts = getCtx().getConfig().getCustomTranslators();
		IJstType toType;
		for (ICustomTranslator t: cts){
			toType = t.processType(javaTypeName, astNode, jstNode);
			if (toType != null){
				return toType;
			}
		}
		return null;
	}
	
	/**
	 * @see ICustomTranslator#processIdentifier(Name, boolean, boolean, IExpr, JstIdentifier, BaseJstNode)
	 */
	public JstIdentifier processIdentifier(
			final Name name, 
			boolean hasSuper, 
			boolean hasThis, 
			final IExpr jstQualifier, 
			final JstIdentifier jstIdentifier, 
			final BaseJstNode jstNode){
		
		JstIdentifier identifier = null;
		List<ICustomTranslator> cts = getCtx().getConfig().getCustomTranslators();
		for (ICustomTranslator t: cts){
			identifier = t.processIdentifier(name, hasSuper, hasThis, jstQualifier, jstIdentifier, jstNode);
			if (identifier != null){
				return identifier;
			}
		}
		return null;
	}
	
	/**
	 * @see ICustomTranslator#processInstanceCreation(ClassInstanceCreation, IExpr, IJstType, List<IExpr>, BaseJstNode)
	 */
	public IExpr processInstanceCreation(
			final ClassInstanceCreation cic, 
			final IExpr jstQualifier, 
			final IJstType jstType,
			final List<IExpr> jstArgs,
			final BaseJstNode jstNode){
		
		IExpr rtnExpr = null;
		List<ICustomTranslator> cts = getCtx().getConfig().getCustomTranslators();
		for (ICustomTranslator t: cts){
			rtnExpr = t.processInstanceCreation(cic, jstQualifier, jstType, jstArgs, jstNode);
			if (rtnExpr != null){
				return rtnExpr;
			}
		}
		return null;
	}
	
	/**
	 * @see ICustomTranslator#processMtdInvocation(ASTNode, boolean, IExpr, JstIdentifier, List<IExpr>, BaseJstNode)
	 */
	public IExpr processMtdInvocation(
			final ASTNode astNode, 
			final boolean hasSuper, 
			final IExpr jstQualifier, 
			final JstIdentifier jstIdentifier,
			final List<IExpr> jstArgs, 
			final BaseJstNode jstNode){
		
		IExpr expr = null;
		List<ICustomTranslator> cts = getCtx().getConfig().getCustomTranslators();
		for (ICustomTranslator t: cts){
			expr = t.processMtdInvocation(astNode, hasSuper, jstQualifier, jstIdentifier, jstArgs, jstNode);
			if (expr != null){
				return expr;
			}
		}
		return null;
	}

	/**
	 * @see ICustomTranslator#processMethodBody(MethodDeclaration, JstMethod)
	 */
	public boolean processMethodBody(
			final MethodDeclaration astMtd, 
			final JstMethod jstMtd) {
		
		List<ICustomTranslator> cts = getCtx().getConfig().getCustomTranslators();
		for (ICustomTranslator t: cts){
			if (t.processMethodBody(astMtd, jstMtd)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @see ICustomTranslator#processTypeBody(List, JstType)
	 */
	public boolean processTypeBody(
			final List<?> bodyDeclaration, 
			final JstType jstType) {
		
		List<ICustomTranslator> cts = getCtx().getConfig().getCustomTranslators();
		for (ICustomTranslator t: cts){
			if (t.processTypeBody(bodyDeclaration, jstType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see ICustomTranslator#getStaticTypeQualifier(IJstType, BaseJstNode)
	 */
	public JstIdentifier getStaticTypeQualifier(
			final IJstType jstType, 
			final BaseJstNode jstNode) {
		
		JstIdentifier identifier = null;
		List<ICustomTranslator> cts = getCtx().getConfig().getCustomTranslators();
		for (ICustomTranslator t: cts){
			identifier = t.getStaticTypeQualifier(jstType, jstNode);
			if (identifier != null){
				return identifier;
			}
		}
		return null;
	}		
}
