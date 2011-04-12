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
import org.eclipse.dltk.mod.ui.PreferencesAdapter;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.mod.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.mod.ui.preferences.PreferenceKey;
import org.eclipse.dltk.mod.ui.util.IStatusChangeListener;
import org.eclipse.dltk.mod.ui.util.SWTFactory;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugPlugin;
import org.ebayopensource.vjet.eclipse.internal.debug.debugger.pref.VjetDebugPreferenceConstants;

public class VjetVariableViewPrefPage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	private static PreferenceKey	ENABLE_MODIFICATION_CONSTRAINTS	= new PreferenceKey(
																			VjetDebugPlugin.PLUGIN_ID,
																			VjetDebugPreferenceConstants.PREF_VJET_ENABLE_MODIFICATION_CONSTRAINTS);

	private static String			PREFERENCE_PAGE_ID				= "org.ebayopensource.vjet.eclipse.preferences.debug"; //$NON-NLS-1$

	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new AbstractOptionsBlock(newStatusChangedListener, project,
				getKeys(), container) {

			protected PreferenceKey getModificationConstrainsEnabledKey() {
				return ENABLE_MODIFICATION_CONSTRAINTS;
			}

			@Override
			protected Control createOptionsBlock(Composite parent) {
				Composite composite = SWTFactory.createComposite(parent, parent
						.getFont(), 1, 1, GridData.FILL_HORIZONTAL);

				createSettingsGroups(composite);
				return composite;
			}

			private void createSettingsGroups(Composite parent) {
				createVariablesViewSettingGroup(parent);
			}

			private void createVariablesViewSettingGroup(Composite parent) {
				final Group group = SWTFactory.createGroup(parent,
						VjetDebugPrefMessages.VjetVariableViewPrefPage_group_text, 1, 1,
						GridData.FILL_HORIZONTAL);

				// Enable modification constraints
				Button b = SWTFactory.createCheckButton(group,
						VjetDebugPrefMessages.VjetVariableViewPrefPage_enableModificationConstraintsButton_text, null,
						false, 1);

				bindControl(b, getModificationConstrainsEnabledKey(), null);
			}

		};
	}

	private PreferenceKey[] getKeys() {
		return new PreferenceKey[] { ENABLE_MODIFICATION_CONSTRAINTS };
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
		setDescription(VjetDebugPrefMessages.VjetVariableViewPrefPage_description);
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
		return null;
	}

}
