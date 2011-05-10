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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jst.IInferred;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

/**
 * A JstProxyType inferring a given JstType
 */
public class JstInferredType extends JstProxyType implements IInferred {

	private static final long serialVersionUID = 1L;
	
	private transient List<TypeMeta> m_modifiedTypes = null;
	private transient TypeMeta m_defaultMeta;
	private boolean m_modified = false;

	public JstInferredType(IJstType targetType) {
		super(targetType);
		m_defaultMeta = new TypeMeta(targetType, 0, null);
	}

	@Override
	public void accept(IJstNodeVisitor visitor) {
		return;
	}
	
	public boolean modified() {
		return m_modified;
	}
	
	public IJstType getCurrentType(int pos, Set<?> scopes) {
		return getMeta(pos, scopes).getType(pos);
	}
	
	public void setCurrentType(IJstType type, int pos, Object scope) {
		m_modified = true;
		List<TypeMeta> list = getModifiedTypes();
		list.add(new TypeMeta(type, pos, scope));
		Collections.sort(list);
	}
	
	public void addNewProperty(String name, IJstType type, int pos, Set<?> scopes) {
		m_modified = true;
		TypeMeta meta = getMeta(pos, scopes);
		JstProperty node = new JstProperty(type, name);
		node.getModifiers().setPublic();
		meta.addProperties(pos, node);
	}
	
	private TypeMeta getMeta(int pos, Set<?> scopes) {
		if (m_modifiedTypes == null) {
			if (m_defaultMeta == null) {//after deserialization
				m_defaultMeta = new TypeMeta(getType(), 0, null);
			}
			return m_defaultMeta;
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
			return m_defaultMeta;
		}
		return m_modifiedTypes.get(candidate);
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
		private Map<Integer, IJstProperty> m_extraProps = null;

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
		
		void addProperties(int pos, IJstProperty node) {
			if (m_extraProps == null) {
				m_extraProps = new LinkedHashMap<Integer, IJstProperty>();
			}
			m_extraProps.put(pos, node);
		}
		
		IJstType getType(int pos) {
			if (m_extraProps == null) {
				return m_type;
			}
			List<IJstProperty> props = new ArrayList<IJstProperty>();
			for (Map.Entry<Integer, IJstProperty> entry : m_extraProps.entrySet()) {
				if (entry.getKey() > pos) {
					break;
				}
				props.add(entry.getValue());
			}
			if (props.isEmpty()) {
				return m_type;
			}
			JstType extraType = new JstType("");
			for (IJstProperty prop : props) {
				extraType.addProperty(prop);			
			}
			return new AugmentedType(m_type, extraType);
		}
	}
	
	public static class AugmentedType extends JstTypeMixer implements IInferred{

		private static final long serialVersionUID = 1L;

		protected AugmentedType(IJstType targetType, IJstType extraType) {
			super(targetType.getName() + "(+)");
			this.addExtend(targetType);
			m_types = new ArrayList<IJstType>(2);
			m_types.add(targetType);
			m_types.add(extraType);
		}
	}
}
