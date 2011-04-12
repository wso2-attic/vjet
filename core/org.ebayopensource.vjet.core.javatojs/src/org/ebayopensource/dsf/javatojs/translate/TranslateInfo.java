/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;

import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.stmt.JstBlockInitializer;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class TranslateInfo {
	
	private final JstType m_type;
	private TranslationMode m_mode;
	private TranslationStatus m_status;
	
	private List<String> m_importedPkgs;
	private Map<String,String> m_importedTypes = new HashMap<String,String>();
	private Map<String,String> m_importedStaticRefs = new HashMap<String,String>();
	
	private Map<String,CustomInfo> m_fldCustomInfos;
	private Map<MethodKey,CustomInfo> m_mtdCustomInfos;
	private Map<String,CustomInfo> m_embeddedTypeCustomInfos;
	
	private Map<String, JstType> m_unknownTypes;
	
	private Map<String,IJstType> m_typeTable = new HashMap<String,IJstType>();

	private IJstType m_baseType;
	private List<IJstType> m_interfaceTypes;
	private List<String> m_embededTypes;
	private Map<String,Map<Integer,List<JstMethod>>> m_overloadedStaticMtds;
	private Map<String,Map<Integer,List<JstMethod>>> m_overloadedInstanceMtds;
	private Map<String,Map<MethodDeclaration,JstMethod>> m_removedMtds;
	
	private Map<Initializer,JstBlockInitializer> m_initilizers;
	private Map<AnonymousClassDeclaration,JstType> m_anonymousTypes;
	private Map<TypeDeclarationStatement,JstType> m_localTypes;
	
	private boolean m_clearTypeRefs = false;
	
	private int m_temp_index_counter = 0;
	
	private Set<IJstType> m_active_imports = new HashSet<IJstType>();
	
	//
	// Constructor
	//
	public TranslateInfo(JstType type){
		m_type = type;
	}
	
	//
	// API
	//
	/**
	 * Answer the type this info is associated with.
	 * @param JstType
	 */
	public JstType getType(){
		return m_type;
	}
	
	/**
	 * Add translation mode
	 * @param mode TranslationMode
	 * @return TranslateInfo
	 */
	public TranslateInfo addMode(TranslationMode mode){
		if (m_mode == null){
			m_mode = mode;
		}
		else if (mode != null){
			m_mode.setMode(m_mode.getMode() | mode.getMode());
		}
		return this;
	}
	
	/**
	 * Answer the translation modes
	 * @return TranslationMode
	 */
	public TranslationMode getMode(){
		if (m_mode == null){
			m_mode = new TranslationMode();
		}
		return m_mode;
	}
	
	/**
	 * Answer the status of the translation
	 * @return
	 */
	public TranslationStatus getStatus() {
		if (m_status == null){
			m_status = new TranslationStatus(m_type);
		}
		return m_status;
	}
	
	public void addImport(final String shortName, final String fullName){
		if (shortName == null || fullName == null){
			return;
		}
		synchronized(this){
			m_importedTypes.put(shortName, fullName);
		}
	}
	
	public String getImported(final String shortName){
		String name = m_importedTypes.get(shortName);
		if (name != null){
			return name;
		}
		return getImportedStaticRefTypeName(shortName);
	}
	
	public void addImportedPkg(String pkgName){
		if (pkgName == null){
			return;
		}
		synchronized(this){
			if (m_importedPkgs == null){
				m_importedPkgs = new ArrayList<String>();
			}
			else if (m_importedPkgs.contains(pkgName)){
				return;
			}
			m_importedPkgs.add(pkgName);
		}
	}
	
	public List<String> getImportedPkgs(){
		if (m_importedPkgs == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_importedPkgs);
	}
	
	public void addImportedStaticRef(final String refName, String typeName){
		synchronized(this){
			m_importedStaticRefs.put(refName, typeName);
		}
	}
	
	public String getImportedStaticRefTypeName(final String refName){
		synchronized(this){
			String typeName = m_importedStaticRefs.get(refName);
			if (typeName != null || !getMode().hasImplementation()){
				return typeName;
			}
			for (Map.Entry<String,String> entry: m_importedStaticRefs.entrySet()){
				if (entry.getKey().equals("*")){
					JstType jstType = JstCache.getInstance().getType(entry.getValue());
					if (jstType == null){
						continue;
					}
					if (jstType.getProperty(refName, true, true) != null
							|| jstType.getMethod(refName, true, true) != null){
						return entry.getValue();
					}
				}
			}
			return null;
		}
	}
	
	public CustomInfo getFieldCustomInfo(final String shortName){
		if (shortName == null || m_fldCustomInfos == null){
			return CustomInfo.NONE;
		}
		CustomInfo cInfo = m_fldCustomInfos.get(shortName);
		return cInfo == null ? CustomInfo.NONE : cInfo;
	}
	
	public void addFieldCustomInfo(final String shortName, final CustomInfo customInfo){
		if (shortName == null || customInfo == null){
			return;
		}
		if (m_fldCustomInfos == null){
			m_fldCustomInfos = new LinkedHashMap<String,CustomInfo>(2);
		}
		m_fldCustomInfos.put(shortName, customInfo);		
	}
	
	public CustomInfo getMethodCustomInfo(final MethodKey mtdKey){
		if (mtdKey == null || m_mtdCustomInfos == null){
			return CustomInfo.NONE;
		}
		CustomInfo cInfo = m_mtdCustomInfos.get(mtdKey);
		return cInfo == null ? CustomInfo.NONE : cInfo;
	}
	
	public void addMethodCustomInfo(final MethodKey mtdKey, final CustomInfo customInfo){
		if (mtdKey == null || customInfo == null){
			return;
		}
		if (m_mtdCustomInfos == null){
			m_mtdCustomInfos = new LinkedHashMap<MethodKey,CustomInfo>(2);
		}
		m_mtdCustomInfos.put(mtdKey, customInfo);		
	}
	
	public CustomInfo getEmbeddedTypeCustomInfo(final String shortName){
		if (shortName == null || m_embeddedTypeCustomInfos == null){
			return CustomInfo.NONE;
		}
		CustomInfo cInfo = m_embeddedTypeCustomInfos.get(shortName);
		return cInfo == null ? CustomInfo.NONE : cInfo;
	}
	
	public void addEmbeddedTypeCustomInfo(final String shortName, final CustomInfo customInfo){
		if (shortName == null || customInfo == null){
			return;
		}
		if (m_embeddedTypeCustomInfos == null){
			m_embeddedTypeCustomInfos = new LinkedHashMap<String,CustomInfo>(2);
		}
		m_embeddedTypeCustomInfos.put(shortName, customInfo);		
	}
	
	public Map<MethodDeclaration,JstMethod> getRemovedMtds(final String name){
		if (name == null || m_removedMtds == null){
			return Collections.emptyMap();
		}
		Map<MethodDeclaration,JstMethod> map = m_removedMtds.get(name);
		if (map == null){
			return Collections.emptyMap();
		}
		return map;
	}
	
	public void addRemovedMtd(final MethodDeclaration astMtd, final JstMethod jstMtd){
		if (astMtd == null || jstMtd == null){
			return;
		}
		if (m_removedMtds == null){
			m_removedMtds = new HashMap<String,Map<MethodDeclaration,JstMethod>>();
		}
		String name = astMtd.getName().toString();
		Map<MethodDeclaration,JstMethod> map = m_removedMtds.get(name);
		if (map == null){
			map = new HashMap<MethodDeclaration,JstMethod>();
			m_removedMtds.put(name, map);
		}
		if (!map.containsKey(astMtd)){
			map.put(astMtd, jstMtd);
		}
	}
	
	public void addUnknownType(final String shortName, final JstType type){
		if (shortName == null){
			return;
		}
		if (m_unknownTypes == null){
			m_unknownTypes = new HashMap<String,JstType>(2);
		}
		if (!m_unknownTypes.containsKey(shortName)){
			m_unknownTypes.put(shortName, type);
		}
	}
	
	public JstType getUnknownType(final String shortName){
		if (shortName == null || m_unknownTypes == null){
			return null;
		}
		return m_unknownTypes.get(shortName);
	}
	
	public Map<String,JstType> getUnknownTypes(){
		if (m_unknownTypes == null){
			return Collections.emptyMap();
		}
		return Collections.unmodifiableMap(m_unknownTypes);
	}
	
	public boolean isUnknownType(final String shortName){
		if (shortName == null || m_unknownTypes == null){
			return false;
		}
		return m_unknownTypes.containsKey(shortName);
	}
	
	public void setType(final String typeName, final IJstType type){
		if (typeName == null || type == null){
			return;
		}
		m_typeTable.put(typeName, type);
	}
	
	public IJstType getType(final String typeName, boolean recursive){
		IJstType type = m_typeTable.get(typeName);
		if (type != null){
			return type;
		}
		if (!recursive || m_type.getParentNode() == null){
			return null;
		}
		JstType childType = m_type;
		JstType parentType = m_type.getParentNode().getOwnerType();
		if (parentType != null && childType != parentType){
			type = TranslateCtx.ctx().getTranslateInfo(parentType).getType(typeName, recursive);
			if (type != null){
				return type;
			}
		}
		return null;
	}
	
	public Map<String,IJstType> getTypes(){
		return Collections.unmodifiableMap(m_typeTable);
	}
	
	public void removeType(final String symbol){
		m_typeTable.remove(symbol);
	}
	
	public void setBaseType(final IJstType baseType){
		m_baseType = baseType;
	}
	
	public IJstType getBaseType(){
		return m_baseType;
	}
	
	public void addInterfaceType(final IJstType type){
		if (type == null){
			return;
		}
		if (m_interfaceTypes == null){
			m_interfaceTypes = new ArrayList<IJstType>(2);
		}
		m_interfaceTypes.add(type);
	}
	
	public boolean isInterfaceType(IJstType type){
		if (m_interfaceTypes == null){
			return false;
		}
		return m_interfaceTypes.contains(type);
	}
	
	public List<IJstType> getInterfaceTypes(){
		if (m_interfaceTypes == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_interfaceTypes);
	}
	
	public void addEmbededType(final String name){
		if (name == null){
			return;
		}
		if (m_embededTypes == null){
			m_embededTypes = new ArrayList<String>(2);
		}
		m_embededTypes.add(name);
	}
	
	public boolean isEmbededType(String name){
		if (m_embededTypes == null){
			return false;
		}
		return m_embededTypes.contains(name);
	}
	
	public List<String> getEmbededTypes(){
		if (m_embededTypes == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_embededTypes);
	}
	
	public void addOverloaded(JstMethod jstMtd){
		if (jstMtd == null){
			return;
		}
		MethodDeclaration astMtd = (MethodDeclaration)AstBindingHelper.getAstNode(jstMtd);
		if (astMtd == null){
			return;
		}
		
		Map<String,Map<Integer,List<JstMethod>>> overloadedMap =  getOverloadedMethodMap(jstMtd.isStatic(), true);
		
		String mtdName = jstMtd.getName().getName();
		Map<Integer,List<JstMethod>> map = overloadedMap.get(mtdName);
		if (map == null){
			map = new HashMap<Integer,List<JstMethod>>();
			overloadedMap.put(mtdName, map);
		}
		Integer argCount = new Integer(astMtd.parameters().size());
		List<JstMethod> list = map.get(argCount);
		if (list == null){
			list = new ArrayList<JstMethod>();
			map.put(argCount, list);
		}
		String surffix = "_" + argCount + "_" + list.size() + JstMethod.getOverloadSuffix(jstMtd.getOwnerType());
		jstMtd.setSurffix(surffix);
		jstMtd.setName(jstMtd.getName() + surffix);
		list.add(jstMtd);
	}

	public boolean isOverloaded(final String mtdName, boolean isStatic){
		if (mtdName == null){
			return false;
		}
		Map<String,Map<Integer,List<JstMethod>>> map = getOverloadedMethodMap(isStatic, false);
		if (map == null){
			return false;
		}
		return map.containsKey(mtdName);
	}
	
	public Map<Integer,List<JstMethod>> getOverloaded(final String mtdName, boolean isStatic){
		if (mtdName == null){
			return Collections.emptyMap();
		}
		Map<String,Map<Integer,List<JstMethod>>> map = getOverloadedMethodMap(isStatic, false);
		if (map == null){
			return Collections.emptyMap();
		}
		return map.get(mtdName);
	}
	
	public JstMethod getOverloaded(MethodDeclaration astMtd){
		if (astMtd == null){
			return null;
		}
		
		String mtdName = astMtd.getName().toString();
		mtdName = TranslateCtx.ctx().getProvider().getNameTranslator().processVarName(mtdName);
		if (astMtd.isConstructor()){
			mtdName = VjoKeywords.CONSTRUCTS;
		}
		Map<Integer,List<JstMethod>> map = getOverloaded(mtdName, TranslateHelper.isStatic(astMtd.modifiers()));
		if (map == null){
			return null;
		}
		List<JstMethod> list = map.get(new Integer(astMtd.parameters().size()));
		if (list == null){
			return null;
		}
		for (JstMethod jstMtd: list){
			if (AstBindingHelper.getAstNode(jstMtd) == astMtd){
				return jstMtd;
			}
		}
		return null;
	}
	
	public void addInitializer(final Initializer initializer, final JstBlockInitializer jstInitializer){
		if (initializer == null || jstInitializer == null){
			return;
		}
		if (m_initilizers == null){
			m_initilizers = new HashMap<Initializer,JstBlockInitializer>();
		}
		m_initilizers.put(initializer, jstInitializer);
	}
	
	public JstBlockInitializer getJstInitializer(final Initializer initializer){
		if (initializer == null || m_initilizers == null){
			return null;
		}
		return m_initilizers.get(initializer);
	}
	
	public void addAnonymousType(final AnonymousClassDeclaration anonymousType, final JstType jstType){
		if (anonymousType == null || jstType == null){
			return;
		}
		if (m_anonymousTypes == null){
			m_anonymousTypes = new HashMap<AnonymousClassDeclaration,JstType>();
		}
		m_anonymousTypes.put(anonymousType, jstType);
	}
	
	public JstType getJstAnonymousType(final AnonymousClassDeclaration anonymousType){
		if (anonymousType == null || m_anonymousTypes == null){
			return null;
		}
		return m_anonymousTypes.get(anonymousType);
	}
	
	public void addLocalType(final TypeDeclarationStatement localType, final JstType jstType){
		if (localType == null || jstType == null){
			return;
		}
		if (m_localTypes == null){
			m_localTypes = new HashMap<TypeDeclarationStatement,JstType>();
		}
		jstType.setLocalType(true);
		m_localTypes.put(localType, jstType);
	}
	
	public JstType getLocalType(final TypeDeclarationStatement localType){
		if (localType == null || m_localTypes == null){
			return null;
		}
		return m_localTypes.get(localType);
	}
	
	public JstType getLocalType(String name) {
		if (null == name || m_localTypes == null) {
			return null;
		}
		for (JstType itm : m_localTypes.values()) {
			if (name.equals(itm.getName())) {
				return itm;
			}
		}
		return null;
	}
	
	public void setClearTypeRefs(boolean clearTypeRefs){
		m_clearTypeRefs = clearTypeRefs;
	}
	
	public boolean clearTypeRefs(){
		boolean clear = m_clearTypeRefs || !getUnknownTypes().isEmpty();
		if (clear == true){
			return true;
		}
		if (m_type != m_type.getRootType()){
			return TranslateCtx.ctx().getTranslateInfo(m_type.getRootType()).clearTypeRefs();
		}
		return false;
	}
	
	@Override
	public String toString(){
		return m_type.getName();
	}
	
	//
	// Private
	//
	private Map<String,Map<Integer,List<JstMethod>>> getOverloadedMethodMap(boolean isStatic, boolean create){
		if (isStatic){
			if (m_overloadedStaticMtds == null && create){
				m_overloadedStaticMtds = new HashMap<String,Map<Integer,List<JstMethod>>>();
			}
			return m_overloadedStaticMtds;
		}
		else {
			if (m_overloadedInstanceMtds == null && create){
				m_overloadedInstanceMtds = new HashMap<String,Map<Integer,List<JstMethod>>>();
			}
			return m_overloadedInstanceMtds;
		}
	}

	public void addActiveImport(IJstType type) {
		m_active_imports.add(type);
	}
	
	public Set<IJstType> getActiveImports() {
		return m_active_imports;
	}
	
	public int getUniqueTempIndex() {
		return m_temp_index_counter++;
	}
}
