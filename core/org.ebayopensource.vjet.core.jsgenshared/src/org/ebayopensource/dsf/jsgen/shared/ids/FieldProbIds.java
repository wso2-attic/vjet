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

public class FieldProbIds {

	// fields
	public static final JstProblemId UndefinedField = new JstProblemId(
			JstProblemCatIds.FIELD_RELATED, "UndefinedField");
	public static final JstProblemId NotVisibleField = new JstProblemId(
			JstProblemCatIds.FIELD_RELATED, "NotVisibleField");
	public static final JstProblemId AmbiguousField = new JstProblemId(
			JstProblemCatIds.FIELD_RELATED, "AmbiguousField");
//	public static final JstProblemId UsingDeprecatedField = new JstProblemId(
//			JstProblemCatIds.FIELD_RELATED, "UsingDeprecatedField");
	public static final JstProblemId NonStaticFieldFromStaticInvocation = new JstProblemId(
			JstProblemCatIds.FIELD_RELATED, "NonStaticFieldFromStaticInvocation");
	public static final JstProblemId StaticReferenceToNonStaticType = new JstProblemId(
            JstProblemCatIds.FIELD_RELATED, "StaticReferenceToNonStaticType");
//	public static final JstProblemId ReferenceToForwardField = new JstProblemId(
//			JstProblemCatIds.INTERNAL_FIELD_RELATED, "ReferenceToForwardField");
	public static final JstProblemId NonStaticAccessToStaticField = new JstProblemId(
			JstProblemCatIds.INTERNAL_FIELD_RELATED, "NonStaticAccessToStaticField");
	public static final JstProblemId UnusedPrivateField = new JstProblemId(
			JstProblemCatIds.INTERNAL_FIELD_RELATED, "UnusedPrivateField");
//	public static final JstProblemId IndirectAccessToStaticField = new JstProblemId(
//			JstProblemCatIds.INTERNAL_FIELD_RELATED, "UndefinedField");
//	public static final JstProblemId UnqualifiedFieldAccess = new JstProblemId(
//			JstProblemCatIds.INTERNAL_FIELD_RELATED, "UnqualifiedFieldAccess");
	
	// blank final fields
	public static final JstProblemId FinalFieldAssignment = new JstProblemId(
			JstProblemCatIds.FIELD_RELATED, "FinalFieldAssignment");
	public static final JstProblemId UninitializedBlankFinalField = new JstProblemId(
			JstProblemCatIds.FIELD_RELATED, "UninitializedBlankFinalField");
//	public static final JstProblemId DuplicateBlankFinalFieldInitialization = new JstProblemId(
//			JstProblemCatIds.FIELD_RELATED, "DuplicateBlankFinalFieldInitialization");
	
	public static final JstProblemId DuplicateField = new JstProblemId(
			JstProblemCatIds.FIELD_RELATED, "DuplicateField");
//	public static final JstProblemId DuplicateModifierForField = new JstProblemId(
//			JstProblemCatIds.FIELD_RELATED, "DuplicateModifierForField");
//	public static final JstProblemId IllegalModifierForField = new JstProblemId(
//			JstProblemCatIds.FIELD_RELATED, "IllegalModifierForField");
//	public static final JstProblemId IllegalModifierForInterfaceField = new JstProblemId(
//			JstProblemCatIds.FIELD_RELATED, "IllegalModifierForInterfaceField");
//	public static final JstProblemId IllegalVisibilityModifierCombinationForField = new JstProblemId(
//			JstProblemCatIds.FIELD_RELATED, "IllegalVisibilityModifierCombinationForField");
//	public static final JstProblemId IllegalModifierCombinationFinalVolatileForField = new JstProblemId(
//			JstProblemCatIds.FIELD_RELATED, "IllegalModifierCombinationFinalVolatileForField");
//	public static final JstProblemId UnexpectedStaticModifierForField = new JstProblemId(
//			JstProblemCatIds.FIELD_RELATED, "UnexpectedStaticModifierForField");
	
	public static final JstProblemId FieldInitializationDependsOnUnintializedTypes = new JstProblemId(
			JstProblemCatIds.FIELD_RELATED, "FieldInitializationDependsOnUnintializedTypes");

	
}
