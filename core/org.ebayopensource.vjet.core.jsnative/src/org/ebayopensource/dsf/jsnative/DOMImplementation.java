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
 * The DOMImplementation interface provides a number of methods for performing operations 
 * that are independent of any particular instance of the document object model. 
 *
 */
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface DOMImplementation extends IWillBeScriptable {
	
	/**
	 * Test if the DOM implementation implements a specific feature and version, 
	 * as specified in DOM Features.
	 * @param feature
	 * @param version
	 * @return
	 */
	@Function boolean hasFeature(String feature, String version);

	/**
	 * Creates an empty DocumentType node. 
	 * Entity declarations and notations are not made available. 
	 * Entity reference expansions and default attribute additions do not occur.
	 * @param qualifiedName
	 * @param publicId
	 * @param systemId
	 * @return
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P})
	@Function DocumentType createDocumentType(String qualifiedName, 
                         String publicId, 
                         String systemId);

	/**
	 * Creates a DOM Document object of the specified type with its document element. 
	 * Note that based on the DocumentType given to create the document, the implementation may 
	 * instantiate specialized Document objects that support additional features than the "Core", 
	 * such as "HTML" [DOM Level 2 HTML]. On the other hand, setting the DocumentType after 
	 * the document was created makes this very unlikely to happen. 
	 * Alternatively, specialized Document creation methods, such as 
	 * createHTMLDocument [DOM Level 2 HTML], can be used to obtain specific types of Document objects.
	 * @param namespaceURI
	 * @param qualifiedName
	 * @param doctype
	 * @return
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P})
	@Function Document createDocument(String namespaceURI, 
                 String qualifiedName, 
                 DocumentType doctype);

	/**
	 * This method returns a specialized object which implements the specialized APIs 
	 * of the specified feature and version, as specified in DOM Features. 
	 * The specialized object may also be obtained by using binding-specific casting methods 
	 * but is not necessarily expected to, as discussed in Mixed DOM Implementations. 
	 * This method also allow the implementation to provide specialized objects which do not 
	 * support the DOMImplementation interface. 
	 * @param feature
	 * @param version
	 * @return
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.OPERA_9P})
	@Function Object getFeature(String feature, String version);


}
