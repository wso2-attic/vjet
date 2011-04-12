/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.bootstrap;

import org.ebayopensource.dsf.jsgen.shared.generate.DefaultJsrFilters;


public class JsNativeJsrFilters extends DefaultJsrFilters {


	@Override
	public boolean isJsr(String jstTypeName) {
		if(jstTypeName.contains("Number")){
			return false;
		}
		if(jstTypeName.contains("Arguments")){
			return true;
		}
		if(jstTypeName.contains("Function")){
			return true;
		}
		
		
		return super.isJsr(jstTypeName);
	}
	
}
