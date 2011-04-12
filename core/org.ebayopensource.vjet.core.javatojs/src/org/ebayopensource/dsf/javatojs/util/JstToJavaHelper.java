/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.util;

import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.jst.declaration.JstType;

public class JstToJavaHelper {

	public static String getJavaTypeName(final JstType jstType){
		if (jstType == null){
			return null;
		}
		
		return TranslateCtx.ctx().getConfig().getPackageMapping().mapFrom(jstType.getName());
	}
}
