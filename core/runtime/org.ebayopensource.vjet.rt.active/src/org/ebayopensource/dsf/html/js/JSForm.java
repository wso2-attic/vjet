/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.dom.DForm;
import org.ebayopensource.dsf.html.dom.DHtmlCollection;
import org.ebayopensource.dsf.html.dom.DInput;
import org.ebayopensource.dsf.html.dom.DSelect;
import org.ebayopensource.dsf.html.dom.DTextArea;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSForm extends ScriptableObject {

	private JSWindow window = null;
//	private Context cx = null;
//	private Scriptable scope = null;
	private DForm form = null;
	private JSFormElementArray elements = null;
	public String name = null;
	public String formId = null;

	private JSForm() {
	}

	/** Creates new JSForms */
	public JSForm(JSWindow window, DForm form) {
		this.window = window;
//		this.cx = window.getContext();
//		this.scope = window.getScope();
		this.form = form;
		this.elements = new JSFormElementArray(window, form);
		this.name = form.getHtmlName();
		this.formId = form.getAttribute("form-id");

		String[] functions = new String[2];
		functions[0] = "submit";
		functions[1] = "reset";

		defineFunctionProperties(
			functions,
			JSForm.class,
			ScriptableObject.DONTENUM);

		defineProperty("elements", JSForm.class, ScriptableObject.READONLY);
		defineProperty("action", JSForm.class, ScriptableObject.DONTENUM);
		defineProperty("id", JSForm.class, ScriptableObject.DONTENUM);

		//updateForm();
	}

	public JSForm(JSWindow window, String formId) {
		//fake form
		this.window = window;
//		this.cx = window.getContext();
//		this.scope = window.getScope();
		this.form = null;
		this.elements = new JSFormElementArray(window, form);
		this.name = "fake_form";
		this.formId = formId;
	}

	public String getClassName() {
		return ("JSForm");
	}

	public void submit() {
		JsHackDetectionCtx.ctx().setLocationChange(form.getHtmlAction());
		if (window.windowState == JSWindow.IN_LOADING) {
			//using actor to store the method
			//using value to store the action url
			//using id to store the formParam
			String method = form.getHtmlMethod();
			String url = form.getHtmlAction();
			String params = getFormParams();
			window.getJSListener().doAction(
				JSAction.SUBMIT,
				method,
				url,
				params);
		} else
			window.getJSListener().doAction(
				JSAction.SUBMIT,
				name,
				null,
				formId);
	}

	public void reset() {
		window.getJSListener().doAction(JSAction.RESET, name, null, formId);
	}

	public Scriptable getElements() {
		return elements;
	}

	public int getLength() {
		return form.getHtmlLength();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		form.setHtmlName(name);
		this.name = name;
	}

	public String getAcceptCharset() {
		return form.getHtmlAcceptCharset();
	}

	public void setAcceptCharset(String acceptCharset) {
		form.setHtmlAcceptCharset(acceptCharset);
	}

	public String getAction() {
		return form.getHtmlAction();
	}

	public void setAction(String action) {
		if (window.windowState == JSWindow.IN_LOADING) {
			form.setHtmlAction(action);
		} else
			window.getJSListener().doAction(
				JSAction.SET_SUBMIT_ACTION,
				name,
				action,
				formId);
	}
	
	public String getId() {
		return form.getHtmlId();
	}

	public void setHtmlId(String id) {
		form.setHtmlId(id);
	}

	public String getEnctype() {
		return form.getHtmlEnctype();
	}

	public void setEnctype(String enctype) {
		form.setHtmlEnctype(enctype);
	}

	public String getMethod() {
		return form.getHtmlMethod();
	}

	public void setMethod(String method) {
		form.setHtmlMethod(method);
	}

	public String getTarget() {
		return form.getHtmlTarget();
	}

	public void setTarget(String target) {
		form.setHtmlTarget(target);
	}

	public void addJSFormChildElem(DElement formChildElem) {
		if (formChildElem instanceof DInput) {
			DInput input = (DInput) formChildElem;
			String name = input.getHtmlName();
			defineElement(name);
			elements.add(new JSInput(window, input));
		} else if (formChildElem instanceof DSelect) {
			DSelect select = (DSelect) formChildElem;
			String name = select.getHtmlName();
			defineElement(name);
			elements.add(new JSSelect(window, select));
		} else if (formChildElem instanceof DTextArea) {
			DTextArea textArea =
				(DTextArea) formChildElem;
			String name = textArea.getHtmlName();
			defineElement(name);
			elements.add(new JSTextArea(window, textArea));
		}
	}

	public void defineElement(String name) {
		try {
			Class[] getArg = new Class[1];
			getArg[0] = ScriptableObject.class;
			Class[] setArg = new Class[2];
			setArg[0] = ScriptableObject.class;
			setArg[1] = Object.class;
			defineProperty(
				name,
				this,
				getClass().getDeclaredMethod("getMyField", getArg),
				getClass().getDeclaredMethod("setMyField", setArg),
			//bDoc.getClass().getDeclaredMethod
			ScriptableObject.DONTENUM);
		} catch (NoSuchMethodException propExp) {
		}
	}

	Object getMyField(ScriptableObject obj) {
		JSDebug.println("***getMyField : " + obj.toString());
		return null;
	}

	void setMyField(ScriptableObject obj, Object value) {
		JSDebug.println("***setMyField : " + obj.toString());
	}

	public Object get(java.lang.String name, Scriptable start) {
		Object obj = null;
		if ((obj = elements.findElemObject(name)) != null)
			return obj;
		obj = super.get(name, start);
		if (obj == NOT_FOUND)
			obj = window.get(name, start);
		if (obj == NOT_FOUND)
			obj = window.getDocument().get(name, start);
		return obj;
	}

	public Object findElemObject(String name) {
		return elements.findElemObject(name);
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

	public String getFormParams() {
		StringBuffer params = new StringBuffer();
		DHtmlCollection elems = form.getHtmlElements();
		int size = elems.getLength();
		for (int i = 0; i < size; i++) {
			Object fieldElem = elems.item(i);
			if (fieldElem instanceof DInput) {
				DInput input = (DInput) fieldElem;
				String name = input.getHtmlName();
				String value = input.getHtmlValue();
				if (name != null && value != null) {
					if (params.length() != 0)
						params.append("&");
					params.append(name).append("=").append(value);
				}
			}
		}
		return params.toString();
	}
}
