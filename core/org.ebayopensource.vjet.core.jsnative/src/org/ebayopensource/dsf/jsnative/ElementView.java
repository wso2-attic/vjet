/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsArray;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.MType;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * http://www.w3.org/TR/cssom-view/#elementview
 */
@DOMSupport(DomLevel.ONE)
@MType
@JsMetatype
public interface ElementView extends IWillBeScriptable {

	@BrowserSupport({BrowserType.IE_6P,BrowserType.FIREFOX_2P})
	
	@JsArray(TextRectangle.class)
	@Function TextRectangleList getClientRects();

	@BrowserSupport({BrowserType.IE_6P,BrowserType.FIREFOX_3P})
	
	@Function TextRectangle getBoundingClientRect();

	/**
	 * Returns height of the element (in pixels).
	 * @return
	 */
	@Property int getOffsetHeight();
	
	/**
	 * Number of pixels the left edge of the element is offset from 
	 * the left edge of its parent element.
	 * @return
	 */
	@Property int getOffsetLeft();
	
	/**
	 * Number of pixels the top edge of the element is offset 
	 * from the top edge of its parent element.
	 * @return
	 */
	@Property int getOffsetTop();
	
	/**
	 * Returns width of the element (in pixels)
	 * @return
	 */
	@Property int getOffsetWidth();
	
	/**
	 * Returns scroll height of the element (in pixels).
	 * @return
	 */
	@Property int getScrollHeight();
	
	
	/**
	 * Gets the left scroll offset of an element.
	 * @return
	 */
	@Property int getScrollLeft();
	
	/**
	 * Gets the top scroll offset of an element.
	 * @return
	 */
	@Property int getScrollTop();
	
	/**
	 * Returns scroll view width of an element.
	 * @return
	 */
	@Property int getScrollWidth();
	
	/**
	 * Gets the client top of an element.
	 * @return
	 */
	@Property int getClientTop();

	/**
	 * Gets the client left of an element.
	 * @return
	 */
	@Property int getClientLeft();

	/**
	 * Gets the client width of an element.
	 * @return
	 */
	@Property int getClientWidth();

	/**
	 * Gets the client height of an element.
	 * @return
	 */
	@Property int getClientHeight();

	/**
	 * Sets height of the element (in pixels).
	 * @param hight
	 */
	@Property void setOffsetHeight(int hight);


	/**
	 * Sets number of pixels the left edge of the element is offset from
	 * the left edge of its parent element.
	 * @param left
	 */
	@Property void setOffsetLeft(int left);
	
	/**
	 * Sets Number of pixels the top edge of the element is offset 
	 * from the top edge of its parent element.
	 * @param top
	 */
	@Property void setOffsetTop(int top);

	/**
	 * Sets width of the element (in pixels)
	 * @param width
	 */
	@Property void setOffsetWidth(int width);

	/**
	 * Sets scroll height of the element (in pixels).
	 * @param hight
	 */
	@Property void setScrollHeight(int hight);

	/**
	 * Sets the left scroll offset of an element.
	 * @param left
	 */
	@Property void setScrollLeft(int left);

	/**
	 * Sets the top scroll offset of an element.
	 * @param top
	 */
	@Property void setScrollTop(int top);
	
	/**
	 * Sets scroll view width of an element.
	 * @param width
	 */
	@Property void setScrollWidth(int width);

	/**
	 * To get offset parent
	 * 
	 */
	@Property HtmlElement getOffsetParent();

}