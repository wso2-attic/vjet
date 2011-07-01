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
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;

public class LocalVarAsAttributedTypeTest extends VjoValidationBaseTester{
	
	List<VjoSemanticProblem> actualProblems = null;
	
	@Test
	public void testLocalVarByAttributedType() throws Exception {
		expectProblems.clear();
		actualProblems 
			= getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.attributed.", "LocalVarAsAttributedType.js", this.getClass());
		
		assertProblemEquals(expectProblems, actualProblems);
	}
	
	@Test
	public void testLocalVarByAttributedTypeErrorCases() throws Exception {
		expectProblems.clear();
		//simple import case
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 22, 0));
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 23, 0));
		expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 24, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 25, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 26, 0));
		
		//inactive import case
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 33, 0));
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 34, 0));
		expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 35, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 36, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 37, 0));
		
		//none-exist and invisible case
		expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 41, 0));
		expectProblems.add(createNewProblem(FieldProbIds.NotVisibleField, 43, 0));
//		expectProblems.add(createNewProblem(FieldProbIds.NonStaticAccessToStaticField, 45, 0));
		
		//attributed type, function type + array type case
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 74, 0));
		expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 75, 0));
		expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 76, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 77, 0));
		expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 78, 0));
		
		//none-exist and invisible case
		expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 81, 0));
		expectProblems.add(createNewProblem(FieldProbIds.NotVisibleField, 83, 0));
//		expectProblems.add(createNewProblem(FieldProbIds.NonStaticAccessToStaticField, 85, 0));
		
		//should differ from other undefined field as it's due to vj$ usage
		expectProblems.add(createNewProblem(TypeProbIds.AttributorTypeUsingVjRuntime, 89, 0));
		
		expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownMissingImport, 92, 0));
		expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 95, 0));
		
		actualProblems 
			= getVjoSemanticProblem("org.ebayopensource.dsf.jst.validation.vjo.attributed.", "LocalVarAsAttributedTypeError.js", this.getClass());
		
		assertProblemEquals(expectProblems, actualProblems);
	}
}
