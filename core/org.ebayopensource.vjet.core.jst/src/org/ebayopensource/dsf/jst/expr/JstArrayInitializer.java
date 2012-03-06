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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstResultTypeModifier;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstInferredRefType;
import org.ebayopensource.dsf.jst.declaration.JstInferredType;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstArrayInitializer extends BaseJstNode implements IExpr,IJstResultTypeModifier  {

	private static final long serialVersionUID = 1L;
	
	private List<IExpr> m_exprs;
	private IJstType m_type;//JstFactory.getInstance().createJstArrayType(m_exprs.get(0).getResultType(), false)
	
	//
	// API
	//
	public void add(IExpr e){
		assert e != null : "e cannot be null";
		synchronized (this){
			if (m_exprs == null){
				m_exprs = new ArrayList<IExpr>();
			}
			m_exprs.add(e);
		}
		addChild(e);
		if (m_type == null){
			if (e != null && e.getResultType() != null){
				//bugfix by huzhou@ebay.com
				//as the jstArray type's component type is inferred
				m_type = JstFactory.getInstance().createJstArrayType(getInferredComponentType(e), false);
			}
		}
	}

	private IJstType getInferredComponentType(IExpr e) {
		final IJstType resultType = e.getResultType();
		if(resultType instanceof IJstRefType){
			return new JstInferredRefType((IJstRefType)resultType);
		}
		else{
			return new JstInferredType(resultType);
		}
	}
	
	//
	// Satisfy IExpr
	//
	public IJstType getResultType(){
		return m_type;
	}
	
	public String toExprText(){
		StringBuilder sb = new StringBuilder("[");
		if (m_exprs != null && !m_exprs.isEmpty()){
			for (int i=0; i<m_exprs.size(); i++){
				if (i>0){
					sb.append(",");
				}
				if (m_exprs.get(i) != null){
					sb.append(m_exprs.get(i).toExprText());
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toExprText();
	}
	
	//
	// API
	//
	public void setType(JstArray type){
		m_type = type;
	}
	public List<IExpr> getExprs(){
		if(m_exprs==null){
			return Collections.EMPTY_LIST;
		}
		return m_exprs;
	}
	public void setExprs(List<IExpr> exprs){
		for (IExpr expr: m_exprs){
			removeChild(expr);
		}
		if (exprs.size() > 0){
			m_exprs = new ArrayList<IExpr>();
			for (IExpr expr : exprs){
				m_exprs.add(expr);
				addChild(expr);
			}
		}
		
	}	
	
	@Override
	public void setType(IJstType type) {
		if (type instanceof JstArray) {
			m_type = (JstArray) type;
		}
	}
}
