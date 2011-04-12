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

import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.stmt.WithStmt;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.WithStatement;

/**
 * 
 * 
 */
public class WithStatementTranslator extends
		BaseAst2JstTranslator<WithStatement, WithStmt> {

	/**
	 * 
	 */
	public WithStatementTranslator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected WithStmt doTranslate(WithStatement astNode) {
		WithStmt withStmt = new WithStmt();
		if (astNode.condition != null) {
			IExpr cond = (IExpr) getTranslatorAndTranslate(astNode.condition,
					withStmt);
			withStmt.setCondition(TranslateHelper.buildCondition(cond));
		}
		if (!astNode.isEmptyBlock()) {
			
			Object obj = getTranslatorAndTranslate(astNode.action,withStmt);
			if(obj instanceof JstBlock){
				withStmt.addChild((JstBlock)obj);
			}else {
				withStmt.getBody().addStmt((IStmt)obj);
			}
				
			//withStmt.
		}
		return withStmt;
	}
}
