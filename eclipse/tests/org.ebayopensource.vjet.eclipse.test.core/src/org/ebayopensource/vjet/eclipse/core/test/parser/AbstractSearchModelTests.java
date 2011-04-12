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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.SearchEngine;
import org.eclipse.dltk.mod.core.search.SearchPattern;

import org.ebayopensource.vjet.eclipse.core.IJSField;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.SearchQueryParameters;
import org.ebayopensource.vjet.eclipse.core.search.VjoMatch;
import org.ebayopensource.vjet.eclipse.core.search.VjoSearchEngine;
import org.ebayopensource.vjet.eclipse.core.test.contentassist.TestConstants;


public class AbstractSearchModelTests extends AbstractVjoModelTests{

	protected void basicTest(IJSSourceModule module, int matches, int limitTo, int searchScope, IModelElement element) throws ModelException {

		List<VjoMatch> results = new ArrayList<VjoMatch>();

		SearchQueryParameters params = new SearchQueryParameters();
		createSearchParams(params, module, element, searchScope, limitTo);

		VjoSearchEngine c = new VjoSearchEngine();
		results = c.search(params);
		VjoMatch[] vjoMatchs = removeDuplicates(results);
		//assertNotNull("Empty result", results);

		assertEquals("Wrong number of matches", matches, vjoMatchs.length);
	}  
	
    protected VjoMatch[] removeDuplicates(List<VjoMatch> matches){
		
		Set<VjoMatch> matchSet=new HashSet<VjoMatch>();
		matchSet.addAll(matches);
		return (VjoMatch[]) matchSet.toArray(new VjoMatch[matchSet.size()]);
	}

	protected void createSearchParams(SearchQueryParameters params, IJSSourceModule module, IModelElement element, int searchScope, int limitTo) {
		SearchPattern pattern = null;
		
		params.setElement(element);
		params.setElementSpecification(true);
		params.setLimitTo(searchScope);
		params.setScope(SearchEngine.createSearchScope(module.getScriptProject()));
		
		pattern = SearchPattern.createPattern(element,
				limitTo | searchScope);
		assertNotNull("Invalid search pattern", pattern);
		params.setPattern(pattern);
	}
	
	protected static List<IJSField> findFieldByName(IField[] fields, String name) {
		List<IJSField> foundFields = new ArrayList<IJSField>();
		for (IModelElement modelElement : fields) {
			if (name.equals(modelElement.getElementName())) {
				foundFields.add((IJSField) modelElement);
			}
		}
		return foundFields;
	}
		
	protected String getProjectName() {			
		return TestConstants.PROJECT_NAME_VJETPROJECT;
	}
}
