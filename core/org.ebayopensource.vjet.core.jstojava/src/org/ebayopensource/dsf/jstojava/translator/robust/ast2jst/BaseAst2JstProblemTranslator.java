/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;


import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;

public abstract class BaseAst2JstProblemTranslator<T extends IASTNode, E>
		extends BaseAst2JstTranslator<T, E> {
	protected int m_problemStart;

	void setProblemStart(int problemStart) {
		m_problemStart = problemStart;
	}
}
