/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.mod.core.CompletionProposal;

/**
 * The abstract class for all keyword vjo completion data objects.
 * 
 * 
 *
 */
public abstract class AbstractVjoCompletionData implements IVjoCompletionData {

	static Map<String, IVjoCompletionData> installedKeywords = new HashMap<String, IVjoCompletionData>();

	private boolean isEnclosable = false;
	private boolean isComposable = false;
	private boolean isTopLevel = false;

	private String name;
	private String trailingPart;

	private String fullName;
	private int flags;
	private int type = CompletionProposal.KEYWORD;
	private int relevance = 1;
	private boolean isProposal = true;

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
	AbstractVjoCompletionData(String name, String trailingPart,
			boolean isEnclosable, boolean isComposable, boolean isTopLevel) {

		this.name = name;
		this.trailingPart = trailingPart;

		this.isEnclosable = isEnclosable;
		this.isComposable = isComposable;
		this.isTopLevel = isTopLevel;

		fullName = name + trailingPart;

		installedKeywords.put(name, this);

	}

	/**
	 * Constructs new Vjo keyword.
	 * 
	 * @param name
	 *            the keyword name.
	 */
	AbstractVjoCompletionData(String name, String trailingPart) {
		this(name, trailingPart, false, false, false);
	}

	/**
	 * Whether keyword is enclosable.
	 * 
	 * @return true if keyword is enclosable otherwise false.
	 */
	public boolean isEnclosableKeyword() {
		return isEnclosable;
	}

	/**
	 * Whether keyword is composable.
	 * 
	 * @return true if keyword is composable otherwise false.
	 */
	public boolean isComposableKeyword() {
		return isComposable;
	}

	/**
	 * Whether keyword is unclosed.
	 * 
	 * @return true if keyword is unclosed otherwise false;
	 */
	public boolean isUnclosed() {
		return false;
	}

	/**
	 * Whether keyword is complemented part of another one.
	 * 
	 * @return true if keyword is complemented part of another one otherwise
	 *         false.
	 */
	public boolean isComplementedPart() {
		return false;
	}

	/**
	 * Whether keyword is top-level one.
	 * 
	 * @return true if keyword is top-level one otherwise false.
	 */
	public boolean isTopLevelKeyword() {
		return isTopLevel;
	}

	public boolean canComplete(String text) {
		return text.length()>0 && ("".equals(text) || name.toLowerCase().startsWith(text.toLowerCase()));
	}

	/**
	 * By default keywords can not contains any triggers.
	 */
	public boolean isAllowedTrigger(char trigger) {
		return false;
	}

	public int getCursorOffsetAfterCompletion() {
		return fullName.length();
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

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getFlags() {
		return flags;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isProposal() {
		return isProposal;
	}

	public void setProposal(boolean isProposal) {
		this.isProposal = isProposal;
	}

	public int getRelevance() {
		return relevance;
	}

	public void setRelevance(int relevance) {
		this.relevance = relevance;
	}

}
