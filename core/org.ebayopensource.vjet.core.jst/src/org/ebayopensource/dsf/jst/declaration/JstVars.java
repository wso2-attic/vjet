/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.JstInitializer;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IInitializer;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstVars extends BaseJstNode implements IInitializer, IStmt {
	
	private static final long serialVersionUID = 1L;
	
	private IJstTypeReference m_typeRef;
	private JstInitializer m_initializer;
	private boolean m_final = false;
	
	//
	// Constructor
	//
	public JstVars(final IJstType type){
		this(new JstTypeReference(type));
	}
	
	public JstVars(final IJstType type, final JstInitializer initializers){
		this(new JstTypeReference(type), initializers);
	}
	
	public JstVars(final IJstTypeReference typeRef){
		m_typeRef = typeRef;
		addChild(m_typeRef);
	}
	
	public JstVars(final IJstTypeReference typeRef, final JstInitializer initializers){
		m_typeRef = typeRef;
		addChild(m_typeRef);
		m_initializer= initializers;
		addChild(m_initializer);
	}
	
	//
	// Satisfy IInitializer, IStmt
	//
	public IJstType getType(){
		if (m_typeRef != null){
			return m_typeRef.getReferencedType();
		} 
		if (m_initializer != null){
			return m_initializer.getType();
		}
		return null;
	}
	
	public void setType(IJstType type){
		m_typeRef = new JstTypeReference(type);
	}
	
	public void setIsFinal(boolean isFinal) {
		m_final = isFinal;
	}
	
	public boolean isFinal() {
		return m_final;
	}
	
	public String toText(){
		StringBuilder sb = new StringBuilder();
		if (m_typeRef != null){
			sb.append("var ");
		}
		if (m_initializer != null){
			return m_initializer.toText();
		}
		return sb.toString();
	}
	
	public String toStmtText(){
		return toText() + ";";
	}
	
	//
	// API
	//
	public IJstTypeReference getTypeRef(){
		return m_typeRef;
	}
	
	public void addAssignment(final ILHS lhs, final IExpr expr){
		addAssignment(new AssignExpr(lhs, expr));
	}
	
	public void addAssignment(final AssignExpr assignExpr){
		if (m_initializer == null){
			m_initializer = new JstInitializer();
		}
		m_initializer.addAssignment(assignExpr);
		addChild(assignExpr);
	}
	
	public JstInitializer getInitializer(){
		return m_initializer;
	}
	
	public List<AssignExpr> getAssignments(){
		if (m_initializer == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_initializer.getAssignments());
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toText();
	}
}
