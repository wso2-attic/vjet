package org.eclipse.dltk.mod.launching.debug;

import org.eclipse.dltk.mod.core.IDLTKContributedExtension;
import org.eclipse.dltk.mod.launching.IInterpreterInstall;
import org.eclipse.dltk.mod.launching.IInterpreterRunner;

public interface IDebuggingEngine extends IDLTKContributedExtension {

	String getModelId();
	
	IInterpreterRunner getRunner(IInterpreterInstall install);
}
