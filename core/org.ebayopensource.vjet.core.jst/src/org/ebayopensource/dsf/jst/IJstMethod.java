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

import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstParamType;

/**
 * JST method declaration (as opposite to invocation).
 * 
 * 
 */
public interface IJstMethod extends IJstNode{
	
	/**
	 * Answer the name of the method
	 * @return JstName
	 */
	JstName getName();

	/**
	 * Answer an unmodifiable list of arguments of the method
	 * @return List<JstArg>
	 */
	List<JstArg> getArgs();
	
	/**
	 * Answer whether the method support variable arguments
	 * @return boolean
	 */
	public boolean isVarArgs();
	
	/**
	 * Answer the return type of the method
	 * @return IJstType
	 */
	IJstType getRtnType();
	
	/**
	 * Answer the return type of the method
	 * @return IJstTypeReference
	 */
	IJstTypeReference getRtnTypeRef();

	/**
	 * Answer the modifiers of the method
	 * @return JstModifiers
	 */
	JstModifiers getModifiers();
	
	/**
	 * Answer whether the method is a constructor
	 * @return boolean
	 */
	boolean isConstructor();

	/**
	 * Answer whether the method is public accessible
	 * @return boolean
	 */
	boolean isPublic();
	
	/**
	 * Answer whether the method is protected
	 * @return boolean
	 */
	boolean isProtected();

	/**
	 * Answer whether the method is internally accessible
	 * only from types with the same package
	 * @return boolean
	 */
	boolean isInternal();
	
	/**
	 * Answer whether the method is private
	 * @return boolean
	 */
	boolean isPrivate();

	/**
	 * Answer whether the method is static
	 * @return boolean
	 */
	boolean isStatic();
	
	/**
	 * Answer whether the return type is optional
	 * 
	 * @return
	 */
	boolean isReturnTypeOptional();

	/**
	 * Answer whether the method is final
	 * @return boolean
	 */
	boolean isFinal();

	/**
	 * Answer whether the method is abstract
	 * @return boolean
	 */
	boolean isAbstract();
	
	/**
	 * Answer the body of the method 
	 * @return JstBlock
	 */
	JstBlock getBlock();
	
	/**
	 * Answer whether the method is a dispatcher
	 * for overloaded methods
	 * @return boolean
	 */
	boolean isDispatcher();
	
	/**
	 * Answer an unmodifiable list of overloaded methods which
	 * is dispatched from this method
	 */
	List<IJstMethod> getOverloaded();
	
	/**
	 * Answer the original name of the method before suffixed
	 * for overloaded methods
	 * @return String
	 */
	String getOriginalName();
	
	/**
	 * Answer whether the given name is a param name of this method
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
	
	/**
	 * is otype Defined 
	 * @return boolean
	 */
	boolean isOType();
	
	
	
	/**
	 * Answer Javadoc style documentation of the method
	 * @return IJstDoc or <code>null</code> if method has no documentation
	 */
	IJstDoc getDoc();
	
	String getParamsDecoration();
	
	boolean isDuplicate();
	
	boolean isTypeFactoryEnabled();
	
	boolean isFuncArgMetaExtensionEnabled();
	
	/**
	 * Answer whether the method has JS annotations
	 * @return boolean
	 */
	public boolean hasJsAnnotation();
}
