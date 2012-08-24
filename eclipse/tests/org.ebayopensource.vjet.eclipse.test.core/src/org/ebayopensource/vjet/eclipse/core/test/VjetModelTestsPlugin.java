/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core.test;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * 
 * 
 */
public class VjetModelTestsPlugin extends Plugin {

	public static final String PLUGIN_NAME = "org.ebayopensource.vjet.eclipse.test.core";

	// The shared instance.
	private static VjetModelTestsPlugin m_plugin;

	/**
	 * The constructor.
	 */
	public VjetModelTestsPlugin() {
		m_plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		m_plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static VjetModelTestsPlugin getDefault() {
		return m_plugin;
	}
}
