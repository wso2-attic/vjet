/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.stmt.IfStmt;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.IfStatement;

public class IfStatementTranslator extends BaseAst2JstTranslator<IfStatement, IfStmt> {

	@Override
	protected IfStmt doTranslate(IfStatement statement) {
		assert statement != null;
		IfStmt ifStmt = new IfStmt();
		if (m_parent != null){
			m_parent.addChild(ifStmt);
		}
		if (statement.condition != null) {
			IExpr cond = (IExpr) getTranslatorAndTranslate(statement.condition, ifStmt);
			ifStmt.setCondition(TranslateHelper.buildCondition(cond));
		}
		if (statement.thenStatement != null) {
			Object node = getTranslatorAndTranslate(statement.thenStatement, ifStmt.getBody());
			if (node instanceof IStmt) {
				ifStmt.addThenStmt((IStmt)node);
			}
		}
		if (statement.elseStatement != null) {
			if (statement.elseStatement instanceof IfStatement) {
				ifStmt.addElseStmt((IfStmt) getTranslatorAndTranslate(statement.elseStatement, ifStmt));
			} else {
				Object node = getTranslatorAndTranslate(statement.elseStatement, ifStmt.getElseBlock(true));
				if (node instanceof IStmt) {
					ifStmt.addElseStmt((IStmt)node);
				}				
			}
		}
		ifStmt.setSource(TranslateHelper.getSource(statement, m_ctx.getSourceUtil()));
		return ifStmt;
	}

}
