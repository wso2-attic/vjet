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

/**
 * Types that directly manage a set of errors themselves will implement this
 * interface.  This interface is different thatn IAggregateErrorContainer which
 * means the type "aggregates" errors from other sources (often other contained)
 * objects.
 */
public interface IDirectErrorsContainer extends Serializable, Cloneable {
	boolean hasDirectErrors() ;
	public boolean hasDirectErrors(ErrorFilter errorFilter) ;	
	
	ErrorList getDirectErrors() ;
	ErrorList getDirectErrors(ErrorFilter errorFilter) ;
	
	void clearDirectErrors() ;
	void clearDirectErrors(ErrorFilter errorFilter) ;
}
