/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.binding;

import java.lang.reflect.Constructor;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.util.ValueHelper;

public class SimpleValueBinding<T> 
	extends BaseValueBinding<T> implements IValueBinding<T>
{	
	private static final long serialVersionUID = 1L;
	private T m_value = null;
	
	//
	// Constructor(s)
	//							
	public SimpleValueBinding(final Class<T> valueType) {	
		super(valueType) ;
	}
					
	public SimpleValueBinding(final Class<T> valueType, final T value){
		this(valueType);
		setValue(value);
		
//		if (value != null && ISerializableForVjo.class.isAssignableFrom(value.getClass())){
//			((ISerializableForVjo)value).init();
//		}
	}
	
	//
	// Implement IValueBinding<T>
	//
	public void setValue(final T value) {
		m_value = value;
	}
	
	public T getValue() {
		return m_value;
	}

	//
	// Override Object
	//	
	@Override
	public boolean equals(final Object obj){
		if (obj == null){
			return false;
		}
		
		if (!(obj instanceof IValueBinding)){
			return false;
		}
		
		IValueBinding that = (IValueBinding)obj;
		if (this.m_value == null){
			return that.getValue() == null;
		}
		else{
			return this.m_value.equals(that.getValue());
		}
	}
	
	@Override
	public int hashCode(){
		if (m_value == null){
			return 0;
		}
		return m_value.hashCode();
	}
	
	/**
	 * Attempts to clone() this instance.  The <T>value is simply reused
	 * and thus is not always safe.  For types like String, Integer, etc...
	 * it is sufficient, but for other complex types that may be mutable it
	 * acts like a shallow clone
	 */
	public Object clone() {
		try {
			final Constructor<? extends SimpleValueBinding> cs = 
				this.getClass().getDeclaredConstructor(Class.class, Object.class) ;

			final Object[] args = {getValueType(), cloneValue()} ;
			return cs.newInstance(args) ;
		}
		catch(Exception e) {
			throw new DsfRuntimeException("Unable to clone() ValueDataSource") ;
		}
	}
	
	@Override
	public String toString() {
		return "{value: " + ValueHelper.toString(getValue()) 
			+ ", type: " + getValueType() + "}" ;
	}
	
	//
	// Private
	// 
	private Object cloneValue() {
		return m_value ;
		
// You really can't get to <T> at runtime.  Even the getTypeParameters() reflection
// call will fail at runtime to give you back anything besides Class.class.
// You can't ask if m_value implements clone() since you don't have the actual
// type at runtime (it is always Object.class).  This also means we can't use
// reflection at the method level since we won't find the right method...
// Oh well, that's how generics work...
/*
		if (m_value == null) return m_value ;
		
		Class cloneable = Cloneable.class ;
		Class mightBeCloneable = m_value.getClass() ;
		System.out.println(mightBeCloneable.getInterfaces()) ; 
		TypeVariable<Class<T>>[] ts = mightBeCloneable.getTypeParameters() ;
		System.out.println(ts) ;
		if (cloneable.isAssignableFrom(mightBeCloneable)) {
			return null ;
		}
		else {
			// May not be a safe deep clone, buyer beware
			return m_value ;
		}
*/
	}
}

