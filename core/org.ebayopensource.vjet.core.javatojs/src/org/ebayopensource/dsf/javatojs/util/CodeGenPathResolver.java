/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.ebayopensource.dsf.javatojs.control.ICodeGenPathResolver;
import org.ebayopensource.dsf.jst.declaration.JstType;

public class CodeGenPathResolver implements ICodeGenPathResolver {

	private final URL m_vjoRoot;
	private final URL m_jsrRoot;

	public CodeGenPathResolver(URL vjoRoot, URL jsrRoot) {
		m_vjoRoot = vjoRoot;
		m_jsrRoot = jsrRoot;
	}

	public URL getJsrFilePath(JstType type) throws MalformedURLException {
		return new URL(m_jsrRoot + "/" + getPath(type) + "Jsr.java");
	}

	public URL getVjoFilePath(JstType type) throws MalformedURLException {
		return new URL(m_vjoRoot + "/" + getPath(type) + ".js");
	}

	private static String getPath(JstType type) {
		return type.getName().replace('.', File.separatorChar);
	}

	public URL getJavaFilePath(JstType type) throws MalformedURLException {
		return new URL(m_vjoRoot + "/" + getPath(type) + ".java");
	}

}
