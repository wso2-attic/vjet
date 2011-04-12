/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.html.dom.DApplet;
import org.ebayopensource.dsf.html.dom.DHtmlCollection;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;

public class JSAppletsArray implements Scriptable {

	private JSWindow m_window = null;
	private Context m_cx = null;
	private Scriptable m_scope = null;
	private Scriptable m_prototype = null;
	private Scriptable m_parent = null;

	private JSAppletsArray() {
	}

	/** Creates new JSAppletsArray */
	public JSAppletsArray(JSWindow window) {
		JSDebug.println("JSAppletsArray:JSAppletsArray()...");
		this.m_window = window;
		this.m_cx = window.getContext();
		this.m_scope = window.getScope();
	}

	public String getClassName() {
		JSDebug.println("JSAppletsArray:getClassName(...)");
		return "JSAppletsArray";
	}

	/**
	 * Defines the "length" property by returning true if name is equal to "length".
	 * Defines no other properties, i.e., returns false for all other names.
	 *
	 * @param name the name of the property
	 * @param start the object where lookup began
	 */
	public boolean has(String name, Scriptable start) {
		JSDebug.println("JSAppletsArray:has()... " + name);
		if (name.equals("length")) {
			return true;
		}

		DHtmlCollection applets = m_window.getHTMLDocument().getApplets();
		if (applets == null) {
			return false;
		}

		DApplet applet = null;
		for (int i = 0; i < applets.getLength(); i++) {
			applet = (DApplet) applets.item(i);
			if (name.equals(applet.getHtmlName())) {
				return true;
			}
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
		JSDebug.println("JSAppletsArray:has()... " + index);
		DHtmlCollection applets = m_window.getHTMLDocument().getApplets();
		if (applets == null) {
			return false;
		}

		return (index >= 0 && index < applets.getLength());
	}

	/**
	 * Get the named property.
	 * Handles the "length" property and returns NOT_FOUND for all other names.
	 *
	 * @param name the property name
	 * @param start the object where the lookup began
	 */
	public Object get(String name, Scriptable start) {
		JSDebug.println("JSAppletsArray:get()... " + name);
		DHtmlCollection applets = m_window.getHTMLDocument().getApplets();
		if (name.equals("length")) {
			if (applets != null) {
				return new Integer(applets.getLength());
			} else {
				return new Integer(0);
			}
		}

		if (applets == null) {
			return Scriptable.NOT_FOUND;
		}

		for (int i = 0; i < applets.getLength(); i++) {
			final DApplet applet = (DApplet) applets.item(i);
			if (name.equals(applet.getHtmlName())) {
				return Context.toObject(applet, m_scope);
			}
		}
		return Scriptable.NOT_FOUND;
	}

	/**
	 * Get the indexed property.
	 * <p>
	 * Look up the element in the DOM tree, and return an HTMLAppletElement of it
	 * if it exists. If it doesn't exist, return NOT_FOUND.<p>
	 *
	 * @param index the index of the integral property
	 * @param start the object where the lookup began
	 */
	public Object get(int index, Scriptable start) {
		JSDebug.println("JSAppletsArray:get()... " + index);
		DHtmlCollection applets = m_window.getHTMLDocument().getApplets();
		if (applets == null) {
			return Scriptable.NOT_FOUND;
		}

		if (index < 0 || index >= applets.getLength()) {
			return Scriptable.NOT_FOUND;
		}
		return Context.toObject(applets.item(index), m_scope);
	}

	/**
	 * Set a named property.
	 *
	 * We do nothing here, so all properties are effectively read-only.
	 */
	public void put(String name, Scriptable start, Object value) {
	}

	/**
	 * Set an indexed property.
	 *
	 * We do nothing here, so all properties are effectively read-only.
	 */
	public void put(int index, Scriptable start, Object value) {
	}

	/**
	 * Remove a named property.
	 *
	 * This method shouldn't even be called since we define all properties
	 * as PERMANENT.
	 */
	public void delete(String id) {
	}

	/**
	 * Remove an indexed property.
	 *
	 * This method shouldn't even be called since we define all properties
	 * as PERMANENT.
	 */
	public void delete(int index) {
	}

	/**
	 * Get prototype.
	 */
	public Scriptable getPrototype() {
		JSDebug.println("JSAppletsArray:getPrototype()...");
		return m_prototype;
	}

	/**
	 * Set prototype.
	 */
	public void setPrototype(Scriptable prototype) {
		JSDebug.println("JSAppletsArray:setPrototype()...");
		this.m_prototype = prototype;
	}

	/**
	 * Get parent.
	 */
	public Scriptable getParentScope() {
		JSDebug.println("JSAppletsArray:getParentScope()...");
		return m_parent;
	}

	/**
	 * Set parent.
	 */
	public void setParentScope(Scriptable parent) {
		JSDebug.println("JSAppletsArray:setPrototype()...");
		this.m_parent = parent;
	}

	/**
	 * Get properties.
	 *
	 * We return an empty array since we define all properties to be DONTENUM.
	 */
	public Object[] getIds() {
		JSDebug.println("JSAppletsArray:getIds()...");
		return new Object[0];
	}

	/**
	 * Default value.
	 *
	 * Use the convenience method from Context that takes care of calling
	 * toString, etc.
	 */
	public Object getDefaultValue(Class typeHint) {
		JSDebug.println("JSAppletsArray:getDefaultValue()...");
		return "[object JSAppletsArray]";
	}

	/**
	 * instanceof operator.
	 *
	 * We mimick the normal JavaScript instanceof semantics, returning
	 * true if <code>this</code> appears in <code>value</code>'s prototype
	 * chain.
	 */
	public boolean hasInstance(Scriptable value) {
		JSDebug.println("JSAppletsArray:hasInstance()...");
		Scriptable proto = value.getPrototype();
		while (proto != null) {
			if (proto.equals(this)) {
				return true;
			}
		}
		return false;
	}
}
