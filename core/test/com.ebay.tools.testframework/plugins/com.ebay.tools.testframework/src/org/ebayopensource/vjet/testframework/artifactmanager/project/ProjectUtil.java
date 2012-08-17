/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.artifactmanager.project;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * 
 * @author ddodd
 * 
 * Set of static methods for managing importing of projects.
 *
 */
public class ProjectUtil {

	public static void importProject(String projectName, File projectLocation, IWorkspace workspace,
			IProgressMonitor monitor) throws Throwable {

		try {

			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}
			
			monitor.subTask("Import Project (" + projectName + ")");
			IProject project = workspace.getRoot().getProject(projectName);
			IProjectDescription description = workspace
					.newProjectDescription(projectName);
			description.setName(projectName);
			description.setLocation(new Path(projectLocation.toString()));
			importProject(project, description, monitor);

		} catch (Throwable any) {

			if (any instanceof InvocationTargetException) {
				InvocationTargetException invocation = (InvocationTargetException) any;

				Throwable target = invocation.getTargetException();
				if (target != null) {
					any = target;
				}
			}

			String message = any.getMessage();
			if (message != null) {

				// ignore if it says project ".project is read-only"...
				if (message.indexOf(".project is read-only") != -1) {
					// okay, keep on going...
				} else {
					// more critical because we didn't expect this one!
					throw any;
				}
				
			} else {
				// more critical because we didn't expect this one!
				throw any;
			}
		} finally {

		}
	}

	private static void importProject(IProject theProject,
			IProjectDescription description, IProgressMonitor monitor)
			throws CoreException {
		
		if (theProject.exists()) {
			try{
				theProject.delete(false, true, null);	
			}catch(Exception e){
				throw new RuntimeException("Error: project: " + theProject.getName() + " already exists, and can't remove it from workspace!");	
			}
		}
		
		theProject.create(description, new SubProgressMonitor(monitor, 1000));
		theProject.open(new SubProgressMonitor(monitor, 1000));
		
		// Make sure we are built before running tests.
		theProject.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, new SubProgressMonitor(monitor, 1000));
		
	}

	public static void setAutoBuild(IWorkspace workspace, boolean enabled) throws CoreException {

		IWorkspaceDescription def = workspace.getDescription();
			def.setAutoBuilding(enabled);
			workspace.setDescription(def);
	}

}
