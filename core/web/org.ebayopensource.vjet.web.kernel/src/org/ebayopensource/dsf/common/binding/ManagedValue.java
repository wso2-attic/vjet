/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.binding;

import java.io.Serializable;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

/**
 * A typed value holder supporting the value management, such as,
 * (1) tracking the previous value, (2) delegation to any other
 * value binding if there is, (3) differentiation of null value
 * and value not provided.
 */
public class ManagedValue<T> implements IValueBinding<T> {

	private static final long serialVersionUID = 1L;
	
	private final Class<T> m_valueType;	
	private T m_value;
	private IValueBinding<T> m_delegator;
	private PreviousValue<T> m_previousValue;
		
	//
	// Constructor(s)
	//
	public ManagedValue(final Class<T> valueType) {
		if (valueType == null) {
			throw new DsfRuntimeException("Value type must not be null") ;
		}
		m_valueType = valueType;
	}
	
	public ManagedValue(final Class<T> valueType, final T value) {
		this(valueType) ;
		setValue(value);
	}
	
	public Class<T> getValueType() {
		return m_valueType;
	}
	
	public boolean hasPreviousValue() {
		if (m_previousValue == null) {
			return false;
		}
		return m_previousValue.hasValue();
	}

	public T getPreviousValue() {
		if (m_previousValue == null || !m_previousValue.hasValue()) {
			return null;
		}
		return m_previousValue.getValue();
	}

	public void setValue(final T value) {
		setPreviousValue(m_value);
		m_value = value;
		if (m_delegator != null) {
			m_delegator.setValue(value);
		}
	}

	public T getValue() {
		T result = m_value;
		if (m_delegator != null) {
			result = m_delegator.getValue();
			if (result != m_value) {
				setPreviousValue(m_value);
				m_value = result;
			}
		}
		return result;
	}
	
	public IValueBinding<T> getDelegator() {
		return m_delegator;
	}
	
	public void setDelegator(final IValueBinding<T> delegator) {
		m_delegator = delegator;
	}
	
	public boolean isValueProvided() {
		return m_delegator != null || m_previousValue != null;
	}
	
	public void unset() {
		m_delegator = null;
		m_previousValue = null;
		m_value = null;
	}
	
	/**
	 * Create the previous value wrapper at first time without
	 * setting the value.
	 */
	private void setPreviousValue(final T value) {
		if (m_previousValue == null) {
			m_previousValue = new PreviousValue<T>();
		}
		else {
			m_previousValue.setValue(value);
		}
	}
	
	public static class PreviousValue<T> implements Serializable, Cloneable{
		
		private static final long serialVersionUID = 1L;
		private T m_value;
		private boolean m_set = false;
		
		public boolean hasValue() {
			return m_set;
		}
		
		public void setValue(T value) {
			m_value = value;
			m_set = true;
		}
		
		public T getValue() {
			return m_value;
		}
		
		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
	}
}
