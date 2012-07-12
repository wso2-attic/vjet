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

public abstract class BaseHeading 
	extends BaseAttrsHtmlElement
	/*implements IDHeading*/
{

	private static final long serialVersionUID = 3906362731924698937L;

	/** "left" */
	public static final String ALIGN_LEFT = "left" ; // ignoreHtmlKeyword
	/** "center" */
	public static final String ALIGN_CENTER = "center" ; // ignoreHtmlKeyword
	/** "right" */
	public static final String ALIGN_RIGHT = "right" ; // ignoreHtmlKeyword
	/** "justify" */
	public static final String ALIGN_JUSTIFY = "justify" ; // ignoreHtmlKeyword
	
	//
	// Constructor(s)
	//
	BaseHeading(final HtmlTypeEnum type) {
		super(type);
	}
	
	BaseHeading(final DHtmlDocument doc,final HtmlTypeEnum type){
		super(doc, type);
	}
	
	//
	// HTML Attributes
	//
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlAlign() {
		return getHtmlAttribute(EHtmlAttr.align);
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public BaseHeading setHtmlAlign(final String align) {
		setHtmlAttribute(EHtmlAttr.align, align);
		return this ;
	}
	
	@Override
	public BaseHeading jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString() +
		Z.fmt("align", getHtmlAlign()) ;		
	}	
}
