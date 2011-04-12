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
import org.ebayopensource.dsf.html.dom.BaseAttrsHtmlElement;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;

public class JSEmbedArray implements Scriptable {

	private JSWindow window = null;
//	private Context cx = null;
	private Scriptable scope = null;

	private JSEmbedArray() {
	}

	/** Creates new JSEmbedsArray */
	public JSEmbedArray(JSWindow window) {
		JSDebug.println("JSEmbedsArray:JSEmbedsArray()...");
		this.window = window;
//		this.cx = window.getContext();
		this.scope = window.getScope();
	}

	public String getClassName() {
		JSDebug.println("JSEmbedsArray:getClassName(...)");
		return "JSEmbedsArray";
	}

	/**
	 * Defines the "length" property by returning true if name is equal to "length".
	 * Defines no other properties, i.e., returns false for all other names.
	 *
	 * @param name the name of the property
	 * @param start the object where lookup began
	 */
	public boolean has(String name, Scriptable start) {
		JSDebug.println("JSEmbedsArray:has()... " + name);
		if (name.equals("length")) {
			return true;
		}

		NodeList embeds = window.getHTMLDocument().getElementsByTagName("EMBED");
		if (embeds == null) {
			return false;
		}

		DElement embed = null;
		for (int i = 0; i < embeds.getLength(); i++) {
			embed = (DElement) embeds.item(i);
			if (name.equals(embed.getAttribute("NAME"))) {
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
		JSDebug.println("JSEmbedsArray:has()... " + index);
		NodeList embeds = window.getHTMLDocument().getElementsByTagName("EMBED");
		if (embeds == null) {
			return false;
		}

		if (index >= 0 && index < embeds.getLength()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the named property.
	 * Handles the "length" property and returns NOT_FOUND for all other names.
	 *
	 * @param name the property name
	 * @param start the object where the lookup began
	 */
	public Object get(String name, Scriptable start) {
		JSDebug.println("JSEmbedsArray:get()... " + name);
		NodeList embeds = window.getHTMLDocument().getElementsByTagName("EMBED");
		if (name.equals("length")) {
			if (embeds != null) {
				return new Integer(embeds.getLength());
			} else {
				return new Integer(0);
			}
		}

		if (embeds == null) {
			return Scriptable.NOT_FOUND;
		}

		for (int i = 0; i < embeds.getLength(); i++) {
			final BaseAttrsHtmlElement embed =
				(BaseAttrsHtmlElement)embeds.item(i);
			if (name.equals(embed.getAttribute("name"))) {
				return Context.toObject(embed, scope);
			}
		}
		return Scriptable.NOT_FOUND;
	}

	/**
	 * Get the indexed property.
	 * <p>
	 * Look up the element in the DOM tree, and return an HTMLEmbedElement of it
	 * if it exists. If it doesn't exist, return NOT_FOUND.<p>
	 *
	 * @param index the index of the integral property
	 * @param start the object where the lookup began
	 */
	public Object get(int index, Scriptable start) {
		JSDebug.println("JSEmbedsArray:get()... " + index);
		NodeList embeds = window.getHTMLDocument().getElementsByTagName("EMBED");
		if (embeds == null) {
			return Scriptable.NOT_FOUND;
		}

		if (index < 0 || index >= embeds.getLength()) {
			return Scriptable.NOT_FOUND;
		}
		return Context.toObject(embeds.item(index), scope);
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
		JSDebug.println("JSEmbedsArray:getPrototype()...");
		return prototype;
	}

	/**
	 * Set prototype.
	 */
	public void setPrototype(Scriptable prototype) {
		JSDebug.println("JSEmbedsArray:setPrototype()...");
		this.prototype = prototype;
	}

	/**
	 * Get parent.
	 */
	public Scriptable getParentScope() {
		JSDebug.println("JSEmbedsArray:getParentScope()...");
		return parent;
	}

	/**
	 * Set parent.
	 */
	public void setParentScope(Scriptable parent) {
		JSDebug.println("JSEmbedsArray:setPrototype()...");
		this.parent = parent;
	}

	/**
	 * Get properties.
	 *
	 * We return an empty array since we define all properties to be DONTENUM.
	 */
	public Object[] getIds() {
		JSDebug.println("JSEmbedsArray:getIds()...");
		return new Object[0];
	}

	/**
	 * Default value.
	 *
	 * Use the convenience method from Context that takes care of calling
	 * toString, etc.
	 */
	public Object getDefaultValue(Class typeHint) {
		JSDebug.println("JSEmbedsArray:getDefaultValue()...");
		return "[object JSEmbedsArray]";
	}

	/**
	 * instanceof operator.
	 *
	 * We mimick the normal JavaScript instanceof semantics, returning
	 * true if <code>this</code> appears in <code>value</code>'s prototype
	 * chain.
	 */
	public boolean hasInstance(Scriptable value) {
		JSDebug.println("JSEmbedsArray:hasInstance()...");
		Scriptable proto = value.getPrototype();
		while (proto != null) {
			if (proto.equals(this)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Some private data for this class.
	 */
	private Scriptable prototype = null, parent = null;
}
