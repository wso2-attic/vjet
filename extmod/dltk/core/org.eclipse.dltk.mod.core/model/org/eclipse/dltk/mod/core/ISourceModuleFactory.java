/**
 * 
 */
package org.eclipse.dltk.mod.core;

import org.eclipse.core.resources.IStorage;
import org.eclipse.dltk.mod.internal.core.ExternalSourceModule;
import org.eclipse.dltk.mod.internal.core.ScriptFolder;

/**
 * @author MPeleshchyshyn
 * 
 */
public interface ISourceModuleFactory {
	public ISourceModule createSourceModule(ScriptFolder parent, String name,
			WorkingCopyOwner owner);

	public ExternalSourceModule createExternalSourceModule(ScriptFolder parent,
			String name, WorkingCopyOwner owner, boolean readOnly,
			IStorage storage);
}
