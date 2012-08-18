package org.eclipse.dltk.mod.javascript.internal.debug.ui;

import org.eclipse.dltk.mod.debug.ui.DLTKDebugUIPluginPreferenceInitializer;
import org.eclipse.dltk.mod.javascript.core.JavaScriptNature;

public class JavaScriptDebugUIPreferenceInitializer extends
		DLTKDebugUIPluginPreferenceInitializer {

	protected String getNatureId() {
		return JavaScriptNature.NATURE_ID;
	}

}
