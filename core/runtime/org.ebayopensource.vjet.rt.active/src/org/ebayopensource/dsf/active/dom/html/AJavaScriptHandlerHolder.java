/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

public class AJavaScriptHandlerHolder {
	private Object m_handler;
	private JAVASCRIPT_HANDLER_TYPE m_handlerType;

	public AJavaScriptHandlerHolder(Object handler, JAVASCRIPT_HANDLER_TYPE type) {
		m_handler = handler;
		m_handlerType = type;
	}

	public Object getHandler() {
		return m_handler;
	}

	public JAVASCRIPT_HANDLER_TYPE getHandlerType() {
		return m_handlerType;
	}

	public static enum JAVASCRIPT_HANDLER_TYPE {
		INLINE, EXTERNAL
	}
}
