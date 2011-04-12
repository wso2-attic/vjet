/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.DSelect;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSSelect extends JSElement {

//	private JSWindow window = null;
//	private JSDocument document = null;
//	private Context cx = null;
//	private Scriptable scope = null;
	private DSelect select = null;
	private JSOptionsArray options = null;
	public String name = null;
	public String formId = null;

	private JSSelect() {
	}

	/** Creates new JSSelectElement */
	public JSSelect(JSWindow window, DSelect select) {
		super(window, select);
		this.window = window;
//		this.document = window.getJSDocument();
//		this.cx = window.getContext();
//		this.scope = window.getScope();
		this.select = select;
		this.name = select.getHtmlName();
		this.formId = select.getAttribute("form-id");
		this.options = new JSOptionsArray(window, this);

		String[] functions = new String[4];
		functions[0] = "add";
		functions[1] = "remove";
		functions[2] = "blur";
		functions[3] = "focus";

		//defineFunctionProperties
		//    (functions,
		//     JSSelectElement.class,
		//     ScriptableObject.DONTENUM);
//		defineProperty(
//			"window",
//			JSSelect.class,
//			ScriptableObject.DONTENUM);
//		defineProperty(
//			"document",
//			JSSelect.class,
//			ScriptableObject.DONTENUM);
//		defineProperty(
//			"form",
//			JSSelect.class,
//			ScriptableObject.DONTENUM);
//
//		defineProperty(
//			"type",
//			JSSelect.class,
//			ScriptableObject.DONTENUM);
//		defineProperty(
//			"selectedIndex",
//			JSSelect.class,
//			ScriptableObject.DONTENUM);
		defineProperty(
			"value",
			JSSelect.class,
			ScriptableObject.DONTENUM);
//		defineProperty(
//			"options",
//			JSSelect.class,
//			ScriptableObject.READONLY);
//		defineProperty(
//			"disabled",
//			JSSelect.class,
//			ScriptableObject.DONTENUM);
//		defineProperty(
//			"multiple",
//			JSSelect.class,
//			ScriptableObject.DONTENUM);
//		defineProperty(
//			"name",
//			JSSelect.class,
//			ScriptableObject.DONTENUM);
//		defineProperty(
//			"size",
//			JSSelect.class,
//			ScriptableObject.DONTENUM);
		//defineProperty("tabindex", JSSelectElement.class, ScriptableObject.DONTENUM);            

	}

	public String getClassName() {
		return ("JSSelectElement");
	}

	public JSWindow getWindow() {
		return window;
	}

//	public JSDocument getDocument() {
//		return document;
//	}

	public Object getForm() {
		return window.findFormElement(formId, null);
	}

	public void add(BaseHtmlElement element, BaseHtmlElement before) {
		select.htmlAdd(element, before);
	}

	public void remove(int index) {
		select.htmlRemove(index);
	}

	public void blur() {
		JSDebug.println("blur is invoked on " + name);
	}

	public void focus() {
		JSDebug.println("focus is invoked on " + name);
	}

	public String getType() {
		return select.getHtmlType();
	}

	public int getSelectedIndex() {
		int selectedIndex = 0;
		if (window.windowState != JSWindow.IN_SERVER)
			selectedIndex = select.getHtmlSelectedIndex();

		if (window.windowState != JSWindow.IN_LOADING) {
			String index =
				window.getJSListener().doAction(
					JSAction.GET_SELECTED_INDEX,
					name,
					String.valueOf(selectedIndex),
					formId);
			if (index != null && index.length() > 0) {
				try {
					selectedIndex = Integer.parseInt(index);
				} catch (NumberFormatException e) {
				}
			}
		}
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		if (window.windowState != JSWindow.IN_LOADING)
			window.getJSListener().doAction(
				JSAction.SET_SELECTED_INDEX,
				name,
				String.valueOf(selectedIndex),
				formId);

		select.setHtmlSelectedIndex(selectedIndex);
	}

	public String getValue() {
		if (window.windowState == JSWindow.IN_LOADING){
			return String.valueOf(select.getHtmlSelectedIndex());
		}
		else {
			String value = null;
			JSOption option =
				options.getOption(select.getHtmlSelectedIndex());
			value = option.getValue();
			if (value.length() == 0)
				value = option.getText();

			return window.getJSListener().doAction(
				JSAction.GET_SELECTED_VALUE,
				name,
				value,
				formId);
		}
	}

	public void setValue(String value) {
		select.setHtmlValue(value);
	}

	public Scriptable getOptions() {
		return options;
	}

	public int getLength() {
		return select.getHtmlLength();
	}

	public boolean getDisabled() {
		return select.getHtmlDisabled();
	}

	public void setDisabled(boolean disabled) {
		select.setHtmlDisabled(disabled);
	}

	public boolean getMultiple() {
		return select.getHtmlMultiple();
	}

	public void setMultiple(boolean multiple) {
		select.setHtmlMultiple(multiple);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		select.setHtmlName(name);
		this.name = name;
	}

	public int getSize() {
		return select.getHtmlSize();
	}

	public void setSize(int size) {
		select.setHtmlSize(size);
	}

	public int getTabIndex() {
		return select.getHtmlTabIndex();
	}

	public void setTabIndex(int tabIndex) {
		select.setHtmlTabIndex(tabIndex);
	}

	public Object get(int index, Scriptable start) {
		return options.get(index, start);
	}

	public Object get(java.lang.String name, Scriptable start) {
		Object obj = super.get(name, start);
		if (obj == NOT_FOUND)
			obj = window.get(name, start);

		return obj;
	}

	/**
	public void put(java.lang.String name,
	                Scriptable start,
	                java.lang.Object value) {
	    if (has(name, start))
	        super.put(name, start, value);
	    else
	        window.put(name, start, value);
	}**/

	public int getOptionIndex(String optionValue) {
		return options.getIndex(optionValue);
	}

	public Object valueOf(String type) {
		if (type.equals("boolean"))
			return Boolean.TRUE;
		else if (type.equals("string"))
			return name;
		else if (type.equals("object"))
			return this;
		else if (type.equals("number"))
			return "0";

		return null;
	}

	public DSelect getHtmlSelect() {
		return select;
	}
	
	public void addOption(JSOption option){
		select.add(option.getDNode());
		options.add(option);
		getListener().onElementChange(select);
	}
	
	public void removeAllOptions(){
		options.removeAll();
		getListener().onElementChange(select);
	}
}