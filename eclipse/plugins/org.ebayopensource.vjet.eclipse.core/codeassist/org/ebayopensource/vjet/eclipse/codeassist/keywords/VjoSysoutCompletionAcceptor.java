/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;

/**
 * This class accept or decline {@link VjoKeywordFactory#KWD_PRINT} and
 * {@link VjoKeywordFactory#KWD_PRINTLN} completion keywords.
 * 
 * 
 * 
 */
public class VjoSysoutCompletionAcceptor extends CompletionAcceptor {
	
	@Override	
	boolean accept(int position, JstCompletion completion) {
		boolean isAccept = false;
		IJstNode node = completion.getRealParent();

		if (node != null && node instanceof FieldAccessExpr) {
			FieldAccessExpr expr = (FieldAccessExpr) node;
			final String str = expr.getExpr().toString();
			isAccept = str.equals("vjo.sysout") || str.equals("vjo.syserr");
		}

		if (node != null && node instanceof MtdInvocationExpr) {
			MtdInvocationExpr expr = (MtdInvocationExpr) node;
			final String str = expr.getQualifyExpr().toString();
			isAccept = str.equals("vjo.sysout") || str.equals("vjo.syserr");
		}
		return isAccept;
	}
}
