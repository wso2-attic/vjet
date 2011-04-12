/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;


public class ResolveMethod {
	
	public static int foo(int[] intArr){
        return 2;
	}
	
	public  int bar(int[] intArr){
        return 2;
    }
	
	public int revolveFoo(){
		int nums[] = { 1, 2, 3, 4, 5 }; 
		return foo(nums) ;
	}
	
	public int resolveBar(){
		int nums[] = { 1, 2, 3, 4, 5 }; 
		return bar(nums) ;
	}
}
