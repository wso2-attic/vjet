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

public class OptionsRobustTranslator extends CompletionsFilteredRobustTranslator {
	private static final Expression[] EMPTYPROPS = {new ObjectLiteral()};
	
	public OptionsRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}

	public boolean transform() {
		current = astElements.pop();
		checkOnNameCompletion();

		Expression[] arguments = ((MessageSend) current).arguments;
		if(arguments==null){
			arguments = EMPTYPROPS;
		}
		weakTranslator.getProvider().getOptionsTranslator().process(
			arguments[0], jst);
		
		m_ctx.setPreviousNodeSourceEnd(current.sourceEnd());
		// lookup possible empty completions
		lookupEmptyCompletion();

		// move to the next iteration
		return super.transform();
	}
}
