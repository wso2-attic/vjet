/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.typespace;

import java.util.List;

/**
 * This interface contains methods for listen {@link TypeSpaceMgr} events.
 * 
 * 
 *
 */
public interface TypeSpaceListener {

	/**
	 * Calls when loading types to type space finished.
	 */
	void loadTypesFinished();

	/**
	 * Calls when refreshing (modify, add, delete) types finished.
	 * 
	 * @param list list of the {@link SourceTypeName} object to refresh.
	 */
	void refreshFinished(List<SourceTypeName> list);

}
