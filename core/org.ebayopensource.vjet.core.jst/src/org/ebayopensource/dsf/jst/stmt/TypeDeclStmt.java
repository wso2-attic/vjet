/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.stmt;

import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class TypeDeclStmt extends JstStmt implements IStmt {

	private static final long serialVersionUID = 1L;
	
	private JstType m_jstType;
	
	//
	// Constructor
	//
	public TypeDeclStmt(JstType jstType){
		m_jstType = jstType;
		addChild(m_jstType);
	}
	
	//
	// Satisfy IStmt
	//
	public String toStmtText(){
		if (m_jstType != null){
			return m_jstType.getName();
		}
		return null;
	}
	
	//
	// API
	//
	public JstType getType(){
		return m_jstType;
	}
	
	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
}
