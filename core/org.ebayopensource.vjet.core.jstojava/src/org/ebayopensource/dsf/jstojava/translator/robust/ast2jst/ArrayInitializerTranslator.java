/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;

public class ArrayInitializerTranslator extends
		BaseAst2JstTranslator<ArrayInitializer, IExpr> {

	@Override
	protected IExpr doTranslate(ArrayInitializer astInitializer) {
		JstArrayInitializer arrayInitializer = new JstArrayInitializer();
		TranslateHelper.addSourceInfo(astInitializer, arrayInitializer, m_ctx.getSourceUtil());

		if (astInitializer.expressions != null) {
			int len = astInitializer.expressions.length;
			for (int i=0; i<len; i++) {
				Expression astExpresion =  astInitializer.expressions[i];
				IExpr jstExpression = (IExpr) getTranslatorAndTranslate(astExpresion);
				arrayInitializer.add(jstExpression);
			}
		}
		
		//TODO by huzhou@ebay.com
		//the JstArrayInitializer should also consume the Meta
		//and set the array type correspondingly
		//will check the impacts

		return arrayInitializer;
	}
}
