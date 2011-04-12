/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.stmt.IfStmt;

/**
 * This class accept or decline {@link VjoKeywordFactory#KWD_ELSE} completion keyword.
 * 
 * 
 *
 */
public class ElseCompletionAcceptor extends
		AbstractCompositeCompletionAcceptor<IfStmt> {

	@Override
	protected List<BaseJstNode> getDependsStatements(IfStmt tryStmt) {
		List<BaseJstNode> list = new ArrayList<BaseJstNode>();
		if (tryStmt.getElseIfBlock(false) != null) {
			list.add(tryStmt.getElseIfBlock(false));
		}
		return list;
	}

	@Override
	protected Object getStatementClass() {
		return IfStmt.class;
	}

}
