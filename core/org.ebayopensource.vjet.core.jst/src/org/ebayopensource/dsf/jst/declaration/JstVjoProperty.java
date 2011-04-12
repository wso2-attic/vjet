/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;

public class JstVjoProperty extends JstSynthesizedProperty {
	private static final long serialVersionUID = 1L;
	
	private transient JstType m_type;
	private boolean m_isInstance;
	private IJstType m_ownerType;
	
	//
	// Constructors
	//
	public JstVjoProperty(final String name, final IJstType ownerType) {
		this(name, ownerType, false);
	}

	public JstVjoProperty(final String name, final IJstType ownerType, final boolean isInstance) {
		super(null, name, (JstIdentifier)null, new JstModifiers());
		m_ownerType = ownerType;
		m_isInstance = isInstance;
	}

	/**
	 * @see IJstProperty#getType()
	 */
	public synchronized IJstType getType() {
		if (m_type == null) {
			m_type = JstFactory.getInstance().createJstType("Vj$Type", false);
			
			//Gather properties
			List<IJstProperty> list = new ArrayList<IJstProperty>();
			IJstRefType typeRef = getTypeRef(m_ownerType);
			list.add(new JstProperty(typeRef, "type"));
			if (!m_ownerType.isMixin() 
					&& !m_ownerType.isEmbededType()) {
				list.add(new JstProperty(typeRef, m_ownerType.getSimpleName()));
			}
			addPropertiesFromType(m_ownerType, list);
			if (m_ownerType.isEmbededType()) {
				IJstType outerType = m_ownerType.getOuterType();
				list.add(new JstProperty(getTypeRef(outerType), outerType.getSimpleName()));
				addPropertiesFromType(outerType, list);
			}
			if (m_isInstance 
					&& m_ownerType.isEmbededType() 
					&& !m_ownerType.isMixin() 
					&& !m_ownerType.getModifiers().isStatic()) {
				list.add(new JstProperty(m_ownerType.getOuterType(), "outer"));
			}
			
			addAllProperties(m_type, list);
		} 
		
		return m_type;
	}

	private void addAllProperties(JstType type, List<IJstProperty> list) {
		for (IJstProperty p : list) {
			p.getModifiers().setPublic();
			type.addProperty(p);
		}
	}

	private void addPropertiesFromType(IJstType type, List<IJstProperty> list) {
		for (Entry<String, ? extends IJstType> itm 
				: type.getImportsMap().entrySet()) {
			//TODO - is there a better way to do this?
			//Do not add full name imports
			if (itm.getKey().equals(itm.getValue().getName())) {
				continue;
			}
			
			if (!propertyExists(list, itm.getKey())) {
				list.add(new JstProperty(getTypeRef(itm.getValue()), itm.getKey()));
			}
		}
		
		if (!type.isMixin()) {
			for (IJstType itm : type.getExtends()) {
				//Don't add default extends vjo.Object
				if ("vjo.Object".equals(itm.getName())) {
					continue;
				}
				if (!propertyExists(list, itm.getSimpleName())) {
					list.add(new JstProperty(getTypeRef(itm), itm.getSimpleName()));
				}
			}
		}
		for (IJstType itm : type.getSatisfies()) {
			if (!propertyExists(list, itm.getSimpleName())) {
				list.add(new JstProperty(getTypeRef(itm), itm.getSimpleName()));
			}
		}
	}
	
	private boolean propertyExists(List<IJstProperty> list, String name) {
		if(name==null){
			return false;
		}
		for (IJstProperty itm : list) {
			if (name.equals(itm.getName().getName())) {
				return true;
			}
		}
		return false;
	}
	
	private IJstRefType getTypeRef(IJstType type) {
		if (type instanceof IJstRefType) {
			return (IJstRefType)type;
		}
		return new JstTypeRefType(type);
	}
}
