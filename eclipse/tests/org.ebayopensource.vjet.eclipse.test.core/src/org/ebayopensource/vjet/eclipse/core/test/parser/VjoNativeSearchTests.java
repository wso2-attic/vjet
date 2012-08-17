/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.SearchQueryParameters;
import org.ebayopensource.vjet.eclipse.core.search.VjoMatch;
import org.ebayopensource.vjet.eclipse.core.search.VjoSearchEngine;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class VjoNativeSearchTests extends AbstractSearchModelTests{

	public void setUp() throws Exception {
		super.setUp();
//		TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();
//		Collection<IJstType> types = mgr.getAllTypes();
//		for(IJstType type : types) {
//			if(type.getName() == null)
//				continue;
//			RemoveTypeEvent removeEvent = new RemoveTypeEvent(new TypeName(getProjectName(), type.getName()));
//			mgr.processEvent(removeEvent);
//		}
	}
	
	
	
	
	/**
	 * search of a method declaration
	 * 
	 * @throws ModelException
	 */
	public void testOnNativeMethodReference() throws ModelException {
		String js = "search/NativeTypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			
			IType[] types = module.getTypes();
			assertEquals("Wrong number of types", 1, types.length);
//			IJSType type = (IJSType) types[0];
			
//			VjoMatch[] matches=null;
			IType nativeType = getNativeType("Array");	
			IJSMethod element = findMethodByName(nativeType.getMethods(), "reverse");
			basicTest2(module, 2, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.REFERENCES, element);
			
			
			

			nativeType = getNativeType("HTMLDocument");	
			element = findMethodByName(nativeType.getMethods(), "getElementById");	
			basicTest2(module, 1, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.REFERENCES, element);
			
			
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}


	
	private VjoMatch[] basicTest2(IJSSourceModule module, int matches, int limitTo, int searchScope, IModelElement element) throws ModelException {

		List<VjoMatch> results = new ArrayList<VjoMatch>();

		SearchQueryParameters params = new SearchQueryParameters();
		createSearchParams(params, module, element, searchScope, limitTo);

		VjoSearchEngine c = new VjoSearchEngine();
		results = c.search(params);
		VjoMatch[] vjoMatchs = removeDuplicates(results);
		//assertNotNull("Empty result", results);

		//may have native references
		assertTrue("Wrong number of matches, at least greater than",vjoMatchs.length>matches);
		
		return vjoMatchs;
		
	}  


	private IType getNativeType(String typeName) {
		IJstType arrayJstType=CodeassistUtils.findNativeJstType(typeName);
		assertNotNull(arrayJstType);
		
		
		
		IType arrayType=CodeassistUtils.findNativeSourceType(arrayJstType);
		assertNotNull(arrayType);
		return arrayType;
	}
	
	
}
