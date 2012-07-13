/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo;

import java.io.IOException;
import java.net.URL;

import org.ebayopensource.dsf.common.resource.ResourceUtil;

//used to access VjBootstrap js resources, this is just an anchor class
public class VjBootstrapJsr {
	
	public static String URN = "org.ebayopensource.vjo.VjBootstrapJsr.VjBootstrap_3";
	/**
	 * get resource uri (eq.: a.b.c.VjBootstrap)
	 */
	public static String getSourceUri() {
		return VjBootstrapJsr.class.getPackage().getName() + ".VjBootstrap_3";
	}
	
	/**
	 * find resource url from classpath
	 */
	public static URL getJsAsUrl() {
		try {
			return ResourceUtil.getResource(VjBootstrapJsr.class, "VjBootstrap_3.js");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// DO NOT LOAD THIS ON SITE
	public static URL getVjoApIAsUrl() {
		try {
			return ResourceUtil.getResource(VjBootstrapJsr.class, "VjBootstrapAPI.jsspec");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// DO NOT LOAD THIS ON SITE
	public static URL getVjoObjectAsUrl() {
		try {
			return ResourceUtil.getResource(VjBootstrapJsr.class, "VjObject.jsspec");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// DO NOT LOAD THIS ON SITE
	public static URL getVjoClassAsUrl() {
		try {
			return ResourceUtil.getResource(VjBootstrapJsr.class, "VjClass.jsspec");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// DO NOT LOAD THIS ON SITE
	public static URL getVjoEnumAsUrl() {
		try {
			return ResourceUtil.getResource(VjBootstrapJsr.class, "VjEnum.jsspec");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	// DO NOT LOAD THIS ON SITE
	public static URL getVjoOptionsOLUrl() {
		try {
			return ResourceUtil.getResource(VjBootstrapJsr.class, "VJOOptionsOL.jsspec");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// DO NOT LOAD THIS ON SITE
	public static URL getVjoConsoleAsUrl() {
		try {
			return ResourceUtil.getResource(VjBootstrapJsr.class, "VjConsole.jsspec");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}