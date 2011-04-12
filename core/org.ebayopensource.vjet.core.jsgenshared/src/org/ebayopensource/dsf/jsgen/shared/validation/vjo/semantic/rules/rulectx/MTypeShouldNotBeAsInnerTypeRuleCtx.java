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
import org.ebayopensource.dsf.jst.IJstType;

public class MTypeShouldNotBeAsInnerTypeRuleCtx extends BaseVjoSemanticRuleCtx {

	private IJstType m_mtype;
	
	public MTypeShouldNotBeAsInnerTypeRuleCtx(final IJstNode node, final String groupId, final String[] arguments, final IJstType mtype){
		super(node, groupId, arguments);

		m_mtype = mtype;
	}
	
	public IJstType getMType(){
		return m_mtype;
	}
}
