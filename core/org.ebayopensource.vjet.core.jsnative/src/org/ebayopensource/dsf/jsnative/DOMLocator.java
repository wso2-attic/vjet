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
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * DOMLocator is an interface that describes a location 
 * (e.g. where an error occurred). 
 *
 */
@DOMSupport(DomLevel.THREE)
@JsMetatype
public interface DOMLocator extends IWillBeScriptable {
	
	/**
	 * The line number this locator is pointing to, 
	 * or -1 if there is no column number available.
	 * @return
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Property int getLineNumber();

	/**
	 * The column number this locator is pointing to, 
	 * or -1 if there is no column number available.
	 * @return
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Property int getColumnNumber();

	/**
	 * The byte offset into the input source this locator is pointing to 
	 * or -1 if there is no byte offset available.
	 * @return
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Property int getByteOffset();

	/**
	 * The UTF-16, as defined in [Unicode] and Amendment 1 of 
	 * [ISO/IEC 10646], offset into the input source this locator is pointing to or -1 if there is no UTF-16 offset available.
	 * @return
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Property int getUtf16Offset();

	/**
	 * The node this locator is pointing to, or null if no node is available.
	 * @return
	 */
	@Property Node getRelatedNode();

	/**
	 * The URI this locator is pointing to, or null if no URI is available.
	 * @return
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Property String getUri();


}
