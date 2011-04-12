/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.support;


public class DsfDomLevelNotSupportedException extends DsfDomNotSupportedRuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DsfDomLevelNotSupportedException(int level) {
		this("DOM level " + level + " is not supported") ;
	}
	
	public DsfDomLevelNotSupportedException(final String message) {
		super(message) ;
	}

//	public DsfDomLevelNotSupportedException(String message, Object[] args) {
//		super(message, args);
//	}
		
//	public DsfDomLevelNotSupportedException(String message, Throwable cause) {
//		super(message, cause);
//	}

//	public DsfDomLevelNotSupportedException(String message, Object[] args, Throwable cause) {
//		super(message, args, cause);
//	}
}
