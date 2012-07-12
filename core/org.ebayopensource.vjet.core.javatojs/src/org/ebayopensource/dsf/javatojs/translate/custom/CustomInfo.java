/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.common.Z;

public class CustomInfo {
	
	public static final CustomInfo NONE = new CustomInfo(CustomAttr.NONE);

	private CustomAttr m_attr = CustomAttr.NONE;
	private String m_name;
	private IJstNode m_jstNode;
	private boolean m_inheritable=true;
	private boolean m_forceFullyQualify = false;
	private IJstType m_asType;
	private String m_asName;

	//
	// Constructor
	//
	public CustomInfo() {
	}
	
	public CustomInfo(CustomAttr attr) {
		setAttr(attr);
	}
	
	public CustomInfo(CustomAttr attr, IJstNode jstNode) {
		this(attr);
		m_jstNode = jstNode;
	}
	
	public CustomInfo(String name) {
		m_name = name;
	}
	
	public CustomInfo(CustomInfo copy) {
		CustomInfo.update(this, copy);
	}

	//
	// API
	//
	public boolean isExcluded() {
		return m_attr == CustomAttr.EXCLUDED;
	}

	public boolean isJavaOnly() {
		return m_attr == CustomAttr.JAVA_ONLY;
	}
	
	public boolean isJSProxy() {
		return m_attr == CustomAttr.JS_PROXY;
	}

	public boolean isMappedToJS() {
		return m_attr == CustomAttr.MAPPED_TO_JS;
	}
	
	public boolean isMappedToVJO() {
		return m_attr == CustomAttr.MAPPED_TO_VJO;
	}
	
	public boolean isNone() {
		return m_attr == CustomAttr.NONE;
	}
	
	public CustomAttr getAttr(){
		return m_attr;
	}
	
	public void setAttr(CustomAttr attr){
		if (this == NONE){
			throw new RuntimeException("singleton NONE is read-only");
		}
		if (attr == null) {
			m_attr = CustomAttr.NONE;
		} else {
			m_attr = attr;
		}
	}
	
	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		m_name = name;
	}
	
	public void setInheritable(boolean inheritable){
		m_inheritable = inheritable;
	}
	
	public boolean isInheritable(){
		return m_inheritable;
	}
	
	public boolean isForceFullyQualify() {
		return m_forceFullyQualify;
	}

	public void setForceFullyQualify(boolean forceFullyQualify) {
		m_forceFullyQualify = forceFullyQualify;
	}
	
	public IJstNode getJstNode(){
		return m_jstNode;
	}
	
	public void setJstNode(IJstNode jstNode){
		if (this == NONE){
			throw new RuntimeException("singleton NONE is read-only");
		}
		m_jstNode = jstNode;
	}

	/**
	 * Update oldInfo with newInfo. Only non-default value will be taken.
	 * @param oldInfo CustomInfo
	 * @param newInfo CustomInfo
	 * @return CustomInfo
	 */
	public static CustomInfo update(final CustomInfo oldInfo, final CustomInfo newInfo){
		if (newInfo == null || newInfo == CustomInfo.NONE){
			return oldInfo;
		}
		CustomInfo cInfo = oldInfo;
		if (cInfo == null || cInfo == NONE){
			cInfo = new CustomInfo();
		}
		if (newInfo.m_attr != CustomAttr.NONE){
			cInfo.m_attr = newInfo.m_attr;
		}
		if (newInfo.m_name != null){
			cInfo.m_name = newInfo.m_name;
		}
		cInfo.m_inheritable = newInfo.m_inheritable;
		cInfo.m_forceFullyQualify = newInfo.m_forceFullyQualify;
		cInfo.m_asType = newInfo.m_asType;
		cInfo.m_asName = newInfo.m_asName;
		return cInfo;
	}

	@Override
	public String toString(){
		Z z = new Z();
		z.format("attr", m_attr.name());
		z.format("name", m_name);
		if (m_jstNode != null){
			z.format("nodeType", m_jstNode.getClass().getSimpleName());
		}
		z.format("m_inheritable", m_inheritable);
		z.format("m_forceFullyQualify", m_forceFullyQualify);
		return z.toString();
	}
	
	public IJstType getAsType() {
		return m_asType;
	}

	public void setAsType(IJstType asType) {
		m_asType = asType;
	}

	public String getAsName() {
		return m_asName;
	}

	public void setAsName(String asName) {
		m_asName = asName;
	}
}
