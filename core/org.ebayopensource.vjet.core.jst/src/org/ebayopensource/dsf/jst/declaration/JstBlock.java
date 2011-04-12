/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstBlock extends BaseJstNode {
	
	private static final long serialVersionUID = 1L;
	
	private List<IStmt> m_stmts; 
	protected VarTable m_varTable;
	
	public JstBlock() {
		// TODO Auto-generated constructor stub
	}
	
	//
	// API
	//
	public JstBlock addStmt(int index, final IStmt stmt){
		if (stmt == null){
			return this;
		}
		if (m_stmts == null){
			m_stmts = new ArrayList<IStmt>(5);
		}
		m_stmts.add(index, stmt);
		addChild((BaseJstNode)stmt);
		return this;
	}
	
	public JstBlock addStmt(final IStmt stmt){
		if (stmt == null){
			return this;
		}
		if (m_stmts == null){
			m_stmts = new ArrayList<IStmt>(5);
		}
		m_stmts.add(stmt);
		addChild((BaseJstNode)stmt);
		return this;
	}
	
	public List<IStmt> getStmts(){
		if (m_stmts == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_stmts);
	}
	
	public VarTable getVarTable(){
		if (m_varTable == null){
			m_varTable = new VarTable();
		}
		return m_varTable;
	}
	
	public String toBlockText(){
		StringBuilder sb = new StringBuilder("{");
		for (IStmt stmt: getStmts()){
			sb.append("\n").append(stmt.toStmtText());
		}
		sb.append("\n}");
		
		return sb.toString();
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toBlockText();
	}
}
