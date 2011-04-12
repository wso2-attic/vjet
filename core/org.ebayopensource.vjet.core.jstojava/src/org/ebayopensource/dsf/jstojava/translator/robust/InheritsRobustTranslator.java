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
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstInheritsOnTypeCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstTypeCompletion;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;

class InheritsRobustTranslator extends CompletionsFilteredRobustTranslator {


	public InheritsRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}


	protected final JstTypeCompletion createOnTypeCompletion() {
		return new JstInheritsOnTypeCompletion(jst);
	}

	public boolean transform() {

		// TODO CHECK TO SEE IF THIS IS ALREADY COVERED IN VALIDATION RULES
		if (!jst.isInterface() && jst.getExtend() != null) {
			// redundant inherits detected
			// put it into error chunk
			getErrorCollector().collect();

		} else {
			// remove inherits("") element from the stack
			current = astElements.pop();
				
			// move to the next iteration
			super.transform();

		}
		return false;
	}
	
	@Override
	protected void processCompletion() {
		weakTranslator.getProvider().getTypeTranslator()
		.processInherits((MessageSend) current,
				jst);
	}
}
