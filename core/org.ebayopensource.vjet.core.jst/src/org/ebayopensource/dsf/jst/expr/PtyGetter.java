/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.expr;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class PtyGetter extends BaseJstNode implements IExpr {
	
	private static final long serialVersionUID = 1L;
	
	private final JstIdentifier m_ptyName;
	private IExpr m_qualifyExpr;
	
	//
	// Constructor
	//
	public PtyGetter(final String ptyName){
		this(new JstIdentifier(ptyName));
	}

	public PtyGetter(final JstIdentifier ptyName){
		this(ptyName, null);
	}
	
	public PtyGetter(final JstIdentifier ptyName, final IExpr qualifyExpr){
		assert ptyName != null : "ptyName cannot be null";
		m_ptyName = ptyName;
		m_qualifyExpr = qualifyExpr;
		addChild(ptyName);
		addChild(qualifyExpr);
	}
	
	//
	// Satisfy IExpr
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
		String text = sb.toString();
		return text.length() > 0 ? text : null;
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

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toExprText();
	}
}
