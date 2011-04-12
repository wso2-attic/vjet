/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data;

import java.util.Date;

import org.ebayopensource.dsf.dap.rt.JsBase;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.jsnative.HtmlElement;

public class Sample extends JsBase {
	
	private final String m_message;
	
	public Sample(String message) {
		m_message = message;
	}
	
	public void update(HtmlElement element){
		HtmlElement span = document().createElement(HtmlTypeEnum.SPAN.getName());
		span.setInnerHTML(getMessage());
		element.appendChild(span);
	}
	
	private String getMessage() {
		Date now = new Date();
		return m_message+" and now is "+now.toString();
	}
}
