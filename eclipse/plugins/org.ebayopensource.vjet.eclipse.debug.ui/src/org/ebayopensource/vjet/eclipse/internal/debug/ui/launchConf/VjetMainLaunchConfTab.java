/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.launchConf;

import org.eclipse.dltk.mod.core.PreferencesLookupDelegate;
import org.eclipse.dltk.mod.debug.core.DLTKDebugPreferenceConstants;
import org.eclipse.dltk.mod.debug.ui.launchConfigurations.MainLaunchConfigurationTab;
import org.eclipse.swt.widgets.Composite;

import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.debug.ui.pref.VjetDebugPrefMessages;

public class VjetMainLaunchConfTab extends MainLaunchConfigurationTab {

	public VjetMainLaunchConfTab(String mode) {
		super(mode);
	}

	@Override
	protected boolean breakOnFirstLinePrefEnabled(
			PreferencesLookupDelegate delegate) {
		return delegate.getBoolean(VjetPlugin.PLUGIN_ID,
				DLTKDebugPreferenceConstants.PREF_DBGP_BREAK_ON_FIRST_LINE);
	}

	@Override
	protected boolean dbpgLoggingPrefEnabled(PreferencesLookupDelegate delegate) {
		return delegate.getBoolean(VjetPlugin.PLUGIN_ID,
				DLTKDebugPreferenceConstants.PREF_DBGP_ENABLE_LOGGING);
	}

	protected void doCreateControl(Composite composite) {
		createMainModuleEditor(composite,
				VjetDebugPrefMessages.VjetDebugDialogMainClass_description);
	}

	@Override
	protected String getNatureID() {
		return VjoNature.NATURE_ID;
	}
}
