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
import java.util.Set;

import org.ebayopensource.dsf.jst.IInferred;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

/**
 * A JstProxyType inferring a given JstType
 */
public class JstInferredType extends JstProxyType implements IInferred {

	private static final long serialVersionUID = 1L;
	
	private transient List<TypeMeta> m_modifiedTypes = null;

	public JstInferredType(IJstType targetType) {
		super(targetType);
	}

	@Override
	public void accept(IJstNodeVisitor visitor) {
		return;
	}
	
	public boolean hasModifiedType() {
		return m_modifiedTypes != null;
	}
	
	public IJstType getCurrentType(int pos, Set<Object> scopes) {
		if (m_modifiedTypes == null) {
			return getType();
		}
		int candidate = -1;
		int i = 0;
		for (; i < m_modifiedTypes.size(); i++) {
			TypeMeta meta = m_modifiedTypes.get(i);
			if (!scopes.contains(meta.m_scope)) {
				continue;
			}
			if (pos < meta.m_pos) {				
				break;
			}
			candidate = i;
		}
		if (candidate == -1) {
			return getType();
		}
		return m_modifiedTypes.get(candidate).m_type;
	}
	
	public void setCurrentType(IJstType type, int pos, Object scope) {
		List<TypeMeta> list = getModifiedTypes();
		list.add(new TypeMeta(type, pos, scope));
		Collections.sort(list);
	}
	
	private synchronized List<TypeMeta> getModifiedTypes() {
		if (m_modifiedTypes == null) {
			m_modifiedTypes = new ArrayList<TypeMeta>(3);
		}
		return m_modifiedTypes;
	}
	
	private static class TypeMeta implements Comparable<TypeMeta>{
		private int m_pos;
		private IJstType m_type;
		private Object m_scope;

		TypeMeta(IJstType type, int pos, Object scope) {
			m_type = type;
			m_pos = pos;
			m_scope = scope;
		}
		
		@Override
		public int compareTo(TypeMeta o) {
			if (m_pos == o.m_pos) {
				return 0;
			}
			if (m_pos > o.m_pos) {
				return 1;
			}
			return -1;
		}		
	}
}
