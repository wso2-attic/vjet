/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.ui.PartInitException;

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class VjoSelectionTests extends AbstractSelectionModelTests {

	private static final String EDITOR_ID = "org.ebayopensource.vjet.ui.VjetJsEditor";

	/**
	 * selection on imports
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnImports() throws ModelException {
		String js = "selection/A.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "B" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = firstPositionInFile("B", module); // test on
															 // .needs("selection.<cursor>B")

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * selection on type declaration
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnTypeDeclaration() throws ModelException {
		String js = "selection/A.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "A" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = firstPositionInFile("A", module); // test on
															// vjo.ctype("selection.<cursor>A")

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	/**
	 * selection on type reference
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnTypeReference() throws ModelException {
		String js = "selection/A.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "A" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("A", module); // test on
															// this.vj$.A<cursor>

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * selection on a field
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnField() throws ModelException {
		String js = "selection/A.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "stA" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = firstPositionInFile("stA", module);

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * selection on a field
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnField2() throws ModelException {
		String js = "selection/A.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "stA" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("stA", module);

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * selection on a field
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnField3() throws ModelException {
		String js = "selection/A.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "x" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = firstPositionInFile("x", module);

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	/**
	 * selection on field initializer
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnFieldInitializer() throws ModelException {
		String js = "selection/D.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "B" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = firstPositionInFile("B", module);

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	/**
	 * selection on a field
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnStaticField() throws ModelException {
		String js = "selection/C.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "stC" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("stC", module);

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * selection on a method
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnMethod() throws ModelException {
		String js = "selection/A.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "stFunc" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = firstPositionInFile("stFunc", module);

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * selection on a method
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnMethod2() throws ModelException {
		String js = "selection/A.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "stFunc" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("stFunc", module);

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * selection on a method
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnMethod3() throws ModelException {
		String js = "selection/A.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "foo" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("foo", module);

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * selection on a method
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnMethod4() throws ModelException {
		String js = "selection/A.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "foo" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("foo", module) + 3; // test on
																	// foo<cursor>

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	/**
	 * selection on a method
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void testOnStaticMethod() throws ModelException {
		String js = "selection/C.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "stFunc" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("stFunc", module) + 3; // test on
																	// foo<cursor>

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testmyStaticFuncMethod() throws ModelException {
		String js = "selection/E.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "myStaticFunc" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("myStaticFunc", module);

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testBug1148() throws ModelException {
		String js = "selection/Bug1148.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "document" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("document", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testProblemVjoSourceParserAndTypeSpaceLoader() throws PartInitException, ModelException{
		
		/*VjetWorkspace.getInstance().setAllowRefresh(false);
		FixtureManager m_fixtureManager  = new FixtureManager(this);
		m_fixtureManager.setUp(TestConstants.FIXTURE_ID_VJETPROJECT);
		
		
		IWorkbench workbench= PlatformUI.getWorkbench();
		Workspace m_workspace = (Workspace) ResourcesPlugin.getWorkspace();
		 
		 //workbench = PlatformUI.getWorlbench()
		IPath path = new Path("VJETProject").append("selection").append("A");		
		File file = (File) m_workspace.newResource(path,IResource.FILE);
		FileEditorInput input = new FileEditorInput(file);
		workbench.getActiveWorkbenchWindow().getActivePage().openEditor(input,
				EDITOR_ID,true);
		
		VjetWorkspace.getInstance().reload();

		try {
			String[] names = new String[] { "stA" };
			String module = "selection/A.js";
			int position = firstPositionInFile("stA", module);

			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
		 //2. workbench.getAcvtiveWorkbenchWIndow().getActivePage().openEditor(....)
*/	}
	
}
