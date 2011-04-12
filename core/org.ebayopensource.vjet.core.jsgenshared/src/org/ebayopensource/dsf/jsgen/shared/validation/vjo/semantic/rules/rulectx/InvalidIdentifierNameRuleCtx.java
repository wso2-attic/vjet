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

public class InvalidIdentifierNameRuleCtx extends
		BaseVjoSemanticRuleCtx {

	private String m_idName;
	
	public InvalidIdentifierNameRuleCtx(final String idName){
		super(null, "", new String[0]);
		m_idName = idName;
	}
	
	public InvalidIdentifierNameRuleCtx(final IJstNode node, final String groupId, final String[] arguments, final String idName){
		super(node, groupId, arguments);
		m_idName = idName;
	}
	
	public String getIdentifierName(){
		return m_idName;
	}
}
