/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.fixture;

import java.util.List;

import org.ebayopensource.vjet.testframework.artifact.IArtifactDef;


/**
 * The state or context a test can rely on is known (from JUnit) as a "fixture,"
 * the same state generally available to each test. In general this includes a
 * "sandbox", a directory pre-populated with input and output files, etc. In
 * Eclipse, the parts of a fixture might include certain projects created or
 * preferences established or some calculated value at deployment time. Each
 * part of these fixtures is known as artifacts. Test fixtures define a set of
 * artifacts.
 * 
 * @author <a href="mailto:ddodd@ebay.com">David Dodd</a>
 * 
 */
public interface IFixtureDef {

	/**
	 * Unique identifier of fixture.
	 * 
	 * @return identifier
	 */
	public String getFixtureId();

	/**
	 * List of artifact definitions contained by this fixture.
	 * 
	 * @return artifact definitions
	 */
	public List<IArtifactDef> getArtifactDefs();

}
