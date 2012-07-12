/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.meta;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.common.Z;

/**
 * Meta data definition for Java to JS method custom translation
 */
public class CustomMethod {
	
	private MethodKey m_mtdKey;
	private String m_javaName;
	private String m_jstName;
	private String m_jstOrigName;
	private String m_jstRtnTypeName;
	private boolean m_isPty;
	private String m_jstOwnerType;
	private String m_delegateType;
	private boolean m_removeQualifier = false;
	private boolean m_lookupBySignature = true;
	private CustomAttr m_attr = CustomAttr.NONE;
	private CustomType m_cType;
	
	//
	// Constructors
	//
	/**
	 * Constructor for overloaded methods
	 * @param mtdKey MethodKey
	 */
	public CustomMethod(final MethodKey mtdKey){
		this(mtdKey, mtdKey != null ? mtdKey.getName() : null);
	}
	
	/**
	 * Constructor for non-overloaded methods
	 * @param javaName String
	 */
	public CustomMethod(final String javaName){
		this(javaName, javaName);
	}
	
	/**
	 * Constructor for overloaded methods
	 * @param mtdKey MethodKey
	 * @param jstName String
	 */
	public CustomMethod(final MethodKey mtdKey, final String jstName){
		if (mtdKey == null){
			throw new DsfRuntimeException("mtdKey cannot be null");
		}
		m_mtdKey = mtdKey;
		m_javaName= m_mtdKey.getName();
		m_jstName = jstName;
	}
	
	/**
	 * Constructor for non-overloaded methods
	 * @param javaName String
	 * @param jstName String
	 */
	public CustomMethod(final String javaName, final String jstName){
		if (javaName == null){
			throw new DsfRuntimeException("javaName cannot be null");
		}
		m_mtdKey = new MethodKey(javaName);
		m_javaName= javaName;
		m_jstName = jstName;
		m_lookupBySignature = false;
	}
	
	//
	// API
	//
	/**
	 * Answer the key of this method meta
	 */
	public MethodKey getKey(){
		return m_mtdKey;
	}
	
	/**
	 * Answer Java name of the method
	 * @return String
	 */
	public String getJavaName(){
		return m_javaName;
	}
	
	/**
	 * Set JST name for the method
	 * @param jstName String
	 * @return CustomMethod
	 */
	public CustomMethod setJstName(String jstName){
		m_jstName = jstName;
		return this;
	}
	
	/**
	 * Answer the JST name for the method
	 * @return
	 */
	public String getJstName(){
		return m_jstName;
	}
	
	/**
	 * Set the original JST name of this method
	 * @param jstOrigName String
	 * @return CustomMethod
	 */
	public CustomMethod setJstOrigName(String jstOrigName) {
		m_jstOrigName = jstOrigName;
		return this;
	}
	
	/**
	 * Answer the original JST name of this method
	 * @return String
	 */
	public String getJstOrigName() {
		return m_jstOrigName;
	}

	/**
	 * Set the JST return type name of the method
	 * @param typeName String
	 * @return CustomMethod
	 */
	public CustomMethod setJstReturnTypeName(String typeName){
		m_jstRtnTypeName = typeName;
		return this;
	}
	
	/**
	 * Answer the JST return type name of the method
	 * @return String
	 */
	public String getJstReturnTypeName(){
		return m_jstRtnTypeName;
	}
	
	/**
	 * Set the method to be property in JST
	 * @return CustomMethod
	 */
	public CustomMethod setIsProperty(boolean isPty){
		m_isPty = isPty;
		return this;
	}
	
	/**
	 * Answer whether the method is property in JST
	 * @return boolean
	 */
	public boolean isProperty(){
		return m_isPty;
	}
	
	/**
	 * Set the JST owner type of the method
	 * @param jstOwnerTypeName String
	 * @return CustomMethod
	 */
	public CustomMethod setJstOwnerTypeName(String jstOwnerTypeName){
		m_jstOwnerType = jstOwnerTypeName;
		return this;
	}
	
	/**
	 * Answer the JST owner type of the method
	 * @return String
	 */
	public String getJstOwnerTypeName(){
		return m_jstOwnerType;
	}
	
	/**
	 * Set whether to remove any qualifier when invoking this method in JST
	 * @param removeQualifier boolean
	 * @return CustomMethod
	 */
	public CustomMethod removeQualifier(boolean removeQualifier){
		m_removeQualifier = removeQualifier;
		return this;
	}
	
	/**
	 * Answer whether to remove any qualifier when invoking this method in JST
	 * @return boolean
	 */
	public boolean getRemoveQualifier(){
		return m_removeQualifier;
	}
	
	/** 
	 * Set the type name this method should be delegated to
	 * @param delegateType String
	 * @return CustomMethod
	 */
	public CustomMethod setDelegateTypeName(String delegateType) {
		m_delegateType = delegateType;
		return this;
	}
	
	/** 
	 * Answer the type name this method should be delegated to
	 * @param delegateType String
	 * @return CustomMethod
	 */
	public String getDelegateTypeName() {
		return m_delegateType;
	}

	/**
	 * Set whether to look up the method by full signature
	 * @param lookupBySignature boolean
	 * @return CustomMethod
	 */
	public CustomMethod setLookupBySignature(boolean lookupBySignature){
		m_lookupBySignature = lookupBySignature;
		return this;
	}
	
	/**
	 * Answer whether to look up the method by full signature
	 * @return boolean
	 */
	public boolean isLookupBySignature(){
		return m_lookupBySignature;
	}
	
	/**
	 * Set custom attribute for this method
	 * @param attr CustomAttr
	 * @return CustomMethod
	 */
	public CustomMethod setAttr(CustomAttr attr){
		if (attr == null){
			m_attr = CustomAttr.NONE;
		}
		else {
			m_attr = attr;
		}
		return this;
	}
	
	/**
	 * Answer the custom attribute for this method
	 * @return CustomAttr
	 */
	public CustomAttr getAttr(){
		if (m_attr != null){
			return m_attr;
		}
		return CustomAttr.NONE;
	}
	
	/**
	 * Set the type meta of this method
	 * @param cType CustomType
	 * @return CustomField
	 */
	public CustomMethod setCustomType(final CustomType cType){
		m_cType = cType;
		return this;
	}
	
	/**
	 * Answer the type meta of this method
	 * @return CustomType
	 */
	public CustomType getCustomType(){
		return m_cType;
	}

	@Override
	public String toString(){
		Z z = new Z();
		z.format("m_mtdKey", m_mtdKey);
		z.format("m_jstName", m_jstName);
		z.format("m_jstOrigName", m_jstOrigName);
		z.format("m_jstRtnTypeName", m_jstRtnTypeName);
		z.format("m_isPty", m_isPty);
		z.format("m_jstOwnerType", m_jstOwnerType);
		z.format("m_delegateType", m_delegateType);
		z.format("m_removeQualifier", m_removeQualifier);
		z.format("m_lookupBySignature", m_lookupBySignature);
		z.format("m_attr", m_attr);
		z.format("m_cType", m_cType == null ? null : m_cType.getJavaName());
		return z.toString();
	}
}