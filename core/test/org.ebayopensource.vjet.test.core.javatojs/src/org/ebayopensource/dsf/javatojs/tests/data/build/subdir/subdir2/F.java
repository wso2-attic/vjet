/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir2;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.javatojs.tests.data.build.Dependent;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir2.subdir3.A;

public class F {
	private static List<A> s_list = new ArrayList<A>();
	
	public static List<A> getList(){
		return s_list;
	}
	
	public E getE(Dependent d){
		return d.getE();
	}
	
	public G createG(){
		return new G();
	}
}
