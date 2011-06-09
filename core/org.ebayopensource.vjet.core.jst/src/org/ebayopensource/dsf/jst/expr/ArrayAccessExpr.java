/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.expr;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstInferredType;
import org.ebayopensource.dsf.jst.declaration.JstMixedType;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class ArrayAccessExpr extends BaseJstNode implements ILHS, IExpr {

	private static final long serialVersionUID = 1L;

	private IExpr m_expr;

	private IExpr m_index;

	private IJstType m_type;

	//
	// Constructor
	//
	public ArrayAccessExpr(final IExpr expr) {
		this(expr, null);
	}

	public ArrayAccessExpr(final IExpr expr, final IExpr index) {
		assert m_expr != null : "m_expr is null";
		m_expr = expr;
		m_index = index;
		addChild(expr);
		addChild(index);
	}

	// 
	// Satisfy ILHS, IExpr
	//
	public String toExprText() {
		StringBuilder sb = new StringBuilder();
		String e = null;
		if (m_expr != null) {
			e = m_expr.toExprText();
		}
		String i = null;
		if (m_index != null) {
			i = m_index.toExprText();
		}
		if (e != null) {
			sb.append(e);
		}
		sb.append("[");
		if (i != null) {
			sb.append(i);
		}
		sb.append("]");
		String text = sb.toString();
		return text.length() > 0 ? text : null;
	}

	public String toLHSText() {
		return toExprText();
	}

	public IJstType getResultType() {
		if (m_type != null){
			return m_type;
		}
		if (m_expr != null){
			IJstType type = m_expr.getResultType();
			if (type != null){
				if(type instanceof JstMixedType){
					JstMixedType mixed = (JstMixedType)type;
					List<IJstType> mixedComponentType = new ArrayList<IJstType>();
					
					for(IJstType mixedType :mixed.getMixedTypes()){
						IJstType foundJstArray = getComponentType(mixedType);
						if(foundJstArray!=null){
							mixedComponentType.add(foundJstArray);
						}
					}
					
					return new JstMixedType(mixedComponentType);
				}
				
				return getComponentType(type);
			}
		}
		return null;
	}

	private IJstType getComponentType(IJstType type) {
		if(type instanceof JstInferredType){
			type = ((JstInferredType)type).getType();
		}
		if(type instanceof JstArray){
			return ((JstArray)type).getComponentType();
		}
		return null;
	}

	public IJstType getType() {
		return getResultType();
	}

	//
	// API, why using JstType?
	//
	public void setType(IJstType type) {
		m_type = type;
	}
	
	public IExpr getIndex() {
		return m_index;
	}

	public IExpr getExpr() {
		return m_expr;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return toExprText();
	}
}
