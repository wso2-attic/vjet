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

public class ContinueStmt extends JstStmt implements IStmt {
	
	private static final long serialVersionUID = 1L;
	
	private JstIdentifier m_identifier;
	
	//
	// Constructor
	//
	public ContinueStmt(){
	}
	
	public ContinueStmt(JstIdentifier identifier){
		m_identifier = identifier;
		addChild(identifier);
	}
	
	//
	// Satisfy IStmt
	//
	public String toStmtText(){
		if (m_identifier == null){
			return "continue;";
		}
		return "continue " + m_identifier.toSimpleTermText() + ";";
	}
	
	//
	// API
	//
	public JstIdentifier getIdentifier(){
		return m_identifier;
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
