/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.ovs;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.Z;

/**
 * Generic type for captioned values. Value cannot be null and is immutable.
 * 
 *
 * @param <T>
 */
public final class CaptionedValue<T> implements ICaptionedValue {	
	
	private final T m_value;
	private String m_caption;
	
	//
	// Constructor
	//
	/**
	 * Constructor. 
	 * @param value T
	 * @exception DsfRuntimeException if value is null.
	 */
	public CaptionedValue(final T value) {
		if (value == null){
			throw new DsfRuntimeException("value is null");
		}
		m_value = value;
	}
	
	/**
	 * Constructor. 
	 * @param value T cannot be null
	 * @param caption String can be null.
	 * @exception DsfRuntimeException if value is null.
	 */
	public CaptionedValue(final T value, final String caption) {
		this(value);
		m_caption = caption;
	}
	
	//
	// Satisfy ICaptionedValue
	//
	/**
	 * Answer object value of this instance.
	 * @return Object.
	 */
	public T getValue() {
		return m_value;
	}
	
	/**
	 * Answer caption for the value of this instance
	 * @return String
	 */
	public String getCaption() {
		return m_caption ;
	}
	
	//
	// API
	//
	/**
	 * Set caption for the value of this instance
	 * @param caption String
	 */
	protected void setCaption(final String caption) {
		m_caption = caption;
	}

	//
	// Override(s) from Object
	//
	public int hashCode(){
		
		int captionHash = getCaption() == null ? 0 : getCaption().hashCode();
				
		Object obj = getValue();
		if(obj == null) {
			return captionHash;
		} else {
			return captionHash + obj.hashCode();
		}
	}

	/**
	 * Answer false if obj is null or not a type of CaptionedValue.
	 * Otherwise, answer true when and only when both caption and value 
	 * are equal based on their implementations of Object::equals(Object).
	 * @param Object
	 * @return boolean
	 */
	@Override
	public boolean equals(final Object obj){
		if (obj == null || !(obj instanceof CaptionedValue)){
			return false;
		}
		
		final CaptionedValue that = (CaptionedValue)obj;
		return captionEquals(that.getCaption()) && valueEquals(that.getValue());
	}
	
	@Override
	public String toString() {
		return Z.fmt(getCaption(), m_value);
	}
	
	//
	// Private
	//
	private boolean captionEquals(final String caption){
		final String thisCaption = this.getCaption();
		
		if (thisCaption == null){
			return (caption == null);
		}	
		else {
			return thisCaption.equals(caption);
		}	
	}
	
	private boolean valueEquals(final Object value){
		final T thisValue = this.getValue();
		
		if (thisValue == null) {
			return (value == null);
		} 
		else {
			return thisValue.equals(value);
		}
	}
}
