/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.active.client.ALocation;
import org.ebayopensource.dsf.active.client.ASelection;
import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.dom.html.AJavaScriptHandlerHolder.JAVASCRIPT_HANDLER_TYPE;
import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.active.event.IDomChangeListener;
import org.ebayopensource.dsf.active.event.IDomEventBindingListener;
import org.ebayopensource.dsf.active.event.IDomEventPublisher;
import org.ebayopensource.dsf.active.event.IEventListenersCollector;
import org.ebayopensource.dsf.dap.event.AKeyEvent;
import org.ebayopensource.dsf.dap.event.AMouseEvent;
import org.ebayopensource.dsf.dap.proxy.NativeJsHelper;
import org.ebayopensource.dsf.dap.rt.DapEventEngine;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.DHtml;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsnative.Document;
import org.ebayopensource.dsf.jsnative.HtmlBody;
import org.ebayopensource.dsf.jsnative.HtmlCollection;
import org.ebayopensource.dsf.jsnative.HtmlDOMImplementation;
import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.HtmlHead;
import org.ebayopensource.dsf.jsnative.HtmlLink;
import org.ebayopensource.dsf.jsnative.HtmlStyle;
import org.ebayopensource.dsf.jsnative.Location;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.NodeList;
import org.ebayopensource.dsf.jsnative.Range;
import org.ebayopensource.dsf.jsnative.Selection;
import org.ebayopensource.dsf.jsnative.StyleSheetList;
import org.ebayopensource.dsf.jsnative.Text;
import org.ebayopensource.dsf.jsnative.Window;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.events.DocumentEvent;
import org.ebayopensource.dsf.jsnative.events.Event;
import org.ebayopensource.dsf.jsnative.events.EventException;
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.ebayopensource.dsf.liveconnect.client.NativeEvent;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;
import org.mozilla.mod.javascript.Undefined;

public class AHtmlDocument extends ADocument implements HtmlDocument,DocumentEvent,EventTarget,IEventListenersCollector {

	private static final long serialVersionUID = 1L;
	private AHtmlCollection m_anchors;
	private AHtmlCollection m_applets;
	private AHtmlCollection m_forms;
	private AHtmlCollection m_frames;
	private AHtmlCollection m_images;
	private AHtmlCollection m_links;
	private StringBuilder m_buffer = new StringBuilder();
	private IDocListener m_docListener;
	
	private DHtmlDocument m_ddoc;
	private IDomEventBindingListener m_domEventBindingListener;
	private IDomEventPublisher m_eventDispatcher;
	private IBrowserBinding m_browserBinding;
	
	private Context m_cx = null;
	private Scriptable m_scope = null;
	private String m_cookie = null;
	private String m_domain = null;
	private String m_referrer = null;
	private String m_URL = null;
	// Location object
	private ALocation m_location;
	private final BrowserType m_browserType;
	private static final String EXEC_COMMAND_JS_METHOD_1 = "document.execCommand(\"{0}\",{1},\"{2}\")";
	private static final String EXEC_COMMAND_JS_METHOD_2 = "document.execCommand(\"{0}\",{1})";
	private ASelection m_selection;
	private static final String MOUSE_EVENTS = "MouseEvents";
	private static final String KEYBOARD_EVENTS = "KeyboardEvent";
	private static final String KEY_EVENTS = "KeyEvents";
	private AWindow m_window;
	private static final String LAST_MODIFIED_PROPERTY = "lastModified";
	private static final String BG_COLOR_PROPERTY = "bgColor";
	private static final String A_LINK_COLOR_PROPERTY = "alinkColor";
	private static final String V_LINK_COLOR_PROPERTY = "vlinkColor";
	private static final String LINK_COLOR_PROPERTY = "linkColor";
	private static final String PROTOCOL_PROPERTY = "protocol";
	private static final String COOKIE_PROPERTY = "cookie";
	private static final String DOMAIN_PROPERTY = "domain";
	private static final String REFERRER_PROPERTY = "referrer";
	private static final String URL_PROPERTY = "URL";
	private static final String FG_COLOR_PROPERTY = "fgColor";
	private static final String READY_STATE_PROPERTY = "readyState";
	private static final String SECURITY_PROPERTY = "security";
	private static final String URL_UNENCODED_PROPERTY = "URLUnencoded";
	private static final String COMPAT_MODE = "compatMode";
	
	private Map<String, List<AJavaScriptHandlerHolder>> m_listeners = null;
	
	private final static ArrayList<String> INVALID_EVENTS = new ArrayList<String>();
	
	private Map<ANode, List<String>> m_deferedANodeEventMap = new LinkedHashMap<ANode, List<String>>();

	static{
		INVALID_EVENTS.add(NativeEvent.resize.toString());
		INVALID_EVENTS.add(NativeEvent.scroll.toString());
		INVALID_EVENTS.add(NativeEvent.load.toString());
		INVALID_EVENTS.add(NativeEvent.unload.toString());
	}

	
	public AHtmlDocument() {
		this(new DHtmlDocument(), BrowserType.IE_6P);
	}
	
	public AHtmlDocument(final ALocation location, final BrowserType browserType) {
		this(new DHtmlDocument(), location, browserType);
	}
	
	public AHtmlDocument(final DHtmlDocument htmlDocument, final BrowserType browserType) {
		this(htmlDocument, null, browserType);
	}

	public AHtmlDocument(
		final DHtmlDocument htmlDocument,
		final ALocation location,
		final BrowserType browserType) {
		
		super(htmlDocument, browserType);
		m_ddoc = htmlDocument;
		m_location = location;
		m_browserType = browserType;
		populateScriptable(AHtmlDocument.class, m_browserType);
	}
	
	//
	// Satisfy DocumentEvent
	//
	public Event createEvent(String eventType)//throws DOMException 
	{
		if(eventType.equalsIgnoreCase(MOUSE_EVENTS)){
			return new AMouseEvent();
		}else if(eventType.equalsIgnoreCase(KEYBOARD_EVENTS)||eventType.equalsIgnoreCase(KEY_EVENTS)){
			return new AKeyEvent();
		}else{
			return DapEventEngine.getInstance().createEvent(eventType);
		}
	}

	@Override
	public Node appendChild(Node newChild) throws DOMException {
		assertLegitimateChild((ANode) newChild);
		appendChild((ANode) newChild, true);
		onElementChange();
		return newChild;
	}

	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		assertLegitimateChild((ANode) newChild);
		insertBefore((ANode) newChild, (ANode) refChild, true);
		onElementChange();
		return newChild;
	}

	@Override
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		assertLegitimateChild((ANode) newChild);
		super.replaceChild(newChild, oldChild);
		onElementChange();
		return oldChild;
	}
	
	@Override
	public Node removeChild(Node oldChild) throws DOMException {
		super.removeChild(oldChild);
		onElementChange();
		return oldChild;
	}

	@Override
	public HtmlElement createElement(String tagName) throws DOMException {
		return (HtmlElement) super.createElement(tagName);
	}

	@Override
	public HtmlElement getDocumentElement() {
		return (HtmlElement) super.getDocumentElement();
	}
	
	@Override
	public Document getOwnerDocument() {
		return null;
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Text createTextNode(String data) {
		return new AText((AHtmlDocument)this, (DText) getDDocument().createTextNode(data));
	}
	
	@Deprecated
	public <T extends HtmlElement> T createElement(AHtmlType<T> type) {
		return type.create(this);
	}
	
	public NodeList getAll() {
		// Get all child elements
		return getElementsByTagName("*");
	}

	public HtmlCollection getAnchors() {
		AHtmlBody body = (AHtmlBody) getBody();
		if (body == null) {
			return AHtmlCollection.EMPTY_COLLECTION;
		}
		if (m_anchors == null) {
			m_anchors = new AHtmlCollection(body, AHtmlCollection.ANCHOR);
		}
		return m_anchors;
	}


	public HtmlCollection getApplets() {
		AHtmlBody body = (AHtmlBody) getBody();
		if (body == null) {
			return AHtmlCollection.EMPTY_COLLECTION;
		}
		if (m_applets == null) {
			m_applets = new AHtmlCollection(body, AHtmlCollection.APPLET);
		}
		return m_applets;
	}


	public HtmlBody getBody() {
		ANode html = getHtml();
		if (html == null) {
			return null;
		}
		Node elem = html.getFirstChild();
		while (elem != null) {
			if (elem instanceof AHtmlBody) {
				return (AHtmlBody) elem;
			}
			elem = elem.getNextSibling();
		}
		return null;
	}


	public String getCookie() {
		if (m_cookie == null) {
			m_cookie = getValue(COOKIE_PROPERTY, null);
		}
		return m_cookie;
	}


	public String getDomain() {
		if (m_domain == null) {
			m_domain = getValue(DOMAIN_PROPERTY, null);
		}
		return m_domain;
	}
	public void setDomain(String domain) {
		m_domain = domain;
		setValue(DOMAIN_PROPERTY, domain);
	}


	/**
	 * Returns an array of nodes of all elements in the document that have a 
	 * specified value for their "name" attributes.
	 * @param elementName value for name attribute
	 * @return ANodeList
	 */
	public NodeList getElementsByName(String elementName) {
		final AElement root = (AElement) getDocumentElement();
		if (root == null) {
			return new ANodeList();
		}
		final ANodeList answer = new ANodeList();
		root.getElementsByName(elementName, answer);
		return answer;
	}
	public NodeList byName(String elementName) {
		return getElementsByName(elementName) ;
	}
	
	public HtmlCollection getForms() {
		AHtmlElement body = (AHtmlElement) getBody();
		if (body == null) {
			return AHtmlCollection.EMPTY_COLLECTION;
		}
		if (m_forms == null) {
			m_forms = new AHtmlCollection(body, AHtmlCollection.FORM);
		}
		return m_forms;
	}
	
	public Object get(String name, Scriptable start) {
		Object obj = super.get(name, start);
		if (obj == NOT_FOUND) {
			obj = findFormObject(name);
		}

		return obj;
	}

	public HtmlCollection getImages() {
		AHtmlElement body = (AHtmlElement) getBody();
		if (body == null) {
			return AHtmlCollection.EMPTY_COLLECTION;
		}
		if (m_images == null) {
			m_images = new AHtmlCollection(body, AHtmlCollection.IMAGE);
		}
		return m_images;
	}

	public HtmlCollection getLinks() {
		AHtmlElement body = (AHtmlElement) getBody();
		if (body == null) {
			return AHtmlCollection.EMPTY_COLLECTION;
		}
		if (m_links == null) {
			m_links = new AHtmlCollection(body, AHtmlCollection.LINK);
		}
		return m_links;
	}
	
	public Location getLocation() {
		return m_location;
	}
	
	public void setLocation(String url) {
		if (m_location != null) {
			try {
				m_location.getWindow().setURL(new URL(url));
			} catch (MalformedURLException e) {
				// NOPMD
			}
		}
		if (m_browserBinding != null) {
			m_browserBinding.setDocumentProperty("location", "\""+url+"\"");
		}
	}


	public String getReferrer() {
		if (m_referrer == null) {
			m_referrer = getValue(REFERRER_PROPERTY, null);
		}
		return m_referrer;
	}


	public String getTitle() {
		AHtmlHead head = getHead();
		return (head == null)? null : head.getTitle();
	}

	public void setTitle(String text) {
		AHtmlHead head = getHead();
		if (head==null) {
			AHtmlHtml html = getHtml();
			if (html==null) {
				DHtml htm = new DHtml(getDDocument());
				html = new AHtmlHtml(this,htm);
				appendChild(html);
			}
			head = new AHtmlHead(this,getDDocument().getHead());
			html.appendChild(head);
			
		}
		head.setTitle(text);
	}
	
	private AHtmlHead getHead() {
		AHtmlHtml html = getHtml();
		if (html == null) {
			return null;
		}
		Node elem = html.getFirstChild();
		while (elem != null) {
			if (elem instanceof AHtmlHead) {
				return (AHtmlHead)elem;
			}
			elem = elem.getNextSibling();
		}
		return null;
	}


	public String getURL() {
		if (m_URL == null) {
			if (m_location != null) {
				m_URL = m_location.getHref();
			} else {
				m_URL = getValue(URL_PROPERTY, null);
			}
		}
		return m_URL;
	}


	public void open() {
		// TODO Auto-generated method stub
	}


	public void setBody(AHtmlElement body) {
		// FIXME DHtmlDocument doesn't implement this method
		if (!AHtmlBody.class.isAssignableFrom(body.getClass())) {
			throw new ADOMException(new DOMException(DOMException.TYPE_MISMATCH_ERR,
			"body is not AHtmlBody type"));
		}
		appendChild(body);
	}


	public void setCookie(String cookie) {
		m_cookie = cookie;
	}

	
	public void write(Object text) {
		
		writeToBuffer(NativeJsHelper.toString(text));
		callBuilderListener();
	}
	
	public void writeln(Object text) {
		
		writeToBuffer(NativeJsHelper.toString(text));
		m_buffer.append("\n");
		callBuilderListener();
	}
	
	public void setDocListener(IDocListener listener) {
		m_docListener = listener;
	}

	public IDocListener getDocListener() {
		return m_docListener;
	}
	
	public void setDomEventBindingListener(IDomEventBindingListener listener) {
		m_domEventBindingListener = listener;
	}
	
	public void setEventDispatcher(IDomEventPublisher eventDispatcher) {
		m_eventDispatcher = eventDispatcher;
	}

	public String getGeneratedContent() {
		String s = m_buffer.toString();
		m_buffer.delete(0, m_buffer.length());
		return s;
	}

	public Context getScriptContext() {
		return m_cx;
	}

	public void setScriptContext(Context cx) {
		m_cx = cx;
	}

	public Scriptable getScriptScope() {
		return m_scope;
	}

	public void setScriptScope(Scriptable scope) {
		m_scope = scope;
	}
	
	public void setBrowserBinding(IBrowserBinding binding)	{
		m_browserBinding = binding;
	}
	
	protected AHtmlHtml getHtml() {
		// The document element is the top-level HTML element of the HTML
		// document. Only this element should exist at the top level.
		// If the HTML element is found, all other elements that might
		// precede it are placed inside the HTML element.
		for (Node node=getFirstChild();node!=null;node=node.getNextSibling()){
			if (node instanceof AHtmlHtml) {
				return (AHtmlHtml)node;
			}
		}
		return null;
	}
		
	public BrowserType getBrowserType() {
		return m_browserType;
	}
	
	public IDomEventPublisher getEventDispatcher() {
		return m_eventDispatcher;
	}
	
	IDomChangeListener getDomListener() {
		if (m_window != null){
			return m_window.getDomListener();
		}
		return null;
	}
	
	public IDomEventBindingListener getDomEventBindingListener() {
		return m_domEventBindingListener;
	}
	
	IBrowserBinding getBrowserBinding() {
		return m_browserBinding;
	}
	
	DHtmlDocument getDHtmlDocument(){
		return m_ddoc;
	}
	
	private void onElementChange() {
		IDomChangeListener listener = getDomListener();
		if (listener != null) {
			listener.onElementChange((BaseHtmlElement) getDNode());
		}
	}
	
	private Object findFormObject(String name) {
		HtmlCollection forms = getForms();
		for (int i = 0; i < forms.getLength(); i++) {
			AHtmlForm form = (AHtmlForm)forms.item(i);
			if (name.equalsIgnoreCase(form.getName())) {
				return form;
			}
		}
		return NOT_FOUND;
	}
	
	public DHtmlDocument getDDocument() {
		return (DHtmlDocument) getDNode();
	}

	private void assertLegitimateChild(ANode child) {
		if (child == null) {
			return;
		}
		final Class<?> newChildsClass = child.getClass();
		if	(AHtmlHtml.class.isAssignableFrom(newChildsClass)
		/*	|| AHTMLDocType.class.isAssignableFrom(newChildsClass)
			|| AHTMLComment.class.isAssignableFrom(newChildsClass)
			|| AHTMLProcessingInstruction.class.isAssignableFrom(newChildsClass) */) 
		{
			return;
		}
		throw new ADOMException(new DOMException(
				DOMException.VALIDATION_ERR, 
				"HtmlDocument children can be HtmlDocType(0..1), HtmlHtmlElement(0..1), ProcessingInstruction(0..n), Comment(0..n)"));
	}
	
	private void callBuilderListener() {
		if (m_docListener != null) {
			m_docListener.doneDocumentWrite();
		}
	}
	
	private void writeToBuffer(Object ... args) {
		for (Object o : args) {
			if (o != null && !(o instanceof Undefined)) {
				m_buffer.append(toStringValue(o));
			}
		}
	}
	
	@Override
	public String toString() {
		return getNodeName();
	}
	
	private String toStringValue(Object o) {
		if (o instanceof ScriptableObject) {
			o = ((ScriptableObject)o).getDefaultValue(null);
		}
		return o.toString();
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

	public HtmlCollection getFrames() {
		AHtmlElement body = (AHtmlElement) getBody();
		if (body == null) {
			return AHtmlCollection.EMPTY_COLLECTION;
		}
		if (m_frames == null) {
			m_frames = new AHtmlCollection(body, AHtmlCollection.IFRAME);
		}
		return m_frames;
	}
	
	public boolean execCommand(String command) {
		return execCommand(command, false, null);
	}
	
	public boolean execCommand(String command, boolean userInterface) {
		return execCommand(command, userInterface, null);
	}

	public boolean execCommand(String command, boolean userInterface, Object value) {
		String ret = "";
		if (m_browserBinding != null) {
			if(value == null || value.toString().equals("undefined")){
				ret = m_browserBinding.executeJs(MessageFormat.format(EXEC_COMMAND_JS_METHOD_2, command,userInterface));
			}else{
				ret = m_browserBinding.executeJs(MessageFormat.format(EXEC_COMMAND_JS_METHOD_1, command,userInterface,value));
			}
		}		
		return ret.equalsIgnoreCase("true")?true:false;
	}

	public Selection getSelection() {
		if(m_selection==null){
			m_selection = new ASelection(m_browserType,m_browserBinding);
		}
		return m_selection; 
	}
	
	public Window getDefaultView() {
		return m_window;
	}

	public Window getParentWindow() {
		return m_window;
	}
	
	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		}
		else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}


	
	
	public AWindow getWindow() {
		return m_window;
	}

	public void setWindow(AWindow m_window) {
		this.m_window = m_window;
	}
	
	@Override	
	public String getLastModified() {
		return getValue(LAST_MODIFIED_PROPERTY,null);
	}

	@Override	
	public String getBgColor() {
		return getValue(BG_COLOR_PROPERTY,null);
	}

	@Override	
	public String getAlinkColor() {
		return getValue(A_LINK_COLOR_PROPERTY,null);
	}

	@Override	
	public String getLinkColor() {
		return getValue(LINK_COLOR_PROPERTY,null);
	}

	@Override	
	public String getVlinkColor() {
		return getValue(V_LINK_COLOR_PROPERTY,null);
	}

	@Override	
	public String getProtocol() {
		return getValue(PROTOCOL_PROPERTY,null);
	}
	
	@Override	
	public String getFgColor() {
		return getValue(FG_COLOR_PROPERTY,null);
	}
	
	@Override
	public String getReadyState() {
		return getValue(READY_STATE_PROPERTY,null);
	}

	@Override
	public String getSecurity() {
		return getValue(SECURITY_PROPERTY,null);
	}

	public String getURLUnencoded() {
		return getValue(URL_UNENCODED_PROPERTY,null);
	}

	public void addEventListener(String type, Object listener,
			boolean useCapture) {
		if(isValidEvent(type)){
			addListener(type,listener,useCapture,true,JAVASCRIPT_HANDLER_TYPE.EXTERNAL);
		}
	}
	
	public void add(String type, Object listener, boolean useCapture) {
		addEventListener(type, listener, useCapture);
	}

	public void attachEvent(String type, Object listener) {
		if(isValidEvent(type)){
			addListener(type,listener,false,true,JAVASCRIPT_HANDLER_TYPE.EXTERNAL);
		}
	}

	public void addListener(String type, Object listener, boolean useCapture, boolean bind, JAVASCRIPT_HANDLER_TYPE handlerType){
		type = AHtmlHelper.getCorrectType(type);
		AHtmlHelper.addEventHandlerListeners( getListeners(),type,listener,handlerType);
		IDomEventBindingListener evtBindingListener = getDomEventBindingListener();
		if (evtBindingListener != null) {
			if (evtBindingListener.isEventBinding(getDDocument(), type) && bind) {
				evtBindingListener.eventBound(getDDocument(), type);
			}
		}

	}
	
	public void removeListener(String type, Object listener, boolean useCapture){
		if(m_listeners != null && m_listeners.get(type)==null){
			List<AJavaScriptHandlerHolder> lst = m_listeners.get(type);
			if(lst!=null){
				for(int i=0; i<lst.size();i++){
					if(lst.get(i).equals(listener)){
						lst.remove(i);
					}
				}
			}
		}
	}

	public Map<String, List<AJavaScriptHandlerHolder>> getListeners() {
		if(m_listeners == null){
			m_listeners = new HashMap<String, List<AJavaScriptHandlerHolder>>();
		}
		return m_listeners;
	}

	public boolean dispatchEvent(Event evt) throws EventException {
		String evtType = AHtmlHelper.getCorrectType(evt.getType());
		return dispatchEvent(evtType, this);
	}

	public boolean fireEvent(String evtType) {
		evtType = AHtmlHelper.getCorrectType(evtType);
		return dispatchEvent(evtType, this);
	}

	public void removeEventListener(String type, Object listener,
			boolean useCapture) {
		type = AHtmlHelper.getCorrectType(type);
		removeListener(type, listener,useCapture);
	}
	
	protected boolean dispatchEvent(String evtType, EventTarget src) {
		evtType = AHtmlHelper.getCorrectType(evtType);
		IDomEventPublisher dispatcher = this.getEventDispatcher();
		if (dispatcher == null){
			return false;
		}
		return dispatcher.publish(evtType, src);
	}

	private boolean isValidEvent(String type){
		type = AHtmlHelper.getCorrectType(type);
		return !INVALID_EVENTS.contains(type);
	}

	public void detachEvent(String type, Object listener) {
		removeListener(type, listener,false);
	}

	@Override
	public String getNodeName() {
		if (m_browserType == null) {
			return super.getNodeName();
		}
		if (m_browserType.isFireFox()) {
			return "[object HTMLDocument]";
		} else if (m_browserType.isIE()) {
			return "[object]";
		} else {
			return super.getNodeName();
		}
		
	}
	
	public Object getOnClick() {
		return getListener(EventType.CLICK.getName());
	}
	
	// Since property name is 'onclick', Rhino invokes this method.
	public Object getOnclick() {
		return getOnClick();
	}

	public Object getOnDblClick() {
		return getListener(EventType.DBLCLICK.getName());
	}
	
	// Since property name is 'ondblclick', Rhino invokes this method.
	public Object getOndblclick() {
		return getOnDblClick();
	}
	
	public Object getOnKeyDown() {
		return getListener(EventType.KEYDOWN.getName());
	}
	
	// Since property name is 'onkeydown', Rhino invokes this method.
	public Object getOnkeydown() {
		return getOnKeyDown();
	}

	public Object getOnKeyPress() {
		return getListener(EventType.KEYPRESS.getName());
	}
	
	// Since property name is 'onkeypress', Rhino invokes this method.
	public Object getOnkeypress() {
		return getOnKeyPress();
	}

	public Object getOnKeyUp() {
		return getListener(EventType.KEYUP.getName());
	}
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnkeyup() {
		return getOnKeyUp();
	}
	
	public Object getOnMouseDown() {
		return getListener(EventType.MOUSEDOWN.getName());
	}
	
	// Since property name is 'onmousedown', Rhino invokes this method.
	public Object getOnmousedown() {
		return getOnMouseDown();
	}

	public Object getOnMouseMove() {
		return getListener(EventType.MOUSEMOVE.getName());
	}
	
	// Since property name is 'onmousemove', Rhino invokes this method.
	public Object getOnmousemove() {
		return getOnMouseMove();
	}

	public Object getOnMouseOut() {
		return getListener(EventType.MOUSEOUT.getName());
	}
	
	// Since property name is 'onmouseout', Rhino invokes this method.
	public Object getOnmouseout() {
		return getOnMouseOut();
	}

	public Object getOnMouseOver() {
		return getListener(EventType.MOUSEOVER.getName());
	}
	
	// Since property name is 'onmouseover', Rhino invokes this method.
	public Object getOnmouseover() {
		return getOnMouseOver();
	}

	public Object getOnMouseUp() {
		return getListener(EventType.MOUSEUP.getName());
	}
	
	// Since property name is 'onmouseup', Rhino invokes this method.
	public Object getOnmouseup() {
		return getOnMouseUp();
	}
	
	public void setOnClick(Object functionRef) {
		registerAndBind(EventType.CLICK.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnclick(Object functionRef) {
		setOnClick(functionRef);
	}

	public void setOnDblClick(Object functionRef) {
		registerAndBind(EventType.DBLCLICK.getName(), functionRef);
	}
	
	// For Rhino
	public void setOndblclick(Object functionRef) {
		setOnDblClick(functionRef);
	}
	
	public void setOnKeyDown(Object functionRef) {
		registerAndBind(EventType.KEYDOWN.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnkeydown(Object functionRef) {
		setOnKeyDown(functionRef);
	}

	public void setOnKeyPress(Object functionRef) {
		registerAndBind(EventType.KEYPRESS.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnkeypress(Object functionRef) {
		setOnKeyPress(functionRef);
	}

	public void setOnKeyUp(Object functionRef) {
		registerAndBind(EventType.KEYUP.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnkeyup(Object functionRef) {
		setOnKeyUp(functionRef);
	}
	
	public void setOnMouseDown(Object functionRef) {
		registerAndBind(EventType.MOUSEDOWN.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnmousedown(Object functionRef) {
		setOnMouseDown(functionRef);
	}

	public void setOnMouseMove(Object functionRef) {
		registerAndBind(EventType.MOUSEMOVE.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnmousemove(Object functionRef) {
		setOnMouseMove(functionRef);
	}

	public void setOnMouseOut(Object functionRef) {
		registerAndBind(EventType.MOUSEOUT.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnmouseout(Object functionRef) {
		setOnMouseOut(functionRef);
	}

	public void setOnMouseOver(Object functionRef) {
		registerAndBind(EventType.MOUSEOVER.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnmouseover(Object functionRef) {
		setOnMouseOver(functionRef);
	}

	public void setOnMouseUp(Object functionRef) {
		registerAndBind(EventType.MOUSEUP.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnmouseup(Object functionRef) {
		setOnMouseUp(functionRef);
	}
	
	public void registerAndBind(String type, Object handler) {
		addListener(type,handler,false,true,JAVASCRIPT_HANDLER_TYPE.INLINE);
	}
	
	public Object getListener(String type) {
		Map<String, List<AJavaScriptHandlerHolder>> listeners = getListeners();
		List<AJavaScriptHandlerHolder> lst = listeners.get(type);
		if(lst==null || lst.isEmpty()){
			return null;
		}
		AJavaScriptHandlerHolder h = lst.get(0);
		if (h == null || h.getHandlerType() != JAVASCRIPT_HANDLER_TYPE.INLINE) {
			return null;
		}
		return h.getHandler();
	}
	
	

//	@Override
//	public void put(String name, Scriptable start, Object value) {
//		super.put(name, start, value);
//		if (!name.startsWith("on")) {
//			return;
//		}
//		if (name.length() < 4) {
//			return;
//		}
//		IDomEventBindingListener listener = getDomEventBindingListener();
//		if (listener != null) {
//			String type = name.substring(2, name.length());
//			if (listener.isEventBinding(this.getDDocument(), type)) {
//				listener.eventBound(this.getDDocument(), type);
//			}
//		}
//	}


	public Map<ANode, List<String>> getDeferedANodeEventMap() {
		return m_deferedANodeEventMap;
	}

	public Object getOnReadyStateChange() {
		return getListener(EventType.READYSTATECHANGE.getName());
	}

	public void setOnReadyStateChange(Object functionRef) {
		registerAndBind(EventType.READYSTATECHANGE.getName(), functionRef);
	}

	// Since property name is 'onreadystatechange', Rhino invokes this method.
	public Object getOnreadystatechange() {
		return getOnReadyStateChange();
	}
	// For Rhino
	public void setOnreadystatechange(Object functionRef) {
		setOnReadyStateChange(functionRef);
	}

	public void __createStyleSheet(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
		if(arg1 instanceof Undefined){
			createStyleSheet();
//		} else if(arg2 instanceof Undefined){
//			createStyleSheet((String)arg1);
		} else {
			createStyleSheet((String)arg1);
		}
	}

	public void createStyleSheet() {
		createStyleSheet(null, 0);
	}

	public void createStyleSheet(String url) {
		createStyleSheet(url, 0);
	}

	public void createStyleSheet(String url, int index) {
		HtmlHead head = (HtmlHead) getElementsByTagName("head").item(0);
		if (url != null) {
			HtmlLink link = (HtmlLink) createElement("link");
			link.setRel("stylesheet");
			link.setType("text/css");
			link.setHref(url);
			if (head != null) {
				head.appendChild(link);
			}
		} else {
			HtmlStyle style = (HtmlStyle) createElement("style");
			if (head != null) {
				head.appendChild(style);
			}
		}
	}

	public String getCompatMode() {
		// We are asking the browser to return us the compatMode property
		// based on the HMTL document that was parsed. Otherwise, we need
		// to implement the DOCTYPE compatibility mode table specified in
		// http://hsivonen.iki.fi/doctype
		return getValue(COMPAT_MODE, "BackCompat");
	}

	public void setBgColor(String value) {
		setValue(BG_COLOR_PROPERTY, value);
	}

	public void setFgColor(String value) {
		setValue(FG_COLOR_PROPERTY, value);
	}

	public void setLinkColor(String value) {
		setValue(LINK_COLOR_PROPERTY, value);
	}
	
	@Override
	public HtmlElement byId(final String elementId) {
		return getElementById(elementId) ;
	}
	
	@Override
	public HtmlElement getElementById(final String elementId) {
		return (HtmlElement) super.getElementById(elementId) ;
	}
		
	private void setValue(String name, String value) {
		if (m_browserBinding != null) {
			if(!value.startsWith("'") || !value.startsWith("\"")){
				value = "'" + value;
			}
			if(!value.endsWith("'") || !value.endsWith("\"")){
				value = value + "'";
			}
			m_browserBinding.setDocumentProperty(name, value);			
		}		
	}

	@Override
	public Node all(int index) {
		return getAll().item(index);
	}

	@Override
	public NodeList all(String id) {
		ANodeList result = (ANodeList) getElementsByName(id);
		Node node = getElementById(id);
		if (node != null) {
			 result.add((ANode) node);
		}
		return result;
	}

	@Override
	public Node all(String id, int subIndex) {
		return all(id).item(subIndex);
	}

	public Event createEventObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public Event createEventObject(Event evt) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Event createEventObject(String eventType) {
		return DapEventEngine.getInstance().createEvent(eventType);
	}
	
	@Override
	public Event __createEventObject(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5) {
		if(arg1 == null){
			return createEventObject();
		}
		if(arg1 instanceof String){
			return createEventObject((String)arg1);
//		} else if(arg2 instanceof Undefined){
//			createStyleSheet((String)arg1);
		} else if (arg1 instanceof Event) {
			return createEventObject((Event)arg1);
		}
		return null;
	}

	@Override
	public StyleSheetList getStyleSheets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Range createRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HtmlDOMImplementation getHtmlImplementation() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
