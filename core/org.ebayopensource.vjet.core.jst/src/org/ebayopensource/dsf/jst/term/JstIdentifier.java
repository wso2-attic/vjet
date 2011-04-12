/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.term;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.token.ISimpleTerm;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstIdentifier extends BaseJstNode implements ILHS, ISimpleTerm, IExpr {

	private static final long serialVersionUID = 1L;
	
	private String m_name;
	private JstIdentifier m_qualifier;
	private IJstType m_type;
	private IJstNode m_binding;
	
	//
	// Constructor
	//
	public JstIdentifier(final String name){
		this(name, null);
	}
	
	public JstIdentifier(final String name, final JstIdentifier qualifier){
		assert name != null : "name is null";
		m_name = name;
		m_qualifier = qualifier;
		addChild(m_qualifier);
	}
	
	// 
	// Satisfy ILHS, IParam, ITerm
	//
	public String toSimpleTermText(){
		StringBuilder sb = new StringBuilder();
		String q = null;
		if (m_qualifier != null){
			q = m_qualifier.toSimpleTermText();
		}
		if (q != null){
			sb.append(q);
		}
		if (q != null && m_name != null){
			sb.append(".");
		}
		if (m_name != null){
			//sb.append(DataTypeHelper.getTypeName(m_name));
			sb.append(m_name);
		}
		String text = sb.toString();
		return text.length() > 0 ? text : null;
	}
	
	public IJstType getType(){
		if (m_type != null){
			return m_type;
		}
		if (m_binding != null){
			if (m_binding instanceof IJstProperty){
				return ((IJstProperty)m_binding).getType();
			}
			else if (m_binding instanceof IJstMethod){
				return ((IJstMethod)m_binding).getRtnType();
			}
			else if (m_binding instanceof IJstType){
				return (IJstType)m_binding;
			}
		}
		return m_type;
	}
	
	public String toLHSText(){
		return toSimpleTermText();
	}
	
	public IJstType getResultType(){
		return getType();
	}
	
	public String toExprText(){
		return toSimpleTermText();
	}
	
	//
	// API
	//
	public void setName(final String name){
		m_name = name;
	}
	
	public String getName(){
		return m_name;
	}
	
	public void setQualifier(final JstIdentifier qualifier){
		removeChild(m_qualifier);
		addChild(qualifier);
		m_qualifier = qualifier;
	}
	
	public JstIdentifier getQualifier(){
		return m_qualifier;
	}
	
	public JstIdentifier setType(IJstType type){
		m_type = type;
		return this;
	}
	
	public void setJstBinding(IJstNode binding){
		m_binding = binding;
	}
	
	public IJstNode getJstBinding(){
		return m_binding;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){	
		String s = toSimpleTermText();
		return (s==null)? "":s;
	}
}
