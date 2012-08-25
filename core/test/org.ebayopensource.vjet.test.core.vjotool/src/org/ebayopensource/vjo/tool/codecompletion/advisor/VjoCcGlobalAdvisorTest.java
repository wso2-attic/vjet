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

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.ts.event.type.AddTypeEvent;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.lib.LibManager;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.eclipse.core.runtime.AssertionFailedException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcGlobalAdvisorTest extends VjoCcBaseTest {
	private VjoCcGlobalAdvisor advisor = new VjoCcGlobalAdvisor();
	
	
	@Test @Ignore	//HZ: failing after global support
	public void testGlobalSpace() {
		
		String groupName = "test";
		String methodName = "foo";
		String propName = "prop";
		
		// Create some types
		JstModifiers jstModifiers = new JstModifiers();
		jstModifiers.setFinal().setPublic().setStatic(true);
		
		JstType T1 =  JstFactory.getInstance().createJstType("a.b.c.T1", false);
		T1.addMethod(new JstMethod(methodName,jstModifiers));
		T1.addProperty(new JstProperty(JstCache.getInstance().getType("String"), propName));
		
		// Promote prop, method, types to global level
		getJstTypeSpaceMgr().getTypeSpace().addAllGlobalTypeMembers(new TypeName(LibManager.JS_NATIVE_LIB_NAME,"Global"));
		
		
//		getJstTypeSpaceMgr().getQueryExecutor().findType(
//				new TypeName(JstTypeSpaceMgr.JS_NATIVE_GRP,
//						TypeSpaceMgr.GLOBAL));
		
		validateGlobalType();
		validateGlobalTypeByToken();
		
		getJstTypeSpaceMgr().processEvent(new AddTypeEvent<IJstType>(new TypeName(groupName,"a.b.c.T1"),T1));
		
		getJstTypeSpaceMgr().getTypeSpace().addToGlobalMemberSymbolMap(groupName, methodName, "a.b.c.T1.foo");
		getJstTypeSpaceMgr().getTypeSpace().addToGlobalMemberSymbolMap(groupName, propName, "a.b.c.T1.prop");
		getJstTypeSpaceMgr().getTypeSpace().addToGlobalTypeSymbolMap(groupName, "T1", "a.b.c.T1");

		// test cases should run to validate proposals of 
		// library contributed globals
		
		validateTypePromotion();
		validatePropPromotion();
		validateMethodPromotion();
		
		
	}


	private void validateTypePromotion() {
		String token = "T1";
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken(token);
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		assertTrue(datas.size()>0);
	}
	
	
	private void validatePropPromotion() {
		String token = "prop";
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken(token);
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		assertTrue(datas.size()>0);
	}
	
	private void validateMethodPromotion() {
		String token = "foo";
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken(token);
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		assertTrue(datas.size()>0);
	}
	
	
	public void validateGlobalType(){
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		IJstType global = LibManager.getInstance().getJsNativeGlobalLib().getType("Global", false);
		List<String> allGlobal = getSuggestions(global);
		
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Assert.assertTrue("Total number of proposals do not match the Global" +
				" native type", datas.size() == allGlobal.size());
		testProposals(datas, allGlobal);
	}
	
	public void validateGlobalTypeByToken() {
		IJstType global = LibManager.getInstance().getJsNativeGlobalLib().getType("Global", false);
		List<String> allGlobal = getSuggestions(global);
//		List<String> allGlobal = getSuggestions(Global.class);
		for (String str : allGlobal) {
			List<String> strComb = getStringCombination(str);
			for (String s : strComb){
				VjoCcCtx ctx = getEmptyContext();
				List<String> matches = getMatchingMembers(allGlobal, s);
				ctx.setActingToken(s);
				advisor.advise(ctx);
				List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
				if (s.equalsIgnoreCase("t")){
//					System.out.println("  ");
				}
				Assert.assertTrue("Total number of proposals do not match the Global" +
						" native type for str = " + s, datas.size() == matches.size());
				testProposals(datas, matches);
			}
		}
	}
	
	private List<String> getSuggestions(IJstType global) {
		List<String> sugg = new ArrayList<String>();
		List<IJstNode> nodes = getJstTypeSpaceMgr().getQueryExecutor()
		.getAllGlobalVars();
		for(IJstNode node:nodes){
			if(node instanceof IJstMethod){
				IJstMethod m = (IJstMethod)node;
				List<? extends IJstMethod> sms = JstTypeHelper.getSignatureMethods(m);
				Iterator<? extends IJstMethod> ims = sms.iterator();
				while (ims.hasNext()) {
					sugg.add(ims.next().getName().getName());
				}	
			}
			if(node instanceof IJstProperty){
				IJstProperty p = (IJstProperty)node;
				sugg.add(p.getName().getName());
			}
		}
		
		return sugg;
	}


	private void testProposals(List<IVjoCcProposalData> datas, List<String> matchList){
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert.assertEquals(data.getAdvisor(), VjoCcGlobalAdvisor.ID);
			Object obj = data.getData();
			if (obj instanceof IJstMethod) {
				IJstMethod method = (IJstMethod)obj;
				Assert.assertTrue("Proposals are wrong", 
						matchList.contains(method.getOriginalName()));
			} else if (obj instanceof IJstProperty) {
				IJstProperty property = (IJstProperty)obj;
				Assert.assertTrue("Proposals are wrong", 
						matchList.contains(property.getName().getName()));
			} else {
				new AssertionFailedException("The proposal returned is " +
						"neither IJStMethod type nor IJstProperty type : " + obj);
			}
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
	
	private List<String> getMatchingMembers(List<String> members, 
			String str){
		List<String> memberList = new ArrayList<String>();
		for (String s : members){
			if (s.startsWith(str)){
				memberList.add(s);
			}
		}
		return memberList;
	}
}
