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
import org.w3c.dom.TypeInfo;

/**
 * @see http://www.w3.org/TR/DOM-Level-2-Core/core.html#ID-637646024
 */
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface Attr extends Node {
	
	/**
	 * Returns the name of this attribute.
	 */
	@Property String getName();

	/**
	 * If this attribute was explicitly given a value in the original document, 
	 * this is true; otherwise, it is false. 
	 */
	@Property boolean getSpecified();

	/**
	 * Returns the value of the attribute is returned as a string. 
	 * Character and general entity references are replaced with their values. 
	 * See also the method getAttribute on the Element interface.
	 */
	@Property String getValue();
    
	/**
	 * On setting, this creates a Text node with the unparsed contents of the string. 
	 * I.e. any characters that an XML processor would recognize as markup are instead 
	 * treated as literal text. See also the method setAttribute on the Element interface.
	 */
	@Property void setValue(String value);

	/**
	 * The Element node this attribute is attached to or null if this attribute is not in use.
	 */
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Property Element getOwnerElement();

	/**
	 * The type information associated with this attribute. 
	 */
	@DOMSupport( DomLevel.THREE)
	@BrowserSupport({BrowserType.UNDEFINED})
	@Property TypeInfo getSchemaTypeInfo();

	/**
	 * Returns whether this attribute is known to be of type ID 
	 * (i.e. to contain an identifier for its owner element) or not. 
	 */
	@DOMSupport( DomLevel.THREE)
	@BrowserSupport({BrowserType.UNDEFINED})
	@Property boolean getIsId();

}

