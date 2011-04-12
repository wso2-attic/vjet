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
 * Each Document has a doctype attribute whose value is either null or a DocumentType object. 
 * The DocumentType interface in the DOM Core provides an interface to the list of entities 
 * that are defined for the document, and little else because the effect of namespaces and 
 * the various XML schema efforts on DTD representation are not clearly understood as of this writing.
 *
 */
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface DocumentType extends Node {
	
	/**
	 * The name of DTD; i.e., the name immediately following the DOCTYPE keyword.
	 * @return
	 */
	@DOMSupport( DomLevel.ONE) @BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Property String getName();

	/**
	 * A NamedNodeMap containing the general entities, both external and internal, declared in the DTD. 
	 * Parameter entities are not contained. Duplicates are discarded.
	 * @return
	 */
	@DOMSupport( DomLevel.ONE) @BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Property NamedNodeMap getEntities();

	/**
	 * A NamedNodeMap containing the notations declared in the DTD. Duplicates are discarded. 
	 * Every node in this map also implements the Notation interface.
	 * The DOM Level 2 does not support editing notations, therefore notations cannot be altered 
	 * in any way.
	 * @return
	 */
	@DOMSupport( DomLevel.ONE) 
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Property NamedNodeMap getNotations();

	/**
	 * The public identifier of the external subset.
	 * @return
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Property String getPublicId();

	/**
	 * The system identifier of the external subset. This may be an absolute URI or not.
	 * @return
	 * @since DOM Level 2
	 */
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@DOMSupport(DomLevel.TWO)
	@Property String getSystemId();

	/**
	 * The internal subset as a string, or null if there is none. 
	 * This is does not contain the delimiting square brackets.
	 * @return
	 * @since DOM Level 2
	 */
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@DOMSupport(DomLevel.TWO)
	@Property String getInternalSubset();

}
