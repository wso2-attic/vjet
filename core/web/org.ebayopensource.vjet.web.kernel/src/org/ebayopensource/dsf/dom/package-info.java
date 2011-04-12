/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
* This package provides V4 DOM 2.0 types which implements the W3C interfaces. To
* avoid collisions with other systems that use these interfaces, and/or have
* their own implementations, V4 uses a naming convention to keep a clean 
* namespace. The V4 naming convention prefixes each of the W3C interface type
* name with a "D". If the spec refers to <code>Document</code>,
* <code>Comment</code> etc., V4 uses <code>DDocument</code>,<code>DText</code>
* and <code>DComment</code>, etc., respectively.
* <p>
* The main hierarchy of the V4 DOM intersects the main DSF HTML types at the 
* following plces:
* <code>
* DNode(DOM) --> DDocument(DOM) --> DHtmlDocument(HTML)
* DElement(DOM) --> BaseHtmlElement(all remaining DSF HTML tags are under this
* abstract type)
* </code>
* You can find DSF HTML types in <code>org.ebayopensource.dsf.html.dom</code>
* 
*/
package org.ebayopensource.dsf.dom;