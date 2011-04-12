/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.ebayopensource.dsf.javatojs.anno.AMappedToJS;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * http://www.w3.org/TR/REC-html40/interact/scripts.html#edef-SCRIPT
 *
 */
@Alias("HTMLScriptElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlScript extends HtmlElement {
	
	/** "text/css" */
	public static final String TYPE_TEXT_CSS = "text/css" ;
	/** "text/javascript" */
	@AJavaOnly @AMappedToJS (name="'text/javascript'")
	public static final String TYPE_TEXT_JAVASCRIPT = "text/javascript" ;
	/** "text/vbscript" */
	@AJavaOnly @AMappedToJS (name="'text/vbscript'")
	public static final String TYPE_TEXT_VBSCRIPT = "text/vbscript" ;
	
	//Document location for this script enum
	/** value = 0 */
	@AJavaOnly @AMappedToJS (name="0")
	public static final int DOCUMENT_LOCATION_HEAD = 0;
	/** value = 1 */
	@AJavaOnly @AMappedToJS (name="1")
	public static final int DOCUMENT_LOCATION_BODY = 1;
	
	@Property String getText();
	@Property void setText(String text);

	@Property String getCharset();
	@Property void setCharset(String charset);

	@Property boolean getDefer();
	@Property void setDefer(boolean defer);

	@Property String getSrc();
	@Property void setSrc(String src);

	@Property String getType();
	@Property void setType(String type);
	
	/**
	 * Returns the onload event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onload")
	Object getOnLoad();
	
	/**
	 * Sets the onload event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onload")
	void setOnLoad(Object functionRef);
	
	/**
	 * Returns the onunload event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onunload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onunload")
	Object getOnUnload();
	
	/**
	 * Sets the onunload event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onunload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onunload")
	void setOnUnload(Object functionRef);
	
	/**
	 * Returns the onmousemove event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onmousemove.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmousemove")
	Object getOnMouseMove();
	
	/**
	 * Sets the onmousemove event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmousemove.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmousemove")
	void setOnMouseMove(Object functionRef);
	
	/**
	 * Returns the onmouseout event handler code on the current element.
	 * @see http://www.w3schools.com/jsref/jsref_onmouseout.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseout")
	Object getOnMouseOut();
	
	/**
	 * Sets the onmouseout event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmouseout.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseout")
	void setOnMouseOut(Object functionRef);
	
	/**
	 * Returns the onmouseover event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onmouseover.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseover")
	Object getOnMouseOver();
	
	/**
	 * Sets the onmouseover event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmouseover.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseover")
	void setOnMouseOver(Object functionRef);

}
