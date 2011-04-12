/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * This package provides various types of bindings. V4 bindings are a
 * flexible way to manage value semantics. Bindings are incorporated in a number
 * of V4 constructs and artifacts:
 * <li>As a standalone construct
 * <li>Use with Fields (initial value and backing value)
 * <li>Use with DNode Attributes
 * <li>Links
 * <li>Images
 * <li>more...
 * Bindings are based on interfaces and are template<T> based. This supports use
 * with other types, as well as the ability to create completely new generic
 * bindings yourself.
 * <p>
 * A flavor of binding is called a <code>ManagedValue Binding</code>. This type
 * of binding includes:
 * <li>Basic typed return
 * <li>Setter/getter
 * <li>Extends to include null handling
 * <li>Supports isset semantics
 * <li>Supports current and previous value retention  
 */
package org.ebayopensource.dsf.common.binding;