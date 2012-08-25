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
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.eclipse.core.runtime.AssertionFailedException;
import org.junit.Assert;
import org.junit.Test;



/**
 * Test if the VjoCcPackageProposalAdvisor can calculate out the correct
 * proposal data
 * 
 * 
 * 
 */
//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcPackageProposalAdvisorTest extends VjoCcBaseTest {
	private VjoCcPackageProposalAdvisor advisor = new VjoCcPackageProposalAdvisor();

	@Test
	// Jack : if package is empty, should no be contained in
	// Lax : if package is empty, they need not be shown in proposal, because
	// the proposal will inject nothing into editor
	public void testPkgAdvise1() {
		testPkgProposalsNoExist(CodeCompletionUtil.GROUP_NAME, "InstanceOfTest");
	}

	@Test
	public void testPkgAdvise2() {
		testPkgProposals(CodeCompletionUtil.GROUP_NAME,
				"packageProposalAdvisor.SampleType");
	}

	@Test
	public void testPkgAdvise3() {
		testPkgProposals(CodeCompletionUtil.GROUP_NAME, "parent.ParentType");
	}

	@Test
	public void testPkgAdvise4() {
		testPkgProposals(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorTest");
	}

	@Test
	public void testPkgAdvise5() {
		testPkgProposals(CodeCompletionUtil.GROUP_NAME,
				"staticPropAdvisor.StaticPropAdvisorTest1");
	}

	@Test
	public void testPkgWithActingToken() {
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("nonStatic");
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorTest");
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		JstPackage pkg = actingType.getPackage();
		Assert.assertNotNull(pkg);

		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		List<JstPackage> pkgList = new ArrayList<JstPackage>();
		Assert.assertTrue("should have proposal datas returned",
				datas.size() == 1);
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert.assertEquals(data.getAdvisor(),
					VjoCcPackageProposalAdvisor.ID);
			Object obj = data.getData();
			if (obj instanceof JstPackage) {
				pkgList.add((JstPackage) obj);
			} else {
				new AssertionFailedException("The proposal returned is not of"
						+ " JstPackage Type : " + obj);
			}
		}
		Assert.assertTrue("Package not found : " + pkg, pkgList.contains(pkg));
	}

	private void testPkgProposals(String group, String js) {
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		TypeName typeName = new TypeName(group, js);
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		JstPackage pkg = actingType.getPackage();
		Assert.assertNotNull(pkg);

		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		List<JstPackage> pkgList = new ArrayList<JstPackage>();
		Assert.assertTrue("should have proposal datas returned",
				datas.size() > 0);
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert.assertEquals(data.getAdvisor(),
					VjoCcPackageProposalAdvisor.ID);
			Object obj = data.getData();
			if (obj instanceof JstPackage) {
				pkgList.add((JstPackage) obj);
			} else {
				new AssertionFailedException("The proposal returned is not of"
						+ " JstPackage Type : " + obj);
			}
		}

		Assert.assertTrue("Package not found : " + pkg, pkgList.contains(pkg));
	}
	
	/**
	 * test that the package where the js file belongs to should not be contained in proposals
	 * @param group
	 * @param js
	 */
	private void testPkgProposalsNoExist(String group, String js) {
		VjoCcCtx ctx = getEmptyContext();
		ctx.setActingToken("");
		TypeName typeName = new TypeName(group, js);
		IJstType actingType = getJstType(typeName);
		Assert.assertNotNull(actingType);
		JstPackage pkg = actingType.getPackage();
		Assert.assertNotNull(pkg);
		
		advisor.advise(ctx);
		List<IVjoCcProposalData> datas = ctx.getReporter().getProposalData();
		List<JstPackage> pkgList = new ArrayList<JstPackage>();
		Assert.assertTrue("should have proposal datas returned",
				datas.size() > 0);
		Iterator<IVjoCcProposalData> it = datas.iterator();
		while (it.hasNext()) {
			IVjoCcProposalData data = it.next();
			Assert.assertEquals(data.getAdvisor(),
					VjoCcPackageProposalAdvisor.ID);
			Object obj = data.getData();
			if (obj instanceof JstPackage) {
				pkgList.add((JstPackage) obj);
			} else {
				new AssertionFailedException("The proposal returned is not of"
						+ " JstPackage Type : " + obj);
			}
		}
		
		Assert.assertTrue("Package not found : " + pkg, !pkgList.contains(pkg));
	}
}
