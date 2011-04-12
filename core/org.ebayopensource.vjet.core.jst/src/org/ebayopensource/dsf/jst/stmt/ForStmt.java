/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.stmt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.token.IBoolExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IInitializer;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class ForStmt extends BlockStmt implements IStmt {

	private static final long serialVersionUID = 1L;
	
	private IInitializer m_initializers;
	private IBoolExpr m_condition;
	private List<IExpr> m_updaters = new ArrayList<IExpr>(1);
	
	//
	// COnstructor
	//
	public ForStmt(){
	}
	
	//
	// API
	//
	public ForStmt setInitializer(final IInitializer initializers){
		removeChild(m_initializers);
		addChild(initializers);
		m_initializers = initializers;
		return this;
	}
	
	public IInitializer getInitializers(){
		return m_initializers;
	}
	
	public ForStmt setCondition(final IBoolExpr condition){
		assert condition != null : "condition cannot be null";
		removeChild(m_condition);
		addChild(condition);
		m_condition = condition;
		return this;
	}
	
	public IBoolExpr getCondition(){
		return m_condition;
	}
	
	public ForStmt addUpdater(final IExpr updater){
		assert updater != null : "updater cannot be null";
		m_updaters.add(updater);
		addChild(updater);
		return this;
	}
	
	public List<IExpr> getUpdaters(){
		return Collections.unmodifiableList(m_updaters);
	}
	
	public ForStmt addStmt(final IStmt stmt){
		assert stmt != null : "stmt cannot be null";
		getBody().addStmt(stmt);
		return this;
	}
	
	// 
	// Satisfy IStmt
	//
	public String toStmtText(){
		StringBuilder sb = new StringBuilder("for (");
		if (m_initializers != null) {
			sb.append(m_initializers.toText());
		}
		sb.append(";");
		if (m_condition!= null){
			sb.append(m_condition.toBoolExprText());
		}
		sb.append(";");
		for (int i=0; i<m_updaters.size(); i++){
			IExpr updater = m_updaters.get(i);
			sb.append(updater.toExprText());
			if (i < m_updaters.size() -1) {
				sb.append(",");
			}
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
