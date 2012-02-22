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
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.SynthOlType;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class ObjLiteral extends JstLiteral {
	
	private static final long serialVersionUID = 1L;
	
	private List<NV> m_nvs = new ArrayList<NV>(2);
	private IJstType m_jstType;
	
	public ObjLiteral() {
		m_jstType = new SynthOlType(this);
	}
	
	public void setJstType(IJstType type){
		m_jstType = type;
	}
	
	//
	// Satisfy ILiteral
	//
	public String toValueText(){
		StringBuilder sb = new StringBuilder("{");
		for (int i=0; i<m_nvs.size(); i++){
			if (i > 0){
				sb.append(",");
			}
			sb.append(m_nvs.get(i).toString());
		}
		sb.append("}");
		
		return sb.toString();
	}
	
	@Override
	public IJstType getResultType(){
		return m_jstType;
	}
	
	public String toParamText(){
		return toValueText();
	}
	
	public String toTermText(){
		return toValueText();
	}
	
	public String toSimpleTermText(){
		return toValueText();
	}
	
	public String toRHSText(){
		return toValueText();
	}
	
	public String toExprText(){
		return toValueText();
	}
	
	//
	// API
	//
	public ObjLiteral add(final String name, final String value){
		return add(new NV(name, SimpleLiteral.getStringLiteral(value)));
	}
	
	public ObjLiteral add(final String name, final boolean value){
		return add(new NV(name, SimpleLiteral.getBooleanLiteral(value)));
	}
	
	public ObjLiteral add(final String name, final int value){
		return add(new NV(name, SimpleLiteral.getIntLiteral(value)));
	}
	
	public ObjLiteral add(final String name, final IExpr value){
		return add(new NV(name, value));
	}
	
	public ObjLiteral add(final NV nv){
		assert nv != null : "nv is null";
		m_nvs.add(nv);
		addChild(nv);
		return this;
	}
	
	public List<NV> getNVs(){
		return Collections.unmodifiableList(m_nvs);
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
