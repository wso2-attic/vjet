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
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.dltk.mod.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore;

/**
 * 
 *
 */
public class VJOBuildPathPreferencePage extends
		AbstractConfigurationBlockPreferencePage {

	public static final String ID = "org.ebayopensource.vjet.eclipse.ui.vjet.BuildPath";
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#createConfigurationBlock(org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore)
	 */
	@Override
	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		return new VJOBuildPathConfigurationBlock(overlayPreferenceStore);
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
		setDescription(VjetPreferenceMessages.NewVJOProjectPreferencePage_description);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#setPreferenceStore()
	 */
	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(VjetUIPlugin.getDefault().getPreferenceStore());
	}

}
