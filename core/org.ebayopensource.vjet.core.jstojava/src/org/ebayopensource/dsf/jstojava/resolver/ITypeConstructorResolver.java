/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.resolver;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.token.IExpr;

public interface ITypeConstructorResolver {

	// IJstType resolve(List<IExpr> args);
	/**
	 * Resolves the given expressions from {@link ITypeConstructContext} to
	 * {@link IJstType} and adds it to the resolved JST types list in
	 * {@link ITypeConstructContext}.
	 * 
	 * @param constrCtx
	 *            reference to {@link ITypeConstructContext} with input
	 *            expressions
	 */
	void resolve(ITypeConstructContext constrCtx);

	/**
	 * The groupId for which this resolver will create the types
	 * 
	 * @return
	 */
	String[] getGroupIds();

	/**
	 * The type of expression supported by this resolver.
	 * 
	 * @return
	 */
	Class<? extends IExpr> getExprClass();
}
