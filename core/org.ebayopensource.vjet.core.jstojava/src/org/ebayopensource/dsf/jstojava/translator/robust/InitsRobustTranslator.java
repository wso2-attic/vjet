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
import org.eclipse.mod.wst.jsdt.core.ast.IProgramElement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;

class InitsRobustTranslator extends BaseRobustTranslator {


	public InitsRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}


	public boolean transform() {

		// remove inits("") element from the stack
		current = astElements.pop();

		// TODO check if this keyword has already been processed
		// if true put it into error chunk

		if (((MessageSend)current).arguments != null) {
			weakTranslator.getProvider().getInitsTranslator()
				.process(((MessageSend)current).arguments[0], jst);
		}
		
		lookupEmptyCompletion();
		
		// move to the next iteration
		return super.transform();

	}

	public IRobustTranslator getSubTranslator(IProgramElement element) {
		
		// to make no sense check the type of incoming element
		// this translator haven't any sub translators
		//Jack: original return null, will cause syntax error. now call super method
		return super.getSubTranslator(element);

	}

	
}
