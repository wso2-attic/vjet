package org.eclipse.dltk.mod.javascript.internal.core;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.mod.compiler.task.TodoTaskPreferences;
import org.eclipse.dltk.mod.javascript.core.JavaScriptPlugin;

public class JavaScriptCorePreferenceInitializer extends
		AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		Preferences store = JavaScriptPlugin.getDefault()
				.getPluginPreferences();

		TodoTaskPreferences.initializeDefaultValues(store);
	}
}
