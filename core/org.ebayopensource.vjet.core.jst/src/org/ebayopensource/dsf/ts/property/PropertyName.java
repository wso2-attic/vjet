/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.property;

import org.ebayopensource.dsf.ts.type.ISymbolName;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.dsf.common.Z;

/**
 * Immutable, scoped name for property
 * 
 * 
 */
public final class PropertyName implements ISymbolName {
	
	private final TypeName m_typeName;
	private final String m_ptyName;
	
	public PropertyName(TypeName typeName, String ptyName){
		assert typeName != null : "typeName is null";
		assert ptyName != null : "ptyName is null";
		m_typeName = typeName;
		m_ptyName = ptyName;
	}
	
	public TypeName typeName(){
		return m_typeName;
	}
	
	public String propertyName(){
		return m_ptyName;
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("m_typeName", m_typeName);
		z.format("m_ptyName", m_ptyName);
		return z.toString();
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != getClass()){
			return false;
		}
		PropertyName that = (PropertyName)obj;
		return that.m_typeName.equals(m_typeName) && that.m_ptyName.equals(m_ptyName);
	}
	
	@Override
	public int hashCode(){
		return m_typeName.hashCode() + m_ptyName.hashCode();
	}
	
	public String getGroupName() {
		return m_typeName.groupName();
	}

	public String getLocalName() {		
		return m_ptyName;
	}

	public String getOwnerTypeName() {
		return m_typeName.typeName();
	}
}
