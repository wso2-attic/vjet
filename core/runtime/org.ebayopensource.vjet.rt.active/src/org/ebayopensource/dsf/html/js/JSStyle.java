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
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSStyle extends ScriptableObject {

	private JSWindow window = null;
//	private Context cx = null;
//	private Scriptable scope = null;
	private BaseHtmlElement element = null;
	
	private int m_width;
	private int m_height;
	private String m_className;

	private JSStyle() {
	}

	/** Creates new JSElementWraper */
	public JSStyle(BaseHtmlElement element, JSWindow window) {
		this.window = window;
//		this.cx = window.getContext();
//		this.scope = window.getScope();
		this.element = element;

		String[] functions = { "valueOf" };

		defineFunctionProperties(
			functions,
			JSStyle.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"window",
			JSStyle.class,
			ScriptableObject.DONTENUM);
		defineProperty(
			"visibility",
			JSStyle.class,
			ScriptableObject.DONTENUM);

	}

	public String getClassName() {
		return ("JSStyleWraper");
	}

	public JSWindow getWindow() {
		return window;
	}

	String visibility = "visibile";
	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String value) {
		if (value.equals("hidden"))
			element.setAttribute(EHtmlAttr.style.getAttributeName(), "visibility:hidden");
		else if (value.equals("visible") || value.equals("inherit")) {
			if (window.windowState == JSWindow.IN_ANALYSIS) {
				String id = element.getAttribute("id");
				if (id.length() > 0)
					window.getJSListener().doAction(
						JSAction.SET_VISIBILITY,
						id,
						"visibile",
						null);
			}
		}
	}

	public Object valueOf(String type) {
		if (type.equals("boolean"))
			return Boolean.TRUE;
		else if (type.equals("string")) {
			if (element != null) {
				String id = element.getAttribute("id");
				return id;
			}
			return "";
		} else if (type.equals("object"))
			return this;
		else if (type.equals("number"))
			return "0";

		return null;
	}

	public int getHeight() {
		return m_height;
	}

	public void setHeight(int height) {
		m_height = height;
		window.getListener().onHeightChange(element, height);
	}

	public int getWidth() {
		return m_width;
	}

	public void setWidth(int width) {
		m_width = width;
		window.getListener().onWidthChange(element, width);
	}
	
	public String getClzName(){
		return m_className;
	}
	
	public void setClzName(String clzName){
		m_className = clzName;
		window.getListener().onClassNameChange(element, clzName);
	}
}
