/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * JavaScript object representing window's history object.
 */
@JsSupport(JsVersion.MOZILLA_ONE_DOT_ZERO)
@JsMetatype
public interface History extends IWillBeScriptable {
	
	// Properties
	
	/**
	 * Returns the number of elements in the history list
	 * @return
	 */
	@Property int getLength();
	
	/**
	 * Loads the previous URL in the history list
	 */
	@Function void back();
	
	/**
	 * Loads the next URL in the history list
	 */
	@Function void forward();
	
	/**
	 * Loads a specific page in the history list
	 * @param number is an Integer number in history array or a URL to go to. 
	 */
	@Function void go(int number);

}
