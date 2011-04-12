/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.WeakHashMap;

import org.mozilla.mod.javascript.Context;

public class DBGPStackManager {

	protected static final WeakHashMap<Context, DBGPStackManager> s_map 
		= new WeakHashMap<Context, DBGPStackManager>();
	private static boolean s_breakpointsThreadLocal;
	private static BreakPointManager s_gmanager = null;
	
	private List<DBGPDebugFrame> m_stack = new ArrayList<DBGPDebugFrame>();
	private boolean m_needSuspend;
	private DBGPDebugger m_observer;
	private BreakPointManager m_manager = null;	
	private boolean m_suspendOnExit;
	private boolean m_suspendOnEntry;
	private boolean m_suspenOnChangeLine;

	public BreakPointManager getManager() {
		return m_manager;
	}

	private DBGPStackManager() {
		if (isBreakpointsThreadLocal()) {
			m_manager = new BreakPointManager();
		} else {
			synchronized (DBGPStackManager.class) {
				if (s_gmanager == null)
					s_gmanager = new BreakPointManager();
			}
			m_manager = s_gmanager;
		}
	}

	public static DBGPStackManager getManager(Context cx) {
		DBGPStackManager object = s_map.get(cx);
		if (object == null) {
			object = new DBGPStackManager();
			s_map.put(cx, object);
		}
		return object;
	}

	public static void removeManager(Context cx) {
		s_map.remove(cx);
	}

	public void enter(DBGPDebugFrame debugFrame) {
		m_stack.add(debugFrame);
		String sn = debugFrame.getWhere();

		if (sn != null) {
			BreakPoint hit = m_manager.hitEnter(sn);
			if (hit != null) {
				checkBreakpoint(debugFrame, hit);
			}
		}
		
		if (m_suspendOnEntry) {
			if (debugFrame.getWhere().equals("module")) {
				m_observer.update(null, this);
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						//DONOTHING
					}
				}
			} else {
				m_suspenOnChangeLine = true;
			}
		}
	}

	public void exit(DBGPDebugFrame debugFrame) {
		if (m_needSuspend || m_suspendOnExit) {
			m_observer.update(null, this);
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					//DONOTHING
				}
			}
		}
		String sn = debugFrame.getWhere();

		if (sn != null) {
			BreakPoint hit = m_manager.hitExit(sn);
			if (hit != null)
				checkBreakpoint(debugFrame, hit);
		}
		m_stack.remove(debugFrame);

	}

	public void changeLine(DBGPDebugFrame frame, int lineNumber) {
		if (m_suspenOnChangeLine) {
			m_suspenOnChangeLine = false;
			m_observer.update(null, this);
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					//DONOTHING
				}
			}
		}
		if (frame.isSuspend()) {
			m_needSuspend = true;
		}
		BreakPoint hit = m_manager.hit(frame.getSourceName(), lineNumber);
		checkBreakpoint(frame, hit);
	}

	private void checkBreakpoint(DBGPDebugFrame frame, BreakPoint hit) {

		if (hit != null) {
			if (hit.isEnabled()) {
				if (hit.m_expression != null) {
					Object eval = frame.eval(hit.m_expression);
					if (eval != null) {
						if (eval.equals(Boolean.TRUE)) {
							m_needSuspend = true;
						} else {
							m_needSuspend = false;
						}
					} else {
						m_needSuspend = false;
					}
				} else {
					m_needSuspend = true;
				}
			}
		}
		if (m_needSuspend) {
			m_observer.update(null, this);
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					//DONOTHING
				}
			}
		}
	}

	public void exceptionThrown(Throwable ex) {
		//TODO
	}

	public void suspend() {
		m_needSuspend = true;
	}

	public int getStackDepth() {
		return m_stack.size();
	}

	public DBGPDebugFrame getStackFrame(int parseInt) {
		int stackCounter = m_stack.size() - parseInt - 1;
		if (stackCounter >= 0) {
			return m_stack.get(stackCounter);
		}
		return null;
	}

	public int getLineNumber(String level) {
		return getStackFrame(0).getLineNumber();
	}

	public void registerBreakPoint(BreakPoint p) {
		m_manager.addBreakPoint(p);
	}

	public void setDebugger(DBGPDebugger debugger) {
		m_observer = debugger;
	}

	public synchronized void resume() {
		m_needSuspend = false;
		for (int a = 0; a < getStackDepth(); a++) {
			getStackFrame(a).setSuspend(false);
		}
		notify();
	}

	public synchronized void stepOver() {
		getStackFrame(0).setSuspend(true);
		if (getStackDepth() > 1) {
			getStackFrame(1).setSuspend(true);
		}
		m_needSuspend = false;
		notify();
	}

	public synchronized void stepIn() {
		m_needSuspend = true;
		notify();
	}

	public synchronized void stepOut() {
		getStackFrame(0).setSuspend(false);
		m_needSuspend = false;
		if (getStackDepth() > 1) {
			getStackFrame(1).setSuspend(true);
		}
		notify();
	}

	public synchronized void waitForNotify() {
		try {
			wait();
		} catch (InterruptedException e) {
			//DONOTHING
		}
	}

	public void removeBreakpoint(String id) {
		m_manager.removeBreakPoint(id);
	}

	public void updateBreakpoint(String id, String newState, String newLine,
			String hitValue, String hitCondition, String condExpr) {
		m_manager.updateBreakpoint(id, newState, newLine, hitValue,
			hitCondition, condExpr);
	}

	public Observer getObserver() {
		return m_observer;
	}

	public BreakPoint getBreakpoint(String id) {
		return m_manager.getBreakpoint(id);
	}

	public void setSuspendOnExit(boolean parseBoolean) {
		m_suspendOnExit = parseBoolean;
	}

	public void setSuspendOnEntry(boolean parseBoolean) {
		m_suspendOnEntry = parseBoolean;
	}

	public static boolean isBreakpointsThreadLocal() {
		return s_breakpointsThreadLocal;
	}

	public static void setBreakpointsThreadLocal(boolean breakpointsThreadLocal) {
		DBGPStackManager.s_breakpointsThreadLocal = breakpointsThreadLocal;
	}

}
