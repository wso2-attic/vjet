/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.stmt;

import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstBlockInitializer extends BlockStmt implements IStmt {
	
	private static final long serialVersionUID = 1L;
	
	private JstModifiers m_modifiers;
	
	//
	// Constructor
	//
	public JstBlockInitializer(boolean isStatic){
		m_modifiers = new JstModifiers().setStatic(isStatic);
	}
	
	//
	// API
	//
	public JstModifiers getModifiers(){
		return m_modifiers;
	}
	
	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
}
