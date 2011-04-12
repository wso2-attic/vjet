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
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class CastExpr extends BaseJstNode implements IExpr {
	
	private static final long serialVersionUID = 1L;
	
	private IExpr m_expr;
	private IJstType m_castTo;
	
	//
	// Constructor
	//
	public CastExpr(final IExpr expr) {
		if (expr == null){
			throw new AssertionError("expr cannot be null");
		}
		m_expr = expr;
		addChild(expr);
	}
	public CastExpr(final IExpr expr, final IJstType castTo){
		this(expr);
		if (castTo == null){
			throw new AssertionError("castTo cannot be null");
		}
		m_castTo = castTo;
		addChild(new JstTypeReference(castTo));
	}

	//
	// Satisfy IExpr
	//
	public String toExprText(){
		return m_expr.toExprText();
	}
	
	public IJstType getResultType(){
		return m_castTo!=null? m_castTo : m_expr.getResultType();
	}
	
	@Override
	public JstSource getSource() {
		return (super.getSource()!=null) ? super.getSource() : m_expr.getSource(); 
	}
	
	//
	// API
	//
	public IExpr getExpr(){
		return m_expr;
	}
	
	public IJstType getCastToType(){
		return m_castTo;
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
