package org.ebayopensource.dsf.jst.validation.vjo.dsf.format;

import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P3;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;

import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Description;

public class FormatTests  extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
    }
    

    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testBigNumberTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.format.", "BigNumberTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testCommentTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.format.", "CommentTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testConcatTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.format.", "ConcatTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testEmptyTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.format.", "EmptyTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testForTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.format.", "ForTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testInner2Tests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.format.", "Inner2Tests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
	
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testInner3Tests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.format.", "Inner3Tests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testInnerTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.format.", "InnerTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testParens2Tests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.format.", "Parens2Tests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testRegExTests() {
    	 expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 6, 0));
         expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 7, 0));
         expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 8, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.format.", "RegExTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testUnicodeTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.format.", "UnicodeTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
}
