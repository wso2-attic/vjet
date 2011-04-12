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
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class TextExpr extends BaseJstNode implements IExpr {
	
	private static final long serialVersionUID = 1L;
	
	private String m_text;
	private IJstType m_resultType;
	
	//
	// Constructor
	//
	public TextExpr(final String text){
		this(text, null);
	}

	public TextExpr(final String text, final IJstType resultType){
		assert text != null : "text cannot be null";
		m_text = text;
		m_resultType = resultType;
	}

	//
	// Satisfy IExpr
	//
	public String toExprText(){
		return m_text;
	}
	
	public IJstType getResultType(){
		return m_resultType;
	}
	
	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	//
	// Overrides(s) from Object
	//
	@Override
	public String toString() {
		if (m_resultType == null) return m_text ;
		
		return m_text + ": " + m_resultType.getName();
	}
}
