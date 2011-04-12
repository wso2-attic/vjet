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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;


/**
 * JstParamType is created to support parameterized types, fields and methods.
 * 
 * 
 */
public class JstParamType extends JstProxyType {

	private static final long serialVersionUID = 1L;
	
	private List<IJstType> m_bounds;

	//
	// Constructor
	//
	public JstParamType(String name){
		super(new JstType(name));
		super.getType().getModifiers().setAbstract(); // abstract type so that it can access satisfies interface list
		updateType();
	}
	
	// 
	// API
	//
	public JstParamType addBound(IJstType bound){
		if (bound == null){
			return this;
		}
		synchronized (this){
			if (m_bounds == null){
				m_bounds = new ArrayList<IJstType>();
			}
			m_bounds.add(bound);
			updateType();
		}
		return this;
	}
	
	public JstParamType updateBound(String key, IJstType newBound) {
		if (newBound == null || key == null){
			return this;
		}
		synchronized (this){
			if (m_bounds != null) {
				boolean update = false;
				
				for (int i = 0; i < m_bounds.size(); i++) {
					IJstType itm = m_bounds.get(i);
					if (key.equals(itm.getName())) {
						m_bounds.set(i, newBound);
						update = true;
					}
				}
				
				if (update) {
					updateType();
				}
			}
		}
		return this;
	}
	
	public synchronized List<IJstType> getBounds(){
		if (m_bounds == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_bounds);
	}
	
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder(super.toString());
		if (m_bounds != null && !m_bounds.isEmpty()){
			sb.append(" extends ").append(m_bounds.get(0).getName());
		}
		return sb.toString();
	}
	
	private void updateType() {
		JstType target = (JstType) super.getType();

		IJstType object = JstCache.getInstance().getType("Object");
		if(object==null){
			object = JstCache.getInstance().getType("org.ebayopensource.dsf.jsnative.global.Object");
		}
		
		target.clearExtends();

		target.clearSatisfies();

		if (m_bounds == null) {
			target.addExtend(object);
		} else {
			boolean hasExtends = false;

			for (IJstType bound : m_bounds) {

				if (bound.isInterface()) {
					target.addSatisfy(bound);
				}
				else {
					target.addExtend(bound);
					hasExtends = true;
				}
			}

			if (!hasExtends) {
				target.addExtend(object);
			}
		}
		
	}
}
