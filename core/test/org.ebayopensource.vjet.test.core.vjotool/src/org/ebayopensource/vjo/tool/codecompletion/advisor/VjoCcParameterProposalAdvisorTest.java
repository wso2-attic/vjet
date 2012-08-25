/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;




import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtxForTest;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Test;



/**
 * Test if the VjoCcParameterProposalAdvisor can calculate out the correct
 * proposal data
 * 
 * 
 * 
 */
//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcParameterProposalAdvisorTest extends VjoCcBaseTest {
	public VjoCcParameterProposalAdvisor advisor = new VjoCcParameterProposalAdvisor();

	@Test
	public void testAdviseWithEmptyToken() {

		VjoCcCtx ctx = createBaseVjoCcCtx();
		advisor.advise(ctx);
		List<IVjoCcProposalData> result = ctx.getReporter().getProposalData();
		Assert.assertEquals(1, result.size());
		checkProposalDataType(result);
	}

	@Test
	public void testAdviseWithTokenS() {

		VjoCcCtx ctx = createBaseVjoCcCtx();
		ctx.setActingToken("s");
		advisor.advise(ctx);
		List<IVjoCcProposalData> result = ctx.getReporter().getProposalData();
		Assert.assertEquals(1, result.size());
		checkProposalDataType(result);
	}

	@Test
	public void testAdviseWithTokenA() {
		VjoCcCtx ctx = createBaseVjoCcCtx();
		ctx.setActingToken("a");
		advisor.advise(ctx);
		List<IVjoCcProposalData> result = ctx.getReporter().getProposalData();
		Assert.assertEquals(0, result.size());
		checkProposalDataType(result);
	}
	/**
	 * Bug 6057
	 * When arg has no name, it should be discarded.
	 */
	@Test
	public void testAdviseOfOverloadedMethod() {
		VjoCcCtx ctx = createBaseVjoCcCtx("staticFunc1");
		ctx.setActingToken("");
		advisor.advise(ctx);
		List<IVjoCcProposalData> result = ctx.getReporter().getProposalData();
		Assert.assertEquals(2, result.size());
		checkProposalDataType(result);
	}
	/**
	 * Bug 5687
	 * When arg has no name, it should be discarded.
	 */
	@Test
	public void testAdviseWhenArgHasnoName() {
		VjoCcCtx ctx = createBaseVjoCcCtx("method1");
		ctx.setActingToken("arr");
		advisor.advise(ctx);
		List<IVjoCcProposalData> result = ctx.getReporter().getProposalData();
		Assert.assertEquals(0, result.size());
		checkProposalDataType(result);
	}
	

	private VjoCcCtx createBaseVjoCcCtx() {
		return createBaseVjoCcCtx("setState");
	}
	private VjoCcCtx createBaseVjoCcCtx(String methodName) {
		String typeName = "variableAdvisor.TestType";
		IJstType type = getJstType(new TypeName(CodeCompletionUtil.GROUP_NAME,
				typeName));
		Assert.assertNotNull("Can not find type" + typeName, type);
		VjoCcCtxForTest ctx = getEmptyContext();
		IJstMethod method = type.getMethod(methodName);
		ctx.setSelectedJstMethod(method);
		ctx.setActingToken("");
		return ctx;
	}

	private void checkProposalDataType(List<IVjoCcProposalData> proposals) {
		Iterator<IVjoCcProposalData> it = proposals.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData proposal = it.next();
			Object data = proposal.getData();
			Assert.assertTrue("proposal should be JstArg",
					data instanceof JstArg);
		}
	}
}
