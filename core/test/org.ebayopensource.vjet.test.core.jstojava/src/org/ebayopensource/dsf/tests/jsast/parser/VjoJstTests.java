/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.tests.jsast.parser;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.Test;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoJstTests {


	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test parsed js file")
	 public void testSimpleJsAST() throws IOException{
	 
//		File simple1 = new File(ResourceUtil.getResource(VjoJstTests.class, "simplevjo.js.txt").getFile());
		URL simple1 = ResourceUtil.getResource(VjoJstTests.class, "simplevjo.js.txt");
		IJstType type = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, simple1).getType();
			
//		((JstType)type).dump();
		assertFalse(type.isInterface());
		JstPackage pkg = type.getPackage();
		assertNotNull(pkg.getSource().getLength());
		assertTrue(pkg.getSource().getStartOffSet()>-1);
		assertTrue(pkg.getSource().getEndOffSet()>0);
		assertEquals("mrp", type.getPackage().getName());
		assertEquals("mrp.BaseCapability",type.getName());
		assertEquals("BaseCapability", type.getSimpleName());
		assertNotNull(type.getConstructor());
		assertNotNull(type.getStaticMethods());
		IJstMethod doItStaticMethod = type.getMethod("doIt");
		assertNotNull(doItStaticMethod);
	//	assertEquals(234,doItStaticMethod.getSource().getStartOffSet());
		assertNotNull(type.getStaticProperties());
		assertNotNull(type.getInstanceMethods());
		assertNotNull(type.getInstanceProperties());
		assertNotNull(type.getImports());
		assertNotNull(type.getExtends());
		assertNotNull(type.getStaticInitializers());
	  }

	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test definition js file with static methods and blocks")
	 public void testTypeWithDef() throws IOException{
	 
//		File simple1 = new File(ResourceUtil.getResource(VjoJstTests.class, "typewithdef.js.txt").getFile());
		URL simple1 = ResourceUtil.getResource(VjoJstTests.class, "typewithdef.js.txt");
		IJstType type = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, simple1).getType();

//		((JstType)type).dump();
		assertFalse(type.isInterface());
		assertEquals("a.b.c", type.getPackage().getName());
		assertEquals("MySingleton", type.getSimpleName());
		assertTrue(type.getInstanceMethods().size()==0);		
	  }
	
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test parsed js with inner type")
	 public void testTypeWithInnerType() throws IOException{
	 
//		File simple1 = new File(ResourceUtil.getResource(VjoJstTests.class,
//				"simplevjoinnertype.js.txt").getFile());
		URL simple1 = ResourceUtil.getResource(VjoJstTests.class, "simplevjoinnertype.js.txt");
		IJstType type = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, simple1).getType();	
//		((JstType)type).dump();
		assertFalse(type.isInterface());
		assertEquals("a.b.c", type.getPackage().getName());
		assertEquals("simplevjo", type.getSimpleName());
//		assertNotNull(type.getConstructor());		
	  }
	
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test parsed js with Itype")
	 public void testIType() throws IOException{
	 
//		File simple1 = new File(ResourceUtil.getResource(VjoJstTests.class, "simpleitype.js.txt").getFile());
		URL simple1 = ResourceUtil.getResource(VjoJstTests.class, "simpleitype.js.txt");
		IJstType type = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, simple1).getType();		
//		((JstType)type).dump();		
		assertTrue(type.isInterface());			
		assertEquals("org.ebayopensource.dsf.tests.jsast.parser", type.getPackage().getName());
		assertEquals("org.ebayopensource.dsf.tests.jsast.parser.simpleitype",type.getName());
		assertEquals("simpleitype", type.getSimpleName());
		assertTrue(type.getInstanceMethods().size()>0);
	  }
	
	

	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test parsed js with Atype")
	 public void testAType() throws IOException{
	 
//		File simple1 = new File(ResourceUtil.getResource(VjoJstTests.class, "simpleatype.js.txt").getFile());
		URL simple1 = ResourceUtil.getResource(VjoJstTests.class, "simpleatype.js.txt");
		IJstType type = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, simple1).getType();
//		((JstType)type).dump();		
		assertFalse(type.isInterface());
		assertEquals("org.ebayopensource.dsf.tests.jsast.parser", type.getPackage().getName());
		assertEquals("org.ebayopensource.dsf.tests.jsast.parser.simpleatype",type.getName());
		assertEquals("simpleatype", type.getSimpleName());
		assertTrue(type.getInstanceMethods().size()>0);		
	  }
	
	

	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test parsed js with Enum type")
	 public void testEnumType() throws IOException{
	 
//		File simple1 = new File(ResourceUtil.getResource(VjoJstTests.class, "simplevjoenum.js.txt").getFile());
		URL simple1 = ResourceUtil.getResource(VjoJstTests.class, "simplevjoenum.js.txt");
		IJstType type = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, simple1).getType();
//		((JstType)type).dump();		
		assertFalse(type.isInterface());
		assertTrue(type.isEnum());
		assertEquals("common", type.getPackage().getName());
		assertEquals("common.MyCars",type.getName());
		assertEquals("MyCars", type.getSimpleName());		
	  }

	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test parsed js with Object literal in ctype")
	public void testOL1() throws Exception {
		
		TranslateCtx ctx = new TranslateCtx();
		ctx.setCompletionPos(31);
//		File simple1 = new File(ResourceUtil.getResource(this.getClass(), "OL1.txt").getFile());
		URL simple1 = ResourceUtil.getResource(VjoJstTests.class, "OL1.txt");
		IJstType type = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse("testgroup", simple1.getPath(), VjoParser.getContent(simple1), ctx).getType();
		IJstMethod mtd = type.getMethod("a");
		assertEquals(true, mtd.getBlock().getStmts().get(0) instanceof JstVars);
		
		JstVars stmt = (JstVars)mtd.getBlock().getStmts().get(0);
		AssignExpr aExpr =(AssignExpr)stmt.getInitializer().getAssignments().get(0);
		assertEquals(true, aExpr.getExpr() instanceof ObjLiteral);
		
		ObjLiteral ol = (ObjLiteral)aExpr.getExpr();
		assertEquals(true, ol.getNVs().get(0).getValue() instanceof FuncExpr);
		
		FuncExpr fExpr = (FuncExpr)ol.getNVs().get(0).getValue();
		
		assertTrue("Function expression missing in Object literal!", fExpr != null);
		
	}

}
