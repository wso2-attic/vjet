/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.term;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public final class ArrayLiteral extends JstLiteral {
	
	private static final long serialVersionUID = 1L;
	
	private List<IExpr> m_values = new ArrayList<IExpr>(2);
	
	public ArrayLiteral() {
		m_jstType = JstCache.getInstance().getType("Array");
	}
	
	//
	// Satisfy ILiteral
	//
	public String toValueText(){
		StringBuilder sb = new StringBuilder("[");
		for (int i=0; i<m_values.size(); i++){
			if (i > 0){
				sb.append(",");
			}
			sb.append(m_values.get(i).toExprText());
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	public String toSimpleTermText(){
		return toValueText();
	}
	
	public String toExprText(){
		return toValueText();
	}
	
	//
	// API
	//
	public ArrayLiteral add(final JstLiteral value){
		assert value != null : "value cannot be null";
		m_values.add(value);
		addChild(value);
		return this;
	}
	
	public Iterator<IExpr> getValues(){
		return m_values.iterator();
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toValueText();
	}
}
