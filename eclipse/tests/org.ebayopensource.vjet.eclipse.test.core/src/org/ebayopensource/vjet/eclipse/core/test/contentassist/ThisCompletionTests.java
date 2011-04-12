/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.contentassist;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.ModelException;
//import org.junit.Before;
//import org.junit.Test;

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;


public class ThisCompletionTests extends AbstractVjoModelTests {

	private static boolean isFirstRun = true;

	//@Before
	public void setUp() {
		setWorkspaceSufix("1");
		IProject project = getWorkspaceRoot().getProject(getTestProjectName());

		if (isFirstRun) {
			try {
				super.deleteResource(project);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			super.setUpSuite();
			isFirstRun = false;
		}
	}
	
	/**
	 * Tests on this.<cursor> in single class
	 * @throws ModelException
	 */
//	@Test
	public void testThis1() throws ModelException {
		String[] names = new String[] { "a1", "a2", "doA", "fooA" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("thisCompletion/A.js"));
		int position = lastPositionInFile("this.", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * Tests on this.<cursor> in inherited class
	 * @throws ModelException
	 */
//	@Test
	public void testThis2() throws ModelException {
		String[] names = new String[] { "a2", "b", "fooA", "fooB" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("thisCompletion/B.js"));
		int position = lastPositionInFile("this.", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * Tests on this.<cursor> in inherited class
	 * @throws ModelException
	 */
//	@Test
	public void testThis3() throws ModelException {
		String[] names = new String[] { "a2", "doC", "fooA", "fooB", "fooC" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("thisCompletion2/C.js"));
		int position = lastPositionInFile("this.", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * Tests on this.fooC().<cursor> in inherited class
	 * @throws ModelException
	 */
//	@Test
	public void testThis4() throws ModelException {
		String[] names = new String[] { "a2", "fooA" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("thisCompletion2/C.js"));
		int position = lastPositionInFile("fooC().", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * test "this.TestCase(...)"
	 * see bug 1781
	 */
	//@Test
	public void testThis5() throws ModelException{
		String[] names = new String[] { "test_15_1_2_7"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("thisCompletion2/EcmaGlobalObjectTests.js"));
		int position = firstPositionInFile("this.TestCase( ", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
}
