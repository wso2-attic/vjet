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
 * Objects implementing the NamedNodeMap interface are used to represent collections of nodes that 
 * can be accessed by name. Note that NamedNodeMap does not inherit from NodeList; 
 * NamedNodeMaps are not maintained in any particular order. 
 * Objects contained in an object implementing NamedNodeMap may also be accessed by an ordinal index, 
 * but this is simply to allow convenient enumeration of the contents of a NamedNodeMap, 
 * and does not imply that the DOM specifies an order to these Nodes. 
 *
 */
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface NamedNodeMap extends IWillBeScriptable {
	
	/**
	 * The number of nodes in this map. The range of valid child node indices is 0 to length-1 inclusive.
	 * @return
	 */
	@Property int getLength();
	
	/**
	 * Retrieves a node specified by name.
	 * @param name
	 * @return
	 */
	@Function Node getNamedItem(String name);
	
	/**
	 * Sets the node in map
	 * @param arg
	 * @return
	 */
	@Function Node setNamedItem(Node arg);

	/**
	 * Removes a node specified by name. When this map contains the attributes attached to an element, 
	 * if the removed attribute is known to have a default value, an attribute immediately appears 
	 * containing the default value as well as the corresponding namespace URI, local name, and prefix 
	 * when applicable.
	 * @param name
	 * @return
	 */
	@Function Node removeNamedItem(String name);

	/**
	 * Returns the indexth item in the map. 
	 * If index is greater than or equal to the number of nodes in this map, this returns null.
	 * @param index
	 * @return
	 */
	@Function Node item(int index);

    /**
     * Retrieves a node specified by local name and namespace URI. 
     * Per [XML Namespaces], applications must use the value null as the namespaceURI parameter 
     * for methods if they wish to have no namespace.
     * @param namespaceURI
     * @param localName
     * @return
     * @since DOM Level 2
     */
	@DOMSupport(DomLevel.TWO) @BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
    @Function Node getNamedItemNS(String namespaceURI, String localName);

    /**
     * Adds a node using its namespaceURI and localName. If a node with that namespace URI and that 
     * local name is already present in this map, it is replaced by the new one. 
     * Replacing a node by itself has no effect. Per [XML Namespaces], applications must use 
     * the value null as the namespaceURI parameter for methods if they wish to have no namespace.
     * @param arg
     * @return
     * @since DOM Level 2
     */
	@DOMSupport(DomLevel.TWO) @BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
    @Function Node setNamedItemNS(Node arg);

    /**
     * Removes a node specified by local name and namespace URI. 
     * A removed attribute may be known to have a default value when this map contains the attributes 
     * attached to an element, as returned by the attributes attribute of the Node interface. 
     * If so, an attribute immediately appears containing the default value as well as the 
     * corresponding namespace URI, local name, and prefix when applicable. 
     * Per [XML Namespaces], applications must use the value null as the namespaceURI parameter 
     * for methods if they wish to have no namespace.
     * @param namespaceURI
     * @param localName
     * @return
     * @since DOM Level 2
     */
	@DOMSupport(DomLevel.TWO) @BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
    @Function Node removeNamedItemNS(String namespaceURI, String localName);
	
	/**
     * Only for Rhino support
     * @param type
     * @return
     */
	@BrowserSupport({BrowserType.RHINO_1P})
    @Function Object valueOf(String type);

}
