/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.dltk.mod.core.search.SearchPattern;

/**
 * Perform search matches use specified {@link SearchQueryParameters}.
 * 
 * 
 * 
 */
public class VjoSearchEngine {

	private static Map<Class<? extends SearchPattern>, IVjoElementSearcher> map = create();

	static {
		add(new VjoMethodSearcher());
		add(new VjoFieldSearcher());
		add(new VjoTypeDeclarationSearcher());
		add(new VjoTypeReferencesSearcher());
		add(new VjoTypeSearcher());
		add(new VjoPackageReferenceSearcher());
	}

	private static HashMap<Class<? extends SearchPattern>, IVjoElementSearcher> create() {
		return new HashMap<Class<? extends SearchPattern>, IVjoElementSearcher>();
	}

	/**
	 * Add to engine new {@link IVjoElementSearcher} searcher.
	 * 
	 * @param handler
	 *            {@link IVjoElementSearcher} searcher.
	 */
	public static void add(IVjoElementSearcher handler) {
		map.put(handler.getSearchPatternClass(), handler);
	}

	/**
	 * Perform search in corresponded {@link IVjoElementSearcher} searcher and
	 * return list of the {@link VjoMatch} objects.
	 * 
	 * @param parameters
	 *            {@link SearchQueryParameters} parameters
	 * @return list of the {@link VjoMatch} objects.
	 */
	public List<VjoMatch> search(SearchQueryParameters parameters) {
		// Add by Oliver. If the selected type is not existed, we will create
		// 'new SearchQueryParameters(null, stringPattern)' as the
		// SearchQueryParameter. So we need to judge whether the pattern is
		// null.
		if (parameters.getPattern() == null) {
			return Collections.EMPTY_LIST;
		}

		IVjoElementSearcher searcher = map.get(parameters.getPattern()
				.getClass());

		List<VjoMatch> result = new ArrayList<VjoMatch>();

		if (searcher != null) {

			// SearchPattern pattern = parameters.getPattern();

			if (parameters.isElementSpecification()) {
				searcher.searchByModel(parameters, result);
			} else {
				searcher.searchByPattern(parameters, result);
			}
			
			//remove duplicates!  sometimes there is duplication in searching results!
			Set<VjoMatch> matchSet=new HashSet<VjoMatch>();
			matchSet.addAll(result);
			result.clear();
			result.addAll(matchSet);

		}

		return result;
	}
}
