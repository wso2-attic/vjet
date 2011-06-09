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
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * The DOMConfiguration interface represents the configuration of a document and maintains a table 
 * of recognized parameters. Using the configuration, it is possible to change 
 * Document.normalizeDocument() behavior, such as replacing the CDATASection nodes with Text nodes 
 * or specifying the type of the schema that must be used when the validation of the Document is requested. DOMConfiguration objects are also used in [DOM Level 3 Load and Save] in the DOMParser and DOMSerializer interfaces. The parameter names used by the DOMConfiguration object are defined throughout the DOM Level 3 specifications. Names are case-insensitive. To avoid possible conflicts, as a convention, names referring to parameters defined outside the DOM specification should be made unique. Because parameters are exposed as properties in the ECMAScript Language Binding, names are recommended to follow the section "5.16 Identifiers" of [Unicode] with the addition of the character '-' (HYPHEN-MINUS) but it is not enforced by the DOM implementation. DOM Level 3 Core Implementations are required to recognize all parameters defined in this specification. Some parameter values may also be required to be supported by the implementation. Refer to the definition of the parameter to know if a value must be supported or not. 
 *
 */
@DOMSupport(DomLevel.THREE)
@JsMetatype
public interface DOMConfiguration extends IWillBeScriptable {
	
	/**
	 * The list of the parameters supported by this DOMConfiguration object and for which 
	 * at least one value can be set by the application. 
	 * Note that this list can also contain parameter names defined outside this specification. 
	 * @return
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.OPERA_9P})
	
	@JsArray(String.class)
	@Property DOMStringList getParameterNames();
	
	/**
	 * Set the value of a parameter.
	 * @param name
	 * @param value
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Function void setParameter(String name, Object value);

	/**
	 * Return the value of a parameter if known. 
	 * @param name
	 * @return
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Function Object getParameter(String name);

	/**
	 * Check if setting a parameter to a specific value is supported.
	 * @param name
	 * @param value
	 * @return
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE) 
	@BrowserSupport({BrowserType.UNDEFINED})
	@Function boolean canSetParameter(String name, Object value);
	
}
