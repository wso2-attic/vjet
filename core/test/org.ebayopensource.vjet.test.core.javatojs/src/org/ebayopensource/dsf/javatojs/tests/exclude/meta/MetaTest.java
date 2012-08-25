/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.meta;



import java.util.List;

import org.junit.Test;

import org.ebayopensource.dsf.javatojs.control.BuildController;
import org.ebayopensource.dsf.javatojs.tests.exclude.TestTargetTranslator;
import org.ebayopensource.dsf.javatojs.tests.exclude.TestTargetTranslator.ClassList;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;

import org.ebayopensource.dsf.logger.LogLevel;

/*
 * Type Error is thrown by Class as when accessing static setFlag() method
 * 
 */
public class MetaTest {
	
	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test usage of custom meta data during translation")
	public void testMeta() {

		final BuildController build = new BuildController(new TestConfigInitializer());
		build.reset();
		ClassList sourceList = TestTargetTranslator.getSourceList();
		sourceList
		.addToList(UseMeta.class)
		.addToList(MyCustomData.class)
		;
		
		TestTargetTranslator.translate(sourceList,build);
		List<TranslateError> errors = build.getTranslateLog(LogLevel.ERROR);
		TestTargetTranslator.printErrors(errors);	
		
		//assertTrue(TestTargetTranslator.hasErrorFor(errors,UseMeta.class.getName()));	
		
		TestTargetTranslator.deleteGenerated(sourceList);
	}
	
	public static void main(String[] args) {
		new MetaTest().testMeta();
	}
}
