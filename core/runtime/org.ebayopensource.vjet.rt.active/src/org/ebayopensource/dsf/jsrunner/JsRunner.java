/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsrunner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.ebayopensource.dsf.active.client.AHtmlParser;
import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.client.ScriptExecutor;
import org.ebayopensource.dsf.active.client.WindowFactory;
import org.ebayopensource.dsf.active.util.ScriptingSessionClassLoader;
import org.ebayopensource.dsf.active.util.VJContextFactory;
import org.ebayopensource.dsf.dap.proxy.ScriptEngineCtx;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.dap.rt.DapCtx.ExeMode;
import org.ebayopensource.dsf.dap.rt.IBrowserEmulatorListener;
import org.ebayopensource.dsf.html.js.ActiveJsExecutionControlCtx;
import org.ebayopensource.dsf.jsdebugger.DebuggerAdapter;
import org.ebayopensource.dsf.jsdebugger.JsDebuggerEnabler;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.json.JsonObject;
import org.ebayopensource.dsf.service.serializer.JsonSerializer;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.ContextFactory;
import org.mozilla.mod.javascript.IJsJavaProxy;
import org.mozilla.mod.javascript.Scriptable;

/**
 * Java Script runner with debugger
 */
public class JsRunner {
	
	protected DebuggerAdapter m_dbg = null;
	protected AWindow m_window = null;
	protected Context m_cx;
	protected Scriptable m_scope;
	protected ActiveWeb m_activeWeb;
	protected ProgramInfo m_info;
	protected ExeMode m_mode = ExeMode.TRANSLATE;
	
	public static final String JS_OPTION_TOKEN = "-V";
	public static final String BROWSER_KEY = "browser";
	public static final String LOAD_JS_KEY = "loadJs";
	public static final String LOAD_HTML_KEY = "loadHtml";
	public static final String BROWSER_DISPLAY_KEY = "browserDisplay";
	public static final String BROWSER_SERVICE_URL_KEY = "browserServiceUrl";
	
	public static final String FILE_TYPE_KEY = "type";	
	public static final String HTML_FILE_TYPE = "html";
	
	public static final String DAP_MODE_KEY = "dapMode";
	public static final String WAIT_TIMEOUT_KEY = "waitTimeout";
	public static final String A_MODE_VALUE = "A";
	public static final String W_MODE_VALUE = "W";
	
	public static final String LIST_SEPERATOR = ";";
	public static final String HTTP = "http://";
	
	private static final String TEST_HTML_PAGE = "<html><head><title>VJET TEST PAGE</title></head><body></body></html>";
	
	/**
	 * the args should follow the following pattern:
	 * [-V{key=value} ...] fileName [jsClass jsArgs ...]
	 * for example,
	 * 		-Vbrowser=IE d:/a/b/c/Xyz.js c.XYZ "dsf efg" 12
	 * for html input, please add -Vtype=html option
	 * 
	 * -Vbrowser option enables browser env.
	 * 
	 * For html input, if -Vbrowser option was not specified, 
	 * IE browser type will be defaulted to.
	 * 
	 * -VloadJs option enables the preloading of a set of js files, for example
	 * 		-VloadJs=d:/a/b/c/ext1.js;http://dojo.org/dojo.js
	 * 
	 * -VloadHtml option enables the preloading of a HTML document, for example
	 * 		-VloadHtml=d:/a/b/c/doc.html
	 * 
	 * -VbrowserServiceUrl open enables remotely launching of browser via BrowserService
	 */
	public static void main(String[] args) {
		
		try {
			ProgramInfo info = getProgramInfo(args);
			if (A_MODE_VALUE.equals(info.getOption(DAP_MODE_KEY))) {
				DapCtx.ctx().setExeMode(ExeMode.ACTIVE);
			}
			IBrowserLauncher browserLauncher = null;
			String browserServiceUrl = info.getBrowserServiceUrl();
			if (browserServiceUrl != null) {
				browserLauncher = new BrowserRemoteLauncher(browserServiceUrl);
			}
			JsRunner runner = new JsRunner(info, needDebug(), browserLauncher);
			if (runner.m_activeWeb != null) {
				runner.m_activeWeb.waitForWindowLoaded();
				runner.m_activeWeb.waitForExit();
				runner.m_activeWeb.destroy();
				return;
			}
			runner.complete();
		} catch (MalformedURLException e) {
			System.err.println("Provided  JS File path is not correct : "+args[0]);
		}
	}
	
	public JsRunner(ProgramInfo info, boolean debug) {
		this(info, debug, null);
	}
	
	public JsRunner(ProgramInfo info, boolean debug, IBrowserLauncher launcher) {
		m_info = info;
		String modeValue = m_info.getOption(DAP_MODE_KEY);
		if (A_MODE_VALUE.equals(modeValue)) {
			m_mode = ExeMode.ACTIVE;
		} else if (W_MODE_VALUE.equals(modeValue)) {
			m_mode = ExeMode.WEB;
		}
		BrowserType type = m_info.getBrowserType();		
		String htmlFile = m_info.getPreloadHtml();
		InputStream htmlInputStream = null;
		if (htmlFile != null) {
			try {
				htmlInputStream = toUrl(htmlFile).openConnection().getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (htmlFile == null && (m_info.needBrowserDisplay()||m_mode == ExeMode.WEB)) {
			htmlInputStream = new ByteArrayInputStream(TEST_HTML_PAGE.getBytes());
		}
		if (htmlInputStream != null && (m_info.needBrowserDisplay()||m_mode == ExeMode.WEB)) {
			loadActiveWeb(htmlInputStream, launcher);
			if (m_mode == ExeMode.WEB) {
				preloadJs();
				m_activeWeb.loadJs(m_info.getFileName());
			} else {
				m_activeWeb.addListener(createWebListener(debug));
			}
			m_activeWeb.displayUrlInBrowser(type);
			return;
		}
		
		// Need to have our own factory to override some hasFeature(..) lookups
		if (!ContextFactory.hasExplicitGlobal()) {
			ContextFactory.initGlobal(new VJContextFactory()) ;
		}
		
		if (debug) {
			initDebugger(type != null);
		}		
		if (type != null) {
			ActiveJsExecutionControlCtx.ctx().setExecuteJavaScript(true);
			m_window = (AWindow)WindowFactory.createWindow(type);
			m_cx = m_window.getContext();
			m_scope = m_window;
			if (debug) {
				m_dbg = m_window.getDebugger();
			}
		}
		else {
			m_cx = ContextFactory.getGlobal().enterContext();
			if (DapCtx.ctx().isActiveMode()) {
				m_cx.setApplicationClassLoader(new ScriptingSessionClassLoader());	
			}
			m_cx.setLanguageVersion(Context.VERSION_1_5);
			m_cx.setOptimizationLevel(-1); //using interpret mode
			m_scope = m_cx.initStandardObjects();
			if (DapCtx.ctx().isActiveMode()) {
				ScriptEngineCtx scriptEngineCtx = ScriptEngineCtx.ctx();
				scriptEngineCtx.setScriptContext(m_cx);
				scriptEngineCtx.setScope(m_scope);
			}
		}
	}
	
	
	/**
	 * Load program (html or js file), then execute js main if exists
	 * 
	 * This call will clean up the runner, so it should not be reused.
	 */
	public void complete() {
		if (m_activeWeb != null) { //run in virtual browser with real browser as displayer
			return;
		}
		if (m_dbg != null) {
			m_dbg.setBreak();
		}
		try {
			preload();
			if (m_info.isHtml()) {
				loadHtml(m_info.getFileName());
				execMain(m_info.getJsClassName(), m_info.getJsArgs());
			}
			else {
				exec(m_info.getFileName(), m_info.getJsClassName(), m_info.getJsArgs());
			}
		}
		finally {
			String timeout = m_info.getOption(WAIT_TIMEOUT_KEY);
			finalize((timeout==null)?1:Integer.parseInt(timeout));
		}		
	}
	
	public void preload() {
		preloadHtml();
		preloadJs();
	}
	
	public void preloadHtml() {
		String htmlFile = m_info.getPreloadHtml();
		if (htmlFile != null) {
			loadHtml(htmlFile);
		}
	}
	
	public void preloadJs() {
		List<String> ls = m_info.getPreloadJsList();
		if (ls == null) {
			return;
		}
		for (String jsFileName : ls) {
			if (m_mode == ExeMode.WEB) {
				m_activeWeb.loadJs(toUrl(jsFileName));
			} else {
				runScript(toUrl(jsFileName));
			}
		}
	}
	
	public void loadHtml(String htmlFile) {
		loadHtml(toUrl(htmlFile));
	}
		
	public void loadHtml(URL url) {
		if (m_window == null) {
			throw new RuntimeException("No window/browser specified");
		}
		AHtmlParser.parse(url, m_window);
	}
	
	public void loadHtmlSrc(String htmlSrc) {
		if (m_window == null) {
			throw new RuntimeException("No window/browser specified");
		}
		
		AHtmlParser.parse(htmlSrc, null, m_window);
	}
	
	public void loadActiveWeb(InputStream is, IBrowserLauncher launcher) {
		try {			
			m_activeWeb = new ActiveWeb(is, launcher, m_mode);
			m_activeWeb.startWebServer();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object exec(URL jsFileName, String clzName, String[] jsArgs) {
		Object result = runScript(jsFileName);
		if (clzName != null) {
			result = execMain(clzName, jsArgs);
		}
		return result;
	}
	
	public Object execMain(String clzName, String[] jsArgs) {
		if (clzName != null) {
			if (DapCtx.ctx().isActiveMode() && isJ2JType(clzName)) {
				m_cx.evaluateString(m_scope, clzName + "=Packages." + clzName + ";", clzName, 1, null);
				return runJavaMain(clzName + ".main", jsArgs);
			}
			return runMain(clzName + ".main", jsArgs);
		}
		return null;
	}
	

	public Object runScript(URL url) {
		try {
			return m_cx.evaluateReader(m_scope, new InputStreamReader(url.openStream()), url.toExternalForm(), 1, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object runScript(String script, String sourceName) {
		try {
			return m_cx.evaluateString(m_scope, script, sourceName, 1, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object runScript(String script, Scriptable scope) {
		return ScriptExecutor.executeScript(script, scope, m_cx);
	}
	
	public Object runScript(String script, String sourceName, Scriptable scope) {
		try {
			return m_cx.evaluateString(scope, script, sourceName, 1, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object runScript(String script) {
		return ScriptExecutor.executeScript(script, m_scope, m_cx);
	}
	
	protected void cancelAllTimeouts() {
		if (m_window != null) {
			m_window.clearAll();
		}
	}
	
	public void finalize() {
		finalize(1);
	}
	
	public void finalize(int timeout) {
		if (m_window != null) {
			try {
				m_window.waitForAllJsExecutionDone(timeout);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.err.println("Use -V" + WAIT_TIMEOUT_KEY + "=[msec] to specify a wait timeout.");
			}
			m_window.finialize();
			if (m_activeWeb != null) {
				m_activeWeb.destroy();
			}
		}
		else {
			Context.exit();
			if (m_dbg != null) {
				m_dbg.dispose();
			}
		}
	}
	
	protected Object runMain(String main, String args[]) {
		try {
			runScript(main, main);
		} catch (Exception e) {
			//System.out.println("-- No VJO main to be executed --");
			return null;
		}
		StringBuilder mainExec = new StringBuilder();
		mainExec.append("if(").append(main).append(") {\n")
		  .append(main).append("(");
		int i = 0;
		if (args != null) {
			for (String arg : args) {
				if (i > 0) {
					mainExec.append(",");
				}
				mainExec.append(JsonObject.quote(arg));
				i++;
			}
		}
		mainExec.append(");\n}");

		return runScript(mainExec.toString(), main);
	}
	
	protected Object runJavaMain(String main, String args[]) {
		StringBuilder mainExec = new StringBuilder();
		mainExec.append("if(").append(main).append(") {\n")
		  .append(main).append("(");
		try {
			mainExec.append(JsonSerializer.getInstance().serialize(args));
		} catch (Exception e) {
			throw new RuntimeException(e); //should never reach
		}
		mainExec.append(");\n}");

		return runScript(mainExec.toString(), main);
	}
	
	public static class ProgramInfo {
		private URL m_fileName = null;
		private String m_jsClassName = null;;
		private Map<String, String> m_options = new LinkedHashMap<String, String>();
		private String[] m_jsArgs = null;
		
		public URL getFileName() {
			return m_fileName;
		}
		
		public String getJsClassName() {
			return m_jsClassName;
		}
		
		public Map<String, String> getOptions() {
			return m_options;
		}
		
		public String getOption(String key) {
			return m_options.get(key);
		}
		
		public String[] getJsArgs() {
			return m_jsArgs;
		}
		
		public BrowserType getBrowserType() {
			String type = getOption(BROWSER_KEY);
			if (type == null) {
				return null;
			}
			return BrowserType.valueOf(type);
		}
		
		public boolean isHtml() {		
			return HTML_FILE_TYPE.equalsIgnoreCase(getOption(FILE_TYPE_KEY));
		}
		
		public List<String> getPreloadJsList() {
			String type = getOption(LOAD_JS_KEY);
			if (type == null) {
				return null;
			}
			StringTokenizer st = new StringTokenizer(type, LIST_SEPERATOR);
			List<String> ls = new ArrayList<String>(2);
			while (st.hasMoreTokens()) {
				ls.add(st.nextToken());
			}
			return ls;
		}
		
		public String getPreloadHtml() {
			return getOption(LOAD_HTML_KEY);
		}
		
		public boolean needBrowserDisplay() {
			return "true".equalsIgnoreCase(getOption(BROWSER_DISPLAY_KEY));
		}
		
		public String getBrowserServiceUrl() {
			return getOption(BROWSER_SERVICE_URL_KEY);
		}
	}
	
	public static ProgramInfo getProgramInfo(String[] args) throws MalformedURLException {
		ProgramInfo info = new ProgramInfo();
		int i = 0;
		for (String arg : args) {
			if (arg.startsWith(JS_OPTION_TOKEN)) {
				addJsOption(arg.substring(JS_OPTION_TOKEN.length()), info);
			}
			else {
				break;
			}
			i++;
		}
		if (i == args.length) {
			throw new RuntimeException("missing argument for js file name");
		}
		final String fileLocation = args[i++];
		try{
			info.m_fileName =  new URL(fileLocation); 
		}catch (MalformedURLException e) {
			info.m_fileName = (new File(fileLocation)).toURI().toURL();
		}
		
		if (i < args.length) {
			info.m_jsClassName = args[i++];
			if (i < args.length) {
				info.m_jsArgs = new String[args.length - i];
				System.arraycopy(args, i, info.m_jsArgs, 0, info.m_jsArgs.length);
			}
		}
		if (info.isHtml() && info.getBrowserType() == null) {
			info.m_options.put(BROWSER_KEY, BrowserType.IE_6P.name());
		}
		return info;
	}
	
	public AWindow getWindow(){
		return m_window;
	}
	
	private static void addJsOption(String arg, ProgramInfo info) {
		int i = arg.indexOf("=");
		if (i > 0 && i < arg.length() - 1) {
			info.m_options.put(arg.substring(0, i), arg.substring(i+1));
		}
	}
	
	protected static boolean needDebug() {
		RuntimeMXBean rtMxBean = ManagementFactory.getRuntimeMXBean();
		for (String arg : rtMxBean.getInputArguments()) {
			if (arg.startsWith("-agentlib:jdwp")) {
				return true;
			}
		}
		return false;
	}
	
	protected static URL toUrl(String fileName) {
		try {
			if (fileName.startsWith(HTTP)) {
				return new URL(fileName);
			}
			else {
				return new File(fileName).toURI().toURL();
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void initDebugger(boolean inBrowser) {
		ActiveJsExecutionControlCtx.ctx().setDebug(true);
		ActiveJsExecutionControlCtx.ctx().setUseSharedDebugClient(false);
		if (!JsDebuggerEnabler.useRemoteClient()) {
			JsDebuggerEnabler.enableDebuggerFileChooser();
		}
		if (!inBrowser) {
			/**
			 * if corresponding env variables are available, start dbgp debugger,
			 * else, start swing js debugger
			 */
			String debuggerServiceIp = System.getProperty("VJETDebugHost");
			String debuggerServicePort = System.getProperty("VJETDebugPort");
			String debuggerServiceSessionID = System.getProperty("VJETDebugSessionID");
			if (debuggerServiceIp == null || debuggerServicePort == null || debuggerServiceSessionID == null) {
				m_dbg = JsDebuggerEnabler.enable();
			}
			else {
				org.ebayopensource.dsf.js.dbgp.JsDebuggerEnabler.enable();
			}
		}
	}
	
	private static boolean isJ2JType(String uri) {
		try {
			Class<?> clz = Class.forName(uri, false, JsRunner.class.getClassLoader());
			return !IJsJavaProxy.class.isAssignableFrom(clz);
		} catch (Exception e) {
			return false;
		}
	}
	
	protected ActiveWebListener createWebListener(boolean debugEnabled) {
		return new ActiveWebListener(debugEnabled);
	}
	
	protected class ActiveWebListener implements IBrowserEmulatorListener {
		protected boolean m_debugEnabled = false;
		public ActiveWebListener(boolean debugEnabled) {
			m_debugEnabled = debugEnabled;
		}

		@Override
		public void windowOnload() {
			init();
			execJs();	
		}
		
		@SuppressWarnings("deprecation")
		protected void init() {
			ActiveJsExecutionControlCtx.ctx().setExecuteJavaScript(true);
			m_window = DapCtx.ctx().getWindow();
			m_cx = m_window.getContext();
			m_scope = m_window;
			Context.enter();
			if (m_debugEnabled) {
				m_dbg = m_window.getDebugger();
			}
			if (m_dbg != null) {
				m_dbg.setBreak();
			}
		}
		
		protected void execJs() {
			preloadJs();				
			exec(m_info.getFileName(), m_info.getJsClassName(), m_info.getJsArgs());
		}
	}
}
