package org.eclipse.dltk.mod.debug.core.eval;

import org.eclipse.dltk.mod.debug.core.model.IScriptDebugTarget;

public interface IScriptEvaluationCommand {
	IScriptDebugTarget getScriptDebugTarget();

	IScriptEvaluationResult syncEvaluate();

	void asyncEvaluate(IScriptEvaluationListener listener);

	void dispose();
}
