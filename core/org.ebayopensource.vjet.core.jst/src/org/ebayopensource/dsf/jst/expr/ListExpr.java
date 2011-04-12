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
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public final class ListExpr extends BaseJstNode implements IExpr, IStmt {

	private static final long serialVersionUID = 1L;

	private final IExpr[] m_exprTerms;
	
	public ListExpr(IExpr[] exprTerms) {
		m_exprTerms = exprTerms;
		for (IExpr expr : exprTerms) {
			addChild(expr);
		}
	}
	
	public IExpr[] getExprTerms() {
		return m_exprTerms;
	}
	
	public IJstType getResultType() {
		return null;
	}

	public String toExprText() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m_exprTerms.length; i++) {
			sb.append(m_exprTerms[i].toExprText());
			if (i != m_exprTerms.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public String toStmtText() {
		return toExprText() + ";";
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
}
