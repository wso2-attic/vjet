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

/**
 * Test case for completion in unexpectd position.
 * 
 *
 */
public class CompletionTestsForUnexpectedPosition extends AbstractVjoModelTests {

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
			isFirstRun = false;
		}
	}
	/**
	 * Tests completion when after a method which has no result
	 * @throws ModelException
	 */
//	@Test
	public void testCompletionAfterVoidResultMethod() throws ModelException {
		String[] names = new String[0];
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("unexpectedPosition/unexpectedPosition.js"));
		int position = lastPositionInFile(" this.method1().", module);

		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	//@Test
	public void testCompletionAftetVj1() throws ModelException{
		String[] names = new String[]{"vjo"};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("unexpectedPosition/BuyerTransactionAlert.js"));
		int position = lastPositionInFile("this.sId = pId;vj", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
}
