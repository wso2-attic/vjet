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
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtxForTest;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Test;



/**
 * Test if the VjoCcVariableProposalAdvisor can calculate out the correct proposal data
 * 
 *
 */
//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcVariableProposalAdvisorTest extends VjoCcBaseTest {
	private VjoCcVariableProposalAdvisor advisor = new VjoCcVariableProposalAdvisor();

	@Test
	public void testAdviseWithEmptyToken() {

		VjoCcCtx ctx = createBaseVjoCcCtx();
		advisor.advise(ctx);
		List<IVjoCcProposalData> result = ctx.getReporter().getProposalData();
		Assert.assertEquals(2, result.size());
		checkProposalDataType(result);
	}

	@Test
	public void testAdviseWithTokenV() {

		VjoCcCtx ctx = createBaseVjoCcCtx();
		ctx.setActingToken("v");
		advisor.advise(ctx);
		List<IVjoCcProposalData> result = ctx.getReporter().getProposalData();
		Assert.assertEquals(1, result.size());
		checkProposalDataType(result);
	}

	@Test
	public void testAdviseWithTokenXx() {
		VjoCcCtx ctx = createBaseVjoCcCtx();
		ctx.setActingToken("xx");
		advisor.advise(ctx);
		List<IVjoCcProposalData> result = ctx.getReporter().getProposalData();
		Assert.assertEquals(0, result.size());
		checkProposalDataType(result);
	}
	
	@Test
	public void testMultiVarPerLine() {
		VjoCcCtx ctx = createBaseVjoCcCtx("method2");
		advisor.advise(ctx);
		List<IVjoCcProposalData> result = ctx.getReporter().getProposalData();
		Assert.assertEquals(4, result.size());
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
	
	private VjoCcCtx createBaseVjoCcCtx() {
		return createBaseVjoCcCtx("main");
	}

	private void checkProposalDataType(List<IVjoCcProposalData> proposals) {
		Iterator<IVjoCcProposalData> it = proposals.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData proposal = it.next();
			Object data = proposal.getData();
			Assert.assertTrue("proposal should be JstIdentifier",
					data instanceof JstIdentifier);
		}
	}
}
