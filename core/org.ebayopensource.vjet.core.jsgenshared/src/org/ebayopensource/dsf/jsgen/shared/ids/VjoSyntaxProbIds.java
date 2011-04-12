/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.ids;

import org.ebayopensource.dsf.jst.JstProblemId;

public class VjoSyntaxProbIds {


	// variables
	public static final JstProblemId IncorrectVjoSyntax = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "IncorrectVjoSyntax");

	public static final JstProblemId MissingEndType = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "MissingEndType");

//	public static final JstProblemId MultipleProtos = new JstProblemId(
//			JstProblemCatIds.SYNTAX_RELATED, "MultipleProtos");
//	
//	public static final JstProblemId MultipleProps = new JstProblemId(
//			JstProblemCatIds.SYNTAX_RELATED, "MultipleProps");
//	
//	public static final JstProblemId MultipleInherits = new JstProblemId(
//			JstProblemCatIds.SYNTAX_RELATED, "MultipleInherits");
//	
//	public static final JstProblemId MultipleValues = new JstProblemId(
//			JstProblemCatIds.SYNTAX_RELATED, "MultipleValues");
//	
//	public static final JstProblemId MultipleDefs = new JstProblemId(
//			JstProblemCatIds.SYNTAX_RELATED, "MultipleDefs");
	
	public static final JstProblemId NameSpaceCollision = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "NameSpaceCollision");
	
	public static final JstProblemId ITypeWithInstanceProperty = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "ITypeWithInstanceProperty");
	
	public static final JstProblemId OTypeWithNoneObjLiteralProperty = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "OTypeWithNoneObjLiteralProperty");
	
	public static final JstProblemId OTypeWithInnerTypes = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "OTypeWithInnerTypes");
	
	public static final JstProblemId OTypeAsInnerType = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "OTypeAsInnerType");
	
	public static final JstProblemId MTypeWithInnerTypes = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "MTypeWithInnerTypes");
	
	public static final JstProblemId MTypeAsInnerType = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "MTypeAsInnerType");
	
	public static final JstProblemId ITypeAllowsOnlyPublicModifier = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "ITypeAllowsOnlyPublicModifier");
	
	public static final JstProblemId TypeUnknownMissingImport = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "TypeUnknownMissingImport");
	
	public static final JstProblemId TypeUnknownNotInTypeSpace = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "TypeUnknownNotInTypeSpace");
	
	public static final JstProblemId RedundantImport = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "RedundantImport");
	
	public static final JstProblemId InvalidIdentifier = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "InvalidIdentifier");
	
	public static final JstProblemId TypeHasIllegalToken = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "TypeHasIllegalToken");
	
	public static final JstProblemId MTypeShouldOnlyBeMixined = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "MTypeShouldOnlyBeMixined");
	
//	public static final JstProblemId InvalidLabelDecoration = new JstProblemId(
//			JstProblemCatIds.SYNTAX_RELATED, "InvalidLabelDecoration");
	
	public static final JstProblemId BreakNoneExistLabel = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "BreakNoneExistLabel");
	
	public static final JstProblemId ContinueNoneExistLabel = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "ContinueNoneExistLabel");
	
	public static final JstProblemId NestedWithDiscouraged = new JstProblemId(
			JstProblemCatIds.SYNTAX_RELATED, "NestedWithDiscouraged");
}
