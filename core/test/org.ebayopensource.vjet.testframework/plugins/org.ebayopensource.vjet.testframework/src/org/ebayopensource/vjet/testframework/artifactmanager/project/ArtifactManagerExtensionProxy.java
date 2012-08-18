/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.artifactmanager.project;

import org.ebayopensource.vjet.testframework.artifact.IArtifactDef;
import org.ebayopensource.vjet.testframework.artifact.IArtifactManager;
import org.ebayopensource.vjet.testframework.sandbox.ISandbox;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;


/**
 * 
 * @author ddodd
 * 
 * This class proxies the artifact managers from the ExtensionPoint registry
 *
 */
public class ArtifactManagerExtensionProxy implements IArtifactManager {

	
	private IConfigurationElement m_ce;
	private IArtifactManager m_artifactManager;
	private String m_artifactType;
	
	public ArtifactManagerExtensionProxy(IConfigurationElement ce) {
		m_ce = ce;
	}
	
	
	private IArtifactManager getArtifactManager() {

		if(m_artifactManager == null) {
			try {
				m_artifactManager = (IArtifactManager)m_ce.createExecutableExtension("className");
				m_artifactType = m_ce.getAttribute("artifactType");
			} catch(CoreException e) {
				throw new RuntimeException(e);
			}
		}
		return m_artifactManager;
	}
	
	
	public String getArtifactType() {
		getArtifactManager();
		return m_artifactType;
	}
	
	
	public void init(IArtifactDef artifactDef, ISandbox sandBox) {
		
		IArtifactManager artifactManager = getArtifactManager();
		artifactManager.init(artifactDef, sandBox);

	}

	public boolean setUp() {
		IArtifactManager artifactManager = getArtifactManager();
		return artifactManager.setUp();

	}

	public void tearDown() {
		IArtifactManager artifactManager = getArtifactManager();
		artifactManager.tearDown();
	}

}
