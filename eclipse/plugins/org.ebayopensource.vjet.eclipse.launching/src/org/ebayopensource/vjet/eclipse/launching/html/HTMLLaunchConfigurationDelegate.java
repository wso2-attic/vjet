/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.launching.html;

import java.io.File;
import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.dltk.mod.launching.ScriptLaunchConfigurationConstants;
import org.eclipse.jdt.internal.launching.LaunchingMessages;
import org.eclipse.jdt.launching.ExecutionArguments;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaLaunchDelegate;
import org.eclipse.jdt.launching.VMRunnerConfiguration;

import org.ebayopensource.vjet.eclipse.internal.launching.VjoRunnerInfo;

/**
 * 
 *
 */
public class HTMLLaunchConfigurationDelegate extends JavaLaunchDelegate {

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration, java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		
		monitor.beginTask(MessageFormat.format("{0}...", new String[]{configuration.getName()}), 3); //$NON-NLS-1$
		
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}
		
		
		try {
			monitor.subTask(LaunchingMessages.JavaLocalApplicationLaunchConfigurationDelegate_Verifying_launch_attributes____1); 
		
			String mainTypeName = verifyMainTypeName(configuration);
			IVMRunner runner = getVMRunner(configuration, mode);
	
			File workingDir = verifyWorkingDirectory(configuration);
			String workingDirName = null;
			if (workingDir != null) {
				workingDirName = workingDir.getAbsolutePath();
			}
			
			// Environment variables
			String[] envp= getEnvironment(configuration);
			
			// Program & VM arguments
			// Program arguments consist of default file type, resource location variable, and browser type - in addition to  
			// arguments passed by the user.
			String pgmArgs = getProgramArguments(configuration);	
			String vmArgs = getVMArguments(configuration);
			ExecutionArguments execArgs = new ExecutionArguments(vmArgs, pgmArgs);
			
			// VM-specific attributes
			Map vmAttributesMap = getVMSpecificAttributesMap(configuration);
			
			//prepare program arguments
			String projectName = configuration.getAttribute(ScriptLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			String fileRelativePath = configuration.getAttribute("org.eclipse.jdt.launching.RESOURCE_LOC", "");
			
			IFile htmlFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(projectName).append(fileRelativePath)); 
			if (!htmlFile.exists())
				return;
			
			String absoluteHtmlPath = htmlFile.getLocation().toPortableString();
			String vjoTypeName = configuration.getAttribute("org.eclipse.jdt.launching.VJO_MAIN_TYPE", "");
			
			String[] programArgurments = {"-Vtype=html", 
					absoluteHtmlPath, 
					vjoTypeName} ;
			
			// Classpath
			String[] newClassPaths = VjoRunnerInfo.getClassPath();
			
			// Create VM config
			VMRunnerConfiguration runConfig = new VMRunnerConfiguration(mainTypeName, newClassPaths);
//			runConfig.setProgramArguments(execArgs.getProgramArgumentsArray());
			runConfig.setProgramArguments(programArgurments);
			runConfig.setEnvironment(envp);
			runConfig.setVMArguments(execArgs.getVMArgumentsArray());
			runConfig.setWorkingDirectory(workingDirName);
			runConfig.setVMSpecificAttributesMap(vmAttributesMap);
	
			// Bootpath
			runConfig.setBootClassPath(getBootpath(configuration));
			
			// check for cancellation
			if (monitor.isCanceled()) {
				return;
			}		
			
			// stop in main
			prepareStopInMain(configuration);
			
			// done the verification phase
			monitor.worked(1);
			
			monitor.subTask(LaunchingMessages.JavaLocalApplicationLaunchConfigurationDelegate_Creating_source_locator____2); 
			// set the default source locator if required
			setDefaultSourceLocator(launch, configuration);
			monitor.worked(1);		
			
			// Launch the configuration - 1 unit of work
			runner.run(runConfig, launch, monitor);
			IDebugTarget[] debugTargets = launch.getDebugTargets();
			
			// check for cancellation
			if (monitor.isCanceled()) {
				return;
			}	
		}
		finally {
			monitor.done();
		}
	}

}
