/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.global;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.Property;


/**
 * 
 * Represents JavaScript native Arguments object
 *
 */
@JsSupport({JsVersion.MOZILLA_ONE_DOT_ONE, JsVersion.JSCRIPT_ONE_DOT_ZERO})
@JsMetatype
public interface Arguments extends Object {
	
	@Constructor void Arguments();
	
	/**
	 * Name of the function being executed.
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_7P})
	@Property String getCallee();
	
	/**
	 * Name of the function that called the function being executed.
	 */
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P})
	@Property String getCaller();
	
	/**
	 * Number of arguments passed to the function.
	 */
	@Property Number getLength();
	
}
