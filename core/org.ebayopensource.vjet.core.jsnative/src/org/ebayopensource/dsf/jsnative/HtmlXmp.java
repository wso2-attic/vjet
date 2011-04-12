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
 * http://www.htmlref.com/Reference/AppA/tag_xmp.htm
 *
 */
@Alias("HTMLXmpElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlXmp extends HtmlElement {
	
	public static final String LANGUAGE_JSCRIPT = "Jscript" ;
	public static final String LANGUAGE_JAVASCRIPT = "javascript" ; 
	public static final String LANGUAGE_VBS = "vbs" ; 
	public static final String LANGUAGE_VBSCRIPT = "vbscript" ; 
	public static final String LANGUAGE_XML = "XML" ;
	
	public static final String UNSELECTABLE_ON = "on" ; 
	public static final String UNSELECTABLE_OFF = "off" ; 
	
	@Property  String getAccessKey();
	@Property  void setAccessKey(String accessKey);

	@Property String getAtomicSelection();
	@Property void setAtomicSelection(String atomic);
	
	@Property String getContentEditable();
	@Property void setContentEditable(String contenteditable);
	
	@Property String getHideFocus();
	@Property void setHideFocus(String hidefocus);
	
	@Property String getLanguage();
	@Property void setLanguage(String language);
	
	@Property int getTabIndex();
	@Property void setTabIndex(String tabindex);
	
	@Property String getUnselectable();
	@Property void setUnselectable(String unselectable);
	
}
