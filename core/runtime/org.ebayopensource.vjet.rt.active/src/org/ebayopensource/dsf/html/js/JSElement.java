/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.lang.reflect.Method;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.active.event.IDomChangeListener;
import org.ebayopensource.dsf.html.dom.BaseCoreHtmlElement;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.FunctionObject;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSElement extends ScriptableObject {

	protected JSWindow window = null;
	protected Context cx = null;
	protected Scriptable scope = null;
	protected BaseHtmlElement element = null;
//	private Scriptable object = null;

	protected JSElement() {
	}

	/** Creates new JSElementWraper */
	public JSElement(JSWindow window, BaseHtmlElement element) {
		
		window.addWrapper(element, this);
		this.window = window;
		this.cx = window.getContext();
		this.scope = window.getScope();
		this.element = element;
//		this.object =
//			(element == null) ? null : Context.toObject(element, scope);
		String[] functions = { "valueOf", "insertAdjacentHTML" };

		defineProperty(
			"style",
			JSElement.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"children",
			JSElement.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"window",
			JSElement.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"document",
			JSElement.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"parentElement",
			JSElement.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"onclick",
			JSElement.class,
			ScriptableObject.DONTENUM);
		defineFunctionProperties(
			functions,
			JSElement.class,
			ScriptableObject.DONTENUM);

	}

	public String getClassName() {
		return ("JSElementWraper");
	}

	public JSWindow getWindow() {
		return window;
	}

	public JSDocument getDocument() {
		return window.getJSDocument();
	}

	public Object get(java.lang.String name, Scriptable start) {
		Object obj = super.get(name, start);
		if (obj == NOT_FOUND) {
			if (name.equals("id")) {
				String id = element.getAttribute("id");
				if (id.length() > 0)
					obj = id;
			} else if (name.equals("isHomePage")) {
				return getIsHomePage(start);
			} else if (name.equals("setHomePage")) {
				return getSetHomePage(start);
			} else
				obj = window.get(name, start);
		}
		return obj;
	}

	JSStyle style = null;
	public JSStyle getStyle() {
		if (style == null) {
			style = new JSStyle(element, window);
		}
		return style;
	}

	JSElementArray children = null;
	public JSElementArray getChildren() {
		if (children == null) {
			children = new JSElementArray(window);
			Node child = element.getFirstChild();
			while (child != null) {
				if (child instanceof BaseHtmlElement) {
					children.add(window.getWrapper(child, true));
//						new JSElement(window, (BaseHtmlElement) child));
				}
				child = child.getNextSibling();
			}
		}
		return children;
	}
	
	public void appendChild(Node child) {
		element.appendChild(child);
		getChildren().add(new JSElement(window, (BaseHtmlElement) child));
	}
	
	public void removeChild(Node child) {
		element.removeChild(child);
	}
	
	public void insertNext(JSElement node) {
		getParentElement().getChildren().insertAfter(node, this);
		element.getParentNode().insertBefore(node.getDNode(), element.getNextSibling());
		getListener().onInsert(element, node.getDNode(), false);
	}
	
	public void removeSibling(JSElement node) {
//		getListener().onRemove(node.getDNode());
		element.getParentNode().removeChild(node.getDNode());
		getParentElement().getChildren().remove(node);
		// TODO remove wrapper as well
	}
	
	public void removeChild(JSElement child) {
		element.removeChild(child.getDNode());
		// TODO remove wrapper as well
	}

	JSElement parentElement = null;
	public JSElement getParentElement() {
		if (parentElement == null) {
			Node parent = element.getParentNode();
			parentElement = window.getWrapper(parent, true);
		}
		return parentElement;
	}

	Object onclick = null;
	public Scriptable getOnclick() {
		return Context.toObject(onclick, scope);
	}

	public void setOnclick(Object onclick) {
		this.onclick = onclick;
		if (onclick instanceof Function) {
			window.setState(JSWindow.IN_ANALYSIS);
			JSListener listener = window.getJSListener();
			listener.reset();
			try {
				((Function) onclick).call(cx, scope, this, new Object[0]);
			} catch (Exception e) {
				JSDebug.println(e.getMessage());
			}
			JSAction action;
			if (listener.getLength() == 1
				&& (action = listener.get(0)) != null
				&& (action.m_actionType == JSAction.OPEN_WINDOW
					|| action.m_actionType == JSAction.SET_LOCATION)) {
				String href = action.m_value;
				if (href.length() > 0) {
					element.setAttribute(
						"onclick",
						"location.href=\"" + href + "\"");
				}
			}
			listener.reset();
			window.setState(JSWindow.IN_LOADING);
		}
	}

	public String getTagName() {
		return element.getTagName();
	}

	public Object jsFunction_valueOf(String type) {
		return valueOf(type);
	}

	public Object valueOf(String type) {
		if (type.equals("boolean"))
			return Boolean.TRUE;
		else if (type.equals("string")) {
			if (element != null) {
				String id = element.getAttribute("id");
				return id;
			}
			return "";
		} else if (type.equals("object"))
			return this;
		else if (type.equals("number"))
			return "0";

		return null;
	}

	public void insertAdjacentHTML(String name, String value) {
		window.getJSDocument().jsFunction_write(value);
	}

	private static FunctionObject getIsHomePage(Scriptable scope) {

		Method method = null;
		try {
			method =
				JSElement.class.getDeclaredMethod(
					"isHomePage",
					new Class[] { String.class });
		} catch (Exception e) {
		}

		return new FunctionObject("isHomePage", method, scope) {
			public Object call(
				Context cx,
				Scriptable scope,
				Scriptable thisObj,
				Object[] args) {
				return isHomePage((String) args[0])
					? Boolean.TRUE
					: Boolean.FALSE;
			}
		};
	}

	private static FunctionObject getSetHomePage(Scriptable scope) {

		Method method = null;
		try {
			method =
				JSElement.class.getDeclaredMethod(
					"setHomePage",
					new Class[] { String.class });
		} catch (Exception e) {
		}

		return new FunctionObject("setHomePage", method, scope) {
			public Object call(
				Context cx,
				Scriptable scope,
				Scriptable thisObj,
				Object[] args) {
				setHomePage((String) args[0]);
				return null;
			}
		};
	}

	static boolean isHomePage(String url) {
		JSDebug.println("isHomePage: " + url);
		return false;
	}

	static void setHomePage(String url) {
		JSDebug.println("setHomePage: " + url);
	}
	
	public String getId(){
		if (element instanceof BaseCoreHtmlElement){
			return ((BaseCoreHtmlElement)element).getHtmlId();
		}
		return null;
	}
	
	protected IDomChangeListener getListener(){
		return window.getListener();
	}
	
	Node getDNode(){
		return element;
	}
}
