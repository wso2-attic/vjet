/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt.CaseStmt;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CaseStatement;

public class CaseStatementTranslator extends
		BaseAst2JstTranslator<CaseStatement, CaseStmt> {

	@Override
	protected CaseStmt doTranslate(CaseStatement statement) {
		if (statement.constantExpression == null) {
			return new SwitchStmt.CaseStmt(null); //default:
		}
		return new SwitchStmt.CaseStmt(
			(IExpr)getTranslatorAndTranslate(statement.constantExpression, m_parent));
	}

}
