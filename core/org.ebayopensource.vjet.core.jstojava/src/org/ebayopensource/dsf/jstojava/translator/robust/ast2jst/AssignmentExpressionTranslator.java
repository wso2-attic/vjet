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
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr.Operator;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.core.ast.IExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Assignment;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompoundAssignment;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteral;

public class AssignmentExpressionTranslator extends
		BaseAst2JstTranslator<Assignment, AssignExpr> {
	@Override
	protected AssignExpr doTranslate(Assignment assign) {
		Object lhs = getTranslatorAndTranslate(assign.getLeftHandSide());
		if (!(lhs instanceof ILHS)) {
			return null;
		}
		IExpression expr = assign.getExpression();
		if (expr instanceof ObjectLiteral) {
			m_ctx.enterBlock(ScopeIds.PROPERTY);
		}
		try {
			Object rhs = getTranslatorAndTranslate(assign.getExpression());
			if (!(rhs instanceof IExpr)) {
				return null;
			} else {
				rhs = TranslateHelper.getCastable((IExpr)rhs, assign, m_ctx);
			}
			Operator operator = getOperator(assign);
			return new AssignExpr((ILHS) lhs, (IExpr) rhs, operator);
		} finally {
			if (expr instanceof ObjectLiteral) {
				m_ctx.exitBlock();
			}
		}
	}

	private Operator getOperator(Assignment assign) {
		if (assign instanceof CompoundAssignment) {
			return Operator.toOperator(((CompoundAssignment) assign)
					.operatorToString());
		}
		return Operator.ASSIGN;
	}
}
