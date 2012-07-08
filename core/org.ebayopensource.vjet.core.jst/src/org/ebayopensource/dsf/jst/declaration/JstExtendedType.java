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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstDoc;
import org.ebayopensource.dsf.jst.IJstGlobalVar;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstOType;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

/**
 * Synthesized type to mix props and methods from extended types
 * to the target type.
 */
public class JstExtendedType implements IJstType {
	
	private static final long serialVersionUID = 1L;

	private final IJstType m_targetType;
	private final List<IJstType> m_extendedTypes;
	
	public JstExtendedType(IJstType targetType, List<IJstType> extendedTypes) {
		m_targetType = targetType;
		m_extendedTypes = extendedTypes;
	}
	
	public IJstType getTargetType() {
		return m_targetType;
	}

	public void accept(IJstNodeVisitor visitor) {
		m_targetType.accept(visitor);
	}

	public void addOType(IJstOType otype) {
		m_targetType.addOType(otype);
	}

	public String getAlias() {
		return m_targetType.getAlias();
	}

	public String getAliasTypeName() {
		return m_targetType.getAliasTypeName();
	}

	public List<IJstProperty> getAllPossibleProperties(boolean isStatic, boolean recursive) {
		return m_targetType.getAllPossibleProperties(isStatic, recursive);
	}

	public IJstAnnotation getAnnotation(String name) {
		return m_targetType.getAnnotation(name);
	}

	public List<IJstAnnotation> getAnnotations() {
		return m_targetType.getAnnotations();
	}

	public List<? extends IJstNode> getChildren() {
		return m_targetType.getChildren();
	}

	public List<String> getComments() {
		return m_targetType.getComments();
	}

	public IJstMethod getConstructor() {
		return m_targetType.getConstructor();
	}

	public IJstType getContainingType() {
		return m_targetType.getContainingType();
	}

	public IJstDoc getDoc() {
		return m_targetType.getDoc();
	}

	public IJstType getEmbededType(String shortName) {
		return m_targetType.getEmbededType(shortName);
	}

	public List<? extends IJstType> getEmbededTypes() {
		return m_targetType.getEmbededTypes();
	}

	public IJstProperty getEnumValue(String name) {
		return m_targetType.getEnumValue(name);
	}

	public List<IJstProperty> getEnumValues() {
		return m_targetType.getEnumValues();
	}

	public List<? extends IJstType> getExpects() {
		return m_targetType.getExpects();
	}

	public List<? extends IJstTypeReference> getExpectsRef() {
		return m_targetType.getExpectsRef();
	}

	public IJstType getExtend() {
		return m_targetType.getExtend();
	}

	public IJstTypeReference getExtendRef() {
		return m_targetType.getExtendRef();
	}

	public List<? extends IJstType> getExtends() {
		return m_targetType.getExtends();
	}

	public List<? extends IJstTypeReference> getExtendsRef() {
		return m_targetType.getExtendsRef();
	}

	public IJstType getFullyQualifiedImport(String typeName) {
		return m_targetType.getFullyQualifiedImport(typeName);
	}

	public List<? extends IJstType> getFullyQualifiedImports() {
		return m_targetType.getFullyQualifiedImports();
	}

	public List<? extends IJstTypeReference> getFullyQualifiedImportsRef() {
		return m_targetType.getFullyQualifiedImportsRef();
	}

	public IJstGlobalVar getGlobalVar(String name, boolean recursive) {
		return m_targetType.getGlobalVar(name, recursive);
	}

	public IJstGlobalVar getGlobalVar(String name) {
		return m_targetType.getGlobalVar(name);
	}

	public List<? extends IJstGlobalVar> getGlobalVars() {
		return m_targetType.getGlobalVars();
	}

	public IJstType getImport(String typeName) {
		return m_targetType.getImport(typeName);
	}

	public IJstTypeReference getImportRef(String typeName) {
		return m_targetType.getImportRef(typeName);
	}

	public List<? extends IJstType> getImports() {
		return m_targetType.getImports();
	}

	public Map<String, ? extends IJstType> getImportsMap() {
		return m_targetType.getImportsMap();
	}

	public List<? extends IJstTypeReference> getImportsRef() {
		return m_targetType.getImportsRef();
	}
	
	/**
	 * @see IJstType#getImportRef()
	 */
	public IJstTypeReference getInactiveImportRef(String typeName) {
		return m_targetType.getInactiveImportRef(typeName);
	}

	public IJstType getInactiveImport(String typeName) {
		return m_targetType.getInactiveImport(typeName);
	}

	public List<? extends IJstType> getInactiveImports() {
		return m_targetType.getInactiveImports();
	}

	public Map<String, ? extends IJstType> getInactiveImportsMap() {
		return m_targetType.getInactiveImportsMap();
	}

	public List<? extends IJstTypeReference> getInactiveImportsRef() {
		return m_targetType.getInactiveImportsRef();
	}

	public JstBlock getInitBlock() {
		return m_targetType.getInitBlock();
	}

	public List<IStmt> getInitializers(boolean isStatic) {
		return m_targetType.getInitializers(isStatic);
	}

	public List<? extends IJstType> getInstanceEmbededTypes() {
		return m_targetType.getInstanceEmbededTypes();
	}

	public List<IStmt> getInstanceInitializers() {
		return m_targetType.getInstanceInitializers();
	}

	public IJstMethod getInstanceMethod(String name, boolean recursive) {
		return getMethod(name, false, recursive);
	}

	public IJstMethod getInstanceMethod(String name) {
		return getInstanceMethod(name, false);
	}

	public List<? extends IJstMethod> getInstanceMethods() {
		return getMethods(false);
	}

	public List<IJstProperty> getInstanceProperties() {
		return getProperties(false);
	}

	public IJstProperty getInstanceProperty(String name, boolean recursive) {
		return getProperty(name, false, recursive);
	}

	public IJstProperty getInstanceProperty(String name) {
		return getInstanceProperty(name, false);
	}

	public IJstMethod getMethod(String name, boolean isStatic, boolean recursive) {
		IJstMethod method = m_targetType.getMethod(name, isStatic, recursive);
		if (method != null) {
			return method;
		}
		for (IJstType type: m_extendedTypes) {
			method = type.getMethod(name, isStatic, recursive);
			if (method != null) {
				return method;
			}
		}
		return null;
	}

	public IJstMethod getMethod(String name, boolean isStatic) {
		return getMethod(name, isStatic, false);
	}

	public IJstMethod getMethod(String name) {
		IJstMethod method = m_targetType.getMethod(name);
		if (method != null) {
			return method;
		}
		for (IJstType type: m_extendedTypes) {
			method = type.getMethod(name);
			if (method != null) {
				return method;
			}
		}
		return null;
	}

	public List<? extends IJstMethod> getMethods() {
		List<IJstMethod> methods = new ArrayList<IJstMethod>(m_targetType.getMethods());
		Set<String> names = getAllEntries();
		for (IJstType type: m_extendedTypes) {
			combineMethods(methods, type.getMethods(), names);
		}
		return methods;
	}

	public List<? extends IJstMethod> getMethods(boolean isStatic, boolean recursive) {
		List<IJstMethod> methods = new ArrayList<IJstMethod>(m_targetType.getMethods(isStatic, recursive));
		Set<String> names = getAllEntries();
		for (IJstType type: m_extendedTypes) {
			combineMethods(methods, type.getMethods(isStatic, recursive), names);
		}
		return methods;
	}

	public List<? extends IJstMethod> getMethods(boolean isStatic) {
		return getMethods(isStatic, false);
	}

	public List<? extends IJstType> getMixins() {
		return m_targetType.getMixins();
	}

	public List<? extends IJstTypeReference> getMixinsRef() {
		return m_targetType.getMixinsRef();
	}

	public JstModifiers getModifiers() {
		return m_targetType.getModifiers();
	}

	public String getName() {
		return m_targetType.getName();
	}

	public Map<String, Object> getOptions() {
		return m_targetType.getOptions();
	}

	public IJstOType getOType(String name) {
		return m_targetType.getOType(name);
	}

	public List<IJstOType> getOTypes() {
		return m_targetType.getOTypes();
	}

	public IJstType getOuterType() {
		return m_targetType.getOuterType();
	}

	public IJstType getOwnerType() {
		return m_targetType.getOwnerType();
	}

	public JstPackage getPackage() {
		return m_targetType.getPackage();
	}

	public List<String> getParamNames() {
		return m_targetType.getParamNames();
	}

	public List<JstParamType> getParamTypes() {
		return m_targetType.getParamTypes();
	}

	public IJstNode getParentNode() {
		return m_targetType.getParentNode();
	}

	public List<IJstProperty> getProperties() {
		List<IJstProperty> props = new ArrayList<IJstProperty>(m_targetType.getProperties());
		Set<String> names = getAllEntries();
		for (IJstType type: m_extendedTypes) {
			combineProps(props, type.getProperties(), names);
		}
		return props;
	}

	public List<IJstProperty> getProperties(boolean isStatic) {
		List<IJstProperty> props = new ArrayList<IJstProperty>(m_targetType.getProperties(isStatic));
		Set<String> names = getAllEntries();
		for (IJstType type: m_extendedTypes) {
			combineProps(props, type.getProperties(isStatic), names);
		}
		return props;
	}

	public IJstProperty getProperty(String name, boolean isStatic,
			boolean recursive) {
		IJstProperty prop = m_targetType.getProperty(name, isStatic, recursive);
		if (prop != null) {
			return prop;
		}
		for (IJstType type: m_extendedTypes) {
			prop = type.getProperty(name, isStatic, recursive);
			if (prop != null) {
				return prop;
			}
		}
		return null;
	}

	public IJstProperty getProperty(String name, boolean isStatic) {
		return getProperty(name, isStatic, false);
	}

	public IJstProperty getProperty(String name) {
		IJstProperty prop = m_targetType.getProperty(name);
		if (prop != null) {
			return prop;
		}
		for (IJstType type: m_extendedTypes) {
			prop = type.getProperty(name);
			if (prop != null) {
				return prop;
			}
		}
		return null;
	}

	public IJstNode getRootNode() {
		return m_targetType.getRootNode();
	}

	public IJstType getRootType() {
		return m_targetType.getRootType();
	}

	public List<? extends IJstType> getSatisfies() {
		return m_targetType.getSatisfies();
	}

	public List<? extends IJstTypeReference> getSatisfiesRef() {
		return m_targetType.getSatisfiesRef();
	}

	public IJstType getSiblingType(String shortName) {
		return m_targetType.getSiblingType(shortName);
	}

	public List<? extends IJstType> getSiblingTypes() {
		return m_targetType.getSiblingTypes();
	}

	public String getSimpleName() {
		return m_targetType.getSimpleName();
	}

	public JstSource getSource() {
		return m_targetType.getSource();
	}

	public List<? extends IJstType> getStaticEmbededTypes() {
		return m_targetType.getStaticEmbededTypes();
	}

	public List<IStmt> getStaticInitializers() {
		return m_targetType.getStaticInitializers();
	}

	public IJstMethod getStaticMethod(String name, boolean recursive) {
		return getMethod(name, true, recursive);
	}

	public IJstMethod getStaticMethod(String name) {
		return getMethod(name, true, false);
	}

	public List<? extends IJstMethod> getStaticMethods() {
		return getMethods(true);
	}

	public List<IJstProperty> getStaticProperties() {
		return getProperties(true);
	}

	public IJstProperty getStaticProperty(String name, boolean recursive) {
		return getProperty(name, true, recursive);
	}

	public IJstProperty getStaticProperty(String name) {
		return getProperty(name, true, false);
	}

	public boolean hasGlobalVars() {
		return m_targetType.hasGlobalVars();
	}

	public boolean hasImport(String typeName) {
		return m_targetType.hasImport(typeName);
	}

	public boolean hasInstanceMethod(String mtdName, boolean recursive) {
		if (m_targetType.hasInstanceMethod(mtdName, recursive)) {
			return true;
		}
		for (IJstType type: m_extendedTypes) {
			if (type.hasInstanceMethod(mtdName, recursive)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasInstanceMethods() {
		if (m_targetType.hasInstanceMethods()) {
			return true;
		}
		for (IJstType type: m_extendedTypes) {
			if (type.hasInstanceMethods()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasInstanceProperties() {
		if (m_targetType.hasInstanceProperties()) {
			return true;
		}
		for (IJstType type: m_extendedTypes) {
			if (type.hasInstanceProperties()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasInstanceProperty(String name, boolean recursive) {
		if (m_targetType.hasInstanceProperty(name, recursive)) {
			return true;
		}
		for (IJstType type: m_extendedTypes) {
			if (type.hasInstanceProperty(name, recursive)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasMixins() {
		return m_targetType.hasMixins();
	}

	public boolean hasStaticMethod(String mtdName, boolean recursive) {
		if (m_targetType.hasStaticMethod(mtdName, recursive)) {
			return true;
		}
		for (IJstType type: m_extendedTypes) {
			if (type.hasStaticMethod(mtdName, recursive)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasStaticMethods() {
		if (m_targetType.hasStaticMethods()) {
			return true;
		}
		for (IJstType type: m_extendedTypes) {
			if (type.hasStaticMethods()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasStaticProperties() {
		if (m_targetType.hasStaticProperties()) {
			return true;
		}
		for (IJstType type: m_extendedTypes) {
			if (type.hasStaticProperties()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasStaticProperty(String name, boolean recursive) {
		if (m_targetType.hasStaticProperty(name, recursive)) {
			return true;
		}
		for (IJstType type: m_extendedTypes) {
			if (type.hasStaticProperty(name, recursive)) {
				return true;
			}
		}
		return false;
	}

	public boolean isAnonymous() {
		return m_targetType.isAnonymous();
	}

	public boolean isClass() {
		return m_targetType.isClass();
	}

	public boolean isEmbededType() {
		return m_targetType.isEmbededType();
	}

	public boolean isEnum() {
		return m_targetType.isEnum();
	}

	public boolean isFakeType() {
		return m_targetType.isFakeType();
	}

	public boolean isFType() {
		return m_targetType.isFType();
	}

	public boolean isImpliedImport() {
		return m_targetType.isImpliedImport();
	}

	public boolean isInterface() {
		return m_targetType.isInterface();
	}

	public boolean isLocalType() {
		return m_targetType.isLocalType();
	}

	public boolean isMetaType() {
		return m_targetType.isMetaType();
	}

	public boolean isMixin() {
		return m_targetType.isMixin();
	}

	public boolean isOType() {
		return m_targetType.isOType();
	}

	public boolean isParamName(String name) {
		return m_targetType.isParamName(name);
	}

	public boolean isSiblingType() {
		return m_targetType.isSiblingType();
	}
	
	@Override
	public List<IJstType> getAllDerivedTypes() {
		return m_targetType.getAllDerivedTypes();
	}
	
	private static void combineProps(
		List<IJstProperty> props,
		List<IJstProperty> additions,
		Set<String> names) {
		for (IJstProperty prop : additions) {
			String name = prop.getName().getName();
			if (names.contains(name)) {
				continue;
			}
			else {
				names.add(name);
			}
			props.add(prop);
		}		
	}
	
	private static void combineMethods(
		List<IJstMethod> methods,
		List<? extends IJstMethod> additions,
		Set<String> names) {
		for (IJstMethod method : additions) {
			String name = method.getName().getName();
			if (names.contains(name)) {
				continue;
			}
			else {
				names.add(name);
			}
			methods.add(method);
		}		
	}
	
	private Set<String> getAllEntries() {
		Set<String> names = new HashSet<String>();
		for (IJstProperty prop: m_targetType.getProperties()) {
			names.add(prop.getName().getName());
		}
		for (IJstMethod method: m_targetType.getMethods()) {
			names.add(method.getName().getName());
		}
		return names;
	}

	@Override
	public List<? extends IJstType> getSecondaryTypes() {
		throw new UnsupportedOperationException("secondary types not supported for extension");
	}
	
	@Override
	public boolean isSingleton() {
		return m_targetType.isSingleton();
	}
	

}
