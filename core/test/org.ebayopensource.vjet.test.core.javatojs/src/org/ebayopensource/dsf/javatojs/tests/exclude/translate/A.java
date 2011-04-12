/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.translate;

import org.ebayopensource.dsf.javatojs.anno.AExclude;


public class A {
	@AExclude
	public static int s_field1 = 10;
	@AExclude
	public int m_field2 = 10;
	
	@AExclude
	public static void s_m1(){		
	}
	
	@AExclude
	public void m_m1(){		
	}
	
}
