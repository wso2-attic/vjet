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

/**
 * This interface represents a known entity, either parsed or unparsed, 
 * in an XML document. 
 * <p>Note that this models the entity itself not the entity declaration.
 *
 */
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface Entity extends Node {
	
	/**
	 * The public identifier associated with the entity if specified, and null otherwise.
	 * @return
	 */
	@DOMSupport(DomLevel.ONE)
	@BrowserSupport({BrowserType.OPERA_9P})
	@Property String getPublicId();

	/**
	 * The system identifier associated with the entity if specified, 
	 * and null otherwise. This may be an absolute URI or not.
	 * @return
	 */
	@DOMSupport(DomLevel.ONE)
	@BrowserSupport({BrowserType.OPERA_9P})
	@Property String getSystemId();

	/**
	 * For unparsed entities, the name of the notation for the entity. 
	 * For parsed entities, this is null.
	 * @return
	 */
	@BrowserSupport(BrowserType.OPERA_9P)
	@DOMSupport(DomLevel.ONE)
	@Property String getNotationName();

	/**
	 * An attribute specifying the encoding used for this entity at the time 
	 * of parsing, when it is an external parsed entity. 
	 * <p>This is null if it an entity from the internal subset or if it is not known.
	 * @return
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Property String getInputEncoding();

	/**
	 * An attribute specifying, as part of the text declaration, 
	 * the encoding of this entity, when it is an external parsed entity. 
	 * This is null otherwise.
	 * @return
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Property String getXmlEncoding();

	/**
	 * An attribute specifying, as part of the text declaration, 
	 * the version number of this entity, when it is an external parsed entity. 
	 * This is null otherwise.
	 * @return
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Property String getXmlVersion();

}
