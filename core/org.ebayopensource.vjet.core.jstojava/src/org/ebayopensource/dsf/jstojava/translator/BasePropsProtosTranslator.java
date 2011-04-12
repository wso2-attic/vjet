/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.BaseAst2JstTranslator;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.TranslatorFactory;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.VjoOLTranslator;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CharLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.StringLiteral;

abstract class BasePropsProtosTranslator extends BaseTranslator {
	protected ScopeId type;

	public BasePropsProtosTranslator(TranslateCtx ctx) {
		super(ctx);
	}

	public void process(Expression expr, JstType jstType) {
		assert expr != null;
		if (expr instanceof ObjectLiteral
				|| expr instanceof StringLiteral
				|| expr instanceof CharLiteral) {
			translateExpr(expr, jstType);
		} else {
			System.err.println("Unprocessed type in " + getClass().getName());
		}
	}

	protected void translateExpr(Expression expr, JstType jstType) {
		getCtx().enterBlock(type);
		try {
			BaseAst2JstTranslator translator;
			if (expr instanceof ObjectLiteral) {
				translator = new VjoOLTranslator(getCtx());
			} else {
				//TODO - Throw error here...
				translator = TranslatorFactory.getTranslator(expr, getCtx());
			}
			translator.setParent(jstType);
			translator.translate(expr);
		} finally {
			getCtx().exitBlock();
		}
	}
}
