/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;


public class AutoboxingUnboxingTests {
	 public static Integer k = 999999999;    
	 public static Integer m = 9 + k;	
	 Integer h = new Integer(88);
	 int int1 = new Integer(99);
	 int int2 = h;
	 int int3 = h + 9;
	 
	 Integer [] intArr = {1,2,3,null};
	 int[] iArr = {k,m}; 
	 
	 public static class Generic<T> {
		 private T t;
		 public T get(){return t;}
		 public void set(T t){}
	 }
	
	public void autoBoxing(){
		//initialize  
		Integer aObj = 9,a = 0;
		int b = 88;
		Integer bObj = b;
		Integer cObj = 3 + b + bObj;
		Integer dObj = getInt();
		Long lObj = 9l;
		Double ddObj = 5.6d;
		char w = 'a';
		Character charObj = 'a'; 
		
		//Array
		Integer[] intArr = {1,2,34,8};
		Character [] charObjArr = {'a','b'};
		
		
		//assignment
		Integer eObj;
		eObj = 99;
		Integer fObj;
		fObj = b;
		Integer gObj;
		gObj = 66 + b + fObj;
		Integer hObj;
		hObj = getInt();
		long l = 93l;
		short s = 9;
		Character charObj1;
		charObj1 = 'r';
		
		//function call
		funIntArg(aObj,lObj,ddObj);
		funcIntegerArg(b,l,s);
		
		// Generics
		Generic<Integer> g = new Generic<Integer>();
		g.set(2);
	}
	
	public void autoUnboxing(){
		//initialize  
		int a = new Integer(9);
		Integer bObj = new Integer(99);
		int b =  bObj;
		int c = bObj + 9 + b;
		int d = rtnInteger1();	
		char c1 = new Character('a');
		
		//assignment
		int e;
		e = new Integer(88);
		int f;
		f = bObj;
		int g;
		g = bObj +66 + b;
		int h;
		h = rtnInteger1();	
		char c2;
		c2 = new Character('b');
		
		// Arithmetic
		Integer iObj = 2;
		int xx = iObj;
		xx = iObj + 2;
		xx = 1 + iObj + 2;
		int yy = iObj++;
		yy = iObj++ + 2;
		yy = 1 + iObj++ + 2;
		int zz = ++iObj;
		zz = ++iObj + 2;
		zz = 1 + ++iObj + 2;
		int ww = iObj + ++iObj + iObj++;
		
		//Array
		Integer i = new Integer(9);
		Integer j = new Integer(88);
		Integer k = new Integer(8);
		int [] iArr = {i,j,k};
		
		Character cObj1 = new Character('a');
		Character cObj2 = new Character('b');
		char [] cArr = {cObj1,cObj2};
		
		// Generics
		Generic<Integer> t = new Generic<Integer>();
		funIntArg(t.get(), 0, 0);
		int x = t.get();
	}	
	private int getInt() {
		return 12;
	}	
	private Integer rtnInteger1(){
		int i = 4;
		return i;
	}
	private Integer rtnInteger2(){
		return 66;
	}
	private Integer rtnInteger3(){
		return getInt();
	}
    private Integer rtnInteger4(){
    	int i = 9;
    	return 6 + i;
    }
    private int rtnInt1(){
    	return new Integer(99);
    }
    private int rtnInt2(){
    	Integer iObj = new Integer(88);
    	return iObj;
    }
    private int rtnInt3(){
    	return rtnInteger1();
    }
    private int rtnInt4(){
    	Integer iObj = new Integer(88);
    	return iObj + 9;
    }
    private int rtnGenerics(){
    	Generic<Integer> t = new Generic<Integer>();
    	return t.get();
    }
    private void funIntArg(int i,long l,double d){
    	
    }
    private void funcIntegerArg (Integer iObje, Long lObj, Short sObj){
    	
    }
}
