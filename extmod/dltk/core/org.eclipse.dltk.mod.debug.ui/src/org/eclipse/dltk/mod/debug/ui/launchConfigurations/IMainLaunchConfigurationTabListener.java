package org.eclipse.dltk.mod.debug.ui.launchConfigurations;

import org.eclipse.core.resources.IProject;

public interface IMainLaunchConfigurationTabListener {
	void projectChanged(IProject project);

	void interactiveChanged(boolean state);
}
