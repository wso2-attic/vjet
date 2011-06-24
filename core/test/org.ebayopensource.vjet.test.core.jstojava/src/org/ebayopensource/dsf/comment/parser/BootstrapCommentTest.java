/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.comment.parser;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import junit.framework.TestCase;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.bootstrap.BootstrapParser;
import org.ebayopensource.vjo.VjBootStrapDef;
import org.ebayopensource.vjo.VjBootstrapJsr;
import org.junit.Test;

public class BootstrapCommentTest {

//	 @Test
	 public void testSimpleCommentParse() throws Exception {

	
			URL f = VjBootstrapJsr.getJsAsUrl();
			URL bapi = VjBootstrapJsr.getVjoApIAsUrl();
		
			Map<String, ? extends IJstType> types = BootstrapParser.createJstType("vjo", f,
					VjBootStrapDef.SCHEMA, bapi, 
					VjBootstrapJsr.getVjoConsoleAsUrl(), 
					VjBootstrapJsr.getVjoClassAsUrl(), 
					VjBootstrapJsr.getVjoEnumAsUrl(), 
					VjBootstrapJsr.getVjoOptionsOLUrl(), 
					VjBootstrapJsr.getVjoObjectAsUrl());
		 
		 
		 IJstType[] typesAry  = new IJstType[types.size()];
		 
		 types.values().toArray(typesAry);
		 
		 printTypes(typesAry);
		 
			IJstType t = types.get("vjo.ctype");
			TestCase.assertNotNull(t);
			TestCase.assertNotNull(t.getMethod("a"));
			TestCase.assertNotNull(t.getMethod("b"));
			TestCase.assertNotNull(t.getMethod("d"));
			
			IJstType t1 = types.get("vjo.ctype1");
			TestCase.assertNotNull(t1);
			TestCase.assertNull(t1.getMethod("a"));
			TestCase.assertNotNull(t1.getMethod("b"));
			TestCase.assertNotNull(t1.getMethod("d"));
			
			IJstType t2 = types.get("vjo.ctype2");
			TestCase.assertNotNull(t2);
			TestCase.assertNotNull(t2.getMethod("a"));
			TestCase.assertNull(t2.getMethod("b"));
			TestCase.assertNotNull(t2.getMethod("d"));
			
			
			IJstType t3 = types.get("vjo.ctype3");
			TestCase.assertNotNull(t3);
			TestCase.assertNull(t3.getMethod("a"));
			TestCase.assertNull(t3.getMethod("b"));
			TestCase.assertNull(t3.getMethod("d"));
		 
		 
		 
	
	 }

	 @Test
	 public void testVjoEtype() throws Exception {
		 
			URL f = VjBootstrapJsr.getJsAsUrl();
			URL bapi = VjBootstrapJsr.getVjoApIAsUrl();
		
			Map<String, ? extends IJstType> types = BootstrapParser.createJstType("vjo", f,
					VjBootStrapDef.SCHEMA, bapi, 
					VjBootstrapJsr.getVjoConsoleAsUrl(), 
					VjBootstrapJsr.getVjoClassAsUrl(), 
					VjBootstrapJsr.getVjoEnumAsUrl(), 
					VjBootstrapJsr.getVjoOptionsOLUrl(), 
					VjBootstrapJsr.getVjoObjectAsUrl());
		 IJstType[] typesAry  = new IJstType[types.size()];
		 
		 types.values().toArray(typesAry);
		 printTypes(typesAry);
		 
		 IJstType t = types.get("vjo.etype");
		 TestCase.assertNotNull(t);
		 TestCase.assertNotNull(t.getMethod("needs"));
		 TestCase.assertNotNull(t.getMethod("satisfies"));
		 IJstMethod values = t.getMethod("values");
		 TestCase.assertNotNull(values);
		 TestCase.assertEquals(values.getRtnType().getName(), "vjo.etype1");
		 TestCase.assertNotNull(t.getMethod("props"));
		 TestCase.assertNotNull(t.getMethod("protos"));
		 TestCase.assertNotNull(t.getMethod("inits"));
		 TestCase.assertNotNull(t.getMethod("options"));
		 TestCase.assertNotNull(t.getMethod("endType"));
		 
		 
		 IJstType t2 = types.get("vjo.etype1");
		 TestCase.assertNotNull(t2);
		 TestCase.assertNotNull(t2.getMethod("props"));
		 TestCase.assertNotNull(t2.getMethod("protos"));
		 TestCase.assertNotNull(t2.getMethod("inits"));
		 TestCase.assertNotNull(t2.getMethod("options"));
		 TestCase.assertNotNull(t2.getMethod("endType"));

		 

		 
		 
		 
		 
	 }
	@Test
	public void testVjoCtype() throws Exception {

		Map<String, ? extends IJstType> types = BootstrapParser.createJstType("vjo", 
				VjBootstrapJsr.getJsAsUrl(),
				VjBootStrapDef.CTYPE, VjBootstrapJsr.getVjoApIAsUrl());

		 IJstType[] typesAry  = new IJstType[types.size()];
		 
		 types.values().toArray(typesAry);
		printTypes(typesAry);
	
			IJstType t = types.get("vjo.ctype");
			TestCase.assertNotNull(t);
			TestCase.assertNotNull(t.getMethod("inherits"));
			TestCase.assertNotNull(t.getMethod("satisfies"));
			TestCase.assertNotNull(t.getMethod("mixin"));

			
			IJstType t2 = types.get("vjo.ctype2");
			TestCase.assertNotNull(t2);
			
			TestCase.assertNull(t2.getMethod("inherits"));
			TestCase.assertNotNull(t2.getMethod("options"));
			TestCase.assertNotNull(t2.getMethod("props"));
			TestCase.assertNotNull(t2.getMethod("inits"));
			TestCase.assertNotNull(t2.getMethod("endType"));
			
			
			IJstType t3 = types.get("vjo.ctype5");
			TestCase.assertNotNull(t3);
			TestCase.assertNull(t3.getMethod("inherits"));
			TestCase.assertNull(t3.getMethod("satisfies"));
			TestCase.assertNull(t3.getMethod("mixin"));
			
			
//			IJstType t4 = types.get("vjo.otype");
//			TestCase.assertNotNull(t4);
//			TestCase.assertNotNull(t4.getMethod("defs"));
//			TestCase.assertNotNull(t4.getMethod("endType"));
//			TestCase.assertNull(t4.getMethod("mixin"));
		

	}

	private void printTypes(IJstType[] types) throws IOException {

		// prep for generation
		TestCase.assertNotNull(types);
		TestCase.assertTrue(types.length > 0);
		// TestCase.assertEquals(16, types.size());

		for (IJstType t : types) {
			VjoGenerator gen = new VjoGenerator(new GeneratorCtx(
					CodeStyle.PRETTY));
			gen.writeType(t);
			System.out.println(gen.getGeneratedText());

			// JstParseController p = new JstParseController(new VjoParser());
			// IScriptUnit unit = p.parse("TEST", "Test.js",
			// gen.getGeneratedText());
			// gen = new VjoGenerator(new GeneratorCtx(CodeStyle.PRETTY));
			// gen.writeType(unit.getType());
			// System.out.println(gen.getGeneratedText());

		}
	}

}
