/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.stmt.CatchStmt;
import org.ebayopensource.dsf.jst.stmt.TryStmt;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Argument;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Block;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.TryStatement;

public class TryStatementTranslator extends
		BaseAst2JstTranslator<TryStatement, TryStmt> {

	@Override
	protected TryStmt doTranslate(TryStatement statement) {
		TryStmt tryStmt = new TryStmt();
		if (m_parent != null){
			m_parent.addChild(tryStmt);
		}
		if (!statement.isEmptyBlock()) {
			getTranslatorAndTranslate(statement.tryBlock, tryStmt.getBody());
		}
		Block[] catchBlocks = statement.catchBlocks;
		if (catchBlocks != null) {
			for (int i = 0; i < catchBlocks.length; i++) {
				Argument argument = statement.catchArguments[i];
				JstVar var = new JstVar((IJstType)null, String.valueOf(argument.name));
				JstSource source = TranslateHelper.getSource(argument, m_ctx.getSourceUtil());
				var.setSource(source);
				CatchStmt catchStmt = new CatchStmt(var);
				if (m_parent != null){
					m_parent.addChild(catchStmt);
				}
				if (!catchBlocks[i].isEmptyBlock()) {
					getTranslatorAndTranslate(catchBlocks[i], catchStmt
							.getBody());
				}
				catchStmt.setSource(TranslateHelper.getSource(catchBlocks[i], m_ctx.getSourceUtil()));

				tryStmt.addCatch(catchStmt);
			}
		}
		Block finallyBlock = statement.finallyBlock;
		if (finallyBlock != null) {
			getTranslatorAndTranslate(finallyBlock, tryStmt
					.getFinallyBlock(true));
		}
		tryStmt.setSource(TranslateHelper.getSource(statement, m_ctx.getSourceUtil()));

		return tryStmt;
	}

}
