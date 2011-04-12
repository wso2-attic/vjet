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
import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * http://www.w3.org/TR/REC-html40/present/frames.html#edef-IFRAME
 *
 */
@Alias("HTMLIFrameElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlIFrame extends HtmlElement {
	

	// For align.
	/** "left" */
	@AJavaOnly @ARename(name="'left'")
	String ALIGN_LEFT = "left" ;
	/** "center" */
	@AJavaOnly @ARename(name="'center'")
	String ALIGN_CENTER = "center" ; // ignoreHtmlKeyword
	/** "right" */
	@AJavaOnly @ARename(name="'right'")
	String ALIGN_RIGHT = "right" ;
	/** "justify" */
	@AJavaOnly @ARename(name="'justify'")
	String ALIGN_JUSTIFY = "justify" ;
	
	// For frameborder.
	/** "0" */
	@AJavaOnly @ARename(name="'0'")
	String FRAMEBORDER_0 = "0" ;
	/** "1" */
	@AJavaOnly @ARename(name="'1'")
	String FRAMEBORDER_1 = "1" ;
	
	// For Scrolling
	/** "auto" */
	@AJavaOnly @ARename(name="'auto'")
	String SCROLLING_AUTO = "auto" ;
	/** "yes" */
	@AJavaOnly @ARename(name="'yes'")
	String SCROLLING_YES = "yes" ;
	/** "no" */
	@AJavaOnly @ARename(name="'no'")
	String SCROLLING_NO = "no" ;
	
	@Property  String getAlign();
	@Property  void setAlign(String align);

	//TODO make this multiple type  {int,String}
	@Property  Object getFrameBorder();
	@Property  void setFrameBorder(Object frameBorder);

	@Property  String getHeight();
	@Property  void setHeight(String height);

	@Property  String getLongDesc();
	@Property  void setLongDesc(String longDesc);

	@Property  String getMarginHeight();
	@Property  void setMarginHeight(String marginHeight);

	@Property  String getMarginWidth();
	@Property  void setMarginWidth(String marginWidth);

    @Property String getName();
    @Property void setName(String name);

    @Property String getScrolling();
    @Property void setScrolling(String scrolling);

    @Property String getSrc();
    @Property void setSrc(String src);

    @Property String getWidth();
    @Property void setWidth(String width);

    @DOMSupport(DomLevel.TWO) @BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
    @Property HtmlDocument getContentDocument();
    
    @BrowserSupport({BrowserType.IE_6P})
    @Property HtmlDocument getDocument();
    
	/**
	 * Returns the onblur event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onblur.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onblur")
	Object getOnBlur();
	
	/**
	 * Sets the onblur event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onblur.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onblur")
	void setOnBlur(Object functionRef);
	
	/**
	 * Returns the onfocus event handler code on the current element.
	 * @see http://www.w3schools.com/jsref/jsref_onfocus.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onfocus")
	Object getOnFocus();
	
	/**
	 * Sets the onfocus event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onfocus.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onfocus")
	void setOnFocus(Object functionRef);
	
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
}
