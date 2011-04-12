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
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstMixinOnTypeCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstTypeCompletion;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;

public class MixinRobustTranslator extends CompletionsFilteredRobustTranslator{

	public MixinRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}


	protected final JstTypeCompletion createOnTypeCompletion() {
		return new JstMixinOnTypeCompletion(jst);
	}
	
	public boolean transform() {
		// remove mixin("") element from the stack
		current = astElements.pop();
		
		checkOnNameCompletion();
		
		//TODO change it in the future
		// check if this keyword has already been processed
		// if true put it into error chunk
		weakTranslator.getProvider().getTypeTranslator()
		.processMixins(((MessageSend) current), jst);

		// lookup possible empty completions
		lookupEmptyCompletion();

		// move to the next iteration
		return super.transform();
	}
}
