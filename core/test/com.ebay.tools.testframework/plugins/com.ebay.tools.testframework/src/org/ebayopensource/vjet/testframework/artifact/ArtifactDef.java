/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.artifact;

import java.util.Map;

/**
 * @author ddodd
 *
 * Artifact definition.
 * 
 * This class defines a test artifact.
 * 
 */
public class ArtifactDef implements IArtifactDef{

	private String m_artifactId;
	private Map<String, String> m_artifactAttributes;

	public ArtifactDef(Map<String, String> attributes) {
		
		m_artifactId = attributes.get(ArtifactConstants.ARTIFACT_TYPE_ATTRIBUTE);
		m_artifactAttributes = attributes;
	}

	public Map<String, String> getArtifactAttributes() {
		return m_artifactAttributes;
	}

	public String getArtifactId() {
		return m_artifactId;
	}

}
