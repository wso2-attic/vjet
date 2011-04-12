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
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class ObjCreationExpr extends BaseJstNode implements IExpr {

	private static final long serialVersionUID = 1L;
	
	private MtdInvocationExpr m_invocation;
	private IExpr m_expr;
	private IJstType m_anonymousType;
	
	//
	// Constructor
	//
	public ObjCreationExpr(final MtdInvocationExpr invocation){
		this(invocation, null);
	}
	
	public ObjCreationExpr(final MtdInvocationExpr invocation, IJstType anonymousType){
		assert invocation != null : "invocation cannot be null";
		m_invocation = invocation;
		m_anonymousType = anonymousType;
		addChild(invocation);
		addChild(anonymousType);
	}
	
	//
	// Satisfy IExpr, IStmt
	//
	public IJstType getResultType(){
		IJstType result = m_invocation == null ? null : m_invocation.getResultType();
		//Jack, ObjectCreationExpr never return JstTypeRefType
		if (result != null && result instanceof IJstRefType) {
			result = ((IJstRefType)result).getReferencedNode();
		}
		return result;
	}
	
	public String toExprText(){
		StringBuilder sb = new StringBuilder();
		sb.append("new ");
		if (m_expr != null){
			sb.append(m_expr.toExprText()).append(".");
		}
		if (m_invocation != null){
			sb.append(m_invocation.toExprText());
		}
		return sb.toString();
	}
	
	public String toStmtText(){
		return toExprText() + ";";
	}
	
	//
	// API
	//
	public MtdInvocationExpr getInvocationExpr(){
		return m_invocation;
	}
	
	public void setExpression(IExpr expr){
		removeChild(m_expr);
		addChild(expr);
		m_expr = expr;
	}
	
	public IExpr getExpression(){
		return m_expr;
	}
	
	public IJstType getAnonymousType(){
		return m_anonymousType;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toExprText();
	}
}
