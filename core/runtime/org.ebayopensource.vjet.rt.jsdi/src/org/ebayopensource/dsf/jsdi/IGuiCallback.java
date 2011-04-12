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


/**
 * Interface for communication between the debugger and its GUI. This should be
 * implemented by the GUI client.
 */
public interface IGuiCallback extends Remote {

	/**
	 * Called when the source text of some script has been changed.
	 */
	void updateSourceText(ISourceInfo sourceInfo) throws RemoteException;

	/**
	 * Called when the interrupt loop has been entered.
	 */
	void enterInterrupt(StackFrameInfo frameInfo, String threadTitle,
			String alertMessage) throws RemoteException;

	/**
	 * Returns whether the current thread is the GUI's event thread. This
	 * information is required to avoid blocking the event thread from the
	 * debugger.
	 */
	boolean isGuiEventThread() throws RemoteException;

	/**
	 * Processes the next GUI event. This manual pumping of GUI events is
	 * necessary when the GUI event thread itself has been stopped.
	 */
	void dispatchNextGuiEvent() throws InterruptedException, RemoteException;
	
	/**
	 * inform GUI that debugger is ready to detach from it.
	 */
	void detach(boolean shutdown) throws RemoteException;
	
	/**
	 * get existing break points for a given source url
	 */
	int[] getBreakPoints(String url) throws RemoteException;
	
	/**
	 * connect debugger GUI with debugger control
	 */
	void connect(IDebuggerControl debuggerControl) throws RemoteException;
}
