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
 * The DOMStringList interface provides the abstraction of an ordered collection of DOMString values, 
 * without defining or constraining how this collection is implemented. 
 * The items in the DOMStringList are accessible via an integral index, starting from 0. 
 */
@DOMSupport(DomLevel.THREE)
@JsMetatype
public interface DOMStringList extends IWillBeScriptable {
	
	
	/**
	 * The number of DOMStrings in the list. 
	 * The range of valid child node indices is 0 to length-1 inclusive.
	 * @return
	 */
	@BrowserSupport({BrowserType.OPERA_9P})
	@Property int getLength();
	
	/**
	 * Returns the indexth item in the collection. 
	 * If index is greater than or equal to the number of DOMStrings in the list, this returns null. 
	 * @param index
	 * @return
	 */
	@BrowserSupport({BrowserType.OPERA_9P})
	@Function String item(int index);

    /**
     * Test if a string is part of this DOMStringList. 
     * @param str
     * @return
     */
	@BrowserSupport({BrowserType.OPERA_9P})
	@Function boolean contains(String str);


}
