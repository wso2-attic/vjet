/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.event;

import org.ebayopensource.dsf.common.exceptions.DsfException;

/**
 * This exception can be thrown during DsfEvent processing to terminate the
 * currently executing DNode.  DsfEvent's actually do the 
 * dispatching to the registered Listeners.  The currently executing Listener
 * that throws this will immediately stop the current Listeners processing and
 * stop any other Listener processing for this event.
 * 
 * The use of an exception for control flow is against the general V4 Error
 * guidelines.  However, there are rare cases where there is no better approach
 * for the framework to obtain similar functionality without complicating the
 * event programming model so an exception (no pun intended) was made.
 */
public class AbortDsfEventProcessingException extends DsfException {

	private static final long serialVersionUID = 1L;

	public AbortDsfEventProcessingException(final String message) {
		super(message) ;
	}

	public AbortDsfEventProcessingException(
		final String message, final Object[] args)
	{
		super(message, args);
	}
		
	public AbortDsfEventProcessingException(
		final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public AbortDsfEventProcessingException(
		final String message, final Object[] args, final Throwable cause)
	{
		super(message, args, cause);
	}
}
