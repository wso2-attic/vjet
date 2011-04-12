/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.term.JstProxyIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstComletionOnMessageSend;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.eclipse.mod.wst.jsdt.core.ast.IExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.AllocationExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;

public class AllocationExpressionTranslator extends BaseAst2JstTranslator<AllocationExpression, ObjCreationExpr> {
	
	private ObjCreationExpr objCreationExpr;

	@Override
	protected ObjCreationExpr doTranslate(AllocationExpression expr) {
		IExpr result = (IExpr) getTranslatorAndTranslate(expr.getMember());
		IExpr[] m_args = new IExpr[0];
		if (expr.arguments != null) {
			m_ctx.enterBlock(ScopeIds.METHOD_CALL);
			m_args = new IExpr[expr.arguments.length];
			Expression[] arguments = expr.arguments;
			for (int i = 0; i < arguments.length; i++) {
				m_args[i] = (IExpr) getTranslatorAndTranslate(arguments[i]);
			}
			m_ctx.exitBlock();
		}
		MtdInvocationExpr mtd = new MtdInvocationExpr(new JstProxyIdentifier(result), m_args);
		mtd.setSource(TranslateHelper.getSource(expr, m_ctx.getSourceUtil()));
		objCreationExpr = new ObjCreationExpr(mtd);
		objCreationExpr.setSource(TranslateHelper.getSource(expr, m_ctx.getSourceUtil()));
		return objCreationExpr;
	}
	
	/* Cursor: "new Date(<cursor>)"
	 * @see org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.BaseAst2JstTranslator#createCompletion(org.eclipse.mod.wst.jsdt.core.ast.IASTNode, boolean)
	 */
	@Override
	protected JstCompletion createCompletion(AllocationExpression node, boolean isAfter) {
		int tempBegin = node.sourceStart();
		String literal = new String(m_ctx.getOriginalSource(), tempBegin, node
				.sourceEnd()
				- tempBegin + 1);
		JstCompletion completion = null;
		int indexOpenBracket = tempBegin + literal.indexOf(OPEN_BRACKET);
		int indexCloseBracket = tempBegin + literal.lastIndexOf(CLOSE_BRACKET);
		if (isInBracket(indexOpenBracket, indexCloseBracket, node)) {
			String token = getTokenInArgurent(node);
			if (isJavaIdentifier(token)) {
				completion = new JstComletionOnMessageSend(objCreationExpr);
				completion.setToken(getTokenInArgurent(node));
				completion.setCompositeToken(getCompositeTokenInArgument(node));
			}
		}
		if (completion != null) {
			m_ctx.setCreatedCompletion(true);
		}

		return completion;
	}
	
	private boolean isInBracket(int indexOpen, int indexClose,
			AllocationExpression astNode) {

		int pos = m_ctx.getCompletionPos();
		boolean isInBracket = false;

		if (indexOpen != -1) {
			isInBracket = pos > indexOpen && pos <= indexClose;
		}

		if (indexOpen != -1 && indexClose != -1 && astNode.statementEnd != -1) {
			isInBracket = pos > indexOpen && pos <= astNode.statementEnd - 1;
		}

		return isInBracket;
	}

	private String getCompositeTokenInArgument(AllocationExpression node) {
//		IExpression[] exprs = node.getArguments();
		IExpression[] exprs = null;
		int pos = m_ctx.getCompletionPos();
		if (exprs == null || exprs.length == 0) {
			return "";
		}
		for (IExpression expr : exprs) {
			if (expr.sourceStart() < pos && expr.sourceEnd() >= pos) {
				String s = new String(m_ctx.getOriginalSource(), expr
						.sourceStart() + 1, expr.sourceEnd()
						- expr.sourceStart() - 1);
				s = (s + "a").trim();
				s = s.substring(0, s.length() - 1);
				return s;
			}
		}
		return "";
	}

	private String getTokenInArgurent(AllocationExpression node) {
//		IExpression[] exprs = node.getArguments();
		IExpression[] exprs = null;
		int pos = m_ctx.getCompletionPos();
		if (exprs == null || exprs.length == 0) {
			return "";
		}
		for (IExpression expr : exprs) {
			if (expr.sourceStart() < pos && expr.sourceEnd() >= pos) {
				String s = new String(m_ctx.getOriginalSource(), expr
						.sourceStart() + 1, pos - expr.sourceStart() - 1);
				s = (s + "a").trim();
				s = s.substring(0, s.length() - 1);
				return s;
			}
		}
		return "";
	}
}
