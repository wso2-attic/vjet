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
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyProperty;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.eclipse.core.runtime.AssertionFailedException;
import org.junit.Assert;
import org.junit.Test;



/**
 * Test if the StaticPropMethodProposal can calculate out the correct proposal
 * data
 * 
 * 
 * 
 */
//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcStaticPropMethodProposalAdvisorTest extends VjoCcBaseTest {
	private VjoCcStaticPropMethodProposalAdvisor advisor = new VjoCcStaticPropMethodProposalAdvisor();
	
	@Test
	public void testSimple(){
		testStaticPropMethods(CodeCompletionUtil.GROUP_NAME, "staticPropAdvisor.ParentType");
	}
	
	@Test
	public void testCType(){
		testStaticPropMethods(CodeCompletionUtil.GROUP_NAME, "staticPropAdvisor.StaticPropAdvisorTest");
	}
	
	@Test
	public void testAType(){
		testStaticPropMethods(CodeCompletionUtil.GROUP_NAME, "staticPropAdvisor.StaticPropAdvisorAType");
	}
	
	@Test
	public void testMType(){
		testStaticPropMethods(CodeCompletionUtil.GROUP_NAME, "staticPropAdvisor.StaticPropAdvisorMType");
	}
	
	@Test
	public void testIType(){
		testStaticPropMethods(CodeCompletionUtil.GROUP_NAME, "staticPropAdvisor.StaticPropAdvisorIType");
	}
	
	// TODO use static list of proposals and then assert equals not assert true
//	@Test
////	@Ignore //TODO See http://quickbugstage.arch.ebay.com/show_bug.cgi?id=8045
//	public void testCTypeComplex(){
//		testStaticPropMethods(CodeCompletionUtil.GROUP_NAME, "staticPropAdvisor.StaticPropAdvisorTest1");
//	}
	
	@Test
	public void testMethodsCharByChar1(){
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME, 
				"staticPropAdvisor.StaticPropAdvisorTest");
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		List<IJstMethod> methods = getJstStaticMethods(actingType);
		for(IJstMethod method : methods){
			List<String> charList = getStringComboForMethod(method);
			testWithCharactersMethod(charList, actingType, methods);
		}
	}
	
	@Test
	public void testMethodsCharByChar2(){
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME, 
				"staticPropAdvisor.StaticPropAdvisorTest1");
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		List<IJstMethod> methods = getJstStaticMethods(actingType);
		for(IJstMethod method : methods){
			List<String> charList = getStringComboForMethod(method);
			testWithCharactersMethod(charList, actingType, methods);
		}
	}
	
	@Test
	public void testPropsCharByChar1(){
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME, 
				"staticPropAdvisor.StaticPropAdvisorTest");
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		List<IJstProperty> properties = getJstStaticProps(actingType);
		for(IJstProperty prop : properties){
			List<String> charList = getStringComboForProp(prop);
			testWithCharactersProp(charList, actingType, properties);
		}
	}
	
	// TODO fix this test must use list that is created independent of the jst properties
//	@Test
////	@Ignore //TODO See http://quickbugstage.arch.ebay.com/show_bug.cgi?id=8045
//	public void testPropsCharByChar2(){
//		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME, 
//				"staticPropAdvisor.StaticPropAdvisorTest1");
//		IJstType actingType = getJstType(typeName);
//		Assert.assertNotNull(actingType);
//		List<IJstProperty> properties = getJstStaticProps(actingType);
//		for(IJstProperty prop : properties){
//			List<String> charList = getStringComboForProp(prop);
//			testWithCharactersProp(charList, actingType, properties);
//		}
//	}
	
	private void testWithCharactersMethod(List<String> charList, IJstType actingType, 
			List<IJstMethod> methods){
		for(String s : charList){
			VjoCcCtx ctx = getEmptyContext();
			ctx.setActingToken(s);
			ctx.setActingType(actingType);
			List<IJstMethod> list = getMatchingMethods(methods, s);
			advisor.advise(ctx);
			List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
			Assert.assertTrue("should have proposal datas returned", datas.size() > 0);
			Assert.assertTrue("proposals does not contain all results for " +
					"str = " + s + ", Should have : " + list +  "\n And the " +
					"proposed : " + datas, datas.size() >= list.size() );
			Iterator<IVjoCcProposalData> it = datas.iterator();
			while (it.hasNext()) {
				IVjoCcProposalData data = it.next();
				Assert.assertEquals(data.getAdvisor(), VjoCcStaticPropMethodProposalAdvisor.ID);
				Object obj = data.getData();
				if (obj instanceof IJstMethod) {
					IJstMethod method = (IJstMethod)obj;
					if (method instanceof JstProxyMethod) {
						method = ((JstProxyMethod)method).getTargetMethod();
					}
					Assert.assertTrue("Proposals are wrong", list.contains(method) || !data.isAccurateMatch());
					Assert.assertTrue(method.isStatic());
				}
			}
		}
	}
	
	private void testWithCharactersProp(List<String> charList, IJstType actingType, 
			List<IJstProperty> props){
		for(String s : charList){
			VjoCcCtx ctx = getEmptyContext();
			ctx.setActingToken(s);
			ctx.setActingType(actingType);
			List<IJstProperty> list = getMatchingProps(props, s);
			advisor.advise(ctx);
			List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
			if(datas.size() <= 0){
//				System.out.println("HAHAHA");
			}
			Assert.assertTrue("should have proposal datas returned for " +
					"str = " + s, datas.size() > 0);
			Assert.assertTrue("proposals does not contain all results. should have : " +
					list +  "\n And the proposed : " + datas, datas.size() >= list.size() );
			Iterator<IVjoCcProposalData> it = datas.iterator();
			while (it.hasNext()) {
				IVjoCcProposalData data = it.next();
				Assert.assertEquals(data.getAdvisor(), VjoCcStaticPropMethodProposalAdvisor.ID);
				Object obj = data.getData();
				if (obj instanceof IJstProperty) {
					IJstProperty prop = (IJstProperty)obj;
					if (prop instanceof JstProxyProperty) {
						prop = ((JstProxyProperty)prop).getTargetProperty();
					}					Assert.assertTrue("Proposals are wrong", list.contains(prop));
					Assert.assertTrue(prop.isStatic());
				}
			}
		}
	}
	
	private List<IJstMethod> getMatchingMethods(List<IJstMethod> methods, 
			String str){
		List<IJstMethod> methodList = new ArrayList<IJstMethod>();
		for (IJstMethod m : methods){
			if (m instanceof JstProxyMethod) {
				m = ((JstProxyMethod)m).getTargetMethod();
			}
			if (m.getOriginalName().startsWith(str)){
				if (!methodList.contains(m))
				methodList.add(m);
			}
		}
		return methodList;
	}
	
	private List<IJstProperty> getMatchingProps(List<IJstProperty> methods, 
			String str){
		List<IJstProperty> propList = new ArrayList<IJstProperty>();
		for (IJstProperty p : methods){
			if (p instanceof JstProxyProperty) {
				p = ((JstProxyProperty)p).getTargetProperty();
			}	
			if (p.getName().getName().startsWith(str)){
				if (!propList.contains(p))
				propList.add(p);
			}
		}
		return propList;
	}
	
	private List<String> getStringComboForMethod(IJstMethod method){
		Assert.assertTrue(method != null);
		Assert.assertTrue(method.getOriginalName() != null);
		Assert.assertTrue(!(method.getOriginalName().equalsIgnoreCase("")));
		
		List<String> list = new ArrayList<String>();
		String name = "";
		for (char c : method.getOriginalName().toCharArray()){
			name = name + c;
			list.add(name);
		}
		return list;
	}
	
	private List<String> getStringComboForProp(IJstProperty prop){
		Assert.assertTrue(prop != null);
		Assert.assertTrue(prop.getName().getName() != null);
		Assert.assertTrue(!(prop.getName().getName().equalsIgnoreCase("")));
		
		List<String> list = new ArrayList<String>();
		String name = "";
		for (char c : prop.getName().getName().toCharArray()){
			name = name + c;
			list.add(name);
		}
		return list;
	}
	
	private void testStaticPropMethods(String group, String js){
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		TypeName typeName = new TypeName(group, js);
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		
		List<IJstMethod> methods = getJstStaticMethods(actingType);
		List<IJstProperty> properties = getJstStaticProps(actingType);
		
		ctx.setActingType(actingType);
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Assert.assertTrue("should have proposal datas returned", datas.size() > 0);
		Assert.assertTrue("proposals does not contain all results. Actual : " +
				methods + properties + "\n And the proposed : " + datas, 
				datas.size() == methods.size() + properties.size());
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert.assertEquals(data.getAdvisor(), VjoCcStaticPropMethodProposalAdvisor.ID);
			Object obj = data.getData();
			if (obj instanceof IJstMethod) {
				IJstMethod method = (IJstMethod)obj;
				if (method instanceof JstProxyMethod) {
					method = ((JstProxyMethod)method).getTargetMethod();
				}
				Assert.assertTrue("Proposals are wrong", methods.contains(method));
				Assert.assertTrue(method.isStatic());
			} else if (obj instanceof IJstProperty) {
				IJstProperty property = (IJstProperty)obj;
				if (property instanceof JstProxyProperty) {
					property = ((JstProxyProperty)property).getTargetProperty();
				}
				Assert.assertTrue("Proposals are wrong", properties.contains(property));
				Assert.assertTrue(property.isStatic());
			} else {
				new AssertionFailedException("The proposal returned is " +
						"neither IJStMethod type nor IJstProperty type : " + obj);
			}
		}
	}
	
	private List<IJstMethod> getJstStaticMethods(IJstType type){
		List<IJstMethod> methodList = new ArrayList<IJstMethod>();
		addJstMethods(type, methodList, true);
//		for (IJstType ext : type.getExtends()){
//			addJstMethods(ext, methodList, false);
//		}
//		for (IJstType satis : type.getSatisfies()){
//			addJstMethods(satis, methodList, false);
//		}
		return methodList;
	}
	
	private void addJstMethods(IJstType type, List<IJstMethod> methodList, boolean containSynthesize){
		List<? extends IJstMethod> list = type.getMethods(true, true);
		for (IJstMethod m : list) {
			if (CodeCompletionUtils.isSynthesizedElement(m) && !containSynthesize) {
				continue;
			}
			if (m instanceof JstProxyMethod) {
				m = ((JstProxyMethod)m).getTargetMethod();
			}
			methodList.add(m);
		}
	}
	
	private List<IJstProperty> getJstStaticProps(IJstType type){
		List<IJstProperty> propList = new ArrayList<IJstProperty>();
		List<IJstProperty>  tempList = type.getAllPossibleProperties(true, true);
		for(IJstProperty property : tempList){
			if (property instanceof JstProxyProperty) {
				property = ((JstProxyProperty)property).getTargetProperty();
			}
			propList.add(property);
		}
//		for (IJstType ext : type.getExtends()){
//			propList.addAll(ext.getProperties(true));
//		}
//		for (IJstType satis : type.getSatisfies()){
//			propList.addAll(satis.getProperties(true));
//		}
		return propList;
	}

}
