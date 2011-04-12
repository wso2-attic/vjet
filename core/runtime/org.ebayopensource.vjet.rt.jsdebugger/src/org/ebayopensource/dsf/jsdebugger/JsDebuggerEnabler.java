/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdebugger;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.WindowConstants;

import org.ebayopensource.dsf.html.js.ActiveJsExecutionControlCtx;
import org.ebayopensource.dsf.jsdebugger.gui.SwingGui;
import org.ebayopensource.dsf.jsdi.agent.host.DebuggerConnector;
import org.ebayopensource.dsf.jsdi.agent.remote.DebuggerClient;
import org.mozilla.mod.javascript.ContextFactory;

/**
 * Enable the swing based JD debugger. It will also exit the debugger if
 * there is no other non-daemon thread besides those AWT threads associated with 
 * swing debugger.
 */
public class JsDebuggerEnabler {
	
	private static ThreadGroup s_debuggerGroup = null;
	private static MonitorTask s_monitor = null;
	private static boolean s_useRemoteClient = false;
	private static boolean s_useEmbededClient = false;
	private static DebuggerClient s_inProcessDebuggerClient = null;

	static {
		
		try {
			LocateRegistry.getRegistry(null).list();
			s_useRemoteClient = true;
		} catch (RemoteException e) {
			//do nothing
		}
		
		if (!s_useRemoteClient) {
			RuntimeMXBean rtMxBean = ManagementFactory.getRuntimeMXBean();
			
			for (String arg : rtMxBean.getInputArguments()) {
				if (arg.startsWith("-agentlib:jdwp")) {
					s_debuggerGroup = new ThreadGroup("JSDebugger");
					s_debuggerGroup.setDaemon(true);
					break;
				}
			}
		}
	}
	
	/**
	 * This method should only be called in main thread. It could cause
	 * dead-lock by native window dialog creation if it is called inside
	 * servlet request thread.
	 */
	public static void enableDebuggerFileChooser() {
		if (s_debuggerGroup == null) {
			return;
		}
		final DebuggerReady dr = new DebuggerReady();
		Thread t = new Thread(s_debuggerGroup, new Runnable() {
			public void run() {
				SwingGui.initFileChooser();
				synchronized (dr) {
					dr.ready();
					dr.notifyAll();
				}
			}
		});		
		t.setDaemon(true);
		t.start();
		synchronized (dr) {
			if (!dr.isReady()) {
				try {
					dr.wait();
				} catch (InterruptedException e) {
					//NO-OP
				}
			}
		}
	}
	
	public synchronized static DebuggerAdapter enable() {
		if (!ActiveJsExecutionControlCtx.ctx().needDebug()) {
			return null;
		}
		
		final DebuggerAdapter dbg = new DebuggerAdapter();
		
		if (!s_useRemoteClient && s_inProcessDebuggerClient == null) {
			final DebuggerReady dr = new DebuggerReady();
			Thread t = new Thread(s_debuggerGroup, new Runnable() {
				public void run() {			
					if (!s_useEmbededClient) {
						SwingGui gui = new SwingGui("DSF JS Debugger");
						gui.pack();
						gui.setSize(600, 460);			
						gui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						gui.setVisible(true);
						s_inProcessDebuggerClient = new DebuggerClient(gui,
							ActiveJsExecutionControlCtx.ctx().useSharedDebugClient());
					}
					else {
						SwingGui gui = new SwingGui("DSF JS Debugger");
						gui.pack();
						gui.setSize(600, 460);			
						gui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						dbg.attachTo( ContextFactory.getGlobal());
						gui.setVisible(true);
						gui.connect(dbg);
					}
					synchronized (dr) {
						dr.ready();
						dr.notifyAll();
					}
				}			
			});		
			t.setDaemon(true);
			t.start();
			synchronized (dr) {
				if (!dr.isReady()) {
					try {
						dr.wait();
					} catch (InterruptedException e) {
						//NO-OP
					}
				}
			}
		}
		if (!s_useEmbededClient) {
			new DebuggerConnector(dbg, null);
		}
		dbg.attachTo(ContextFactory.getGlobal());
		
		if (!s_useRemoteClient && s_monitor == null) {
			s_monitor = new MonitorTask();
			Timer timer = new Timer(true);
			timer.schedule(s_monitor, 1000, 5000);
		}
		return dbg;
	}
	
	public static boolean useRemoteClient() {
		return s_useRemoteClient;
	}
	
	private static int getNondaemonThreadCount(ThreadGroup tg) {
		
		Thread[] threads = new Thread[tg.activeCount()];
		tg.enumerate(threads);
		int count = 0;
		for (Thread tt : threads) {
			if (!tt.isDaemon()) {
				count++;
			}
		}
		return count;
	}
	
	private static class MonitorTask extends TimerTask {
		public void run() {
			int nondaemonAwtThreadCount = getNondaemonThreadCount(s_debuggerGroup);
			ThreadGroup tg = Thread.currentThread().getThreadGroup();
			while (tg.getParent() != null) {
				tg = tg.getParent();
			}
			int totalNondaemonThreadCount = getNondaemonThreadCount(tg);
			if (totalNondaemonThreadCount == nondaemonAwtThreadCount) {
				System.exit(0);
			}
		}	
	}
	
	private static class DebuggerReady {
		private boolean m_ready = false;
		
		boolean isReady() {
			return m_ready;
		}
		
		void ready() {
			m_ready = true;
		}
	}
}
