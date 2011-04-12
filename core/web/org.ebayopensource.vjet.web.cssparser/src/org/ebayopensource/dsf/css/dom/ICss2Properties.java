/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom;

import org.w3c.dom.DOMException;

/**
 *  The <code>CSS2Properties</code> interface represents a convenience 
 * mechanism for retrieving and setting properties within a 
 * <code>CSSStyleDeclaration</code>. The attributes of this interface 
 * correspond to all the properties specified in CSS2. Getting an attribute 
 * of this interface is equivalent to calling the 
 * <code>getPropertyValue</code> method of the 
 * <code>CSSStyleDeclaration</code> interface. Setting an attribute of this 
 * interface is equivalent to calling the <code>setProperty</code> method of 
 * the <code>CSSStyleDeclaration</code> interface. 
 * <p> A conformant implementation of the CSS module is not required to 
 * implement the <code>CSS2Properties</code> interface. If an implementation 
 * does implement this interface, the expectation is that language-specific 
 * methods can be used to cast from an instance of the 
 * <code>CSSStyleDeclaration</code> interface to the 
 * <code>CSS2Properties</code> interface. 
 * <p> If an implementation does implement this interface, it is expected to 
 * understand the specific syntax of the shorthand properties, and apply 
 * their semantics; when the <code>margin</code> property is set, for 
 * example, the <code>marginTop</code>, <code>marginRight</code>, 
 * <code>marginBottom</code> and <code>marginLeft</code> properties are 
 * actually being set by the underlying implementation. 
 * <p> When dealing with CSS "shorthand" properties, the shorthand properties 
 * should be decomposed into their component longhand properties as 
 * appropriate, and when querying for their value, the form returned should 
 * be the shortest form exactly equivalent to the declarations made in the 
 * ruleset. However, if there is no shorthand declaration that could be 
 * added to the ruleset without changing in any way the rules already 
 * declared in the ruleset (i.e., by adding longhand rules that were 
 * previously not declared in the ruleset), then the empty string should be 
 * returned for the shorthand property. 
 * <p> For example, querying for the <code>font</code> property should not 
 * return "normal normal normal 14pt/normal Arial, sans-serif", when "14pt 
 * Arial, sans-serif" suffices. (The normals are initial values, and are 
 * implied by use of the longhand property.) 
 * <p> If the values for all the longhand properties that compose a particular 
 * string are the initial values, then a string consisting of all the 
 * initial values should be returned (e.g. a <code>border-width</code> value 
 * of "medium" should be returned as such, not as ""). 
 * <p> For some shorthand properties that take missing values from other 
 * sides, such as the <code>margin</code>, <code>padding</code>, and 
 * <code>border-[width|style|color]</code> properties, the minimum number of 
 * sides possible should be used; i.e., "0px 10px" will be returned instead 
 * of "0px 10px 0px 10px". 
 * <p> If the value of a shorthand property can not be decomposed into its 
 * component longhand properties, as is the case for the <code>font</code> 
 * property with a value of "menu", querying for the values of the component 
 * longhand properties should return the empty string. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface ICss2Properties {
    /**
     *  See the azimuth property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getAzimuth();
	ICss2Properties setAzimuth(String azimuth) throws DOMException;

    /**
     *  See the background property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
   String getBackground();
   ICss2Properties setBackground(String background) throws DOMException;

    /**
     *  See the background-attachment property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBackgroundAttachment();
	ICss2Properties setBackgroundAttachment(String backgroundAttachment)
		throws DOMException;

    /**
     *  See the background-color property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBackgroundColor();
	ICss2Properties setBackgroundColor(String backgroundColor) throws DOMException;

    /**
     *  See the background-image property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBackgroundImage();
	ICss2Properties setBackgroundImage(String backgroundImage)
                                             throws DOMException;

    /**
     *  See the background-position property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBackgroundPosition();
	ICss2Properties setBackgroundPosition(String backgroundPosition)
                                             throws DOMException;

    /**
     *  See the background-repeat property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBackgroundRepeat();
	ICss2Properties setBackgroundRepeat(String backgroundRepeat)
                                             throws DOMException;

    /**
     *  See the border property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorder();
	ICss2Properties setBorder(String border) throws DOMException;

    /**
     *  See the border-collapse property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderCollapse();
	ICss2Properties setBorderCollapse(String borderCollapse) throws DOMException;

    /**
     *  See the border-color property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderColor();
	ICss2Properties setBorderColor(String borderColor) throws DOMException;

    /**
     *  See the border-spacing property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderSpacing();
	ICss2Properties setBorderSpacing(String borderSpacing) throws DOMException;

    /**
     *  See the border-style property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderStyle();
	ICss2Properties setBorderStyle(String borderStyle) throws DOMException;

    /**
     *  See the border-top property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderTop();
	ICss2Properties setBorderTop(String borderTop) throws DOMException;

    /**
     *  See the border-right property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderRight();
	ICss2Properties setBorderRight(String borderRight) throws DOMException;

    /**
     *  See the border-bottom property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderBottom();
	ICss2Properties setBorderBottom(String borderBottom) throws DOMException;

    /**
     *  See the border-left property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderLeft();
	ICss2Properties setBorderLeft(String borderLeft) throws DOMException;

    /**
     *  See the border-top-color property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderTopColor();
	ICss2Properties setBorderTopColor(String borderTopColor) throws DOMException;

    /**
     *  See the border-right-color property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderRightColor();
	ICss2Properties setBorderRightColor(String borderRightColor) throws DOMException;

    /**
     *  See the border-bottom-color property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderBottomColor();
	ICss2Properties setBorderBottomColor(String borderBottomColor) throws DOMException;

    /**
     *  See the border-left-color property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderLeftColor();
	ICss2Properties setBorderLeftColor(String borderLeftColor) throws DOMException;

    /**
     *  See the border-top-style property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderTopStyle();
	ICss2Properties setBorderTopStyle(String borderTopStyle) throws DOMException;

    /**
     *  See the border-right-style property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderRightStyle();
	ICss2Properties setBorderRightStyle(String borderRightStyle) throws DOMException;

    /**
     *  See the border-bottom-style property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderBottomStyle();
	ICss2Properties setBorderBottomStyle(String borderBottomStyle) throws DOMException;

    /**
     *  See the border-left-style property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderLeftStyle();
	ICss2Properties setBorderLeftStyle(String borderLeftStyle) throws DOMException;

    /**
     *  See the border-top-width property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderTopWidth();
	ICss2Properties setBorderTopWidth(String borderTopWidth) throws DOMException;

    /**
     *  See the border-right-width property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderRightWidth();
	ICss2Properties setBorderRightWidth(String borderRightWidth) throws DOMException;

    /**
     *  See the border-bottom-width property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderBottomWidth();
	ICss2Properties setBorderBottomWidth(String borderBottomWidth) throws DOMException;

    /**
     *  See the border-left-width property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderLeftWidth();
	ICss2Properties setBorderLeftWidth(String borderLeftWidth) throws DOMException;

    /**
     *  See the border-width property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBorderWidth();
	ICss2Properties setBorderWidth(String borderWidth) throws DOMException;

    /**
     *  See the bottom property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getBottom();
	ICss2Properties setBottom(String bottom) throws DOMException;

    /**
     *  See the caption-side property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getCaptionSide();
	ICss2Properties setCaptionSide(String captionSide) throws DOMException;

    /**
     *  See the clear property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getClear();
	ICss2Properties setClear(String clear) throws DOMException;

    /**
     *  See the clip property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getClip();
	ICss2Properties setClip(String clip) throws DOMException;

    /**
     *  See the color property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getColor();
	ICss2Properties setColor(String color) throws DOMException;

    /**
     *  See the content property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getContent();
	ICss2Properties setContent(String content) throws DOMException;

    /**
     *  See the counter-increment property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getCounterIncrement();
	ICss2Properties setCounterIncrement(String counterIncrement) throws DOMException;

    /**
     *  See the counter-reset property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getCounterReset();
	ICss2Properties setCounterReset(String counterReset) throws DOMException;

    /**
     *  See the cue property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getCue();
	ICss2Properties setCue(String cue) throws DOMException;

    /**
     *  See the cue-after property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getCueAfter();
	ICss2Properties setCueAfter(String cueAfter) throws DOMException;

    /**
     *  See the cue-before property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getCueBefore();
	ICss2Properties setCueBefore(String cueBefore) throws DOMException;

    /**
     *  See the cursor property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getCursor();
	ICss2Properties setCursor(String cursor) throws DOMException;

    /**
     *  See the direction property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getDirection();
	ICss2Properties setDirection(String direction) throws DOMException;

    /**
     *  See the display property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getDisplay();
	ICss2Properties setDisplay(String display) throws DOMException;

    /**
     *  See the elevation property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getElevation();
	ICss2Properties setElevation(String elevation) throws DOMException;

    /**
     *  See the empty-cells property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getEmptyCells();
	ICss2Properties setEmptyCells(String emptyCells) throws DOMException;

    /**
     *  See the float property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getCssFloat();
	ICss2Properties setCssFloat(String cssFloat) throws DOMException;

    /**
     *  See the font property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getFont();
	ICss2Properties setFont(String font) throws DOMException;

    /**
     *  See the font-family property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getFontFamily();
	ICss2Properties setFontFamily(String fontFamily) throws DOMException;

    /**
     *  See the font-size property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getFontSize();
	ICss2Properties setFontSize(String fontSize) throws DOMException;

    /**
     *  See the font-size-adjust property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getFontSizeAdjust();
	ICss2Properties setFontSizeAdjust(String fontSizeAdjust) throws DOMException;

    /**
     *  See the font-stretch property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getFontStretch();
	ICss2Properties setFontStretch(String fontStretch) throws DOMException;

    /**
     *  See the font-style property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getFontStyle();
	ICss2Properties setFontStyle(String fontStyle) throws DOMException;

    /**
     *  See the font-variant property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getFontVariant();
	ICss2Properties setFontVariant(String fontVariant) throws DOMException;

    /**
     *  See the font-weight property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getFontWeight();
	ICss2Properties setFontWeight(String fontWeight) throws DOMException;

    /**
     *  See the height property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getHeight();
	ICss2Properties setHeight(String height) throws DOMException;

    /**
     *  See the left property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getLeft();
	ICss2Properties setLeft(String left) throws DOMException;

    /**
     *  See the letter-spacing property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getLetterSpacing();
	ICss2Properties setLetterSpacing(String letterSpacing) throws DOMException;

    /**
     *  See the line-height property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getLineHeight();
	ICss2Properties setLineHeight(String lineHeight) throws DOMException;

    /**
     *  See the list-style property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getListStyle();
	ICss2Properties setListStyle(String listStyle) throws DOMException;

    /**
     *  See the list-style-image property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getListStyleImage();
	ICss2Properties setListStyleImage(String listStyleImage) throws DOMException;

    /**
     *  See the list-style-position property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getListStylePosition();
	ICss2Properties setListStylePosition(String listStylePosition) throws DOMException;

    /**
     *  See the list-style-type property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getListStyleType();
	ICss2Properties setListStyleType(String listStyleType) throws DOMException;

    /**
     *  See the margin property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getMargin();
	ICss2Properties setMargin(String margin) throws DOMException;

    /**
     *  See the margin-top property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getMarginTop();
	ICss2Properties setMarginTop(String marginTop) throws DOMException;

    /**
     *  See the margin-right property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getMarginRight();
	ICss2Properties setMarginRight(String marginRight) throws DOMException;

    /**
     *  See the margin-bottom property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getMarginBottom();
	ICss2Properties setMarginBottom(String marginBottom) throws DOMException;

    /**
     *  See the margin-left property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getMarginLeft();
	ICss2Properties setMarginLeft(String marginLeft) throws DOMException;

    /**
     *  See the marker-offset property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getMarkerOffset();
	ICss2Properties setMarkerOffset(String markerOffset) throws DOMException;

    /**
     *  See the marks property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getMarks();
	ICss2Properties setMarks(String marks) throws DOMException;

    /**
     *  See the max-height property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getMaxHeight();
	ICss2Properties setMaxHeight(String maxHeight) throws DOMException;

    /**
     *  See the max-width property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getMaxWidth();
	ICss2Properties setMaxWidth(String maxWidth) throws DOMException;

    /**
     *  See the min-height property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getMinHeight();
	ICss2Properties setMinHeight(String minHeight) throws DOMException;

    /**
     *  See the min-width property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getMinWidth();
	ICss2Properties setMinWidth(String minWidth) throws DOMException;

    /**
     *  See the orphans property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getOrphans();
	ICss2Properties setOrphans(String orphans) throws DOMException;

    /**
     *  See the outline property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getOutline();
	ICss2Properties setOutline(String outline) throws DOMException;

    /**
     *  See the outline-color property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getOutlineColor();
	ICss2Properties setOutlineColor(String outlineColor) throws DOMException;

    /**
     *  See the outline-style property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getOutlineStyle();
	ICss2Properties setOutlineStyle(String outlineStyle) throws DOMException;

    /**
     *  See the outline-width property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getOutlineWidth();
	ICss2Properties setOutlineWidth(String outlineWidth) throws DOMException;

    /**
     *  See the overflow property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getOverflow();
	ICss2Properties setOverflow(String overflow) throws DOMException;

    /**
     *  See the padding property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPadding();
	ICss2Properties setPadding(String padding) throws DOMException;

    /**
     *  See the padding-top property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPaddingTop();
	ICss2Properties setPaddingTop(String paddingTop) throws DOMException;

    /**
     *  See the padding-right property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPaddingRight();
	ICss2Properties setPaddingRight(String paddingRight) throws DOMException;

    /**
     *  See the padding-bottom property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPaddingBottom();
	ICss2Properties setPaddingBottom(String paddingBottom) throws DOMException;

    /**
     *  See the padding-left property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPaddingLeft();
	ICss2Properties setPaddingLeft(String paddingLeft) throws DOMException;

    /**
     *  See the page property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPage();
	ICss2Properties setPage(String page) throws DOMException;

    /**
     *  See the page-break-after property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPageBreakAfter();
	ICss2Properties setPageBreakAfter(String pageBreakAfter) throws DOMException;

    /**
     *  See the page-break-before property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPageBreakBefore();
	ICss2Properties setPageBreakBefore(String pageBreakBefore) throws DOMException;

    /**
     *  See the page-break-inside property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPageBreakInside();
	ICss2Properties setPageBreakInside(String pageBreakInside) throws DOMException;

    /**
     *  See the pause property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPause();
	ICss2Properties setPause(String pause) throws DOMException;

    /**
     *  See the pause-after property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPauseAfter();
	ICss2Properties setPauseAfter(String pauseAfter) throws DOMException;

    /**
     *  See the pause-before property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPauseBefore();
	ICss2Properties setPauseBefore(String pauseBefore) throws DOMException;

    /**
     *  See the pitch property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPitch();
	ICss2Properties setPitch(String pitch) throws DOMException;

    /**
     *  See the pitch-range property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPitchRange();
	ICss2Properties setPitchRange(String pitchRange) throws DOMException;

    /**
     *  See the play-during property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPlayDuring();
	ICss2Properties setPlayDuring(String playDuring) throws DOMException;

    /**
     *  See the position property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getPosition();
	ICss2Properties setPosition(String position) throws DOMException;

    /**
     *  See the quotes property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getQuotes();
	ICss2Properties setQuotes(String quotes) throws DOMException;

    /**
     *  See the richness property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getRichness();
	ICss2Properties setRichness(String richness) throws DOMException;

    /**
     *  See the right property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getRight();
	ICss2Properties setRight(String right) throws DOMException;

    /**
     *  See the size property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getSize();
	ICss2Properties setSize(String size) throws DOMException;

    /**
     *  See the speak property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getSpeak();
	ICss2Properties setSpeak(String speak) throws DOMException;

    /**
     *  See the speak-header property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getSpeakHeader();
	ICss2Properties setSpeakHeader(String speakHeader) throws DOMException;

    /**
     *  See the speak-numeral property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getSpeakNumeral();
	ICss2Properties setSpeakNumeral(String speakNumeral) throws DOMException;

    /**
     *  See the speak-punctuation property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getSpeakPunctuation();
	ICss2Properties setSpeakPunctuation(String speakPunctuation) throws DOMException;

    /**
     *  See the speech-rate property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getSpeechRate();
	ICss2Properties setSpeechRate(String speechRate) throws DOMException;

    /**
     *  See the stress property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getStress();
	ICss2Properties setStress(String stress) throws DOMException;

    /**
     *  See the table-layout property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getTableLayout();
	ICss2Properties setTableLayout(String tableLayout) throws DOMException;

    /**
     *  See the text-align property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getTextAlign();
	ICss2Properties setTextAlign(String textAlign) throws DOMException;

    /**
     *  See the text-decoration property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getTextDecoration();
	ICss2Properties setTextDecoration(String textDecoration) throws DOMException;

    /**
     *  See the text-indent property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getTextIndent();
	ICss2Properties setTextIndent(String textIndent) throws DOMException;

    /**
     *  See the text-shadow property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getTextShadow();
	ICss2Properties setTextShadow(String textShadow) throws DOMException;

    /**
     *  See the text-transform property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getTextTransform();
	ICss2Properties setTextTransform(String textTransform) throws DOMException;

    /**
     *  See the top property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getTop();
	ICss2Properties setTop(String top) throws DOMException;

    /**
     *  See the unicode-bidi property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getUnicodeBidi();
	ICss2Properties setUnicodeBidi(String unicodeBidi) throws DOMException;

    /**
     *  See the vertical-align property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getVerticalAlign();
	ICss2Properties setVerticalAlign(String verticalAlign) throws DOMException;

    /**
     *  See the visibility property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getVisibility();
	ICss2Properties setVisibility(String visibility) throws DOMException;

    /**
     *  See the voice-family property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getVoiceFamily();
	ICss2Properties setVoiceFamily(String voiceFamily) throws DOMException;

    /**
     *  See the volume property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getVolume();
	ICss2Properties setVolume(String volume) throws DOMException;

    /**
     *  See the white-space property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getWhiteSpace();
	ICss2Properties setWhiteSpace(String whiteSpace) throws DOMException;

    /**
     *  See the widows property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getWidows();
	ICss2Properties setWidows(String widows) throws DOMException;

    /**
     *  See the width property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getWidth();
	ICss2Properties setWidth(String width) throws DOMException;

    /**
     *  See the word-spacing property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getWordSpacing();
	ICss2Properties setWordSpacing(String wordSpacing) throws DOMException;

    /**
     *  See the z-index property definition in CSS2. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is 
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    String getZIndex();
	ICss2Properties setZIndex(String zIndex) throws DOMException;
}
