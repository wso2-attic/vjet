/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.stmt;

import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class BlockStmt extends JstStmt implements IStmt {

	private static final long serialVersionUID = 1L;
	
	private JstBlock m_block;
	
	public BlockStmt() {
	}
	
	public BlockStmt(JstBlock block) {
		m_block = block;
		addChild(m_block);
		
		//added by huzhou@ebay.com to provide correct error source
		setSource(block.getSource());
	}
	
	public String toStmtText(){
		if (m_block != null){
			return m_block.toBlockText();
		}
		return null;
	}
	
	public JstBlock getBody(){
		if (m_block == null){
			m_block = new JstBlock();
			addChild(m_block);
		}
		return m_block;
	}
	
	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	public String toString() {
		return toStmtText();
	}
}
