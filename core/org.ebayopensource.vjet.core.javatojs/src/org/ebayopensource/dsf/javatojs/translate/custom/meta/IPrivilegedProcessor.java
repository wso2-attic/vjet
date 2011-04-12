/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.meta;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Name;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;

public interface IPrivilegedProcessor {
	
	/**
	 * Process a name and return a corresponding JstIdentifier
	 * @param name Name
	 * @param jstNode BaseJstNode
	 * @param clientType CustomType
	 * @param clientField CustomField
	 * @return JstIdentifier null if no custom translation needed.
	 */
	public JstIdentifier processIdentifier(
			final Name name, 
			final BaseJstNode jstNode,
			final CustomType clientType,
			final CustomField clientField);
	
	/**
	 * Process a method invocation
	 * @param astNode ASTNode
	 * @param identifier JstIdentifier
	 * @param optionalExpr IExpr
	 * @param args List
	 * @param isSuper boolean
	 * @param jstNode BaseJstNode
	 * @param clientType CustomType
	 * @param clientField CustomField
	 * @return IExpr null if no custom translation needed.
	 */
	public IExpr processMtdInvocation(
			final ASTNode astNode,
			final JstIdentifier identifier, 
			final IExpr optionalExpr, 
			final List<IExpr> jstArgs, 
			final boolean isSuper,
			final BaseJstNode jstNode,
			final CustomType clientType,
			final CustomMethod clientMethod);
	
	/**
	 * Process an instance creation
	 * @param cic ClassInstanceCreation
	 * @param jstNode BaseJstNode
	 * @param argExprList List<IExpr>
	 * @param clientType CustomType
	 */
	public IExpr processInstanceCreation(
			final ClassInstanceCreation cic,
			final BaseJstNode jstNode,
			final List<IExpr> argExprList,
			final CustomType clientType);
}
