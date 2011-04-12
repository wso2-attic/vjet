/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum provides a mapping between style properties in DOM and 
 * its corresponding CSS properties.
 *
 */
public enum ECssAttr {
	FIRST("__first__", "dummy"),	// MUST ALWAYS BE THE FIRST ENUM
	
	// CSS Properties defined by CSS 2.1 Specification (http://www.w3.org/TR/CSS21)
	azimuth("azimuth", "azimuth"),
	background("background", "background"),
	backgroundAttachment("backgroundAttachment", "background-attachment"),
	backgroundColor("backgroundColor", "background-color"),
	backgroundImage("backgroundImage", "background-image"),
	backgroundPosition("backgroundPosition", "background-position"),
	backgroundRepeat("backgroundRepeat", "background-repeat"),
	border("border", "border"),
	borderCollapse("borderCollapse", "border-collapse"),
	borderColor("borderColor", "border-color"),
	borderSpacing("borderSpacing", "border-spacing"),
	borderStyle("borderStyle", "border-style"),
	borderTop("borderTop", "border-top"),
    borderRight("borderRight","border-right"),
    borderBottom("borderBottom","border-bottom"),
    borderLeft("borderLeft","border-left"),
    borderTopColor("borderTopColor","border-top-color"),
    borderRightColor("borderRightColor","border-right-color"),
    borderBottomColor("borderBottomColor","border-bottom-color"),
    borderLeftColor("borderLeftColor","border-left-color"),
    borderTopStyle("borderTopStyle","border-top-style"),
    borderRightStyle("borderRightStyle","border-right-style"),
    borderBottomStyle("borderBottomStyle","border-bottom-style"),
    borderLeftStyle("borderLeftStyle","border-left-style"),
    borderTopWidth("borderTopWidth","border-top-width"),
    borderRightWidth("borderRightWidth","border-right-width"),
    borderBottomWidth("borderBottomWidth","border-bottom-width"),
    borderLeftWidth("borderLeftWidth","border-left-width"),
    borderWidth("borderWidth","border-width"),
    bottom("bottom","bottom"),
    captionSide("captionSide","caption-side"),
    clear("clear","clear"),
    clip("clip","clip"),
    color("color","color"),
    content("content","content"),
    counterIncrement("counterIncrement","counter-increment"),
    counterReset("counterReset","counter-reset"),
    cue("cue","cue"),
    cueAfter("cueAfter","cue-after"),
    cueBefore("cueBefore","cue-before"),
    cursor("cursor","cursor"),
    direction("direction","direction"),
    display("display","display"),
    elevation("elevation","elevation"),
    emptyCells("emptyCells","empty-cells"),
    cssFloat("cssFloat","float"),
    font("font","font"),
    fontFamily("fontFamily","font-family"),
    fontSize("fontSize","font-size"),
    fontSizeAdjust("fontSizeAdjust","font-size-adjust"),
    fontStretch("fontStretch","font-stretch"),
    fontStyle("fontStyle","font-style"),
    fontVariant("fontVariant","font-variant"),
    fontWeight("fontWeight","font-weight"),
    height("height","height"),
    left("left","left"),
    letterSpacing("letterSpacing","letter-spacing"),
    lineHeight("lineHeight","line-height"),
    listStyle("listStyle","list-style"),
    listStyleImage("listStyleImage","list-style-image"),
    listStylePosition("listStylePosition","list-style-position"),
    listStyleType("listStyleType","list-style-type"),
    margin("margin","margin"),
    marginTop("marginTop","margin-top"),
    marginRight("marginRight","margin-right"),
    marginBottom("marginBottom","margin-bottom"),
    marginLeft("marginLeft","margin-left"),
    markerOffset("markerOffset","marker-offset"),
    marks("marks","marks"),
    maxHeight("maxHeight","max-height"),
    maxWidth("maxWidth","max-width"),
    minHeight("minHeight","min-height"),
    minWidth("minWidth","min-width"),
    orphans("orphans","orphans"),
    outline("outline","outline"),
    outlineColor("outlineColor","outline-color"),
    outlineStyle("outlineStyle","outline-style"),
    outlineWidth("outlineWidth","outline-width"),
    overflow("overflow","overflow"),
    padding("padding","padding"),
    paddingTop("paddingTop","padding-top"),
    paddingRight("paddingRight","padding-right"),
    paddingBottom("paddingBottom","padding-bottom"),
    paddingLeft("paddingLeft","padding-left"),
    page("page","page"),
    pageBreakAfter("pageBreakAfter","page-break-after"),
    pageBreakBefore("pageBreakBefore","page-break-before"),
    pageBreakInside("pageBreakInside","page-break-inside"),
    pause("pause","pause"),
    pauseAfter("pauseAfter","pause-after"),
    pauseBefore("pauseBefore","pause-before"),
    pitch("pitch","pitch"),
    pitchRange("pitchRange","pitch-range"),
    playDuring("playDuring","play-during"),
    position("position","position"),
    quotes("quotes","quotes"),
    richness("richness","richness"),
    right("right","right"),
    size("size","size"),
    speak("speak","speak"),
    speakHeader("speakHeader","speak-header"),
    speakNumeral("speakNumeral","speak-numeral"),
    speakPunctuation("speakPunctuation","speak-punctuation"),
    speechRate("speechRate","speech-rate"),
    stress("stress","stress"),
    tableLayout("tableLayout","table-layout"),
    textAlign("textAlign","text-align"),
    textDecoration("textDecoration","text-decoration"),
    textIndent("textIndent","text-indent"),
    textShadow("textShadow","text-shadow"),
    textTransform("textTransform","text-transform"),
    top("top","top"),
    unicodeBidi("unicodeBidi","unicode-bidi"),
    verticalAlign("verticalAlign","vertical-align"),
    visibility("visibility","visibility"),
    voiceFamily("voiceFamily","voice-family"),
    volume("volume","volume"),
    whiteSpace("whiteSpace","white-space"),
    widows("widows","widows"),
    width("width","width"),
    wordSpacing("wordSpacing","word-spacing"),
    zIndex("zIndex","z-index"),
    
    // CSS Properties defined in IE
    accelerator("accelerator","accelerator"),
    backgroundPositionX("backgroundPositionX", "background-position-x"),
    backgroundPositionY("backgroundPositionY", "background-position-y"),
    behavior("behavior", "behavior"),
    cssText("cssText", "css-text"),
    filter("filter","filter"),
    imeMode("imeMode","ime-mode"),
    layoutFlow("layoutFlow","layout-flow"),
    layoutGrid("layoutGrid","layout-grid"),
    layoutGridChar("layoutGridChar","layout-grid-char"),
    layoutGridLine("layoutGridLine", "layout-grid-line"),
    layoutGridMode("layoutGridMode", "layout-grid-mode"),
    layoutGridType("layoutGridType","layout-grid-type"),
    lineBreak("lineBreak", "line-break"),
    zoom("zoom","zoom"),
    
    // CSS Properties defined in FireFox
    mozOpacity("MozOpacity","moz-opacity"),
    opacity("opacity","opacity"),
	
	LAST("__last__", "dummy") ;  // MUST ALWAYS BE THE LAST
	
	private final String m_cssPropName;
	private final String m_propertyName;
	
	ECssAttr(final String propertyName, final String cssAttrName) {
		m_propertyName = propertyName;
		m_cssPropName = cssAttrName;
	}
	
	public String cssName() { 
		return m_cssPropName ;
	}
	
	public String domName() { 
		return m_propertyName ;
	}
	
	public static ECssAttr findByCssName(String cssName) {
		if (s_lookupByCssName == null) {
			loadLookupByCssName();
		}
		return s_lookupByCssName.get(cssName);
	}

	private static volatile Map<String, ECssAttr> s_lookupByCssName = null;	
	private static synchronized void loadLookupByCssName() {
		if (s_lookupByCssName == null) {
			Map<String, ECssAttr> lookup = new HashMap<String, ECssAttr>(199);
			for (ECssAttr ec : ECssAttr.values()) {
				lookup.put(ec.cssName(), ec);
			}
			s_lookupByCssName = lookup;
		}
	}
}
