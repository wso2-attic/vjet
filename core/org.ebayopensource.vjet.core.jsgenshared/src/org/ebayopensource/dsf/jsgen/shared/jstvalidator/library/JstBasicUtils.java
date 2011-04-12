/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.jstvalidator.library;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;

public class JstBasicUtils {

	public static IJstMethod getMethod(String methodName, List<? extends IJstMethod> list){
		for(IJstMethod m:list){
			if(m.getName().getName().equals(methodName)){
				return m;
			}
		}
		return null;
	}
	
	public static IJstProperty getProp(String propName, List<IJstProperty> list){
		for(IJstProperty p:list){
			if(p.getName().getName().equals(propName)){
				return p;
			}
		}
		return null;
	}
	
	
}
