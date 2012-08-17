/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PluginTestFrameworkTestSuite extends TestSuite {
	
	public static Test suite() {
		
		TestSuite suite = new TestSuite("Tests for the Plugin Test Framework");
		System.out.println("PluginTestFrameworkTestSuite starting ...");
		suite.addTestSuite(BundleLocationTests.class);
		suite.addTestSuite(EmbeddedProjectArtifactTests.class);
		suite.addTestSuite(FixtureAndArtifactTests.class);
		suite.addTestSuite(PluginTestManagerTests.class);
		suite.addTestSuite(ProjectArtifactManagerTests.class);
		suite.addTestSuite(SampleTest.class);
		suite.addTestSuite(TestFixtureDefManager.class);

		return suite;
	}

}
