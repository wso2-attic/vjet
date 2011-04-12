/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.jsnative.MimeType;
import org.ebayopensource.dsf.jsnative.Navigator;
import org.ebayopensource.dsf.jsnative.PluginArray;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class ANavigator extends ActiveObject implements Navigator {

	private static final long serialVersionUID = 1L;
	private static final String APP_CODE_NAME = "appCodeName";
	private static final String APP_NAME = "appName";
	private static final String APP_VERSION = "appVersion";
	private static final String APP_MINOR_VERSION = "appMinorVersion";
	private static final String COOKIE_ENABLED = "cookieEnabled";
	private static final String ONLINE = "onLine";
	private static final String CPU_CLASS = "cpuClass";
	private static final String MIME_TYPES = "mimeTypes";
	private static final String PLATFORM = "platform";
	private static final String PLUGINS = "plugins";
	public static final String USER_AGENT = "userAgent";
	private static final String BROWSER_LANGUAGE = "browserLanguage";
	private static final String SYSTEM_LANGUAGE = "systemLanguage";
	private static final String USER_LANGUAGE = "userLanguage";
	private static final String LANGUAGE = "language";
	
	private static Map<String, String> s_properties = new HashMap<String, String>(13);
	
	private MimeType[] m_mimeTypes = new MimeType[0];
	private APluginArray m_plugins = new APluginArray();
	private BrowserType m_browserType;
	
	private IBrowserBinding m_browserBinding;
	
    public ANavigator(BrowserType browserType) {
    	m_browserType = browserType;
    	initProperties(browserType);
    	defineProperties(s_properties.keySet(), ANavigator.class);
    }
    
	public String getAppCodeName() {
		return getValue(APP_CODE_NAME, s_properties.get(APP_CODE_NAME));
	}

	public String getAppName() {
		return getValue(APP_NAME, s_properties.get(APP_NAME));
	}

	public String getAppVersion() {
		return getValue(APP_VERSION, s_properties.get(APP_VERSION));
	}

	public boolean getCookieEnabled() {
		return getBooleanValue(COOKIE_ENABLED, true);
	}
	
	public boolean getOnLine() {
		return getBooleanValue(ONLINE, true);
	}

	public String getBrowserLanguage() {
		return getValue(BROWSER_LANGUAGE, s_properties.get(BROWSER_LANGUAGE));
	}

	public MimeType[] getMimeTypes() {
		return m_mimeTypes;
	}

	public String getPlatform() {
		return getValue(PLATFORM, s_properties.get(PLATFORM));
	}

	public PluginArray getPlugins() {
		return m_plugins;
	}

	public String getSystemLanguage() {
		return getValue(SYSTEM_LANGUAGE, s_properties.get(SYSTEM_LANGUAGE));
	}

	public String getUserAgent() {
		return getValue(USER_AGENT, s_properties.get(USER_AGENT));
	}

	public String getUserLanguage() {
		return getValue(USER_LANGUAGE, s_properties.get(USER_LANGUAGE));
	}
	
	public String getLanguage() {
		return getValue(LANGUAGE, s_properties.get(LANGUAGE));
	}

	public String getAppMinorVersion() {
		return getValue(APP_MINOR_VERSION, s_properties.get(APP_MINOR_VERSION));
	}

	public String getCpuClass() {
		return getValue(CPU_CLASS, s_properties.get(CPU_CLASS));
	}
	
//	 quickbug http://quickbugs.arch.ebay.com/show_bug.cgi?id=237 javaEnabled method seems to be duplicate method to getJavaEnabled
	public boolean javaEnabled() {
		return true;
	}

	public boolean taintEnabled() {
		return false;
	}

	public BrowserType getBrowserType() {
		return m_browserType;
	}	
	
	public void setBrowserBinding(IBrowserBinding binding)	{
		m_browserBinding = binding;
	}
	
	//
	// API
	//
	public void addPlugin(APlugin plugin){
		if (plugin != null){
			m_plugins.add(plugin);
		}
	}
	
	/**
	 * Used the following JS to get the navigator object properties for 
	 * different browsers.
	 * <code>
	 * for (var propname in navigator) {
 	 *		document.write ("<p>" + propname + " : " + navigator[propname]);
 	 * }
	 * </code>
	 * @param browserType
	 */
	private void initProperties(BrowserType browserType) {
		if (browserType.isIE()) {
			s_properties.put(APP_CODE_NAME, "Mozilla");
			s_properties.put(APP_NAME, "Microsoft Internet Explorer");
			s_properties.put(APP_MINOR_VERSION, ",SP2;");
			s_properties.put(COOKIE_ENABLED, "true");
			s_properties.put(ONLINE, "true");
			s_properties.put(CPU_CLASS, "x86");
			s_properties.put(MIME_TYPES, "");
			s_properties.put(PLATFORM, "Win32");
			s_properties.put(PLUGINS, "");
			if (browserType.getVersion() == 8) {
				s_properties.put(APP_VERSION, "4.0 (compatible; MSIE 8.0; Windows NT 5.2; WOW64; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)");
				s_properties.put(USER_AGENT, "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; WOW64; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)");
			} else if (browserType.getVersion() == 7) {
				s_properties.put(APP_VERSION, "4.0 (compatible; MSIE 7.0; Windows NT 5.2; WOW64; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)");
				s_properties.put(USER_AGENT, "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.2; WOW64; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)");
			} else {
				s_properties.put(APP_VERSION, "4.0 (compatible; MSIE 6.0; Windows NT 5.2; WOW64; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)");
				s_properties.put(USER_AGENT, "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; WOW64; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)");
			}
			s_properties.put(BROWSER_LANGUAGE, "en-us");
			s_properties.put(SYSTEM_LANGUAGE, "en-us");
			s_properties.put(USER_LANGUAGE, "en-us");
		} else if (browserType.isFireFox()) {
			s_properties.put(APP_CODE_NAME, "Mozilla");
			s_properties.put(APP_NAME, "Netscape");
			s_properties.put(APP_VERSION, "5.0 (Windows; en-US)");
			s_properties.put(COOKIE_ENABLED, "true");
			s_properties.put(MIME_TYPES, "");
			s_properties.put(PLATFORM, "Win32");
			s_properties.put(PLUGINS, "");
			if (browserType.getVersion() == 3) {
				s_properties.put(USER_AGENT, "Mozilla/5.0 (Windows; U; Windows NT 5.2; en-US; rv:1.8.1.14) Gecko/20080404 Firefox/3.0.0.0");
			} else if (browserType.getVersion() == 2) {
				s_properties.put(USER_AGENT, "Mozilla/5.0 (Windows; U; Windows NT 5.2; en-US; rv:1.8.1.14) Gecko/20080404 Firefox/2.0.0.0");
			} else {
				s_properties.put(USER_AGENT, "Mozilla/5.0 (Windows; U; Windows NT 5.2; en-US; rv:1.8.1.14) Gecko/20080404 Firefox/1.0.0.0");
			}
			s_properties.put(LANGUAGE, "en-us");
			
		} else if (browserType.isSafari()) {
			s_properties.put(APP_CODE_NAME, "Mozilla");
			s_properties.put(APP_NAME, "Netscape");
			s_properties.put(APP_VERSION, "5.0 (Windows; U; Windows NT 5.2; en) AppleWebKit/522.15.5 (KHTML, like Gecko) Version/3.0.3 Safari/522.15.5");
			s_properties.put(COOKIE_ENABLED, "true");
			s_properties.put(MIME_TYPES, "");
			s_properties.put(PLATFORM, "Win32");
			s_properties.put(PLUGINS, "");
			s_properties.put(LANGUAGE, "en");
			s_properties.put(USER_AGENT, "Mozilla/5.0 (Windows; U; Windows NT 5.2; en) AppleWebKit/522.15.5 (KHTML, like Gecko) Version/3.0.3 Safari/522.15.5");
		} else if (browserType.isOpera()) {
			s_properties.put(APP_CODE_NAME, "Opera/9.64 (Windows NT 5.2; U; en)");
			s_properties.put(APP_NAME, "Netscape");
			s_properties.put(APP_VERSION, "9.64");
			s_properties.put(COOKIE_ENABLED, "true");
			s_properties.put(MIME_TYPES, "");
			s_properties.put(PLATFORM, "Win32");
			s_properties.put(PLUGINS, "");
			s_properties.put(LANGUAGE, "en");
			s_properties.put(USER_AGENT, "Opera/9.64 (Windows NT 5.2; U; en) Presto/2.1.1");
		}
		else {
			throw new RuntimeException("Unsupported browser type " +  browserType);
		}
	}
	
	private String getValue(String name, String fallback) {
		if (m_browserBinding != null) {
			String value = m_browserBinding.getNavigatorProperty(name);
			if (value != null) {
				return value;
			}
		}		
		return fallback;
	}
	
	private boolean getBooleanValue(String name, boolean fallback) {
		if (m_browserBinding != null) {
			String value = m_browserBinding.getNavigatorProperty(name);
			if (value != null) {
				return Boolean.parseBoolean(value);
			}
		}		
		return fallback;
	}
}
