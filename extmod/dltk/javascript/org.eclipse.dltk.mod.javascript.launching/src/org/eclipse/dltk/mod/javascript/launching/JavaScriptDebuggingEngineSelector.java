package org.eclipse.dltk.mod.javascript.launching;

import org.eclipse.dltk.mod.core.DLTKIdContributionSelector;
import org.eclipse.dltk.mod.core.PreferencesLookupDelegate;
import org.eclipse.dltk.mod.javascript.internal.debug.JavaScriptDebugConstants;
import org.eclipse.dltk.mod.javascript.internal.debug.JavaScriptDebugPlugin;

/**
 * JavaScript debugging engine id based selector
 */
public class JavaScriptDebuggingEngineSelector extends
		DLTKIdContributionSelector {
	/*
	 * @see org.eclipse.dltk.mod.core.DLTKIdContributionSelector#getSavedContributionId(org.eclipse.dltk.mod.core.PreferencesLookupDelegate)
	 */
	protected String getSavedContributionId(PreferencesLookupDelegate delegate) {
		return delegate.getString(JavaScriptDebugPlugin.PLUGIN_ID,
				JavaScriptDebugConstants.DEBUGGING_ENGINE_ID_KEY);
	}

}
