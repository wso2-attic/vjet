package org.eclipse.dltk.mod.javascript.jsjdtdebugger;

import org.eclipse.dltk.mod.launching.IInterpreterInstall;
import org.eclipse.dltk.mod.launching.IInterpreterRunner;
import org.eclipse.dltk.mod.launching.IInterpreterRunnerFactory;

public class JavaScriptAndJDTDebuggerRunnerFactory implements
		IInterpreterRunnerFactory {

	/*
	 * @see org.eclipse.dltk.mod.launching.IInterpreterRunnerFactory#createRunner(org.eclipse.dltk.mod.launching.IInterpreterInstall)
	 */
	public IInterpreterRunner createRunner(IInterpreterInstall install) {
		return new JavaScriptAndJDTDebuggerRunner(install);
	}
}
