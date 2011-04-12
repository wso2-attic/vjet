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
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class DoStmt extends WhileStmt {
	
	private static final long serialVersionUID = 1L;

	//
	// Constructor
	//
	public DoStmt(){
	}
	
	//	 
	// Satisfy IStmt
	//
	@Override
	public String toStmtText(){
		
		IBoolExpr cond = getCondition();
		JstBlock body = getBody();
		
		StringBuilder sb = new StringBuilder("do ");
		sb.append(body.toBlockText());
		
		sb.append("while (");
		if (cond != null){
			sb.append(cond.toBoolExprText());
		}
		sb.append(");");
		
		return sb.toString();
	}
	

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
}
