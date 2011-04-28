package org.ebayopensource.eclipse.vjet.javalaunch.utils;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.ui.IStartup;

public class LaunchUtilEarlyStartup implements IStartup {

	@Override
	public void earlyStartup() {
		  // Add a launch listener so we can change the vm args.
        ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
        LaunchListener listener = new LaunchListener();
        launchManager.addLaunchListener(listener);

	}

}
