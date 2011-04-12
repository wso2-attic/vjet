/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.handlers;

import org.eclipse.dltk.mod.debug.ui.handlers.AbstractToggleGlobalVariableHandler;
import org.eclipse.dltk.mod.ui.PreferencesAdapter;
import org.eclipse.jface.preference.IPreferenceStore;

import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugConstants;
import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugPlugin;

/**
 * Toggles the display of javascript global variables in the debug 'Variables'
 * view
 */
public class ToggleGlobalVariablesHandler extends
		AbstractToggleGlobalVariableHandler {
	/*
	 * @see org.eclipse.dltk.mod.debug.ui.handlers.AbstractToggleVariableHandler#getModelId()
	 */
	protected String getModelId() {
		return VjetDebugConstants.DEBUG_MODEL_ID;
	}

	/*
	 * @see org.eclipse.dltk.mod.debug.ui.handlers.AbstractToggleVariableHandler#getPreferenceStore()
	 */
	protected IPreferenceStore getPreferenceStore() {
		return new PreferencesAdapter(VjetDebugPlugin.getDefault()
				.getPluginPreferences());
	}
}
