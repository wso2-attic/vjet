/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.vjolang.feature.tests;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.junit.Before;
import org.junit.Test;



import org.ebayopensource.dsf.common.FileUtils;

//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoNSTests {
	private HashMap<String,String> outerVJExpectedProp = new HashMap<String,String>();
	private HashMap<String,String> innerVJExpectedProp1 = new HashMap<String,String>();
	private HashMap<String,String> innerVJExpectedProp2 = new HashMap<String,String>();
	private HashMap<String,String> innerStaticVJExpectedProp1 = new HashMap<String,String>();
	private HashMap<String,String> innerStaticVJExpectedProp2 = new HashMap<String,String>();
	private HashMap<String, String> innerStaticVJExpectedProp3 = new HashMap<String,String>();
	
	@Before
	public void init(){
		outerVJExpectedProp.put("type", "type::a.b.vjDollar");
		outerVJExpectedProp.put("vjDollar", "type::a.b.vjDollar");
		outerVJExpectedProp.put("A", "type::a.b.A");
		outerVJExpectedProp.put("CALIAS", "type::a.b.C");
		outerVJExpectedProp.put("IA", "type::a.b.IA");
		outerVJExpectedProp.put("D", "type::a.b.D");
		
		innerVJExpectedProp1.put("type", "type::a.b.vjDollar.Inner1");
		innerVJExpectedProp1.put("vjDollar", "type::a.b.vjDollar");
		innerVJExpectedProp1.put("A", "type::a.b.A");
		innerVJExpectedProp1.put("CALIAS", "type::a.b.C");
		innerVJExpectedProp1.put("IA", "type::a.b.IA");
		innerVJExpectedProp1.put("D", "type::a.b.D");
		innerVJExpectedProp1.put("outer", "a.b.vjDollar");
		
		
		innerVJExpectedProp2.put("type", "type::a.b.vjDollar.Inner1.Inner1_1");
		innerVJExpectedProp2.put("Inner1", "type::a.b.vjDollar.Inner1");
		innerVJExpectedProp2.put("outer", "a.b.vjDollar.Inner1");
		
		
		innerStaticVJExpectedProp1.put("type", "type::a.b.vjDollar.InnerS");
		innerStaticVJExpectedProp1.put("vjDollar", "type::a.b.vjDollar");
		innerStaticVJExpectedProp1.put("A", "type::a.b.A");
		innerStaticVJExpectedProp1.put("CALIAS", "type::a.b.C");
		innerStaticVJExpectedProp1.put("IA", "type::a.b.IA");
		innerStaticVJExpectedProp1.put("D", "type::a.b.D");		
		
		
		innerStaticVJExpectedProp2.put("type", "type::a.b.vjDollar.InnerS.InnerS1_1");
		innerStaticVJExpectedProp2.put("InnerS", "type::a.b.vjDollar.InnerS");
		innerStaticVJExpectedProp2.put("outer", "a.b.vjDollar.InnerS");
		
		
		innerStaticVJExpectedProp3.put("type", "type::a.b.vjDollar.InnerS.InnerS1_1.Inner1_1_1");
		innerStaticVJExpectedProp3.put("InnerS1_1", "type::a.b.vjDollar.InnerS.InnerS1_1");
		innerStaticVJExpectedProp3.put("outer", "a.b.vjDollar.InnerS.InnerS1_1");
		
		//System.out.println("Before");
	}
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Assert vj$ property for outer type")
	public  void testOuterVJ() throws Exception {
		String name = "vjDollar.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		//ParseUtils.printTree(unit.getSyntaxRoot());
		
		IJstType outerType = unit.getType();		
		//System.err.println("Outer type="+ outerType.getName());
		
		IJstProperty outerVJProp = outerType.getProperty("vj$");
		IJstType outerVJType = outerVJProp.getType();
		List<IJstProperty> outerVJProps = outerVJType.getProperties();
		int index=1;
		 for (IJstProperty itm : outerVJProps) {
			 //System.out.println(String.format("[%d] Outer VJ prop[%s],type [%s]", index++, itm.getName().toString() ,itm.getType().getName()));      
			 String expectedType = outerVJExpectedProp.get(itm.getName().toString());
			
			 assertEquals(expectedType, itm.getType().getName().toString());
			
	     }
	}	
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Assert vj$ property for inner type")
	public  void testInnerVJ() throws Exception {
		String name = "vjDollar.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		//ParseUtils.printTree(unit.getSyntaxRoot());
		
		IJstType outerType = unit.getType();	
		
		IJstType innerType = outerType.getEmbededType("Inner1");
		IJstProperty innerVJProp = innerType.getProperty("vj$");
		IJstType innerVJType = innerVJProp.getType();	
	
		List<IJstProperty> innerVJProps = innerVJType.getProperties();
		int index=1;
		 for (IJstProperty itm : innerVJProps) {
			 //System.out.println(String.format("[%d] Inner VJ prop[%s],type [%s]", index++, itm.getName().toString() ,itm.getType().getName()));      
			 String expectedType = innerVJExpectedProp1.get(itm.getName().toString());
			 assertEquals(expectedType, itm.getType().getName().toString());
			 //System.err.println("expectedType = "+ expectedType);
	     }
	}	
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Assert vj$ property for 2nd level inner type")
	public  void testInnerVJ2() throws Exception {
		String name = "vjDollar.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		//ParseUtils.printTree(unit.getSyntaxRoot());
		
		IJstType outerType = unit.getType();	
		
		IJstType innerType = outerType.getEmbededType("Inner1").getEmbededType("Inner1_1");
		IJstProperty innerVJProp = innerType.getProperty("vj$");
		IJstType innerVJType = innerVJProp.getType();	
	
		List<IJstProperty> innerVJProps = innerVJType.getProperties();
		int index=1;
		 for (IJstProperty itm : innerVJProps) {
			 //System.out.println(String.format("[%d] Inner2 VJ prop[%s],type [%s]", index++, itm.getName().toString() ,itm.getType().getName()));      
			 String expectedType = innerVJExpectedProp2.get(itm.getName().toString());
			 assertEquals(expectedType, itm.getType().getName().toString());
			 //System.err.println("expectedType = "+ expectedType);
	     }
	}	
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Assert vj$ property for static inner type")
	public  void testInnerStaticVJ() throws Exception {
		String name = "vjDollar.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		//ParseUtils.printTree(unit.getSyntaxRoot());
		
		IJstType outerType = unit.getType();	
		
		IJstType innerStaticType = outerType.getEmbededType("InnerS");
		IJstProperty innerStaticVJProp = innerStaticType.getProperty("vj$");
		IJstType innerStaticVJType = innerStaticVJProp.getType();	
	
		List<IJstProperty> innerStaticVJProps = innerStaticVJType.getProperties();
		int index=1;
		 for (IJstProperty itm : innerStaticVJProps) {
			 //System.out.println(String.format("[%d] Static Inner VJ prop[%s],type [%s]", index++, itm.getName().toString() ,itm.getType().getName()));      
			 String expectedType = innerStaticVJExpectedProp1.get(itm.getName().toString());
			 assertEquals(expectedType, itm.getType().getName().toString());
			 
	     }
	}
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Assert vj$ property for 2nd level static inner type")
	public  void testInnerStaticVJ2() throws Exception {
		String name = "vjDollar.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		//ParseUtils.printTree(unit.getSyntaxRoot());
		
		IJstType outerType = unit.getType();	
		
		IJstType innerStaticType = outerType.getEmbededType("InnerS").getEmbededType("InnerS1_1");
		IJstProperty innerStaticVJProp = innerStaticType.getProperty("vj$");
		IJstType innerStaticVJType = innerStaticVJProp.getType();	
	
		List<IJstProperty> innerStaticVJProps = innerStaticVJType.getProperties();
		int index=1;
		 for (IJstProperty itm : innerStaticVJProps) {
			 //System.out.println(String.format("[%d] Static Inner2 VJ prop[%s],type [%s]", index++, itm.getName().toString() ,itm.getType().getName()));      
			 String expectedType = innerStaticVJExpectedProp2.get(itm.getName().toString());
			 assertEquals(expectedType, itm.getType().getName().toString());
			 
	     }
	}
	
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Assert vj$ property for 3rd level static inner type")
	public  void testInnerStaticVJ3() throws Exception {
		String name = "vjDollar.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		//ParseUtils.printTree(unit.getSyntaxRoot());
		
		IJstType outerType = unit.getType();	
		
		IJstType innerStaticType = outerType.getEmbededType("InnerS").getEmbededType("InnerS1_1").getEmbededType("Inner1_1_1");
		IJstProperty innerStaticVJProp = innerStaticType.getProperty("vj$");
		IJstType innerStaticVJType = innerStaticVJProp.getType();	
	
		List<IJstProperty> innerStaticVJProps = innerStaticVJType.getProperties();
		int index=1;
		 for (IJstProperty itm : innerStaticVJProps) {
			 //System.out.println(String.format("[%d] Static Inner3 VJ prop[%s],type [%s]", index++, itm.getName().toString() ,itm.getType().getName()));      
			 String expectedType = innerStaticVJExpectedProp3.get(itm.getName().toString());
			 assertEquals(expectedType, itm.getType().getName().toString());
			 
	     }
	}
	
	
	public static void main(String[] args) {
		VjoNSTests test = new VjoNSTests();
		try {
			test.init();
			test.testOuterVJ();
			test.testInnerVJ();
			test.testInnerVJ2();
			test.testInnerStaticVJ();
			test.testInnerStaticVJ2();
			test.testInnerStaticVJ3();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test //TODO - Work in progress, need more coverage
	//@Category({P1, UNIT, FAST})
	//@Description("Generic assertions for vj$ property")
	public void testVj$() throws Exception {
		String name = "vjDollar.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		ParseUtils.printTree(unit.getSyntaxRoot());
		assertNotNull(unit.getType());
		IJstType type = unit.getType();
		
		assertNotNull(type.getProperty("vj$", true));
		
		System.out.println("Static: ");
        IJstType staticVj$ = type.getProperty("vj$", true).getType();
        assertNotNull(staticVj$);
        assertEquals(6, staticVj$.getProperties().size());
        for (IJstProperty itm : staticVj$.getProperties()) {
        	System.out.println(itm.getName() + " : " + itm.getType().getName());
        }
        //Call getType second time
        assertEquals(6, type.getProperty("vj$", true).getType().getProperties().size());
        
        assertNotNull(type.getProperty("vj$", false));
		System.out.println("Instance: ");
        IJstType insVj$ = type.getProperty("vj$", false).getType();
        assertNotNull(insVj$);
        assertEquals(6, insVj$.getProperties().size());
        for (IJstProperty itm : insVj$.getProperties()) {
        	System.out.println(itm.getName() + " : " + itm.getType().getName());
        }
        
        assertNotNull(type.getEmbededType("Inner1").getProperty("vj$", false));
        assertFalse(type.getEmbededType("Inner1").getModifiers().isStatic());
        assertFalse(type.getProperty("Inner1").getModifiers().isStatic());
        assertTrue(type.getProperty("InnerS").getModifiers().isStatic());
		
        System.out.println("Inner: ");
        IJstType inner = type.getEmbededType("Inner1").getProperty("vj$", false).getType();
        assertNotNull(inner);
        for (IJstProperty itm : inner.getProperties()) {
        	System.out.println(itm.getName() + " : " + itm.getType().getName());
        }

	}
	
}
