/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.io.ObjectStreamException;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;




@Deprecated
public final class JstRefType extends JstType {
	
	private static final long serialVersionUID = 1L;
	
	private final Class m_javaType;
	private final boolean m_isPrimitive;
	private final boolean m_isArray;
	private int m_dimensions;
	private boolean m_useNameFromConstructor;
	

	JstRefType(final Class refType, final String name, final boolean primitive, final boolean isArray) {
		int index = name.lastIndexOf(".");
		if (index != -1) {
			setPackage(JstFactory.getInstance().createPackage(name.substring(0, index)));
			setSimpleName(name.substring(index+1));
		} else {
			setSimpleName(name);
		}
		m_javaType = refType;
		m_isPrimitive = primitive;
		m_isArray = isArray;
		if (isArray) {
			m_dimensions = 1;
		}
		getModifiers().setPublic();
	}
	
	JstRefType(final JstPackage pkg, final String name, final boolean primitive, final boolean isArray) {
		super(pkg, name);
		m_javaType = null;
		m_isPrimitive = primitive;
		m_isArray = isArray;
		if (isArray) {
			m_dimensions = 1;
		} 
		getModifiers().setPublic();
	}
	
	JstRefType(final String name, final boolean primitive, final boolean isArray) {
		int index = name.lastIndexOf(".");
		if (index != -1) {
			setPackage(JstFactory.getInstance().createPackage(name.substring(0, index)));
			setSimpleName(name.substring(index+1));
		} else {
			setSimpleName(name);
		}
		
		m_javaType = null;
		m_isPrimitive = primitive;
		m_isArray = isArray;
		if (isArray) {
			m_dimensions = 1;
		}
		getModifiers().setPublic();
	}
	
	public boolean isPrimitive(){
		return m_isPrimitive;
	}
	
	public boolean isArray() {
		return m_isArray;
	}
	
	public Class getJavaType() {
		return m_javaType;
	}
	
	public Class getRefType() {
		if (m_javaType == null) {
			return null;
		}
		
		if (m_javaType == boolean.class){
			return Boolean.class;
		}
		else if (m_javaType == int.class){
			return Integer.class;
		}
		else if (m_javaType == long.class){
			return Long.class;
		}
		else if (m_javaType == float.class){
			return Float.class;
		}
		else if (m_javaType == double.class){
			return Double.class;
		}
		else if (m_javaType == short.class){
			return Short.class;
		}
		else if (m_javaType == byte.class){
			return Byte.class;
		}
		else if (m_javaType == char.class){
			return Character.class;
		}
		return m_javaType;
	}

	/**
	 * Alias for getSimpleName
	 * @return
	 * @see JstRefType#getSimpleName()
	 */
	public String getShortName(){
		return getSimpleName();
	}
	
//	@Override
//	public String getSimpleName() {
//		String name;
//		if (m_isPrimitive) {
//			name =  super.getSimpleName();//getPrimitiveName();
//		} else {
//			name = super.getSimpleName();
//		}
////		if (m_isArray) {
////			for (int i = 0; i < m_dimensions; i++) {
////				name += "[]";
////			}
////		}
//		return name;
//	}

//	@Override
//	public String getName() {
//		String name;
//		if (m_isPrimitive) {
//			name =  getPrimitiveName();
//		} else {
//			name = super.getName();
//		}
////		if (m_isArray) {
////			for (int i = 0; i < m_dimensions; i++) {
////				name += "[]";
////			}
////		}
//		return name;
//	}
	
	public int getDimensions() {
		return m_dimensions;
	}

	public void setDimensions(int dimensions) {
		m_dimensions = dimensions;
	}
	
//	public String getDefaultValue(){
//		
//		if (m_javaType == null){
//			return null;
//		}
//		
//		if (isArray()){
//			return null;
//		}
//		
//		if (m_javaType == Boolean.class
//			|| m_javaType == boolean.class){
//			return "false";
//		}
//		else if (m_javaType == Integer.class
//			|| m_javaType == int.class){
//			return "0";
//		}
//		else if (m_javaType == Long.class
//			|| m_javaType == long.class){
//			return "0";
//		}
//		else if (m_javaType == Float.class
//			|| m_javaType == float.class){
//			return "0";
//		}
//		else if (m_javaType == Double.class
//			|| m_javaType == double.class){
//			return "0";
//		}
//		else if (m_javaType == Short.class
//			|| m_javaType == short.class){
//			return "0";
//		}
//		else if (m_javaType == Byte.class
//			|| m_javaType == byte.class){
//			return "0";
//		}
//
//		return null;
//	}
	
//	private String getPrimitiveName() {
////		String jName = null;
////		if (m_refType == Boolean.class){
////			jName = "boolean";
////		}
////		else if (m_refType == Integer.class){
////			jName = "int";
////		}
////		else if (m_refType == Long.class){
////			jName = "long";
////		}
////		else if (m_refType == Float.class){
////			jName = "float";
////		}
////		else if (m_refType == Double.class){
////			jName = "double";
////		}
////		else if (m_refType == Short.class){
////			jName = "short";
////		}
////		else if (m_refType == Byte.class){
////			jName = "byte";
////		}
////		else if (m_refType == Character.class){
////			jName = "char";
////		}
//		return getSimpleName();
//	}

	public boolean isUseNameFromConstructor() {
		return m_useNameFromConstructor;
	}

	public void setUseNameFromConstructor(boolean useNameFromConstructor) {
		m_useNameFromConstructor = useNameFromConstructor;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	private Object readResolve() throws ObjectStreamException{
		IJstType type = JstCache.getInstance().getType(getName());
		if(type!=null){
			return type;
		}
		return this;
	}
}
