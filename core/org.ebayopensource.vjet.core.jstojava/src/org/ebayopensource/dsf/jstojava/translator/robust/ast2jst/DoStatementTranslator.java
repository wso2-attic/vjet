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
import org.ebayopensource.dsf.jst.stmt.DoStmt;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.DoStatement;

public class DoStatementTranslator extends
		BaseAst2JstTranslator<DoStatement, DoStmt> {

	@Override
	protected DoStmt doTranslate(DoStatement statement) {
		DoStmt doStmt = new DoStmt();
		if (m_parent != null){
			m_parent.addChild(doStmt);
		}
		try {
			m_ctx.enterBlock(ScopeIds.LOOP);
			doTranslate(statement, doStmt);
		} finally {
			m_ctx.exitBlock();
		}

		return doStmt;
	}

	private void doTranslate(DoStatement statement, DoStmt doStmt) {
		if (!statement.isEmptyBlock()) {
			getTranslatorAndTranslate(statement.action, doStmt.getBody());
		}
		if (statement.condition != null) {
			IExpr cond = (IExpr) getTranslatorAndTranslate(statement.condition,
					doStmt);
			doStmt.setCondition(TranslateHelper.buildCondition(cond));
		}
		doStmt.setSource(TranslateHelper.getSource(statement, m_ctx.getSourceUtil()));
	}
}
