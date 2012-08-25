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

import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Test;



/**
 * Test case to validate parser for various scenes in "mixin" block. The test
 * case should be put into DsfJsToJavaTest. but there is no way to get
 * JstPraseController there, so put it here.
 * 
 * 
 * 
 */
//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class SquareBracketCompletionTest extends BaseTest {

	/**
	 * mixin does not support "mixin([''])", will cause ClassCaseException;
	 */
	@Test
	public void testSbInMixinFromParser() {
		String fileName = "MixinTestFile1.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName, "a.b.c.MyVjoType2", "mixin(['");
		assertNotNull(jstType);
		IJstParseController controller = CodeCompletionUtil
				.getJstParseController();
		controller.resolve(jstType);
	}

	/**
	 * mixin does not support "mixin([''])", will cause ClassCaseException;
	 */
	@Test
	public void testSbInMixinFromSyntaxTreeFactory() {
		String fileName = "MixinTestFile1.js.txt";
		IJstType jstType = getJstTypeSyntaxTreeFactory(fileName, "mixin(['");

		assertNotNull(jstType);
		IJstParseController controller = CodeCompletionUtil
				.getJstParseController();
		controller.resolve(jstType);
	}
	
	/**
	 * mixin does not support "mixin()", will cause ClassCaseException;
	 */
	@Test
	public void testEmptyInMixinFromParser() {
		String fileName = "MixinTestFile2.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName, "a.b.c.MyVjoType2", "mixin(");
		assertNotNull(jstType);
		IJstParseController controller = CodeCompletionUtil
				.getJstParseController();
		controller.resolve(jstType);
	}

	/**
	 * mixin does not support "mixin()", will cause ClassCaseException;
	 */
	@Test
	public void testEmptyInMixinFromSyntaxTreeFactory() {
		String fileName = "MixinTestFile2.js.txt";
		IJstType jstType = getJstTypeSyntaxTreeFactory(fileName, "mixin(");

		assertNotNull(jstType);
		IJstParseController controller = CodeCompletionUtil
				.getJstParseController();
		controller.resolve(jstType);
	}
	/**
	 * When syntax error: needs([]''), param.expressions == null, will result at NPE
	 */
	@Test
	public void testSbInNeedsFromParser() {
		String fileName = "NeedsTestFile1.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName, "a.b.c.MyVjoType2", "needs([]'");
		assertNotNull(jstType);
		IJstParseController controller = CodeCompletionUtil
		.getJstParseController();
		controller.resolve(jstType);
	}
	
	/**
	 * When syntax error: needs([]''), param.expressions == null, will result at NPE
	 */
	@Test
	public void testSbInNeedsFromSyntaxTreeFactory() {
		String fileName = "NeedsTestFile1.js.txt";
		IJstType jstType = getJstTypeSyntaxTreeFactory(fileName, "needs([]'");
		
		assertNotNull(jstType);
		IJstParseController controller = CodeCompletionUtil
		.getJstParseController();
		controller.resolve(jstType);
	}

}
