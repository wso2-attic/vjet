/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;

import org.ebayopensource.dsf.common.exceptions.BaseRuntimeException;

public class AbortStageProcessingException extends BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	public AbortStageProcessingException(final String message) {
		super(message) ;
	}
	
	public AbortStageProcessingException(
		final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
