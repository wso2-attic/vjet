/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation;





import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@ModuleInfo(value = "DsfPrebuild", subModuleId = "JsToJava")
public class VjoParamTypeAsFunctionTest extends VjoValidationBaseTester {

	List<VjoSemanticProblem> actualProblems = null;

	@Test
	//@Category( { P2, FAST, UNIT })
	//@Description("Test local method declaration and invocation")
	public void testParamTypeAsFunction() throws Exception {
		expectProblems.clear();
		expectProblems
		 .add(createNewProblem(MethodProbIds.ParameterMismatch, 15, 0));
		expectProblems
		 .add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 15, 0));
		expectProblems
		 .add(createNewProblem(MethodProbIds.ParameterMismatch, 24, 0));
		expectProblems
		 .add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 24, 0));
		
		
		actualProblems = getVjoSemanticProblem(
				"org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.",
				"ParamTypeAsFunction.js", this.getClass());
		
		assertProblemEquals(expectProblems, actualProblems);
	}
}
