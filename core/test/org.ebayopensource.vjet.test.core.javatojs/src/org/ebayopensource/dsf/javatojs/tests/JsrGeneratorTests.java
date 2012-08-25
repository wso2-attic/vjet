/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.ebayopensource.dsf.javatojs.control.DefaultTranslationInitializer;
import org.ebayopensource.dsf.javatojs.control.ITranslationInitializer;
import org.ebayopensource.dsf.javatojs.tests.data.structure.InactiveNeeds;
import org.ebayopensource.dsf.javatojs.tests.data.structure.InheritanceTest;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Methods;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Overloadings;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Properties;
import org.ebayopensource.dsf.javatojs.tests.data.structure.SameMemberNameData;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.vjet.test.util.TestHelper;




//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class JsrGeneratorTests {

	private static ITranslationInitializer s_initializerA;

	public static ITranslationInitializer getInitializerA() {

		if (s_initializerA == null) {
			s_initializerA = new DefaultTranslationInitializer() {
				public void initialize() {
					super.initialize();
					TranslateCtx.ctx().enableParallel(true).enableTrace(true);
				}
			};

		}
		return s_initializerA;
	}

	private static ITranslationInitializer s_initializerB;

	public static ITranslationInitializer getInitializerB() {

		if (s_initializerB == null) {
			s_initializerB = new DefaultTranslationInitializer() {
				public void initialize() {
					super.initialize();
					TranslateCtx ctx = TranslateCtx.ctx().enableParallel(true)
							.enableTrace(true);
					ctx.getConfig().getPackageMapping().add("com", "vjo");
				}
			};

		}
		return s_initializerB;
	}

	private static TranslateCtx getCtx() {
		return TranslateCtx.ctx();
	}

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test translation of methods")
	public void testMethods() {

		Class srcType = Methods.class;
		TestHelper helper = new TestHelper(srcType, getInitializerB());
		String actual = helper.toJsr(helper.translate(), CodeStyle.PRETTY);
		String expectedJsr = helper.getExpectedJsr();
		if (helper.hasErrorsReported()) {
			assertFalse("Errors are reported .. : \n"
					+ helper.getErrorsReported(), helper.hasErrorsReported());
		} else {
			assertEquals("Expected Results : \n" + expectedJsr
					+ "\nActual Results : \n" + actual, expectedJsr, actual);
		}
	}

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test translation of instance and static members")
	public void testProperties() {
		Class srcType = Properties.class;
		TestHelper helper = new TestHelper(srcType, getInitializerB());
		String actual = helper.toJsr(helper.translate(), CodeStyle.PRETTY);
		String expectedJsr = helper.getExpectedJsr();
		if (helper.hasErrorsReported()) {
			assertFalse("Errors are reported .. : \n"
					+ helper.getErrorsReported(), helper.hasErrorsReported());
		} else {
			assertEquals("Expected Results : \n" + expectedJsr
					+ "\nActual Results : \n" + actual, expectedJsr, actual);
		}
	}

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test translation of overloaded methods")
	public void testOverloading() {
		Class srcType = Overloadings.class;
		TestHelper helper = new TestHelper(srcType, getInitializerB());
		String actual = helper.toJsr(helper.translate(), CodeStyle.PRETTY);
		String expectedJsr = helper.getExpectedJsr();
		// if (helper.hasErrorsReported()) {
		assertTrue("Missing error .. : \n" + helper.getErrorsReported(), helper
				.hasErrorsReported());
		// } else {
		assertEquals("Expected Results : \n" + expectedJsr
				+ "\nActual Results : \n" + actual, expectedJsr, actual);
		// }

	}

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test that translation where a member name and method name are the same results in errors")
	public void testSameMemberNames() {
		Class srcType = SameMemberNameData.class;
		TestHelper helper = new TestHelper(srcType, getInitializerA());
		String actual = helper.toJsr(helper.translate(), CodeStyle.PRETTY);
		assertTrue(
				"If input java class has one method and one memeber variable with"
						+ " same name and same type of argument (in case of method) and same variable type"
						+ " (in case of member variable) ; generated JSR should not two mehtods with same"
						+ " name and same arguments , such code can not be compiled.",
				helper.hasErrorsReported());
		assertTrue(helper.getErrorsReported().indexOf(
				"Method name is same as other field") !=-1);
	}

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test translation where two classes have the same simple name but different names when fully resolved")
//	@Ignore("complex test case should be simplified into multiple asserts")
	public void testSameSimpleNameForMemberType() {
		Class srcType = vjo.ebay.dsf.javatojs.tests.data.structure.SameSimpleNameForMemberTypeTest.class;
		JstCache.getInstance().clear();
		TestHelper helper = new TestHelper(srcType, getInitializerA());
		String actual = helper.toJsr(helper.translate(), CodeStyle.PRETTY);
		assertFalse(
				"If some classes used in input java class, are having same simple "
						+ "name , then first one can be declared with simple name only , but the second "
						+ "one must be declared with fully qualified name. \nGenerated Code : \n"
						+ actual,
				(actual
						.indexOf("new JsObjData(\"vjo.ebay.dsf.javatojs.tests.data.structure.SameSimpleNameForMemberTypeTest\", SameSimpleNameForMemberTypeTestJsr.class, \"SameSimpleNameForMemberTypeTest\", true);") == -1)
					);
		assertFalse(
				"If some classes used in input java class, are having same simple "
				+ "name , then first one can be declared with simple name only , but the second "
				+ "one must be declared with fully qualified name. \nGenerated Code : \n"
				+ actual,
				
					(actual
							.indexOf("public JsProp<EmployeeJsr> employee2(){") == -1)
							|| (actual
									.indexOf("return getProp(EmployeeJsr.class, \"employee2\");") == -1)
									|| (actual
											.indexOf("public IJsPropSetter employee2(EmployeeJsr v) {") == -1)
											|| (actual
													.indexOf("public IJsPropSetter employee2(IValueBinding<? extends EmployeeJsr> v) {") == -1));

		assertTrue("Errors are reported .. : \n" + helper.getErrorsReported(),
				helper.hasErrorsReported());
	}

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test translation of unused imports")
	public void testUnusedImports() {
		Class srcType = InheritanceTest.class;
		TestHelper helper = new TestHelper(srcType, getInitializerA());
		JstCache.getInstance().clear();
		String actual = helper.toJsr(helper.translate(), CodeStyle.PRETTY);
		assertTrue(
				"Class used in JSR other that DSF stuff and primitives should be a JSR.\n\nActual Results : \n"
						+ actual,
				actual
						.indexOf("import vjo.ebay.dsf.javatojs.tests.data.Person;") == -1);
		assertTrue("Errors are reported .. : \n" + helper.getErrorsReported(),
				helper.hasErrorsReported());

	}

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test translation of inheritance hierarchies")
	public void testInheritance() {
		Class srcType = InheritanceTest.class;
		TestHelper helper = new TestHelper(srcType, getInitializerA());
		JstCache.getInstance().clear();
		String actual = helper.toJsr(helper.translate(), CodeStyle.PRETTY);
		assertTrue(
				"Parent class should be converted into JSR and then class to be translated should extend generated JSR of parent.\n Generated Code Should consist : \n public class InheritanceTestJsr extends PersonJsr implements InterfaceJsr {\n\n Actual Generated Code : \n "
						+ actual,
				actual
						.indexOf("public class InheritanceTestJsr extends PersonJsr implements InterfaceJsr {") != -1);
		assertTrue("Errors are reported .. : \n" + helper.getErrorsReported(),
				helper.hasErrorsReported());

	}

	// Disable this test until it is fixed. The problem is that it expects a checked in class
	// with a name ending in Jsr to be present, however other general test setup logic
	// associated with this suite, deletes all files ending in Jsr as part of cleanup.
//	@Test
//	//@Category( { P1, FUNCTIONAL })
//	//@Description("Test that any class having name with suffix 'Jsr' should not be considered as Jsr")
//	public void testDummyJsr() {
//		Class srcType = DummyJsrTest.class;
//		TestHelper helper = new TestHelper(srcType, getInitializerB());
//		String actual = helper.toJsr(helper.translate(), CodeStyle.PRETTY);
//		String expectedJsr = helper.getExpectedJsr();
//		assertEquals(
//				"Any Class having name with suffix 'Jsr' should not be considered as Jsr.\nExpected Results : \n"
//						+ expectedJsr + "\nActual Results : \n" + actual,
//				helper.getExpectedJsr(), actual);
//		assertFalse("Errors are reported .. : \n" + helper.getErrorsReported(),
//				helper.hasErrorsReported());
//	}

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test translation of inactive imports, no dependency should get added")
	public void testInactiveImports() {
		Class<?> srcType = InactiveNeeds.class;
		TestHelper helper = new TestHelper(srcType, getInitializerA());
		JstCache.getInstance().clear();
		String actual = helper.toJsr(helper.translate(), CodeStyle.PRETTY);
		assertTrue(
				"Dependency should not be added for inactive imports: \n"
						+ actual,
				actual
						.indexOf("addDependentComponent(IntegerJsr.ResourceSpec.getInstance())") == -1
						&& actual
								.indexOf("addDependentComponent(ListJsr.ResourceSpec.getInstance())") == -1);
		assertFalse("Errors are reported .. : \n" + helper.getErrorsReported(),
				helper.hasErrorsReported());
	}
}
