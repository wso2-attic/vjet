/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

/*
 * *** DO NOT CHANGE THE VALUES FOR THESE CONSTANTS ***
 * 
 * The values are used for array indexes and are in a sequential order that
 * allows for  HTML node types starting at 14 (DA).
 */
public interface IDNodeType {
	int UNDEFINED = -1 ;
	int ATTRIBUTE = 0 ;
	int CDATA = 1 ;
	int COMMENT = 2 ;
	int DOCUMENT = 3 ;
	int DOCUMENT_FRAGMENT = 4 ;
	int ELEMENT = 5 ;
	int ENTITY = 6 ;
	int ENTITY_REF = 7 ;
	int NOTATION = 8 ;
	int PROCESSING_INSTRUCTION = 9 ;
	int TEXT = 10 ;
	int RAWSTRING = 13; // W3C uses 1-12, so lets make sure not to conflict.
}
