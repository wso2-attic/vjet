/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.OptionalArg;

/**
 * An <code>HTMLOptionsCollection</code> is a list of nodes representing HTML option element. 
 * An individual node may be accessed by either ordinal index or the node's name 
 * or id attributes.
 *
 */
@Alias("HTMLOptionsCollection")
@DOMSupport(DomLevel.THREE)
@JsMetatype
public interface HtmlOptionsCollection extends HtmlCollection {
	
	/**
	 * To support setting options.length to manipulate the options
	 * in Select element.
	 * @param length int 
	 * <ul>
	 * <li>If options.length is set to zero, it will clear all Select options. 
	 * <li>If options.length is set to a value less than the current value, the 
	 * number of options in Select is decreased.
	 * </ul>
	 */
	@Function void setLength(int length);
	
	// select.options.add(new Option());
	// select.options.add(new Option(), 0);
    
//    @Function void add(HtmlOption element);
    
	/*
	 * overload
	 */
	
	@OptionalArg({"index", "element"})
    @Function void add(HtmlOption element, int index);

}
