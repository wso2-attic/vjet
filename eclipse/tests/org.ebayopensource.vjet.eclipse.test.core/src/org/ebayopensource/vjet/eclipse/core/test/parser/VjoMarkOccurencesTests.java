/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.jface.text.Region;

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class VjoMarkOccurencesTests extends AbstractMarkOccurencesTests {

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
	 * test on type
	 * 
	 * @throws ModelException
	 */
	public void testOnType() throws ModelException {
		String js = "markOccurences/TypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "TypeA";

			List<Region> matches = getPositions(module.getSource(), name);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());

			Region position = getLastRegionInFile(name, module); // test on
			// this.vj$.<cursor>Type
			basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * test on type after keyword
	 * 
	 * @throws ModelException
	 */
	public void testOnTypeAfterKeyword() throws ModelException {
		String js = "markOccurences/TypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "TypeA";

			List<Region> matches = getPositions(module.getSource(), name);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());

			Region position = getLastRegionInFile(".stFunc", module); // test
			// on
			// this.vj$.Type<cursor>.stFunc()
			basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * test on type in super class
	 * 
	 * @throws ModelException
	 */
	public void testOnTypeSuperClass() throws ModelException {
		String js = "markOccurences/TypeB.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "TypeA";

			List<Region> matches = getPositions(module.getSource(), name);
			// correct first match
			// must be "markOccurences.TypeA" but was "TypeA"
			//correctPosition(matches, "markOccurences.", 0);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());

			Region position = getLastRegionInFile(name, module); // test on
			// this.vj$.Type<cursor>
			basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * test on field
	 * 
	 * @throws ModelException
	 */
	public void testOnField() throws ModelException {
		String js = "markOccurences/TypeC.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "stC";

			List<Region> matches = getPositions(module.getSource(), name);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());
			assertSame(3, matches.size());

			Region position = getFirstRegionInFile(name, module); // test on
			// <cursor>stC
			
			basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * test on field in method
	 * 
	 * @throws ModelException
	 */
	public void testOnFieldInMethod() throws ModelException {
		String js = "markOccurences/TypeC.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "stC";

			List<Region> matches = getPositions(module.getSource(), name);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());
			assertSame(3, matches.size());
			
			Region position = getLastRegionInFile(name, module); // test on
			// <cursor>stC


			basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * test on method
	 * 
	 * @throws ModelException
	 */
	public void testOnMethodDeclaration() throws ModelException {
		String js = "markOccurences/TypeD.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "foo";

			List<Region> matches = getPositions(module.getSource(), name);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());

			Region position = getLastRegionInFile(name, module);

			basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * test on method
	 * 
	 * @throws ModelException
	 */
	public void testOnMethodReference() throws ModelException {
		String js = "markOccurences/TypeD.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "stFunc";

			List<Region> matches = getPositions(module.getSource(), name);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());

			Region position = getFirstRegionInFile(name, module);

			
			 basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * test on method
	 * 
	 * @throws ModelException
	 */
	public void testOnMethodReferenceLast() throws ModelException {
		String js = "markOccurences/TypeD.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "stFunc";

			List<Region> matches = getPositions(module.getSource(), name);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());

			
			Region position = getLastRegionInFile(name, module);
		
			 basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * test on local variable
	 * 
	 * @throws ModelException
	 */
	public void testOnLocalVariable() throws ModelException {
		String js = "markOccurences/TypeF.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "num";

			List<Region> matches = getPositions(module.getSource(), name);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());

			Region position = getLastRegionInFile(name, module);

			basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * test on local variable
	 * 
	 * @throws ModelException
	 */
	public void testOnLocalVariableLast() throws ModelException {
		String js = "markOccurences/TypeF.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "num";

			List<Region> matches = getPositions(module.getSource(), name);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());

			int index = module.getSource().lastIndexOf(name);
			Region position = null;
			if (index >= 0) {
				position = new Region(index + name.length(), 0);
			}

			basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * test on reference of local variable
	 * 
	 * @throws ModelException
	 */
	public void testOnReferenceLocalVariable() throws ModelException {
		String js = "markOccurences/TypeF.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "num";

			List<Region> matches = getPositions(module.getSource(), name);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());

			Region position = getFirstRegionInFile(name, module);

			basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * test on reference of local variable
	 * 
	 * @throws ModelException
	 */
	public void testOnReferenceLocalVariableLast() throws ModelException {
		String js = "markOccurences/TypeF.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "num";

			List<Region> matches = getPositions(module.getSource(), name);
			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());

			int index = module.getSource().lastIndexOf(name);
			Region position = null;
			if (index >= 0) {
				position = new Region(index + name.length(), 0);
			}

			basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	/**
	 * test same name references for JstProperty and method variables
	 * 
	 * @throws ModelException
	 */
	public void testBug1853() throws ModelException {
		String js = "markOccurences/Bug1853.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			String name = "prop1 ";
			
			int offset = 0;
			List<Region> matches = new ArrayList<Region>();
			while (offset < module.getSource().length()) {
				if (module.getSource().indexOf(name, offset) >= 0) {
					offset = module.getSource().indexOf(name, offset);
					if (firstPositionInFile("var ", module) == offset
							|| firstBeforePositionInFile("prop1 = 50", module)+1 == offset){
						offset += name.length();
						continue;
					}
					matches.add(new Region(offset, name.trim().length()));
					offset += name.length();
				} else
					break;
			}

			assertNotNull("Cant find position in file", matches);
			assertNotSame(0, matches.size());

			Region position = getFirstRegionInFile(name.trim(), module);

			basicTest(module, position, matches);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
    public void testBug1601() throws Exception {
        String js = "markOccurences/TypeA.js";
        FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
        try {
            IJSSourceModule module = (IJSSourceModule) getSourceModule(
                    getProjectName(), "src", new Path(js));
            String name = "stA";

            List<Region> matches = getPositions(module.getSource(), name);
            assertNotNull("Cant find position in file", matches);
            assertNotSame(1, matches.size());

            Region position = getLastRegionInFile("stA", module); // test

            basicTest(module, position, matches);
        } finally {
            FixtureUtils.tearDownFixture(m_fixtureManager);
        }
    }
}
