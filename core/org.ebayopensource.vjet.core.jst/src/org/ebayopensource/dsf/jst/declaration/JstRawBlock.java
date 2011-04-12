/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;


public class JstRawBlock extends JstBlock{
	private static final long serialVersionUID = 1L;
	private String m_content;
	public JstRawBlock(String content) {
		m_content = content;
	}
	public String toBlockText(){
		return m_content;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toBlockText();
	}
}

