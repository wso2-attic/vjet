/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

class Util {
	
	// Purposefully did not use full name "char" to avoid confusion
	public static String getHtmlCh(final BaseHtmlElement element) {
		// Make sure that the access key is a single character.
		String ch = element.getHtmlAttribute(EHtmlAttr._char);
		if (ch != null && ch.length() > 1) {
			ch = ch.substring(0, 1);
		}
		return ch;
	}

	// Purposefully did not use full name "char" to avoid confusion
	public static void setHtmlCh(final BaseHtmlElement element, String _char) {
		// Make sure that the access key is a single character.
		if (_char != null && _char.length() > 1) {
			_char = _char.substring(0, 1);
		}
		element.setHtmlAttribute(EHtmlAttr._char, _char);
	}
	
	public static String getHtmlAccessKey(final BaseHtmlElement element) {
		// Make sure that the access key is a single character.
		String accessKey = element.getHtmlAttribute(EHtmlAttr.accesskey);
		if (accessKey != null && accessKey.length() > 1) {
			accessKey = accessKey.substring(0, 1);
		}
		return accessKey;
	}

	public static void setHtmlAccessKey(
		final BaseHtmlElement element,  String accessKey)
	{
		// Make sure that the access key is a single character.
		if (accessKey != null && accessKey.length() > 1) {
			accessKey = accessKey.substring(0, 1);
		}
		element.setHtmlAttribute(EHtmlAttr.accesskey, accessKey);
	}
}
