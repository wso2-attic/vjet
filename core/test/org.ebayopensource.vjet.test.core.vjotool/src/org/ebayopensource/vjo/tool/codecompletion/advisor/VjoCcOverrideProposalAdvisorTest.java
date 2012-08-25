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

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtxForTest;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcOverrideProposalAdvisorTest extends VjoCcBaseTest {
	private VjoCcOverrideProposalAdvisor advisor = 
		new VjoCcOverrideProposalAdvisor();
	
	@Test
	@Ignore("not sure about 14 items vs 18")
	public void testAdviseWithEmptyToken() {

		VjoCcCtx ctx = createBaseVjoCcCtx();
		advisor.advise(ctx);
		List<IVjoCcProposalData> result = ctx.getReporter().getProposalData();
		Assert.assertEquals(14, result.size());
		checkProposalDataType(result);
	}

	@Test
	@Ignore("converted to advisor test")
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

	private VjoCcCtx createBaseVjoCcCtx() {
		String typeName = "overrideAdvisor.SonType";
		IJstType type = getJstType(new TypeName(CodeCompletionUtil.GROUP_NAME,
				typeName));
		Assert.assertNotNull("Can not find type" + typeName, type);
		VjoCcCtxForTest ctx = getEmptyContext();
		ctx.setActingType(type);
		ctx.setActingToken("");
		return ctx;
	}

	private void checkProposalDataType(List<IVjoCcProposalData> proposals) {
		Iterator<IVjoCcProposalData> it = proposals.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData proposal = it.next();
			Object data = proposal.getData();
			Assert.assertTrue("proposal should be IJstMethod",
					data instanceof IJstMethod);
		}
	}
}
