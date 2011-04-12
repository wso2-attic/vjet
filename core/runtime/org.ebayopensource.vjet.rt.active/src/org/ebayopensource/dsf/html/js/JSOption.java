/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.html.dom.DOption;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSOption extends JSElement{

//	private JSWindow window = null;
//	private Context cx = null;
//	private Scriptable scope = null;
	private DOption option = null;
	public String name = null;
	public String formId = null;

	private JSOption() {
	}

	/** Creates new JSOptionElement */
	public JSOption(
			JSWindow window){
		super(window, new DOption());
		option = (DOption)super.getDNode();
		
	}
	public JSOption(
		JSWindow window,
		DOption option,
		String name,
		String formId) {
		super(window, option);
//		this.window = window;
//		this.cx = window.getContext();
//		this.scope = window.getScope();
		this.option = option;
		this.name = name;
		this.formId = formId;

		defineProperty(
			"defaultSelected",
			JSOption.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"text",
			JSOption.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"index",
			JSOption.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"disabled",
			JSOption.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"label",
			JSOption.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"selected",
			JSOption.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"value",
			JSOption.class,
			ScriptableObject.DONTENUM);

	}

	public String getClassName() {
		return ("JSOptionElement");
	}

	public boolean getDefaultSelected() {
		return option.getHtmlDefaultSelected();
	}

	public void setDefaultSelected(boolean defaultSelected) {
		option.setHtmlDefaultSelected(defaultSelected);
	}

	public String getText() {
		return option.getHtmlText();
	}

	public void setText(String text) {
		option.setHtmlText(text);
	}

	public int getIndex() {
		return option.getHtmlIndex();
	}

	public void setIndex(int index) {
		option.setHtmlIndex(index);
	}

	public boolean getDisabled() {
		return option.getHtmlDisabled();
	}

	public void setDisabled(boolean disabled) {
		option.setHtmlDisabled(disabled);
	}

	public String getLabel() {
		return option.getHtmlLabel();
	}

	public void setLabel(String label) {
		option.setHtmlLabel(label);
	}

	public boolean getSelected() {
		return option.getHtmlSelected();
	}

	public void setSelected(boolean selected) {
		//option.setSelected(selected);
	}

	public String getValue() {
		String value = null;
		value = option.getHtmlValue();

		if (window.windowState == JSWindow.IN_ANALYSIS)
			value =
				window.getJSListener().doAction(
					JSAction.GET_OPTION_VALUE,
					name,
					value,
					formId);

		return value;
	}

	public void setValue(String value) {
		if (window.windowState == JSWindow.IN_LOADING)
			option.setHtmlValue(value);
		else
			window.getJSListener().doAction(
				JSAction.SET_INPUT_VALUE,
				name,
				value,
				formId);
	}

	public DOption getDNode(){
		return option;
	}
}