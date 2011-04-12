/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.OperatorIds;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.UnaryExpression;

/**
 * 
 * 
 */
public class UnaryExpressionTranslator extends
		BaseAst2JstTranslator<UnaryExpression, PrefixExpr> {

	/*
	 * @see org.ebayopensource.dsf.jstojava.translator2.robust.ast2jst.BaseAst2JstTranslator#doTranslate(org.eclipse.mod.wst.jsdt.core.ast.IASTNode)
	 */
	@Override
	protected PrefixExpr doTranslate(UnaryExpression astUnaryExpression) {
		int operator = (astUnaryExpression.bits & UnaryExpression.OperatorMASK) >> UnaryExpression.OperatorSHIFT;
		PrefixExpr.Operator prefixExprOperator = s_operatorMapping.get(operator);
		if (prefixExprOperator != null) {
			IExpr expr = (IExpr) getTranslatorAndTranslate(astUnaryExpression.expression);
			return new PrefixExpr(expr, prefixExprOperator);
		}
		return null;
	}
	
	private static Map<Integer, PrefixExpr.Operator> s_operatorMapping = new HashMap<Integer, PrefixExpr.Operator>();
	static {
		s_operatorMapping.put(OperatorIds.NOT, PrefixExpr.Operator.NOT);
		s_operatorMapping.put(OperatorIds.MINUS, PrefixExpr.Operator.MINUS);
		s_operatorMapping.put(OperatorIds.PLUS, PrefixExpr.Operator.PLUS);
		s_operatorMapping.put(OperatorIds.PLUS_PLUS, PrefixExpr.Operator.INCREMENT);
		s_operatorMapping.put(OperatorIds.MINUS_MINUS, PrefixExpr.Operator.DECREMENT);
		s_operatorMapping.put(OperatorIds.TWIDDLE, PrefixExpr.Operator.COMPLEMENT);
		s_operatorMapping.put(OperatorIds.TYPEOF, PrefixExpr.Operator.TYPEOF);
		s_operatorMapping.put(OperatorIds.DELETE, PrefixExpr.Operator.DELETE);
		s_operatorMapping.put(OperatorIds.VOID, PrefixExpr.Operator.VOID);
	}

}
