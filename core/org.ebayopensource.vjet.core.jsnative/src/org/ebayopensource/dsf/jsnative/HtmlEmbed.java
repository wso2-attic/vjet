/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * http://www.html-reference.com/EMBED.htm
 *
 */
@Alias("HTMLEmbedElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlEmbed extends HtmlElement {
	
	public static final String ALIGN_ABSBOTTOM = "absbottom" ;
	public static final String ALIGN_ABSMIDDLE = "absmiddle" ;
	public static final String ALIGN_BASELINE = "baseline" ;
	public static final String ALIGN_LEFT = "left" ;
	public static final String ALIGN_MIDDLE = "middle" ;
	public static final String ALIGN_RIGHT = "right" ; 
	public static final String ALIGN_TEXTTOP = "texttop" ; 
	public static final String ALIGN_TOP = "top" ; 
	
	public static final String HIDDEN_FALSE = "false" ;
	public static final String HIDDEN_TRUE = "true" ;
	
	public static final String LANGUAGE_JSCRIPT = "Jscript" ;
	public static final String LANGUAGE_JAVASCRIPT = "javascript" ; 
	public static final String LANGUAGE_VBS = "vbs" ; 
	public static final String LANGUAGE_VBSCRIPT = "vbscript" ; 
	public static final String LANGUAGE_XML = "XML" ;
	
	public static final String UNITS_PX = "px" ; // pixels
	public static final String UNITS_EM = "em" ; // relative to the element's font
	
	public static final String UNSELECTABLE_OFF = "off" ; 
	public static final String UNSELECTABLE_ON = "on" ; 
	
	@Property  String getAccessKey();
	@Property  void setAccessKey(String accessKey);

	@Property  String getAlign();
	@Property  void setAlign(String align);

	@Property String getAtomicSelection();
	@Property void setAtomicSelection(String atomic);
	
	@Property String getHeight();
	@Property void setHeight(String height);
	
	@Property String getHidden();
	@Property void setHidden(String hidden);
	
	@Property String getHideFocus();
	@Property void setHideFocus(String hidefocus);
	
	@Property String getLanguage();
	@Property void setLanguage(String language);
	
	@Property String getName();
	@Property void setName(String name);
	
	@Property String getPluginsPage();
	@Property void setPluginsPage(String pluginspage);
	
	@Property String getSrc();
	@Property void setSrc(String src);
	
	@Property String getType();
	@Property void setType(String type);
	
	@Property String getUnits();
	@Property void setUnits(String units);
	
	@Property String getUnselectable();
	@Property void setUnselectable(String unselectable);
	
	@Property String getWidth();
	@Property void setWidth(String width);
}
