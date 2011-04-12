/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.generate;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;

public interface IJsrFilters {
	//This method returns whether a class is JSR or not.
	public boolean isJsr(String jstTypeName);
	
	//This method returns if imports is not needed(Example: anything from java.lang.*)
	public boolean isSkipImport(IJstType jstType);
	
	public String normalize(String sourceName);

	public boolean isSkipSatisfies(IJstType jstType);
	
	public boolean isSkipMethod(IJstMethod jstMethod);
	
	public boolean isSkipExtends(IJstType jstType);
	
	public String decorateMethod(IJstMethod jstMethod);
    //This method returns if imports is not needed for Jsr
	//TODO:Should we combine this method and isSkipImport
	public boolean isSkipImportJsr(IJstType jstType);
	
	public boolean isJavaPrimitiveOrWrapper(String name);
	
	public boolean isJavaLang(String typeName);
	
	public boolean isJavaPrimitive(String name);
	
	public boolean isJavaWrapper(String name);
	
	public boolean isSerializableForVjo(IJstType type, boolean isRoot);
	
	public Class<?> getJavaTypeForSerialable(IJstType type);
	
	public boolean isSkipMethodAndProperties(String fullName);

	public String decorateProperty(String name);
}
