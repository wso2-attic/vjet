package org.eclipse.dltk.mod.javascript.internal.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.mod.javascript.console.JavaScriptConsoleConstants;
import org.eclipse.dltk.mod.javascript.internal.debug.ui.JavaScriptDebugUIPlugin;
import org.eclipse.jface.preference.IPreferenceStore;


public class JavaScriptConsolePreferenceInitializer extends AbstractPreferenceInitializer {

	public JavaScriptConsolePreferenceInitializer() {
	}

	public void initializeDefaultPreferences() {
		IPreferenceStore store = JavaScriptDebugUIPlugin.getDefault()
				.getPreferenceStore();
		store.setDefault(JavaScriptConsoleConstants.PREF_NEW_PROMPT,
				JavaScriptConsoleConstants.DEFAULT_NEW_PROMPT);
		store.setDefault(JavaScriptConsoleConstants.PREF_CONTINUE_PROMPT,
				JavaScriptConsoleConstants.DEFAULT_CONTINUE_PROMPT);
	}

}
