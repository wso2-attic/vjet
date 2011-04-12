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
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * This is a script object that Opera browser has provided
 * http://www.howtocreate.co.uk/operaStuff/operaObject.html
 */
@BrowserSupport({BrowserType.OPERA_7P})
@JsMetatype
public interface Opera extends IWillBeScriptable {
	
	/**
	 * Returns the current build number of the Opera version being used. 
	 * @return
	 */
	@BrowserSupport({BrowserType.OPERA_7P})
	@Function String buildNumber(); 
	
	/**
	 * Returns the current version number (as a string) 
	 * of the Opera version being used.
	 * @return
	 */
	@BrowserSupport({BrowserType.OPERA_7P})
	@Function String version(); 
	
	/**
	 * Registers an event listener for a User JavaScript event. 
	 */
	@BrowserSupport({BrowserType.OPERA_8P})
	@Function void addEventListener(String type, org.ebayopensource.dsf.jsnative.global.Function handler, boolean phase);
	
	/**
	 * Removes an event listener for a User JavaScript event.
	 */
	@BrowserSupport({BrowserType.OPERA_8P})
	@Function void removeEventListener(String type, org.ebayopensource.dsf.jsnative.global.Function handler, boolean phase);
	
	/**
	 * Attempts to initiate JavaScript garbage collection. 
	 * This method will only have any effect if Opera has allocated 
	 * enough memory to JavaScript since the last time it ran a 
	 * garbage collection.
	 */
	@BrowserSupport({BrowserType.OPERA_7P})
	@Function void collect();
	
	/**
	 * Overrides functions defined by the page.
	 */
	@BrowserSupport({BrowserType.OPERA_8P})
	@Function void defineMagicFunction(String funcName, org.ebayopensource.dsf.jsnative.global.Function replacementFunction);
	
	/**
	 * Overrides variables defined by the page. 
	 */
	@BrowserSupport({BrowserType.OPERA_8P})
	@Function void defineMagicVariable(String VarName, org.ebayopensource.dsf.jsnative.global.Function getter, org.ebayopensource.dsf.jsnative.global.Function setter);
	
	/**
	 * Returns the last last value of history navigation mode that 
	 * was set using for the current setOverrideHistoryNavigationMode. 
	 */
	@BrowserSupport({BrowserType.OPERA_8P})
	@Function String getOverrideHistoryNavigationMode(); 
	
	/**
	 * Sets the the history navigation mode to 'automatic', 
	 * 'compatible' or 'fast'. 
	 */
	@BrowserSupport({BrowserType.OPERA_8P})
	@Function void setOverrideHistoryNavigationMode(String mode); 
	
	/**
	 * Returns the current value of the specified preference in opera6.ini 
	 */
	@BrowserSupport({BrowserType.OPERA_9P})
	@Function String getPreference(String section,String preference);
	
	/**
	 * Returns the current value of the specified preference in opera6.ini 
	 */
	@BrowserSupport({BrowserType.OPERA_9P})
	@Function String getPreferenceDefault(String section,String preference);
	
	/**
	 * Sets the the specified preference in opera6.ini to the specified value. 
	 */
	@BrowserSupport({BrowserType.OPERA_9P})
	@Function void setPreference(String section, String preference, String value);

}
