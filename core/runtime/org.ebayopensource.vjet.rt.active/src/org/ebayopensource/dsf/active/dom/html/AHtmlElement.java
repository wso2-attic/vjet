/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.active.client.ATextRectangle;
import org.ebayopensource.dsf.active.client.ScriptExecutor;
import org.ebayopensource.dsf.active.dom.html.AJavaScriptHandlerHolder.JAVASCRIPT_HANDLER_TYPE;
import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.active.event.IDomChangeListener;
import org.ebayopensource.dsf.active.event.IDomEventBindingListener;
import org.ebayopensource.dsf.active.event.IDomEventPublisher;
import org.ebayopensource.dsf.active.event.IEventListenersCollector;
import org.ebayopensource.dsf.active.util.ANodeHelper;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;
import org.ebayopensource.dsf.css.dom.impl.DCssStyleDeclaration;
import org.ebayopensource.dsf.dap.rt.DapDomEventBindingListener;
import org.ebayopensource.dsf.dap.util.DapDomHelper;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.html.HtmlBuilderHelper;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.DBody;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.DScript;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.sax.AHtmlSchema;
import org.ebayopensource.dsf.jsnative.ElementView;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.HtmlElementStyle;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.NodeList;
import org.ebayopensource.dsf.jsnative.TextRectangle;
import org.ebayopensource.dsf.jsnative.TextRectangleList;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.events.Event;
import org.ebayopensource.dsf.jsnative.events.EventException;
import org.ebayopensource.dsf.jsnative.events.EventListener;
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.mozilla.mod.javascript.Context;

public class AHtmlElement extends AElement 
	implements HtmlElement, ElementView, EventTarget, IEventListenersCollector {
	
	static final String FOCUS_JS_METHOD = "focus()";
	private static final long serialVersionUID = 1L;
	
	private HtmlElementStyle m_style = null;
	
	private HtmlElementStyle m_currentStyle = null; //IE only
	private HtmlElementStyle m_runtimeStyle = null; //IE only
	
	private TextRectangle m_textRectangle;
	
	private Map<String, List<AJavaScriptHandlerHolder>> m_listeners = null;
	private static final String COMPARE_DOCUMENT_POSITION =  "{0}.compareDocumentPosition({1})";
	
	protected AHtmlElement(AHtmlDocument doc, BaseHtmlElement node) {
		super(doc, node);
		populateScriptable(AHtmlElement.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
	protected AHtmlElement(AHtmlDocument doc, DElement node) {
		super(doc, node);
		populateScriptable(AHtmlElement.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	//
	// Satisfy EventTarget
	//
	/**
	 * @see EventTarget#addEventListener(String, EventListener, boolean)
	 */
	public void addEventListener(
			String type, 
			Object listener, 
			boolean useCapture){
		addListener(type,listener,useCapture,true,JAVASCRIPT_HANDLER_TYPE.EXTERNAL);
	}
	
	public void add(String type, Object listener, boolean useCapture) {
		addEventListener(type, listener, useCapture);
	}

	public Node addBr() {
		HtmlElement newChild = getOwnerDocument().createElement("br") ;
		appendChild(newChild) ;
		return newChild ;
	}
    
	public void blur() {
		dispatchEvent(EventType.BLUR.getName(), this);

	}

	public void focus() {
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			IBrowserBinding browserBinding = doc.getBrowserBinding();
			if(browserBinding!=null){
				browserBinding.executeDomMethod(getElement(), FOCUS_JS_METHOD);
			}
		}
	}
	
	/**
	 * @see EventTarget#removeEventListener(String, EventListener, boolean)
	 */
	public void removeEventListener(
			String type, 
			Object listener, 
            boolean useCapture){
		removeListener(type, listener,useCapture);
	}

	/**
	 * @see EventTarget#dispatchEvent(Event)
	 */
	public boolean dispatchEvent(Event evt) throws EventException{
		return dispatchEvent(evt.getType(), this);
	}
	
	@Override
	public AHtmlDocument getOwnerDocument() {
		return (AHtmlDocument) super.getOwnerDocument();
	}
	
	private boolean isDynamicScript(AHtmlScript script){		
		String src = script.getSrc(); 
		if(src == null || src.trim().length() == 0){
			return false;
		}
		return true;
	}
	
	@Override
	public Node appendChild(Node newChild) throws DOMException {
		//handling ADocumentFragment
		Node[] arr = getDocumentFragmentNodes(newChild);
		if(arr!=null){
			for (Node node : arr) {
				appendChild(node);
			}
			return newChild;
		}
		super.appendChild(newChild);
		if (newChild instanceof AHtmlElement) {
			((AHtmlElement)newChild).associateIdElementsToDoc();
		}
		onAppendChild((ANode) newChild);
		evalJs(newChild);
		return newChild;
	}

	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		//handling ADocumentFragment
		Node[] arr = getDocumentFragmentNodes(newChild);
		if(arr!=null){
			for (Node node : arr) {
				insertBefore(node, refChild);
			}
			return newChild;
		}
		if (refChild == null) {
			appendChild(newChild);
			return newChild;
		}
		Node oldParent = newChild.getParentNode();
		if (oldParent != null) {
			oldParent.removeChild(newChild);
		}
		onInsert((ANode) newChild, (ANode) refChild, true); //call listener before real action
		Node rtnVal = super.insertBefore(newChild, refChild);
		if (newChild instanceof AHtmlElement) {
			((AHtmlElement)newChild).associateIdElementsToDoc();
		}
		evalJs(newChild);
		return rtnVal;

	}

	@Override
	public Node removeChild(Node oldChild) throws DOMException {
		onRemove((ANode) oldChild); //call listener before real action
		return super.removeChild(oldChild);
	}

	@Override
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		super.replaceChild(newChild, oldChild);
		onElementChange();
		return oldChild;
	}


	public String getClassName() {
		return getHtmlAttribute(EHtmlAttr._class);
	}

	public String getDir() {
		return getHtmlAttribute(EHtmlAttr.dir);
	}


	public String getId() {
		return getHtmlAttribute(EHtmlAttr.id);
	}


	public String getLang() {
		return getHtmlAttribute(EHtmlAttr.lang);
	}


	public String getTitle() {
		return getHtmlAttribute(EHtmlAttr.title);
	}
	
	public HtmlElementStyle getStyle() {
		if (m_style == null) {
			m_style = new AHtmlElementStyle(this);
		}
		return m_style;
	}
	
	public String getInnerHTML() {
		if (m_childNodes == null || m_childNodes.getLength() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m_childNodes.getLength(); i++) {
			sb.append(ANodeHelper.getHtmlText((ANode) m_childNodes.item(i)));
		}
		return sb.toString();
	}
	
	// NOT in the org.w3c.dom.html.HTMLElement API
	public String getStyleAsString() {
		return getHtmlStyleAsString();
	}


	public void setClassName(String className) {
		setHtmlAttribute(EHtmlAttr._class, className);
	}


	public void setDir(String dir) {
		setHtmlAttribute(EHtmlAttr.dir, dir);
	}


	public void setId(String id) {
		setHtmlAttribute(EHtmlAttr.id, id);
	}


	public void setLang(String lang) {
		setHtmlAttribute(EHtmlAttr.lang, lang);
	}


	public void setTitle(String title) {
		setHtmlAttribute(EHtmlAttr.title, title);
	}
	
	public void setStyle(final HtmlElementStyle style) {
		m_style = style;
	}
	
	@Override
	public void setAttribute(String name, String value) throws DOMException {
		super.setAttribute(name, value);
		if ("id".equalsIgnoreCase(name)) {
			setIdAttribute("id", true);
		} 
		try {
			if (!"style".equalsIgnoreCase(name)) {
				if ("class".equalsIgnoreCase(name)) {
					onAttrChange(EHtmlAttr.className, value);
				}
				else {
					onAttrChange(EHtmlAttr.enumFor(name), value);
				}
			}
		} catch (IllegalArgumentException e) {
			// NOPMD ignore if attribute name is not defined in EHtmlAttr
		}
	}
	
	@Override
	public void setIdAttribute(String name, boolean isId) throws DOMException {
		super.setIdAttribute(name, isId);
		
		final String id = getAttribute(name);
		if (id == null) {
			throw new ADOMException(new DOMException(DOMException.NOT_FOUND_ERR,
					"no attribute found with name " + name));
		}
		AHtmlDocument doc = getOwnerDocument();
		if (isId) {
			if (isInDom(this, doc.getHtml())) {
				doc.putIdentifier(id, this);
			}
		} 
		else {
			doc.removeIdentifier(id);
		}
	}
	
	public void setInnerHTML(String html) {
		if (m_childNodes != null) {
			for (int i = m_childNodes.getLength() - 1; i >= 0; i--) {
				ANode child = (ANode)m_childNodes.item(i);
				removeChild(child);
				child.setOwnerDocument(null);
				child.getDNode().dsfDetachFromOwnerDocument();
			}
		}

		String source;
		// Null input removes the entire body content
		if (html == null) {
			source = "<body></body>";
		}
		else if (html.startsWith("<body>")) {
			source = html;
		} else {
			source = "<body>" + html + "</body>";
		}
		DHtmlDocument doc = HtmlBuilderHelper.parseHtmlFragment(source, true, AHtmlSchemaForParser.getInstance());
		DBody body = doc.getBody();
		DNode child = (DNode)body.getFirstChild();
		while (child != null) {
			body.removeChild(child);
			child.dsfDetachFromOwnerDocument();
//			getDNode().appendChild(child);
			AHtmlFactory.appendChild(this, child);
			child = (DNode)body.getFirstChild();
		}
	}
	
	// NOT in the org.w3c.dom.html.HTMLElement API
//	public void setStyleAsString(final String styleString) {
//		setHtmlStyleAsString(styleString);
//	}
	
	protected AHtmlForm getFormInternal() {
		Node parent = getParentNode();
		while (parent != null) {
			if (parent instanceof  AHtmlForm) {
				return (AHtmlForm) parent;
			}
			parent = parent.getParentNode();
		}
		return null;
	}
	
	public String getTagName() {
		return super.getTagName().toUpperCase();
	}
	
	protected void onElementChange() {
		IDomChangeListener listener = getChangeListener();
		if (listener != null) {
			listener.onElementChange((BaseHtmlElement) getDNode());
		}
	}

	protected void onAppendChild(ANode newChild) {
		IDomChangeListener listener = getChangeListener();
		if (listener != null && isInDoc(newChild)) {
			listener.onAppendChild(newChild.getDNode());
			enableDeferedElementsEventBinding(newChild);
		}
	}
	
	private boolean isInDoc(ANode node){
		return DapDomHelper.isInDoc(node.getDNode());
	}
	
	private void collectDeferedElementEvents(ANode node,String eventType){
		Map<ANode, List<String>> elmEvtMap = getOwnerDocument().getDeferedANodeEventMap();
		List<String> events = elmEvtMap.get(node);
		if(events==null){
			events = new ArrayList<String>(2);
			elmEvtMap.put(node, events);
		}
		events.add(eventType);
	}

	private void enableDeferedElementsEventBinding(ANode newChild){
		
		AHtmlDocument doc = getOwnerDocument();
		DapDomEventBindingListener binding = (DapDomEventBindingListener)doc.getDomEventBindingListener();
		//if listener is null, return
		if(binding==null) return;

		Map<ANode, List<String>> elmEvtMap = doc.getDeferedANodeEventMap();
		//if map is empty, return
		if(elmEvtMap.size()==0) return;
		
		
		Set<ANode> keys = elmEvtMap.keySet();
		ArrayList<ANode> toBeRemoved = new ArrayList<ANode>() ;
		for(ANode node : keys){
			List<String> evts = elmEvtMap.get(node);
			int size = evts.size();
			if(size>0 && isInDom(node, newChild)){
				for(int i=0;i<size;i++){
					binding.eventBound(node.getDNode(), evts.get(i));
				}
				// Remember what we need to cleanup.  We can't do the remove
			    // "in-loop" without risking a ConcurrentModification exception.
			    toBeRemoved.add(node);
			}
		}
		for(ANode node: toBeRemoved){ 
			elmEvtMap.remove(node) ;
		}
	}

	protected void onInsert(ANode newNode, ANode refNode, boolean insertBefore) {
		IDomChangeListener listener = getChangeListener();
		if (listener != null) {
			listener.onInsert(newNode.getDNode(), refNode.getDNode(), insertBefore);
		}
	}
	
	protected void onRemove(ANode newNode) {
		IDomChangeListener listener = getChangeListener();
		if (listener != null) {
			listener.onRemove(newNode.getDNode());
		}
	}
	
	protected void onValueChange(final String value) {
		IDomChangeListener listener = getChangeListener();
		if (listener != null) {
			listener.onValueChange((BaseHtmlElement) getDNode(), value);
		}
	}
	
	protected void onAttrChange(EHtmlAttr attr, boolean value) {
		IDomChangeListener listener = getChangeListener();
		if (listener != null) {
			listener.onAttrChange((BaseHtmlElement) getDNode(), attr, value);
		}
	}
	
	protected void onAttrChange(EHtmlAttr attr, int value) {
		IDomChangeListener listener = getChangeListener();
		if (listener != null) {
			listener.onAttrChange((BaseHtmlElement) getDNode(), attr, value);
		}
	}
	
	protected void onAttrChange(EHtmlAttr attr, double value) {
		IDomChangeListener listener = getChangeListener();
		if (listener != null) {
			listener.onAttrChange((BaseHtmlElement) getDNode(), attr, value);
		}
	}
	
	protected void onAttrChange(EHtmlAttr attr, String value) {
		IDomChangeListener listener = getChangeListener();
		if (listener != null) {
			listener.onAttrChange((BaseHtmlElement) getDNode(), attr, value);
		}
	}
	
	public void onWidthChange(int width) {
		IDomChangeListener listener = getChangeListener();
		if (listener != null) {
			listener.onWidthChange((BaseHtmlElement) getDNode(), width);
		}
	}
	
	public void onHeightChange(int height) {
		IDomChangeListener listener = getChangeListener();
		if (listener != null) {
			listener.onHeightChange((BaseHtmlElement) getDNode(), height);
		}
	}
	
	private IDomChangeListener getChangeListener() {
		AHtmlDocument doc = getOwnerDocument();
		if (doc == null) {
			return null;
		}
		return doc.getDomListener();
	}
	
	private IBrowserBinding getBrowserBinding() {
		AHtmlDocument doc = getOwnerDocument();
		if (doc == null) {
			return null;
		}
		return doc.getBrowserBinding();
	}

	protected boolean dispatchEvent(String evtType, EventTarget src) {
		AHtmlDocument doc = getOwnerDocument();
		if (doc == null) {
			return false;
		}
		IDomEventPublisher dispatcher = doc.getEventDispatcher();
		if (dispatcher == null){
			return false;
		}
		return dispatcher.publish(evtType, src);
	}

	
	ICssStyleDeclaration getHtmlStyle() {
		final String styleString = getHtmlAttribute(EHtmlAttr.style);
		if (styleString == null || "".equals(styleString)) {
			return null;
		}
		final ICssStyleDeclaration style = new DCssStyleDeclaration(null);
		style.setCssText("{" + styleString + "}");
		return style;
	}
	
	String getHtmlStyleAsString() {
		final String styleString = getHtmlAttribute(EHtmlAttr.style);
		if (styleString == null || "".equals(styleString)) {
			return null;
		}
		return styleString;
	}
	
	String getHtmlAttribute(final EHtmlAttr attr) {
		return getAttribute(attr.getAttributeName());
	}
	
	void setHtmlAttribute(final EHtmlAttr attr, final boolean value) {
		setHtmlAttribute(attr, String.valueOf(value));
	}
	
	void setHtmlAttribute(final EHtmlAttr attr, final int value) {
		setHtmlAttribute(attr, String.valueOf(value));
	}
	
	void setHtmlAttribute(final EHtmlAttr attr, final String value) {
		setAttribute(attr.getAttributeName(), value);
	}

	void setHtmlStyle(final ICssStyleDeclaration style) {
		if (style == null) {
			if (hasAttributes()) {
				getElement().removeAttribute(EHtmlAttr.style);
			}
			return;
		}
		final String styleString = style.getCssText();
		// TODO: why can't we just parse/generate the stuff between '{' and '}'
		final String realText = styleString.substring(1, styleString.length()-1);
		setHtmlAttribute(EHtmlAttr.style, realText);
	}
	
	private BaseHtmlElement getElement() {
		return (BaseHtmlElement) getDNode();
	}
	
	protected int getIntBindingValue(EHtmlAttr attr) {
		String value = getBindingValue(attr);
		if (value == null || value.length() == 0) {
			return 0;
		}
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			return 0;
		}
	}
	
	protected String getBindingValue(EHtmlAttr attr) {
		BaseHtmlElement elem = getElement();
		String value = null;
		IBrowserBinding browserBinding = getBrowserBinding();
		if (browserBinding != null) {
			value = browserBinding.getDomAttributeValue(elem, attr);
		}
		if(value != null){
			return value;
		}
		return getHtmlAttribute(attr);
	}
	
	protected void onStyleChange(String name, String value) {
		AHtmlDocument doc = getOwnerDocument();
		if (doc == null) {
			return;
		}
		IDomChangeListener listener = doc.getDomListener();
		if (listener != null) {
			listener.onStyleChange((BaseHtmlElement)getDNode(), name, value);
		}
	}
	
	@Override
	void setOwnerDocument(ADocument doc) {
		super.setOwnerDocument(doc);
		if (doc != null) {
			String idValue = getAttribute("id");
			if (idValue != null && idValue.length() > 0) {
				setIdAttribute("id", true);
			}
		}
	}
	
	public HtmlElementStyle getCurrentStyle() {
		if (m_currentStyle == null) {
			m_currentStyle = new AHtmlElementCurrentStyle(this);
		}
		return m_currentStyle;
	}
	
	public HtmlElementStyle getRuntimeStyle() {
		if (m_runtimeStyle == null) {
			m_runtimeStyle = new AHtmlElementRuntimeStyle(this);
		}
		return m_runtimeStyle;
	}

	public void attachEvent(String type, Object listener) {
		type = AHtmlHelper.getCorrectType(type);
		addListener(type,listener,false,true,JAVASCRIPT_HANDLER_TYPE.EXTERNAL);
	}

	public boolean fireEvent(String evtType){
		evtType = AHtmlHelper.getCorrectType(evtType);
		return dispatchEvent(evtType, this);
	}

	public void detachEvent(String type, Object listener) {
		type = AHtmlHelper.getCorrectType(type);
		removeEventListener(type, listener,false);
	}

	public void addListener(String type, Object listener, boolean useCapture, boolean bind, JAVASCRIPT_HANDLER_TYPE handlerType){
		type = AHtmlHelper.getCorrectType(type);
		AHtmlHelper.addEventHandlerListeners( getListeners(),type,listener,handlerType);
		AHtmlDocument doc = getOwnerDocument();
		if (doc == null) {
			return;
		}
		if(isInDoc(this)){
			IDomEventBindingListener evtBindingListener = doc.getDomEventBindingListener();
			if (evtBindingListener != null) {
				if (evtBindingListener.isEventBinding(this.getElement(), type) && bind) {
					evtBindingListener.eventBound(this.getElement(), type);
				}
			}
		}else{
			collectDeferedElementEvents(this,type);
		}
		
	}
	
	public void removeListener(String type, Object listener, boolean useCapture){
		type = AHtmlHelper.getCorrectType(type);
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

	@Override
	public short compareDocumentPosition(Node other) {
		IBrowserBinding browserBinding = getBrowserBinding();
		if(browserBinding !=null){
			String currentNodePath = AHtmlHelper.getElementPath(this);
			String otherNodePath = AHtmlHelper.getElementPath((AHtmlElement)other);
			String code = MessageFormat.format(COMPARE_DOCUMENT_POSITION, currentNodePath, otherNodePath);
			String retValue = browserBinding.executeJs(code);
			try {
				return Short.parseShort(retValue);
			} catch (Exception e) {
				//Ignore exception
			}
		}
		return 0;
	}

	/**
	 * check whether node is/contains <script>, if yes, evaluate these scripts
	 * @param node
	 */
	private void evalJs(Node node) {
		//process newly created JavaScript - no document.write support
		if (!(node instanceof AHtmlElement)) {
			return;
		}
		AHtmlDocument document = getOwnerDocument();
		if (document == null) {
			return;
		}
		if (!isInDom(node, document.getHtml())) {
			return;
		}
		
		if (node instanceof AHtmlScript) {
			eval((AHtmlScript)node);
			return;
		}

		NodeList scripts = ((AHtmlElement)node).getElementsByTagName("script");
		int size = scripts.getLength();
		for (int i = 0; i < size; i++) {
			AHtmlScript script = (AHtmlScript)scripts.item(i);
			eval(script);
		}
	}
	
	private static final String JS_TYPE = "text/javascript";
	
	private void eval(AHtmlScript script) {
		//check type 1stly to improve performance
		String scriptType = script.getType();
		if (!JS_TYPE.equalsIgnoreCase(scriptType)) {
			return;
		}
		AHtmlDocument document = getOwnerDocument();		
		if(isDynamicScript(script)){
			document.getWindow().handleDynamicScript((DScript)script.getDNode());
		}
		else {			
			Context context = document.getScriptContext();
			if (context == null) {
				return;
			}
			String jsText = script.getText();
			if (jsText != null && jsText.length() != 0) {
				ScriptExecutor.executeScript(jsText, document.getWindow());
			}	
		}				
	}
	
	private static boolean isInDom(Node node, Node root) {
		if (node == null) {
			return false;
		}
		if (node == root) {
			return true;
		}
		return isInDom(node.getParentNode(), root);
	}
	
	private void associateIdElementsToDoc() {
		AHtmlDocument document = getOwnerDocument();
		if (document != null && isInDom(this, document.getHtml())) {
			associateIdElementsToDoc(document);
		}
	}
	
	private void associateIdElementsToDoc(AHtmlDocument document) {
		String idValue = getAttribute("id");
		if (idValue != null && idValue.length() > 0) {
			document.putIdentifier(idValue, this);
		}
		if (!hasChildNodes()) {
			return;
		}
		NodeList children = getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child instanceof AHtmlElement) {
				((AHtmlElement)child).associateIdElementsToDoc(document);
			}
		}
	}
	
	public static class AHtmlSchemaForParser extends AHtmlSchema {
		private static final AHtmlSchemaForParser s_instance = new AHtmlSchemaForParser();
		
		public static AHtmlSchemaForParser getInstance(){
			return s_instance;
		}
		// to allow structures like TR or OPTION to be under body
		@Override
		protected void setupElementTypes() {
			super.setupElementTypes();
			elementType("body", M_PCDATA|M_INLINE|M_BLOCK|M_OPTION|M_TR, M_HTML|M_BODY, 0);
		}
	}
	
	private Node[] getDocumentFragmentNodes(Node newChild){
		if (newChild instanceof ADocumentFragment) {
			ADocumentFragment frag = (ADocumentFragment)newChild;
			NodeList nodes = frag.getChildNodes();
			int size = nodes.getLength();
			if (size > 0) {
				//copy nodes from active list to this non-active array
				Node[] arr = new Node[size];
				for (int i = 0; i < size; i++) {
					arr[i] = nodes.item(i);
				}
				return arr;
			}
		}
		return null;
	}
	
	public HtmlElement getOffsetParent() {
		IBrowserBinding browserBinding = getOwnerDocument().getBrowserBinding();
		String path =  DapDomHelper.getPath(getDNode());
		if(browserBinding!=null && !path.equals(JS_BODY_PATH)){
			String elm = browserBinding.executeJs(MessageFormat.format(JS_OFFSET_PARENT_PATH, path));
			ANode aelm = AHtmlHelper.getElementByPath(getOwnerDocument(), elm);
			if(aelm instanceof HtmlElement){
				return (HtmlElement)aelm;
			}
		}
		return null;
	}
	
	public Object getOnAbort() {
		return getListener(EventType.ABORT.getName());
	}

	public Object getOnBlur() {
		return getListener(EventType.BLUR.getName());
	}

	public Object getOnChange() {
		return getListener(EventType.CHANGE.getName());
	}

	public Object getOnClick() {
		return getListener(EventType.CLICK.getName());
	}

	public Object getOnDblClick() {
		return getListener(EventType.DBLCLICK.getName());
	}

	public Object getOnFocus() {
		return getListener(EventType.FOCUS.getName());
	}

	public Object getOnKeyDown() {
		return getListener(EventType.KEYDOWN.getName());
	}

	public Object getOnKeyPress() {
		return getListener(EventType.KEYPRESS.getName());
	}

	public Object getOnKeyUp() {
		return getListener(EventType.KEYUP.getName());
	}
	
	public Object getOnLoad() {
		return getListener(EventType.LOAD.getName());
	}

	public Object getOnMouseDown() {
		return getListener(EventType.MOUSEDOWN.getName());
	}

	public Object getOnMouseMove() {
		return getListener(EventType.MOUSEMOVE.getName());
	}

	public Object getOnMouseOut() {
		return getListener(EventType.MOUSEOUT.getName());
	}

	public Object getOnMouseOver() {
		return getListener(EventType.MOUSEOVER.getName());
	}

	public Object getOnMouseUp() {
		return getListener(EventType.MOUSEUP.getName());
	}
	
	public Object getOnReadyStateChange() {
		return getListener(EventType.READYSTATECHANGE.getName());
	}
	
	// Since property name is 'onreadystatechange', Rhino invokes this method.
	public Object getOnreadystatechange() {
		return getOnReadyStateChange();
	}

	public Object getOnResize() {
		return getListener(EventType.RESIZE.getName());
	}
	
	public Object getOnReset() {
		return getListener(EventType.RESET.getName());
	}

	public Object getOnScroll() {
		return getListener(EventType.SCROLL.getName());
	}
	
	public Object getOnSelect() {
		return getListener(EventType.SELECT.getName());
	}
	
	public Object getOnSubmit() {
		return getListener(EventType.SUBMIT.getName());
	}
	
	public Object getOnUnload() {
		return getListener(EventType.UNLOAD.getName());
	}
	
	public void setOnAbort(Object functionRef) {
		registerAndBind(EventType.ABORT.getName(), functionRef);
	}
	
	public void setOnBlur(Object functionRef) {
		registerAndBind(EventType.BLUR.getName(), functionRef);
	}
	
	public void setOnChange(Object functionRef) {
		registerAndBind(EventType.CHANGE.getName(), functionRef);
	}
	
	public void setOnClick(Object functionRef) {
		registerAndBind(EventType.CLICK.getName(), functionRef);
	}
	
	public void setOnDblClick(Object functionRef) {
		registerAndBind(EventType.DBLCLICK.getName(), functionRef);
	}
	
	public void setOnFocus(Object functionRef) {
		registerAndBind(EventType.FOCUS.getName(), functionRef);
	}
	
	public void setOnKeyDown(Object functionRef) {
		registerAndBind(EventType.KEYDOWN.getName(), functionRef);
	}
	
	public void setOnKeyPress(Object functionRef) {
		registerAndBind(EventType.KEYPRESS.getName(), functionRef);
	}
	
	public void setOnKeyUp(Object functionRef) {
		registerAndBind(EventType.KEYUP.getName(), functionRef);
	}
	
	public void setOnLoad(Object functionRef) {
		registerAndBind(EventType.LOAD.getName(), functionRef);
	}
	
	public void setOnMouseDown(Object functionRef) {
		registerAndBind(EventType.MOUSEDOWN.getName(), functionRef);
	}
	
	public void setOnMouseMove(Object functionRef) {
		registerAndBind(EventType.MOUSEMOVE.getName(), functionRef);
	}
	
	public void setOnMouseOut(Object functionRef) {
		registerAndBind(EventType.MOUSEOUT.getName(), functionRef);
	}
	
	public void setOnMouseOver(Object functionRef) {
		registerAndBind(EventType.MOUSEOVER.getName(), functionRef);
	}
	
	public void setOnMouseUp(Object functionRef) {
		registerAndBind(EventType.MOUSEUP.getName(), functionRef);
	}
	
	public void setOnReadyStateChange(Object functionRef) {
		registerAndBind(EventType.READYSTATECHANGE.getName(), functionRef);
	}
	
	// For Rhino
	public void setOnreadystatechange(Object functionRef) {
		setOnReadyStateChange(functionRef);
	}

	public void setOnResize(Object functionRef) {
		registerAndBind(EventType.RESIZE.getName(), functionRef);
	}
	
	public void setOnReset(Object functionRef) {
		registerAndBind(EventType.RESET.getName(), functionRef);
	}
	
	public void setOnScroll(Object functionRef) {
		registerAndBind(EventType.SCROLL.getName(), functionRef);
	}
	
	public void setOnSelect(Object functionRef) {
		registerAndBind(EventType.SELECT.getName(), functionRef);
	}
	
	public void setOnSubmit(Object functionRef) {
		registerAndBind(EventType.SUBMIT.getName(), functionRef);
	}
	
	public void setOnUnload(Object functionRef) {
		registerAndBind(EventType.UNLOAD.getName(), functionRef);
	}
	
	protected void registerAndBind(String type, Object handler) {
		addListener(type,handler,false,true,JAVASCRIPT_HANDLER_TYPE.INLINE);
	}
	
	protected Object getListener(String type) {
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
	
	public TextRectangle getBoundingClientRect() {
		if(m_textRectangle==null){
			m_textRectangle = new ATextRectangle((AHtmlDocument)getOwnerDocument(),this,getBrowserBinding());
		}
		return m_textRectangle;
	}

	public TextRectangleList getClientRects() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getClientHeight() {
		return getIntBindingValue(EHtmlAttr.clientHeight);
	}

	public int getClientWidth() {
		return getIntBindingValue(EHtmlAttr.clientWidth);
	}

	public int getOffsetHeight() {
		return getIntBindingValue(EHtmlAttr.offsetHeight);
	}

	public int getOffsetLeft() {
		return getIntBindingValue(EHtmlAttr.offsetLeft);
	}
	
	public int getOffsetTop() {
		return getIntBindingValue(EHtmlAttr.offsetTop);
	}

	public int getOffsetWidth() {
		return getIntBindingValue(EHtmlAttr.offsetWidth);
	}

	public void setOffsetHeight(int hight) {
		setHtmlAttribute(EHtmlAttr.offsetHeight, String.valueOf(hight));
	}

	public void setOffsetLeft(int left) {
		setHtmlAttribute(EHtmlAttr.offsetLeft, String.valueOf(left));
	}

	public void setOffsetTop(int top) {
		setHtmlAttribute(EHtmlAttr.offsetTop, String.valueOf(top));
	}

	public void setOffsetWidth(int width) {
		setHtmlAttribute(EHtmlAttr.offsetWidth, String.valueOf(width));
	}
	
	public int getScrollHeight() {
		return getIntBindingValue(EHtmlAttr.scrollHeight);
	}

	public int getScrollLeft() {
		return getIntBindingValue(EHtmlAttr.scrollLeft);
	}

	public int getScrollTop() {
		return getIntBindingValue(EHtmlAttr.scrollTop);
	}

	public int getScrollWidth() {
		return getIntBindingValue(EHtmlAttr.scrollWidth);
	}

	public void setScrollHeight(int hight) {
		setHtmlAttribute(EHtmlAttr.scrollHeight, String.valueOf(hight));
	}

	public void setScrollLeft(int left) {
		setHtmlAttribute(EHtmlAttr.scrollLeft, String.valueOf(left));
	}

	public void setScrollTop(int top) {
		setHtmlAttribute(EHtmlAttr.scrollTop, String.valueOf(top));
	}

	public void setScrollWidth(int width) {
		setHtmlAttribute(EHtmlAttr.scrollWidth, String.valueOf(width));
	}

	public int getClientLeft() {
		return getIntBindingValue(EHtmlAttr.clientLeft);
	}

	public int getClientTop() {
		return getIntBindingValue(EHtmlAttr.clientTop);
	}
	
	protected double getDouble(String value) {
		try {
			return Double.valueOf(value);	
		} 
		catch (Exception e) {
			return 0;
		}
	}
	
	protected int getInt(String value) {
		try {
			return Integer.valueOf(value);	
		} 
		catch (Exception e) {
			return 0;
		}
	}

	@Override
	public String componentFromPoint(int iCoordX, int iCoordY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doScroll(String sScrollAction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doScroll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAdjacentText(String sWhere) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
//	@Override
//	public void put(String name, Scriptable start, Object value) {
//		if (!name.startsWith("on")) {
//			return;
//		}
//		if (name.length() < 4) {
//			return;
//		}
//		System.out.println();
//		super.put(name, start, value);
//	}
}