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

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.ts.ITypeSpace;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Assert;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcCTypeProposalAdvisorTest extends VjoCcBaseTest {
	public VjoCcCTypeProposalAdvisor advisor = new VjoCcCTypeProposalAdvisor();
	private IJstType type = null;

	private IJstType getActingType() {
		if (type == null) {
			TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,
					"nonStaticPropAdvisor.ParentType1");
			type = getJstType(typeName);
		}
		return type;
	}

	@Test
	public void testAdvise() {
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("H");
		ctx.setActingType(getActingType());
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert
					.assertEquals(data.getAdvisor(),
							VjoCcCTypeProposalAdvisor.ID);
			Object obj = data.getData();
			Assert.assertTrue(obj instanceof IJstType);
			IJstType type = (IJstType) obj;
			Assert.assertTrue(type.getSimpleName().startsWith("H"));
			Assert.assertTrue(type.isInterface() == false);
			Assert.assertTrue(type.isMixin() == false);
			Assert.assertTrue(type.isEnum() == false);
		}
	}

	@Test
	public void testGlobalTypesAdvise() {
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("Glo");
		ctx.setActingType(getActingType());
		int no = ctx.getJstTypeSpaceMgr().getTypeSpace().getTypes().size();
		advisor.advise(ctx);
		List<IVjoCcProposalData> data = ctx.getReporter().getProposalData();
		Assert.assertTrue("Global type should have not been proposed. " + data,
				data.size() == 0);
	}

	@Test
	public void testWrongTypesAdvise() {
		String[] tokens = { "AAA", "ZZ", "1", "0", "A21" };
		for (String token : tokens) {
			VjoCcCtx ctx = getEmptyContext();
			ctx.setActingToken(token);
			ctx.setActingType(getActingType());
			advisor.advise(ctx);
			List<IVjoCcProposalData> data = ctx.getReporter().getProposalData();
			Assert.assertTrue("No type should have been proposed for token = "
					+ token + " but got proposals = " + data.size(), data
					.size() == 0);
		}
	}

//	@Test //@Category({SLOW})
//	public void testTypeProposalAdvisor() {
//		VjoCcCtx ctx = getEmptyContext();
//		ctx.setActingType(getActingType());
//		Map<TypeName, IJstType> allTypes = getAllTypes(ctx);
//		Iterator<TypeName> iter = allTypes.keySet().iterator();
//		int count = 0;
//		while (iter.hasNext()) {
//			TypeName typeName = iter.next();
//			IJstType jstType = allTypes.get(typeName);
//			if ( !(jstType.isEnum() ||  jstType.isMetaType() || jstType.isInterface() || jstType
//					.isMixin()) ) {
//				List<String> strComb = getStringCombinationsForType(jstType);
//				for (String str : strComb) {
//					List<IJstType> matchTypes = findMatchingTypes(str, allTypes);
//					// every time, the ctx's report should be cleared
//					ctx.getReporter().getProposalData().clear();
//					checkAdviseForType(typeName, str, ctx, matchTypes);
//					count = count + 1;
//				}
//			}
//			if (count > 1000)
//				break;
//		}
//	}

	public void checkAdviseForType(TypeName typeName, String token, VjoCcCtx ctx,
			List<IJstType> matchTypes) {
		ctx.setActingToken(token);
		advisor.advise(ctx);
		List<IVjoCcProposalData> propDataList = ctx.getReporter()
				.getProposalData();
		Assert.assertTrue("The proposal data list from advisor was null",
				propDataList != null);
		Assert.assertTrue(
				"Type: " + typeName.typeName()+ "-- The proposal data list size is not same as expected. "
						+ "The expected size = " + matchTypes.size()
						+ " while actual size was = " + propDataList.size()
						+ "\n Expected : " + matchTypes + "\n Actual : "
						+ propDataList, propDataList.size() == matchTypes
						.size());

		for (IVjoCcProposalData data : propDataList) {
			Assert
					.assertEquals(data.getAdvisor(),
							VjoCcCTypeProposalAdvisor.ID);
			Object obj = data.getData();
			Assert.assertTrue(obj instanceof IJstType);
			IJstType type = (IJstType) obj;
			Assert.assertTrue("Type does not start with token. Type is = "
					+ type.getSimpleName() + " and token = " + token, type
					.getSimpleName().startsWith(token) || !data.isAccurateMatch());
			Assert.assertTrue("Type = " + type.getSimpleName()
					+ " should not proposed " + "for token = " + token,
					matchTypes.contains(type) || !data.isAccurateMatch());
		}
	}

	public Map<TypeName, IJstType> getAllTypes(VjoCcCtx ctx) {
		JstTypeSpaceMgr tsMrg = ctx.getJstTypeSpaceMgr();
		ITypeSpace<IJstType, IJstNode> tsds = tsMrg.getTypeSpace();
		Map<TypeName, IJstType> types = tsds.getVisibleTypesMap(tsMrg.getTypeSpace().getGroup(ctx.getGroupName()));
		Assert.assertTrue("Type Map returned from TypeSpace is null",
				types != null);
		Assert.assertTrue("Type Map returned from TypeSpace is empty", types
				.size() > 0);
		return types;
	}

	public static List<String> getStringCombinationsForType(IJstType type) {
		Assert.assertTrue(type != null);
		Assert.assertTrue(type.getSimpleName() != null);
		Assert.assertTrue(!(type.getSimpleName().equalsIgnoreCase("")));

		List<String> list = new ArrayList<String>();
		String name = "";
		for (char c : type.getSimpleName().toCharArray()) {
			name = name + c;
			list.add(name);
		}
		return list;
	}

	public List<IJstType> findMatchingTypes(String str,
			Map<TypeName, IJstType> types) {
		List<IJstType> typeList = new ArrayList<IJstType>();
		Iterator<TypeName> iter = types.keySet().iterator();
		while (iter.hasNext()) {
			TypeName typeName = iter.next();
			IJstType jstType = types.get(typeName);
				
			if (!jstType.isInterface() && !jstType.isMetaType() && !jstType.isFakeType() && !jstType.isMixin() && !jstType.isOType()
					&& (jstType.getSimpleName().toLowerCase().startsWith(str.toLowerCase()))) {
				typeList.add(jstType);
			}
		}
		return typeList;
	}
}
