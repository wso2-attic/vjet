/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdi.agent.host;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsdi.FunctionSource;
import org.ebayopensource.dsf.jsdi.IDebuggerControl;
import org.ebayopensource.dsf.jsdi.IGuiCallback;
import org.ebayopensource.dsf.jsdi.ISourceInfo;
import org.ebayopensource.dsf.jsdi.IVariable;
import org.ebayopensource.dsf.jsdi.StackFrameInfo;
import org.ebayopensource.dsf.jsdi.agent.remote.IDebuggerClient;

/**
 * RMI based connector for connecting remote RMI
 * debugger client.
 */
public class DebuggerConnector implements IGuiCallback, IDebuggerControl {
	
	private IDebuggerControl m_debuggerControl;
	private IDebuggerClient m_debuggerClient;
	private String m_host;
	private List<Remote> m_bindedRemotes = new ArrayList<Remote>();
	
	public DebuggerConnector(IDebuggerControl debuggerControl, String host) {
		m_host= host;
		try {
			connect(debuggerControl);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
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

	public IVariable[] loadMembers(long id) throws RemoteException {
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
		//DO Nothing
	}

	public void setReturnValue(int returnValue) throws RemoteException {
		m_debuggerControl.setReturnValue(returnValue);
	}

	public boolean shouldBreak() throws RemoteException {
		return m_debuggerControl.shouldBreak();
	}

	public ISourceInfo getSourceInfo(String url) throws RemoteException {
		ISourceInfo info = m_debuggerControl.getSourceInfo(url);
		m_bindedRemotes.add(info);
		return (ISourceInfo)UnicastRemoteObject.exportObject(info, 0);		
	}

	public boolean stringIsCompilableUnit(String str) throws RemoteException {
		return m_debuggerControl.stringIsCompilableUnit(str);
	}

	public void connect(IDebuggerControl debuggerControl) throws RemoteException {
		m_debuggerControl = debuggerControl;	
		debuggerControl.setGuiCallback(this);
		IDebuggerControl remote = (IDebuggerControl)UnicastRemoteObject.exportObject(this, 0);
		Registry registry = LocateRegistry.getRegistry(m_host);
		try {
			m_debuggerClient = (IDebuggerClient) registry.lookup("DebuggerClient");
		} catch (NotBoundException e) {
			throw new RuntimeException(e);
		}
		m_debuggerClient.connect(remote);
	}
	
	public void detach(boolean shutdown) throws RemoteException {		
		UnicastRemoteObject.unexportObject(this, true);
		for (Remote obj : m_bindedRemotes) {
			UnicastRemoteObject.unexportObject(obj, true);
		}
		m_debuggerClient.detach(shutdown);
	}

	public void dispatchNextGuiEvent() throws InterruptedException, RemoteException {
		m_debuggerClient.dispatchNextGuiEvent();
	}

	public void enterInterrupt(StackFrameInfo frameInfo, String threadTitle, String alertMessage)
		throws RemoteException {
		m_debuggerClient.enterInterrupt(frameInfo, threadTitle, alertMessage);
	}

	public int[] getBreakPoints(String url) throws RemoteException {
		return m_debuggerClient.getBreakPoints(url);
	}

	public boolean isGuiEventThread() throws RemoteException {
		return m_debuggerClient.isGuiEventThread();
	}

	public void updateSourceText(ISourceInfo sourceInfo) throws RemoteException {
		m_bindedRemotes.add(sourceInfo);
		m_debuggerClient.updateSourceText((ISourceInfo)UnicastRemoteObject.exportObject(sourceInfo, 0));
	}

}
