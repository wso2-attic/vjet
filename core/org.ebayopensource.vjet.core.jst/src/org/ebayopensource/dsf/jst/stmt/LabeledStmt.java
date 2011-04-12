/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.stmt;

import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class LabeledStmt extends JstStmt implements IStmt {
	
	private static final long serialVersionUID = 1L;
	
	private JstIdentifier m_identifier;
	private IStmt m_stmt;
	
	//
	// Construcotr
	//
	public LabeledStmt(JstIdentifier identifier){
		this(identifier, null);
	}
	
	public LabeledStmt(JstIdentifier identifier, IStmt stmt){
		assert identifier != null : "identifier cannot be null";
		assert stmt != null : "stmt cannot be null";
		m_identifier = identifier;
		addChild(identifier);
		setStmt(stmt);
	}
	
	//
	// Satisfy IStmt
	//
	public String toStmtText(){
		return m_identifier.toSimpleTermText() + ":\n" + m_stmt.toString();
	}
	
	//
	// API
	//
	public JstIdentifier getLabel(){
		return m_identifier;
	}
	
	public void setStmt(IStmt stmt){
		m_stmt = stmt;
		addChild(stmt);
	}
	
	public IStmt getStmt(){
		return m_stmt;
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
