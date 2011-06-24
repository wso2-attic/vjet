/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.dltk.mod.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore;

public class VjetGlobalPreferencesPage extends
		AbstractConfigurationBlockPreferencePage {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#createConfigurationBlock(org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore)
	 */
	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		// TODO Auto-generated method stub
		return new VjetGlobalConfigurationBlock(overlayPreferenceStore, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#getHelpId()
	 */
	protected String getHelpId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#setDescription()
	 */
	protected void setDescription() {
		String description = VjetPreferenceMessages.VjetGlobalPreferencePage_description;
		setDescription(description);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#setPreferenceStore()
	 */
	protected void setPreferenceStore() {
		setPreferenceStore(VjetUIPlugin.getDefault().getPreferenceStore());
	}

}
