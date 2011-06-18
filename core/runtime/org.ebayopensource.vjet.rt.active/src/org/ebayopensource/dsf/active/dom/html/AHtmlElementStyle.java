/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.active.client.ActiveObject;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;
import org.ebayopensource.dsf.css.dom.impl.DCssStyleDeclaration;
import org.ebayopensource.dsf.html.dom.ECssAttr;
import org.ebayopensource.dsf.jsnative.HtmlElementStyle;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

/**
 * This is a synthesized style type for browser dom element.
 * It is not a true node or element. It acts as a proxy to
 * set style on AHtmlElement.
 */
public class AHtmlElementStyle extends ActiveObject implements HtmlElementStyle {

	private static final long serialVersionUID = 1L;
	
	protected final AHtmlElement m_elem;	
	protected ICssStyleDeclaration m_style;
	
	protected AHtmlElementStyle(AHtmlElement elem) {
		m_elem = elem;
		m_style = elem.getHtmlStyle();
		if (m_style == null) {
			m_style = new DCssStyleDeclaration(null);
		}
		
		AHtmlDocument doc = elem.getOwnerDocument();
		populateScriptable(AHtmlElementStyle.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
	public String getVisibility() {
		return getCssValue(ECssAttr.visibility);
	}
	
	public void setVisibility(String value) {
		update(ECssAttr.visibility, value, true);
	}
	
	public String getWidth() {
		return getCssValue(ECssAttr.width);
	}
	
	public void setWidth(String width) {
		update(ECssAttr.width, width, true);
	}
	
	public String getHeight() {
		return getCssValue(ECssAttr.height);
	}
	
	public void setHeight(String height) {
		update(ECssAttr.height, height, true);
	}
	
	public String getBackgroundColor() {
		return getCssValue(ECssAttr.backgroundColor);
	}

	public String getColor() {
		return getCssValue(ECssAttr.color);
	}
	
	public String getContent(){
		return getCssValue(ECssAttr.content);
	}

	public String getCounterIncrement(){
		return getCssValue(ECssAttr.counterIncrement);
	}
	
	public String getCounterReset(){
		return getCssValue(ECssAttr.counterReset);
	}
	
	public String getCssFloat(){
		return getCssValue(ECssAttr.cssFloat);
	}
	
	public String getFontStyle() {
		return getCssValue(ECssAttr.fontStyle);
	}
	
	public String getFontWeight() {
		return getCssValue(ECssAttr.fontWeight);
	}

	public String getAccelerator() {
		return getCssValue(ECssAttr.accelerator);
	}

	public String getBackground() {
		return getCssValue(ECssAttr.background);
	}

	public String getBackgroundAttachment() {
		return getCssValue(ECssAttr.backgroundAttachment);
	}

	public String getBackgroundImage() {
		return getCssValue(ECssAttr.backgroundImage);
	}

	public String getBackgroundPosition() {
		return getCssValue(ECssAttr.backgroundPosition);
	}

	public String getBackgroundPositionX() {
		return getCssValue(ECssAttr.backgroundPositionX);
	}

	public String getBackgroundPositionY() {
		return getCssValue(ECssAttr.backgroundPositionY);
	}

	public String getBackgroundRepeat() {
		return getCssValue(ECssAttr.backgroundRepeat);
	}

	public String getBehavior() {
		return getCssValue(ECssAttr.behavior);
	}

	public String getBorder() {
		return getCssValue(ECssAttr.border);
	}

	public String getBorderBottom() {
		return getCssValue(ECssAttr.borderBottom);
	}
	
	public String getBorderBottomColor() {
		return getCssValue(ECssAttr.borderBottomColor);
	}
	
	public String getBorderBottomStyle() {
		return getCssValue(ECssAttr.borderBottomStyle);
	}
	
	public String getBorderBottomWidth() {
		return getCssValue(ECssAttr.borderBottomWidth);
	}
	
	public String getBorderColor() {
		return getCssValue(ECssAttr.borderColor);
	}
	
	public String getBorderLeft(){
		return getCssValue(ECssAttr.borderLeft);
	}
	
	public String getBorderLeftColor(){
		return getCssValue(ECssAttr.borderLeftColor);
	}
	
	public String getBorderLeftStyle(){
		return getCssValue(ECssAttr.borderLeftStyle);
	}
	
	public String getBorderLeftWidth(){
		return getCssValue(ECssAttr.borderLeftWidth);
	}
	
	public String getBorderRight(){
		return getCssValue(ECssAttr.borderRight);
	}
	
	public String getBorderRightColor(){
		return getCssValue(ECssAttr.borderRightColor);
	}
	
	public String getBorderRightStyle(){
		return getCssValue(ECssAttr.borderRightStyle);
	}
	
	public String getBorderRightWidth(){
		return getCssValue(ECssAttr.borderRightWidth);
	}
	
	public String getBorderStyle(){
		return getCssValue(ECssAttr.borderStyle);
	}
	
	public String getBorderTop(){
		return getCssValue(ECssAttr.borderTop);
	}
	
	public String getBorderTopColor(){
		return getCssValue(ECssAttr.borderTopColor);
	}
	
	public String getBorderTopStyle(){
		return getCssValue(ECssAttr.borderTopStyle);
	}
	
	public String getBorderTopWidth(){
		return getCssValue(ECssAttr.borderTopWidth);
	}

	public String getBorderWidth(){
		return getCssValue(ECssAttr.borderWidth);
	}
	
	public String getBottom() {
		return getCssValue(ECssAttr.bottom);
	}

	public String getClear() {
		return getCssValue(ECssAttr.clear);
	}

	public String getClip() {
		return getCssValue(ECssAttr.clip);
	}

	public String getCssText() {
		return getCssValue(ECssAttr.cssText);
	}

	public String getCursor() {
		return getCssValue(ECssAttr.cursor);
	}

	public String getDirection() {
		return getCssValue(ECssAttr.direction);
	}

	public String getDisplay() {
		return getCssValue(ECssAttr.display);
	}

	public String getFilter() {
		return getCssValue(ECssAttr.filter);
	}

	public String getFont() {
		return getCssValue(ECssAttr.font);
	}

	public String getFontFamily() {
		return getCssValue(ECssAttr.fontFamily);
	}

	public String getFontSize() {
		return getCssValue(ECssAttr.fontSize);
	}

	public String getFontSizeAdjust(){
		return getCssValue(ECssAttr.fontSizeAdjust);
	}
	
	public String getFontStretch(){
		return getCssValue(ECssAttr.fontStretch);
	}
	
	public String getFontVariant() {
		return getCssValue(ECssAttr.fontVariant);
	}

	public String getImeMode() {
		return getCssValue(ECssAttr.imeMode);
	}

	public String getLayoutFlow() {
		return getCssValue(ECssAttr.layoutFlow);
	}

	public String getLayoutGrid() {
		return getCssValue(ECssAttr.layoutGrid);
	}

	public String getLayoutGridChar() {
		return getCssValue(ECssAttr.layoutGridChar);
	}

	public String getLayoutGridLine() {
		return getCssValue(ECssAttr.layoutGridLine);
	}

	public String getLayoutGridMode() {
		return getCssValue(ECssAttr.layoutGridMode);
	}

	public String getLayoutGridType() {
		return getCssValue(ECssAttr.layoutGridType);
	}

	public String getLeft() {
		return getCssValue(ECssAttr.left);
	}

	public String getLetterSpacing() {
		return getCssValue(ECssAttr.letterSpacing);
	}

	public String getLineBreak() {
		return getCssValue(ECssAttr.lineBreak);
	}

	public String getLineHeight() {
		return getCssValue(ECssAttr.lineHeight);
	}

	public String getListStyle() {
		return getCssValue(ECssAttr.listStyle);
	}

	public String getListStyleImage(){
		return getCssValue(ECssAttr.listStyleImage);
	}
	
	public String getListStylePosition(){
		return getCssValue(ECssAttr.listStylePosition);
	}
	
	public String getListStyleType(){
		return getCssValue(ECssAttr.listStyleType);
	}
	
	public String getMargin(){
		return getCssValue(ECssAttr.margin);
	}
	
	public String getMarginBottom(){
		return getCssValue(ECssAttr.marginBottom);
	}
	public String getMarginLeft(){
		return getCssValue(ECssAttr.marginLeft);
	}
	
	public String getMarginRight(){
		return getCssValue(ECssAttr.marginRight);
	}
	
	public String getMarginTop(){
		return getCssValue(ECssAttr.marginTop);
	}

	public String getMarkerOffset(){
		return getCssValue(ECssAttr.markerOffset);
	}
	
	public String getMarks(){
		return getCssValue(ECssAttr.marks);
	}
	
	public String getMaxHeight(){
		return getCssValue(ECssAttr.maxHeight);
	}
	
	public String getMaxWidth(){
		return getCssValue(ECssAttr.maxWidth);
	}
	
	public String getMinHeight(){
		return getCssValue(ECssAttr.minHeight);
	}
	
	public String getMinWidth(){
		return getCssValue(ECssAttr.minWidth);
	}
	
	public String getVerticalAlign(){
		return getCssValue(ECssAttr.verticalAlign);
	}
	
	public String getMozOpacity() {
		return getCssValue(ECssAttr.mozOpacity);
	}

	public String getOverflow() {
		return getCssValue(ECssAttr.overflow);
	}

	public String getPadding() {
		return getCssValue(ECssAttr.padding);
	}

	public String getPaddingBottom(){
		return getCssValue(ECssAttr.paddingBottom);
	}
	
	public String getPaddingLeft(){
		return getCssValue(ECssAttr.paddingLeft);
	}
	
	public String getPaddingRight(){
		return getCssValue(ECssAttr.paddingRight);
	}
	
	public String getPaddingTop(){
		return getCssValue(ECssAttr.paddingTop);
	}
	
	public String getQuotes(){
		return getCssValue(ECssAttr.quotes);
	}
	
	public String getRight(){
		return getCssValue(ECssAttr.right);
	}
	
	public String getOutline(){
		return getCssValue(ECssAttr.outline);
	}
	
	public String getOutlineColor(){
		return getCssValue(ECssAttr.outlineColor);
	}
	
	public String getOutlineStyle(){
		return getCssValue(ECssAttr.outlineStyle);
	}
	
	public String getOutlineWidth(){
		return getCssValue(ECssAttr.outlineWidth);
	}
	
	public String getPosition(){
		return getCssValue(ECssAttr.position);
	}
	
	public String getTextAlign(){
		return getCssValue(ECssAttr.textAlign);
	}
	
	public String getTextDecoration(){
		return getCssValue(ECssAttr.textDecoration);
	}
	
	public String getTextIndent(){
		return getCssValue(ECssAttr.textIndent);
	}
	
	public String getTextShadow(){
		return getCssValue(ECssAttr.textShadow);
	}
	
	public String getTextTransform(){
		return getCssValue(ECssAttr.textTransform);
	}
	
	public String getUnicodeBidi(){
		return getCssValue(ECssAttr.unicodeBidi);
	}
	
	public String getWhiteSpace(){
		return getCssValue(ECssAttr.whiteSpace);
	}
	
	public String getWordSpacing(){
		return getCssValue(ECssAttr.wordSpacing);
	}
	
	public String getTop() {
		return getCssValue(ECssAttr.top);
	}

	public String getZIndex() {
		return getCssValue(ECssAttr.zIndex);
	}

	public void setAccelerator(String accelerator) {
		update(ECssAttr.accelerator, accelerator, true);
	}

	public void setBackground(String background) {
		update(ECssAttr.background, background, true);
	}

	public void setBackgroundColor(String color) {
		update(ECssAttr.backgroundColor, color, true);
	}

	public void setBackgroundAttachment(String backgroundAttachment) {
		update(ECssAttr.backgroundAttachment, backgroundAttachment, true);
	}

	public void setBackgroundImage(String backgroundImage) {
		update(ECssAttr.backgroundImage, backgroundImage, true);
		
	}

	public void setBackgroundPosition(String backgroundPosition) {
		update(ECssAttr.backgroundPosition, backgroundPosition, true);
		
	}

	public void setBackgroundPositionX(String backgroundPositionX) {
		update(ECssAttr.backgroundPositionX, backgroundPositionX, true);
	}

	public void setBackgroundPositionY(String backgroundPositionY) {
		update(ECssAttr.backgroundPositionY, backgroundPositionY, true);
	}

	public void setBackgroundRepeat(String backgroundRepeat) {
		update(ECssAttr.backgroundRepeat, backgroundRepeat, true);
	}

	public void setBehavior(String behavior) {
		update(ECssAttr.behavior, behavior, true);
	}

	public void setBorder(String border) {
		update(ECssAttr.border, border, true);
	}

	public void setBorderBottom(String borderBottom) {
		update(ECssAttr.borderBottom, borderBottom, true);
	}
	
	public void setBorderBottomColor(String borderBottomColor) {
		update(ECssAttr.borderBottomColor, borderBottomColor, true);
	}
	
	public void setBorderBottomStyle(String borderBottomStyle) {
		update(ECssAttr.borderBottomStyle, borderBottomStyle, true);
	}
	
	public void setBorderBottomWidth(String borderBottomWidth) {
		update(ECssAttr.borderBottomWidth, borderBottomWidth, true);
	}
	
	public void setBorderColor(String borderColor) {
		update(ECssAttr.borderColor, borderColor, true);
	}
	
	public void setBorderLeft(String borderLeft){
		update(ECssAttr.borderLeft, borderLeft, true);
	}
	
	public void setBorderLeftColor(String borderLeftColor){
		update(ECssAttr.borderLeftColor, borderLeftColor, true);
	}
	
	public void setBorderLeftStyle(String borderLeftStyle){
		update(ECssAttr.borderLeftStyle, borderLeftStyle, true);
	}
	
	public void setBorderLeftWidth(String borderLeftWidth){
		update(ECssAttr.borderLeftWidth, borderLeftWidth, true);
	}
	
	public void setBorderRight(String borderRight){
		update(ECssAttr.borderRight, borderRight, true);
	}
	
	public void setBorderRightColor(String borderRightColor){
		update(ECssAttr.borderRightColor, borderRightColor, true);
	}
	
	public void setBorderRightStyle(String borderRightStyle){
		update(ECssAttr.borderRightStyle, borderRightStyle, true);
	}
	
	public void setBorderRightWidth(String borderRightWidth){
		update(ECssAttr.borderRightWidth, borderRightWidth, true);
	}
	
	public void setBorderStyle(String borderStyle){
		update(ECssAttr.borderStyle, borderStyle, true);
	}
	
	public void setBorderTop(String borderTop){
		update(ECssAttr.borderTop, borderTop, true);
	}
	
	public void setBorderTopColor(String borderTopColor){
		update(ECssAttr.borderTopColor, borderTopColor, true);
	}
	
	public void setBorderTopStyle(String borderTopStyle){
		update(ECssAttr.borderTopStyle, borderTopStyle, true);
	}
	
	public void setBorderTopWidth(String borderTopWidth){
		update(ECssAttr.borderTopWidth, borderTopWidth, true);
	}
	
	public void setBorderWidth(String borderWidth){
		update(ECssAttr.borderWidth, borderWidth, true);
	}
	
	public void setBottom(String bottom) {
		update(ECssAttr.bottom, bottom, true);
	}
	
	public void setColor(String color) {
		update(ECssAttr.color, color, true);
	}
	
	public void setContent(String content) {
		update(ECssAttr.content, content, true);
	}
	
	public void setCounterIncrement(String counterIncrement) {
		update(ECssAttr.counterIncrement, counterIncrement, true);
	}
	
	public void setCounterReset(String counterRest) {
		update(ECssAttr.counterReset, counterRest, true);
	}
	public void setClear(String clear) {
		update(ECssAttr.clear, clear, true);
	}

	public void setClip(String clip) {
		update(ECssAttr.clip, clip, true);
	}

	public void setCssFloat(String cssFloat) {
		update(ECssAttr.cssFloat, cssFloat, true);
	}
	
	public void setCssText(String cssText) {
		update(ECssAttr.cssText, cssText, true);
	}

	public void setCursor(String cursor) {
		update(ECssAttr.cursor, cursor, true);
	}

	public void setDirection(String direction) {
		update(ECssAttr.direction, direction, true);
	}

	public void setDisplay(String display) {
		update(ECssAttr.display, display, true);
	}

	public void setFilter(String filter) {
		update(ECssAttr.filter, filter, true);
	}

	public void setFont(String font) {
		update(ECssAttr.font, font, true);
	}
	
	public void setFontFamily(String fontFamily) {
		update(ECssAttr.fontFamily, fontFamily, true);
	}

	public void setFontSize(String fontSize) {
		update(ECssAttr.fontSize, fontSize, true);
	}

	public void setFontSizeAdjust(String fontSizeAdjust){
		update(ECssAttr.fontSizeAdjust, fontSizeAdjust, true);
	}
	
	public void setFontStretch(String fontStretch){
		update(ECssAttr.fontStretch, fontStretch, true);
	}
	
	public void setFontStyle(String fontStyle) {
		update(ECssAttr.fontStyle, fontStyle, true);
	}

	public void setFontVariant(String fontVariant) {
		update(ECssAttr.fontVariant, fontVariant, true);
	}
	
	public void setFontWeight(String fontWeight) {
		update(ECssAttr.fontWeight, fontWeight, true);
	}
	
	public void setImeMode(String imeMode) {
		update(ECssAttr.imeMode, imeMode, true);
	}

	public void setLayoutFlow(String layoutFlow) {
		update(ECssAttr.layoutFlow, layoutFlow, true);
	}

	public void setLayoutGrid(String layoutGrid) {
		update(ECssAttr.layoutGrid, layoutGrid, true);
	}

	public void setLayoutGridChar(String layoutGridChar) {
		update(ECssAttr.layoutGridChar, layoutGridChar, true);
	}

	public void setLayoutGridLine(String layoutGridLine) {
		update(ECssAttr.layoutGridLine, layoutGridLine, true);
	}

	public void setLayoutGridMode(String layoutGridMode) {
		update(ECssAttr.layoutGridMode, layoutGridMode, true);
	}

	public void setLayoutGridType(String layoutGridType) {
		update(ECssAttr.layoutGridType, layoutGridType, true);
	}

	public void setLeft(String left) {
		update(ECssAttr.left, left, true);
	}

	public void setLetterSpacing(String letterSpacing) {
		update(ECssAttr.letterSpacing, letterSpacing, true);
	}

	public void setLineBreak(String lineBreak) {
		update(ECssAttr.lineBreak, lineBreak, true);
	}

	public void setLineHeight(String lineHeight) {
		update(ECssAttr.lineHeight, lineHeight, true);
	}

	public void setListStyle(String listStyle) {
		update(ECssAttr.listStyle, listStyle, true);
	}
	
	public void setListStyleImage(String listStyleImage){
		update(ECssAttr.listStyleImage, listStyleImage, true);
	}

	public void setListStylePosition(String listStylePosition){
		update(ECssAttr.listStylePosition, listStylePosition, true);
	}
	
	public void setListStyleType(String listStyleType){
		update(ECssAttr.listStyleType, listStyleType, true);
	}
	
	public void setMargin(String margin){
		update(ECssAttr.margin, margin, true);
	}
	
	public void setMarginBottom(String marginBottom){
		update(ECssAttr.marginBottom, marginBottom, true);
	}
	
	public void setMarginLeft(String marginLeft){
		update(ECssAttr.marginLeft, marginLeft, true);
	}
	
	public void setMarginRight(String marginRight){
		update(ECssAttr.marginRight, marginRight, true);
	}
	
	public void setMarginTop(String marginTop){
		update(ECssAttr.marginTop, marginTop, true);
	}	
	
	public void setMarkerOffset(String markerOffset){
		update(ECssAttr.markerOffset, markerOffset, true);
	}
	
	public void setMarks(String marks){
		update(ECssAttr.marks, marks, true);
	}
	
	public void setMaxHeight(String maxHeight){
		update(ECssAttr.maxHeight, maxHeight, true);
	}
	
	public void setMaxWidth(String maxWidth){
		update(ECssAttr.maxWidth, maxWidth, true);
	}
	public void setMinHeight(String minHeight){
		update(ECssAttr.minHeight, minHeight, true);
	}
	
	public void setMinWidth(String minWidth){
		update(ECssAttr.minWidth, minWidth, true);
	}
	
	public void setVerticalAlign(String verticalAlign){
		update(ECssAttr.verticalAlign, verticalAlign, true);
	}
	
	public void setMozOpacity(String mozOpacity) {
		update(ECssAttr.mozOpacity, mozOpacity, true);
	}

	public void setOutline(String outline){
		update(ECssAttr.outline, outline, true);
	}
	
	public void setOutlineColor(String outlineColor){
		update(ECssAttr.outlineColor, outlineColor, true);
	}
	
	public void setOutlineStyle(String outlineStyle){
		update(ECssAttr.outlineStyle, outlineStyle, true);
	}
	
	public void setOutlineWidth(String outlineWidth){
		update(ECssAttr.outlineWidth, outlineWidth, true);
	}
	
	public void setOverflow(String overflow) {
		update(ECssAttr.overflow, overflow, true);
	}

	public void setPosition(String position){
		update(ECssAttr.position, position, true);
	}
	
	public void setPadding(String padding) {
		update(ECssAttr.padding, padding, true);
	}

	public void setPaddingBottom(String paddingBottom) {
		update(ECssAttr.paddingBottom, paddingBottom, true);
	}
	public void setPaddingLeft(String paddingLeft) {
		update(ECssAttr.paddingLeft, paddingLeft, true);
	}
	
	public void setPaddingRight(String paddingRight) {
		update(ECssAttr.paddingRight, paddingRight, true);
	}
	
	public void setPaddingTop(String paddingTop) {
		update(ECssAttr.paddingTop, paddingTop, true);
	}

	public void setQuotes(String quotes){
		update(ECssAttr.quotes, quotes, true);
	}
	
	public void setRight(String right){
		update(ECssAttr.right, right, true);
	}
	
	public void setTextAlign(String textAlign){
		update(ECssAttr.textAlign, textAlign, true);
	}
	public void setTextDecoration(String textDecoration){
		update(ECssAttr.textDecoration, textDecoration, true);
	}
	
	public void setTextIndent(String textIndent){
		update(ECssAttr.textIndent, textIndent, true);
	}
	
	public void setTextShadow(String textShadow){
		update(ECssAttr.textShadow, textShadow, true);
	}
	
	public void setTextTransform(String textTransform){
		update(ECssAttr.textTransform, textTransform, true);
	}
	
	public void setUnicodeBidi(String unicodeBidi){
		update(ECssAttr.unicodeBidi, unicodeBidi, true);
	}
	
	public void setWhiteSpace(String whiteSpace){
		update(ECssAttr.whiteSpace, whiteSpace, true);
	}
	
	public void setWordSpacing(String wordSpacing){
		update(ECssAttr.wordSpacing, wordSpacing, true);
	}
	
	public void setTop(String top) {
		update(ECssAttr.top, top, true);
	}


	public void setZIndex(Object zIndex) {
		String value = "";
		if(zIndex instanceof String){
			value = (String)zIndex;
		}else if(zIndex instanceof Number){
			value = String.valueOf(zIndex);
			if(value.contains(".")){
				value = value.substring(0, value.indexOf("."));
			}
		}else{
			value = String.valueOf(zIndex);
		}
		
		update(ECssAttr.zIndex, value, false);
	}
	
	
	

	public Object getPropertyValue(String name) {
		return getCssValue(name);
	}
	
	public String getOpacity() {
		return getCssValue(ECssAttr.opacity);
	}

	public void setOpacity(String opacity) {
		update(ECssAttr.opacity, opacity, true);
	}

	public String getZoom() {
		return getCssValue(ECssAttr.zoom);
	}

	public void setZoom(String zoom) {
		update(ECssAttr.zoom, zoom, true);
	}

	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		}
		else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}
	
	protected void update(ECssAttr attr, String value, boolean isString) {
		String name = attr.cssName();
		if (value != null && value.length() > 0) {
			//FIXME Temp fix for CSS property in the form of chroma(color=\"white\")
			if (value.indexOf('=') == -1) {
				m_style.setProperty(name, value, null);
			}
		} else {
			m_style.removeProperty(name);
		}
		m_elem.setHtmlStyle(m_style);
		if (isString) {
			value = "'" + value + "'";
		}
		m_elem.onStyleChange(attr.domName(), value);
	}
	
	protected String getCssValue(ECssAttr attr) {
		return getCssValue(attr.cssName());
	}
	
	private String getCssValue(String name) {
		return m_style.getPropertyValue(name);
	}

	@Override
	public Object getAttribute(String sAttrName, int iFlags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttribute(String sAttrName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getExpression(String sPropertyName) {
		// TODO Auto-generated method stub
		return null;
	}


}
