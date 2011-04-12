/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

public class VjoValidationRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -1L;

	public VjoValidationRuntimeException(final Throwable cause) {
		super(cause.getMessage(), cause);
	}

	public VjoValidationRuntimeException(String stringMessage) {
		super(stringMessage, (Throwable) null);
	}

	public VjoValidationRuntimeException(String stringMessage, Throwable cause) {
		super(stringMessage, cause);
	}

}