package org.eclipse.dltk.mod.core.tests.launching;

import org.eclipse.dltk.mod.core.environment.IFileHandle;

public interface IFileVisitor {

	boolean visit(IFileHandle file);

}
