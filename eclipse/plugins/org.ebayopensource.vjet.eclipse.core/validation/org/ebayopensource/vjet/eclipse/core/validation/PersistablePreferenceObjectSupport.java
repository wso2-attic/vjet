/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.validation;

import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.eclipse.core.resources.IProject;

public abstract class PersistablePreferenceObjectSupport {

	private boolean isEnabledByDefault = true;
	
	protected abstract String getPreferenceId();
	
	private String getPreferenceIdForPluginPreference() {
		return VjetPlugin.PLUGIN_ID + "." + getPreferenceId();
	}
	
	public boolean isEnabled(IProject project) {
		if (project != null) {
			return Vjet2CorePreferences.getProjectPreferences(project)
					.getBoolean(getPreferenceId(), this.isEnabledByDefault);
		}
		else {
			return VjetPlugin.getDefault().getPluginPreferences().getBoolean(
					getPreferenceIdForPluginPreference());
		}
	}
}
