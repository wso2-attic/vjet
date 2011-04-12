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

import org.ebayopensource.dsf.jst.IJstNode;

/**
 * Searcher for jst node occurrence
 * 
 *  Ouyang
 * 
 */
public interface IVjoOccurrenceSearcher {

	/**
	 * Find the occurrence of the given jst node in the specified scope.
	 * 
	 * @param jstNode
	 *            the node need to find occurrence
	 * @param scope
	 * @return
	 */
	List<VjoMatch> findOccurrence(IJstNode jstNode, IJstNode scope);
}
