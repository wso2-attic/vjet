/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.attributed;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;

public class GlobalVarAsAttributedTypeTest extends VjoValidationBaseTester{
	
	List<VjoSemanticProblem> actualProblems = null;
	
	@Test
	public void testGlobalVarByAttributedType() throws Exception {
		expectProblems.clear();
		actualProblems 
			= getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.attributed.", "GlobalVarAsAttributedType.js", this.getClass());
		
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test
	public void testGlobalVarByAttributedTypeErrorCases() throws Exception {
		expectProblems.clear();
		//simple import case
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 86, 0));
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 87, 0));
		expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 88, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 89, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 90, 0));
		//inactive import case
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 92, 0));
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 93, 0));
		expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 94, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 95, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 96, 0));
		//none-exist and invisible case
		expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 58, 0));
//by huzhou, temporary bugfix sideeffect TODO expectProblems.add(createNewProblem(FieldProbIds.NotVisibleField, 60, 0));
//		expectProblems.add(createNewProblem(FieldProbIds.NonStaticAccessToStaticField, 62, 0));
		//attributed type, function type + array type case
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 111, 0));
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 112, 0));
		expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 113, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 114, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 115, 0));
		//none-exist and invisible case
		expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 64, 0));
		expectProblems.add(createNewProblem(FieldProbIds.NotVisibleField, 66, 0));
//		expectProblems.add(createNewProblem(FieldProbIds.NonStaticAccessToStaticField, 68, 0));
		//should differ from other undefined field as it's due to vj$ usage
		expectProblems.add(createNewProblem(TypeProbIds.AttributorTypeUsingVjRuntime, 72, 0));
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownMissingImport, 76, 0));
		
		actualProblems 
			= getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.attributed.", "GlobalVarAsAttributedTypeError.js", this.getClass());
		
		assertProblemEquals(expectProblems, actualProblems);
	}
}
