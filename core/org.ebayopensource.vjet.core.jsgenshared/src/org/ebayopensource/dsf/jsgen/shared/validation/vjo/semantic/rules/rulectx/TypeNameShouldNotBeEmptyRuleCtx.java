/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx;

import org.ebayopensource.dsf.jst.IJstNode;

public class TypeNameShouldNotBeEmptyRuleCtx extends
		BaseVjoSemanticRuleCtx {

	private String m_classSimpleName;
	
	public TypeNameShouldNotBeEmptyRuleCtx(final String simpleName){
		super(null, "", new String[0]);
		m_classSimpleName = simpleName;
	}
	
	public TypeNameShouldNotBeEmptyRuleCtx(final IJstNode node, final String groupId, final String[] arguments, final String simpleName){
		super(node, groupId, arguments);
		m_classSimpleName = simpleName;
	}
	
	public String getClassSimpleName(){
		return m_classSimpleName;
	}
}
