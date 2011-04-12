/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.stmt;

import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.token.IBoolExpr;
import org.ebayopensource.dsf.jst.token.IIfStmt;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class IfStmt extends BlockStmt implements IIfStmt {

	private static final long serialVersionUID = 1L;
	
	private IBoolExpr m_condition;
	private JstBlock m_elseIfBlock;
	private JstBlock m_elseBlock;
	
	//
	// Constructor
	//
	public IfStmt(){
	}
	
	//
	// API
	//
	public IfStmt setCondition(final IBoolExpr condition){
		assert condition != null : "condition cannot be null";
		removeChild(m_condition);
		addChild(condition);
		m_condition = condition;
		return this;
	}
	
	public IBoolExpr getCondition(){
		return m_condition;
	}
	
	public IfStmt addThenStmt(final IStmt stmt){
		assert stmt != null : "stmt cannot be null";
		getBody().addStmt(stmt);
		return this;
	}
	
	public IfStmt addElseStmt(final IfStmt stmt){
		assert stmt != null : "stmt cannot be null";
		getElseIfBlock(true).addStmt(stmt);
		return this;
	}
	
	public IfStmt addElseStmt(final IStmt stmt){
		assert stmt != null : "stmt cannot be null";
		getElseBlock(true).addStmt(stmt);
		return this;
	}
	
	public JstBlock getElseIfBlock(){
		return m_elseIfBlock;
	}
	
	public JstBlock getElseIfBlock(boolean create){
		if (m_elseIfBlock == null && create){
			m_elseIfBlock = new JstBlock();
			addChild(m_elseIfBlock);
		}
		return m_elseIfBlock;
	}
	
	public JstBlock getElseBlock(){
		return m_elseBlock;
	}
	
	
	public JstBlock getElseBlock(boolean create){
		if (m_elseBlock == null && create){
			m_elseBlock = new JstBlock();
			addChild(m_elseBlock);
		}
		return m_elseBlock;
	}
	
	// 
	// Satisfy IStmt
	//
	public String toStmtText(){
		if (m_condition == null){
			return null;
		}
		
		StringBuilder sb = new StringBuilder("if(");
		sb.append(m_condition.toBoolExprText());
		sb.append(")");
		sb.append(getBody().toBlockText());
		if (m_elseIfBlock != null){
			for (IStmt eif: m_elseIfBlock.getStmts()){
				sb.append("else ").append(eif.toStmtText());
			}
		}
		if (m_elseBlock != null){
			sb.append("else ").append(m_elseBlock.toBlockText());
		}
		
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
