/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.naming;

import java.io.Serializable;
/**
 * The verifier defined to verify a series of V4 names, including 
 * <code>Facet Name</code>, <code>Attribute Name</code> and 
 * <code>DSF local Name</code>.
 *
 */
public interface IDsfNamingVerifier extends Serializable, Cloneable {
	Status STATUS_OK = new Status() {
		private static final long serialVersionUID = 1L;

		public boolean isOK() {
			return true ;
		}
		
		public String getErrorMessage() {
			return null ;
		}
	};
	
	/** 
	 * This normally deletegates to verifyDsfName.
	 */
	Status verifyFacetName(String name) ; //throws DsfInvalidNameException;
	/** This normally deletegates to verifyDsfName.
	 */
	Status verifyAttributeName(String name) ; //throws DsfInvalidNameException;

	/** This should verify just the local name and not the fully
	 * qualified name.
	 * 
	 */
	//TODO: why not just verifyName()???
	Status verifyDsfName(String name) ; //throws DsfInvalidNameException;
	
	public static interface Status extends Serializable, Cloneable {
		boolean isOK();
		String getErrorMessage();
	}		
}
