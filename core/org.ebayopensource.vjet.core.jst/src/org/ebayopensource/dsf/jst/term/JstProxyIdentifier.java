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
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

/**
 * <p> {@link JstProxyIdentifier} is conceptually the same as {@link JstIdentifier} but with a few differences:
 * <ol>
 * <li> the proxy identifier doesn't really have a name, using "proxy" just for debugging purpose
 * </li>
 * <li> the proxy identifier serves to wrap IExpr which couldn't have a JstBinding associated
 * </li>
 * </ol>
 * the proxy identifier should have the same type as its actual expression's result type
 * the proxy identifier's binding is determined by the type linker context
 * </p>
 * 
 *
 */
public class JstProxyIdentifier extends JstIdentifier {

	private static final long serialVersionUID = 1L;
	
	private IExpr m_actualExpr;
	
	//
	// Constructor
	//
	public JstProxyIdentifier(final IExpr actualExpr){
		this(actualExpr, null);
	}
	
	public JstProxyIdentifier(final IExpr actualExpr, final JstProxyIdentifier qualifier){
		super("proxy", qualifier);
		
		setActualExpr(actualExpr);
	}
	
	public IExpr getActualExpr(){
		return m_actualExpr;
	}
	
	public void setActualExpr(final IExpr actualExpr){
		if(m_actualExpr != null){
			removeChild(m_actualExpr);
		}
		m_actualExpr = actualExpr;
		addChild(m_actualExpr);
		
		//bugfix by huzhou@ebay.com
		/**
		 * @see ParsingTests#primitiveAsProps
		 */
		if(actualExpr != null){
			if(actualExpr instanceof BaseJstNode){
				setSource(((BaseJstNode)actualExpr).getSource());
			}
			//bugfix by huzhou@ebay.com to handle new this() where "this" needs to be exposed by proxy identifier
			if(actualExpr instanceof JstIdentifier){
				setName(((JstIdentifier)actualExpr).getName());
			}
		}
		
	}
	
	// 
	// Satisfy ILHS, IParam, ITerm
	//
	public String toSimpleTermText(){
		StringBuilder sb = new StringBuilder();
		String q = null;
		if (getQualifier() != null){
			q = getQualifier().toSimpleTermText();
		}
		if (q != null){
			sb.append(q);
		}
		if (q != null && m_actualExpr != null){
			sb.append(".");
		}
		if (m_actualExpr != null){
			//sb.append(DataTypeHelper.getTypeName(m_name));
			sb.append(m_actualExpr.toExprText());
		}
		String text = sb.toString();
		return text.length() > 0 ? text : null;
	}
	
	public IJstType getType(){
		return super.getType();
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
		super.setName(name);
	}
	
	public String getName(){
		return super.getName();
	}
	
	public void setQualifier(final JstIdentifier qualifier){
		super.setQualifier(qualifier);
	}
	
	public JstIdentifier getQualifier(){
		return super.getQualifier();
	}
	
	public JstIdentifier setType(IJstType type){
		return super.setType(type);
	}
	
	public void setJstBinding(IJstNode binding){
		super.setJstBinding(binding);
	}
	
	public IJstNode getJstBinding(){
		return super.getJstBinding();
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){	
		return super.toString();
	}
}
