/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.rt.mtype;







import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Ignore;
import org.junit.Test;




//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoMTypeTest extends VjoValidationBaseTester{

	List<VjoSemanticProblem> actualProblems = null;
	
	@Test //@Category({P3,FAST,UNIT})
	//@Description("Test validation warning for unused type in needs section")
	@Ignore
	public void testMType() throws Exception {
		expectProblems.clear();
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.mtype.", "MType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test MType is defined properly and there should not be any validation error/warning")
	public void testMixin() throws Exception {
		expectProblems.clear();
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.mtype.", "Mixin1.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P3,FAST,UNIT})
	//@Description("Test MType is defined properly and there should not be any validation error/warning")
	public void testMixType() throws Exception {
		expectProblems.clear();
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.mtype.", "Employee1.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P4,FAST,UNIT})
	//@Description("Test validation error for non existing type usage and redundant needs")
	public void testBaseMType() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2,0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 3,0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 4,0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.RedundantImport, 2,0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.mtype.", "BaseMType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P4,FAST,UNIT})
	//@Description("Test MType can have only needs, satisfies, expects, props, protos, options and endtype sections")
	public void testBadMType() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2,  0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.mtype.", "BadMType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P4,FAST,UNIT})
	//@Description("Test MType can have only needs, satisfies, expects, props, protos, options and endtype sections")
	public void testBadMType1() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2,  0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.mtype.", "BadMType1.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	
	@Test //@Category({P2,FAST,UNIT})
	//@Description("Test MType can have only needs, satisfies, expects, props, protos, options and endtype sections")
	public void testBadMType3() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2,  0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.mtype.", "BadMType3.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P2,FAST,UNIT})
	//@Description("Test MType can have only needs, satisfies, expects, props, protos, options and endtype sections")
	public void testBadMType4() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2,  0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.mtype.", "BadMType4.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P2,FAST,UNIT})
	//@Description("Test MType can have only needs, satisfies, expects, props, protos, options and endtype sections")
	public void testBadMType5() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2,  0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.mtype.", "BadMType5.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
}
