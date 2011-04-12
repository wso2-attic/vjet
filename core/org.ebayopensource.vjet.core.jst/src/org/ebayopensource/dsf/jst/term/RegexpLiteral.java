/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.term;

import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class RegexpLiteral extends JstLiteral {
	
	private static final long serialVersionUID = 1L;
	
	private String m_value;
	
	public RegexpLiteral(String value) {
		m_value = value;
		m_jstType = JstCache.getInstance().getType("RegExp");
	}

	@Override
	public String toValueText() {
		return m_value;
	}

	public String toSimpleTermText() {
		return toValueText();
	}

	public String toExprText() {
		return toValueText();
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toValueText();
	}
}
