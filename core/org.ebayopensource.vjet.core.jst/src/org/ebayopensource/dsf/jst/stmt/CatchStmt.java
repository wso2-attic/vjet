/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.stmt;

import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class CatchStmt extends BlockStmt implements IStmt {
	
	private static final long serialVersionUID = 1L;
	
	private JstVar m_exception;
	
	//
	// Constructor
	//
	public CatchStmt(JstVar exception){
		assert exception != null : "exception cannot be null";
		m_exception = exception;
		addChild(exception);
	}
	
	//
	// API
	//
	public void add(IStmt stmt){
		getBody().addStmt(stmt);
	}
	
	public JstVar getException(){
		return m_exception;
	}
	
	//
	// Satisfy IStmt
	//
	public String toStmtText(){
		StringBuilder sb = new StringBuilder("catch (");
		if (m_exception != null){
			sb.append(m_exception.getName()).append("){")
				.append(getBody().toBlockText())
			.append("}");
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
