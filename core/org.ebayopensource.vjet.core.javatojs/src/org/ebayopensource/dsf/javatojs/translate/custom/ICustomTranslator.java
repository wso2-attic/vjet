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

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * Contract for all custom translators.
 */
public interface ICustomTranslator {
	
	/**
	 * Perform meta initialization for given jstType.
	 * It'll be invoked when the type is loaded as library type,
	 * or when the type is translated, or when the type is referenced
	 * during custom translation.
	 * @param jstType JstType
	 */
	void initialize(JstType jstType);
	
	/**
	 * Answer jstType for given type name based on custom type meta
	 * @param javaTypeName String name of the java type
	 * @param astNode ASTNode the referencing astNode 
	 * @param jstNode BaseJstNode the corresponding jstNode
	 * @return IJstType null if no custom translation needed.
	 */
	IJstType processType(String javaTypeName, ASTNode astNode, BaseJstNode jstNode);
	
	/**
	 * Answer jstIdentifier for given AST name based on custom field meta.
	 * @param name Name
	 * @param hasSuper boolean 
	 * @param hasThis boolean 
	 * @param jstQualifier IExpr
	 * @param jstIdentifier JstIdentifier
	 * @param jstNode BaseJstNode
	 * @return JstIdentifier null if no custom translation needed.
	 */
	JstIdentifier processIdentifier(
			Name name, 
			boolean hasSuper, 
			boolean hasThis, 
			IExpr jstQualifier, 
			JstIdentifier jstIdentifier, 
			BaseJstNode jstNode);
	
	/**
	 * Process a object instantiation
	 * @param cic ClassInstanceCreation
	 * @param jstQualifier IExpr
	 * @param jstType IJstType
	 * @param jstArgs List<IExpr>
	 * @param jstNode BaseJstNode
	 * @return IExpr null if no custom translation needed.
	 */
	IExpr processInstanceCreation(
			ClassInstanceCreation cic, 
			IExpr jstQualifier, 
			IJstType jstType,
			List<IExpr> jstArgs,
			BaseJstNode jstNode);
	
	/**
	 * Process a method invocation
	 * @param astNode ASTNode
	 * @param hasSuper boolean
	 * @param jstQualifier IExpr
	 * @param jstIdentifier JstIdentifier
	 * @param jstArgs List<IExpr>
	 * @param jstNode BaseJstNode
	 * @return IExpr null if no custom translation needed.
	 */
	IExpr processMtdInvocation(
			ASTNode astNode, 
			boolean hasSuper,
			IExpr jstQualifier, 
			JstIdentifier jstIdentifier, 
			List<IExpr> jstArgs, 
			BaseJstNode jstNode);

	/**
	 * Process method body
	 * @param astMtd MethodDeclaration
	 * @param jstMtd JstMethod
	 * @return boolean true if method body was processed. False, Otherwise.
	 */
	boolean processMethodBody(
			MethodDeclaration astMtd, 
			JstMethod jstMtd);
	
	/**
	 * Process BodyDeclarations
	 * @param bodyDeclaration List<?> list of BodyDeclaration elements
	 * @param jstType JstType to update
	 * @return boolean true if BodyDeclaration was processed. False, Otherwise.
	 */
	boolean processTypeBody(
			List<?> bodyDeclaration, 
			JstType jstType);
	
	/**
	 * Return a JstIdentifier as static type qualifier for a given type
	 * @param jstType IJstType
	 * @param jstNode BaseJstNode
	 * @return null if there is no customization, EMPTY_QUALIFIER or non-null value for customized translation
	 */
	JstIdentifier getStaticTypeQualifier(
			IJstType jstType, 
			BaseJstNode jstNode);
}
