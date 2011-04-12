/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import java.util.Map;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;

public class FunctionTypeRefVisitor implements IJstVisitor {

	private Map<IJstType, JstFunctionRefType> m_map;

	FunctionTypeRefVisitor(Map<IJstType, JstFunctionRefType> map){
		m_map= map;
	}
	
	
	@Override
	public void endVisit(IJstNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postVisit(IJstNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preVisit(IJstNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean visit(IJstNode node) {
		if(node instanceof JstTypeReference){
			JstTypeReference refType = (JstTypeReference)node;
			JstFunctionRefType funcType = m_map.get(refType.getReferencedType());
			if(funcType!=null){
//				System.out.println("match here... replacing");
				refType.setReferencedType(funcType);
			}
			return false;
		}
		return true;
	}

}
