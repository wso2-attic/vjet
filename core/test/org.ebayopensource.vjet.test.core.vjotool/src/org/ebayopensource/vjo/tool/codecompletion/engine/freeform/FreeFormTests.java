/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.engine.freeform;





import org.ebayopensource.vjo.tool.codecompletion.handler.VjoCcBaseHandlerTestUtil;
import org.ebayopensource.vjo.tool.codecompletion.handler.VjoCcHandlerTestUtil;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class FreeFormTests {


		@Test
		public void testFreeForm() {
			
			VjoCcBaseHandlerTestUtil testUtil = new VjoCcHandlerTestUtil();
			testUtil.testTag = "advisor";
			testUtil.sampleJs = "freeform/FreeForm.js";
			testUtil.testJs = "freeform.FreeForm";
			testUtil.xmlFile = "freeform/FreeForm.xml";
			testUtil.testHandlerCases();

		
		}
		
}
