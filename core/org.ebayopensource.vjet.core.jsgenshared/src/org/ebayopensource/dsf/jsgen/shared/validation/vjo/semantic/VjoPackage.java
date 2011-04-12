/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;

@Deprecated
public class VjoPackage extends JstPackage implements Comparable<VjoPackage>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private VjoPackage m_parentPackage;
	private Map<String, VjoPackage> m_childrenPackages;
	private Map<String, IJstType> m_childrenTypes;
	
	
	public VjoPackage(){
		super();
	}
	
	public VjoPackage(String name){
		super(name);
	}
	
	public VjoPackage getParentPackage() {
		return m_parentPackage;
	}
	
	public void setParentPackage(VjoPackage parentPackage) {
		m_parentPackage = parentPackage;
	}
	
	public List<VjoPackage> getChildrenPackages() {
		if(m_childrenPackages == null){
			return Collections.emptyList();
		}
		final List<VjoPackage> childrenPackages = new ArrayList<VjoPackage>();
		childrenPackages.addAll(m_childrenPackages.values());
		return childrenPackages;
	}
	
	public void setChildrenPackages(Map<String, VjoPackage> childrenPackages) {
		m_childrenPackages = childrenPackages;
	}
	
	public void addChildPackage(VjoPackage childPackage){
		if(m_childrenPackages == null){
			m_childrenPackages = new HashMap<String, VjoPackage>();
		}
		m_childrenPackages.put(childPackage.getName(), childPackage);
	}
	
	public VjoPackage getChildPackage(String name){
		if(m_childrenPackages == null){
			return null;
		}
		return m_childrenPackages.get(name);
	}
	
	public List<IJstType> getChildrenTypes() {
		if(m_childrenTypes == null){
			return Collections.emptyList();
		}
		final List<IJstType> childrenTypes = new ArrayList<IJstType>();
		childrenTypes.addAll(m_childrenTypes.values());
		return childrenTypes;
	}
	
	public void setChildrenTypes(Map<String, IJstType> childrenTypes) {
		m_childrenTypes = childrenTypes;
	}
	
	public void addChildType(IJstType childType){
		if(m_childrenTypes == null){
			m_childrenTypes = new HashMap<String, IJstType>();
		}
		m_childrenTypes.put(childType.getName(), childType);
	}
	
	public IJstType getChildType(String name){
		if(m_childrenTypes == null){
			return null;
		}
		return m_childrenTypes.get(name);
	}

	@Override
	public boolean equals(Object other){
		if(other == null){
			return false;
		}
		if(VjoPackage.class.isAssignableFrom(other.getClass())){
			return getName().equals(((VjoPackage)other).getName())
				&& getGroupName().equals(((VjoPackage)other).getGroupName());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		int hashCode = 0;
		if(getName() != null){
			hashCode += getName().hashCode() << 7;
		}
		if(getGroupName() != null){
			hashCode += getGroupName().hashCode();
		}
		return hashCode;
	}
	
	public int compareTo(VjoPackage o) {
		if(equals(o)){
			return 0;
		}
		return 1;
	}
	
}
