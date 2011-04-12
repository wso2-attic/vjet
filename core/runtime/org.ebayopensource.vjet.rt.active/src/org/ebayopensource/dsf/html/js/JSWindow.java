/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.active.event.IDomChangeListener;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.css.dom.ICssStyleSheet;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.DBody;
import org.ebayopensource.dsf.html.dom.DButton;
import org.ebayopensource.dsf.html.dom.DDiv;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.DInput;
import org.ebayopensource.dsf.html.dom.DLabel;
import org.ebayopensource.dsf.html.dom.DSelect;
import org.ebayopensource.dsf.html.dom.DSpan;
import org.ebayopensource.dsf.html.dom.util.HtmlBuilder;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.JavaScriptException;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSWindow extends ScriptableObject {

	private static final long serialVersionUID = 1L;
	private URL m_url = null;
	private boolean m_isTopWindow = false;

	private Context m_cx = null;
	private Scriptable m_scope = null;
	private JSFrames m_frames = null;
	private JSListener m_listener;
	private DHtmlDocument m_doc = null;

	private ICssStyleSheet m_styleSheet = null;

	private String m_openWindowUrl = null;
	private String m_preTimeOutFunction = "";
	
	private Map<Node,JSElement> m_elements = new HashMap<Node,JSElement>(10);
	private IDomChangeListener m_domChangeListener;
	
	private Position m_topLeft;
	private int m_width;
	private int m_height;

	/** An empty construct required by Rhino. Do not put any init code here.
	 *
	 */
	public JSWindow() {
		windowState = IN_LOADING;
	}

	/** A Rhino constructor. Do not put init code here.
	 *
	 */
	public void jsConstructor() {
		// Put all the init code here. Do not put them in default constructor.
		// Because ScriptableObject.defineClass(scope, JSWindow.class)
		// will invoke the constructor once.
	}

	/** A method required by ScriptableObject. Usually same as the class name.
	 *
	 * @return The class name without package.
	 */
	public String getClassName() {
		return ("JSWindow");
	}

	/** Init the window object.
	 *
	 * @param parent Parent window.
	 * @param cx Current context.
	 * @param scope Current scope.
	 */
	public void init(JSWindow parent, Context cx, Scriptable scope)
		throws JavaScriptException {
		m_url = null;
		if (parent == null) {
			// For top-most window, parent == self
			m_parent = this;
			m_top = this;
			m_opener = this;
			m_listener = new JSAnalysisListener();
			m_isTopWindow = true;
		} else {
			m_parent = parent;
			m_top = m_parent.getTop();
			m_listener = m_parent.m_listener;
			m_isTopWindow = false;
		}
		m_cx = cx;
//		m_cx.setGeneratingDebug(true);
//		m_cx.setOptimizationLevel(-1);
//		m_cx.setDebugger(new JSDebugger(), new Object());
		m_scope = scope;
		m_frames = new JSFrames(this);

//		String s =
//			" function Image() {this.border=0; this.name=\"\"; this.src=\"\"; this.lowsrc=\"\"; this.width=0; this.height=0; this.hspace=0; this.vspace=0; this.complete=true;}";
//		s =
//			s
//				+ " function Option(arg) {this.text=arg; this.value=\"\"; this.defaultSelected=false; this.selected=false;}";
//		executeScript(s);

		defineProperty("closed", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty(
			"defaultStatus",
			JSWindow.class,
			ScriptableObject.DONTENUM);
		defineProperty("document", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("frames", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("history", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("length", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("location", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("name", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("navigator", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty(
			"offscreenBuffering",
			JSWindow.class,
			ScriptableObject.DONTENUM);
		defineProperty("opener", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("parent", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("screen", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("self", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("status", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("top", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("window", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("onload", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("loading", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("onblur", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("ondragdrop", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("onerror", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("onfocus", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("onmove", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("onresize", JSWindow.class, ScriptableObject.DONTENUM);
		defineProperty("onunload", JSWindow.class, ScriptableObject.DONTENUM);

		String[] methodNames =
			{
				"alert",
				"blur",
				"clearInterval",
				"clearTimeout",
				"close",
				"confirm",
				"focus",
				"open",
				"prompt",
				"scroll",
				"scrollBy",
				"scrollTo",
				"setInterval",
				"setTimeout",
				"valueOf" };
		defineFunctionProperties(
			methodNames,
			JSWindow.class,
			ScriptableObject.DONTENUM);

		// Document.
		m_document = new JSDocument(this);

		// History.
		m_history = new JSHistory(this);

		// Location.
		m_location = new JSLocation(this);

		// Navigator.
		m_navigator = new JSNavigator(this);

		// Screen.
		m_screen = new JSScreen(this);

		// Parser related stuff. ---------------------------------------------------
		baseBuilder = new HtmlBuilder();
		isRoot = true;
	}

	/** Return current context.
	 *
	 * @return Current context.
	 */
	public Context getContext() {
		return m_cx;
	}

	/** Return current scope.
	 *
	 * @return Current scope.
	 */
	public Scriptable getScope() {
		return m_scope;
	}

	/** Get window's URL.
	 *
	 * @return Window's URL.
	 */
	public URL getURL() {
		return m_url;
	}

	public void setURL(URL url) {
		m_url = url;
	}

	public JSListener getJSListener() {
		return m_listener;
	}

	public ICssStyleSheet getCssStyleSheet() {
		return m_styleSheet;
	}

	public void setCssStyleSheet(ICssStyleSheet styleSheet) {
		m_styleSheet = styleSheet;
	}

	/** Destroy the window. Call this method when this object is not need.
	 * Destroy children before parents.
	 *
	 */
	public void destroy() {
		Context.exit();
	}

	// Parser related stuff. ---------------------------------------------------
	private HtmlBuilder baseBuilder = null;

	public boolean isRoot = true;
	
	public void setHTMLDocument(DHtmlDocument doc){
		m_doc = doc;
	}

	/** Get the xml Document object of the parsed web page.
	 *
	 * @return The xml Document object.
	 */
	public DHtmlDocument getHTMLDocument() {
		if (m_doc != null){
			return m_doc;
		}
		return baseBuilder.getHTMLDocument();
	}

	/** Get the singleton HTMLBuilder of this window.
	 *
	 * @return The singleton HTMLBuilder of this window.
	 */
	public HtmlBuilder getBaseBuilder() {
		return baseBuilder;
	}

	/** Set the cookie of this window.
	 *
	 * @param s The cookie string.
	 *
	 */
	public void setCookie(String s) {
		m_document.setCookie2(s);
	}

	/** Get the cookie string of this window.
	 *
	 * @return The cookie string of this window.
	 */
	public String getCookie() {
		return m_document.getCookie2();
	}

	public String executeScript(String script) throws JavaScriptException {
		return executeScript(script, m_scope);
	}

	// Run script related stuff. -----------------------------------------------
	public String executeScript(String script, Scriptable scope)
		throws JavaScriptException {
		String s = script.trim();

//		if (s.startsWith("//")) {
//			s = s.substring(2);
//		}
//		if (!s.startsWith("<!--")) {
//			s = "<!--\n" + s;
//		}
//		if (s.endsWith("-->")) {
//			s = s.substring(0, s.length() - 3).trim();
//		}
//		if (s.endsWith("//")) {
//			s = s + " -->";
//		} else {
//			s = s + "\n// -->";
//		}

		int numHiddenWindow = 0;
		if (m_parent != this) {
			numHiddenWindow = m_parent.m_frames.numHiddenWindow();
		}
		
		Object ret = null;
		if (ActiveJsExecutionControlCtx.ctx().needDebug()) {
			Map<String, String> fragments = getSourceFragments(s);
			for (Map.Entry<String, String> entry : fragments.entrySet()) {
				ret = m_cx.evaluateString(scope, entry.getValue(), entry.getKey(), 1, null);
			}
		}
		else {
			JSDebug.println("Executing JavaScript:\n" + s);
			String id = "TheScript_" + s.length() + "_"+ s.hashCode();
			ret = m_cx.evaluateString(scope, s, id, 1, null);
		}

		if (m_parent != this && m_url != null) {
			m_parent.m_frames.fixHiddenWindowHref(
				URLUtil.getBaseURL(m_url.toString()),
				numHiddenWindow);
		}
		return (Context.toString(ret));
	}
	
	private Map<String, String> getSourceFragments(String data) {
		Map<String, String> fragments = new LinkedHashMap<String, String>();
		int currentIndex = 0;
		int tokenIndex = data.indexOf(ActiveJsExecutionControlCtx.ACTIVE_JS_SRC);
		if (tokenIndex == -1) {
			fragments.put(createKey(data), data);
		}
		else {
			while (tokenIndex != -1) {
				if (tokenIndex > currentIndex) {
					String fragment = data.substring(currentIndex, tokenIndex).trim();
					if (fragment.length() > 0) {
						fragments.put(createKey(fragment), fragment);
					}
				}
				currentIndex = processTokenValue(data, tokenIndex, fragments);
				tokenIndex = -1;
				if (currentIndex < data.length()) {
					tokenIndex = data.indexOf(ActiveJsExecutionControlCtx.ACTIVE_JS_SRC, currentIndex);
				}
			}
			if (currentIndex < data.length()) {
				String fragment = data.substring(currentIndex).trim();
				fragments.put(createKey(fragment), fragment);
			}
		}
		return fragments;
	}
	
	private static int processTokenValue(String data, int tokenIndex, Map<String, String> fragments) {
		int start = tokenIndex + ActiveJsExecutionControlCtx.ACTIVE_JS_SRC.length() + 1;
		int end = data.indexOf("]", start);
		String url = data.substring(start, end);
		String fileName = url;
		if (url.startsWith("file:")) {
			//tmp logic to get source JS under /src instead of the copy under /bin
			try {
				int index = url.indexOf("/bin/");
				String srcUrl = url;
				if (index > 0) {
					srcUrl = url.substring(0, index) + "/src/" + url.substring(index + 5);
				}
				File file = new File(new URL(srcUrl).getFile());
				if (file.exists()) {
					fileName = file.getAbsolutePath();
					url = srcUrl;
				}
			} catch (MalformedURLException e) {
				throw new DsfRuntimeException(url, e);
			}
		}
		String text = getJsContent(url);
		fragments.put(fileName, text);
		end++;
		return end;
	}
	
	private static String createKey(String data) {
		return "TheScript_" + data.length() + "_"+ data.hashCode();
	}
	
	public static String getJsContent(final String url) {
		final InputStream inputStream;
		try {
			inputStream = new URL(url).openStream();
		} catch (IOException e) {
			throw new DsfRuntimeException(url, e);
		}
		final ByteArrayOutputStream os = new ByteArrayOutputStream(4096);
		final byte [] buffer = new byte[4096];
		try {
			try {
				do {
					final int numBytesXferred = inputStream.read(buffer);
					if (numBytesXferred == -1 ) {
						break ; // EOF
					}
					os.write(buffer, 0, numBytesXferred);
				} while (true);
			} finally {
				inputStream.close();
			}
			final String scriptText = os.toString("utf-8");
			return scriptText;
		} catch (IOException e) {
			throw new DsfRuntimeException("can not load '" + url + "'", e);
		}
	}

	public String executeScript(java.io.Reader reader)
		throws JavaScriptException, IOException {
		Object ret = m_cx.evaluateReader(m_scope, reader, "TheScript", 1, null);
		return (Context.toString(ret));
	}

	public String getGeneratedContentFromScript() {
		return m_document.getGeneratedContent();
	}

	// Property: closed, R/O ---------------------------------------------------
	private boolean m_closed = false;
	public Scriptable getClosed() {
		return (
			Context.toObject(m_closed ? Boolean.TRUE : Boolean.FALSE, m_scope));
	}

	// Property: defaultStatus, R/W --------------------------------------------
	private String m_defaultStatus = "";
	public Scriptable getDefaultStatus() {
		return (Context.toObject(m_defaultStatus, m_scope));
	}

	public void setDefaultStatus(Object defaultStatus) {
		m_defaultStatus = defaultStatus.toString();
	}

	// Property: document, R/O -------------------------------------------------
	private JSDocument m_document = null;
	public Scriptable getDocument() {
		return m_document;
	}

	public JSDocument getJSDocument() {
		return m_document;
	}

	public Scriptable getFrames() {
		return m_frames;
	}

	public void addChildWindow(JSWindow cw) {
		m_frames.addChildWindow(cw);
	}

	public void removeChildWindow(JSWindow cw) {
		m_frames.removeChildWindow(cw);
	}

	/** Get a list of sub windows of this window.
	 *
	 * @return An array of the sub windows.
	 */
	public JSWindow[] getChildWindow() {
		int size = m_frames.size();
		JSWindow[] windows = new JSWindow[size];
		for (int i = 0; i < size; i++) {
			windows[i] = m_frames.at(i);
		}
		return windows;
	}

	// Property: history, R/O -------------------------------------------------
	private JSHistory m_history = null;
	public Scriptable getHistory() {
		return Context.toObject(m_history, m_scope);
	}

	public JSHistory getJSHistory() {
		return m_history;
	}

	// Property: length, R/O -------------------------------------------------
	public Scriptable getLength() {
		return (Context.toObject(new Integer(m_frames.size()), m_scope));
	}

	// Property: location, R/W -------------------------------------------------
	private JSLocation m_location = null;
	public Scriptable getLocation() {
		return m_location;
	}

	public void setLocation(Object s) {
		JsHackDetectionCtx.ctx().setLocationChange(s.toString());
		m_location.replace(s);
	}

	/** Get the JSLocation object of the window.
	 *
	 * @return The JSLocation object of this window.
	 */
	public JSLocation getJSLocation() {
		return m_location;
	}

	// Property: name, R/W -------------------------------------------------
	private String name = "";
	public Scriptable getName() {
		return (Context.toObject(name, m_scope));
	}

	public void setName(Object name) {
		this.name = name.toString();
	}

	// Property: navigator, R/O ---------------------------------------------------
	private JSNavigator m_navigator = null;
	public Scriptable getNavigator() {
		return Context.toObject(m_navigator, m_scope);
	}

	public JSNavigator getJSNavigator() {
		return m_navigator;
	}

	// Property: offscreenBuffering, R/O ---------------------------------------------------
	private String m_offscreenBuffering = "auto";
	public Scriptable getOffscreenBuffering() {
		return (Context.toObject(m_offscreenBuffering, m_scope));
	}

	// Property: OnXXX event handlers
	private Object m_onblur = null;
	public Object getOnblur() {
		return m_onblur;
	}

	public void setOnblur(Object o) {
		m_onblur = o;
	}

	private Object m_ondragdrop = null;
	public Object getOndragdrop() {
		return m_ondragdrop;
	}

	public void setOndragdrop(Object o) {
		m_ondragdrop = o;
	}

	private Object m_onerror = null;
	public Object getOnerror() {
		return m_onerror;
	}

	public void setOnerror(Object o) {
		m_onerror = o;
	}

	private Object m_onfocus = null;
	public Object getOnfocus() {
		return m_onfocus;
	}

	public void setOnfocus(Object o) {
		m_onfocus = o;
	}

	private Object m_onload = null;
	public Object getOnload() {
		return m_onload;
	}

	public void setOnload(Object o) {
		m_onload = o;
	}
	
	public void executeOnload() {
		if (m_onload == null) {
			return;
		}
		if (m_onload instanceof Function) {
			try {
				((Function) m_onload).call(
					m_cx,
					m_scope,
					this,
					new Object[0]);
			} catch (Exception e) {
				JSDebug.println(e.getMessage());
			}
		}
	}

	private Object m_onmove = null;
	public Object getOnmove() {
		return m_onmove;
	}

	public void setOnmove(Object o) {
		m_onmove = o;
	}

	private Object m_onresize = null;
	public Object getOnresize() {
		return m_onresize;
	}

	public void setOnresize(Object o) {
		m_onresize = o;
	}

	private Object m_onunload = null;
	public Object getOnunload() {
		return m_onunload;
	}

	public void setOnunload(Object o) {
		m_onunload = o;
	}

	// Property: opener, R/W ---------------------------------------------------
	private Object m_opener = null;
	public Object getOpener() {
		return m_opener;
	}

	public void setOpener(Object o) {
		m_opener = o;
	}

	// Property: parent, R/O ---------------------------------------------------
	private JSWindow m_parent = null;
	public Scriptable getParent() {
		return m_parent;
	}

	// Property: screen, R/O ---------------------------------------------------
	private JSScreen m_screen = null;
	public Scriptable getScreen() {
		return Context.toObject(m_screen, m_scope);
	}

	// Property: self, R/O ---------------------------------------------------
	public Scriptable getSelf() {
		return (this);
	}

	// Property: status, R/W -------------------------------------------------
	private String status = "";
	public Scriptable getStatus() {
		return (Context.toObject(status, m_scope));
	}

	public void setStatus(Object status) {
		this.status = status.toString();
	}

	// Property: top, R/O ---------------------------------------------------
	public JSWindow m_top = null;
	public JSWindow getTop() {
		JSWindow m_top = this;
		while (m_top != null && m_top.m_isTopWindow == false) {
			m_top = m_top.m_parent;
		}
		return m_top;
	}

	// Property: window, R/O ---------------------------------------------------
	public JSWindow getWindow() {
		return (this);
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_alert(Object s) {
		JSDebug.println("jsFunction_alert:" + s);
		return;
	}

	public void alertFunction(Object[] s) {
		JSDebug.println("alertFunction:" + s);
		return;
	}

	public static void alert(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).alertFunction(args);
		return;
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_blur() {
		JSDebug.println("jsFunction_blur:");
		return;
	}

	public void blurFunction() {
		JSDebug.println("blurFunction:");
		return;
	}

	public static void blur(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).blurFunction();
		return;
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_captureEvents(Object id) {
		JSDebug.println("jsFunction_captureEvents:");
		return;
	}

	public void captureEventsFunction(Object[] args) {
		JSDebug.println("captureEventsFunction:" + args);
		return;
	}

	public static void captureEvents(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).captureEventsFunction(args);
		return;
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_clearInterval(Object id) {
		JSDebug.println("jsFunction_clearInterval:");
		return;
	}

	public void clearIntervalFunction(Object[] args) {
		JSDebug.println("clearIntervalFunction:" + args);
		return;
	}

	public static void clearInterval(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).clearIntervalFunction(args);
		return;
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_clearTimeout(Object id) {
		JSDebug.println("jsFunction_clearTimeout:");
		return;
	}

	public void clearTimeoutFunction(Object[] args) {
		JSDebug.println("clearTimeoutFunction:" + args);
		return;
	}

	public static void clearTimeout(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).clearTimeoutFunction(args);
		return;
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_close() {
		JSDebug.println("jsFunction_close:");
		m_closed = true;
		return;
	}

	public void closeFunction() {
		JSDebug.println("closeFunction:");
		m_closed = true;
		m_listener.doAction(JSAction.CLOSE_WINDOW, "window", null, null);
		return;
	}

	public static void close(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).closeFunction();
		return;
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_confirm(Object s) {
		JSDebug.println("jsFunction_confirm:" + s);
		return;
	}

	public void confirmFunction(Object[] s) {
		JSDebug.println("confirmFunction:" + s);
		return;
	}

	public static Scriptable confirm(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).confirmFunction(args);
		return (Context.toObject(Boolean.TRUE, thisObj));
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_focus() {
		JSDebug.println("jsFunction_focus:");
		return;
	}

	public void focusFunction() {
		JSDebug.println("focusFunction:");
		return;
	}

	public static void focus(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).focusFunction();
		return;
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_open(
		Object url,
		Object name,
		Object features,
		Object replace) {
		JSDebug.println("jsFunction_open(...):");
		return;
	}

	public void openFunction(Object[] args) {
		m_openWindowUrl = (String) args[0];
		if (windowState == IN_LOADING) {
			//create an IFRAME to hold this window
			String iframe = "<IFRAME src=\"" + m_openWindowUrl + "\">";
			m_document.writeln(iframe);
		}
		m_listener.doAction(
			JSAction.OPEN_WINDOW,
			"window",
			m_openWindowUrl,
			null);
		return;
	}

	public static Scriptable open(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).openFunction(args);
		return thisObj;
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_prompt(Object message, Object def) {
		JSDebug.println("jsFunction_prompt:");
		return;
	}

	public void promptFunction(Object[] args) {
		JSDebug.println("promptFunction:" + args);
		return;
	}

	public static Scriptable prompt(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).promptFunction(args);
		return (Context.toObject("", thisObj));
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_scroll(Object x, Object y) {
		JSDebug.println("jsFunction_scroll:");
		return;
	}

	public void scrollFunction(Object[] args) {
		JSDebug.println("scrollFunction:" + args);
		return;
	}

	public static void scroll(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).scrollFunction(args);
		return;
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_scrollBy(Object x, Object y) {
		JSDebug.println("jsFunction_scrollBy:");
		return;
	}

	public void scrollByFunction(Object[] args) {
		JSDebug.println("scrollByFunction:" + args);
		return;
	}

	public static void scrollBy(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).scrollByFunction(args);
		return;
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_scrollTo(Object x, Object y) {
		JSDebug.println("jsFunction_scrollTo:");
		return;
	}

	public void scrollToFunction(Object[] args) {
		JSDebug.println("scrollToFunction:" + args);
		return;
	}

	public static void scrollTo(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).scrollToFunction(args);
		return;
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_setInterval(Object code, Object interval) {
		JSDebug.println("jsFunction_setInterval:");
		return;
	}

	public void setIntervalFunction(Object[] args) {
		JSDebug.println("setIntervalFunction:" + args);
		return;
	}

	public static Scriptable setInterval(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).setIntervalFunction(args);
		return (Context.toObject(new Integer(100), thisObj));
	}

	// Functions ---------------------------------------------------------------
	public void jsFunction_setTimeout(Object code, Object delay) {
		JSDebug.println("jsFunction_setTimeout:");
		return;
	}

	public void setTimeoutFunction(Object[] args) {
		JSDebug.println("setTimeoutFunction");
		if (args == null || args.length == 0)
			return;
		String timeOutFunction = args[0].toString();
		if (m_preTimeOutFunction.equals(timeOutFunction))
			return; //avoid the recursion
		else
			m_preTimeOutFunction = timeOutFunction;
		try {
			executeScript(timeOutFunction);
		} catch (Exception e) {
			JSDebug.println(e.getMessage());
		}
	}

	public static Scriptable setTimeout(
		Context cxt,
		Scriptable thisObj,
		Object[] args,
		Function funObj) {
		((JSWindow) thisObj).setTimeoutFunction(args);
		return (Context.toObject(new Integer(100), thisObj));
	}

	public Object jsFunction_valueOf(String type) {
		return valueOf(type);
	}

	public Object valueOf(String type) {
		if (type.equals("boolean"))
			return Boolean.TRUE;
		else if (type.equals("string")) {
			return getURL().toExternalForm();
		} else if (type.equals("object"))
			return this;
		else if (type.equals("number"))
			return "0";

		return null;
	}

	// Property: status, R/W -------------------------------------------------
	private boolean loading = true;
	public boolean getLoading() {
		return loading;
	}

	public void setLoading(Object loading) {
		if (loading.toString().equalsIgnoreCase("true"))
			this.loading = true;
		else
			this.loading = false;
	}

	public static int IN_LOADING = 0;
	public static int IN_ANALYSIS = 1;
	public static int IN_SERVER = 2;
	public static int IDLE = 3;

	public int windowState; // state of browserWindow

	public void setState(int state) {
		setState(state, null);
		if (state == IN_LOADING)
			loading = true;
		else
			loading = false;
	}

	public void setState(int state, String urlSource) {
		if (state != this.windowState && state == IN_SERVER) {
			// state changing to run time
			m_listener = new JSServerListener(urlSource, this);
		} else if (
			this.windowState == IN_SERVER
				&& state != IN_SERVER
				&& m_listener != null
				&& m_listener instanceof JSServerListener) {
			m_listener.reset(); // dereference session, etc...
			m_listener = null; // allow garbage collection
		}

		this.windowState = state;
		if (state == IN_LOADING)
			loading = true;
		else
			loading = false;
		//set child window's state
		int size = m_frames.size();
		for (int i = 0; i < size; i++) {
			m_frames.at(i).setState(state);
			m_frames.at(i).m_listener = m_listener;
		}
	}

	public int getState() {
		return this.windowState;
	}

	static HashMap<String, String> constants = new HashMap<String, String>(13);
	static {
		constants.put("Object", "Object");
		constants.put("layers", "layers");
		constants.put("getElementById", "getElementById");
		constants.put("SyntaxError", "SyntaxError");
		constants.put("ConversionError", "ConversionError");
		constants.put("TypeError", "TypeError");
		constants.put("ReferenceError", "ReferenceError");
	}

	public Object get(String name, Scriptable start) {
		JSDebug.println("JSWindow:get... " + name);
		Object obj = super.get(name, start);
		if (obj == NOT_FOUND) {
			obj = getParentScope().get(name, start);
			if (obj != NOT_FOUND) {
				return obj;
			}

			if (constants.get(name) != null)
				return NOT_FOUND;

			//try to find it from the child window
			int size = m_frames.size();
			for (int i = 0; i < size; i++) {
				JSWindow child = m_frames.at(i);
				if (child.name.equals(name))
					return child;
				obj = child.get(name, start);
				if (obj != NOT_FOUND)
					return obj;
			}
			obj = m_document.findHtmlObjectById(name, start);
			if (obj == NOT_FOUND)
				obj = m_document.findFormObject(name);
		}

		if (obj == NOT_FOUND) {
			JSDebug.println("****** NOT_FOUND : " + name);
		}

		return obj;
	}

	public void put(String name, Scriptable start, Object value) {
		super.put(name, start, value);
		JSDebug.println("-------- JSWindow:put... " + name + " : " + value);
	}

	public Scriptable findFormElement(String formId, String name) {
		Scriptable obj = m_document.findFormElement(formId, name);
		if (obj == null) { //look into child window
			int size = m_frames.size();
			for (int i = 0; i < size; i++) {
				obj = m_frames.at(i).m_document.findFormElement(formId, name);
				if (obj != null)
					return obj;
			}
		} else
			return obj;
		return m_scope; //cann't find anything matched
	}

	public String getSelectedIndex(
		String formId,
		String name,
		String optionValue) {
		return m_document.getSelectedIndex(formId, name, optionValue);
	}

	public void setJsBuilderListener(JSBuilderListener builderListener) {
		m_document.setJsBuilderListener(builderListener);
	}

	public JSBuilderListener getJsBuilderListener() {
		return m_document.getJsBuilderListener();
	}

	public Scriptable getScopeById(String name) {
		if (name == null || name.length() == 0)
			return m_scope;
		Object obj = ((JSAllArray) m_document.getAll()).findObjById(name);
		if (obj == null) { //look into child window
			int size = m_frames.size();
			for (int i = 0; i < size; i++) {
				obj =
					(
						(JSAllArray) m_frames
							.at(i)
							.m_document
							.getAll())
							.findObjById(
						name);
				if (obj != null)
					return (Scriptable) obj;
			}
		} else
			return (Scriptable) obj;

		return m_scope;
	}

	public JSWindow getHiddenWindow(String name) {
		return m_frames.getHiddenWindow(name, false);
	}

	public JSWindow getHiddenWindow() {
		return m_frames.getHiddenWindow();
	}
	
	public DNode getDNode(){
		return null; // TODO
	}
	
	public void setListener(IDomChangeListener listener){
		m_domChangeListener = listener;
	}
	
	IDomChangeListener getListener(){
		return m_domChangeListener;
	}
	
	public JSElement getElementById(final String id){
		Node node = getHTMLDocument().getElementById(id);
		return getWrapper(node, true);
	}
	
	public JSElement getWrapper(Node node, boolean create){
		JSElement elem = m_elements.get(node);
		if (elem == null && create){
			elem = createWrapper(node);
			addWrapper(node, elem);
		}
		return elem;
	}
	
	void addWrapper(Node node, JSElement element){
		m_elements.put(node, element);
	}
	
	private JSElement createWrapper(Node node){
		if (node instanceof DBody){
			return new JSBody(this, (DBody)node);
		}
		else if (node instanceof DInput){
			return new JSInput(this, (DInput)node);
		}
		else if (node instanceof DSelect){
			return new JSSelect(this, (DSelect)node);
		}
		else if (node instanceof DButton){
			return new JSButton(this, (DButton)node);
		}
		else if (node instanceof DLabel){
			return new JSLabel(this, (DLabel)node);
		}
		else if (node instanceof DDiv){
			return new JSDiv(this, (DDiv)node);
		}
		else if (node instanceof DSpan){
			return new JSSpan(this, (DSpan)node);
		}
		else {
			return new JSElement(this, (BaseHtmlElement) node);
		}
	}
	
	public static class Position {
		private int x;
		private int y;
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
	}

	public Position getTopLeft() {
		return m_topLeft;
	}

	public void setTopLeft(Position topLeft) {
		m_topLeft = topLeft;
	}

	public int getHeight() {
		return m_height;
	}

	public void setHeight(int hight) {
		m_height = hight;
	}

	public int getWidth() {
		return m_width;
	}

	public void setWidth(int width) {
		m_width = width;
	}
}
