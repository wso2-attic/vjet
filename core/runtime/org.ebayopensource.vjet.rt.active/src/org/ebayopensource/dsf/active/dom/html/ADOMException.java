/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.jsnative.DOMException;
import org.mozilla.mod.javascript.JavaScriptException;

public class ADOMException extends JavaScriptException implements DOMException {

	private static final long serialVersionUID = 1L;

	public ADOMException(org.w3c.dom.DOMException e) {
		super(e, null, 0);
	}

	public short getCode() {
		org.w3c.dom.DOMException e = (org.w3c.dom.DOMException) getValue();
		return e.code;
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

	@Override
	public void DOMException() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DOMException(int errorCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object valueOf(org.ebayopensource.dsf.jsnative.global.String type) {
		// TODO Auto-generated method stub
		return null;
	}
}
