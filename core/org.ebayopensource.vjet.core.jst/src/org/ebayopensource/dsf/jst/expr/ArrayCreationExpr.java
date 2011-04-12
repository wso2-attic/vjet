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
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;

public class ArrayCreationExpr extends BaseJstNode implements IExpr {

	private static final long serialVersionUID = 1L;
	
	private final IExpr m_identifier;
	private final List<IExpr> m_dimensions = new ArrayList<IExpr>();
	
	//
	// Constructor
	//
	public ArrayCreationExpr(final IExpr identifier){
		assert identifier != null : "identifier cannot be null";
		m_identifier = identifier;
		addChild(identifier);
	}
	
	//
	// API
	//
	public void addDimension(IExpr dimension){
		m_dimensions.add(dimension);
		addChild(dimension);
	}
	
	public List<IExpr> getDimensions(){
		return m_dimensions;
	}
	
	//
	// Satisfy IExpr, IStmt
	//
	public IJstType getResultType(){
		return m_identifier == null ? null : m_identifier.getResultType();
	}
	
	public String toExprText(){
		StringBuilder sb = new StringBuilder("vjo.createArray(");
		sb.append(getDefaultValString());
		for (IExpr d: m_dimensions){
			if (d != null){
				sb.append(", ").append(d.toExprText());
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	private String getDefaultValString() {
		String type;
		String dType = m_identifier.toExprText().replace("[]", "");
		type = DataTypeHelper.getDefaultValue(dType);
		if (null == type) {
			type = "null";
		}
		return type;
	}
	public String toStmtText(){
		return toExprText() + ";";
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
