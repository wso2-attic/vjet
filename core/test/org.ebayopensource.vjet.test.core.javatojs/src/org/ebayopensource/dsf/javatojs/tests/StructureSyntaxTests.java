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

import org.ebayopensource.dsf.javatojs.control.DefaultTranslationInitializer;
import org.ebayopensource.dsf.javatojs.control.ITranslationInitializer;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Array;
import org.ebayopensource.dsf.javatojs.tests.data.structure.AutoboxingUnboxingTests;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Blocks;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Cast;
import org.ebayopensource.dsf.javatojs.tests.data.structure.EmbededType;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Employee;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Expressions;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Fields;
import org.ebayopensource.dsf.javatojs.tests.data.structure.FullyQualifiedTypes;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Identifiers;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Inheritance;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Initializer;
import org.ebayopensource.dsf.javatojs.tests.data.structure.InstanceAccess;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Interface;
import org.ebayopensource.dsf.javatojs.tests.data.structure.LocalType;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Methods;
import org.ebayopensource.dsf.javatojs.tests.data.structure.NestedInnerTypes;
import org.ebayopensource.dsf.javatojs.tests.data.structure.NestedTypes;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Overloadings;
import org.ebayopensource.dsf.javatojs.tests.data.structure.ResolveMethod;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Statements;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Synchronized;
import org.ebayopensource.dsf.javatojs.tests.data.structure.ThrowTry;
import org.ebayopensource.dsf.javatojs.tests.data.structure.Throws;
import org.ebayopensource.dsf.javatojs.tests.data.structure.autoboxing.BooleanAutoBoxing;
import org.ebayopensource.dsf.javatojs.tests.data.structure.autoboxing.CharAutoBoxing;
import org.ebayopensource.dsf.javatojs.tests.data.structure.autoboxing.GenericAutoBoxing;
import org.ebayopensource.dsf.javatojs.tests.data.structure.autoboxing.NumericAutoBoxing;
import org.ebayopensource.dsf.javatojs.tests.data.structure.imports.FullyQualifiedUsage;
import org.ebayopensource.dsf.javatojs.tests.data.structure.imports.OnDemandImports;
import org.ebayopensource.dsf.javatojs.tests.data.structure.imports.OnDemandInField;
import org.ebayopensource.dsf.javatojs.tests.data.structure.imports.OnDemandInMethod;
import org.ebayopensource.dsf.javatojs.tests.data.structure.imports.StaticImportedField;
import org.ebayopensource.dsf.javatojs.tests.data.structure.imports.StaticImportedMethod;
import org.ebayopensource.dsf.javatojs.tests.data.structure.nested.A;
import org.ebayopensource.dsf.javatojs.tests.data.structure.nested.B;
import org.ebayopensource.dsf.javatojs.tests.data.structure.nested.DeepEmbededInstance;
import org.ebayopensource.dsf.javatojs.tests.data.structure.nested.DeepEmbededStatic;
import org.ebayopensource.dsf.javatojs.tests.data.structure.nested.EmbededPath;
import org.ebayopensource.dsf.javatojs.tests.data.structure.nested.UseNested;
import org.ebayopensource.dsf.javatojs.tests.data.structure.ns.SuperInstance;
import org.ebayopensource.dsf.javatojs.tests.data.structure.ns.SuperStatic;
import org.ebayopensource.dsf.javatojs.tests.data.structure.ns.This;
import org.ebayopensource.dsf.javatojs.tests.data.structure.ns.UseSuperStaticMtd;
import org.ebayopensource.dsf.javatojs.tests.data.structure.ns.UseSuperStaticPty;
import org.ebayopensource.dsf.javatojs.tests.data.structure.overloading.DispatchingOrder;
import org.ebayopensource.dsf.javatojs.tests.data.structure.overloading.FindConstructor;
import org.ebayopensource.dsf.javatojs.tests.data.structure.overloading.FindMethod;
import org.ebayopensource.dsf.javatojs.tests.data.structure.overloading.OverloadingMultipleArgs;
import org.ebayopensource.dsf.javatojs.tests.data.structure.overloading.OverloadingWithIType;
import org.ebayopensource.dsf.javatojs.tests.data.structure.sibling.SiblingTypes;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.util.JstToJavaHelper;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.vjet.test.util.TestHelper;
import org.junit.After;
import org.junit.Test;




//@ModuleInfo(value = "DsfPrebuild", subModuleId = "JavaToJs")
public class StructureSyntaxTests {

	// private static ITranslationInitializer s_initializer;
	public static ITranslationInitializer getInitializer() {

		// if (s_initializer == null) {
		return new DefaultTranslationInitializer() {
			boolean initializated = false;
			public void initialize() {
				if(initializated){
					return;
				}
				super.initialize();
				TranslateCtx ctx = TranslateCtx.ctx().enableParallel(true)
						.enableTrace(true);

				initializated = true;
				// ctx.getConfig().getPackageMapping().add("org", "vjo");
			}
		};

	}
	
	@After
	public void after(){
		TranslateCtx.ctx().reset();
	}

	// return s_initializer;

	private static TranslateCtx getCtx() {
		return TranslateCtx.ctx();
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that cast operations are translated correctly")
	public void testCast() {
		TestHelper.testType(Cast.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that blocks are translated correctly")
	public void testBlocks() {
		TestHelper.testType(Blocks.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that a bean java class can be translated correctly")
	public void testEmployee() {
		TestHelper.testType(Employee.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that field declarations can be translated correctly")
	public void testFieldDecalarations() {
		TestHelper.testType(Fields.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that field declarations can be translated correctly")
	public void testMethods() {
		TestHelper.testType(Methods.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that nested types can be translated correctly")
	public void testNestedTypes() {
		TestHelper.testType(NestedTypes.class, getInitializer());
		getCtx().getTraceManager().close();
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that inner types can be translated correctly")
	public void testNestedInnerTypes() {
		TestHelper.testType(NestedInnerTypes.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that nested static types can be translated correctly")
	public void testNestedStatic() {
		TestHelper.testType(A.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that nested instances can be translated correctly")
	public void testNestedInstance() {
		TestHelper.testType(B.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that deeply embedded static types can be translated correctly")
	public void testDeepEmbededStatic() {
		TestHelper.testType(DeepEmbededStatic.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that deeply embedded static types can be translated correctly")
	public void testEmbededPath() {
		TestHelper.testType(EmbededPath.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that deeply embedded instances can be translated correctly")
	public void testDeepEmbededInstance() {
		TestHelper.testType(DeepEmbededInstance.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that deeply nested types can be translated correctly")
	public void testUseNested() {
		TestHelper.testType(UseNested.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that local types can be translated correctly")
	public void testLocalType() {
		TestHelper.testType(LocalType.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of 'this' is translated correctly")
	public void testThisNS() {
		TestHelper.testType(This.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of super/static is translated correctly")
	public void testSuperStaticNS() {
		TestHelper.testType(SuperStatic.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of super/static instance is translated correctly")
	public void testSuperInstanceNS() {
		TestHelper.testType(SuperInstance.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of super/static from other classes is translated correctly")
	public void testUseSuperStaicPty() {
		TestHelper.testType(UseSuperStaticPty.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of super/static methods is translated correctly")
	public void testUseSuperStaicMtd() {
		TestHelper.testType(UseSuperStaticMtd.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of static imported fields is translated correctly")
	public void testStaticImportedField() {
		TestHelper.testType(StaticImportedField.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of static imported method is translated correctly")
	public void testStaticImportedMethod() {
		TestHelper.testType(StaticImportedMethod.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of on demand imports is translated correctly")
	public void testOnDemandImports() {
		TestHelper.testType(OnDemandImports.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of on-demand in fields is translated correctly")
	public void testOnDemandInField() {
		TestHelper.testType(OnDemandInField.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of on-demand in methods is translated correctly")
	public void testOnDemandInMethod() {
		TestHelper.testType(OnDemandInMethod.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of fully qualified types is translated correctly")
	public void testFullyQualifiedUsage() {
		TestHelper.testType(FullyQualifiedUsage.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that expressions are translated correctly")
	public void testExpressions() {
		TestHelper.testType(Expressions.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that statements are translated correctly")
	public void testStatements() {
		TestHelper.testType(Statements.class, getInitializer());
		JstCache c = JstCache.getInstance();
		JstType type = JstCache.getInstance().getType("vjo.java.util.List");
		String fromName = JstToJavaHelper.getJavaTypeName(type);
		// package mapping is com
		assertEquals("vjo.java.util.List", fromName);
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that sibling types are translated correctly")
	public void testSiblingTypes() {
		TestHelper.testType(SiblingTypes.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of 'throws' is translated correctly")
	public void testThrows() {
		TestHelper.testType(Throws.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of  'try'-'throw' is translated correctly")
	public void testThrowTry() {
		TestHelper.testType(ThrowTry.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage arrays is translated correctly")
	public void testArray() {
		TestHelper.testType(Array.class, getInitializer());
		assertEquals(0, TestHelper.getTranslationController(getInitializer())
				.getErrors().size());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of embedded types is translated correctly")
	public void testEmbededType() {
		TestHelper.testType(EmbededType.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of identifiers is translated correctly")
	public void testIdentifiers() {
		TestHelper.testType(Identifiers.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test that usage of identifiers is translated correctly")
	public void testInstanceAccess() {
		TestHelper.testType(InstanceAccess.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of interfaces")
	public void testInterfaces() {
		TestHelper.testType(Interface.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation Enum")
	public void testEnum() {
		TestHelper
				.testType(
						org.ebayopensource.dsf.javatojs.tests.data.structure.Enum.class,
						getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of initializers")
	public void testInitializer() {
		TestHelper.testType(Initializer.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of overloading constructs")
	public void testOverloading() {
		TestHelper.testType(Overloadings.class, getInitializer());
		assertEquals(0, TestHelper.getTranslationController(getInitializer())
				.getErrors().size());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of dispatch ordering")
	public void testDispatchingOrder() {
		TestHelper.testType(DispatchingOrder.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of dispatch ordering")
	public void testOverloadingWithIType() {
		TestHelper.testType(OverloadingWithIType.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of dispatch ordering")
	public void testOverloadingMultipleArgs() {
		TestHelper.testType(OverloadingMultipleArgs.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of method resolving constructs")
	public void testResolveMethod() {
		TestHelper.testType(ResolveMethod.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of Boxing/Unboxing and the choice of methods")
	public void testFindMethod() {
		TestHelper.testType(FindMethod.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation various constructors with varying signatures")
	public void testFindConstructor() {
		TestHelper.testType(FindConstructor.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of synchronized structs")
	public void testSynchronized() {
		TestHelper.testType(Synchronized.class, getInitializer());
		assertEquals(0, TestHelper.getTranslationController(getInitializer())
				.getErrors().size());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of inheritance hierarchies")
	public void testInheritance() {
		TestHelper.testType(Inheritance.class, getInitializer());
		assertEquals(0, TestHelper.getTranslationController(getInitializer())
				.getErrors().size());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of Generics")
	public void testGenerics() {
		// TODO - The Date usage in Generics.java is not handled correctly now
		// for JSR
		// Needs to be fixed once decided on the usage of Date since translation
		// is not available
		// For this reason Generics.jsr is not accurage
		TestHelper
				.testType(
						org.ebayopensource.dsf.javatojs.tests.data.structure.Generics.class,
						getInitializer(), false, true);

		JstType type = JstCache
				.getInstance()
				.getType(
						"org.ebayopensource.dsf.javatojs.tests.data.structure.Generics");
		assertEquals("Ctx", type.getParamNames().get(0));
		assertEquals("Ctx2", type.getParamNames().get(1));
		assertEquals(true, type.getParamType("Ctx").getBounds().isEmpty());
		assertEquals("Date", type.getParamType("Ctx2").getBounds().get(0)
				.getSimpleName());
		getCtx().getTraceManager().close();
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test translation of fully qualified types")
	public void testFullyQualifiedTypes() {
		TestHelper.testType(FullyQualifiedTypes.class, getInitializer());
		assertEquals(0, TestHelper.getTranslationController(getInitializer())
				.getErrors().size());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test AutoBoxing and UnBoxing")
	public void testAutoboxingUnboxing() {
		TestHelper.testType(AutoboxingUnboxingTests.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test Numeric AutoBoxing")
	public void testNumericAutoBoxing() {
		TestHelper.testType(NumericAutoBoxing.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test Boolean AutoBoxing")
	public void testBooleanAutoBoxing() {
		TestHelper.testType(BooleanAutoBoxing.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test Char AutoBoxing")
	public void testCharAutoBoxing() {
		TestHelper.testType(CharAutoBoxing.class, getInitializer());
	}

	@Test
	//@Category({ P4, FUNCTIONAL })
	//@Description("Test Generic AutoBoxing")
	public void testGenericAutoBoxing() {
		TestHelper.testType(GenericAutoBoxing.class, getInitializer());
	}
}
