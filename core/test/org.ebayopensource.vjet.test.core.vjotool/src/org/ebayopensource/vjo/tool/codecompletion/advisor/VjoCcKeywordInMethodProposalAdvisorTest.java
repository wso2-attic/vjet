/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.IVjoKeywordCompletionData;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.VjoKeywordFactory;
import org.junit.Assert;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcKeywordInMethodProposalAdvisorTest extends VjoCcBaseTest {
	public VjoCcKeywordInMethodProposalAdvisor advisor = new VjoCcKeywordInMethodProposalAdvisor();

	public List<IVjoKeywordCompletionData> keywords = VjoKeywordFactory
			.getMethodKeyworkds();

	@Test
	public void testKeywordProposals1() {
		testProposals("F");
	}

	@Test
	public void testKeywordProposals2() {
		testProposals("f");
	}

	@Test
	public void testKeywordProposals3() {
		testProposals("I");
	}

	@Test
	public void testKeywordProposals4() {
		testProposals("i");
	}

	@Test
	public void testKeywordProposals5() {
		testProposals("");
	}
	
	@Test
	public void testKeywordProposals6() {
		String[] tokens = { "AA", "ZZ", "1", "0", "A1" };
		for (String s : tokens) {
			testProposals(s);
		}
	}
	
	@Test
	public void testKeywordProposals7() {
		testProposals("n");
	}

	private void testProposals(String token) {
		List<IVjoKeywordCompletionData> keyList = getMatchKeywords(token);
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken(token);
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Assert.assertTrue("Proposals are not as expected",
				keyList.size() == datas.size());
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			// Assert.assertEquals(data.getAdvisor(),
			// VjoCcKeywordInMethodProposalAdvisor.ID);
			Assert.assertTrue(keyList.contains(data));
			Object obj = data.getData();
			Assert.assertTrue(obj instanceof String);
			String keyData = (String) obj;
			Assert.assertTrue(keyData.startsWith(token)
					|| keyData.startsWith(token.toLowerCase()));
		}
	}

	private List<IVjoKeywordCompletionData> getMatchKeywords(String str) {
		List<IVjoKeywordCompletionData> list = new ArrayList<IVjoKeywordCompletionData>();
		for (IVjoKeywordCompletionData key : keywords) {
			if (key.getName().startsWith(str)
					|| key.getName().startsWith(str.toLowerCase()))
				list.add(key);
		}
		return list;
	}
}
