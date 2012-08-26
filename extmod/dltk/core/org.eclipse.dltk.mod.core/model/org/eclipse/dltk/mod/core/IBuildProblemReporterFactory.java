package org.eclipse.dltk.mod.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.mod.internal.core.builder.BuildProblemReporter;

public interface IBuildProblemReporterFactory {

	BuildProblemReporter createReporter(IResource resource);

}
