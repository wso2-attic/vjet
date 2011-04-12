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
import org.ebayopensource.dsf.jst.stmt.TryStmt;

/**
 * This class accept or decline {@link VjoKeywordFactory#KWD_CATCH} completion keyword.
 * 
 * 
 *
 */
public class AcceptCatchCompletion extends
		AbstractCompositeCompletionAcceptor<TryStmt> {

	protected Object getStatementClass() {
		return TryStmt.class;
	}

	@Override
	protected List<BaseJstNode> getDependsStatements(TryStmt tryStmt) {
		List<BaseJstNode> list = new ArrayList<BaseJstNode>();
		list.add(tryStmt.getCatchBlock(false));
		list.add(tryStmt.getFinallyBlock(false));
		return list;
	}

}
