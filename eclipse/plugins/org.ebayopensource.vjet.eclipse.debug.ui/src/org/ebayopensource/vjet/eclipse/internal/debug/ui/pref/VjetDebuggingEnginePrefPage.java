/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.pref;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.debug.ui.preferences.AbstractDebuggingEngineOptionsBlock;
import org.eclipse.dltk.mod.ui.PreferencesAdapter;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.mod.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.mod.ui.preferences.PreferenceKey;
import org.eclipse.dltk.mod.ui.util.IStatusChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugConstants;
import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugPlugin;


public class VjetDebuggingEnginePrefPage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	private static PreferenceKey DEBUGGING_ENGINE = new PreferenceKey(
			VjetDebugPlugin.PLUGIN_ID,
			VjetDebugConstants.DEBUGGING_ENGINE_ID_KEY);

	private static final String PREFERENCE_PAGE_ID = "org.ebayopensource.vjet.eclipse.preferences.debug.engines";
	private static final String PROPERTY_PAGE_ID = "org.ebayopensource.vjet.eclipse.propertyPage.debug.engines";

	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new AbstractDebuggingEngineOptionsBlock(
				newStatusChangedListener, project, getKeys(), container) {

			protected String getNatureId() {
				return VjoNature.NATURE_ID;
			}

			protected PreferenceKey getSavedContributionKey() {
				return DEBUGGING_ENGINE;
			}
		};
	}

	private PreferenceKey[] getKeys() {
		return new PreferenceKey[] { DEBUGGING_ENGINE };
	}

	@Override
	protected String getHelpId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getProjectHelpId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setDescription() {
		setDescription(VjetDebugPrefMessages.VjetDebugEnginePreferencePage_description);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(new PreferencesAdapter(VjetDebugPlugin.getDefault()
				.getPluginPreferences()));
	}

	@Override
	protected String getPreferencePageId() {
		return PREFERENCE_PAGE_ID;
	}

	@Override
	protected String getPropertyPageId() {
		return PROPERTY_PAGE_ID;
	}

}
