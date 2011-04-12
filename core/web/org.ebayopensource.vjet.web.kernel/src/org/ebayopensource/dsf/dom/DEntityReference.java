/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;

/**
 * <code>EntityReference</code> nodes may be used to represent an entity 
 * reference in the tree. Note that character references and references to 
 * predefined entities are considered to be expanded by the HTML or XML 
 * processor so that characters are represented by their Unicode equivalent 
 * rather than by an entity reference. Moreover, the XML processor may 
 * completely expand references to entities while building the 
 * <code>Document</code>, instead of providing <code>EntityReference</code> 
 * nodes. If it does provide such nodes, then for an 
 * <code>EntityReference</code> node that represents a reference to a known 
 * entity an <code>Entity</code> exists, and the subtree of the 
 * <code>EntityReference</code> node is a copy of the <code>Entity</code> 
 * node subtree. However, the latter may not be true when an entity contains 
 * an unbound namespace prefix. In such a case, because the namespace prefix 
 * resolution depends on where the entity reference is, the descendants of 
 * the <code>EntityReference</code> node may be bound to different namespace 
 * URIs. When an <code>EntityReference</code> node represents a reference to 
 * an unknown entity, the node has no children and its replacement value, 
 * when used by <code>Attr.value</code> for example, is empty.
 * <p>As for <code>Entity</code> nodes, <code>EntityReference</code> nodes and 
 * all their descendants are readonly.
 * <p ><b>Note:</b> <code>EntityReference</code> nodes may cause element 
 * content and attribute value normalization problems when, such as in XML 
 * 1.0 and XML Schema, the normalization is performed after entity reference 
 * are expanded.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 */
public class DEntityReference extends DNode implements EntityReference {
	private static final long serialVersionUID = 6265039493933021898L;

	//
	// Constructor(s)
	//
	DEntityReference(final DDocument document, final String name) {
		super(document, name) ;
	}
	
	@Override
	public final short getNodeType() {
		return Node.ENTITY_REFERENCE_NODE;
	}
	
	@Override
	public DEntityReference jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		return "&" + getNodeName() + ";" ;
	}
}
