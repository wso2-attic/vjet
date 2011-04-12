/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.pref;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.mod.debug.core.DLTKDebugPreferenceConstants;

import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugPlugin;
import org.ebayopensource.vjet.eclipse.internal.debug.debugger.pref.VjetDebugPreferenceConstants;

public class VjetDebugPrefInitializer extends AbstractPreferenceInitializer{

	public void initializeDefaultPreferences() {
		
		Preferences store = VjetDebugPlugin.getDefault().getPluginPreferences();
		
		//store.setDefault(JavaScriptDebugConstants.DEBUGGING_ENGINE_ID_KEY, "");

		store.setDefault(DLTKDebugPreferenceConstants.PREF_DBGP_BREAK_ON_FIRST_LINE, false);
		store.setDefault(DLTKDebugPreferenceConstants.PREF_DBGP_ENABLE_LOGGING, false);
		store.setDefault(DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_GLOBAL, true);
		store.setDefault(DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_CLASS, true);
		store.setDefault(DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_LOCAL, true);
		
		store.setDefault(VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH, false);
	}
	
}
