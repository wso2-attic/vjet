/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.core;

import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.dltk.mod.core.search.AbstractSearchFactory;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;
import org.eclipse.dltk.mod.core.search.IMatchLocatorParser;
import org.eclipse.dltk.mod.core.search.SearchPattern;
import org.eclipse.dltk.mod.core.search.SearchRequestor;
import org.eclipse.dltk.mod.core.search.indexing.SourceIndexerRequestor;
import org.eclipse.dltk.mod.core.search.indexing.VjoSourceIndexerRequestor;
import org.eclipse.dltk.mod.core.search.matching.MatchLocator;

/**
 * Vjo search facrory implementation.
 * 
 * 
 *
 */
public class VjetSearchFactory extends AbstractSearchFactory {

	public IMatchLocatorParser createMatchParser(MatchLocator locator) {
		return null;
	}

	public SourceIndexerRequestor createSourceRequestor() {
		return new VjoSourceIndexerRequestor();
	}

	@Override
	public MatchLocator createMatchLocator(SearchPattern pattern,
			SearchRequestor requestor, IDLTKSearchScope scope,
			SubProgressMonitor monitor) {
		return null;
	}
}
