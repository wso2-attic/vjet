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

public class TypeProbIds {
	/**
	 * General type related problems
	 */
//	public static final JstProblemId ObjectHasNoSuperclass = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "ObjectHasNoSuperclass");
//	public static final JstProblemId NotVisibleType = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "UndefinedType");
//	public static final JstProblemId UsingDeprecatedType = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "UsingDeprecatedType");
//	public static final JstProblemId InternalTypeNameProvided = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "InternalTypeNameProvided");
//	public static final JstProblemId UnusedPrivateType = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "UnusedPrivateType");

	public static final JstProblemId IncompatibleTypesInEqualityOperator = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "IncompatibleTypesInEqualityOperator");
	public static final JstProblemId IncompatibleTypesInConditionalOperator = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "IncompatibleTypesInConditionalOperator");
	public static final JstProblemId TypeMismatch = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "TypeMismatch");
//	public static final JstProblemId IndirectAccessToStaticType = new JstProblemId(
//			JstProblemCatIds.INTERNAL_TYPE_RELATED, "IndirectAccessToStaticType");

	/**
	 * Inner types related problems
	 */
//	public static final JstProblemId MissingEnclosingInstanceForConstructorCall = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "MissingEnclosingInstanceForConstructorCall");
//	public static final JstProblemId MissingEnclosingInstance = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "MissingEnclosingInstance");
//	public static final JstProblemId IncorrectEnclosingInstanceReference = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "IncorrectEnclosingInstanceReference");
//	public static final JstProblemId IllegalEnclosingInstanceSpecification = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "IllegalEnclosingInstanceSpecification");
//	public static final JstProblemId CannotDefineStaticInitializerInLocalType = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "CannotDefineStaticInitializerInLocalType");
	public static final JstProblemId CannotDefineStaticMembersInInstanceInnerType = new JstProblemId(
			JstProblemCatIds.INTERNAL_RELATED, "CannotDefineStaticMembersInInstanceInnerType");
//	public static final JstProblemId OuterLocalMustBeFinal = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "OuterLocalMustBeFinal");
//	public static final JstProblemId CannotDefineInterfaceInLocalType = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "IndirectAccessToStaticType");
//	public static final JstProblemId IllegalPrimitiveOrArrayTypeForEnclosingInstance = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "IllegalPrimitiveOrArrayTypeForEnclosingInstance");
//	public static final JstProblemId EnclosingInstanceInConstructorCall = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "EnclosingInstanceInConstructorCall");
//	public static final JstProblemId AnonymousClassCannotExtendFinalClass = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "AnonymousClassCannotExtendFinalClass");
//	public static final JstProblemId CannotDefineAnnotationInLocalType = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "CannotDefineAnnotationInLocalType");
//	public static final JstProblemId CannotDefineEnumInLocalType = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "CannotDefineEnumInLocalType");
//	public static final JstProblemId NonStaticContextForEnumMemberType = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "NonStaticContextForEnumMemberType");
//	public static final JstProblemId TypeHidingType = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "TypeHidingType");
	
//	// NOT SUPPORTED
//	public static final JstProblemId UnhandledExceptionInDefaultConstructor = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "UnhandledExceptionInDefaultConstructor");
//	// NOT SUPPORTED
//	public static final JstProblemId UnhandledExceptionInImplicitConstructorCall = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "UnhandledExceptionInImplicitConstructorCall");
//		
	// allocations
	public static final JstProblemId InvalidClassInstantiation = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "InvalidClassInstantiation");
//	public static final JstProblemId CannotDefineDimensionExpressionsWithInit = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "CannotDefineDimensionExpressionsWithInit");
//	public static final JstProblemId MustDefineEitherDimensionExpressionsOrInitializer = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "MustDefineEitherDimensionExpressionsOrInitializer");
	

//	public static final JstProblemId DiscouragedReference = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "DiscouragedReference");
//	public static final JstProblemId InterfaceCannotHaveInitializers = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "InterfaceCannotHaveInitializers");
//	public static final JstProblemId DuplicateModifierForType = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "DuplicateModifierForType");
	public static final JstProblemId IllegalModifierForClass = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "IllegalModifierForClass");
	public static final JstProblemId IllegalModifierForInterface = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "IllegalModifierForInterface");
//	public static final JstProblemId IllegalModifierForMemberClass = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "IllegalModifierForMemberClass");
//	public static final JstProblemId IllegalModifierForMemberInterface = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "IllegalModifierForMemberInterface");
//	public static final JstProblemId IllegalModifierForLocalClass = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "IllegalModifierForLocalClass");
//	public static final JstProblemId ForbiddenReference = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "ForbiddenReference");
//	public static final JstProblemId IllegalModifierCombinationFinalAbstractForClass = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "IllegalModifierCombinationFinalAbstractForClass");

//	public static final JstProblemId IllegalVisibilityModifierForInterfaceMemberType = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "IllegalVisibilityModifierForInterfaceMemberType");
//	public static final JstProblemId IllegalVisibilityModifierCombinationForMemberType = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "IllegalVisibilityModifierCombinationForMemberType");
//	public static final JstProblemId IllegalStaticModifierForMemberType = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "IllegalStaticModifierForMemberType");
	public static final JstProblemId SuperclassMustBeAClass = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "SuperclassMustBeAClass");
	public static final JstProblemId ClassExtendFinalClass = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "ClassExtendFinalClass");
	public static final JstProblemId ClassExtendItself = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "ClassExtendItself");
//	public static final JstProblemId DuplicateSuperInterface = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "DuplicateSuperInterface");
	public static final JstProblemId SuperInterfaceMustBeAnInterface = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "SuperInterfaceMustBeAnInterface");
	public static final JstProblemId ExpectsMustBeMtypeOrItype = new JstProblemId(
            JstProblemCatIds.TYPE_RELATED, "ExpectsMustBeMtypeOrItype");
    public static final JstProblemId MixinedTypeShouldNotBeItself = new JstProblemId(
            JstProblemCatIds.TYPE_RELATED, "MixinedTypeShouldNotBeItself");
//	public static final JstProblemId HierarchyCircularitySelfReference = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "HierarchyCircularitySelfReference");
//	public static final JstProblemId HierarchyCircularity = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "HierarchyCircularity");
	public static final JstProblemId HidingEnclosingType = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "HidingEnclosingType");
//	public static final JstProblemId DuplicateNestedType = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "DuplicateNestedType");
//	public static final JstProblemId CannotThrowType = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "CannotThrowType");
//	public static final JstProblemId PackageCollidesWithType = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "PackageCollidesWithType");
//	public static final JstProblemId TypeCollidesWithPackage = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "TypeCollidesWithPackage");
	public static final JstProblemId DuplicateTypes = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "DuplicateTypes");
	public static final JstProblemId IsClassPathCorrect = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "IsClassPathCorrect");
//	public static final JstProblemId PublicClassMustMatchFileName = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "PublicClassMustMatchFileName");
//	public static final JstProblemId MustSpecifyPackage = new JstProblemId(
//			JstProblemCatIds.INTERNAL_RELATED, "MustSpecifyPackage");
//	public static final JstProblemId HierarchyHasProblems = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "HierarchyHasProblems");
//	public static final JstProblemId PackageIsNotExpectedPackage = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "PackageIsNotExpectedPackage");
//	public static final JstProblemId ObjectCannotHaveSuperTypes = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED, "ObjectCannotHaveSuperTypes");
	public static final JstProblemId ObjectMustBeClass = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED, "ObjectMustBeClass");
	public static final JstProblemId NoMixinAllowedForMixin = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED,"NoMixinAllowedForMixin");
	public static final JstProblemId MixinExpectsMustBeSatisfied = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED,"MixinExpectsMustBeSatisfied");
//	public static final JstProblemId MTypeExpectsCannotBeOverwritten = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED,"MTypeExpectsCannotBeOverwritten");
//	public static final JstProblemId MTypeShouldNotBeReferencedDirectly = new JstProblemId(
//			JstProblemCatIds.TYPE_RELATED,"MTypeShouldNotBeReferencedDirectly");
	public static final JstProblemId ClassBetterStartsWithCapitalLetter = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED,"ClassBetterStartsWithCapitalLetter");
	public static final JstProblemId ClassNameShouldNotBeEmpty = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED,"ClassNameShouldNotBeEmpty");
	public static final JstProblemId InactiveNeedsInUse = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED,"InactiveNeedsInUse");
	public static final JstProblemId UnusedActiveNeeds = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED,"UnusedActiveNeeds");
	public static final JstProblemId GenericParamTypeMismatch = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED,"GenericParamTypeMismatch");
	public static final JstProblemId GenericParamNumMismatch = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED,"GenericParamNumMismatch");
	public static final JstProblemId AttributorTypeUsingVjRuntime = new JstProblemId(
			JstProblemCatIds.TYPE_RELATED,"AttributorTypeUsingVjRuntime");
}
