/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.exceptions;

import org.ebayopensource.dsf.logger.LogLevel;
import org.ebayopensource.dsf.logger.Logger;
/**
 * The helper class used to log the error when <code>DsfRuntimeException</code>
 * is thrown.
 */
public class DsfExceptionHelper {
	
	private static Logger s_logger;
	
	public static void chuck(final String msg){
		throw new DsfRuntimeException(msg);
	}
	
	public static void chuck(final String msg, final Throwable t){
		throw new DsfRuntimeException(msg, t);
	}
	
	public static void log(final String msg){
		getLogger().log(LogLevel.ERROR, new DsfRuntimeException(msg));
	}
	
	public static void log(final Throwable t){
		String msg = t.getMessage();
		if (msg == null){
			msg = "Message not found (null) in throwable";
		}
		getLogger().log(LogLevel.ERROR, msg, t);
	}
	
	public static void log(final String msg, final Throwable t){
		getLogger().log(LogLevel.ERROR, msg, t);
	}
	
	private static Logger getLogger(){
		if (s_logger != null){
			return s_logger;
		}
		
		s_logger = Logger.getInstance(DsfRuntimeException.class);
		return s_logger;
	}
}
