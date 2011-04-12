/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.af.common.error;


public interface ErrorFilter {
	/**
	 * Answer true if this filter matches the ErrorObject.  It may not always 
	 * possible for the ErrorObject to be provided to this filter so the
	 * implementations should expect the ErrorObject to sometimes be null.
	 * 
	 * Generally the implementations will be threadsafe and immutable.  However
	 * this is not a requirement and implementations may take whatever design
	 * choice is appropriate.
	 * 
	 * @param errorObject - ErrorObject to match.  May be null.
	 * @return true if the errorObject matches the filter
	 */
	boolean matches(ErrorObject errorObject) ;
}

