/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.ts;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * This class contains helper methods for create list of the {@link IExpr} from
 * the expressions tree.
 * 
 * 
 * 
 */
public class ExpressionListCreator {

	/**
	 * Create list of the {@link IExpr} from the expressions tree.
	 * 
	 * @param expr
	 *            {@link IExpr} object.
	 * @return list of the {@link IExpr}
	 */
	public static List<IExpr> create(Object expr) {
		List<IExpr> list = new ArrayList<IExpr>();
		process(expr, list);
		return list;
	}
	
	private static void process(Object qualifier, List<IExpr> list) {
		if (qualifier == null) {
			return;
		}
		IExpr expression = null;
		if (qualifier instanceof FieldAccessExpr) {
			expression = ((FieldAccessExpr) qualifier).getExpr();
		} else if (qualifier instanceof MtdInvocationExpr) {
			expression = ((MtdInvocationExpr) qualifier).getQualifyExpr();
		} else if (qualifier instanceof JstIdentifier) {
			expression = ((JstIdentifier) qualifier).getQualifier();
		}
		if (expression != null) {
			list.add(0, expression);
			process(expression, list);
		}
		return;
	}
}
