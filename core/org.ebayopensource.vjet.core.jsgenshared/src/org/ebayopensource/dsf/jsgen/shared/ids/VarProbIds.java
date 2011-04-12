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

public class VarProbIds {


	// variables
	public static final JstProblemId UndefinedName = new JstProblemId(
			JstProblemCatIds.INTERNAL_FIELD_RELATED, "UndefinedName");
//	public static final JstProblemId UninitializedLocalVariable = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "UninitializedLocalVariable");
//	public static final JstProblemId VariableTypeCannotBeVoid = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "VariableTypeCannotBeVoid");
//	public static final JstProblemId CannotAllocateVoidArray = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "CannotAllocateVoidArray");
	
	// local variables
	public static final JstProblemId RedefinedLocal = new JstProblemId(
			JstProblemCatIds.INTERNAL_RELATED, "RedefinedLocal");
//	public static final JstProblemId RedefinedArgument = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "RedefinedArgument");
	
	// final local variables
//	public static final JstProblemId DuplicateFinalLocalInitialization = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "DuplicateFinalLocalInitialization");
//	public static final JstProblemId NonBlankFinalLocalAssignment = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "NonBlankFinalLocalAssignment");
//	public static final JstProblemId ParameterAssignment = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "ParameterAssignment");
//	public static final JstProblemId FinalOuterLocalAssignment = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "FinalOuterLocalAssignment");
//	public static final JstProblemId LocalVariableIsNeverUsed = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "LocalVariableIsNeverUsed");
//	public static final JstProblemId ArgumentIsNeverUsed = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "ArgumentIsNeverUsed");
//	public static final JstProblemId TooManyArgumentSlots = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "TooManyArgumentSlots");
//	public static final JstProblemId TooManyLocalVariableSlots = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "TooManyLocalVariableSlots");
//	public static final JstProblemId TooManySyntheticArgumentSlots = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "TooManySyntheticArgumentSlots");
//	public static final JstProblemId TooManyArrayDimensions = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "TooManyArrayDimensions");
//	public static final JstProblemId BytecodeExceeds64KLimitForConstructor = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "BytecodeExceeds64KLimitForConstructor");
	
	// variable hiding
	public static final JstProblemId LocalVariableHidingLocalVariable = new JstProblemId(
			JstProblemCatIds.INTERNAL_RELATED, "LocalVariableHidingLocalVariable");
//	public static final JstProblemId LocalVariableHidingField = new JstProblemId(
//			JstProblemCatIds.INTERNAL_FIELD_RELATED, "LocalVariableHidingField");
//	public static final JstProblemId FieldHidingLocalVariable = new JstProblemId(
//			JstProblemCatIds.INTERNAL_FIELD_RELATED, "FieldHidingLocalVariable");
//	public static final JstProblemId FieldHidingField = new JstProblemId(
//			JstProblemCatIds.INTERNAL_FIELD_RELATED, "FieldHidingField");
//	public static final JstProblemId ArgumentHidingLocalVariable = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "ArgumentHidingLocalVariable");
//	public static final JstProblemId ArgumentHidingField = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "ArgumentHidingField");
//	public static final JstProblemId MissingSerialVersion = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "MissingSerialVersion");
	public static final JstProblemId LooseVarDecl = new JstProblemId(
			JstProblemCatIds.INTERNAL_RELATED, "LooseVarDecl");

	
}
