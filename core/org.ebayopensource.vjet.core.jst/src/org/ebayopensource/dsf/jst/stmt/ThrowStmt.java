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
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class ThrowStmt extends BaseJstNode implements IStmt {

	private static final long serialVersionUID = 1L;
	
	private IExpr m_expr;

	//
	// Constructor
	//
	public ThrowStmt(IExpr expr) {
		assert expr != null : "expr cannot be null";
		m_expr = expr;
		addChild(expr);
	}

	//
	// Satisfy IStmt
	//
	public String toStmtText() {
		StringBuilder sb = new StringBuilder("throw ");
		if (m_expr != null) {
			sb.append(m_expr.toExprText());
		}
		sb.append(";");
		return sb.toString();
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return toStmtText();
	}

	public IExpr getExpression() {
		return m_expr;
	}
}
