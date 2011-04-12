/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.sdk;

import java.net.URL;
import java.net.URLClassLoader;

import org.ebayopensource.dsf.jst.ts.util.ISdkEnvironment;

public class VJetSdkEnvironment implements ISdkEnvironment {
	private String m_name;
	private String[] m_paths;
	private URLClassLoader m_loader;

	public VJetSdkEnvironment(String[] paths, String name) {
		this.m_name = name;
		this.m_paths = paths;
		initLoader();
	}
	
	private void initLoader() {
		m_loader = new URLClassLoader(getUrlArray(m_paths));
	}

	private URL[] getUrlArray(String[] m_paths2) {
		return new URL[0];
	}

	@Override
	public Class getAnchorClass(String className) throws ClassNotFoundException {
		return m_loader.loadClass(className);
	}

	@Override
	public String getSdkName() {
		return m_name;
	}

	@Override
	public String[] getSdkPaths() {
		return m_paths;
	}

}
