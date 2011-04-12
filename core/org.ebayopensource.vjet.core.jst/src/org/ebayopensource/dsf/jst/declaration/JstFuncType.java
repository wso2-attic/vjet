/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstFuncType extends JstProxyType {

	private static final long serialVersionUID = 1L;
	
	private IJstMethod m_function;

	private final JstType m_functionExtType;
	
	/**
	 * 
	 * @param funcType this should always be "Function" type
	 * @param function
	 */
	public JstFuncType(final IJstMethod function) {
		super(JstCache.getInstance().getType("Function", true));
		
		assert function != null;

		m_function = function;
		m_functionExtType = new JstType("FunctionProxy");//bugfix by huzhou@ebay.com, must give proxy ext type a name
		m_functionExtType.addExtend(JstCache.getInstance().getType("Function"));
	}
	
	public void setFunction(final IJstMethod function){
		m_function =  function;
	}
	

	/**
	 * Add given type as base type
	 * @param extend IJstType
	 * @exception Runtime exception if the type is not an interface but
	 * the type already has a base type
	 */
	public void addExtend(final IJstType extend) {
		addExtend(new JstTypeReference(extend));
	}
	
	/**
	 * Add given type as base type
	 * @param extend IJstTypeReference
	 * @exception Runtime exception if the type is not an interface but
	 * the type already has a base type
	 */
	public void addExtend(final IJstTypeReference extend) {
		m_functionExtType.addExtend(extend);
	}
	
	/**
	 * @see IJstType#getExtend()
	 */
	public IJstType getExtend() {
		return m_functionExtType.getExtend();
	}
	
	/**
	 * Remove the extend with given name
	 * @param extend IJstType
	 * @return JstType the removed extended type
	 */
	public IJstType removeExtend(final IJstType extend) {
		return m_functionExtType.removeExtend(extend);
	}

	/**
	 * Remove all extends from the type
	 */
	public void clearExtends() {
		m_functionExtType.clearExtends();
	}
	
	/**
	 * @see IJstType#getProperty(String)
	 */
	public IJstProperty getProperty(final String name) {
		return getProperty(name, false);
	}

	/**
	 * @see IJstType#getProperty(String,boolean)
	 */
	public IJstProperty getProperty(final String name, boolean isStatic) {
		return getProperty(name, isStatic, true);
	}

	/**
	 * @see IJstType#getProperty(String,boolean,boolean)
	 */
	public IJstProperty getProperty(final String name, boolean isStatic, boolean recursive) {
		IJstProperty pty = m_functionExtType.getProperty(name, isStatic, recursive);
		pty = pty == null ? m_functionExtType.getProperty(name, !isStatic, recursive) : pty;
		return pty;
	}
	
	/**
	 * @see IJstType#getProperties()
	 */
	public List< IJstProperty> getProperties(){
		return m_functionExtType.getProperties();
	}

	/**
	 * @see IJstType#getProperties(boolean)
	 */
	public List< IJstProperty> getProperties(boolean isStatic) {
		return m_functionExtType.getProperties(isStatic);
	}

	/**
	 * @see IJstType#getAllPossibleProperties(boolean,boolean)
	 */
	public List< IJstProperty> getAllPossibleProperties(boolean isStatic, boolean recursive) {
		return m_functionExtType.getAllPossibleProperties(isStatic, recursive);
	}

	/**
	 * @see IJstType#hasStaticProperties()
	 */
	public boolean hasStaticProperties() {
		return m_functionExtType.hasStaticProperties();
	}

	/**
	 * @see IJstType#getStaticProperty(String)
	 */
	public IJstProperty getStaticProperty(final String name) {
		return m_functionExtType.getStaticProperty(name);
	}

	/**
	 * @see IJstType#getStaticProperty(String,boolean)
	 */
	public IJstProperty getStaticProperty(final String name, boolean recursive) {
		return m_functionExtType.getStaticProperty(name, recursive);
	}

	/**
	 * @see IJstType#getStaticProperties()
	 */
	public List< IJstProperty> getStaticProperties() {
		return m_functionExtType.getStaticProperties();
	}
	
	/**
	 * @see IJstType#hasInstanceProperties()
	 */
	public boolean hasInstanceProperties() {
		return m_functionExtType.hasInstanceProperties();
	}

	/**
	 * @see IJstType#getInstanceProperty(String)
	 */
	public IJstProperty getInstanceProperty(final String name) {
		return m_functionExtType.getInstanceProperty(name);
	}
	
	/**
	 * @see IJstType#getInstanceProperty(String,boolean)
	 */
	public IJstProperty getInstanceProperty(final String name, boolean recursive) {
		return m_functionExtType.getInstanceProperty(name, recursive);
	}

	/**
	 * @see IJstType#getInstanceProperties()
	 */
	public List< IJstProperty> getInstanceProperties() {
		return m_functionExtType.getInstanceProperties();
	}
	
	/**
	 * @see IJstType#getConstructor()
	 */
	public IJstMethod getConstructor() {
		return m_functionExtType.getConstructor();
	}

	/**
	 * @see IJstType#getMethod(String)
	 */
	public IJstMethod getMethod(final String name) {
		return getMethod(name, false);
	}

	/**
	 * @see IJstType#getMethod(String,boolean)
	 */
	public IJstMethod getMethod(final String name, boolean isStatic) {
		return getMethod(name, isStatic, true);
	}

	/**
	 * @see IJstType#getMethod(String,boolean,boolean)
	 */
	public IJstMethod getMethod(final String name, boolean isStatic, boolean recursive) {
		IJstMethod mtd = m_functionExtType.getMethod(name, isStatic, recursive);
		mtd = mtd == null ? m_functionExtType.getMethod(name, !isStatic, recursive) : mtd;
		return mtd;
	}
	
	/**
	 * @see IJstType#getMethods()
	 */
	public List<? extends IJstMethod> getMethods(){
		return m_functionExtType.getMethods();
	}

	/**
	 * @see IJstType#getMethods(boolean)
	 */
	public List<? extends IJstMethod> getMethods(boolean isStatic) {
		return m_functionExtType.getMethods(isStatic);
	}

	/**
	 * @see IJstType#getMethods(boolean,boolean)
	 */
	public List<? extends IJstMethod> getMethods(boolean isStatic, boolean recursive) {
		return m_functionExtType.getMethods(isStatic, recursive);
	}

	/**
	 * @see IJstType#hasStaticMethods()
	 */
	public boolean hasStaticMethods() {
		return m_functionExtType.hasStaticMethods();
	}
	
	/**
	 * @see IJstType#hasStaticMethod(String,boolean)
	 */
	public boolean hasStaticMethod(String mtdName, boolean recursive) {
		return m_functionExtType.hasStaticMethod(mtdName, recursive);
	}

	/**
	 * @see IJstType#getStaticMethods()
	 */
	public List<? extends IJstMethod> getStaticMethods() {
		return m_functionExtType.getStaticMethods();
	}
	
	/**
	 * @see IJstType#getStaticMethod(String)
	 */
	public IJstMethod getStaticMethod(final String name) {
		return m_functionExtType.getStaticMethod(name);
	}

	/**
	 * @see IJstType#getStaticMethod(String,boolean)
	 */
	public IJstMethod getStaticMethod(final String name, boolean recursive) {
		return m_functionExtType.getStaticMethod(name, recursive);
	}
	
	/**
	 * @see IJstType#hasInstanceMethods()
	 */
	public boolean hasInstanceMethods() {
		return m_functionExtType.hasInstanceMethods();
	}
	
	/**
	 * @see IJstType#hasInstanceMethod(String,boolean)
	 */
	public boolean hasInstanceMethod(String mtdName, boolean recursive) {
		return m_functionExtType.hasInstanceMethod(mtdName, recursive);
	}

	/**
	 * @see IJstType#getInstanceMethods()
	 */
	public List<? extends IJstMethod> getInstanceMethods() {
		return m_functionExtType.getInstanceMethods();
	}
	
	/**
	 * @see IJstType#getInstanceMethod(String)
	 */
	public IJstMethod getInstanceMethod(final String name) {
		return m_functionExtType.getInstanceMethod(name);
	}

	/**
	 * @see IJstType#getInstanceMethod(String,boolean)
	 */
	public IJstMethod getInstanceMethod(final String name, boolean recursive) {
		return m_functionExtType.getInstanceMethod(name, recursive);
	}
	
	public IJstMethod getFunction(){
		return m_function;
	}

	@Override
	public void accept(IJstNodeVisitor visitor) {
		return;
	}
}