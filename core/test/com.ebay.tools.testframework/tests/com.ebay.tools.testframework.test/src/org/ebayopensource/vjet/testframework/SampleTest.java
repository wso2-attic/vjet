/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework;

import org.ebayopensource.vjet.testframework.fixture.FixtureManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;


import junit.framework.TestCase;



public class SampleTest extends TestCase {
	
	FixtureManager m_fixtureManager  = new FixtureManager(this);
	
	@Override
	public void setUp() {
		m_fixtureManager.setUpAll();
	}

	@Override
	public void tearDown() {
		m_fixtureManager.tearDown();
	}
	
	public void testSomething() {
		// ...
		// Reference some resource in the workspace 
		// that has been setup by the framework
		ResourcesPlugin.getWorkspace().getRoot().getProject(
			"org.ebayopensource.vjet.testframework.test");
	}
}
