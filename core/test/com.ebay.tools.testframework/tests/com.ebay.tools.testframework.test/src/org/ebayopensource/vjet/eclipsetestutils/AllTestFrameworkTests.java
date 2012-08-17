/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipsetestutils;

import org.ebayopensource.vjet.testframework.PluginTestFrameworkTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;


public class AllTestFrameworkTests {
	
	public static Test suite() {
		final TestSuite suite = new TestSuite(AllTestFrameworkTests.class.getName() );
		// $JUnit-BEGIN$
		suite.addTest(PluginTestFrameworkTestSuite.suite());
		// $JUnit-END$
		return suite;
	}
	

}
