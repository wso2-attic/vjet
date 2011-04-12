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

/**
 * EntityReference nodes may be used to represent an entity reference in the tree. 
 * Note that character references and references to predefined entities are considered to 
 * be expanded by the HTML or XML processor so that characters are represented by 
 * their Unicode equivalent rather than by an entity reference. 
 * Moreover, the XML processor may completely expand references to entities while building the Document, 
 * instead of providing EntityReference nodes. 
 * If it does provide such nodes, then for an EntityReference node that represents a reference 
 * to a known entity an Entity exists, and the subtree of the EntityReference node is 
 * a copy of the Entity node subtree. However, the latter may not be true when an entity contains 
 * an unbound namespace prefix. In such a case, because the namespace prefix resolution 
 * depends on where the entity reference is, the descendants of the EntityReference node may be 
 * bound to different namespace URIs. 
 * When an EntityReference node represents a reference to an unknown entity, 
 * the node has no children and its replacement value, when used by Attr.value for example, is empty.
 *
 */
@DOMSupport(DomLevel.ONE)
@BrowserSupport(BrowserType.UNDEFINED)
@JsMetatype
public interface EntityReference extends Node {

}
