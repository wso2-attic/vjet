/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationDriver.VjoValidationMode;
import org.ebayopensource.dsf.jst.IJstNode;

public class BaseVjoSemanticRuleCtx implements IVjoSemanticRuleCtx {
	private IJstNode m_node;
	private String m_groupId;
	private String[] m_arguments;
	private VjoValidationMode m_mode;
	
	public BaseVjoSemanticRuleCtx(final IJstNode node, final String groupId, final String[] arguments){
		this(node, groupId, arguments, VjoValidationMode.validateTypeSpace);
	}
	
	public BaseVjoSemanticRuleCtx(final IJstNode node, final String groupId, final String[] arguments, final VjoValidationMode mode){
		m_node = node;
		m_groupId = groupId;
		
		if(arguments == null){
			m_arguments = new String[0];
		}
		else{
			m_arguments = new String[arguments.length];
			System.arraycopy(arguments, 0, m_arguments, 0, arguments.length);
		}
		
		m_mode = mode;
	}
	
	
	
	public IJstNode getNode() {
		return m_node;
	}
	
	public String getGroupId(){
		return m_groupId;
	}
	
	public String[] getArguments(){
		if(m_arguments == null){
			return new String[0];
		}
		
		final String[] arguments = new String[m_arguments.length];
		System.arraycopy(m_arguments, 0, arguments, 0, m_arguments.length);
		return arguments;
	}

	@Override
	public VjoValidationMode getMode() {
		return m_mode;
	}

	@Override
	public void setMode(VjoValidationMode mode) {
		m_mode = mode;
	}
}
