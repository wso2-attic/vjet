/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdebugger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.ebayopensource.dsf.jsdi.FunctionSource;
import org.ebayopensource.dsf.jsdi.IDebuggerControl;
import org.ebayopensource.dsf.jsdi.IGuiCallback;
import org.ebayopensource.dsf.jsdi.IValue;
import org.ebayopensource.dsf.jsdi.IVariable;
import org.ebayopensource.dsf.jsdi.SourceInfo;
import org.ebayopensource.dsf.jsdi.StackFrameInfo;
import org.ebayopensource.dsf.jsdi.Value;
import org.ebayopensource.dsf.jsdi.Variable;
import org.ebayopensource.dsf.jsdi.VariableType;
import org.mozilla.mod.javascript.Callable;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.ContextAction;
import org.mozilla.mod.javascript.ContextFactory;
import org.mozilla.mod.javascript.ImporterTopLevel;
import org.mozilla.mod.javascript.Kit;
import org.mozilla.mod.javascript.NativeCall;
import org.mozilla.mod.javascript.ObjArray;
import org.mozilla.mod.javascript.ScriptRuntime;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;
import org.mozilla.mod.javascript.Undefined;
import org.mozilla.mod.javascript.debug.DebugFrame;
import org.mozilla.mod.javascript.debug.DebuggableObject;
import org.mozilla.mod.javascript.debug.DebuggableScript;
import org.mozilla.mod.javascript.debug.Debugger;

/**
 * Debugger Implementation for Rhino.
 */
public class DebuggerAdapter implements IDebuggerControl {

	// Constants for the DimIProxy interface implementation class.
	private static final int IPROXY_DEBUG = 0;

	private static final int IPROXY_LISTEN = 1;

	private static final int IPROXY_COMPILE_SCRIPT = 2;

	private static final int IPROXY_EVAL_SCRIPT = 3;

	private static final int IPROXY_STRING_IS_COMPILABLE = 4;

	private static final int IPROXY_OBJECT_TO_STRING = 5;

	private static final int IPROXY_OBJECT_PROPERTY = 6;

	private static final int IPROXY_OBJECT_IDS = 7;

	/**
	 * Interface to the debugger GUI.
	 */
	private IGuiCallback m_callback;

	/**
	 * Whether the debugger should break.
	 */
	private boolean m_shouldBreak;

	/**
	 * The ScopeProvider object that provides the scope in which to evaluate
	 * script.
	 */
	private ScopeProvider m_scopeProvider;

	/**
	 * The index of the current stack frame.
	 */
	private int m_frameIndex = -1;

	/**
	 * Information about the current stack at the point of interruption.
	 */
	private volatile ContextData m_interruptedContextData;

	/**
	 * The ContextFactory to listen to for debugging information.
	 */
	private ContextFactory m_contextFactory;

	/**
	 * Synchronization object used to allow script evaluations to happen when a
	 * thread is resumed.
	 */
	private Object m_monitor = new Object();

	/**
	 * Synchronization object used to wait for valid
	 * {@link #m_interruptedContextData}.
	 */
	private Object m_eventThreadMonitor = new Object();

	/**
	 * The action to perform to end the interruption loop.
	 */
	private volatile int m_returnValue = -1;

	/**
	 * Whether the debugger is inside the interruption loop.
	 */
	private boolean m_insideInterruptLoop;

	/**
	 * The requested script string to be evaluated when the thread has been
	 * resumed.
	 */
	private String m_evalRequest;

	/**
	 * The stack frame in which to evaluate {@link #m_evalRequest}.
	 */
	private StackFrame m_evalFrame;

	/**
	 * The result of evaluating {@link #m_evalRequest}.
	 */
	private String m_evalResult;

	/**
	 * Whether the debugger should break when a script exception is thrown.
	 */
	private boolean m_breakOnExceptions;

	/**
	 * Whether the debugger should break when a script function is entered.
	 */
	private boolean m_breakOnEnter;

	/**
	 * Whether the debugger should break when a script function is returned
	 * from.
	 */
	private boolean m_breakOnReturn;

	/**
	 * Table mapping URLs to information about the script source.
	 */
	private final Hashtable<String, SourceInfo> m_urlToSourceInfo = new Hashtable<String, SourceInfo>();

	/**
	 * Table mapping function names to information about the function.
	 */
	private final Hashtable<String, FunctionSource> m_functionNames = new Hashtable<String, FunctionSource>();

	/**
	 * Table mapping functions to information about the function.
	 */
	private final Hashtable<DebuggableScript, FunctionSource> m_functionToSource = new Hashtable<DebuggableScript, FunctionSource>();

	/**
	 * ContextFactory.Listener instance attached to {@link #m_contextFactory}.
	 */
	private DimIProxy m_listener;

	/**
	 * Sets the GuiCallback object to use.
	 */
	public void setGuiCallback(IGuiCallback callback) {
		m_callback = callback;
	}

	/**
	 * Tells the debugger to break at the next opportunity.
	 */
	public void setBreak() {
		m_shouldBreak = true;
	}
	
	public boolean shouldBreak() {
		return m_shouldBreak;
	}

	/**
	 * Sets the ScopeProvider to be used.
	 */
	public void setScopeProvider(ScopeProvider scopeProvider) {
		m_scopeProvider = scopeProvider;
	}

	/**
	 * Switches context to the stack frame with the given index.
	 */
	public void contextSwitch(int frameIndex) {
		m_frameIndex = frameIndex;
	}

	/**
	 * Sets whether the debugger should break on exceptions.
	 */
	public void setBreakOnExceptions(boolean breakOnExceptions) {
		m_breakOnExceptions = breakOnExceptions;
	}
	
	public boolean isBreakOnExceptions() {
		return m_breakOnExceptions;
	}

	/**
	 * Sets whether the debugger should break on function entering.
	 */
	public void setBreakOnEnter(boolean breakOnEnter) {
		m_breakOnEnter = breakOnEnter;
	}
	
	public boolean isBreakOnEnter() {
		return m_breakOnEnter;
	}

	/**
	 * Sets whether the debugger should break on function return.
	 */
	public void setBreakOnReturn(boolean breakOnReturn) {
		m_breakOnReturn = breakOnReturn;
	}
	
	public boolean isBreakOnReturn() {
		return m_breakOnReturn;
	}

	/**
	 * Attaches the debugger to the given ContextFactory.
	 */
	public void attachTo(ContextFactory factory) {
		detach();
		m_contextFactory = factory;
		m_listener = new DimIProxy(this, IPROXY_LISTEN);
		factory.addListener(m_listener);
	}

	/**
	 * Detaches the debugger from the current ContextFactory.
	 */
	public void detach() {
		if (m_listener != null) {
			m_contextFactory.removeListener(m_listener);
			m_contextFactory = null;
			m_listener = null;
		}
	}

	/**
	 * Releases resources associated with this debugger.
	 */
	public void dispose() {
		detach();
		try {
			m_callback.detach(true);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the FunctionSource object for the given script or function.
	 */
	private FunctionSource getFunctionSource(DebuggableScript fnOrScript) {
		FunctionSource fsource = functionSource(fnOrScript);
		if (fsource == null) {
			String url = getNormalizedUrl(fnOrScript);
			SourceInfo si = getSourceInfo(url);
			if (si == null) {
				if (!fnOrScript.isGeneratedScript()) {
					// Not eval or Function, try to load it from URL
					String source = loadSource(url);
					if (source != null) {
						DebuggableScript top = fnOrScript;
						for (;;) {
							DebuggableScript parent = top.getParent();
							if (parent == null) {
								break;
							}
							top = parent;
						}
						registerTopScript(top, source);
						fsource = functionSource(fnOrScript);
					}
				}
			}
		}
		return fsource;
	}

	/**
	 * Loads the script at the given URL.
	 */
	private String loadSource(String sourceUrl) {
		String source = null;
		int hash = sourceUrl.indexOf('#');
		if (hash >= 0) {
			sourceUrl = sourceUrl.substring(0, hash);
		}
		try {
			InputStream is;
			openStream: {
				if (sourceUrl.indexOf(':') < 0) {
					// Can be a file name
					try {
						if (sourceUrl.startsWith("~/")) {
							String home = System.getProperty("user.home");
							if (home != null) {
								String pathFromHome = sourceUrl.substring(2);
								File f = new File(new File(home), pathFromHome);
								if (f.exists()) {
									is = new FileInputStream(f);
									break openStream;
								}
							}
						}
						File f = new File(sourceUrl);
						if (f.exists()) {
							is = new FileInputStream(f);
							break openStream;
						}
					} catch (SecurityException ex) {
					}
					// No existing file, assume missed http://
					if (sourceUrl.startsWith("//")) {
						sourceUrl = "http:" + sourceUrl;
					} else if (sourceUrl.startsWith("/")) {
						sourceUrl = "http://127.0.0.1" + sourceUrl;
					} else {
						sourceUrl = "http://" + sourceUrl;
					}
				}

				is = (new URL(sourceUrl)).openStream();
			}

			try {
				source = Kit.readReader(new InputStreamReader(is));
			} finally {
				is.close();
			}
		} catch (IOException ex) {
			System.err.println("Failed to load source from " + sourceUrl + ": "
					+ ex);
		}
		return source;
	}

	/**
	 * Registers the given script as a top-level script in the debugger.
	 */
	private void registerTopScript(DebuggableScript topScript, String source) {
		if (!topScript.isTopLevel()) {
			throw new IllegalArgumentException();
		}
		String url = getNormalizedUrl(topScript);
		DebuggableScript[] functions = getAllFunctions(topScript);
		//copy breakpoint from gui client		
		SourceInfo sourceInfo = null;
		try {
			sourceInfo = new SourceInfo(source, functions, url,
				m_callback.getBreakPoints(url));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}

		synchronized (m_urlToSourceInfo) {
			SourceInfo old = m_urlToSourceInfo.get(url);
			if (old != null) {
				sourceInfo.copyBreakpointsFrom(old);
			}
			m_urlToSourceInfo.put(url, sourceInfo);
			for (int i = 0; i != sourceInfo.getFunctionSourceCount(); ++i) {
				FunctionSource fsource = sourceInfo.getFunctionSource(i);
				String name = fsource.name();
				if (name.length() != 0) {
					m_functionNames.put(name, fsource);
				}
			}
		}

		synchronized (m_functionToSource) {
			for (int i = 0; i != functions.length; ++i) {
				FunctionSource fsource = sourceInfo.getFunctionSource(i);
				m_functionToSource.put(functions[i], fsource);
			}
		}

		try {
			m_callback.updateSourceText(sourceInfo);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the FunctionSource object for the given function or script.
	 */
	private FunctionSource functionSource(DebuggableScript fnOrScript) {
		return m_functionToSource.get(fnOrScript);
	}

	/**
	 * Returns an array of all function names.
	 */
	public String[] functionNames() {
		String[] a;
		synchronized (m_urlToSourceInfo) {
			Enumeration<String> e = m_functionNames.keys();
			a = new String[m_functionNames.size()];
			int i = 0;
			while (e.hasMoreElements()) {
				a[i++] = e.nextElement();
			}
		}
		return a;
	}

	/**
	 * Returns the FunctionSource object for the function with the given name.
	 */
	public FunctionSource functionSourceByName(String functionName) {
		return m_functionNames.get(functionName);
	}

	/**
	 * Returns the SourceInfo object for the given URL.
	 */
	public SourceInfo getSourceInfo(String url) {
		return m_urlToSourceInfo.get(url);
	}

	/**
	 * Returns the source URL for the given script or function.
	 */
	private String getNormalizedUrl(DebuggableScript fnOrScript) {
		String url = fnOrScript.getSourceName();
		if (url == null) {
			url = "<stdin>";
		} else {
			// Not to produce window for eval from different lines,
			// strip line numbers, i.e. replace all #[0-9]+\(eval\) by
			// (eval)
			// Option: similar teatment for Function?
			char evalSeparator = '#';
			StringBuffer sb = null;
			int urlLength = url.length();
			int cursor = 0;
			for (;;) {
				int searchStart = url.indexOf(evalSeparator, cursor);
				if (searchStart < 0) {
					break;
				}
				String replace = null;
				int i = searchStart + 1;
				while (i != urlLength) {
					int c = url.charAt(i);
					if (!('0' <= c && c <= '9')) {
						break;
					}
					++i;
				}
				if (i != searchStart + 1) {
					// i points after #[0-9]+
					if ("(eval)".regionMatches(0, url, i, 6)) {
						cursor = i + 6;
						replace = "(eval)";
					}
				}
				if (replace == null) {
					break;
				}
				if (sb == null) {
					sb = new StringBuffer();
					sb.append(url.substring(0, searchStart));
				}
				sb.append(replace);
			}
			if (sb != null) {
				if (cursor != urlLength) {
					sb.append(url.substring(cursor));
				}
				url = sb.toString();
			}
		}
		return url;
	}

	/**
	 * Returns an array of all functions in the given script.
	 */
	private static DebuggableScript[] getAllFunctions(DebuggableScript function) {
		ObjArray functions = new ObjArray();
		collectFunctions_r(function, functions);
		DebuggableScript[] result = new DebuggableScript[functions.size()];
		functions.toArray(result);
		return result;
	}

	/**
	 * Helper function for {@link #getAllFunctions(DebuggableScript)}.
	 */
	private static void collectFunctions_r(DebuggableScript function,
			ObjArray array) {
		array.add(function);
		for (int i = 0; i != function.getFunctionCount(); ++i) {
			collectFunctions_r(function.getFunction(i), array);
		}
	}

	/**
	 * Clears all breakpoints.
	 */
	public void clearAllBreakpoints() {
		Enumeration e = m_urlToSourceInfo.elements();
		while (e.hasMoreElements()) {
			SourceInfo si = (SourceInfo) e.nextElement();
			si.removeAllBreakpoints();
//			Breakpoints bpCache = BreakpointCache.getBreakpoints(si.url());
//			if (bpCache != null) {
//				bpCache.removeAllBreakpoints();
//			}
		}
	}

	/**
	 * Called when a breakpoint has been hit.
	 */
	void handleBreakpointHit(StackFrame frame, Context cx) {
		m_shouldBreak = false;
		try {
			interrupted(cx, frame, null);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Called when a script exception has been thrown.
	 */
	void handleExceptionThrown(Context cx, Throwable ex,
			StackFrame frame) {
		if (m_breakOnExceptions) {
			ContextData cd = frame.contextData();
			if (cd.getLastProcessedException() != ex) {
				try {
					interrupted(cx, frame, ex);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
				cd.setLastProcessedException(ex);
			}
		}
	}

	/**
	 * Returns the current ContextData object.
	 */
	public ContextData currentContextData() {
		return m_interruptedContextData;
	}

	/**
	 * Sets the action to perform to end interruption.
	 */
	public void setReturnValue(int returnValue) {
		synchronized (m_monitor) {
			m_returnValue = returnValue;
			m_monitor.notify();
		}
	}

	/**
	 * Resumes execution of script.
	 */
	public void go() {
		synchronized (m_monitor) {
			m_returnValue = GO;
			m_monitor.notifyAll();
		}
	}

	/**
	 * Evaluates the given script.
	 */
	public String eval(String expr) {
		String result = "undefined";
		if (expr == null) {
			return result;
		}
		ContextData contextData = currentContextData();
		if (contextData == null || m_frameIndex >= contextData.frameCount()) {
			return result;
		}
		StackFrame frame = contextData.getFrame(m_frameIndex);
		if (contextData.getEventThreadFlag()) {
			Context cx = Context.getCurrentContext();
			result = do_eval(cx, frame, expr);
		} else {
			synchronized (m_monitor) {
				if (m_insideInterruptLoop) {
					m_evalRequest = expr;
					m_evalFrame = frame;
					m_monitor.notify();
					do {
						try {
							m_monitor.wait();
						} catch (InterruptedException exc) {
							Thread.currentThread().interrupt();
							break;
						}
					} while (m_evalRequest != null);
					result = m_evalResult;
				}
			}
		}
		return result;
	}

	/**
	 * Compiles the given script.
	 */
	public void compileScript(String url, String text) {
		DimIProxy action = new DimIProxy(this, IPROXY_COMPILE_SCRIPT);
		action.m_url = url;
		action.m_text = text;
		action.withContext();
	}

	/**
	 * Evaluates the given script.
	 */
	public void evalScript(final String url, final String text) {
		DimIProxy action = new DimIProxy(this, IPROXY_EVAL_SCRIPT);
		action.m_url = url;
		action.m_text = text;
		action.withContext();
	}

	/**
	 * Converts the given script object to a string.
	 */
	public String objectToString(Object object) {
		DimIProxy action = new DimIProxy(this, IPROXY_OBJECT_TO_STRING);
		action.m_object = object;
		action.withContext();
		return action.m_stringResult;
	}

	/**
	 * Returns whether the given string is syntactically valid script.
	 */
	public boolean stringIsCompilableUnit(String str) {
		DimIProxy action = new DimIProxy(this, IPROXY_STRING_IS_COMPILABLE);
		action.m_text = str;
		action.withContext();
		return action.m_booleanResult;
	}

	/**
	 * Returns the value of a property on the given script object.
	 */
	public Object getObjectProperty(Object object, Object id) {
		DimIProxy action = new DimIProxy(this, IPROXY_OBJECT_PROPERTY);
		action.m_object = object;
		action.m_id = id;
		action.withContext();
		return action.m_objectResult;
	}
	
//	public Object[] getObjectIds(Object object) {
//		
//	}

	/**
	 * Returns an array of the property names on the given script object.
	 */
	public Object[] getObjectIds(Object object) {
		DimIProxy action = new DimIProxy(this, IPROXY_OBJECT_IDS);
		action.m_object = object;
		action.withContext();
		return action.m_objectArrayResult;
	}

	/**
	 * Returns the value of a property on the given script object.
	 */
	private Object getObjectPropertyImpl(Context cx, Object object, Object id) {
		Scriptable scriptable = (Scriptable) object;
		Object result;
		if (id instanceof String) {
			String name = (String) id;
			if (name.equals("this")) {
				result = scriptable;
			} else if (name.equals("__proto__")) {
				result = scriptable.getPrototype();
			} else if (name.equals("__parent__")) {
				result = scriptable.getParentScope();
			} else {
				result = ScriptableObject.getProperty(scriptable, name);
				if (result == ScriptableObject.NOT_FOUND) {
					result = Undefined.instance;
				}
			}
		} else {
			int index = ((Integer) id).intValue();
			result = ScriptableObject.getProperty(scriptable, index);
			if (result == ScriptableObject.NOT_FOUND) {
				result = Undefined.instance;
			}
		}
		return result;
	}

	/**
	 * Returns an array of the property names on the given script object.
	 */
	private Object[] getObjectIdsImpl(Context cx, Object object) {
		if (!(object instanceof Scriptable) || object == Undefined.instance) {
			return Context.emptyArgs;
		}

		Object[] ids;
		Scriptable scriptable = (Scriptable) object;
		if (scriptable instanceof DebuggableObject) {
			ids = ((DebuggableObject) scriptable).getAllIds();
		} else {
			ids = scriptable.getIds();
		}

		Scriptable proto = scriptable.getPrototype();
		Scriptable parent = scriptable.getParentScope();
		int extra = 0;
		if (proto != null) {
			++extra;
		}
		if (parent != null) {
			++extra;
		}
		if (extra != 0) {
			Object[] tmp = new Object[extra + ids.length];
			System.arraycopy(ids, 0, tmp, extra, ids.length);
			ids = tmp;
			extra = 0;
			if (proto != null) {
				ids[extra++] = "__proto__";
			}
			if (parent != null) {
				ids[extra++] = "__parent__";
			}
		}

		return ids;
	}

	/**
	 * Interrupts script execution.
	 * @throws RemoteException 
	 */
	private void interrupted(Context cx, final StackFrame frame,
			Throwable scriptException) throws RemoteException {
		ContextData contextData = frame.contextData();
		boolean eventThreadFlag = false;
		try {
			eventThreadFlag = m_callback.isGuiEventThread();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		contextData.setEventThreadFlag(eventThreadFlag);

		boolean recursiveEventThreadCall = false;

		interruptedCheck: synchronized (m_eventThreadMonitor) {
			if (eventThreadFlag) {
				if (m_interruptedContextData != null) {
					recursiveEventThreadCall = true;
					break interruptedCheck;
				}
			} else {
				while (m_interruptedContextData != null) {
					try {
						m_eventThreadMonitor.wait();
					} catch (InterruptedException exc) {
						return;
					}
				}
			}
			m_interruptedContextData = contextData;
		}

		if (recursiveEventThreadCall) {
			// XXX: For now the following is commented out as on Linux
			// too deep recursion of dispatchNextGuiEvent causes GUI lockout.
			// Note: it can make GUI unresponsive if long-running script
			// will be called on GUI thread while processing another interrupt
			if (false) {
				// Run event dispatch until gui sets a flag to exit the initial
				// call to interrupted.
				while (m_returnValue == -1) {
					try {
						m_callback.dispatchNextGuiEvent();
					} catch (InterruptedException exc) {
					} catch (RemoteException e) {
						throw new RuntimeException(e);
					}
				}
			}
			return;
		}

		if (m_interruptedContextData == null)
			Kit.codeBug();

		try {
			do {
				int frameCount = contextData.frameCount();
				m_frameIndex = frameCount - 1;

				final String threadTitle = Thread.currentThread().toString();
				final String alertMessage;
				if (scriptException == null) {
					alertMessage = null;
				} else {
					alertMessage = scriptException.toString();
				}

				int returnValue = -1;
				if (!eventThreadFlag) {
					synchronized (m_monitor) {
						if (m_insideInterruptLoop)
							Kit.codeBug();
						m_insideInterruptLoop = true;
						m_evalRequest = null;
						m_returnValue = -1;
						//fix the bug due to Interpreter's "Icode_TAIL_CALL" handling which incorrectly exit the frame
						if (frame.contextData().getFrame(0) != frame) {
							System.err.println("remove this");
							frame.contextData().pushFrame(frame);
						}
						m_callback.enterInterrupt(
							new StackFrameInfo(m_frameIndex, frame.getUrl(), frame.getLineNumber(),
								frame.thisObj() != frame.scope()), 
							threadTitle, alertMessage);
						try {
							for (;;) {
								try {
									m_monitor.wait();
								} catch (InterruptedException exc) {
									Thread.currentThread().interrupt();
									break;
								}
								if (m_evalRequest != null) {
									m_evalResult = null;
									try {
										m_evalResult = do_eval(cx, m_evalFrame,
												m_evalRequest);
									} finally {
										m_evalRequest = null;
										m_evalFrame = null;
										m_monitor.notify();
									}
									continue;
								}
								if (m_returnValue != -1) {
									returnValue = m_returnValue;
									break;
								}
							}
						} finally {
							m_insideInterruptLoop = false;
						}
					}
				} else {
					m_returnValue = -1;
					m_callback.enterInterrupt(
						new StackFrameInfo(m_frameIndex, frame.getUrl(), frame.getLineNumber(),
							frame.thisObj() != frame.scope()),
						threadTitle, alertMessage);
					while (m_returnValue == -1) {
						try {
							m_callback.dispatchNextGuiEvent();
						} catch (InterruptedException exc) {
						}
					}
					returnValue = m_returnValue;
				}
				switch (returnValue) {
				case STEP_OVER:
					contextData.setBreakNextLine(true);
					contextData.setStopAtFrameDepth(contextData.frameCount());
					break;
				case STEP_INTO:
					contextData.setBreakNextLine(true);
					contextData.setStopAtFrameDepth(-1);
					break;
				case STEP_OUT:
					if (contextData.frameCount() > 1) {
						contextData.setBreakNextLine(true);
						contextData.setStopAtFrameDepth(contextData.frameCount() - 1);
					}
					break;
				}
			} while (false);
		} finally {
			synchronized (m_eventThreadMonitor) {
				m_interruptedContextData = null;
				m_eventThreadMonitor.notifyAll();
			}
		}

	}

	/**
	 * Evaluates script in the given stack frame.
	 */
	private static String do_eval(Context cx, StackFrame frame, String expr) {
		String resultString;
		Debugger saved_debugger = cx.getDebugger();
		Object saved_data = cx.getDebuggerContextData();
		int saved_level = cx.getOptimizationLevel();

		cx.setDebugger(null, null);
		cx.setOptimizationLevel(-1);
		cx.setGeneratingDebug(false);
		try {
			Callable script = (Callable) cx.compileString(expr, "", 0, null);
			Object result = script.call(cx, frame.scope(), frame.thisObj(),
					ScriptRuntime.emptyArgs);
			if (result == Undefined.instance) {
				resultString = "";
			} else {
				resultString = ScriptRuntime.toString(result);
			}
		} catch (Exception exc) {
			resultString = exc.getMessage();
		} finally {
			cx.setGeneratingDebug(true);
			cx.setOptimizationLevel(saved_level);
			cx.setDebugger(saved_debugger, saved_data);
		}
		if (resultString == null) {
			resultString = "null";
		}
		return resultString;
	}

	/**
	 * Proxy class to implement debug interfaces without bloat of class files.
	 */
	private static class DimIProxy implements ContextAction,
			ContextFactory.Listener, Debugger {

		/**
		 * The debugger.
		 */
		private DebuggerAdapter m_dim;

		/**
		 * The interface implementation type. One of the IPROXY_* constants
		 * defined in {@link DebuggerAdapter}.
		 */
		private int m_type;

		/**
		 * The URL origin of the script to compile or evaluate.
		 */
		private String m_url;

		/**
		 * The text of the script to compile, evaluate or test for compilation.
		 */
		private String m_text;

		/**
		 * The object to convert, get a property from or enumerate.
		 */
		private Object m_object;

		/**
		 * The property to look up in {@link #m_object}.
		 */
		private Object m_id;

		/**
		 * The boolean result of the action.
		 */
		private boolean m_booleanResult;

		/**
		 * The String result of the action.
		 */
		private String m_stringResult;

		/**
		 * The Object result of the action.
		 */
		private Object m_objectResult;

		/**
		 * The Object[] result of the action.
		 */
		private Object[] m_objectArrayResult;

		/**
		 * Creates a new DimIProxy.
		 */
		private DimIProxy(DebuggerAdapter dim, int type) {
			m_dim = dim;
			m_type = type;
		}

		// ContextAction

		/**
		 * Performs the action given by {@link #m_type}.
		 */
		public Object run(Context cx) {
			switch (m_type) {
			case IPROXY_COMPILE_SCRIPT:
				cx.compileString(m_text, m_url, 1, null);
				break;

			case IPROXY_EVAL_SCRIPT: {
				Scriptable scope = null;
				if (m_dim.m_scopeProvider != null) {
					scope = m_dim.m_scopeProvider.getScope();
				}
				if (scope == null) {
					scope = new ImporterTopLevel(cx);
				}
				cx.evaluateString(scope, m_text, m_url, 1, null);
			}
				break;

			case IPROXY_STRING_IS_COMPILABLE:
				m_booleanResult = cx.stringIsCompilableUnit(m_text);
				break;

			case IPROXY_OBJECT_TO_STRING:
				if (m_object == Undefined.instance) {
					m_stringResult = "undefined";
				} else if (m_object == null) {
					m_stringResult = "null";
				} else if (m_object instanceof NativeCall) {
					m_stringResult = "[object Call]";
				} else {
					m_stringResult = Context.toString(m_object);
				}
				break;

			case IPROXY_OBJECT_PROPERTY:
				m_objectResult = m_dim.getObjectPropertyImpl(cx, m_object, m_id);
				break;

			case IPROXY_OBJECT_IDS:
				m_objectArrayResult = m_dim.getObjectIdsImpl(cx, m_object);
				break;

			default:
				throw Kit.codeBug();
			}
			return null;
		}

		/**
		 * Performs the action given by {@link #m_type} with the attached
		 * {@link ContextFactory}.
		 */
		private void withContext() {
			m_dim.m_contextFactory.call(this);
		}

		// ContextFactory.Listener

		/**
		 * Called when a Context is created.
		 */
		public void contextCreated(Context cx) {
			if (m_type != IPROXY_LISTEN)
				Kit.codeBug();
			ContextData contextData = new ContextData();
			Debugger debugger = new DimIProxy(m_dim, IPROXY_DEBUG);
			cx.setDebugger(debugger, contextData);
			cx.setGeneratingDebug(true);
			cx.setOptimizationLevel(-1);
		}

		/**
		 * Called when a Context is destroyed.
		 */
		public void contextReleased(Context cx) {
			if (m_type != IPROXY_LISTEN)
				Kit.codeBug();
		}

		// Debugger

		/**
		 * Returns a StackFrame for the given function or script.
		 */
		public DebugFrame getFrame(Context cx, DebuggableScript fnOrScript) {
			if (m_type != IPROXY_DEBUG)
				Kit.codeBug();

			FunctionSource item = m_dim.getFunctionSource(fnOrScript);
			if (item == null) {
				// Can not debug if source is not available
				return null;
			}
			return new StackFrame(cx, m_dim, item);
		}

		/**
		 * Called when compilation is finished.
		 */
		public void handleCompilationDone(Context cx,
				DebuggableScript fnOrScript, String source) {
			if (m_type != IPROXY_DEBUG)
				Kit.codeBug();

			if (!fnOrScript.isTopLevel()) {
				return;
			}
			m_dim.registerTopScript(fnOrScript, source);
		}
	}
	
	public int getFrameCount() {
		return currentContextData().frameCount();
	}
	
	public StackFrameInfo getFrameInfo(int frameIndex) {
		StackFrame frame = currentContextData().getFrame(frameIndex);
		return new StackFrameInfo(frameIndex, frame.getUrl(), frame.getLineNumber(),
			frame.thisObj() != frame.scope());
	}

	public IVariable[] loadMembers(long id) {
		StackFrame frame = currentContextData().getFrame(m_frameIndex);
		Object obj = frame.getObject(id);
		Object[] propIds = getObjectIds(obj);
		IVariable[] members = new IVariable[propIds.length];
		Object prop = null;
		IValue value;
		for (int i = 0; i < members.length; i++) {
			Object propId = propIds[i];
			prop = getObjectProperty(obj, propId);
			
			value = Value.forPrimitive(prop);
			if (value == null) {
				value = new Value(VariableType.OBJECT, null, frame.getId(prop));
			}
			members[i] = new Variable(propId.toString(), value, obj.getClass().getName());
		}
		return members;
	}
	
	public String getObjectValueAsString(long id) {
		StackFrame frame = currentContextData().getFrame(m_frameIndex);
		Object obj = frame.getObject(id);
		return objectToString(obj);
	}
}
