/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsArray;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * JavaScript object representing window's navigator object.
 */
public interface Navigator extends IWillBeScriptable {
	
	// Properties
	
	/**
	 * Returns the code name of the browser
	 * @return
	 */
	@Property String getAppCodeName();
	
	/**
	 * Returns the name of the browser
	 * @return
	 */
	@Property String getAppName();
	
	/**
	 * Returns the minor version of the browser
	 * @return
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property String getAppMinorVersion();
	
	/**
	 * Returns the platform and version of the browser
	 * @return
	 */
	@Property String getAppVersion();
	
	/**
	 * Returns a boolean value that specifies whether cookies are enabled in the browser
	 * @return
	 */
	@Property boolean getCookieEnabled();
	
	/**
	 * Returns the current browser language
	 * @return
	 */
	@BrowserSupport({BrowserType.IE_6P, BrowserType.OPERA_7P})
	@Property String getBrowserLanguage();
	
	/**
	 * Array of MIME type objects supported by the web browser.
	 * @return
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_7P, BrowserType.SAFARI_3P})
	@Property MimeType[] getMimeTypes();
	
	/**
	 * Returns the operating system platform
	 * @return
	 */
	@Property String getPlatform();
	
	/**
	 * Returns a reference to all embedded objects in the document
	 * @return
	 */
	@JsArray(Plugin.class)
	@Property PluginArray getPlugins();
	
	/**
	 * Retruns default language of the OS.
	 * @return
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property String getSystemLanguage();
	
	/**
	 * Returns the value of the user-agent header sent by the client to the server
	 * @return
	 */
	@Property String getUserAgent();
	
	/**
	 * Returns the OS' natural language setting
	 * @return
	 */
	@BrowserSupport({BrowserType.IE_6P, BrowserType.OPERA_7P})
	@Property String getUserLanguage();
	
	/**
	 * Default language of the web browser.
	 * @return
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@Property String getLanguage();
	
	/**
	 * Returns the CPU class of the browser's system
	 * @return
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property String getCpuClass();
	
	/**
	 * Returns a Boolean value that specifies whether the system is in offline mode
	 * @return
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property boolean getOnLine();
	
	// Functions
	
	/**
	 * Specifies whether or not the browser has Java enabled
	 * @return
	 */
	@Function boolean javaEnabled();
	
	/**
	 * Specifies whether or not the browser has data tainting enabled
	 * @return
	 */
	@Function boolean taintEnabled();

}
