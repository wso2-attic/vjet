/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.globals.extension;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;

public class GlobalsExtensionTest extends VjoValidationBaseTester{
	
	List<VjoSemanticProblem> actualProblems = null;
	
	@Test
	public void testGlobalsExtensionTest() throws Exception {
		expectProblems.clear();
		expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 9, 0));
		
		actualProblems 
			= getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.globals.extension.", "GlobalsExtensionTest.js", this.getClass());
		
		assertProblemEquals(expectProblems, actualProblems);
	}
}
