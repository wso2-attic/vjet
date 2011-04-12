/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.ns;

import org.ebayopensource.dsf.javatojs.tests.data.Super;

public class SuperStatic extends Super {
	
	public void foo(){
		s_super = null;
		getSuperStaticMtd();
		
		super.s_super = null;
		super.getSuperStaticMtd();
		
		SuperStatic.s_super = null;
		SuperStatic.getSuperStaticMtd();
		
		Super.s_super = null;
		Super.getSuperStaticMtd();
	}
}
