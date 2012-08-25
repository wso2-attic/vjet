/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;




import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Assert;
import org.junit.Test;


import org.ebayopensource.dsf.common.resource.ResourceUtil;

//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcEnumElementAdvisorTest extends VjoCcBaseTest {
	public VjoCcEnumElementAdvisor advisor = new VjoCcEnumElementAdvisor();
	
	@Test
	public void testEnumProposalsForCType() {
		testEnumProposals("ETypeJs.ETypeTest", "ETypeJs.ProtosAdvisorTest");
	}
	
	@Test
	public void testEnumProposalsForIType() {
		testEnumProposals("ETypeJs.ETypeTest", "ETypeJs.ProtosAdvisorIType");
	}
	
	@Test
	public void testEnumProposalsForMType() {
		testEnumProposals("ETypeJs.ETypeTest", "ETypeJs.ProtosAdvisorMType");
	}
	
	@Test
	public void testEnumProposalsForAType() {
		testEnumProposals("ETypeJs.ETypeTest", "ETypeJs.ProtosAdvisorAType");
	}
	
	@Test
	public void testCharByChar(){
		TypeName calledName = new TypeName(CodeCompletionUtil.GROUP_NAME, 
				"ETypeJs.ETypeTest");
		IJstType calledType = getJstType(calledName);
		Assert.assertNotNull(calledType);
		TypeName actingName = new TypeName(CodeCompletionUtil.GROUP_NAME, 
				"ETypeJs.ProtosAdvisorTest");
		IJstType actingType = getJstType(actingName);
		Assert.assertNotNull(actingType);
		
		List<IJstProperty> properties = calledType.getEnumValues();
		Assert.assertTrue("Enum values not found", properties.size() > 0);
		List<String> strComb = new ArrayList<String>();
		for (IJstProperty prop : properties) {
			getStringCombination(strComb, prop.getName().getName());
		}
		for (String token : strComb){
			List<IJstProperty> matcheList = 
				getAllMatchingProps(properties, token);
			VjoCcCtx ctx = getEmptyContext();
			ctx.setCalledType(calledType);
			ctx.setActingType(actingType);
			ctx.setActingToken(token);
			advisor.advise(ctx);
			List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
			Assert.assertTrue("Proposals were not provided for token : " 
					+ token, datas.size() > 0);
			Iterator<IVjoCcProposalData> it = datas.iterator();
			while (it.hasNext()) {
				IVjoCcProposalData data = it.next();
				Assert.assertEquals(data.getAdvisor(), VjoCcEnumElementAdvisor.ID);
				Object obj = data.getData();
				Assert.assertTrue(obj instanceof IJstProperty);
				IJstProperty prop = (IJstProperty)obj;
				Assert.assertTrue(matcheList.contains(prop));
			}
			ctx.getReporter().getProposalData().clear();
		}
	}
	
	private void testEnumProposals(String calledJs, String actingJs){
		VjoCcCtx ctx = getEmptyContext();
		TypeName calledName = new TypeName(CodeCompletionUtil.GROUP_NAME, calledJs);
		IJstType calledType = getJstType(calledName);
		Assert.assertNotNull(calledType);
		ctx.setCalledType(calledType);
		TypeName actingName = new TypeName(CodeCompletionUtil.GROUP_NAME, actingJs);
		IJstType actingType = getJstType(actingName);
		Assert.assertNotNull(actingType);
		ctx.setActingType(actingType);
		advisor.advise(ctx);
		
		List<IJstProperty> properties = calledType.getEnumValues();
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Assert.assertTrue("Proposals were not provided", datas.size() > 0);
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert.assertEquals(data.getAdvisor(), VjoCcEnumElementAdvisor.ID);
			Object obj = data.getData();
			Assert.assertTrue(obj instanceof IJstProperty);
			IJstProperty prop = (IJstProperty)obj;
			Assert.assertTrue(properties.contains(prop));
		}
	}
	
	private void getStringCombination(List<String>list, String str){
		String name = "";
		for (char c : str.toCharArray()){
			name = name + c;
			if (!list.contains(name))
				list.add(name);
		}
	}
	
	private List<IJstProperty> getAllMatchingProps(List<IJstProperty> allProps,
			String str){
		List<IJstProperty> list = new ArrayList<IJstProperty>();
		for(IJstProperty p : allProps){
			if (p.getName().getName().startsWith(str))
				list.add(p);
		}
		return list;
	}
	
	public static void main(String [] args) throws IOException{
		URL url = ResourceUtil.getResource(VjoCcEnumElementAdvisorTest.class, "ProtosAdvisorTest1.js");
		IScriptUnit unit = new VjoParser().parse("test", url);
		IJstType type = unit.getType();
		System.out.println(type.getName());
	}
}
