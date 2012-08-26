package org.eclipse.dltk.mod.javascript.internal.debug.ui.handlers;

import org.eclipse.dltk.mod.debug.ui.handlers.AbstractToggleLocalVariableHandler;
import org.eclipse.dltk.mod.javascript.internal.debug.JavaScriptDebugConstants;
import org.eclipse.dltk.mod.javascript.internal.debug.JavaScriptDebugPlugin;
import org.eclipse.dltk.mod.ui.PreferencesAdapter;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Toggles the display of javascript local variables in the debug 'Variables'
 * view
 */
public class ToggleLocalVariablesHandler extends
		AbstractToggleLocalVariableHandler {

	/*
	 * @see org.eclipse.dltk.mod.debug.ui.handlers.AbstractToggleVariableHandler#getModelId()
	 */
	protected String getModelId() {
		return JavaScriptDebugConstants.DEBUG_MODEL_ID;
	}

	/*
	 * @see org.eclipse.dltk.mod.debug.ui.handlers.AbstractToggleVariableHandler#getPreferenceStore()
	 */
	protected IPreferenceStore getPreferenceStore() {
		return new PreferencesAdapter(JavaScriptDebugPlugin.getDefault()
				.getPluginPreferences());
	}
}
