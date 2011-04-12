/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.util;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jst.IJstAnnotation;
//import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * Utility class to help with browser type information in JstAnnotation
 *
 */
public class SupportedBrowser {
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for
	 * IE 6 and above
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isIE6Plus(IJstAnnotation annot) {
		return isSupportedBy(annot, BrowserType.IE_6P);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for
	 * IE 7 and above
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isIE7Plus(IJstAnnotation annot) {
		return isSupportedBy(annot, BrowserType.IE_7P);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for
	 * IE 8 and above
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isIE8Plus(IJstAnnotation annot) {
		return isSupportedBy(annot, BrowserType.IE_8P);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for
	 * FireFox 1 and above
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isFF1Plus(IJstAnnotation annot) {
		return isSupportedBy(annot, BrowserType.FIREFOX_1P);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for
	 * FireFox 2 and above
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isFF2Plus(IJstAnnotation annot) {
		return isSupportedBy(annot, BrowserType.FIREFOX_2P);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for
	 * FireFox 3 and above
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isFF3Plus(IJstAnnotation annot) {
		return isSupportedBy(annot, BrowserType.FIREFOX_3P);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for
	 * opera 7 and above
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isOpera7Plus(IJstAnnotation annot) {
		return isSupportedBy(annot, BrowserType.OPERA_7P);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for
	 * opera 8 and above
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isOpera8Plus(IJstAnnotation annot) {
		return isSupportedBy(annot, BrowserType.OPERA_8P);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for
	 * opera 9 and above
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isOpera9Plus(IJstAnnotation annot) {
		return isSupportedBy(annot, BrowserType.OPERA_9P);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for
	 * Safari 3 and above
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isSafari3Plus(IJstAnnotation annot) {
		return isSupportedBy(annot, BrowserType.SAFARI_3P);
	}

	public static boolean isSupportedBy(IJstAnnotation annot, BrowserType browserType) {
		if (!BrowserSupport.class.getSimpleName().equals(annot.getName().toString())) {
			return false;
		}
		String name = browserType.getName();
		int version = browserType.getVersion();
		for (IExpr expr : annot.values()) {
			if (expr instanceof JstArrayInitializer) {
//				String lhs = ((AssignExpr)expr).getLHS().toLHSText();
					JstArrayInitializer init = (JstArrayInitializer)expr;
					String browsers = init.toExprText();
					browsers = browsers.substring(browsers.indexOf('[')+1, browsers.indexOf(']'));
					String[] supportedBrowsers = browsers.split(",");
					for (String browser : supportedBrowsers) {
						int dotIndex = browser.indexOf('.');
						if (dotIndex > 0) {
							BrowserType bType = BrowserType.valueOf(browser.substring(dotIndex+1));
							if (bType != null) {
								String bName = bType.getName();
								int bVersion = bType.getVersion();
								if (name.equals(bName)) {
									return browserType.isPlus() ? 
											version >= bVersion : version == bVersion;
								}
							}
						}
					}
				
			}
		}
		return false;
	}
}
