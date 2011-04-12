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
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * http://www.w3.org/TR/cssom-view/#textrectanglelist
 */

@JsMetatype
public interface TextRectangleList extends IWillBeScriptable {
	/**
	 * 
	 * The length attribute must return the total number of TextRectangle objects associated with the object. 
	 */
	@BrowserSupport({BrowserType.IE_6P,BrowserType.FIREFOX_2P})
	@Property long getLength();

	/**
	 * 
	 * The item(index) method, when invoked, must raise an INDEX_SIZE_ERR exception when index is negative or greater than the number of TextRectangle objects associated with the object. Otherwise, the TextRectangle object at index must be returned. 
	 */
	@BrowserSupport({BrowserType.IE_6P,BrowserType.FIREFOX_2P})
	@Function TextRectangle item(long index);

}
