/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

public class JSServerListener implements JSListener {

	private String urlSource = null;
	private JSWindow window = null;

	public JSServerListener(String urlSource, JSWindow window) {

		this.urlSource = new String(urlSource); // for cookie getting
		this.window = window;
	}

	public void reset() {
		//form = null;
	}

	public String doAction(
		int actionType,
		String name,
		String value,
		String formId) {

		switch (actionType) {
			case JSAction.GET_COOKIE :
				//            String cookie = serverSession.getCookieJar().
				//                getCookieStrForClient(urlSource);
				//            if (cookie != null)
				//                value = cookie;
				break;
			case JSAction.SET_COOKIE :
				//            serverSession.getCookieJar().
				//                replaceCookiesForClient(urlSource, value);
				break;
			case JSAction.GET_LOCATION :
				break;
			case JSAction.SET_LOCATION :
			case JSAction.OPEN_WINDOW :
				//            form.setField("_url_", value);
				//            form.setField("_method_", "GET_OPEN"); //no form submit
				break;

			case JSAction.CLOSE_WINDOW :
				//            form.setField("_url_", "dsf_window_close");
				//            form.setField("_method_", "CLOSE"); //close window
				break;

			case JSAction.SET_SUBMIT_ACTION :
				//            String referer = form.getFieldValue("_referer_");
				//            if (referer != null) {
				//                value = URLUtil.getAbsoluteURL(value, referer);
				//            }
				//            form.setField("_url_", value);
				break;
			case JSAction.SUBMIT :
				//            page.setSubmittedFormId(formId);
				break;

			case JSAction.RESET :
				break;

			case JSAction.GET_INPUT_VALUE :
				//            value = form.getFieldValue(name);
				break;

			case JSAction.SET_INPUT_VALUE :
				//            form.setField(name, value);
				break;

			case JSAction.GET_INPUT_CHECKED :
				//            if (form.hasUserInput(name))
				//                value = "true";
				//            else
				//                value = "false";
				break;

			case JSAction.SET_INPUT_CHECKED :
				//            if (value.equals("false"))
				//                form.setField(name, "");
				break;

			case JSAction.GET_SELECTED_INDEX :
				//            String optionValue = form.getFieldValue(name);
				//            if (optionValue != null)
				//                value = window.getSelectedIndex
				//                    (formId, name, optionValue);
				break;
			case JSAction.SET_SELECTED_INDEX :
				break;

			case JSAction.GET_SELECTED_VALUE :
				//            value = form.getFieldValue(name);
				break;

			case JSAction.SET_SELECTED_VALUE :
				break;

			case JSAction.GET_OPTION_VALUE :
				break;

			case JSAction.SET_OPTION_VALUE :
				break;

			case JSAction.HISTORY_BACK :
				//            form.setField("_url_", "dsf_window_close");
				//            form.setField("_method_", "CLOSE"); //go to previous window
				break;

			case JSAction.HISTORY_FORWARD :
				//            form.setField("_url_", "dsf_window_next");
				//            form.setField("_method_", "CLOSE"); //go to next window
				break;

			case JSAction.LOCATION_RELOAD :
				//            form.setField("_url_", "dsf_window_reload");
				//            form.setField("_method_", "CLOSE"); //reload current window
				break;
		}

		new JSAction(actionType, name, value, formId); //just for debug
		return value; //return the same value passed in
	}

	public int getLength() {
		return 0;
	}

	public JSAction get(int index) {
		return null;
	}

	public void cleanUp() {
	}

}
