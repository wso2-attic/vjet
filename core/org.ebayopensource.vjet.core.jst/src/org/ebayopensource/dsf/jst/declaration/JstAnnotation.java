/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;
import org.ebayopensource.dsf.common.Z;

public class JstAnnotation extends BaseJstNode implements IJstAnnotation {
	
	private static final long serialVersionUID = 1L;
	
	private JstName m_name;
	private List<IExpr> m_values;
	
	public JstName getName() {
		return m_name;
	}
	
	public void setName(String name){
		setName(new JstName(name));
	}
	
	public void setName(JstName name){
		assert name != null : "name cannot be null";
		m_name = name;
	}
	
	public void addExpr(IExpr expr) {
		if (m_values == null) {
			m_values = new ArrayList<IExpr>();
		}
		m_values.add(expr);
		addChild(expr);
	}

	public List<IExpr> values() {
		if (m_values == null || m_values.size() == 0) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_values);
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		Z z = new Z();
		z.format("m_name", m_name == null ? null : m_name.getName());
		z.format("m_values", m_values);
		return z.toString();
	}

}
