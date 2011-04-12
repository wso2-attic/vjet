/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.meta;

/**
 * Interface for meta data providers for Java to JavaScript
 * custom translation.
 */
public interface ICustomMetaProvider {

	/**
	 * Answer the custom type for java type with given name
	 * @param typeName String
	 * @return CustomType
	 */
	CustomType getCustomType(String typeName);
	
	/**
	 * Answer the privileged processor for the type with given name
	 * @param typeName String
	 * @return IPrivilegedProcessor
	 */
	IPrivilegedProcessor getPrivilegedTypeProcessor(String typeName);
	
	/**
	 * Answer the privileged processor for constructor of given type
	 * @param typeName String
	 * @return IPrivilegedProcessor
	 */
	IPrivilegedProcessor getPrivilegedConstructorProcessor(String typeName);
	
	/**
	 * Answer the privileged processor for the type and method with given names
	 * @param typeName String
	 * @param mtdName String
	 * @return IPrivilegedProcessor
	 */
	IPrivilegedProcessor getPrivilegedMethodProcessor(String typeName, String mtdName);
	
	/**
	 * Answer the privileged processor for the field with given type and field names
	 * @param typeName String
	 * @param fieldName String
	 * @return IPrivilegedProcessor
	 */
	IPrivilegedProcessor getPrivilegedFieldProcessor(String typeName, String fieldName);
}
