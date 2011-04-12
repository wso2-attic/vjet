/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.text.MessageFormat;

import org.ebayopensource.dsf.active.dom.html.AHtmlForm;
import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.jsnative.External;
import org.ebayopensource.dsf.jsnative.HtmlForm;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AExternal extends ActiveObject implements External {
	private static final long serialVersionUID = 6596437860066205967L;

	private final String AUTO_COMPLETE_SAVE_FORM_JS = "window.external.AutoCompleteSaveForm({0})";

	private IBrowserBinding m_browserBinding;

	private BrowserType m_browserType;

	public AExternal(BrowserType browserType, IBrowserBinding bowserBinding) {
		m_browserBinding = bowserBinding;
		m_browserType = browserType;
		populateScriptable(AExternal.class, browserType);
	}

	public void AutoCompleteSaveForm(HtmlForm elem) {
		if(m_browserBinding!=null){
			m_browserBinding.executeJs(MessageFormat.format(
				AUTO_COMPLETE_SAVE_FORM_JS, ((AHtmlForm)elem).getReferenceAsJs()));
		}
	}

	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		} else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}
}
