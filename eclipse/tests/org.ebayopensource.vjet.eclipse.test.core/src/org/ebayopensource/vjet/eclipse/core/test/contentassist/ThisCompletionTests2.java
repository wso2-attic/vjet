/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.contentassist;

import java.util.LinkedList;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.ModelException;

import org.ebayopensource.vjet.eclipse.codeassist.VjoCompletionEngine;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class ThisCompletionTests2 extends AbstractVjoModelTests {

	protected int lastPositionInFile(String string, String moduleName)
			throws ModelException {
		String content = ((IJSSourceModule) getSourceModule(
				TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(
						moduleName))).getSource();
		int position = content.lastIndexOf(string);

		if (position >= 0) {
			return position + string.length();
		}
		return -1;
	}
	
	protected int firstPositionInFile(String string, String moduleName)
			throws ModelException {
		String content = ((IJSSourceModule) getSourceModule(
				TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(
						moduleName))).getSource();
		
		int position = content.indexOf(string);
		if (position >= 0) {
			return position + string.length();
		}
		return -1;
	}

	protected void basicTest(String mname, int position, String[] compNames,
			String category) throws ModelException {
		assertNotSame("Invalid file content, cant find position", -1, position);

		LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();

		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(mname));

		VjoCompletionEngine c = createEngine(results, category, module);
		c.complete((ISourceModule) module, position, 0);
		compareCompletions(results, compNames, false);
	}

	/**
	 * Tests on this compeltion on class instance
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testThis1() throws ModelException {
		String js = "thisCompletion/B.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "fooA" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("this.a.", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * Test on this completion inside constructs--should propose instance
	 * members
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testThis2() throws ModelException {
		String js = "thisCompletion/C.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "base", "fooC", "i", "this.vj$" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("this.", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * Test on this completion inside constructs-should have proposal for
	 * consturctor parameter
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testThis3() throws ModelException {
		String js = "thisCompletion/C.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "j" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("this.i=", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
//			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * Tests on this completion for static method inside vjo main
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testThis4() throws ModelException {
		String js = "thisCompletion/D.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "doStatic" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("this.", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * Tests on this completion for super class constructor
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testThis5() throws ModelException {
		String js = "thisCompletion/TypeD.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "base" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("this.", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	/**
	 * Tests on this.vj$ completion in static block
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testThis6() throws ModelException {
		String js = "thisCompletion/TypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "this.vj$" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = firstPositionInFile("this.", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	/**
	 * Tests on this.vj$ completion in non-static block
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testThis7() throws ModelException {
		String js = "thisCompletion/TypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "this.vj$" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("this.", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
		
	@Override
	protected String getProjectName() {			
		return TestConstants.PROJECT_NAME_VJETPROJECT;
	}
}
