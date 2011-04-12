/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.control;

import java.net.MalformedURLException;
import java.net.URL;

import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.jst.declaration.JstType;

public interface ICodeGenPathResolver {
	
	URL getVjoFilePath(JstType type)throws MalformedURLException;
	URL getJsrFilePath(JstType type) throws MalformedURLException;
	URL getJavaFilePath(JstType type)throws MalformedURLException;
	
	ICodeGenPathResolver DEFAULT = new ICodeGenPathResolver() {

		public URL getJsrFilePath(JstType type) throws MalformedURLException {
			return new URL(AstBindingHelper.getPackagePath(type)
				+ "/" + type.getSimpleName() + "Jsr.java");
		}

		public URL getVjoFilePath(JstType type) throws MalformedURLException {
			return new URL(AstBindingHelper.getPackagePath(type)
					+ "/"+ type.getSimpleName() + ".js");
		}
		
		public URL getJavaFilePath(JstType type) throws MalformedURLException {
			return new URL(AstBindingHelper.getPackagePath(type)
					+ "/"+ type.getSimpleName() + ".java");
		}
		
	};

}
