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
public class VjoCcInterfaceProposalAdvisorTest extends VjoCcBaseTest {
	private VjoCcInterfaceProposalAdvisor advisor = new VjoCcInterfaceProposalAdvisor();
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
	public void testBasic() {
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		ctx.setActingType(getActingType());
		advisor.advise(ctx);
		List<IJstType> interfaces = getAllInterfacesFromTS();
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert.assertEquals(data.getAdvisor(),
					VjoCcInterfaceProposalAdvisor.ID);
			Object obj = data.getData();
			Assert.assertTrue(obj instanceof IJstType);
			IJstType type = (IJstType) obj;
			Assert.assertTrue(type.isInterface());
			Assert.assertTrue(interfaces.contains(type));
		}
	}

	@Test
	public void testCtypeExclussion() {
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		ctx.setActingType(getActingType());
		advisor.advise(ctx);
		List<IJstType> ctypes = getAllCTypesFromTS();
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert.assertEquals(data.getAdvisor(),
					VjoCcInterfaceProposalAdvisor.ID);
			Object obj = data.getData();
			Assert.assertTrue(obj instanceof IJstType);
			IJstType type = (IJstType) obj;
			Assert.assertTrue(type.isInterface());
			Assert.assertFalse(ctypes.contains(type));
		}
	}

	@Test
	public void testTypeProposalAdvisor() {
		VjoCcCtx ctx = getEmptyContext();
		List<IJstType> interfaces = getAllInterfacesFromTS();
		for (IJstType t : interfaces) {
			List<String> strComb = VjoCcTypeProposalAdvisorTest
					.getStringCombinationsForType(t);
			for (String s : strComb) {
				List<IJstType> matchTypes = findMatchingTypes(s, interfaces);
				ctx.getReporter().getProposalData().clear();
				ctx.setActingToken(s);
				ctx.setActingType(getActingType());
				advisor.advise(ctx);
				List<IVjoCcProposalData> datas = ctx.getReporter()
						.getProposalData();
				Iterator<IVjoCcProposalData> it = datas.iterator();
				while (it.hasNext()) {
					IVjoCcProposalData data = it.next();
					Assert.assertEquals(data.getAdvisor(),
							VjoCcInterfaceProposalAdvisor.ID);
					Object obj = data.getData();
					Assert.assertTrue(obj instanceof IJstType);
					IJstType type = (IJstType) obj;
					Assert.assertTrue(type.isInterface());
					Assert.assertTrue("Type = " + type.getSimpleName()
							+ " should not proposed " + "for token = " + s,
							matchTypes.contains(type) || !data.isAccurateMatch());
					Assert.assertTrue(
							"Type does not start with token. Type is = "
									+ type.getSimpleName() + " and token = "
									+ s, type.getSimpleName().startsWith(s) || !data.isAccurateMatch());
				}
			}
		}
	}

	public List<IJstType> findMatchingTypes(String str, List<IJstType> types) {
		List<IJstType> typeList = new ArrayList<IJstType>();
		Iterator<IJstType> iter = types.iterator();
		while (iter.hasNext()) {
			IJstType jstType = iter.next();
			if (jstType.isInterface() && jstType.getSimpleName().startsWith(str)
					&& !jstType.getSimpleName().equalsIgnoreCase("global")) {
				typeList.add(jstType);
			}
		}
		return typeList;
	}

	private List<IJstType> getAllInterfacesFromTS() {
		List<IJstType> list = new ArrayList<IJstType>();
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		ctx.setActingType(getActingType());
		new VjoCcTypeProposalAdvisor().advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			Object obj = it.next().getData();
			IJstType type = (IJstType) obj;
			if (type.isInterface()) {
				list.add(type);
			}
		}
		return list;
	}

	private List<IJstType> getAllCTypesFromTS() {
		List<IJstType> list = new ArrayList<IJstType>();
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		ctx.setActingType(getActingType());
		new VjoCcTypeProposalAdvisor().advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			Object obj = it.next().getData();
			IJstType type = (IJstType) obj;
			if (!type.isInterface()) {
				list.add(type);
			}
		}
		return list;
	}
}
