/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.expr.ListExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ListExpression;

public class ListExpressionTranslator extends
		BaseAst2JstTranslator<ListExpression, ListExpr> {

	@Override
	protected ListExpr doTranslate(ListExpression astNode) {		
		Expression[] expressions = astNode.expressions;
		IExpr[] jstExprArr = new IExpr[expressions.length];
		for (int i = 0; i < expressions.length; i++) {
			Object term = getTranslatorAndTranslate(expressions[i]);
			if (term instanceof IExpr) {
				jstExprArr[i] = (IExpr)term;
			}
			else {
				throw new RuntimeException(term == null ? 
					expressions[i].toString() + " can't be translated " : term.toString());
			}
		}		
		return new ListExpr(jstExprArr);
	}
}
