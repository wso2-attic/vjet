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
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * The NameList interface provides the abstraction of an ordered collection of parallel pairs of name 
 * and namespace values (which could be null values), without defining or constraining how 
 * this collection is implemented. The items in the NameList are accessible via an integral index, 
 * starting from 0. 
 *
 */
@DOMSupport(DomLevel.THREE)
@JsMetatype
public interface NameList extends IWillBeScriptable {
	
	/**
	 * The number of pairs (name and namespaceURI) in the list. 
	 * The range of valid child node indices is 0 to length-1 inclusive. 
	 */
	@BrowserSupport({BrowserType.NONE})
	@Property int getLength();
	
	/**
	 * Returns the indexth name item in the collection. 
	 * @param index
	 * @return
	 */
	@BrowserSupport({BrowserType.NONE})
	@Function String getName(int index);

	/**
	 * Returns the indexth namespaceURI item in the collection. 
	 * @param index
	 * @return
	 */
	@BrowserSupport({BrowserType.NONE})
	@Function String getNamespaceURI(int index);

	/**
	 * Test if a name is part of this NameList. 
	 * @param str
	 * @return
	 */
	@BrowserSupport({BrowserType.UNDEFINED})
	@Function boolean contains(String str);

	/**
	 * Test if the pair namespaceURI/name is part of this NameList. 
	 * @param namespaceURI
	 * @param name
	 * @return
	 */
	@BrowserSupport({BrowserType.UNDEFINED})
	@Function boolean containsNS(String namespaceURI, 
                              String name);

}
