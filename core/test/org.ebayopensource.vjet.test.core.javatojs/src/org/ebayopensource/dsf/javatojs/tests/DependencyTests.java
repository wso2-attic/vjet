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
import org.ebayopensource.dsf.javatojs.tests.data.dependency.Dependency;
import org.ebayopensource.dsf.javatojs.trace.ITranslateTracer;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.trace.TranslationTraceId;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.util.JavaToJsHelper;
import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.vjet.test.util.TestHelper;

//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class DependencyTests{
	
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
	
	private static ITranslateTracer s_tracer;
	
	private ITranslateTracer getTracer(){
		if (s_tracer == null){
			s_tracer = getCtx().getTraceManager().getTracer();
		}
		return s_tracer;
		
	}

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Compare expected and actual vjo outcomes for a class with an unknown dependency")
	public void testUnknownDependencyType(){
		getTracer().startGroup(ID, new TraceAttr("name", "UnknownDependency"));
		Class srcType = Dependency.class;
		TranslationController controller = TestHelper.getTranslationController(getInitializer());
		JstType jstType = controller.targetedTranslation(srcType);
		GeneratorCtx generatorCtx = new GeneratorCtx(CodeStyle.PRETTY);
		generatorCtx.setNewline(TestHelper.NEWLINE);
		String actual = JavaToJsHelper.toVjo(jstType, generatorCtx);
		TestHelper helper = new TestHelper(srcType, getInitializer());
		assertEquals(helper.getExpectedVjo(), actual);
		getTracer().endGroup(ID);
		getCtx().getTraceManager().close();
		printErrors(controller.getErrors());
	}
	
	private void printErrors(List<TranslateError> errors){
		for (TranslateError e: errors){
			System.err.println(e.toString());
		}
	}
}
