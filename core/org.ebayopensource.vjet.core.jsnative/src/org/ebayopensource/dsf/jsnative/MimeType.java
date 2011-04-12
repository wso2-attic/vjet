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
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * JavaScript object representing a MIME (Multipart Internet Mail Extension) data type
 *
 */
@JsSupport( JsVersion.MOZILLA_ONE_DOT_ONE)
@JsMetatype
public interface MimeType extends IWillBeScriptable {
	
	/**
	 * Returns description of the MIME type
	 * @return
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_7P, BrowserType.SAFARI_3P})
	@Property String getDescription();
	
	/**
	 * Returns reference to the Plugin object that is configured for the MIME type
	 * @return
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_7P, BrowserType.SAFARI_3P})
	@Property Plugin getEnabledPlugin();
	
	/**
	 * Returns String listing possible file extensions for the MIME type, separated by commas
	 * @return
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_7P, BrowserType.SAFARI_3P})
	@Property String getSuffixes();
	
	/**
	 * Returns name of the MIME type
	 * @return
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_7P, BrowserType.SAFARI_3P})
	@Property String getType();

}
