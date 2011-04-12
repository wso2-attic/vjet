/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package org.ebayopensource.vjet.eclipse.core.ts;
//
//import java.io.IOException;
//
//import org.eclipse.core.resources.IProject;
//import org.eclipse.core.runtime.CoreException;
//import org.eclipse.dltk.mod.internal.core.ModelManager;
//import org.junit.Before;
//import org.junit.Test;
//
//import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
//import org.ebayopensource.vjet.eclipse.tsloader.translator2.TypeSpaceMgr;
//
//public class CyclicDependencyTest extends AbstractVjoModelTests {
//	
//	private static final String TEST_C = "TestC";
//
//	private static boolean isFirstRun = true;
//
//	private TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();
//
//	@Before
//	public void setUpSuite() {
//		setWorkspaceSufix("TS");
//		IProject projectC = getWorkspaceRoot().getProject(TEST_C);
//		
//		if (isFirstRun) {
//			try {
//				super.deleteResource(projectC);
//				copyProjects(TEST_C);
//				ModelManager manager = ModelManager.getModelManager();
//				manager.startup();						
//				mgr.setTypeLoader(new EclipseTypeSpaceLoader());
//				mgr.reload();
//				mgr.setAllowRefresh(false);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			isFirstRun = false;
//		}
//	}
//
//	private void copyProjects(String... names) throws CoreException,
//			IOException {
//		for (String name : names) {
//			setUpProject(name);
//		}
//	}
//
//	@Test
//	public void test() {
//	}
//}
