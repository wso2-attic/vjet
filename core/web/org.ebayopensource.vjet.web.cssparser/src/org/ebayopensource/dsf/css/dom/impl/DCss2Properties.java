/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom.impl;

import java.io.Serializable;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.css.dom.ICss2Properties;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;

/**
 * @see org.w3c.dom.css.CSS2Properties
 */
public class DCss2Properties 
	implements ICss2Properties, Serializable, Cloneable
{
	private static final long serialVersionUID = -4063166386965937606L;
	private ICssStyleDeclaration m_styleDecl;
	
    public DCss2Properties(ICssStyleDeclaration styleDecl) {
    	m_styleDecl = styleDecl;
    }

//    public String getAzimuth() {
//        return get("azimuth");
//    }
//
//    public DCss2Properties setAzimuth(String azimuth) throws DOMException {
//    	return put("azimuth", azimuth) ;
//    }
//
//    public DCss2Properties setAzimuth(String azimuth, boolean important) throws DOMException {
//    	return put("azimuth", azimuth, important) ;
//    }
    
    public String getBackground() {
		return get("background");
    }

    public DCss2Properties setBackground(String background) throws DOMException {
    	return put("background", background) ;
    }

    public DCss2Properties setBackground(String background, boolean important) throws DOMException {
    	return put("background", background, important) ;
    }
    
    public String getBackgroundAttachment() {
		return get("background-attachment");
    }

    public DCss2Properties setBackgroundAttachment(String backgroundAttachment) throws DOMException {
    	return put("background-attachment", backgroundAttachment) ;
    }

    public DCss2Properties setBackgroundAttachment(String backgroundAttachment, boolean important) throws DOMException {
    	return put("background-attachment", backgroundAttachment, important) ;
    }
    
    public String getBackgroundColor() {
		return get("background-color");
    }

    public DCss2Properties setBackgroundColor(String backgroundColor) throws DOMException {
    	return put("background-color", backgroundColor) ;
    }

    public DCss2Properties setBackgroundColor(String backgroundColor, boolean important) throws DOMException {
    	return put("background-color", backgroundColor, important) ;
    }
    
//    public String getBackgroundImage() {
//		return get("background-image");
//    }
//
//    public DCss2Properties setBackgroundImage(String backgroundImage) throws DOMException {
//    	return put("background-image", backgroundImage) ;
//    }
//
//    public DCss2Properties setBackgroundImage(String backgroundImage, boolean important) throws DOMException {
//    	return put("background-image", backgroundImage, important) ;
//    }
    
    public String getBackgroundPosition() {
		return get("background-position");
    }

    public DCss2Properties setBackgroundPosition(String backgroundPosition) throws DOMException {
    	return put("background-position", backgroundPosition) ;
    }

    public DCss2Properties setBackgroundPosition(String backgroundPosition, boolean important) throws DOMException {
    	return put("background-position", backgroundPosition, important) ;
    }
    
//    public String getBackgroundRepeat() {
//		return get("background-repeat");
//    }
//
//    public DCss2Properties setBackgroundRepeat(String backgroundRepeat) throws DOMException {
//    	return put("background-repeat", backgroundRepeat) ;
//    }
//
//    public DCss2Properties setBackgroundRepeat(String backgroundRepeat, boolean important) throws DOMException {
//    	return put("background-repeat", backgroundRepeat, important) ;
//    }
    
    public String getBorder() {
		return get("border");
    }

    public DCss2Properties setBorder(String border) throws DOMException {
		return put("border", border) ;   	
    }

    public DCss2Properties setBorder(String border, boolean important) throws DOMException {
		return put("border", border, important) ;   	
    }
    
//    public String getBorderCollapse() {
//		return get("border-collapse");
//    }
//
//    public DCss2Properties setBorderCollapse(String borderCollapse) throws DOMException {
//		return put("border-collapse", borderCollapse) ;
//    }
//
//    public DCss2Properties setBorderCollapse(String borderCollapse, boolean important) throws DOMException {
//		return put("border-collapse", borderCollapse, important) ;
//    }
    
    public String getBorderColor() {
		return get("border-color");
    }

    public DCss2Properties setBorderColor(String borderColor) throws DOMException {
		return put("border-color", borderColor) ;
    }

    public DCss2Properties setBorderColor(String borderColor, boolean important) throws DOMException {
		return put("border-color", borderColor, important) ;
    }
    
//    public String getBorderSpacing() {
//		return get("border-spacing");
//    }
//
//    public DCss2Properties setBorderSpacing(String borderSpacing) throws DOMException {
//		return put("border-spacing", borderSpacing) ;
//    }
//
//    public DCss2Properties setBorderSpacing(String borderSpacing, boolean important) throws DOMException {
//		return put("border-spacing", borderSpacing, important) ;
//    }

    public String getBorderStyle() {
		return get("border-style");
    }

    public DCss2Properties setBorderStyle(String borderStyle) throws DOMException {
		return put("border-style", borderStyle) ;
    }

    public DCss2Properties setBorderStyle(String borderStyle, boolean important) throws DOMException {
		return put("border-style", borderStyle, important) ;
    }

//    public String getBorderTop() {
//		return get("border-top");
//    }
//
//    public DCss2Properties setBorderTop(String borderTop) throws DOMException {
//		return put("border-top", borderTop) ;
//    }
//
//    public DCss2Properties setBorderTop(String borderTop, boolean important) throws DOMException {
//		return put("border-top", borderTop, important) ;
//    }

//    public String getBorderRight() {
//		return get("border-right");
//    }
//
//    public DCss2Properties setBorderRight(String borderRight) throws DOMException {
//		return put("border-right", borderRight) ;
//    }
//
//    public DCss2Properties setBorderRight(String borderRight, boolean important) throws DOMException {
//		return put("border-right", borderRight, important) ;
//    }

//    public String getBorderBottom() {
//		return get("border-bottom");
//    }
//
//    public DCss2Properties setBorderBottom(String borderBottom) throws DOMException {
//		return put("border-bottom", borderBottom) ;
//    }
//
//    public DCss2Properties setBorderBottom(String borderBottom, boolean important) throws DOMException {
//		return put("border-bottom", borderBottom, important) ;
//    }

//    public String getBorderLeft() {
//		return get("border-left");
//    }
//
//    public DCss2Properties setBorderLeft(String borderLeft) throws DOMException {
//		return put("border-left", borderLeft) ;
//    }
//
//    public DCss2Properties setBorderLeft(String borderLeft, boolean important) throws DOMException {
//		return put("border-left", borderLeft, important) ;
//    }

//    public String getBorderTopColor() {
//		return get("border-top-color");
//    }
//
//    public DCss2Properties setBorderTopColor(String borderTopColor) throws DOMException {
//		return put("border-top-color", borderTopColor) ;
//    }
//
//    public DCss2Properties setBorderTopColor(String borderTopColor, boolean important) throws DOMException {
//		return put("border-top-color", borderTopColor, important) ;
//    }

//    public String getBorderRightColor() {
//		return get("border-right-color");
//    }
//
//    public DCss2Properties setBorderRightColor(String borderRightColor) throws DOMException {
//		return put("border-right-color", borderRightColor) ;
//    }
//
//    public DCss2Properties setBorderRightColor(String borderRightColor, boolean important) throws DOMException {
//		return put("border-right-color", borderRightColor, important) ;
//    }

//    public String getBorderBottomColor() {
//		return get("border-bottom-color");
//    }
//
//    public DCss2Properties setBorderBottomColor(String borderBottomColor) throws DOMException {
//		return put("border-bottom-color", borderBottomColor) ;
//    }
//
//    public DCss2Properties setBorderBottomColor(String borderBottomColor, boolean important) throws DOMException {
//		return put("border-bottom-color", borderBottomColor, important) ;
//    }
    
//    public String getBorderLeftColor() {
//		return get("border-left-color");
//    }
//
//    public DCss2Properties setBorderLeftColor(String borderLeftColor) throws DOMException {
//		return put("border-left-color", borderLeftColor) ;
//    }
//
//    public DCss2Properties setBorderLeftColor(String borderLeftColor, boolean important) throws DOMException {
//		return put("border-left-color", borderLeftColor, important) ;
//    }

//    public String getBorderTopStyle() {
//		return get("border-top-style");
//    }
//
//    public DCss2Properties setBorderTopStyle(String borderTopStyle) throws DOMException {
//		return put("border-top-style", borderTopStyle) ;
//    }
//
//    public DCss2Properties setBorderTopStyle(String borderTopStyle, boolean important) throws DOMException {
//		return put("border-top-style", borderTopStyle, important) ;
//    }

//    public String getBorderRightStyle() {
//		return get("border-right-style");
//    }
//
//    public DCss2Properties setBorderRightStyle(String borderRightStyle) throws DOMException {
//		return put("border-right-style", borderRightStyle) ;
//    }
//
//    public DCss2Properties setBorderRightStyle(String borderRightStyle, boolean important) throws DOMException {
//		return put("border-right-style", borderRightStyle, important) ;
//    }

//    public String getBorderBottomStyle() {
//		return get("border-bottom-style");
//    }
//
//    public DCss2Properties setBorderBottomStyle(String borderBottomStyle) throws DOMException {
//		return put("border-bottom-style", borderBottomStyle) ;
//    }
//
//    public DCss2Properties setBorderBottomStyle(String borderBottomStyle, boolean important) throws DOMException {
//		return put("border-bottom-style", borderBottomStyle, important) ;
//    }

//    public String getBorderLeftStyle() {
//		return get("border-left-style");
//    }
//
//    public DCss2Properties setBorderLeftStyle(String borderLeftStyle) throws DOMException {
//		return put("border-left-style", borderLeftStyle) ;
//    }
//
//    public DCss2Properties setBorderLeftStyle(String borderLeftStyle, boolean important) throws DOMException {
//		return put("border-left-style", borderLeftStyle, important) ;
//    }

//    public String getBorderTopWidth() {
//		return get("border-top-width");
//    }
//
//    public DCss2Properties setBorderTopWidth(String borderTopWidth) throws DOMException {
//		return put("border-top-width", borderTopWidth) ;
//    }
//
//    public DCss2Properties setBorderTopWidth(String borderTopWidth, boolean important) throws DOMException {
//		return put("border-top-width", borderTopWidth, important) ;
//    }

//    public String getBorderRightWidth() {
//		return get("border-right-width");
//    }
//
//    public DCss2Properties setBorderRightWidth(String borderRightWidth) throws DOMException {
//		return put("border-right-width", borderRightWidth) ;
//    }
//
//    public DCss2Properties setBorderRightWidth(String borderRightWidth, boolean important) throws DOMException {
//		return put("border-right-width", borderRightWidth, important) ;
//    }

//    public String getBorderBottomWidth() {
//		return get("border-bottom-width");
//    }
//
//    public DCss2Properties setBorderBottomWidth(String borderBottomWidth) throws DOMException {
//		return put("border-bottom-width", borderBottomWidth) ;
//    }
//
//    public DCss2Properties setBorderBottomWidth(String borderBottomWidth, boolean important) throws DOMException {
//		return put("border-bottom-width", borderBottomWidth, important) ;
//    }

//    public String getBorderLeftWidth() {
//		return get("border-left-width");
//    }
//
//    public DCss2Properties setBorderLeftWidth(String borderLeftWidth) throws DOMException {
//		return put("border-left-width", borderLeftWidth) ;
//    }
//
//    public DCss2Properties setBorderLeftWidth(String borderLeftWidth, boolean important) throws DOMException {
//		return put("border-left-width", borderLeftWidth, important) ;
//    }

    public String getBorderWidth() {
		return get("border-width");
    }

    public DCss2Properties setBorderWidth(String borderWidth) throws DOMException {
		return put("border-width", borderWidth) ;
    }

    public DCss2Properties setBorderWidth(String borderWidth, boolean important) throws DOMException {
		return put("border-width", borderWidth, important) ;
    }

    public String getBottom() {
		return get("bottom");
    }

    public DCss2Properties setBottom(String bottom) throws DOMException {
		return put("bottom", bottom) ;
    }

    public DCss2Properties setBottom(String bottom, boolean important) throws DOMException {
		return put("bottom", bottom, important) ;
    }

//    public String getCaptionSide() {
//		return get("caption-side");
//    }
//
//    public DCss2Properties setCaptionSide(String captionSide) throws DOMException {
//		return put("caption-side", captionSide) ;
//    }
//
//    public DCss2Properties setCaptionSide(String captionSide, boolean important) throws DOMException {
//		return put("caption-side", captionSide, important) ;
//    }

    public String getClear() {
		return get("clear");
    }

    public DCss2Properties setClear(String clear) throws DOMException {
		return put("clear", clear) ;
    }

    public DCss2Properties setClear(String clear, boolean important) throws DOMException {
		return put("clear", clear, important) ;
    }

//    public String getClip() {
//		return get("clip");
//    }
//
//    public DCss2Properties setClip(String clip) throws DOMException {
//		return put("clip", clip) ;
//    }
//
//    public DCss2Properties setClip(String clip, boolean important) throws DOMException {
//		return put("clip", clip, important) ;
//    }

    public String getColor() {
		return get("color");
    }

    public DCss2Properties setColor(String color) throws DOMException {
		return put("color", color) ;
    }

    public DCss2Properties setColor(String color, boolean important) throws DOMException {
		return put("color", color, important) ;
    }

//    public String getContent() {
//		return get("content");
//    }
//
//    public DCss2Properties setContent(String content) throws DOMException {
//		return put("content", content) ;
//    }
//
//    public DCss2Properties setContent(String content, boolean important) throws DOMException {
//		return put("content", content, important) ;
//    }

//    public String getCounterIncrement() {
//		return get("counter-increment");
//    }
//
//    public DCss2Properties setCounterIncrement(String counterIncrement) throws DOMException {
//		return put("counter-increment", counterIncrement) ;
//    }
//
//    public DCss2Properties setCounterIncrement(String counterIncrement, boolean important) throws DOMException {
//		return put("counter-increment", counterIncrement, important) ;
//    }

//    public String getCounterReset() {
//		return get("counter-reset");
//    }
//
//    public DCss2Properties setCounterReset(String counterReset) throws DOMException {
//		return put("counter-reset", counterReset) ;
//    }
//
//    public DCss2Properties setCounterReset(String counterReset, boolean important) throws DOMException {
//		return put("counter-reset", counterReset, important) ;
//    }

//    public String getCue() {
//		return get("cue");
//    }
//
//    public DCss2Properties setCue(String cue) throws DOMException {
//		return put("cue", cue) ;
//    }
//
//    public DCss2Properties setCue(String cue, boolean important) throws DOMException {
//		return put("cue", cue, important) ;
//    }

//    public String getCueAfter() {
//		return get("cue-after");
//    }
//
//    public DCss2Properties setCueAfter(String cueAfter) throws DOMException {
//		return put("cue-after", cueAfter) ;
//    }
//
//    public DCss2Properties setCueAfter(String cueAfter, boolean important) throws DOMException {
//		return put("cue-after", cueAfter, important) ;
//    }

//    public String getCueBefore() {
//		return get("cue-before");
//    }
//
//    public DCss2Properties setCueBefore(String cueBefore) throws DOMException {
//		return put("cue-before", cueBefore) ;
//    }
//
//    public DCss2Properties setCueBefore(String cueBefore, boolean important) throws DOMException {
//		return put("cue-before", cueBefore, important) ;
//    }

    public String getCursor() {
		return get("cursor");
    }

    public DCss2Properties setCursor(String cursor) throws DOMException {
		return put("cursor", cursor) ;
    }

    public DCss2Properties setCursor(String cursor, boolean important) throws DOMException {
		return put("cursor", cursor, important) ;
    }

    
    public String getDirection() {
		return get("direction");
    }

    public DCss2Properties setDirection(String direction) throws DOMException {
		return put("direction", direction) ;
    }

    public DCss2Properties setDirection(String direction, boolean important) throws DOMException {
		return put("direction", direction, important) ;
    }

    public String getDisplay() {
		return get("display");
    }

    public DCss2Properties setDisplay(String display) throws DOMException {
		return put("display", display) ;
    }

    public DCss2Properties setDisplay(String display, boolean important) throws DOMException {
		return put("display", display, important) ;
    }

//    public String getElevation() {
//		return get("elevation");
//    }
//
//    public DCss2Properties setElevation(String elevation) throws DOMException {
//		return put("elevation", elevation) ;
//    }
//
//    public DCss2Properties setElevation(String elevation, boolean important) throws DOMException {
//		return put("elevation", elevation, important) ;
//    }

    public String getEmptyCells() {
		return get("empty-cells");
    }

    public DCss2Properties setEmptyCells(String emptyCells) throws DOMException {
		return put("empty-cells", emptyCells) ;
    }

    public DCss2Properties setEmptyCells(String emptyCells, boolean important) throws DOMException {
		return put("empty-cells", emptyCells, important) ;
    }

    public String getCssFloat() {
		return get("float");
    }

    public DCss2Properties setCssFloat(String cssFloat) throws DOMException {
		return put("float", cssFloat) ;
    }

    public DCss2Properties setCssFloat(String cssFloat, boolean important) throws DOMException {
		return put("float", cssFloat, important) ;
    }

    public String getFont() {
		return get("font");
    }

    public DCss2Properties setFont(String font) throws DOMException {
		return put("font", font) ;
    }

    public DCss2Properties setFont(String font, boolean important) throws DOMException {
		return put("font", font, important) ;
    }

    public String getFontFamily() {
		return get("font-family");
    }

    public DCss2Properties setFontFamily(String fontFamily) throws DOMException {
		return put("font-family", fontFamily) ;
    }

    public DCss2Properties setFontFamily(String fontFamily, boolean important) throws DOMException {
		return put("font-family", fontFamily, important) ;
    }

    public String getFontSize() {
		return get("font-size");
    }

    public DCss2Properties setFontSize(String fontSize) throws DOMException {
		return put("font-size", fontSize) ;
    }

    public DCss2Properties setFontSize(String fontSize, boolean important) throws DOMException {
		return put("font-size", fontSize, important) ;
    }

//    public String getFontSizeAdjust() {
//		return get("font-size-adjust");
//    }
//
//    public DCss2Properties setFontSizeAdjust(String fontSizeAdjust) throws DOMException {
//		return put("font-size-adjust", fontSizeAdjust) ;
//    }
//
//    public DCss2Properties setFontSizeAdjust(String fontSizeAdjust, boolean important) throws DOMException {
//		return put("font-size-adjust", fontSizeAdjust, important) ;
//    }

//    public String getFontStretch() {
//		return get("font-stretch");
//    }
//
//    public DCss2Properties setFontStretch(String fontStretch) throws DOMException {
//		return put("font-stretch", fontStretch) ;
//    }
//
//    public DCss2Properties setFontStretch(String fontStretch, boolean important) throws DOMException {
//		return put("font-stretch", fontStretch, important) ;
//    }

    public String getFontStyle() {
		return get("font-style");
    }

    public DCss2Properties setFontStyle(String fontStyle) throws DOMException {
		return put("font-style", fontStyle) ;
    }

    public DCss2Properties setFontStyle(String fontStyle, boolean important) throws DOMException {
		return put("font-style", fontStyle, important) ;
    }

    public String getFontVariant() {
		return get("font-variant");
    }

    public DCss2Properties setFontVariant(String fontVariant) throws DOMException {
		return put("font-variant", fontVariant) ;
    }

    public DCss2Properties setFontVariant(String fontVariant, boolean important) throws DOMException {
		return put("font-variant", fontVariant, important) ;
    }

    public String getFontWeight() {
		return get("font-weight");
    }

    public DCss2Properties setFontWeight(String fontWeight) throws DOMException {
		return put("font-weight", fontWeight) ;
    }

    public DCss2Properties setFontWeight(String fontWeight, boolean important) throws DOMException {
		return put("font-weight", fontWeight, important) ;
    }

//    public String getHeight() {
//		return get("height");
//    }
//
//    public DCss2Properties setHeight(String height) throws DOMException {
//		return put("height", height) ;
//    }
//
//    public DCss2Properties setHeight(String height, boolean important) throws DOMException {
//		return put("height", height, important) ;
//    }
    
    public String getLeft() {
		return get("left");
    }

    public DCss2Properties setLeft(String left) throws DOMException {
		return put("left", left) ;
    }

    public DCss2Properties setLeft(String left, boolean important) throws DOMException {
		return put("left", left, important) ;
    }

//    public String getLetterSpacing() {
//		return get("letter-spacing");
//    }
//
//    public DCss2Properties setLetterSpacing(String letterSpacing) throws DOMException {
//		return put("letter-spacing", letterSpacing) ;
//    }
//
//    public DCss2Properties setLetterSpacing(String letterSpacing, boolean important) throws DOMException {
//		return put("letter-spacing", letterSpacing, important) ;
//    }

//    public String getLineHeight() {
//		return get("line-height");
//    }
//
//    public DCss2Properties setLineHeight(String lineHeight) throws DOMException {
//		return put("line-height", lineHeight) ;
//    }
//
//    public DCss2Properties setLineHeight(String lineHeight, boolean important) throws DOMException {
//		return put("line-height", lineHeight, important) ;
//    }

//    public String getListStyle() {
//		return get("list-style");
//    }
//
//    public DCss2Properties setListStyle(String listStyle) throws DOMException {
//		return put("list-style", listStyle) ;
//    }
//
//    public DCss2Properties setListStyle(String listStyle, boolean important) throws DOMException {
//		return put("list-style", listStyle, important) ;
//    }

//    public String getListStyleImage() {
//		return get("list-style-image");
//    }
//
//    public DCss2Properties setListStyleImage(String listStyleImage) throws DOMException {
//		return put("list-style-image", listStyleImage) ;
//    }
//
//    public DCss2Properties setListStyleImage(String listStyleImage, boolean important) throws DOMException {
//		return put("list-style-image", listStyleImage, important) ;
//    }

//    public String getListStylePosition() {
//		return get("list-style-position");
//    }
//
//    public DCss2Properties setListStylePosition(String listStylePosition) throws DOMException {
//		return put("list-style-position", listStylePosition) ;
//    }
//
//    public DCss2Properties setListStylePosition(String listStylePosition, boolean important) throws DOMException {
//		return put("list-style-position", listStylePosition, important) ;
//    }

//    public String getListStyleType() {
//		return get("list-style-type");
//    }
//
//    public DCss2Properties setListStyleType(String listStyleType) throws DOMException {
//		return put("list-style-type", listStyleType) ;
//    }
//
//    public DCss2Properties setListStyleType(String listStyleType, boolean important) throws DOMException {
//		return put("list-style-type", listStyleType, important) ;
//    }

//    public String getMargin() {
//		return get("margin");
//    }
//
//    public DCss2Properties setMargin(String margin) throws DOMException {
//		return put("margin", margin) ;
//    }
//
//    public DCss2Properties setMargin(String margin, boolean important) throws DOMException {
//		return put("margin", margin, important) ;
//    }

//    public String getMarginTop() {
//		return get("margin-top");
//    }
//
//    public DCss2Properties setMarginTop(String marginTop) throws DOMException {
//		return put("margin-top", marginTop) ;
//    }
//
//    public DCss2Properties setMarginTop(String marginTop, boolean important) throws DOMException {
//		return put("margin-top", marginTop, important) ;
//    }
//
//    public String getMarginRight() {
//		return get("margin-right");
//    }
//
//    public DCss2Properties setMarginRight(String marginRight) throws DOMException {
//		return put("margin-right", marginRight) ;
//    }
//
//    public DCss2Properties setMarginRight(String marginRight, boolean important) throws DOMException {
//		return put("margin-right", marginRight, important) ;
//    }
//
//    public String getMarginBottom() {
//		return get("margin-bottom");
//    }
//
//    public DCss2Properties setMarginBottom(String marginBottom) throws DOMException {
//		return put("margin-bottom", marginBottom) ;
//    }
//
//    public DCss2Properties setMarginBottom(String marginBottom, boolean important) throws DOMException {
//		return put("margin-bottom", marginBottom, important) ;
//    }
//
//    public String getMarginLeft() {
//		return get("margin-left");
//    }
//
//    public DCss2Properties setMarginLeft(String marginLeft) throws DOMException {
//		return put("margin-left", marginLeft) ;
//    }
//
//    public DCss2Properties setMarginLeft(String marginLeft, boolean important) throws DOMException {
//		return put("margin-left", marginLeft, important) ;
//    }
//
//    public String getMarkerOffset() {
//		return get("marker-offset");
//    }
//
//    public DCss2Properties setMarkerOffset(String markerOffset) throws DOMException {
//		return put("marker-offset", markerOffset) ;
//    }
//
//    public DCss2Properties setMarkerOffset(String markerOffset, boolean important) throws DOMException {
//		return put("marker-offset", markerOffset, important) ;
//    }
//
//    public String getMarks() {
//		return get("marks");
//    }
//
//    public DCss2Properties setMarks(String marks) throws DOMException {
//		return put("marks", marks) ;
//    }
//
//    public DCss2Properties setMarks(String marks, boolean important) throws DOMException {
//		return put("marks", marks, important) ;
//    }
//
//    public String getMaxHeight() {
//		return get("max-height");
//    }
//
//    public DCss2Properties setMaxHeight(String maxHeight) throws DOMException {
//		return put("max-height", maxHeight) ;
//    }
//
//    public DCss2Properties setMaxHeight(String maxHeight, boolean important) throws DOMException {
//		return put("max-height", maxHeight, important) ;
//    }
//
//    public String getMaxWidth() {
//		return get("max-width");
//    }
//
//    public DCss2Properties setMaxWidth(String maxWidth) throws DOMException {
//		return put("max-width", maxWidth) ;
//    }
//
//    public DCss2Properties setMaxWidth(String maxWidth, boolean important) throws DOMException {
//		return put("max-width", maxWidth, important) ;
//    }
//
//    public String getMinHeight() {
//		return get("min-height");
//    }
//
//    public DCss2Properties setMinHeight(String minHeight) throws DOMException {
//		return put("min-height", minHeight) ;
//    }
//
//    public DCss2Properties setMinHeight(String minHeight, boolean important) throws DOMException {
//		return put("min-height", minHeight, important) ;
//    }
//
//    public String getMinWidth() {
//		return get("min-width");
//    }
//
//    public DCss2Properties setMinWidth(String minWidth) throws DOMException {
//		return put("min-width", minWidth) ;
//    }
//
//    public DCss2Properties setMinWidth(String minWidth, boolean important) throws DOMException {
//		return put("min-width", minWidth, important) ;
//    }

    public String getOpacity() {
		return get("opacity");
    }

    public DCss2Properties setOpacity(String opacity) throws DOMException {
		return put("opacity", opacity) ;
    }

    public DCss2Properties setOpacity(String opacity, boolean important) throws DOMException {
		return put("opacity", opacity, important) ;
    }
    
//    public String getOrphans() {
//		return get("orphans");
//    }
//
//    public DCss2Properties setOrphans(String orphans) throws DOMException {
//		return put("orphans", orphans) ;
//    }
//
//    public DCss2Properties setOrphans(String orphans, boolean important) throws DOMException {
//		return put("orphans", orphans, important) ;
//    }
//
//    public String getOutline() {
//		return get("outline");
//    }
//
//    public DCss2Properties setOutline(String outline) throws DOMException {
//		return put("outline", outline) ;
//    }
//
//    public DCss2Properties setOutline(String outline, boolean important) throws DOMException {
//		return put("outline", outline, important) ;
//    }
//
//    public String getOutlineColor() {
//        return get("outline-color");
//    }
//
//    public DCss2Properties setOutlineColor(String outlineColor) throws DOMException {
//		return put("outline-color", outlineColor) ;
//    }
//
//    public DCss2Properties setOutlineColor(String outlineColor, boolean important) throws DOMException {
//		return put("outline-color", outlineColor, important) ;
//    }
//
//    public String getOutlineStyle() {
//		return get("outline-style");
//    }
//
//    public DCss2Properties setOutlineStyle(String outlineStyle) throws DOMException {
//		return put("outline-style", outlineStyle) ;
//    }
//
//    public DCss2Properties setOutlineStyle(String outlineStyle, boolean important) throws DOMException {
//		return put("outline-style", outlineStyle, important) ;
//    }
//
//    public String getOutlineWidth() {
//		return get("outline-width");
//    }
//
//    public DCss2Properties setOutlineWidth(String outlineWidth) throws DOMException {
//		return put("outline-width", outlineWidth) ;
//    }
//
//    public DCss2Properties setOutlineWidth(String outlineWidth, boolean important) throws DOMException {
//		return put("outline-width", outlineWidth, important) ;
//    }

    public String getOverflow() {
		return get("overflow");
    }

    public DCss2Properties setOverflow(String overflow) throws DOMException {
		return put("overflow", overflow) ;
    }

    public DCss2Properties setOverflow(String overflow, boolean important) throws DOMException {
		return put("overflow", overflow, important) ;
    }

//    public String getPadding() {
//		return get("padding");
//    }
//
//    public DCss2Properties setPadding(String padding) throws DOMException {
//		return put("padding", padding) ;
//    }
//
//    public DCss2Properties setPadding(String padding, boolean important) throws DOMException {
//		return put("padding", padding, important) ;
//    }
//
//    public String getPaddingTop() {
//		return get("padding-top");
//    }
//
//    public DCss2Properties setPaddingTop(String paddingTop) throws DOMException {
//		return put("padding-top", paddingTop) ;
//    }
//
//    public DCss2Properties setPaddingTop(String paddingTop, boolean important) throws DOMException {
//		return put("padding-top", paddingTop, important) ;
//    }
//
//    public String getPaddingRight() {
//		return get("padding-right");
//    }
//
//    public DCss2Properties setPaddingRight(String paddingRight) throws DOMException {
//		return put("padding-right", paddingRight) ;
//    }
//
//    public DCss2Properties setPaddingRight(String paddingRight, boolean important) throws DOMException {
//		return put("padding-right", paddingRight, important) ;
//    }
//
//    public String getPaddingBottom() {
//		return get("padding-bottom");
//    }
//
//    public DCss2Properties setPaddingBottom(String paddingBottom) throws DOMException {
//		return put("padding-bottom", paddingBottom) ;
//    }
//
//    public DCss2Properties setPaddingBottom(String paddingBottom, boolean important) throws DOMException {
//		return put("padding-bottom", paddingBottom, important) ;
//    }
//
//    public String getPaddingLeft() {
//		return get("padding-left");
//    }
//
//    public DCss2Properties setPaddingLeft(String paddingLeft) throws DOMException {
//		return put("padding-left", paddingLeft) ;
//    }
//
//    public DCss2Properties setPaddingLeft(String paddingLeft, boolean important) throws DOMException {
//		return put("padding-left", paddingLeft, important) ;
//    }
//
//    public String getPage() {
//		return get("page");
//    }
//
//    public DCss2Properties setPage(String page) throws DOMException {
//		return put("page", page) ;
//    }
//
//    public DCss2Properties setPage(String page, boolean important) throws DOMException {
//		return put("page", page, important) ;
//    }
//
//    public String getPageBreakAfter() {
//		return get("page-break-after");
//    }
//
//    public DCss2Properties setPageBreakAfter(String pageBreakAfter) throws DOMException {
//		return put("page-break-after", pageBreakAfter) ;
//    }
//
//    public DCss2Properties setPageBreakAfter(String pageBreakAfter, boolean important) throws DOMException {
//		return put("page-break-after", pageBreakAfter, important) ;
//    }
//
//    public String getPageBreakBefore() {
//		return get("page-break-before");
//    }
//
//    public DCss2Properties setPageBreakBefore(String pageBreakBefore) throws DOMException {
//		return put("page-break-before", pageBreakBefore) ;
//    }
//
//    public DCss2Properties setPageBreakBefore(String pageBreakBefore, boolean important) throws DOMException {
//		return put("page-break-before", pageBreakBefore, important) ;
//    }
//
//    public String getPageBreakInside() {
//		return get("page-break-inside");
//    }
//
//    public DCss2Properties setPageBreakInside(String pageBreakInside) throws DOMException {
//		return put("page-break-inside", pageBreakInside) ;
//    }
//
//    public DCss2Properties setPageBreakInside(String pageBreakInside, boolean important) throws DOMException {
//		return put("page-break-inside", pageBreakInside, important) ;
//    }
//
//    public String getPause() {
//		return get("pause");
//    }
//
//    public DCss2Properties setPause(String pause) throws DOMException {
//		return put("pause", pause) ;
//    }
//
//    public DCss2Properties setPause(String pause, boolean important) throws DOMException {
//		return put("pause", pause, important) ;
//    }
//
//    public String getPauseAfter() {
//		return get("pause-after");
//    }
//
//    public DCss2Properties setPauseAfter(String pauseAfter) throws DOMException {
//		return put("pause-after", pauseAfter) ;
//    }
//
//    public DCss2Properties setPauseAfter(String pauseAfter, boolean important) throws DOMException {
//		return put("pause-after", pauseAfter, important) ;
//    }
//
//    public String getPauseBefore() {
//		return get("pause-before");
//    }
//
//    public DCss2Properties setPauseBefore(String pauseBefore) throws DOMException {
//		return put("pause-before", pauseBefore) ;
//    }
//
//    public DCss2Properties setPauseBefore(String pauseBefore, boolean important) throws DOMException {
//		return put("pause-before", pauseBefore, important) ;
//    }
//
//    public String getPitch() {
//        return get("pitch") ;
//    }
//
//    public DCss2Properties setPitch(String pitch) throws DOMException {
//		return put("pitch", pitch) ;
//    }
//
//    public DCss2Properties setPitch(String pitch, boolean important) throws DOMException {
//		return put("pitch", pitch, important) ;
//    }
//
//    public String getPitchRange() {
//		return get("pitch-range");
//    }
//
//    public DCss2Properties setPitchRange(String pitchRange) throws DOMException {
//		return put("pitch-range", pitchRange) ;
//    }
//
//    public DCss2Properties setPitchRange(String pitchRange, boolean important) throws DOMException {
//		return put("pitch-range", pitchRange, important) ;
//    }
//
//    public String getPlayDuring() {
//		return get("play-during");
//    }
//
//    public DCss2Properties setPlayDuring(String playDuring) throws DOMException {
//		return put("play-during", playDuring) ;
//    }
//
//    public DCss2Properties setPlayDuring(String playDuring, boolean important) throws DOMException {
//		return put("play-during", playDuring, important) ;
//    }

    public String getPosition() {
		return get("position");
    }

    public DCss2Properties setPosition(String position) throws DOMException {
		return put("position", position) ;
    }

    public DCss2Properties setPosition(String position, boolean important) throws DOMException {
		return put("position", position, important) ;
    }

//    public String getQuotes() {
//		return get("quotes");
//    }
//
//    public DCss2Properties setQuotes(String quotes) throws DOMException {
//		return put("quotes", quotes) ;
//    }
//
//    public DCss2Properties setQuotes(String quotes, boolean important) throws DOMException {
//		return put("quotes", quotes, important) ;
//    }
//
//    public String getRichness() {
//		return get("richness");
//    }
//
//    public DCss2Properties setRichness(String richness) throws DOMException {
//		return put("richness", richness) ;
//    }
//
//    public DCss2Properties setRichness(String richness, boolean important) throws DOMException {
//		return put("richness", richness, important) ;
//    }

    public String getRight() {
		return get("right");
    }

    public DCss2Properties setRight(String right) throws DOMException {
		return put("right", right) ;
    }

    public DCss2Properties setRight(String right, boolean important) throws DOMException {
		return put("right", right, important) ;
    }

//    public String getSize() {
//		return get("size");
//    }
//
//    public DCss2Properties setSize(String size) throws DOMException {
//		return put("size", size) ;
//    }
//
//    public DCss2Properties setSize(String size, boolean important) throws DOMException {
//		return put("size", size, important) ;
//    }
//
//    public String getSpeak() {
//		return get("speak");
//    }
//
//    public DCss2Properties setSpeak(String speak) throws DOMException {
//		return put("speak", speak) ;
//    }
//
//    public DCss2Properties setSpeak(String speak, boolean important) throws DOMException {
//		return put("speak", speak, important) ;
//    }
//
//    public String getSpeakHeader() {
//		return get("speak-header");
//    }
//
//    public DCss2Properties setSpeakHeader(String speakHeader) throws DOMException {
//		return put("speak-header", speakHeader) ;
//    }
//
//    public DCss2Properties setSpeakHeader(String speakHeader, boolean important) throws DOMException {
//		return put("speak-header", speakHeader, important) ;
//    }
//
//    public String getSpeakNumeral() {
//		return get("speak-numeral");
//    }
//
//    public DCss2Properties setSpeakNumeral(String speakNumeral) throws DOMException {
//		return put("speak-numeral", speakNumeral) ;
//    }
//
//    public DCss2Properties setSpeakNumeral(String speakNumeral, boolean important) throws DOMException {
//		return put("speak-numeral", speakNumeral, important) ;
//    }
//
//    public String getSpeakPunctuation() {
//		return get("speak-punctuation");
//    }
//
//    public DCss2Properties setSpeakPunctuation(String speakPunctuation) throws DOMException {
//		return put("speak-punctuation", speakPunctuation) ;
//    }
//
//    public DCss2Properties setSpeakPunctuation(String speakPunctuation, boolean important) throws DOMException {
//		return put("speak-punctuation", speakPunctuation, important) ;
//    }
//
//    public String getSpeechRate() {
//		return get("speech-rate");
//    }
//
//    public DCss2Properties setSpeechRate(String speechRate) throws DOMException {
//		return put("speech-rate", speechRate) ;
//    }
//
//    public DCss2Properties setSpeechRate(String speechRate, boolean important) throws DOMException {
//		return put("speech-rate", speechRate, important) ;
//    }
//
//    public String getStress() {
//		return get("stress");
//    }
//
//    public DCss2Properties setStress(String stress) throws DOMException {
//		return put("stress", stress) ;
//    }
//
//    public DCss2Properties setStress(String stress, boolean important) throws DOMException {
//		return put("stress", stress, important) ;
//    }
//
//    public String getTableLayout() {
//		return get("table-layout");
//    }
//
//    public DCss2Properties setTableLayout(String tableLayout) throws DOMException {
//		return put("table-layout", tableLayout) ;
//    }
//
//    public DCss2Properties setTableLayout(String tableLayout, boolean important) throws DOMException {
//		return put("table-layout", tableLayout, important) ;
//    }
//
    public String getTextAlign() {
		return get("text-align");
    }

    public DCss2Properties setTextAlign(String textAlign) throws DOMException {
		return put("text-align", textAlign) ;
    }

    public DCss2Properties setTextAlign(String textAlign, boolean important) throws DOMException {
		return put("text-align", textAlign, important) ;
    }
//
//    public String getTextDecoration() {
//		return get("text-decoration");
//    }
//
//    public DCss2Properties setTextDecoration(String textDecoration) throws DOMException {
//		return put("text-decoration", textDecoration) ;
//    }
//
//    public DCss2Properties setTextDecoration(String textDecoration, boolean important) throws DOMException {
//		return put("text-decoration", textDecoration, important) ;
//    }
//
//    public String getTextIndent() {
//		return get("text-indent");
//    }
//
//    public DCss2Properties setTextIndent(String textIndent) throws DOMException {
//		return put("text-indent", textIndent) ;
//    }
//
//    public DCss2Properties setTextIndent(String textIndent, boolean important) throws DOMException {
//		return put("text-indent", textIndent, important) ;
//    }
//
//    public String getTextShadow() {
//		return get("text-shadow");
//    }
//
//    public DCss2Properties setTextShadow(String textShadow) throws DOMException {
//		return put("text-shadow", textShadow) ;
//    }
//
//    public DCss2Properties setTextShadow(String textShadow, boolean important) throws DOMException {
//		return put("text-shadow", textShadow, important) ;
//    }

    public String getTextTransform() {
		return get("text-transform");
    }

    public DCss2Properties setTextTransform(String textTransform) throws DOMException {
		return put("text-transform", textTransform) ;
    }

    public DCss2Properties setTextTransform(String textTransform, boolean important) throws DOMException {
		return put("text-transform", textTransform, important) ;
    }

    public String getTop() {
		return get("top");
    }

    public DCss2Properties setTop(String top) throws DOMException {
		return put("top", top) ;
    }

    public DCss2Properties setTop(String top, boolean important) throws DOMException {
		return put("top", top, important) ;
    }

    public String getUnicodeBidi() {
		return get("unicode-bidi");
    }

    public DCss2Properties setUnicodeBidi(String unicodeBidi) throws DOMException {
		return put("unicode-bidi", unicodeBidi) ;
    }

    public DCss2Properties setUnicodeBidi(String unicodeBidi, boolean important) throws DOMException {
		return put("unicode-bidi", unicodeBidi, important) ;
    }

//    public String getVerticalAlign() {
//		return get("vertical-align");
//    }
//
//    public DCss2Properties setVerticalAlign(String verticalAlign) throws DOMException {
//		return put("vertical-align", verticalAlign) ;
//    }
//
//    public DCss2Properties setVerticalAlign(String verticalAlign, boolean important) throws DOMException {
//		return put("vertical-align", verticalAlign, important) ;
//    }

    public String getVisibility() {
		return get("visibility");
    }

    public DCss2Properties setVisibility(String visibility) throws DOMException {
		return put("visibility", visibility) ;
    }

    public DCss2Properties setVisibility(String visibility, boolean important) throws DOMException {
		return put("visibility", visibility, important) ;
    }

//    public String getVoiceFamily() {
//		return get("voice-family");
//    }
//
//    public DCss2Properties setVoiceFamily(String voiceFamily) throws DOMException {
//		return put("voice-family", voiceFamily) ;
//    }
//
//    public DCss2Properties setVoiceFamily(String voiceFamily, boolean important) throws DOMException {
//		return put("voice-family", voiceFamily, important) ;
//    }

//    public String getVolume() {
//		return get("volume");
//    }
//
//    public DCss2Properties setVolume(String volume) throws DOMException {
//		return put("volume", volume) ;
//    }
//
//    public DCss2Properties setVolume(String volume, boolean important) throws DOMException {
//		return put("volume", volume, important) ;
//    }

    public String getWhiteSpace() {
		return get("white-space");
    }

    public DCss2Properties setWhiteSpace(String whiteSpace) throws DOMException {
		return put("white-space", whiteSpace) ;
    }

    public DCss2Properties setWhiteSpace(String whiteSpace, boolean important) throws DOMException {
		return put("white-space", whiteSpace, important) ;
    }

    public String getWidows() {
		return get("widows");
    }

    public DCss2Properties setWidows(String widows) throws DOMException {
		return put("widows", widows) ;
    }

    public DCss2Properties setWidows(String widows, boolean important) throws DOMException {
		return put("widows", widows, important) ;
    }

    public String getWidth() {
		return get("width");
    }

    public DCss2Properties setWidth(String width) throws DOMException {
		return put("width", width) ;
    }

    public DCss2Properties setWidth(String width, boolean important) throws DOMException {
		return put("width", width, important) ;
    }

    public String getWordSpacing() {
		return get("word-spacing");
    }

    public DCss2Properties setWordSpacing(String wordSpacing) throws DOMException {
		return put("word-spacing", wordSpacing) ;
    }

    public DCss2Properties setWordSpacing(String wordSpacing, boolean important) throws DOMException {
		return put("word-spacing", wordSpacing, important) ;
    }

    public String getZIndex() {
		return get("z-index");
    }

    public DCss2Properties setZIndex(String zIndex) throws DOMException {
		return put("z-index", zIndex) ;
    }

    public DCss2Properties setZIndex(String zIndex, boolean important) throws DOMException {
		return put("z-index", zIndex, important) ;
    }

    //
    // Latest code generated W3C properties
    //
    public String getAzimuth() { 
    	return get("azimuth"); 
    } 
    public DCss2Properties setAzimuth(final String value) throws DOMException { 
    	return put("azimuth", value) ; 
    } 
    public DCss2Properties setAzimuth(final String value, final boolean important) throws DOMException { 
    	return put("azimuth", value, important) ; 
    } 


    public String getBackgroundBreak() { 
    	return get("background-break"); 
    } 
    public DCss2Properties setBackgroundBreak(final String value) throws DOMException { 
    	return put("background-break", value) ; 
    } 
    public DCss2Properties setBackgroundBreak(final String value, final boolean important) throws DOMException { 
    	return put("background-break", value, important) ; 
    } 


    public String getBackgroundPositionX() { 
    	return get("background-position-x"); 
    } 
    public DCss2Properties setBackgroundPositionX(final String value) throws DOMException { 
    	return put("background-position-x", value) ; 
    } 
    public DCss2Properties setBackgroundPositionX(final String value, final boolean important) throws DOMException { 
    	return put("background-position-x", value, important) ; 
    } 


    public String getBackgroundPositionY() { 
    	return get("background-position-y"); 
    } 
    public DCss2Properties setBackgroundPositionY(final String value) throws DOMException { 
    	return put("background-position-y", value) ; 
    } 
    public DCss2Properties setBackgroundPositionY(final String value, final boolean important) throws DOMException { 
    	return put("background-position-y", value, important) ; 
    } 


    public String getCaptionSide() { 
    	return get("caption-side"); 
    } 
    public DCss2Properties setCaptionSide(final String value) throws DOMException { 
    	return put("caption-side", value) ; 
    } 
    public DCss2Properties setCaptionSide(final String value, final boolean important) throws DOMException { 
    	return put("caption-side", value, important) ; 
    } 


    public String getImeMode() { 
    	return get("ime-mode"); 
    } 
    public DCss2Properties setImeMode(final String value) throws DOMException { 
    	return put("ime-mode", value) ; 
    } 
    public DCss2Properties setImeMode(final String value, final boolean important) throws DOMException { 
    	return put("ime-mode", value, important) ; 
    } 


    public String getPitchRange() { 
    	return get("pitch-range"); 
    } 
    public DCss2Properties setPitchRange(final String value) throws DOMException { 
    	return put("pitch-range", value) ; 
    } 
    public DCss2Properties setPitchRange(final String value, final boolean important) throws DOMException { 
    	return put("pitch-range", value, important) ; 
    } 


    public String getPitch() { 
    	return get("pitch"); 
    } 
    public DCss2Properties setPitch(final String value) throws DOMException { 
    	return put("pitch", value) ; 
    } 
    public DCss2Properties setPitch(final String value, final boolean important) throws DOMException { 
    	return put("pitch", value, important) ; 
    } 


    public String getPlayDuring() { 
    	return get("play-during"); 
    } 
    public DCss2Properties setPlayDuring(final String value) throws DOMException { 
    	return put("play-during", value) ; 
    } 
    public DCss2Properties setPlayDuring(final String value, final boolean important) throws DOMException { 
    	return put("play-during", value, important) ; 
    } 


    public String getQuotes() { 
    	return get("quotes"); 
    } 
    public DCss2Properties setQuotes(final String value) throws DOMException { 
    	return put("quotes", value) ; 
    } 
    public DCss2Properties setQuotes(final String value, final boolean important) throws DOMException { 
    	return put("quotes", value, important) ; 
    } 


    public String getRichness() { 
    	return get("richness"); 
    } 
    public DCss2Properties setRichness(final String value) throws DOMException { 
    	return put("richness", value) ; 
    } 
    public DCss2Properties setRichness(final String value, final boolean important) throws DOMException { 
    	return put("richness", value, important) ; 
    } 


    public String getSpeakHeader() { 
    	return get("speak-header"); 
    } 
    public DCss2Properties setSpeakHeader(final String value) throws DOMException { 
    	return put("speak-header", value) ; 
    } 
    public DCss2Properties setSpeakHeader(final String value, final boolean important) throws DOMException { 
    	return put("speak-header", value, important) ; 
    } 


    public String getSpeakNumeral() { 
    	return get("speak-numeral"); 
    } 
    public DCss2Properties setSpeakNumeral(final String value) throws DOMException { 
    	return put("speak-numeral", value) ; 
    } 
    public DCss2Properties setSpeakNumeral(final String value, final boolean important) throws DOMException { 
    	return put("speak-numeral", value, important) ; 
    } 


    public String getSpeakPunctuation() { 
    	return get("speak-punctuation"); 
    } 
    public DCss2Properties setSpeakPunctuation(final String value) throws DOMException { 
    	return put("speak-punctuation", value) ; 
    } 
    public DCss2Properties setSpeakPunctuation(final String value, final boolean important) throws DOMException { 
    	return put("speak-punctuation", value, important) ; 
    } 


    public String getSpeechRate() { 
    	return get("speech-rate"); 
    } 
    public DCss2Properties setSpeechRate(final String value) throws DOMException { 
    	return put("speech-rate", value) ; 
    } 
    public DCss2Properties setSpeechRate(final String value, final boolean important) throws DOMException { 
    	return put("speech-rate", value, important) ; 
    } 


    public String getStress() { 
    	return get("stress"); 
    } 
    public DCss2Properties setStress(final String value) throws DOMException { 
    	return put("stress", value) ; 
    } 
    public DCss2Properties setStress(final String value, final boolean important) throws DOMException { 
    	return put("stress", value, important) ; 
    } 


    public String getTableLayout() { 
    	return get("table-layout"); 
    } 
    public DCss2Properties setTableLayout(final String value) throws DOMException { 
    	return put("table-layout", value) ; 
    } 
    public DCss2Properties setTableLayout(final String value, final boolean important) throws DOMException { 
    	return put("table-layout", value, important) ; 
    } 


    public String getVolume() { 
    	return get("volume"); 
    } 
    public DCss2Properties setVolume(final String value) throws DOMException { 
    	return put("volume", value) ; 
    } 
    public DCss2Properties setVolume(final String value, final boolean important) throws DOMException { 
    	return put("volume", value, important) ; 
    } 


    public String getBackgroundImage() { 
    	return get("background-image"); 
    } 
    public DCss2Properties setBackgroundImage(final String value) throws DOMException { 
    	return put("background-image", value) ; 
    } 
    public DCss2Properties setBackgroundImage(final String value, final boolean important) throws DOMException { 
    	return put("background-image", value, important) ; 
    } 


    public String getBackgroundRepeat() { 
    	return get("background-repeat"); 
    } 
    public DCss2Properties setBackgroundRepeat(final String value) throws DOMException { 
    	return put("background-repeat", value) ; 
    } 
    public DCss2Properties setBackgroundRepeat(final String value, final boolean important) throws DOMException { 
    	return put("background-repeat", value, important) ; 
    } 


    public String getBackgroundClip() { 
    	return get("background-clip"); 
    } 
    public DCss2Properties setBackgroundClip(final String value) throws DOMException { 
    	return put("background-clip", value) ; 
    } 
    public DCss2Properties setBackgroundClip(final String value, final boolean important) throws DOMException { 
    	return put("background-clip", value, important) ; 
    } 


    public String getBackgroundOrigin() { 
    	return get("background-origin"); 
    } 
    public DCss2Properties setBackgroundOrigin(final String value) throws DOMException { 
    	return put("background-origin", value) ; 
    } 
    public DCss2Properties setBackgroundOrigin(final String value, final boolean important) throws DOMException { 
    	return put("background-origin", value, important) ; 
    } 


    public String getBackgroundSize() { 
    	return get("background-size"); 
    } 
    public DCss2Properties setBackgroundSize(final String value) throws DOMException { 
    	return put("background-size", value) ; 
    } 
    public DCss2Properties setBackgroundSize(final String value, final boolean important) throws DOMException { 
    	return put("background-size", value, important) ; 
    } 


    public String getBorderCollapse() { 
    	return get("border-collapse"); 
    } 
    public DCss2Properties setBorderCollapse(final String value) throws DOMException { 
    	return put("border-collapse", value) ; 
    } 
    public DCss2Properties setBorderCollapse(final String value, final boolean important) throws DOMException { 
    	return put("border-collapse", value, important) ; 
    } 


    public String getBorderSpacing() { 
    	return get("border-spacing"); 
    } 
    public DCss2Properties setBorderSpacing(final String value) throws DOMException { 
    	return put("border-spacing", value) ; 
    } 
    public DCss2Properties setBorderSpacing(final String value, final boolean important) throws DOMException { 
    	return put("border-spacing", value, important) ; 
    } 


    public String getOutline() { 
    	return get("outline"); 
    } 
    public DCss2Properties setOutline(final String value) throws DOMException { 
    	return put("outline", value) ; 
    } 
    public DCss2Properties setOutline(final String value, final boolean important) throws DOMException { 
    	return put("outline", value, important) ; 
    } 


    public String getOutlineWidth() { 
    	return get("outline-width"); 
    } 
    public DCss2Properties setOutlineWidth(final String value) throws DOMException { 
    	return put("outline-width", value) ; 
    } 
    public DCss2Properties setOutlineWidth(final String value, final boolean important) throws DOMException { 
    	return put("outline-width", value, important) ; 
    } 


    public String getOutlineStyle() { 
    	return get("outline-style"); 
    } 
    public DCss2Properties setOutlineStyle(final String value) throws DOMException { 
    	return put("outline-style", value) ; 
    } 
    public DCss2Properties setOutlineStyle(final String value, final boolean important) throws DOMException { 
    	return put("outline-style", value, important) ; 
    } 


    public String getOutlineColor() { 
    	return get("outline-color"); 
    } 
    public DCss2Properties setOutlineColor(final String value) throws DOMException { 
    	return put("outline-color", value) ; 
    } 
    public DCss2Properties setOutlineColor(final String value, final boolean important) throws DOMException { 
    	return put("outline-color", value, important) ; 
    } 


    public String getMinWidth() { 
    	return get("min-width"); 
    } 
    public DCss2Properties setMinWidth(final String value) throws DOMException { 
    	return put("min-width", value) ; 
    } 
    public DCss2Properties setMinWidth(final String value, final boolean important) throws DOMException { 
    	return put("min-width", value, important) ; 
    } 


    public String getMaxWidth() { 
    	return get("max-width"); 
    } 
    public DCss2Properties setMaxWidth(final String value) throws DOMException { 
    	return put("max-width", value) ; 
    } 
    public DCss2Properties setMaxWidth(final String value, final boolean important) throws DOMException { 
    	return put("max-width", value, important) ; 
    } 


    public String getHeight() { 
    	return get("height"); 
    } 
    public DCss2Properties setHeight(final String value) throws DOMException { 
    	return put("height", value) ; 
    } 
    public DCss2Properties setHeight(final String value, final boolean important) throws DOMException { 
    	return put("height", value, important) ; 
    } 


    public String getMinHeight() { 
    	return get("min-height"); 
    } 
    public DCss2Properties setMinHeight(final String value) throws DOMException { 
    	return put("min-height", value) ; 
    } 
    public DCss2Properties setMinHeight(final String value, final boolean important) throws DOMException { 
    	return put("min-height", value, important) ; 
    } 


    public String getMaxHeight() { 
    	return get("max-height"); 
    } 
    public DCss2Properties setMaxHeight(final String value) throws DOMException { 
    	return put("max-height", value) ; 
    } 
    public DCss2Properties setMaxHeight(final String value, final boolean important) throws DOMException { 
    	return put("max-height", value, important) ; 
    } 


    public String getMarginTop() { 
    	return get("margin-top"); 
    } 
    public DCss2Properties setMarginTop(final String value) throws DOMException { 
    	return put("margin-top", value) ; 
    } 
    public DCss2Properties setMarginTop(final String value, final boolean important) throws DOMException { 
    	return put("margin-top", value, important) ; 
    } 


    public String getMarginBottom() { 
    	return get("margin-bottom"); 
    } 
    public DCss2Properties setMarginBottom(final String value) throws DOMException { 
    	return put("margin-bottom", value) ; 
    } 
    public DCss2Properties setMarginBottom(final String value, final boolean important) throws DOMException { 
    	return put("margin-bottom", value, important) ; 
    } 


    public String getMarginRight() { 
    	return get("margin-right"); 
    } 
    public DCss2Properties setMarginRight(final String value) throws DOMException { 
    	return put("margin-right", value) ; 
    } 
    public DCss2Properties setMarginRight(final String value, final boolean important) throws DOMException { 
    	return put("margin-right", value, important) ; 
    } 


    public String getMarginLeft() { 
    	return get("margin-left"); 
    } 
    public DCss2Properties setMarginLeft(final String value) throws DOMException { 
    	return put("margin-left", value) ; 
    } 
    public DCss2Properties setMarginLeft(final String value, final boolean important) throws DOMException { 
    	return put("margin-left", value, important) ; 
    } 


    public String getMarginWidth() { 
    	return get("margin-width"); 
    } 
    public DCss2Properties setMarginWidth(final String value) throws DOMException { 
    	return put("margin-width", value) ; 
    } 
    public DCss2Properties setMarginWidth(final String value, final boolean important) throws DOMException { 
    	return put("margin-width", value, important) ; 
    } 


    public String getMargin() { 
    	return get("margin"); 
    } 
    public DCss2Properties setMargin(final String value) throws DOMException { 
    	return put("margin", value) ; 
    } 
    public DCss2Properties setMargin(final String value, final boolean important) throws DOMException { 
    	return put("margin", value, important) ; 
    } 


    public String getPaddingTop() { 
    	return get("padding-top"); 
    } 
    public DCss2Properties setPaddingTop(final String value) throws DOMException { 
    	return put("padding-top", value) ; 
    } 
    public DCss2Properties setPaddingTop(final String value, final boolean important) throws DOMException { 
    	return put("padding-top", value, important) ; 
    } 


    public String getPaddingRight() { 
    	return get("padding-right"); 
    } 
    public DCss2Properties setPaddingRight(final String value) throws DOMException { 
    	return put("padding-right", value) ; 
    } 
    public DCss2Properties setPaddingRight(final String value, final boolean important) throws DOMException { 
    	return put("padding-right", value, important) ; 
    } 


    public String getPaddingBottom() { 
    	return get("padding-bottom"); 
    } 
    public DCss2Properties setPaddingBottom(final String value) throws DOMException { 
    	return put("padding-bottom", value) ; 
    } 
    public DCss2Properties setPaddingBottom(final String value, final boolean important) throws DOMException { 
    	return put("padding-bottom", value, important) ; 
    } 


    public String getPaddingLeft() { 
    	return get("padding-left"); 
    } 
    public DCss2Properties setPaddingLeft(final String value) throws DOMException { 
    	return put("padding-left", value) ; 
    } 
    public DCss2Properties setPaddingLeft(final String value, final boolean important) throws DOMException { 
    	return put("padding-left", value, important) ; 
    } 


    public String getPadding() { 
    	return get("padding"); 
    } 
    public DCss2Properties setPadding(final String value) throws DOMException { 
    	return put("padding", value) ; 
    } 
    public DCss2Properties setPadding(final String value, final boolean important) throws DOMException { 
    	return put("padding", value, important) ; 
    } 


    public String getListStyle() { 
    	return get("list-style"); 
    } 
    public DCss2Properties setListStyle(final String value) throws DOMException { 
    	return put("list-style", value) ; 
    } 
    public DCss2Properties setListStyle(final String value, final boolean important) throws DOMException { 
    	return put("list-style", value, important) ; 
    } 


    public String getListStyleType() { 
    	return get("list-style-type"); 
    } 
    public DCss2Properties setListStyleType(final String value) throws DOMException { 
    	return put("list-style-type", value) ; 
    } 
    public DCss2Properties setListStyleType(final String value, final boolean important) throws DOMException { 
    	return put("list-style-type", value, important) ; 
    } 


    public String getListStyleImage() { 
    	return get("list-style-image"); 
    } 
    public DCss2Properties setListStyleImage(final String value) throws DOMException { 
    	return put("list-style-image", value) ; 
    } 
    public DCss2Properties setListStyleImage(final String value, final boolean important) throws DOMException { 
    	return put("list-style-image", value, important) ; 
    } 


    public String getListStylePosition() { 
    	return get("list-style-position"); 
    } 
    public DCss2Properties setListStylePosition(final String value) throws DOMException { 
    	return put("list-style-position", value) ; 
    } 
    public DCss2Properties setListStylePosition(final String value, final boolean important) throws DOMException { 
    	return put("list-style-position", value, important) ; 
    } 


    public String getMarkerOffset() { 
    	return get("marker-offset"); 
    } 
    public DCss2Properties setMarkerOffset(final String value) throws DOMException { 
    	return put("marker-offset", value) ; 
    } 
    public DCss2Properties setMarkerOffset(final String value, final boolean important) throws DOMException { 
    	return put("marker-offset", value, important) ; 
    } 


    public String getTextAlignLast() { 
    	return get("text-align-last"); 
    } 
    public DCss2Properties setTextAlignLast(final String value) throws DOMException { 
    	return put("text-align-last", value) ; 
    } 
    public DCss2Properties setTextAlignLast(final String value, final boolean important) throws DOMException { 
    	return put("text-align-last", value, important) ; 
    } 


    public String getTextBlink() { 
    	return get("text-blink"); 
    } 
    public DCss2Properties setTextBlink(final String value) throws DOMException { 
    	return put("text-blink", value) ; 
    } 
    public DCss2Properties setTextBlink(final String value, final boolean important) throws DOMException { 
    	return put("text-blink", value, important) ; 
    } 


    public String getTextLineDecoration() { 
    	return get("text-line-decoration"); 
    } 
    public DCss2Properties setTextLineDecoration(final String value) throws DOMException { 
    	return put("text-line-decoration", value) ; 
    } 
    public DCss2Properties setTextLineDecoration(final String value, final boolean important) throws DOMException { 
    	return put("text-line-decoration", value, important) ; 
    } 


    public String getTextLineColor() { 
    	return get("text-line-color"); 
    } 
    public DCss2Properties setTextLineColor(final String value) throws DOMException { 
    	return put("text-line-color", value) ; 
    } 
    public DCss2Properties setTextLineColor(final String value, final boolean important) throws DOMException { 
    	return put("text-line-color", value, important) ; 
    } 


    public String getTextLineStyle() { 
    	return get("text-line-style"); 
    } 
    public DCss2Properties setTextLineStyle(final String value) throws DOMException { 
    	return put("text-line-style", value) ; 
    } 
    public DCss2Properties setTextLineStyle(final String value, final boolean important) throws DOMException { 
    	return put("text-line-style", value, important) ; 
    } 


    public String getTextLineSkip() { 
    	return get("text-line-skip"); 
    } 
    public DCss2Properties setTextLineSkip(final String value) throws DOMException { 
    	return put("text-line-skip", value) ; 
    } 
    public DCss2Properties setTextLineSkip(final String value, final boolean important) throws DOMException { 
    	return put("text-line-skip", value, important) ; 
    } 


    public String getTextLineThrough() { 
    	return get("text-line-through"); 
    } 
    public DCss2Properties setTextLineThrough(final String value) throws DOMException { 
    	return put("text-line-through", value) ; 
    } 
    public DCss2Properties setTextLineThrough(final String value, final boolean important) throws DOMException { 
    	return put("text-line-through", value, important) ; 
    } 


    public String getTextLineThroughColor() { 
    	return get("text-line-through-color"); 
    } 
    public DCss2Properties setTextLineThroughColor(final String value) throws DOMException { 
    	return put("text-line-through-color", value) ; 
    } 
    public DCss2Properties setTextLineThroughColor(final String value, final boolean important) throws DOMException { 
    	return put("text-line-through-color", value, important) ; 
    } 


    public String getTextLineThroughMode() { 
    	return get("text-line-through-mode"); 
    } 
    public DCss2Properties setTextLineThroughMode(final String value) throws DOMException { 
    	return put("text-line-through-mode", value) ; 
    } 
    public DCss2Properties setTextLineThroughMode(final String value, final boolean important) throws DOMException { 
    	return put("text-line-through-mode", value, important) ; 
    } 


    public String getTextLineThroughStyle() { 
    	return get("text-line-through-style"); 
    } 
    public DCss2Properties setTextLineThroughStyle(final String value) throws DOMException { 
    	return put("text-line-through-style", value) ; 
    } 
    public DCss2Properties setTextLineThroughStyle(final String value, final boolean important) throws DOMException { 
    	return put("text-line-through-style", value, important) ; 
    } 


    public String getTextLineThroughWidth() { 
    	return get("text-line-through-width"); 
    } 
    public DCss2Properties setTextLineThroughWidth(final String value) throws DOMException { 
    	return put("text-line-through-width", value) ; 
    } 
    public DCss2Properties setTextLineThroughWidth(final String value, final boolean important) throws DOMException { 
    	return put("text-line-through-width", value, important) ; 
    } 


    public String getTextOverline() { 
    	return get("text-overline"); 
    } 
    public DCss2Properties setTextOverline(final String value) throws DOMException { 
    	return put("text-overline", value) ; 
    } 
    public DCss2Properties setTextOverline(final String value, final boolean important) throws DOMException { 
    	return put("text-overline", value, important) ; 
    } 


    public String getTextOverlineColor() { 
    	return get("text-overline-color"); 
    } 
    public DCss2Properties setTextOverlineColor(final String value) throws DOMException { 
    	return put("text-overline-color", value) ; 
    } 
    public DCss2Properties setTextOverlineColor(final String value, final boolean important) throws DOMException { 
    	return put("text-overline-color", value, important) ; 
    } 


    public String getTextOverlineMode() { 
    	return get("text-overline-mode"); 
    } 
    public DCss2Properties setTextOverlineMode(final String value) throws DOMException { 
    	return put("text-overline-mode", value) ; 
    } 
    public DCss2Properties setTextOverlineMode(final String value, final boolean important) throws DOMException { 
    	return put("text-overline-mode", value, important) ; 
    } 


    public String getTextOverlineStyle() { 
    	return get("text-overline-style"); 
    } 
    public DCss2Properties setTextOverlineStyle(final String value) throws DOMException { 
    	return put("text-overline-style", value) ; 
    } 
    public DCss2Properties setTextOverlineStyle(final String value, final boolean important) throws DOMException { 
    	return put("text-overline-style", value, important) ; 
    } 


    public String getTextOverlineWidth() { 
    	return get("text-overline-width"); 
    } 
    public DCss2Properties setTextOverlineWidth(final String value) throws DOMException { 
    	return put("text-overline-width", value) ; 
    } 
    public DCss2Properties setTextOverlineWidth(final String value, final boolean important) throws DOMException { 
    	return put("text-overline-width", value, important) ; 
    } 


    public String getTextDecoration() { 
    	return get("text-decoration"); 
    } 
    public DCss2Properties setTextDecoration(final String value) throws DOMException { 
    	return put("text-decoration", value) ; 
    } 
    public DCss2Properties setTextDecoration(final String value, final boolean important) throws DOMException { 
    	return put("text-decoration", value, important) ; 
    } 


    public String getTextUnderline() { 
    	return get("text-underline"); 
    } 
    public DCss2Properties setTextUnderline(final String value) throws DOMException { 
    	return put("text-underline", value) ; 
    } 
    public DCss2Properties setTextUnderline(final String value, final boolean important) throws DOMException { 
    	return put("text-underline", value, important) ; 
    } 


    public String getTextUnderlineColor() { 
    	return get("text-underline-color"); 
    } 
    public DCss2Properties setTextUnderlineColor(final String value) throws DOMException { 
    	return put("text-underline-color", value) ; 
    } 
    public DCss2Properties setTextUnderlineColor(final String value, final boolean important) throws DOMException { 
    	return put("text-underline-color", value, important) ; 
    } 


    public String getTextUnderlineMode() { 
    	return get("text-underline-mode"); 
    } 
    public DCss2Properties setTextUnderlineMode(final String value) throws DOMException { 
    	return put("text-underline-mode", value) ; 
    } 
    public DCss2Properties setTextUnderlineMode(final String value, final boolean important) throws DOMException { 
    	return put("text-underline-mode", value, important) ; 
    } 


    public String getTextUnderlineStyle() { 
    	return get("text-underline-style"); 
    } 
    public DCss2Properties setTextUnderlineStyle(final String value) throws DOMException { 
    	return put("text-underline-style", value) ; 
    } 
    public DCss2Properties setTextUnderlineStyle(final String value, final boolean important) throws DOMException { 
    	return put("text-underline-style", value, important) ; 
    } 


    public String getTextUnderlineWidth() { 
    	return get("text-underline-width"); 
    } 
    public DCss2Properties setTextUnderlineWidth(final String value) throws DOMException { 
    	return put("text-underline-width", value) ; 
    } 
    public DCss2Properties setTextUnderlineWidth(final String value, final boolean important) throws DOMException { 
    	return put("text-underline-width", value, important) ; 
    } 


    public String getTextUnderlinePosition() { 
    	return get("text-underline-position"); 
    } 
    public DCss2Properties setTextUnderlinePosition(final String value) throws DOMException { 
    	return put("text-underline-position", value) ; 
    } 
    public DCss2Properties setTextUnderlinePosition(final String value, final boolean important) throws DOMException { 
    	return put("text-underline-position", value, important) ; 
    } 


    public String getTextEmphasis() { 
    	return get("text-emphasis"); 
    } 
    public DCss2Properties setTextEmphasis(final String value) throws DOMException { 
    	return put("text-emphasis", value) ; 
    } 
    public DCss2Properties setTextEmphasis(final String value, final boolean important) throws DOMException { 
    	return put("text-emphasis", value, important) ; 
    } 


    public String getTextShadow() { 
    	return get("text-shadow"); 
    } 
    public DCss2Properties setTextShadow(final String value) throws DOMException { 
    	return put("text-shadow", value) ; 
    } 
    public DCss2Properties setTextShadow(final String value, final boolean important) throws DOMException { 
    	return put("text-shadow", value, important) ; 
    } 


    public String getTextOutline() { 
    	return get("text-outline"); 
    } 
    public DCss2Properties setTextOutline(final String value) throws DOMException { 
    	return put("text-outline", value) ; 
    } 
    public DCss2Properties setTextOutline(final String value, final boolean important) throws DOMException { 
    	return put("text-outline", value, important) ; 
    } 


    public String getTextWrap() { 
    	return get("text-wrap"); 
    } 
    public DCss2Properties setTextWrap(final String value) throws DOMException { 
    	return put("text-wrap", value) ; 
    } 
    public DCss2Properties setTextWrap(final String value, final boolean important) throws DOMException { 
    	return put("text-wrap", value, important) ; 
    } 


    public String getWhiteSpaceCollapse() { 
    	return get("white-space-collapse"); 
    } 
    public DCss2Properties setWhiteSpaceCollapse(final String value) throws DOMException { 
    	return put("white-space-collapse", value) ; 
    } 
    public DCss2Properties setWhiteSpaceCollapse(final String value, final boolean important) throws DOMException { 
    	return put("white-space-collapse", value, important) ; 
    } 


    public String getTextIndent() { 
    	return get("text-indent"); 
    } 
    public DCss2Properties setTextIndent(final String value) throws DOMException { 
    	return put("text-indent", value) ; 
    } 
    public DCss2Properties setTextIndent(final String value, final boolean important) throws DOMException { 
    	return put("text-indent", value, important) ; 
    } 


    public String getHangingPunctuation() { 
    	return get("hanging-punctuation"); 
    } 
    public DCss2Properties setHangingPunctuation(final String value) throws DOMException { 
    	return put("hanging-punctuation", value) ; 
    } 
    public DCss2Properties setHangingPunctuation(final String value, final boolean important) throws DOMException { 
    	return put("hanging-punctuation", value, important) ; 
    } 


    public String getSpacingLimit() { 
    	return get("spacing-limit"); 
    } 
    public DCss2Properties setSpacingLimit(final String value) throws DOMException { 
    	return put("spacing-limit", value) ; 
    } 
    public DCss2Properties setSpacingLimit(final String value, final boolean important) throws DOMException { 
    	return put("spacing-limit", value, important) ; 
    } 


    public String getLetterSpacing() { 
    	return get("letter-spacing"); 
    } 
    public DCss2Properties setLetterSpacing(final String value) throws DOMException { 
    	return put("letter-spacing", value) ; 
    } 
    public DCss2Properties setLetterSpacing(final String value, final boolean important) throws DOMException { 
    	return put("letter-spacing", value, important) ; 
    } 


    public String getPunctuationTrim() { 
    	return get("punctuation-trim"); 
    } 
    public DCss2Properties setPunctuationTrim(final String value) throws DOMException { 
    	return put("punctuation-trim", value) ; 
    } 
    public DCss2Properties setPunctuationTrim(final String value, final boolean important) throws DOMException { 
    	return put("punctuation-trim", value, important) ; 
    } 


    public String getLineBreak() { 
    	return get("line-break"); 
    } 
    public DCss2Properties setLineBreak(final String value) throws DOMException { 
    	return put("line-break", value) ; 
    } 
    public DCss2Properties setLineBreak(final String value, final boolean important) throws DOMException { 
    	return put("line-break", value, important) ; 
    } 


    public String getWordBreak() { 
    	return get("word-break"); 
    } 
    public DCss2Properties setWordBreak(final String value) throws DOMException { 
    	return put("word-break", value) ; 
    } 
    public DCss2Properties setWordBreak(final String value, final boolean important) throws DOMException { 
    	return put("word-break", value, important) ; 
    } 


    public String getWordWrap() { 
    	return get("word-wrap"); 
    } 
    public DCss2Properties setWordWrap(final String value) throws DOMException { 
    	return put("word-wrap", value) ; 
    } 
    public DCss2Properties setWordWrap(final String value, final boolean important) throws DOMException { 
    	return put("word-wrap", value, important) ; 
    } 


    public String getTextJustify() { 
    	return get("text-justify"); 
    } 
    public DCss2Properties setTextJustify(final String value) throws DOMException { 
    	return put("text-justify", value) ; 
    } 
    public DCss2Properties setTextJustify(final String value, final boolean important) throws DOMException { 
    	return put("text-justify", value, important) ; 
    } 


    public String getTextJustifyTrim() { 
    	return get("text-justify-trim"); 
    } 
    public DCss2Properties setTextJustifyTrim(final String value) throws DOMException { 
    	return put("text-justify-trim", value) ; 
    } 
    public DCss2Properties setTextJustifyTrim(final String value, final boolean important) throws DOMException { 
    	return put("text-justify-trim", value, important) ; 
    } 


    public String getTextKashida() { 
    	return get("text-kashida"); 
    } 
    public DCss2Properties setTextKashida(final String value) throws DOMException { 
    	return put("text-kashida", value) ; 
    } 
    public DCss2Properties setTextKashida(final String value, final boolean important) throws DOMException { 
    	return put("text-kashida", value, important) ; 
    } 


    public String getPunctuationWrap() { 
    	return get("punctuation-wrap"); 
    } 
    public DCss2Properties setPunctuationWrap(final String value) throws DOMException { 
    	return put("punctuation-wrap", value) ; 
    } 
    public DCss2Properties setPunctuationWrap(final String value, final boolean important) throws DOMException { 
    	return put("punctuation-wrap", value, important) ; 
    } 


    public String getTextCombine() { 
    	return get("text-combine"); 
    } 
    public DCss2Properties setTextCombine(final String value) throws DOMException { 
    	return put("text-combine", value) ; 
    } 
    public DCss2Properties setTextCombine(final String value, final boolean important) throws DOMException { 
    	return put("text-combine", value, important) ; 
    } 


    public String getTextAutospace() { 
    	return get("text-autospace"); 
    } 
    public DCss2Properties setTextAutospace(final String value) throws DOMException { 
    	return put("text-autospace", value) ; 
    } 
    public DCss2Properties setTextAutospace(final String value, final boolean important) throws DOMException { 
    	return put("text-autospace", value, important) ; 
    } 


    public String getTextFit() { 
    	return get("text-fit"); 
    } 
    public DCss2Properties setTextFit(final String value) throws DOMException { 
    	return put("text-fit", value) ; 
    } 
    public DCss2Properties setTextFit(final String value, final boolean important) throws DOMException { 
    	return put("text-fit", value, important) ; 
    } 


    public String getRubyPosition() { 
    	return get("ruby-position"); 
    } 
    public DCss2Properties setRubyPosition(final String value) throws DOMException { 
    	return put("ruby-position", value) ; 
    } 
    public DCss2Properties setRubyPosition(final String value, final boolean important) throws DOMException { 
    	return put("ruby-position", value, important) ; 
    } 


    public String getRubyAlign() { 
    	return get("ruby-align"); 
    } 
    public DCss2Properties setRubyAlign(final String value) throws DOMException { 
    	return put("ruby-align", value) ; 
    } 
    public DCss2Properties setRubyAlign(final String value, final boolean important) throws DOMException { 
    	return put("ruby-align", value, important) ; 
    } 


    public String getRubyOverhang() { 
    	return get("ruby-overhang"); 
    } 
    public DCss2Properties setRubyOverhang(final String value) throws DOMException { 
    	return put("ruby-overhang", value) ; 
    } 
    public DCss2Properties setRubyOverhang(final String value, final boolean important) throws DOMException { 
    	return put("ruby-overhang", value, important) ; 
    } 


    public String getRubySpan() { 
    	return get("ruby-span"); 
    } 
    public DCss2Properties setRubySpan(final String value) throws DOMException { 
    	return put("ruby-span", value) ; 
    } 
    public DCss2Properties setRubySpan(final String value, final boolean important) throws DOMException { 
    	return put("ruby-span", value, important) ; 
    } 


    public String getMarqueeDirection() { 
    	return get("marquee-direction"); 
    } 
    public DCss2Properties setMarqueeDirection(final String value) throws DOMException { 
    	return put("marquee-direction", value) ; 
    } 
    public DCss2Properties setMarqueeDirection(final String value, final boolean important) throws DOMException { 
    	return put("marquee-direction", value, important) ; 
    } 


    public String getMarqueeLoop() { 
    	return get("marquee-loop"); 
    } 
    public DCss2Properties setMarqueeLoop(final String value) throws DOMException { 
    	return put("marquee-loop", value) ; 
    } 
    public DCss2Properties setMarqueeLoop(final String value, final boolean important) throws DOMException { 
    	return put("marquee-loop", value, important) ; 
    } 


    public String getMarqueePlayCount() { 
    	return get("marquee-play-count"); 
    } 
    public DCss2Properties setMarqueePlayCount(final String value) throws DOMException { 
    	return put("marquee-play-count", value) ; 
    } 
    public DCss2Properties setMarqueePlayCount(final String value, final boolean important) throws DOMException { 
    	return put("marquee-play-count", value, important) ; 
    } 


    public String getMarqueeSpeed() { 
    	return get("marquee-speed"); 
    } 
    public DCss2Properties setMarqueeSpeed(final String value) throws DOMException { 
    	return put("marquee-speed", value) ; 
    } 
    public DCss2Properties setMarqueeSpeed(final String value, final boolean important) throws DOMException { 
    	return put("marquee-speed", value, important) ; 
    } 


    public String getMarqueeStyle() { 
    	return get("marquee-style"); 
    } 
    public DCss2Properties setMarqueeStyle(final String value) throws DOMException { 
    	return put("marquee-style", value) ; 
    } 
    public DCss2Properties setMarqueeStyle(final String value, final boolean important) throws DOMException { 
    	return put("marquee-style", value, important) ; 
    } 


    public String getOverflowStyle() { 
    	return get("overflow-style"); 
    } 
    public DCss2Properties setOverflowStyle(final String value) throws DOMException { 
    	return put("overflow-style", value) ; 
    } 
    public DCss2Properties setOverflowStyle(final String value, final boolean important) throws DOMException { 
    	return put("overflow-style", value, important) ; 
    } 


    public String getOverflowX() { 
    	return get("overflow-x"); 
    } 
    public DCss2Properties setOverflowX(final String value) throws DOMException { 
    	return put("overflow-x", value) ; 
    } 
    public DCss2Properties setOverflowX(final String value, final boolean important) throws DOMException { 
    	return put("overflow-x", value, important) ; 
    } 


    public String getOverflowY() { 
    	return get("overflow-y"); 
    } 
    public DCss2Properties setOverflowY(final String value) throws DOMException { 
    	return put("overflow-y", value) ; 
    } 
    public DCss2Properties setOverflowY(final String value, final boolean important) throws DOMException { 
    	return put("overflow-y", value, important) ; 
    } 


    public String getRotation() { 
    	return get("rotation"); 
    } 
    public DCss2Properties setRotation(final String value) throws DOMException { 
    	return put("rotation", value) ; 
    } 
    public DCss2Properties setRotation(final String value, final boolean important) throws DOMException { 
    	return put("rotation", value, important) ; 
    } 


    public String getRotationPoint() { 
    	return get("rotation-point"); 
    } 
    public DCss2Properties setRotationPoint(final String value) throws DOMException { 
    	return put("rotation-point", value) ; 
    } 
    public DCss2Properties setRotationPoint(final String value, final boolean important) throws DOMException { 
    	return put("rotation-point", value, important) ; 
    } 


    public String getVoiceVolume() { 
    	return get("voice-volume"); 
    } 
    public DCss2Properties setVoiceVolume(final String value) throws DOMException { 
    	return put("voice-volume", value) ; 
    } 
    public DCss2Properties setVoiceVolume(final String value, final boolean important) throws DOMException { 
    	return put("voice-volume", value, important) ; 
    } 


    public String getVoiceBalance() { 
    	return get("voice-balance"); 
    } 
    public DCss2Properties setVoiceBalance(final String value) throws DOMException { 
    	return put("voice-balance", value) ; 
    } 
    public DCss2Properties setVoiceBalance(final String value, final boolean important) throws DOMException { 
    	return put("voice-balance", value, important) ; 
    } 


    public String getSpeak() { 
    	return get("speak"); 
    } 
    public DCss2Properties setSpeak(final String value) throws DOMException { 
    	return put("speak", value) ; 
    } 
    public DCss2Properties setSpeak(final String value, final boolean important) throws DOMException { 
    	return put("speak", value, important) ; 
    } 


    public String getPauseBefore() { 
    	return get("pause-before"); 
    } 
    public DCss2Properties setPauseBefore(final String value) throws DOMException { 
    	return put("pause-before", value) ; 
    } 
    public DCss2Properties setPauseBefore(final String value, final boolean important) throws DOMException { 
    	return put("pause-before", value, important) ; 
    } 


    public String getPauseAfter() { 
    	return get("pause-after"); 
    } 
    public DCss2Properties setPauseAfter(final String value) throws DOMException { 
    	return put("pause-after", value) ; 
    } 
    public DCss2Properties setPauseAfter(final String value, final boolean important) throws DOMException { 
    	return put("pause-after", value, important) ; 
    } 


    public String getPause() { 
    	return get("pause"); 
    } 
    public DCss2Properties setPause(final String value) throws DOMException { 
    	return put("pause", value) ; 
    } 
    public DCss2Properties setPause(final String value, final boolean important) throws DOMException { 
    	return put("pause", value, important) ; 
    } 


    public String getRestBefore() { 
    	return get("rest-before"); 
    } 
    public DCss2Properties setRestBefore(final String value) throws DOMException { 
    	return put("rest-before", value) ; 
    } 
    public DCss2Properties setRestBefore(final String value, final boolean important) throws DOMException { 
    	return put("rest-before", value, important) ; 
    } 


    public String getRestAfter() { 
    	return get("rest-after"); 
    } 
    public DCss2Properties setRestAfter(final String value) throws DOMException { 
    	return put("rest-after", value) ; 
    } 
    public DCss2Properties setRestAfter(final String value, final boolean important) throws DOMException { 
    	return put("rest-after", value, important) ; 
    } 


    public String getRest() { 
    	return get("rest"); 
    } 
    public DCss2Properties setRest(final String value) throws DOMException { 
    	return put("rest", value) ; 
    } 
    public DCss2Properties setRest(final String value, final boolean important) throws DOMException { 
    	return put("rest", value, important) ; 
    } 


    public String getCueBefore() { 
    	return get("cue-before"); 
    } 
    public DCss2Properties setCueBefore(final String value) throws DOMException { 
    	return put("cue-before", value) ; 
    } 
    public DCss2Properties setCueBefore(final String value, final boolean important) throws DOMException { 
    	return put("cue-before", value, important) ; 
    } 


    public String getCueAfter() { 
    	return get("cue-after"); 
    } 
    public DCss2Properties setCueAfter(final String value) throws DOMException { 
    	return put("cue-after", value) ; 
    } 
    public DCss2Properties setCueAfter(final String value, final boolean important) throws DOMException { 
    	return put("cue-after", value, important) ; 
    } 


    public String getCue() { 
    	return get("cue"); 
    } 
    public DCss2Properties setCue(final String value) throws DOMException { 
    	return put("cue", value) ; 
    } 
    public DCss2Properties setCue(final String value, final boolean important) throws DOMException { 
    	return put("cue", value, important) ; 
    } 


    public String getMarkBefore() { 
    	return get("mark-before"); 
    } 
    public DCss2Properties setMarkBefore(final String value) throws DOMException { 
    	return put("mark-before", value) ; 
    } 
    public DCss2Properties setMarkBefore(final String value, final boolean important) throws DOMException { 
    	return put("mark-before", value, important) ; 
    } 


    public String getMarkAfter() { 
    	return get("mark-after"); 
    } 
    public DCss2Properties setMarkAfter(final String value) throws DOMException { 
    	return put("mark-after", value) ; 
    } 
    public DCss2Properties setMarkAfter(final String value, final boolean important) throws DOMException { 
    	return put("mark-after", value, important) ; 
    } 


    public String getMark() { 
    	return get("mark"); 
    } 
    public DCss2Properties setMark(final String value) throws DOMException { 
    	return put("mark", value) ; 
    } 
    public DCss2Properties setMark(final String value, final boolean important) throws DOMException { 
    	return put("mark", value, important) ; 
    } 


    public String getVoiceFamily() { 
    	return get("voice-family"); 
    } 
    public DCss2Properties setVoiceFamily(final String value) throws DOMException { 
    	return put("voice-family", value) ; 
    } 
    public DCss2Properties setVoiceFamily(final String value, final boolean important) throws DOMException { 
    	return put("voice-family", value, important) ; 
    } 


    public String getVoiceRate() { 
    	return get("voice-rate"); 
    } 
    public DCss2Properties setVoiceRate(final String value) throws DOMException { 
    	return put("voice-rate", value) ; 
    } 
    public DCss2Properties setVoiceRate(final String value, final boolean important) throws DOMException { 
    	return put("voice-rate", value, important) ; 
    } 


    public String getVoicePitch() { 
    	return get("voice-pitch"); 
    } 
    public DCss2Properties setVoicePitch(final String value) throws DOMException { 
    	return put("voice-pitch", value) ; 
    } 
    public DCss2Properties setVoicePitch(final String value, final boolean important) throws DOMException { 
    	return put("voice-pitch", value, important) ; 
    } 


    public String getVoicePitchRange() { 
    	return get("voice-pitch-range"); 
    } 
    public DCss2Properties setVoicePitchRange(final String value) throws DOMException { 
    	return put("voice-pitch-range", value) ; 
    } 
    public DCss2Properties setVoicePitchRange(final String value, final boolean important) throws DOMException { 
    	return put("voice-pitch-range", value, important) ; 
    } 


    public String getVoiceStress() { 
    	return get("voice-stress"); 
    } 
    public DCss2Properties setVoiceStress(final String value) throws DOMException { 
    	return put("voice-stress", value) ; 
    } 
    public DCss2Properties setVoiceStress(final String value, final boolean important) throws DOMException { 
    	return put("voice-stress", value, important) ; 
    } 


    public String getVoiceDuration() { 
    	return get("voice-duration"); 
    } 
    public DCss2Properties setVoiceDuration(final String value) throws DOMException { 
    	return put("voice-duration", value) ; 
    } 
    public DCss2Properties setVoiceDuration(final String value, final boolean important) throws DOMException { 
    	return put("voice-duration", value, important) ; 
    } 


    public String getPhonemes() { 
    	return get("phonemes"); 
    } 
    public DCss2Properties setPhonemes(final String value) throws DOMException { 
    	return put("phonemes", value) ; 
    } 
    public DCss2Properties setPhonemes(final String value, final boolean important) throws DOMException { 
    	return put("phonemes", value, important) ; 
    } 


    public String getElevation() { 
    	return get("elevation"); 
    } 
    public DCss2Properties setElevation(final String value) throws DOMException { 
    	return put("elevation", value) ; 
    } 
    public DCss2Properties setElevation(final String value, final boolean important) throws DOMException { 
    	return put("elevation", value, important) ; 
    } 


    public String getFit() { 
    	return get("fit"); 
    } 
    public DCss2Properties setFit(final String value) throws DOMException { 
    	return put("fit", value) ; 
    } 
    public DCss2Properties setFit(final String value, final boolean important) throws DOMException { 
    	return put("fit", value, important) ; 
    } 


    public String getFitPosition() { 
    	return get("fit-position"); 
    } 
    public DCss2Properties setFitPosition(final String value) throws DOMException { 
    	return put("fit-position", value) ; 
    } 
    public DCss2Properties setFitPosition(final String value, final boolean important) throws DOMException { 
    	return put("fit-position", value, important) ; 
    } 


    public String getImageOrientation() { 
    	return get("image-orientation"); 
    } 
    public DCss2Properties setImageOrientation(final String value) throws DOMException { 
    	return put("image-orientation", value) ; 
    } 
    public DCss2Properties setImageOrientation(final String value, final boolean important) throws DOMException { 
    	return put("image-orientation", value, important) ; 
    } 


    public String getOrphans() { 
    	return get("orphans"); 
    } 
    public DCss2Properties setOrphans(final String value) throws DOMException { 
    	return put("orphans", value) ; 
    } 
    public DCss2Properties setOrphans(final String value, final boolean important) throws DOMException { 
    	return put("orphans", value, important) ; 
    } 


    public String getPage() { 
    	return get("page"); 
    } 
    public DCss2Properties setPage(final String value) throws DOMException { 
    	return put("page", value) ; 
    } 
    public DCss2Properties setPage(final String value, final boolean important) throws DOMException { 
    	return put("page", value, important) ; 
    } 


    public String getPageBreakAfter() { 
    	return get("page-break-after"); 
    } 
    public DCss2Properties setPageBreakAfter(final String value) throws DOMException { 
    	return put("page-break-after", value) ; 
    } 
    public DCss2Properties setPageBreakAfter(final String value, final boolean important) throws DOMException { 
    	return put("page-break-after", value, important) ; 
    } 


    public String getPageBreakBefore() { 
    	return get("page-break-before"); 
    } 
    public DCss2Properties setPageBreakBefore(final String value) throws DOMException { 
    	return put("page-break-before", value) ; 
    } 
    public DCss2Properties setPageBreakBefore(final String value, final boolean important) throws DOMException { 
    	return put("page-break-before", value, important) ; 
    } 


    public String getPageBreakInside() { 
    	return get("page-break-inside"); 
    } 
    public DCss2Properties setPageBreakInside(final String value) throws DOMException { 
    	return put("page-break-inside", value) ; 
    } 
    public DCss2Properties setPageBreakInside(final String value, final boolean important) throws DOMException { 
    	return put("page-break-inside", value, important) ; 
    } 


    public String getSize() { 
    	return get("size"); 
    } 
    public DCss2Properties setSize(final String value) throws DOMException { 
    	return put("size", value) ; 
    } 
    public DCss2Properties setSize(final String value, final boolean important) throws DOMException { 
    	return put("size", value, important) ; 
    } 


    public String getWindows() { 
    	return get("windows"); 
    } 
    public DCss2Properties setWindows(final String value) throws DOMException { 
    	return put("windows", value) ; 
    } 
    public DCss2Properties setWindows(final String value, final boolean important) throws DOMException { 
    	return put("windows", value, important) ; 
    } 


    public String getAppearance() { 
    	return get("appearance"); 
    } 
    public DCss2Properties setAppearance(final String value) throws DOMException { 
    	return put("appearance", value) ; 
    } 
    public DCss2Properties setAppearance(final String value, final boolean important) throws DOMException { 
    	return put("appearance", value, important) ; 
    } 


    public String getIcon() { 
    	return get("icon"); 
    } 
    public DCss2Properties setIcon(final String value) throws DOMException { 
    	return put("icon", value) ; 
    } 
    public DCss2Properties setIcon(final String value, final boolean important) throws DOMException { 
    	return put("icon", value, important) ; 
    } 


    public String getNavIndex() { 
    	return get("nav-index"); 
    } 
    public DCss2Properties setNavIndex(final String value) throws DOMException { 
    	return put("nav-index", value) ; 
    } 
    public DCss2Properties setNavIndex(final String value, final boolean important) throws DOMException { 
    	return put("nav-index", value, important) ; 
    } 


    public String getNavUp() { 
    	return get("nav-up"); 
    } 
    public DCss2Properties setNavUp(final String value) throws DOMException { 
    	return put("nav-up", value) ; 
    } 
    public DCss2Properties setNavUp(final String value, final boolean important) throws DOMException { 
    	return put("nav-up", value, important) ; 
    } 


    public String getNavRight() { 
    	return get("nav-right"); 
    } 
    public DCss2Properties setNavRight(final String value) throws DOMException { 
    	return put("nav-right", value) ; 
    } 
    public DCss2Properties setNavRight(final String value, final boolean important) throws DOMException { 
    	return put("nav-right", value, important) ; 
    } 


    public String getNavLeft() { 
    	return get("nav-left"); 
    } 
    public DCss2Properties setNavLeft(final String value) throws DOMException { 
    	return put("nav-left", value) ; 
    } 
    public DCss2Properties setNavLeft(final String value, final boolean important) throws DOMException { 
    	return put("nav-left", value, important) ; 
    } 


    public String getNavDown() { 
    	return get("nav-down"); 
    } 
    public DCss2Properties setNavDown(final String value) throws DOMException { 
    	return put("nav-down", value) ; 
    } 
    public DCss2Properties setNavDown(final String value, final boolean important) throws DOMException { 
    	return put("nav-down", value, important) ; 
    } 


    public String getResize() { 
    	return get("resize"); 
    } 
    public DCss2Properties setResize(final String value) throws DOMException { 
    	return put("resize", value) ; 
    } 
    public DCss2Properties setResize(final String value, final boolean important) throws DOMException { 
    	return put("resize", value, important) ; 
    } 


    public String getBorderBreak() { 
    	return get("border-break"); 
    } 
    public DCss2Properties setBorderBreak(final String value) throws DOMException { 
    	return put("border-break", value) ; 
    } 
    public DCss2Properties setBorderBreak(final String value, final boolean important) throws DOMException { 
    	return put("border-break", value, important) ; 
    } 


    public String getBorderTopWidth() { 
    	return get("border-top-width"); 
    } 
    public DCss2Properties setBorderTopWidth(final String value) throws DOMException { 
    	return put("border-top-width", value) ; 
    } 
    public DCss2Properties setBorderTopWidth(final String value, final boolean important) throws DOMException { 
    	return put("border-top-width", value, important) ; 
    } 


    public String getBorderRightWidth() { 
    	return get("border-right-width"); 
    } 
    public DCss2Properties setBorderRightWidth(final String value) throws DOMException { 
    	return put("border-right-width", value) ; 
    } 
    public DCss2Properties setBorderRightWidth(final String value, final boolean important) throws DOMException { 
    	return put("border-right-width", value, important) ; 
    } 


    public String getBorderBottomWidth() { 
    	return get("border-bottom-width"); 
    } 
    public DCss2Properties setBorderBottomWidth(final String value) throws DOMException { 
    	return put("border-bottom-width", value) ; 
    } 
    public DCss2Properties setBorderBottomWidth(final String value, final boolean important) throws DOMException { 
    	return put("border-bottom-width", value, important) ; 
    } 


    public String getBorderLeftWidth() { 
    	return get("border-left-width"); 
    } 
    public DCss2Properties setBorderLeftWidth(final String value) throws DOMException { 
    	return put("border-left-width", value) ; 
    } 
    public DCss2Properties setBorderLeftWidth(final String value, final boolean important) throws DOMException { 
    	return put("border-left-width", value, important) ; 
    } 


    public String getBorderTopColor() { 
    	return get("border-top-color"); 
    } 
    public DCss2Properties setBorderTopColor(final String value) throws DOMException { 
    	return put("border-top-color", value) ; 
    } 
    public DCss2Properties setBorderTopColor(final String value, final boolean important) throws DOMException { 
    	return put("border-top-color", value, important) ; 
    } 


    public String getBorderRightColor() { 
    	return get("border-right-color"); 
    } 
    public DCss2Properties setBorderRightColor(final String value) throws DOMException { 
    	return put("border-right-color", value) ; 
    } 
    public DCss2Properties setBorderRightColor(final String value, final boolean important) throws DOMException { 
    	return put("border-right-color", value, important) ; 
    } 


    public String getBorderBottomColor() { 
    	return get("border-bottom-color"); 
    } 
    public DCss2Properties setBorderBottomColor(final String value) throws DOMException { 
    	return put("border-bottom-color", value) ; 
    } 
    public DCss2Properties setBorderBottomColor(final String value, final boolean important) throws DOMException { 
    	return put("border-bottom-color", value, important) ; 
    } 


    public String getBorderLeftColor() { 
    	return get("border-left-color"); 
    } 
    public DCss2Properties setBorderLeftColor(final String value) throws DOMException { 
    	return put("border-left-color", value) ; 
    } 
    public DCss2Properties setBorderLeftColor(final String value, final boolean important) throws DOMException { 
    	return put("border-left-color", value, important) ; 
    } 


    public String getBorderTopStyle() { 
    	return get("border-top-style"); 
    } 
    public DCss2Properties setBorderTopStyle(final String value) throws DOMException { 
    	return put("border-top-style", value) ; 
    } 
    public DCss2Properties setBorderTopStyle(final String value, final boolean important) throws DOMException { 
    	return put("border-top-style", value, important) ; 
    } 


    public String getBorderRightStyle() { 
    	return get("border-right-style"); 
    } 
    public DCss2Properties setBorderRightStyle(final String value) throws DOMException { 
    	return put("border-right-style", value) ; 
    } 
    public DCss2Properties setBorderRightStyle(final String value, final boolean important) throws DOMException { 
    	return put("border-right-style", value, important) ; 
    } 


    public String getBorderBottomStyle() { 
    	return get("border-bottom-style"); 
    } 
    public DCss2Properties setBorderBottomStyle(final String value) throws DOMException { 
    	return put("border-bottom-style", value) ; 
    } 
    public DCss2Properties setBorderBottomStyle(final String value, final boolean important) throws DOMException { 
    	return put("border-bottom-style", value, important) ; 
    } 


    public String getBorderLeftStyle() { 
    	return get("border-left-style"); 
    } 
    public DCss2Properties setBorderLeftStyle(final String value) throws DOMException { 
    	return put("border-left-style", value) ; 
    } 
    public DCss2Properties setBorderLeftStyle(final String value, final boolean important) throws DOMException { 
    	return put("border-left-style", value, important) ; 
    } 


    public String getBorderTop() { 
    	return get("border-top"); 
    } 
    public DCss2Properties setBorderTop(final String value) throws DOMException { 
    	return put("border-top", value) ; 
    } 
    public DCss2Properties setBorderTop(final String value, final boolean important) throws DOMException { 
    	return put("border-top", value, important) ; 
    } 


    public String getBorderRight() { 
    	return get("border-right"); 
    } 
    public DCss2Properties setBorderRight(final String value) throws DOMException { 
    	return put("border-right", value) ; 
    } 
    public DCss2Properties setBorderRight(final String value, final boolean important) throws DOMException { 
    	return put("border-right", value, important) ; 
    } 


    public String getBorderBottom() { 
    	return get("border-bottom"); 
    } 
    public DCss2Properties setBorderBottom(final String value) throws DOMException { 
    	return put("border-bottom", value) ; 
    } 
    public DCss2Properties setBorderBottom(final String value, final boolean important) throws DOMException { 
    	return put("border-bottom", value, important) ; 
    } 


    public String getBorderLeft() { 
    	return get("border-left"); 
    } 
    public DCss2Properties setBorderLeft(final String value) throws DOMException { 
    	return put("border-left", value) ; 
    } 
    public DCss2Properties setBorderLeft(final String value, final boolean important) throws DOMException { 
    	return put("border-left", value, important) ; 
    } 


    public String getBorderRadius() { 
    	return get("border-radius"); 
    } 
    public DCss2Properties setBorderRadius(final String value) throws DOMException { 
    	return put("border-radius", value) ; 
    } 
    public DCss2Properties setBorderRadius(final String value, final boolean important) throws DOMException { 
    	return put("border-radius", value, important) ; 
    } 


    public String getBorderTopLeftRadius() { 
    	return get("border-top-left-radius"); 
    } 
    public DCss2Properties setBorderTopLeftRadius(final String value) throws DOMException { 
    	return put("border-top-left-radius", value) ; 
    } 
    public DCss2Properties setBorderTopLeftRadius(final String value, final boolean important) throws DOMException { 
    	return put("border-top-left-radius", value, important) ; 
    } 


    public String getBorderTopRightRadius() { 
    	return get("border-top-right-radius"); 
    } 
    public DCss2Properties setBorderTopRightRadius(final String value) throws DOMException { 
    	return put("border-top-right-radius", value) ; 
    } 
    public DCss2Properties setBorderTopRightRadius(final String value, final boolean important) throws DOMException { 
    	return put("border-top-right-radius", value, important) ; 
    } 


    public String getBorderBottomLeftRadius() { 
    	return get("border-bottom-left-radius"); 
    } 
    public DCss2Properties setBorderBottomLeftRadius(final String value) throws DOMException { 
    	return put("border-bottom-left-radius", value) ; 
    } 
    public DCss2Properties setBorderBottomLeftRadius(final String value, final boolean important) throws DOMException { 
    	return put("border-bottom-left-radius", value, important) ; 
    } 


    public String getBorderBottomRightRadius() { 
    	return get("border-bottom-right-radius"); 
    } 
    public DCss2Properties setBorderBottomRightRadius(final String value) throws DOMException { 
    	return put("border-bottom-right-radius", value) ; 
    } 
    public DCss2Properties setBorderBottomRightRadius(final String value, final boolean important) throws DOMException { 
    	return put("border-bottom-right-radius", value, important) ; 
    } 


    public String getBorderImage() { 
    	return get("border-image"); 
    } 
    public DCss2Properties setBorderImage(final String value) throws DOMException { 
    	return put("border-image", value) ; 
    } 
    public DCss2Properties setBorderImage(final String value, final boolean important) throws DOMException { 
    	return put("border-image", value, important) ; 
    } 


    public String getBorderImageOutset() { 
    	return get("border-image-outset"); 
    } 
    public DCss2Properties setBorderImageOutset(final String value) throws DOMException { 
    	return put("border-image-outset", value) ; 
    } 
    public DCss2Properties setBorderImageOutset(final String value, final boolean important) throws DOMException { 
    	return put("border-image-outset", value, important) ; 
    } 


    public String getBorderImageSource() { 
    	return get("border-image-source"); 
    } 
    public DCss2Properties setBorderImageSource(final String value) throws DOMException { 
    	return put("border-image-source", value) ; 
    } 
    public DCss2Properties setBorderImageSource(final String value, final boolean important) throws DOMException { 
    	return put("border-image-source", value, important) ; 
    } 


    public String getBorderImageSlice() { 
    	return get("border-image-slice"); 
    } 
    public DCss2Properties setBorderImageSlice(final String value) throws DOMException { 
    	return put("border-image-slice", value) ; 
    } 
    public DCss2Properties setBorderImageSlice(final String value, final boolean important) throws DOMException { 
    	return put("border-image-slice", value, important) ; 
    } 


    public String getBorderImageWidth() { 
    	return get("border-image-width"); 
    } 
    public DCss2Properties setBorderImageWidth(final String value) throws DOMException { 
    	return put("border-image-width", value) ; 
    } 
    public DCss2Properties setBorderImageWidth(final String value, final boolean important) throws DOMException { 
    	return put("border-image-width", value, important) ; 
    } 


    public String getBorderImageRepeat() { 
    	return get("border-image-repeat"); 
    } 
    public DCss2Properties setBorderImageRepeat(final String value) throws DOMException { 
    	return put("border-image-repeat", value) ; 
    } 
    public DCss2Properties setBorderImageRepeat(final String value, final boolean important) throws DOMException { 
    	return put("border-image-repeat", value, important) ; 
    } 


    public String getBoxDecorationBreak() { 
    	return get("box-decoration-break"); 
    } 
    public DCss2Properties setBoxDecorationBreak(final String value) throws DOMException { 
    	return put("box-decoration-break", value) ; 
    } 
    public DCss2Properties setBoxDecorationBreak(final String value, final boolean important) throws DOMException { 
    	return put("box-decoration-break", value, important) ; 
    } 


    public String getBoxShadow() { 
    	return get("box-shadow"); 
    } 
    public DCss2Properties setBoxShadow(final String value) throws DOMException { 
    	return put("box-shadow", value) ; 
    } 
    public DCss2Properties setBoxShadow(final String value, final boolean important) throws DOMException { 
    	return put("box-shadow", value, important) ; 
    } 


    public String getFontEmphasizeStyle() { 
    	return get("font-emphasize-style"); 
    } 
    public DCss2Properties setFontEmphasizeStyle(final String value) throws DOMException { 
    	return put("font-emphasize-style", value) ; 
    } 
    public DCss2Properties setFontEmphasizeStyle(final String value, final boolean important) throws DOMException { 
    	return put("font-emphasize-style", value, important) ; 
    } 


    public String getFontEmphasizePosition() { 
    	return get("font-emphasize-position"); 
    } 
    public DCss2Properties setFontEmphasizePosition(final String value) throws DOMException { 
    	return put("font-emphasize-position", value) ; 
    } 
    public DCss2Properties setFontEmphasizePosition(final String value, final boolean important) throws DOMException { 
    	return put("font-emphasize-position", value, important) ; 
    } 


    public String getFontEmphasize() { 
    	return get("font-emphasize"); 
    } 
    public DCss2Properties setFontEmphasize(final String value) throws DOMException { 
    	return put("font-emphasize", value) ; 
    } 
    public DCss2Properties setFontEmphasize(final String value, final boolean important) throws DOMException { 
    	return put("font-emphasize", value, important) ; 
    } 


    public String getFontSizeAdjust() { 
    	return get("font-size-adjust"); 
    } 
    public DCss2Properties setFontSizeAdjust(final String value) throws DOMException { 
    	return put("font-size-adjust", value) ; 
    } 
    public DCss2Properties setFontSizeAdjust(final String value, final boolean important) throws DOMException { 
    	return put("font-size-adjust", value, important) ; 
    } 


    public String getFontStretch() { 
    	return get("font-stretch"); 
    } 
    public DCss2Properties setFontStretch(final String value) throws DOMException { 
    	return put("font-stretch", value) ; 
    } 
    public DCss2Properties setFontStretch(final String value, final boolean important) throws DOMException { 
    	return put("font-stretch", value, important) ; 
    } 


    public String getColumns() { 
    	return get("columns"); 
    } 
    public DCss2Properties setColumns(final String value) throws DOMException { 
    	return put("columns", value) ; 
    } 
    public DCss2Properties setColumns(final String value, final boolean important) throws DOMException { 
    	return put("columns", value, important) ; 
    } 


    public String getColumnSpan() { 
    	return get("column-span"); 
    } 
    public DCss2Properties setColumnSpan(final String value) throws DOMException { 
    	return put("column-span", value) ; 
    } 
    public DCss2Properties setColumnSpan(final String value, final boolean important) throws DOMException { 
    	return put("column-span", value, important) ; 
    } 


    public String getColumnWidth() { 
    	return get("column-width"); 
    } 
    public DCss2Properties setColumnWidth(final String value) throws DOMException { 
    	return put("column-width", value) ; 
    } 
    public DCss2Properties setColumnWidth(final String value, final boolean important) throws DOMException { 
    	return put("column-width", value, important) ; 
    } 


    public String getColumnCount() { 
    	return get("column-count"); 
    } 
    public DCss2Properties setColumnCount(final String value) throws DOMException { 
    	return put("column-count", value) ; 
    } 
    public DCss2Properties setColumnCount(final String value, final boolean important) throws DOMException { 
    	return put("column-count", value, important) ; 
    } 


    public String getColumnFill() { 
    	return get("column-fill"); 
    } 
    public DCss2Properties setColumnFill(final String value) throws DOMException { 
    	return put("column-fill", value) ; 
    } 
    public DCss2Properties setColumnFill(final String value, final boolean important) throws DOMException { 
    	return put("column-fill", value, important) ; 
    } 


    public String getColumnGap() { 
    	return get("column-gap"); 
    } 
    public DCss2Properties setColumnGap(final String value) throws DOMException { 
    	return put("column-gap", value) ; 
    } 
    public DCss2Properties setColumnGap(final String value, final boolean important) throws DOMException { 
    	return put("column-gap", value, important) ; 
    } 


    public String getColumnRule() { 
    	return get("column-rule"); 
    } 
    public DCss2Properties setColumnRule(final String value) throws DOMException { 
    	return put("column-rule", value) ; 
    } 
    public DCss2Properties setColumnRule(final String value, final boolean important) throws DOMException { 
    	return put("column-rule", value, important) ; 
    } 


    public String getColumnRuleColor() { 
    	return get("column-rule-color"); 
    } 
    public DCss2Properties setColumnRuleColor(final String value) throws DOMException { 
    	return put("column-rule-color", value) ; 
    } 
    public DCss2Properties setColumnRuleColor(final String value, final boolean important) throws DOMException { 
    	return put("column-rule-color", value, important) ; 
    } 


    public String getColumnRuleStyle() { 
    	return get("column-rule-style"); 
    } 
    public DCss2Properties setColumnRuleStyle(final String value) throws DOMException { 
    	return put("column-rule-style", value) ; 
    } 
    public DCss2Properties setColumnRuleStyle(final String value, final boolean important) throws DOMException { 
    	return put("column-rule-style", value, important) ; 
    } 


    public String getColumnRuleWidth() { 
    	return get("column-rule-width"); 
    } 
    public DCss2Properties setColumnRuleWidth(final String value) throws DOMException { 
    	return put("column-rule-width", value) ; 
    } 
    public DCss2Properties setColumnRuleWidth(final String value, final boolean important) throws DOMException { 
    	return put("column-rule-width", value, important) ; 
    } 


    public String getBoxAlign() { 
    	return get("box-align"); 
    } 
    public DCss2Properties setBoxAlign(final String value) throws DOMException { 
    	return put("box-align", value) ; 
    } 
    public DCss2Properties setBoxAlign(final String value, final boolean important) throws DOMException { 
    	return put("box-align", value, important) ; 
    } 


    public String getBoxDirection() { 
    	return get("box-direction"); 
    } 
    public DCss2Properties setBoxDirection(final String value) throws DOMException { 
    	return put("box-direction", value) ; 
    } 
    public DCss2Properties setBoxDirection(final String value, final boolean important) throws DOMException { 
    	return put("box-direction", value, important) ; 
    } 


    public String getBoxFlex() { 
    	return get("box-flex"); 
    } 
    public DCss2Properties setBoxFlex(final String value) throws DOMException { 
    	return put("box-flex", value) ; 
    } 
    public DCss2Properties setBoxFlex(final String value, final boolean important) throws DOMException { 
    	return put("box-flex", value, important) ; 
    } 


    public String getBoxFlexGroup() { 
    	return get("box-flex-group"); 
    } 
    public DCss2Properties setBoxFlexGroup(final String value) throws DOMException { 
    	return put("box-flex-group", value) ; 
    } 
    public DCss2Properties setBoxFlexGroup(final String value, final boolean important) throws DOMException { 
    	return put("box-flex-group", value, important) ; 
    } 


    public String getBoxLines() { 
    	return get("box-lines"); 
    } 
    public DCss2Properties setBoxLines(final String value) throws DOMException { 
    	return put("box-lines", value) ; 
    } 
    public DCss2Properties setBoxLines(final String value, final boolean important) throws DOMException { 
    	return put("box-lines", value, important) ; 
    } 


    public String getBoxOrient() { 
    	return get("box-orient"); 
    } 
    public DCss2Properties setBoxOrient(final String value) throws DOMException { 
    	return put("box-orient", value) ; 
    } 
    public DCss2Properties setBoxOrient(final String value, final boolean important) throws DOMException { 
    	return put("box-orient", value, important) ; 
    } 


    public String getBoxPack() { 
    	return get("box-pack"); 
    } 
    public DCss2Properties setBoxPack(final String value) throws DOMException { 
    	return put("box-pack", value) ; 
    } 
    public DCss2Properties setBoxPack(final String value, final boolean important) throws DOMException { 
    	return put("box-pack", value, important) ; 
    } 


    public String getBoxSizing() { 
    	return get("box-sizing"); 
    } 
    public DCss2Properties setBoxSizing(final String value) throws DOMException { 
    	return put("box-sizing", value) ; 
    } 
    public DCss2Properties setBoxSizing(final String value, final boolean important) throws DOMException { 
    	return put("box-sizing", value, important) ; 
    } 


    public String getTabSide() { 
    	return get("tab-side"); 
    } 
    public DCss2Properties setTabSide(final String value) throws DOMException { 
    	return put("tab-side", value) ; 
    } 
    public DCss2Properties setTabSide(final String value, final boolean important) throws DOMException { 
    	return put("tab-side", value, important) ; 
    } 


    public String getAnimation() { 
    	return get("animation"); 
    } 
    public DCss2Properties setAnimation(final String value) throws DOMException { 
    	return put("animation", value) ; 
    } 
    public DCss2Properties setAnimation(final String value, final boolean important) throws DOMException { 
    	return put("animation", value, important) ; 
    } 


    public String getAnimationDelay() { 
    	return get("animation-delay"); 
    } 
    public DCss2Properties setAnimationDelay(final String value) throws DOMException { 
    	return put("animation-delay", value) ; 
    } 
    public DCss2Properties setAnimationDelay(final String value, final boolean important) throws DOMException { 
    	return put("animation-delay", value, important) ; 
    } 


    public String getAnimationDirection() { 
    	return get("animation-direction"); 
    } 
    public DCss2Properties setAnimationDirection(final String value) throws DOMException { 
    	return put("animation-direction", value) ; 
    } 
    public DCss2Properties setAnimationDirection(final String value, final boolean important) throws DOMException { 
    	return put("animation-direction", value, important) ; 
    } 


    public String getAnimationDuration() { 
    	return get("animation-duration"); 
    } 
    public DCss2Properties setAnimationDuration(final String value) throws DOMException { 
    	return put("animation-duration", value) ; 
    } 
    public DCss2Properties setAnimationDuration(final String value, final boolean important) throws DOMException { 
    	return put("animation-duration", value, important) ; 
    } 


    public String getAnimationIterationCount() { 
    	return get("animation-iteration-count"); 
    } 
    public DCss2Properties setAnimationIterationCount(final String value) throws DOMException { 
    	return put("animation-iteration-count", value) ; 
    } 
    public DCss2Properties setAnimationIterationCount(final String value, final boolean important) throws DOMException { 
    	return put("animation-iteration-count", value, important) ; 
    } 


    public String getAnimationName() { 
    	return get("animation-name"); 
    } 
    public DCss2Properties setAnimationName(final String value) throws DOMException { 
    	return put("animation-name", value) ; 
    } 
    public DCss2Properties setAnimationName(final String value, final boolean important) throws DOMException { 
    	return put("animation-name", value, important) ; 
    } 


    public String getAnimationPlayState() { 
    	return get("animation-play-state"); 
    } 
    public DCss2Properties setAnimationPlayState(final String value) throws DOMException { 
    	return put("animation-play-state", value) ; 
    } 
    public DCss2Properties setAnimationPlayState(final String value, final boolean important) throws DOMException { 
    	return put("animation-play-state", value, important) ; 
    } 


    public String getAnimationTimingFunction() { 
    	return get("animation-timing-function"); 
    } 
    public DCss2Properties setAnimationTimingFunction(final String value) throws DOMException { 
    	return put("animation-timing-function", value) ; 
    } 
    public DCss2Properties setAnimationTimingFunction(final String value, final boolean important) throws DOMException { 
    	return put("animation-timing-function", value, important) ; 
    } 


    public String getTransition() { 
    	return get("transition"); 
    } 
    public DCss2Properties setTransition(final String value) throws DOMException { 
    	return put("transition", value) ; 
    } 
    public DCss2Properties setTransition(final String value, final boolean important) throws DOMException { 
    	return put("transition", value, important) ; 
    } 


    public String getTransitionDelay() { 
    	return get("transition-delay"); 
    } 
    public DCss2Properties setTransitionDelay(final String value) throws DOMException { 
    	return put("transition-delay", value) ; 
    } 
    public DCss2Properties setTransitionDelay(final String value, final boolean important) throws DOMException { 
    	return put("transition-delay", value, important) ; 
    } 


    public String getTransitionProperty() { 
    	return get("transition-property"); 
    } 
    public DCss2Properties setTransitionProperty(final String value) throws DOMException { 
    	return put("transition-property", value) ; 
    } 
    public DCss2Properties setTransitionProperty(final String value, final boolean important) throws DOMException { 
    	return put("transition-property", value, important) ; 
    } 


    public String getTransitionTimingFunction() { 
    	return get("transition-timing-function"); 
    } 
    public DCss2Properties setTransitionTimingFunction(final String value) throws DOMException { 
    	return put("transition-timing-function", value) ; 
    } 
    public DCss2Properties setTransitionTimingFunction(final String value, final boolean important) throws DOMException { 
    	return put("transition-timing-function", value, important) ; 
    } 


    public String getGridColumns() { 
    	return get("grid-columns"); 
    } 
    public DCss2Properties setGridColumns(final String value) throws DOMException { 
    	return put("grid-columns", value) ; 
    } 
    public DCss2Properties setGridColumns(final String value, final boolean important) throws DOMException { 
    	return put("grid-columns", value, important) ; 
    } 


    public String getGridRows() { 
    	return get("grid-rows"); 
    } 
    public DCss2Properties setGridRows(final String value) throws DOMException { 
    	return put("grid-rows", value) ; 
    } 
    public DCss2Properties setGridRows(final String value, final boolean important) throws DOMException { 
    	return put("grid-rows", value, important) ; 
    } 


    public String getBackfaceVisibility() { 
    	return get("backface-visibility"); 
    } 
    public DCss2Properties setBackfaceVisibility(final String value) throws DOMException { 
    	return put("backface-visibility", value) ; 
    } 
    public DCss2Properties setBackfaceVisibility(final String value, final boolean important) throws DOMException { 
    	return put("backface-visibility", value, important) ; 
    } 


    public String getPerspective() { 
    	return get("perspective"); 
    } 
    public DCss2Properties setPerspective(final String value) throws DOMException { 
    	return put("perspective", value) ; 
    } 
    public DCss2Properties setPerspective(final String value, final boolean important) throws DOMException { 
    	return put("perspective", value, important) ; 
    } 


    public String getPerspectiveOrigin() { 
    	return get("perspective-origin"); 
    } 
    public DCss2Properties setPerspectiveOrigin(final String value) throws DOMException { 
    	return put("perspective-origin", value) ; 
    } 
    public DCss2Properties setPerspectiveOrigin(final String value, final boolean important) throws DOMException { 
    	return put("perspective-origin", value, important) ; 
    } 


    public String getTransform() { 
    	return get("transform"); 
    } 
    public DCss2Properties setTransform(final String value) throws DOMException { 
    	return put("transform", value) ; 
    } 
    public DCss2Properties setTransform(final String value, final boolean important) throws DOMException { 
    	return put("transform", value, important) ; 
    } 


    public String getTransformOrigin() { 
    	return get("transform-origin"); 
    } 
    public DCss2Properties setTransformOrigin(final String value) throws DOMException { 
    	return put("transform-origin", value) ; 
    } 
    public DCss2Properties setTransformOrigin(final String value, final boolean important) throws DOMException { 
    	return put("transform-origin", value, important) ; 
    } 


    public String getTransformStyle() { 
    	return get("transform-style"); 
    } 
    public DCss2Properties setTransformStyle(final String value) throws DOMException { 
    	return put("transform-style", value) ; 
    } 
    public DCss2Properties setTransformStyle(final String value, final boolean important) throws DOMException { 
    	return put("transform-style", value, important) ; 
    } 


    public String getBookmarkLabel() { 
    	return get("bookmark-label"); 
    } 
    public DCss2Properties setBookmarkLabel(final String value) throws DOMException { 
    	return put("bookmark-label", value) ; 
    } 
    public DCss2Properties setBookmarkLabel(final String value, final boolean important) throws DOMException { 
    	return put("bookmark-label", value, important) ; 
    } 


    public String getBookmarkLevel() { 
    	return get("bookmark-level"); 
    } 
    public DCss2Properties setBookmarkLevel(final String value) throws DOMException { 
    	return put("bookmark-level", value) ; 
    } 
    public DCss2Properties setBookmarkLevel(final String value, final boolean important) throws DOMException { 
    	return put("bookmark-level", value, important) ; 
    } 


    public String getBookmarkTarget() { 
    	return get("bookmark-target"); 
    } 
    public DCss2Properties setBookmarkTarget(final String value) throws DOMException { 
    	return put("bookmark-target", value) ; 
    } 
    public DCss2Properties setBookmarkTarget(final String value, final boolean important) throws DOMException { 
    	return put("bookmark-target", value, important) ; 
    } 


    public String getBorderLength() { 
    	return get("border-length"); 
    } 
    public DCss2Properties setBorderLength(final String value) throws DOMException { 
    	return put("border-length", value) ; 
    } 
    public DCss2Properties setBorderLength(final String value, final boolean important) throws DOMException { 
    	return put("border-length", value, important) ; 
    } 


    public String getContent() { 
    	return get("content"); 
    } 
    public DCss2Properties setContent(final String value) throws DOMException { 
    	return put("content", value) ; 
    } 
    public DCss2Properties setContent(final String value, final boolean important) throws DOMException { 
    	return put("content", value, important) ; 
    } 


    public String getCounterReset() { 
    	return get("counter-reset"); 
    } 
    public DCss2Properties setCounterReset(final String value) throws DOMException { 
    	return put("counter-reset", value) ; 
    } 
    public DCss2Properties setCounterReset(final String value, final boolean important) throws DOMException { 
    	return put("counter-reset", value, important) ; 
    } 


    public String getCounterIncrement() { 
    	return get("counter-increment"); 
    } 
    public DCss2Properties setCounterIncrement(final String value) throws DOMException { 
    	return put("counter-increment", value) ; 
    } 
    public DCss2Properties setCounterIncrement(final String value, final boolean important) throws DOMException { 
    	return put("counter-increment", value, important) ; 
    } 


    public String getCrop() { 
    	return get("crop"); 
    } 
    public DCss2Properties setCrop(final String value) throws DOMException { 
    	return put("crop", value) ; 
    } 
    public DCss2Properties setCrop(final String value, final boolean important) throws DOMException { 
    	return put("crop", value, important) ; 
    } 


    public String getFloatOffset() { 
    	return get("float-offset"); 
    } 
    public DCss2Properties setFloatOffset(final String value) throws DOMException { 
    	return put("float-offset", value) ; 
    } 
    public DCss2Properties setFloatOffset(final String value, final boolean important) throws DOMException { 
    	return put("float-offset", value, important) ; 
    } 


    public String getHyphenateAfter() { 
    	return get("hyphenate-after"); 
    } 
    public DCss2Properties setHyphenateAfter(final String value) throws DOMException { 
    	return put("hyphenate-after", value) ; 
    } 
    public DCss2Properties setHyphenateAfter(final String value, final boolean important) throws DOMException { 
    	return put("hyphenate-after", value, important) ; 
    } 


    public String getHyphenateBefore() { 
    	return get("hyphenate-before"); 
    } 
    public DCss2Properties setHyphenateBefore(final String value) throws DOMException { 
    	return put("hyphenate-before", value) ; 
    } 
    public DCss2Properties setHyphenateBefore(final String value, final boolean important) throws DOMException { 
    	return put("hyphenate-before", value, important) ; 
    } 


    public String getHyphenateCharacter() { 
    	return get("hyphenate-character"); 
    } 
    public DCss2Properties setHyphenateCharacter(final String value) throws DOMException { 
    	return put("hyphenate-character", value) ; 
    } 
    public DCss2Properties setHyphenateCharacter(final String value, final boolean important) throws DOMException { 
    	return put("hyphenate-character", value, important) ; 
    } 


    public String getHyphenateLines() { 
    	return get("hyphenate-lines"); 
    } 
    public DCss2Properties setHyphenateLines(final String value) throws DOMException { 
    	return put("hyphenate-lines", value) ; 
    } 
    public DCss2Properties setHyphenateLines(final String value, final boolean important) throws DOMException { 
    	return put("hyphenate-lines", value, important) ; 
    } 


    public String getHyphenateResource() { 
    	return get("hyphenate-resource"); 
    } 
    public DCss2Properties setHyphenateResource(final String value) throws DOMException { 
    	return put("hyphenate-resource", value) ; 
    } 
    public DCss2Properties setHyphenateResource(final String value, final boolean important) throws DOMException { 
    	return put("hyphenate-resource", value, important) ; 
    } 


    public String getHyphens() { 
    	return get("hyphens"); 
    } 
    public DCss2Properties setHyphens(final String value) throws DOMException { 
    	return put("hyphens", value) ; 
    } 
    public DCss2Properties setHyphens(final String value, final boolean important) throws DOMException { 
    	return put("hyphens", value, important) ; 
    } 


    public String getImageResolution() { 
    	return get("image-resolution"); 
    } 
    public DCss2Properties setImageResolution(final String value) throws DOMException { 
    	return put("image-resolution", value) ; 
    } 
    public DCss2Properties setImageResolution(final String value, final boolean important) throws DOMException { 
    	return put("image-resolution", value, important) ; 
    } 


    public String getMarks() { 
    	return get("marks"); 
    } 
    public DCss2Properties setMarks(final String value) throws DOMException { 
    	return put("marks", value) ; 
    } 
    public DCss2Properties setMarks(final String value, final boolean important) throws DOMException { 
    	return put("marks", value, important) ; 
    } 


    public String getMoveTo() { 
    	return get("move-to"); 
    } 
    public DCss2Properties setMoveTo(final String value) throws DOMException { 
    	return put("move-to", value) ; 
    } 
    public DCss2Properties setMoveTo(final String value, final boolean important) throws DOMException { 
    	return put("move-to", value, important) ; 
    } 


    public String getPagePolicy() { 
    	return get("page-policy"); 
    } 
    public DCss2Properties setPagePolicy(final String value) throws DOMException { 
    	return put("page-policy", value) ; 
    } 
    public DCss2Properties setPagePolicy(final String value, final boolean important) throws DOMException { 
    	return put("page-policy", value, important) ; 
    } 


    public String getStringSet() { 
    	return get("string-set"); 
    } 
    public DCss2Properties setStringSet(final String value) throws DOMException { 
    	return put("string-set", value) ; 
    } 
    public DCss2Properties setStringSet(final String value, final boolean important) throws DOMException { 
    	return put("string-set", value, important) ; 
    } 


    public String getTextReplace() { 
    	return get("text-replace"); 
    } 
    public DCss2Properties setTextReplace(final String value) throws DOMException { 
    	return put("text-replace", value) ; 
    } 
    public DCss2Properties setTextReplace(final String value, final boolean important) throws DOMException { 
    	return put("text-replace", value, important) ; 
    } 


    public String getAlignmentAdjust() { 
    	return get("alignment-adjust"); 
    } 
    public DCss2Properties setAlignmentAdjust(final String value) throws DOMException { 
    	return put("alignment-adjust", value) ; 
    } 
    public DCss2Properties setAlignmentAdjust(final String value, final boolean important) throws DOMException { 
    	return put("alignment-adjust", value, important) ; 
    } 


    public String getAlignmentBaseline() { 
    	return get("alignment-baseline"); 
    } 
    public DCss2Properties setAlignmentBaseline(final String value) throws DOMException { 
    	return put("alignment-baseline", value) ; 
    } 
    public DCss2Properties setAlignmentBaseline(final String value, final boolean important) throws DOMException { 
    	return put("alignment-baseline", value, important) ; 
    } 


    public String getBaselineShift() { 
    	return get("baseline-shift"); 
    } 
    public DCss2Properties setBaselineShift(final String value) throws DOMException { 
    	return put("baseline-shift", value) ; 
    } 
    public DCss2Properties setBaselineShift(final String value, final boolean important) throws DOMException { 
    	return put("baseline-shift", value, important) ; 
    } 


    public String getDominantBaseline() { 
    	return get("dominant-baseline"); 
    } 
    public DCss2Properties setDominantBaseline(final String value) throws DOMException { 
    	return put("dominant-baseline", value) ; 
    } 
    public DCss2Properties setDominantBaseline(final String value, final boolean important) throws DOMException { 
    	return put("dominant-baseline", value, important) ; 
    } 


    public String getDropInitialAfterAlign() { 
    	return get("drop-initial-after-align"); 
    } 
    public DCss2Properties setDropInitialAfterAlign(final String value) throws DOMException { 
    	return put("drop-initial-after-align", value) ; 
    } 
    public DCss2Properties setDropInitialAfterAlign(final String value, final boolean important) throws DOMException { 
    	return put("drop-initial-after-align", value, important) ; 
    } 


    public String getDropInitialAfterAdjust() { 
    	return get("drop-initial-after-adjust"); 
    } 
    public DCss2Properties setDropInitialAfterAdjust(final String value) throws DOMException { 
    	return put("drop-initial-after-adjust", value) ; 
    } 
    public DCss2Properties setDropInitialAfterAdjust(final String value, final boolean important) throws DOMException { 
    	return put("drop-initial-after-adjust", value, important) ; 
    } 


    public String getDropInitialBeforeAlign() { 
    	return get("drop-initial-before-align"); 
    } 
    public DCss2Properties setDropInitialBeforeAlign(final String value) throws DOMException { 
    	return put("drop-initial-before-align", value) ; 
    } 
    public DCss2Properties setDropInitialBeforeAlign(final String value, final boolean important) throws DOMException { 
    	return put("drop-initial-before-align", value, important) ; 
    } 


    public String getDropInitialBeforeAdjust() { 
    	return get("drop-initial-before-adjust"); 
    } 
    public DCss2Properties setDropInitialBeforeAdjust(final String value) throws DOMException { 
    	return put("drop-initial-before-adjust", value) ; 
    } 
    public DCss2Properties setDropInitialBeforeAdjust(final String value, final boolean important) throws DOMException { 
    	return put("drop-initial-before-adjust", value, important) ; 
    } 


    public String getDropInitialValue() { 
    	return get("drop-initial-value"); 
    } 
    public DCss2Properties setDropInitialValue(final String value) throws DOMException { 
    	return put("drop-initial-value", value) ; 
    } 
    public DCss2Properties setDropInitialValue(final String value, final boolean important) throws DOMException { 
    	return put("drop-initial-value", value, important) ; 
    } 


    public String getDropInitialSize() { 
    	return get("drop-initial-size"); 
    } 
    public DCss2Properties setDropInitialSize(final String value) throws DOMException { 
    	return put("drop-initial-size", value) ; 
    } 
    public DCss2Properties setDropInitialSize(final String value, final boolean important) throws DOMException { 
    	return put("drop-initial-size", value, important) ; 
    } 


    public String getInlineBoxAlign() { 
    	return get("inline-box-align"); 
    } 
    public DCss2Properties setInlineBoxAlign(final String value) throws DOMException { 
    	return put("inline-box-align", value) ; 
    } 
    public DCss2Properties setInlineBoxAlign(final String value, final boolean important) throws DOMException { 
    	return put("inline-box-align", value, important) ; 
    } 


    public String getLineHeight() { 
    	return get("line-height"); 
    } 
    public DCss2Properties setLineHeight(final String value) throws DOMException { 
    	return put("line-height", value) ; 
    } 
    public DCss2Properties setLineHeight(final String value, final boolean important) throws DOMException { 
    	return put("line-height", value, important) ; 
    } 


    public String getLineStacking() { 
    	return get("line-stacking"); 
    } 
    public DCss2Properties setLineStacking(final String value) throws DOMException { 
    	return put("line-stacking", value) ; 
    } 
    public DCss2Properties setLineStacking(final String value, final boolean important) throws DOMException { 
    	return put("line-stacking", value, important) ; 
    } 


    public String getLineStackingStrategry() { 
    	return get("line-stacking-strategry"); 
    } 
    public DCss2Properties setLineStackingStrategry(final String value) throws DOMException { 
    	return put("line-stacking-strategry", value) ; 
    } 
    public DCss2Properties setLineStackingStrategry(final String value, final boolean important) throws DOMException { 
    	return put("line-stacking-strategry", value, important) ; 
    } 


    public String getLineStackingRuby() { 
    	return get("line-stacking-ruby"); 
    } 
    public DCss2Properties setLineStackingRuby(final String value) throws DOMException { 
    	return put("line-stacking-ruby", value) ; 
    } 
    public DCss2Properties setLineStackingRuby(final String value, final boolean important) throws DOMException { 
    	return put("line-stacking-ruby", value, important) ; 
    } 


    public String getLineStackingShift() { 
    	return get("line-stacking-shift"); 
    } 
    public DCss2Properties setLineStackingShift(final String value) throws DOMException { 
    	return put("line-stacking-shift", value) ; 
    } 
    public DCss2Properties setLineStackingShift(final String value, final boolean important) throws DOMException { 
    	return put("line-stacking-shift", value, important) ; 
    } 


    public String getTextHeight() { 
    	return get("text-height"); 
    } 
    public DCss2Properties setTextHeight(final String value) throws DOMException { 
    	return put("text-height", value) ; 
    } 
    public DCss2Properties setTextHeight(final String value, final boolean important) throws DOMException { 
    	return put("text-height", value, important) ; 
    } 


    public String getVerticalAlign() { 
    	return get("vertical-align"); 
    } 
    public DCss2Properties setVerticalAlign(final String value) throws DOMException { 
    	return put("vertical-align", value) ; 
    } 
    public DCss2Properties setVerticalAlign(final String value, final boolean important) throws DOMException { 
    	return put("vertical-align", value, important) ; 
    } 


    public String getTarget() { 
    	return get("target"); 
    } 
    public DCss2Properties setTarget(final String value) throws DOMException { 
    	return put("target", value) ; 
    } 
    public DCss2Properties setTarget(final String value, final boolean important) throws DOMException { 
    	return put("target", value, important) ; 
    } 


    public String getTargetName() { 
    	return get("target-name"); 
    } 
    public DCss2Properties setTargetName(final String value) throws DOMException { 
    	return put("target-name", value) ; 
    } 
    public DCss2Properties setTargetName(final String value, final boolean important) throws DOMException { 
    	return put("target-name", value, important) ; 
    } 


    public String getTargetNew() { 
    	return get("target-new"); 
    } 
    public DCss2Properties setTargetNew(final String value) throws DOMException { 
    	return put("target-new", value) ; 
    } 
    public DCss2Properties setTargetNew(final String value, final boolean important) throws DOMException { 
    	return put("target-new", value, important) ; 
    } 


    public String getTargetPosition() { 
    	return get("target-position"); 
    } 
    public DCss2Properties setTargetPosition(final String value) throws DOMException { 
    	return put("target-position", value) ; 
    } 
    public DCss2Properties setTargetPosition(final String value, final boolean important) throws DOMException { 
    	return put("target-position", value, important) ; 
    } 


    public String getClip() { 
    	return get("clip"); 
    } 
    public DCss2Properties setClip(final String value) throws DOMException { 
    	return put("clip", value) ; 
    } 
    public DCss2Properties setClip(final String value, final boolean important) throws DOMException { 
    	return put("clip", value, important) ; 
    } 

    public String getTextOverflow() { 
    	return get("text-overflow"); 
    } 
    public DCss2Properties setTextOverflow(final String value) throws DOMException { 
    	return put("text-overflow", value) ; 
    } 
    public DCss2Properties setTextOverflow(final String value, final boolean important) throws DOMException { 
    	return put("text-overflow", value, important) ; 
    } 


    public String getLinearGradient() { 
    	return get("linear-gradient"); 
    } 
    public DCss2Properties setLinearGradient(final String value) throws DOMException { 
    	return put("linear-gradient", value) ; 
    } 
    public DCss2Properties setLinearGradient(final String value, final boolean important) throws DOMException { 
    	return put("linear-gradient", value, important) ; 
    } 


    public String getRadialGradient() { 
    	return get("radial-gradient"); 
    } 
    public DCss2Properties setRadialGradient(final String value) throws DOMException { 
    	return put("radial-gradient", value) ; 
    } 
    public DCss2Properties setRadialGradient(final String value, final boolean important) throws DOMException { 
    	return put("radial-gradient", value, important) ; 
    } 
    
    // Start Microsoft extensions
    public String getMsAccelerator() { 
    	return get("-ms-accelerator"); 
    } 
    public DCss2Properties setMsAccelerator(final String value) throws DOMException { 
    	return put("-ms-accelerator", value) ; 
    } 
    public DCss2Properties setMsAccelerator(final String value, final boolean important) throws DOMException { 
    	return put("-ms-accelerator", value, important) ; 
    } 


    public String getMsBackgroundPositionX() { 
    	return get("-ms-background-position-x"); 
    } 
    public DCss2Properties setMsBackgroundPositionX(final String value) throws DOMException { 
    	return put("-ms-background-position-x", value) ; 
    } 
    public DCss2Properties setMsBackgroundPositionX(final String value, final boolean important) throws DOMException { 
    	return put("-ms-background-position-x", value, important) ; 
    } 


    public String getMsBackgroundPositionY() { 
    	return get("-ms-background-position-y"); 
    } 
    public DCss2Properties setMsBackgroundPositionY(final String value) throws DOMException { 
    	return put("-ms-background-position-y", value) ; 
    } 
    public DCss2Properties setMsBackgroundPositionY(final String value, final boolean important) throws DOMException { 
    	return put("-ms-background-position-y", value, important) ; 
    } 


    public String getMsBehavior() { 
    	return get("-ms-behavior"); 
    } 
    public DCss2Properties setMsBehavior(final String value) throws DOMException { 
    	return put("-ms-behavior", value) ; 
    } 
    public DCss2Properties setMsBehavior(final String value, final boolean important) throws DOMException { 
    	return put("-ms-behavior", value, important) ; 
    } 


    public String getMsBlockProgression() { 
    	return get("-ms-block-progression"); 
    } 
    public DCss2Properties setMsBlockProgression(final String value) throws DOMException { 
    	return put("-ms-block-progression", value) ; 
    } 
    public DCss2Properties setMsBlockProgression(final String value, final boolean important) throws DOMException { 
    	return put("-ms-block-progression", value, important) ; 
    } 


    public String getMsFilter() { 
    	return get("-ms-filter"); 
    } 
    public DCss2Properties setMsFilter(final String value) throws DOMException { 
    	return put("-ms-filter", value) ; 
    } 
    public DCss2Properties setMsFilter(final String value, final boolean important) throws DOMException { 
    	return put("-ms-filter", value, important) ; 
    } 


    public String getMsImeMode() { 
    	return get("-ms-ime-mode"); 
    } 
    public DCss2Properties setMsImeMode(final String value) throws DOMException { 
    	return put("-ms-ime-mode", value) ; 
    } 
    public DCss2Properties setMsImeMode(final String value, final boolean important) throws DOMException { 
    	return put("-ms-ime-mode", value, important) ; 
    } 


    public String getMsLayoutFlow() { 
    	return get("-ms-layout-flow"); 
    } 
    public DCss2Properties setMsLayoutFlow(final String value) throws DOMException { 
    	return put("-ms-layout-flow", value) ; 
    } 
    public DCss2Properties setMsLayoutFlow(final String value, final boolean important) throws DOMException { 
    	return put("-ms-layout-flow", value, important) ; 
    } 


    public String getMsLayoutGrid() { 
    	return get("-ms-layout-grid"); 
    } 
    public DCss2Properties setMsLayoutGrid(final String value) throws DOMException { 
    	return put("-ms-layout-grid", value) ; 
    } 
    public DCss2Properties setMsLayoutGrid(final String value, final boolean important) throws DOMException { 
    	return put("-ms-layout-grid", value, important) ; 
    } 


    public String getMsLayoutGridChar() { 
    	return get("-ms-layout-grid-char"); 
    } 
    public DCss2Properties setMsLayoutGridChar(final String value) throws DOMException { 
    	return put("-ms-layout-grid-char", value) ; 
    } 
    public DCss2Properties setMsLayoutGridChar(final String value, final boolean important) throws DOMException { 
    	return put("-ms-layout-grid-char", value, important) ; 
    } 


    public String getMsLayoutGridLine() { 
    	return get("-ms-layout-grid-line"); 
    } 
    public DCss2Properties setMsLayoutGridLine(final String value) throws DOMException { 
    	return put("-ms-layout-grid-line", value) ; 
    } 
    public DCss2Properties setMsLayoutGridLine(final String value, final boolean important) throws DOMException { 
    	return put("-ms-layout-grid-line", value, important) ; 
    } 


    public String getMsLayoutGridMode() { 
    	return get("-ms-layout-grid-mode"); 
    } 
    public DCss2Properties setMsLayoutGridMode(final String value) throws DOMException { 
    	return put("-ms-layout-grid-mode", value) ; 
    } 
    public DCss2Properties setMsLayoutGridMode(final String value, final boolean important) throws DOMException { 
    	return put("-ms-layout-grid-mode", value, important) ; 
    } 


    public String getMsLayoutGridType() { 
    	return get("-ms-layout-grid-type"); 
    } 
    public DCss2Properties setMsLayoutGridType(final String value) throws DOMException { 
    	return put("-ms-layout-grid-type", value) ; 
    } 
    public DCss2Properties setMsLayoutGridType(final String value, final boolean important) throws DOMException { 
    	return put("-ms-layout-grid-type", value, important) ; 
    } 


    public String getMsLineBreak() { 
    	return get("-ms-line-break"); 
    } 
    public DCss2Properties setMsLineBreak(final String value) throws DOMException { 
    	return put("-ms-line-break", value) ; 
    } 
    public DCss2Properties setMsLineBreak(final String value, final boolean important) throws DOMException { 
    	return put("-ms-line-break", value, important) ; 
    } 


    public String getMsInterpolationMode() { 
    	return get("-ms-interpolation-mode"); 
    } 
    public DCss2Properties setMsInterpolationMode(final String value) throws DOMException { 
    	return put("-ms-interpolation-mode", value) ; 
    } 
    public DCss2Properties setMsInterpolationMode(final String value, final boolean important) throws DOMException { 
    	return put("-ms-interpolation-mode", value, important) ; 
    } 


    public String getMsOverflowX() { 
    	return get("-ms-overflow-x"); 
    } 
    public DCss2Properties setMsOverflowX(final String value) throws DOMException { 
    	return put("-ms-overflow-x", value) ; 
    } 
    public DCss2Properties setMsOverflowX(final String value, final boolean important) throws DOMException { 
    	return put("-ms-overflow-x", value, important) ; 
    } 


    public String getMsOverflowY() { 
    	return get("-ms-overflow-y"); 
    } 
    public DCss2Properties setMsOverflowY(final String value) throws DOMException { 
    	return put("-ms-overflow-y", value) ; 
    } 
    public DCss2Properties setMsOverflowY(final String value, final boolean important) throws DOMException { 
    	return put("-ms-overflow-y", value, important) ; 
    } 


    public String getMsScrollbar3dlightColor() { 
    	return get("-ms-scrollbar-3dlight-color"); 
    } 
    public DCss2Properties setMsScrollbar3dlightColor(final String value) throws DOMException { 
    	return put("-ms-scrollbar-3dlight-color", value) ; 
    } 
    public DCss2Properties setMsScrollbar3dlightColor(final String value, final boolean important) throws DOMException { 
    	return put("-ms-scrollbar-3dlight-color", value, important) ; 
    } 


    public String getMsScrollbarArrowColor() { 
    	return get("-ms-scrollbar-arrow-color"); 
    } 
    public DCss2Properties setMsScrollbarArrowColor(final String value) throws DOMException { 
    	return put("-ms-scrollbar-arrow-color", value) ; 
    } 
    public DCss2Properties setMsScrollbarArrowColor(final String value, final boolean important) throws DOMException { 
    	return put("-ms-scrollbar-arrow-color", value, important) ; 
    } 


    public String getMsScrollbarBaseColor() { 
    	return get("-ms-scrollbar-base-color"); 
    } 
    public DCss2Properties setMsScrollbarBaseColor(final String value) throws DOMException { 
    	return put("-ms-scrollbar-base-color", value) ; 
    } 
    public DCss2Properties setMsScrollbarBaseColor(final String value, final boolean important) throws DOMException { 
    	return put("-ms-scrollbar-base-color", value, important) ; 
    } 


    public String getMsScrollbarDarkShadowColor() { 
    	return get("-ms-scrollbar-dark-shadow-color"); 
    } 
    public DCss2Properties setMsScrollbarDarkShadowColor(final String value) throws DOMException { 
    	return put("-ms-scrollbar-dark-shadow-color", value) ; 
    } 
    public DCss2Properties setMsScrollbarDarkShadowColor(final String value, final boolean important) throws DOMException { 
    	return put("-ms-scrollbar-dark-shadow-color", value, important) ; 
    } 


    public String getMsScrollbarFaceColor() { 
    	return get("-ms-scrollbar-face-color"); 
    } 
    public DCss2Properties setMsScrollbarFaceColor(final String value) throws DOMException { 
    	return put("-ms-scrollbar-face-color", value) ; 
    } 
    public DCss2Properties setMsScrollbarFaceColor(final String value, final boolean important) throws DOMException { 
    	return put("-ms-scrollbar-face-color", value, important) ; 
    } 


    public String getMsScrollbarHighlightColor() { 
    	return get("-ms-scrollbar-highlight-color"); 
    } 
    public DCss2Properties setMsScrollbarHighlightColor(final String value) throws DOMException { 
    	return put("-ms-scrollbar-highlight-color", value) ; 
    } 
    public DCss2Properties setMsScrollbarHighlightColor(final String value, final boolean important) throws DOMException { 
    	return put("-ms-scrollbar-highlight-color", value, important) ; 
    } 


    public String getMsScrollbarShadowColor() { 
    	return get("-ms-scrollbar-shadow-color"); 
    } 
    public DCss2Properties setMsScrollbarShadowColor(final String value) throws DOMException { 
    	return put("-ms-scrollbar-shadow-color", value) ; 
    } 
    public DCss2Properties setMsScrollbarShadowColor(final String value, final boolean important) throws DOMException { 
    	return put("-ms-scrollbar-shadow-color", value, important) ; 
    } 


    public String getMsScrollbarTrackColor() { 
    	return get("-ms-scrollbar-track-color"); 
    } 
    public DCss2Properties setMsScrollbarTrackColor(final String value) throws DOMException { 
    	return put("-ms-scrollbar-track-color", value) ; 
    } 
    public DCss2Properties setMsScrollbarTrackColor(final String value, final boolean important) throws DOMException { 
    	return put("-ms-scrollbar-track-color", value, important) ; 
    } 


    public String getMsTextAlignLast() { 
    	return get("-ms-text-align-last"); 
    } 
    public DCss2Properties setMsTextAlignLast(final String value) throws DOMException { 
    	return put("-ms-text-align-last", value) ; 
    } 
    public DCss2Properties setMsTextAlignLast(final String value, final boolean important) throws DOMException { 
    	return put("-ms-text-align-last", value, important) ; 
    } 


    public String getMsTextAutospace() { 
    	return get("-ms-text-autospace"); 
    } 
    public DCss2Properties setMsTextAutospace(final String value) throws DOMException { 
    	return put("-ms-text-autospace", value) ; 
    } 
    public DCss2Properties setMsTextAutospace(final String value, final boolean important) throws DOMException { 
    	return put("-ms-text-autospace", value, important) ; 
    } 


    public String getMsTextJustify() { 
    	return get("-ms-text-justify"); 
    } 
    public DCss2Properties setMsTextJustify(final String value) throws DOMException { 
    	return put("-ms-text-justify", value) ; 
    } 
    public DCss2Properties setMsTextJustify(final String value, final boolean important) throws DOMException { 
    	return put("-ms-text-justify", value, important) ; 
    } 


    public String getMsTextKashidaSpace() { 
    	return get("-ms-text-kashida-space"); 
    } 
    public DCss2Properties setMsTextKashidaSpace(final String value) throws DOMException { 
    	return put("-ms-text-kashida-space", value) ; 
    } 
    public DCss2Properties setMsTextKashidaSpace(final String value, final boolean important) throws DOMException { 
    	return put("-ms-text-kashida-space", value, important) ; 
    } 


    public String getMsTextOverflow() { 
    	return get("-ms-text-overflow"); 
    } 
    public DCss2Properties setMsTextOverflow(final String value) throws DOMException { 
    	return put("-ms-text-overflow", value) ; 
    } 
    public DCss2Properties setMsTextOverflow(final String value, final boolean important) throws DOMException { 
    	return put("-ms-text-overflow", value, important) ; 
    } 


    public String getMsTextUnderlinePosition() { 
    	return get("-ms-text-underline-position"); 
    } 
    public DCss2Properties setMsTextUnderlinePosition(final String value) throws DOMException { 
    	return put("-ms-text-underline-position", value) ; 
    } 
    public DCss2Properties setMsTextUnderlinePosition(final String value, final boolean important) throws DOMException { 
    	return put("-ms-text-underline-position", value, important) ; 
    } 


    public String getMsWordBreak() { 
    	return get("-ms-word-break"); 
    } 
    public DCss2Properties setMsWordBreak(final String value) throws DOMException { 
    	return put("-ms-word-break", value) ; 
    } 
    public DCss2Properties setMsWordBreak(final String value, final boolean important) throws DOMException { 
    	return put("-ms-word-break", value, important) ; 
    } 


    public String getMsWordWrap() { 
    	return get("-ms-word-wrap"); 
    } 
    public DCss2Properties setMsWordWrap(final String value) throws DOMException { 
    	return put("-ms-word-wrap", value) ; 
    } 
    public DCss2Properties setMsWordWrap(final String value, final boolean important) throws DOMException { 
    	return put("-ms-word-wrap", value, important) ; 
    } 


    public String getMsWritingMode() { 
    	return get("-ms-writing-mode"); 
    } 
    public DCss2Properties setMsWritingMode(final String value) throws DOMException { 
    	return put("-ms-writing-mode", value) ; 
    } 
    public DCss2Properties setMsWritingMode(final String value, final boolean important) throws DOMException { 
    	return put("-ms-writing-mode", value, important) ; 
    } 


    public String getMsZoom() { 
    	return get("-ms-zoom"); 
    } 
    public DCss2Properties setMsZoom(final String value) throws DOMException { 
    	return put("-ms-zoom", value) ; 
    } 
    public DCss2Properties setMsZoom(final String value, final boolean important) throws DOMException { 
    	return put("-ms-zoom", value, important) ; 
    } 
    // End Microsoft extensions
    
    // Start Webkit extensions
    public String getWebkitAnimation() { 
    	return get("-webkit-animation"); 
    } 
    public DCss2Properties setWebkitAnimation(final String value) throws DOMException { 
    	return put("-webkit-animation", value) ; 
    } 
    public DCss2Properties setWebkitAnimation(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-animation", value, important) ; 
    } 

    public String getWebkitCanvas() { 
    	return get("-webkit-canvas"); 
    } 
    public DCss2Properties setWebkitCanvas(final String value) throws DOMException { 
    	return put("-webkit-canvas", value) ; 
    } 
    public DCss2Properties setWebkitCanvas(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-canvas", value, important) ; 
    } 
    
    public String getWebkitAnimationDelay() { 
    	return get("-webkit-animation-delay"); 
    } 
    public DCss2Properties setWebkitAnimationDelay(final String value) throws DOMException { 
    	return put("-webkit-animation-delay", value) ; 
    } 
    public DCss2Properties setWebkitAnimationDelay(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-animation-delay", value, important) ; 
    } 


    public String getWebkitAnimationDirection() { 
    	return get("-webkit-animation-direction"); 
    } 
    public DCss2Properties setWebkitAnimationDirection(final String value) throws DOMException { 
    	return put("-webkit-animation-direction", value) ; 
    } 
    public DCss2Properties setWebkitAnimationDirection(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-animation-direction", value, important) ; 
    } 


    public String getWebkitAnimationDuration() { 
    	return get("-webkit-animation-duration"); 
    } 
    public DCss2Properties setWebkitAnimationDuration(final String value) throws DOMException { 
    	return put("-webkit-animation-duration", value) ; 
    } 
    public DCss2Properties setWebkitAnimationDuration(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-animation-duration", value, important) ; 
    } 


    public String getWebkitAnimationFillMode() { 
    	return get("-webkit-animation-fill-mode"); 
    } 
    public DCss2Properties setWebkitAnimationFillMode(final String value) throws DOMException { 
    	return put("-webkit-animation-fill-mode", value) ; 
    } 
    public DCss2Properties setWebkitAnimationFillMode(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-animation-fill-mode", value, important) ; 
    } 


    public String getWebkitAnimationIterationCount() { 
    	return get("-webkit-animation-iteration-count"); 
    } 
    public DCss2Properties setWebkitAnimationIterationCount(final String value) throws DOMException { 
    	return put("-webkit-animation-iteration-count", value) ; 
    } 
    public DCss2Properties setWebkitAnimationIterationCount(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-animation-iteration-count", value, important) ; 
    } 


    public String getWebkitAnimationName() { 
    	return get("-webkit-animation-name"); 
    } 
    public DCss2Properties setWebkitAnimationName(final String value) throws DOMException { 
    	return put("-webkit-animation-name", value) ; 
    } 
    public DCss2Properties setWebkitAnimationName(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-animation-name", value, important) ; 
    } 


    public String getWebkitAnimationPlayState() { 
    	return get("-webkit-animation-play-state"); 
    } 
    public DCss2Properties setWebkitAnimationPlayState(final String value) throws DOMException { 
    	return put("-webkit-animation-play-state", value) ; 
    } 
    public DCss2Properties setWebkitAnimationPlayState(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-animation-play-state", value, important) ; 
    } 


    public String getWebkitAnimationTimingFunction() { 
    	return get("-webkit-animation-timing-function"); 
    } 
    public DCss2Properties setWebkitAnimationTimingFunction(final String value) throws DOMException { 
    	return put("-webkit-animation-timing-function", value) ; 
    } 
    public DCss2Properties setWebkitAnimationTimingFunction(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-animation-timing-function", value, important) ; 
    } 


    public String getWebkitAppearance() { 
    	return get("-webkit-appearance"); 
    } 
    public DCss2Properties setWebkitAppearance(final String value) throws DOMException { 
    	return put("-webkit-appearance", value) ; 
    } 
    public DCss2Properties setWebkitAppearance(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-appearance", value, important) ; 
    } 


    public String getWebkitBackfaceVisibility() { 
    	return get("-webkit-backface-visibility"); 
    } 
    public DCss2Properties setWebkitBackfaceVisibility(final String value) throws DOMException { 
    	return put("-webkit-backface-visibility", value) ; 
    } 
    public DCss2Properties setWebkitBackfaceVisibility(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-backface-visibility", value, important) ; 
    } 


    public String getWebkitBackgroundClip() { 
    	return get("-webkit-background-clip"); 
    } 
    public DCss2Properties setWebkitBackgroundClip(final String value) throws DOMException { 
    	return put("-webkit-background-clip", value) ; 
    } 
    public DCss2Properties setWebkitBackgroundClip(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-background-clip", value, important) ; 
    } 


    public String getWebkitBackgroundComposite() { 
    	return get("-webkit-background-composite"); 
    } 
    public DCss2Properties setWebkitBackgroundComposite(final String value) throws DOMException { 
    	return put("-webkit-background-composite", value) ; 
    } 
    public DCss2Properties setWebkitBackgroundComposite(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-background-composite", value, important) ; 
    } 


    public String getWebkitBackgroundOrigin() { 
    	return get("-webkit-background-origin"); 
    } 
    public DCss2Properties setWebkitBackgroundOrigin(final String value) throws DOMException { 
    	return put("-webkit-background-origin", value) ; 
    } 
    public DCss2Properties setWebkitBackgroundOrigin(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-background-origin", value, important) ; 
    } 


    public String getWebkitBackgroundSize() { 
    	return get("-webkit-background-size"); 
    } 
    public DCss2Properties setWebkitBackgroundSize(final String value) throws DOMException { 
    	return put("-webkit-background-size", value) ; 
    } 
    public DCss2Properties setWebkitBackgroundSize(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-background-size", value, important) ; 
    } 


    public String getWebkitBinding() { 
    	return get("-webkit-binding"); 
    } 
    public DCss2Properties setWebkitBinding(final String value) throws DOMException { 
    	return put("-webkit-binding", value) ; 
    } 
    public DCss2Properties setWebkitBinding(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-binding", value, important) ; 
    } 


    public String getWebkitBorderFit() { 
    	return get("-webkit-border-fit"); 
    } 
    public DCss2Properties setWebkitBorderFit(final String value) throws DOMException { 
    	return put("-webkit-border-fit", value) ; 
    } 
    public DCss2Properties setWebkitBorderFit(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-border-fit", value, important) ; 
    } 


    public String getWebkitBorderHorizontalSpacing() { 
    	return get("-webkit-border-horizontal-spacing"); 
    } 
    public DCss2Properties setWebkitBorderHorizontalSpacing(final String value) throws DOMException { 
    	return put("-webkit-border-horizontal-spacing", value) ; 
    } 
    public DCss2Properties setWebkitBorderHorizontalSpacing(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-border-horizontal-spacing", value, important) ; 
    } 


    public String getWebkitBorderImage() { 
    	return get("-webkit-border-image"); 
    } 
    public DCss2Properties setWebkitBorderImage(final String value) throws DOMException { 
    	return put("-webkit-border-image", value) ; 
    } 
    public DCss2Properties setWebkitBorderImage(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-border-image", value, important) ; 
    } 


    public String getWebkitBorderRadius() { 
    	return get("-webkit-border-radius"); 
    } 
    public DCss2Properties setWebkitBorderRadius(final String value) throws DOMException { 
    	return put("-webkit-border-radius", value) ; 
    } 
    public DCss2Properties setWebkitBorderRadius(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-border-radius", value, important) ; 
    } 


    public String getWebkitBorderVerticalSpacing() { 
    	return get("-webkit-border-vertical-spacing"); 
    } 
    public DCss2Properties setWebkitBorderVerticalSpacing(final String value) throws DOMException { 
    	return put("-webkit-border-vertical-spacing", value) ; 
    } 
    public DCss2Properties setWebkitBorderVerticalSpacing(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-border-vertical-spacing", value, important) ; 
    } 


    public String getWebkitBoxAlign() { 
    	return get("-webkit-box-align"); 
    } 
    public DCss2Properties setWebkitBoxAlign(final String value) throws DOMException { 
    	return put("-webkit-box-align", value) ; 
    } 
    public DCss2Properties setWebkitBoxAlign(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-box-align", value, important) ; 
    } 


    public String getWebkitBoxDirection() { 
    	return get("-webkit-box-direction"); 
    } 
    public DCss2Properties setWebkitBoxDirection(final String value) throws DOMException { 
    	return put("-webkit-box-direction", value) ; 
    } 
    public DCss2Properties setWebkitBoxDirection(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-box-direction", value, important) ; 
    } 


    public String getWebkitBoxFlex() { 
    	return get("-webkit-box-flex"); 
    } 
    public DCss2Properties setWebkitBoxFlex(final String value) throws DOMException { 
    	return put("-webkit-box-flex", value) ; 
    } 
    public DCss2Properties setWebkitBoxFlex(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-box-flex", value, important) ; 
    } 


    public String getWebkitBoxFlexGroup() { 
    	return get("-webkit-box-flex-group"); 
    } 
    public DCss2Properties setWebkitBoxFlexGroup(final String value) throws DOMException { 
    	return put("-webkit-box-flex-group", value) ; 
    } 
    public DCss2Properties setWebkitBoxFlexGroup(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-box-flex-group", value, important) ; 
    } 


    public String getWebkitBoxLines() { 
    	return get("-webkit-box-lines"); 
    } 
    public DCss2Properties setWebkitBoxLines(final String value) throws DOMException { 
    	return put("-webkit-box-lines", value) ; 
    } 
    public DCss2Properties setWebkitBoxLines(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-box-lines", value, important) ; 
    } 


    public String getWebkitBoxOrdinalGroup() { 
    	return get("-webkit-box-ordinal-group"); 
    } 
    public DCss2Properties setWebkitBoxOrdinalGroup(final String value) throws DOMException { 
    	return put("-webkit-box-ordinal-group", value) ; 
    } 
    public DCss2Properties setWebkitBoxOrdinalGroup(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-box-ordinal-group", value, important) ; 
    } 


    public String getWebkitBoxOrient() { 
    	return get("-webkit-box-orient"); 
    } 
    public DCss2Properties setWebkitBoxOrient(final String value) throws DOMException { 
    	return put("-webkit-box-orient", value) ; 
    } 
    public DCss2Properties setWebkitBoxOrient(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-box-orient", value, important) ; 
    } 


    public String getWebkitBoxPack() { 
    	return get("-webkit-box-pack"); 
    } 
    public DCss2Properties setWebkitBoxPack(final String value) throws DOMException { 
    	return put("-webkit-box-pack", value) ; 
    } 
    public DCss2Properties setWebkitBoxPack(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-box-pack", value, important) ; 
    } 


    public String getWebkitBoxReflect() { 
    	return get("-webkit-box-reflect"); 
    } 
    public DCss2Properties setWebkitBoxReflect(final String value) throws DOMException { 
    	return put("-webkit-box-reflect", value) ; 
    } 
    public DCss2Properties setWebkitBoxReflect(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-box-reflect", value, important) ; 
    } 


    public String getWebkitBoxShadow() { 
    	return get("-webkit-box-shadow"); 
    } 
    public DCss2Properties setWebkitBoxShadow(final String value) throws DOMException { 
    	return put("-webkit-box-shadow", value) ; 
    } 
    public DCss2Properties setWebkitBoxShadow(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-box-shadow", value, important) ; 
    } 


    public String getWebkitBoxSizing() { 
    	return get("-webkit-box-sizing"); 
    } 
    public DCss2Properties setWebkitBoxSizing(final String value) throws DOMException { 
    	return put("-webkit-box-sizing", value) ; 
    } 
    public DCss2Properties setWebkitBoxSizing(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-box-sizing", value, important) ; 
    } 


    public String getWebkitColorCorrection() { 
    	return get("-webkit-color-correction"); 
    } 
    public DCss2Properties setWebkitColorCorrection(final String value) throws DOMException { 
    	return put("-webkit-color-correction", value) ; 
    } 
    public DCss2Properties setWebkitColorCorrection(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-color-correction", value, important) ; 
    } 


    public String getWebkitColumnBreakAfter() { 
    	return get("-webkit-column-break-after"); 
    } 
    public DCss2Properties setWebkitColumnBreakAfter(final String value) throws DOMException { 
    	return put("-webkit-column-break-after", value) ; 
    } 
    public DCss2Properties setWebkitColumnBreakAfter(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-column-break-after", value, important) ; 
    } 


    public String getWebkitColumnBreakBefore() { 
    	return get("-webkit-column-break-before"); 
    } 
    public DCss2Properties setWebkitColumnBreakBefore(final String value) throws DOMException { 
    	return put("-webkit-column-break-before", value) ; 
    } 
    public DCss2Properties setWebkitColumnBreakBefore(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-column-break-before", value, important) ; 
    } 


    public String getWebkitColumnBreakInside() { 
    	return get("-webkit-column-break-inside"); 
    } 
    public DCss2Properties setWebkitColumnBreakInside(final String value) throws DOMException { 
    	return put("-webkit-column-break-inside", value) ; 
    } 
    public DCss2Properties setWebkitColumnBreakInside(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-column-break-inside", value, important) ; 
    } 


    public String getWebkitColumnCount() { 
    	return get("-webkit-column-count"); 
    } 
    public DCss2Properties setWebkitColumnCount(final String value) throws DOMException { 
    	return put("-webkit-column-count", value) ; 
    } 
    public DCss2Properties setWebkitColumnCount(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-column-count", value, important) ; 
    } 


    public String getWebkitColumnGap() { 
    	return get("-webkit-column-gap"); 
    } 
    public DCss2Properties setWebkitColumnGap(final String value) throws DOMException { 
    	return put("-webkit-column-gap", value) ; 
    } 
    public DCss2Properties setWebkitColumnGap(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-column-gap", value, important) ; 
    } 


    public String getWebkitColumnRule() { 
    	return get("-webkit-column-rule"); 
    } 
    public DCss2Properties setWebkitColumnRule(final String value) throws DOMException { 
    	return put("-webkit-column-rule", value) ; 
    } 
    public DCss2Properties setWebkitColumnRule(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-column-rule", value, important) ; 
    } 


    public String getWebkitColumnRuleColor() { 
    	return get("-webkit-column-rule-color"); 
    } 
    public DCss2Properties setWebkitColumnRuleColor(final String value) throws DOMException { 
    	return put("-webkit-column-rule-color", value) ; 
    } 
    public DCss2Properties setWebkitColumnRuleColor(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-column-rule-color", value, important) ; 
    } 


    public String getWebkitColumnRuleStyle() { 
    	return get("-webkit-column-rule-style"); 
    } 
    public DCss2Properties setWebkitColumnRuleStyle(final String value) throws DOMException { 
    	return put("-webkit-column-rule-style", value) ; 
    } 
    public DCss2Properties setWebkitColumnRuleStyle(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-column-rule-style", value, important) ; 
    } 


    public String getWebkitColumnRuleWidth() { 
    	return get("-webkit-column-rule-width"); 
    } 
    public DCss2Properties setWebkitColumnRuleWidth(final String value) throws DOMException { 
    	return put("-webkit-column-rule-width", value) ; 
    } 
    public DCss2Properties setWebkitColumnRuleWidth(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-column-rule-width", value, important) ; 
    } 


    public String getWebkitColumnWidth() { 
    	return get("-webkit-column-width"); 
    } 
    public DCss2Properties setWebkitColumnWidth(final String value) throws DOMException { 
    	return put("-webkit-column-width", value) ; 
    } 
    public DCss2Properties setWebkitColumnWidth(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-column-width", value, important) ; 
    } 


    public String getWebkitColumns() { 
    	return get("-webkit-columns"); 
    } 
    public DCss2Properties setWebkitColumns(final String value) throws DOMException { 
    	return put("-webkit-columns", value) ; 
    } 
    public DCss2Properties setWebkitColumns(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-columns", value, important) ; 
    } 


    public String getWebkitFontSizeDelta() { 
    	return get("-webkit-font-size-delta"); 
    } 
    public DCss2Properties setWebkitFontSizeDelta(final String value) throws DOMException { 
    	return put("-webkit-font-size-delta", value) ; 
    } 
    public DCss2Properties setWebkitFontSizeDelta(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-font-size-delta", value, important) ; 
    } 


    public String getWebkitFontSmoothing() { 
    	return get("-webkit-font-smoothing"); 
    } 
    public DCss2Properties setWebkitFontSmoothing(final String value) throws DOMException { 
    	return put("-webkit-font-smoothing", value) ; 
    } 
    public DCss2Properties setWebkitFontSmoothing(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-font-smoothing", value, important) ; 
    } 


    public String getWebkitHighlight() { 
    	return get("-webkit-highlight"); 
    } 
    public DCss2Properties setWebkitHighlight(final String value) throws DOMException { 
    	return put("-webkit-highlight", value) ; 
    } 
    public DCss2Properties setWebkitHighlight(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-highlight", value, important) ; 
    } 


    public String getWebkitLineBreak() { 
    	return get("-webkit-line-break"); 
    } 
    public DCss2Properties setWebkitLineBreak(final String value) throws DOMException { 
    	return put("-webkit-line-break", value) ; 
    } 
    public DCss2Properties setWebkitLineBreak(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-line-break", value, important) ; 
    } 


    public String getWebkitLineClamp() { 
    	return get("-webkit-line-clamp"); 
    } 
    public DCss2Properties setWebkitLineClamp(final String value) throws DOMException { 
    	return put("-webkit-line-clamp", value) ; 
    } 
    public DCss2Properties setWebkitLineClamp(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-line-clamp", value, important) ; 
    } 


    public String getWebkitMarginBottomCollapse() { 
    	return get("-webkit-margin-bottom-collapse"); 
    } 
    public DCss2Properties setWebkitMarginBottomCollapse(final String value) throws DOMException { 
    	return put("-webkit-margin-bottom-collapse", value) ; 
    } 
    public DCss2Properties setWebkitMarginBottomCollapse(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-margin-bottom-collapse", value, important) ; 
    } 


    public String getWebkitMarginCollapse() { 
    	return get("-webkit-margin-collapse"); 
    } 
    public DCss2Properties setWebkitMarginCollapse(final String value) throws DOMException { 
    	return put("-webkit-margin-collapse", value) ; 
    } 
    public DCss2Properties setWebkitMarginCollapse(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-margin-collapse", value, important) ; 
    } 


    public String getWebkitMarginEnd() { 
    	return get("-webkit-margin-end"); 
    } 
    public DCss2Properties setWebkitMarginEnd(final String value) throws DOMException { 
    	return put("-webkit-margin-end", value) ; 
    } 
    public DCss2Properties setWebkitMarginEnd(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-margin-end", value, important) ; 
    } 


    public String getWebkitMarginStart() { 
    	return get("-webkit-margin-start"); 
    } 
    public DCss2Properties setWebkitMarginStart(final String value) throws DOMException { 
    	return put("-webkit-margin-start", value) ; 
    } 
    public DCss2Properties setWebkitMarginStart(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-margin-start", value, important) ; 
    } 


    public String getWebkitMarginTopCollapse() { 
    	return get("-webkit-margin-top-collapse"); 
    } 
    public DCss2Properties setWebkitMarginTopCollapse(final String value) throws DOMException { 
    	return put("-webkit-margin-top-collapse", value) ; 
    } 
    public DCss2Properties setWebkitMarginTopCollapse(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-margin-top-collapse", value, important) ; 
    } 


    public String getWebkitMarquee() { 
    	return get("-webkit-marquee"); 
    } 
    public DCss2Properties setWebkitMarquee(final String value) throws DOMException { 
    	return put("-webkit-marquee", value) ; 
    } 
    public DCss2Properties setWebkitMarquee(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-marquee", value, important) ; 
    } 


    public String getWebkitMarqueeDirection() { 
    	return get("-webkit-marquee-direction"); 
    } 
    public DCss2Properties setWebkitMarqueeDirection(final String value) throws DOMException { 
    	return put("-webkit-marquee-direction", value) ; 
    } 
    public DCss2Properties setWebkitMarqueeDirection(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-marquee-direction", value, important) ; 
    } 


    public String getWebkitMarqueeIncrement() { 
    	return get("-webkit-marquee-increment"); 
    } 
    public DCss2Properties setWebkitMarqueeIncrement(final String value) throws DOMException { 
    	return put("-webkit-marquee-increment", value) ; 
    } 
    public DCss2Properties setWebkitMarqueeIncrement(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-marquee-increment", value, important) ; 
    } 


    public String getWebkitMarqueeRepetition() { 
    	return get("-webkit-marquee-repetition"); 
    } 
    public DCss2Properties setWebkitMarqueeRepetition(final String value) throws DOMException { 
    	return put("-webkit-marquee-repetition", value) ; 
    } 
    public DCss2Properties setWebkitMarqueeRepetition(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-marquee-repetition", value, important) ; 
    } 


    public String getWebkitMarqueeSpeed() { 
    	return get("-webkit-marquee-speed"); 
    } 
    public DCss2Properties setWebkitMarqueeSpeed(final String value) throws DOMException { 
    	return put("-webkit-marquee-speed", value) ; 
    } 
    public DCss2Properties setWebkitMarqueeSpeed(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-marquee-speed", value, important) ; 
    } 


    public String getWebkitMarqueeStyle() { 
    	return get("-webkit-marquee-style"); 
    } 
    public DCss2Properties setWebkitMarqueeStyle(final String value) throws DOMException { 
    	return put("-webkit-marquee-style", value) ; 
    } 
    public DCss2Properties setWebkitMarqueeStyle(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-marquee-style", value, important) ; 
    } 


    public String getWebkitMask() { 
    	return get("-webkit-mask"); 
    } 
    public DCss2Properties setWebkitMask(final String value) throws DOMException { 
    	return put("-webkit-mask", value) ; 
    } 
    public DCss2Properties setWebkitMask(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask", value, important) ; 
    } 


    public String getWebkitMaskAttachment() { 
    	return get("-webkit-mask-attachment"); 
    } 
    public DCss2Properties setWebkitMaskAttachment(final String value) throws DOMException { 
    	return put("-webkit-mask-attachment", value) ; 
    } 
    public DCss2Properties setWebkitMaskAttachment(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-attachment", value, important) ; 
    } 


    public String getWebkitMaskBoxImage() { 
    	return get("-webkit-mask-box-image"); 
    } 
    public DCss2Properties setWebkitMaskBoxImage(final String value) throws DOMException { 
    	return put("-webkit-mask-box-image", value) ; 
    } 
    public DCss2Properties setWebkitMaskBoxImage(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-box-image", value, important) ; 
    } 


    public String getWebkitMaskClip() { 
    	return get("-webkit-mask-clip"); 
    } 
    public DCss2Properties setWebkitMaskClip(final String value) throws DOMException { 
    	return put("-webkit-mask-clip", value) ; 
    } 
    public DCss2Properties setWebkitMaskClip(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-clip", value, important) ; 
    } 


    public String getWebkitMaskComposite() { 
    	return get("-webkit-mask-composite"); 
    } 
    public DCss2Properties setWebkitMaskComposite(final String value) throws DOMException { 
    	return put("-webkit-mask-composite", value) ; 
    } 
    public DCss2Properties setWebkitMaskComposite(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-composite", value, important) ; 
    } 


    public String getWebkitMaskImage() { 
    	return get("-webkit-mask-image"); 
    } 
    public DCss2Properties setWebkitMaskImage(final String value) throws DOMException { 
    	return put("-webkit-mask-image", value) ; 
    } 
    public DCss2Properties setWebkitMaskImage(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-image", value, important) ; 
    } 


    public String getWebkitMaskOrigin() { 
    	return get("-webkit-mask-origin"); 
    } 
    public DCss2Properties setWebkitMaskOrigin(final String value) throws DOMException { 
    	return put("-webkit-mask-origin", value) ; 
    } 
    public DCss2Properties setWebkitMaskOrigin(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-origin", value, important) ; 
    } 


    public String getWebkitMaskPosition() { 
    	return get("-webkit-mask-position"); 
    } 
    public DCss2Properties setWebkitMaskPosition(final String value) throws DOMException { 
    	return put("-webkit-mask-position", value) ; 
    } 
    public DCss2Properties setWebkitMaskPosition(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-position", value, important) ; 
    } 


    public String getWebkitMaskPositionX() { 
    	return get("-webkit-mask-position-x"); 
    } 
    public DCss2Properties setWebkitMaskPositionX(final String value) throws DOMException { 
    	return put("-webkit-mask-position-x", value) ; 
    } 
    public DCss2Properties setWebkitMaskPositionX(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-position-x", value, important) ; 
    } 


    public String getWebkitMaskPositionY() { 
    	return get("-webkit-mask-position-y"); 
    } 
    public DCss2Properties setWebkitMaskPositionY(final String value) throws DOMException { 
    	return put("-webkit-mask-position-y", value) ; 
    } 
    public DCss2Properties setWebkitMaskPositionY(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-position-y", value, important) ; 
    } 


    public String getWebkitMaskRepeat() { 
    	return get("-webkit-mask-repeat"); 
    } 
    public DCss2Properties setWebkitMaskRepeat(final String value) throws DOMException { 
    	return put("-webkit-mask-repeat", value) ; 
    } 
    public DCss2Properties setWebkitMaskRepeat(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-repeat", value, important) ; 
    } 


    public String getWebkitMaskRepeatX() { 
    	return get("-webkit-mask-repeat-x"); 
    } 
    public DCss2Properties setWebkitMaskRepeatX(final String value) throws DOMException { 
    	return put("-webkit-mask-repeat-x", value) ; 
    } 
    public DCss2Properties setWebkitMaskRepeatX(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-repeat-x", value, important) ; 
    } 


    public String getWebkitMaskRepeatY() { 
    	return get("-webkit-mask-repeat-y"); 
    } 
    public DCss2Properties setWebkitMaskRepeatY(final String value) throws DOMException { 
    	return put("-webkit-mask-repeat-y", value) ; 
    } 
    public DCss2Properties setWebkitMaskRepeatY(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-repeat-y", value, important) ; 
    } 


    public String getWebkitMaskSize() { 
    	return get("-webkit-mask-size"); 
    } 
    public DCss2Properties setWebkitMaskSize(final String value) throws DOMException { 
    	return put("-webkit-mask-size", value) ; 
    } 
    public DCss2Properties setWebkitMaskSize(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-mask-size", value, important) ; 
    } 


    public String getWebkitMatchNearestMailBlockquoteColor() { 
    	return get("-webkit-match-nearest-mail-blockquote-color"); 
    } 
    public DCss2Properties setWebkitMatchNearestMailBlockquoteColor(final String value) throws DOMException { 
    	return put("-webkit-match-nearest-mail-blockquote-color", value) ; 
    } 
    public DCss2Properties setWebkitMatchNearestMailBlockquoteColor(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-match-nearest-mail-blockquote-color", value, important) ; 
    } 


    public String getWebkitNbspMode() { 
    	return get("-webkit-nbsp-mode"); 
    } 
    public DCss2Properties setWebkitNbspMode(final String value) throws DOMException { 
    	return put("-webkit-nbsp-mode", value) ; 
    } 
    public DCss2Properties setWebkitNbspMode(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-nbsp-mode", value, important) ; 
    } 


    public String getWebkitPaddingEnd() { 
    	return get("-webkit-padding-end"); 
    } 
    public DCss2Properties setWebkitPaddingEnd(final String value) throws DOMException { 
    	return put("-webkit-padding-end", value) ; 
    } 
    public DCss2Properties setWebkitPaddingEnd(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-padding-end", value, important) ; 
    } 


    public String getWebkitPaddingStart() { 
    	return get("-webkit-padding-start"); 
    } 
    public DCss2Properties setWebkitPaddingStart(final String value) throws DOMException { 
    	return put("-webkit-padding-start", value) ; 
    } 
    public DCss2Properties setWebkitPaddingStart(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-padding-start", value, important) ; 
    } 


    public String getWebkitPerspective() { 
    	return get("-webkit-perspective"); 
    } 
    public DCss2Properties setWebkitPerspective(final String value) throws DOMException { 
    	return put("-webkit-perspective", value) ; 
    } 
    public DCss2Properties setWebkitPerspective(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-perspective", value, important) ; 
    } 


    public String getWebkitPerspectiveOrigin() { 
    	return get("-webkit-perspective-origin"); 
    } 
    public DCss2Properties setWebkitPerspectiveOrigin(final String value) throws DOMException { 
    	return put("-webkit-perspective-origin", value) ; 
    } 
    public DCss2Properties setWebkitPerspectiveOrigin(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-perspective-origin", value, important) ; 
    } 


    public String getWebkitPerspectiveOriginX() { 
    	return get("-webkit-perspective-origin-x"); 
    } 
    public DCss2Properties setWebkitPerspectiveOriginX(final String value) throws DOMException { 
    	return put("-webkit-perspective-origin-x", value) ; 
    } 
    public DCss2Properties setWebkitPerspectiveOriginX(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-perspective-origin-x", value, important) ; 
    } 


    public String getWebkitPerspectiveOriginY() { 
    	return get("-webkit-perspective-origin-y"); 
    } 
    public DCss2Properties setWebkitPerspectiveOriginY(final String value) throws DOMException { 
    	return put("-webkit-perspective-origin-y", value) ; 
    } 
    public DCss2Properties setWebkitPerspectiveOriginY(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-perspective-origin-y", value, important) ; 
    } 


    public String getWebkitRtlOrdering() { 
    	return get("-webkit-rtl-ordering"); 
    } 
    public DCss2Properties setWebkitRtlOrdering(final String value) throws DOMException { 
    	return put("-webkit-rtl-ordering", value) ; 
    } 
    public DCss2Properties setWebkitRtlOrdering(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-rtl-ordering", value, important) ; 
    } 


    public String getWebkitTextDecorationsInEffect() { 
    	return get("-webkit-text-decorations-in-effect"); 
    } 
    public DCss2Properties setWebkitTextDecorationsInEffect(final String value) throws DOMException { 
    	return put("-webkit-text-decorations-in-effect", value) ; 
    } 
    public DCss2Properties setWebkitTextDecorationsInEffect(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-text-decorations-in-effect", value, important) ; 
    } 


    public String getWebkitTextFillColor() { 
    	return get("-webkit-text-fill-color"); 
    } 
    public DCss2Properties setWebkitTextFillColor(final String value) throws DOMException { 
    	return put("-webkit-text-fill-color", value) ; 
    } 
    public DCss2Properties setWebkitTextFillColor(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-text-fill-color", value, important) ; 
    } 


    public String getWebkitTextSecurity() { 
    	return get("-webkit-text-security"); 
    } 
    public DCss2Properties setWebkitTextSecurity(final String value) throws DOMException { 
    	return put("-webkit-text-security", value) ; 
    } 
    public DCss2Properties setWebkitTextSecurity(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-text-security", value, important) ; 
    } 


    public String getWebkitTextSizeAdjust() { 
    	return get("-webkit-text-size-adjust"); 
    } 
    public DCss2Properties setWebkitTextSizeAdjust(final String value) throws DOMException { 
    	return put("-webkit-text-size-adjust", value) ; 
    } 
    public DCss2Properties setWebkitTextSizeAdjust(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-text-size-adjust", value, important) ; 
    } 


    public String getWebkitTextStroke() { 
    	return get("-webkit-text-stroke"); 
    } 
    public DCss2Properties setWebkitTextStroke(final String value) throws DOMException { 
    	return put("-webkit-text-stroke", value) ; 
    } 
    public DCss2Properties setWebkitTextStroke(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-text-stroke", value, important) ; 
    } 


    public String getWebkitTextStrokeColor() { 
    	return get("-webkit-text-stroke-color"); 
    } 
    public DCss2Properties setWebkitTextStrokeColor(final String value) throws DOMException { 
    	return put("-webkit-text-stroke-color", value) ; 
    } 
    public DCss2Properties setWebkitTextStrokeColor(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-text-stroke-color", value, important) ; 
    } 


    public String getWebkitTextStrokeWidth() { 
    	return get("-webkit-text-stroke-width"); 
    } 
    public DCss2Properties setWebkitTextStrokeWidth(final String value) throws DOMException { 
    	return put("-webkit-text-stroke-width", value) ; 
    } 
    public DCss2Properties setWebkitTextStrokeWidth(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-text-stroke-width", value, important) ; 
    } 


    public String getWebkitTransform() { 
    	return get("-webkit-transform"); 
    } 
    public DCss2Properties setWebkitTransform(final String value) throws DOMException { 
    	return put("-webkit-transform", value) ; 
    } 
    public DCss2Properties setWebkitTransform(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-transform", value, important) ; 
    } 


    public String getWebkitTransformOrigin() { 
    	return get("-webkit-transform-origin"); 
    } 
    public DCss2Properties setWebkitTransformOrigin(final String value) throws DOMException { 
    	return put("-webkit-transform-origin", value) ; 
    } 
    public DCss2Properties setWebkitTransformOrigin(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-transform-origin", value, important) ; 
    } 


    public String getWebkitTransformOriginX() { 
    	return get("-webkit-transform-origin-x"); 
    } 
    public DCss2Properties setWebkitTransformOriginX(final String value) throws DOMException { 
    	return put("-webkit-transform-origin-x", value) ; 
    } 
    public DCss2Properties setWebkitTransformOriginX(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-transform-origin-x", value, important) ; 
    } 


    public String getWebkitTransformOriginY() { 
    	return get("-webkit-transform-origin-y"); 
    } 
    public DCss2Properties setWebkitTransformOriginY(final String value) throws DOMException { 
    	return put("-webkit-transform-origin-y", value) ; 
    } 
    public DCss2Properties setWebkitTransformOriginY(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-transform-origin-y", value, important) ; 
    } 


    public String getWebkitTransformOriginZ() { 
    	return get("-webkit-transform-origin-z"); 
    } 
    public DCss2Properties setWebkitTransformOriginZ(final String value) throws DOMException { 
    	return put("-webkit-transform-origin-z", value) ; 
    } 
    public DCss2Properties setWebkitTransformOriginZ(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-transform-origin-z", value, important) ; 
    } 


    public String getWebkitTransformStyle() { 
    	return get("-webkit-transform-style"); 
    } 
    public DCss2Properties setWebkitTransformStyle(final String value) throws DOMException { 
    	return put("-webkit-transform-style", value) ; 
    } 
    public DCss2Properties setWebkitTransformStyle(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-transform-style", value, important) ; 
    } 


    public String getWebkitTransition() { 
    	return get("-webkit-transition"); 
    } 
    public DCss2Properties setWebkitTransition(final String value) throws DOMException { 
    	return put("-webkit-transition", value) ; 
    } 
    public DCss2Properties setWebkitTransition(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-transition", value, important) ; 
    } 


    public String getWebkitTransitionDelay() { 
    	return get("-webkit-transition-delay"); 
    } 
    public DCss2Properties setWebkitTransitionDelay(final String value) throws DOMException { 
    	return put("-webkit-transition-delay", value) ; 
    } 
    public DCss2Properties setWebkitTransitionDelay(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-transition-delay", value, important) ; 
    } 


    public String getWebkitTransitionDuration() { 
    	return get("-webkit-transition-duration"); 
    } 
    public DCss2Properties setWebkitTransitionDuration(final String value) throws DOMException { 
    	return put("-webkit-transition-duration", value) ; 
    } 
    public DCss2Properties setWebkitTransitionDuration(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-transition-duration", value, important) ; 
    } 


    public String getWebkitTransitionProperty() { 
    	return get("-webkit-transition-property"); 
    } 
    public DCss2Properties setWebkitTransitionProperty(final String value) throws DOMException { 
    	return put("-webkit-transition-property", value) ; 
    } 
    public DCss2Properties setWebkitTransitionProperty(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-transition-property", value, important) ; 
    } 


    public String getWebkitTransitionTimingFunction() { 
    	return get("-webkit-transition-timing-function"); 
    } 
    public DCss2Properties setWebkitTransitionTimingFunction(final String value) throws DOMException { 
    	return put("-webkit-transition-timing-function", value) ; 
    } 
    public DCss2Properties setWebkitTransitionTimingFunction(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-transition-timing-function", value, important) ; 
    } 


    public String getWebkitUserDrag() { 
    	return get("-webkit-user-drag"); 
    } 
    public DCss2Properties setWebkitUserDrag(final String value) throws DOMException { 
    	return put("-webkit-user-drag", value) ; 
    } 
    public DCss2Properties setWebkitUserDrag(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-user-drag", value, important) ; 
    } 


    public String getWebkitUserModify() { 
    	return get("-webkit-user-modify"); 
    } 
    public DCss2Properties setWebkitUserModify(final String value) throws DOMException { 
    	return put("-webkit-user-modify", value) ; 
    } 
    public DCss2Properties setWebkitUserModify(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-user-modify", value, important) ; 
    } 


    public String getWebkitUserSelect() { 
    	return get("-webkit-user-select"); 
    } 
    public DCss2Properties setWebkitUserSelect(final String value) throws DOMException { 
    	return put("-webkit-user-select", value) ; 
    } 
    public DCss2Properties setWebkitUserSelect(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-user-select", value, important) ; 
    } 


    public String getWebkitVariableDeclarationBlock() { 
    	return get("-webkit-variable-declaration-block"); 
    } 
    public DCss2Properties setWebkitVariableDeclarationBlock(final String value) throws DOMException { 
    	return put("-webkit-variable-declaration-block", value) ; 
    } 
    public DCss2Properties setWebkitVariableDeclarationBlock(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-variable-declaration-block", value, important) ; 
    } 


    public String getWebkitBorderTopRightRadius() { 
    	return get("-webkit-border-top-right-radius"); 
    } 
    public DCss2Properties setWebkitBorderTopRightRadius(final String value) throws DOMException { 
    	return put("-webkit-border-top-right-radius", value) ; 
    } 
    public DCss2Properties setWebkitBorderTopRightRadius(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-border-top-right-radius", value, important) ; 
    } 


    public String getWebkitBorderBottomRightRadius() { 
    	return get("-webkit-border-bottom-right-radius"); 
    } 
    public DCss2Properties setWebkitBorderBottomRightRadius(final String value) throws DOMException { 
    	return put("-webkit-border-bottom-right-radius", value) ; 
    } 
    public DCss2Properties setWebkitBorderBottomRightRadius(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-border-bottom-right-radius", value, important) ; 
    } 


    public String getWebkitBorderBottomLeftRadius() { 
    	return get("-webkit-border-bottom-left-radius"); 
    } 
    public DCss2Properties setWebkitBorderBottomLeftRadius(final String value) throws DOMException { 
    	return put("-webkit-border-bottom-left-radius", value) ; 
    } 
    public DCss2Properties setWebkitBorderBottomLeftRadius(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-border-bottom-left-radius", value, important) ; 
    } 


    public String getWebkitBorderTopLeftRadius() { 
    	return get("-webkit-border-top-left-radius"); 
    } 
    public DCss2Properties setWebkitBorderTopLeftRadius(final String value) throws DOMException { 
    	return put("-webkit-border-top-left-radius", value) ; 
    } 
    public DCss2Properties setWebkitBorderTopLeftRadius(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-border-top-left-radius", value, important) ; 
    } 


    public String getWebkitDashboardRegion() { 
    	return get("-webkit-dashboard-region"); 
    } 
    public DCss2Properties setWebkitDashboardRegion(final String value) throws DOMException { 
    	return put("-webkit-dashboard-region", value) ; 
    } 
    public DCss2Properties setWebkitDashboardRegion(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-dashboard-region", value, important) ; 
    } 

    public String getWebkitTapHighlightColor() { 
    	return get("-webkit-tap-highlight-color"); 
    } 
    public DCss2Properties setWebkitTapHighlightColor(final String value) throws DOMException { 
    	return put("-webkit-tap-highlight-color", value) ; 
    } 
    public DCss2Properties setWebkitTapHighlightColor(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-tap-highlight-color", value, important) ; 
    } 


    public String getWebkitTouchCallout() { 
    	return get("-webkit-touch-callout"); 
    } 
    public DCss2Properties setWebkitTouchCallout(final String value) throws DOMException { 
    	return put("-webkit-touch-callout", value) ; 
    } 
    public DCss2Properties setWebkitTouchCallout(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-touch-callout", value, important) ; 
    }   
    
    public String getWebkitGradient() { 
    	return get("-webkit-gradient"); 
    } 
    public DCss2Properties setWebkitGradient(final String value) throws DOMException { 
    	return put("-webkit-gradient", value) ; 
    } 
    public DCss2Properties setWebkitGradient(final String value, final boolean important) throws DOMException { 
    	return put("-webkit-gradient", value, important) ; 
    } 
    
    // End Webkit extensions
    
    // Start Mozilla extensions
    public String getMozAppearance() { 
    	return get("-moz-appearance"); 
    } 
    public DCss2Properties setMozAppearance(final String value) throws DOMException { 
    	return put("-moz-appearance", value) ; 
    } 
    public DCss2Properties setMozAppearance(final String value, final boolean important) throws DOMException { 
    	return put("-moz-appearance", value, important) ; 
    } 


    public String getMozBackgroundClip() { 
    	return get("-moz-background-clip"); 
    } 
    public DCss2Properties setMozBackgroundClip(final String value) throws DOMException { 
    	return put("-moz-background-clip", value) ; 
    } 
    public DCss2Properties setMozBackgroundClip(final String value, final boolean important) throws DOMException { 
    	return put("-moz-background-clip", value, important) ; 
    } 


    public String getMozBackgroundInlinePolicy() { 
    	return get("-moz-background-inline-policy"); 
    } 
    public DCss2Properties setMozBackgroundInlinePolicy(final String value) throws DOMException { 
    	return put("-moz-background-inline-policy", value) ; 
    } 
    public DCss2Properties setMozBackgroundInlinePolicy(final String value, final boolean important) throws DOMException { 
    	return put("-moz-background-inline-policy", value, important) ; 
    } 


    public String getMozBackgroundOrigin() { 
    	return get("-moz-background-origin"); 
    } 
    public DCss2Properties setMozBackgroundOrigin(final String value) throws DOMException { 
    	return put("-moz-background-origin", value) ; 
    } 
    public DCss2Properties setMozBackgroundOrigin(final String value, final boolean important) throws DOMException { 
    	return put("-moz-background-origin", value, important) ; 
    } 


    public String getMozBackgroundSize() { 
    	return get("-moz-background-size"); 
    } 
    public DCss2Properties setMozBackgroundSize(final String value) throws DOMException { 
    	return put("-moz-background-size", value) ; 
    } 
    public DCss2Properties setMozBackgroundSize(final String value, final boolean important) throws DOMException { 
    	return put("-moz-background-size", value, important) ; 
    } 


    public String getMozBinding() { 
    	return get("-moz-binding"); 
    } 
    public DCss2Properties setMozBinding(final String value) throws DOMException { 
    	return put("-moz-binding", value) ; 
    } 
    public DCss2Properties setMozBinding(final String value, final boolean important) throws DOMException { 
    	return put("-moz-binding", value, important) ; 
    } 


    public String getMozBorderBottomColors() { 
    	return get("-moz-border-bottom-colors"); 
    } 
    public DCss2Properties setMozBorderBottomColors(final String value) throws DOMException { 
    	return put("-moz-border-bottom-colors", value) ; 
    } 
    public DCss2Properties setMozBorderBottomColors(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-bottom-colors", value, important) ; 
    } 


    public String getMozBorderLeftColors() { 
    	return get("-moz-border-left-colors"); 
    } 
    public DCss2Properties setMozBorderLeftColors(final String value) throws DOMException { 
    	return put("-moz-border-left-colors", value) ; 
    } 
    public DCss2Properties setMozBorderLeftColors(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-left-colors", value, important) ; 
    } 


    public String getMozBorderRightColors() { 
    	return get("-moz-border-right-colors"); 
    } 
    public DCss2Properties setMozBorderRightColors(final String value) throws DOMException { 
    	return put("-moz-border-right-colors", value) ; 
    } 
    public DCss2Properties setMozBorderRightColors(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-right-colors", value, important) ; 
    } 


    public String getMozBorderTopColors() { 
    	return get("-moz-border-top-colors"); 
    } 
    public DCss2Properties setMozBorderTopColors(final String value) throws DOMException { 
    	return put("-moz-border-top-colors", value) ; 
    } 
    public DCss2Properties setMozBorderTopColors(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-top-colors", value, important) ; 
    } 


    public String getMozBorderEnd() { 
    	return get("-moz-border-end"); 
    } 
    public DCss2Properties setMozBorderEnd(final String value) throws DOMException { 
    	return put("-moz-border-end", value) ; 
    } 
    public DCss2Properties setMozBorderEnd(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-end", value, important) ; 
    } 


    public String getMozBorderEndColor() { 
    	return get("-moz-border-end-color"); 
    } 
    public DCss2Properties setMozBorderEndColor(final String value) throws DOMException { 
    	return put("-moz-border-end-color", value) ; 
    } 
    public DCss2Properties setMozBorderEndColor(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-end-color", value, important) ; 
    } 


    public String getMozBorderEndStyle() { 
    	return get("-moz-border-end-style"); 
    } 
    public DCss2Properties setMozBorderEndStyle(final String value) throws DOMException { 
    	return put("-moz-border-end-style", value) ; 
    } 
    public DCss2Properties setMozBorderEndStyle(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-end-style", value, important) ; 
    } 


    public String getMozBorderEndWidth() { 
    	return get("-moz-border-end-width"); 
    } 
    public DCss2Properties setMozBorderEndWidth(final String value) throws DOMException { 
    	return put("-moz-border-end-width", value) ; 
    } 
    public DCss2Properties setMozBorderEndWidth(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-end-width", value, important) ; 
    } 


    public String getMozBorderImage() { 
    	return get("-moz-border-image"); 
    } 
    public DCss2Properties setMozBorderImage(final String value) throws DOMException { 
    	return put("-moz-border-image", value) ; 
    } 
    public DCss2Properties setMozBorderImage(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-image", value, important) ; 
    } 


    public String getMozBorderRadius() { 
    	return get("-moz-border-radius"); 
    } 
    public DCss2Properties setMozBorderRadius(final String value) throws DOMException { 
    	return put("-moz-border-radius", value) ; 
    } 
    public DCss2Properties setMozBorderRadius(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-radius", value, important) ; 
    } 


    public String getMozBorderRadiusBottomleft() { 
    	return get("-moz-border-radius-bottomleft"); 
    } 
    public DCss2Properties setMozBorderRadiusBottomleft(final String value) throws DOMException { 
    	return put("-moz-border-radius-bottomleft", value) ; 
    } 
    public DCss2Properties setMozBorderRadiusBottomleft(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-radius-bottomleft", value, important) ; 
    } 


    public String getMozBorderRadiusBottomright() { 
    	return get("-moz-border-radius-bottomright"); 
    } 
    public DCss2Properties setMozBorderRadiusBottomright(final String value) throws DOMException { 
    	return put("-moz-border-radius-bottomright", value) ; 
    } 
    public DCss2Properties setMozBorderRadiusBottomright(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-radius-bottomright", value, important) ; 
    } 


    public String getMozBorderRadiusTopleft() { 
    	return get("-moz-border-radius-topleft"); 
    } 
    public DCss2Properties setMozBorderRadiusTopleft(final String value) throws DOMException { 
    	return put("-moz-border-radius-topleft", value) ; 
    } 
    public DCss2Properties setMozBorderRadiusTopleft(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-radius-topleft", value, important) ; 
    } 


    public String getMozBorderRadiusTopright() { 
    	return get("-moz-border-radius-topright"); 
    } 
    public DCss2Properties setMozBorderRadiusTopright(final String value) throws DOMException { 
    	return put("-moz-border-radius-topright", value) ; 
    } 
    public DCss2Properties setMozBorderRadiusTopright(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-radius-topright", value, important) ; 
    } 


    public String getMozBorderStart() { 
    	return get("-moz-border-start"); 
    } 
    public DCss2Properties setMozBorderStart(final String value) throws DOMException { 
    	return put("-moz-border-start", value) ; 
    } 
    public DCss2Properties setMozBorderStart(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-start", value, important) ; 
    } 


    public String getMozBorderStartColor() { 
    	return get("-moz-border-start-color"); 
    } 
    public DCss2Properties setMozBorderStartColor(final String value) throws DOMException { 
    	return put("-moz-border-start-color", value) ; 
    } 
    public DCss2Properties setMozBorderStartColor(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-start-color", value, important) ; 
    } 


    public String getMozBorderStartStyle() { 
    	return get("-moz-border-start-style"); 
    } 
    public DCss2Properties setMozBorderStartStyle(final String value) throws DOMException { 
    	return put("-moz-border-start-style", value) ; 
    } 
    public DCss2Properties setMozBorderStartStyle(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-start-style", value, important) ; 
    } 


    public String getMozBorderStartWidth() { 
    	return get("-moz-border-start-width"); 
    } 
    public DCss2Properties setMozBorderStartWidth(final String value) throws DOMException { 
    	return put("-moz-border-start-width", value) ; 
    } 
    public DCss2Properties setMozBorderStartWidth(final String value, final boolean important) throws DOMException { 
    	return put("-moz-border-start-width", value, important) ; 
    } 


    public String getMozBoxAlign() { 
    	return get("-moz-box-align"); 
    } 
    public DCss2Properties setMozBoxAlign(final String value) throws DOMException { 
    	return put("-moz-box-align", value) ; 
    } 
    public DCss2Properties setMozBoxAlign(final String value, final boolean important) throws DOMException { 
    	return put("-moz-box-align", value, important) ; 
    } 


    public String getMozBoxDirection() { 
    	return get("-moz-box-direction"); 
    } 
    public DCss2Properties setMozBoxDirection(final String value) throws DOMException { 
    	return put("-moz-box-direction", value) ; 
    } 
    public DCss2Properties setMozBoxDirection(final String value, final boolean important) throws DOMException { 
    	return put("-moz-box-direction", value, important) ; 
    } 


    public String getMozBoxFlex() { 
    	return get("-moz-box-flex"); 
    } 
    public DCss2Properties setMozBoxFlex(final String value) throws DOMException { 
    	return put("-moz-box-flex", value) ; 
    } 
    public DCss2Properties setMozBoxFlex(final String value, final boolean important) throws DOMException { 
    	return put("-moz-box-flex", value, important) ; 
    } 


    public String getMozBoxFlexgroup() { 
    	return get("-moz-box-flexgroup"); 
    } 
    public DCss2Properties setMozBoxFlexgroup(final String value) throws DOMException { 
    	return put("-moz-box-flexgroup", value) ; 
    } 
    public DCss2Properties setMozBoxFlexgroup(final String value, final boolean important) throws DOMException { 
    	return put("-moz-box-flexgroup", value, important) ; 
    } 


    public String getMozBoxOrdinalGroup() { 
    	return get("-moz-box-ordinal-group"); 
    } 
    public DCss2Properties setMozBoxOrdinalGroup(final String value) throws DOMException { 
    	return put("-moz-box-ordinal-group", value) ; 
    } 
    public DCss2Properties setMozBoxOrdinalGroup(final String value, final boolean important) throws DOMException { 
    	return put("-moz-box-ordinal-group", value, important) ; 
    } 


    public String getMozBoxOrient() { 
    	return get("-moz-box-orient"); 
    } 
    public DCss2Properties setMozBoxOrient(final String value) throws DOMException { 
    	return put("-moz-box-orient", value) ; 
    } 
    public DCss2Properties setMozBoxOrient(final String value, final boolean important) throws DOMException { 
    	return put("-moz-box-orient", value, important) ; 
    } 


    public String getMozBoxPack() { 
    	return get("-moz-box-pack"); 
    } 
    public DCss2Properties setMozBoxPack(final String value) throws DOMException { 
    	return put("-moz-box-pack", value) ; 
    } 
    public DCss2Properties setMozBoxPack(final String value, final boolean important) throws DOMException { 
    	return put("-moz-box-pack", value, important) ; 
    } 


    public String getMozBoxShadow() { 
    	return get("-moz-box-shadow"); 
    } 
    public DCss2Properties setMozBoxShadow(final String value) throws DOMException { 
    	return put("-moz-box-shadow", value) ; 
    } 
    public DCss2Properties setMozBoxShadow(final String value, final boolean important) throws DOMException { 
    	return put("-moz-box-shadow", value, important) ; 
    } 


    public String getMozBoxSizing() { 
    	return get("-moz-box-sizing"); 
    } 
    public DCss2Properties setMozBoxSizing(final String value) throws DOMException { 
    	return put("-moz-box-sizing", value) ; 
    } 
    public DCss2Properties setMozBoxSizing(final String value, final boolean important) throws DOMException { 
    	return put("-moz-box-sizing", value, important) ; 
    } 


    public String getMozColumnCount() { 
    	return get("-moz-column-count"); 
    } 
    public DCss2Properties setMozColumnCount(final String value) throws DOMException { 
    	return put("-moz-column-count", value) ; 
    } 
    public DCss2Properties setMozColumnCount(final String value, final boolean important) throws DOMException { 
    	return put("-moz-column-count", value, important) ; 
    } 


    public String getMozColumnGap() { 
    	return get("-moz-column-gap"); 
    } 
    public DCss2Properties setMozColumnGap(final String value) throws DOMException { 
    	return put("-moz-column-gap", value) ; 
    } 
    public DCss2Properties setMozColumnGap(final String value, final boolean important) throws DOMException { 
    	return put("-moz-column-gap", value, important) ; 
    } 


    public String getMozColumnWidth() { 
    	return get("-moz-column-width"); 
    } 
    public DCss2Properties setMozColumnWidth(final String value) throws DOMException { 
    	return put("-moz-column-width", value) ; 
    } 
    public DCss2Properties setMozColumnWidth(final String value, final boolean important) throws DOMException { 
    	return put("-moz-column-width", value, important) ; 
    } 


    public String getMozColumnRule() { 
    	return get("-moz-column-rule"); 
    } 
    public DCss2Properties setMozColumnRule(final String value) throws DOMException { 
    	return put("-moz-column-rule", value) ; 
    } 
    public DCss2Properties setMozColumnRule(final String value, final boolean important) throws DOMException { 
    	return put("-moz-column-rule", value, important) ; 
    } 


    public String getMozColumnRuleWidth() { 
    	return get("-moz-column-rule-width"); 
    } 
    public DCss2Properties setMozColumnRuleWidth(final String value) throws DOMException { 
    	return put("-moz-column-rule-width", value) ; 
    } 
    public DCss2Properties setMozColumnRuleWidth(final String value, final boolean important) throws DOMException { 
    	return put("-moz-column-rule-width", value, important) ; 
    } 


    public String getMozColumnRuleStyle() { 
    	return get("-moz-column-rule-style"); 
    } 
    public DCss2Properties setMozColumnRuleStyle(final String value) throws DOMException { 
    	return put("-moz-column-rule-style", value) ; 
    } 
    public DCss2Properties setMozColumnRuleStyle(final String value, final boolean important) throws DOMException { 
    	return put("-moz-column-rule-style", value, important) ; 
    } 


    public String getMozColumnRuleColor() { 
    	return get("-moz-column-rule-color"); 
    } 
    public DCss2Properties setMozColumnRuleColor(final String value) throws DOMException { 
    	return put("-moz-column-rule-color", value) ; 
    } 
    public DCss2Properties setMozColumnRuleColor(final String value, final boolean important) throws DOMException { 
    	return put("-moz-column-rule-color", value, important) ; 
    } 


    public String getMozFloatEdge() { 
    	return get("-moz-float-edge"); 
    } 
    public DCss2Properties setMozFloatEdge(final String value) throws DOMException { 
    	return put("-moz-float-edge", value) ; 
    } 
    public DCss2Properties setMozFloatEdge(final String value, final boolean important) throws DOMException { 
    	return put("-moz-float-edge", value, important) ; 
    } 


    public String getMozForceBrokenImageIcon() { 
    	return get("-moz-force-broken-image-icon"); 
    } 
    public DCss2Properties setMozForceBrokenImageIcon(final String value) throws DOMException { 
    	return put("-moz-force-broken-image-icon", value) ; 
    } 
    public DCss2Properties setMozForceBrokenImageIcon(final String value, final boolean important) throws DOMException { 
    	return put("-moz-force-broken-image-icon", value, important) ; 
    } 


    public String getMozImageRegion() { 
    	return get("-moz-image-region"); 
    } 
    public DCss2Properties setMozImageRegion(final String value) throws DOMException { 
    	return put("-moz-image-region", value) ; 
    } 
    public DCss2Properties setMozImageRegion(final String value, final boolean important) throws DOMException { 
    	return put("-moz-image-region", value, important) ; 
    } 


    public String getMozMarginEnd() { 
    	return get("-moz-margin-end"); 
    } 
    public DCss2Properties setMozMarginEnd(final String value) throws DOMException { 
    	return put("-moz-margin-end", value) ; 
    } 
    public DCss2Properties setMozMarginEnd(final String value, final boolean important) throws DOMException { 
    	return put("-moz-margin-end", value, important) ; 
    } 


    public String getMozMarginStart() { 
    	return get("-moz-margin-start"); 
    } 
    public DCss2Properties setMozMarginStart(final String value) throws DOMException { 
    	return put("-moz-margin-start", value) ; 
    } 
    public DCss2Properties setMozMarginStart(final String value, final boolean important) throws DOMException { 
    	return put("-moz-margin-start", value, important) ; 
    } 


    public String getMozOpacity() { 
    	return get("-moz-opacity"); 
    } 
    public DCss2Properties setMozOpacity(final String value) throws DOMException { 
    	return put("-moz-opacity", value) ; 
    } 
    public DCss2Properties setMozOpacity(final String value, final boolean important) throws DOMException { 
    	return put("-moz-opacity", value, important) ; 
    } 


    public String getMozOutline() { 
    	return get("-moz-outline"); 
    } 
    public DCss2Properties setMozOutline(final String value) throws DOMException { 
    	return put("-moz-outline", value) ; 
    } 
    public DCss2Properties setMozOutline(final String value, final boolean important) throws DOMException { 
    	return put("-moz-outline", value, important) ; 
    } 


    public String getMozOutlineColor() { 
    	return get("-moz-outline-color"); 
    } 
    public DCss2Properties setMozOutlineColor(final String value) throws DOMException { 
    	return put("-moz-outline-color", value) ; 
    } 
    public DCss2Properties setMozOutlineColor(final String value, final boolean important) throws DOMException { 
    	return put("-moz-outline-color", value, important) ; 
    } 


    public String getMozOutlineOffset() { 
    	return get("-moz-outline-offset"); 
    } 
    public DCss2Properties setMozOutlineOffset(final String value) throws DOMException { 
    	return put("-moz-outline-offset", value) ; 
    } 
    public DCss2Properties setMozOutlineOffset(final String value, final boolean important) throws DOMException { 
    	return put("-moz-outline-offset", value, important) ; 
    } 


    public String getMozOutlineRadius() { 
    	return get("-moz-outline-radius"); 
    } 
    public DCss2Properties setMozOutlineRadius(final String value) throws DOMException { 
    	return put("-moz-outline-radius", value) ; 
    } 
    public DCss2Properties setMozOutlineRadius(final String value, final boolean important) throws DOMException { 
    	return put("-moz-outline-radius", value, important) ; 
    } 


    public String getMozOutlineRadiusBottomleft() { 
    	return get("-moz-outline-radius-bottomleft"); 
    } 
    public DCss2Properties setMozOutlineRadiusBottomleft(final String value) throws DOMException { 
    	return put("-moz-outline-radius-bottomleft", value) ; 
    } 
    public DCss2Properties setMozOutlineRadiusBottomleft(final String value, final boolean important) throws DOMException { 
    	return put("-moz-outline-radius-bottomleft", value, important) ; 
    } 


    public String getMozOutlineRadiusBottomright() { 
    	return get("-moz-outline-radius-bottomright"); 
    } 
    public DCss2Properties setMozOutlineRadiusBottomright(final String value) throws DOMException { 
    	return put("-moz-outline-radius-bottomright", value) ; 
    } 
    public DCss2Properties setMozOutlineRadiusBottomright(final String value, final boolean important) throws DOMException { 
    	return put("-moz-outline-radius-bottomright", value, important) ; 
    } 


    public String getMozOutlineRadiusTopleft() { 
    	return get("-moz-outline-radius-topleft"); 
    } 
    public DCss2Properties setMozOutlineRadiusTopleft(final String value) throws DOMException { 
    	return put("-moz-outline-radius-topleft", value) ; 
    } 
    public DCss2Properties setMozOutlineRadiusTopleft(final String value, final boolean important) throws DOMException { 
    	return put("-moz-outline-radius-topleft", value, important) ; 
    } 


    public String getMozOutlineRadiusTopright() { 
    	return get("-moz-outline-radius-topright"); 
    } 
    public DCss2Properties setMozOutlineRadiusTopright(final String value) throws DOMException { 
    	return put("-moz-outline-radius-topright", value) ; 
    } 
    public DCss2Properties setMozOutlineRadiusTopright(final String value, final boolean important) throws DOMException { 
    	return put("-moz-outline-radius-topright", value, important) ; 
    } 


    public String getMozOutlineStyle() { 
    	return get("-moz-outline-style"); 
    } 
    public DCss2Properties setMozOutlineStyle(final String value) throws DOMException { 
    	return put("-moz-outline-style", value) ; 
    } 
    public DCss2Properties setMozOutlineStyle(final String value, final boolean important) throws DOMException { 
    	return put("-moz-outline-style", value, important) ; 
    } 


    public String getMozOutlineWidth() { 
    	return get("-moz-outline-width"); 
    } 
    public DCss2Properties setMozOutlineWidth(final String value) throws DOMException { 
    	return put("-moz-outline-width", value) ; 
    } 
    public DCss2Properties setMozOutlineWidth(final String value, final boolean important) throws DOMException { 
    	return put("-moz-outline-width", value, important) ; 
    } 


    public String getMozPaddingEnd() { 
    	return get("-moz-padding-end"); 
    } 
    public DCss2Properties setMozPaddingEnd(final String value) throws DOMException { 
    	return put("-moz-padding-end", value) ; 
    } 
    public DCss2Properties setMozPaddingEnd(final String value, final boolean important) throws DOMException { 
    	return put("-moz-padding-end", value, important) ; 
    } 


    public String getMozPaddingStart() { 
    	return get("-moz-padding-start"); 
    } 
    public DCss2Properties setMozPaddingStart(final String value) throws DOMException { 
    	return put("-moz-padding-start", value) ; 
    } 
    public DCss2Properties setMozPaddingStart(final String value, final boolean important) throws DOMException { 
    	return put("-moz-padding-start", value, important) ; 
    } 


    public String getMozStackSizing () { 
    	return get("-moz-stack-sizing"); 
    } 
    public DCss2Properties setMozStackSizing (final String value) throws DOMException { 
    	return put("-moz-stack-sizing", value) ; 
    } 
    public DCss2Properties setMozStackSizing (final String value, final boolean important) throws DOMException { 
    	return put("-moz-stack-sizing", value, important) ; 
    } 


    public String getMozTransform () { 
    	return get("-moz-transform"); 
    } 
    public DCss2Properties setMozTransform (final String value) throws DOMException { 
    	return put("-moz-transform", value) ; 
    } 
    public DCss2Properties setMozTransform (final String value, final boolean important) throws DOMException { 
    	return put("-moz-transform", value, important) ; 
    } 


    public String getMozTransformOrigin() { 
    	return get("-moz-transform-origin"); 
    } 
    public DCss2Properties setMozTransformOrigin(final String value) throws DOMException { 
    	return put("-moz-transform-origin", value) ; 
    } 
    public DCss2Properties setMozTransformOrigin(final String value, final boolean important) throws DOMException { 
    	return put("-moz-transform-origin", value, important) ; 
    } 


    public String getMozTransition() { 
    	return get("-moz-transition"); 
    } 
    public DCss2Properties setMozTransition(final String value) throws DOMException { 
    	return put("-moz-transition", value) ; 
    } 
    public DCss2Properties setMozTransition(final String value, final boolean important) throws DOMException { 
    	return put("-moz-transition", value, important) ; 
    } 


    public String getMozTransitionDelay() { 
    	return get("-moz-transition-delay"); 
    } 
    public DCss2Properties setMozTransitionDelay(final String value) throws DOMException { 
    	return put("-moz-transition-delay", value) ; 
    } 
    public DCss2Properties setMozTransitionDelay(final String value, final boolean important) throws DOMException { 
    	return put("-moz-transition-delay", value, important) ; 
    } 


    public String getMozTransitionDuration() { 
    	return get("-moz-transition-duration"); 
    } 
    public DCss2Properties setMozTransitionDuration(final String value) throws DOMException { 
    	return put("-moz-transition-duration", value) ; 
    } 
    public DCss2Properties setMozTransitionDuration(final String value, final boolean important) throws DOMException { 
    	return put("-moz-transition-duration", value, important) ; 
    } 


    public String getMozTransitionProperty() { 
    	return get("-moz-transition-property"); 
    } 
    public DCss2Properties setMozTransitionProperty(final String value) throws DOMException { 
    	return put("-moz-transition-property", value) ; 
    } 
    public DCss2Properties setMozTransitionProperty(final String value, final boolean important) throws DOMException { 
    	return put("-moz-transition-property", value, important) ; 
    } 


    public String getMozTransitionTimingFunction() { 
    	return get("-moz-transition-timing-function"); 
    } 
    public DCss2Properties setMozTransitionTimingFunction(final String value) throws DOMException { 
    	return put("-moz-transition-timing-function", value) ; 
    } 
    public DCss2Properties setMozTransitionTimingFunction(final String value, final boolean important) throws DOMException { 
    	return put("-moz-transition-timing-function", value, important) ; 
    } 


    public String getMozUserFocus() { 
    	return get("-moz-user-focus"); 
    } 
    public DCss2Properties setMozUserFocus(final String value) throws DOMException { 
    	return put("-moz-user-focus", value) ; 
    } 
    public DCss2Properties setMozUserFocus(final String value, final boolean important) throws DOMException { 
    	return put("-moz-user-focus", value, important) ; 
    } 


    public String getMozUserInput() { 
    	return get("-moz-user-input"); 
    } 
    public DCss2Properties setMozUserInput(final String value) throws DOMException { 
    	return put("-moz-user-input", value) ; 
    } 
    public DCss2Properties setMozUserInput(final String value, final boolean important) throws DOMException { 
    	return put("-moz-user-input", value, important) ; 
    } 


    public String getMozUserModify() { 
    	return get("-moz-user-modify"); 
    } 
    public DCss2Properties setMozUserModify(final String value) throws DOMException { 
    	return put("-moz-user-modify", value) ; 
    } 
    public DCss2Properties setMozUserModify(final String value, final boolean important) throws DOMException { 
    	return put("-moz-user-modify", value, important) ; 
    } 


    public String getMozUserSelect() { 
    	return get("-moz-user-select"); 
    } 
    public DCss2Properties setMozUserSelect(final String value) throws DOMException { 
    	return put("-moz-user-select", value) ; 
    } 
    public DCss2Properties setMozUserSelect(final String value, final boolean important) throws DOMException { 
    	return put("-moz-user-select", value, important) ; 
    } 


    public String getMozWindowShadow() { 
    	return get("-moz-window-shadow"); 
    } 
    public DCss2Properties setMozWindowShadow(final String value) throws DOMException { 
    	return put("-moz-window-shadow", value) ; 
    } 
    public DCss2Properties setMozWindowShadow(final String value, final boolean important) throws DOMException { 
    	return put("-moz-window-shadow", value, important) ; 
    }  
    
    public String getMozLinearGradient() { 
    	return get("-moz-linear-gradient"); 
    } 
    public DCss2Properties setMozLinearGradient(final String value) throws DOMException { 
    	return put("-moz-linear-gradient", value) ; 
    } 
    public DCss2Properties setMozLinearGradient(final String value, final boolean important) throws DOMException { 
    	return put("-moz-linear-gradient", value, important) ; 
    } 


    public String getMozRadialGradient() { 
    	return get("-moz-radial-gradient"); 
    } 
    public DCss2Properties setMozRadialGradient(final String value) throws DOMException { 
    	return put("-moz-radial-gradient", value) ; 
    } 
    public DCss2Properties setMozRadialGradient(final String value, final boolean important) throws DOMException { 
    	return put("-moz-radial-gradient", value, important) ; 
    } 


    public String getMozRepeatingLinearGradient() { 
    	return get("-moz-repeating-linear-gradient"); 
    } 
    public DCss2Properties setMozRepeatingLinearGradient(final String value) throws DOMException { 
    	return put("-moz-repeating-linear-gradient", value) ; 
    } 
    public DCss2Properties setMozRepeatingLinearGradient(final String value, final boolean important) throws DOMException { 
    	return put("-moz-repeating-linear-gradient", value, important) ; 
    } 
    // End Mozilla extensions
    
    // Start Opera extensions
    public String getXvInterpretAs() { 
    	return get("-xv-interpret-as"); 
    } 
    public DCss2Properties setXvInterpretAs(final String value) throws DOMException { 
    	return put("-xv-interpret-as", value) ; 
    } 
    public DCss2Properties setXvInterpretAs(final String value, final boolean important) throws DOMException { 
    	return put("-xv-interpret-as", value, important) ; 
    } 


    public String getXvPhonemes() { 
    	return get("-xv-phonemes"); 
    } 
    public DCss2Properties setXvPhonemes(final String value) throws DOMException { 
    	return put("-xv-phonemes", value) ; 
    } 
    public DCss2Properties setXvPhonemes(final String value, final boolean important) throws DOMException { 
    	return put("-xv-phonemes", value, important) ; 
    } 


    public String getXvVoiceBalance() { 
    	return get("-xv-voice-balance"); 
    } 
    public DCss2Properties setXvVoiceBalance(final String value) throws DOMException { 
    	return put("-xv-voice-balance", value) ; 
    } 
    public DCss2Properties setXvVoiceBalance(final String value, final boolean important) throws DOMException { 
    	return put("-xv-voice-balance", value, important) ; 
    } 


    public String getXvVoiceDuration() { 
    	return get("-xv-voice-duration"); 
    } 
    public DCss2Properties setXvVoiceDuration(final String value) throws DOMException { 
    	return put("-xv-voice-duration", value) ; 
    } 
    public DCss2Properties setXvVoiceDuration(final String value, final boolean important) throws DOMException { 
    	return put("-xv-voice-duration", value, important) ; 
    } 


    public String getXvVoicePitch() { 
    	return get("-xv-voice-pitch"); 
    } 
    public DCss2Properties setXvVoicePitch(final String value) throws DOMException { 
    	return put("-xv-voice-pitch", value) ; 
    } 
    public DCss2Properties setXvVoicePitch(final String value, final boolean important) throws DOMException { 
    	return put("-xv-voice-pitch", value, important) ; 
    } 


    public String getXvVoicePitchRange() { 
    	return get("-xv-voice-pitch-range"); 
    } 
    public DCss2Properties setXvVoicePitchRange(final String value) throws DOMException { 
    	return put("-xv-voice-pitch-range", value) ; 
    } 
    public DCss2Properties setXvVoicePitchRange(final String value, final boolean important) throws DOMException { 
    	return put("-xv-voice-pitch-range", value, important) ; 
    } 


    public String getXvVoiceRate() { 
    	return get("-xv-voice-rate"); 
    } 
    public DCss2Properties setXvVoiceRate(final String value) throws DOMException { 
    	return put("-xv-voice-rate", value) ; 
    } 
    public DCss2Properties setXvVoiceRate(final String value, final boolean important) throws DOMException { 
    	return put("-xv-voice-rate", value, important) ; 
    } 


    public String getXvVoiceStress() { 
    	return get("-xv-voice-stress"); 
    } 
    public DCss2Properties setXvVoiceStress(final String value) throws DOMException { 
    	return put("-xv-voice-stress", value) ; 
    } 
    public DCss2Properties setXvVoiceStress(final String value, final boolean important) throws DOMException { 
    	return put("-xv-voice-stress", value, important) ; 
    } 


    public String getXvVoiceVolume() { 
    	return get("-xv-voice-volume"); 
    } 
    public DCss2Properties setXvVoiceVolume(final String value) throws DOMException { 
    	return put("-xv-voice-volume", value) ; 
    } 
    public DCss2Properties setXvVoiceVolume(final String value, final boolean important) throws DOMException { 
    	return put("-xv-voice-volume", value, important) ; 
    } 
    // End Opera extensions
    
    //------------------------------------------------------------
    /**
     * A special property to mark a rule to be exposed to CssRealizer, 
     * and the value of the DsfRuleName will be the reference name for
     * the exposed rule
     */
    public String getDsfRuleName() {
    	return get("v4Rule") ;
    }
    
    public DCss2Properties setDsfRuleName(String ruleName) {
    	return put("v4Rule", ruleName) ;
    }
    
    public void removeDsfRuleNameProperty() {
    	m_styleDecl.removeProperty("v4Rule");
    }
    
	/**
	 * A DSF-specific CSS property to store JCssDef variable names to support
	 * bi-directional code generation.
	 */
	public String getDsfJCssRuleName() {
		return get("v4-jcss-rule") ;
	}
	
	public DCss2Properties setDsfJCssRuleName(String ruleName) {
		return put("v4-jcss-rule", ruleName) ;
	}
	
	public void removeDsfJCssRuleNameProperty() {
		m_styleDecl.removeProperty("v4-jcss-rule");
	}

    @Override
	public Object clone() throws CloneNotSupportedException {
    	DCss2Properties copy = (DCss2Properties)super.clone();
    	copy.m_styleDecl = (ICssStyleDeclaration)((DCssStyleDeclaration)m_styleDecl).clone();
    	return copy;
	}
    
    @Override
	public String toString() {
		return getCssValues();
	}
    
    /**
     * Use this to get the CSS text value.
     * @return
     */
    public String getCssValues() {
    	return m_styleDecl.getCssText();
    }
	
	//
	// Private
	//
	protected String get(String propertyName) {
		return m_styleDecl.getPropertyValue(propertyName);
	}
	
	protected DCss2Properties put(String propertyName, String propertyValue) {
		return put(propertyName, propertyValue, false);
	}

	protected DCss2Properties put(String propertyName, String propertyValue, boolean important) {
		if (important) {
			m_styleDecl.setProperty(propertyName, propertyValue, ICssStyleDeclaration.PRIORITY_IMPORTANT);
		}
		else {
			m_styleDecl.setProperty(propertyName, propertyValue, null);
		}
		return this;
	}
}