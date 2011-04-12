/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.event;

import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.Window;

/**
 * This binding allows virtual browser to bind some of its value with real browser,
 * since some of the values are only make available in real renderable browser, such
 * as dimentions of rendered elements.
 */
public interface IBrowserBinding {
	String getScreenProperty(final String name);
	void setScreenProperty(final String name, final String value);
	
	String getNavigatorProperty(final String name);
	void setNavigatorProperty(final String name, final String value);
	
	String getDocumentProperty(final String name);
	void setDocumentProperty(final String name, final String value);
	
	String getDomAttributeValue(final BaseHtmlElement elem, final EHtmlAttr attr);
	String getDomAttributeValue(final BaseHtmlElement elem, final String attrName);
	void setDomAttributeValue(final BaseHtmlElement elem, final EHtmlAttr attr, final String value);
	
	String getElementCurrentStyleValue(final BaseHtmlElement elem, final String name);
	String getElementRuntimeStyleValue(final BaseHtmlElement elem, final String name);
	void setElementRuntimeStyleValue(final BaseHtmlElement elem, final String name, final String value);
	
	void executeDomMethod(final BaseHtmlElement elem, final String methodCall);
	String executeJs(String code);
	int getWindowWidth();
	void setWindowWidth(int width);
	
	int getWindowHeight();
	void setWindowHeight(int heigth);
	
	void alert(String message);
	void blur();
	void focus();
	void moveBy(int x, int y);
	void moveTo(int x, int y);
	int getPageXOffset();
	int getPageYOffset();
	int getScreenLeft();
	int getScreenTop();
	boolean confirm(String message);
	void print();
	void close();
	String prompt(String message, String defaultReply);
	Window open(String url, String windowName, String features, boolean replace);
	void resizeBy(int width, int height);
	void resizeTo(int width, int height);
	void scrollBy(int x, int y);
	void scrollTo(int x, int y);
	void locationAssign(String url);
	void locationReload(boolean forceGet);
	void locationReplace(String url);
	int setTimeout(String code, int delay);
	int setInterval(String code, int delay);
	void clearInterval(int timerId);
	void clearTimeout(int timerId);
	void back();
	void go(Object o);
	void forward();
	int historyLength();
}
