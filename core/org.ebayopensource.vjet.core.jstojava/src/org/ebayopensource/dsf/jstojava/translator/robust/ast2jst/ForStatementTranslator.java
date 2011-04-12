/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.JstInitializer;
import org.ebayopensource.dsf.jst.stmt.ForStmt;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ForStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Statement;

public class ForStatementTranslator extends
		BaseAst2JstTranslator<ForStatement, ForStmt> {

	@Override
	protected ForStmt doTranslate(ForStatement statement) {
		assert statement != null;
		ForStmt forStmt = new ForStmt();
		if (m_parent != null) {
			m_parent.addChild(forStmt);
		}
		try {
			m_ctx.enterBlock(ScopeIds.LOOP);
			doTranslate(statement, forStmt);
		} finally {
			m_ctx.exitBlock();
		}
		return forStmt;
	}

	private void doTranslate(ForStatement statement, ForStmt forStmt) {
		if (statement.initializations != null
				&& statement.initializations.length > 0) {
			Statement[] inits = statement.initializations;
			List<AssignExpr> list = new ArrayList<AssignExpr>();
			boolean isLocalDeclaration = false;
			for (Statement initializer : inits) {
				if (initializer.getASTType() == IASTNode.LOCAL_DECLARATION) {
					isLocalDeclaration = true;
				}
				Object jstVar = getTranslatorAndTranslate(initializer, forStmt);
				if (jstVar instanceof JstVars) {
					List<AssignExpr> initializers = ((JstVars) jstVar)
							.getAssignments();
					for (AssignExpr jstInitializer : initializers) {
						list.add(jstInitializer);
					}
					// } else if (jstVar instanceof JstInitializer) {
					// list.add(jstVar);
				} else if (jstVar instanceof AssignExpr) {
					AssignExpr assignExpr = (AssignExpr) jstVar;
					// JstInitializer init = new JstInitializer(assignExpr
					// .getLHS(), assignExpr.getExpr());
					list.add(assignExpr);
				} else {
					System.err.println("Unprocessed type: "
							+ initializer.getClass()
							+ " in ForStatementTranslator");
				}

			}
			if (isLocalDeclaration) { // initializer is a local declaration: for (var i = 0;;;)
				JstType obj =JstCache.getInstance().getType("Object");
				if(obj==null){
					obj = JstFactory.getInstance().createJstType(
							"Object", true);
				}
				JstVars vars = 
					new JstVars(obj);
				for (AssignExpr assignExpr : list) {
					vars.addAssignment(assignExpr);
				}
				forStmt.setInitializer(vars);
			} else { // initializer is assignment: for (i = 0;;;)
				JstInitializer jstInitializer = null;
				for (AssignExpr assignExpr : list) {
					if (jstInitializer == null){
						jstInitializer = new JstInitializer();
						forStmt.setInitializer(jstInitializer);
					}
					jstInitializer.addAssignment(assignExpr);
				}
				forStmt.setInitializer(jstInitializer);
			}
		}
		if (statement.condition != null) {
			IExpr cond = (IExpr) getTranslatorAndTranslate(statement.condition,
					forStmt);
			forStmt.setCondition(TranslateHelper.buildCondition(cond));
		}
		if (statement.increments != null) {
			for (Statement increment : statement.increments) {
				forStmt.addUpdater((IExpr) getTranslatorAndTranslate(increment,
						forStmt));
			}
		}
		if (!statement.isEmptyBlock()) {
			Object obj = getTranslatorAndTranslate(statement.action, forStmt.getBody());
			if(!(obj instanceof JstBlock)){
				forStmt.getBody().addStmt((IStmt)obj);
			}
		}
		forStmt.setSource(TranslateHelper.getSource(statement, m_ctx.getSourceUtil()));
	}
}
