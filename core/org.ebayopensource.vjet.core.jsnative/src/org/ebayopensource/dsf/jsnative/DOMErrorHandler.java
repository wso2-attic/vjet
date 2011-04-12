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
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * DOMErrorHandler is a callback interface that the DOM implementation can call 
 * when reporting errors that happens while processing XML data, 
 * or when doing some other processing (e.g. validating a document). 
 * A DOMErrorHandler object can be attached to a Document using 
 * the "error-handler" on the DOMConfiguration interface. 
 * If more than one error needs to be reported during an operation, 
 * the sequence and numbers of the errors passed to the error handler 
 * are implementation dependent. 
 * <p>The application that is using the DOM implementation is expected to implement this interface. 
 *
 */
@DOMSupport(DomLevel.THREE)
@JsMetatype
public interface DOMErrorHandler extends IWillBeScriptable {
	
	/**
	 * This method is called on the error handler when an error occurs. 
	 * If an exception is thrown from this method, 
	 * it is considered to be equivalent of returning true. 
	 * @param error
	 * @return
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Function boolean handleError(DOMError error);

}
