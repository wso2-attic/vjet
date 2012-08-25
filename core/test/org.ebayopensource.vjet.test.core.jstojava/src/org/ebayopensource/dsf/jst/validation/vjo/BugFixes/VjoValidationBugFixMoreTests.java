/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.BugFixes;





import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.JstProblemId;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoValidationBugFixMoreTests extends VjoValidationBaseTester {
    List<VjoSemanticProblem> actualProblems = null;

    private List<JstProblemId> getAllJstProblemIds(List<VjoSemanticProblem> list) {
        List<JstProblemId> probList = new ArrayList<JstProblemId>();
        for (VjoSemanticProblem problem : list) {
            probList.add(problem.getID());
        }
        return probList;
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("user should not be able to add properties to vjo.Enum")
    // Bug 1074
    public void testAddPropertyToEnum() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 6, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug1074_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test assign string boolean and int")
    // Bug 4007
    public void testCompatibleTypes() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 17, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 15, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 21, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 19, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ShouldReturnValue,
                13, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4007_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test empty needs")
    // Bug 356
    public void testEmptyNeeds_1() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.ClassNameShouldNotBeEmpty, 2, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug356_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test empty needs")
    // Bug 356
    public void testEmptyNeeds_2() throws Exception {
        expectProblems.clear();
        // .needs('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug356_2', '') is
        // valid!
        // expectProblems.add(createNewProblem(TypeProbIds.ClassNameShouldNotBeEmpty,
        // 2,
        // 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug356_2.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 464
//    @Ignore
    public void testExpectsInCType() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug464_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 475
    //@Category( { P3, FAST, UNIT })
    //@Description("Test undefined method")
    public void testExpectsInIType() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
        // bugfix by roy, both vjo.NEEDS_IMPL and empty function are taken as
        // empty function now
        // expectProblems.add(createNewProblem(MethodProbIds.BodyForAbstractMethod,
        // 6,
        // 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug475_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 4280
    //@Category( { P3, FAST, UNIT })
    //@Description("Test final peroperty initilize ")
    public void testFinalInConstructs_1() throws Exception {
        expectProblems.clear();
        // final property isn't initialized, should be allowed in constructor
        // assignements
        // expectProblems.add(createNewProblem(FieldProbIds.FinalFieldAssignment,
        // 8,
        // 0));
        // expectProblems.add(createNewProblem(FieldProbIds.FinalFieldAssignment,
        // 9,
        // 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4280_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 4280
    //@Category( { P3, FAST, UNIT })
    //@Description("Test final peroperty initilize ")
    public void testFinalInConstructs_2() throws Exception {
        expectProblems.clear();
        // expectProblems.add(createNewProblem(FieldProbIds.FinalFieldAssignment,
        // 8, 0));
        // expectProblems.add(createNewProblem(FieldProbIds.FinalFieldAssignment,
        // 9, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4280_2.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 478
    //@Category( { P1, FAST, UNIT })
    //@Description("Test metype inherits ")
    public void testInheritsInMType() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
        // bugfix by roy, cannot predict the method invocation result now
        // mtype doesn't have extends anymore, TODO should be a signle error as
        // undefined method, and no subsequent error
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug478_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 481
    //@Category( { P1, FAST, UNIT })
    //@Description("Test mtype inits block ")
    public void testInitsInMType() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 8, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug481_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 1159
    //@Category( { P1, FAST, UNIT })
    //@Description("Test create etype ")
    public void testInstantiateEnum() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.InvalidClassInstantiation, 6, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug1159_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }


    @Test
    // Bug 490
    //@Category( { P3, FAST, UNIT })
    //@Description("Test duplicate method name ")
    public void testMethodsWithSameName_1() throws Exception {
        expectProblems.clear();
        
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 25,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 29,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 33,
                0));
        expectProblems
                .add(createNewProblem(MethodProbIds.AmbiguousMethod, 8, 0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 12,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 16,
                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug490_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 490
    //@Category( { P3, FAST, UNIT })
    //@Description("Test duplicate method name ")
    public void testMethodsWithSameName_2() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.AmbiguousMethod, 7, 0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 10,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 13,
                0));
        // bugfix by roy, empty function body is allowed for abstract method
        // expectProblems.add(createNewProblem(MethodProbIds.BodyForAbstractMethod,
        // 4,
        // 0));
        // expectProblems.add(createNewProblem(MethodProbIds.BodyForAbstractMethod,
        // 7,
        // 0));
        // expectProblems.add(createNewProblem(MethodProbIds.BodyForAbstractMethod,
        // 10,
        // 0));
        // expectProblems.add(createNewProblem(MethodProbIds.BodyForAbstractMethod,
        // 13,
        // 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug490_2.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 490
    //@Category( { P3, FAST, UNIT })
    //@Description("Test duplicate method name ")
    public void testMethodsWithSameName_3() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 25,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 29,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 33,
                0));
        expectProblems
                .add(createNewProblem(MethodProbIds.AmbiguousMethod, 8, 0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 12,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 16,
                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug490_3.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 490
    //@Category( { P3, FAST, UNIT })
    //@Description("Test duplicate method name ")
    public void testMethodsWithSameName_4() throws Exception {
        expectProblems.clear();
        // bugfix by roy, redundant errors removed, test resource sequence
        // updated, line number updated accordingly
        // etype should have values come in prior to protos
        // expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
        // 30, 0));
        // expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
        // 32, 0));
        // expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
        // 33, 0));
        // expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
        // 31, 0));
        // expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
        // 29, 0));
        // expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
        // 34, 0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 32,
                0));
        // expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
        // 35, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug490_4.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test duplicate method name ")
    public void testMethodsWithSameName_5() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.AmbiguousMethod, 4, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug490_5.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 476
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Itype mixin mtype ")
    public void testMixinInIType() throws Exception {
        expectProblems.clear();
        // bugfix by roy, empty function body is allowed for abstract method
        // expectProblems.add(createNewProblem(MethodProbIds.BodyForAbstractMethod,
        // 6,
        // 0));
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
        // expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 3,
        // 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug476_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 482
    //@Category( { P1, FAST, UNIT })
    //@Description("Test mtype mixin mtype ")
    public void testMixinInMType() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(VjoSyntaxProbIds.MTypeShouldOnlyBeMixined, 1, 0));
         expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2,
         0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug482_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

//    @Test
//    // Bug 477
//    //@Category( { P3, FAST, UNIT })
//    //@Description("Test itype mixin  ")
//    public void testMixinPropsInIType() throws Exception {
//        expectProblems.clear();
//        // expectProblems.add(createNewProblem(MethodProbIds.BodyForAbstractMethod,
//        // 6,
//        // 0));
//        expectProblems
//                .add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
//        // expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 3,
//        // 0));
//        actualProblems = getVjoSemanticProblem(
//                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug477_1.js",
//                this.getClass());
//        assertProblemEquals(expectProblems, actualProblems);
//    }

//    @Test
//    // Bug 483
//    //@Category( { P3, FAST, UNIT })
//    //@Description("Test itype mixin  ")
//    public void testMixinPropsInMType() throws Exception {
//        expectProblems.clear();
//        expectProblems
//                .add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
//        // expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 3,
//        // 0));
//        actualProblems = getVjoSemanticProblem(
//                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug483_1.js",
//                this.getClass());
//        assertProblemEquals(expectProblems, actualProblems);
//    }

    @Test
    // Bug 796
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Multiple inherit  ")
    public void testMultipleInherits_1() throws Exception {
        expectProblems.clear();
        // expectProblems.add(createNewProblem(VjoSyntaxProbIds.MultipleInherits,
        // 3,
        // 0));
        // bugfix by roy, change multiple inherits error to undefined method
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 3, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug796_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 796
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Multiple inherit  ")
    public void testMultipleInherits_2() throws Exception {
        expectProblems.clear();
        // expectProblems.add(createNewProblem(VjoSyntaxProbIds.MultipleInherits,
        // 4,
        // 0));
        // bugfix by roy, change multiple inherits error to undefined method
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 4, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug796_2.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 796
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Multiple inherit  ")
    public void testMultipleInherits_3() throws Exception {
        expectProblems.clear();
        // bugfix by roy, empty body rule is now loosen
        // expectProblems.add(createNewProblem(MethodProbIds.BodyForAbstractMethod,
        // 7,
        // 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug796_3.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 303
    //@Category( { P3, FAST, UNIT })
    //@Description("Test itype satisfy")
    public void testSatisfiesInIType_1() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
        // expectProblems.add(createNewProblem(MethodProbIds.BodyForAbstractMethod,
        // 4,
        // 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug303_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 303
    //@Category( { P3, FAST, UNIT })
    //@Description("Test itype satisfy")
    public void testSatisfiesInIType_2() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
        // expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 3,
        // 0));
        // expectProblems.add(createNewProblem(MethodProbIds.BodyForAbstractMethod,
        // 5,
        // 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug303_2.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);

    }

    @Test
    // Bug 479
    //@Category( { P3, FAST, UNIT })
    //@Description("Test mtype satisfy")
    public void testSatisfiesInMType_1() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug479_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 305
    //@Category( { P1, FAST, UNIT })
    //@Description("Test itype include static function")
    public void testStaticFuncITypeError_1() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.IllegalModifierForInterface, 3, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug305_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 305
    //@Category( { P1, FAST, UNIT })
    //@Description("Test itype include static function")
    public void testStaticFuncITypeError_2() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.IllegalModifierForInterface, 3, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug305_2.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 4446
    //@Category( { P1, FAST, UNIT })
    //@Description("Test assignment is invalid")
    public void testTypeCase_1() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 19, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 10, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 18, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 14, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 20, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 24, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 22, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 23, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4446_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 353
    //@Category( { P1, FAST, UNIT })
    //@Description("Test type object start with vjo")
    public void testTypeSpaceWithVjoPrefix_1() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug353_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 1170
    //@Category( { P1, FAST, UNIT })
    //@Description("Test undefined variable")
    public void testUndefinedVar() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug1170_Enum.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 394
    //@Category( { P3, FAST, UNIT })
    //@Description("Test parameter delcarate as void")
    public void testVoidParam_1() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 6,
                0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 8, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
                13, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug394_1.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    // Bug 394
    //@Description("Test parameter delcarate as void")
    public void testVoidParam_2() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 6,
                0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 8, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug394_2.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
}
