/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.debugger.pref;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.mod.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.mod.ui.preferences.PreferenceKey;
import org.eclipse.dltk.mod.ui.util.IStatusChangeListener;
import org.eclipse.dltk.mod.ui.util.SWTFactory;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugPlugin;

public class VjetDebuggerPrefPage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	private static String PREFERENCE_PAGE_ID = "org.ebayopensource.vjet.eclipse.preferences.debug.engines.vjetdebugger";
	private static String PROPERTY_PAGE_ID = "org.ebayopensource.vjet.eclipse.propertyPage.debug.engines.vjetdebugger";

	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {

		return new AbstractOptionsBlock(newStatusChangedListener, project,
				new PreferenceKey[] {}, container) {

			protected Control createOptionsBlock(Composite parent) {
				
				Composite composite = SWTFactory.createComposite(parent, parent
						.getFont(), 1, 1, GridData.FILL);
				SWTFactory.createLabel(composite,
						VjetDebuggerPrefMessages.NoSettingsAvailable, 1);

				return composite;
			}
		};

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
		setDescription(VjetDebuggerPrefMessages.PreferencesDescription);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(VjetDebugPlugin.getDefault().getPreferenceStore());
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
