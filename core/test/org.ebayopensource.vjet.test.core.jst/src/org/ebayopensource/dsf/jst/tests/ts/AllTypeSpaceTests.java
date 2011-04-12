/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	DependencyGraphTests.class,
	TraversalTests.class,
	PropertyIndexTests.class,
	MethodIndexTests.class,
	QueryTests.class,
	TypeEventTests.class,
	PropertyEventTests.class,
	MethodEventTests.class,
	NotificationTests.class,
//	ResourceMgrTests.class,
	JstTypeSerializerTests.class
})

public class AllTypeSpaceTests {

}