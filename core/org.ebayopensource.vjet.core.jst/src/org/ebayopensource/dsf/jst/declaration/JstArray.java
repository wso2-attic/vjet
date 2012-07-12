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

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;
import org.ebayopensource.dsf.common.Z;


public class JstArray extends JstType {
	
	private static final long serialVersionUID = 1L;
	
	private IJstType m_componentType;
	
	//
	// Constructor
	//
	/**
	 * Constructor
	 * @param componentType JstType
	 */
	public JstArray(IJstType componentType) {
		m_componentType = componentType;
		// must extend js native array
		addExtend(JstCache.getInstance().getType("Array"));
	}
	
	@Override
	public String getSimpleName() {
		String name = getElementType().getSimpleName();
		int dimensions = getDimensions();
		for (int i = 0; i < dimensions; i++) {
			name += "[]";
		}
		return name;
	}

	@Override
	public List<IJstMethod> getMethods(boolean isStatic, boolean recursive) {
		if(m_componentType instanceof JstMixedType){
			List<IJstMethod> mtds = new ArrayList<IJstMethod>();
			JstMixedType mt = (JstMixedType)m_componentType;
			for(IJstType type: mt.m_types){
				mtds.addAll(type.getMethods(isStatic, recursive));
			}
			return mtds;
			
		}
		return super.getMethods(isStatic, recursive);
	}
	
	@Override
	public IJstMethod getMethod(String name, boolean isStatic, boolean recursive) {
		if(m_componentType instanceof JstMixedType){
			
		}
		return super.getMethod(name, isStatic, recursive);
	}
	
	@Override
	public String getName() {
		String name = getElementType().getName();
		int dimensions = getDimensions();
		for (int i = 0; i < dimensions; i++) {
			name += "[]";
		}
		return name;
	}
	
	//
	// API
	//
	public int getDimensions() {
		IJstType type = getComponentType();
		int dimensions = 1; 
		while (type != null && type instanceof  JstArray) {
			dimensions++;
			type = ((JstArray) type).getComponentType();
		}
		return dimensions;
	}

	/**
	 * Returns the element JstType of this JstArray type. The element JstType is
	 * never a JstArray type.
	 * <p>
	 * This is a convenience method that descends a chain of nested JstArray types
	 * until it reaches a non-array type. 
	 * </p>
	 * 
	 * @return JstType
	 */ 
	public IJstType getElementType() {
		IJstType type = getComponentType();
		while (type != null && type instanceof  JstArray) {
			type = ((JstArray) type).getComponentType();
		}
		return type;
	}

	/**
	 * Returns the component type of this JstArray type. The component type
	 * may be another JstArray type.
	 * 
	 * @return the component JstType
	 */
	public IJstType getComponentType() {
		return m_componentType;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		Z z = new Z();
		z.format("m_componentType", m_componentType);
		return z.toString();
	}
}
