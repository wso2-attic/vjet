/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.rt.itype;





import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoITypeTest extends VjoValidationBaseTester{
	
	List<VjoSemanticProblem> actualProblems = null;
	
	@Test //@Category({P3,FAST,UNIT})
	//@Description("If needs statement has a type not being used aactively in js, " +
//			"it's redundant and produce validation warning")
	public void testIType() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.itype.", "IType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Sanity test for itype. No validation error should be produced.")
	public void testBaseIType() throws Exception {
		expectProblems.clear();
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.itype.", "BaseIType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test IType can not have satisfies statement.")
	public void testBadIType1() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.itype.", "BadIType.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test IType can not have invalid statement.")
	public void testBadIType2() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.itype.", "BadIType2.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test IType can not have defs statement.")
	public void testBadIType3() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 2, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 1, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.ITypeWithInstanceProperty, 1, 0));
		
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.itype.", "BadIType3.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test IType can not have any statement in method decalred in protos section")
	public void testBadIType4() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.BodyForAbstractMethod, 3, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.itype.", "BadIType4.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test IType can not have properties defined in protos block.")
	public void testBadIType5() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.ITypeWithInstanceProperty, 3, 0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.itype.", "BadIType5.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test //@Category({P1,FAST,UNIT})
	//@Description("Test IType methods/properties can not any other access modifier except public.")
	public void testBadIType6() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.ITypeAllowsOnlyPublicModifier, 4,  0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.ITypeAllowsOnlyPublicModifier, 8,  0));
		actualProblems = getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.rt.itype.", "BadIType6.js", this.getClass());
		assertProblemEquals(expectProblems, actualProblems);
	}
}
