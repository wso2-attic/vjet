/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.vjolang.feature.tests;




import java.net.URL;

import org.ebayopensource.dsf.jstojava.controller.BuildController;
import org.junit.Test;




//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class BuildControllerTests {

	
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
	//@Description("Generates jsr and java files")
	public void testGeneration() throws Exception {
		
		BuildController c = new BuildController();
		c.loadTypes(TEST_A, getGroupPath()+"/" +TEST_A, "src");
		c.generateAll();
		

	}
	
	
	
	private String getGroupPath() {
															 //\org.ebayopensource.dsf\tests\data\workspaceTS\TestA\src\test\A.js
		URL url = this.getClass().getClassLoader().getResource("org/ebayopensource/dsf/tests/data/workspaceTS/TestA/src/test/A.js");
		
		String path = url.getFile();
		int end = path.indexOf("workspaceTS");
		int len = new String("workspaceTS").length();
		String groupFullPath = path.substring(0, end+len);
		return groupFullPath;
	}
	
}
