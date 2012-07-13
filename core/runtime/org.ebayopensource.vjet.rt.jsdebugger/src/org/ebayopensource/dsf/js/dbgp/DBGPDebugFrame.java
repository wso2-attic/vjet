/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.NativeJavaArray;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;
import org.mozilla.mod.javascript.VMBridge;
import org.mozilla.mod.javascript.debug.DebugFrame;
import org.mozilla.mod.javascript.debug.DebuggableScript;
import org.mozilla.mod.javascript.debug.Debugger;
import org.ebayopensource.dsf.logger.LogLevel;
import org.ebayopensource.dsf.logger.Logger;
import org.ebayopensource.dsf.common.StringUtils;

public final class DBGPDebugFrame implements DebugFrame {

	private static final Logger	logger	= Logger.getInstance(DBGPDebugFrame.class);
	private final String m_sourceName;
	private final DBGPStackManager m_stackManager;
	private int m_lineNumber;
	private String m_where;
	private Scriptable m_thisObj;
	private Scriptable m_scope;
	private DebuggableScript m_script;
	private Context m_context;
	private boolean m_suspend;

	// private VMBridgeExt vmBridgeExtInstance = new VMBridgeExt();
	public boolean isSuspend() {
		return m_suspend;
	}

	public void setSuspend(boolean suspend) {
		m_suspend = suspend;
	}

	public DBGPDebugFrame(Context ct, DebuggableScript node, String fileName) {

		m_sourceName = fileName != null ? fileName : node.getSourceName();
		m_context = ct;
		m_stackManager = DBGPStackManager.getManager(ct);
		m_where = node.getFunctionName();
		m_script = node;
		if (m_where == null) {
			m_where = "module";
		}

	}

	public String[] getParametersAndVars() {
		String[] result = new String[m_script.getParamAndVarCount()];
		for (int a = 0; a < result.length; a++) {
			result[a] = m_script.getParamOrVarName(a);
		}
		return result;
	}

	public void onEnter(Context cx, Scriptable activation, Scriptable thisObj,
			Object[] args) {
		m_scope = activation;
		m_thisObj = thisObj;
		m_stackManager.enter(this);
	}

	public void onExceptionThrown(Context cx, Throwable ex) {
		m_stackManager.exceptionThrown(ex);
	}

	public void onExit(Context cx, boolean byThrow, Object resultOrException) {
		m_stackManager.exit(this);
	}

	public void onLineChange(Context cx, int lineNumber) {
		m_lineNumber = lineNumber;
		m_stackManager.changeLine(this, getLineNumber());
	}

	public String getSourceName() {
		if (isHtmlFrame()) {
			return getHtmlSourceName();
		} else {
			return m_sourceName;
		}
	}

	public int getLineNumber() {
		if (isHtmlFrame()) {
			return getHtmlScriptOffset() + m_lineNumber;
		}
		return m_lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		m_lineNumber = lineNumber;
	}

	public String getWhere() {
		return m_where;
	}

	public Object getValue(int num) {
		return m_scope.get(m_script.getParamOrVarName(num), m_thisObj);
	}

	public Scriptable getThis() {
		return m_thisObj;
	}

	public void setValue(String name, String value) {
		// modify by patrick -- fix long name issue. e.g. "this.lion.name"
		Scriptable start;
		if (name.startsWith("this.")) {
			name = name.substring("this.".length());
			start = m_thisObj;
		} else {
			start = m_scope;
		}
		int offset = name.lastIndexOf('.');
		String propertyName = null;
		if (offset == -1) {
			propertyName = name;
		} else {
			String objName = name.substring(0, offset);
			Object obj = getProperty(start, objName);
			if (obj instanceof Scriptable) {
				start = (Scriptable) obj;
			}
			propertyName = name.substring(offset + 1);
		}
		try {
			start.put(propertyName, start, eval(value));
		} catch (Exception e) {
			logger.log(LogLevel.ERROR, e);
		}
		// end modify
	}

	public Object eval(Reader reader) {
		Debugger debugger = m_context.getDebugger();
		boolean fromThis = false;
		try {
			m_context.setDebugger(null, null);

			Scriptable cs = (Scriptable) getProperty(m_thisObj, "window");
			if (cs == null) {
				cs = m_scope;
			}

			Object helper = VMBridge.instance.getThreadContextHelper();
			VMBridge.instance.setContext(helper, m_context);

			// if (value.startsWith("this.")) {
			//
			// value = value.substring("this.".length());
			// cs = m_thisObj;
			// }
			return innerEval(cs, reader);
		} catch (Throwable e) {
				return "Error during evaluation:" + e.getMessage();
		} finally {
			m_context.setDebugger(debugger, null);
		}
	}

	public Object eval(String value) {
		Debugger debugger = m_context.getDebugger();
		boolean fromThis = false;
		try {
			m_context.setDebugger(null, null);

			Scriptable cs;// = (Scriptable) getProperty(m_thisObj, "window");
//			if (cs == null) {
				if (value.startsWith("this")) {
					int indexOf = value.indexOf('.');
					if (indexOf == -1) {
						return m_thisObj;
					}
					value = value.substring("this.".length());
					fromThis = true;
				}
				if (fromThis) {
					cs = m_thisObj;
				}else {
					cs = m_scope;
				}
//			}

			Object helper = VMBridge.instance.getThreadContextHelper();
			VMBridge.instance.setContext(helper, m_context);

			// if (value.startsWith("this.")) {
			//
			// value = value.substring("this.".length());
			// cs = m_thisObj;
			// }
			return innerEval(cs, value);
		} catch (Throwable e) {
			if (!fromThis) {
				try {
					return innerEval(m_thisObj, value);
				}catch (Throwable e1) {
					return "Error during evaluation:" + e.getMessage();
				}
			} else {
				return "Error during evaluation:" + e.getMessage();
			}
		} finally {
			m_context.setDebugger(debugger, null);
		}
	}
	
	private Object innerEval(Scriptable cs, String value) {
		return m_context.evaluateString(cs, value, "eval", 0, null);
	}
	
	private Object innerEval(Scriptable cs, Reader reader) throws IOException {
		return m_context.evaluateReader(cs, reader, "eval", 0, null);
	}

	public Object getValue(String longName) {
		if (longName.startsWith("this")) {
			int indexOf = longName.indexOf('.');
			if (indexOf == -1) {
				return m_thisObj;
			}
			longName = longName.substring("this.".length());
			return getProperty(m_thisObj, longName);
		}
		return getProperty(m_scope, longName);
	}

	private Object getProperty(Scriptable obj, String longName) {
		int k = longName.indexOf('.');
		if (k == -1) {
			return shortGet(obj, longName);
		}
		String shortName = longName.substring(0, k);
		String sm = longName.substring(k + 1);
		Object property = shortGet(obj, shortName);
		if (property instanceof Scriptable) {
			return getProperty((Scriptable) property, sm);
		}
		return null;
	}

	private Object shortGet(Scriptable obj, String longName) {
		if (obj instanceof NativeJavaArray) {
			int parseInt = Integer.parseInt(longName);
			NativeJavaArray na = (NativeJavaArray) obj;
			return na.get(parseInt, na);
		}
		Scriptable parent = obj;
		while (parent != null) {
			Object o = ScriptableObject.getProperty(parent, longName);
			if (o != null && o != Scriptable.NOT_FOUND) {
				return o;
			}
			parent = parent.getParentScope();
		}
		return null;
	}

	public void onDebuggerStatement(Context cx) {
		// TODO Auto-generated method stub
	}

	// Jack: debug on html script
	private int getHtmlScriptOffset() {
		String sourceName = m_sourceName;
		if (sourceName.indexOf(BreakPointManager.SCRIPT_STR) > 0) {
			List<String> ss = StringUtils.splitStr(sourceName, '#');
			if (ss.size() == 5)
				try {
					return Integer.parseInt(ss.get(3))
							+ Integer.parseInt(ss.get(4));
				} catch (Exception e) {
					
				}
		}
		return 0;
	}

	private boolean isHtmlFrame() {
		String sourceName = m_sourceName;
		if (sourceName == null) {
			return false;
		}
		return sourceName.indexOf(BreakPointManager.SCRIPT_STR) > 0;
	}

	private String getHtmlSourceName() {
		String sourceName = m_sourceName;
		if (sourceName.indexOf(BreakPointManager.SCRIPT_STR) > 0) {
			List<String> ss = StringUtils.splitStr(sourceName, '#');
			if (ss.size() == 5) {
				return ss.get(0);
			}
		}
		return sourceName;
	}
	
	// add by patrick
	protected Context getContext() {
		return this.m_context;
	}
	
	private void setProperty(Scriptable scriptable, String property,
			Object value) {
		scriptable.put(property, scriptable, value);
	}
	// end add
}
