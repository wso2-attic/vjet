/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.overloading;

public class FindConstructor {

	FindConstructor(){
		this(1,2);
	}
	
	FindConstructor(Integer x, long y){
		this(x.intValue(), new Long(y));
	};
	
	FindConstructor(int x, Long y){};
	
	static void foo(){
		FindConstructor c1 = new FindConstructor();
		FindConstructor c2 = new FindConstructor(1,2);
		FindConstructor c3 = new FindConstructor(1,new Long(2));
		FindConstructor c4 = new FindConstructor(new Integer(1), 2);
	}
}
