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
public class VjoCcOwnerTypeProposalAdvisorTest extends VjoCcBaseTest{

	public VjoCcOwnerTypeProposalAdvisor advisor = new VjoCcOwnerTypeProposalAdvisor();
	
	private IJstType getOuterActingType() {
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,
					"ownertype.COuter");
		IJstType type = getJstType(typeName);
		return type;
	}
	
	private IJstType getInnerActingType() {
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,
					"ownertype.COuter.CInner1");
		IJstType type = getJstType(typeName);
		return type;
	}
	
	private IJstType getInnerInnerActingType() {
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,
					"ownertype.COuter.CInner2.CInnerInner2");
		IJstType type = getJstType(typeName);
		return type;
	}
	
	@Test
	public void testOwnerTypeAdvise() {
		VjoCcCtx ctx = getEmptyContext();
		String outerType = "COuter";
		ctx.setActingToken("");
		ctx.setActingType(getOuterActingType());
		advisor.advise(ctx);
		List<IVjoCcProposalData> data = ctx.getReporter().getProposalData();
		Assert.assertTrue(outerType+" should have been proposed in " + data,
				data.size() == 1);
		
		data = null;
		String innerType = "CInner1";
		ctx = getEmptyContext();
		ctx.setActingToken("");
		ctx.setActingType(getInnerActingType());
		advisor.advise(ctx);
		data = ctx.getReporter().getProposalData();
		Assert.assertTrue(outerType+" and "+ innerType +" should have been proposed in " + data,
				data.size() == 2);
		
		data = null;
		String innerInnerType = "CInnerInner2";
		ctx = getEmptyContext();
		ctx.setActingToken("");
		ctx.setActingType(getInnerInnerActingType());
		advisor.advise(ctx);
		data = ctx.getReporter().getProposalData();
		Assert.assertTrue(outerType+", "+ innerType +" and "+ innerInnerType +
				" should have been proposed in " + data,
				data.size() == 3);
		
	}
	
	@Test
	public void testInnerTypeAdvise() {
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("CInner");
		ctx.setActingType(getOuterActingType());
		advisor.advise(ctx);
		List<IVjoCcProposalData> data = ctx.getReporter().getProposalData();
		Assert.assertTrue("Inner types CInner1 and CInner2 should have not been proposed in " + data,
				data.size() == 3);
		
		data = null;
		ctx = getEmptyContext();
		ctx.setActingToken("CInner");
		ctx.setActingType(getInnerActingType());
		advisor.advise(ctx);
		data = ctx.getReporter().getProposalData();
		Assert.assertTrue("Inner types CInner1 and CInner2 should have been proposed in " + data,
				data.size() == 3);
		
		data = null;
		ctx = getEmptyContext();
		ctx.setActingToken("CInner");
		ctx.setActingType(getInnerInnerActingType());
		advisor.advise(ctx);
		data = ctx.getReporter().getProposalData();
		Assert.assertTrue("Inner types CInner1 and CInner2 should have been proposed in " + data,
				data.size() == 3);
	}
	
	@Test
	public void testInnerInnerTypeAdvise() {
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("CInnerInner");
		ctx.setActingType(getOuterActingType());
		advisor.advise(ctx);
		List<IVjoCcProposalData> data = ctx.getReporter().getProposalData();
		Assert.assertTrue("Inner type CInnerInner2 should have not been proposed in " + data,
				data.size() == 1);
		
		data = null;
		ctx = getEmptyContext();
		ctx.setActingToken("CInnerInner");
		ctx.setActingType(getInnerActingType());
		advisor.advise(ctx);
		data = ctx.getReporter().getProposalData();
		Assert.assertTrue("Inner type CInnerInner2 should have not been proposed in " + data,
				data.size() == 1);
		
		data = null;
		ctx = getEmptyContext();
		ctx.setActingToken("CInnerInner");
		ctx.setActingType(getInnerInnerActingType());
		advisor.advise(ctx);
		data = ctx.getReporter().getProposalData();
		Assert.assertTrue("Inner type CInnerInner2 should have been proposed in " + data,
				data.size() == 1);
	}
}
