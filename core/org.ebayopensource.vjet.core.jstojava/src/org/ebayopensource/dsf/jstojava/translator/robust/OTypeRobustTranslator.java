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

public class OTypeRobustTranslator extends TypeRobustTranslator{
	public OTypeRobustTranslator(TranslateCtx ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

//	private static final Map<String, Class> OTYPE_SUB_TRANSLATORS_MAP = new HashMap<String, Class>();

	public String[] filter(String[] completions) {

		List<String> filtered = new ArrayList<String>();

		String[] baseFiltered = super.filter(completions);

		for (String completion : baseFiltered) {
			if (completion.equals(VjoKeywords.DEFS)
					|| completion.equals(END_TYPE)) {
				filtered.add(completion);
			}
		}

		return filtered.toArray(new String[] {});
	}

//	static {
//		OTYPE_SUB_TRANSLATORS_MAP.put(VjoKeywords.DEFS,
//				DefsRobustTranslator.class);
//		OTYPE_SUB_TRANSLATORS_MAP.put(VjoKeywords.ENDTYPE,
//				EndTypeRobustTranslator.class);
//	}

//	@Override
//	protected Map<String, Class> getTranslatorMap() {
//		return OTYPE_SUB_TRANSLATORS_MAP;
//	}

	protected void transformType() {
		weakTranslator.getProvider().getTypeTranslator().processOType(
				(MessageSend) current, jst);
		weakTranslator.getCtx().setCurrentType(jst);
	}
}
