/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.stmt.BlockStmt;
import org.ebayopensource.dsf.jst.stmt.LabeledStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.LabeledStatement;

public class LabeledStatementTranslator extends
		BaseAst2JstTranslator<LabeledStatement, LabeledStmt> {

	@Override
	protected LabeledStmt doTranslate(LabeledStatement statement) {
		String labelName = String.valueOf(statement.label);
		JstIdentifier identifier = new JstIdentifier(labelName);
		// TODO add JSTSource to JstIdentifier

		Object result = getTranslatorAndTranslate(statement.statement, m_parent);
		LabeledStmt labeledStmt = null;
		//TODO what to do this JstIdentifier as right part?
		if (result instanceof IStmt) {
			IStmt st = (IStmt) result; //bugfix by huzhou, shouldn't call getTranslatorAndTranslate which creates an extra statement under m_parent
			labeledStmt = new LabeledStmt(identifier, st);
		} else if (result instanceof JstBlock) {
			labeledStmt = new LabeledStmt(identifier, new BlockStmt((JstBlock)result));
		} else {
			//TODO - Are there more cases?
			System.err.println("Unable to create LabeledStatement for " + statement);
		}
		if (labeledStmt != null) {
			m_parent.addChild(labeledStmt);
			labeledStmt.setSource(TranslateHelper.getSource(statement, m_ctx.getSourceUtil()));
		}
		return labeledStmt;
	}

}
