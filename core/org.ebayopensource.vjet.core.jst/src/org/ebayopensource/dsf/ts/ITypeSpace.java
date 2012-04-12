/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts;

import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.group.Group;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.dsf.ts.method.MethodIndex;
import org.ebayopensource.dsf.ts.method.MethodName;
import org.ebayopensource.dsf.ts.property.PropertyIndex;
import org.ebayopensource.dsf.ts.property.PropertyName;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * Interface of type space which is capable to answer the following:
 * <li>Type(s) in the type space</li>
 * <li>Group(s) in the type space</li>
 * <li>Type dependencies</li>
 * <li>Property dependencies</li>
 * <li>Method dependencies</li>
 *
 * @param <T> Type the type space is for
 * @param <D> Type of dependents of properties and methods
 *  
 * 
 */
public interface ITypeSpace<T,D> {

	//
	// Types
	//
	/**
	 * Answer the type with given name
	 * @param typeName TypeName
	 * @return T
	 */
	T getType(final TypeName typeName);
	
	/**
	 * Answer a list of types with given name
	 * @param typeName String
	 * @return List<T>
	 */
	List<T> getType(final String typeName);
	
	/**
	 * Answer the type with given typeName visible to given fromGroup. 
	 * No visibility (group dependency) check if fromGroup is null
	 * @param typeName TypeName
	 * @param fromGroup IGroup<T>
	 * @return T null if type with given typeName is not found or not visible 
	 */
	T getVisibleType(final TypeName typeName, final IGroup<T> fromGroup);
	
	/**
	 * Answer a list of types with given typeName visible to given fromGroup. 
	 * No visibility (group dependency) check if fromGroup is null
	 * @param typeName String
	 * @param fromGroup IGroup<T>
	 * @return List<T> empty list if type with given typeName is not found or not visible 
	 */
	List<T> getVisibleType(final String typeName, final IGroup<T> fromGroup);
	
	/**
	 * Answer whether the type with given typeName is visible to given fromGroup 
	 * 
	 * @param typeName TypeName
	 * @param fromGroup IGroup<T>
	 * @return boolean false if type or group is not found for given typeName. 
	 * @exception RuntimeException if given fromGroup is null
	 */
	boolean isTypeVisible(final TypeName typeName, IGroup<T> fromGroup);
	
	/**
	 * Answer whether the type with given typeName is visible to given fromGroup 
	 * 
	 * @param typeName String
	 * @param fromGroup IGroup<T>
	 * @return boolean false if type or group is not found for given typeName. 
	 * @exception RuntimeException if given fromGroup is null
	 */
	boolean isTypeVisible(final String typeName, IGroup<T> fromGroup);
	
	/**
	 * Answer a read-only list of all types visible from given fromGroup.
	 * @param fromGroup IGroup<T>
	 * @return List<T>
	 */
	List<T> getVisibleTypes(IGroup<T> fromGroup);
	
	/**
	 * Answer a read-only map of all visiable types keyed by type name.
	 * @return Map<TypeName,T>
	 */
	Map<TypeName,T> getVisibleTypesMap(IGroup<T> fromGroup);
	
	/**
	 * Answer a read-only map of all types keyed by type name.
	 * @return Map<TypeName,T>
	 */
	Map<TypeName,T> getTypes();
	
	/**
	 * Get the customized user object associated with the type name
	 * @param typeName TypeName
	 * @return Object
	 */
	Object getUserObject(final TypeName typeName);
	
	/**
	 * Set the customized user object associated with the type
	 * @param typeName TypeName
	 * @param userObj Object
	 */
	boolean setUserObject(final TypeName typeName, Object userObj);
	
	//
	// Groups
	//
	/**
	 * Answer the group with given name
	 * @param groupName String
	 * @return IGroup<T>
	 */
	IGroup<T> getGroup(String groupName);
	
	/**
	 * Answer the group of given type
	 * @param type T
	 * @return Group<T>
	 */
	Group<T> getGroup(T type);
	
	/**
	 * Answer a read-only map of all groups keyed by group name.
	 * @return Map<String,? extends IGroup<T>>
	 */
	Map<String,? extends IGroup<T>> getGroups();

	//
	// Dependency Graph
	//
	/**
	 * Answer a read-only list of types that the type with given name
	 * directly depends on
	 * @param typeName TypeName
	 * @return List<T>
	 */
	List<T> getDirectDependencies(TypeName typeName);
	
	/**
	 * Answer a read-only list of types that the type with given name
	 * indirectly depends on
	 * @param typeName TypeName
	 * @return List<T>
	 */
	List<T> getIndirectDependencies(TypeName typeName);
	
	/**
	 * Answer a read-only list of all types that the type with given name
	 * depends on directly and indirectly
	 * @param typeName TypeName
	 * @return List<T>
	 */
	List<T> getAllDependencies(TypeName typeName);
	
	/**
	 * Answer a read-only list of types that directly depend on the type 
	 * with given name
	 * @param typeName TypeName
	 * @return List<T>
	 */
	List<T> getDirectDependents(TypeName typeName);
	
	/**
	 * Answer a read-only list of types that indirectly depend on the type 
	 * with given name
	 * @param typeName TypeName
	 * @return List<T>
	 */
	List<T> getIndirectDependents(TypeName typeName);
	
	/**
	 * Answer a read-only list of all types that directly and indirectly depend 
	 * on the type with given name
	 * @param typeName TypeName
	 * @return List<T>
	 */
	List<T> getAllDependents(TypeName typeName);
	
	//
	// Property Index
	//
	/**
	 * Answer the property index for the type with given name
	 * @param typeName TypeName
	 * @return PropertyIndex<T,D>
	 */
	PropertyIndex<T,D> getPropertyIndex(TypeName typeName);

	/**
	 * Answer a read-only list of dependents of the property with
	 * given property name and type name
	 * @param ptyName PropertyName
	 * @return List<D>
	 */
	List<D> getPropertyDependents(PropertyName ptyName);
	
	/**
	 * Answer a read-only map of unresolved property dependents
	 * @return Map<PropertyName,List<D>>
	 */
	Map<PropertyName,List<D>> getUnresolvedPropertyDependents();
	
	//
	// Method Index
	//
	/**
	 * Answer the method index for the type with given name
	 * @param typeName TypeName
	 * @return MethodIndex<T,D>
	 */
	MethodIndex<T,D> getMethodIndex(TypeName typeName);

	/**
	 * Answer a read-only list of dependents of the method with
	 * given method name and type name
	 * @param mtdName MethodName
	 * @return List<D>
	 */
	List<D> getMethodDependents(MethodName mtdName);
	
	/**
	 * Answer a read-only map of unresolved method dependents
	 * @return Map<MethodName,List<D>>
	 */
	Map<MethodName,List<D>> getUnresolvedMethodDependents();
	
	/**
	 * enumerate all T objects.
	 * @return Iterable<T> set of all T objects in the type space
	 */
	Iterable<T> enumerateTypes();
	
	/**
	 * Add a global type map entry to map a global scope short type name to a fully qualified
	 * long type name.
	 * For example, {"$", "jquery.JQ"}.
	 * 
	 * @param groupName - group to add the global map
	 * @param globalTypeName - short type name to map
	 * @param fullyQualifiedTypeName - fully qualified long name
	 */
	void addToGlobalTypeSymbolMap(String groupName, String globalTypeName, String fullyQualifiedTypeName);
	
	/**
	 * Add a global map entry to map a global scope short method/property name to a fully qualified
	 * long method name.
	 * For example, {"alert", "Window.alert"}, {"assertEqual", "vjo.jsunit.assertEqual"} etc.
	 * 
	 * @param groupName - group to add the global map
	 * @param globalMemberName - short method or property name to map
	 * @param fullyQualifiedMemberName - fully qualified long name
	 */
	void addToGlobalMemberSymbolMap(String groupName, String globalMemberName, String fullyQualifiedMemberName);
	
	
	void addToGlobalSymbolMap(String groupName, String varName, String typeName, D node);
	
	/**
	 * Add all methods and properties of the input type to the global scope.
	 * For example, {"JsNative", "Window"} will add all Window methods and properties
	 * to the global scope
	 * 
	 * @param typeName - type whose methods and properties are added to global scope
	 * 
	 */
	void addAllGlobalTypeMembers(TypeName typeName);

	void removeGlobalsFromType(String groupName, String name);
	
	boolean hasGlobalExtension(String globalVarName);
	
	List<D> getGlobalExtensions(String globalVarName);
	
	
	/**
	 * @see ITypeSpace#getVisibleGlobal(String, IGroup)
	 */
	D getVisibleGlobal(final String global, IGroup<T> fromGroup);

	Map<String, T> getAllVisibleAliasNames(IGroup<T> fromGroup);


}
