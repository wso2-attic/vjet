/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.af.common.error;


public interface IClearableAggregateErrorContainer 
	extends IAggregateErrorContainer
{
	/**
	 * Clear all Errors in the implementing container.  A call to any of the
	 * has errors or get errors methods from AggregateErrorContainer should
	 * answer false and empty ErrorList respectively after this call.
	 */
	void clearAllErrors() ;
	
	/**
	 * Clear all Errors in the implementing container that match the passed
	 * in filter.  The filter should not be null.
	 */
	void clearAllErrors(ErrorFilter filter) ;
}
