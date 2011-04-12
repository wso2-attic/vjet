/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.util.Vector;

import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.dom.DForm;
import org.ebayopensource.dsf.html.dom.DHtmlCollection;
import org.ebayopensource.dsf.html.dom.DInput;
import org.ebayopensource.dsf.html.dom.DSelect;
import org.ebayopensource.dsf.html.dom.DTextArea;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSFormElementArray extends ScriptableObject {

	private JSWindow window = null;
	private DForm form = null;
	private Context cx = null;
	private Scriptable scope = null;
	private Vector elements = new Vector();

	private JSFormElementArray() {
	}

	/** Creates new JSFormElementArray */
	public JSFormElementArray(JSWindow window, DForm form) {
		this.window = window;
		this.form = form;
		this.cx = window.getContext();
		this.scope = window.getScope();
	}

	public String getClassName() {
		return "JSFormElementArray";
	}

	/**
	 * Defines the "length" property by returning true if name is equal to "length".
	 * Defines no other properties, i.e., returns false for all other names.
	 *
	 * @param name the name of the property
	 * @param start the object where lookup began
	 */
	public boolean has(String name, Scriptable start) {
		if (name.equals("length")) {
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
		DHtmlCollection elems = form.getHtmlElements();
		if (elems == null) {
			return false;
		}

		if (index >= 0 && index < elems.getLength()) {
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
		if (window.windowState == JSWindow.IN_LOADING) {
			DHtmlCollection elems = form.getHtmlElements();
			if (name.equals("length")) {
				if (elems != null)
					return (new Integer(elems.getLength()));
				else
					return (new Integer(0));
			}

			if (elems == null)
				return (Scriptable.NOT_FOUND);

		} else {
			Object object = findElemObject(name);
			if (object != null)
				return object;
		}
		return (Scriptable.NOT_FOUND);
	}

	/**
	 * Get the indexed property.
	 * <p>
	 * Look up the element in the DOM tree, and return an Object of it
	 * if it exists. If it doesn't exist, return NOT_FOUND.<p>
	 *
	 * @param index the index of the integral property
	 * @param start the object where the lookup began
	 */
	public Object get(int index, Scriptable start) {
		if (window.windowState == JSWindow.IN_LOADING) {
			DHtmlCollection elems = form.getHtmlElements();

			if (elems != null && index >= 0 && index < elems.getLength())
				return Context.toObject(elems.item(index), scope);
		} else {
			if (index >= 0 && index < elements.size())
				return Context.toObject(elements.get(index), scope);
		}
		return Scriptable.NOT_FOUND;
	}

	public void add(Object element) {
		elements.add(element);
	}

	public Object findElemObject(String name) {
		int numElems = elements.size();
		for (int i = 0; i < numElems; i++) {
			Object elem = elements.get(i);
			if (elem instanceof JSInput) {
				if (((JSInput) elem).name.equals(name))
					return elem;
			} else if (elem instanceof JSSelect) {
				if (((JSSelect) elem).name.equals(name))
					return elem;
			} else if (elem instanceof JSTextArea) {
				if (((JSTextArea) elem).name.equals(name))
					return elem;
			}
		}
		if (window.windowState == JSWindow.IN_LOADING) {
			DHtmlCollection elems = form.getHtmlElements();
			numElems = elems.getLength();
			for (int i = 0; i < numElems; i++) {
				DElement elem = (DElement) elems.item(i);
				if (elem instanceof DInput
					&& ((DInput) elem).getHtmlName().equals(name))
					return new JSInput(window, (DInput) elem);
				else if (
					elem instanceof DSelect
						&& ((DSelect) elem).getHtmlName().equals(name))
					return new JSSelect(
						window,
						(DSelect) elem);
				else if (
					elem instanceof DTextArea
						&& ((DTextArea) elem).getHtmlName().equals(name))
					return new JSTextArea(
						window,
						(DTextArea) elem);
			}
		}

		return null;
	}
}
