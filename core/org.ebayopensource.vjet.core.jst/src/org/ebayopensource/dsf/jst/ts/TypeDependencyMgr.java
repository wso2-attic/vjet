/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.util.JstTypeDependencyCollector;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.group.Group;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * TypeDependencyMgr manages type related changes to ensure type space integrity
 * 
 * 
 */
class TypeDependencyMgr {
	
	private static JstTypeDependencyCollector s_typeDependencyCollector = new JstTypeDependencyCollector();
	private JstTypeSpaceMgr m_tsMgr;
	private TypeSpace<IJstType,IJstNode> m_ts;
	
	//
	// Constructor
	//
	TypeDependencyMgr(JstTypeSpaceMgr tsMgr){
		assert tsMgr != null : "tsMgr cannot be null";
		m_tsMgr = tsMgr;
		m_ts = tsMgr.getTypeSpaceImpl();
	}
	
	//
	// API
	//
	/**
	 * This method is normally used to handle AddTypeEvent event:
	 * Add given type into group with given name. Update type dependency graph
	 * as well as property/method indexes accordingly.
	 * @param typeName String is the type name plus its group name
	 * @param type IJstType the JST tree for the type
	 * @exception RuntimeException if type already exists in the group with given name
	 */
	public void addType(final TypeName typeName, final IJstType type){
		
		if (type == null || typeName == null || typeName.groupName() == null){
			return;
		}
		
		IJstType oldType = m_ts.getType(typeName);
		
		if (oldType != null && oldType != type){
			throw new RuntimeException("Type alreay exists:" + typeName);
		}
		
		// Add type
		Group<IJstType> group = m_ts.getGroup(typeName.groupName());
		if (group == null){
			group = m_tsMgr.getGroupMgr().addGroup(typeName.groupName(), null);
		}
		group.addEntity(typeName.typeName(), type);
		
		addInnerAndOTypes(group, type, true);
		
		addTypeToPackage(typeName, type);
		
		// Add property dependencies to dependent lists of property index nodes
		m_tsMgr.getPropertyIndexMgr().processTypeAdded(typeName, type);
		
		// Add method dependencies to dependent lists of method index nodes
		m_tsMgr.getMethodIndexMgr().processTypeAdded(typeName, type);
	}
	
	/**
	 * Add all inner types within input type to the input group owning the type
	 * @param group
	 * @param type
	 * @param buildDependency
	 */
	public void addInnerAndOTypes(Group<IJstType> group, IJstType type, boolean buildDependency) {
		List<? extends IJstType> innerTypes = type.getEmbededTypes();
		List<? extends IJstType> secondaryTypes = type.getSecondaryTypes();
		List<? extends IJstType> oTypes = type.getOTypes();
		
		List<IJstType> combinedTypeList = new ArrayList<IJstType>();
		
		if (innerTypes != null && !innerTypes.isEmpty()) {
			combinedTypeList.addAll(innerTypes);
		}
		
		if (oTypes != null && !oTypes.isEmpty()) {
			combinedTypeList.addAll(oTypes);
		}
		
		if (secondaryTypes != null && !secondaryTypes.isEmpty()) {
			combinedTypeList.addAll(secondaryTypes);
		}
		
		for (IJstType innerOType : combinedTypeList) {
			String typeName = innerOType.getName();
			
			if (typeName != null && typeName.length() > 0) {
				if (buildDependency) {
					addType(new TypeName(group.getName(), typeName), innerOType);
				}
				else {
					addTypeNoDependency(new TypeName(group.getName(), typeName), innerOType);
				}
			}
		}
		
	}
	
	public void removeInnerAndOTypes(Group<IJstType> group, IJstType type) {
		List<? extends IJstType> innerTypes = type.getEmbededTypes();
		List<? extends IJstType> secondaryTypes = type.getSecondaryTypes();
		List<? extends IJstType> oTypes = type.getOTypes();
		
		List<IJstType> combinedTypeList = new ArrayList<IJstType>();
		
		if (innerTypes != null && !innerTypes.isEmpty()) {
			combinedTypeList.addAll(innerTypes);
		}
		
		if (oTypes != null && !oTypes.isEmpty()) {
			combinedTypeList.addAll(oTypes);
		}
		if (secondaryTypes != null && !secondaryTypes.isEmpty()) {
			combinedTypeList.addAll(secondaryTypes);
		}
		
		for (IJstType innerOType : combinedTypeList) {
			String typeName = innerOType.getName();
			
			if (typeName != null && typeName.length() > 0) {
				removeType(new TypeName(group.getName(), typeName));
			}
		}
	}
	
	/**
	 * Add type to type space only without building dependency
	 * @param typeName
	 * @param type
	 */
	public void addTypeNoDependency(final TypeName typeName, final IJstType type){
		if (type == null || typeName == null || typeName.groupName() == null){
			return;
		}
		
		IJstType oldType = m_ts.getType(typeName);
		
		if (oldType != null && oldType != type) {
			throw new RuntimeException("Type alreay exists:" + typeName);
		}
		else if (oldType == type) {			
			return;
		}
		
		// Add type
		Group<IJstType> group = m_ts.getGroup(typeName.groupName());
		if (group == null){
			group = m_tsMgr.getGroupMgr().addGroup(typeName.groupName(), null);
		}
		
		group.addEntity(typeName.typeName(), type, false);
		
		addInnerAndOTypes(group, type, false);
		
		addTypeToPackage(typeName, type);
		
	}
	
	/**
	 * Remove type with given name from type space. Update type dependency graph
	 * as well as property/method indexes accordingly.
	 * @param typeName TypeName
	 * @return IJstType
	 */
	public IJstType removeType(final TypeName typeName){
		if (typeName == null){
			return null;
		}
		IJstType type = m_ts.getType(typeName);
		if (type == null){
			throw new RuntimeException("cannot find type:" + typeName);
		}
		
		// Remove type
		Group<IJstType> group = m_ts.getGroup(typeName.groupName());
		if (group == null){
			throw new RuntimeException("cannot find group for type:" + typeName);
		}
		group.removeEntity(typeName.typeName());
		
		removeInnerAndOTypes(group, type);
		
		removeTypeFromPackage(typeName, type);
		
		// Remove property index & property dependencies to dependent lists of property index nodes
		m_tsMgr.getPropertyIndexMgr().processTypeRemoved(typeName);
		
		// Remove method index & method dependencies to dependent lists of method index nodes
		m_tsMgr.getMethodIndexMgr().processTypeRemoved(typeName);
		
		return type;
	};
	
	private void removeTypeFromPackage(TypeName typeName, IJstType type) {
		
		if (type != null) {
			String packageName = "";
			
			if (type.getPackage() != null) {
				packageName = type.getPackage().getName();
			}
			
			m_ts.removeTypeFromPackage(packageName, typeName);
		}		
	}
	
	private void addTypeToPackage(TypeName typeName, IJstType type) {
		
		if (type != null) {
			String packageName = "";
			
			if (type.getPackage() != null) {
				packageName = type.getPackage().getName();
			}
			

			m_ts.addTypeToPackage(packageName, typeName, type);			
		}		
	}
	
	//
	// Package protected
	//
	JstTypeDependencyCollector getTypeDependencyCollector(){
		return s_typeDependencyCollector;
	}
}
