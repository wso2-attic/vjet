/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.term;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ISimpleTerm;


public abstract class JstLiteral extends BaseJstNode implements ISimpleTerm, IExpr {
	private static final long serialVersionUID = 1L;
	protected  IJstType m_jstType;
	
	public abstract String toValueText();
	
	public IJstType getResultType() {
		return m_jstType;
	}
}
