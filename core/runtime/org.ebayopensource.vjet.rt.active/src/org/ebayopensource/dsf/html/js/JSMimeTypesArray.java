/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;

public class JSMimeTypesArray implements Scriptable {

	private JSWindow window = null;
	private Context cx = null;
	private Scriptable scope = null;

	private JSMimeTypesArray() {
	}

	/** Creates new JSMimeTypesArray */
	public JSMimeTypesArray(JSWindow window) {
		JSDebug.println("JSMimeTypesArray:JSMimeTypesArray()...");
		this.window = window;
		this.cx = window.getContext();
		this.scope = window.getScope();
	}

	public String getClassName() {
		JSDebug.println("JSMimeTypesArray:getClassName(...)");
		return ("JSMimeTypesArray");
	}

	/**
	 * Defines the "length" property by returning true if name is equal to "length".
	 * Defines no other properties, i.e., returns false for all other names.
	 *
	 * @param name the name of the property
	 * @param start the object where lookup began
	 */
	public boolean has(String name, Scriptable start) {
		JSDebug.println("JSMimeTypesArray:has()... " + name);
		if (name.equals("length")) {
			return (true);
		} else {
			return (false);
		}
	}

	/**
	 * Defines all numeric properties by returning true.
	 *
	 * @param index the index of the property
	 * @param start the object where lookup began
	 */
	public boolean has(int index, Scriptable start) {
		JSDebug.println("JSMimeTypesArray:has()... " + index);
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
		JSDebug.println("JSMimeTypesArray:get()... " + name);
		if (name.equals("length")) {
			return (new Integer(0));
		} else {
			return (Scriptable.NOT_FOUND);
		}
	}

	/**
	 * Get the indexed property.
	 * <p>
	 * Look up the element in the DOM tree, and return an HTMLMimeTypeElement of it
	 * if it exists. If it doesn't exist, return NOT_FOUND.<p>
	 *
	 * @param index the index of the integral property
	 * @param start the object where the lookup began
	 */
	public Object get(int index, Scriptable start) {
		JSDebug.println("JSMimeTypesArray:get()... " + index);
		return (Scriptable.NOT_FOUND);
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
		JSDebug.println("JSMimeTypesArray:getPrototype()...");
		return prototype;
	}

	/**
	 * Set prototype.
	 */
	public void setPrototype(Scriptable prototype) {
		JSDebug.println("JSMimeTypesArray:setPrototype()...");
		this.prototype = prototype;
	}

	/**
	 * Get parent.
	 */
	public Scriptable getParentScope() {
		JSDebug.println("JSMimeTypesArray:getParentScope()...");
		return parent;
	}

	/**
	 * Set parent.
	 */
	public void setParentScope(Scriptable parent) {
		JSDebug.println("JSMimeTypesArray:setPrototype()...");
		this.parent = parent;
	}

	/**
	 * Get properties.
	 *
	 * We return an empty array since we define all properties to be DONTENUM.
	 */
	public Object[] getIds() {
		JSDebug.println("JSMimeTypesArray:getIds()...");
		return new Object[0];
	}

	/**
	 * Default value.
	 *
	 * Use the convenience method from Context that takes care of calling
	 * toString, etc.
	 */
	public Object getDefaultValue(Class typeHint) {
		JSDebug.println("JSMimeTypesArray:getDefaultValue()...");
		return "[object JSMimeTypesArray]";
	}

	/**
	 * instanceof operator.
	 *
	 * We mimick the normal JavaScript instanceof semantics, returning
	 * true if <code>this</code> appears in <code>value</code>'s prototype
	 * chain.
	 */
	public boolean hasInstance(Scriptable value) {
		JSDebug.println("JSMimeTypesArray:hasInstance()...");
		Scriptable proto = value.getPrototype();
		while (proto != null) {
			if (proto.equals(this)) {
				return (true);
			}
		}
		return (false);
	}

	/**
	 * Some private data for this class.
	 */
	private Scriptable prototype = null, parent = null;
}
