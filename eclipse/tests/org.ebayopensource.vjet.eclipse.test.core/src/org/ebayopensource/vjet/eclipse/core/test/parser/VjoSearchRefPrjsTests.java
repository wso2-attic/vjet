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
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.mod.core.search.SearchEngine;

import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.SearchQueryParameters;

public class VjoSearchRefPrjsTests extends AbstractSearchModelTests {
	
	private static final String PROJECT1_NAME = "Project1";
	
	private static final String PROJECT2_NAME = "Project2";
	
	private static boolean isFirstRun = true;
		
	public void setUp() throws IOException {
		setWorkspaceSufix("3");
		mgr.reload(this);
		waitTypeSpaceLoaded();
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
		}
	}
		
	private void copyProjects(String... names) throws CoreException,
		IOException {
		for (String name : names) {
			setUpProject(name);
		}
	}
	
	@Override
	protected void createSearchParams(SearchQueryParameters params,
			IJSSourceModule module, IModelElement element, int searchScope,
			int limitTo) {
		super.createSearchParams(params, module, element, searchScope, limitTo);
		params.setScope(SearchEngine.createWorkspaceScope(module.getScriptProject().getLanguageToolkit())); // create workspace scope of searching
	}
		
	/**
	 * search of a type declaration
	 * 
	 * @throws ModelException
	 */
	public void testOnTypeDeclaration() throws ModelException {
		int matches = 1;
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				PROJECT2_NAME, "src", new Path("fld1/A.js"));
		
		IModelElement element = CodeassistUtils.findResourceType((ISourceModule) module, "fld1.A");
		basicTest(module, matches, IDLTKSearchConstants.TYPE, IDLTKSearchConstants.DECLARATIONS, element);
	}
	
	/**
	 * search of a type declaration in reference project
	 * 
	 * @throws ModelException
	 */
	public void testOnTypeDeclaration2() throws ModelException {
		int matches = 1;
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				PROJECT2_NAME, "src", new Path("fld2/C.js"));
		
		IModelElement element = CodeassistUtils.findResourceType((ISourceModule) module, "fld1.A");
		basicTest(module, matches, IDLTKSearchConstants.TYPE, IDLTKSearchConstants.DECLARATIONS, element);
	}
	
	/**
	 * search of a type reference
	 * 
	 * @throws ModelException
	 */
	public void testOnTypeReference() throws ModelException {
		int matches = 4;
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				PROJECT1_NAME, "src", new Path("fld1/A.js"));
		
		IModelElement element = CodeassistUtils.findResourceType((ISourceModule) module, "fld1.A");
		basicTest(module, matches, IDLTKSearchConstants.TYPE, IDLTKSearchConstants.REFERENCES, element);
	}
	
	/**
	 * search of a type reference in reference projects
	 * 
	 * @throws ModelException
	 */
	public void testOnTypeReference2() throws ModelException {
		int matches = 4;
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				PROJECT2_NAME, "src", new Path("fld2/C.js"));
		
		IModelElement element = CodeassistUtils.findResourceType((ISourceModule) module, "fld1.A");
		basicTest(module, matches, IDLTKSearchConstants.TYPE, IDLTKSearchConstants.REFERENCES, element);
	}
}
