package org.eclipse.dltk.mod.internal.launching.debug;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.dltk.mod.core.DLTKContributedExtension;
import org.eclipse.dltk.mod.debug.core.ScriptDebugManager;
import org.eclipse.dltk.mod.launching.IInterpreterInstall;
import org.eclipse.dltk.mod.launching.IInterpreterRunner;
import org.eclipse.dltk.mod.launching.IInterpreterRunnerFactory;
import org.eclipse.dltk.mod.launching.debug.IDebuggingEngine;

public class DebuggingEngine extends DLTKContributedExtension implements
		IDebuggingEngine {

	private IInterpreterRunnerFactory factory;

	public DebuggingEngine(IInterpreterRunnerFactory factory,
			IConfigurationElement config) {
		this.factory = factory;

		/*
		 * this is a cheat - this class contains all the attributes of the
		 * configured extension, so leverage the code DLTKContributedExtension
		 * already provides
		 */
		setInitializationData(config, null, null);
	}

	public String getModelId() {
		return ScriptDebugManager.getInstance().getDebugModelByNature(
				getNatureId());
	}

	public IInterpreterRunner getRunner(IInterpreterInstall install) {
		return factory.createRunner(install);
	}
}
