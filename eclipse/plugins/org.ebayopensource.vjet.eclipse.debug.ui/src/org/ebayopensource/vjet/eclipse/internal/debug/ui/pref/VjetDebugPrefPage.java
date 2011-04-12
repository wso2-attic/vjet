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
import org.eclipse.dltk.mod.debug.core.DLTKDebugPreferenceConstants;
import org.eclipse.dltk.mod.debug.ui.preferences.AbstractDebuggingOptionsBlock;
import org.eclipse.dltk.mod.debug.ui.preferences.ScriptDebugPreferencesMessages;
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

public class VjetDebugPrefPage extends AbstractConfigurationBlockPropertyAndPreferencePage {

	private static PreferenceKey BREAK_ON_FIRST_LINE =
        new PreferenceKey(VjetDebugPlugin.PLUGIN_ID,
            DLTKDebugPreferenceConstants.PREF_DBGP_BREAK_ON_FIRST_LINE);

    private static PreferenceKey ENABLE_DBGP_LOGGING =
        new PreferenceKey(VjetDebugPlugin.PLUGIN_ID,
            DLTKDebugPreferenceConstants.PREF_DBGP_ENABLE_LOGGING);
    
	private static String PREFERENCE_PAGE_ID = "org.ebayopensource.vjet.eclipse.preferences.debug";
	private static String PROPERTY_PAGE_ID = "org.ebayopensource.vjet.eclipse.propertyPage.debug";
    
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new AbstractDebuggingOptionsBlock(newStatusChangedListener,
				project, getKeys(), container) {

			protected PreferenceKey getBreakOnFirstLineKey() {
				return BREAK_ON_FIRST_LINE;
			}

			protected PreferenceKey getDbgpLoggingEnabledKey() {
				return ENABLE_DBGP_LOGGING;
			}

			@Override
			protected Control createOptionsBlock(Composite parent) {
				Composite composite = SWTFactory.createComposite(parent, parent
						.getFont(), 1, 1, GridData.FILL_HORIZONTAL);

				createSettingsGroup(composite);
				return composite;
			}
			
			
			private void createSettingsGroup(Composite parent) {
				final Group group = SWTFactory.createGroup(parent,
						ScriptDebugPreferencesMessages.EngineSettingsLabel, 1, 1,
						GridData.FILL_HORIZONTAL);

				// Break on first line
				Button b = SWTFactory.createCheckButton(group,
						ScriptDebugPreferencesMessages.BreakOnFirstLineLabel, null,
						false, 1);

				bindControl(b, getBreakOnFirstLineKey(), null);

				// Enable dbgp logging
				b = SWTFactory.createCheckButton(group,
						ScriptDebugPreferencesMessages.EnableDbgpLoggingLabel, null,
						false, 1);
				bindControl(b, getDbgpLoggingEnabledKey(), null);
			}
			
		};
	}

	private PreferenceKey[] getKeys() {
		return new PreferenceKey[] { BREAK_ON_FIRST_LINE, ENABLE_DBGP_LOGGING };
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
		setDescription(VjetDebugPrefMessages.VjetDebugPreferencePage_description);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(new PreferencesAdapter(VjetDebugPlugin.getDefault().getPluginPreferences()));
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
