/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.vjolang.feature.tests;







import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.List;

import junit.framework.Assert;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.dsf.ts.event.group.BatchGroupLoadingEvent;
import org.ebayopensource.dsf.ts.event.type.AddTypeEvent;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.lib.LibManager;
import org.ebayopensource.vjo.lib.TsLibLoader;
import org.junit.Ignore;
import org.junit.Test;



import org.ebayopensource.dsf.common.FileUtils;

//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class ParsingTests {

	static final String GROUP_PROJ = "TEST_PROJ_GROUP";
	static final String GROUP_LIB = "TEST_LIB_GROUP";
//	public final TypeName TYPE_JSA = new TypeName(GROUP_PROJ, "org.ebayopensource.dsf.tests.jst.ts.data.JSA");
//	public final TypeName TYPE_JSB = new TypeName(GROUP_PROJ, "org.ebayopensource.dsf.tests.jst.ts.data.JSB");
//	public final TypeName TYPE_JSC = new TypeName(GROUP_PROJ, "org.ebayopensource.dsf.tests.jst.ts.data.JSC");
//	public final TypeName TYPE_JSD = new TypeName(GROUP_PROJ, "org.ebayopensource.dsf.tests.jst.ts.data.JSD");
//
//	public final TypeName TYPE_JSA1 = new TypeName(GROUP_LIB, "org.ebayopensource.dsf.tests.jst.ts.data.JSA1");
//	public final TypeName TYPE_JSB1 = new TypeName(GROUP_LIB, "org.ebayopensource.dsf.tests.jst.ts.data.JSB1");
//	public final TypeName TYPE_JSC1 = new TypeName(GROUP_LIB, "org.ebayopensource.dsf.tests.jst.ts.data.JSC1");
//	public final TypeName TYPE_JSD1 = new TypeName(GROUP_LIB, "org.ebayopensource.dsf.tests.jst.ts.data.JSD1");
//	
	static final String TEST_A = "TestA";
	static final String TEST_B = "TestB";
	static final String TEST_C = "TestC";
	static final String TEST_D = "TestD";
	
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Parses and validates js with primitive types in props")
	public void primitiveAsProps() throws Exception{
		
		String name = "primitiveAsProps.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		if (file == null){
			System.out.println(">>>>> java.source.path = " + System.getProperty("java.source.path"));
			System.out.println(">>>>> java.class.path = " + System.getProperty("java.class.path"));
        	throw new RuntimeException("primitiveAsProps.txt is not found");
		}
		
		VjoParser p = new VjoParser();
		IJstParseController c = new JstParseController(p);
		IScriptUnit unit = c.parse(name, name, file);
		IJstType type = unit.getType();
		//ParseUtils.printTree(type);

		assertTrue(unit.getProblems().isEmpty());
		ParseUtils.validateJstSource(type);
		
	}
	
	

	@Test
	@Ignore
	//@Category({P2, UNIT, FAST})
	//@Description("Validates Arguments type")
	public void  bugArgumentsType() throws Exception{
		
		String name = "bugArguments.js";
		//bugArguments.js
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		assertTrue(unit.getProblems().size()==0);
		JstDepthFirstTraversal.accept(unit.getType(), new IJstVisitor(){

			public void endVisit(IJstNode node) {
				// TODO Auto-generated method stub
				
			}

			public void postVisit(IJstNode node) {
				// TODO Auto-generated method stub
				
			}

			public void preVisit(IJstNode node) {
				// TODO Auto-generated method stub
				
			}

			public boolean visit(IJstNode node) {
				if(node instanceof JstVars){
					JstVars vars = (JstVars)node;
					if(vars.getInitializer().toText().contains("arguments")){
						Assert.assertEquals("Arguments" , vars.getType().getName());
					}
				}
				return true;
			}
			
		});
//		ParseUtils.printTree2(unit.getType());
	}

	
	@Test
	//@Category({P3, UNIT, FAST})
	//@Description("Parsing HTML Code hiding comments")
	public void  htmlcodehiding() throws Exception{
		
		// fix for object literal field translator
		// requires controller fix from Yubin
		String name = "htmlcodehiding.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		VjoParser p = new VjoParser();
		IJstParseController c = new JstParseController(p);
		IScriptUnit unit = c.parse(name, name, file);
		assertTrue(unit.getProblems().isEmpty());
		
	}
	
	@Test
	//@Category({P2, UNIT, FAST})
	//@Description("Validates arguments in method")
	public void testArgumentsInMethod() throws Exception {
		String name = "simpletype.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		IJstParseController c = new JstParseController(p);
		IJstType t = c.parse(name, name, file).getType();
		ParseUtils.validateJstSource(t);
		
		List<? extends IJstMethod> methods = JstTypeHelper.getDeclaredMethods(t.getMethods());
		boolean foundIt = false;
		for(IJstMethod m: methods){
			JstBlock b = m.getBlock();
			JstVars var = (JstVars)b.getChildren().get(0);
			assertNotNull(var);
			List<AssignExpr> expressions = var.getAssignments();
			for(AssignExpr a:expressions){
				if(a.toString().contains("arguments")){
					foundIt = true;
				}
			}
			assertTrue(foundIt);
			
		}
	}
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Validates method invocation")
	public void testMtdInvocation() throws Exception {
		
		String name = "methodinv.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		IJstParseController c = new JstParseController(p);
		IJstType t = c.parse(name, name, file).getType();
		ParseUtils.printTree(t);
	}
	
	@Test
	//@Category({P3, UNIT, FAST})
	//@Description("Parses empty file")
	public void testEmptyFile() throws Exception {
		
		String name = "empty.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		IJstParseController c = new JstParseController(p);
		IJstType t = c.parse(name, name, file).getType();
		ParseUtils.printTree(t);
	}
	
	@Test
	//@Category({P3, UNIT, FAST})
	//@Description("Parsing partial file")
	public void testVjoPartialType() throws Exception {
		
		String name = "vjopartialtype.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		IJstParseController c = new JstParseController(p);
		IScriptUnit t = c.parse(name, name, file);
		ParseUtils.validateJstSource(t.getSyntaxRoot());
	}
	
	@Test
	//@Category({P3, UNIT, FAST})
	//@Description("Parsing partial OType. Bug8085")
	public void testVjoPartialOType() throws Exception {
		
		String name = "PartialOType.js";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		IJstParseController c = new JstParseController(p);
		IScriptUnit t = c.parse(name, name, file);
		ParseUtils.validateJstSource(t.getSyntaxRoot());
	}
	
	@Test
	//@Category({P2, UNIT, FAST})
	//@Description("Parsing js with property comments")
	public void testCommentTest() throws Exception {
		
		String name = "propcommenttest.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		IJstParseController c = new JstParseController(p);
		IJstType t = c.parse(name, name, file).getType();
//		ParseUtils.printTree(t);
		ParseUtils.validateJstSource(t);

	}
	
	@Test
	//@Category({P5, UNIT, FAST})
	//@Description("Parsing mtype copy")
	public void testMtypeCopy() throws Exception {
		
		/**
		 * Test the VJO Mtype copy after types are resolved
		 * 
		 */
		
		
	}
	
	
	
	@Test
	//@Category({P2, UNIT, FAST})
	//@Description("Parsing native types")
	public void testVjoGeneratorForNative() throws Exception {
		VjoParser p = new VjoParser();

		IJstParseController c = new JstParseController(p);
	
		JstTypeSpaceMgr tsMgr = new JstTypeSpaceMgr(c, new DefaultJstTypeLoader());
		
		
		TsLibLoader.loadDefaultLibs(tsMgr);
		ParseUtils.genTypes(tsMgr.getTypeSpace().getGroup(LibManager.JS_NATIVE_LIB_NAME));
		ParseUtils.genTypes(tsMgr.getTypeSpace().getGroup(LibManager.VJO_JAVA_LIB_NAME));
		ParseUtils.genTypes(tsMgr.getTypeSpace().getGroup(LibManager.JAVA_PRIMITIVE_LIB_NAME));
		ParseUtils.genTypes(tsMgr.getTypeSpace().getGroup(LibManager.JS_NATIVE_GLOBAL_LIB_NAME));
		
//		IJstType array = tsMgr.getQueryExecutor().findType(new TypeName(LibManager.JS_NATIVE_LIB_NAME,"Array"));

		
	}
	
	@Test
	//@Category({P2, UNIT, FAST})
	//@Description("Parsing and asserting array of types inherited and satisfied")
	public void testVjoAsArray() throws Exception {
		String name = "vjoasarray.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		IJstType type = unit.getType();
		assertTrue(type.getSatisfies().size()!=0);
		assertTrue(type.getExtends().size()!=0);
		ParseUtils.genType(type);
	}
	
	

	@Test
	//@Category({P3, UNIT, FAST})
	//@Description("Correct translation of RegExpLiteral")
	public void testbug1425() throws Exception {
		String name = "bug1425.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		IScriptUnit unit  = ParseUtils.createScriptUnit(name, file);
		ParseUtils.genType(unit.getType());
	}
	
	@Test
	//@Category({P3, UNIT, FAST})
	//@Description("Variable Comments parsed correctly")
	public void testbug2024() throws Exception {
		String name = "bug2024.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		ParseUtils.genType(unit.getType());
	}
	
	@Test
	//@Category({P2, UNIT, FAST})
	//@Description("method return type: String should compatible with its definition " +
//			"return type: HTMLFormElement of the method: foo in the expression: return ref;")
	public void testbug5515() throws Exception {
	
//		//>public void f(String x);
//		//>public void f(Number x);
//		//>public void f(Date x);
//		f:function(x){
//			x.length;
//			x.charCodeAt(3);
//			x.slice(3);
//			x.getDate();
//			
//			
//		}
		String name = "bug5515.js";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		TypeName typename = new TypeName("test", "org.ebayopensource.dsf.vjolang.feature.tests.bug5515");
		

		
		IJstParseController controller = new JstParseController(new VjoParser());
		JstTypeSpaceMgr ts = new JstTypeSpaceMgr(controller, new DefaultJstTypeLoader());
		ts.initialize();
		TsLibLoader.loadDefaultLibs(ts);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		ts.processEvent(new AddTypeEvent<IJstType>(typename,unit.getType()));
		
		IJstType type = ts.getQueryExecutor().findType(typename);
		
		
		// TODO resolve and find out qual expression is correct
		// TODO init variable are resolved prob with Jst Method
		
		ParseUtils.genType(type);
		


//		ParseUtils.printTree2(type);
		
		
		
	}
	
	@Test
	@Ignore
	//@Category({P5, UNIT, FAST})
	//@Description("property name: length is undefined in the expression: eval.length." +
//			"Test case com.ebay.tools.vjet2.core.test.bug.BugVerifyTests.test5069() added")
	public void testbug5069() throws Exception {
		
//		//>public void f(String x);
//		//>public void f(Number x);
//		//>public void f(Date x);
//		f:function(x){
//			x.length;
//			x.charCodeAt(3);
//			x.slice(3);
//			x.getDate();
//			
//			
//		}
		String name = "bug5069.js";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		TypeName typename = new TypeName("test", "org.ebayopensource.dsf.vjolang.feature.tests.bug5069");
		
		
		
		IJstParseController controller = new JstParseController(new VjoParser());
		JstTypeSpaceMgr ts = new JstTypeSpaceMgr(controller, new DefaultJstTypeLoader());
		ts.initialize();
		TsLibLoader.loadDefaultLibs(ts);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		ts.processEvent(new AddTypeEvent<IJstType>(typename,unit.getType()));
		
		IJstType type = ts.getQueryExecutor().findType(typename);
		
		
		
//		ParseUtils.printTree2(type);
		
		
		
	}
	
	@Test
	//@Category({P3, UNIT, FAST})
	//@Description("Parsing js with variable name as keyword")
	public void testbugKeywordNewParserBug() throws Exception {
		
//		//>public void f(String x);
//		//>public void f(Number x);
//		//>public void f(Date x);
//		f:function(x){
//			x.length;
//			x.charCodeAt(3);
//			x.slice(3);
//			x.getDate();
//			
//			
//		}
		String name = "keywordtest.js";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		TypeName typename = new TypeName("test", "org.ebayopensource.dsf.vjolang.feature.tests.keywordtest");
		
		
		
		IJstParseController controller = new JstParseController(new VjoParser());
		JstTypeSpaceMgr ts = new JstTypeSpaceMgr(controller, new DefaultJstTypeLoader());
		ts.initialize();
		TsLibLoader.loadDefaultLibs(ts);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		ts.processEvent(new AddTypeEvent<IJstType>(typename,unit.getType()));
		
		IJstType type = ts.getQueryExecutor().findType(typename);
		
		
		// TODO resolve and find out qual expression is correct
		// TODO init variable are resolved prob with Jst Method
		
		ParseUtils.genType(type);
		
		
		
//		ParseUtils.printTree2(type);
		
		
		
	}
	
	@Test
	//@Category({P2, UNIT, FAST})
	//@Description("Parsing js with nested type")
	public void testbugNestedTypesJst() throws Exception {
		
//		//>public void f(String x);
//		//>public void f(Number x);
//		//>public void f(Date x);
//		f:function(x){
//			x.length;
//			x.charCodeAt(3);
//			x.slice(3);
//			x.getDate();
//			
//			
//		}
		String name = "nestedScriptUnit.js";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		TypeName typename = new TypeName("test", "org.ebayopensource.dsf.vjolang.feature.tests.nestedScriptUnit");
		
		IJstParseController controller = new JstParseController(new VjoParser());
		JstTypeSpaceMgr ts = new JstTypeSpaceMgr(controller, new DefaultJstTypeLoader());
		ts.initialize();
		TsLibLoader.loadDefaultLibs(ts);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		ts.processEvent(new AddTypeEvent<IJstType>(typename,unit.getType()));
		
		IJstType type = ts.getQueryExecutor().findType(typename);
		
		
		// TODO resolve and find out qual expression is correct
		// TODO init variable are resolved prob with Jst Method
		
		ParseUtils.genType(type);
//		ParseUtils.printTree2(type);
//		System.out.println("\n---------Syntax Root------------\n" );
//		ParseUtils.printTree2( unit.getSyntaxRoot());
		
		
	}
	
	@Test
	//@Category({P2, UNIT, FAST})
	//@Description("Parsing js with void method returning value")
	public void test5109() throws Exception {
		
//		//>public void f(String x);
//		//>public void f(Number x);
//		//>public void f(Date x);
//		f:function(x){
//			x.length;
//			x.charCodeAt(3);
//			x.slice(3);
//			x.getDate();
//			
//			
//		}
		
		String name = "bug5109.js";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		TypeName typename = new TypeName("test", "org.ebayopensource.dsf.vjolang.feature.tests.bug5109");
		
		IJstParseController controller = new JstParseController(new VjoParser());
		JstTypeSpaceMgr ts = new JstTypeSpaceMgr(controller, new DefaultJstTypeLoader());
		ts.initialize();
		TsLibLoader.loadDefaultLibs(ts);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		ts.processEvent(new AddTypeEvent<IJstType>(typename,unit.getType()));
		
		IJstType type = ts.getQueryExecutor().findType(typename);
		
		
		// TODO resolve and find out qual expression is correct
		// TODO init variable are resolved prob with Jst Method
		
		ParseUtils.genType(type);
//		ParseUtils.printTree2(type);
		
		
	}
	
	
	@Test
	//@Category({P3, FUNCTIONAL, FAST})
	//@Description("Parsing js with variable in inits was add to JstType as child directly.")
	public void testbug5172() throws Exception {
		String name = "bug5172.js";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		TypeName typename = new TypeName("test", "org.ebayopensource.dsf.vjolang.feature.tests.bug5172");
		
		IJstParseController controller = new JstParseController(new VjoParser());
		JstTypeSpaceMgr ts = new JstTypeSpaceMgr(controller, new DefaultJstTypeLoader());
		ts.initialize();
		TsLibLoader.loadDefaultLibs(ts);
		IScriptUnit unit = ParseUtils.createScriptUnit(name, file);
		ts.processEvent(new AddTypeEvent<IJstType>(typename,unit.getType()));
		
		IJstType type = ts.getQueryExecutor().findType(typename);
		
		
		// TODO resolve and find out qual expression is correct
		// TODO init variable are resolved prob with Jst Method
		
		ParseUtils.genType(type);
//		ParseUtils.printTree2(type);
	}
	
	private BatchGroupLoadingEvent createBatchEvent() {
		
		String groupFullPath = getGroupPath();
		
		BatchGroupLoadingEvent batchEvent = new BatchGroupLoadingEvent();
		
		batchEvent.addGroupEvent(new AddGroupEvent(TEST_A, groupFullPath+"/" +TEST_A, "src", null));
		batchEvent.addGroupEvent(new AddGroupEvent(TEST_B, groupFullPath+"/" +TEST_B, "src", null));
		batchEvent.addGroupEvent(new AddGroupEvent(TEST_C, groupFullPath+"/" +TEST_C, "src", null));
		batchEvent.addGroupEvent(new AddGroupEvent(TEST_D, groupFullPath+"/" +TEST_D, "src", null));
		
		return batchEvent;
	}
	private String getGroupPath() {
		URL url = this.getClass().getClassLoader().getResource("org.ebayopensource.dsf/tests/jst/ts/workspaceTS/TestA/src/test/A.vjo");
		
		String path = url.getFile();
		
		System.out.println("Test JS file path is " + path);		

		int end = path.indexOf("workspaceTS");
		int len = new String("workspaceTS").length();
		String groupFullPath = path.substring(0, end+len);
		return groupFullPath;
	}
	
}
