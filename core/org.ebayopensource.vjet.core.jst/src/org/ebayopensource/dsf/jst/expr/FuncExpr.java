/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.expr;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstResultTypeModifier;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class FuncExpr extends BaseJstNode implements IExpr, IJstResultTypeModifier {
	
	private static final long serialVersionUID = 1L;
	
	private IJstType m_type;
	
	private JstMethod m_func;
	
	//
	// Constructor
	//
	public FuncExpr(final JstMethod func){
		m_func = func;
		addChild(func);
	}

	//
	// Satisfy IExpr
	//
	public String toExprText(){
		return m_func == null ? null : m_func.toString();
	}
	
	public IJstType getResultType(){
		if(m_type != null){
			return m_type;
		}
		return m_func == null ? null : m_func.getRtnType();
	}
	
	//
	// API
	//
	public JstMethod getFunc(){
		return m_func;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toExprText();
	}

	public void setType(IJstType type) {
		m_type = type;
	}
}
