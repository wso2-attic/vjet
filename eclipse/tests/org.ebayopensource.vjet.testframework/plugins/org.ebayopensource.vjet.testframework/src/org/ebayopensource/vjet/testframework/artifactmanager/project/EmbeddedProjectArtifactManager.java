/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.artifactmanager.project;


public class EmbeddedProjectArtifactManager extends WorkspaceProjectArtifactManager {

	
	private final String ARTIFACT_MANAGER_ID = "org.ebayopensource.vjet.testframework.artifact.project.embedded";
	
	public String getArtifactType() {
		return ARTIFACT_MANAGER_ID;
	}
}
