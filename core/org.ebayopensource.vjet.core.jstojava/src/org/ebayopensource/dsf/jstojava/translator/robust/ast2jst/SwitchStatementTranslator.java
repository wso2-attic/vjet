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
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.stmt.ExprStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnSingleNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Statement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.SwitchStatement;

public class SwitchStatementTranslator extends
		BaseAst2JstTranslator<SwitchStatement, SwitchStmt> {

	private static final String SPACE = "";
	private static final char SQUARE_OPEN_BRACKET = '{';

	@Override
	protected SwitchStmt doTranslate(SwitchStatement statement) {
		SwitchStmt switchStmt = new SwitchStmt();
		if (m_parent != null){
			m_parent.addChild(switchStmt);
		}
		try {
			m_ctx.enterBlock(ScopeIds.SWITCH);
			doTranslate(statement, switchStmt);
		} finally {
			m_ctx.exitBlock();
		}

		return switchStmt;
	}

	private void doTranslate(SwitchStatement statement, SwitchStmt switchStmt) {
		IExpr expr = (IExpr) getTranslatorAndTranslate(statement.expression,
				switchStmt);
		switchStmt.setExpr(expr);
		JstSource source = TranslateHelper.createJstSource(m_ctx.getSourceUtil(), statement
				.sourceEnd()
				- statement.sourceStart(), statement.sourceStart(), statement
				.sourceEnd());
		switchStmt.setSource(source);
		// process statements
		if (statement.statements != null) {
			for (Statement stmt : statement.statements) {
				Object obj = getTranslatorAndTranslate(stmt, switchStmt);
				if (obj != null && obj instanceof IStmt) {
					IStmt st = (IStmt) obj;
					switchStmt.addStmt(st);
				} else if (obj instanceof FieldAccessExpr) {
					FieldAccessExpr st = (FieldAccessExpr) obj;
					switchStmt.addStmt(new ExprStmt(st));
				}
			}
		}
	}

	@Override
	protected JstCompletion createCompletion(SwitchStatement astNode,
			boolean isAfterSource) {

		JstType node = m_ctx.getCurrentType();
		JstCompletion completion = new JstCompletionOnSingleNameReference(node);
		completion.setToken(SPACE);
		completion.setSource(createSource(m_ctx.getCompletionPos(), m_ctx.getCompletionPos(), m_ctx.getSourceUtil()));
		completion.setScopeStack(m_ctx.getScopeStack());
		char[] cs = m_ctx.getOriginalSource();

		if (!Character.isWhitespace(cs[m_ctx.getCompletionPos() - 1])) {
			String token = getToken(cs, m_ctx.getCompletionPos() - 1);
			completion.setToken(token);
			completion.setSource(createSource(m_ctx.getCompletionPos()-token.length(), m_ctx.getCompletionPos(), m_ctx.getSourceUtil()));
		}

		m_ctx.setCreatedCompletion(true);
		completion.pushScope(ScopeIds.SWITCH);

		return completion;
	}

	private String getToken(char[] cs, int completionPos) {

		StringBuilder builder = new StringBuilder();

		for (int i = completionPos; i > 0; i--) {

			if (Character.isWhitespace(cs[i])
					|| isSquareOpenBracket(cs[i])) {
				break;
			}

			builder.insert(0,cs[i]);
		}

		return builder.toString();
	}

	private boolean isSquareOpenBracket(char c) {
		return c == SQUARE_OPEN_BRACKET;
	}
}
