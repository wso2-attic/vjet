/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.WeakHashMap;

import org.ebayopensource.dsf.js.dbgp.file.IVFile;
import org.ebayopensource.dsf.js.dbgp.file.IVFileManager;
import org.ebayopensource.dsf.js.dbgp.file.VFileManager;
import org.ebayopensource.dsf.js.dbgp.file.VFileSourceProvider;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.NativeJavaArray;
import org.mozilla.mod.javascript.NativeJavaClass;
import org.mozilla.mod.javascript.NativeJavaObject;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;
import org.mozilla.mod.javascript.Undefined;
import org.mozilla.mod.javascript.UniqueTag;
import org.mozilla.mod.javascript.debug.DebugFrame;
import org.mozilla.mod.javascript.debug.DebuggableScript;
import org.mozilla.mod.javascript.debug.Debugger;

public class DBGPDebugger extends Thread implements Debugger, Observer {

	private Socket					m_socket;
	private PrintStream				m_out;
	private Map<String, Command>	m_strategies	= new HashMap<String, Command>();
	String							m_runTransctionId;
	DBGPStackManager				m_stackmanager;
	public boolean					isInited;
	private boolean					m_sessionEstablished;
	private String					m_sessionId;
	private static int				s_count			= 0;
	// modify by patrick
	private IVFileManager			m_vFileManager		= new VFileManager();
	private ISourceProvider[]		m_sourceProviders;
	private SourceProvider			m_sourceProvider	= new SourceProvider();
	private Context m_ctx;
	private Scriptable m_scope;
	// end modify

	static abstract class Command {
		abstract void parseAndExecute(String command,
				Map<String, String> options);
	}

	void printResponse(String response) {
		m_out.print(response.length());
		m_out.write(0);
		m_out.print(response);
		m_out.write(0);
		m_out.flush();
	}

	public void setContext(Context cx) {
		DBGPStackManager manager = DBGPStackManager.getManager(cx);
		manager.setDebugger(this);
		m_stackmanager = manager;
	}

	public DBGPDebugger(Socket socket, String file, String sessionId, Context ct)
			throws IOException {
		m_sessionId = sessionId;
		createDebugger(socket, ct);
	}

	public DBGPDebugger(Socket socket, Context ct) throws IOException {

		super();
		createDebugger(socket, ct);
	}

	private void createDebugger(Socket socket, Context ct) throws IOException {
		m_socket = socket;
		m_ctx = ct;
		// m_runTransctionId=m_runTransctionId==null?"0":m_runTransctionId;
		m_stackmanager = DBGPStackManager.getManager(ct);
		m_stackmanager.suspend();
		m_out = new PrintStream(socket.getOutputStream());
		m_stackmanager.setDebugger(this);
		m_strategies.put("feature_get", new FeatureGetCommand(this));
		m_strategies.put("feature_set", new FeatureSetCommand(this));
		m_strategies.put("stdin", new StdInCommand(this));
		m_strategies.put("stdout", new StdOutCommand(this));
		m_strategies.put("stderr", new StdErrCommand(this));
		m_strategies.put("run", new RunCommand(this));
		m_strategies.put("context_names", new ContextNamesCommand(this));
		m_strategies.put("stop", new StopCommand(this));
		m_strategies.put("step_over", new StepOverCommand(this));
		m_strategies.put("step_into", new StepIntoCommand(this));
		m_strategies.put("step_out", new StepOutCommand(this));
		m_strategies.put("breakpoint_get", new GetBreakPointCommand(this));
		m_strategies.put("breakpoint_set", new SetBreakPointCommand(this));
		m_strategies
				.put("breakpoint_remove", new RemoveBreakPointCommand(this));
		m_strategies
				.put("breakpoint_update", new UpdateBreakPointCommand(this));
		m_strategies.put("context_get", new ContextGetCommand(this));
		m_strategies.put("property_set", new PropertySetCommand(this));
		m_strategies.put("eval", new EvalCommand(this));
		m_strategies.put("property_get", new PropertyGetCommand(this));
		m_strategies.put("break", new BreakCommand(this));
		m_strategies.put("stack_depth", new StackDepthCommand(this));
		m_strategies.put("stack_get", new StackGetCommand(this));
		// add by patrick
		m_strategies.put("source",
				new SourceCommand(this, getSourceProviders()));
		m_strategies.put("source_list", new SourceListCommand(this,
				getSourceProviders()));
		// end add
		m_out.flush();
		isInited = true;

	}

	private ISourceProvider[] getSourceProviders() {
		if (m_sourceProviders == null) {
			VFileSourceProvider vFileSourceProvider = new VFileSourceProvider(m_vFileManager);
			m_sourceProviders = new ISourceProvider[] {vFileSourceProvider,m_sourceProvider};
		}
		return m_sourceProviders;
	}

	protected void printProperty(String id, String fullName, Object value,
			StringBuffer properties, int level, boolean addChilds) {
		boolean hasChilds = false;

		int numC = 0;
		String vlEncoded;
		String name_of_object_class = "";
		String data_type = "Object";
		if (value instanceof Function) {
			data_type = "function";
		} else if (value instanceof NativeJavaObject) {
			data_type = "javaobject";
		} else if (value instanceof NativeJavaClass) {
			data_type = "javaclass";
		} else if (value instanceof NativeJavaArray) {
			data_type = "javaarray";
		}
		if (value instanceof Scriptable) {
			hasChilds = true;
			StringBuffer stringBuffer = new StringBuffer();
			Scriptable p = (Scriptable) value;
			// add by patrick
//			ScriptEngineCtx.ctx().setScope(p);
			// end add
			// value = stringBuffer; to fix bug 2206, findbugs warning, comment
			// it.
			Object[] ids = p.getIds();
			numC = ids.length;
			String nv = p.getClassName();
			name_of_object_class = nv;
			if (p instanceof NativeJavaObject) {

				NativeJavaObject obj = (NativeJavaObject) p;

				Object unwrap = obj.unwrap();
				if (unwrap instanceof Class) {
					nv = ((Class<?>) unwrap).getName();
				} else if (unwrap.getClass().isArray()) {
					String string = unwrap.getClass().getName();
					int len = Array.getLength(unwrap);
					int q = string.indexOf('[');
					if (q != -1) {
						string = string.substring(0, q);
					}
					int q1 = string.indexOf(']');
					nv = string + "[" + len + "]";
					if (q1 != -1) {
						nv += string.substring(q1);
					}
				} else {
					if (unwrap instanceof String) {
						nv = unwrap.toString();
					} else {
						String string = unwrap.toString();
						nv = unwrap.getClass().getName() + "(" + string + ")";
					}
				}

			}

			stringBuffer.append(Base64Helper.encodeString(nv));
			if (addChilds) {
				for (int a = 0; a < ids.length; a++) {
					Object pvalue;
					// modify by patrick
					if (ids[a] instanceof Integer) {
						pvalue = p.get(((Integer) ids[a]).intValue(), p);
					} else if (ids[a] instanceof String) {
						pvalue = p.get(ids[a].toString(), p);
					} else {
						pvalue = null;
					}
					// end modify
					printProperty(ids[a].toString(), fullName + "." + ids[a],
							pvalue, stringBuffer, level + 1, false);
				}
			}
			vlEncoded = stringBuffer.toString();
		} else {
			if (!(value instanceof Undefined)) {
				if (value == UniqueTag.NOT_FOUND) {
					vlEncoded = "";
				} else {
					vlEncoded = Base64Helper.encodeString(value != null ? value
							.toString() : "null");
				}
			} else {
				vlEncoded = Base64Helper.encodeString("Undefined");
			}
			if (value != null)
				name_of_object_class = value.getClass().getName();
		}

		properties.append("<property\r\n" + "    name=\"" + id + "\"\r\n"
				+ "    fullname=\"" + fullName + "\"\r\n" + "    type=\""
				+ data_type + "\"\r\n" + "    classname=\""
				+ name_of_object_class + "\"\r\n" + "    constant=\"0\"\r\n"
				+ "    children=\"" + (hasChilds ? 1 : 0) + "\"\r\n"
				+ "    encoding=\"base64\"\r\n" + "    numchildren=\"" + numC
				+ "\">\r\n" + vlEncoded + "</property>\r\n");
	}

	public void run() {
		try {
			DataInputStream ds = new DataInputStream(m_socket.getInputStream());
			StringBuffer buf = new StringBuffer();
			// Context.enter();
			while (ds.available() >= 0) {
				int c = ds.read();
				if (c < 0)
					break;
				if (c < 32) {
					String s = buf.toString();

					int indexOf = s.indexOf(' ');
					if (indexOf != -1) {
						String commandId = buf.substring(0, indexOf);
						Command object = (Command) m_strategies.get(commandId);
						if (object == null) {
							System.err.println(commandId);
							continue;
						}
						HashMap<String, String> options = new HashMap<String, String>();

						String result = buf.substring(indexOf + 1);
						String[] split = result.split(" ");

						try {
							for (int a = 0; a < split.length; a++) {
								options.put(split[a], split[++a]);
							}
						} catch (Exception e) {
						}
						object.parseAndExecute(result, options);
					}
					buf = new StringBuffer();
				} else {
					buf.append((char) c);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public DebugFrame getFrame(Context cx, DebuggableScript fnOrScript) {
		if (!m_sessionEstablished) {

			String jsSourceFilePath = fnOrScript.getSourceName();

			String response = "<init appid=\"APPID\"\r\n" + "      idekey=\""
					+ m_sessionId + "\"\r\n" + "      session=\"" + m_sessionId
					+ "\"\r\n" + "      thread=\""
					+ Thread.currentThread().getId() + "\"\r\n"
					+ "      parent=\"PARENT_APPID\"\r\n"
					+ "      language=\"javascript\"\r\n"
					+ "      protocol_version=\"1.0\"\r\n"
					+ "      fileuri=\"file://" + jsSourceFilePath + "\"\r\n"
					+ "/>";

			printResponse(response);
			m_sessionEstablished = true;
		}
		// modify by patrick
		IVFile vfile = m_vFileManager.getFileByName(fnOrScript.getSourceName());
		String filePath = vfile != null ? vfile.toURI().toASCIIString()
				: fnOrScript.getSourceName();
		// end modify
		return new DBGPDebugFrame(cx, fnOrScript, filePath);
	}

	public void handleCompilationDone(Context cx, DebuggableScript fnOrScript,
			String source) {
		// TODO create mapping between source name and source
		// create file right here for script fragments
		s_count++;
		// modify by patrick
		String sourceName = fnOrScript.getSourceName();
		if (!isValidURL(sourceName)
				&& (m_vFileManager.getFileByName(sourceName) == null)) {
			m_vFileManager.createFile(s_count, sourceName, source);
		} else if (isValidURL(sourceName)
				&& (!m_sourceProvider.containsFile(sourceName))
				&& (m_vFileManager.getFileByName(sourceName) == null)) {
			m_sourceProvider.addFile(sourceName);
		}
		// end modify
	}

	private boolean isValidURL(String name) {
		// assuming name is url
		try {
			URL url = new URL(name);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}

	}

	public void update(Observable arg0, Object arg1) {
		if (m_runTransctionId != null) {
			printResponse("<response command=\"run\"\r\n" + "status=\"break\""
					+ " reason=\"ok\"" + " transaction_id=\""
					+ m_runTransctionId + "\">\r\n" + "</response>\r\n" + "");
		}
	}

	public void notifyEnd() {
		printResponse("<response command=\"run\"\r\n" + "status=\"stopped\""
				+ " reason=\"ok\"" + " transaction_id=\"" + m_runTransctionId
				+ "\">\r\n" + "</response>\r\n" + "");

		cleanUpTempFiles();

	}

	private void cleanUpTempFiles() {
		// modify by patrick
		m_vFileManager.clear();
		// end modify
	}

	public void access(String property, ScriptableObject object) {
		List<BreakPoint> list = m_stackmanager.getManager().getWatchPoints(
				property);

		for (BreakPoint watchPoint : list) {
			if (watchPoint != null && watchPoint.m_enabled) {
				if (watchPoint.m_isAccess) {
					String wkey = watchPoint.m_file + watchPoint.m_line;
					String s = cache.get(object);
					if (s != null && s.equals(wkey)) {
						m_stackmanager.getObserver().update(null, this);
						m_stackmanager.waitForNotify();
					}
				}
			}
		}
	}

	WeakHashMap<ScriptableObject, String>	cache	= new WeakHashMap<ScriptableObject, String>();

	public void modification(String property, ScriptableObject object) {

		List<BreakPoint> list = m_stackmanager.getManager().getWatchPoints(
				property);
		if (list != null && m_stackmanager.getStackDepth() > 0) {
			for (BreakPoint watchPoint : list) {
				if (watchPoint != null && watchPoint.m_enabled) {
					String sn = m_stackmanager.getStackFrame(0).getSourceName();
					int ln = m_stackmanager.getStackFrame(0).getLineNumber();
					String key = sn + ln;
					String wkey = watchPoint.m_file + watchPoint.m_line;
					if (key.equals(wkey)) {
						cache.put(object, wkey);
					}
					if (watchPoint.m_isModification) {
						String object2 = cache.get(object);
						if (object2 != null && object2.equals(wkey)) {
							m_stackmanager.getObserver().update(null, this);
							synchronized (m_stackmanager) {
								try {
									m_stackmanager.wait();
								} catch (InterruptedException e) {
									// DONOTHING
								}
							}
						}
					}
				}
			}
		}
	}

	public void setProperty(String name, String value) {

	}

	public void setSuspendOnExit(boolean parseBoolean) {
		m_stackmanager.setSuspendOnExit(parseBoolean);
	}

	public void setSuspendOnEntry(boolean parseBoolean) {
		m_stackmanager.setSuspendOnEntry(parseBoolean);
	}
	
	// add by patrick
	public Context getContext() {
		return this.m_ctx;
	}

	public synchronized Scriptable getCurrentScope() {
		return this.m_scope;
	}

	public synchronized void setCurrentScope(Scriptable scope) {
		this.m_scope = scope;
	}
	// end add
}
