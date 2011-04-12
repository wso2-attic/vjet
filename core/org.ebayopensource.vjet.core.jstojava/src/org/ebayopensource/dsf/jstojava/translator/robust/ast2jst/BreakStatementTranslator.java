/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.stmt.BreakStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.BreakStatement;

public class BreakStatementTranslator extends BaseAst2JstTranslator<BreakStatement, BreakStmt> {

	@Override
	protected BreakStmt doTranslate(BreakStatement statement) {
		BreakStmt breakStmt;
		if (statement.label != null && statement.label.length > 0) {
			JstIdentifier identifier = new JstIdentifier(String
					.valueOf(statement.label));
			// TODO add JSTSource to JstIdentifier
			breakStmt = new BreakStmt(identifier);
		}
		else {
			breakStmt = new BreakStmt();
		}
		breakStmt.setSource(TranslateHelper.getSource(statement, m_ctx.getSourceUtil()));
		m_parent.addChild(breakStmt);		
		return breakStmt;
	}

}
