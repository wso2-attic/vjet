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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcFunctionGenProposalAdvisorTest extends VjoCcBaseTest {
	public VjoCcFunctionGenProposalAdvisor advisor = 
		new VjoCcFunctionGenProposalAdvisor();
	
	@Test
	public void testFuncAdviseBasic() {
		testProposals(CodeCompletionUtil.GROUP_NAME, 
				"constructorAdvisor.ParentType", "testFunc");
	}
	
	@Test
	public void testFuncAdviseCtype() {
		testProposals(CodeCompletionUtil.GROUP_NAME, 
				"constructorAdvisor.StaticPropAdvisorTest", "testFunc");
	}
	
	@Test
	public void testFuncAdviseMType() {
		testProposals(CodeCompletionUtil.GROUP_NAME, 
				"constructorAdvisor.StaticPropAdvisorMType", "testFunc");
	}
	
	@Test
	public void testFuncAdviseAType() {
		testProposals(CodeCompletionUtil.GROUP_NAME, 
				"constructorAdvisor.StaticPropAdvisorAType", "testFunc");
	}
	
	@Test
	public void testFuncAdviseCTypeComplex() {
		testProposals(CodeCompletionUtil.GROUP_NAME, 
				"constructorAdvisor.StaticPropAdvisorTest1", "testFunc");
	}
	
	@Test
	public void testFuncAdviseEType() {
		testProposals(CodeCompletionUtil.GROUP_NAME, 
				"ETypeJs.ETypeTest", "testFunc");
	}
	
	private void testProposals(String group, String js, String token){
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken(token);
		TypeName typeName = new TypeName(group, js);
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		ctx.setActingType(actingType);
		
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Iterator<IVjoCcProposalData> it = datas.iterator();
		Assert.assertTrue("Advisor should have returned just one constructor",
				datas.size() == 1);
		IVjoCcProposalData propData = datas.get(0);
		Assert.assertEquals(propData.getAdvisor(), 
				VjoCcFunctionGenProposalAdvisor.ID);
		Object obj = propData.getData();
		Assert.assertTrue("Proposal is not of IJstType", 
				obj instanceof IJstType);
	}
}
