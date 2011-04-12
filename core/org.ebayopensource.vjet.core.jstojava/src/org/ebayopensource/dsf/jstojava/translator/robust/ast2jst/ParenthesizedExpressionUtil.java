/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.expr.ArithExpr;
import org.ebayopensource.dsf.jst.expr.ParenthesizedExpr;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ASTNode;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;

public class ParenthesizedExpressionUtil {
	
	public static ArithExpr convert(Expression astExpr, ArithExpr expr) {
		if ((astExpr.bits & ASTNode.ParenthesizedMASK) == 0) {
			return expr;
		}
		// decrement the number of parenthesis
		int numberOfParenthesis = (astExpr.bits & 
				ASTNode.ParenthesizedMASK) >> ASTNode.ParenthesizedSHIFT;
		astExpr.bits &= ~ASTNode.ParenthesizedMASK;
		astExpr.bits |= (numberOfParenthesis - 1) << ASTNode.ParenthesizedSHIFT;
		ParenthesizedExpr pe = new ParenthesizedExpr(expr);
		return convert(astExpr, pe);
	}

}
