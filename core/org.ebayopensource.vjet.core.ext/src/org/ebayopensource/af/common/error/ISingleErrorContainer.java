/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.af.common.error;

import java.io.Serializable;


public interface ISingleErrorContainer extends Serializable, Cloneable {
	/**
	 * Answer if the container (a ValueHolder for example) has an error 
	 * (ErrorObject).
	 */
	boolean hasError() ;
	
	/**
	 * Answer if the container (a ValueHolder for example) has an error 
	 * (ErrorObject) matching the passed in filter.
	 */
	boolean hasError(ErrorFilter filter) ;
	
	/**
	 * Answer the ErrorObject for this instance, if none, answers null.
	 */
	ErrorObject getError() ;
	
	/**
	 * Answer the ErrorObject for this instance matching the passed in filter,
	 * if no match answers null.
	 */
	ErrorObject getError(ErrorFilter filter) ;
	
	/**
	 * Set the ErrorObject for this instance.  The passed in ErrorObject can
	 * be null.
	 */
	void setError(ErrorObject errorObject) ;
	
	/**
	 * Clear any ErrorObject for this instance.  Same as setError(null) ;
	 */
	void clearError() ;
	
	/**
	 * Clear the ErrorObject for this instance if it matches the passed
	 * in filter.
	 */
	void clearError(ErrorFilter filter) ;
}
