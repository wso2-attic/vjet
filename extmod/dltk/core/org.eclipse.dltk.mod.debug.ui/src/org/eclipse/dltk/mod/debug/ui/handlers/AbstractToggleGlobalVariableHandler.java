package org.eclipse.dltk.mod.debug.ui.handlers;

import org.eclipse.dltk.mod.debug.core.DLTKDebugPreferenceConstants;
import org.eclipse.dltk.mod.debug.core.model.IScriptDebugTarget;

/**
 * Abstract handler implementation that can be used to toggle the display of
 * 'global' debugging variables.
 */
public abstract class AbstractToggleGlobalVariableHandler extends
		AbstractToggleVariableHandler {

	/*
	 * @see org.eclipse.dltk.mod.debug.ui.handlers.AbstractToggleVariableHandler#getVariableDisplayPreferenceKey()
	 */
	protected String getVariableDisplayPreferenceKey() {
	    return DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_GLOBAL;
	}

	/*
	 * @see org.eclipse.dltk.mod.debug.ui.handlers.AbstractToggleVariableHandler#toggleVariableDisplay(org.eclipse.dltk.mod.debug.core.model.IScriptDebugTarget, boolean)
	 */
	protected final void toggleVariableDisplay(IScriptDebugTarget target, boolean enabled) {
	    target.toggleGlobalVariables(enabled);
	}

}
