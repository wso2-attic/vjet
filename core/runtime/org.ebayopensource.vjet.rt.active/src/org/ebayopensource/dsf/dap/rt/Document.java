/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.active.dom.html.AHtmlType;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.jsnative.HtmlBody;
import org.ebayopensource.dsf.jsnative.HtmlCollection;
import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.NodeList;
import org.ebayopensource.dsf.jsnative.Text;

/**
 * proxy class for staticly referencing browser document properties and methods
 * for active JS programming
 * @deprecated
 */
abstract class Document {
	
	// Properties
	public static HtmlElement getDocumentElement() {
		return document().getDocumentElement();
	}
	
	public static HtmlCollection getAnchors() {
		return document().getAnchors();
	}
	
	public static HtmlCollection getApplets() {
		return document().getApplets();
	}
	
	public static HtmlCollection getForms() {
		return document().getForms();
	}
	
	public static HtmlCollection getImages() {
		return document().getImages();
	}
	
	public static HtmlCollection getLinks() {
		return document().getLinks();
	}
	
	public static HtmlBody getBody() {
		return document().getBody();
	}
	
	public static String getCookie() {
		return document().getCookie();
	}
	
	public static void setCookie(final String cookie) {
		document().setCookie(cookie);
	}
	
	public static String getDomain() {
		return document().getDomain();
	}
	
	public static String getReferrer() {
		return document().getReferrer();
	}
	
	public static String getTitle() {
		return document().getTitle();
	}
	
	public static void setTitle(final String title) {
		document().setTitle(title);
	}
	
	public static String getURL() {
		return document().getURL();
	}
	
	// Functions
	public static HtmlElement createElement(String tagName) throws DOMException {
		return document().createElement(tagName);
	}
	
	public static <T extends HtmlElement> T createElement(AHtmlType<T> type) throws DOMException {
		return type.create(document());
	}
	
	public static Text createTextNode(String data) {
		return document().createTextNode(data);
	}
	
	public static HtmlElement getElementById(final String elementId) {
		return document().getElementById(elementId);
	}

	public static NodeList getElementsByTagName(final String tagName) {
		return document().getElementsByTagName(tagName);
	}
	
	public static NodeList getElementsByName(String elementName) {
		return document().getElementsByName(elementName);
	}
	
	public static Node importNode(Node importedNode, boolean deep) {
		return document().importNode(importedNode, deep);
	}
	
	public static void close() {
		document().close();
	}
	
	public static void open() {
		document().open();
	}
	
	public void write(String text) {
		document().write(text);
	}
	
	public void writeln(String text) {
		document().writeln(text);
	}

	private static HtmlDocument document() {
		HtmlDocument document = DapCtx.document();
		if (document == null) {
			throw new DsfRuntimeException("document did not exist");
		}
		return document;
	}
}
