/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DFrame;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.HtmlFrame;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlFrame extends AHtmlElement implements HtmlFrame {
	
	private static final long serialVersionUID = 1L;
	private AHtmlDocument m_contentDocument = null;

	protected AHtmlFrame(AHtmlDocument doc, DFrame node) {
		super(doc, node);
		populateScriptable(AHtmlFrame.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
	public HtmlDocument getDocument() {
		return m_contentDocument;
	}
	
	public HtmlDocument getContentDocument() {
		return m_contentDocument;
	}

	public String getFrameBorder() {
		return getDFrame().getHtmlFrameBorder();
	}

	public String getLongDesc() {
		return getDFrame().getHtmlLongDesc();
	}

	public String getMarginHeight() {
		return getDFrame().getHtmlMarginHeight();
	}

	public String getMarginWidth() {
		return getDFrame().getHtmlMarginWidth();
	}

	public String getName() {
		return getDFrame().getHtmlName();
	}

	public boolean getNoResize() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.noresize,getHtmlAttribute(EHtmlAttr.noresize));
	}

	/**
	 * Returns whether or not a frame should have scrollbars
	 * @return string of "yes" or "no"
	 * @see DFrame.SCROLLING_YES and DFrame.SCROLLING_NO
	 */
	public String getScrolling() {
		return getDFrame().getHtmlScrolling();
	}

	public String getSrc() {
		return getDFrame().getHtmlSrc();
	}

	public void setFrameBorder(String frameBorder) {
		getDFrame().setHtmlFrameBorder(frameBorder);
		onAttrChange(EHtmlAttr.frameborder, frameBorder);
	}

	public void setLongDesc(String longDesc) {
		getDFrame().setHtmlLongDesc(longDesc);
		onAttrChange(EHtmlAttr.longdesc, longDesc);
	}

	public void setMarginHeight(String marginHeight) {
		getDFrame().setHtmlMarginHeight(marginHeight);
		onAttrChange(EHtmlAttr.marginheight, marginHeight);
	}

	public void setMarginWidth(String marginWidth) {
		getDFrame().setHtmlMarginWidth(marginWidth);
		onAttrChange(EHtmlAttr.marginwidth, marginWidth);
	}

	public void setName(String name) {
		getDFrame().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setNoResize(boolean noResize) {
		setHtmlAttribute(EHtmlAttr.noresize, noResize);
		onAttrChange(EHtmlAttr.noresize, noResize);
	}

	/**
	 * Sets whether or not a frame should have scrollbars
	 * @param scrolling string of "yes" or "no"
	 * @see DFrame.SCROLLING_YES and DFrame.SCROLLING_NO
	 */
	public void setScrolling(String scrolling) {
		getDFrame().setHtmlScrolling(scrolling);
		onAttrChange(EHtmlAttr.scrolling, scrolling);
	}

	public void setSrc(String src) {
		getDFrame().setHtmlSrc(src);
		setContentDocument(AHtmlHelper.getContentDocument(this, src));
		onAttrChange(EHtmlAttr.src, src);
	}
	
	// Since property name is 'onblur', Rhino invokes this method.
	public Object getOnblur() {
		return getOnBlur();
	}
	
	// Since property name is 'onfocus', Rhino invokes this method.
	public Object getOnfocus() {
		return getOnFocus();
	}
	
	// For Rhino
	public void setOnblur(Object functionRef) {
		setOnBlur(functionRef);
	}
	
	// For Rhino
	public void setOnfocus(Object functionRef) {
		setOnFocus(functionRef);
	}
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnload() {
		return getOnLoad();
	}
	
	// For Rhino
	public void setOnload(Object functionRef) {
		setOnLoad(functionRef);
	}
	
	// Since property name is 'onunload', Rhino invokes this method.
	public Object getOnunload() {
		return getOnLoad();
	}
	
	// For Rhino
	public void setOnunload(Object functionRef) {
		setOnLoad(functionRef);
	}
	
	private DFrame getDFrame() {
		return (DFrame) getDNode();
	}

	void setContentDocument(AHtmlDocument frameDoc) {
		m_contentDocument = frameDoc;
	}
}
