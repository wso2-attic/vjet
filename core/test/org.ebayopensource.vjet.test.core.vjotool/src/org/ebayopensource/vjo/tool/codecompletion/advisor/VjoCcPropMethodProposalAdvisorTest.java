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
import org.junit.Ignore;
import org.junit.Test;



/**
 * Test if the VjoCcPropMethodProposalAdvisor can calculate out the correct
 * proposal data
 * 
 * 
 * 
 */
//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcPropMethodProposalAdvisorTest extends VjoCcBaseTest {
	private VjoCcPropMethodProposalAdvisor advisor = new VjoCcPropMethodProposalAdvisor();
	private VjoCcDerivedPropMethodAdvisor advisor2 = new VjoCcDerivedPropMethodAdvisor();

	@Test
	public void testSimple() {
		testPropMethods(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ParentType1");
	}

	@Test
	public void testCType() {
		testPropMethods(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorTest");
	}

	@Test
	public void testAType() {
		testPropMethods(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorAType");
	}

	@Test
	public void testMType() {
		testPropMethods(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorMType");
	}

	@Test
	public void testIType() {
		testPropMethods(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorIType");
	}


	@Test
	public void testMethodsCharByChar1() {
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorTest");
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		List<IJstMethod> methods = getJstMethods(actingType);
		for (IJstMethod method : methods) {
			List<String> charList = getStringComboForMethod(method);
			testWithCharactersMethod(charList, actingType, methods);
		}
	}


	@Test
	public void testPropsCharByChar1() {
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorTest");
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		List<IJstProperty> properties = getJstProps(actingType);
		for (IJstProperty prop : properties) {
			List<String> charList = getStringComboForProp(prop);
			testWithCharactersProp(charList, actingType, properties);
		}
	}

	@Test
	@Ignore //TODO See http://quickbugstage.arch.ebay.com/show_bug.cgi?id=8045
	public void testPropsCharByChar2() {
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorTest1");
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		List<IJstProperty> properties = getJstProps(actingType);
		for (IJstProperty prop : properties) {
			List<String> charList = getStringComboForProp(prop);
			testWithCharactersProp(charList, actingType, properties);
		}
	}
	/**
	 * Test the call rule, the private method can not be visited by other type 
	 */
	@Test
	public void testIdentiferOfProps() {
		TypeName typeName1 = new TypeName(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorTest1");
		IJstType actingType = getJstType(typeName1);
		Assert.assertNotNull(actingType);
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorTest2");
		IJstType calledType = getJstType(typeName);
		Assert.assertNotNull(calledType);
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingType(actingType);
		ctx.setCalledType(calledType);
		ctx.setActingToken("pri");
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Assert.assertTrue("should not return any proposal, because the only token matched method is private",
				datas.isEmpty());

	}
	
	@Test
	public void testIdentiferOfProps1() {
		TypeName typeName1 = new TypeName(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorTest1");
		IJstType actingType = getJstType(typeName1);
		Assert.assertNotNull(actingType);
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorTest2");
		IJstType calledType = getJstType(typeName);
		Assert.assertNotNull(calledType);
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingType(actingType);
		ctx.setCalledType(calledType);
		ctx.setActingToken("testFun");
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Assert.assertTrue("should not return any proposal, because the only token matched method is private",
				datas.isEmpty());

	}

	private void testWithCharactersMethod(List<String> charList,
			IJstType actingType, List<IJstMethod> methods) {
		for (String s : charList) {
			VjoCcCtx ctx = getEmptyContext();
			ctx.setActingToken(s);
			ctx.setActingType(actingType);
			List<IJstMethod> list = getMatchingMethods(methods, s);
			advisor.advise(ctx);
			advisor2.advise(ctx);
			List<IVjoCcProposalData> datas = ctx.getReporter()
					.getProposalData();
//			Assert.assertTrue("should have proposal datas returned", datas
//					.size() > 0);
//			Assert.assertTrue(
//					"proposals does not contain all results. should have : "
//							+ list + "\n And the proposed : " + datas, datas
//							.size() >= list.size());
			Iterator<IVjoCcProposalData> it = datas.iterator();
			while (it.hasNext()) {
				IVjoCcProposalData data = it.next();
				Assert.assertTrue(data.getAdvisor()==
						VjoCcPropMethodProposalAdvisor.ID || 
						data.getAdvisor() == VjoCcDerivedPropMethodAdvisor.ID);
				Object obj = data.getData();
				if (obj instanceof IJstMethod) {
					IJstMethod method = (IJstMethod) obj;
					if (method instanceof JstProxyMethod) {
						method = ((JstProxyMethod)method).getTargetMethod();
					}
					Assert.assertTrue("Proposals are missing method " + method.getName().getName() +" token was:" + s, list
							.contains(method)
							|| !data.isAccurateMatch());
					Assert.assertFalse(method.isStatic());
				}
			}
		}
	}

	private void testWithCharactersProp(List<String> charList,
			IJstType actingType, List<IJstProperty> props) {
		for (String s : charList) {
			VjoCcCtx ctx = getEmptyContext();
			ctx.setActingToken(s);
			ctx.setActingType(actingType);
			List<IJstProperty> list = getMatchingProps(props, s);
			advisor.advise(ctx);
			advisor2.advise(ctx);
			List<IVjoCcProposalData> datas = ctx.getReporter()
					.getProposalData();
			Assert.assertTrue("should have proposal datas returned", datas
					.size() > 0);
			Assert.assertTrue(
					"proposals does not contain all results. should have : "
							+ list + "\n And the proposed : " + datas, datas
							.size() >= list.size());
			Iterator<IVjoCcProposalData> it = datas.iterator();
			while (it.hasNext()) {
				IVjoCcProposalData data = it.next();
				Assert.assertTrue(data.getAdvisor()==
					VjoCcPropMethodProposalAdvisor.ID || 
					data.getAdvisor() == VjoCcDerivedPropMethodAdvisor.ID);
				Object obj = data.getData();
				if (obj instanceof IJstProperty) {
					IJstProperty prop = (IJstProperty) obj;
					if (prop instanceof JstProxyProperty) {
						prop = ((JstProxyProperty)prop).getTargetProperty();
					}
					Assert.assertTrue("Proposals are wrong", list
							.contains(prop));
					Assert.assertFalse(prop.isStatic());
				}
			}
		}
	}

	private List<IJstMethod> getMatchingMethods(List<IJstMethod> methods,
			String str) {
		List<IJstMethod> methodList = new ArrayList<IJstMethod>();
		for (IJstMethod m : methods) {
			if (m instanceof JstProxyMethod) {
				m = ((JstProxyMethod)m).getTargetMethod();
			}
			if (m.getOriginalName().startsWith(str)) {
				if (!methodList.contains(m))
					methodList.add(m);
			}
		}
		return methodList;
	}

	private List<IJstProperty> getMatchingProps(List<IJstProperty> methods,
			String str) {
		List<IJstProperty> propList = new ArrayList<IJstProperty>();
		for (IJstProperty p : methods) {
			if (p instanceof JstProxyProperty) {
				p = ((JstProxyProperty)p).getTargetProperty();
			}
			if (p.getName().getName().startsWith(str)) {
				
				if (!propList.contains(p))
					
					propList.add(p);
			}
		}
		return propList;
	}

	private List<String> getStringComboForMethod(IJstMethod method) {
		Assert.assertTrue(method != null);
		Assert.assertTrue(method.getOriginalName() != null);
		Assert.assertTrue(!(method.getOriginalName().equalsIgnoreCase("")));

		List<String> list = new ArrayList<String>();
		String name = "";
		for (char c : method.getOriginalName().toCharArray()) {
			name = name + c;
			list.add(name);
		}
		return list;
	}

	private List<String> getStringComboForProp(IJstProperty prop) {
		Assert.assertTrue(prop != null);
		Assert.assertTrue(prop.getName().getName() != null);
		Assert.assertTrue(!(prop.getName().getName().equalsIgnoreCase("")));

		List<String> list = new ArrayList<String>();
		String name = "";
		for (char c : prop.getName().getName().toCharArray()) {
			name = name + c;
			list.add(name);
		}
		return list;
	}

	private void testPropMethods(String group, String js) {
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		TypeName typeName = new TypeName(group, js);
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);

		List<IJstMethod> methods = getJstMethods(actingType);
		List<IJstProperty> properties = getJstProps(actingType);

		ctx.setActingType(actingType);
		advisor.advise(ctx);
		advisor2.advise(ctx); // add in derived props and methods
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		Assert.assertTrue("should have proposal datas returned",
				datas.size() > 0);
//		Assert.assertTrue("proposals does not contain all results. Actual : "
//				+ methods + properties + "\n And the proposed : " + datas,
//				datas.size() >= methods.size() + properties.size());
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert.assertTrue(data.getAdvisor() ==
					VjoCcPropMethodProposalAdvisor.ID || data.getAdvisor() ==VjoCcDerivedPropMethodAdvisor.ID);
			Object obj = data.getData();
			if (obj instanceof IJstMethod) {
				IJstMethod method = (IJstMethod) obj;
				String name = method.getName().getName();
				// if (name.equals("equals") || name.equals("getClass") ||
				// name.equals("hashCode") || name.equals("toString")){
				// //do nothing
				// } else {
				if (method instanceof JstProxyMethod) {
					method = ((JstProxyMethod)method).getTargetMethod();
				}
				Assert.assertTrue("Proposals are wrong", methods
						.contains(method));
				Assert.assertFalse(method.isStatic());
				// }
			} else if (obj instanceof IJstProperty) {
				IJstProperty property = (IJstProperty) obj;
				if (property instanceof JstProxyProperty) {
					property = ((JstProxyProperty)property).getTargetProperty();
				}
				Assert.assertTrue("Proposals missing property: " + property.getName().getName(), properties
						.contains(property));
				Assert.assertFalse(property.isStatic());
			} else {
				new AssertionFailedException("The proposal returned is "
						+ "neither IJStMethod type nor IJstProperty type : "
						+ obj);
			}
		}
	}

	private List<IJstMethod> getJstMethods(IJstType type) {
		List<IJstMethod> methodList = new ArrayList<IJstMethod>();
		List<? extends IJstMethod> methods = type.getMethods(false, true);
		List<String> tempList = new ArrayList<String>();
		boolean isNative = CodeCompletionUtils.isNativeType(type);
		IJstType tempCalledType = type;
		for (IJstMethod method : methods) {
			if (tempCalledType != method.getOwnerType()) {
				tempCalledType = method.getOwnerType();
				isNative = CodeCompletionUtils.isNativeType(tempCalledType);
			}
//			if (isNative && method.getName().getName().startsWith("_")) {
//				continue;
//			}
			String tempStr = CodeCompletionUtils.getMthodsStr(method);
			if (!tempList.contains(tempStr)) {
				tempList.add(tempStr);
				if (method instanceof JstProxyMethod) {
					method = ((JstProxyMethod)method).getTargetMethod();
				}
				methodList.add(method);
				
			}
		}
		// for (IJstType ext : type.getExtends()){
		// addJstMethods(ext, methodList);
		// }
		// for (IJstType satis : type.getSatisfies()){
		// addJstMethods(satis, methodList);
		// }
		return methodList;
	}

	private List<IJstProperty> getJstProps(IJstType type) {
		List<IJstProperty> tempPropList = new ArrayList<IJstProperty>();
		List<IJstProperty> propList = type.getAllPossibleProperties(false, true);
		// List<IJstMethod> methodList = new ArrayList<IJstMethod>();
		boolean isNative = CodeCompletionUtils.isNativeType(type);
		IJstType tempCalledType = type;
		for (IJstProperty property : propList) {
			if (tempCalledType != property.getOwnerType()) {
				tempCalledType = property.getOwnerType();
				isNative = CodeCompletionUtils.isNativeType(tempCalledType);
			}
			
			if (property instanceof JstProxyProperty) {
				property = ((JstProxyProperty)property).getTargetProperty();
			}
			tempPropList.add(property);
		}
		// for (IJstType ext : type.getExtends()){
		// addJstMethods(ext, methodList);
		// }
		// for (IJstType satis : type.getSatisfies()){
		// addJstMethods(satis, methodList);
		// }
		return tempPropList;
	}
}
