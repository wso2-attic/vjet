/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.bootstrap;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;


public class CombinationFormula {

	public static void main( String args[] ){
		
		
		System.out.println(combinations(1));
		System.out.println(combinations(2));
		System.out.println(combinations(3));
		System.out.println(combinations(4));
	}
 
	public static long combinations(long n){
		long i= n-1;
		long sum = 0;
		while(i>0){
			sum = sum + combinationFormula(n, i);
			i--;
		}
		
		return sum;
	}


	
	public static long combinationFormula( long n, long r )
	{
		long factorialR = factorial(r);
		return factorial(n) / (factorial(n-r) * factorialR);
	}
	
	public static long factorial(long n){
		if(n<0){
			throw new DsfRuntimeException("n must be > 0");
		}
		
		long factorial = 1;
		for(long i=n; i>1; i-- ){
			factorial = factorial * i;
		}
		return factorial;
		
	}
	

	
}
