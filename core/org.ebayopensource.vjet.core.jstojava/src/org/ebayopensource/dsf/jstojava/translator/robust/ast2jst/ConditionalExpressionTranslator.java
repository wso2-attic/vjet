/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.expr.ConditionalExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ConditionalExpression;

public class ConditionalExpressionTranslator extends BaseAst2JstTranslator<ConditionalExpression, org.ebayopensource.dsf.jst.expr.ConditionalExpr> {

	@Override
	protected ConditionalExpr doTranslate(ConditionalExpression astNode) {
		IExpr condition = (IExpr) getTranslatorAndTranslate(astNode.condition, m_parent);
		IExpr thenv = (IExpr) getTranslatorAndTranslate(astNode.valueIfTrue, m_parent);
		IExpr elsev = (IExpr) getTranslatorAndTranslate(astNode.valueIfFalse, m_parent);
		return new ConditionalExpr(TranslateHelper.buildCondition(condition), thenv, elsev);
	}

}
