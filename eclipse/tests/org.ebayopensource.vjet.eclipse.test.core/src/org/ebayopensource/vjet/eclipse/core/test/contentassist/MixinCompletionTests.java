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

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;

public class MixinCompletionTests extends AbstractVjoModelTests {

	private static boolean isFirstRun = true;

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
	 * Tests on vjo.type().m<cursor> in single class
	 * 
	 * @throws ModelException
	 */
	//@Test
	public void test1() throws ModelException {
		String[] names = new String[] { "mixin('')", "mixinProps('')"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("mixinCompletion/test1.js"));
		int position = lastPositionInFile(null, module);
		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * completion on mixin in mixed class
	 * tests this.<cursor> in mixed class
	 * 
	 * @throws ModelException
	 */
	//@Test
	public void test2() throws ModelException {
		String[] names = new String[] { "base", "doC", "fooM", "m4"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("mixinCompletion/test2.js"));
		int position = lastPositionInFile("this.", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * completion on mixin in mixed class
	 * tests this.vjo.T.<cursor> in mixed class
	 * 
	 * @throws ModelException
	 */
	//@Test
	public void test2a() throws ModelException {
		String[] names = new String[] {"stC"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("mixinCompletion/test2.js"));
		int position = lastPositionInFile("this.vj$.test2.", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * completion on mixin in extended class
	 * tests this.<cursor> in extended class
	 * 
	 * @throws ModelException
	 */
	//@Test
	public void test3() throws ModelException {
		String[] names = new String[] { "base", "doC", "doT", "fooM", "m4", "t"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("mixinCompletion/test3.js"));
		int position = lastPositionInFile("this.", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * completion on mixin in extended class
	 * tests this.vjo.T.<cursor> in extended class
	 * 
	 * @throws ModelException
	 */
	//@Test
	public void test3a() throws ModelException {
		String[] names = new String[] {"stT"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("mixinCompletion/test3.js"));
		int position = lastPositionInFile("this.vj$.test3.", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * completion on mixinProps in mixed class
	 * tests this.<cursor> in mixed class
	 * 
	 * @throws ModelException
	 */
	//@Test
	public void test4() throws ModelException {
		String[] names = new String[] { "base", "c", "doC"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("mixinCompletion/test4.js"));
		int position = firstPositionInFile("this.", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * completion on mixinProps in mixed class
	 * tests this.vjo.T.<cursor> in mixed class
	 * 
	 * @throws ModelException
	 */
	//@Test
	public void test4a() throws ModelException {
		String[] names = new String[] {"stC"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("mixinCompletion/test4.js"));
		int position = lastPositionInFile("this.vj$.test4.", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * completion on mixinProps in extended class
	 * tests this.<cursor> in extended class
	 * 
	 * @throws ModelException
	 */
	//@Test
	public void test5() throws ModelException {
		String[] names = new String[] { "base", "doC", "doT", "t"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("mixinCompletion/test5.js"));
		int position = lastPositionInFile("this.", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * completion on mixinProps in extended class
	 * tests this.vjo.T.<cursor> in extended class
	 * 
	 * @throws ModelException
	 */
	//@Test
	public void test5a() throws ModelException {
		String[] names = new String[] {"stT"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("mixinCompletion/test5.js"));
		int position = lastPositionInFile("this.vj$.test5.", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * completion on exptects in mtype
	 * tests expects("pkg.<cursor>") in module class
	 * 
	 * @throws ModelException
	 */
	//@Test
	public void test6() throws ModelException {
		String[] names = new String[] { "test0"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("mixinCompletion/test6.js"));
		int position = lastPositionInFile("expects(\"mixinCompletion.", module);
		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
}
