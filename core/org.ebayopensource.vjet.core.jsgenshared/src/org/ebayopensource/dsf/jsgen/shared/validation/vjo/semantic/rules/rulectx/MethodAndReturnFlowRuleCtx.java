/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.VjoMethodControlFlowTable;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;

public class MethodAndReturnFlowRuleCtx extends BaseVjoSemanticRuleCtx{

	private IJstMethod m_method;
	private VjoMethodControlFlowTable m_flowTable;
	
	public MethodAndReturnFlowRuleCtx(final IJstNode node, final String groupId, final String[] arguments, final IJstMethod mtd, final VjoMethodControlFlowTable flowTable){
		super(node, groupId, arguments);

		m_method = mtd;
		m_flowTable = flowTable;
	}
	
	public IJstMethod getMethod(){
		return m_method;
	}
	
	public VjoMethodControlFlowTable getReturnFlowTable(){
		return m_flowTable;
	}
}
