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

public class PrivilegedProcessorAdapter implements IPrivilegedProcessor {
	
	/**
	 * @see IPrivilegedProcessor#processIdentifier(Name, BaseJstNode, CustomType, CustomField)
	 */
	public JstIdentifier processIdentifier(
			final Name name, 
			final BaseJstNode jstNode,
			final CustomType clientType,
			final CustomField clientField){
		return null;
	}
	
	/**
	 * @see IPrivilegedProcessor#processMtdInvocation(ASTNode, JstIdentifier, IExpr, List, boolean, BaseJstNode, CustomType, CustomMethod)
	 */
	public IExpr processMtdInvocation(
			final ASTNode astNode,
			final JstIdentifier identifier, 
			final IExpr optionalExpr, 
			final List<IExpr> jstArgs, 
			final boolean isSuper,
			final BaseJstNode jstNode,
			final CustomType clientType,
			final CustomMethod clientMethod){
		return null;
	}
	
	/**
	 * @see IPrivilegedProcessor#processInstanceCreation(ClassInstanceCreation, BaseJstNode, CustomType)
	 */
	public IExpr processInstanceCreation(
			final ClassInstanceCreation cic,
			final BaseJstNode jstNode,
			final List<IExpr> argExprList,
			final CustomType clientType) {
		return null;
	}
}
