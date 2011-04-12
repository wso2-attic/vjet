/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.ebayopensource.dsf.jst.tests.ts.AllTypeSpaceTests;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	GroupDependencyTests.class,
	GroupVisibilityTests.class,
	AllTypeSpaceTests.class
})
public class AllJstTests {

}
