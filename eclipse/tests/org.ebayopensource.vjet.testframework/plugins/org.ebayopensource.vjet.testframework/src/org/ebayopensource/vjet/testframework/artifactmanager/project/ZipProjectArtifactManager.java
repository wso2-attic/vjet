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
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.ebayopensource.vjet.testframework.artifact.IArtifactDef;
import org.ebayopensource.vjet.testframework.artifact.IArtifactManager;
import org.ebayopensource.vjet.testframework.sandbox.ISandbox;
import org.ebayopensource.vjet.testframework.util.TestUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;



/**
 * @author   ddodd
 * 
 * This artifact manager is used to import a project based a project that has been 
 * zipped up and is embedded in the test project.
 */
public class ZipProjectArtifactManager implements IArtifactManager {

	String m_projectName;
	
	ISandbox m_sandbox;
	
	IArtifactDef m_artifactDef;
	
	public static final String PROJECT_NAME_ATTRIBUTE = "projectName";
	
	public static final String ARTIFACT_MANAGER_ID = "org.ebayopensource.vjet.testframework.artifact.project.zip";
	
	
	public void init(IArtifactDef artifactDef, ISandbox sandBox) {
		
		// Get the name of the project
		Map<String, String> artifactAttributes = artifactDef.getArtifactAttributes();
		String projectName = artifactAttributes.get(PROJECT_NAME_ATTRIBUTE);
		
		if (projectName == null) {
			throw new RuntimeException("projectName attribute not found in project artifact element");
		}
		
		m_artifactDef = artifactDef;
		m_projectName = projectName;
		m_sandbox = sandBox;
	}
	
	public String getArtifactType() {
		return ARTIFACT_MANAGER_ID;
	}
	
	public boolean setUp() {

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(m_projectName);
		if (project.exists()) {
			return false;
		}
		
		File projectArtifactFile = new File(m_sandbox.getSandBoxDir(),
				m_projectName + ".zip");
		File projectDir = new File(m_sandbox.getSandBoxDir(), m_projectName);
		

		if (projectArtifactFile.exists() == false) {
			throw new RuntimeException(
					"Project artifact compressed file not found:"
							+ projectArtifactFile.getAbsoluteFile().toString());
		}

		try {
			TestUtils.unzip(projectArtifactFile, projectDir);
			ProjectUtil.importProject(m_projectName, projectDir, ResourcesPlugin
					.getWorkspace(), null);
			return true;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}


	public void tearDown() {
		File projectDir = new File(m_sandbox.getSandBoxDir(), m_projectName);
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(m_projectName);
		try {
			project.close(null);
			project.delete(true, null);
		} catch (CoreException e) {
			e.printStackTrace();
			// Who cares...
		}
		
		try {
			FileUtils.deleteDirectory(projectDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
