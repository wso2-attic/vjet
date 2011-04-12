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
import java.net.URL;
import java.net.URLClassLoader;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

/**
 * This class loads and inits the class again by a specific
 * URLClassLoader (child-first delegation mode).
 * 
 * It can be used for hot-code replacement for "static" java definitions.
 */
public class ChildFirstClassLoader extends URLClassLoader{

	public static Class load(Class clz) {		
		String baseUri = clz.getName().replace(".", "/");
		String fileName = baseUri + ".class";
		URL url = clz.getResource("/" + fileName);
		String fullUrlName = url.toExternalForm();
		try {
			URL rootUrl = new URL(fullUrlName
				.substring(0, fullUrlName.length() - fileName.length()));
			URLClassLoader loader = new ChildFirstClassLoader(
				new URL[] {rootUrl}, 
				ChildFirstClassLoader.class.getClassLoader());
			try {
				return Class.forName(clz.getName(), true, loader);
			} catch (ClassNotFoundException e) {
				throw new DsfRuntimeException(e);
			}

		} catch (MalformedURLException e) {
			throw new DsfRuntimeException(e);
		}
	}
	
	private ClassLoader m_parent;
	private ChildFirstClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, null);
		m_parent = parent;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> clz = null;
		try {
			clz = super.findClass(name);
		}
		catch (ClassNotFoundException e) {
			//DO NOTHING
		}
		if (clz == null) {
			clz = m_parent.loadClass(name);
		}
		return clz;
	}

}
