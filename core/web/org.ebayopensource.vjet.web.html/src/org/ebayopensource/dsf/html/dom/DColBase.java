/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import org.ebayopensource.dsf.common.Z;

/**
* Abstract base for COL and COLGROUP
*/
public abstract class DColBase extends BaseAttrsHtmlElement {

	private static final long serialVersionUID = 3257845497996915254L;
	/** "left" */
	public static final String ALIGN_LEFT = "left" ;	// default, ignoreHtmlKeyword
	/** "center" */
	public static final String ALIGN_CENTER = "center" ; // ignoreHtmlKeyword
	/** "right" */
	public static final String ALIGN_RIGHT = "right" ; // ignoreHtmlKeyword
	/** "justify" */
	public static final String ALIGN_JUSTIFY = "justify" ; // ignoreHtmlKeyword
	/** "char" */
	public static final String ALIGN_CHAR = "char" ; // ignoreHtmlKeyword
	
	/** "top" */
	public static final String VALIGN_TOP = "top" ;
	/** "middle" */
	public static final String VALIGN_MIDDLE = "middle" ;
	/** "bottom" */
	public static final String VALIGN_BOTTOM = "bottom" ;
	/** "baseline" */
	public static final String VALIGN_BASELINE = "baseline" ;		
	
	//
	// Constructor(s)
	//
	DColBase(final HtmlTypeEnum type) {
		super(type);
	}

	DColBase(final DHtmlDocument doc,final HtmlTypeEnum type){
		super(doc, type);
	}
	//
	// HTML Attributes
	//	
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlAlign() {
		return capitalize(getHtmlAttribute(EHtmlAttr.align));
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public DColBase setHtmlAlign(final String align) {
		setHtmlAttribute(EHtmlAttr.align, align);
		return this ;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	// purposefully did not use full name "char" to avoid confusion
	public String getHtmlCh() {
		return Util.getHtmlCh(this) ;
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	// purposefully did not use full name "char" to avoid confusion
	public DColBase setHtmlCh(final String _char) {
		Util.setHtmlCh(this, _char) ;
		return this;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	// purposefully did not use full name "char" to avoid confusion
	public String getHtmlChOff() {
		return getHtmlAttribute(EHtmlAttr.charoff);
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	// purposefully did not use full name "char" to avoid confusion
	public DColBase setHtmlChOff(final String charoff) {
		setHtmlAttribute(EHtmlAttr.charoff, charoff);
		return this ;
	}

	public int getHtmlSpan() {
		return getHtmlAttributeInteger(EHtmlAttr.span);
	}

	public DColBase setHtmlSpan(final String span) {
		setHtmlAttribute(EHtmlAttr.span, span);
		return this ;
	}
	
	public DColBase setHtmlSpan(final int span) {
		return setHtmlSpan(String.valueOf(span)) ;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlValign() {
		return capitalize(getHtmlAttribute(EHtmlAttr.valign));
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public DColBase setHtmlValign(final String vAlign) {
		setHtmlAttribute(EHtmlAttr.valign, vAlign);
		return this ;
	}

	public String getHtmlWidth() {
		return getHtmlAttribute(EHtmlAttr.width);
	}

	public DColBase setHtmlWidth(final String width) {
		setHtmlAttribute(EHtmlAttr.width, width);
		return this ;
	}
	
	public DColBase setHtmlWidth(final int width) {
		return setHtmlWidth(String.valueOf(width)) ;
	}
	
	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString() +
		Z.fmt(EHtmlAttr.align.getAttributeName(), getHtmlAlign()) +
		Z.fmt(EHtmlAttr._char.getAttributeName(), "" + getHtmlCh()) +
		Z.fmt(EHtmlAttr.charoff.getAttributeName(), "" + getHtmlChOff()) +
		Z.fmt(EHtmlAttr.span.getAttributeName(), "" + getHtmlSpan()) +
		Z.fmt(EHtmlAttr.valign.getAttributeName(), "" + getHtmlValign()) +
		Z.fmt(EHtmlAttr.width.getAttributeName(), getHtmlWidth()) ;	
	}
}
