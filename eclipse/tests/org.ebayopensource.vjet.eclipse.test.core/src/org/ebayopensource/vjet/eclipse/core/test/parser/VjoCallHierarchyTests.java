/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;

import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class VjoCallHierarchyTests extends AbstractCallHierarchyModelTests {
	
	public void testStaticMethods() throws ModelException {
		String js = "callHierarchy/A.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String method = "a";
			String[] res = { "test"};
						
			basicTest("callHierarchy/A.js", method, res, IDLTKSearchConstants.ALL_OCCURRENCES);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testInstanceMethods() throws ModelException {
		String js = "callHierarchy/B.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String method = "b";
			String[] res = { "test"};
						
			basicTest("callHierarchy/B.js", method, res, IDLTKSearchConstants.ALL_OCCURRENCES);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testMethods() throws ModelException {
		String js = "callHierarchy/C.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String method = "a";
			String[] res = { "foo", "test"};
						
			basicTest("callHierarchy/C.js", method, res, IDLTKSearchConstants.ALL_OCCURRENCES);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
}
