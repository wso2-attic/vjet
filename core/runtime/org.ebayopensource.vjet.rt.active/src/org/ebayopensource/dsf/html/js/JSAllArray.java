/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.FunctionObject;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSAllArray extends ScriptableObject {

	private JSWindow m_window = null;
	private Context m_context = null;
	private Scriptable m_scope = null;

	private JSAllArray() {
	}

	/** Creates new JSAllArray */
	public JSAllArray(JSWindow window) {
		JSDebug.println("JSAllArray:JSAllArray()...");
		this.m_window = window;
		this.m_context = window.getContext();
		this.m_scope = window.getScope();
	}

	public String getClassName() {
		JSDebug.println("JSAllArray:getClassName(...)");
		return ("JSAllArray");
	}

	/**
	 * Defines the "length" property by returning true if name is equal to "length".
	 * Defines no other properties, i.e., returns false for all other names.
	 *
	 * @param name the name of the property
	 * @param start the object where lookup began
	 */
	public boolean has(String name, Scriptable start) {
		JSDebug.println("JSAllArray:has... " + name);
		if (name.equals("length")) {
			return true;
		}
		// for names
		NodeList elements =
			m_window.getHTMLDocument().getElementsByTagName(name);
		if (elements != null && elements.getLength() > 0) {
			return true;
		}
		// for ids
		DElement element =
			(DElement)m_window.getHTMLDocument().getElementById(name);
		if (element != null) {
			return true;
		}
		return false;
	}

	/**
	 * Defines all numeric properties by returning true.
	 *
	 * @param index the index of the property
	 * @param start the object where lookup began
	 */
	public boolean has(int index, Scriptable start) {
		JSDebug.println("JSAllArray:has... " + index);
		final NodeList elements = m_window.getHTMLDocument().getElementsByTagName("*");
		if (elements != null && index >= 0 && index < elements.getLength()) {
			return (true);
		}
		return (false);
	}

	/**
	 * Get the named property.
	 * Handles the "length" property and returns NOT_FOUND for all other names.
	 *
	 * @param name the property name
	 * @param start the object where the lookup began
	 */
	public Object get(String name, Scriptable start) {
		JSDebug.println("JSAllArray:get... " + name);
		NodeList elements = null;
		if (name.equals("length")) {
			elements = m_window.getHTMLDocument().getElementsByTagName("*");
			if (elements != null) {
				return (new Integer(elements.getLength()));
			} else {
				return (new Integer(0));
			}
		} else if (name.equals("Function")) {
			return NOT_FOUND;
		} else if (name.equals("item")) {
			try {
				Class thisClass = this.getClass();
				Class[] parameterTypes = { java.lang.Object.class };
				java.lang.reflect.Method doItemMethod =
					thisClass.getMethod("doItem", parameterTypes);
				return (new FunctionObject("doItem", doItemMethod, this));
			} catch (Exception ex) {
			}
		} else if (name.equals("tags")) {
			try {
				Class thisClass = this.getClass();
				Class[] parameterTypes = { java.lang.Object.class };
				java.lang.reflect.Method doTagsMethod =
					thisClass.getMethod("doTags", parameterTypes);
				return (new FunctionObject("doTags", doTagsMethod, this));
			} catch (Exception ex) {
			}
		}

		Object obj = super.get(name, start);
		if (obj != NOT_FOUND)
		{
			return obj;
		}
			

		DElement element =
			(DElement)m_window.getHTMLDocument().getElementById(name);
		if (element != null) {
			JSElement jsElement =
				new JSElement(m_window, (BaseHtmlElement) element);
			super.put(name, start, jsElement);
			return jsElement;
		}

		elements = m_window.getHTMLDocument().getElementsByTagName(name);
		if (elements == null)
			{return (Scriptable.NOT_FOUND);} 

		int length = elements.getLength();

		BaseHtmlElement[] htmlElements = new BaseHtmlElement[length];
		for (int i = 0; i < length; i++) {
			htmlElements[i] = (BaseHtmlElement) elements.item(i);
		}

		if (length > 1) {
			return (m_context.newArray(m_scope, htmlElements));
		} else {
			return (Scriptable.NOT_FOUND);
		}
	}

	/**
	 * Get the indexed property.
	 * <p>
	 * Look up the element in the DOM tree, and return an JSImage of it
	 * if it exists. If it doesn't exist, return NOT_FOUND.<p>
	 *
	 * @param index the index of the integral property
	 * @param start the object where the lookup began
	 */
	public Object get(int index, Scriptable start) {
		JSDebug.println("JSAllArray:get... " + index);
		NodeList elements = m_window.getHTMLDocument().getElementsByTagName("*");
		if (elements == null || index < 0 || index >= elements.getLength()) {
			return (Scriptable.NOT_FOUND);
		}
		return (Context.toObject(elements.item(index),  m_scope));
	}

	/**
	 * Get properties.
	 *
	 * We return an empty array since we define all properties to be DONTENUM.
	 */
	public Object[] getIds() {
		JSDebug.println("JSAllArray:getIds...");
		return new Object[0];
	}

	/**
	 * Default value.
	 *
	 * Use the convenience method from Context that takes care of calling
	 * toString, etc.
	 */
	public Object getDefaultValue(Class typeHint) {
		JSDebug.println("JSAllArray:getDefaultValue(Class)..." + typeHint);
		if (typeHint.equals(java.lang.Boolean.class))
			{return Boolean.TRUE;}
		else if (typeHint.equals(java.lang.String.class)) {
			return ("[object JSAllArray]");
		}
		return Scriptable.NOT_FOUND;
	}

	/**
	 * instanceof operator.
	 *
	 * We mimick the normal JavaScript instanceof semantics, returning
	 * true if <code>this</code> appears in <code>value</code>'s prototype
	 * chain.
	 */
	public boolean hasInstance(Scriptable value) {
		JSDebug.println("JSAllArray:hasInstance(Scriptable)...");
		Scriptable proto = value.getPrototype();
		while (proto != null) {
			if (proto.equals(this)) {
				return (true);
			}
		}
		return (false);
	}

	public Object doItem(Object name) {
		JSDebug.println("JSAllArray:doItem(Object)..." + name);
		return (get(name.toString(), this));
	}

	public Object doTags(Object name) {
		JSDebug.println("JSAllArray:doTags(Object)..." + name);
		NodeList elements =
			m_window.getHTMLDocument().getElementsByTagName(name.toString());
		if (elements == null) {
			return (m_context.newArray(m_scope, 0));
		}
		int length = elements.getLength();
		BaseHtmlElement[] htmlElements = new BaseHtmlElement[length];
		for (int i = 0; i < length; i++) {
			htmlElements[i] = (BaseHtmlElement) elements.item(i);
		}
		return (m_context.newArray(m_scope, htmlElements));
	}

	public Object findObjById(String name) {
		Object obj = super.get(name, this);
		if (obj == Scriptable.NOT_FOUND)
		{
			return null;
		}
			
		return obj;
	}
}
