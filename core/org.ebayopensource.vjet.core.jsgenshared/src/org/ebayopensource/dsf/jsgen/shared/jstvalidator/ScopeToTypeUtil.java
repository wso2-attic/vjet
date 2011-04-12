/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.jstvalidator;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;

public class ScopeToTypeUtil {

	// TODO figure out how to scope the rest of statement,property, expression
	public ScopeId getScopeId(IJstNode node){
		String name = node.getClass().getName();
		if(name.equals(IJstType.class.getName())){
			return ScopeIds.GLOBAL;
		}
		
		if(name.equals(IJstMethod.class.getName())){
			return ScopeIds.METHOD;
		}
		
		
		if(name.equals(IJstProperty.class.getName())){
			return ScopeIds.PROPERTY;
		}
		
		return ScopeIds.GLOBAL;
	}
	
}
