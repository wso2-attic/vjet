/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.method;

import org.ebayopensource.dsf.ts.type.ISymbolName;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * Immutable, scoped name for method
 * 
 * 
 */
public final class MethodName implements ISymbolName {
	
	private final TypeName m_typeName;
	private final String m_mtdName;
	
	public MethodName(TypeName typeName, String mtdName){
		assert typeName != null : "typeName is null";
		assert mtdName != null : "mtdName is null";
		m_typeName = typeName;
		m_mtdName = mtdName;
	}
	
	public TypeName typeName(){
		return m_typeName;
	}
	
	public String methodName(){
		return m_mtdName;
	}
	
	@Override
	public String toString(){
		//Z z = new Z();
		//z.format("m_typeName", m_typeName);
		//z.format("m_mtdName", m_mtdName);
		//return z.toString();
		return "[MethodName "+m_typeName+" mtd="+m_mtdName+"]";
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != getClass()){
			return false;
		}
		MethodName that = (MethodName)obj;
		return that.m_typeName.equals(m_typeName) && that.m_mtdName.equals(m_mtdName);
	}
	
	@Override
	public int hashCode(){
		return m_typeName.hashCode() + m_mtdName.hashCode();
	}

	public String getGroupName() {
		return m_typeName.groupName();
	}

	public String getLocalName() {		
		return m_mtdName;
	}

	public String getOwnerTypeName() {
		return m_typeName.typeName();
	}
}
