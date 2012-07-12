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
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;
import org.ebayopensource.dsf.common.Z;

public class JstPackage extends BaseJstNode {
	
	private static final long serialVersionUID = 1L;
	
	private String m_name = "";
	private String m_groupName = "";

	//
	// Constructor
	//
	public JstPackage(){
	}
	
	public JstPackage(String name){
		m_name = name;
	}
	
	//
	// API
	//
	public String getName(){
		return m_name;
	}
	
	public void setGroupName(final String groupName){
		m_groupName = groupName;
	}
	
	public String getGroupName(){
		return m_groupName;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("m_name", m_name);
		z.format("m_groupName", m_groupName);
		return z.toString();
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != getClass()){
			return false;
		}
		
		JstPackage that = (JstPackage)obj;
		
		if (m_groupName == null){
			if (that.m_groupName != null){
				return false;
			}
			else {
				if (m_name == null){
					return that.m_name == null;
				}
				else {
					return m_name.equals(that.m_name);
				}
			}
		}
		else {
			if (!m_groupName.equals(that.m_groupName)){
				return false;
			}
			else {
				if (m_name == null){
					return that.m_name == null;
				}
				else {
					return m_name.equals(that.m_name);
				}
			}
		}
	}
	
	@Override 
	public int hashCode(){
		int code = 0;
		if (m_groupName != null){
			code += m_groupName.hashCode();
		}
		if (m_name != null){
			code += m_name.hashCode();
		}
		return code;
	}
}
