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
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcTypeNameAdvisorTest extends VjoCcBaseTest {
	private VjoCcTypeNameAdvisor advisor = new VjoCcTypeNameAdvisor();
	
	@Test
	public void testATypeValid(){
		testTypeNameProposals("nonStaticPropAdvisor.ProtosAdvisorAType");
	}
	
	@Test
	public void testMTypeValid(){
		testTypeNameProposals("nonStaticPropAdvisor.ProtosAdvisorMType");
	}
	
	@Test
	public void testITypeValid(){
		testTypeNameProposals("nonStaticPropAdvisor.ProtosAdvisorIType");
	}
	
	@Test
	public void testCTypeValid(){
		testTypeNameProposals("staticPropAdvisor.StaticPropAdvisorTest1");
	}
	
	@Test
	public void testETypeValid(){
		testTypeNameProposals("parent.ETypeTest");
	}
	
	@Test
	public void testATypeInValid(){
		testTypeNameProposals("nonStaticPropAdvisor.ProtosAdvisorAType");
	}
	
	@Test
	public void testMTypeInValid(){
		testTypeNameProposals("nonStaticPropAdvisor.ProtosAdvisorMType");
	}
	
	@Test
	public void testITypeInValid(){
		testTypeNameProposals("nonStaticPropAdvisor.ProtosAdvisorIType");
	}
	
	@Test
	public void testCTypeInValid(){
		testTypeNameProposals("staticPropAdvisor.StaticPropAdvisorTest1");
	}
	
	@Test
	public void testETypeInValid(){
		testTypeNameProposals("parent.ETypeTest");
	}
	
	private void testTypeNameProposals(String strType){
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME, strType);
		VjoCcCtx ctx = getEmptyContext(typeName);
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert.assertEquals(data.getAdvisor(), VjoCcTypeNameAdvisor.ID);
			Object obj = data.getData();
			Assert.assertTrue(obj instanceof String);
			Assert.assertTrue(((String)obj).equals(strType));
		}
	}
}
