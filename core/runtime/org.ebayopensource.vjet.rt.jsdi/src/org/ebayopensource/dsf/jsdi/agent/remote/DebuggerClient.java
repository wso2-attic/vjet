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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.ebayopensource.dsf.jsdi.FunctionSource;
import org.ebayopensource.dsf.jsdi.IDebuggerControl;
import org.ebayopensource.dsf.jsdi.IGuiCallback;
import org.ebayopensource.dsf.jsdi.ISourceInfo;
import org.ebayopensource.dsf.jsdi.IVariable;
import org.ebayopensource.dsf.jsdi.StackFrameInfo;

/**
 * A RMI based debugger client proxy which can proxy
 * any pluggable GUI implementation (IGuiCallback).
 */
public class DebuggerClient implements IDebuggerClient {
	
	private final IGuiCallback m_guiCallback;
	private IDebuggerControl m_debuggerControl;
	private boolean m_sharedClient = false;
	
	public DebuggerClient(IGuiCallback guiCallback) {
		this(guiCallback, false);
	}
	
	public DebuggerClient(IGuiCallback guiCallback, boolean shared) {
		m_guiCallback = guiCallback;
		m_sharedClient = shared;
		try {
			IDebuggerClient stub = (IDebuggerClient) UnicastRemoteObject.exportObject(this, 0);
		    Registry registry = LocateRegistry.createRegistry(1099);
		    registry.bind("DebuggerClient", stub);
		    System.out.println("DebuggerClient ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setShared(boolean set) {
		m_sharedClient = set;
	}

	public void compileScript(String url, String text) throws RemoteException {
		m_debuggerControl.compileScript(url, text);
	}

	public void contextSwitch(int frameIndex) throws RemoteException {
		m_debuggerControl.contextSwitch(frameIndex);
	}

	public String eval(String expr) throws RemoteException {
		return m_debuggerControl.eval(expr);
	}

	public void evalScript(String url, String text) throws RemoteException {
		m_debuggerControl.evalScript(url, text);
	}

	public String[] functionNames() throws RemoteException {
		return m_debuggerControl.functionNames();
	}

	public FunctionSource functionSourceByName(String functionName) throws RemoteException {
		return m_debuggerControl.functionSourceByName(functionName);
	}

	public int getFrameCount() throws RemoteException {
		return m_debuggerControl.getFrameCount();
	}

	public StackFrameInfo getFrameInfo(int frameIndex) throws RemoteException {
		return m_debuggerControl.getFrameInfo(frameIndex);
	}

	public String getObjectValueAsString(long id) throws RemoteException {
		return m_debuggerControl.getObjectValueAsString(id);
	}

	public boolean isBreakOnEnter() throws RemoteException {
		return m_debuggerControl.isBreakOnEnter();
	}

	public boolean isBreakOnExceptions() throws RemoteException {
		return m_debuggerControl.isBreakOnExceptions();
	}

	public boolean isBreakOnReturn() throws RemoteException {
		return m_debuggerControl.isBreakOnReturn();
	}

	public IVariable[] loadMembers(long id)  throws RemoteException {
		return m_debuggerControl.loadMembers(id);
	}

	public void setBreak() throws RemoteException {
		m_debuggerControl.setBreak();
	}

	public void setBreakOnEnter(boolean breakOnEnter) throws RemoteException {
		m_debuggerControl.setBreakOnEnter(breakOnEnter);
	}

	public void setBreakOnExceptions(boolean breakOnExceptions) throws RemoteException {
		m_debuggerControl.setBreakOnExceptions(breakOnExceptions);
	}

	public void setBreakOnReturn(boolean breakOnReturn) throws RemoteException {
		m_debuggerControl.setBreakOnReturn(breakOnReturn);
	}

	public void setGuiCallback(IGuiCallback callback) throws RemoteException {
		//do nothing
	}

	public void setReturnValue(int returnValue) throws RemoteException {
		m_debuggerControl.setReturnValue(returnValue);
	}

	public boolean shouldBreak() throws RemoteException {
		return m_debuggerControl.shouldBreak();
	}

	public ISourceInfo getSourceInfo(String url) throws RemoteException {
		return m_debuggerControl.getSourceInfo(url);
	}

	public boolean stringIsCompilableUnit(String str) throws RemoteException {
		return m_debuggerControl.stringIsCompilableUnit(str);
	}
	
	public void connect(IDebuggerControl debuggerControl) throws RemoteException {
		System.out.println("DebuggerClient was connected");
		m_debuggerControl = debuggerControl;
		m_guiCallback.connect(this);
	}
	
	public void detach(boolean shutdown) throws RemoteException {
		if (m_sharedClient) {
			shutdown = false;
		}
		m_guiCallback.detach(shutdown);
		if (shutdown) {
			UnicastRemoteObject.unexportObject(this, true);
		}
	}

	public void dispatchNextGuiEvent() throws InterruptedException, RemoteException {
		m_guiCallback.dispatchNextGuiEvent();
	}

	public void enterInterrupt(StackFrameInfo frameInfo, String threadTitle, String alertMessage)
		throws RemoteException {
		m_guiCallback.enterInterrupt(frameInfo, threadTitle, alertMessage);
	}

	public int[] getBreakPoints(String url) throws RemoteException {
		return m_guiCallback.getBreakPoints(url);
	}

	public boolean isGuiEventThread() throws RemoteException {
		return m_guiCallback.isGuiEventThread();
	}

	public void updateSourceText(ISourceInfo sourceInfo) throws RemoteException {
		m_guiCallback.updateSourceText(sourceInfo);
	}

}
