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
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JstExclude;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * An <code>HTMLCollection</code> is a list of nodes. An individual node may be accessed by 
 * either ordinal index or the node's name or id attributes.
 * <p>
 * Note: Collections in the HTML DOM are assumed to be live meaning that 
 * they are automatically updated when the underlying document is changed.
 *
 */
@Alias("HTMLCollection")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlCollection extends IWillBeScriptable {
	
	/**
	 * Returns the length or size of the list.
	 * @return
	 */
	@Property int getLength();

    /**
     * This method retrieves a node specified by ordinal index. 
     * Nodes are numbered in tree order (depth-first traversal order). 
     * @param index The index of the node to be fetched. The index origin is 0
     * @return <code>Node</code> The node at the corresponding position upon success. 
     * A value of null is returned if the index is out of range.
     */
	@Function Node item(int index);

    /**
     * This method retrieves a Node using a name. With [HTML 4.01] documents, 
     * it first searches for a Node with a matching id attribute. 
     * If it doesn't find one, it then searches for a Node with a matching 
     * name attribute, but only on those elements that are allowed a name attribute. 
     * With [XHTML 1.0] documents, this method only searches for Nodes with 
     * a matching id attribute. This method is case insensitive in HTML documents 
     * and case sensitive in XHTML documents. 
     * @param name The name of the <code>Node</code> to be fetched.
     * @return <code>Node</code> The node with a name or id attribute whose value corresponds 
     * to the specified string. Upon failure (e.g., no node with this name exists),
     * returns <code>null</code>.
     */
	@Function Node namedItem(String name);
	
	/**
     * Only for Rhino support
     * @param type
     * @return
     */
	@BrowserSupport({BrowserType.RHINO_1P})
	@JstExclude
    @Function Object valueOf(String type);
	
	@BrowserSupport({BrowserType.NONE})
	@JstExclude
	<T extends HtmlElement> T get(String name);
}
