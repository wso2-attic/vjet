/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework;

import java.io.File;
import java.util.Map;

import junit.framework.TestCase;

import org.ebayopensource.vjet.testframework.artifact.IArtifactDef;
import org.ebayopensource.vjet.testframework.artifactmanager.project.ProjectUtil;
import org.ebayopensource.vjet.testframework.artifactmanager.project.ZipProjectArtifactManager;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;
import org.ebayopensource.vjet.testframework.fixture.IFixtureDef;
import org.ebayopensource.vjet.testframework.fixture.IFixtureDefManager;
import org.ebayopensource.vjet.testframework.sandbox.ISandbox;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;




public class PluginTestManagerTests extends TestCase {

	
	/*
	 * This is to verify 'teardown' mechanism of an Artifact
	 */
	public void testArtifactSetupTeardown() {

		FixtureManager testManager = new FixtureManager(this);
		IFixtureDefManager testFixtures = testManager.getFixtures();
		
		IFixtureDef fixtureDef = testFixtures.getFixtureDefs().iterator().next();
		System.out.println("Using Fixture Definition : " + fixtureDef.getFixtureId());
		
		IArtifactDef artifactDef = ArtifactTestHelper.getArtifactDefWithType(fixtureDef, ZipProjectArtifactManager.ARTIFACT_MANAGER_ID);
		
		try {

			testManager.setUp(fixtureDef.getFixtureId());
			ISandbox sandbox = testManager.getSandBox();
			
			Map<String, String> projectArtifactAttributes = artifactDef.getArtifactAttributes();
			String projectNameNode = projectArtifactAttributes.get(ZipProjectArtifactManager.PROJECT_NAME_ATTRIBUTE);
			
			if (projectNameNode == null) {
				fail(ZipProjectArtifactManager.PROJECT_NAME_ATTRIBUTE + " not found in attributes of project artifact");
			}
			
			File projectDir = new File(sandbox.getSandBoxDir(), projectNameNode);
			File projectFile = new File(projectDir, ".project");
			
			assertTrue(projectFile.exists());
			testManager.tearDown();	//Cleaning up
			assertFalse(projectFile.exists());
			
		} catch (Exception e) {

			e.printStackTrace();
			fail(e.getMessage());
			
		} finally {
			//Just making sure...
			testManager.tearDown();
		}
	}


	/*
	 * This test is a negative test case for ProjectUtil.importProject()
	 */	
	public void testProjectUtilImportProject() {

		FixtureManager testManager = new FixtureManager(this);
		IFixtureDefManager testFixtures = testManager.getFixtures();
		
		IFixtureDef fixtureDef = testFixtures.getFixtureDefs().iterator().next();
		System.out.println("Using Fixture Definition : " + fixtureDef.getFixtureId());
		
		IArtifactDef artifactDef = ArtifactTestHelper.getArtifactDefWithType(fixtureDef, ZipProjectArtifactManager.ARTIFACT_MANAGER_ID);
		System.out.println("Using Fixture Definition : " + artifactDef.getArtifactId());		
		
		try {

			testManager.setUp(fixtureDef.getFixtureId());
			System.out.println("testManager.setUp() SUCCESS");

			//Try to import the project again. It should return false.
			ZipProjectArtifactManager artifactManager = new ZipProjectArtifactManager();
			artifactManager.init(artifactDef, testManager.getSandBox());
			boolean isImported = artifactManager.setUp();
			
			if (isImported) {
				
				System.out.println("Imported project succesfully again - BAD!");
				fail("PluginTestManagerTests.testProjectUtilImportProject() : Didn't return 'false'.");
			} 			

		} catch (Exception ex) {

			fail("PluginTestManagerTests.testProjectUtilImportProject() : Didn't return 'false'.");
			ex.printStackTrace();
			
		} finally {
			//Just making sure...
			testManager.tearDown();
		}		
	}		
	
	
	/*
	 * This test is for ProjectUtil.setAutoBuild()
	 */			
	public void testProjectUtilSetAutoBuild() {

		try {
			
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			ProjectUtil.setAutoBuild(workspace, true);
			assertTrue(workspace.isAutoBuilding());
			
			ProjectUtil.setAutoBuild(workspace, false);
			assertFalse(workspace.isAutoBuilding());

		} catch(Exception ex) {
			
			fail(ex.getMessage());
		}
	}	
}
