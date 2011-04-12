/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.global;

import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;


/**
 * 
 * Represents JavaScript Boolean native object
 *
 */
@JsSupport( {JsVersion.MOZILLA_ONE_DOT_ONE, 
		JsVersion.JSCRIPT_TWO_DOT_ZERO})
public interface Boolean extends Object {
	
	@Constructor void Boolean();
	
	@Constructor void Boolean(boolean value);
	
	@Constructor void Boolean(Number value);
	
	@Constructor void Boolean(String value);
	
	@Function boolean valueOf();
	
}
