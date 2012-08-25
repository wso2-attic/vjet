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
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.ebayopensource.vjo.tool.codecompletion.proposaldata.VjoCcAliasProposalData;
import org.junit.Assert;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcAliasProposalAdvisorTest extends VjoCcBaseTest {
	public VjoCcAliasProposalAdvisor advisor = new VjoCcAliasProposalAdvisor();

	@Test
	public void testOneAlias() {
		testAliasProposals(CodeCompletionUtil.GROUP_NAME,
				"aliasProposal.AliasTest2");
	}

	@Test
	public void testMultipleAlias() {
		testAliasProposals(CodeCompletionUtil.GROUP_NAME,
				"aliasProposal.AliasTest1");
	}

	@Test
	public void testNeedsWitoutAlias() {
		testAliasProposals(CodeCompletionUtil.GROUP_NAME,
				"aliasProposal.AliasTest3");
	}

	@Test
	public void testWithoutNeedsAlias() {
		testAliasProposals(CodeCompletionUtil.GROUP_NAME,
				"aliasProposal.AliasTest4");
	}

	@Test
	public void testCharByChar() {
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,
				"aliasProposal.AliasTest1");
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		Map<String, ? extends IJstType> needsMap = actingType.getImportsMap();
		List<String> strComb = getAllStringComb(needsMap);
		for (String str : strComb) {
			List<IJstType> matches = getAllMatches(needsMap, str);
			VjoCcCtx ctx = getEmptyContext();
			ctx.setActingToken(str);
			ctx.setActingType(actingType);
			advisor.advise(ctx);

			List<IVjoCcProposalData> datas = ctx.getReporter()
					.getProposalData();
			Assert.assertTrue(matches.size() == datas.size());
			Iterator<IVjoCcProposalData> it = datas.iterator();
			while (it.hasNext()) {
				IVjoCcProposalData data = it.next();
				Assert.assertEquals(data.getAdvisor(),
						VjoCcAliasProposalAdvisor.ID);
				Assert.assertTrue(data instanceof VjoCcAliasProposalData);

				VjoCcAliasProposalData prop = (VjoCcAliasProposalData) data;
				Assert.assertTrue(matches.contains(prop.getType()));
				Assert.assertTrue(prop.getAlias().startsWith(str));
				Assert.assertTrue(needsMap.containsKey(prop.getAlias()));
				Assert.assertTrue(needsMap.get(prop.getAlias()) == prop
						.getType());
			}
		}
	}

	private void testAliasProposals(String group, String js) {
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		TypeName typeName = new TypeName(group, js);
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		ctx.setActingType(actingType);

		advisor.advise(ctx);
		Map<String, ? extends IJstType> needsMap = actingType.getImportsMap();
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Assert.assertTrue(getNeedsItemSize(needsMap) == datas.size());
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert
					.assertEquals(data.getAdvisor(),
							VjoCcAliasProposalAdvisor.ID);
			Assert.assertTrue(data instanceof VjoCcAliasProposalData);

			VjoCcAliasProposalData prop = (VjoCcAliasProposalData) data;
			Assert.assertTrue(needsMap.containsKey(prop.getAlias()));
			Assert.assertTrue(needsMap.get(prop.getAlias()) == prop.getType());
		}
	}

	private int getNeedsItemSize(Map<String, ? extends IJstType> map) {
		int size = map.size();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String alias = it.next();
			IJstType type = map.get(alias);
			if (alias.equals(type.getSimpleName())
					|| StringUtils.isBlankOrEmpty(alias)) {
				size--;
			}
		}
		return size;
	}

	private List<String> getAllStringComb(Map<String, ? extends IJstType> map) {
		List<String> list = new ArrayList<String>();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			getStringCombination(list, iter.next());
		}
		return list;
	}

	private void getStringCombination(List<String> list, String str) {
		String name = "";
		for (char c : str.toCharArray()) {
			name = name + c;
			if (!list.contains(name))
				list.add(name);
		}
	}

	private List<IJstType> getAllMatches(Map<String, ? extends IJstType> map,
			String str) {
		List<IJstType> list = new ArrayList<IJstType>();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String alias = iter.next();
			IJstType type = map.get(alias);
			if (!StringUtils.isBlankOrEmpty(alias) && alias.startsWith(str)
					&& type != null && !alias.equals(type.getSimpleName())) {
				list.add(map.get(alias));
			}
		}
		return list;
	}
}
