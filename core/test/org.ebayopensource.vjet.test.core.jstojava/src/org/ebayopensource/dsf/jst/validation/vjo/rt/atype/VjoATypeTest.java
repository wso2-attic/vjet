/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.rt.atype;






import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@Category({P2,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoATypeTest extends VjoValidationBaseTester{
	
	List<VjoSemanticProblem> actualProblems = null;
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test validation error for non existing type usage and redundant needs")
	public void testAType() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.atype.", "AType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test proper usage of abstract type declaration")
	public void testBaseAType() throws Exception {
		expectProblems.clear();
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.atype.", "BaseAType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P3,FAST,UNIT})
	//@Description("Test CType can have only needs, satisfies, inherits, mixin, " +
//			"props, protos, options, inits and endtype sections")
	public void testBadAType() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
		expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 3, 0));
		expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 4, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.atype.", "BadAType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P3,FAST,UNIT})
	//@Description("Test CType can have only needs, satisfies, inherits, mixin, " +
//			"props, protos, options, inits and endtype sections")
	public void testBadAType2() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.atype.", "BadAType2.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
}
