/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.expr.ArrayAccessExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ArrayReference;

public class ArrayReferenceTranslator extends BaseAst2JstTranslator<ArrayReference, ArrayAccessExpr> {

	@Override
	protected ArrayAccessExpr doTranslate(ArrayReference arrayRef) {
		IExpr indexExpr = (IExpr) getTranslatorAndTranslate(arrayRef.position);
		IExpr expr = (IExpr) getTranslatorAndTranslate(arrayRef.receiver);
		ArrayAccessExpr arrayAccessExpr = new ArrayAccessExpr(expr, indexExpr);
		JstSource source = TranslateHelper.getSource(arrayRef, m_ctx.getSourceUtil());
		arrayAccessExpr.setSource(source);
		return arrayAccessExpr;
	}
}
