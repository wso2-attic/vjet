/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.util;

import org.xml.sax.Attributes;

import org.ebayopensource.dsf.html.HtmlParserOptions;

/**
 * The <code>HtmlBuilderExt</code> class extends <code>HtmlBuilder</code> to
 * handle HtmlParserOptions
 * 
 * @see HtmlBuilder
 * @see HtmlParserOptions
 */
public class HtmlBuilderExt extends HtmlBuilder {
	/** set of HTML parser options */
	private HtmlParserOptions m_buildOptions;

	/**
	 * Sole constructor.
	 * 
	 * @see HtmlParserOptions
	 * @param buildOptions
	 *            HTML parser options
	 */
	public HtmlBuilderExt(HtmlParserOptions buildOptions) {
		m_buildOptions = buildOptions;
		m_fixDuplicateIds = buildOptions.isFixDuplicateIds();
	}
	
	/**
	 * Add attributes to element after checking if a fix for duplicated IDs is
	 * needed.
	 * 
	 * @param atts
	 *            list of attributes
	 */

	@Override
	protected void addAttributesToElement(final Attributes atts) {
		if (m_buildOptions.isIgnoreDuplicateIds()) {
			if (atts != null) {
				for (int i = 0; i < atts.getLength(); ++i) {
					final String name = atts.getLocalName(i);
					String value = atts.getValue(i);
					m_current.setAttribute(name, value);
				}
			}
		} else {
			super.addAttributesToElement(atts);
		}
	}
}
