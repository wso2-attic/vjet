/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;

class ProtosRobustTranslator extends CompletionsFilteredRobustTranslator {

	private static final Map<String, Class> PROTOS_SUB_TRANSLATORS_MAP = new HashMap<String, Class>();



	public ProtosRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}



	public boolean transform() {

		// remove protos("") element from the stack
		current = astElements.pop();
		
		checkOnNameCompletion();

		// TODO change it in the future
		// check if this keyword has already been processed
		// if true put it into error chunk

		if (((MessageSend) current).arguments != null) {
			weakTranslator.getProvider().getProtosTranslator().process(
					((MessageSend) current).arguments[0], jst);
		}
		m_ctx.setPreviousNodeSourceEnd(current.sourceEnd());
		// translate(weakTranslator.getCtx());

		// lookup possible empty completions
		lookupEmptyCompletion();

		// move to the next iteration
		return super.transform();

	}
}
