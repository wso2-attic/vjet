/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;

/** this is a content model based upon HtmlTypeSets.  One set for exclude
 * and one for include.
 */
public class ContentModelSets implements IContentModel {
	final HtmlTypeSet m_include;
	final HtmlTypeSet m_exclude;
		
	public ContentModelSets(final HtmlTypeSet include){
		this(include, null);
	}
	public ContentModelSets(final HtmlTypeSet include, final HtmlTypeSet exclude){
		m_include = include;
		m_exclude = exclude;
	}
	/** This could return true if both sets are null or empty; however,
	 * one should use ContentModel.EMTPY instead.
	 */
	public boolean isEmpty() {
		return false;
	}
	public boolean isElementAllowed(final HtmlTypeEnum type){
		if (m_exclude != null && m_exclude.contains(type)){
			return false;
		}
		if (m_include != null && m_include.contains(type)){
			return true;
		}
		return false;
	}
}
