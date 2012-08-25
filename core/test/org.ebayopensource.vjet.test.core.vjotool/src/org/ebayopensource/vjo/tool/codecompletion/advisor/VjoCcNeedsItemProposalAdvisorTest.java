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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Assert;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcNeedsItemProposalAdvisorTest extends VjoCcBaseTest {
	public VjoCcNeedsItemProposalAdvisor advisor = 
		new VjoCcNeedsItemProposalAdvisor();
	
	@Test
	public void testOneNeedsWithAlias(){
		testNeedsProposals(CodeCompletionUtil.GROUP_NAME, 
				"aliasProposal.AliasTest2");
	}
	
	@Test
	public void testMultipleNeedsWithAlias(){
		testNeedsProposals(CodeCompletionUtil.GROUP_NAME, 
				"aliasProposal.AliasTest1");
	}
	
	@Test
	public void testNeedsWitoutAlias(){
		testNeedsProposals(CodeCompletionUtil.GROUP_NAME, 
				"aliasProposal.AliasTest3");
	}
	
	@Test
	public void testWithoutNeeds(){
		testNeedsProposals(CodeCompletionUtil.GROUP_NAME, 
				"aliasProposal.AliasTest4");
	}
	
	@Test
	public void testCharByChar(){
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME, 
				"aliasProposal.AliasTest1");
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		List<? extends IJstType> importList = getNeedsList(actingType);
//		List<? extends IJstType> importList = actingType.getImports();
		List<String> strComb = getAllStringComb(importList);
		for (String str : strComb){
			List<IJstType> matches = getAllMatches(actingType, importList, str);
			VjoCcCtx ctx = getEmptyContext();
			ctx.setActingToken(str);
			ctx.setActingType(actingType);
			advisor.advise(ctx);
			
			List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
			Assert.assertTrue(matches.size() == datas.size());
			Iterator<IVjoCcProposalData> it = datas.iterator();
			while (it.hasNext()) {
				IVjoCcProposalData data = it.next();
				Assert.assertEquals(data.getAdvisor(), VjoCcNeedsItemProposalAdvisor.ID);
				Object obj = data.getData();
				Assert.assertTrue(obj instanceof IJstType);
				Assert.assertTrue(matches.contains(obj) || obj == actingType);
			}
		}
	}

	private void testNeedsProposals(String group, String js) {
		VjoCcCtx ctx = getEmptyContext();
		TypeName typeName = new TypeName(group, js);
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		ctx.setActingToken("");
		ctx.setActingType(actingType);
		advisor.advise(ctx);
		
//		List<? extends IJstType> importList = actingType.getImports();
		List<? extends IJstType> importList = getNeedsList(actingType);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Assert.assertTrue(importList.size()+ 1 == datas.size());
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert.assertEquals(data.getAdvisor(), VjoCcNeedsItemProposalAdvisor.ID);
			Object obj = data.getData();
			Assert.assertTrue(obj instanceof IJstType);
			Assert.assertTrue(importList.contains(obj) || obj == actingType);
		}
	}
	
	private List<String> getAllStringComb(List<? extends IJstType> importList){
		List<String> list = new ArrayList<String>();
		for (IJstType type : importList){
			getStringCombination(list, type.getSimpleName());
		}
		return list;
	}
	
	private void getStringCombination(List<String> list, String str){
		String name = "";
		for (char c : str.toCharArray()){
			name = name + c;
			if (!list.contains(name))
				list.add(name);
		}
	}
	
	public List<? extends IJstType> getNeedsList(IJstType type) {
		List<IJstType> result = new ArrayList<IJstType>();
		List<? extends IJstType> importList = type.getImports();
		result.addAll(importList);
		IJstType etype = type.getExtend();
		if (etype != null) { 
			result.add(etype);
		}
		List<? extends IJstType> satiList = type.getSatisfies();
		result.addAll(satiList);
		return result;
	}
	
	private List<IJstType> getAllMatches(IJstType actingType, List<? extends IJstType> importList, 
			String str){
		List<IJstType> list = new ArrayList<IJstType>();
		for (IJstType type : importList){
			if (type.getSimpleName().startsWith(str))
				list.add(type);
		}
		if (actingType.getSimpleName().startsWith(str)) {
			list.add(actingType);
		}
		return list;
	}
}
