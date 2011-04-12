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
package org.ebayopensource.vjet.eclipse.internal.debug.debugger.pref;

import org.eclipse.dltk.mod.ui.PreferencesAdapter;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.dltk.mod.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore;

import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugPlugin;

/**
 * preference page for attaching vjet debugger to java-based project.
 * 
 * 
 *
 */
public class VjetDebuggerAttachmentPreferencePage extends
		AbstractConfigurationBlockPreferencePage {

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#createConfigurationBlock(org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore)
	 */
	@Override
	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		return new DebuggerAttachmentConfigurationBlock(overlayPreferenceStore);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#getHelpId()
	 */
	@Override
	protected String getHelpId() {
		return "";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#setDescription()
	 */
	@Override
	protected void setDescription() {
		setDescription("VJET Debugger Attachment");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#setPreferenceStore()
	 */
	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(new PreferencesAdapter(VjetDebugPlugin.getDefault()
				.getPluginPreferences()));
	}

}
