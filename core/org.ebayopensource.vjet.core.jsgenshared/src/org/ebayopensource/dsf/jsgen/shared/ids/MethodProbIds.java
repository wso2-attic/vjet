/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.ids;

import org.ebayopensource.dsf.jst.JstProblemId;

public class MethodProbIds {


	// methods
	public static final JstProblemId UndefinedMethod = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "UndefinedMethod");
	public static final JstProblemId NotVisibleMethod = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "NotVisibleMethod");
	public static final JstProblemId NotVisibleConstructor = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "NotVisibleConstructor");
	public static final JstProblemId AmbiguousMethod = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "AmbiguousMethod");
//	public static final JstProblemId DirectInvocationOfAbstractMethod = new JstProblemId(
//			JstProblemCatIds.METHOD_RELATED, "DirectInvocationOfAbstractMethod");	
	public static final JstProblemId VoidMethodReturnsValue = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "VoidMethodReturnsValue");
//	public static final JstProblemId MethodReturnsVoid = new JstProblemId(
//			JstProblemCatIds.METHOD_RELATED, "MethodReturnsVoid");
//	public static final JstProblemId UsingDeprecatedMethod = new JstProblemId(
//			JstProblemCatIds.METHOD_RELATED, "UsingDeprecatedMethod");

//	public static final JstProblemId MethodRequiresBody = new JstProblemId(
//			JstProblemCatIds.INTERNAL_METHOD_RELATED, "MethodRequiresBody");
	public static final JstProblemId ShouldReturnValue = new JstProblemId(
			JstProblemCatIds.INTERNAL_METHOD_RELATED, "ShouldReturnValue");
	public static final JstProblemId UndefinedFunction = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "UndefinedFunction");
	
//	public static final JstProblemId MethodButWithConstructorName = new JstProblemId(
//			JstProblemCatIds.METHOD_RELATED, "MethodButWithConstructorName");
//	
//	public static final JstProblemId MissingReturnType = new JstProblemId(
//			JstProblemCatIds.METHOD_RELATED, "MissingReturnType");
//	
	public static final JstProblemId UnreachableStmt = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "UnreachableStmt");
	
	// NATIVE NOT SUPPORTED IN VJO
//	public static final JstProblemId BodyForNativeMethod = new JstProblemId(
//			JstProblemCatIds.METHOD_RELATED, "BodyForNativeMethod");
	
	// ABSTRACT METHOD NOT SUPPORTED IN VJO
	public static final JstProblemId BodyForAbstractMethod = new JstProblemId(
			JstProblemCatIds.INTERNAL_METHOD_RELATED, "BodyForAbstractMethod");
//	public static final JstProblemId NoMessageSendOnBaseType = new JstProblemId(
//			JstProblemCatIds.METHOD_RELATED, "NoMessageSendOnBaseType");
	public static final JstProblemId ParameterMismatch = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "ParameterMismatch");
//	public static final JstProblemId NoMessageSendOnArrayType = new JstProblemId(
//			JstProblemCatIds.METHOD_RELATED, "NoMessageSendOnArrayType");
//	public static final JstProblemId NonStaticAccessToStaticMethod = new JstProblemId(
//			JstProblemCatIds.INTERNAL_METHOD_RELATED, "NonStaticAccessToStaticMethod");
	public static final JstProblemId UnusedPrivateMethod = new JstProblemId(
			JstProblemCatIds.INTERNAL_METHOD_RELATED, "UnusedPrivateMethod");
//	public static final JstProblemId IndirectAccessToStaticMethod = new JstProblemId(
//			JstProblemCatIds.INTERNAL_METHOD_RELATED, "IndirectAccessToStaticMethod");
	public static final JstProblemId WrongNumberOfArguments = new JstProblemId(
			JstProblemCatIds.INTERNAL_METHOD_RELATED, "WrongNumberOfArguments");
	
	//UNSURE WHAT THIS IS
//	public static final JstProblemId NotAFunction = new JstProblemId(
//			JstProblemCatIds.INTERNAL_METHOD_RELATED, "NotAFunction");
	
	public static final JstProblemId OverrideSuperFinalMethod = new JstProblemId(
			JstProblemCatIds.INTERNAL_METHOD_RELATED, "OverrideSuperFinalMethod");
	
	public static final JstProblemId OverrideSuperStaticMethod = new JstProblemId(
			JstProblemCatIds.INTERNAL_METHOD_RELATED, "OverrideSuperStaticMethod");
	
	public static final JstProblemId OverrideSuperMethodWithReducedVisibility = new JstProblemId(
			JstProblemCatIds.INTERNAL_METHOD_RELATED, "OverrideSuperMethodWithReducedVisibility");
	
	public static final JstProblemId MethodBothFinalAndAbstract = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "MethodBothFinalAndAbstract");
	
	public static final JstProblemId OverlyVisibleMethod = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "OverlyVisibleMethod");
	
	public static final JstProblemId MethodBothStaticAndAbstract = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "MethodBothStaticAndAbstract");

	public static final JstProblemId MethodBothPrivateAndAbstract = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "MethodBothPrivateAndAbstract");
	
//	public static final JstProblemId OverloadMethodWithVariableModifiers = new JstProblemId(
//			JstProblemCatIds.METHOD_RELATED, "OverloadMethodWithVariableModifiers");
	
	public static final JstProblemId AmbiguousOverloadingMethods = new JstProblemId(
			JstProblemCatIds.METHOD_RELATED, "AmbiguousOverloadingMethods");
}
