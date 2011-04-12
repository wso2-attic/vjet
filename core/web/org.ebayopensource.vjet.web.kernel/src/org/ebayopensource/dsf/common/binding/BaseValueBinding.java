/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.binding;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;


public abstract class BaseValueBinding<T> implements IValueBinding<T> {
	private Class<T> m_valueType;
	
	//
	// Constructor(s)
	//							
	public BaseValueBinding(final Class<T> valueType){	
		m_valueType = valueType;
	} 
	
	public Class<T> getValueType() {
		return m_valueType ;
	}
	
	//
	// Framework support
	//
	protected void chuck(final String message) {
		throw new DsfRuntimeException(message) ;
	}
	
	protected void assertNotNull(final Object value, final String errorMessage) {
		if (value != null) return ;
		chuck(errorMessage) ;
	}
}
