/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

public interface IJstRefResolver {
	
	ResolutionResult resolve(IJstType type);
	ResolutionResult resolve(IJstProperty property);
	ResolutionResult resolve(IJstMethod method);
	/**
	 * Resolve any JstNode (node) in a JstType (type)
	 * for example resovle MtdInvocationExpr:
	 * vjo.ctype().protos({}).endType();
	 * @param type
	 * @param node
	 */
	ResolutionResult resolve(IJstType type, IJstNode node);
	
	ResolutionResult resolve(String groupName, IJstType type);
	ResolutionResult resolve(String groupName, IJstProperty property);
	ResolutionResult resolve(String groupName, IJstMethod method);
}
