/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.syntax;





import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Ignore;
import org.junit.Test;




//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoSyntaxTest extends VjoValidationBaseTester{
	
	List<VjoSemanticProblem> actualProblems = null;
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Ctype can have only one inherits statement. " +
//			"Multiple inherits statements will result in validation error")
	public void testDupInherits() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2, 0));
		//bugfix by roy, missing type in type space is covered by multiple inherits in this case
		//multiple inherits results in an undefined method for the 2nd inherits call, which blocks the subsequent error in .inherits mtd invocation expr
//		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 3, 0));
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 3, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.syntax.", "CNoExist.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P5,FAST,UNIT})
	//@Description("Test VJET validation if no type is defined.")
	@Ignore("No type here")
	public void testNoType() throws Exception {
		expectProblems.clear();
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.syntax.", "Notype.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test validation warning for redundant needs. The types provided in satisfies, " +
//			"inherits, mixin are honorary needs and not required to be added inot needs statement")
	public void testSyntaxCombined() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownMissingImport, 15, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.RedundantImport, 2, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.RedundantImport, 3, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.RedundantImport, 4, 0));
        expectProblems.add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
		
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.syntax.", "Syntax.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Tests the proper package name usage in decalring a VJET type.")
	public void testBadTypeName() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(TypeProbIds.IsClassPathCorrect, 1, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeHasIllegalToken, 1, 0));
		
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.syntax.", "BadTypeName.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
}
