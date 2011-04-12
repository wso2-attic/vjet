/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.binding;


public class BeanValueBinding<Target, T> 
	extends BaseValueBinding<T> implements IBeanValueBinding<Target, T> {	
	
	private static final long serialVersionUID = 1L;
	private Target m_target ;
	private String m_propertyName ;
	
	//
	// Constructor(s)
	//
	public BeanValueBinding(
		final Target target, 
		final String propertyNameKey, 
		final Class<T> valueType)
	{
		super(valueType) ;
		setTarget(target) ;
		setPropertyName(propertyNameKey) ;
	}
	
	//
	// Satisfy IBeanValueBinding
	//
	public void setTarget(final Target target)  {
		assertNotNull(target, "Bean target must not be null") ;
		m_target = target ;
	}
	
	public Target getTarget() {
		return m_target ;
	}
	
	public void setPropertyName(final String propertyName) {
		assertNotNull(propertyName, "Bean property name must not be null") ;
		m_propertyName = propertyName ;
	}
	
	public String getPropertyName() {
		return m_propertyName ;
	}
	
	//
	// Satisfy DsfBinding
	//
	public void setValue(final T value) {		
		assertState() ; // make sure target and propertyName is set		
		PropertyHelper.put(getTarget(), getPropertyName(), value) ;	
	}
	
	@SuppressWarnings("unchecked")
	public T getValue() {		
		assertState() ; // make sure target and propertyName is set		
		return (T)PropertyHelper.get(getTarget(), getPropertyName()) ;
	}
	
	//
	// Framework
	//
	protected final void assertState() {
		if (m_target == null) {
			chuck("Bean target must not be null") ;
		}
		
		if (m_propertyName == null) {
			chuck("Property name must not be null") ;
		}
	}
	
	//
	// Overrides from Object
	//
	public String toString() {
		return m_propertyName + " on: {" + m_target + "}" ;
	}
}
