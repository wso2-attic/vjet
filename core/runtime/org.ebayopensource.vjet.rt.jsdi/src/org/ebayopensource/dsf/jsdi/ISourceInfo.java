/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISourceInfo extends Remote {

	/**
	 * Returns the source text.
	 */
	String getText() throws RemoteException;

	/**
	 * Returns the script's origin URI.
	 */
	String getUri() throws RemoteException;
	
	/**
	 * Returns whether the given line number can have a breakpoint set on
	 * it.
	 */
	boolean isBreakableLine(int line) throws RemoteException;

	/**
	 * Returns whether there is a breakpoint set on the given line.
	 */
	boolean isBreakpoint(int line) throws RemoteException;
	/**
	 * Sets or clears the breakpoint flag for the given line.
	 */
	public boolean setBreakpoint(int line, boolean value) throws RemoteException;
}
