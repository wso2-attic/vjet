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
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.VjoConstants;
import org.ebayopensource.dsf.jst.ProblemSeverity;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoValidationBugFixTests extends VjoValidationBaseTester {
    @Test
    // Bug 4753
    // @Ignore //TODO - Bug 7725 created to fix this test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test alias must be found at current space via this.vj$")
    public void testAliasError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>();
        final List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4753.js", this
                        .getClass());
        // assertProblemEquals(expectProblems, problems);

        assertProblemEquals(expectedProblems, problems);
    }
    
    
    @Test
    // Bug 8786
    //@Category( { P1, FAST, UNIT })
    //@Description("Test alias must be found at current space via this.vj$")
    public void testBug8786() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8786.js",
                this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    

    @Test
    // Bug 4753
    // @Ignore //TODO - Bug 7725 created to fix this test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test alias must be found at current space via this.vj$")
    public void testAliasErrorExtn() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>();
        expectedProblems.add(createNewProblem(TypeProbIds.UnusedActiveNeeds,
                1, 0));
        expectedProblems.add(createNewProblem(FieldProbIds.UndefinedField,
                6, 0));
        final List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4753Extn.js",
                this.getClass());
        assertProblemEquals(expectedProblems, problems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test no NPE before process context")
    // Bug 3942
    public void testBug3942Error() throws Exception {
        try {
            final List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                    "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug3942.js",
                    this.getClass());
            Assert.assertEquals(0, problems.size());
        } catch (AssertionError err) {
            return;
        }

        Assert
                .fail("there should be syntax error blocking the following validation");
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test no NPE before process context")
    // Bug 3943
    public void testBug3943Error() throws Exception {
        final List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "IA.js", this
                        .getClass());
        Assert.assertEquals(0, problems.size());
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Tets correctly parser qualifier name")
    // Bug 3944
    // @Ignore //Bug 7832
    public void testBug3944Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>();
        final List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug3944.js", this
                        .getClass());
        for (VjoSemanticProblem p : problems) {
            System.out.println(p);
        }
        assertProblemEquals(expectedProblems, problems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Tets undefined function")
    // Bug 3945
    public void testBug3945Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>();
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                13, 0));
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                34, 0));
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                24, 0));
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                29, 0));
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                12, 0));
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                23, 0));
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                18, 0));
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                35, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug3945.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 3947
    //@Category( { P3, FAST, UNIT })
    //@Description("test vjo.getType().clazz")
    @Ignore("linking seems to be problem for vjo.getType().clazz works when you run test standalone but not in multiple case")
    public void testBug3947Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>();
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 32, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug3947.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("test correctlly get type name from model")
    // Bug 3981
    public void testBug3981Error() throws Exception {
        try {
            final List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                    "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug3981.js",
                    this.getClass());
            Assert.assertEquals(0, problems.size());
        } catch (AssertionError err) {
            return;
        }

        Assert
                .fail("there should be syntax error blocking the following validation");
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test arguments numerb is wrong with expected")
    // Bug 4591
    // @Ignore
    public void testBug4591Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>();
        expectedProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 2, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.InvalidIdentifier, 2, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4591.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    //  
    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test create Function. Navigator. Array ")
    // Bug 4790, 4402, 3865, 3941, 4627, 4894, 4752, 3972, 5004, 5006
    public void testBug4626Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>();

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "CTypeUtil.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 4630
    // @Ignore
    //@Category( { P1, FAST, UNIT })
    //@Description("test two vjo.ctype exist  ")
    public void testBug4630Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>();
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.IncorrectVjoSyntax, 2, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4630.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 4699
    //@Category( { P3, FAST, UNIT })
    //@Description("test return value 1.0 match definition type ")
    public void testBug4699Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4699.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test can't assign a type to a vaiable ")
    // Bug 4743
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug4743Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4743.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 4754
    //@Category( { P3, FAST, UNIT })
    //@Description("test redefined local variable ")
    public void testBug4754Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(VarProbIds.RedefinedLocal, 8, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4754.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test create Function. Navigator. Array ")
    // Bug 4790
    public void testBug4790Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "CTypeUtil.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test undefined method ")
    // Bug 4791
    public void testBug4791Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 7,
                0));
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod,
                12, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4791.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test ctype only exist inits block ")
    // Bug 4827
    public void testBug4827Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4827.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 4926
    //@Category( { P3, FAST, UNIT })
    //@Description("test defined varibale in inits block ")
    public void testBug4926Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4926.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test anotation args number is differ with actual args number ")
    // Bug 4985
    public void testBug4985Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4985.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test invlok static method from init block")
    // Bug 4987
    public void testBug4987Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4987.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test method return type incompatible with declared type")
    // Bug 4991
    public void testBug4991Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 9, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4991.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Ignore
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("test overload the return type different invoving void situation")
    // Bug 4993
    public void testBug4993Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 6,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4993.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test invoking type is not import but exist in type space")
    // Bug 4997
    public void testBug4997Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownMissingImport, 4, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4997.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 4998
    //@Category( { P1, FAST, UNIT })
    //@Description("test @SUPRESSTYPECHECK tag")
    public void testBug4998Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4998.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 5000
    //@Category( { P1, FAST, UNIT })
    //@Description("test prototype value of ")
    public void testBug5000Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5000.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 5002
    //@Category( { P1, FAST, UNIT })
    //@Description("test create Date object with number ")
    public void testBug5002Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5002.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test alert method can be invloked ")
    // Bug 5013
    public void testBug5013Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5013.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test alert method argment is an invoking expression")
    // Bug 5061
    public void testBug5061Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5061.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test alert method can be exist in props block")
    // Bug 5065
    public void testBug5065Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5065.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("test alert method argment is an expression of array object invoking method or field ")
    // Bug 5066
    public void testBug5066Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5066.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test alert method can give accept arguemtn such as RegExp.$1  ")
    // Bug 5079
    public void testBug5079Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5079.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test alert method can give accept arguemtent as variable invoking expression  ")
    // Bug 5080
    public void testBug5080Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5080.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test alert method can give accept arguemtent as variable invoking expression  ")
    // Bug 5103
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug5103Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5103.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test document can be used directly ")
    // Bug 5108
    public void testBug5108Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5108.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test ctype can be declared as saitisfied IType ")
    // Bug 5152
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug5152Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5152.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test parseFloat can be used directly ")
    // Bug 5234
    public void testBug5234Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5234.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 5293
    //@Category( { P3, FAST, UNIT })
    //@Description("Test type reference ")
    public void testBug5293Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5293.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test mutiply assignment with one var declaration ")
    // Bug 5296
    public void testBug5296Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5296.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test alert argument can be accept by array.name and array.location ")
    // Bug 5297
    public void testBug5297Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5297.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    // @Test //Bug 5296
    // @Ignore //due to itype method doesn't have default abtract modifier
    // it's confirmed that vjo.NEEDS_IMPL doesn't mean abstract equivalent
    // public void testBug5298Error() throws Exception {
    // VjoValidationProblemHelper helper = new VjoValidationProblemHelper();
    // helper.addProblems(new VjoSemanticProblem(
    // MethodProbIds.UndefinedMethod, 1, 0, 0, ProblemSeverity.error));
    // helper.addProblems(new VjoSemanticProblem(
    // MethodProbIds.UndefinedMethod, 1, 0, 0, ProblemSeverity.error));
    //      
    // lookUpTarget("Bug5296IType.vjo");
    // lookUpTarget("Bug5296CType.vjo");
    // VjoValidationResult result = getProblems("Bug5296.vjo");
    // printResult(result);
    // Assert.assertTrue(result.getAllProblems().size() ==
    // helper.getProblems().size());
    //      
    // for(VjoSemanticProblem problem : result.getAllProblems()){
    // Assert.assertTrue(helper.getProblems().contains(problem));
    // }
    // }
    //  
    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test encodeURIComponent method can be used directly ")
    // Bug 5305
    public void testBug5305Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5305.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test mutiply satisfy situation ")
    // Bug 5318
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug5318Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 1,
                0));
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 1,
                0));
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 1,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5318.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("static field can be initilize at inits block")
    public void testBug5346Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5346.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("arguments is bound with Array type,")
    public void testBug5349Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5349.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Assignment with var to var")
    public void testBug5395Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5395.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Outer ctype delcared as private")
    public void testBug5397Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                TypeProbIds.IllegalModifierForClass, 1, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5397.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test unkonw type in type space")
    // @Ignore
    public void testBug5398Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 4, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5398.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test unkonw type in type space")
    public void testBug5399Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownMissingImport, 9, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5399.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test method return type is differ with declared")
    public void testBug5464Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 10, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5464.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test invoking static field or fucntion from protos block")
    public void testBug5466Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                FieldProbIds.NonStaticFieldFromStaticInvocation, 9, 0));
        expectedProblems.add(createNewProblem(
                FieldProbIds.NonStaticFieldFromStaticInvocation, 10, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5466.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Support ?: expression")
    public void testBug5482Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5482.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Get field style")
    public void testBug5483Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5483.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test child and faterh construcours all missed anoation ")
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug5485Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "Bug5485CType2.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test method atuo binding return type ")
    public void testBug5513Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5513.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test invloking array with index ")
    public void testBug5514Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5514.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    @Ignore
    public void testBug5515Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5515.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test anotation function lost () and return with this ")
    public void testBug5516Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5516.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test empty protos block have no valdiation error")
    public void testBug5612Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5612.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test final field assignment")
    public void testBug5678Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                FieldProbIds.FinalFieldAssignment, 11, 0));
        expectedProblems.add(createNewProblem(
                FieldProbIds.FinalFieldAssignment, 17, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5678.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test if statement exist in block")
    // test case is ignored as this requires a backend support of block
    // statement in method body
    public void testBug5685Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5685.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test switch expression return statements")
    public void testBug5710Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
              MethodProbIds.UnreachableStmt, 14, 0));
        
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5710.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test support inner function return statement")
    public void testBug5720Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5720.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test overload method modifiers")
    public void testBug5783Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
//        expectedProblems.add(createNewProblem(
//                MethodProbIds.OverloadMethodWithVariableModifiers, 26, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5783.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test java key words")
    public void testBug5880Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        for (int i = 0; i < VjoConstants.JAVA_ONLY_KEYWORDS.size(); i++) {
            expectedProblems.add(createNewProblem(
                    VjoSyntaxProbIds.InvalidIdentifier, i + 3, 0));
        }

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5880.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test overrriden static methods")
    public void testBug5891Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                MethodProbIds.OverrideSuperStaticMethod, 11, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "Bug5891CType2.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test return statements in inits block")
    public void testBug5908Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5908.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test private constructure")
    // Bug 6056
    public void testBug6056Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "Bug6056CType2.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test undefined name in alert method")
    public void testBug6100Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(VarProbIds.UndefinedName, 4, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6100.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test method is overridden by child of child js file")
    // Bug 6184f method is overridden by child of child js file
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6184Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "Bug6184CType3.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test multiprops block and protos block in itype ")
    // Bug 6191
    public void testBug6191Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        try {
            final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                    "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6191.js",
                    this.getClass());
            assertProblemEquals(expectedProblems, actualProblems);
        } catch (AssertionError error) {
            return;
        }

        Assert
                .fail("syntax error should blocked the test cases from reaching here");
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test otype related issues:multi endtype(). function block")
    // Bug 6217
    public void testBug6217Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
//        expectedProblems.add(createNewProblem(
//                VjoSyntaxProbIds.OTypeWithNoneObjLiteralProperty, 3, 0));
        expectedProblems.add(createNewProblem(
                MethodProbIds.BodyForAbstractMethod, 12, 0));
//        expectedProblems.add(createNewProblem(
//                VjoSyntaxProbIds.OTypeWithInnerTypes, 1, 0));
        // expectedProblems.add(createNewProblem(VjoSyntaxProbIds.IncorrectVjoSyntax,
        // 22, 0));
        // bugfix by roy, incorrect vjo syntax issue are now changed to
        // undefined method error
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod,
                22, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6217.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test reduce overriden visibility")
    // Bug 6222
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6222Error1() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                MethodProbIds.OverrideSuperMethodWithReducedVisibility, 4, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "Bug6222CTypeErr1.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test unimplement method from iType")
    // Bug 6222
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6222Error2() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 1,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "Bug6222CTypeErr2.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test unimplement method from iType")
    // Bug 6222
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6222Error3() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 1,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "Bug6222CTypeErr3.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test unimplement method from iType, but ctype no protos and props block")
    // Bug 6239
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6239Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 1,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6239CType.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test Otype can't as an inner type")
    // Bug 6246
    public void testBug6246Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.OTypeAsInnerType, 3, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6246.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test itype can't be defined with final")
    // Bug 6247
    public void testBug6247Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                TypeProbIds.IllegalModifierForInterface, 1, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6247.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test itype mtype can't be initilized")
    // Bug 6310
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6310Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(TypeProbIds.ObjectMustBeClass, 7,
                0));
        expectedProblems.add(createNewProblem(TypeProbIds.ObjectMustBeClass, 6,
                0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6310.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test itype mtype can't be initilized")
    // Bug 6310
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug8846Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 7, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8846.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
        VjoSemanticProblem problem = actualProblems.get(0);
        Assert.assertTrue(problem.getMessage().contains("Date"));
        Assert.assertTrue(problem.getMessage().contains("Bug8846"));
        Assert.assertTrue(problem.getMessage().contains("String"));
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test ctype inherits atype which have abstract method")
    // Bug 6312
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6312Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 1,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6312CType.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test multi overloaded methods exist in itype. But ctype only implement one method")
    // Bug 6351
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6351Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 1,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6351CType.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test arguments field in current function's property and function")
    public void testBug6358Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6358.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test mtype expect itype which have some function")
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6445Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6445MType.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test ctype and inner type have same name")
    public void testBug6451Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(TypeProbIds.HidingEnclosingType,
                1, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6451.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test create static innertype, and instance innertype")
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6452Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6452.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test ctype mix mtype, and mtype's function and property can be used")
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6465Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6465.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test etype only eixt values and inits block")
    public void testBug6476Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6476.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test itype's modifier and method's mdofier")
    public void testBug6512Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.ITypeAllowsOnlyPublicModifier, 4, 0));
        expectedProblems.add(createNewProblem(
                MethodProbIds.MethodBothFinalAndAbstract, 4, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6512.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test assign etype's value to a variable")
    // @Ignore //parser issue that enum values couldn't be parsed if etype has
    // satisfies
    // unignored in 7725 bugfix, parser seems to be working in this case now
    public void testBug6514Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6514EType.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test itype's inner type is marked final by default")
    // when inner typs is itype, all members become both abstract and final
    // which caused the validation error
    // from validation perspective, it's better that parser could
    // unmark itype's inner type's final flag
    public void testBug6544Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6544.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test itype can't exist instance property")
    public void testBug6545Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.ITypeAllowsOnlyPublicModifier, 5, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.ITypeWithInstanceProperty, 9, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6545.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test function without anonation")
    public void testBug6550Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6550.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test itype public function is overriden with default access specifier")
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6555Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                MethodProbIds.OverrideSuperMethodWithReducedVisibility, 5, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6555CType.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);

        final List<VjoSemanticProblem> expectedProblems2 = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems2 = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6555Main.js",
                this.getClass());
        assertProblemEquals(expectedProblems2, actualProblems2);

        final List<VjoSemanticProblem> expectedProblems3 = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems3.add(createNewProblem(MethodProbIds.NotVisibleMethod,
                8, 0));

        final List<VjoSemanticProblem> actualProblems3 = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.bad.",
                "Bug6555MainBad.js", this.getClass());
        assertProblemEquals(expectedProblems3, actualProblems3);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test ctype extends itself")
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testBug6557Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems3 = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems3.add(createNewProblem(TypeProbIds.ClassExtendItself,
                1, 0));

        final List<VjoSemanticProblem> actualProblems3 = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6557.js", this
                        .getClass());
        assertProblemEquals(expectedProblems3, actualProblems3);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test ctype name can't be named wtih number")
    public void testBug6564Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems3 = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems3.add(createNewProblem(
                VjoSyntaxProbIds.TypeHasIllegalToken, 1, 0));

        final List<VjoSemanticProblem> actualProblems3 = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "6564.js", this
                        .getClass());
        assertProblemEquals(expectedProblems3, actualProblems3);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test etype constructor argsments type match issue")
    public void testBug6565Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6565.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test this.base")
    public void testBug6566Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 5,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6566.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test static property in instance inner class")
    public void testBug6603Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems
                .add(createNewProblem(
                        TypeProbIds.CannotDefineStaticMembersInInstanceInnerType,
                        4, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6603.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test override static methods")
    public void testBug6628Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                MethodProbIds.OverrideSuperStaticMethod, 4, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "Bug6628CType2.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test methods both have static and abstract modifier")
    public void testBug6759Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                MethodProbIds.MethodBothStaticAndAbstract, 7, 0));
        expectedProblems.add(createNewProblem(
                MethodProbIds.MethodBothStaticAndAbstract, 9, 0));
        expectedProblems.add(createNewProblem(
                MethodProbIds.MethodBothPrivateAndAbstract, 14, 0));
        expectedProblems.add(createNewProblem(
                MethodProbIds.MethodBothPrivateAndAbstract, 20, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6759.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    // @Ignore //TODO - Bug 7725 created to fix this test
    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Itype fucntion is duplicate with mixined mtype function")
    public void testBug6803Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        // mtype is a template. Type related validations for satisfy and
        // expects do not apply
        // expectedProblems.add(createNewProblem(
        // TypeProbIds.MTypeExpectsCannotBeOverwritten, 4, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6803MType.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);

        final List<VjoSemanticProblem> expectedProblems2 = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems2 = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6803CType.js",
                this.getClass());
        assertProblemEquals(expectedProblems2, actualProblems2);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test mtype satisfy itype")
    public void testBug8830Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8830Mtype.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test args number is not correct")
    public void testBug8833Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);

        expectedProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
                15, 0));
        expectedProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
                16, 0));
        expectedProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 17, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8833.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test not import type which exist in type space")
    public void testBug8836Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownMissingImport, 3, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8836.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test array child type follow array delclared type")
    public void testBug7013Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 11, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7013.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test type start with capital letter")
    public void testBug7207Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                TypeProbIds.ClassBetterStartsWithCapitalLetter, 1, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "bug7207.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test crate innertype with vjo.make method")
    public void testBug7252Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7252.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test final field initilize with different type from delcared type")
    public void testBug7255Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                6);
        // expectedProblems.add(createNewProblem(
        // FieldProbIds.FinalFieldAssignment, 9, 0));
        // expectedProblems.add(createNewProblem(
        // FieldProbIds.FinalFieldAssignment, 19, 0));
        // expectedProblems.add(createNewProblem(
        // FieldProbIds.FinalFieldAssignment, 20, 0));
        // expectedProblems.add(createNewProblem(
        // FieldProbIds.FinalFieldAssignment, 21, 0));
//         expectedProblems.add(createNewProblem(
//         FieldProbIds.FinalFieldAssignment, 22, 0));
        expectedProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 16, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7255.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test inner type have same type name")
    public void testBug7503Error() throws Exception {
//        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
//                0);
        try {
            /*final List<VjoSemanticProblem> actualProblems = */getVjoSemanticProblem(
                    "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7503.js",
                    this.getClass());
            // Having 2 inner
            Assert.fail("Expected AssertionError due to syntax error.");
        } catch (AssertionError e) {
            // expected
        }
        // assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Literal Validation Error")
    public void testBug7671Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.InvalidIdentifier, 3, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.InvalidIdentifier, 6, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7671.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test assign a funtion to a variable")
    public void testBug7731Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7731.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test redefined local variable")
    public void testBug7752Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems
                .add(createNewProblem(VarProbIds.RedefinedLocal, 18, 0));
        expectedProblems
                .add(createNewProblem(VarProbIds.RedefinedLocal, 20, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7752.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test type name not be empty")
    public void testBug7776Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        // .needs('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug7776CType', '')
        // is valid!
        // expectedProblems.add(createNewProblem(TypeProbIds.ClassNameShouldNotBeEmpty,
        // 2, 0));
        expectedProblems.add(createNewProblem(
                TypeProbIds.ClassNameShouldNotBeEmpty, 3, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7776.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test no implement method in ctyp which no proto and propos block")
    public void testBug7921Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7921.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Mtyp as an inner type")
    public void testBug8028Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.MTypeAsInnerType, 12, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8028.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test initilze outer type in innertype")
    public void testBug8030Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8030.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test invoking undeinfied function")
    public void testBug8119Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                9, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8119.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test no return statement")
    public void testBug8123Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.ShouldReturnValue,
                4, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8123.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test final field initilize")
    public void testBug8311Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8311.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test final field initilize in etype")
    public void testBug8311ETypeError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8311EType.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test invoking static function in inits block")
    public void testBug8450Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8450.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test inactive needs function")
    public void testBug8455Error() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(TypeProbIds.InactiveNeedsInUse,
                1, 0));
        expectedProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 8, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8455.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test not needs type but exist in type space")
    public void testBugBadImport() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownMissingImport, 4, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownMissingImport, 7, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownMissingImport, 17, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "BugBadImport.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test try catch block return range")
    public void testBugCatchException() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.ShouldReturnValue,
                19, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugCatchException.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test etype values have same value")
    public void testBugDupEnumValue() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems
                .add(createNewProblem(FieldProbIds.DuplicateField, 2, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugDupEnumValue.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test ctype can be initilize with generic")
    // bug8464 filed
    public void testBugGenerics() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
                10, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "BugGenerics.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    @Ignore
    public void testBugGlobalVarUndefinedCheck() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugGlobalVarUndefinedCheck.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test ctype can be initilize for a static field")
    public void testBugInitFailure() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                FieldProbIds.FieldInitializationDependsOnUnintializedTypes, 4,
                0));
        expectedProblems.add(createNewProblem(
                FieldProbIds.FieldInitializationDependsOnUnintializedTypes, 5,
                0));
        expectedProblems.add(createNewProblem(
                FieldProbIds.FieldInitializationDependsOnUnintializedTypes, 9,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugInitFailure.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test invoking inner type")
    public void testBugInnerType() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "BugInnerType.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test nested with statements")
    public void testBugNestedWith() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        // bugfix by roy, not reporting issue in with as agreed with Justin now
        // expectedProblems.add(createNewProblem(VarProbIds.ArgumentHidingLocalVariable,
        // 6, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.NestedWithDiscouraged, 7, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugNestedWith.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);

        // on purpose calling twice, use to have aggregated error
        final List<VjoSemanticProblem> actualProblems2 = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugNestedWith.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems2);
    }

    @Test
    @Ignore
    // Roy, please fix it
    public void testBugObjLiteralInSyntax() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.InvalidIdentifier, 6, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.InvalidIdentifier, 3, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugObjLiteralInSyntax.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test nested inner type")
    public void testBugOOM() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "BugOOM.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test type path is different with actual path")
    // Bug 4788
    public void testBugOtherTypeUsageError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(TypeProbIds.IsClassPathCorrect,
                1, 0));
        expectedProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace,
                1, 0));
        
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4788.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test variable delcared as String, then support variable dot string method and field")
    public void testBugStringConcat() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugStringConcat.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test overload methods with conflict situation")
    public void testBugConflictOverloading() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                2);
        expectedProblems.add(createNewProblem(
                MethodProbIds.AmbiguousOverloadingMethods, 11, 0));
        expectedProblems.add(createNewProblem(
                MethodProbIds.AmbiguousOverloadingMethods, 31, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugConflictOverloading.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test overload methods with arg type mismatch situation")
    public void testBugOverloadingArgTypeMismatch() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                2);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugOverloadingArgTypeMismatch.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test support typeof function")
    public void testBugTypeOfUndefined() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                2);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugTypeOfUndefined.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test support vjo.class[]")
    public void testBugVjoClass() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "BugVjoClass.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    // @Ignore //TODO - Bug 7725 created to fix this test
    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test method vjo.make ")
    public void testBugVjoMake() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
                20, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "BugVjoMake.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test document and form's property ")
    // Bug 4829
    public void testDocumentMethodsError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4829.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 4690
    //@Category( { P1, FAST, UNIT })
    //@Description("Test dynamic property")
    public void testDynamicPropertyError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4690.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 4628
    //@Category( { P1, FAST, UNIT })
    //@Description("Test missing endType")
    public void testEndTypeError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(VjoSyntaxProbIds.MissingEndType,
                1, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4628.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test missing end type")
    // Bug 4667
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testEndTypeErrorExtn() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(VjoSyntaxProbIds.MissingEndType,
                1, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4667.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test variable type mismatch with fuction return type")
    // Bug 4839
    public void testGenericTypeMethodError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 10, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4839.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    // Bug 4792
    //@Category( { P3, FAST, UNIT })
    //@Description("Test support no annotation situation")
    public void testIdentifierNotDefinedError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4792.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test support no annotation situation for multi variables")
    // Bug 4792
    public void testIdentifierNotDefinedError1() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4792Extn.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test support no annotation situation for array")
    // Bug 4792
    public void testIdentifierNotDefinedError2() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "Bug4792ExtnMore.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test invalid syntax situation dot.x")
    // Bug 4667
    public void testIncorrectVjoSyntaxError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 1, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.IncorrectVjoSyntax, 1, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4667Extn.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test invalid syntax situation dot.x")
    // Bug 4667
    public void testIncorrectVjoSyntaxMoreError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 1, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.IncorrectVjoSyntax, 1, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "Bug4667ExtnMore.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test instance property in itype")
    // Bug 4680
    public void testITypeMethodBodyError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                MethodProbIds.BodyForAbstractMethod, 6, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.ITypeWithInstanceProperty, 3, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4680.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test instance property in itype")
    // Bug 4679
    public void testITypeProtosPropertiesError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.ITypeWithInstanceProperty, 3, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4679.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test ctype mixin mtype")
    // Bug 4687
    // @Ignore //TODO - Bug 7725 created to fix this test
    public void testMTypeObjectLiteralError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4687.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Math's static function and Date initilize")
    // Bug 5003, 5002, 5073, 5072, 5068, 5067, 5064, 5021, 5014, 5108, 5142
    public void testMultipleBugsCtypeError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "TestMultipleBugsCtype.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test multi props error")
    // Bug 4632Extn
    public void testMultiplePropsError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        // expectedProblems.add(createNewProblem(VjoSyntaxProbIds.MultipleProps,
        // 6, 0));
        // bugfix by roy, change to undefined method as vjo self-described
        // inplace
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 6,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4632Extn.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test multi protos error")
    // Bug 4632
    public void testMultipleProtosError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        // expectedProblems.add(createNewProblem(VjoSyntaxProbIds.MultipleProtos,
        // 6, 0));
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 6,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4632.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test inactive issues")
    // Bug 4668
    public void testNameSpaceCollisionError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4668.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    @Ignore
    //@Category( { P3, FAST, UNIT })
    //@Description("Test inactive issues")
    // Bug 4668
    public void testNameSpaceCollisionError4() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.NameSpaceCollision, 2, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.NameSpaceCollision, 2, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug46684.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test return null")
    // Bug 4702
    public void testReturnNullError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4702.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test return type mismatch wtih declaration")
    // Bug 4696
    public void testReturnTypeError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 5, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4696.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test return type with void situation")
    // Bug 4696
    public void testReturnTypeErrorExtn1() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(
                MethodProbIds.VoidMethodReturnsValue, 5, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4696Extn1.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test assignment self type to variable")
    // Bug 4732
    public void testThisAssignmentError() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4732.js", this
                        .getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test assignment self type to variable")
    // Bug 4732
    public void testThisAssignmentErrorExtn() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4732Extn.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    @Ignore("missing file")
    public void testAfterRefactoring() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "AfterRefactor.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test not exist property in array ")
    public void testUnexistProp() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 7, 0));
        expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 9, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7715.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test typeof")
    public void testTypeOF() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 8, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7831.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test borderWidth constants")
    public void testBug7915Error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7915.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Mtype and Otype as inner type")
    public void testBug8405Error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.MTypeWithInnerTypes, 1, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.MTypeAsInnerType,
                11, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.OTypeAsInnerType,
                13, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8405.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test enume property in inits block")
    public void testBugEnumPropertyInit() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugEnumPropertyInit.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parent and child have same field situation")
    public void testBugFieldHidesParentField() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems
                .add(createNewProblem(FieldProbIds.AmbiguousField, 9, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugFieldHidesParentField.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test nested function")
    public void testBugNestedFunction() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugNestedFunction.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test unimplement ctype methods")
    public void testBugConfusingITypeUnimplemented() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 1,
                0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "BugConfusingITypeUnimplementedCType.js", this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test undefiend mthods's problem type")
    public void testBug8490Error() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 5, 0));
        expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 6, 0));
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 7, 0));
        expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 8, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8490.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
        for (Iterator<VjoSemanticProblem> iterator = actualProblems.iterator(); iterator.hasNext();) {
            final VjoSemanticProblem vjoSemanticProblem = iterator.next();
            if (vjoSemanticProblem.getSourceLineNumber() == 5) {
                Assert.assertEquals(ProblemSeverity.error, vjoSemanticProblem
                        .type());
                continue;
            }
            if (vjoSemanticProblem.getSourceLineNumber() == 6) {
                Assert.assertEquals(ProblemSeverity.warning, vjoSemanticProblem
                        .type());
                continue;
            }
            if (vjoSemanticProblem.getSourceLineNumber() == 7) {
                Assert.assertEquals(ProblemSeverity.error, vjoSemanticProblem
                        .type());
                continue;
            }
            if (vjoSemanticProblem.getSourceLineNumber() == 8) {
                Assert.assertEquals(ProblemSeverity.warning, vjoSemanticProblem
                        .type());
                continue;
            }
        }
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test instanceof ")
    public void testBug7928Error() throws Exception {
        expectProblems.clear();

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug7928.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Can't support var innerType = new o situation")
    public void testBug8553Error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 10,
                0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8553.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("gloable variable error problem type")
    public void testBug8605Error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 7, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8605.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
        Assert.assertEquals(actualProblems.get(0).type(),
                ProblemSeverity.warning);

    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Boolean valueof() issues")
    public void testBug8624Error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8624.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test unimplement methods issues")
    public void testBug8654Error() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 1, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8654.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Support no annoation function's args")
    public void testBug8669Error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8669.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Nested type reutn")
    public void testBug8670Error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8670.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Use global variable in one statement")
    public void testBug8671Error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 5, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 5, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8671.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Void method returns value, down to warning")
    public void testBug8702Error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                MethodProbIds.VoidMethodReturnsValue, 5, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8702.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("No validation in expected section of MType")
    public void testBug6400Error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug6400MType.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("No validation in expected section of MType")
    public void testBug4832Error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug4832.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Inactive needs")
    // Bug 4668
    public void testNameSpaceCollisionError2() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.NameSpaceCollision, 2, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug46682.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Inactive needs")
    public void testBug4668Inactive1() throws Exception {
        expectProblems.add(createNewProblem(
         VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 1, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.",
                "Bug4668Inactive1.js", this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Private constructor")
    public void testBug8707Error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                MethodProbIds.NotVisibleConstructor, 7, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8707.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);

        for (VjoSemanticProblem actualProb : actualProblems) {
            assert actualProb != null;
            assert actualProb.getSourceLineNumber() >= 0;
            assert ProblemSeverity.error.equals(actualProb.type());
        }
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Final variable initiated at con and init")
    public void testBug8717Error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8717.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    // Bug 4668
    //@Category( { P1, FAST, UNIT })
    //@Description("active needs issues")
    @Test
    public void testNameSpaceCollisionError3() throws Exception {
        final List<VjoSemanticProblem> expectedProblems = new ArrayList<VjoSemanticProblem>(
                0);
        expectedProblems.add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1,
                0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.NameSpaceCollision, 2, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.NameSpaceCollision, 3, 0));
        expectedProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 3, 0));

        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug46683.js",
                this.getClass());
        assertProblemEquals(expectedProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("int plus String such like 3+D")
    public void testBug8698Error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8698.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Method name conflict")
    public void testBug8714Error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 11,
                0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8714.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("parseFloat takes String type, fix in js code according to Homayoun now")
    public void testBug8715Error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8715.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Invokeing gloabl function")
    public void testBug8747Error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedFunction, 8,
                0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8747.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Special idintifer")
    public void testBug8751error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8751.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("int to boolean must be type mismatch error")
    public void testBug8710error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 8, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 9, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8710.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Arg as string [] issue")
    public void testBug8822error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 14, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8822.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("This. base issue")
    public void testBug8841error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8841.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("mtype issue")
    public void testBug8851error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8851MType.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);

        final List<VjoSemanticProblem> actualProblems2 = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8851CType.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems2);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("This. base issue")
    public void testBug8780error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8780A.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("return; as return undefined")
    public void testBug8949error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8949.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("This. base issue")
    public void testBug8878error() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.AmbiguousMethod, 9, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8878.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("instanceof should be supported")
    public void testBug8623error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8623.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("same variable name used in different for-in statement")
    public void testBug8834error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.RedefinedLocal, 13, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8834.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
    
    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("if the name in comment is constructs and return type is not void, it should produce warning for not returning a return type.")
    public void testBug8875error() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.ShouldReturnValue, 4, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8875.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
    
    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("if this.X x is an Object or reference type. should skip validate")
    public void testBug8977error() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
                5, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug8977.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
    
    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("types without constructor and extends interfaces should leave as itype, not ctype")
    public void testBug9133error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug9133.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("test java lib type here")
    public void testBug5476error() throws Exception {
        expectProblems.clear();
//        removed by huzhou@ebay.com as it requires java2js dependencies
//        expectProblems.add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug5476.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("test java lib type here")
    public void testBug9650error() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.BugFixes.", "Bug9650.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
    
}
