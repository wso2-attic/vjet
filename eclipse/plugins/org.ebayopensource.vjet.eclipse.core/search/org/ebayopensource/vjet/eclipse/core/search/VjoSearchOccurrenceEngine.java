/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstVars;

/**
 * Search engine for jst node occurrnce
 * 
 *  Ouyang
 * 
 */
public final class VjoSearchOccurrenceEngine {

	private static final int							FIELD	= 0;
	private static final int							METHOD	= 1;
	private static final int							TYPE	= 2;

	private static Map<Integer, IVjoOccurrenceSearcher>	s_map;

	static {
		s_map = new HashMap<Integer, IVjoOccurrenceSearcher>(3);
		s_map.put(FIELD, new VjoFieldSearcher());
		s_map.put(METHOD, new VjoMethodSearcher());
		s_map.put(TYPE, new VjoTypeSearcher());

	};

	private VjoSearchOccurrenceEngine() {
	}

	/**
	 * Find occurrences of the given node in the specified jst node tree
	 * 
	 * @param node
	 * @param scope
	 * @return
	 */
	public static List<VjoMatch> findOccurrence(IJstNode node, IJstNode scope) {
		IVjoOccurrenceSearcher searcher = getSearcher(node);
		if (searcher == null) {
			return Collections.emptyList();
		}
		return searcher.findOccurrence(node, scope);
	}

	private static IVjoOccurrenceSearcher getSearcher(IJstNode node) {
		int searchType = -1;
		if (isField(node)) {
			searchType = FIELD;
		} else if (isMethod(node)) {
			searchType = METHOD;
		} else if (isType(node)) {
			searchType = TYPE;
		}
		return getSearcher(searchType);
	}

	private static IVjoOccurrenceSearcher getSearcher(final int searchType) {
		return s_map.get(searchType);
	}

	private static boolean isField(IJstNode node) {
		return ((node instanceof JstVars) || (node instanceof JstArg) || (node instanceof IJstProperty));
	}

	private static boolean isMethod(IJstNode node) {
		return (node instanceof IJstMethod);
	}

	private static boolean isType(IJstNode node) {
		return (node instanceof IJstType);
	}
}
