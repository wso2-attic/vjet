/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.launching;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class VjetLaunchingPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.ebayopensource.vjet.eclipse.launching";

	// The shared instance
	private static VjetLaunchingPlugin plugin;
	
	/**
	 * The constructor
	 */
	public VjetLaunchingPlugin() {
		
	}
	
	private void initializeLaunchListeners() {
		// Add a launch listener so we can change the vm args.
//		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
//		LaunchListener listener = new LaunchListener();
//		launchManager.addLaunchListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		setPluginInstance(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		setPluginInstance(null);
		super.stop(context);
	}

	
	// Modify by Oliver, 2009-12-01, fix findbugs bug.
	private static void setPluginInstance(VjetLaunchingPlugin pluginPar) {
		plugin = pluginPar;
	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static VjetLaunchingPlugin getDefault() {
		return plugin;
	}

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
}
