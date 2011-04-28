package org.ebayopensource.eclipse.vjet.javalaunch.utils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchListener;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;


/**
 * 
 *
 */
public class LaunchListener implements ILaunchListener {
	/**
	 * Logger for this class
	 */
	// private static final Logger logger =
	// Logger.getLogger(LaunchListener.class);

	public LaunchListener() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * ILaunchListener methods
	 */
	public void launchRemoved(ILaunch launch) {
		// logger.info("launchRemoved(ILaunch) - start");
		
		
	}

	public void launchAdded(ILaunch launch) {

		// logger.info("launchAdded(ILaunch):");
		addSourcePathToVMParameters(launch);
	}

	public void launchChanged(ILaunch launch) {

		// logger.info("launchChanged(ILaunch) - start");
		addSourcePathToVMParameters(launch);
	}

	private void addSourcePathToVMParameters(ILaunch launch) {

		ILaunchConfiguration launchConfiguration = launch
				.getLaunchConfiguration();
		IProject launchProject = null;

		if (launchConfiguration == null) {
			// No configuration associated with this launch
			return;
		}

		try {

			// Get the name of the project this launch configuration is
			// associated with
			String projectName = launchConfiguration.getAttribute(
					"org.eclipse.jdt.launching.PROJECT_ATTR", "");
			if (projectName != null && projectName.length() > 0) {
				launchProject = EclipseResourceUtils
						.getWorkspaceProject(projectName);
			}
		} catch (CoreException e1) {
			e1.printStackTrace();
		}

		if (launchProject == null) {
			// No project associated with this launch configuration
			return;
		}

		try {
			String sourcePath = SourcePathUtil
					.getSoucePathString(launchProject);

			String vmArgs = "";

			if (launchConfiguration == null) {
				return;
			}
			vmArgs = launchConfiguration.getAttribute(
					IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, "");

			vmArgs = SourcePathUtil.configureVmArguments(sourcePath, vmArgs);

			ILaunchConfigurationWorkingCopy wc = launchConfiguration
					.getWorkingCopy();
			wc.setAttribute(
					IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmArgs);
			wc.doSave();

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
