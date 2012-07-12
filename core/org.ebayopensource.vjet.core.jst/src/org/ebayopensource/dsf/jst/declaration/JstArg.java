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
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;
import org.ebayopensource.dsf.common.Z;

public class JstArg extends BaseJstNode {

	private static final long serialVersionUID = 1L;

	private final List<IJstTypeReference> m_types = new ArrayList<IJstTypeReference>();
	private final String m_name;
	private boolean m_isVariable;
	private boolean m_isOptional;
	private final boolean m_isFinal;

	//
	// Constructor
	//
	public JstArg(final IJstType type, final String name,
			final boolean isVarargs) {
		this(new JstTypeReference(type), name, isVarargs);
	}

	public JstArg(final IJstType type, final String name,
			final boolean isVarargs, final boolean isOptional) {
		this(new JstTypeReference(type), name, isVarargs, isOptional);
	}

	public JstArg(final IJstType type, final String name,
			final boolean isVarargs, final boolean isOptional,
			final boolean isFinal) {
		this(new JstTypeReference(type), name, isVarargs, isOptional, isFinal);
	}

	public JstArg(final IJstTypeReference type, final String name,
			final boolean isVarargs) {
		m_types.add(type);
		m_name = name;
		m_isVariable = isVarargs;
		m_isOptional = false;
		m_isFinal = false;
	}

	public JstArg(final IJstTypeReference type, final String name,
			final boolean isVarargs, final boolean isOptional) {
		m_types.add(type);
		m_name = name;
		m_isVariable = isVarargs;
		m_isOptional = isOptional;
		m_isFinal = false;
	}

	public JstArg(final IJstTypeReference type, final String name,
			final boolean isVarargs, final boolean isOptional,
			final boolean isFinal) {
		m_types.add(type);
		m_name = name;
		m_isVariable = isVarargs;
		m_isOptional = isOptional;
		m_isFinal = isFinal;
	}

	public JstArg(final List<IJstType> types, final String name,
			final boolean isVarargs, final boolean isOptional) {
		assert name != null : "name is null";

		addTypes(types);
		m_name = name;
		m_isVariable = isVarargs;
		m_isOptional = isOptional;
		m_isFinal = false;
	}

	public JstArg(final List<IJstType> types, final String name,
			final boolean isVarargs, final boolean isOptional,
			final boolean isFinal) {
		assert name != null : "name is null";

		addTypes(types);
		m_name = name;
		m_isVariable = isVarargs;
		m_isOptional = isOptional;
		m_isFinal = isFinal;
	}

	public JstArg(final List<? extends IJstTypeReference> types, int a,
			final String name, final boolean isVarargs, final boolean isOptional) {
		assert name != null : "name is null";

		addTypesRefs(types);
		m_name = name;
		m_isVariable = isVarargs;
		m_isOptional = isOptional;
		m_isFinal = false;
	}

	public JstArg(final List<? extends IJstTypeReference> types, int a,
			final String name, final boolean isVarargs,
			final boolean isOptional, final boolean isFinal) {
		assert name != null : "name is null";

		addTypesRefs(types);
		m_name = name;
		m_isVariable = isVarargs;
		m_isOptional = isOptional;
		m_isFinal = isFinal;
	}

	//
	// API
	//
	public IJstType getType() {
		return getTypeRef().getReferencedType();
	}

	public IJstTypeReference getTypeRef() {
		if(m_types.size()>0){
			return m_types.get(0);
		}
		return new JstTypeReference(JstCache.getInstance().getType("Object"));
	}

	public String getName() {
		return m_name;
	}

	public boolean isVariable() {
		return m_isVariable;
	}

	public void setVariable(boolean isVariable) {
		this.m_isVariable = isVariable;
	}

	public boolean isOptional() {
		return m_isOptional;
	}

	public void setOptional(boolean isOptional) {
		this.m_isOptional = isOptional;
	}

	public boolean isFinal() {
		return m_isFinal;
	}

	public List<IJstType> getTypes() {
		return JstType.unwrap(m_types);
	}

	public List<IJstTypeReference> getTypesRef() {
		return Collections.unmodifiableList(m_types);
	}

	@Override
	public void accept(IJstNodeVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		Z z = new Z();
		z.format("m_name", m_name);
		z.format("m_types", m_types);
		z.format("m_isVariable", m_isVariable);
		z.format("m_isOptional", m_isOptional);

		return z.toString();
	}

	public void addType(final IJstType type) {
		if (!isTypeExists(type)) {
			JstTypeReference jt = new JstTypeReference(type);
			m_types.add(jt);
			addChild(jt);
		}
	}

	public void addTypes(final List<IJstType> types) {
		for (IJstType t : types) {
			if (!isTypeExists(t)) {
				JstTypeReference jt = new JstTypeReference(t);
				m_types.add(jt);
				addChild(jt);
			}
		}
	}

	public void addTypesRefs(final List<? extends IJstTypeReference> types) {
		for (IJstTypeReference t : types) {
			if (!isTypeExists(t.getReferencedType())) {
				m_types.add(t);
				addChild(t);
			}
		}
	}

	public void updateType(String fullName, IJstType newType) {
		for (int i = 0; i < m_types.size(); i++) {
			IJstTypeReference type = m_types.get(i);
			if (type.getReferencedType().getName().equals(fullName)) {
				m_types.set(i, new JstTypeReference(newType));
			}
		}
	}

	public void clearTypes() {
		m_types.clear();
	}

	//
	// Private
	//

	private boolean isTypeExists(IJstType type) {
		for (int i = 0; i < m_types.size(); i++) {
			if (null != type.getName()
					&& type.getName().equals(
							m_types.get(i).getReferencedType().getName())) {
				return true;
			}
		}
		return false;
	}
}
