/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.exception;

/**
 * Traps exceptions generated during code generation.
 * 
 * 
 *
 */
public class DsfJs2JavaException extends RuntimeException {

	
	private static final long serialVersionUID = -2115765634009127376L;

	public DsfJs2JavaException(final String message) {
		super(message) ;
	}

	public DsfJs2JavaException(final String message, final Object[] args) {
		super(message);
	}
		
	public DsfJs2JavaException(
		final String message, final Throwable cause)
	{
		super(message, cause);
	}
}