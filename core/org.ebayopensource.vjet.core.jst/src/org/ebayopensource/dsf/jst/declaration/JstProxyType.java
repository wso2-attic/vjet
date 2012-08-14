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
import java.util.Map;

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

/**
 * Proxy of another IJstType.
 * 
 * 
 */
public abstract class JstProxyType implements IJstType {


	private static final long serialVersionUID = 1L;

	private IJstType m_targetType;
	
	//
	// Constructor
	//
	protected JstProxyType(IJstType targetType){
		if (targetType == null){
			throw new AssertionError("targetType cannot be null");
		}
		m_targetType = targetType;
	}
	
	//
	// Satisfy IJstNode
	//
	public IJstNode getRootNode(){
		return m_targetType.getRootNode();
	}
	public IJstNode getParentNode(){
		return m_targetType.getParentNode();
	}
	public IJstType getOwnerType(){
		return m_targetType.getOwnerType();
	}
	public IJstType getRootType(){
		return m_targetType.getRootType();
	}
	public List<? extends IJstNode> getChildren(){
		return m_targetType.getChildren();
	}
	public JstSource getSource(){
		return m_targetType.getSource();
	}
	
	public List<IJstType> getAllDerivedTypes(){
		return m_targetType.getAllDerivedTypes();
	}

	public List<IJstAnnotation> getAnnotations() {
		return m_targetType.getAnnotations();
	}
	
	public IJstAnnotation getAnnotation(String name) {
		return m_targetType.getAnnotation(name);
	}
	
	//
	// Satisfy IIJstType
	//
	/**
	 * @see IIJstType#getPackage()
	 */
	public JstPackage getPackage(){
		return m_targetType.getPackage();
	}
	
	/**
	 * @see IJstType#getSimpleName()
	 */
	public String getSimpleName(){
		return m_targetType.getSimpleName();
	}
	
	/**
	 * @see IJstType#getName()
	 */
	public String getName(){
		return m_targetType.getName();
	}
	
	/**
	 * @see IJstType#getAlias()
	 */
	public String getAlias() {
		return m_targetType.getAlias();
	}
	
	/**
	 * @see IJstType#getAliasTypeName()
	 */
	public String getAliasTypeName() {
		return m_targetType.getAliasTypeName();
	}

	/**
	 * @see IJstType#isClass()
	 */
	public boolean isClass(){
		return m_targetType.isClass();
	}

	/**
	 * @see IJstType#isInterface()
	 */
	public boolean isInterface(){
		return m_targetType.isInterface();
	}

	/**
	 * @see IJstType#isEnum()
	 */
	public boolean isEnum(){
		return m_targetType.isEnum();
	}
	
	public boolean isOType() {
		return m_targetType.isOType();
	}
	
	/**
	 * @see IJstType#isMixin()
	 */
	public boolean isMixin() {
		return m_targetType.isMixin();
	}
	
	/**
	 * @see IJstType#isFType()
	 */
	public boolean isFType() {
		return m_targetType.isFType();
	}
	
	/**
	 * @see IJstType#isMetaType()
	 */
	public boolean isMetaType() {
		return m_targetType.isMetaType();
	}
	
	/**
	 * @see IJstType#isFakeType()
	 */
	public boolean isFakeType() {
		return m_targetType.isFakeType();
	}
	
	public boolean isAnonymous(){
		return false;
	}

	/**
	 * @see IJstType#hasImport(String)
	 */
	public boolean hasImport(final String typeName){
		return m_targetType.hasImport(typeName);
	}
	
	/**
	 * @see IJstType#getImport(String)
	 */
	public IJstType getImport(final String typeName) {
		return m_targetType.getImport(typeName);
	}
	
	/**
	 * @see IJstType#getImports()
	 */
	public List<? extends IJstType> getImports() {
		return m_targetType.getImports();
	}

	/**
	 * @see IJstType#getInactiveImport(String)
	 */
	public IJstType getInactiveImport(String typeName) {
		return m_targetType.getInactiveImport(typeName);
	}
	
	/**
	 * @see IJstType#getImportRef()
	 */
	public IJstTypeReference getInactiveImportRef(String typeName) {
		return m_targetType.getInactiveImportRef(typeName);
	}

	/**
	 * @see IJstType#getInactiveImports()
	 */
	public List<? extends IJstType> getInactiveImports() {
		return m_targetType.getInactiveImports();
	}
	
	/**
	 * Answer the fully qualified imported type with given name.
	 * @param typeName String
	 * @return IJstType
	 */
	public IJstType getFullyQualifiedImport(String typeName){
		return m_targetType.getFullyQualifiedImport(typeName);
	}
	
	/**
	 * Answer an unmodifiable list of fully qualified imports 
	 * @return List<? extends IJstType>
	 */
	public List<? extends IJstType> getFullyQualifiedImports(){
		return m_targetType.getFullyQualifiedImports();
	}
	
	/**
	 * Answer an unmodifiable list of fully qualified imports 
	 * @return List<? extends IJstType>
	 */
	public List<? extends IJstTypeReference> getFullyQualifiedImportsRef(){
		return m_targetType.getFullyQualifiedImportsRef();
	}
	
	/**
	 * @see IJstType#getExtend()
	 */
	public IJstType getExtend() {
		return m_targetType.getExtend();
	}

	/**
	 * @see IJstType#getExtends()
	 */
	public List<? extends IJstType> getExtends() {
		return m_targetType.getExtends();
	}

	/**
	 * @see IJstType#getSatisfies()
	 */
	public List<? extends IJstType> getSatisfies() {
		return m_targetType.getSatisfies();
	}

	/**
	 * @see IJstType#getExpects()
	 */
	public List<? extends IJstType> getExpects() {
		return m_targetType.getExpects();
	}
		
	/**
	 * @see IJstType#getExtendRef()
	 */
	public IJstTypeReference getExtendRef() {
		return m_targetType.getExtendRef();
	}

	/**
	 * @see IJstType#getExtendsRef()
	 */
	public List<? extends IJstTypeReference> getExtendsRef() {
		return m_targetType.getExtendsRef();
	}

	/**
	 * @see IJstType#getImportRef()
	 */
	public IJstTypeReference getImportRef(String typeName) {
		return m_targetType.getImportRef(typeName);
	}

	public void addOType(IJstOType otype) {
		m_targetType.addOType(otype);
	}
	
	public List<IJstOType> getOTypes() {
		return m_targetType.getOTypes(); 
	}
	
	public IJstOType getOType(String name) {
		return m_targetType.getOType(name);
	}
	/**
	 * @see IJstType#getImportsRef()
	 */
	public List<? extends IJstTypeReference> getImportsRef() {
		return m_targetType.getImportsRef();
	}

	/**
	 * @see IJstType#getSatisfiesRef()
	 */
	public List<? extends IJstTypeReference> getSatisfiesRef() {
		return m_targetType.getSatisfiesRef();
	}

	/**
	 * @see IJstType#getModifiers()
	 */
	public JstModifiers getModifiers() {
		return m_targetType.getModifiers();
	}

	/**
	 * @see IJstType#getProperty(String)
	 */
	public IJstProperty getProperty(final String name) {
		return m_targetType.getProperty(name);
	}

	/**
	 * @see IJstType#getProperty(String,boolean)
	 */
	public IJstProperty getProperty(final String name, boolean isStatic) {
		return m_targetType.getProperty(name, isStatic);
	}

	/**
	 * @see IJstType#getProperty(String,boolean,boolean)
	 */
	public IJstProperty getProperty(final String name, boolean isStatic, boolean recursive) {
		return m_targetType.getProperty(name, isStatic, recursive);
	}
	
	/**
	 * @see IJstType#getProperties()
	 */
	public List< IJstProperty> getProperties(){
		return m_targetType.getProperties();
	}

	/**
	 * @see IJstType#getProperties(boolean)
	 */
	public List< IJstProperty> getProperties(boolean isStatic) {
		return m_targetType.getProperties(isStatic);
	}

	/**
	 * @see IJstType#getAllPossibleProperties(boolean,boolean)
	 */
	public List< IJstProperty> getAllPossibleProperties(boolean isStatic, boolean recursive) {
		return m_targetType.getAllPossibleProperties(isStatic, recursive);
	}

	/**
	 * @see IJstType#hasStaticProperties()
	 */
	public boolean hasStaticProperties() {
		return m_targetType.hasStaticProperties();
	}

	/**
	 * @see IJstType#getStaticProperty(String)
	 */
	public IJstProperty getStaticProperty(final String name) {
		return m_targetType.getStaticProperty(name);
	}

	/**
	 * @see IJstType#getStaticProperty(String,boolean)
	 */
	public IJstProperty getStaticProperty(final String name, boolean recursive) {
		return m_targetType.getStaticProperty(name, recursive);
	}

	/**
	 * @see IJstType#getStaticProperties()
	 */
	public List< IJstProperty> getStaticProperties() {
		return m_targetType.getStaticProperties();
	}
	
	/**
	 * @see IJstType#hasInstanceProperties()
	 */
	public boolean hasInstanceProperties() {
		return m_targetType.hasInstanceProperties();
	}

	/**
	 * @see IJstType#getInstanceProperty(String)
	 */
	public IJstProperty getInstanceProperty(final String name) {
		return m_targetType.getInstanceProperty(name);
	}
	
	/**
	 * @see IJstType#getInstanceProperty(String,boolean)
	 */
	public IJstProperty getInstanceProperty(final String name, boolean recursive) {
		return m_targetType.getInstanceProperty(name, recursive);
	}

	/**
	 * @see IJstType#getInstanceProperties()
	 */
	public List< IJstProperty> getInstanceProperties() {
		return m_targetType.getInstanceProperties();
	}
	
	/**
	 * @see IJstType#getConstructor()
	 */
	public IJstMethod getConstructor() {
		return m_targetType.getConstructor();
	}

	/**
	 * @see IJstType#getMethod(String)
	 */
	public IJstMethod getMethod(final String name) {
		return m_targetType.getMethod(name);
	}

	/**
	 * @see IJstType#getMethod(String,boolean)
	 */
	public IJstMethod getMethod(final String name, boolean isStatic) {
		return m_targetType.getMethod(name, isStatic);
	}

	/**
	 * @see IJstType#getMethod(String,boolean,boolean)
	 */
	public IJstMethod getMethod(final String name, boolean isStatic, boolean recursive) {
		return m_targetType.getMethod(name, isStatic, recursive);
	}
	
	/**
	 * @see IJstType#getMethods()
	 */
	public List<? extends IJstMethod> getMethods(){
		return m_targetType.getMethods();
	}

	/**
	 * @see IJstType#getMethods(boolean)
	 */
	public List<? extends IJstMethod> getMethods(boolean isStatic) {
		return m_targetType.getMethods(isStatic);
	}

	/**
	 * @see IJstType#getMethods(boolean,boolean)
	 */
	public List<? extends IJstMethod> getMethods(boolean isStatic, boolean recursive) {
		return m_targetType.getMethods(isStatic, recursive);
	}

	/**
	 * @see IJstType#hasStaticMethods()
	 */
	public boolean hasStaticMethods() {
		return m_targetType.hasStaticMethods();
	}
	
	/**
	 * @see IJstType#hasStaticMethod(String,boolean)
	 */
	public boolean hasStaticMethod(String mtdName, boolean recursive) {
		return m_targetType.hasStaticMethod(mtdName, recursive);
	}

	/**
	 * @see IJstType#getStaticMethods()
	 */
	public List<? extends IJstMethod> getStaticMethods() {
		return m_targetType.getStaticMethods();
	}
	
	/**
	 * @see IJstType#getStaticMethod(String)
	 */
	public IJstMethod getStaticMethod(final String name) {
		return m_targetType.getStaticMethod(name);
	}

	/**
	 * @see IJstType#getStaticMethod(String,boolean)
	 */
	public IJstMethod getStaticMethod(final String name, boolean recursive) {
		return m_targetType.getStaticMethod(name, recursive);
	}
	
	/**
	 * @see IJstType#hasInstanceMethods()
	 */
	public boolean hasInstanceMethods() {
		return m_targetType.hasInstanceMethods();
	}
	
	/**
	 * @see IJstType#hasInstanceMethod(String,boolean)
	 */
	public boolean hasInstanceMethod(String mtdName, boolean recursive) {
		return m_targetType.hasInstanceMethod(mtdName, recursive);
	}

	/**
	 * @see IJstType#getInstanceMethods()
	 */
	public List<? extends IJstMethod> getInstanceMethods() {
		return m_targetType.getInstanceMethods();
	}
	
	/**
	 * @see IJstType#getInstanceMethod(String)
	 */
	public IJstMethod getInstanceMethod(final String name) {
		return m_targetType.getInstanceMethod(name);
	}

	/**
	 * @see IJstType#getInstanceMethod(String,boolean)
	 */
	public IJstMethod getInstanceMethod(final String name, boolean recursive) {
		return m_targetType.getInstanceMethod(name, recursive);
	}
	
	/**
	 * @see IJstType#getInitializers(boolean)
	 */
	public List<IStmt> getInitializers(boolean isStatic) {
		return m_targetType.getInitializers(isStatic);
	}
	
	public JstBlock getInitBlock(){
		return m_targetType.getInitBlock();
	}

	/**
	 * @see IJstType#getStaticInitializers()
	 */
	public List<IStmt> getStaticInitializers() {
		return m_targetType.getStaticInitializers();
	}

	/**
	 * @see IJstType#getInstanceInitializers()
	 */
	public List<IStmt> getInstanceInitializers() {
		return m_targetType.getInstanceInitializers();
	}

	/**
	 * @see IJstType#hasStaticProperty(String,boolean)
	 */
	public boolean hasStaticProperty(final String varName, boolean recursive) {
		return m_targetType.hasStaticProperty(varName,recursive);
	}

	/**
	 * @see IJstType#hasInstanceProperty(String,boolean)
	 */
	public boolean hasInstanceProperty(String varName, boolean recursive) {
		return m_targetType.hasStaticProperty(varName,recursive);
	}

	/**
	 * @see IJstType#getMixins(boolean)
	 */
	public List<? extends IJstTypeReference> getMixinsRef() {
		return m_targetType.getMixinsRef();
	}
	
	@Override
	public List<? extends IJstType> getMixins() {
		return m_targetType.getMixins();
	}

	/**
	 * @see IJstType#hasMixins()
	 */
	public boolean hasMixins() {
		return m_targetType.hasMixins();
	}

	/**
	 * @see IJstType#getEmbededTypes()
	 */
	public List<? extends IJstType> getEmbededTypes(){
		return m_targetType.getEmbededTypes();
	}

	/**
	 * @see IJstType#getEmbededType(String)
	 */
	public IJstType getEmbededType(final String shortName) {
		return m_targetType.getEmbededType(shortName);
	}
	
	/**
	 * @see IJstType#getStaticEmbededTypes()
	 */
	public List<? extends IJstType> getStaticEmbededTypes() {
		return m_targetType.getStaticEmbededTypes();
	}

	/**
	 * @see IJstType#getInstanceEmbededTypes()
	 */
	public List<? extends IJstType> getInstanceEmbededTypes() {
		return m_targetType.getInstanceEmbededTypes();
	}

	/**
	 * @see IJstType#isEmbededType()
	 */
	public boolean isEmbededType(){
		return m_targetType.isEmbededType();
	}

	/**
	 * @see IJstType#getOuterType()
	 */
	public IJstType getOuterType() {
		return m_targetType.getOuterType();
	}
	
	/**
	 * @see IJstType#getSiblingType(String)
	 */
	public IJstType getSiblingType(final String shortName) {
		return m_targetType.getSiblingType(shortName);
	}
	
	/**
	 * @see IJstType#getSiblingTypes()
	 */
	public List<? extends IJstType> getSiblingTypes() {
		return m_targetType.getSiblingTypes();
	}

	/**
	 * @see IJstType#isSiblingType()
	 */
	public boolean isSiblingType(){
		return m_targetType.isSiblingType();
	}
	
	/**
	 * @see IJstType#isLocalType()
	 */
	public boolean isLocalType() {
		return m_targetType.isLocalType();
	}

	/**
	 * @see IJstType#getContainingType()
	 */
	public IJstType getContainingType() {
		return m_targetType.getContainingType();
	}
	
	public List<String> getComments() {
		return m_targetType.getComments();
	}
	/**
	 * @see IJstType#isParamName(String)
	 */
	public boolean isParamName(String name){
		return m_targetType.isParamName(name);
	}
	
	/**
	 * @see IJstType#getParamNames()
	 */
	public List<String> getParamNames(){
		return m_targetType.getParamNames();
	}
	
	/**
	 * @see IJstType#getParamTypes()
	 */
	public List<JstParamType> getParamTypes(){
		return m_targetType.getParamTypes();
	}
	
	/**
	 * {@link IJstType#getDoc()}
	 */
	public IJstDoc getDoc() {
		return m_targetType.getDoc();
	}
	
	//
	// API
	//
	public IJstType getType(){
		return m_targetType;
	}
	
	@Override
	public String toString(){
		return m_targetType.toString();
	}
	
	public Map<String, ? extends IJstType> getImportsMap() {
		return m_targetType.getImportsMap();
	}
	
	public Map<String, ? extends IJstType> getInactiveImportsMap() {
		return m_targetType.getImportsMap();
	}

	public List<IJstProperty> getEnumValues() {
		return m_targetType.getEnumValues();
	}
	
	public IJstProperty getEnumValue(final String name) {
		return m_targetType.getEnumValue(name);
	}
	
	public List<? extends IJstTypeReference> getExpectsRef() {
		return m_targetType.getExpectsRef();
	}

	public List<? extends IJstTypeReference> getInactiveImportsRef() {
		return m_targetType.getInactiveImportsRef();
	}
	
	
	
	@Override
	public boolean hasGlobalVars() {
		return m_targetType.hasGlobalVars();
	}

	@Override
	public IJstGlobalVar getGlobalVar(String name, boolean recursive) {
		return m_targetType.getGlobalVar(name, recursive);
	}

	@Override
	public IJstGlobalVar getGlobalVar(String name) {
		return m_targetType.getGlobalVar(name);
	}

	@Override
	public List<? extends IJstGlobalVar> getGlobalVars() {
		return m_targetType.getGlobalVars();
	}

	@Override
	public boolean isImpliedImport() {
		return m_targetType.isImpliedImport();
	}
	
	@Override
	public Map<String, Object> getOptions() {
		return m_targetType.getOptions();
	}
	
	@Override
	public List<? extends IJstType> getSecondaryTypes() {
		return m_targetType.getSecondaryTypes();
	}
	
	@Override
	public boolean isSingleton() {
		return m_targetType.isSingleton();
	}

	
}
