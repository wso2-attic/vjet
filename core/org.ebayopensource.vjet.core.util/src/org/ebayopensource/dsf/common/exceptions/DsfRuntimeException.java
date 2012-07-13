/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.exceptions;

import org.ebayopensource.dsf.common.exceptions.BaseRuntimeException;

/**
 * DSF defined runtime exception, which can be thrown during the DSF operation
 * at runtime.
 * 
 */
public class DsfRuntimeException extends BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	public DsfRuntimeException(final Throwable cause) {
		this(cause.getMessage(), cause);
	}

	public DsfRuntimeException(final String message) {
		super(message);
	}

	public DsfRuntimeException(final String message, final Object[] args) {
		super(message, args);
	}

	public DsfRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DsfRuntimeException(final String message, final Object[] args,
			final Throwable cause) {
		super(message, args, cause);
	}
}
