/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.mod.ui.preferences.TodoTaskAbstractPreferencePage;

public class VjetTodoTaskPreferencePage extends TodoTaskAbstractPreferencePage {

	@Override
	protected Preferences getPluginPreferences() {
		//return vjetUiPlugin as we use VjetUIPreferenceInitializer to initialize preference defaults
		return VjetUIPlugin.getDefault().getPluginPreferences();
	}

	@Override
	protected String getHelpId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setDescription() {
		setDescription("Task Tags");
	}

}
