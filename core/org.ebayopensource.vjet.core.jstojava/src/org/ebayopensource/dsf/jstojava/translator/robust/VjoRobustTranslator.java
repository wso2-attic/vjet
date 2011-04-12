/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class VjoRobustTranslator extends BaseRobustTranslator implements ICompletionsFilter{

//	private static final Map<String, Class> VJO_SUB_TRANSLATORS_MAP = new HashMap<String, Class>();

	public String[] filter(String[] completions) {

		List<String> filtered = new ArrayList<String>();

		for (String completion : completions) {
			if (!completion.equals(VjoKeywords.NEEDS)) {
				filtered.add(completion);
			}
		}

		return filtered.toArray(new String[] {});
	}
//	static {
//		VJO_SUB_TRANSLATORS_MAP.put(VjoKeywords.TYPE,
//				TypeRobustTranslator.class);
//		VJO_SUB_TRANSLATORS_MAP.put(VjoKeywords.ITYPE,
//				ITypeRobustTranslator.class);
////		VJO_SUB_TRANSLATORS_MAP.put(VjoKeywords.LTYPE,
////				LTypeRobustTranslator.class);
////		VJO_SUB_TRANSLATORS_MAP.put(VjoKeywords.ATYPE,
////				ATypeRobustTranslator.class);
//		VJO_SUB_TRANSLATORS_MAP.put(VjoKeywords.CTYPE,
//				TypeRobustTranslator.class);
//		VJO_SUB_TRANSLATORS_MAP.put(VjoKeywords.NEEDS,
//				NeedsRobustTranslator.class);
//		VJO_SUB_TRANSLATORS_MAP.put(VjoKeywords.ETYPE,
//				ETypeRobustTranslator.class);
//		VJO_SUB_TRANSLATORS_MAP.put(VjoKeywords.MTYPE,
//				MTypeRobustTranslator.class);
//		VJO_SUB_TRANSLATORS_MAP.put(VjoKeywords.OTYPE,
//				OTypeRobustTranslator.class);
//	}

	public VjoRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}

//	@Override
//	protected final Map<String, Class> getTranslatorMap() {
//		return VJO_SUB_TRANSLATORS_MAP;
//	}

	public boolean transform() {

		// pop vjo element from the stack
		// store this element as current one
		current = astElements.pop();

		// lookup possible empty completions
		lookupEmptyCompletion();

		return super.transform();

	}
}
