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

/**
 * Lowest common denominator for brokering a value.  To keep the ever growing
 * set of binding styles/types etc... we settled for Java 5 generic semantic.
 * 
 * In the cases where Java primtive types are being bound, use the corresponding
 * Java wrapper type.
 * 
 * This interface makes no assumption of valid domain values.  Thus for a binding
 * to a Java primitive, returning null will cause an error.
 * 
 * Null is always valid for an Object semantic at face value, but it is upto the
 * actual binding implementation to properly deal with all domain value
 * interpretation -- including null.
 *
 */
public interface IValueBinding<T> extends Serializable, Cloneable {
	/**
	 * Answer the actual Class type for <T>
	 */
	Class<T> getValueType();
	
	/**
	 * Gets the value of right type
	 */
	T getValue();
	
	/**
	 * Sets the value of right type
	 */
	void setValue(T value);
}
