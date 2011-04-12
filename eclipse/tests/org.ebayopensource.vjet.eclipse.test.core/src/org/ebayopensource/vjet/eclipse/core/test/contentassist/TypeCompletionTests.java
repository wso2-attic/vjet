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

public class TypeCompletionTests extends AbstractVjoModelTests {

	private static boolean isFirstRun = true;

	public void setUp() {
		setWorkspaceSufix("2");
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
	 * Tests on A<cursor> in single class
	 * 
	 * @throws ModelException
	 */
	public void testType1() throws ModelException {
		String[] names = new String[] { "this.vj$.A", "this.vj$.AC", "Array" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("pkg1/A.js"));
		int position = lastPositionInFile("A", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * Tests on this.vjo.A.<cursor> in single class
	 * 
	 * @throws ModelException
	 */
	public void testType2() throws ModelException {
		String[] names = new String[] { "a", "fooA" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("pkg1/A.js"));
		int position = lastPositionInFile("A.", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * Tests on C<cursor> in single class
	 * 
	 * @throws ModelException
	 */
	public void testType3() throws ModelException {
		String[] names = new String[] { "this.vj$.C", "this.vj$.CB",
				"this.vj$.CD" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("pkg2/C.js"));
		int position = lastPositionInFile("C", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * Tests on this.vjo.C.<cursor> in single class
	 * 
	 * @throws ModelException
	 */
	public void testType4() throws ModelException {
		String[] names = new String[] { "c", "fooC" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("pkg2/C.js"));
		int position = lastPositionInFile("C.", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * Tests on D<cursor> in single class
	 * 
	 * @throws ModelException
	 */
	public void testType5() throws ModelException {
		String[] names = new String[] { "this.vj$.D", "this.vj$.DB", "Date" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("pkg1/B.js"));
		int position = lastPositionInFile("D", module);

		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
}
