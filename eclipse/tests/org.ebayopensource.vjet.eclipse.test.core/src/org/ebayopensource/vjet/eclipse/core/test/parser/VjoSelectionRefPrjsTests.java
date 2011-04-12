/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.ModelException;

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;

public class VjoSelectionRefPrjsTests extends AbstractSelectionModelTests {
	
	private static final String PROJECT1_NAME = "Project1";
	
	private static final String PROJECT2_NAME = "Project2";
	
	private static boolean isFirstRun = true;
		
	public void setUp() throws IOException {
		setWorkspaceSufix("3");
		IProject project1 = getWorkspaceRoot().getProject(PROJECT1_NAME);
		IProject project2 = getWorkspaceRoot().getProject(PROJECT2_NAME);
		
		if (isFirstRun) {
			try {
				super.deleteResource(project1);
				super.deleteResource(project2);
				copyProjects(PROJECT1_NAME, PROJECT2_NAME);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			isFirstRun = false;
			mgr.reload(this);
			waitTypeSpaceLoaded();
		}
	}
		
	private void copyProjects(String... names) throws CoreException,
		IOException {
		for (String name : names) {
			setUpProject(name);
		}
	}
				
	/**
	 * selection on needs of referenced project
	 * 
	 * @throws ModelException
	 */
	public void testOnNeeds() throws ModelException {
		String[] names = new String[] { "B" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				PROJECT2_NAME, "src", new Path("fld2/D.js"));
		int position = firstPositionInFile("B", module);
		
		basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
	}
	
	/**
	 * selection on type reference
	 * 
	 * @throws ModelException
	 */
	public void testOnRefType() throws ModelException {
		String[] names = new String[] { "B" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				PROJECT2_NAME, "src", new Path("fld2/D.js"));
		int position = firstPositionInFile("vj$.", module) + 1; // cursor on this.vj$.<cursor>Type

		/**
		 * comment by kevin:
		 * currently, 'vj$' should has no corresponding dltk element
		 */
//		basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
	}
	
	/**
	 * selection on method
	 * 
	 * @throws ModelException
	 */
	public void testOnMethod() throws ModelException {
		String[] names = new String[] { "b" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				PROJECT2_NAME, "src", new Path("fld2/D.js"));
		int position = firstPositionInFile("b", module) + 1; // cursor on this.vj$.<cursor>Type

		basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
	}
}
