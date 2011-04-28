package org.ebayopensource.eclipse.vjet.javalaunch.utils;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;


/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = LaunchUtilPlugin.getDefault().getPreferenceStore();
		store.setDefault(LaunchUtilPlugin.PLUGIN_PREFERENCE_SCOPE, false);
	}

}
