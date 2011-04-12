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

public class InvalidIdentifierNameWithKeywordRuleCtx extends
		BaseVjoSemanticRuleCtx {

	private String m_idName;
	private boolean m_inEnumCtx;
	private String[] m_keywordInCtx;
	
	public InvalidIdentifierNameWithKeywordRuleCtx(final String idName, final boolean inEnumCtx){
		super(null, "", new String[0]);
		m_idName = idName;
		m_inEnumCtx = inEnumCtx;
	}
	
	public InvalidIdentifierNameWithKeywordRuleCtx(final IJstNode node, final String groupId, final String[] arguments, final String idName, final boolean inEnumCtx){
		super(node, groupId, arguments);
		m_idName = idName;
		m_inEnumCtx = inEnumCtx;
	}
	
	public InvalidIdentifierNameWithKeywordRuleCtx(final IJstNode node, final String groupId, final String[] arguments, final String idName, final String[] keywordsInCtx){
		super(node, groupId, arguments);
		m_idName = idName;
		if(keywordsInCtx == null){
			m_keywordInCtx = new String[0];
		}
		else{
			m_keywordInCtx = new String[keywordsInCtx.length];
			System.arraycopy(keywordsInCtx, 0, m_keywordInCtx, 0, keywordsInCtx.length);
		}
	}
	
	public String getIdentifierName(){
		return m_idName;
	}
	
	public boolean isEnumContexted(){
		return m_inEnumCtx;
	}
	
	public String[] getKeywordsInContext(){
		if(m_keywordInCtx == null){
			return null;
		}
		
		final String[] keywordInCtx = new String[m_keywordInCtx.length];
		System.arraycopy(m_keywordInCtx, 0, keywordInCtx, 0, keywordInCtx.length);
		return keywordInCtx;
	}
}
