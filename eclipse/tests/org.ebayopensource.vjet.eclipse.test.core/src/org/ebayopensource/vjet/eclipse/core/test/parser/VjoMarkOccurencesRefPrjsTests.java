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
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.jface.text.Region;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.event.type.RemoveTypeEvent;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;

public class VjoMarkOccurencesRefPrjsTests extends AbstractMarkOccurencesTests {

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
			TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();
			Collection<IJstType> types = mgr.getAllTypes();
			for(IJstType type : types) {
				if(type.getName() == null)
					continue;
				RemoveTypeEvent removeEvent = new RemoveTypeEvent(new TypeName(getProjectName(), type.getName()));
				mgr.processEvent(removeEvent);
			}
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
	
	public void testOnNeeds() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				PROJECT2_NAME, "src", new Path("fld2/TypeB.js"));
		String name = "TypeA";
		
		List<Region> matches = getPositions(module.getSource(), name);
		assertNotNull("Cant find position in file", matches);
		assertEquals(3, matches.size());
		
		Region position = getFirstRegionInFile(name, module); // test on
														 // .needs("pkg.<cursor>T")
		// correct first match
		// must be "pkg.T" but was "T"
		correctPosition(matches, "fld1.", 0);
		correctPosition(matches, "fld1.", 1); // correct comment region
		
		basicTest(module, position, matches);
	}
	
	public void testOnTypeRef() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				PROJECT2_NAME, "src", new Path("fld2/TypeB.js"));
		String name = "TypeA";
		
		List<Region> matches = getPositions(module.getSource(), name);
		assertNotNull("Cant find position in file", matches);
		assertEquals(3, matches.size());
		
		Region position = getLastRegionInFile(name, module); // test on
														 // this.vj$.<cursor>T();
		// correct first match
		// must be "pkg.T" but was "T"
		correctPosition(matches, "fld1.", 0);
		correctPosition(matches, "fld1.", 1); // correct comment region
		basicTest(module, position, matches);
	}
}
