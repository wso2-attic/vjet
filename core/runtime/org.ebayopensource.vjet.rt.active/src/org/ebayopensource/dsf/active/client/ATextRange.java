/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.jsnative.TextRange;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class ATextRange extends ActiveObject implements TextRange {

	private static final long serialVersionUID = 505441596754466444L;

	private IBrowserBinding m_browserBinding;

	private final static String BOUND_HEIGHT_JS = "document.selection.createRange().boundingHeight";

	private final static String BOUND_LEFT_JS = "document.selection.createRange().boundingLeft";

	private final static String BOUND_TOP_JS = "document.selection.createRange().boundingTop";

	private final static String BOUND_WIDTH_JS = "document.selection.createRange().boundingWidth";

	private final static String OFFSET_LEFT_JS = "document.selection.createRange().offsetLeft";

	private final static String OFFSET_TOP_JS = "document.selection.createRange().offsetTop";

	private final static String HTML_TEXT_JS = "document.selection.createRange().htmlText";

	private final static String TEXT_JS = "document.selection.createRange().text";

	public ATextRange(BrowserType browserType, IBrowserBinding bowserBinding) {
		m_browserBinding = bowserBinding;
		populateScriptable(ATextRange.class, browserType);
	}

	public int getBoundingHeight() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(BOUND_HEIGHT_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;
	}

	public int getBoundingLeft() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(BOUND_LEFT_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;
	}

	public int getBoundingTop() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(BOUND_TOP_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;
	}

	public int getBoundingWidth() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(BOUND_WIDTH_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;

	}

	public String getHtmlText() {
		if (m_browserBinding != null) {
			return m_browserBinding.executeJs(HTML_TEXT_JS);
		}
		return null;
	}

	public int getOffsetLeft() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(OFFSET_LEFT_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;
	}

	public int getOffsetTop() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(OFFSET_TOP_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;
	}

	public String getText() {
		if (m_browserBinding != null) {
			return m_browserBinding.executeJs(TEXT_JS);
		}
		return null;
	}
	
	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		}
		else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}
}
