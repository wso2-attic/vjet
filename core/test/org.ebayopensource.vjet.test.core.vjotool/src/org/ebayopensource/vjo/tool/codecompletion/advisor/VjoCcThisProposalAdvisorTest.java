/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;




import java.util.List;

import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.junit.Assert;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcThisProposalAdvisorTest extends VjoCcBaseTest {
	public VjoCcThisProposalAdvisor advisor = new VjoCcThisProposalAdvisor();
	
	@Test
	public void testBasic(){
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
//		Assert.assertTrue(datas.size() == 3);
		IVjoCcProposalData data = datas.get(0);
		Assert.assertEquals(data.getAdvisor(), VjoCcThisProposalAdvisor.ID);
		Object obj = data.getData();
		Assert.assertTrue(data.getData().equals("this"));
	}
	
	@Test
	public void testCharByChar(){
		String [] strComb = {"t", "th", "thi", "this", "T", "tH", "thI", "thiS"};
		for (String str : strComb){
			VjoCcCtx ctx = getEmptyContext();
			ctx.setActingToken(str);
			advisor.advise(ctx);
			List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
			Assert.assertTrue(datas.size() == 1);
			IVjoCcProposalData data = datas.get(0);
			Assert.assertEquals(data.getAdvisor(), VjoCcThisProposalAdvisor.ID);
			Object obj = data.getData();
			Assert.assertTrue(data.getData().equals("this"));
		}
	}
	
	@Test
	public void testInvalidCase(){
		String [] strComb = {"H", "I", " t", " "};
		for (String str : strComb){
			VjoCcCtx ctx = getEmptyContext();
			ctx.setActingToken(str);
			advisor.advise(ctx);
			List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
			Assert.assertTrue(datas.size() == 0);
		}
	}
}
