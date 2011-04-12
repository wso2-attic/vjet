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

import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.html.dom.DHtmlCollection;
import org.ebayopensource.dsf.html.dom.DOption;
import org.ebayopensource.dsf.html.dom.DSelect;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.NativeObject;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSOptionsArray extends ScriptableObject {

	private JSWindow window = null;
	private JSSelect jsSelect = null;
//	private Context cx = null;
	private Scriptable scope = null;
	private DSelect select = null;
	private String name = null;
	private String formId = null;

	private JSOptionsArray() {
	}

	/** Creates new JSOptionssArray */
	public JSOptionsArray(JSWindow window, JSSelect jsSelect) {
		this.window = window;
		this.jsSelect = jsSelect;
//		this.cx = window.getContext();
		this.scope = window.getScope();
		this.select = jsSelect.getHtmlSelect();
		this.name = select.getHtmlName();
		this.formId = select.getAttribute("form-id");

		defineProperty(
			"selectedIndex",
			JSOptionsArray.class,
			ScriptableObject.DONTENUM);

		updateOptions();
	}

	public String getClassName() {
		return "JSOptionssArray";
	}

	public boolean has(int index, Scriptable start) {
		DHtmlCollection options = select.getHtmlOptions();
		if (options == null) {
			return false;
		}

		if (index >= 0 && index < options.getLength()) {
			return true;
		} else {
			return false;
		}
	}

	public Object get(int index, Scriptable start) {
		if (index < 0)
			return (Scriptable.NOT_FOUND);
		if (window.windowState == JSWindow.IN_LOADING) {
			NodeList options = select.getElementsByTagName(HtmlTypeEnum.OPTION);
			if (options == null || index >= options.getLength()) {
				return Scriptable.NOT_FOUND;
			}
			return Context.toObject(options.item(index), scope);
		} else {
			if (options == null || index >= options.size()) {
				return Scriptable.NOT_FOUND;
			}
			return Context.toObject(options.get(index), scope);
		}
	}

	public void put(int index, Scriptable start, Object obj) {
		if (index < 0)
			return;
		if (window.windowState == JSWindow.IN_LOADING) {
			NativeObject nativeOptionObj = (NativeObject) obj;
			String text = nativeOptionObj.get("text", start).toString();

			DOption option = null;
			NodeList options = select.getElementsByTagName(HtmlTypeEnum.OPTION);
			if (options == null || index >= options.getLength()) {
				option = new DOption();
				select.add(option);
			} else
				option = (DOption) options.item(index);

			option.setHtmlText(text);
		}
	}

	public int getSelectedIndex() {
		return jsSelect.getSelectedIndex();
	}

	public void setSelectedIndex(int selectedIndex) {
		jsSelect.setSelectedIndex(selectedIndex);
	}

	private Vector options = new Vector();
	private void updateOptions() {
		DHtmlCollection domOptions = select.getHtmlOptions();
		int numOptions = 0;
		if (domOptions == null || (numOptions = domOptions.getLength()) == 0)
			return;

		options.removeAllElements();
		for (int i = 0; i < numOptions; i++) {
			options.add(
				new JSOption(
					window,
					(DOption) domOptions.item(i),
					name,
					formId));
		}
	}

	public JSOption getOption(int index) {
		if (index >= 0 && index < options.size())
			return (JSOption) options.get(index);

		return null;
	}

	public int numOptions() {
		return options.size();
	}

	public int getIndex(String optionValue) {
		optionValue = optionValue.trim();
		int size = options.size();
		for (int index = 0; index < size; index++) {
			JSOption option = (JSOption) options.get(index);
			String value = option.getValue().trim();
			if (value.length() == 0 && optionValue.length() != 0)
				value = option.getText().trim();
			if (value.equals(optionValue))
				return index;
		}
		return 0;
	}
	
	public void add(JSOption option){
		select.add(option.getDNode());
		options.add(option);
	}
	
	public void removeAll(){
		DHtmlCollection domOptions = select.getHtmlOptions();
		if (domOptions == null || domOptions.getLength() == 0)
			return;
		
		for (int i = domOptions.getLength()-1; i >=0; i--) {
			select.removeChild(domOptions.item(i));
		}
		options.removeAllElements();
	}
}
