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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;


/**
 * @author ddodd
 *
 * This artifact manager is used to import the defined set of projects
 * into the runtime workspace.
 * 
 */
public class WorkspaceProjectArtifactManager extends ProjectArtifactManager {

	public static final String ARTIFACT_MANAGER_ID = "org.ebayopensource.vjet.testframework.artifact.project";

	public boolean setUp() {
		File targetProjectDir = getTargetProjectDir();

		try {
			
			super.setUp();

			IWorkspace workspace = ResourcesPlugin.getWorkspace();

			// Import project
			ProjectUtil.importProject(m_projectName, targetProjectDir,
					workspace, null);

			return true;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}

	}
	
	
	public void tearDown() {

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				m_projectName);
		try {
			project.close(null);
			project.delete(true, null);
	
		} catch (CoreException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Error tearing down project: " + m_projectName);
		}
		
		super.tearDown();
	}
	
	
	public String getArtifactType() {
		return ARTIFACT_MANAGER_ID;
	}
	

	
}
