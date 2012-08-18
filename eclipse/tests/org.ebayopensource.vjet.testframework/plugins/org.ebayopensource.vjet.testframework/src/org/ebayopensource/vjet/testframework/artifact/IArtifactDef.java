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
 * 
 * @author ddodd
 *
 * IArtifactDef - Artifact definition
 * 
 * Each test artifact has an id and a set of attributes corresponding to it.
 * 
 */
public interface IArtifactDef {

	public String getArtifactId();
	
	public Map<String, String> getArtifactAttributes();
}
