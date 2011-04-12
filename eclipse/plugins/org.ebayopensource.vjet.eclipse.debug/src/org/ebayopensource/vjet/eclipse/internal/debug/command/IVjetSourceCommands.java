/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.command;

import java.net.URI;

import org.eclipse.dltk.mod.dbgp.exceptions.DbgpException;

/**
 * Extended source commands for vjet
 * 
 *  Ouyang
 * 
 */
public interface IVjetSourceCommands {

	/**
	 * 
	 * @return all file URIs executed in debugger engine, or an empty array if
	 *         no file.
	 */
	URI[] list() throws DbgpException;
}
