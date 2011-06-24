/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DBody;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlBody;
import org.ebayopensource.dsf.jsnative.TextRange;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlBody extends AHtmlElement implements HtmlBody {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlBody(AHtmlDocument doc, DBody body) {
		super(doc, body);
		populateScriptable(AHtmlBody.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getALink() {
		return getDBody().getHtmlAlink();
	}

	public String getBackground() {
		return getDBody().getHtmlBackground();
	}

	public String getBgColor() {
		return getDBody().getHtmlBgColor();
	}

	public String getLink() {
		return getDBody().getHtmlLink();
	}

	public String getText() {
		return getDBody().getHtmlText();
	}

	public String getVLink() {
		return getDBody().getHtmlVlink();
	}

	public void setALink(String aLink) {
		getDBody().setHtmlAlink(aLink);
		onAttrChange(EHtmlAttr.alink, aLink);
	}

	public void setBackground(String background) {
		getDBody().setHtmlBackground(background);
		onAttrChange(EHtmlAttr.background, background);
	}

	public void setBgColor(String bgColor) {
		getDBody().setHtmlBgColor(bgColor);
		onAttrChange(EHtmlAttr.bgcolor, bgColor);
	}

	public void setLink(String link) {
		getDBody().setHtmlLink(link);
		onAttrChange(EHtmlAttr.link, link);
	}

	public void setText(String text) {
		getDBody().setHtmlText(text);
		onAttrChange(EHtmlAttr.text, text);
	}

	public void setVLink(String vlink) {
		getDBody().setHtmlVlink(vlink);
		onAttrChange(EHtmlAttr.vlink, vlink);
	}
	
	// Since property name is 'onkeydown', Rhino invokes this method.
	public Object getOnkeydown() {
		return getOnKeyDown();
	}
	
	// For Rhino
	public void setOnkeydown(Object functionRef) {
		setOnKeyDown(functionRef);
	}
	
	// Since property name is 'onkeypress', Rhino invokes this method.
	public Object getOnkeypress() {
		return getOnKeyPress();
	}
	
	// For Rhino
	public void setOnkeypress(Object functionRef) {
		setOnKeyPress(functionRef);
	}
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnkeyup() {
		return getOnKeyUp();
	}
	
	// For Rhino
	public void setOnkeyup(Object functionRef) {
		setOnKeyUp(functionRef);
	}
	
	// Since property name is 'onresize', Rhino invokes this method.
	public Object getOnresize() {
		return getOnResize();
	}
	
	// For Rhino
	public void setOnresize(Object functionRef) {
		setOnResize(functionRef);
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
	
	// Since property name is 'onclick', Rhino invokes this method.
	public Object getOnclick() {
		return getOnClick();
	}
	
	// For Rhino
	public void setOnclick(Object functionRef) {
		setOnClick(functionRef);
	}
	
	// Since property name is 'ondblclick', Rhino invokes this method.
	public Object getOndblclick() {
		return getOnDblClick();
	}
	
	// For Rhino
	public void setOndblclick(Object functionRef) {
		setOnDblClick(functionRef);
	}
	
	// Since property name is 'onmousedown', Rhino invokes this method.
	public Object getOnmousedown() {
		return getOnMouseDown();
	}
	
	// For Rhino
	public void setOnmousedown(Object functionRef) {
		setOnMouseDown(functionRef);
	}
	
	// Since property name is 'onmousemove', Rhino invokes this method.
	public Object getOnmousemove() {
		return getOnMouseMove();
	}
	
	// For Rhino
	public void setOnmousemove(Object functionRef) {
		setOnMouseMove(functionRef);
	}
	
	// Since property name is 'onmouseout', Rhino invokes this method.
	public Object getOnmouseout() {
		return getOnMouseOut();
	}
	
	// For Rhino
	public void setOnmouseout(Object functionRef) {
		setOnMouseOut(functionRef);
	}
	
	// Since property name is 'onmouseover', Rhino invokes this method.
	public Object getOnmouseover() {
		return getOnMouseOver();
	}
	
	// For Rhino
	public void setOnmouseover(Object functionRef) {
		setOnMouseOver(functionRef);
	}
	
	// Since property name is 'onmouseup', Rhino invokes this method.
	public Object getOnmouseup() {
		return getOnMouseUp();
	}
	
	// For Rhino
	public void setOnmouseup(Object functionRef) {
		setOnMouseUp(functionRef);
	}
	
	private DBody getDBody() {
		return (DBody) getDNode();
	}

	@Override
	public TextRange createTextRange() {
		// TODO implement adom for this
			return null; 
		
	}

	@Override
	public TextRange createControlRange() {
		// TODO Auto-generated method stub
		return null;
	}
}
