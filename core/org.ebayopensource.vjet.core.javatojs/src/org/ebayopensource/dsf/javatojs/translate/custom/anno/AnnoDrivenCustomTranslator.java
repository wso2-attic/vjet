/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.anno;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;

import org.ebayopensource.dsf.javatojs.translate.BaseTranslator;
import org.ebayopensource.dsf.javatojs.translate.custom.ICustomTranslator;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.IPrivilegedProcessor;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;

public class AnnoDrivenCustomTranslator extends BaseTranslator implements ICustomTranslator {
	
	IAnnoCustomMetaProvider m_provider;
	
	//
	// Constructor
	//
	/**
	 * Constructor.
	 */
	public AnnoDrivenCustomTranslator(){
		this(AnnoMeta.getInstance());
	}
	
	/**
	 * Constructor.
	 * @param customTypeMetaProvider ICustomTypeMetaProvider required
	 */
	public AnnoDrivenCustomTranslator(IAnnoCustomMetaProvider annoProvider){
		m_provider = annoProvider;
	}

	//
	// Satisfy ICustomTranslator
	//
	/**
	 * @see ICustomTranslator#initialize(JstType)
	 */
	public void initialize(JstType jstType){
		
	}
	
	/**
	 * @see ICustomTranslator#processType(String, ASTNode, BaseJstNode)
	 */
	public IJstType processType(final String javaTypeName, final ASTNode astNode, final BaseJstNode jstNode){
		return null;
	}
	
	/**
	 * @see ICustomTranslator#processVarName(dName, boolean, BaseJstNode)
	 */
	public JstIdentifier processIdentifier(final Name astName, boolean hasSuper, boolean hasThis, 
			final IExpr qualifierExpr, final JstIdentifier identifier, final BaseJstNode jstNode){
		return null;
	}
	
	/**
	 * @see ICustomTranslator#processInstanceCreation(ClassInstanceCreation cic, BaseJstNode)
	 */
	public IExpr processInstanceCreation(
			final ClassInstanceCreation cic, 
			final IExpr expr, 
			final IJstType jstType,
			final List<IExpr> args,
			final BaseJstNode jstNode){
		return null;
	}
	
	/**
	 * @see ICustomTranslator#processMtdInvocation(ASTNode, JstIdentifier, IExpr, List, boolean, BaseJstNode)
	 */
	public IExpr processMtdInvocation(
			final ASTNode astNode,
			final boolean hasSuper,
			final IExpr jstQualifier, 
			final JstIdentifier jstIdentifier, 
			final List<IExpr> jstArgs, 
			final BaseJstNode jstNode){
		
		IJstType type = null;
		if (jstQualifier != null) {
			if (jstQualifier instanceof JstIdentifier) {
				type =((JstIdentifier)jstQualifier).getType();
			}
		}
		else {
			JstIdentifier qualifier = jstIdentifier.getQualifier();
			if (qualifier != null) {
				type = qualifier.getType();
			}
		}
		if (type!=null) {
			IJstMethod method = type.getMethod(jstIdentifier.getName());
			if (method!=null) {
				List<IJstAnnotation> annos = getAnnotations(method);
				for (IJstAnnotation anno : annos) {
					String name = anno.getName().getName();
					IPrivilegedProcessor processor = m_provider.getPrivilegedProcessor(name);
					if (processor!=null) { //TODO do we need custom method/type information
						return processor.processMtdInvocation(astNode,
								jstIdentifier, jstQualifier, jstArgs, hasSuper,
								jstNode, null, null);
					}
					
				}
			}
		}
		return null;
	}
	
	public boolean processMethodBody(MethodDeclaration astMtd, JstMethod jstMtd) {
		return false;
	}
	
	public boolean processTypeBody(List<?> bodyDeclaration, JstType jstType) {
		// Indicate we don't process this
		return false;
	}
	
	public boolean processMethodDecl(MethodDeclaration astMtd,JstType jstType) {
		return false;	
	}

	public JstIdentifier getStaticTypeQualifier(IJstType type, BaseJstNode jstNode) {
		return null;
	}
	
	/**
	 * get annotation associated with the method, if the method is a dispatcher method,
	 * get annotation from first overloaded method (TODO, to select right one base on args)
	 */
	private static List<IJstAnnotation> getAnnotations(IJstMethod method) {
		List<IJstAnnotation> annos = method.getAnnotations();
		if (annos == null || annos.isEmpty()) {
			if (method.isDispatcher()) {
				annos = method.getOverloaded().get(0).getAnnotations();
			}
		}
		return annos;
	}
}

