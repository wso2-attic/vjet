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
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Assert;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcConstructorGenProposalAdvisorTest extends VjoCcBaseTest {
	private VjoCcConstructorGenProposalAdvisor advisor = 
		new VjoCcConstructorGenProposalAdvisor();
	
	@Test
	public void testconstrAdviseBasic() {
		testConstructorProposals(CodeCompletionUtil.GROUP_NAME, 
				"constructorAdvisor.ParentType", true);
	}
	
	@Test
	public void testconstrAdviseCtype() {
		testConstructorProposals(CodeCompletionUtil.GROUP_NAME, 
				"constructorAdvisor.StaticPropAdvisorTest", false);
	}
	
	@Test
	public void testconstrAdviseMType() {
		testConstructorProposals(CodeCompletionUtil.GROUP_NAME, 
				"constructorAdvisor.StaticPropAdvisorMType", false);
	}
	
	@Test
	public void testconstrAdviseAType() {
		testConstructorProposals(CodeCompletionUtil.GROUP_NAME, 
				"constructorAdvisor.StaticPropAdvisorAType", false);
	}
	
	@Test
	public void testconstrAdviseCTypeComplex() {
		testConstructorProposals(CodeCompletionUtil.GROUP_NAME, 
				"constructorAdvisor.StaticPropAdvisorTest1", false);
	}
	
	@Test
	public void testconstrAdviseCTypeComplex1() {
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME, 
				"constructorAdvisor.ParentType");
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		
		List<String> strComb = getStringCombination("constructs");
		for (String str : strComb){
			VjoCcCtx ctx = getEmptyContext();
			ctx.setActingToken(str);
			ctx.setActingType(actingType);
			advisor.advise(ctx);
			List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
			Assert.assertTrue("Advisor should have returned just one " +
					"constructor for str = " + str, datas.size() == 1);
			
			IVjoCcProposalData propData = datas.get(0);
			Assert.assertEquals(propData.getAdvisor(), 
					VjoCcConstructorGenProposalAdvisor.ID);
			Object obj = propData.getData();
			Assert.assertTrue("Proposal is not of IJstType", 
					obj instanceof IJstType);
			IJstType t = (IJstType) obj;
			IJstMethod method = t.getConstructor();
			Assert.assertTrue("Constructor should not be there in " +
					"proposed type", method == null || CodeCompletionUtils.isSynthesizedElement(method));
		}
	}
	
	private void testConstructorProposals(String group, 
			String js, boolean returnType){
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		TypeName typeName = new TypeName(group, js);
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		ctx.setActingType(actingType);
		
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		if (returnType){
			Assert.assertTrue("Advisor should have returned just one constructor",
					datas.size() == 1);
			IVjoCcProposalData propData = datas.get(0);
			Assert.assertEquals(propData.getAdvisor(), 
					VjoCcConstructorGenProposalAdvisor.ID);
			Object obj = propData.getData();
			Assert.assertTrue("Proposal is not of IJstType", 
					obj instanceof IJstType);
			IJstType t = (IJstType) obj;
			IJstMethod method = t.getConstructor();
			Assert.assertTrue("Constructor should not be there in " +
					"proposed type", method == null || CodeCompletionUtils.isSynthesizedElement(method));
		} else {
			Assert.assertTrue("Advisor should not have returned any " +
					"constructor", datas.size() == 0);
		}
	}
	
	private List<String> getStringCombination(String str){
		List<String> list = new ArrayList<String>();
		String name = "";
		for (char c : str.toCharArray()){
			name = name + c;
			list.add(name);
		}
		return list;
	}
}
