/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

/**
 * <pre>
 * HTML has two basic content models:
 * <li>%inline;     character level elements and text strings
 * <li>%block;      block-like elements e.g. paragraphs and lists
 * </pre>
 */
public interface IDInline extends IDFlow {
	// Includes DFontStyle, DPhrase, DSpecial, DFormControl
	
	// empty on purpose
}
