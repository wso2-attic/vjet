/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;

public class JstMixedType extends JstTypeMixer {

	private static final long serialVersionUID = 1L;
	
	public JstMixedType(List<IJstType> types) {
		super("_object_");
		this.addExtend(JstCache.getInstance().getType("Object"));
		m_types = types;
	}
	
	public List<IJstType> getMixedTypes() {
		return m_types;
	}
	
	public JstModifiers getModifiers() {
		if(m_types==null){
			return super.getModifiers();
		}
		
		
		JstModifiers modifiers = new JstModifiers();
		
		for(IJstType t : getMixedTypes()){
			modifiers.merge(t.getModifiers().getFlags());
		}
		return modifiers;
	}
	
	@Override
	public String getSimpleName(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean isFirst = true;
		for (IJstType t : m_types) {
			if (!isFirst) {
				sb.append("+");
			}
			sb.append(t.getSimpleName());
			isFirst = false;
		}
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public String getName(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean isFirst = true;
		for (IJstType t : m_types) {
			if (!isFirst) {
				sb.append("+");
			}
			sb.append(t.getName());
			isFirst = false;
		}
		sb.append("]");
		return sb.toString();
	}
}
