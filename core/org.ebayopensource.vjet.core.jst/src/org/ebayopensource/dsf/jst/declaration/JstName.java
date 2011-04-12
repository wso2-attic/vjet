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
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.JstSource.IBinding;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstName extends BaseJstNode {
	
	private static final long serialVersionUID = 1L;
	
	private String m_name;
	
	//
	// Constructor
	//
	public JstName(final String name){
		m_name = name;
	}
	
	public JstName(final String name, final IBinding srcBinding){
		this(name);
		setSource(new JstSource(srcBinding));
	}
	
	//
	// API
	//
	public void setName(String name){
		m_name = name;
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
		return m_name;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != getClass()){
			return false;
		}
		JstName other = (JstName)obj;
		if (m_name == null){
			return other.m_name == null;
		}
		return m_name.equals(other.m_name);
	}
	
	@Override
	public int hashCode(){
		return m_name == null ? 0 : m_name.hashCode();
	}
}
