/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;
import org.w3c.dom.DOMImplementationSource;

/**
 * Permits a DOM implementer to supply one or more implementations, based upon 
 * requested features and versions, as specified in . Each implemented 
 * DOMImplementationSource object is listed in the binding-specific list of 
 * available sources so that its DOMImplementation objects are made available.
 */
class DDOMImplementationSource implements DOMImplementationSource {
	private static final DOMImplementation s_impl = new DDOMImplementation() ;
	private static final DOMImplementationList s_list 
		= new DDOMImplementationList(s_impl);
	
	//
	// Constructor(s)
	//
	DDOMImplementationSource() {
		// empty on purpose
	}
	
	//
	// Satisfy DOMImplementationSource
	//
	/**
	 *  @param features - A string that specifies which features and versions are 
	 *  required. This is a space separated list in which each feature is specified 
	 *  by its name optionally followed by a space and a version number. This 
	 *  method returns the first item of the list returned by getDOMImplementationList. 
	 *  As an example, the string "XML 3.0 Traversal +Events 2.0" will request a 
	 *  DOM implementation that supports the module "XML" for its 3.0 version, a 
	 *  module that support of the "Traversal" module for any version, and the 
	 *  module "Events" for its 2.0 version. The module "Events" must be accessible 
	 *  using the method Node.getFeature() and DOMImplementation.getFeature(). 
	 *  @return The first DOM implementation that support the desired features, 
	 *  or null if this source has none.
	 */
	public DOMImplementation getDOMImplementation(final String features) {
		if (features == null) return null ;
		
		final String normalizedFeatures = features.trim().toLowerCase() ;		
		final int len = s_list.getLength() ;
		
		for(int i = 0; i < len; i++) {
			DOMImplementation impl = s_list.item(i) ;
			if (impl.hasFeature(normalizedFeatures, null)) {
				return impl ;
			}
		}
		return null ;
	}

	/**
	 * @param A string that specifies which features and versions are required. 
	 * This is a space separated list in which each feature is specified by its 
	 * name optionally followed by a space and a version number. This is something 
	 * like: "XML 3.0 Traversal +Events 2.0" 
	 * @return A list of DOM implementations that support the desired features.
	 */
	public DOMImplementationList getDOMImplementationList(final String features) {
		if (features == null) return null ;
		
		final String normalizedFeatures = features.trim().toLowerCase() ;
		final int len = s_list.getLength() ;
		DDOMImplementationList answer = new DDOMImplementationList() ;
		
		for(int i = 0; i < len; i++) {
			DOMImplementation impl = s_list.item(i) ;
			if (impl.hasFeature(normalizedFeatures, null)) {
				answer.add(impl) ;
			}
		}
		return answer ;
	}

}
