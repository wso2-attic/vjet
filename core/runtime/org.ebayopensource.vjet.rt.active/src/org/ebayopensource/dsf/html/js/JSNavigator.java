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

public class JSNavigator { //extends ScriptableObject {

	private JSWindow window = null;
	private Context cx = null;
	private Scriptable scope = null;

	public JSNavigator(JSWindow window) {
		this.window = window;
		this.cx = window.getContext();
		this.scope = window.getScope();
	}

	// Following property values are from IE 5.5.

	// Property: appCodeName, R/O ----------------------------------------------
	private String appCodeName = "Mozilla";
	public Scriptable getAppCodeName() {
		return (Context.toObject(appCodeName, scope));
	}

	// Property: appName, R/O ----------------------------------------------
	private String appName = "Microsoft Internet Explorer"; // Netscape
	public Scriptable getAppName() {
		return (Context.toObject(appName, scope));
	}

	// Property: appVersion, R/O ----------------------------------------------
	private String appVersion = "4.0 (compatible; MSIE 5.5; Windows NT 4.0)";
	public Scriptable getAppVersion() {
		return (Context.toObject(appVersion, scope));
	}

	// Property: cookieEnabled, R/O -------------------------------------------------
	private Boolean cookieEnabled = Boolean.TRUE;
	public Scriptable getCookieEnabled() {
		return (Context.toObject(cookieEnabled, scope));
	}

	// Property: language, R/O -------------------------------------------------
	private String language = "undefined";
	public Scriptable getLanguage() {
		return (Context.toObject(language, scope));
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	// Property: mimeTypes[], R/O ----------------------------------------------
	//    private String[] mimeTypes = new String[0];
	private JSMimeTypesArray browserMimeTypesArray = null;
	public Scriptable getMimeTypes() {
		//return (Context.toObject(mimeTypes, scope));
		if (browserMimeTypesArray == null) {
			browserMimeTypesArray = new JSMimeTypesArray(window);
		}
		return (browserMimeTypesArray);
	}

	// Property: platform, R/O ----------------------------------------------
	private String platform = "Win32";
	public Scriptable getPlatform() {
		return (Context.toObject(platform, scope));
	}

	// Property: plugins[], R/O ----------------------------------------------
	//    private String[] plugins = new String[0];
	private JSPluginsArray browserPluginsArray = null;
	public Scriptable getPlugins() {
		//return (Context.toObject(plugins, scope));
		if (browserPluginsArray == null) {
			browserPluginsArray = new JSPluginsArray(window);
		}
		return (browserPluginsArray);
	}

	// Property: systemLanguage, R/O ----------------------------------------------
	private String systemLanguage = "en-us";
	public Scriptable getSystemLanguage() {
		return (Context.toObject(systemLanguage, scope));
	}

	// Property: userAgent, R/O ----------------------------------------------
	private String userAgent =
		"Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 4.0)";
	public Scriptable getUserAgent() {
		return (Context.toObject(userAgent, scope));
	}

	// Property: userLanguage, R/O ----------------------------------------------
	private String userLanguage = "en-us";
	public Scriptable getUserLanguage() {
		return (Context.toObject(userLanguage, scope));
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	// Function: javaEnabled() -------------------------------------------------
	public Scriptable javaEnabled() {
		return (Context.toObject(Boolean.FALSE, scope));
	}

}
