/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.util;

import java.io.StringReader;
import java.net.URL;

import org.xml.sax.InputSource;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.html.js.Encoding;
import org.ebayopensource.dsf.html.js.JSHTMLBuilder;
import org.ebayopensource.dsf.html.js.JSWindow;
import org.ebayopensource.dsf.html.js.JSWindowFactory;

public class HtmlBuilderHelper {
	public static final String REGX_TAG_SKIP = "(<[a-zA-Z_0-9:]+)/([ a-zA-Z_0-9:]+)";
	public static JSWindow parse(final String src, final URL baseUrl,
			final JSWindow window) {
		return parse(src, baseUrl, Encoding.SOURCE_DEFAULT, window);
	}

	public static JSWindow parse(final String src, final URL baseUrl) {
		return parse(src, baseUrl, Encoding.SOURCE_DEFAULT);
	}

	public static JSWindow parse(final String src, final URL baseUrl,
			final int encoding) {
		final JSWindow window = JSWindowFactory.createJSWindow();
		return parse(src, baseUrl, encoding, window);
	}

	public static JSWindow parse(final String src, final URL baseUrl,
			final int encoding, JSWindow window) {
		final StringReader sr = new StringReader(src);
		try {
			JSHTMLBuilder.doParse(window, new InputSource(sr), baseUrl,
					encoding);
		} catch (Exception e) {
			throw new DsfRuntimeException(e.getMessage(), e);
		}
		return window;
	}

	public static JSWindow parse(final URL url) {
		return parse(url, Encoding.SOURCE_DEFAULT);
	}

	public static JSWindow parse(final URL url, final int encoding) {
		final JSWindow window = JSWindowFactory.createJSWindow();
		try {
			JSHTMLBuilder.doParse(window, url, encoding);
		} catch (Exception e) {
			throw new DsfRuntimeException(e.getMessage(), e);
		}
		return window;
	}

}
