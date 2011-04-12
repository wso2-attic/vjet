/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.generate;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;



public interface IJsrTypeProvider {

	enum Type{
		Primitive,
		Wrapper, Qualified, AddParams, IgnoreTypeMap
	}
	
	void setCurrentType(IJstType type);
	String getJavaTypeFullName(IJstType type, Type ... t);
	String getJavaTypeSimpleName(IJstType alias, Type ... t);
	boolean isExcludedFromImport(IJstType type);
	boolean isMappedEventType(IJstType type);
	boolean isInactiveImport(IJstType type);
	boolean isLiteral(IJstType type);
	boolean shouldImport(IJstType type);
	boolean supportsValueBinding(List<SimpleParam> list);
	boolean isBrowserEventType(IJstType argType);
	boolean supportsValueBinding(IJstType argType);
	void setEnableTypeMapping(boolean enableTypeMapping);
	boolean isObject(IJstType type);
	
	
}
