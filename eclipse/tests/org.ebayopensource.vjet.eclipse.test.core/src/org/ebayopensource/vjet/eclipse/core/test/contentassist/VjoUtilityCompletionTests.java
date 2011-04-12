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

public class VjoUtilityCompletionTests extends AbstractVjoModelTests {

	private static boolean isFirstRun = true;

//	@Before
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
			//waitRefreshFinished();
			isFirstRun = false;
		}
	}

	/**
	 * completion on this.vjo.T
	 * 
	 * @throws ModelException
	 */
	//@Test
	public void testVjoStatic1() throws ModelException {
		String[] names = new String[] { "A" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("vjoCompletion/A.js"));
		int position = lastPositionInFile("this.vj$.", module);

		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	//@Test
	public void testVjoStatic2() throws ModelException {
		String[] names = new String[] { "stA", "stDoSomething" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("vjoCompletion/A.js"));
		int position = firstPositionInFile("this.vj$.A.", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	//@Test
	public void testVjoStatic3() throws ModelException {
		String[] names = new String[] { "stA", "stDoSomething" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("vjoCompletion/B.js"));
		int position = lastPositionInFile("this.vj$.A.", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	//@Test
	public void testVjoStatic4() throws ModelException {
		String[] names = new String[] { "stB", "stDoIt" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("vjoCompletion/B.js"));
		int position = lastPositionInFile("this.vj$.B.", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	//@Test
	public void testVjoStatic5() throws ModelException {
		String[] names = new String[] { "fooA", "func" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("vjoCompletion/B.js"));
		int position = lastPositionInFile("stDoSomething().", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	//@Test
	public void testVjo() throws ModelException {
		String[] names = new String[] { "printStackTrace(\"\");", "syserr", "sysout" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("vjoCompletion/Vjo.js"));
		int position = lastPositionInFile("vjo.", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	//@Test
	public void testVjoSysout() throws ModelException {
		String[] names = new String[] { "print(\"\");", "println(\"\");" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("vjoCompletion/VjoSysout.js"));
		int position = lastPositionInFile("vjo.sysout.", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	//@Test
	public void testVjoSyserr() throws ModelException {
		String[] names = new String[] { "print(\"\");", "println(\"\");" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("vjoCompletion/VjoSyserr.js"));
		int position = lastPositionInFile("vjo.syserr.", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
}
