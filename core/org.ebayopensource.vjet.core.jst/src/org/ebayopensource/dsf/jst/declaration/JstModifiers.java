/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public final class JstModifiers extends BaseJstNode {

	private static final long serialVersionUID = 1L;

	/**
	 * Modifier constant (bit mask, value 0) indicating no modifiers.
	 */
	public static final int NONE = 0x0000;

	/**
	 * "public" modifier constant (bit mask). Applicable to types, methods,
	 * constructors, and fields.
	 */
	public static final int PUBLIC = 0x0001;

	/**
	 * "protected" modifier constant (bit mask). Applicable to types, methods,
	 * constructors, and fields.
	 * 
	 * @since 2.0
	 */
	public static final int PROTECTED = 0x0004;

	/**
	 * "private" modifier constant (bit mask). Applicable to types, methods,
	 * constructors, and fields.
	 */
	public static final int PRIVATE = 0x0002;

	/**
	 * "static" modifier constant (bit mask). Applicable to types, methods,
	 * fields, and initializers.
	 */
	public static final int STATIC = 0x0008;

	/**
	 * "final" modifier constant (bit mask). Applicable to types, methods,
	 * fields, and variables.
	 */
	public static final int FINAL = 0x0010;

	/**
	 * "abstract" modifier constant (bit mask). Applicable to types and methods.
	 */
	public static final int ABSTRACT = 0x0400;
	
	/**
	 * "dynamic" modifier constant (bit mask). Applicable to JS types.
	 */
	public static final int DYNAMIC = 0x0020;
	

	private static final String S_PUBLIC = "public";
	private static final String S_PROTECTED = "protected";
	private static final String S_PRIVATE = "private";
	private static final String S_PACKAGE = ""; //should "package" be returned?

	private static final String S_PUBLIC_COMMA = "public,";
	private static final String S_PROTECTED_COMMA = "protected,";
	private static final String S_PRIVATE_COMMA = "private,";
	private static final String S_STATIC_COMMA = "static,";
	private static final String S_FINAL_COMMA = "final,";
	private static final String S_ABSTRACT_COMMA = "abstract,";
	private static final String S_DYNAMIC_COMMA = "dynamic,";


	private int m_flags;

	//
	// Constructor
	//
	public JstModifiers() {
		this(NONE);
	}

	public JstModifiers(int flags) {
		m_flags = flags;
	}

	//
	// API
	//
	public void merge(int flags) {
		m_flags = m_flags | flags;
	}

	public JstModifiers setPublic() {
		m_flags = m_flags | PUBLIC;
		m_flags = m_flags & ~PROTECTED;
		m_flags = m_flags & ~PRIVATE;
		return this;
	}
	
	public JstModifiers setDefault() {
		m_flags = m_flags & ~PUBLIC;
		m_flags = m_flags & ~PROTECTED;
		m_flags = m_flags & ~PRIVATE;
		return this;
	}

	public boolean isNone() {
		return (m_flags == NONE);
	}
	
	public boolean isInternal() {
		return !isPublic() && !isProtected() && !isPrivate();
	}

	public boolean isPublic() {
		return (m_flags & PUBLIC) != 0;
	}

	public JstModifiers setProtected() {
		m_flags = m_flags | PROTECTED;
		m_flags = m_flags & ~PUBLIC;
		m_flags = m_flags & ~PRIVATE;
		return this;
	}

	public boolean isProtected() {
		return (m_flags & PROTECTED) != 0;
	}

	public JstModifiers setPrivate() {
		m_flags = m_flags | PRIVATE;
		m_flags = m_flags & ~PUBLIC;
		m_flags = m_flags & ~PROTECTED;
		return this;
	}

	public boolean isPrivate() {
		return (m_flags & PRIVATE) != 0;
	}

	public JstModifiers setStatic(boolean isTrue) {
		if (isTrue) {
			m_flags = m_flags | STATIC;
		} else {
			m_flags = m_flags & ~STATIC;
		}
		return this;
	}

	public boolean isStatic() {
		return (m_flags & STATIC) != 0;
	}

	public JstModifiers setFinal() {
		setFinal(true);
		return this;
	}

	public JstModifiers setFinal(boolean isTrue) {
		if (isTrue) {
			m_flags = m_flags | FINAL;
		} else {
			m_flags = m_flags & ~FINAL;
		}
		return this;
	}

	public boolean isFinal() {
		return (m_flags & FINAL) != 0;
	}

	public JstModifiers setAbstract() {
		m_flags = m_flags | ABSTRACT;
		return this;
	}
	
	public JstModifiers setAbstract(boolean isTrue) {
		if (isTrue) {
			m_flags = m_flags | ABSTRACT;
		} else {
			m_flags = m_flags & ~ABSTRACT;
		}
		return this;
	}

	public boolean isAbstract() {
		return (m_flags & ABSTRACT) != 0;
	}

	public boolean isDynamic() {
		return (m_flags & DYNAMIC) != 0;
	}
	
	public JstModifiers setDynamic() {
		m_flags = m_flags | DYNAMIC;
		return this;
	}
	
	public JstModifiers setDynamic(boolean isTrue) {
		if (isTrue) {
			m_flags = m_flags | DYNAMIC;
		} else {
			m_flags = m_flags & ~DYNAMIC;
		}
		return this;
	}
	
	public String getAccessScope() {
		if (isPublic()) {
			return S_PUBLIC;
		} else if (isProtected()) {
			return S_PROTECTED;
		} else if (isPrivate()){
			return S_PRIVATE;
		}
		return S_PACKAGE;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		if (isPublic()) {
			sb.append(S_PUBLIC_COMMA);
		}
		if (isProtected()) {
			sb.append(S_PROTECTED_COMMA);
		}
		if (isPrivate()) {
			sb.append(S_PRIVATE_COMMA);
		}
		//if (isPackage()) should not print anything
		if (isStatic()) {
			sb.append(S_STATIC_COMMA);
		}
		if (isFinal()) {
			sb.append(S_FINAL_COMMA);
		}
		if (isAbstract()) {
			sb.append(S_ABSTRACT_COMMA);
		}
		if (isDynamic()) {
			sb.append(S_DYNAMIC_COMMA);
		}

		sb.append("]");

		return sb.toString();
	}

	public static int getFlag(String accessScope) {
		int flag;
		if (S_PRIVATE.equals(accessScope)) {
			flag = PRIVATE;
		} else if (S_PROTECTED.equals(accessScope)) {
			flag = PROTECTED;
		} else if (S_PUBLIC.equals(accessScope)) {
			flag = PUBLIC;
		} else {
			// default
			flag = NONE;
		}

		return flag;
	}
	
	public JstModifiers getCopy() {
		return new JstModifiers(m_flags);
	}
	
	public void copy(JstModifiers modifiers) {
		m_flags = modifiers.m_flags;
	}
	/**
	 * @return current bit mask 
	 */
	public int getFlags() {
		return m_flags;
	}

}
