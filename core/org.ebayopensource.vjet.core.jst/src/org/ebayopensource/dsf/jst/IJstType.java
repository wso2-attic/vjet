/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.token.IStmt;

/**
 * Interface for all JST types, including itype/atype/etype/mtype
 * 
 * 
 */
public interface IJstType extends IJstNode {

	/**
	 * Answer the package of the type
	 * @return JstPackage
	 */
	JstPackage getPackage();
	
	/**
	 * Answer the simple name of the type
	 * @return String
	 */
	String getSimpleName();
	
	/**
	 * Answer the full name of the type
	 * @return String
	 */
	String getName();
	
	/**
	 * Answer the alias of the type
	 * @return String
	 */
	String getAlias();
	
	/**
	 * Answer the alias name (a short-hand to refer the type) of the type
	 * @return String
	 */
	String getAliasTypeName();
	
	/**
	 * Answer whether it's a class
	 * @return boolean
	 */
	boolean isClass();
	
	/**
	 * Answer whether it's an interface
	 * @return boolean
	 */
	boolean isInterface();
	
	/**
	 * Answer whether it's a enum
	 * @return boolean
	 */
	boolean isEnum();
	
	/**
	 * Answer whether it's a mixin
	 * @return boolean
	 */
	boolean isMixin();
	
	/**
	 * Answer whether it's a mixin
	 * @return boolean
	 */
	boolean isOType();
	
	/**
	 * Answer whether it's a ftype
	 * @return
	 */
	boolean isFType();
	
	/**
	 * Answer whether it is marked as meta type
	 */
	boolean isMetaType();
	
	/**
	 * Answer whether it is a fake type to hold floating JS blocks
	 */
	boolean isFakeType();
	
	/**
	 * Answer whether it's anonymous
	 * @return boolean
	 */
	boolean isAnonymous();
	
	/**
	 * Answer whether the type imported the type with given name
	 * @param typeName String
	 * @return boolean
	 */
	boolean hasImport(String typeName);
	
	/**
	 * Answer the imported type with given name.
	 * @param typeName String
	 * @return IJstType
	 */
	IJstType getImport(String typeName);
	
	
	JstBlock getInitBlock();
	
	/**
	 * Answer the imported type with given name.
	 * @param typeName
	 * @return
	 */
	IJstTypeReference getImportRef(String typeName);
	
	
	/**
	 * Answer an unmodifiable list of imports 
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getImports();
	
	/**
	 * Answer the inactive imported type with given name.
	 * @param typeName String
	 * @return IJstType
	 */
	IJstType getInactiveImport(String typeName);
	
	/**
	 * Answer the inactive imported type with given name.
	 * 
	 * @param typeName
	 * @return
	 */
	IJstTypeReference getInactiveImportRef(String typeName);
	
	/**
	 * Answer an unmodifiable list of inactive imports 
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getInactiveImports();
	
	/**
	 * Answer an unmodifiable list of inactive imports 
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstTypeReference> getInactiveImportsRef();
	
	/**
	 * Answer an unmodifiable list of imports 
	 * @return List<? extends IJstTypeReference>
	 */
	List<? extends IJstTypeReference> getImportsRef();
	
	/**
	 * Answer the fully qualified imported type with given name.
	 * @param typeName String
	 * @return IJstType
	 */
	IJstType getFullyQualifiedImport(String typeName);
	
	/**
	 * Answer an unmodifiable list of fully qualified imports 
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getFullyQualifiedImports();
	
	/**
	 * Answer an unmodifiable list of fully qualified imports 
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstTypeReference> getFullyQualifiedImportsRef();
	
	/**
	 * add otype to type
	 * @param otype IJstOtype
	 */
	void addOType(IJstOType otype);
	
	/**
	 * Answer an unmodifiable list of OTypes 
	 * @return List<IJstOType>
	 */
	List<IJstOType> getOTypes();
	
	/**
	 *  
	 * @return IJstOType
	 */
	IJstOType getOType(String name);
	
	/**
	 * Answer the type this type extends. If this type is an interface (itype)
	 * and extends more than one itypes, it returns the first one.
	 * @return IJstType
	 */
	IJstType getExtend();
	
	/**
	 * Answer the type this type extends. If this type is an interface (itype)
	 * and extends more than one itypes, it returns the first one.
	 * @return IJstType
	 */
	IJstTypeReference getExtendRef();
	
	/**
	 * Answer an unmodifiable list of types this type extends. 
	 * If the type is not an interface, the size of the list should not exceeds one.
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getExtends();
	
	/**
	 * Answer an unmodifiable list of types this type extends. 
	 * If the type is not an interface, the size of the list should not exceeds one.
	 * @return List<? extends IJstTypeReference>
	 */
	List<? extends IJstTypeReference> getExtendsRef();
	
	/**
	 * Answer an unmodifiable list of types this type satisfies. 
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getSatisfies();
	
	/**
	 * Answer an unmodifiable list of types this type satisfies. 
	 * @return List<? extends IJstTypeReference>
	 */
	List<? extends IJstTypeReference> getSatisfiesRef();
	
	/**
	 * Answer an unmodifiable list of types this type expects. 
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getExpects();
	
	/**
	 * Answer an unmodifiable list of types this type expects. 
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstTypeReference> getExpectsRef();
	
	/**
	 * 
	 * Get all derived types for a specific type
	 * 
	 * @return
	 */
	List<IJstType> getAllDerivedTypes();

	/**
	 * Answer the modifiers of the type
	 * @return JstModifiers
	 */
	JstModifiers getModifiers();
	
	/**
	 * Answer the property with given name in this type, searching static first then instance,
	 * not including inherited property from base type(s).
	 * @param name String
	 * @return IJstProperty
	 */
	IJstProperty getProperty(String name);
	
	/**
	 * Answer the property with given name in this type, including base type(s)
	 * if "recursive" is true.
	 * @param name String
	 * @param isStatic boolean
	 * @param recursive boolean if true, search in base types as well
	 * @return IJstProperty
	 */
	IJstProperty getProperty(String name, boolean isStatic);
	
	/**
	 * Answer the property with given name in this type, including base type(s)
	 * if "recursive" is true.
	 * @param name String
	 * @param isStatic boolean
	 * @param recursive boolean if true, search in base types as well
	 * @return IJstProperty
	 */
	IJstProperty getProperty(String name, boolean isStatic, boolean recursive);
	
	/**
	 * Answer an unmodifiable list of all properties in this type
	 * @return List< IJstProperty>
	 */
	List< IJstProperty> getProperties();

	/**
	 * Answer an unmodifiable list of static or instance properties in this type
	 * @param isStatic boolean
	 * @return List< IJstProperty>
	 */
	List< IJstProperty> getProperties(boolean isStatic);
	
	/**
	 * Answer an unmodifiable list of static or instance properties in this type
	 * , and super types if recursive is true.
	 * @param isStatic boolean
	 * @param recursive boolean
	 * @return List< IJstProperty>
	 */
	List<IJstProperty> getAllPossibleProperties(boolean isStatic, boolean recursive);
	
	/**
	 * Answer whether the type has any static properties
	 * @return boolean
	 */
	boolean hasStaticProperties();
	
	/**
	 * Answer whether the type has an static property with given name. 
	 * Including base type(s) if "recursive" is true.
	 * @param name String
	 * @param recursive boolean if true, search in base types as well
	 * @return boolean
	 */
	boolean hasStaticProperty(final String name, boolean recursive);

	/**
	 * Answer the static property in this type with given name
	 * @return IJstProperty
	 */
	IJstProperty getStaticProperty(String name);
	
	/**
	 * Answer the the static property with given name. 
	 * Including base type(s) if "recursive" is true.
	 * @param name String
	 * @param recursive boolean if true, search in base types as well
	 * @return boolean
	 */
	IJstProperty getStaticProperty(final String name, boolean recursive);	
	
	/**
	 * Answer an unmodifiable list of static properties in this type
	 * @return List< IJstProperty>
	 */
	List< IJstProperty> getStaticProperties();
	
	/**
	 * Answer whether the type has any instance properties
	 * @return boolean
	 */
	boolean hasInstanceProperties() ;
	
	/**
	 * Answer whether the type has an instance property with given name. 
	 * Including base type(s) if "recursive" is true.
	 * @param name String
	 * @param recursive boolean if true, search in base types as well
	 * @return boolean
	 */
	boolean hasInstanceProperty(String name, boolean recursive);

	/**
	 * Answer the instance property in this type with given name
	 * @return IJstProperty
	 */
	IJstProperty getInstanceProperty(String name);
	
	/**
	 * Answer the the instance property with given name. 
	 * Including base type(s) if "recursive" is true.
	 * @param name String
	 * @param recursive boolean if true, search in base types as well
	 * @return boolean
	 */
	IJstProperty getInstanceProperty(final String name, boolean recursive);
	
	/**
	 * Answer an unmodifiable list of instance properties in this type
	 * @return List< IJstProperty>
	 */
	List<IJstProperty> getInstanceProperties();
	
	/**
	 * Answer the constructor of the type
	 * @return IJstMethod
	 */
	IJstMethod getConstructor();
	
	/**
	 * Answer the method with given name in this type, searching for static first then instance,
	 * not including inherited method from base type(s).
	 * @param name String
	 * @return IJstMethod
	 */
	IJstMethod getMethod(String name);
	
	/**
	 * Answer the method with given name in this type, not including
	 * inherited method from base type(s).
	 * @param name String
	 * @param isStatic boolean 
	 * @return IJstMethod
	 */
	IJstMethod getMethod(String name, boolean isStatic);
	
	/**
	 * Answer the method with given name in this type, including base type(s)
	 * if "recursive" is true.
	 * @param name String
	 * @param isStatic boolean 
	 * @param recursive boolean if true, search in base types as well
	 * @return IJstMethod
	 */
	IJstMethod getMethod(String name, boolean isStatic, boolean recursive) ;
	
	/**
	 * Answer an unmodifiable list of all methods in this type
	 * @return List<? extends IJstMethod>
	 */
	List<? extends IJstMethod> getMethods();

	/**
	 * Answer an unmodifiable list of static or instance methods in this type
	 * @param isStatic boolean
	 * @return List<? extends IJstMethod>
	 */
	List<? extends IJstMethod> getMethods(boolean isStatic);
	
	/**
	 * Answer an unmodifiable list of static or instance methods in this type,
	 * and super types if recursive is true.
	 * @param isStatic boolean
	 * @param recursive boolean
	 * @return List<? extends IJstMethod>
	 */
	List<? extends IJstMethod> getMethods(boolean isStatic, boolean recursive);
	
	/**
	 * Answer whether the type has any static methods in this type
	 * @return boolean
	 */
	boolean hasStaticMethods();
	
	/**
	 * Answer whether the type has a static method with given name
	 * Including base type(s) if "recursive" is true.
	 * @param mtdName String
	 * @param recursive boolean if true, search in base types as well
	 * @return boolean
	 */
	boolean hasStaticMethod(String mtdName, boolean recursive);
	
	/**
	 * Answer an unmodifiable list of static methods in this type
	 * @return List<? extends IJstMethod>
	 */
	List<? extends IJstMethod> getStaticMethods();
	
	/**
	 * Answer the static method in this type with given name
	 * @return IJstMethod
	 */
	IJstMethod getStaticMethod(String name);
	
	/**
	 * Answer the the static method with given name. 
	 * Including base type(s) if "recursive" is true.
	 * @param name String
	 * @param recursive boolean if true, search in base types as well
	 * @return boolean
	 */
	IJstMethod getStaticMethod(final String name, boolean recursive);
	
	/**
	 * Answer whether the type has any instance methods
	 * @return boolean
	 */
	boolean hasInstanceMethods();
	
	/**
	 * Answer whether the type has an instance method with given name. 
	 * Including base type(s) if "recursive" is true.
	 * @param mtdName String
	 * @param recursive boolean if true, search in base types as well
	 * @return boolean
	 */
	boolean hasInstanceMethod(String mtdName, boolean recursive);
	
	/**
	 * Answer an unmodifiable list of instance methods in this type
	 * @return List<? extends IJstMethod>
	 */
	List<? extends IJstMethod> getInstanceMethods();
	
	/**
	 * Answer the instance method in this type with given name
	 * @return IJstMethod
	 */
	IJstMethod getInstanceMethod(String name);
	
	/**
	 * Answer the the instance method with given name. 
	 * Including base type(s) if "recursive" is true.
	 * @param name String
	 * @param recursive boolean if true, search in base types as well
	 * @return boolean
	 */
	IJstMethod getInstanceMethod(final String name, boolean recursive);
	
	/**
	 * Answer an unmodifiable list of initializers
	 * @param isStatic boolean
	 * @return List<IStmt>
	 */
	List<IStmt> getInitializers(boolean isStatic);
	
	/**
	 * Answer an unmodifiable list of static initializers
	 * @return List<IStmt>
	 */
	List<IStmt> getStaticInitializers();
	
	/**
	 * Answer an unmodifiable list of instance initializerss
	 * @return List<IStmt>
	 */
	List<IStmt> getInstanceInitializers();
	
	/**
	 * Answer an unmodifiable list of mixed-in modules
	 * @param isStatic boolean
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstTypeReference> getMixinsRef();
	
	/**
	 * Answer an unmodifiable list of mixed-in modules
	 * @param isStatic boolean
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getMixins();

	/**
	 * Answer whether the type has static mixin's
	 */
	boolean hasMixins();

	/**
	 * Answer an unmodifiable list of all embeded types in this type
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getEmbededTypes();

	/**
	 * Answer the embeded type with given short name
	 * @param shortName String
	 * @return IJstType
	 */
	IJstType getEmbededType(String shortName);
	
	/**
	 * Answer an unmodifiable list of all secondary types in this type
	 * These types are similar to Java Raw type support see java lang spec
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getSecondaryTypes();
	
	/**
	 * Answer an unmodifiable list of static embeded types of the type
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getStaticEmbededTypes();
	
	/**
	 * Answer an unmodifiable list of instance embeded types of the type
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getInstanceEmbededTypes();
	
	/**
	 * Answer whether the type is an embeded type
	 * @return boolean
	 */
	boolean isEmbededType();
	
	/**
	 * Answer the outer type if the type is an inner type
	 * @return IJstType
	 */
	IJstType getOuterType() ;
	
	/**
	 * Answer the sibling type with given short name
	 * @param shortName String
	 * @return IJstType
	 */
	IJstType getSiblingType(String shortName);
	
	/**
	 * Answer an unmodifiable list of sibling types of the type
	 * @return List<? extends IJstType>
	 */
	List<? extends IJstType> getSiblingTypes();
	
	/**
	 * Answer whether the type is an sibling type
	 * @return boolean
	 */
	boolean isSiblingType();
	
	/**
	 * Answer whether the type is a local type(a type that's inside a method)
	 * @return boolean
	 */
	boolean isLocalType();

	/**
	 * Answer the containing type if the type is an sibling type
	 * @return IJstType
	 */
	IJstType getContainingType() ;
	
	/**
	 * Answer whether the given name is a param name of this type
	 * @param name String
	 * @return boolean
	 */
	boolean isParamName(String name);
	
	/**
	 * Answer an unmodifiable list of param names 
	 * @return List<String>
	 */
	List<String> getParamNames();
	
	/**
	 * Answer an unmodifiable list of param types 
	 * @return List<JstParamType>
	 */
	List<JstParamType> getParamTypes();
	
	Map<String, ? extends IJstType> getImportsMap();
	
	Map<String, ? extends IJstType> getInactiveImportsMap();
	/**
	 * Answer list of enum values 
	 * @return List<IJstProperty>
	 */
	List<IJstProperty> getEnumValues();	

	/**
	 * Answer the enum value with given name in this type, 
	 * not including inherited property from base type(s).
	 * @param name String
	 * @return IJstProperty
	 */
	IJstProperty getEnumValue(final String name);
	
	/**
	 * Answer Javadoc style documentation of this type
	 * @return IJstDoc or <code>null</code> if type has no documentation
	 */
	IJstDoc getDoc();
	
	
	/**
	 * Answers if the type has any globals that need to be promoted
	 * does not answer yes if globals are from base type(s).
	 * @param name String
	 * @return IJstGlobal
	 */
	boolean hasGlobalVars();
	/**
	 * Answer the name of the global variable with given name in this type,
	 * does not include inherited globals from base type(s).
	 * @param name String
	 * @return IJstGlobal
	 */
	IJstGlobalVar getGlobalVar(String name);
	
	
	/**
	 * Answer the global var with given name in this type, including base type(s)
	 * if "recursive" is true.
	 * @param name String
	 * @param recursive boolean if true, search in base types as well
	 * @return IJstGlobalVar
	 */
	IJstGlobalVar getGlobalVar(String name, boolean recursive) ;
	
	/**
	 * Answer an unmodifiable list of all global vars defined in this type
	 * @return List<? extends IJstGlobalVar>
	 */
	List<? extends IJstGlobalVar> getGlobalVars();


	/**
	 * Answers if this type requires an explicit import
	 * or if the import is implied. For example in java  
	 * primitive types such as boolean or Boolean is implied
	 * @return
	 */
	boolean isImpliedImport();
	
	Map<String, Object> getOptions();

	boolean isSingleton();

}
