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

class FTypeRobustTranslator extends TypeRobustTranslator {

	public FTypeRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}

	public String[] filter(String[] completions) {

		List<String> filtered = new ArrayList<String>();

		String[] baseFiltered = super.filter(completions);

		for (String completion : baseFiltered) {
			if (completion.equals(VjoKeywords.PROPS)
					|| completion.equals(VjoKeywords.INITS)
					|| completion.equals(END_TYPE)) {
				filtered.add(completion);
			}
		}

		return filtered.toArray(new String[] {});
	}

	protected void transformType() {
		weakTranslator.getProvider().getTypeTranslator().processFType(
				(MessageSend) current, jst);
		weakTranslator.getCtx().setCurrentType(jst);
	}
}
