/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.ui;

import org.eclipse.core.resources.IProject;

import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

public class JsPerspectiveTests extends AbstractWizardTests {

	private static boolean isFirstRun = true;

	private static IScriptProject project;
	
	private static String JS_PERPECTIVE_ID = "org.ebayopensource.vjet.eclipse.ui.JavascriptBrowsingPerspective";
	

	public void setUp() throws Exception {
		super.setUp();
		if (isFirstRun) {
			IProject pr = getWorkspaceRoot().getProject(getTestProjectName());
			super.deleteResource(pr);
			project = setUpScriptProjectTo(getTestProjectName(), PROJECT_NAME);
			isFirstRun = false;
		}
	}

	public void testOpenPerspective() throws Exception {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		try {
			workbench.showPerspective(JS_PERPECTIVE_ID, window);
			IPerspectiveDescriptor pd = workbench.getActiveWorkbenchWindow().getActivePage().getPerspective();
			assertEquals(pd.getId(), JS_PERPECTIVE_ID);
			
		} catch (WorkbenchException e) {
			assertFalse(true);
		}
		
	}

}
