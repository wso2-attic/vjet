/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.rt.etype;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoETypeTest extends VjoValidationBaseTester{
	List<VjoSemanticProblem> actualProblems = null;

	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test validation warning for unused type in needs section")
	public void testEType() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.etype.", "EType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Sanity Test, EType defined properly should not produce validation error/warning")
	public void testBaseEType() throws Exception {
		expectProblems.clear();
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.etype.", "BaseEType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test EType can have only needs, satisfies, values, props, protos, inits, options and endtype sections")
	public void testBadEType1() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.etype.", "BadEType1.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test EType can have only needs, satisfies, values, props, protos, inits, options and endtype sections")
	public void testBadEType2() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.etype.", "BadEType2.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test EType should have same name and package as file name and path. " +
//			"Also, EType fields are final and can not be modified.")
	public void testBadETypeAssign() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 8, 0));
		expectProblems.add(createNewProblem(FieldProbIds.FinalFieldAssignment, 7, 0));
		expectProblems.add(createNewProblem(TypeProbIds.IsClassPathCorrect, 1, 0));		
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.etype.", "BadETypeAssign.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
}
