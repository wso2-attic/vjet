/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstdoc;



import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

@RunWith(value = Parameterized.class)
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class JsDocTests {

	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList( new Object[][] {
				{"nojsdoc"}, 
				{"jsdocmethod"}, 
				{"jsdocmethod2"}, 
				{"jsdocmixed"}, 
				{"nojsdocmixed2"}, 
		});

	}


	private String m_inputName;
	
	
	/**
	 * foo
	 */
	public JsDocTests(String inputFileName) {
		m_inputName = inputFileName;
	}
	
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("AST Recovery tests with and without errors")
	public void testDocs() throws Exception {
		
		
		String postiveCaseJs = m_inputName + ".js";
		URL goodCaseFile = ResourceUtil.getResource(JsDocTests.class, postiveCaseJs);
		
		String goodCase = VjoParser.getContent(goodCaseFile);
		
	
		TranslateCtx positiveCtx = new TranslateCtx();
		assertTrue(positiveCtx.getErrorReporter().getErrors().size()==0);
		assertTrue(positiveCtx.getErrorReporter().getWarnings().size()==0);
	
		
		IJstType posJST = new VjoParser().parse("TEST", goodCaseFile.getFile(), goodCase, false).getType();
		List<? extends IJstMethod> methods = posJST.getMethods();
		assertTrue(methods.size()>0);
		
		
		
		if(m_inputName.startsWith("no")){
			return;
		}
		
		assertNotNull(posJST.getDoc());
		assertEquals("type comment", posJST.getDoc().getComment().trim());
		
		
		for(IJstMethod m: methods){
			String comment = m.getDoc().getComment().trim();
			if(m.getModifiers().isStatic()){
				assertEquals("static method",comment);
			}else{
				assertEquals("instance method",comment);
			}
		}
	
		List<? extends IJstProperty> props = posJST.getProperties();
		assertTrue(props.size()>0);
		for(IJstProperty p: props){
			if(p instanceof ISynthesized){
				continue;
			}
			String comment = p.getDoc().getComment().trim();
			if(p.getModifiers().isStatic()){
				assertTrue(comment.contains("static property"));
			}else{
				assertTrue(comment.contains("instance property"));
			}
			assertNotNull(p.getDoc());
		}

	
	
	}
	
		
		






}
