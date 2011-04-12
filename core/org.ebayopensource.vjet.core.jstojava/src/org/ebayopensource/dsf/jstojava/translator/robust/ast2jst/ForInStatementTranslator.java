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
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.stmt.ForInStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ForInStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Statement;

public class ForInStatementTranslator extends BaseAst2JstTranslator<ForInStatement, ForInStmt> {

	@Override
	protected ForInStmt doTranslate(ForInStatement statement) {
		
		IExpr expr = (IExpr)getTranslatorAndTranslate(statement.collection, m_parent);
		if (expr instanceof BaseJstNode) {
			((BaseJstNode)expr).setSource(TranslateHelper.getSource(statement.collection, m_ctx.getSourceUtil()));
		}
		Statement varStatement = statement.iterationVariable;
		ForInStmt stmt;
		if (varStatement instanceof LocalDeclaration) {
			LocalDeclaration locdec = (LocalDeclaration) statement.iterationVariable;
			JstVar var = new JstVar((IJstType)null, String.valueOf(locdec.getName()));
			var.setSource(TranslateHelper.getSource(varStatement, m_ctx.getSourceUtil()));
			stmt = new ForInStmt(var, expr);
		}
		else { //SingleNameReference
			JstIdentifier var = new JstIdentifier(((SingleNameReference)varStatement).toString());
			var.setSource(TranslateHelper.getSource(varStatement, m_ctx.getSourceUtil()));
			stmt = new ForInStmt(var, expr);
		}
		
		if (m_parent != null){
			m_parent.addChild(stmt);
		}
		if (!statement.isEmptyBlock()) {
			getTranslatorAndTranslate(statement.action, stmt.getBody());
		}
		stmt.setSource(TranslateHelper.getSource(statement, m_ctx.getSourceUtil()));
		return stmt;
	}

}
