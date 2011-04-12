/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.container;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

/**
 * This exception should be thrown if a component can not be instantiated.
 */
public class DsfNodeInstantiationException extends DsfRuntimeException {
	private static final long serialVersionUID = 1L;

	public DsfNodeInstantiationException(final String message) {
		super(message);
	}
	
	public DsfNodeInstantiationException(
		final String message, final Throwable t)
	{
		super(message, t);
	}
}
