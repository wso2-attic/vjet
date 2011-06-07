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
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsArray;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * This interface permits a DOM implementer to supply one or more implementations, 
 * based upon requested features and versions, as specified in DOM Features. 
 * Each implemented DOMImplementationSource object is listed in the binding-specific list 
 * of available sources so that its DOMImplementation objects are made available. 
 *
 */
@DOMSupport(DomLevel.THREE)
@JsMetatype
public interface DOMImplementationSource extends IWillBeScriptable {
	
	/**
	 * A method to request the first DOM implementation that supports the specified features. 
	 * @param features
	 * @return
	 */
	@BrowserSupport({BrowserType.UNDEFINED})
	@Function DOMImplementation getDOMImplementation(String features);

	/**
	 * A method to request a list of DOM implementations that support the specified features 
	 * and versions, as specified in DOM Features.
	 * @param features
	 * @return
	 */
	@BrowserSupport({BrowserType.UNDEFINED})
	@JsArray(DOMImplementation.class)
	@Function DOMImplementationList getDOMImplementationList(String features);
}
