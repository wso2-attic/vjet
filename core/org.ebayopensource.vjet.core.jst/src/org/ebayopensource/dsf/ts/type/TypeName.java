/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.type;


public class TypeName {
	private String m_groupName;
	private String m_typeName;
	
	public TypeName(String groupName, String typeName){
		if (typeName == null){
			throw new AssertionError("typeName is null");
		}
		m_groupName = groupName;
		m_typeName = typeName;
	}
	
	public String groupName(){
		return m_groupName;
	}
	
	public String typeName(){
		return m_typeName;
	}
	
	@Override
	public String toString(){
		//Z z = new Z();
		//z.format("m_groupName", m_groupName);
		//z.format("m_typeName", m_typeName);
		//return z.toString();
		return "[TypeName group="+m_groupName+" type="+m_typeName+"]";
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != getClass()){
			return false;
		}
		TypeName that = (TypeName)obj;
		if (m_groupName == null){
			return that.m_groupName == null && that.m_typeName.equals(m_typeName);
		}
		return that.m_groupName.equals(m_groupName) && that.m_typeName.equals(m_typeName);
	}
	
	@Override
	public int hashCode(){
		int code = m_typeName.hashCode();
		if (m_groupName != null){
			code += m_groupName.hashCode();
		}
		return code; 
	}
}
