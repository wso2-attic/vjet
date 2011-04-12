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
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteral;

class GlobalsRobustTranslator extends CompletionsFilteredRobustTranslator {

	private static final Expression[] EMPTYPROPS = {new ObjectLiteral()};
	

	public GlobalsRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}



	public boolean transform() {

		// remove protos("") element from the stack
		current = astElements.pop();

		checkOnNameCompletion();
		// TODO change it in the future
		// check if this keyword has already been processed
		// if true put it into error chunk
		Expression[] arguments = ((MessageSend) current).arguments;
		if(arguments==null ){
			arguments = EMPTYPROPS;
		}
		if (arguments.length == 2) {
			String scopeForGlobals = arguments[1].toString();
			m_ctx.setScopeForGlobals(scopeForGlobals);
		}
		weakTranslator.getProvider().getGlobalsTranslator().process(
			arguments[0], jst);

		m_ctx.setPreviousNodeSourceEnd(current.sourceEnd());
		// lookup possible empty completions
		lookupEmptyCompletion();

		// move to the next iteration
		return super.transform();

	}

}
