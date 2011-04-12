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
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;

/**
 * 
 * Represents JavaScript native Enumerator object
 *
 */
@JsSupport( {JsVersion.JSCRIPT_THREE_DOT_ZERO}) 
@BrowserSupport( {BrowserType.IE_6P})
public interface Enumerator  extends Object {
	
	@Constructor void Enumerator(Object group);
	
	/**
	 * Returns true if the current item is the last one in the group.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Function boolean atEnd();
	
	/**
	 * Returns the current item in the group.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Function Object item();
	
	/**
	 * Moves the current item to the first position in the group.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Function Object moveFirst();
	
	/**
	 * Moves the current item to the next position in the group.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Function Object moveNext();
	
}
