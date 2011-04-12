/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstOType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstFunctionRefType extends JstType implements IJstOType{
	private static final long serialVersionUID = 1L;
	IJstMethod m_mtd;
	public JstFunctionRefType(IJstMethod mtd) {
		assert mtd != null;
		m_mtd = mtd;
		setSimpleName(mtd.getName().getName());
	}
	public IJstMethod getMethodRef() {
		return m_mtd;
	}
	
//	public String getName() {
//		JstType parent = (JstType)getParentNode();
//		if (parent!=null) {
//			return parent.getSimpleName() + "." + getSimpleName();
//		}
//		return super.getName();
//	}


	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
}
