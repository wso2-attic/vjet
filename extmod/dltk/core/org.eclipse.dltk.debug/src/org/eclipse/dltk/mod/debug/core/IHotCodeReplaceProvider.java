package org.eclipse.dltk.mod.debug.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.DebugException;
import org.eclipse.dltk.mod.debug.core.model.IScriptDebugTarget;

public interface IHotCodeReplaceProvider {
	void performCodeReplace(IScriptDebugTarget target, IResource[] resources)
			throws DebugException;
}
