package org.ebayopensource.vjet.eclipse.core;

import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ITypeHierarchy;

public interface IVjoTypeHierarchy extends ITypeHierarchy {
	/**
	 * Returns the java project this hierarchy was created in.
	 */
	public IScriptProject javaProject();
}
