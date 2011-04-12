/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.stmt;

import org.ebayopensource.dsf.jst.token.IBoolExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class WhileStmt extends BlockStmt implements IStmt {

	private static final long serialVersionUID = 1L;
	
	private IBoolExpr m_condition;
	
	//
	// Constructor
	//
	public WhileStmt(){
	}
	
	//
	// API
	//
	public WhileStmt setCondition(final IBoolExpr condition){
		assert condition != null : "condition cannot be null";
		removeChild(m_condition);
		addChild(condition);
		m_condition = condition;
		return this;
	}
	
	public IBoolExpr getCondition(){
		return m_condition;
	}
	
	public WhileStmt addStmt(final IStmt stmt){
		assert stmt != null : "stmt cannot be null";
		getBody().addStmt(stmt);
		return this;
	}
	
	// 
	// Satisfy IStmt
	//
	public String toStmtText(){
		StringBuilder sb = new StringBuilder("while (");
		if (m_condition!= null){
			sb.append(m_condition.toBoolExprText());
		}
		sb.append(")");
		sb.append(getBody().toBlockText());
		
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
}
