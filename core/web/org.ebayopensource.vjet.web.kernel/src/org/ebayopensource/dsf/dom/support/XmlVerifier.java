/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.support;

/**
 * XML name verifier, based on W3C spec
 */
public class XmlVerifier {
	/**
	 * Check the string against XML's definition of acceptable names for
	 * elements and attributes and so on using the XMLCharacterProperties
	 * utility class
	 */

	public static final boolean isXMLName(
		final String s, final boolean xml11Version)
	{
		if (s == null) {
			return false;
		}
		if (xml11Version) {
			return Xml11Char.isXML11ValidName(s);
		}
		return XmlChar.isValidName(s);

	} // isXMLName(String):boolean
} 