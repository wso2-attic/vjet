/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.anno.method;

import org.ebayopensource.dsf.javatojs.anno.AExclude;


public class ExcludedMethod {
	@AExclude
	public void excludedMethod1(){		
	}
	
	@AExclude
	public void excludedMethod2(){		
	}
	

	public void callExcludedMethod2(){	
		excludedMethod2();
	}
	
	@AExclude
	public static void excludedMethodStatic(){
		
	}	
	
	public void nonExcludedMethod(){
		
	}

}
