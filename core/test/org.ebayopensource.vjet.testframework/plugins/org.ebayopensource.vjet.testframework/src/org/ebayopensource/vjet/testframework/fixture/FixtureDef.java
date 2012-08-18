/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ebayopensource.vjet.testframework.artifact.ArtifactDef;
import org.ebayopensource.vjet.testframework.artifact.IArtifactDef;


/**
 * 
 * @author ddodd
 * 
 * An implementation of {@link IFixtureDef}
 *
 */
public class FixtureDef implements IFixtureDef {

	private String m_fixtureId;

	private List<IArtifactDef> m_artifacts;
	
	public FixtureDef(Map<String, String> fixtureAttributes) {
		
		m_fixtureId = fixtureAttributes.get(FixtureContstants.FIXTURE_ID_ATTRIBUTE);
		m_artifacts = new ArrayList<IArtifactDef>();
		
	}

	public String getFixtureId() {
		return m_fixtureId;
	}
	
	public List<IArtifactDef> getArtifactDefs() {
		return m_artifacts;
	}
	
	public void addArtifactDef(ArtifactDef artifactDef) {
		m_artifacts.add(artifactDef);
	}
	
}

