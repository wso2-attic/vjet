package org.eclipse.dltk.mod.javascript.internal.debug.ui.interpreters;

import org.eclipse.dltk.mod.debug.ui.launchConfigurations.InterpreterTab;
import org.eclipse.dltk.mod.internal.debug.ui.interpreters.AbstractInterpreterComboBlock;
import org.eclipse.dltk.mod.javascript.core.JavaScriptNature;

public class JavaScriptInterpreterTab extends InterpreterTab {

	protected AbstractInterpreterComboBlock getInterpreterBlock() {
		return new JavaScriptInterpreterComboBlock(getMainTab());
	}

	protected String getNature() {
		return JavaScriptNature.NATURE_ID;
	}

}
