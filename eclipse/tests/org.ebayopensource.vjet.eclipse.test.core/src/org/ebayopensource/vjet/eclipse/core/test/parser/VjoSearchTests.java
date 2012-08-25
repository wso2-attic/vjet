/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import java.net.MalformedURLException;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.mod.internal.ui.search.SearchMessages;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.core.IJSField;
import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.IJSType;
import org.ebayopensource.vjet.eclipse.core.search.TypeSearcher;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class VjoSearchTests extends AbstractSearchModelTests {

	public void setUp() throws Exception {
		super.setUp();
		// TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();
		// Collection<IJstType> types = mgr.getAllTypes();
		// for(IJstType type : types) {
		// if(type.getName() == null)
		// continue;
		// RemoveTypeEvent removeEvent = new RemoveTypeEvent(new
		// TypeName(getProjectName(), type.getName()));
		// mgr.processEvent(removeEvent);
		// }
	}

	/**
	 * search of a type declaration
	 * 
	 * @throws ModelException
	 */
	public void testOnTypeDeclaration() throws ModelException {
		String js = "search/TypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			mgr.reload(this);
			waitTypeSpaceLoaded();
			int matches = 1;
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));

			IModelElement element = module.getType("TypeA");
			basicTest(module, matches, IDLTKSearchConstants.TYPE,
					IDLTKSearchConstants.DECLARATIONS, element);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * search of a superclass
	 * 
	 * @throws ModelException
	 */
	public void testOnSuperclass() throws ModelException {
		String js = "search/TypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			int matches = 7;
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));

			IModelElement element = module.getType("TypeA");
			basicTest(module, matches, IDLTKSearchConstants.TYPE,
					IDLTKSearchConstants.REFERENCES, element);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * search of a field declaration
	 * 
	 * @throws ModelException
	 */
	public void testOnFieldDeclaration() throws ModelException {
		String js = "search/TypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			int matches = 1;
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));

			IType[] types = module.getTypes();
			assertEquals("Wrong number of types", 1, types.length);
			IJSType type = (IJSType) types[0];

			List<IJSField> foundFields = findFieldByName(type.getFields(),
					"count");
			assertEquals("Wrong number of fields found", 1, foundFields.size());

			IJSField element = foundFields.get(0);
			basicTest(module, matches, IDLTKSearchConstants.FIELD,
					IDLTKSearchConstants.DECLARATIONS, element);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * search of a field reference
	 * 
	 * @throws ModelException
	 */
	public void testOnFieldReference() throws ModelException {
		String js = "search/TypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			int matches = 7;
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));

			IType[] types = module.getTypes();
			assertEquals("Wrong number of types", 1, types.length);
			IJSType type = (IJSType) types[0];

			List<IJSField> foundFields = findFieldByName(type.getFields(),
					"count");
			assertTrue("Wrong number of fields found", foundFields.size() > 0);

			IJSField element = foundFields.get(0);
			basicTest(module, matches, IDLTKSearchConstants.FIELD,
					IDLTKSearchConstants.REFERENCES, element);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * search of a method declaration
	 * 
	 * @throws ModelException
	 */
	public void testOnMethodDeclaration() throws ModelException {
		String js = "search/TypeB.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			int matches = 1;
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));

			IType[] types = module.getTypes();
			assertEquals("Wrong number of types", 1, types.length);
			IJSType type = (IJSType) types[0];

			IJSMethod element = findMethodByName(type.getMethods(), "doIt");
			basicTest(module, matches, IDLTKSearchConstants.METHOD,
					IDLTKSearchConstants.DECLARATIONS, element);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * search of a method reference
	 * 
	 * @throws ModelException
	 */
	public void testOnMethodReference() throws ModelException {
		String js = "search/TypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			int matches = 2;
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));

			IType[] types = module.getTypes();
			assertEquals("Wrong number of types", 1, types.length);
			IJSType type = (IJSType) types[0];

			IJSMethod element = findMethodByName(type.getMethods(),
					"initialize");
			basicTest(module, matches, IDLTKSearchConstants.METHOD,
					IDLTKSearchConstants.REFERENCES, element);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * @Test
	 * //@Description("Bug3300: It tests the main method references")
	 * @throws ModelException
	 */
	public void testOnMainMethodReference() throws ModelException {
		String js = "search/TypeSearchMainMethod.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			int matches = 1;
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));

			IType[] types = module.getTypes();
			assertEquals("Wrong number of types", 1, types.length);
			IJSType type = (IJSType) types[0];

			IJSMethod element = findMethodByName(type.getMethods(), "main");
			basicTest(module, matches, IDLTKSearchConstants.METHOD,
					IDLTKSearchConstants.REFERENCES, element);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * VJET - Open type declaration doesn't search full type names
	 * 
	 * bug
	 */
	public void testOnFullTypeName() {
		String js = "search/TypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String expType = "search.TypeA";
			List<IJstType> results = TypeSearcher.getInstance().search(expType);

			// check results
			assertNotNull("No results", results);
			assertEquals("Wrong size of matches", 1, results.size());
			assertEquals("Wrong type found", expType, ((IJstType) results
					.get(0)).getName());
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * @throws Exception
	 * @throws MalformedURLException
	 * @Test
	 * //@Description("Bug3274: VJET: Invalid search returns error window with
	 *                        DLTK instead of VJO.")
	 */
	public void testBug3274() {
		String expectedString = "The operation is unavailable on the current selection. Please select a valid VJO element name.";
		assertEquals("Invalid search object message not right", expectedString,
				SearchMessages.DLTKElementAction_operationUnavailable_generic);

	}
}
