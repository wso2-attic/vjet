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
import org.ebayopensource.dsf.jsnative.Selection;
import org.ebayopensource.dsf.jsnative.TextRange;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class ASelection extends ActiveObject implements Selection {
	private static final long serialVersionUID = 2708447508280395592L;

	private IBrowserBinding m_browserBinding;

	private BrowserType m_browserType;
	
	private final static String SELECTION_CLEAR_JS = "document.selection.clear()";

	private final static String SELECTION_EMPTY_JS = "document.selection.empty()";

	private final static String SELECTION_TYPE_JS = "document.selection.type";

	private final static String SELECTION_TYPE_DETAIL_JS = "document.selection.typeDetail";

	public ASelection(BrowserType browserType, IBrowserBinding bowserBinding) {
		m_browserBinding = bowserBinding;
		m_browserType = browserType;
		populateScriptable(ASelection.class, browserType);
	}

	public void clear() {
		if (m_browserBinding != null) {
			m_browserBinding.executeJs(SELECTION_CLEAR_JS);
		}
	}
	
	public void empty() {
		if (m_browserBinding != null) {
			m_browserBinding.executeJs(SELECTION_EMPTY_JS);
		}
	}

	public String getType() {
		if (m_browserBinding != null) {
			return m_browserBinding.executeJs(SELECTION_TYPE_JS);
		}
		return null;
	}

	public String getTypeDetail() {
		if (m_browserBinding != null) {
			return m_browserBinding.executeJs(SELECTION_TYPE_DETAIL_JS);
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

	public TextRange createRange() {
		return new ATextRange(m_browserType, m_browserBinding);
	}

	public TextRange[] createRangeCollection() {
		TextRange[] trArray = {new ATextRange(m_browserType, m_browserBinding)};
		return trArray;
	}
}
