/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.ebayopensource.vjet.testframework.artifact.IArtifactDef;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;
import org.ebayopensource.vjet.testframework.fixture.IFixtureDef;
import org.ebayopensource.vjet.testframework.fixture.IFixtureDefManager;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

public class TestFixtureDefManager extends TestCase {

	static String PROJECT_ARTIFACT_ID = "repositoryeditor.project.artifact";

	/**
	 * Sample definition manager
	 */
	IFixtureDefManager defManager = new IFixtureDefManager() {

		/**
		 * Sample artifact definition
		 */
		IArtifactDef sampleProjectArtifact = new IArtifactDef() {

			Map<String, String> m_artifactAttributes = null;

			public Map<String, String> getArtifactAttributes() {

				if (m_artifactAttributes == null) {
					m_artifactAttributes = new HashMap<String, String>();
					m_artifactAttributes.put("artifactType", getArtifactId());
					m_artifactAttributes.put("testBundleId",
							"org.ebayopensource.vjet.testframework.test");
					m_artifactAttributes
							.put("projectName", PROJECT_ARTIFACT_ID);
				}
				return m_artifactAttributes;
			}

			public String getArtifactId() {
				return "org.ebayopensource.vjet.testframework.artifact.project.workspace";
			}
		};
		/***************************************************/

		/**
		 * Sample fixture definition
		 */
		IFixtureDef sampleFixture2 = new IFixtureDef() {

			List<IArtifactDef> m_artifactDefs;

			public List<IArtifactDef> getArtifactDefs() {
				if (m_artifactDefs == null) {
					m_artifactDefs = new ArrayList<IArtifactDef>();
					m_artifactDefs.add(sampleProjectArtifact);
				}
				return m_artifactDefs;
			}

			public String getFixtureId() {
				return "org.ebayopensource.vjet.testframework.fixture2";
			}
		};

		/***************************************************/

		/**
		 * Methods of IFixtureDefManager
		 */
		public IFixtureDef getFixtureDef(String fixtureId) {

			if (fixtureId.equals(sampleFixture2.getFixtureId())) {
				return sampleFixture2;
			}
			return null;
		}

		Collection<IFixtureDef> defCollection = new ArrayList<IFixtureDef>();

		public Collection<IFixtureDef> getFixtureDefs() {

			// Add our fixtures
			if (defCollection.size() == 0) {
				defCollection.add(sampleFixture2);

			}
			return defCollection;

		}
	};

	public void testCodeFixtureDefManager() {

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		IResource project = workspace.getRoot().findMember(PROJECT_ARTIFACT_ID);
		assertNull(project);

		FixtureManager fixtureManager = new FixtureManager(this, defManager);
		fixtureManager.setUpAll();

		project = workspace.getRoot().findMember(PROJECT_ARTIFACT_ID);
		assertNotNull(project);

		fixtureManager.tearDown();
		project = workspace.getRoot().findMember(PROJECT_ARTIFACT_ID);
		assertNull(project);

	}

}
