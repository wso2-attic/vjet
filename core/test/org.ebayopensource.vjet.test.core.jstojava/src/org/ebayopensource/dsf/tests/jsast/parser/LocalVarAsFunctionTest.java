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

import java.io.File;
import java.net.URL;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstSynthesizedMethod;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

/**
 * Tests if explicit static in .props is ok
 * 
 * 
 *
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class LocalVarAsFunctionTest implements ICommentConstants {
	
	private static final String fileName = FOLDER + "/LocalVarAsFunction.vjo";
	
	private static IJstType jstType = null;
	private static TranslateCtx ctx = null;
	
	@BeforeClass
	public static void setUpJst() throws Exception {
		VjoParser p = new VjoParser();
		ctx = new TranslateCtx();

		// get file
		File resource= new File(ResourceUtil.getResource(LocalVarAsFunctionTest.class,
				                fileName).getFile());
		URL url = ResourceUtil.getResource(BadCommentTest.class,fileName);
		String fileAsString = JsPreGenHelper.url2String(url);
		jstType = p.parse(fileName, resource.getAbsolutePath(), fileAsString, ctx).getType();
		assertNotNull(jstType);
	}
		
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies local variables declared as function")
	public void verifyLocalVariableAsFunctionFunction() {
		IJstMethod bar = jstType.getStaticMethod("bar");
		JstModifiers jm = bar.getModifiers();
		assertTrue(jm.isStatic());
		
		for(IStmt stmt: bar.getBlock().getStmts()){
			if(stmt instanceof JstVars){
				final IJstType varType = ((JstVars)stmt).getType();
				assertNotNull(varType);
				assertEquals(stmt.toStmtText(), "Function", varType.getName());
				for(AssignExpr assignment: ((JstVars)stmt).getAssignments()){
					assertNotNull(assignment);
					assertNotNull(assignment.getLHS());
					if(assignment.getLHS() instanceof JstIdentifier){
						final JstIdentifier identifier = (JstIdentifier)assignment.getLHS();
						assertNotNull(identifier);
						assertNotNull(identifier.getJstBinding());
						assertTrue(identifier.getJstBinding() instanceof JstMethod);
						
						if("f".equals(identifier.getName())){
							assertTrue(identifier.getJstBinding() instanceof JstSynthesizedMethod);
						}
					}
				}
			}
		}
		
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());
	}
	
	
	@AfterClass
	public static void tearDownJst() throws Exception {
		ctx = null;
		jstType = null;
	}
}
