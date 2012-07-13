/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import org.ebayopensource.dsf.common.exceptions.BaseException;

public class DSFJstException extends BaseException {

	private static final long serialVersionUID = 1L;

	public DSFJstException(final String message) {
		super(message) ;
	}

	public DSFJstException(final String message, final Object[] args) {
		super(message, args);
	}
		
	public DSFJstException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DSFJstException(
		final String message, final Object[] args, final Throwable cause)
	{
		super(message, args, cause);
	}
}