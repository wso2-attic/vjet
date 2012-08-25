/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.vjolang.feature.tests;





import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.Test;



import org.ebayopensource.dsf.common.FileUtils;

//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoSyntaxParsingTests {

	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Parses and validates ctype")
	public void testCtype() throws Exception {
		
		String name = "ctype.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJavaPrimitiveLib());
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		p.addLib(LibManager.getInstance().getBrowserTypesLib());
		
		
		IJstParseController c = new JstParseController(p);
		
		IJstType type = c.parse(name, name, file).getType();
		ParseUtils.validateJstSource(type);
		
		
	}
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Parses and validates array type declared")
	public void testArraytype() throws Exception {
		
		String name = "x.js";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJavaPrimitiveLib());
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		p.addLib(LibManager.getInstance().getBrowserTypesLib());
		
		
		IJstParseController c = new JstParseController(p);
		
		IJstType type = c.parse(name, name, file).getType();
//		ParseUtils.printTree(type);
		ParseUtils.validateJstSource(type);
		
		
	}
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Parses and validates property declared public and final")
	public void testPublicFinal() throws Exception {
		
		String name = "vjopublicfinal.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJavaPrimitiveLib());
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		p.addLib(LibManager.getInstance().getBrowserTypesLib());
		
		IJstParseController c = new JstParseController(p);
		
		IJstType type = c.parse(name, name, file).getType();
		ParseUtils.printTree(type);
		
		
	}
	
	@Test
	//@Category({P3, UNIT, FAST})
	//@Description("Correct parsing spaces in comments")
	public void testSpaceComment() throws Exception {
		
		String name = "vjospacecomment.txt";
		String file = FileUtils.getResourceAsString(ParsingTests.class, name);
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJavaPrimitiveLib());
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		p.addLib(LibManager.getInstance().getBrowserTypesLib());
		
		IJstParseController c = new JstParseController(p);
		
		IJstType type = c.parse(name, name, file).getType();
		ParseUtils.printTree(type);
		
		
	}
	
}
