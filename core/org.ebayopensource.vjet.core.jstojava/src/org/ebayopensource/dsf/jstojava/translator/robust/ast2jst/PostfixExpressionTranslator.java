/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr.Operator;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.PostfixExpression;

public class PostfixExpressionTranslator extends BaseAst2JstTranslator<PostfixExpression, PostfixExpr> {

	@Override
	protected PostfixExpr doTranslate(PostfixExpression expr) {
		IExpr e = (IExpr) getTranslatorAndTranslate(expr.getLeftHandSide(), m_parent);
		return new PostfixExpr(e, Operator.toOperator(expr.operatorToString()));
	}

}
