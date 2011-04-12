/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.engine;
import com.ebay.junitnexgen.category.ModuleInfo;

import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P1;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import org.junit.Test;

import com.ebay.junitnexgen.category.Category;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;

@Category({P1,FAST,UNIT})
@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcJavaOneSampleTest extends VjoCcBaseTest {
	
	@Test
	public void testAnimalKingdom() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/javaone/AnimalKingdomTest.js";
		testUtil.testJs = "engine.javaone.AnimalKingdom";
		testUtil.xmlFile = "engine/javaone/JavaOne_DateFile.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test
	public void testAnimalLab() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/javaone/AnimalLabTest.js";
		testUtil.testJs = "engine.javaone.AnimalLab";
		testUtil.xmlFile = "engine/javaone/JavaOne_DateFile.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test
	public void testCat() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/javaone/CatTest.js";
		testUtil.testJs = "engine.javaone.Cat";
		testUtil.xmlFile = "engine/javaone/JavaOne_DateFile.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test
	public void testIMate() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/javaone/IMateTest.js";
		testUtil.testJs = "engine.javaone.IMate";
		testUtil.xmlFile = "engine/javaone/JavaOne_DateFile.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test
	public void testLiger() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/javaone/LigerTest.js";
		testUtil.testJs = "engine.javaone.Liger";
		testUtil.xmlFile = "engine/javaone/JavaOne_DateFile.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test
	public void testLion() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/javaone/LionTest.js";
		testUtil.testJs = "engine.javaone.Lion";
		testUtil.xmlFile = "engine/javaone/JavaOne_DateFile.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test
	public void testTiger() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/javaone/TigerTest.js";
		testUtil.testJs = "engine.javaone.Tiger";
		testUtil.xmlFile = "engine/javaone/JavaOne_DateFile.xml";
		
		testUtil.testCcProposals();
	}

}
