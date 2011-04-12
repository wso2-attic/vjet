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
import org.ebayopensource.dsf.jst.token.ITryStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class TryStmt extends BlockStmt implements ITryStmt {
	
	private static final long serialVersionUID = 1L;
	
	private JstBlock m_catchBlock;
	private JstBlock m_finallyBlock;
	
	//
	// Constructor
	//
	public TryStmt(){
	}
	
	//
	// API
	//
	public void addCatch(CatchStmt catchStmt){
		getCatchBlock(true).addStmt(catchStmt);
	}
	
	public JstBlock getCatchBlock(){
		return m_catchBlock;
	}
	
	public JstBlock getCatchBlock(boolean create){
		if (m_catchBlock == null && create){
			m_catchBlock = new JstBlock();
			addChild(m_catchBlock);
		}
		return m_catchBlock;
	}
	
	public JstBlock getFinallyBlock(){
		return m_finallyBlock;
	}
	
	public JstBlock getFinallyBlock(boolean create){
		if (m_finallyBlock == null && create){
			m_finallyBlock = new JstBlock();
			addChild(m_finallyBlock);
		}
		return m_finallyBlock;
	}
	
	//
	// Satisfy IStmt
	//
	public String toStmtText(){
		StringBuilder sb = new StringBuilder("try {");
		
		sb.append(getBody().toBlockText()).append("}");
		
		if (m_catchBlock != null){
			sb.append(m_catchBlock.toBlockText());
		}
		
		if (m_finallyBlock != null){
			sb.append(m_finallyBlock.toBlockText());
		}

		return sb.toString();
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toStmtText();
	}
}
