/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ast.recovery;



import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

@RunWith(value = Parameterized.class)
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class RecoveryTests {

	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList( new Object[][] {
//				{"astrecovery"}, 
//				{"astrecovery2"},// recovery is acceptable here
				{"astrecovery3"},
//				{"astrecovery4"},// no
//				{"astrecovery5"},
				{"astrecovery5a"},
//				{"astrecovery6"},
//				{"astrecovery7"},
//				{"astrecovery8"},
				{"astrecovery9"},
//				{"astrecovery_objliteralgarbage"},
//				{"astrecovery_objliteralgarbage1"},
//				{"astrecovery_objliteralgarbage2"},
//				{"astrecovery_objliteralgarbage3"},
				{"jstrecovery1"},
//				{"jstrecovery2"},
//				{"jstrecovery3"},
//				{"jstrecovery4"},
//				{"jstrecovery5"},
//				{"jstrecovery6"},
//				{"jstrecovery7"},
//				{"jstrecovery8"},
				{"jstrecovery9"},
//				{"jstrecovery10"},
//				{"jstrecovery11"},
//				{"jstrecovery12"},
				{"jstrecovery13"}
				//FIXME: is getting error and breaking RO build
//				{"bug8128"} // expect this will not return correct AST this.$missingthis.$missing will break ast known issue.
				
		});

	}


	private String m_inputName;
	
	
	/**
	 * foo
	 */
	public RecoveryTests(String inputFileName) {
		m_inputName = inputFileName;
	}
	
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("AST Recovery tests with and without errors")
	public void testRecovery() throws Exception {
		
		
		String errorCaseJS = m_inputName + ".js";
		String postiveCaseJs = m_inputName + "P.js";
		URL errorCaseFile = ResourceUtil.getResource(RecoveryTests.class, errorCaseJS);
		URL goodCaseFile = ResourceUtil.getResource(RecoveryTests.class, postiveCaseJs);
		
		String errorCase = VjoParser.getContent(errorCaseFile);
		String goodCase = VjoParser.getContent(goodCaseFile);
		
		System.out.println("********ERROR CASE: "+ m_inputName +"*******");
		System.out.println(errorCase);
		
		TranslateCtx errorCTX = new TranslateCtx();
		IJstType errorJST = SyntaxTreeFactory2.createJST(null, errorCase.toCharArray(),
				errorCaseJS, null, errorCTX);
		
		TranslateCtx positiveCtx = new TranslateCtx();
		assertTrue(positiveCtx.getErrorReporter().getErrors().size()==0);
		assertTrue(positiveCtx.getErrorReporter().getWarnings().size()==0);
		
		IJstType posJST = SyntaxTreeFactory2.createJST(null, goodCase.toCharArray(),
				postiveCaseJs, null, positiveCtx);
		
		assertSameAst(errorCTX.getAST(), positiveCtx.getAST());
		
		if(errorCaseJS.startsWith("jst")){
			assertSameJst(errorJST, posJST);
		}
	
	}


	private void assertSameJst(IJstType errorJST, IJstType posJST) {
		// TODO Auto-generated method stub
		
	}



	private void assertSameAst(CompilationUnitDeclaration error,
			CompilationUnitDeclaration good) {
		assertEquals(good.toString(), error.toString());
		
	}
}
