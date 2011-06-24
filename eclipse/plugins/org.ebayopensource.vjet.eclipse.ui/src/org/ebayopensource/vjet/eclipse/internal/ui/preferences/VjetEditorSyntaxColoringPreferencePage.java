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
import org.eclipse.dltk.mod.ui.preferences.PreferencesMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This class is representation of the vjet/editor/syntax coloring preferences page.
 * 
 * 
 *
 */
public class VjetEditorSyntaxColoringPreferencePage extends
		AbstractConfigurationBlockPreferencePage {

	/*
	 * @see org.eclipse.ui.internal.editors.text.AbstractConfigureationBlockPreferencePage#createConfigurationBlock(org.eclipse.ui.internal.editors.text.OverlayPreferenceStore)
	 */
	@Override
	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		return new VjetEditorColoringConfigurationBlock(overlayPreferenceStore);
	}

	@Override
	protected Label createDescriptionLabel(Composite parent) {
		return null;
	}

	/*
	 * @see org.eclipse.ui.internal.editors.text.AbstractConfigureationBlockPreferencePage#getHelpId()
	 */
	@Override
	protected String getHelpId() {
		return "";
	}

	/*
	 * @see org.eclipse.ui.internal.editors.text.AbstractConfigurationBlockPreferencePage#setDescription()
	 */
	@Override
	protected void setDescription() {
		String description = PreferencesMessages.DLTKEditorPreferencePage_colors;
		setDescription(description);
	}

	/*
	 * @see org.org.eclipse.ui.internal.editors.text.AbstractConfigurationBlockPreferencePage#setPreferenceStore()
	 */
	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(VjetUIPlugin.getDefault().getPreferenceStore());
	}
}
