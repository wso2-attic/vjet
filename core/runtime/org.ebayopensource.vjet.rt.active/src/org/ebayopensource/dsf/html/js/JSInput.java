/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.active.event.IDomChangeListener;
import org.ebayopensource.dsf.html.dom.DInput;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSInput extends JSElement {

//	private JSWindow window = null;
//	private Context cx = null;
//	private Scriptable scope = null;
	private DInput input = null;
	public String name = null;
	public String formId = null;

	private JSInput() {
	}

	/** Creates new JSInputElement */
	public JSInput(JSWindow window, DInput input) {
		super(window, input);
//		this.window = window;
//		this.cx = window.getContext();
//		this.scope = window.getScope();
		this.input = input;
		this.name = input.getHtmlName();
		this.formId = input.getAttribute("form-id");

		String[] functions = new String[5];
		functions[0] = "blur";
		functions[1] = "focus";
		functions[2] = "select";
		functions[3] = "click";
		functions[4] = "valueOf";

		defineFunctionProperties(
			functions,
			JSInput.class,
			ScriptableObject.DONTENUM);

//		defineProperty(
//			"window",
//			JSInputElement.class,
//			ScriptableObject.DONTENUM);
//		defineProperty(
//			"document",
//			JSInputElement.class,
//			ScriptableObject.DONTENUM);
		defineProperty("form", JSInput.class, ScriptableObject.DONTENUM);
		defineProperty(
			"value",
			JSInput.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"defaultValue",
			JSInput.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"defaultChecked",
			JSInput.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"accept",
			JSInput.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"accessKey",
			JSInput.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"align",
			JSInput.class,
			ScriptableObject.DONTENUM);
		defineProperty("alt", JSInput.class, ScriptableObject.DONTENUM);
		defineProperty(
			"checked",
			JSInput.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"disabled",
			JSInput.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"maxLength",
			JSInput.class,
			ScriptableObject.DONTENUM);
		defineProperty("name", JSInput.class, ScriptableObject.DONTENUM);
		defineProperty(
			"readOnly",
			JSInput.class,
			ScriptableObject.DONTENUM);
		defineProperty("size", JSInput.class, ScriptableObject.DONTENUM);
		defineProperty("src", JSInput.class, ScriptableObject.DONTENUM);
		defineProperty(
			"tabIndex",
			JSInput.class,
			ScriptableObject.DONTENUM);
		defineProperty("type", JSInput.class, ScriptableObject.DONTENUM);
		defineProperty(
			"useMap",
			JSInput.class,
			ScriptableObject.DONTENUM);

	}

	public String getClassName() {
		return ("JSInputElement");
	}

	public void blur() {
		JSDebug.println("blur is invoked on " + name);
	}

	public void focus() {
		JSDebug.println("focus is invoked on " + name);
	}

	public void select() {
		JSDebug.println("select is invoked on " + name);
	}

	public void click() {
		JSDebug.println("click is invoked on " + name);
	}

	public JSWindow getWindow() {
		return window;
	}

	public JSDocument getDocument() {
		return window.getJSDocument();
	}

	public Object getForm() {
		return window.findFormElement(formId, null);
	}

	public String getValue() {
		String value = null;
		if (window.windowState != JSWindow.IN_SERVER)
			value = input.getHtmlValue();
		if (window.windowState != JSWindow.IN_LOADING)
			value =
				window.getJSListener().doAction(
					JSAction.GET_INPUT_VALUE,
					name,
					value,
					formId);

		return value;
	}

	public void setValue(String value) {
		
		IDomChangeListener listener = getListener();
		if (listener != null) {
			listener.onValueChange(input, value);
		}
		
		if (window.windowState == JSWindow.IN_LOADING)
			input.setHtmlValue(value);
		else
			window.getJSListener().doAction(
				JSAction.SET_INPUT_VALUE,
				name,
				value,
				formId);
	}

	public String getDefaultValue() {
		return input.getHtmlDefaultValue();
	}

	public void setDefaultValue(String defaultValue) {
		input.setHtmlDefaultValue(defaultValue);
	}

	public boolean getDefaultChecked() {
		return input.getHtmlDefaultChecked();
	}

	public void setDefaultChecked(boolean defaultChecked) {
		input.setHtmlDefaultChecked(defaultChecked);
	}

	public String getAccept() {
		return input.getHtmlAccept();
	}

	public void setAccept(String accept) {
		input.setHtmlAccept(accept);
	}

	public String getAccessKey() {
		return input.getHtmlAccessKey();
	}

	public void setAccessKey(String accessKey) {
		input.setHtmlAccessKey(accessKey);
	}

	public String getAlign() {
		return input.getHtmlAlign();
	}

	public void setAlign(String align) {
		input.setHtmlAlign(align);
	}

	public String getAlt() {
		return input.getHtmlAlt();
	}

	public void setAlt(String alt) {
		input.setHtmlAlt(alt);
	}

	public boolean getChecked() {
		boolean checked = false;
		if (window.windowState != JSWindow.IN_SERVER)
			checked = input.getHtmlChecked();
		if (window.windowState != JSWindow.IN_LOADING)
			checked =
				Boolean
					.valueOf(
						window.getJSListener().doAction(
							JSAction.GET_INPUT_CHECKED,
							name,
							String.valueOf(checked),
							formId))
					.booleanValue();

		return checked;
	}

	public void setChecked(boolean checked) {
		if (window.windowState == JSWindow.IN_LOADING)
			input.setHtmlChecked(checked);
		else
			window.getJSListener().doAction(
				JSAction.SET_INPUT_CHECKED,
				name,
				String.valueOf(checked),
				formId);
	}

	public boolean getDisabled() {
		return input.getHtmlDisabled();
	}

	public void setDisabled(boolean disabled) {
		input.setHtmlDisabled(disabled);
	}

	public int getMaxLength() {
		return input.getHtmlMaxLength();
	}

	public void setMaxLength(int maxLength) {
		input.setHtmlMaxLength(maxLength);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		input.setHtmlName(name);
		this.name = name;
	}

	public boolean getReadOnly() {
		return input.getHtmlReadOnly();
	}

	public void setReadOnly(boolean readOnly) {
		input.setHtmlReadOnly(readOnly);
	}

	public String getSize() {
		return input.getHtmlSize();
	}

	public void setSize(String size) {
		input.setHtmlSize(size);
	}

	public String getSrc() {
		return input.getHtmlSrc();
	}

	public void setSrc(String src) {
		input.setHtmlSrc(src);
	}

	public int getTabIndex() {
		return input.getHtmlTabIndex();
	}

	public void setTabIndex(int tabIndex) {
		input.setHtmlTabIndex(tabIndex);
	}

	public String getType() {
		return input.getHtmlType();
	}

	public String getUseMap() {
		return input.getHtmlUseMap();
	}

	public void setUseMap(String useMap) {
		input.setHtmlUseMap(useMap);
	}

	public Object valueOf(String type) {
		if (type.equals("boolean"))
			return Boolean.TRUE;
		else if (type.equals("string"))
			return getValue();
		else if (type.equals("object"))
			return this;
		else if (type.equals("number"))
			return "0";

		return null;
	}
	
	public DInput getDNode(){
		return input;
	}
}
