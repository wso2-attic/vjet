/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstSatisfiesOnTypeCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstTypeCompletion;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;

class SatisfiesRobustTranslator extends CompletionsFilteredRobustTranslator {


	public SatisfiesRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}

	protected final JstTypeCompletion createOnTypeCompletion() {
		return new JstSatisfiesOnTypeCompletion(jst);
	}

	public boolean transform() {

		// remove satisfies("") element from the stack
		current = astElements.pop();

		// move to the next iteration
		return super.transform();

	}

	@Override
	protected void processCompletion() {
		weakTranslator.getProvider().getTypeTranslator().processSatisfies(
				(MessageSend) current, jst);
	}
}