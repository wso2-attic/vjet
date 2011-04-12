/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.stmt;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class PtySetter extends JstStmt implements IExpr, IStmt {
	
	private static final long serialVersionUID = 1L;
	
	private final JstIdentifier m_ptyName;
	private IExpr m_qualifyExpr;
	private final String m_ptyValue;
	
	//
	// Constructor
	//
	public PtySetter(final String ptyName, final String ptyValue, BaseJstNode parent){
		this(new JstIdentifier(ptyName), ptyValue);
	}

	public PtySetter(final JstIdentifier ptyName, final String ptyValue){
		this(ptyName, null, ptyValue);
	}
	
	public PtySetter(final JstIdentifier ptyName, final IExpr qualifyExpr, final String ptyValue){
		assert ptyName != null : "ptyName cannot be null";
		m_ptyName = ptyName;
		m_qualifyExpr = qualifyExpr;
		m_ptyValue = ptyValue;
		addChild(ptyName);
		addChild(qualifyExpr);
	}
	
	//
	// Satisfy IExpr, IStmt
	//
	public IJstType getResultType(){
		return m_ptyName == null ? null : m_ptyName.getType();
	}
	
	public String toExprText(){
		StringBuilder sb = new StringBuilder();
		String q = null;
		if (m_qualifyExpr != null){
			q = m_qualifyExpr.toExprText();
		}
		String n = null;
		if (m_ptyName != null){
			n = m_ptyName.toSimpleTermText();
		}
		if (q != null){
			sb.append(q);
		}
		if (q != null && n != null){
			sb.append(".");
		}
		if (n != null){
			sb.append(n);
		}
		sb.append("=");
		if (m_ptyValue != null){
			sb.append(m_ptyValue);
		}
		String text = sb.toString();
		return text.length() > 0 ? text : null;
	}
	
	public String toStmtText(){
		return toExprText() + ";";
	}
	
	//
	// API
	//
	public JstIdentifier getPtyName(){
		return m_ptyName;
	}
	
	public void setQualifyExpr(IExpr qualifyExpr){
		removeChild(m_qualifyExpr);
		addChild(qualifyExpr);
		m_qualifyExpr = qualifyExpr;
	}
	
	public IExpr getQualifyExpr(){
		return m_qualifyExpr;
	}
	
	public String getPtyValue(){
		return m_ptyValue;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toExprText();
	}
}
