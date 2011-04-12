/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.lib;

import java.io.InputStream;

import org.ebayopensource.dsf.jst.IJstSerializer;

/**
 * Contract for JST Lib resource resolvers
 */
public interface IResourceResolver {

	/**
	 * Answer the input stream of serialized global JST native types
	 * @return InputStream
	 */
	InputStream getJsNativeGlobalSerializedStream();
	
	/**
	 * Answer the input stream of serialized JST native types
	 * @return InputStream
	 */
	InputStream getJsNativeSerializedStream();
	
	/**
	 * Answer the input stream of serialized JST VJO types
	 * @return InputStream
	 */
	InputStream getVjoLibSerializedStream();
	
	/**
	 * Answer the input stream of serialized JST Java types
	 * @return InputStream
	 */
	InputStream getVjoJavaLibSerializedStream();
	
	/**
	 * Answer the JST serializer for JST resource streams;
	 * @return IJstSerializer
	 */
	IJstSerializer getJstSerializer();
}
