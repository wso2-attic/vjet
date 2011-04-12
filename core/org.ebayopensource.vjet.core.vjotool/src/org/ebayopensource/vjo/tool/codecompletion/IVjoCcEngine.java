/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;

/**
 * Vjo code completion engine, accept jst type and position, return all the
 * legal vjo cc proposal data
 * 
 * 
 * 
 */
public interface IVjoCcEngine {

	/**
	 * Calculte proposal data
	 * 
	 * @param type,
	 *            the current JstType
	 * @param position,
	 *            the cursor position
	 * @return
	 */
	public List<IVjoCcProposalData> complete(IJstType type, int position);

	/**
	 * Calculate proposal data in syntax area.
	 * 
	 * @param groupName
	 *            JstType's group name
	 * @param typeName
	 *            JstType's type name
	 * @param content
	 *            JstType file's content
	 * @param position
	 *            Cursor position
	 * @return
	 */
	List<IVjoCcProposalData> complete(String groupName, String typeName,
			String content, int position);
	/**
	 * Calculate proposal data in comment area.
	 * 
	 * @param groupName
	 *            JstType's group name
	 * @param typeName
	 *            JstType's type name
	 * @param content
	 *            JstType file's content
	 * @param position
	 *            Cursor position
	 * @return
	 */
	List<IVjoCcProposalData> completeComment(String groupName, String typeName,
			String content, int position, String commentString, int commentOffset);

	/**
	 * @return VjoCcCtx
	 */
	VjoCcCtx getContext();
}
