/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.af.common.error;

public interface IAggregateErrorContainer {
	/**
	 * Answer true if any error occurred
	 */
	boolean hasAnyErrors() ;
	
	/**
	 * Answer true if any errors on the implementer matches the filter.
	 * 
	 * If an ErrorList is passed to the implementation it should be the owner
	 * of the ErrorObject passed.
	 * 
	 * It is not required for an implementation to pass the ErrorList but is 
	 * recommended.
	 * 
	 * The ErrorObject passed to the filter should not null.
	 */
	boolean hasAnyErrors(ErrorFilter errorFilter) ;
	
	/**
	 * Answer an ErrorList of any errors in this implementation.  If there are
	 * no errors an empty ErrorList should be returned.
	 * 
	 * There is no gaurantee that the empty ErrorList returned is actually
	 * mutable but in almost all cases it will be mutable.  
	 * 
	 * There is no guarantee that the ErrorList is still actively backed by
	 * the implementation.
	 * 
	 * There is no guarantee
	 * that impl.getErrors() == impl.getErrors().
	 */
	ErrorList getAllErrors() ;
	
	/**
	 * Same as getErrors() except that the ErrorList returns only contains
	 * ErrorObjects that match the filter.
	 *  
	 * If an ErrorList is passed to the implementation it should be the owner
	 * of the ErrorObject passed.
	 * 
	 * It is not required for an implementation to pass the ErrorList but is 
	 * recommended.
	 * 
	 * The ErrorObject passed to the filter should not null.
	 */
	ErrorList getAllErrors(ErrorFilter errorFilter) ;
}
