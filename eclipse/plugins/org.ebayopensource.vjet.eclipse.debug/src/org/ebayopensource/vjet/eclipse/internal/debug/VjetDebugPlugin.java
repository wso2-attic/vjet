/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class VjetDebugPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.ebayopensource.vjet.eclipse.debug";

	// The shared instance
	private static VjetDebugPlugin plugin;
	
	/**
	 * The constructor
	 */
	public VjetDebugPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static VjetDebugPlugin getDefault() {
		return plugin;
	}
	
	// add by patrick
	public static void error(String message) {
		error(message, IStatus.ERROR);
	}

	public static void error(String message, int status) {
		plugin.getLog().log(
				new Status(status, PLUGIN_ID, IStatus.OK, message, null));
	}

	public static void error(String message, Throwable t) {
		error(message, t, IStatus.ERROR);
	}

	public static void error(String message, Throwable t, int status) {
		plugin.getLog().log(
				new Status(status, PLUGIN_ID, IStatus.OK, message, t));
	}
	// end add
}
