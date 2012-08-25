/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.engine.innertypes;





import org.junit.Ignore;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcInnerTypeTests {
	@Test
	public void testCtypeWithCTypeProposals() {
		VjoCcInnerTypeTestUtil testUtil = new VjoCcInnerTypeTestUtil();
		testUtil.sampleJs = "innertypes/OuterTypeTest.js";
		testUtil.testJs = "innertypes.OuterType";
		testUtil.xmlFile = "innertypes/OuterTypeData.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test //@Category({SLOW})
	public void testCtypeWithCType1Proposals() {
		VjoCcInnerTypeTestUtil testUtil = new VjoCcInnerTypeTestUtil();
		testUtil.sampleJs = "innertypes/OuterType1Test.js";
		testUtil.testJs = "innertypes.OuterType1";
		testUtil.xmlFile = "innertypes/OuterType1Data.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test //@Category({SLOW})
	public void testCtypeWithITypeProposals() {
		VjoCcInnerTypeTestUtil testUtil = new VjoCcInnerTypeTestUtil();
		testUtil.sampleJs = "innertypes/CTypeWithITypeTest.js";
		testUtil.testJs = "innertypes.CTypeWithIType";
		testUtil.xmlFile = "innertypes/CTypeWithITypeData.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test //@Category({SLOW})
	@Ignore("this test was not running due to NPE in framework")
	public void ignoredTestCtypeWithETypeProposals() {
		VjoCcInnerTypeTestUtil testUtil = new VjoCcInnerTypeTestUtil();
		testUtil.sampleJs = "innertypes/CTypeWithETypeTest.js";
		testUtil.testJs = "innertypes.CTypeWithEType";
		testUtil.xmlFile = "innertypes/CTypeWithETypeData.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test
	@Ignore
	public void ignoredTestCtypeWithMTypeProposals() {
		VjoCcInnerTypeTestUtil testUtil = new VjoCcInnerTypeTestUtil();
		testUtil.sampleJs = "innertypes/CTypeWithMTypeTest.js";
		testUtil.testJs = "innertypes.CTypeWithMType";
		testUtil.xmlFile = "innertypes/CTypeWithMTypeData.xml";
		
		testUtil.testCcProposals();
	}
}
