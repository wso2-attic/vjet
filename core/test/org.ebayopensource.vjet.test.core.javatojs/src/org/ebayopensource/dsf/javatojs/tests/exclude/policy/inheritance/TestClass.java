/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.policy.inheritance;



import java.util.List;

import org.junit.Test;

import org.ebayopensource.dsf.javatojs.control.BuildController;
import org.ebayopensource.dsf.javatojs.tests.exclude.TestTargetTranslator;
import org.ebayopensource.dsf.javatojs.tests.exclude.TestTargetTranslator.ClassList;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.translate.policy.ITranslationPolicy;



/*
 * Type Error is thrown by Class as when accessing static setFlag() method
 * 
 */
public class TestClass {
	
	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test exclusion policies for inheritance")
	public void test1() {
		final BuildController build = new BuildController();
		//build.setUseOnDemand(false);
		//build.reset();
		ITranslationPolicy policy = build.getTranslateController()
				.getCtx().getConfig().getPolicy();
		policy.addExcludeClass(A.class);		
		
		
		ClassList sourceList = TestTargetTranslator.getSourceList();
		sourceList
		.addToList(B.class)	
		.addToList(C.class)			
		//.addToList(C.class)			
		;
		
		TestTargetTranslator.translate(sourceList,build);
		List<TranslateError> errors = build.getAllErrors(); 
		TestTargetTranslator.printErrors(errors);	
		//build.getTranslateLog(LogLevel.ERROR);		
		TestTargetTranslator.deleteGenerated(sourceList);
	}
	
	
	
	public static void main(String[] args) {
		new TestClass().test1();
	}
	
	
}
