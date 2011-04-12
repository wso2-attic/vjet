/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.ebayopensource.dsf.active.dom.html.AHtmlBuilder;
import org.ebayopensource.dsf.active.dom.html.AHtmlDocument;
import org.ebayopensource.dsf.active.dom.html.AHtmlHelper;
import org.ebayopensource.dsf.active.dom.html.AImage;
import org.ebayopensource.dsf.active.dom.html.AOption;
import org.ebayopensource.dsf.active.dom.html.IDocListener;
import org.ebayopensource.dsf.active.dom.html.AJavaScriptHandlerHolder.JAVASCRIPT_HANDLER_TYPE;
import org.ebayopensource.dsf.active.event.DomChangeListenerAdapter;
import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.active.event.IDomChangeListener;
import org.ebayopensource.dsf.active.util.IAsyncTask;
import org.ebayopensource.dsf.active.util.WindowAsyncTask;
import org.ebayopensource.dsf.active.util.WindowIntervalTask;
import org.ebayopensource.dsf.active.util.WindowTask;
import org.ebayopensource.dsf.active.util.WindowTaskManager;
import org.ebayopensource.dsf.active.util.WindowTimerTask;
import org.ebayopensource.dsf.dap.proxy.NativeJsHelper;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.dap.rt.XMLHttpRequestImpl;
import org.ebayopensource.dsf.dap.rt.jsonp.JsonpProxy;
import org.ebayopensource.dsf.html.dom.DImg;
import org.ebayopensource.dsf.html.dom.DOption;
import org.ebayopensource.dsf.html.dom.DScript;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsdebugger.DebuggerAdapter;
import org.ebayopensource.dsf.jsnative.External;
import org.ebayopensource.dsf.jsnative.Frames;
import org.ebayopensource.dsf.jsnative.History;
import org.ebayopensource.dsf.jsnative.HtmlCollection;
import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.HtmlElementStyle;
import org.ebayopensource.dsf.jsnative.Image;
import org.ebayopensource.dsf.jsnative.Location;
import org.ebayopensource.dsf.jsnative.Navigator;
import org.ebayopensource.dsf.jsnative.Opera;
import org.ebayopensource.dsf.jsnative.Option;
import org.ebayopensource.dsf.jsnative.Screen;
import org.ebayopensource.dsf.jsnative.Window;
import org.ebayopensource.dsf.jsnative.XMLHttpRequest;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.JsNativeMeta;
import org.ebayopensource.dsf.jsnative.events.Event;
import org.ebayopensource.dsf.jsnative.events.EventException;
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.ebayopensource.dsf.liveconnect.client.NativeEvent;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.NativeJavaClass;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;
import org.mozilla.mod.javascript.Undefined;

public class AWindow extends ActiveObject implements Window,EventTarget {

	private static final long serialVersionUID = 1L;
	private Context m_cx = null;
	private Scriptable m_scope = null;
	private URL m_url;
	private boolean m_isRoot;
	private DebuggerAdapter m_dbg;
	private BrowserType m_browserType = BrowserType.IE_6P;

	// whether or not a window has been closed (R/O)
	private boolean m_closed;
	// sets or returns the default text in the statusbar of the window
	private String m_defaultStatus;
	// document object (R/O)
	private AHtmlDocument m_document;
	private AHtmlBuilder m_builder;
	// named frames in the window
	private Frames m_frames;
	// default text in the statusbar of the window (IE, Opera)
//	private String m_defaultStatus;
	// History object (R/O)
	private History m_history;
	// number of frames in the window (R/O)
	private int m_length;
	// Location object
	private Location m_location;
	// name of the window
	private String m_name = "";
	// reference to the window that created the window
	private Window m_opener;
	// reference to the navigator object (R/O)
	private Navigator m_navigator;
	// the parent window (R/O)
	private Window m_parent;
	// reference to Screen object (R/O)
	private Screen m_screen;
	// the text in the statusbar of a window
	private String m_status = "";
	// the topmost ancestor window (R/O)
	private Window m_top;
	
	// IE only property
	private Event m_event;
	
	private AExternal m_external;
	
	// Opera only property
	private AOpera m_opera;
	
	//timer task management
	//private WindowTaskQueue m_taskQueue;
	//private WindowTaskExecutor m_taskExecutor;
	private WindowTaskManager m_taskMgr;
	
	private IBrowserBinding m_browserBinding;
	private DomChangeListenerAdapter m_domListener;
	private boolean m_domChangeListenerEnabled = true;
	
	private final String WINDOW_DOT_SET_STATUS = "window.status=\"{0}\"";
	private final String WINDOW_DOT_GET_STATUS = "window.status";

	private final static ArrayList<String> VALID_EVENTS = new ArrayList<String>();
	static{
		VALID_EVENTS.add(NativeEvent.load.toString());
		VALID_EVENTS.add(NativeEvent.unload.toString());
		VALID_EVENTS.add(NativeEvent.resize.toString());
		VALID_EVENTS.add(NativeEvent.keydown.toString());
		VALID_EVENTS.add(NativeEvent.keyup.toString());
		VALID_EVENTS.add(NativeEvent.keypress.toString());
		VALID_EVENTS.add(NativeEvent.scroll.toString());
	}
	
	 /**
     * The zero-parameter constructor.
     *
     * When Context.defineClass is called with this class, it will
     * construct AWindow.prototype using this constructor.
     */
    public AWindow() {
    }
    
	public Window getWindow() {
		return this;
	}

	public boolean getClosed() {
		return m_closed;
	}

	public HtmlDocument getDocument() {
		return m_document;
	}

	public HtmlCollection getFrames() {
		return getDocument().getFrames();
	}

	public History getHistory() {
		return m_history;
	}

	public Location getLocation() {
		return m_location;
	}

	public void setLocation(Location location) {
		m_location = location;
	}

	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		this.m_name = name;
	}

	public Navigator getNavigator() {
		return m_navigator;
	}

	public Window getOpener() {
		return m_opener;
	}

	public Window getParent() {
		return m_parent;
	}

	public Window getSelf() {
		return this;
	}

	public String getStatus() {
		if (m_browserBinding != null) {
			return m_browserBinding.executeJs(WINDOW_DOT_GET_STATUS);
		}
		return m_status;
	}

	public void setStatus(String status) {
		this.m_status = status;
		if (m_browserBinding != null) {
			m_browserBinding.executeJs(MessageFormat.format(WINDOW_DOT_SET_STATUS, status));
		}
	}

	public Window getTop() {
		return m_top;
	}

	public int getLength() {
		return m_length;
	}

	public String getDefaultStatus() {
		return m_defaultStatus;
	}

	public void setDefaultStatus(String status) {
		m_defaultStatus = status;
	}

	public Screen getScreen() {
		return m_screen;
	}
	
	public int getInnerHeight() {
		if (m_browserBinding != null) {
			return m_browserBinding.getWindowHeight();
		}
		return 0;
	}

	public int getInnerWidth() {
		if (m_browserBinding != null) {
			return m_browserBinding.getWindowWidth();
		}
		return 0;
	}
	
	public int getOuterHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getOuterWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getPageXOffset() {
		if (m_browserBinding != null) {
			return m_browserBinding.getPageXOffset();
		}
		return 0;
	}

	public int getPageYOffset() {
		if (m_browserBinding != null) {
			return m_browserBinding.getPageYOffset();
		}
		return 0;
	}
	
	public int getScreenLeft() {
		if (m_browserBinding != null) {
			return m_browserBinding.getScreenLeft();
		}
		return 0;
	}

	public int getScreenTop() {
		if (m_browserBinding != null) {
			return m_browserBinding.getScreenTop();
		}
		return 0;
	}

	public int getScreenX() {
		if (m_browserBinding != null) {
			return m_browserBinding.getScreenLeft();
		}
		return 0;
	}

	public int getScreenY() {
		if (m_browserBinding != null) {
			return m_browserBinding.getScreenTop();
		}
		return 0;
	}
	
	// Window Object Methods

	// Displays an alert box with a message and an OK button
	public void alert(Object message) {
		if (m_browserBinding != null) {
			m_browserBinding.alert(NativeJsHelper.toString(message));
		}
	}
	
	// Removes focus from the current window
	public void blur() {
		if (m_browserBinding != null) {
			m_browserBinding.blur();
		}
	}
	
	// Cancels a timeout set with setInterval()
	public void clearInterval(int id) {
		cancelTimer(id);
	}
	
	// Cancels a timeout set with setTimeout()
	public void clearTimeout(int id) {
		cancelTimer(id);
	}
	
	public void clearAll() {
		m_taskMgr.cancelAll();
		if(m_browserBinding != null) {
		    m_browserBinding.executeJs("if(TM) TM.clearAll();");
		}
	}
	
	private void cancelTimer(int id){
		m_taskMgr.cancel(id);
		if(m_browserBinding != null) {
			//System.out.println("===== cancelTimer: " + id);
			m_browserBinding.executeJs("if(TM) TM.clear('" + id + "');");
		}
	}

	// Closes the current window
	public void close() {
		if (m_browserBinding != null) {
			m_browserBinding.close();
		}
	}
	
	// Displays a dialog box with a message and an OK and a Cancel button
	public boolean confirm(String message) {
		if (m_browserBinding != null) {
			return m_browserBinding.confirm(message);
		}
		return false;
	}
	         
	// Sets focus to the current window
	public void focus() {
		if (m_browserBinding != null) {
			m_browserBinding.focus();
		}
	}
	
	// Moves a window relative to its current position
	public void moveBy(int x, int y) {
		if (m_browserBinding != null) {
			m_browserBinding.moveBy(x, y);
		}
	}
	
	// Moves a window to the specified position
	public void moveTo(int x, int y) {
		if (m_browserBinding != null) {
			m_browserBinding.moveTo(x, y);
		}
	}
	
	public Window __open(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5) {
		if(arg1 instanceof Undefined){
			return open();
		}
		if(arg2 instanceof Undefined){
			return open((String)arg1);
		}
		if(arg3 instanceof Undefined){
			return open ((String)arg1, (String)arg2);
		}
		if(arg4 instanceof Undefined){
			return open ((String)arg1, (String)arg2, (String)arg3);
		}
		return open ((String)arg1, (String)arg2, (String)arg3, (Boolean) arg4);
	}

	public Window open() {
		return open(null);
	}
	
	public Window open(String url) {
		return open(url, null);
	}

	public Window open(String url, String windowName) {
		return open(url, windowName, null);
	}

	public Window open(String url, String windowName, String features) {
		return open(url, windowName, features, false);
	}
	
	// Opens a new browser window
	public Window open(String url, String windowName, String features, boolean replace)  {
		if (m_browserBinding != null) {
			m_browserBinding.open(url, windowName, features, replace);
		}
		return null;
	}
	
	// Prints the contents of the current window
	public void print() {
		if (m_browserBinding != null) {
			m_browserBinding.print();
		}
	}
	
	// Displays a dialog box that prompts the user for input
	public String __prompt(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
		if(arg1 instanceof Undefined){
			return prompt();
		}
		if(arg2 instanceof Undefined){
			return prompt((String)arg1);
		}
		return prompt((String)arg1,(String)arg2);
	}
	
	public String prompt() {
		return prompt(null);
	}

	public String prompt(String message) {
		return prompt(message,null);
	}
	
	public String prompt(String message, String defaultReply) {
		if (m_browserBinding != null) {
			return m_browserBinding.prompt(message, defaultReply);
		}
		return null;
	}
	
	// Resizes a window by the specified pixels
	public void resizeBy(int width, int height) {
		if (m_browserBinding != null) {
			m_browserBinding.resizeBy(width, height);
		}
	}
	
	// Resizes a window to the specified width and height
	public void resizeTo(int width, int height) {
		if (m_browserBinding != null) {
			m_browserBinding.resizeTo(width, height);
		}
	}
	
	// Scrolls the content by the specified number of pixels
	public void scrollBy(int x, int y) {
		if (m_browserBinding != null) {
			m_browserBinding.scrollBy(x, y);
		}
	}
	
	// Scrolls the content to the specified coordinates
	public void scrollTo(int x, int y) {
		if (m_browserBinding != null) {
			m_browserBinding.scrollTo(x, y);
		}
	}
	
	// Evaluates an expression at specified intervals
	public int setInterval(Object code, int msecs) {
		WindowIntervalTask task =  new WindowIntervalTask(code, msecs, m_scope, m_cx, m_taskMgr);
		int id = task.getId();
		if(m_browserBinding != null) {
		    //System.out.println("(setInterval) add Task to Client: " + id + " : " + code.toString());
		    m_browserBinding.executeJs("if(TM) TM.setInterval('" + id + "', " + msecs + ");");
		}
		return id;
	}
	
	// Evaluates an expression after a specified number of milliseconds
	public int setTimeout(Object code, int msecs) {
		WindowTimerTask task =  new WindowTimerTask(code, msecs, m_scope, m_cx, m_taskMgr);
		int id = task.getId();
		if(m_browserBinding != null) {
			//System.out.println("(setTimeout) add Task to Client: " + id + "-: " + code.toString());
			m_browserBinding.executeJs("if(TM) TM.setTimeOut('" + id + "', " + msecs + ");");
		}
		return id;
	}
	
	public void addTask(Object code) {
		WindowTask task =  new WindowTask(code, m_scope, m_cx, m_taskMgr);
		int id = task.getId();
		m_browserBinding.executeJs("if(TM) TM.setTimeOut('" + id + ");");
	}

	public void addTask(IAsyncTask asyncTask) {
		WindowAsyncTask task =  new WindowAsyncTask(asyncTask, m_scope, m_cx, m_taskMgr);
		int id = task.getId();
		if(m_browserBinding != null) {
			m_browserBinding.executeJs("if(TM) TM.setTimeOut('" + id + "');");
		}
	}

	public Context getContext() {
		return m_cx;
	}
	
	public Scriptable getScope() {
		return m_scope;
	}
	
	public Object getJsExcutionLock() {
		//return m_taskExecutor;
		return m_taskMgr;
	}

	public void addChildWindow(AWindow window) {
		m_frames.addChildWindow(window);
	}
	
	public void setHtmlDocument(AHtmlDocument doc){
		m_document = doc;
	}
	
	public AHtmlDocument getHtmlDocument() {
		return m_document;
	}
	
	public void setBrowserBinding(IBrowserBinding binding)	{
		m_browserBinding = binding;
		if (m_document != null) {
			m_document.setBrowserBinding(m_browserBinding);
		}
		if (m_navigator != null) {
			((ANavigator)m_navigator).setBrowserBinding(m_browserBinding);
		}
		if (m_screen != null) {
			((AScreen)m_screen).setBrowserBinding(m_browserBinding);
		}
		if (m_history!= null) {
			((AHistory)m_history).setBrowserBinding(m_browserBinding);
		}
	}
	
	public IBrowserBinding getBrowserBinding() {
		return m_browserBinding;
	}
	
	/** Destroy the window. Call this method when this object is not need.
	 * Destroy children before parents.
	 *
	 */
	public void destroy() {
		Context.exit();
		//m_taskExecutor.shutdown();
	}
	
	public void setParent(Window parent) {
		m_parent = parent;
		m_top = m_parent.getTop();
	}
	
	/**
	 * For unit test only, which will wait for all timer tasks done.
	 * A runtime exception could be thrown after timeout (in milliseconds)
	 */
	public void waitForAllJsExecutionDone(long timeoutInMs) {
		//m_taskQueue.waitForEmpty(timeoutInMs);
	}
	
	static HashMap<String, String> constants = new HashMap<String, String>(13);
	static {
		constants.put("Object", "Object");
		constants.put("layers", "layers");
		constants.put("SyntaxError", "SyntaxError");
		constants.put("ConversionError", "ConversionError");
		constants.put("TypeError", "TypeError");
		constants.put("ReferenceError", "ReferenceError");
	}
	
	public Object get(String name, Scriptable start) {
		Object obj = super.get(name, start);
		if (obj == NOT_FOUND) {
			obj = getParentScope().get(name, start);
			if (obj != NOT_FOUND) {
				return obj;
			}

			if (constants.get(name) != null)
				return NOT_FOUND;
			
			// check if it is a W3C DOM object
			Class<?> c = JsNativeMeta.getClass(name);
			if (c != null) {
				return new NativeJavaClass(m_scope, c);
			}

			//try to find it from the child window
			int size = m_frames.size();
			for (int i = 0; i < size; i++) {
				AWindow child = (AWindow) m_frames.at(i);
				if (child.getName().equals(name)) {
					return child;
				}
				obj = child.get(name, start);
				if (obj != NOT_FOUND) {
					return obj;
				}
			}
		}
		return obj;
	}

	void init(AWindow parent, Context cx, Scriptable scope, BrowserType browserType, WindowTaskManager taskMgr) {
		m_browserType = browserType;
		m_isRoot = true;
		if (parent == null) {
			// For top-most window, parent == self
			m_parent = this;
			m_top = this;
			m_opener = this;
		} else {
			m_parent = parent;
			m_top = m_parent.getTop();
		}
		m_cx = cx;
		m_scope = scope;
		
		populateScriptable(AWindow.class, m_browserType);
		
		// Location.
		m_location = new ALocation(this);
		
		m_document = new AHtmlDocument((ALocation) m_location, m_browserType);
		m_document.setBrowserBinding(m_browserBinding);
		m_document.setScriptContext(m_cx);
		m_document.setScriptScope(m_scope);		
		m_document.setWindow(this);
		
		m_builder = new AHtmlBuilder(m_document);
		m_builder.setFixDuplicateIds(true);

		// History.
		m_history = new AHistory(browserType);
		((AHistory)m_history).setBrowserBinding(m_browserBinding);
		
		// Navigator.
		m_navigator = new ANavigator(m_browserType);
		((ANavigator)m_navigator).setBrowserBinding(m_browserBinding);
		
		// Screen.
		m_screen = new AScreen(browserType);
		((AScreen)m_screen).setBrowserBinding(m_browserBinding);
		
		m_frames = new AFrames();
		
		//m_taskQueue = new WindowTaskQueue();
		//m_taskExecutor = new WindowTaskExecutor(m_taskQueue);
		m_taskMgr = taskMgr;
				
		String s =
		" function Image(arg0,arg1) { var img = document.createElement('img'); img.border=0; img.name=\"\"; img.src=\"\"; img.lowsrc=\"\"; img.width=arg0||0; img.height=arg1||0; img.hspace=0; img.vspace=0; img.complete=true; return img; };";
		s += " function Option(stext,svalue,bdefaultSelected,bselected) { var option = document.createElement('option'); option.text=stext; option.value=svalue; var defSel = false||bdefaultSelected; var sel = false||bselected; option.defaultSelected=defSel; option.selected=sel; return option; }";
		ScriptExecutor.executeScript(s, this);
		
		try {
			ScriptableObject.defineClass(this, XMLHttpRequestImpl.class);
		} catch (Exception e) {
			throw new RuntimeException(
				"Fail to define class XMLHttpRequestImpl", e);
		}
		
		if (m_browserType.isIE()) {
//			s = "function ActiveXObject(type) {if (type == 'Msxml2.XMLHTTP'|| type == 'Microsoft.XMLHTTP') {return new XMLHttpRequestImpl(window);}}";
			s = "function ActiveXObject() {return new XMLHttpRequestImpl(window);}";
		} else {
			s = "function XMLHttpRequest() {return new XMLHttpRequestImpl(window);}";
		}
		m_cx.evaluateString(this, s, "XMLHttpRequest", 0, null);
	}

	AHtmlBuilder getHtmlBuilder() {
		return m_builder;
	}

	URL getURL() {
		if (m_url == null) {
			String urlString = getValue("URL", null);
			if (urlString != null) {
				try {
					m_url = new URL(urlString);
				} catch (MalformedURLException e) {
					// NOPMD
				}
			}
		}
		return m_url;
	}

	public void setURL(URL url) {
		m_url = url;
	}

	IDocListener getDocListener() {
		return m_document.getDocListener();
	}

	void setDocListener(IDocListener listener) {
		m_document.setDocListener(listener);
	}
	
	public void addDomListener(IDomChangeListener domListener, boolean enabled) {
		if (m_domListener == null) {
			m_domListener = new DomChangeListenerAdapter();
		}
		m_domListener.add(domListener);
		m_domChangeListenerEnabled = enabled;
	}
	
	public IDomChangeListener getDomListener() {
		AWindow window = DapCtx.ctx().getWindow();
		if (window == null){
			window = this;
		}
		if (!window.m_domChangeListenerEnabled) {
			return null;
		}
		return window.m_domListener;
	}
	
	public boolean isDomChangeListenerEnabled() {
		return m_domChangeListenerEnabled;
	}
	
	public void enableDomChangeListener(boolean set) {
		m_domChangeListenerEnabled = set;
	}
	
	String getGeneratedContentFromScript() {
		return m_document.getGeneratedContent();
	}

	boolean isRoot() {
		return m_isRoot;
	}

	void setRoot(boolean value) {
		m_isRoot = value;
	}
	
	public void setDebugger(DebuggerAdapter dbg) {
		m_dbg = dbg;
	}
	
	public DebuggerAdapter getDebugger() {
		return m_dbg;
	}
	
	public BrowserType getBrowserType() {
		return m_browserType;
	}
	
	public void finialize() {
		Context.exit();
		if (m_dbg != null) {
			m_dbg.dispose();
			m_dbg = null;
		}
		//m_taskExecutor.shutdown();
	}
	
	private String getValue(String name, String fallback) {
		if (m_browserBinding != null) {
			String value = m_browserBinding.getDocumentProperty(name);
			if (value != null) {
				return value;
			}
		}		
		return fallback;
	}

	public HtmlElementStyle getComputedStyle(HtmlElement elem, String pseudoElem) {
		return elem.getCurrentStyle();
	}

	public External getExternal() {
		if(m_external==null){
			m_external = new AExternal(m_browserType,m_browserBinding);
		}
		return m_external; 
	}

	public Frames getChildWindows() {
		return m_frames;
	}

	public Event getEvent() {
		return m_event;
	}

	public void setEvent(Object event) {
		if (event instanceof Event) {
			m_event = (Event)event;
		}
		
	}
	
	public Object getOnLoad() {
		return getListener(EventType.LOAD.getName());
	}
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnload() {
		return getOnLoad();
	}
	
	public Object getOnBlur() {
		return getListener(EventType.BLUR.getName());
	}
	
	// Since property name is 'onblur', Rhino invokes this method.
	public Object getOnblur() {
		return getOnBlur();
	}
	
	public Object getOnFocus() {
		return getListener(EventType.FOCUS.getName());
	}
	
	// Since property name is 'onfocus', Rhino invokes this method.
	public Object getOnfocus() {
		return getOnFocus();
	}
	
	public Object getOnResize() {
		return getListener(EventType.RESIZE.getName());
	}
	
	// Since property name is 'onresize', Rhino invokes this method.
	public Object getOnresize() {
		return getOnResize();
	}
	
	public Object getOnUnload() {
		return getListener(EventType.UNLOAD.getName());
	}
	
	// Since property name is 'onunload', Rhino invokes this method.
	public Object getOnunload() {
		return getOnLoad();
	}
	
	public void setOnLoad(Object functionRef) {
		registerAndBind(EventType.LOAD.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnload(Object functionRef) {
		setOnLoad(functionRef);
	}
	
	public void setOnBlur(Object functionRef) {
		registerAndBind(EventType.BLUR.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnblur(Object functionRef) {
		setOnBlur(functionRef);
	}
	
	public void setOnFocus(Object functionRef) {
		registerAndBind(EventType.FOCUS.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnfocus(Object functionRef) {
		setOnFocus(functionRef);
	}
	
	public void setOnResize(Object functionRef) {
		registerAndBind(EventType.RESIZE.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnresize(Object functionRef) {
		setOnResize(functionRef);
	}
	
	public void setOnUnload(Object functionRef) {
		registerAndBind(EventType.UNLOAD.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnunload(Object functionRef) {
		setOnLoad(functionRef);
	}

	public void addEventListener(String type, Object listener,
			boolean useCapture) {
		if (isValidEvent(type)) {
			getHtmlDocument().addListener(type,listener,useCapture,true,JAVASCRIPT_HANDLER_TYPE.EXTERNAL);
		}
	}
	
	public void add(String type, Object listener, boolean useCapture) {
		addEventListener(type, listener, useCapture);
	}

	public void attachEvent(String type, Object listener) {
		if (isValidEvent(type)) {
			getHtmlDocument().addListener(type,listener,false,true,JAVASCRIPT_HANDLER_TYPE.EXTERNAL);
		}
	}

	public boolean dispatchEvent(Event evt) throws EventException {
		return getHtmlDocument().dispatchEvent(evt);
	}

	public boolean fireEvent(String evtType) {
		evtType = AHtmlHelper.getCorrectType(evtType);
		return getHtmlDocument().fireEvent(evtType);
	}

	public void removeEventListener(String type, Object listener,
			boolean useCapture) {
		type = AHtmlHelper.getCorrectType(type);
		getHtmlDocument().removeEventListener(type, listener,useCapture);
	}
	
	private Object getListener(String type) {
		return getHtmlDocument().getListener(type);
	}
	
	protected void registerAndBind(String type, Object handler) {
		getHtmlDocument().registerAndBind(type, handler);
	}
	
	private boolean isValidEvent(String type){
		type = AHtmlHelper.getCorrectType(type);
		return VALID_EVENTS.contains(type);
	}

	public void detachEvent(String type, Object listener) {
		type = AHtmlHelper.getCorrectType(type);
		getHtmlDocument().removeEventListener(type, listener,false);
	}

	public XMLHttpRequest newXmlHttpReq() {
		return new XMLHttpRequestImpl(this);
	}

	public Option newOption() {
		AOption option = new AOption((AHtmlDocument) getDocument(), new DOption());
		return option;
	}

	public Option newOption(String text) {
		AOption option = new AOption((AHtmlDocument) getDocument(), new DOption());
		option.setText(text);
		return option;
	}

	public Option newOption(String text, String value) {
		AOption option = new AOption((AHtmlDocument) getDocument(), new DOption());
		option.setText(text);
		option.setValue(value);
		return option;
	}

	public Option newOption(String text, String value, boolean defaultSelected) {
		AOption option = new AOption((AHtmlDocument) getDocument(), new DOption());
		option.setText(text);
		option.setValue(value);
		option.setDefaultSelected(defaultSelected);
		return option;
	}

	public Option newOption(String text, String value, boolean defaultSelected,
			boolean selected) {
		AOption option = new AOption((AHtmlDocument) getDocument(), new DOption());
		option.setText(text);
		option.setValue(value);
		option.setDefaultSelected(defaultSelected);
		option.setSelected(selected);
		return option;
	}

	public Image newImage() {
		AImage image = new AImage((AHtmlDocument) getDocument(), new DImg());
		return image;
	}

	public Image newImage(int width) {
		AImage image = new AImage((AHtmlDocument) getDocument(), new DImg());
		image.setWidth(width);
		return image;
	}

	public Image newImage(int width, int height) {
		AImage image = new AImage((AHtmlDocument) getDocument(), new DImg());
		image.setWidth(width);
		image.setHeight(height);
		return image;
	}
	
	/**
	 * If newly added node is dynamic script, 
	 * send it to JsonpProxy for processing.
	 */
	private static final String JS_TYPE = "text/javascript";
	public void handleDynamicScript(final DScript script) {		
		String scriptType = script.getHtmlType();
		if (!JS_TYPE.equalsIgnoreCase(scriptType)) {
		    return;
		}

		//Call JsonpProxy
		String src = script.getHtmlSrc(); 
		if(src != null){
			JsonpProxy.evaluate(src, this);
			script.removeAttribute(EHtmlAttr.src);//to avoid the browser send another request
		}
	}

	@Override
	public String getTitle() {
		return getDocument().getTitle();
	}

	@Override
	public void setTitle(String title) {
		getDocument().setTitle(title);
	}

	@Override
	public Opera getOpera() {
		if(m_opera == null){
			m_opera = new AOpera(m_browserType,m_browserBinding);
		}
		return m_opera; 
	}
}
