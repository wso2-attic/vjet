/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;
import org.ebayopensource.dsf.common.Z;

public final class JstVar extends BaseJstNode implements ILHS, IStmt {

	private static final long serialVersionUID = 1L;
	
	private IJstTypeReference m_typeRef;
	private final String m_name;
	
	//
	// Constructor
	//
	public JstVar(final IJstType type, final String name){
		assert type != null : "type is null";
		assert name != null : "name is null";
		m_typeRef = new JstTypeReference(type);
		addChild(m_typeRef);
		m_name = name;
	}
	
	public JstVar(final IJstTypeReference typeRef, final String name){
		assert typeRef != null : "typeRef is null";
		assert name != null : "name is null";
		m_typeRef = typeRef;
		addChild(m_typeRef);
		m_name = name;
	}
	
	// 
	// Satisfy ILHS
	//
	public String toLHSText(){
		return "var " + m_name;
	}
	
	public String toStmtText(){
		return toLHSText() + ";";
	}
	
	//
	// API
	//
	public IJstType getType(){
		return m_typeRef.getReferencedType();
	}
	
	public void setType(IJstType type){
		m_typeRef = new JstTypeReference(type);
	}
	
	public IJstTypeReference getTypeRef(){
		return m_typeRef;
	}
	
	public String getName(){
		return m_name;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){	
		Z z = new Z();
		z.format("m_type", m_typeRef.getReferencedType());
		z.format("m_name", m_name);
		
		return z.toString();
	}
}
