/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.policy;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import org.ebayopensource.dsf.javatojs.control.BuildController;
import org.ebayopensource.dsf.javatojs.tests.exclude.TestTargetTranslator;
import org.ebayopensource.dsf.javatojs.tests.exclude.TestTargetTranslator.ClassList;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.translate.policy.ITranslationPolicy;

import org.ebayopensource.dsf.logger.LogLevel;


/*
 * Type Error is thrown by Class as when accessing static setFlag() method
 * 
 */
public class TestClass {
	
	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test exclusion policies")
	@Ignore
	public void test1() {
		final BuildController build = new BuildController();
		build.reset();
		ITranslationPolicy policy = build.getTranslateController()
				.getCtx().getConfig().getPolicy();
		policy.addExcludeClass(A.class);		
		
		
		ClassList sourceList = TestTargetTranslator.getSourceList();
		sourceList
		.addToList(B.class)			
		;
		
		TestTargetTranslator.translate(sourceList,build);
		assertTrue(TestTargetTranslator.hasErrorFor(build.getAllErrors(),A.class.getName()));
		assertTrue(build.getAllErrors().size() == 3);
		TestTargetTranslator.deleteGenerated(sourceList);
	}
	
	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test exclusion policies")
	@Ignore
	public void test2() {
		final BuildController build = new BuildController();
		build.reset();
		ITranslationPolicy policy = build.getTranslateController()
				.getCtx().getConfig().getPolicy();
		policy.addExcludeClass(A.class);		
		
		
		ClassList sourceList = TestTargetTranslator.getSourceList();
		sourceList
		.addToList(B.class)			
		;
		
		TestTargetTranslator.translate(sourceList,build);		
		assertTrue(build.getAllErrors().size() == 3);
		TestTargetTranslator.deleteGenerated(sourceList);
	}
	
	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test exclusion policies")
	public void test3() {
		final BuildController build = new BuildController();
		build.reset();
		ITranslationPolicy policy = build.getTranslateController()
				.getCtx().getConfig().getPolicy();
		policy.addExcludeClass(A.class);		
		
		
		ClassList sourceList = TestTargetTranslator.getSourceList();
		sourceList
		.addToList(C.class)			
		;
		
		TestTargetTranslator.translate(sourceList,build);
		
		List<TranslateError> errors = build.getTranslateLog(LogLevel.ERROR);
		
		TestTargetTranslator.printErrors(errors);
		
		assertTrue(TestTargetTranslator.hasErrorFor(errors,A.class.getName()));	
	  
		assertTrue(errors.size()== 1);
		
		TestTargetTranslator.deleteGenerated(sourceList);
	}
	
	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test exclusion policies")
	@Ignore
	public void test4() {
		final BuildController build = new BuildController();
		build.reset();
		ITranslationPolicy policy = build.getTranslateController()
				.getCtx().getConfig().getPolicy();
		policy.addExcludeClass(A.class);		
		
		
		ClassList sourceList = TestTargetTranslator.getSourceList();
		sourceList
		.addToList(D.class)			
		;
		
		TestTargetTranslator.translate(sourceList,build);
		
		List<TranslateError> errors = build.getTranslateLog(LogLevel.ERROR);
		
		TestTargetTranslator.printErrors(errors);
		
		assertTrue(TestTargetTranslator.hasErrorFor(errors,A.class.getName(),5) );			
		assertTrue(TestTargetTranslator.hasErrorFor(errors,A.class.getName(),7) );	
		assertTrue(TestTargetTranslator.hasErrorFor(errors,A.class.getName(),8) );	
		assertTrue(TestTargetTranslator.hasErrorFor(errors,A.class.getName(),11) );	
		assertTrue(TestTargetTranslator.hasErrorFor(errors,A.class.getName(),12) );	
		assertTrue(TestTargetTranslator.hasErrorFor(errors,A.class.getName(),13) );	
		assertTrue(TestTargetTranslator.hasErrorFor(errors,A.class.getName(),14) );		
	  
		assertTrue(errors.size()== 8);
		
		TestTargetTranslator.deleteGenerated(sourceList);
	}
	
	public static void main(String[] args) {
		new TestClass().test1();
	}
	
	
}
