/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;
import org.w3c.dom.DOMImplementationSource;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

/**
 * This class mirrors the capability of the DOMImplementationRegistry.  Since
 * DOMImplementationRegistry is not an interface, is marked as final, and exposes
 * exceptions that are not very friendly, we are creating a mirror of it here.
 * <p>from org.w3c.dom.bootstrap.DOMImplementationRegistry<p>
 * A factory that enables applications to obtain instances of DOMImplementation.
<p>
Example:
<p>
<code>
  // get an instance of the DOMImplementation registry
  DOMImplementationRegistry registry =
       DOMImplementationRegistry.newInstance();
  // get a DOM implementation the Level 3 XML module
  DOMImplementation domImpl =
       registry.getDOMImplementation("XML 3.0");
</code>
<p>
This provides an application with an implementation-independent starting point. 
DOM implementations may modify this class to meet new security standards or to 
provide *additional* fallbacks for the list of DOMImplementationSources. 
 */
public class DDOMImplementationRegistry {
	private static List<DOMImplementationSource> s_srcs 
		= new ArrayList<DOMImplementationSource>() ;
	
	private List<DOMImplementationSource> m_srcs 
		= new ArrayList<DOMImplementationSource>() ;
	
	static {
		// load any sources from the system properties
		
		// Add in the only DOM/XML implementation
		s_srcs.add(new DDOMImplementationSource()) ;
		
// TODO: MrP - we need tests since these are reflection lookups and we need to
// make sure they stay valid.
		registerImplSource("org.ebayopensource.dsf.dom.DDOMImplementationSource");
		registerImplSource("org.ebayopensource.dsf.html.dom.DHtmlDOMImplementationSource");
		registerImplSource("org.ebayopensource.dsf.nb.BmlDOMImplementationSource");
//		registerImplSource("org.ebayopensource.dsf.mml.MmlDOMImplementationSource");
	}

	private static void registerImplSource(final String implClassName) {
		try {
			// Add in the only HTML implementation.  This is a reach in
			final Class<?> clz = Class.forName(implClassName) ;
			final Constructor<?> c = clz.getDeclaredConstructor() ;
			c.setAccessible(true) ;
			DOMImplementationSource src = (DOMImplementationSource)c.newInstance() ;
			s_srcs.add(src) ;
		}
		catch(Exception e) {
			// MrP - We don't want to fail loading this class if we have a problem
			// TODO: It's ok to use .err here.  Should also do a CAL error.
			System.err.println( // KEEPME
				"Unable to load " + implClassName + " errmsg: " + e.getMessage()) ;
		}
	}
	
	//
	// Construction
	//
	/**
	 * 
	 */
	public static DDOMImplementationRegistry newInstance() {
		DDOMImplementationRegistry reg = new DDOMImplementationRegistry() ;
		reg.m_srcs.addAll(s_srcs) ;
		return reg ; 
	}
	
	//
	// API
	//
	public void addSource(DOMImplementationSource... srcs) {
		if (srcs == null) {
			chuck("Can not add a null source") ;
		}
		for(DOMImplementationSource src: srcs) {
			if (src == null) {
				chuck("Can not add a null source") ;
			}
			m_srcs.add(src) ;
		}
	}
	
	public void addSource(final Collection<DOMImplementationSource> srcs) {
		if (srcs == null) {
			chuck("Can not add a null source") ;
		}
		for(DOMImplementationSource src: srcs) {
			if (src == null) {
				chuck("Can not add a null source") ;
			}
			m_srcs.add(src) ;
		}		
	}
	
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
		for (DOMImplementationSource src: m_srcs) {
			DOMImplementation impl = src.getDOMImplementation(features) ;
			if (impl != null) return impl ;
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
		final DDOMImplementationList answer = new DDOMImplementationList() ;
		for (DOMImplementationSource src: m_srcs) {
			DOMImplementationList list = src.getDOMImplementationList(features) ;
			answer.add(list) ;
		}
		return answer ;
	}
	
	//
	// Private
	//
	private void chuck(final String msg) {
		throw new DsfRuntimeException(msg) ;
	}
}
