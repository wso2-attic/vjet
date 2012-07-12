/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.meta;

import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.common.Z;

/**
 * Meta data definition for Java to JS field custom translation
 */
public class CustomField {
	
	private String m_javaName;
	private String m_jstName;
	private String m_jstTypeName;
	private String m_jstOwnerTypeName;
	private CustomAttr m_attr = CustomAttr.NONE;
	private CustomType m_cType;
	
	//
	// Constructors
	//
	/**
	 * Constructor
	 * @param javaName String
	 */
	public CustomField(final String javaName){
		m_javaName = javaName;
	}

	//
	// API
	//
	/**
	 * Answer the java name of this field
	 * @return String
	 */
	public String getJavaName(){
		return m_javaName;
	}
	
	/**
	 * Set JST name for this field
	 * @param jstName String
	 * @return CustomField
	 */
	public CustomField setJstName(final String jstName){
		m_jstName = jstName;
		return this;
	}
	
	/**
	 * Answer the JST name of this field
	 * @return String
	 */
	public String getJstName(){
		return m_jstName;
	}
	
	/**
	 * Set the JST type name of this field
	 * @param jstTypeName String
	 * @return CustomField
	 */
	public CustomField setJstTypeName(final String jstTypeName){
		m_jstTypeName = jstTypeName;
		return this;
	}
	
	/**
	 * Answer the JST type name of this field
	 * @return String
	 */
	public String getJstTypeName(){
		return m_jstTypeName;
	}
	
	/**
	 * Set JST owner type name for this field
	 * @param jstOwnerTypeName String
	 * @return CustomField
	 */
	public CustomField setJstOwnerTypeName(final String jstOwnerTypeName){
		m_jstOwnerTypeName = jstOwnerTypeName;
		return this;
	}
	
	/**
	 * Answer JST owner type name for this field
	 * @return String
	 */
	public String getJstOwnerTypeName(){
		return m_jstOwnerTypeName;
	}
	
	/**
	 * Set custom attribute for this field
	 * @param attr CustomAttr
	 * @return CustomField
	 */
	public CustomField setAttr(CustomAttr attr){
		if (attr == null){
			m_attr = CustomAttr.NONE;
		}
		else {
			m_attr = attr;
		}
		return this;
	}
	
	/**
	 * Answer custom attribute of this field
	 * @return CustomAttr
	 */
	public CustomAttr getAttr(){
		if (m_attr != null){
			return m_attr;
		}
		return CustomAttr.NONE;
	}
	
	/**
	 * Set the type meta of this field
	 * @param cType CustomType
	 * @return CustomField
	 */
	public CustomField setCustomType(final CustomType cType){
		m_cType = cType;
		return this;
	}
	
	/**
	 * Answer the type meta of this field
	 * @return CustomType
	 */
	public CustomType getCustomType(){
		return m_cType;
	}

	@Override
	public String toString(){
		Z z = new Z();
		z.format("m_jstFieldTypeName", m_jstTypeName);
		z.format("m_javaName", m_javaName);
		z.format("m_jstName", m_jstName);
		z.format("m_attr", m_attr);
		z.format("m_jstOwnerTypeName", m_jstOwnerTypeName);
		z.format("m_cType", m_cType == null ? null : m_cType.getJavaName());
		return z.toString();
	}
}
