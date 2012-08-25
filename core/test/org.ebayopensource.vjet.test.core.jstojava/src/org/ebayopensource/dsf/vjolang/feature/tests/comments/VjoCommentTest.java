/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.vjolang.feature.tests.comments;




import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjet.test.util.TestHelper;
//import org.ebayopensource.vjet.test.util.TestHelper;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.Test;



import org.ebayopensource.dsf.common.FileUtils;

//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoCommentTest {


	@Test
	//@Category({P5, UNIT, FAST})
	//@Description("Blank test method")
	public void testAnnotations() throws Exception {
		
	}
	
	@Test
	//@Category({P2, UNIT, FAST})
	//@Description("Test casting in comments parsed")
	public void testCast() throws IOException {
		IScriptUnit unit = compareText("SimpleCast.vjo");
		//TODO: test ArrayInitializers
		//TODO: test object literals
	}
	
	@Test
	//@Category({P2, UNIT, FAST})
	//@Description("Test dynamic type parsed")
	public void testDynamic() throws IOException {
		compareText("SimpleDynamic.vjo");
	}
	
	@Test
	//@Category({P2, UNIT, FAST})
	//@Description("Test inactive needs parsed")
	public void testInactiveNeeds() throws IOException {
		compareText("SimpleINeeds.vjo");
		compareText("TestCommentsUsingVjoSections.vjo");
		compareText("MoreINeeds.vjo");
		compareText("MoreINeeds2.vjo");
	}

	
	@Test
	//@Category({P2, UNIT, FAST})
	//@Description("Test false negative. Errors should occur incase of final atype," +
//			" final final ctype, method declared abstract in ctype," +
//			" method declaration with incorrect format")
	public void testErrors() throws Exception{
		IScriptUnit unit = checkErrors("AbstractFinal.vjo");
		assertEquals(1, unit.getProblems().size());
		IScriptProblem scriptProblem = unit.getProblems().get(0);
		assertEquals(44, scriptProblem.getSourceEnd());
		assertEquals(19, scriptProblem.getSourceStart());
		assertEquals("VJET comment error: can be either abstract or final, not both. For comment ://< public final abstract", scriptProblem.getMessage());
		
		unit = checkErrors("FinalAbstract.vjo");
		assertEquals(1, unit.getProblems().size());
		scriptProblem = unit.getProblems().get(0);
		assertEquals(44, scriptProblem.getSourceEnd());
		assertEquals(19, scriptProblem.getSourceStart());
		assertEquals("VJET comment error: can be either abstract or final, not both. For comment ://< public abstract final", scriptProblem.getMessage());
		
		
		unit = checkErrors("FinalFinal.vjo");
		assertEquals(1, unit.getProblems().size());
		scriptProblem = unit.getProblems().get(0);
		assertEquals(41, scriptProblem.getSourceEnd());
		assertEquals(19, scriptProblem.getSourceStart());
		assertEquals("VJET comment error: Duplicate modifier final not allowed. For comment ://< public final final", scriptProblem.getMessage());
		
		
		unit = checkErrors("Access1.vjo");
		assertEquals(1, unit.getProblems().size());
		scriptProblem = unit.getProblems().get(0);
		assertEquals(31, scriptProblem.getSourceStart());
		assertEquals(51, scriptProblem.getSourceEnd());
		assertEquals("VJET comment error: Access control cannot be set after abstract keyword. For comment ://>abstract public()", scriptProblem.getMessage());
		
		
		unit = checkErrors("TypeError.vjo");
		assertEquals(1, unit.getProblems().size());
		scriptProblem = unit.getProblems().get(0);
		assertEquals("VJET comment error: Syntax error at , delete this token For comment ://> public void foo(T1, )", scriptProblem.getMessage());
		assertEquals(46, scriptProblem.getSourceStart());
		assertEquals(71, scriptProblem.getSourceEnd());
		
//		unit = checkErrors("Conversion.vjo");
//		assertEquals(1, unit.getProblems().size());
//		scriptProblem = unit.getProblems().get(0);
//		assertEquals("VJET comment error: Exception at String. Expecting type to covert to. For comment ://>void doIt(String > )", scriptProblem.getMessage());
//		assertEquals(32, scriptProblem.getSourceStart());
//		assertEquals(55, scriptProblem.getSourceEnd());
		
		unit = checkErrors("TypeModifier.vjo");
		assertEquals(1, unit.getProblems().size());
		scriptProblem = unit.getProblems().get(0);
		assertEquals("VJET comment error: Syntax error at final delete this token For comment ://< int final", scriptProblem.getMessage());
		assertEquals(62, scriptProblem.getSourceStart());
		assertEquals(75, scriptProblem.getSourceEnd());
		
		
		// TODO go through rest of parse exceptions that are indicated in class JsCommentMeta. Bring up coverage an content checking
		
		
	}
	
	
	private static IScriptUnit checkErrors(String fileName) throws IOException{
		String file = getFileContents(fileName);
		IScriptUnit unit = parseUnit(fileName, file);
//		assertValidText(fileName, file, unit.getType());
		return unit;

	}
	
	
	public static IScriptUnit compareText(String fileName) throws IOException {
		String file = getFileContents(fileName);
		IScriptUnit unit = parseUnit(fileName, file);
		assertValidText(fileName, file, unit.getType());
		return unit;
	}

	private static String getFileContents(String fileName) throws IOException {
		String file = FileUtils.getResourceAsString(VjoCommentTest.class, fileName);
		return file;
	}

	private static IScriptUnit parseUnit(String fileName, String file) throws IOException {
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJavaPrimitiveLib());
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		p.addLib(LibManager.getInstance().getBrowserTypesLib());
		IJstParseController c = new JstParseController(p);
		IScriptUnit unit = c.parse(fileName, fileName, file);
		return unit;
	}

	private static void assertValidText(String fileName, String file,
			IJstType type) {
		GeneratorCtx ctx = new GeneratorCtx(CodeStyle.PRETTY);
		ctx.setNewline(TestHelper.NEWLINE);
		ctx.getConfig().setAddCodeGenAnnotation(false);
		VjoGenerator gen = new VjoGenerator(ctx);
		gen.setNewline(TestHelper.NEWLINE);
		gen.writeVjo(type);
		//System.out.println(gen.getGeneratedText());
		assertEquals("[Expected result is in " + fileName + "]", file.trim(),
				gen.getGeneratedText().trim());
	}
}
