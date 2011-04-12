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
import java.util.List;

import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class MTypeRobustTranslator extends TypeRobustTranslator {

	public MTypeRobustTranslator(TranslateCtx ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

//	private static final Map<String, Class> MTYPE_SUB_TRANSLATORS_MAP = new HashMap<String, Class>();

	public String[] filter(String[] completions) {

		List<String> filtered = new ArrayList<String>();

		String[] baseFiltered = super.filter(completions);

		for (String completion : baseFiltered) {
			if (completion.equals(VjoKeywords.EXPECTS)
					|| completion.equals(VjoKeywords.PROPS)
					|| completion.equals(VjoKeywords.PROTOS)
					|| completion.equals(END_TYPE)) {
				filtered.add(completion);
			}
		}

		return filtered.toArray(new String[] {});
	}

//	static {
//		MTYPE_SUB_TRANSLATORS_MAP.put(VjoKeywords.NEEDS,
//				NeedsRobustTranslator.class);
//		MTYPE_SUB_TRANSLATORS_MAP.put(VjoKeywords.EXPECTS,
//				ExpectsRobustTranslator.class);
//		MTYPE_SUB_TRANSLATORS_MAP.put(VjoKeywords.SATISFIES,
//				SatisfiesRobustTranslator.class);
//		MTYPE_SUB_TRANSLATORS_MAP.put(VjoKeywords.PROTOS,
//				ProtosRobustTranslator.class);
//		MTYPE_SUB_TRANSLATORS_MAP.put(VjoKeywords.PROPS,
//				PropsRobustTranslator.class);
//		MTYPE_SUB_TRANSLATORS_MAP.put(VjoKeywords.ENDTYPE,
//				EndTypeRobustTranslator.class);
//	}

//	@Override
//	protected Map<String, Class> getTranslatorMap() {
//		return MTYPE_SUB_TRANSLATORS_MAP;
//	}

	protected void transformType() {
		weakTranslator.getProvider().getTypeTranslator().processMType(
				(MessageSend) current, jst);
		weakTranslator.getCtx().setCurrentType(jst);
	}
}
