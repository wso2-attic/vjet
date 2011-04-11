package org.eclipse.dltk.mod.debug.core;

import org.eclipse.dltk.mod.debug.core.model.IScriptThread;

public interface ISmartStepEvaluator {
	boolean skipSuspend(String[] filters, IScriptThread thread);
}
