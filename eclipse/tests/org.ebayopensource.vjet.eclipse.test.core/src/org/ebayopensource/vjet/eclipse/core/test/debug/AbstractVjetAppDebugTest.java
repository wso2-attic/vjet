/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import junit.framework.AssertionFailedError;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.IInternalDebugUIConstants;
import org.eclipse.dltk.mod.dbgp.exceptions.DbgpException;
import org.eclipse.dltk.mod.debug.core.ScriptDebugManager;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptExceptionBreakpoint;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptLineBreakpoint;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptMethodEntryBreakpoint;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptSpawnpoint;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptWatchpoint;
import org.eclipse.dltk.mod.launching.ScriptLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.preference.IPreferenceStore;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.core.test.VjetModelTestsPlugin;
import org.ebayopensource.vjet.eclipse.core.test.contentassist.TestConstants;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public abstract class AbstractVjetAppDebugTest extends AbstractVjoModelTests {

	public interface IBreakpointFactory {
		IBreakpoint createExceptionBreakpoint(IResource resource,
				String exceptionName, boolean caught, boolean uncaught,
				boolean register, Map attributes) throws DebugException;

		IBreakpoint createLineBreakpoint(IResource resource, IPath resPath,
				int lineNo, int startChar, int endChar, boolean register)
				throws DebugException;

		IBreakpoint createMethodEntryBreakpoint(IResource resource,
				IPath resPath, int lineNo, int startChar, int endChar,
				boolean register, String methodName) throws DebugException;

		IBreakpoint createSpwanpoint(IResource resource, IPath resPath,
				int lineNo, int startChar, int endChar, boolean register)
				throws DebugException;

		IBreakpoint createWatchpoint(IResource resource, IPath resPath,
				int lineNo, int startChar, int endChar, boolean register,
				String fieldName) throws CoreException;
	}

	public interface IDebugEventHandler {
		void handleDebugEvent(DebugEvent event) throws DbgpException,
				DebugException;
	}

	class ScriptBreakpointFactory implements IBreakpointFactory {

		@Override
		public IBreakpoint createExceptionBreakpoint(IResource resource,
				String exceptionName, boolean caught, boolean uncaught,
				boolean register, Map attributes) throws DebugException {
			return new ScriptExceptionBreakpoint(MODEL_ID, resource,
					exceptionName, caught, uncaught, register, attributes);
		}

		@Override
		public IBreakpoint createLineBreakpoint(IResource resource,
				IPath resPath, int lineNo, int startChar, int endChar,
				boolean register) throws DebugException {
			return new ScriptLineBreakpoint(MODEL_ID, resource, resPath,
					lineNo, startChar, endChar, register);
		}

		@Override
		public IBreakpoint createMethodEntryBreakpoint(IResource resource,
				IPath resPath, int lineNo, int startChar, int endChar,
				boolean register, String methodName) throws DebugException {
			return new ScriptMethodEntryBreakpoint(MODEL_ID, resource, resPath,
					lineNo, startChar, endChar, register, methodName);
		}

		@Override
		public IBreakpoint createSpwanpoint(IResource resource, IPath resPath,
				int lineNo, int startChar, int endChar, boolean register)
				throws DebugException {
			return new ScriptSpawnpoint(MODEL_ID, resource, resPath, lineNo,
					startChar, endChar, register);
		}

		@Override
		public IBreakpoint createWatchpoint(IResource resource, IPath resPath,
				int lineNo, int startChar, int endChar, boolean register,
				String fieldName) throws CoreException {
			return new ScriptWatchpoint(MODEL_ID, resource, resPath, lineNo,
					startChar, endChar, fieldName);
		}

	}

	private static final char		DOT					= '.';
	private static final String		MODEL_ID			= ScriptDebugManager
																.getInstance()
																.getDebugModelByNature(
																		VjoNature.NATURE_ID);
	private static final int		POLL_TIME			= 200;
	private static final String		SOURCE_FOLDER		= "src";

	private static final String		TEMPLATE_DIR		= "launch";

	private static final String		TEMPLATE_VJET_APP	= "vjetApp.launch";

	private static final int		TIMEOUT				= 5 * 60 * 1000;

	private IDebugEventHandler		m_debugEventHandler;

	private IDebugEventSetListener	m_debugEventListener;

	private AssertionFailedError	m_error;

	private IBreakpointFactory		m_factory			= new ScriptBreakpointFactory();
	private IJobChangeListener		m_jobChangeListener;

	protected IBreakpoint createLineBreakpoint(String resourcePath, int lineNo)
			throws DebugException {
		Path path = new Path(resourcePath);
		return m_factory.createLineBreakpoint(getWorkspaceRoot().findMember(
				path), path, lineNo, -1, -1, false);
	}

	protected String getMainScriptAttrbute(String fileName, String packagePath) {
		return SOURCE_FOLDER + IPath.SEPARATOR + packagePath + IPath.SEPARATOR
				+ fileName;
	}

	protected String getMainScriptPath(String fileName, String packagePath) {
		return getProjectName() + IPath.SEPARATOR
				+ getMainScriptAttrbute(fileName, packagePath);
	}

	@Override
	protected String getProjectName() {
		return TestConstants.PROJECT_NAME_VJETPROJECT;
	}

	protected void launchVjetAppDebug(String fileName, String packagePath,
			IBreakpoint[] breakpoints) throws CoreException, Exception,
			InterruptedException, DebugException {
		setupBreakpoints(breakpoints);
		ILaunch launch = null;
		ILaunchConfiguration config = null;
		try {
			config = getVjetAppLaunch(fileName, packagePath);

			// handle debug events
			registerListeners();

			// launch app
			if (config != null) {
				launch = config.launch(ILaunchManager.DEBUG_MODE,
						new NullProgressMonitor(), true);
				redirectOutput(launch);
				long count = TIMEOUT;
				while (!launch.isTerminated() && count > 0) {
					Thread.sleep(POLL_TIME);
					count = count - POLL_TIME;
				}
			}

		} finally {
			removeListeners();
			if ((launch != null) && (!launch.isTerminated())) {
				launch.terminate();
			}
			if (config != null) {
				config.delete();
			}

			AssertionFailedError error = getAssertFailedError();
			if (error != null) {
				throw error;
			}
		}
	}

	private void removeListeners() {
		removeDebugEventListener();
		// removeJobChangeListener();
	}

	private void registerListeners() {
		registerDebugEventListener();
		// registerJobChangeListener();
	}

	protected void setDebugEventHandler(IDebugEventHandler handler) {
		this.m_debugEventHandler = handler;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// clear breakpoints
		IBreakpointManager manager = DebugPlugin.getDefault()
				.getBreakpointManager();
		manager.removeBreakpoints(manager.getBreakpoints(), true);

		// init error flag to null
		setAssertFailedError(null);

		// enable launching with errors
		IPreferenceStore store = DebugUIPlugin.getDefault()
				.getPreferenceStore();
		String value = "always";
		if (!value
				.equals(store
						.getString(IInternalDebugUIConstants.PREF_CONTINUE_WITH_COMPILE_ERROR))) {
			store.setValue(
					IInternalDebugUIConstants.PREF_CONTINUE_WITH_COMPILE_ERROR,
					value);
		}
	}

	private void configure(ILaunchConfiguration config, String fileName,
			String packagePath) throws CoreException {
		ILaunchConfigurationWorkingCopy wc = config.getWorkingCopy();
		String mainScriptPath = getMainScriptAttrbute(fileName, packagePath);
		wc.setAttribute(
				ScriptLaunchConfigurationConstants.ATTR_MAIN_SCRIPT_NAME,
				mainScriptPath);
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME,
				getProjectName());
		wc.setAttribute(ScriptLaunchConfigurationConstants.ATTR_PROJECT_NAME,
				getProjectName());

		IResource resource = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(getMainScriptPath(fileName, packagePath));
		IResource[] mappedResources = new IResource[] { resource };
		wc.setMappedResources(mappedResources);

		wc.doSave();
	}

	private synchronized AssertionFailedError getAssertFailedError() {
		return m_error;
	}

	private ILaunchConfiguration getLaunchConfiguration(String fileName,
			IFile launchFile) throws Exception {
		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		return launchManager.getLaunchConfiguration(launchFile);
	}

	private IFile getLaunchFile(String fileName) throws IOException,
			URISyntaxException, FileNotFoundException, CoreException {
		File file = getTemplateLaunchFile();
		String launchFileName = fileName + DOT
				+ ILaunchConfiguration.LAUNCH_CONFIGURATION_FILE_EXTENSION;
		IProject project = getProject(getProjectName());
		project.getFile(launchFileName).create(new FileInputStream(file), true,
				null);
		return project.getFile(launchFileName);
	}

	private File getTemplateLaunchFile() throws IOException, URISyntaxException {
		URL fileURL = FileLocator.find(VjetModelTestsPlugin.getDefault()
				.getBundle(), new Path(TEMPLATE_DIR + IPath.SEPARATOR
				+ TEMPLATE_VJET_APP), null);
		fileURL = FileLocator.toFileURL(fileURL);
		File file = new File(fileURL.toURI());
		return file;
	}

	private ILaunchConfiguration getVjetAppLaunch(String fileName,
			String packagePath) throws Exception {
		IFile launchFile = getLaunchFile(fileName);

		ILaunchConfiguration config = getLaunchConfiguration(fileName,
				launchFile);

		configure(config, fileName, packagePath);

		return config;
	}

	private void redirectOutput(ILaunch launch) {
		IProcess[] processes = launch.getProcesses();
		if (processes.length == 0) {
			return;
		}
		IProcess process = processes[0];
		IStreamsProxy streamsProxy = process.getStreamsProxy();
		streamsProxy.getOutputStreamMonitor().addListener(
				new IStreamListener() {

					@Override
					public void streamAppended(String text,
							IStreamMonitor monitor) {
						System.out.println(text);
					}
				});
		streamsProxy.getErrorStreamMonitor().addListener(new IStreamListener() {

			@Override
			public void streamAppended(String text, IStreamMonitor monitor) {
				System.err.println(text);
			}
		});
	}

	private void registerDebugEventListener() {
		m_debugEventListener = new IDebugEventSetListener() {

			@Override
			public void handleDebugEvents(DebugEvent[] events) {
				for (DebugEvent event : events) {
					if (m_debugEventHandler != null) {
						try {
							m_debugEventHandler.handleDebugEvent(event);
						} catch (Exception e) {
							e.printStackTrace();
						} catch (AssertionFailedError e) {
							setAssertFailedError(e);
							throw e;
						}
					}
				}

			}
		};
		DebugPlugin.getDefault().addDebugEventListener(m_debugEventListener);
	}

	private void registerJobChangeListener() {
		m_jobChangeListener = new IJobChangeListener() {

			@Override
			public void aboutToRun(IJobChangeEvent event) {
				// do nothing

			}

			@Override
			public void awake(IJobChangeEvent event) {
				// do nothing

			}

			@Override
			public void done(IJobChangeEvent event) {
				IStatus result = event.getResult();
				if (IStatus.ERROR == result.getSeverity()) {
					Throwable ex = result.getException();
					if (ex instanceof AssertionFailedError) {
						setAssertFailedError((AssertionFailedError) ex);
					}
				}
			}

			@Override
			public void running(IJobChangeEvent event) {
				// do nothing

			}

			@Override
			public void scheduled(IJobChangeEvent event) {
				// do nothing

			}

			@Override
			public void sleeping(IJobChangeEvent event) {
				// do nothing

			}
		};
		Job.getJobManager().addJobChangeListener(m_jobChangeListener);
	}

	private void removeDebugEventListener() {
		if (m_debugEventListener != null) {
			DebugPlugin.getDefault().removeDebugEventListener(
					m_debugEventListener);
		}
	}

	private void removeJobChangeListener() {
		if (m_jobChangeListener != null) {
			Job.getJobManager().removeJobChangeListener(m_jobChangeListener);
		}

	}

	private synchronized void setAssertFailedError(AssertionFailedError error) {
		m_error = error;
	}

	private void setupBreakpoints(IBreakpoint[] breakpoints)
			throws CoreException {
		DebugPlugin.getDefault().getBreakpointManager().addBreakpoints(
				breakpoints);
	}
}
