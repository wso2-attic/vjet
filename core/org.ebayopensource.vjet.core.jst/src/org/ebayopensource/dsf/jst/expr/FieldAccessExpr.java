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
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class FieldAccessExpr extends BaseJstNode implements ILHS, IExpr {

	private static final long serialVersionUID = 1L;
	
	private JstIdentifier m_name;
	private IExpr m_qualifier;
	private IJstType m_type;
	
	//
	// Constructor
	//
	public FieldAccessExpr(final JstIdentifier name){
		this(name, null);
	}
	
	public FieldAccessExpr(final JstIdentifier name, final IExpr qualifier){
		assert name != null : "name is null";
		m_name = name;
		m_qualifier = qualifier;
		addChild(name);
		addChild(qualifier);
	}
	
	// 
	// Satisfy ILHS, IExpr
	//
	public String toExprText(){
		StringBuilder sb = new StringBuilder();
		String n = null;
		if (m_name != null){
			n = m_name.toSimpleTermText();
		}
		String q = null;
		if (m_qualifier != null){
			q = m_qualifier.toExprText();
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
	
	public String toLHSText(){
		return toExprText();
	}
	
	public IJstType getResultType(){
		return m_type != null ? m_type : m_name.getResultType();
	}
	
	public IJstType getType(){
		return m_type != null ? m_type : m_name.getType();
	}
	
	//
	// API
	//
	public void setName(final JstIdentifier name){
		removeChild(m_name);
		addChild(name);
		m_name = name;
	}
	
	public JstIdentifier getName(){
		return m_name;
	}
	
	public void setExpr(final IExpr qualifier){
		removeChild(m_qualifier);
		addChild(qualifier);
		m_qualifier = qualifier;
	}
	
	public IExpr getExpr(){
		return m_qualifier;
	}
	
	public void setType(IJstType type){
		m_type = type;
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
