/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.IJstNode;
import org.eclipse.mod.wst.jsdt.core.ast.IExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ConditionalExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteralField;

public class ProblemConditionalExpressionTranslator extends
		BaseAst2JstProblemTranslator<ConditionalExpression, IJstNode> {

	@Override
	protected IJstNode doTranslate(
			ConditionalExpression astConditionalExpression) {

		IExpression condition = astConditionalExpression.condition;
		IJstNode res = (IJstNode) getTranslatorAndTranslate(condition);

		ObjectLiteralField recoveredField = new ObjectLiteralField(
				astConditionalExpression.valueIfTrue,
				astConditionalExpression.valueIfFalse,
				astConditionalExpression.valueIfTrue.sourceStart,
				astConditionalExpression.valueIfFalse.sourceEnd);
		m_recoveredNodes.add(recoveredField);

		return res;
	}
}
