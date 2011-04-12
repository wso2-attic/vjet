/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

/**
 * Single search root based (such as single jar file or single /bin/)
 * resource search using URLClassLoader.
 * 
 * ClassBasedResourceFinder instances are cached and reused based on
 * the search roots.
 */
public class ClassBasedResourceFinder {
	
	private final URLClassLoader m_loader;
	
	private static Map<URI, ClassBasedResourceFinder> s_finders = 
		new HashMap<URI, ClassBasedResourceFinder>();
	
	private ClassBasedResourceFinder(URLClassLoader loader) {
		m_loader = loader;
	}
	
	public static ClassBasedResourceFinder getFinder(Class clz) {		
		String baseUri = clz.getName().replace(".", "/");
		String fileName = baseUri + ".class";
		URL url = clz.getResource("/" + fileName);
		String fullUrlName = url.toExternalForm();
		try {
			URL rootUrl = new URL(fullUrlName.substring(0, fullUrlName.length() - fileName.length()));
			synchronized (ClassBasedResourceFinder.class) {
				ClassBasedResourceFinder finder = s_finders.get(rootUrl.toURI());
				if (finder == null) {
//					java.security.AccessController.doPrivileged(
//							
//					)
//					
					finder = new ClassBasedResourceFinder(
						new URLClassLoader(new URL[] {rootUrl}, null));
					s_finders.put(rootUrl.toURI(), finder);
				}
				return finder;
			}
		} catch (MalformedURLException e) {
			throw new DsfRuntimeException(e);
		} catch (URISyntaxException e) {
			throw new DsfRuntimeException(e);			
		}
	}
	
	public URL findResource(String resourceName) {
		return m_loader.findResource(resourceName);
	}
	
	public boolean hasResource(String resourceName) {
		return m_loader.findResource(resourceName) != null;
	}
}
