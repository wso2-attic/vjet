/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.support;

import org.w3c.dom.DOMException;

public class DsfDomNotSupportedRuntimeException extends DOMException {

	private static final long serialVersionUID = 1L;

	public DsfDomNotSupportedRuntimeException(final String message) {
		super(DOMException.NOT_SUPPORTED_ERR, message) ;
	}

//	public DsfDomNotSupportedException(String message, Object[] args) {
//		super(message, args);
//	}
		
//	public DsfDomNotSupportedRuntimeException(String message, Throwable cause) {
//		super(message, cause);
//	}

//	public DsfDomNotSupportedException(String message, Object[] args, Throwable cause) {
//		super(message, args, cause);
//	}
}

