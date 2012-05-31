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
import java.util.Map;

/**
 * This interface define methods for loading types to the type space.
 * 
 * 
 *
 */
public interface ITypeSpaceLoader {

	/**
	 * Returns all types from the workspace.
	 * 
	 * @return all types from the workspace.
	 */
	List<SourceTypeName> getTypes();

	/**
	 * Returns all types from the group.
	 * 
	 * @param group name of the group
	 * @return all types from the group.
	 */
	List<SourceTypeName> getTypes(String group);
	
	/**
	 * Returns map with depends between groups.
	 * 
	 * @return map with depends between groups.
	 */
	Map<String, List<String>> getGroupDepends();

	/**
	 * Returns list of the all changed types.
	 * 
	 * @return list of the all changed types.
	 */
	List<SourceTypeName> getChangedTypes();
	
	/**
	 * Return list of the {@link GroupInfo} objects in workspace.
	 * 
	 * @return list of the {@link GroupInfo} objects.
	 */
	List<GroupInfo> getGroupInfo();
	/**
	 * Return list of the {@link GroupInfo} objects for one group.
	 * 
	 * @return list of the {@link GroupInfo} objects.
	 */
	List<GroupInfo> getGroupInfo(String group);

}
