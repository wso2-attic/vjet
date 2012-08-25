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
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.declaration.SynthOlType;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.NV;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
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
public class ObjLiteralWithAnnoTest implements ICommentConstants {
	
	private static final String fileName = FOLDER + "/ObjLiteralWithAnno.vjo";
	
	private static IJstType jstType = null;
	private static TranslateCtx ctx = null;
	
	@BeforeClass
	public static void setUpJst() throws Exception {
		VjoParser p = new VjoParser();
		ctx = new TranslateCtx();

		// get file
		File resource= new File(ResourceUtil.getResource(ObjLiteralWithAnnoTest.class,
				                fileName).getFile());
		URL url = ResourceUtil.getResource(BadCommentTest.class,fileName);
		String fileAsString = JsPreGenHelper.url2String(url);
		jstType = p.parse(fileName, resource.getAbsolutePath(), fileAsString, ctx).getType();
		assertNotNull(jstType);
	}
		
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies local variables declared as function")
	public void testObjLiteralAnnotations() {
		final IJstMethod main = jstType.getStaticMethod("main");
		
		boolean visited = false;
		int i = 0;
		for(IStmt stmt: main.getBlock().getStmts()){
			if(stmt instanceof JstVars){
				final IJstType varType = ((JstVars)stmt).getType();
				assertNotNull(varType);
				for(AssignExpr assignment: ((JstVars)stmt).getAssignments()){
					assertNotNull(assignment);
					assertNotNull(assignment.getLHS());
					if(assignment.getLHS() instanceof JstIdentifier){
						final JstIdentifier identifier = (JstIdentifier)assignment.getLHS();
						assertNotNull(identifier);
					}
					
					assertNotNull(assignment.getExpr());
					if(assignment.getExpr() instanceof ObjLiteral){
						final ObjLiteral objLiteral = (ObjLiteral)assignment.getExpr();
						assertNotNull(objLiteral);
						assertEquals(SynthOlType.class, objLiteral.getResultType().getClass());
						
						verifyNVs(objLiteral, i);
						visited = true;
					}
					
					i++;
				}
			}
		}
		
		assertTrue(visited);
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());
	}

	private void verifyNVs(final ObjLiteral objLiteral, final int index) {
		final List<NV> nvs = objLiteral.getNVs();
		String[] names;
		String[] types;
		
		switch(index){
		case 0:
			names = new String[]{"x", "f"};
			types = new String[]{null, null};
			for (int i = 0, len = names.length; i < len; i++) {
				final NV nv = nvs.get(i);
				assertEquals(names[i], nv.getName());
				assertEquals(types[i], readType(nv));
			}
			break;
		case 1:
			names = new String[]{"x", "f"};
			types = new String[]{"int", null};
			for (int i = 0, len = names.length; i < len; i++) {
				final NV nv = nvs.get(i);
				assertEquals(names[i], nv.getName());
				assertEquals(types[i], readType(nv));
			}
			break;
		case 2:
			names = new String[]{"x", "f"};
			types = new String[]{"int", null};
			for (int i = 0, len = names.length; i < len; i++) {
				final NV nv = nvs.get(i);
				assertEquals(names[i], nv.getName());
				assertEquals(types[i], readType(nv));
			}
			break;
		case 3:
			names = new String[]{"x", "f"};
			types = new String[]{null, "Function"};
			for (int i = 0, len = names.length; i < len; i++) {
				final NV nv = nvs.get(i);
				assertEquals(names[i], nv.getName());
				assertEquals(types[i], readType(nv));
			}
			break;
		case 4:
			names = new String[]{"x", "f"};
			types = new String[]{null, "Function"};
			for (int i = 0, len = names.length; i < len; i++) {
				final NV nv = nvs.get(i);
				assertEquals(names[i], nv.getName());
				assertEquals(types[i], readType(nv));
			}
			break;
		case 5:
			names = new String[]{"x", "f", "z"};
			types = new String[]{null, "Function", null};
			for (int i = 0, len = names.length; i < len; i++) {
				final NV nv = nvs.get(i);
				assertEquals(names[i], nv.getName());
				assertEquals(types[i], readType(nv));
			}
			break;
		case 6:
			names = new String[]{"x", "f", "z"};
			types = new String[]{null, "Function", null};
			for (int i = 0, len = names.length; i < len; i++) {
				final NV nv = nvs.get(i);
				assertEquals(names[i], nv.getName());
				assertEquals(types[i], readType(nv));
			}
			break;
			default:
//				fail("no such case");
		}
	}

	private String readType(final NV nv) {
		if(nv.getIdentifier().getResultType() != null){
			return nv.getIdentifier().getResultType().getName();
		}
		return null;
	}
	
	
	@AfterClass
	public static void tearDownJst() throws Exception {
		ctx = null;
		jstType = null;
	}
}
