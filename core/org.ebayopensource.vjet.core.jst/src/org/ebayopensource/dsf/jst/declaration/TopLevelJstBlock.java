/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.IJstType;

public class TopLevelJstBlock extends JstBlock {

	private static final long serialVersionUID = 1L;
	
	public TopLevelJstBlock() {
		m_varTable = new TopLevelVarTable();
	}
	
	public TopLevelJstBlock addIncludedType(IJstType type) {
		((TopLevelVarTable)m_varTable).addIncludedType(type);
		return this;
	}
}
