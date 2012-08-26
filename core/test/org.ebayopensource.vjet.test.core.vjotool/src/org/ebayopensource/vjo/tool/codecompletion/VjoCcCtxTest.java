/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Ignore;
import org.junit.Test;



//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcCtxTest extends VjoCcBaseTest {
	@Test
	public void testNeedInsertNeedsExprForInnerType() {
		VjoCcCtxForTest ctx = getEmptyContext();
		IJstType actingType = getJstType(CodeCompletionUtil.GROUP_NAME,
				"innertypes.CTypeWithIType");
		List<? extends IJstType> subTypes = actingType.getEmbededTypes();
		assertTrue("The test type has no sub types", !subTypes.isEmpty());
		IJstType subType = subTypes.get(0);
		ctx.setActingType(actingType);
		assertTrue(
				"Outer type need not add needs expression when referencing its inner type.",
				!ctx.needInsertNeedsExpr(subType));
	}

	@Test
	public void testNeedInsertNeedsExprForNativeType() {

		VjoCcCtxForTest ctx = getEmptyContext();
		IJstType actingType = getJstType(CodeCompletionUtil.GROUP_NAME,
				"innertypes.CTypeWithIType");
		List<? extends IJstType> subTypes = actingType.getEmbededTypes();
		assertTrue("The test type has no sub types", !subTypes.isEmpty());
		IJstType subType = subTypes.get(0);
		ctx.setActingType(subType);
		assertTrue(
				"inner type need not add needs expression when referencing its outer type.",
				!ctx.needInsertNeedsExpr(actingType));
	
	}

	@Test
	public void testNeedInsertNeedsExprForOtherType() {
		VjoCcCtxForTest ctx = getEmptyContext();
		IJstType actingType = getJstType(CodeCompletionUtil.GROUP_NAME,
				"innertypes.CTypeWithIType");
		IJstType insertedType = getJstType(CodeCompletionUtil.GROUP_NAME,
		"innertypes.CTypeWithEType");
		assertNotNull("Can not find 'innertypes.CTypeWithEType'", insertedType);
		ctx.setActingType(actingType);
		assertTrue(
				"Native type should not be added to needs expression",
				ctx.needInsertNeedsExpr(insertedType));
	}
	@Test
	@Ignore
	public void TestGetNeedsPosition() {
		VjoCcCtxForTest ctx = getEmptyContext();
		IJstType actingType = getJstType(CodeCompletionUtil.GROUP_NAME,
		"innertypes.CTypeWithIType");
		assertNotNull("Can not find 'innertypes.CTypeWithIType'", actingType);
		ctx.setActingType(actingType);
		assertTrue(
				"Get wrong inserting point",
				ctx.getNeedsPosition() != -1);
	}
}
