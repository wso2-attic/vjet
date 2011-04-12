/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.anno.derivedclz;

import org.ebayopensource.dsf.javatojs.anno.AExclude;


@AExclude
public class ExcludedBase {
	
	public static String s_field1;
	public String m_field1;
	
	public static void staticMethod1(){
		
	}
	public void memberMethod1(){
		
	}
	public String getValue(){
		return "value";
	}

}
