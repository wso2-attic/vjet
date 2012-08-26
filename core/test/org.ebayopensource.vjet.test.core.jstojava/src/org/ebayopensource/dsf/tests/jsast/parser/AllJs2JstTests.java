/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.tests.jsast.parser;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	FunctionBodyTest.class,
	CommentVarsTest.class,
	CommentMethodsNoArgsTest.class,
	CommentMethodsWithArgsTest.class,
	CommentConstructorsWithArgsTest.class,
	CommentMultipleArgsTest.class,
	MultiLineVarTest.class,
	ExplicitStaticTest.class,
	VoidParameterTest.class,
	JSTToolableAPITesting.class,
	VjoSelectionTests.class,
	CommentRecorderParserTest.class,
//	VjoParserAndGeneratorTest.class,
	JsFormattingTests.class,
	//added by huzhou for vjet lang enhancement of attributed type and function type
	LocalVarAsFunctionTest.class,
	LocalFunctionOverloadingTest.class,
	ChainOfFunctionInvocationTest.class,
	LocalVarByAttributedTypeTest.class,
	LocalVarByAttributedTypeSyntaxErrorTest.class,
	LocalVarByAttributedTypeSemanticErrorTest.class,
	GlobalVarByAttributedTypeTest.class,
	GlobalVarByAttributedTypeSyntaxErrorTest.class,
	GlobalVarByAttributedTypeSemanticErrorTest.class,
	ParamTypeAsFunctionTest.class,
	ReturnTypeAsFunctionTest.class,
	OptionalParamTest.class,
	MultiValuedParamTest.class,
	ObjLiteralWithAnnoTest.class,
	IncompleteFunctionTest.class
})

public class AllJs2JstTests {

	
	
	
	
	
	
	
	
	
	
}
