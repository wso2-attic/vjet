/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor.keyword;

import org.ebayopensource.vjo.meta.VjoKeywords;

public class CompletionConstants {

	public static final String VJ$ = "vj$";
	public static final char DOT = '.';
	public static final String THIS = "this";
	public static final String THIS_VJO = THIS + DOT + VJ$;
	public static final String THIS_BASE = THIS + DOT + VjoKeywords.BASE;
	public static final String THIS_VJ$_OUTER = THIS_VJO + DOT + VjoKeywords.OUTER;
	public static final String THIS_VJ$_PARENT = THIS_VJO + DOT + VjoKeywords.PARENT;

	public static final String FUNCTION_NAME_MAIN = "main";
}
