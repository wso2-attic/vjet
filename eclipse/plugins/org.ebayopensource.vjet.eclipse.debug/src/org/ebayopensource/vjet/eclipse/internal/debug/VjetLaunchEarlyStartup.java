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
package org.ebayopensource.vjet.eclipse.internal.debug;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.ui.IStartup;


/**
 * vjet launching early start up to init launch listener
 * 
 * 
 *
 */
public class VjetLaunchEarlyStartup implements IStartup {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	public void earlyStartup() {
		this.initializeLaunchListeners();
	}

	private void initializeLaunchListeners() {
		// Add a launch listener so we can change the vm args.
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		LaunchListener listener = new LaunchListener();
		launchManager.addLaunchListener(listener);
	}
}
