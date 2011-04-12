/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.html.dom.DTextArea;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSTextArea extends ScriptableObject {

	private JSWindow window = null;
//	private Context cx = null;
//	private Scriptable scope = null;
	private DTextArea textArea = null;
	public String name = null;
	public String formId = null;

	private JSTextArea() {
	}

	/** Creates new JSTextAreaElement */
	public JSTextArea(JSWindow window, DTextArea textArea) {
		this.window = window;
//		this.cx = window.getContext();
//		this.scope = window.getScope();
		this.textArea = textArea;
		this.name = textArea.getHtmlName();
		this.formId = textArea.getAttribute("form-id");

		String[] functions = new String[3];
		functions[0] = "blur";
		functions[1] = "focus";
		functions[2] = "select";

		defineFunctionProperties(
			functions,
			JSTextArea.class,
			ScriptableObject.DONTENUM);

		defineProperty(
			"window",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"document",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"form",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"defaultValue",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"accessKey",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"cols",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"disabled",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"name",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"readOnly",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"rows",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"tabIndex",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"type",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"value",
			JSTextArea.class,
			ScriptableObject.DONTENUM);
	}

	public String getClassName() {
		return ("JSTextAreaElement");
	}

	public void blur() {
		JSDebug.println("blur is invoked on " + name);
	}

	public void focus() {
		JSDebug.println("focus is invoked on " + name);
	}

	public void select() {
		JSDebug.println("select is invoked");
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
			value = textArea.getHtmlValue();

		if (window.windowState != JSWindow.IN_LOADING)
			value =
				window.getJSListener().doAction(
					JSAction.GET_TEXTAREA_VALUE,
					name,
					value,
					formId);
		return value;
	}

//	public void setValue(String value) {
//		if (window.windowState == JSWindow.IN_LOADING)
//			textArea.setHtmlValue(value);
//		else
//			window.getJSListener().doAction(
//				JSAction.SET_TEXTAREA_VALUE,
//				name,
//				value,
//				formId);
//	}

//	public String getDefaultValue() {
//		return textArea.getHtmlExtDefaultValue();
//	}

//	public void setDefaultValue(String defaultValue) {
//		textArea.setHtmlExtDefaultValue(defaultValue);
//	}

	public String getAccessKey() {
		return textArea.getHtmlAccessKey();
	}

	public void setAccessKey(String accessKey) {
		textArea.setHtmlAccessKey(accessKey);
	}

	public int getCols() {
		return textArea.getHtmlCols();
	}

	public void setCols(int cols) {
		textArea.setHtmlCols(cols);
	}

	public boolean getDisabled() {
		return textArea.getHtmlDisabled();
	}

	public void setDisabled(boolean disabled) {
		textArea.setHtmlDisabled(disabled);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		textArea.setHtmlName(name);
		this.name = name;
	}

	public boolean getReadOnly() {
		return textArea.getHtmlReadOnly();
	}

	public void setReadOnly(boolean readOnly) {
		textArea.setHtmlReadOnly(readOnly);
	}

	public int getRows() {
		return textArea.getHtmlRows();
	}

	public void setRows(int rows) {
		textArea.setHtmlRows(rows);
	}

	public int getTabIndex() {
		return textArea.getHtmlTabIndex();
	}

	public void setTabIndex(int tabIndex) {
		textArea.setHtmlTabIndex(tabIndex);
	}

	public String getType() {
		return textArea.getHtmlType();
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

}
