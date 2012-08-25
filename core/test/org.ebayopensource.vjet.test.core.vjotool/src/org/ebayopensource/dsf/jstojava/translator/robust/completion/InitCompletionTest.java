/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.completion;



import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Test;



/**
 * Test case to validate parser for various scenes in "inits" block. The test
 * case should be put into DsfJsToJavaTest. but there is no way to get
 * JstPraseController there, so put it here.
 * 
 * 
 * 
 */
//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class InitCompletionTest extends BaseTest {

	/**
	 * Get JstType from VjoParser. When there is "inits" block, completion after
	 * "this.base." will cause NPE;
	 */
	@Test
	public void testThisBaseFromParser() {
		String fileName = "initTestFile2.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"a.b.c.MyVjoType2", "this.base.");
		assertNotNull(jstType);
		IJstParseController controller = CodeCompletionUtil
				.getJstParseController();
		controller.resolve(jstType);
	}

	/**
	 * Get JstType from SyntaxTreeFactory
	 */
	@Test
	public void testThisBaseFromSyntaxTreeFactory() {
		String fileName = "initTestFile2.js.txt";
		IJstType jstType = getJstTypeSyntaxTreeFactory(fileName, "this.base.");

		assertNotNull(jstType);
		IJstParseController controller = CodeCompletionUtil
				.getJstParseController();
		controller.resolve(jstType);
	}

	/**
	 * Get JstType from VjoParser. When there are two function in "inits" block,
	 * will cause NPE
	 */
	@Test
	public void testDupliFunctionFromParser() {

		String fileName = "initTestFile1.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"a.b.c.MyVjoType2", "this.base.");
		assertNotNull(jstType);
		IJstParseController controller = CodeCompletionUtil
				.getJstParseController();
		controller.resolve(jstType);

	}

	/**
	 * Get JstType from SyntaxTreeFactory
	 */
	// This test case doesn't appear to be valid no inits is in the test file
	// TODO explain usage here
	
//	@Test
//	public void testSingleCompletionFromParser() {
//
//		String fileName = "initTestFile3.js.txt";
//		IJstType jstType = getJstTypeFromVjoParser(fileName,
//				"Test", ".needs(\'");
//		assertNotNull(jstType);
//		JstCompletion completion = getJstCompletion(jstType);
//		assertNotNull(completion);
//
//	}

	/**
	 * Get JstType from SyntaxTreeFactory
	 */
	@Test
	public void testDoubleQuotoCompletionFromParser() {

		String fileName = "initTestFile4.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"a.b.c.MyVjoType2", ".needs(\"");
		assertNotNull(jstType);
		JstCompletion completion = getJstCompletion(jstType);
		assertNotNull(completion);
	}

	/**
	 * vjo.ctype('vjo.vjet.Sample2') //< public .inits(function(){ this. })
	 * .endType();
	 * 
	 */
	@Test
	public void testThisInInits1FromParser() {

		String fileName = "initTestFile5_1.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"a.b.c.MyVjoType2", "this.");
		assertNotNull(jstType);
		JstCompletion completion = getJstCompletion(jstType);
		assertNotNull("Can not get JstCompletion after 'this.'", completion);

	}

	/**
	 * vjo.ctype('vjo.vjet.Sample2') //< public .needs('vjo.vjet.Sample2')
	 * .inits(function(){ this. }) .endType();
	 * 
	 */
	@Test
	public void testThisInInits2FromParser() {

		String fileName = "initTestFile5_2.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"a.b.c.MyVjoType2", "this.");
		assertNotNull(jstType);
		JstCompletion completion = getJstCompletion(jstType);
		assertNotNull("Can not get JstCompletion after 'this.'", completion);
	}

	/**
	 * vjo.ctype('a.b.c.MyVjoType2') //< public .protos({ }).inits(function(){
	 * var list = document.getElementsByTagName("A"); //<NodeList var str =
	 * "";//<String list.; }) .endType();
	 * 
	 * This case is not correct
	 */
	@Test
	public void testVariableInInits2FromParser() {

		String fileName = "initTestFile6.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"a.b.c.MyVjoType2", "list.");
		assertNotNull(jstType);
		JstCompletion completion = getJstCompletion(jstType);
		assertNotNull("Can not get JstCompletion after 'list.'", completion);
		IJstNode node = completion.getRealParent();
		resovleJstType(jstType);
		IJstType type = null;
		if (node instanceof FieldAccessExpr) {
			FieldAccessExpr fae = (FieldAccessExpr) node;
			type = fae.getExpr().getResultType();
		}
		assertNotNull("Can not get JstType of varivalbe ", type);
	}
	
	@Test
	public void testScopeStackInInits2FromParser() {
		
		String fileName = "initTestFile7.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"a.b.c.MyVjoType2", "list.");
		assertNotNull(jstType);
		JstCompletion completion = getJstCompletion(jstType);
		assertNotNull("Can not get JstCompletion after 'list.'", completion);
		assertTrue("The scope stack is not correct ", completion
				.inScope(ScopeIds.INITS));
	}

	@Test
	public void testVariableInInits1FromParser() {

		String fileName = "initTestFile6_1.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"a.b.c.MyVjoType2", "list.");
		assertNotNull(jstType);
		JstCompletion completion = getJstCompletion(jstType);
		assertNotNull("Can not get JstCompletion after 'list.'", completion);
		IJstNode node = completion.getRealParent();
		IJstType type = null;
		resovleJstType(jstType);
		if (node instanceof FieldAccessExpr) {
			FieldAccessExpr fae = (FieldAccessExpr) node;
			type = fae.getExpr().getResultType();
		}
		assertNotNull("Can not get JstType of varivalbe ", type);
	}

	@Test
	public void testMethodFieldCOmpletionInitsFromParser() {

		String fileName = "initTestFile6.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"a.b.c.MyVjoType2", "inits(");
		assertNotNull(jstType);
		JstCompletion completion = getJstCompletion(jstType);
		assertNotNull("Can not get JstCompletion after 'this.'", completion);
	}
	@Test
//	@Ignore
	public void testVariableInInitsFromParser() {
		
		String fileName = "initTestFile8.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"a.b.c.MyVjoType2", "myA.");
		assertNotNull(jstType);
		JstCompletion completion = getJstCompletion(jstType);
		resovleJstType(jstType);
		assertNotNull("Can not get JstCompletion after 'this.'", completion);
		IJstNode node = completion.getRealParent();
		IJstType type = null;
		resovleJstType(jstType);
		if (node instanceof FieldAccessExpr) {
			FieldAccessExpr fae = (FieldAccessExpr) node;
			type = fae.getExpr().getResultType();
		}
		assertNotNull("Can not get JstType of varivalbe ", type);
	}

}
