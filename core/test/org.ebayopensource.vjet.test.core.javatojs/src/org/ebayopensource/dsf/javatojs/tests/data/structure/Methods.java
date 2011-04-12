/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

public class Methods {

	public static void main(String[] args){

		Methods mtd = new Methods();
		
		mtd.varargs("Std", "a", "b", "c");
	}
	
	public final boolean varargs(String std, String... titles){
	    if (titles.length > 0){
	    	titles[0] = std;
	        return true;
	    }
	    return false;
	}
	
	public static class TypeA {
		private String m_name;
		public TypeA(final String name){
			m_name = name;
		}
		
		public String getName(){
			return m_name;
		}
		
		public String getNameA(){
			return m_name;
		}
		
		public static String getBase(){
			return "Base";
		}
	}
	
	public static class TypeB extends TypeA{
		public TypeB(final String name){
			super(name);
		}
		
		public String getName(){
			String name = getBase() + super.getBase() + getNameA() + super.getName();
			name += getPrefix() + this.getPrefix() + getPostfix() + this.getPostfix();
			return name;
		}
		
		private String getPrefix(){
			return "B";
		}
		
		private static String getPostfix(){
			return ".java";
		}
	}
}
