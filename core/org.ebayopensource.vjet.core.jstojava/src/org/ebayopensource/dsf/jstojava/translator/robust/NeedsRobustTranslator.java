/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstNeedsOnTypeCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstTypeCompletion;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;

class NeedsRobustTranslator extends CompletionsFilteredRobustTranslator {


	public NeedsRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}


	public boolean transform() {

		// remove needs("") element from the stack
		current = astElements.pop();

		if (!checkOnTypeCompletion()) {
			weakTranslator.getProvider().getTypeTranslator().processNeeds(
					((MessageSend) current).getArguments(), jst);
		}

		//Revisit type definition and update param bounds if any...
		for (JstParamType itm : jst.getParamTypes()) {
			for (IJstType bound : itm.getBounds()) {
				String bnm = bound.getName();
				IJstType newType = TranslateHelper.findType(m_ctx, bnm);
				if (!bound.equals(newType)) {
					itm.updateBound(bnm, newType);
				}
			}
		}
		// move to the next iteration
		return super.transform();

	}

	protected final JstTypeCompletion createOnTypeCompletion() {
		return new JstNeedsOnTypeCompletion(jst);
	}

}
