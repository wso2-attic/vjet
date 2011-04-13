/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

/**
 * An IJstType representing an attributed type from another IJstType as an attributor
 */
public class JstAttributedType extends JstProxyType {

	private static final long serialVersionUID = 1L;
	
	private String m_attributeName;
	private boolean m_staticAttribute;
	private IJstNode m_jstBinding;

	public JstAttributedType(final IJstType attributorType, final String attribute, final boolean staticAttribute) {
		super(attributorType);
		m_attributeName = attribute;
		m_staticAttribute = staticAttribute;
	}
	
	public void setAttributeName(final String attribute){
		m_attributeName = attribute;
	}
	
	public String getAttributeName(){
		return m_attributeName;
	}
	
	public void setStaticAttribute(final boolean staticAttribute){
		m_staticAttribute = staticAttribute;
	}
	
	public boolean isStaticAttribute(){
		return m_staticAttribute;
	}
	
	public void setJstBinding(final IJstNode binding){
		m_jstBinding = binding;
	}
	
	/**
	 * by huzhou@ebay.com
	 * <p>
	 * 	The attributed type's binding used to be one of the following:
	 *  {global var, global function, instance property, instance method, static property, static method}
	 *  
	 *  Now that we want to have the otype fit to the attributed syntax:
	 *  <code>vjet.sample.Otype:ObjLiteralType</code>
	 *  <code>vjet.sample.Otype:FunctionRefType</code>
	 *  The motivation was to ease the otype usages and development esp. at translate, linking time
	 *  With this syntax enforcement, dot styled chaining becomes exclusing for nested ctypes or top level types
	 *  
	 *  Therefore, the new binding options are listed as the following:
	 *  {{@link JstObjectLiteralType}, {@link JstFunctionRefType}}
	 * </p>
	 * @return
	 */
	public IJstNode getJstBinding(){
		return m_jstBinding;
	}
	
	public IJstType getAttributorType(){
		return getType();
	}

	@Override
	public void accept(IJstNodeVisitor visitor) {
		return;
	}
	
	@Override
	public String getName() {
		return super.getName()
			+ (m_staticAttribute ? "::" : ":") 
			+ m_attributeName;
	}
	
	@Override
	public String getSimpleName() {
		return super.getSimpleName()
			+ (m_staticAttribute ? "::" : ":") 
			+ m_attributeName;
	}
}
