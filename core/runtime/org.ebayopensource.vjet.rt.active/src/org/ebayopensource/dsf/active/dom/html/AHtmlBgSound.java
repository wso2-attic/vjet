/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DBgSound;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlBgSound;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlBgSound extends AHtmlElement implements HtmlBgSound {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlBgSound(AHtmlDocument doc, DBgSound node) {
		super(doc, node);
		populateScriptable(AHtmlBgSound.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getBalance() {
		return getDBgSound().getHtmlBalance();
	}

	public String getLoop() {
		return getDBgSound().getHtmlLoop();
	}

	public String getSrc() {
		return getDBgSound().getHtmlSrc();
	}

	public String getVolume() {
		return getDBgSound().getHtmlVolume();
	}

	public void setBalance(String balance) {
		getDBgSound().setHtmlBalance(balance);
		onAttrChange(EHtmlAttr.balance, balance);
	}

	public void setLoop(String loop) {
		getDBgSound().setHtmlLoop(loop);
		onAttrChange(EHtmlAttr.loop, loop);
	}

	public void setSrc(String src) {
		getDBgSound().setHtmlSrc(src);
		onAttrChange(EHtmlAttr.src, src);
	}

	public void setVolume(String volume) {
		getDBgSound().setHtmlVolume(volume);
		onAttrChange(EHtmlAttr.volume, volume);
	}
	
	private DBgSound getDBgSound() {
		return (DBgSound) getDNode();
	}
}
