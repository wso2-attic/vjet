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
import org.ebayopensource.dsf.jst.token.IBoolExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class ConditionalExpr extends BaseJstNode implements IExpr {
		
	private static final long serialVersionUID = 1L;
	
	private IBoolExpr m_cond;
	private IExpr m_then;
	private IExpr m_else;
	
	private IJstType m_resultType;
	//
	// Constructor
	//
	public ConditionalExpr(final IBoolExpr cond, final IExpr thenExpr, final IExpr elseExpr){
		assert cond != null : "cond cannot be null";
		assert thenExpr != null : "thenExpr cannot be null";
		assert elseExpr != null : "elseExpr cannot be null";
		
		m_cond = cond;
		m_then = thenExpr;
		m_else = elseExpr;
		addChild(cond);
		addChild(m_then);
		addChild(m_else);
	}
	
	//
	// Satisfy IBoolExpr, IExpr, IStmt
	//
	public IJstType getResultType(){
		return m_resultType != null ? m_resultType : (m_then == null ? null : m_then.getResultType());
	}

	public void setResultType(final IJstType resultType) {
		m_resultType = resultType;
	}
	
	public String toExprText(){
		StringBuilder sb = new StringBuilder();
		if (m_cond != null){
			sb.append(m_cond.toExprText());
		}
		sb.append("?");
		if (m_then != null){
			sb.append(m_then.toExprText());
		}
		sb.append(":");
		if (m_else != null){
			sb.append(m_else.toExprText());
		}
		return  sb.toString();
	}
	
	//
	// API
	//
	public IBoolExpr getCondition(){
		return m_cond;
	}
	
	public IExpr getThenExpr(){
		return m_then;
	}
	
	public IExpr getElseExpr(){
		return m_else;
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
