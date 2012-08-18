/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.artifact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.vjet.testframework.artifactmanager.project.ArtifactManagerExtensionProxy;
import org.ebayopensource.vjet.testframework.artifactmanager.project.ZipProjectArtifactManager;
import org.ebayopensource.vjet.testframework.util.ExtensionPointUtils;
import org.eclipse.core.runtime.IConfigurationElement;



/**
 * @author ddodd
 * 
 * ArtifactManagerRegistry
 * 
 * This class loads the artifact managers that are registered through extension points.
 * 
 */
public class ArtifactManagerRegistry {

	static Map<String, IArtifactManager> m_registry;

	static ArtifactManagerRegistry m_instance = null;

	public static ArtifactManagerRegistry getInstance() {
		if (m_instance == null) {
			m_instance = new ArtifactManagerRegistry();
			m_registry = new HashMap<String, IArtifactManager>();

			m_instance.registerArtifact(
					ZipProjectArtifactManager.ARTIFACT_MANAGER_ID,
					new ZipProjectArtifactManager());
		}

		loadExtensionPointArtifactManagers(m_registry);

		return m_instance;
	}

	private static void loadExtensionPointArtifactManagers(
			Map<String, IArtifactManager> m_registry2) {

		List<IConfigurationElement> configElements = ExtensionPointUtils
				.getConfigurationElements("org.ebayopensource.vjet.testframework",
						"ArtifactManager");

		// Register all the artifact managers from extension points.
		for (IConfigurationElement configElement : configElements) {
			ArtifactManagerExtensionProxy artifactManager = new ArtifactManagerExtensionProxy(configElement);
			m_instance.registerArtifact(artifactManager.getArtifactType(), artifactManager);
		}
	}

	public void registerArtifact(String id, IArtifactManager artifact) {
		m_registry.put(id, artifact);
	}

	public IArtifactManager getArtifact(String artifactId) {
		return m_registry.get(artifactId);
	}

}
