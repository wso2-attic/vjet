package org.eclipse.dltk.mod.javascript.internal.ui.preferences;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.mod.javascript.core.JavaScriptPlugin;
import org.eclipse.dltk.mod.ui.preferences.TodoTaskAbstractPreferencePage;

public class JavaScriptTodoTaskPreferencePage extends
		TodoTaskAbstractPreferencePage {

	protected Preferences getPluginPreferences() {
		return JavaScriptPlugin.getDefault().getPluginPreferences();
	}

	protected String getHelpId() {
		return null;
	}

	protected void setDescription() {
		setDescription(JavaScriptPreferenceMessages.TodoTaskDescription);
	}
}
