/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchListener;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.internal.core.LaunchConfiguration;
import org.eclipse.debug.internal.core.LaunchConfigurationWorkingCopy;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.environment.EnvironmentManager;
import org.eclipse.dltk.mod.core.environment.IEnvironment;
import org.eclipse.dltk.mod.dbgp.DbgpSessionIdGenerator;
import org.eclipse.dltk.mod.debug.core.DLTKDebugLaunchConstants;
import org.eclipse.dltk.mod.debug.core.DLTKDebugPlugin;
import org.eclipse.dltk.mod.debug.core.IDbgpService;
import org.eclipse.dltk.mod.debug.core.model.IScriptDebugTarget;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptDebugTarget;
import org.eclipse.dltk.mod.launching.InterpreterConfig;
import org.eclipse.dltk.mod.launching.ScriptLaunchConfigurationConstants;
import org.eclipse.jdt.internal.launching.JavaSourceLookupDirector;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.sourcelookup.JavaSourceLocator;

import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.internal.debug.debugger.VjetDebugEngineRunner;
import org.ebayopensource.vjet.eclipse.internal.debug.debugger.pref.VjetDebugPreferenceConstants;
import org.ebayopensource.vjet.eclipse.internal.launching.GenericVjetInstallType;
import org.ebayopensource.vjet.eclipse.internal.launching.SourceLocatorProxy;
import org.ebayopensource.vjet.eclipse.internal.launching.VjetSourceLookupDirector;
import org.ebayopensource.vjet.eclipse.launching.VjetLaunchingPlugin;

public class LaunchListener implements ILaunchListener {
	private static final String		JAVA_APPLICATION_LAUNCH_TYPE	= "org.eclipse.jdt.launching.localJavaApplication";
	private static final String		DERVLET_APPLICATION_LAUNCH_TYPE	= "com.ebay.darwin.tools.eclipse.plugin.launcher.dervlet";
	private static final String		JSUNIT_LAUNCH_TYPE				= "com.ebay.darwin.tools.eclipse.plugin.launcher.jsunit";

	private static final String[]	ATTACHABLE_LAUNCH_TYPES			= {
			JAVA_APPLICATION_LAUNCH_TYPE, DERVLET_APPLICATION_LAUNCH_TYPE,
			JSUNIT_LAUNCH_TYPE										};

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
		if (!ILaunchManager.DEBUG_MODE.equals(launch.getLaunchMode())) {
			return;
		}
		try {
			// determine whether to attach script debug target according to
			// preference value
			boolean attachDebugger = VjetDebugPlugin
					.getDefault()
					.getPreferenceStore()
					.getBoolean(
							VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH);
			if (!attachDebugger)
				return;

			// currently, just attach vjet debugger for java application, 
			// dervlet application, and JsUnit application
			ILaunchConfiguration launchConfiguration = launch
					.getLaunchConfiguration();
			String launchTypeId = launchConfiguration.getType().getIdentifier();
			if (!canAttachVjetDebugger(launchTypeId))
				return;

			// TODO: check whether the project contains a script nature?

			String projectName = launchConfiguration.getAttribute(
					IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");

			IScriptProject sp = DLTKCore.create(
					ResourcesPlugin.getWorkspace().getRoot()).getScriptProject(
					projectName);

			IEnvironment env = EnvironmentManager.getEnvironment(sp);

			ILaunchConfiguration config = launchConfiguration;
			ILaunchConfigurationWorkingCopy copy = new LaunchWorkingCopy(
					(LaunchConfiguration) config);

			boolean breakOnFirstLine = VjetDebugPlugin
					.getDefault()
					.getPreferenceStore()
					.getBoolean(
							VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH_BREAK_FIRST_LINE);
			copy
					.setAttribute(
							ScriptLaunchConfigurationConstants.ENABLE_BREAK_ON_FIRST_LINE,
							breakOnFirstLine);
			boolean useInteractiveConsole = VjetDebugPlugin
					.getDefault()
					.getPreferenceStore()
					.getBoolean(
							VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH_USE_INTERACTIVE_CONSOLE);
			copy
					.setAttribute(
							ScriptLaunchConfigurationConstants.ATTR_USE_INTERACTIVE_CONSOLE,
							useInteractiveConsole);
			copy.setAttribute(
					ScriptLaunchConfigurationConstants.ATTR_DLTK_CONSOLE_ID,
					Long.toString(System.currentTimeMillis()));
			copy.setAttribute(
					ScriptLaunchConfigurationConstants.ATTR_DLTK_DBGP_REMOTE,
					true);
			copy.setAttribute(
					ScriptLaunchConfigurationConstants.ATTR_PROJECT_NAME,
					projectName);
			copy.setAttribute(DLTKDebugLaunchConstants.ATTR_DEBUG_CONSOLE,
					Boolean.TRUE.toString());
			// copy.setAttribute("org.eclipse.debug.core.capture_output", true);
			copy.setAttribute("org.eclipse.debug.ui.ATTR_CONSOLE_ENCODING",
					(String) null);
			copy.setAttribute("debugging_engine_id",
					(String) "org.ebayopensource.vjet.eclipse.debug");

			boolean enableDbgpLogging = VjetDebugPlugin
					.getDefault()
					.getPreferenceStore()
					.getBoolean(
							VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH_ENABLE_DBGP_LOGGING);
			copy.setAttribute("enableDbgpLogging", enableDbgpLogging);

			// StringBuilder vjetArgs = new StringBuilder();
			// vjetArgs.append("-DVJETDebugHost=").append(config.getProperty(DbgpConstants.HOST_PROP));
			//			
			// /**
			// * vmArgs.add( "-DVJETDebugHost=" +
			// config.getProperty(DbgpConstants.HOST_PROP));
			// vmArgs.add( "-DVJETDebugPort=" +
			// config.getProperty(DbgpConstants.PORT_PROP));
			// vmArgs.add( "-DVJETDebugSessionID=" +
			// config.getProperty(DbgpConstants.SESSION_ID_PROP));
			// */
			//			
			// String vmArgs =
			// launchConfiguration.getAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS,"");
			//			
			// int containsVJETArgs = -1;
			//			
			// if (containsVJETArgs < 0) {
			//
			// vmArgs = MessageFormat.format("{0} {1}", vmArgs, vjetArgs);
			// }
			//			
			// copy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS,
			// vmArgs);

			/**
			 * org.eclipse.debug.core.capture_output=false,
			 * debugging_engine_id=org.ebayopensource.vjet.eclipse.debug,
			 * org.eclipse.debug.ui.ATTR_CONSOLE_ENCODING=null,
			 */

			copy.doSave();

			config = launchConfiguration;

			// // add script arguments as line
			// pArgs.add(new ArgsNormalizer(args).normalize());
			//
			// vmConfig.setProgramArguments(pArgs.toArray(new String[] {}));
			//
			// List<String> vmArgs = new ArrayList<String>(4);
			// if (sourceSearchPath != null) {
			// vmArgs.add("-Djava.source.path=" +sourceSearchPath);
			// }
			//			
			// if (m_mode.equals(ILaunchManager.DEBUG_MODE)){
			// vmArgs.add( "-DVJETDebugHost=" +
			// config.getProperty(DbgpConstants.HOST_PROP));
			// vmArgs.add( "-DVJETDebugPort=" +
			// config.getProperty(DbgpConstants.PORT_PROP));
			// vmArgs.add( "-DVJETDebugSessionID=" +
			// config.getProperty(DbgpConstants.SESSION_ID_PROP));
			// }
			//
			//		
			// vmConfig.setVMArguments(vmArgs.toArray(new
			// String[vmArgs.size()]));
			//
			//			

			// System.out.println(config.getAttribute(ScriptLaunchConfigurationConstants.ENABLE_BREAK_ON_FIRST_LINE,
			// true));
			// System.out.println(config.getAttribute(ScriptLaunchConfigurationConstants.ATTR_USE_INTERACTIVE_CONSOLE,
			// true));
			// System.out.println(config.getAttribute(ScriptLaunchConfigurationConstants.ATTR_DLTK_CONSOLE_ID,
			// (String)null));
			// System.out.println(config.getAttribute(ScriptLaunchConfigurationConstants.ATTR_DLTK_DBGP_REMOTE,
			// true));
			// System.out.println(config.getAttribute(ScriptLaunchConfigurationConstants.ATTR_DLTK_DBGP_PORT,
			// true));
			// System.out.println(config.getAttribute(ScriptLaunchConfigurationConstants.ATTR_DLTK_DBGP_WAITING_TIMEOUT,
			// true));
			// System.out.println(config.getAttribute(
			// ScriptLaunchConfigurationConstants.ATTR_PROJECT_NAME,
			// (String) null));
			//			
			// System.out.println(config.getAttribute(ScriptLaunchConfigurationConstants.ATTR_MAIN_SCRIPT_NAME,
			// true));
			// System.out.println(config.getAttribute(ScriptLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY,
			// true));
			// System.out.println(config.getAttribute(ScriptLaunchConfigurationConstants.ATTR_SCRIPT_ARGUMENTS,
			// true));

			VjetDebugEngineRunner debugEngine = new VjetDebugEngineRunner(
					new GenericVjetInstallType()
							.createInterpreterInstall("org.ebayopensource.vjet.eclipse.launching.embeddedRhino"));
			debugEngine.run(new InterpreterConfig(), launch, null);
		} catch (Exception e) {
			VjetLaunchingPlugin.error("Failed to start VJET debugger.", e,
					IStatus.WARNING);
		}
	}

	private boolean canAttachVjetDebugger(String launchTypeId) {
		for (String id : ATTACHABLE_LAUNCH_TYPES) {
			if (id.equals(launchTypeId)) {
				return true;
			}
		}
		return false;
	}

	private void setUpSourceLocator(ILaunch launch) {
		
		
		final ISourceLocator origLocator = launch.getSourceLocator();

		if (origLocator == null || origLocator instanceof SourceLocatorProxy) {
			return;
		}

		if (origLocator instanceof VjetSourceLookupDirector) {
			return;
		}
		
		// added for bug VJET-107 only proxy java source lookup not other js lookups
		if(origLocator instanceof JavaSourceLookupDirector ){
			launch.setSourceLocator(getSourceLocator(launch, origLocator));
		}
	}

	// add by patrick
	private SourceLocatorProxy getSourceLocator(ILaunch launch,
			final ISourceLocator origLocator) {
		SourceLocatorProxy sourceLocator = new SourceLocatorProxy(origLocator,
				new VjetSourceLookupDirector());
		try {
			sourceLocator.initializeDefaults(launch.getLaunchConfiguration());
		} catch (CoreException e) {
			VjetPlugin.error(e.getLocalizedMessage(), e);
		}
		sourceLocator.initializeParticipants();
		return sourceLocator;
	}

	// end add

	private IProject getProjectFromLaunch(ILaunch launch) {

		ILaunchConfiguration launchConfiguration = launch
				.getLaunchConfiguration();
		IProject launchProject = null;

		try {

			// Get the name of the project this launch configuration is
			// associated with
			String projectName = launchConfiguration.getAttribute(
					"org.eclipse.jdt.launching.PROJECT_ATTR", "");
			if (projectName != null && projectName.length() > 0) {
				launchProject = getWorkspaceProject(projectName);
			}
		} catch (CoreException e1) {
			e1.printStackTrace();
		}

		if (launchProject == null) {
			// No project associated with this launch configuration
			return null;
		}

		return launchProject;
	}

	static public IProject getWorkspaceProject(String projectName)
			throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// Get the dependent project
		IProject codeGenProject = workspace.getRoot().getProject(projectName);

		if (codeGenProject != null && codeGenProject.isOpen() == false) {
			codeGenProject.open(null);
		}

		return codeGenProject;

	}

	/**
	 * create and bind script debug target
	 * 
	 * @return
	 */
	private IScriptDebugTarget installScriptDebugTarget(ILaunch launch,
			IDbgpService service) {
		try {
			String modelID = "org.ebayopensource.vjet.eclipse.debug.vjetModel";
			String sessionID = DbgpSessionIdGenerator.generate();

			IScriptDebugTarget scriptDebugTarget = new ScriptDebugTarget(
					modelID, service, sessionID, launch, null);

			launch.addDebugTarget(scriptDebugTarget);

			ISourceLocator sourceLocator = launch.getSourceLocator();

			return scriptDebugTarget;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * specify corresponding system properties used by DBGP Debug Server.
	 * 
	 * @param scriptDebugTarget
	 * @param service
	 */
	private void setSystemProperties(IScriptDebugTarget scriptDebugTarget,
			IDbgpService service) {
		int port = service.getPort();
		System.setProperty("-DVJETDebugPort", String.valueOf(port));

		String host = DLTKDebugPlugin.getDefault().getBindAddress();
		System.setProperty("-DVJETDebugHost", host);

		String debugID = scriptDebugTarget.getSessionId();
		System.setProperty("-DVJETDebugDebugID", debugID);
	}

	public void launchChanged(ILaunch launch) {
		if (!ILaunchManager.DEBUG_MODE.equals(launch.getLaunchMode())) {
			return;
		}
		boolean attachDebugger = VjetDebugPlugin
				.getDefault()
				.getPreferenceStore()
				.getBoolean(
						VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH);
		if (!attachDebugger)
			return;
		
		
		setUpSourceLocator(launch);

		// String launchType =
		// launch.getLaunchConfiguration().getType().getName();
		// if (!"Java Application".equals(launchType))
		// return;
		//		
		// launch.getSourceLocator();
		// System.out.println();

		// logger.info("launchChanged(ILaunch) - start");

	}

	class LaunchWorkingCopy extends LaunchConfigurationWorkingCopy {

		protected LaunchWorkingCopy(LaunchConfiguration original)
				throws CoreException {
			super(original);
			// TODO Auto-generated constructor stub
		}

	}

}
