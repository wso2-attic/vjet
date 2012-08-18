package org.eclipse.dltk.mod.javascript.internal.debug.ui.interpreters;

import org.eclipse.dltk.mod.internal.debug.ui.interpreters.ScriptInterpreterPreferencePage;
import org.eclipse.dltk.mod.internal.debug.ui.interpreters.InterpretersBlock;

public class JavaScriptInterpreterPreferencePage extends ScriptInterpreterPreferencePage {

	//public static final String PAGE_ID = "org.eclipse.dltk.mod.debug.ui.TCLInterpreters";

	public InterpretersBlock createInterpretersBlock() {
		return new JavaScriptInterpretersBlock();
	}
}
