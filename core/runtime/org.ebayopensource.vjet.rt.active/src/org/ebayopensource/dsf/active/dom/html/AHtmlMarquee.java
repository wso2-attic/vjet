/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DMarquee;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlMarquee;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlMarquee extends AHtmlElement implements HtmlMarquee {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlMarquee(AHtmlDocument doc, DMarquee node) {
		super(doc, node);
		populateScriptable(AHtmlMarquee.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getBehavior() {
		return getDMarquee().getHtmlBehavior();
	}

	public String getBgcolor() {
		return getDMarquee().getHtmlBgColor();
	}

	public String getDirection() {
		return getDMarquee().getHtmlDirection();
	}

	public String getHeight() {
		return getDMarquee().getHtmlHeight();
	}

	public String getHspace() {
		return getDMarquee().getHtmlHSpace();
	}

	public String getLoop() {
		return getDMarquee().getHtmlLoop();
	}

	public String getScrollamount() {
		return getDMarquee().getHtmlScrollAmount();
	}

	public String getScrolldelay() {
		return getDMarquee().getHtmlScrollDelay();
	}

	public String getTruespeed() {
		return getDMarquee().getHtmlTrueSpeed();
	}

	public String getVspace() {
		return getDMarquee().getHtmlVSpace();
	}

	public String getWidth() {
		return getDMarquee().getHtmlWidth();
	}

	public void setBehavior(String behavior) {
		getDMarquee().setHtmlBehavior(behavior);
		onAttrChange(EHtmlAttr.behavior, behavior);
	}

	public void setBgcolor(String bgcolor) {
		getDMarquee().setHtmlBgColor(bgcolor);
		onAttrChange(EHtmlAttr.bgcolor, bgcolor);
	}

	public void setDirection(String direction) {
		getDMarquee().setHtmlDirection(direction);
		onAttrChange(EHtmlAttr.direction, direction);
	}

	public void setHeight(String height) {
		getDMarquee().setHtmlHeight(height);
		onAttrChange(EHtmlAttr.height, height);
	}

	public void setHspace(String hspace) {
		getDMarquee().setHtmlHSpace(hspace);
		onAttrChange(EHtmlAttr.hspace, hspace);
	}

	public void setLoop(String loop) {
		getDMarquee().setHtmlLoop(loop);
		onAttrChange(EHtmlAttr.loop, loop);
	}

	public void setScrollamount(String scrollamount) {
		getDMarquee().setHtmlScrollAmount(scrollamount);
		onAttrChange(EHtmlAttr.scrollamount, scrollamount);
	}

	public void setScrolldelay(String scrolldelay) {
		getDMarquee().setHtmlScrollDelay(scrolldelay);
		onAttrChange(EHtmlAttr.scrolldelay, scrolldelay);
	}

	public void setTruespeed(String truespeed) {
		getDMarquee().setHtmlTrueSpeed(truespeed);
		onAttrChange(EHtmlAttr.truespeed, truespeed);
	}

	public void setVspace(String vspace) {
		getDMarquee().setHtmlVSpace(vspace);
		onAttrChange(EHtmlAttr.vspace, vspace);
	}

	public void setWidth(String width) {
		getDMarquee().setHtmlWidth(width);
		onAttrChange(EHtmlAttr.width, width);
	}

	public void start() {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	private DMarquee getDMarquee() {
		return (DMarquee) getDNode();
	}
}
