/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.binding;

/**
 * 
 */
public interface IBeanValueBinding<Target, T> extends IValueBinding<T> {
	/**
	 * Set the Java Bean instance for this binding.  The target instance must
	 * not be null.
	 */
	void setTarget(Target o) ;
	
	/**
	 * Answers the Java Bean instance target for this binding.  The target
	 * will never be null.
	 */
	Target getTarget() ;

	/**
	 * Sets the Java Bean property name for this binding.  The property name
	 * must not be null.
	 */
	void setPropertyName(String propertyName);
	
	/**
	 * Answers the Java Bean property name for this binding.  The name will 
	 * never be null
	 */
	String getPropertyName() ;
}
