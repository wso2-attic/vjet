/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework;


import java.util.Collection;
import java.util.List;

import org.ebayopensource.vjet.testframework.artifact.ArtifactManagerRegistry;
import org.ebayopensource.vjet.testframework.artifact.IArtifactDef;
import org.ebayopensource.vjet.testframework.artifact.IArtifactManager;
import org.ebayopensource.vjet.testframework.fixture.FixtureDefManager;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;
import org.ebayopensource.vjet.testframework.fixture.FixtureUtils;
import org.ebayopensource.vjet.testframework.fixture.IFixtureDef;
import org.ebayopensource.vjet.testframework.fixture.IFixtureDefManager;
import org.ebayopensource.vjet.testframework.sandbox.ISandbox;
import org.ebayopensource.vjet.testframework.sandbox.Sandbox;

import junit.framework.TestCase;


public class FixtureAndArtifactTests extends TestCase {
	
	/*
	 * This test is to verify if the framework loads fixture and artifact definitons correctly.
	 */
	public void testFixtures() {
		
		ISandbox m_sandbox; 
		FixtureDefManager m_fixtures; 
		
		m_sandbox = new Sandbox(this);
		m_fixtures = FixtureUtils.createFixtureDefManagerFromXml(this, m_sandbox);
		
		Collection<IFixtureDef> fixtureDefs = m_fixtures.getFixtureDefs();
		assertTrue(fixtureDefs.size() >= 1);
		
		IFixtureDef fixtureDef = fixtureDefs.iterator().next();
		List<IArtifactDef> artifacts = fixtureDef.getArtifactDefs();
		assertTrue(artifacts.size() >= 1);
	}
	

	/*
	 * This test verifies 'setup' and 'teardown' mechanism works fine for a SandBox.  
	 */
	public void testSandboxSetupTearDown() {

		FixtureManager testManager = new FixtureManager(this);
		ISandbox sandbox = testManager.getSandBox();
		
		sandbox.setUp();
		assertTrue(sandbox.getSandBoxDir().exists());

		sandbox.tearDown();
		assertFalse(sandbox.getSandBoxDir().exists());
		
	}
	
	
	/*
	 * This test verifies ArtifactManagerRegistry returns a valid artifact manager.   
	 */
	public void testArtifacts() {

		FixtureManager testManager = new FixtureManager(this);
		IFixtureDefManager testFixtures = testManager.getFixtures();
		
		Collection<IFixtureDef> fixtureDefs = testFixtures.getFixtureDefs();
		IFixtureDef fixtureDef = fixtureDefs.iterator().next();
		
		List<IArtifactDef> artifacts = fixtureDef.getArtifactDefs();
		IArtifactDef artifactDef = artifacts.get(0);
		
		IArtifactManager artifact = ArtifactManagerRegistry.getInstance().getArtifact(artifactDef.getArtifactId());
		assertNotNull(artifact);
		
		assertTrue(artifacts.size() >= 1);
	}
	
	
	/*
	 * This test verifies Artifact creation.
	 */
	public void testArtifactCreation() {
		
		FixtureManager testManager = new FixtureManager(this);
		IFixtureDefManager testFixtures = testManager.getFixtures();
		IFixtureDef fixtureDef = testFixtures.getFixtureDefs().iterator().next();

		List<IArtifactDef> artifacts = fixtureDef.getArtifactDefs();
		IArtifactDef artifactDef = artifacts.get(0);
		IArtifactManager artifact = ArtifactManagerRegistry.getInstance().getArtifact(artifactDef.getArtifactId());

		assertNotNull(artifact);
	}

	
	/*
	 * This test is to test the constructor for FixtureManager class
	 */
	public void testFixtureManagerConstruct() {
		
		ISandbox sandBox = new Sandbox(this);
		sandBox.setUp();

		System.out.println("Verifying FixtureManager(arg1, arg2) constructor ...");		
		FixtureDefManager fixtureDefManager = FixtureUtils.createFixtureDefManagerFromXml(this, sandBox);
		Collection<IFixtureDef> fixtureDefs = fixtureDefManager.getFixtureDefs();
		assertTrue(fixtureDefs.size() >= 1);
		
		sandBox.tearDown();
	}
	
	
	/*
	 * This test is for FixtureManager.setUp(NULL)
	 */
	public void testFixtureManagerSetUp() {
		
		ISandbox sandBox = new Sandbox(this);
		sandBox.setUp();
		FixtureDefManager fixtureDefManager = FixtureUtils.createFixtureDefManagerFromXml(TestConstants.INVALID_FIXTURE_FILENAME, 
																						  this, sandBox);
		FixtureManager fixtureManager = new FixtureManager(this, fixtureDefManager);
	
		try {
		
			System.out.println("Verifying FixtureManager.setUp(NULL) ...");
			//Exception may be thrown while calling setUp(null) because the fixture.xml 
			//has some invalid entries and the corresponding ZIP file won't be available
			fixtureManager.setUp(null);
			
			fail("Expected exception not thrown. Make sure " + TestConstants.INVALID_FIXTURE_FILENAME + " file has atleast one invalid entry");
			
		}catch(Exception ex) {
			
			System.out.println("FixtureAndArtifactTests.testFixtureManagerConstruct() : Expected exception thrown...");
			System.out.println("This exception happened because of some wrong entries in fixtures.xml");
			
		} finally {
			
			fixtureManager.tearDown();
		}
	}
	
	/*
	 * This test is for FixtureManager.setUpAll()
	 *  
	 */	
	public void testFixtureManagerSetUpAll() {

		ISandbox sandBox = new Sandbox(this);
		sandBox.setUp();			
		FixtureDefManager fixtureDefManager = FixtureUtils.createFixtureDefManagerFromXml(TestConstants.INVALID_FIXTURE_FILENAME, 
																						  this, sandBox);
		FixtureManager fixtureManager = new FixtureManager(this, fixtureDefManager);

		try {
			
			//Exception may be thrown while calling setUpAll() because the fixture.xml 
			//has some invalid entries and the corresponding ZIP file won't be available			
			fixtureManager.setUpAll();

			System.out.println("Make sure the fixtures.xml file has atleast one invalid entry");
			fail("testFixtureManagerSetUpAll : Expected exception is not thrown ...");
			
		} catch (Exception e) {

			System.out.println("testFixtureManagerSetUpAll : Expected exception thrown.");
			
		} finally {
			
			fixtureManager.tearDown();
		}		
	}

	/*
	 * This test is to test the excepton scenario FixtureUtils.createFixtureDefManagerFromXml()
	 *  
	 */		
	public void testFixtureUtilException() {

		FixtureDefManager fixtureDefManager = null;
		try {

			fixtureDefManager = FixtureUtils.createFixtureDefManagerFromXml(null, null, null);
			
			//Since the above code doesn't throw any exception back to the caller, we don't need to add any
			//mechanism here to make the test case fail if an exception is not thrown back here. 
			//fail("testFixtureUtilException : Expected exception is not thrown ...");

		} catch (Exception e) {

			System.out.println("FixtureAndArtifactTests.testFixtureUtilException : Expected exception thrown.");
		}
	}
	

	/*
	 * Test the Sandbox creation
	 */
	public void testSandBoxCreation() {
	
		ISandbox sandBox = new Sandbox(this);
		sandBox.setUp();
		
		FixtureDefManager fixtureDefManager = FixtureUtils.createFixtureDefManagerFromXml(TestConstants.VALID_FIXTURE_FILENAME, 
																					  this, sandBox);
		//Find out the fixture to test
		FixtureManager fixtureManager = new FixtureManager(this, fixtureDefManager);

		System.out.println("Original SandBox Dir : " + sandBox.getSandBoxDir().getAbsolutePath());
		System.out.println("SandBox Dir From FixtureManager : " + fixtureManager.getSandBox().getSandBoxDir().getAbsolutePath());
		assertEquals("Sandbox paths doesn't match", sandBox.getSandBoxDir().getAbsolutePath(), 
													fixtureManager.getSandBox().getSandBoxDir().getAbsolutePath());
		
		sandBox.tearDown();
	}
	
}
