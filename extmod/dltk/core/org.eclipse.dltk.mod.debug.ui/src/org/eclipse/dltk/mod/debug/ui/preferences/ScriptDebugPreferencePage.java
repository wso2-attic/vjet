/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.debug.ui.preferences;

import org.eclipse.dltk.mod.debug.core.DLTKDebugPlugin;
import org.eclipse.dltk.mod.internal.ui.text.PreferencesAdapter;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.dltk.mod.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore;

public class ScriptDebugPreferencePage extends
		AbstractConfigurationBlockPreferencePage {
	
	public static final String PAGE_ID = "org.eclipse.dltk.mod.preferences.debug"; //$NON-NLS-1$

	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		return new ScriptDebugConfigurationBlock(overlayPreferenceStore, this);
	}

	protected String getHelpId() {
		return null;
	}

	protected void setDescription() {
		setDescription(ScriptDebugPreferencesMessages.GeneralPreferencesDescription);
	}

	protected void setPreferenceStore() {
		setPreferenceStore(new PreferencesAdapter(DLTKDebugPlugin.getDefault()
				.getPluginPreferences()));
	}

	public boolean performOk() {
		super.performOk();
		DLTKDebugPlugin.getDefault().savePluginPreferences();
		return true;
	}
}
