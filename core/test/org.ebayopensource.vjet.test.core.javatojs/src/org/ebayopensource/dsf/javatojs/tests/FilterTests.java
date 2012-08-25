/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests;



import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import org.ebayopensource.dsf.common.trace.TraceAttr;
import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.javatojs.control.DefaultTranslationInitializer;
import org.ebayopensource.dsf.javatojs.control.ITranslationInitializer;
import org.ebayopensource.dsf.javatojs.control.TranslationController;
import org.ebayopensource.dsf.javatojs.tests.data.filter.ExcludeAll;
import org.ebayopensource.dsf.javatojs.tests.data.filter.ExcludeRest;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.trace.TranslationTraceId;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.vjet.test.util.TestHelper;


//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class FilterTests {

	public static final TraceId ID = TranslationTraceId.TEST;

	private static ITranslationInitializer s_initializer;
	public static ITranslationInitializer getInitializer() {

		if (s_initializer == null) {
			s_initializer = new DefaultTranslationInitializer(){
				public void initialize(){
					super.initialize();
					TranslateCtx.ctx()
						.enableParallel(true)
						.enableTrace(true);
				}
			};
			
		}
		return s_initializer;
	}
	
	private static TranslateCtx getCtx(){
		return TranslateCtx.ctx();
	}

	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test the exclude-all filter with the Translation Controller")
	public void testExcludeAll() {
		TranslationController controller = TestHelper.getTranslationController(getInitializer());
		testType(ExcludeAll.class, controller, true, false);
		assertEquals(0, controller.getErrors().size());
	}

	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test the exclude-rest filter with the Translation Controller")
	public void testExcludeRest() {

		TranslationController controller = TestHelper.getTranslationController(getInitializer());
		testType(ExcludeRest.class, controller, false, true);
//		assertEquals(2, controller.getErrors().size());
	}



	private void testType(Class srcType, TranslationController controller,
			boolean doWrite, boolean doAssert) {

		controller.reset();
		getCtx().getTraceManager().getTracer().startGroup(ID,
				new TraceAttr("name", srcType.getSimpleName()));

		TestHelper helper = new TestHelper(srcType, getInitializer());

		String actual = helper.toVjo(controller.targetedTranslation(srcType),
				CodeStyle.PRETTY);

		getCtx().getTraceManager().getTracer().endGroup(ID);
		printErrors(controller.getErrors());

		if (doWrite) {
			helper.writeVjo(actual);
		}

		if (doAssert) {
			assertEquals(helper.getExpectedVjo(), actual);
		}
	}

	private void printErrors(List<TranslateError> errors) {
		for (TranslateError e : errors) {
			System.err.println(e.toString());
		}
	}
}
