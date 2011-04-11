package org.eclipse.dltk.mod.debug.ui.launchConfigurations;

public interface IMainLaunchConfigurationTabListenerManager {
	public void addListener(IMainLaunchConfigurationTabListener listener);

	public void removeListener(IMainLaunchConfigurationTabListener listener);
}
