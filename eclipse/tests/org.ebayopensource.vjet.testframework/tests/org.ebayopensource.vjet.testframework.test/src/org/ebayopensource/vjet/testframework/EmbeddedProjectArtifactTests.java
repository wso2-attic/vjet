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

import org.ebayopensource.vjet.testframework.artifact.IArtifactDef;
import org.ebayopensource.vjet.testframework.artifactmanager.project.EmbeddedProjectArtifactManager;
import org.ebayopensource.vjet.testframework.fixture.FixtureDefManager;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;
import org.ebayopensource.vjet.testframework.fixture.FixtureUtils;
import org.ebayopensource.vjet.testframework.fixture.IFixtureDef;
import org.ebayopensource.vjet.testframework.sandbox.ISandbox;
import org.ebayopensource.vjet.testframework.sandbox.Sandbox;

import junit.framework.TestCase;


public class EmbeddedProjectArtifactTests extends TestCase {


	public static final String VALID_EMBEDDED_ARTIFACT_FOR_FILE_INSTALL = "org.ebayopensource.vjet.testframework.fixture3";
	
	/*
	 * This test will test the File Installation part of EmbeddedProjectArtifactManager
	 */
	public void testFileInstallation() {
		
		FixtureManager fixtureManager = null;
		try {
		
			ISandbox sandBox = new Sandbox(this);
			sandBox.setUp();;
			FixtureDefManager fixtureDefManager = FixtureUtils.createFixtureDefManagerFromXml(TestConstants.VALID_FIXTURE_FILENAME, 
																							  this, sandBox);
			
			//Find out the fixture to test
			fixtureManager = new FixtureManager(this, fixtureDefManager);
			IFixtureDef fixtureToTest = fixtureManager.getFixtures().getFixtureDef(TestConstants.VALID_EMBEDDED_PROJECT_FIXTURE_ID);

			//Find out the artifact to test
			EmbeddedProjectArtifactManager artifactManager = new EmbeddedProjectArtifactManager();
			IArtifactDef artifactToTest = ArtifactTestHelper.getArtifactDefWithType(fixtureToTest, artifactManager.getArtifactType());
			
			if ( (fixtureToTest == null) || (artifactToTest == null) ) {
				throw new NullPointerException("Fixture Definiton or Artifact definition is NULL! - Time to debug!!");
			}
			
			//setup the fixture
			fixtureManager.setUp(fixtureToTest.getFixtureId());
			
			Map<String, String> artifactAttributes = artifactToTest.getArtifactAttributes();
			String projectName = artifactAttributes.get(EmbeddedProjectArtifactManager.PROJECT_NAME_ATTRIBUTE);

			//Make sure the project folder exists
			File expectedProjectFolder = new File(sandBox.getSandBoxDir(), "projects" + File.separator + projectName);
			assertTrue("Project folder doesn't exists", expectedProjectFolder.exists());

			File expectedBinFolder = new File(sandBox.getSandBoxDir(), "projects" + File.separator + projectName + File.separator + "bin");
			assertTrue("Imported project doesn't have the 'bin' folder in it.", expectedBinFolder.exists());

		} catch (Exception ex) {
			
			ex.printStackTrace();
			fail("EmbeddedProjectArtifactTests.testFileInstallation() : " + ex.getMessage());
			
		} finally {
			
			if (fixtureManager != null) {
				fixtureManager.tearDown();
			}
		}
	}//End of testFileInstallation test case
	
}
