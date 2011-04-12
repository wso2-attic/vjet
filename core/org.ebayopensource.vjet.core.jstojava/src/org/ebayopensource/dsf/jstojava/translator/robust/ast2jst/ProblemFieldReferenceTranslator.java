/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnQualifiedNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FieldReference;

public class ProblemFieldReferenceTranslator extends
		BaseAst2JstProblemTranslator<FieldReference, BaseJstNode> {

	@Override
	protected BaseJstNode doTranslate(FieldReference expr) {
		String identifierStr = String.valueOf(expr.token);
		if(TranslateHelper.MISSING_TOKEN.equals(identifierStr)) {
			identifierStr = "";
		}
		JstIdentifier identifier = new JstIdentifier(identifierStr);
		int nameLength = identifier.getName().length();
		JstSource sourceIden = TranslateHelper.getSource(expr.sourceEnd,
				nameLength);
		identifier.setSource(sourceIden);

		FieldAccessExpr far = new FieldAccessExpr(identifier);

		IExpr qualifier = (IExpr) getTranslatorAndTranslate(expr.receiver, far);
		far.setExpr(qualifier);
		JstSource source = TranslateHelper.getSource(expr, m_ctx.getSourceUtil());
		far.setSource(source);
		this.setParent(far);

		return far;
	}

	@Override
	protected JstCompletion createCompletion(FieldReference astFieldRef,
			boolean isAfterSource) {
		int completionPos = m_ctx.getCompletionPos();
		if (completionPos > m_problemStart) {
			char[] source = m_ctx.getOriginalSource();

			JstCompletion completion = new JstCompletionOnQualifiedNameReference(
					 m_parent);					

			int realStart = astFieldRef.sourceStart;
			completion.setSource(createSource(realStart,
					astFieldRef.sourceEnd + 1, m_ctx.getSourceUtil()));
			String wholeWord = new String(source, realStart,
					astFieldRef.sourceEnd - realStart + 1);
			String prefix = new String(source, realStart, completionPos
					- realStart);

			completion.setCompositeToken(wholeWord);
			completion.setToken(prefix);

			m_ctx.setCompletionPos(-1);
			return completion;
		}
		return null;
	}
}
