package org.eclipse.dltk.mod.launching;


public interface IInterpreterRunnerFactory {
	IInterpreterRunner createRunner(IInterpreterInstall install);
}
