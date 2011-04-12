/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.util.Stack;

import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jstojava.translator.BaseTranslator;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstKeywordCompletion;
import org.eclipse.mod.wst.jsdt.core.ast.IProgramElement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FieldReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.SingleNameReference;
import org.ebayopensource.vjo.meta.VjoKeywords;

class RootRobustTranslator extends BaseRobustTranslator {


	public RootRobustTranslator(Stack<IProgramElement> astElements, JstType jst,
			BaseTranslator weakTranslator, IntegerHolder completionPos) {
		super(weakTranslator.getCtx());
		configure(astElements, jst, weakTranslator, completionPos);
	}


	private static void createRootCompletion(JstType jst, int end, JstSourceUtil util) {

		JstKeywordCompletion vjoKeyword = new JstKeywordCompletion(jst,
				new String[] { VjoKeywords.VJO });
		vjoKeyword.setSource(TranslateHelper.createJstSource( util,
				"JstKeywordCompletion".length(), 0, end));
		vjoKeyword.setCompositeToken(EMPTY);
		vjoKeyword.setToken(EMPTY);

		vjoKeyword.setParent(jst);
	}

	static boolean preTransform(JstType jst, TranslateCtx ctx, int completionPos) {

		boolean completed = false;
		char[] origin = ctx.getOriginalSource();
		if (new String(origin).trim().equals(EMPTY)) {
			createRootCompletion(jst, origin.length, ctx.getSourceUtil());
			completed = true;
		} else {
			for (int iter = 0; iter < origin.length; iter++) {

				if (!Character.isWhitespace(origin[iter])) {

					if (completionPos >= 0 && completionPos <= iter) {

						createRootCompletion(jst, iter, ctx.getSourceUtil());

						completed = true;
					} // if (completionPos >= 0 && completionPos <= iter)

					break;

				} // if (!Character.isWhitespace(origin[iter]))

			} // for (int iter = 0; iter < origin.length; iter++) {
		}

		return completed;
	}

	public boolean transform() {

		char[] origin = weakTranslator.getCtx().getOriginalSource();
		
		if(astElements.size()<=0){
			return false;
		}
		
		int start = ((Expression) astElements.firstElement()).statementEnd;

		// move to the next iteration
		super.transform();

		// check forward possible completion

		if (start > -1 && start < origin.length) {

			char lastChar = origin[start];
			int end = start + 1;

			while (end < origin.length) {

				if (!Character.isWhitespace(origin[end])) {
					break;
				} else {
					end++;
				}

			}

			if (lastChar != '.' && completionPos.value() >= start
					&& completionPos.value() <= end) {
				setKeywordCompletion(start, end, EMPTY, EMPTY,
						getAllowedTokens());
			}
		}
		return false;
	}

	public IRobustTranslator getSubTranslator(IProgramElement element) {

		IRobustTranslator subTranslator = null;

		if (element instanceof SingleNameReference
				|| element instanceof FieldReference) {

			subTranslator = instantiateTranslator(element);

		}

		return subTranslator;
	}

}
