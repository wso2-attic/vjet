/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdi.agent.remote;

import java.rmi.RemoteException;
import java.util.BitSet;

import org.ebayopensource.dsf.jsdi.ISourceInfo;

/**
 * For holding temp source info before it is used by active debugger
 */
public class LocalSourceInfo implements ISourceInfo {

	private final String m_uri;
	private final String m_text;
	private final BitSet m_breakLines = new BitSet();
	
	public LocalSourceInfo(String uri, String text) {
		m_uri = uri;
		m_text = text;
	}
	
	public String getText() throws RemoteException {
		return m_text;
	}

	public String getUri() throws RemoteException {
		return m_uri;
	}

	public boolean isBreakableLine(int line) throws RemoteException {
		//need some logic here
		return true;
	}

	public boolean isBreakpoint(int line) throws RemoteException {
		return m_breakLines.get(line);
	}

	public boolean setBreakpoint(int line, boolean value) throws RemoteException {
		m_breakLines.set(line, value);
		return true;
	}

}
