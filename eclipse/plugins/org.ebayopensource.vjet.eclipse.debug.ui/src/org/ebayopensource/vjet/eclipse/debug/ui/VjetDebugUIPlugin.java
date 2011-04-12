/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.debug.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import org.ebayopensource.vjet.eclipse.internal.debug.ui.VjetDebugOptionsManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class VjetDebugUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String			PLUGIN_ID	= "org.ebayopensource.vjet.eclipse.debug.ui";

	// The shared instance
	private static VjetDebugUIPlugin	plugin;

	/**
	 * The constructor
	 */
	public VjetDebugUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		setPluginInstance(this);
		VjetDebugOptionsManager.getDefault().start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		VjetDebugOptionsManager.getDefault().stop();
		setPluginInstance(null);
		super.stop(context);
	}

	// Modify by Oliver, 2009-12-01, fix findbugs bug.
	private static void setPluginInstance(VjetDebugUIPlugin pluginPar) {
		plugin = pluginPar;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static VjetDebugUIPlugin getDefault() {
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
		log(new Status(status, PLUGIN_ID, IStatus.OK, message, t));
	}

	public static void log(IStatus status) {
		plugin.getLog().log(status);
	}

	public static void errorDialog(Shell shell, String title, String msg,
			DebugException e) {
		String message = msg;
		IStatus status;
		if (e instanceof CoreException) {
			status = ((CoreException) e).getStatus();
			// if the 'message' resource string and the IStatus' message are
			// the same,
			// don't show both in the dialog
			if (status != null && message.equals(status.getMessage())) {
				message = null;
			}
		} else {
			status = new Status(IStatus.ERROR, PLUGIN_ID,
					IDebugUIConstants.INTERNAL_ERROR,
					"Error within Debug UI: ", e); //$NON-NLS-1$
			log(status);
		}
		ErrorDialog.openError(shell, title, message, status);
	}
	// end add
}
