/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework;

import java.util.List;

import org.ebayopensource.vjet.testframework.artifact.IArtifactDef;
import org.ebayopensource.vjet.testframework.fixture.IFixtureDef;


public class ArtifactTestHelper {

	/*
	 * Returns the specific ArtifactDef that is available in the Fixture definition. 
	 * Returns null if the specific artifact is not found.
	 */
	public static IArtifactDef getArtifactDefWithType(IFixtureDef fixtureDef, String artifactType) {
		
		IArtifactDef artifactDef = null;

		List<IArtifactDef> artifacts = fixtureDef.getArtifactDefs();
		for (IArtifactDef artifact : artifacts) {
			
			if(artifact.getArtifactId().equals(artifactType)) {
			
				artifactDef = artifact;
				break;
			}
		}		

		return artifactDef;
	}

}
