package org.ebayopensource.dsf.jst.validation.vjo.dsf.dom;

import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P3;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;

import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Description;

public class DomTests extends VjoValidationBaseTester {
	
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testW3cDomLevel1CoreTests() {
    	   expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 35, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem("dsf.dom.",
                "W3cDomLevel1CoreTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testXMLDomTests() {
    	expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 80, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem("dsf.dom.",
                "XMLDomTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testAnchorTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.dom.html.level2.", "AnchorTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test DSF project, To validate false positive ")
    public void testAreaTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.dom.html.level2.", "AreaTests.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
