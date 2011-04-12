/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.completion;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;

public class JstSyntaxError {
	
	private static final long serialVersionUID = 1L;
	private JstSource m_source;
	
	private String token;
	private String compositeToken;
	private IJstNode m_parent;
	
	public JstSyntaxError(final BaseJstNode parent){
		m_parent = parent;
//		super(parent);
	}
	
	public void setSource(JstSource source) {
		m_source = source;
	}

	public JstSource getSource() {
		return m_source;
	}
	
	public String getCompositeToken() {
		return compositeToken;
	}

	public void setCompositeToken(String compositeToken) {
		this.compositeToken = compositeToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public void setParent(IJstType node){
		m_parent = node;
	}
	
	public IJstType getParentNode(){
		return m_parent == null ? null : m_parent.getOwnerType();
	}
	
}
