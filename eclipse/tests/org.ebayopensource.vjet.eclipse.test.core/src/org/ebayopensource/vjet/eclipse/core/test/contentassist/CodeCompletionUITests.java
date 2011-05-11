///*******************************************************************************
// * Copyright (c) 2005-2011 eBay Inc.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// *
// *******************************************************************************/
//package org.ebayopensource.vjet.eclipse.core.test.contentassist;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.eclipse.core.runtime.Path;
//import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
//import org.eclipse.dltk.mod.internal.ui.editor.ScriptEditor;
//import org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider;
//import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;
//import org.eclipse.jface.text.contentassist.ICompletionProposal;
//import org.eclipse.jface.text.source.ISourceViewer;
//
//import com.ebay.tools.testframework.fixture.FixtureManager;
//import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
//import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
//import org.ebayopensource.vjet.eclipse.core.ts.EclipseTypeSpaceLoader;
//import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.CompletionProposal;
//import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoProposalEclipsePresenter;
//import org.ebayopensource.vjo.tool.codecompletion.IVjoCcEngine;
//import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
//import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
//import org.ebayopensource.vjo.tool.codecompletion.engine.VjoCcEngine;
//import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
//import org.junit.Ignore;
//
//public class CodeCompletionUITests extends AbstractVjoModelTests {
//	private static boolean isFirstRun = true;
//
//	public void setUp() throws Exception {
//		super.setUp();
//		if (isFirstRun) {
//			/**
//			 * renew the type loader In testEnumReplacement, it will read group
//			 * dependencies, and if dependencies from previous test cases are
//			 * not cleaned, case would fail
//			 * 
//			 * @see EclipseTypeSpaceLoader#getGroupDepends()
//			 */
//			TypeSpaceMgr.getInstance().setTypeLoader(new EclipseTypeSpaceLoader());
//			FixtureManager m_fixtureManager = null;
//			try {
//				m_fixtureManager = FixtureUtils.setUpFixture(this);
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally{
//				isFirstRun = false;
//				FixtureUtils.tearDownFixture(m_fixtureManager);
//			}
//		}
//	}
//
//	@Ignore("update order")
//	public void testProposalsOrder() throws Exception {
//		String js = "defect/Bug2193.js";
//		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
//		try {
//			String[] names = { "arguments Arguments", "arr Array", "i int",
//					"aaPprop1 int - Bug2193", "aaProp2 String - Bug2193",
//					"aaProp3 Boolean - Bug2193", "base Object - Bug2193",
//					"constructor Object - Object", "vj$ Vj$Type - Bug2193",
//					"aaMethod() void - Bug2193",
//					"aaStaticProp1 int - Bug2193",
//					"aaStaticProp2 String - Bug2193",
//					"aaStaticProp3 Boolean - Bug2193" };
//			VjoSourceModule module = (VjoSourceModule) getSourceModule(
//					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
//			int position = lastPositionInFile("alert(", module);
//			IVjoCcEngine engine = new VjoCcEngine(TypeSpaceMgr.parser());
//			List<IVjoCcProposalData> list = engine.complete(module
//					.getGroupName(), new String(module.getFileName()), module
//					.getSourceContents(), position);
//			VjoCcCtx ctx = engine.getContext();
//			VjoProposalEclipsePresenter presenter = new VjoProposalEclipsePresenter(
//					ctx, position, null, null);
//			List<ICompletionProposal> pList = presenter.doPresenter(list);
//			for (int i = 0; i < names.length; i++) {
//				CompletionProposal completionProp = (CompletionProposal) pList
//						.get(i);
//				assertEquals(names[i], completionProp.getDisplayString());
//			}
//		} finally {
//			FixtureUtils.tearDownFixture(m_fixtureManager);
//		}
//	}
//
//
//	private List<String> getStringList(List<ICompletionProposal> pList) {
//		List<String> list = new ArrayList<String>();
//		for (ICompletionProposal prop : pList) {
//			CompletionProposal completionProp = (CompletionProposal) prop;
//			list.add(completionProp.getDisplayString());
//		}
//		return list;
//	}
//}
