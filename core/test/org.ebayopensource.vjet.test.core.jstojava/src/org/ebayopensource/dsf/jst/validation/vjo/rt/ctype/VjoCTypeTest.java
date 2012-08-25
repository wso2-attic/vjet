/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.rt.ctype;







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
public class VjoCTypeTest extends VjoValidationBaseTester{
	
	List<VjoSemanticProblem> actualProblems = null;
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test validation error for non existing type usage and redundant needs")
	public void testCType() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 4, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.RedundantImport, 2, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.RedundantImport, 2, 0));
		expectProblems.add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
		//bugfix by roy, report only type unresolved
//		expectProblems.add(createNewProblem(TypeProbIds.SuperInterfaceMustBeAnInterface, 1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "CType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test CType is defined properly and there should not be any validation error/warning")
	public void testBaseCType() throws Exception {
		expectProblems.clear();
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BaseCType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P3,FAST,UNIT})
	//@Description("Test CType can have only needs, satisfies, inherits, mixin, " +
//			"props, protos, options, inits and endtype sections")
	public void testBadCType() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P3,FAST,UNIT})
	//@Description("Test CType can have only needs, satisfies, inherits, mixin, " +
//			"props, protos, options, inits and endtype sections")
	public void testBadCType1() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCType1.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P3,FAST,UNIT})
	//@Description("Test CType can have only needs, satisfies, inherits, mixin, " +
//			"props, protos, options, inits and endtype sections")
	public void testBadCType2() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCType2.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P3,FAST,UNIT})
	//@Description("Test CType can have only needs, satisfies, inherits, mixin, " +
//			"props, protos, options, inits and endtype sections")
	public void testBadCType4() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCType4.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test CType must implement the abstract methods of itype it is satisfying")
	public void testBadCType3() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCType3.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test every VJET JS must end with endType() section")
	public void testBadCType5() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.MissingEndType,1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCType5.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test every VJET JS must have only one props, protos")
	public void testBadCType6() throws Exception {
		expectProblems.clear();
//		expectProblems.add(createNewProblem(VjoSyntaxProbIds.MultipleProps,4, 0));
		//bugfix by roy, change to undefined method after self-described vjo inplace
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 4, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCType6.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P4,FAST,UNIT})
	//@Description("Test every VJET JS must have only one props, protos")
	public void testBadCType7() throws Exception {
		expectProblems.clear();
//		expectProblems.add(createNewProblem(VjoSyntaxProbIds.MultipleProtos,4, 0));
//		bugfix by roy, change to undefined method after self-described vjo inplace
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 4, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCType7.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test every VJET JS must end with endType() section")
	@Ignore
	public void testBadCType8() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.MissingEndType,1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCType8.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test validation error for non existing type usage and redundant needs")
	public void testBadCType9() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.NameSpaceCollision,3, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace,2, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace,3, 0));
		expectProblems.add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCType9.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test validation error for non existing type usage and redundant needs." +
//			" Also, two types of the same name are imported (needs), one of them must have alias.")
	public void testBadCTypeA() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.NameSpaceCollision,2, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace,2, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace,2, 0));
		expectProblems.add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCTypeA.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P2,FAST,UNIT})
	//@Description("Test validation warning for not using private method in the type")
	public void testBadCTypeB() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UnusedPrivateMethod, 5, 0));
		expectProblems.add(createNewProblem(MethodProbIds.UnusedPrivateMethod, 26, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCTypeB.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test validation warning for not importing (needs) the types used in comments and functions/var")
	public void testBadCTypeC() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownMissingImport, 5, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownMissingImport, 4, 0));
		//bugfix by roy, undefined method is covered by missing import
//		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 6, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "BadCTypeC.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test proper usage of alias for needs. No validation warning/errors")
	public void testCTypeAlias() throws Exception {
		expectProblems.clear();
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.", "CTypeAlias.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
}
