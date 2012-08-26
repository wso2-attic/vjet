package org.eclipse.dltk.mod.javascript.internal.debug.ui;

import org.eclipse.dltk.mod.debug.ui.AbstractDebugUILanguageToolkit;
import org.eclipse.dltk.mod.javascript.internal.debug.JavaScriptDebugConstants;
import org.eclipse.jface.preference.IPreferenceStore;

public class JavaScriptDebugUILanguageToolkit extends
		AbstractDebugUILanguageToolkit {

	/*
	 * @see
	 * org.eclipse.dltk.mod.debug.ui.IDLTKDebugUILanguageToolkit#getDebugModelId()
	 */
	public String getDebugModelId() {
		return JavaScriptDebugConstants.DEBUG_MODEL_ID;
	}

	/*
	 * @see
	 * org.eclipse.dltk.mod.debug.ui.IDLTKDebugUILanguageToolkit#getPreferenceStore
	 * ()
	 */
	public IPreferenceStore getPreferenceStore() {
		return JavaScriptDebugUIPlugin.getDefault().getPreferenceStore();
	}

	public String[] getVariablesViewPreferencePages() {
		return new String[] { "org.eclipse.dltk.mod.javascript.preferences.debug.detailFormatters" };
	}

}
