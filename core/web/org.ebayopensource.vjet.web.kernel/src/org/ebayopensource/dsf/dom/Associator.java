/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

/**
 * Framework only class - applications should NOT use this class
 * <br>
 * Needed to get around lack of "friend" concept in Java
 */
public class Associator {
	protected static void setNameAsCharIndex(final DAttr attr, final int index) {
		attr.setNameAsCharIndex(index) ;
	}
	
	protected static int getNameAsCharIndex(final DAttr attr) {
		return attr.getNameAsCharIndex() ;
	}
	
	protected static DAttr attributeMapPut(
		final DElement element, final DAttr attr)
	{
		return element.m_attributes.put(attr) ;
	}		
}
