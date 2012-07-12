/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.meta;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.common.Z;

/**
 * Meta data definition for Java to JS Custom Translation
 */
public class CustomType {

	private Class<?> m_javaType;
	private String m_javaName;
	private String m_jstName;
	private Map<String,CustomField> m_fldMapping = 
		new HashMap<String,CustomField>(5);
	private Map<String,Map<MethodKey,CustomMethod>> m_mtdMapping = 
		new HashMap<String,Map<MethodKey,CustomMethod>>(5);
	private boolean m_removeTypeQualifier = false;
	private CustomAttr m_attr = CustomAttr.NONE;
	
	//
	// Constructors
	//
	/**
	 * Constructor
	 * @param javaType Class required not null
	 */
	public CustomType(final Class<?> javaType){
		assert javaType != null : "javaType cannot be null";
		m_javaType = javaType;		
		m_javaName = javaType.getName();
		m_jstName = m_javaName;
	}
	
	/**
	 * Constructor
	 * @param javaName String required not null
	 */
	public CustomType(final String javaName){
		assert javaName != null : "javaName cannot be null";
		m_javaName = javaName;
		m_jstName = javaName;
		try {
			m_javaType = Class.forName(
				javaName, false, this.getClass().getClassLoader());
		}
		catch (ClassNotFoundException e) {
//			String msg = "Could not load class for " + javaName 
//				+ " during CustomType construction\n" ;
//			throw new DsfRuntimeException(msg, e) ;TODO 
		}
	}
	
	/**
	 * Constructor
	 * @param javaType Class required not null
	 * @param jstName String required not null
	 */
	public CustomType(final Class<?> javaType, final String jstName){
		this(javaType);
		assert jstName != null : "jstTypeName cannot be null";	
		m_jstName = jstName;		
	}
	
	/**
	 * Constructor
	 * @param javaName String required not null
	 * @param jstName String required not null
	 */
	public CustomType(final String javaName, final String jstName){
		this(javaName);
		assert jstName != null : "jstName cannot be null";
		m_jstName = jstName;
		
	}
	
	//
	// API
	//
	/**
	 * Answer the java class of this type
	 * @return Class
	 */
	public Class<?> getJavaType(){
		return m_javaType;
	}
	
	/**
	 * Answer the Java name of this type
	 * @return String
	 */
	public String getJavaName(){
		return m_javaName!=null ? m_javaName : m_javaType.getName();
	}

	/**
	 * Set the type name in JST
	 * @param typeName String
	 */
	public CustomType setJstName(final String typeName) {
		m_jstName = typeName;
		return this;
	}
	
	/**
	 * Answer the type name in JST
	 * @return String
	 */
	public String getJstName(){
		return m_jstName;
	}
	
	/**
	 * Set whether to remove the type qualifier in JST
	 * @param removeTypeQualifier boolean
	 * @return CustomType
	 */
	public CustomType setRemoveTypeQualifier(boolean removeTypeQualifier) {
		
		m_removeTypeQualifier = removeTypeQualifier;
		return this;
	}
	
	/**
	 * Answer whether to remove the type qualifier in JST
	 * @return boolean
	 */
	public boolean removeTypeQualifier() {
		return m_removeTypeQualifier;
	}
	
	/**
	 * Set the custom attribute of this type
	 * @param attr CustomAttr
	 * @return CustomType
	 */
	public CustomType setAttr(final CustomAttr attr){
		if (attr == null){
			m_attr = CustomAttr.NONE;
		}
		else {
			m_attr = attr;
		}
		return this;
	}
	
	/**
	 * Answer the custom attribute of this type.
	 * @return CustomAttr
	 */
	public CustomAttr getAttr(){
		if (m_attr != null){
			return m_attr;
		}
		return CustomAttr.NONE;
	}
	
	/**
	 * Answer the custom field with given java field name
	 * @param javaFieldName String
	 * @return CustomField
	 */
	public CustomField getCustomField(final String javaFieldName){
		return m_fldMapping.get(javaFieldName);
	}
	
	/**
	 * Answer all custom fields of this type	
	 * @return Collection<CustomField> 
	 */
	public Collection<CustomField> getAllCustomFields(){	
		return m_fldMapping.values();		
	}
	
	/**
	 * Add meta data for field custom translation
	 * @param cField CustomField
	 * @return CustomType
	 */
	public CustomType addCustomField(final CustomField cField){
		if (cField != null){
			m_fldMapping.put(cField.getJavaName(), cField);
			cField.setCustomType(this);
		}
		return this;
	}
	
	/**
	 * Add meta data for field custom translation
	 * @param javaName String
	 * @param jstName String
	 * @param jstTypeName String
	 * @return CustomType
	 */
	public CustomType addCustomField(
			final String javaName, 
			final String jstName,
			final String jstTypeName){
		
		return addCustomField(
				new CustomField(javaName)
					.setJstName(jstName)
					.setJstTypeName(jstTypeName));
	}
	
	/**
	 * Add meta data for field custom translation
	 * @param javaName String
	 * @param jstName String
	 * @param jstTypeName String
	 * @param jstOwnerTypeName String
	 * @return CustomType
	 */
	public CustomType addCustomField(
			final String javaName, 
			final String jstName, 
			final String jstTypeName, 
			final String jstOwnerTypeName){

		return addCustomField(
				new CustomField(javaName)
					.setJstName(jstName)
					.setJstTypeName(jstTypeName)
					.setJstOwnerTypeName(jstOwnerTypeName));
	}
	
	/**
	 * Answer method meta data with given method key
	 * @param mtdKey MethodKey
	 * @return CustomMethod
	 */
	public CustomMethod getCustomMethod(final MethodKey mtdKey){
		if (mtdKey == null){
			return null;
		}
		Map<MethodKey,CustomMethod> map = m_mtdMapping.get(mtdKey.getName());
		if (map == null){
			return null;
		}
		return map.get(mtdKey);
	}
	
	/**
	 * Answer custom methods with given method name
	 * @param javaName String
	 * @return Collection<CustomMethod> 
	 */
	public Collection<CustomMethod> getCustomMethods(final String javaName){	
		Map<MethodKey,CustomMethod> map = m_mtdMapping.get(javaName);	
		if (map == null){
			return Collections.emptySet();
		}
		return map.values();
	}
	
	/**
	 * Answer all custom methods of this type
	 * @return Map<String,Map<MethodKey,CustomMethod>> 
	 */
	public Map<String,Map<MethodKey,CustomMethod>> getAllCustomMethods(){	
		return Collections.unmodifiableMap(m_mtdMapping);		
	}

	/**
	 * Add meta data for method custom translation
	 * @param cMethod CustomMethod
	 * @return CustomType
	 */
	public CustomType addCustomMethod(final CustomMethod cMethod){
		if (cMethod != null){
			final MethodKey mtdKey = cMethod.getKey();
			String mtdName = mtdKey.getName();
			Map<MethodKey,CustomMethod> map = m_mtdMapping.get(mtdName);
			if (map == null){
				map = new HashMap<MethodKey,CustomMethod>();
				m_mtdMapping.put(mtdName, map);
			}
			map.put(mtdKey, cMethod);
			cMethod.setCustomType(this);
		}
		return this;
	}
	
	/**
	 * Add meta data for method custom translation
	 * @param javaName String
	 * @param jstName String
	 * @return CustomType
	 */
	public CustomType addCustomMethod(
			final String javaName, 
			final String jstName){
		
		return addCustomMethod(new CustomMethod(javaName, jstName));
	}
	
	/**
	 * Add meta data for method custom translation
	 * @param javaName String 
	 * @param jstName String
	 * @param isProperty boolean
	 * @return CustomType
	 */
	public CustomType addCustomMethod(
			final String javaName, 
			final String jstName, 
			final boolean isProperty){

		return addCustomMethod(new CustomMethod(javaName, jstName).setIsProperty(isProperty));
	}
	
	/**
	 * Add meta data for method custom translation
	 * @param javaName String
	 * @param jstName String
	 * @param jstOwnerTypeName String
	 * @return CustomType
	 */
	public CustomType addCustomMethod(
			final String javaName, 
			final String jstName, 
			final String jstOwnerTypeName){
		
		return addCustomMethod(new CustomMethod(javaName, jstName).setJstOwnerTypeName(jstOwnerTypeName));
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("m_javaName", m_javaName);
		z.format("m_jstName", m_jstName);
		z.format("m_removeTypeQualifier", m_removeTypeQualifier);
		z.format("m_attr", m_attr);
		
		z.format("m_fldMapping:");
		for (Entry<String,CustomField> entry: m_fldMapping.entrySet()){
			z.format("\t" + entry.getKey() + "->" + entry.getValue().getJstName());
		}
		
		String mtd;
		z.format("m_mtdMapping:");
		for (Entry<String,Map<MethodKey,CustomMethod>> mapEntry: m_mtdMapping.entrySet()){
			for (Entry<MethodKey,CustomMethod> entry: mapEntry.getValue().entrySet()){
				mtd = "\t" + entry.getKey() + "->" + entry.getValue().getJstName();
				if (entry.getValue().isProperty()){
					mtd += " (property)";
				}
				z.format(mtd);
			}
		}
		return z.toString();
	}
}
