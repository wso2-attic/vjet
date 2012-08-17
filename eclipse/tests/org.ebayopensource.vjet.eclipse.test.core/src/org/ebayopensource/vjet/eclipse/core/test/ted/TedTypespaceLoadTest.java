/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.ted;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.internal.core.Model;
import org.eclipse.dltk.mod.internal.core.ModelManager;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Bundle;

import org.ebayopensource.vjet.eclipse.core.PiggyBackClassPathUtil;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;
import org.ebayopensource.vjet.testframework.util.UnitTestHelper;

public class TedTypespaceLoadTest extends AbstractVjoModelTests {
	public static final String POM_FILE_NAME = "pom.xml";
	public static final String KEY_PROJECT_NAME = "ProjectName";

	private boolean m_isProjectSetUp = false;
	/**
	 * verify type space loader in TED
	 * 
	 * @throws Exception
	 */
	public void testTypespaceLoaderFromJDTInTed() throws Exception {
		
		if (!isTedInstalled()) {
			return;
		}
		VjoEditor vjoEditor = null;
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		try {
			IProject project = setUpProjectTo("TestProject", "TestProject");
			waitJobs();
			// copyPomFile(project);
			IJavaProject jProject = JavaCore.create(project);
			assertTrue("Failed to create java project", jProject.exists());
			IFile file = project.getFile("/src/test/DragDrop.js");
			vjoEditor = (VjoEditor) IDE.openEditor(workbenchPage, file);
			List<URL> urls = PiggyBackClassPathUtil
					.getProjectDependencyUrls_bak(jProject);
			assertTrue(
					"Can not resolved the correct URLs from Maven project, the result size is "
							+ urls.size(), urls.size() > 0);
		} finally {
			deleteProject("TestProject");
			workbenchPage.closeEditor(vjoEditor, false);
		}
	}
	/**
	 * verify type space loader in TED
	 * 
	 * @throws Exception
	 */
	public void testTypespaceLoaderFromDLTKInTed() throws Exception {
		
		if (!isTedInstalled()) {
			return;
		}
		VjoEditor vjoEditor = null;
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
		.getActiveWorkbenchWindow().getActivePage();
		try {
			IProject project = setUpProjectTo("TestProject", "TestProject");
			waitJobs();
			Model model = ModelManager.getModelManager().getModel();
			IScriptProject sProject = model.getScriptProject(project.getName());
			assertTrue("Failed to create java project", sProject.exists());
			PiggyBackClassPathUtil
			.initializeScriptProjectFromJavProject(sProject);
			List<URL> urls = PiggyBackClassPathUtil
			.getProjectDependantJars_DLTK(sProject);
			//Use 3 because this jars this test project depends on is larger than 3;
			assertTrue(
					"Can not resolved the correct URLs from Maven project, the result size is "
					+ urls.size(), urls.size() > 0);
		} finally {
			deleteProject("TestProject");
			workbenchPage.closeEditor(vjoEditor, false);
		}
	}

	private void copyPomFile(IProject project) throws CoreException {
		IFile file = project.getFile(POM_FILE_NAME);
		String pomContent = getPomContent();
		pomContent.replaceAll(KEY_PROJECT_NAME, project.getName());
		file.create(new ByteArrayInputStream(pomContent.getBytes()), true,
				new NullProgressMonitor());
	}

	private String getPomContent() {
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				"org.ebayopensource.vjet.eclipse/core/test/ted/" + POM_FILE_NAME);
		StringBuffer buffer = new StringBuffer();
		try {
			byte[] bytes = new byte[2048];

			while (is.read(bytes) != -1) {
				buffer.append(new String(bytes));
				is.read();
			}
		} catch (IOException e) {
			assertTrue("Failed to create pom.xml", false);
		}
		return buffer.toString();
	}

	public File getSourceWorkspacePath() {
		return new File(getPluginDirectoryPath(), "workspace_ted");
	}

	private boolean isTedInstalled() {
		Bundle bundle = Platform.getBundle("com.ebay.tools.v4.ebox.ui");
		return bundle != null && bundle.getState() == Bundle.ACTIVE;
	}

	private void waitJobs() {
		 waitUIOperation(40);//wait 2s to make sure build starts
		try {
			// wait auto/manual build jobs finishes.
			Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
			Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is actually works for the first time The latter call will not
	 * trigger the job again <job value will be null> as the index is already
	 * updated. wait for index to set up
	 */
	private void waitUpdateIndex() {
		// this line of code is to ensure maven index to start load and not
		// depend on maven
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display
				.getCurrent().getActiveShell());
		try {
			dialog.run(true, false, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					monitor.beginTask("waiting for index updater to finish...",
							10);
					Job job = null;// Jobs.getJobByName("Updating indexes");
					if (job != null) {
						job.join();
					} else {
						System.err
								.println("cannot find job! May be the job has run yet, so ignore the error message if this is a wrong alarm.");
					}
					monitor.done();
				}

			});
		} catch (Exception e) {

		}
	}

	public static Job[] getJobs() {
		return Job.getJobManager().find(null);
	}

	public static Job getJobByName(String name) {
		Job[] jobs = getJobs();
		for (Job job : jobs) {
			if (name.equals(job.getName())) {
				return job;
			}
		}

		return null;
	}
	
	 /**
	  * wait X*50 ms.at least 500ms
	  * 
	  * @param waitCount
	  */
	 public static void waitUIOperation(int waitCount) {
	  if (waitCount < 10) {
	   waitCount = 10;
	  }
	  int countDown = waitCount;
	  try {
	   while (countDown-- != 0) {
	    UnitTestHelper.runEventQueue();
	    try {
	     Thread.sleep(50);
	    } catch (InterruptedException e) {
	     e.printStackTrace();
	    }
	   }
	  } catch (RuntimeException e) {
	   // TODO Auto-generated catch block
	   // ignore
	  }

	 }
	 
	 protected IProject setUpProjectTo(final String projectName,
				final String fromName) throws CoreException, IOException {
		 if (!m_isProjectSetUp) {
			 m_isProjectSetUp = true;
			 return super.setUpProjectTo(projectName, fromName);
		 } else {
			 return 	ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		 }
	 }
}
