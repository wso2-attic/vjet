/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.aexclude;

import org.ebayopensource.dsf.javatojs.anno.AExclude;
@AExclude
public class A {
	
	public static int s_field1 = 10;
	
	public int m_field2 = 10;
	
	
	public static void s_m1(){		
	}
	
	public void m_m1(){		
	}
	
}
