/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.List;

import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.search.SearchPattern;

/**
 * Interface for searcher. Can perform search by model or by pattern.
 * 
 * 
 *
 */
public interface IVjoElementSearcher {

	/**
	 * Returns class of the search patterns
	 * @return class of the search patterns
	 */
	Class<? extends SearchPattern> getSearchPatternClass();

	/**
	 * Perform search by {@link IModelElement}.
	 * 
	 * @param parameters search parameters
	 * @param result list found elements
	 */
	void searchByModel(SearchQueryParameters parameters, List<VjoMatch> result);

	/**
	 * Perform search by {@link SearchPattern}.
	 * 
	 * @param parameters search parameters
	 * @param result list found elements
	 */
	void searchByPattern(SearchQueryParameters parameters, List<VjoMatch> result);

}
