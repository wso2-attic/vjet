package org.eclipse.dltk.mod.javascript.internal.debug.ui.interpreters;

import org.eclipse.dltk.mod.internal.debug.ui.interpreters.AddScriptInterpreterDialog;
import org.eclipse.dltk.mod.internal.debug.ui.interpreters.InterpretersBlock;
import org.eclipse.dltk.mod.javascript.core.JavaScriptNature;
import org.eclipse.dltk.mod.launching.IInterpreterInstall;
import org.eclipse.dltk.mod.launching.ScriptRuntime;

public class JavaScriptInterpretersBlock extends InterpretersBlock {
	protected AddScriptInterpreterDialog createInterpreterDialog(IInterpreterInstall standin) {
		AddJavaScriptInterpreterDialog dialog = new AddJavaScriptInterpreterDialog(this, 
				getShell(), ScriptRuntime.getInterpreterInstallTypes(getCurrentNature()), 
				standin);
		return dialog;
	}

	protected String getCurrentNature() {
		return JavaScriptNature.NATURE_ID;
	}
}
