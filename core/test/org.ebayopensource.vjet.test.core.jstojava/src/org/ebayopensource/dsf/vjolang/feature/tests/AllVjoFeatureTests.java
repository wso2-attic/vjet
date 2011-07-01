/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.vjolang.feature.tests;

import org.ebayopensource.dsf.vjolang.feature.tests.comments.VjoCommentTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	BuildControllerTests.class,
	ParsingTests.class,
	VjoSyntaxParsingTests.class,
	VjoAsJstTests.class,
	VjoCommentTest.class,
	VjoNSTests.class
})

public class AllVjoFeatureTests {

	
}
