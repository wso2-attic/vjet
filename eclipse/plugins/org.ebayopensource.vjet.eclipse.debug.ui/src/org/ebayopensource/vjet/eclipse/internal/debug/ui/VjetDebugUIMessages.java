/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui;

import org.eclipse.osgi.util.NLS;

public class VjetDebugUIMessages extends NLS {
	private static final String	BUNDLE_NAME	= "org.ebayopensource.vjet.eclipse.internal.debug.ui.VjetDebugUIMessages";	//$NON-NLS-1$
	public static String		RunningScriptView_column_name;
	public static String		RunningScriptView_open_file_action_name;
	public static String		RunningScriptView_open_file_action_tooltip;
	public static String		RunningScriptView_refresh_job_name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, VjetDebugUIMessages.class);
	}

	private VjetDebugUIMessages() {
	}
}
