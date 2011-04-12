/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * a custom class loader for loading java classes which are "active" JavasCript
 * written in java
 */
public class ScriptingSessionClassLoader extends ClassLoader {

	public ScriptingSessionClassLoader() {
		super(ScriptingSessionClassLoader.class.getClassLoader());
	}

	public Class<?> loadClass(String className) throws ClassNotFoundException {
		return findClass(className);
	}

	public Class<?> findClass(String className) throws ClassNotFoundException {
		Class<?> loadedClz = findLoadedClass(className);
		if (loadedClz != null) {
			return loadedClz;
		}
		
		ClassLoader parent = getParent();
		//check if the java class is for "active JavasCript" by examining the
		//existence of associated JS file
		if (parent.getResource(getOuterClassName(className).replace('.', '/') + ".js") == null) {
			//load normal class by parent ClassLoader
			loadedClz = parent.loadClass(className);
		}
		else {
			//load "active JavasCript" in this ClassLoader
			URL url = parent.getResource(className.replace('.', '/') + ".class");
			if (url == null) {
				throw new ClassNotFoundException(className);
			}
			byte[] classByte = loadClassData(url);
			loadedClz = defineClass(className, classByte, 0, classByte.length, null);			
		}
		return loadedClz;
	}

	private byte[] loadClassData(URL url) {
		byte[] buffer = new byte[1024];
		int numRead = 0;
		try {
			InputStream is = url.openStream();
			ByteArrayOutputStream os = new ByteArrayOutputStream();			
			while ((numRead = is.read(buffer)) > 0) {
				os.write(buffer, 0, numRead);
			}
			is.close();
			return os.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getOuterClassName(String name) {
		int index = name.indexOf("$");
		if (index == -1) {
			return name;
		}
		return name.substring(0, index);
	}
}
