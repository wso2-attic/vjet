/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

import org.ebayopensource.dsf.javatojs.tests.data.Super;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Methods.TypeA;

public class NestedTypes extends Super {
	
	private String m_name;
	private static int s_total;
	
	public static void main(String[] args){
		NestedTypes nested = new NestedTypes();
		
		TypeA a = new TypeA("A");
		org.ebayopensource.dsf.javatojs.tests.data.structure.Methods.TypeB b1 = new org.ebayopensource.dsf.javatojs.tests.data.structure.Methods.TypeB("B");
		TypeB b2 = new TypeB();
		TypeC c = new TypeC();
		TypeC c2 = new NestedTypes.TypeC();
		TypeD d = nested.new TypeD("hello");
	}
	
	private static String getStaticMtd(){
		return "Static";
	}
	
	private String getInstanceMtd(){
		TypeA a = new TypeA("A");
		org.ebayopensource.dsf.javatojs.tests.data.structure.Methods.TypeB b1 = new org.ebayopensource.dsf.javatojs.tests.data.structure.Methods.TypeB("B");
		TypeB b2 = new TypeB();
		TypeC c = new TypeC();
		TypeC c2 = new NestedTypes.TypeC();
		TypeD d = new TypeD("hello");
		TypeD d2 = this.new TypeD("hello");
		return a.getName() + b1.getName() + b2.getB() + c.getC() + d.getD();
	}
	
	public static class TypeB extends NestedTypes {
		public String getB(){
			TypeD d = new TypeD("hello");
			s_total++;
			s_super = null;
			NestedTypes.s_total++;
			Super.s_super = null;
			return "C" + d.getD() + getStaticMtd() + getSuperStaticMtd();
		}
	}

	public static class TypeC {
		public String getC(){
			s_total++;
			s_super = null;
			NestedTypes.s_total++;
			Super.s_super = null;
			return "C" + getStaticMtd() + getSuperStaticMtd();
		}
	}
	
	public class TypeD extends TypeC {
		private TypeD(String msg){
		}
		public String getD(){
			TypeC c = new TypeC();
			s_total++;
			s_super = m_name;
			return  "D" + c.getC() + getStaticMtd() + getInstanceMtd() + getSuperStaticMtd() + getSuperInstanceMtd();
		}
	}
	
	private String m_group;
	
	public String getGroup(){
		return m_group;
	}
}
