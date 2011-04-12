/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;

import org.ebayopensource.vjo.meta.VjoKeywords;

public class StringUtils {
	public static boolean isBlankOrEmpty(String string) {
		if (string == null || string.trim().equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isLetter(char c) {
		return (c >= 'A' && c<='Z') || (c>='a' && c <= 'z') || c == '_' || c=='$' || (c >= '0' && c<='9');
	}

	public static boolean compare(String oname, String name) {
		if (oname == null) {
			return name == null;
		} else {
			return oname.equals(name);
		}
	}
	
	/**
	 * Tests if specified char is tab or space
	 * 
	 * @param ch
	 * @return
	 */
	public static boolean isSpaceOrTab(char ch) {
		return ch == ' ' || ch == '\t';
	}
	
	public static boolean isVj$Expr(String s) {
		return s.indexOf(VjoKeywords.VJ$) != -1;
	}
	
}
