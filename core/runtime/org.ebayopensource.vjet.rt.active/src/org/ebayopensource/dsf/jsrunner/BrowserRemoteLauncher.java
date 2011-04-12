/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsrunner;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class BrowserRemoteLauncher implements IBrowserLauncher {

	private final String m_remoteLauncherUrl;
	public static final String IE = "IE";
	public static final String FIREFOX = "FIREFOX";
	public static final String OPERA = "OPERA";
	public static final String SAFARI = "SAFARI";
	public static final String CHROME = "CHROME";
	
	public BrowserRemoteLauncher(String remoteLauncherUrl) {
		m_remoteLauncherUrl = remoteLauncherUrl;
	}
	
	@Override
	public Process launch(String webUrl, BrowserType type) {
		try {
			String bType = IE;
			if (type.isFireFox()) {
				bType = FIREFOX;
			} else if (type.isOpera()) {
				bType = OPERA;
			} else if (type.isSafari()) {
				bType = SAFARI;
			} else if (type == BrowserType.CHROME_1P) {
				bType = CHROME;
			}
			StringBuilder sb = new StringBuilder(m_remoteLauncherUrl);
			sb.append("?webUrl=").append(URLEncoder.encode(webUrl, "UTF8"))
				.append("&bType=").append(bType);
			URL url = new URL(webUrl);
			URLConnection conn = url.openConnection();
			conn.connect();
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		return null;
	}
}
