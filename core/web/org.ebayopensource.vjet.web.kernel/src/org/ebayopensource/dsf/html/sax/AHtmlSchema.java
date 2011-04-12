/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.sax;


/**
 * Alternative Html schema for Active client work. Created to avoid changing
 * HtmlSchema.java which is used by other areas of V4. We need to relax certain
 * constraints of that model like <link> can only appear in <head> tag to reflect
 * the real world of browser pages.
 * 
 * 
 *
 */
public class AHtmlSchema extends HtmlSchema {

	private static AHtmlSchema theSharedSchema = new AHtmlSchema();

	/**
	Returns the shared HTMLSchema object.  This object is initialized
	when the HTMLSchema class is loaded.  The parser uses this schema
	by default.  Any declarations that are automatically or manually
	added to it will be shared with every (default) parser.  To avoid
	this, use the HTMLSchema constructor instead.
	*/

	public static AHtmlSchema sharedSchema() {
		return theSharedSchema;
	}
	
	

	@Override
	protected void setupParents() {
		super.setupParents();
		parent("noscript", "html"); // allow <noscript> tag to be specified any place <script> tag is specified
	}



	@Override
	protected void setupElementTypes() {
		super.setupElementTypes();
		elementType("a", M_PCDATA|M_NOLINK|M_BLOCK, M_INLINE, 0);// Fudged to match browsers
		elementType("dir", M_ANY, M_BLOCK, 0);// Fudged to match browsers
		elementType("dl", M_ANY, M_BLOCK, 0);// Fudged to match browsers
		elementType("map", M_ANY, M_INLINE, 0);// Fudged to match browsers
		elementType("menu", M_ANY, M_BLOCK, 0);// Fudged to match browsers
		elementType("noscript", M_PCDATA, M_ANY & ~M_ROOT, F_CDATA); // allow <noscript> tag to be specified any place <script> tag is specified
		elementType("span",M_PCDATA | M_ANY, M_INLINE|M_NOLINK|M_BLOCK, 0); // Fudged to match browsers
		elementType("ul", M_ANY, M_BLOCK, 0);// Fudged to match browsers
	}

	@Override
	protected void setupAttributes() {
		super.setupAttributes();
		attribute("td", "colspan", "CDATA", null);
		attribute("td", "rowspan", "CDATA",null);
		attribute("button", "type", "CDATA", null);
	}
}
