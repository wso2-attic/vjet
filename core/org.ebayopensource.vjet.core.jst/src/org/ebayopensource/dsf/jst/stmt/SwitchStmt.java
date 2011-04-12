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

public class SwitchStmt extends BlockStmt implements IStmt {

	private static final long serialVersionUID = 1L;
	
	private IExpr m_expr;
	
	//
	// Constructor
	//
	public SwitchStmt(){
	}
	
	//
	// API
	//
	public SwitchStmt setExpr(final IExpr expr){
		assert expr != null : "expr cannot be null";
		removeChild(m_expr);
		addChild(expr);
		m_expr = expr;
		return this;
	}
	
	public IExpr getExpr(){
		return m_expr;
	}
	
	public SwitchStmt addStmt(final IStmt stmt){
		assert stmt != null : "stmt cannot be null";
		getBody().addStmt(stmt);
		return this;
	}
	
	// 
	// Satisfy IStmt
	//
	public String toStmtText(){
		if (m_expr == null){
			return null;
		}
		
		StringBuilder sb = new StringBuilder("switch(");
		sb.append(m_expr.toExprText());
		sb.append("){\n");
		sb.append(getBody().toBlockText());
		sb.append("}");
		
		return sb.toString();
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toStmtText();
	}
	
	//
	// Inner
	//
	public static class CaseStmt extends BaseJstNode implements IStmt {
		
		private static final long serialVersionUID = 1L;
		
		private final IExpr m_expr;
		
		//
		// Constructor
		//
		public CaseStmt(final IExpr expr){
			m_expr = expr;
			addChild(expr);
		}
		
		//
		// API
		//
		public IExpr getExpr(){
			return m_expr;
		}
		
		//		 
		// Satisfy IStmt
		//
		public String toStmtText(){
			if (m_expr == null){
				return "default: ";
			}

			return "case " + m_expr.toExprText() +":";
		}
		

		@Override
		public void accept(IJstNodeVisitor visitor){
			visitor.visit(this);
		}
		
		@Override
		public String toString(){
			return toStmtText();
		}
	}
}
