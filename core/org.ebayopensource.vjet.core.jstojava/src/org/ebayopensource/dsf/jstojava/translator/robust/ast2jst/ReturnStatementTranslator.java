/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ReturnStatement;

public class ReturnStatementTranslator extends
		BaseAst2JstTranslator<ReturnStatement, RtnStmt> {

	@Override
	protected RtnStmt doTranslate(ReturnStatement statement) {
		IExpr expr = null;
		
		if (statement.expression != null) {
			Object stmt = getTranslatorAndTranslate(statement.expression,
					m_parent);
			if(stmt instanceof IExpr) {
				//Jack: See bug 5749
				List<IJsCommentMeta> metaList = null;
				if(stmt instanceof BaseJstNode){
					metaList = TranslateHelper.findMetaFromExpr((BaseJstNode)stmt);
				}
				if(metaList != null){
					expr = TranslateHelper.getCastable((IExpr)stmt, metaList, m_ctx);
					// remove after using
					metaList = null;
					
				}
				else{
					expr = TranslateHelper.getCastable((IExpr)stmt, statement, m_ctx);
				}
				
			}
			else if(stmt instanceof JstMethod){
				expr = (IExpr) new FuncExpr((JstMethod)stmt);
			}
			else{
				System.err.println("Cannot cast statement to "+IExpr.class+" in "+ReturnStatementTranslator.class);
			}
		}
		RtnStmt rtnStmt = new RtnStmt(expr);
		if (m_parent != null){
			m_parent.addChild(rtnStmt);
		}
		rtnStmt.setSource(TranslateHelper.getSource(statement, m_ctx.getSourceUtil()));
		return rtnStmt;
	}
}
