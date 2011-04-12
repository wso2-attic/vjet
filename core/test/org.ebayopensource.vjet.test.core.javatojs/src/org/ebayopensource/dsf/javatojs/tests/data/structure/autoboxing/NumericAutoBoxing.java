/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.autoboxing;

public class NumericAutoBoxing {
	
	Integer aI = 2;
	Integer bI  = 1 + aI + 2 + aI + 3;
	Integer cI  = 1 + ++aI + 2 + aI-- + 3;
	Integer dI  = ++aI;
	Integer eI  = ++aI + 1;
	Integer fI  = aI--;
	Integer gI  = aI-- + 1;
	Integer hI  = (++aI);
	Integer iI = (aI = 3) + 4;
	Integer jI = 2 + (aI = 3) + 4;
	
	int ai = 2;
	int bi  = 1 + aI + 2 + aI + 3;
	int ci  = 1 + ++aI + 2 + aI-- + 3;
	int di  = ++aI;
	int ei  = ++aI + 1;
	int fi  = aI--;
	int gi  = aI-- + 1;
	int hi  = (++aI);
	int ii = (aI = 3) + 4;
	int ji = 2 + (aI = 3) + 4;	
	
	public void assignment(){
		
		aI = 2;
		bI  = 1 + aI + 2 + aI + 3;
		cI  = 1 + ++aI + 2 + aI-- + 3;
		dI  = ++aI;
		eI  = ++aI + 1;
		Integer fI  = aI--;
		Integer gI  = aI-- + 1;
		Integer hI  = (++aI);
		Integer iI = (aI = 3) + 4;
		Integer jI = 2 + (aI = 3) + 4;
		
		ai = 2;
		bi  = 1 + aI + 2 + aI + 3;
		ci  = 1 + ++aI + 2 + aI-- + 3;
		di  = ++aI;
		ei  = ++aI + 1;
		int fi  = aI--;
		int gi  = aI-- + 1;
		int hi  = (++aI);
		int ii = (aI = 3) + 4;
		int ji = 2 + (aI = 3) + 4;	
	}
	
	public void mtdInvocation(){
		foo(2);
		foo(1 + aI + 2 + aI + 3);
		foo(1 + ++aI + 2 + aI-- + 3);
		foo(++aI);
		foo(++aI + 1);
		foo(aI--);
		foo(aI-- + 1);
		foo((++aI));
		foo((aI = 3) + 4);
		foo(2 + (aI = 3) + 4);
	}
	
	void foo(Integer x){
		
	}
}
