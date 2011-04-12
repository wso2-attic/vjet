/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.mod.debug.ui.IDLTKDebugUIPreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;

import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;

public class VjetDebugPreferenceInitializer extends
		AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = VjetDebugUIPlugin.getDefault()
				.getPreferenceStore();
		store.setDefault(
				IDLTKDebugUIPreferenceConstants.PREF_ACTIVE_FILTERS_LIST,
				"*org.ebayopensource.vjo/VjBootstrap*,dbgp:///*");
		store.setDefault(
				IDLTKDebugUIPreferenceConstants.PREF_INACTIVE_FILTERS_LIST, "");
	}

}
