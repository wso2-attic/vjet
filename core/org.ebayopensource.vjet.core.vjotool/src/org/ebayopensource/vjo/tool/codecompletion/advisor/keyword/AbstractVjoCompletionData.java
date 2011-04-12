/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor.keyword;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcKeywordAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.proposaldata.AbstractVjoCcProposalData;

/**
 * The abstract class for all keyword vjo completion data objects.
 * 
 * 
 * 
 */
public abstract class AbstractVjoCompletionData extends
		AbstractVjoCcProposalData implements IVjoKeywordCompletionData {

	static Map<String, IVjoKeywordCompletionData> installedKeywords = new HashMap<String, IVjoKeywordCompletionData>();

	private String name;
	// commenting out as noone in the class is reading from this var.
	// private String trailingPart;

	private String fullName;

	// commenting out as field is never used or read from.
	// private static VjoCcCtx ctx;

	private String type = VjoCcKeywordAdvisor.ID;

	/**
	 * Constructs new Vjo keyword.
	 * 
	 * @param name
	 *            the keyword name.
	 * @param trailingPart
	 *            the keyword's trailing part
	 * @param isEnclosable
	 *            whether keyword is enclosable.
	 * @param isComposable
	 *            whether keyword is composable.
	 * @param isTopLevel
	 *            whether keyword is top-level one.
	 */
	AbstractVjoCompletionData(String name, String trailingPart
			) {

		this.name = name;

		fullName = name + trailingPart;

		installedKeywords.put(name, this);

	}


	public boolean canComplete(String text) {
		
		return "".equals(text)
				|| name.toLowerCase().startsWith(text.toLowerCase());
	}


	/**
	 * Gets string representation of the keyword.
	 * 
	 * @return string representation of the keyword.
	 */
	public String toString() {
		return fullName;
	}

	/**
	 * Gets name of the keyword.
	 * 
	 * @return name of the keyword.
	 */
	public String getName() {
		return name;
	}

	public String getAdvisor() {
		return type;
	}

	public String getData() {
		return name;
	}
}
