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

public class ExprStmt extends BaseJstNode implements IStmt {
	
	private static final long serialVersionUID = 1L;
	
	private IExpr m_expr;
	
	public ExprStmt(IExpr expr){
		m_expr = expr;
		addChild(expr);
	}

	public String toStmtText(){
		return m_expr == null ? ";" : m_expr.toExprText() + ";";
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toStmtText();
	}

	public IExpr getExpr() {
		return m_expr;
	}
}
