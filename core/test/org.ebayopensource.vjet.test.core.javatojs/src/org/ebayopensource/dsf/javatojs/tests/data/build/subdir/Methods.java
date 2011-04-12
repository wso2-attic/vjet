/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.build.subdir;

public class Methods {

	public static void main(String[] args){

		Methods mtd = new Methods();
		
		mtd.varargs("Std", "a", "b", "c");
	}
	
	public boolean varargs(String std, String... names){
	    if (names.length > 0){
	        names[0] = std;
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
	}
	
	public static class TypeB extends TypeA{
		public TypeB(final String name){
			super(name);
		}
		
		public String getName(){
			return getPrefix() + super.getName();
		}
		
		private String getPrefix(){
			return "B";
		}
	}
}
