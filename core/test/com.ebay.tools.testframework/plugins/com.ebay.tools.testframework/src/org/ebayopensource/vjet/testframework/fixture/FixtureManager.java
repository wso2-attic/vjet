/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.fixture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.ebayopensource.vjet.testframework.artifact.ArtifactManagerRegistry;
import org.ebayopensource.vjet.testframework.artifact.IArtifactDef;
import org.ebayopensource.vjet.testframework.artifact.IArtifactManager;
import org.ebayopensource.vjet.testframework.sandbox.ISandbox;
import org.ebayopensource.vjet.testframework.sandbox.Sandbox;
import org.ebayopensource.vjet.testframework.util.TestUtils;



/**
 * @author   ddodd
 * 
 * FixtureManager is used to help setup and teardown fixtures defined by the FixtureDefs
 */
public class FixtureManager {

	Object m_testSource;

	ISandbox m_sandbox;

	IFixtureDefManager m_fixtureDefManager;

	List<IArtifactManager> m_tearDownList;

	boolean m_tearDown = true;

	public FixtureManager(Object testSource, IFixtureDefManager fixtureDefManager) {
		this(testSource, true, fixtureDefManager,  new Sandbox(testSource));
	}

	public FixtureManager(Object testSource) {
		this(testSource, true, null, new Sandbox(testSource));
	}
	
	public FixtureManager(Object testSource, boolean tearDown) {
		this(testSource, tearDown, null, new Sandbox(testSource));
	}

	/**
	 * Additional constructor. Mainly used for debuging fixtures
	 * 
	 * @param testSource
	 * @param tearDown
	 */
	public FixtureManager(Object testSource, boolean tearDown, IFixtureDefManager fixtureDefManager, ISandbox sandbox) {

		m_testSource = testSource;
		m_tearDownList = new ArrayList<IArtifactManager>();
		
		if (sandbox == null) {
			 sandbox = new Sandbox(testSource);
		}
		
		m_sandbox = sandbox;
		
		if (fixtureDefManager == null) {
			m_fixtureDefManager = getFixtureDefManagerFromFixturesXml(testSource, m_sandbox);
		} else {
			m_fixtureDefManager = fixtureDefManager;
		}
		m_tearDown = tearDown;
	}

	private FixtureDefManager getFixtureDefManagerFromFixturesXml(
			Object testSource, ISandbox sandbox) {
		
		return FixtureUtils.createFixtureDefManagerFromXml(testSource, sandbox);
	}


	
	/**
	 * Setup up the sandbox and fixtures. If we are also tearing down, then add
	 * artifacts to teardown list.
	 * 
	 */
	public void setUpAll() {

		m_sandbox.setUp();

		setupAllFixtures();
	}

	/**
	 * 
	 * @param fixtureId
	 *            If null
	 */
	public void setUp(String fixtureId) {

		m_sandbox.setUp();

		if (fixtureId == null) {
			setupAllFixtures();
		} else {
			IFixtureDef fixtureDef = m_fixtureDefManager.getFixtureDef(fixtureId);
			if (fixtureDef == null) {
				throw new RuntimeException("The fixture with id:" + fixtureId + " was not found!\nPlease ensure the fixture id is correct and is available in the classpath.");
			}
			processFixture(fixtureDef);
		}
	}

	private void setupAllFixtures() {
		Collection<IFixtureDef> fixtures = m_fixtureDefManager.getFixtureDefs();
		for (IFixtureDef fixtureDef : fixtures) {
			processFixture(fixtureDef);
		}
	}
	
	
	/**
	 * Process the fixture.  Add all the artifacts in the 
	 * @param fixtureDef
	 */
	private void processFixture(IFixtureDef fixtureDef) {

		// Unpack the fixture into the sandbox
		String fixtureId = fixtureDef.getFixtureId();
		
		if (fixtureId == null) {
			throw new RuntimeException("Fixture id not defined.  You must specify a id attribute for the fixture element");
		}
		
		try {

			// If this fixture comes with a jar, unpack it.
			unpackFixture(fixtureId);
			
			IArtifactManager fixtureArtifact = null;

			// Process and install all the artifacts of the fixture
			List<IArtifactDef> artifactDefs = fixtureDef.getArtifactDefs();
			for (IArtifactDef artifactDef : artifactDefs) {

				fixtureArtifact = getArtifactManager(artifactDef);
				boolean setupPerformed = fixtureArtifact.setUp();

				if (m_tearDown && setupPerformed) {
					m_tearDownList.add(fixtureArtifact);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}


	private void unpackFixture(String fixtureId) throws FileNotFoundException,
			IOException {
		String fixtureFilename = getFixtureFilename(fixtureId);
		final InputStream fixtureXmlInput = m_testSource.getClass().getClassLoader().getResourceAsStream(fixtureFilename);

		if (fixtureXmlInput == null) {
			return;
		}

		File outputFile = new File(m_sandbox.getSandBoxDir(), fixtureFilename);
		FileUtils.copyInputStreamToFile(fixtureXmlInput, outputFile);
		TestUtils.unzip(outputFile, m_sandbox.getSandBoxDir());
	}
	
	public static String getFixtureFilename(String fixtureId) {
		return fixtureId + ".zip";
	}

	/**
	 * Consult the artifact registry for the appropiate artifact handler.
	 * 
	 * @param artifactDef
	 * @return
	 */
	private IArtifactManager getArtifactManager(IArtifactDef artifactDef) {

		IArtifactManager artifact = ArtifactManagerRegistry.getInstance().getArtifact(artifactDef.getArtifactId());
		if (artifact == null) {
			throw new RuntimeException("Artifact Manager not found in registry with id:" + artifactDef.getArtifactId());
		}

		artifact.init(artifactDef, getSandBox());
		return artifact;
	}

	public void tearDown() {
		
		// Teardown in reverse order.
		if (m_tearDown) {
			for (int idx = m_tearDownList.size()-1; idx>=0; idx--) {
				IArtifactManager artifact = m_tearDownList.get(idx);
				artifact.tearDown();
			}
			m_sandbox.tearDown();
		}
	}

	public ISandbox getSandBox() {
		return m_sandbox;
	}

	public IFixtureDefManager getFixtures() {
		return m_fixtureDefManager;
	}

}
