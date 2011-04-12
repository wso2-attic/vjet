/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.highlight;

import java.util.Arrays;

import org.ebayopensource.dsf.jst.reserved.JsCoreKeywords;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class DefaultVjoHighlightKeywordProvider implements
		IVjoHighlightKeywordsProvider {

	private static final String VJO_NEEDS = vjo(VjoKeywords.NEEDS);

	private final static String[] VJO_KEYWORDS = {
		JsCoreKeywords.THIS + "." + VjoKeywords.BASE,
		JsCoreKeywords.THIS + "." + VjoKeywords.VJ$ + "." + VjoKeywords.PARENT,
		JsCoreKeywords.THIS + "." + VjoKeywords.VJ$ + "." + VjoKeywords.OUTER,
		JsCoreKeywords.THIS + "." + vjo(VjoKeywords.NEEDS_IMPL) 
	};

	private final static String[] VJO_METHODS = {
		VjoKeywords.NEEDS,
		VjoKeywords.GLOBALS,
		VjoKeywords.OTYPE,
		VjoKeywords.TYPE,
		VjoKeywords.PROPS,
		VjoKeywords.PROTOS,
		VjoKeywords.OPTIONS,
		VjoKeywords.DEFS,
		VjoKeywords.INHERITS,
		VjoKeywords.INITS,
		VjoKeywords.SATISFIES,
		VjoKeywords.ITYPE,
		VjoKeywords.ETYPE,
		VjoKeywords.LTYPE,
		VjoKeywords.MTYPE,
		VjoKeywords.CTYPE,
		VjoKeywords.MAKE_FINAL,
		VjoKeywords.MIXIN,
//		VjoKeywords.MIXINPROPS,
		VjoKeywords.EXPECTS,
		VjoKeywords.VALUES,
		VjoKeywords.ENDTYPE,
		VjoKeywords.VJO
	};

	private final static String[] VJO_COMPOSITE_METHODS_KEYWORDS = {
		//syntax block highlights
		vjo(VjoKeywords.TYPE),
		vjo(VjoKeywords.CTYPE),
		vjo(VjoKeywords.ETYPE),
		vjo(VjoKeywords.ITYPE), 
		vjo(VjoKeywords.OTYPE), 
		vjo(VjoKeywords.LTYPE), 
		vjo(VjoKeywords.MTYPE),
		vjo(VjoKeywords.FTYPE),
		//syntax block methods calls highlights
		dot(VjoKeywords.NEEDSLIB), 
		dot(VjoKeywords.NEEDS),
		dot(VjoKeywords.PROPS), 
		dot(VjoKeywords.PROTOS),
		dot(VjoKeywords.DEFS), 
		dot(VjoKeywords.INHERITS),
		dot(VjoKeywords.INITS), 
		dot(VjoKeywords.SATISFIES),
		dot(VjoKeywords.MAKE_FINAL),
		dot(VjoKeywords.MIXIN), 
//		dot(VjoKeywords.MIXINPROPS),
		dot(VjoKeywords.EXPECTS), 
		dot(VjoKeywords.GLOBALS),
		dot(VjoKeywords.OPTIONS),
		dot(VjoKeywords.VALUES), 
		dot(VjoKeywords.ENDTYPE),
		//non syntax block hightlights
		vjo(VjoKeywords.MIXIN),
		vjo(VjoKeywords.MAKE)
	};
	
	@Override
	public String[] getVjoKeywords() {
		return Arrays.copyOf(VJO_KEYWORDS, VJO_KEYWORDS.length);
	}
	
	@Override
	public String[] getVjoMethods() {
		return Arrays.copyOf(VJO_METHODS, VJO_METHODS.length);
	}

	@Override
	public String[] getVjoCompositeMethods() {
		return Arrays.copyOf(VJO_COMPOSITE_METHODS_KEYWORDS, VJO_COMPOSITE_METHODS_KEYWORDS.length);
	}

	public static String dot(String keyword) {
		return "." + keyword;
	}

	public static String vjo(String keyword) {
		return new StringBuilder().append(VjoKeywords.VJO).append('.').append(keyword).toString();
	}

	@Override
	public String getVjoNeeds() {
		return VJO_NEEDS;
	}
}
