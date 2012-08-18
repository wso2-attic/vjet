package org.eclipse.dltk.mod.debug.core.model;

import org.eclipse.dltk.mod.internal.debug.core.model.ScriptThread;

public interface IScriptStack {
	ScriptThread getThread();

	int size();

	boolean hasFrames();

	IScriptStackFrame[] getFrames();

	IScriptStackFrame getTopFrame();
}
