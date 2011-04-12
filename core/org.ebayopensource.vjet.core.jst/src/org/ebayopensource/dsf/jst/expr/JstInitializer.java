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
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IInitializer;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstInitializer extends BaseJstNode implements IInitializer {

	private static final long serialVersionUID = 1L;
	
	private List<AssignExpr> m_assignments = new ArrayList<AssignExpr>();
	
	//
	// COnstructor
	//
	public JstInitializer(){
	}
	
	public JstInitializer(final ILHS lhs, final IExpr expr){
		addAssignment(lhs, expr);
	}
	
	//
	// Satisfy IInitializer
	//
	public IJstType getType(){
		if (m_assignments.size() > 0){
			return m_assignments.get(0).getResultType();
		}
		return null;
	}
	
	public List<AssignExpr> getAssignments(){
		return Collections.unmodifiableList(m_assignments);
	}
	
	public String toText(){
		StringBuilder sb = new StringBuilder();
		AssignExpr assignExpr;
		for (int i=0; i<m_assignments.size(); i++){
			if (i>0){
				sb.append(",");
			}
			assignExpr = m_assignments.get(i);
			sb.append(assignExpr.getLHS().toLHSText());
			if (assignExpr.getExpr() != null){
				sb.append(assignExpr.getOprator().toString());
				sb.append(assignExpr.getExpr().toExprText());
			}
		}
		return sb.toString();
	}
	
	//
	// API
	//
	public void addAssignment(final ILHS lhs, final IExpr expr){
		addAssignment(new AssignExpr(lhs, expr));
	}
	
	public void addAssignment(final AssignExpr assignment){
		m_assignments.add(assignment);
		addChild(assignment);
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
