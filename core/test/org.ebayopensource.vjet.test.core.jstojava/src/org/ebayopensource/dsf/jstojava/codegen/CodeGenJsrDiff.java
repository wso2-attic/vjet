/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.codegen;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.generate.JsrGenerator;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.ts.IJstTypeLoader;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.controller.BuildController;
import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader.FileSuffix;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.lib.IResourceResolver;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
// @ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class CodeGenJsrDiff {

	private static final String FOLDER = "data";
	private static final String FOLDER_PARENT = "data/parent";

	// private String input = null;
	// private String gold = null;
	private String m_input;
	private String m_gold;
	private static String s_groupPath;
	private static BuildController s_controller = null;
	private static String srcPath = "";
	private static JstTypeSpaceMgr mgr;

	public CodeGenJsrDiff(String input, String gold) {
		m_input = input;
		m_gold = gold;
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays
				.asList(new Object[][] {
						// Note:
						// 1-Identical files need to be added in both
						// org.ebayopensource.dsf.jstojava.codegen.data
						// and
						// typespacesrc/workspaceTS.src.org.ebayopensource.dsf.jstojava.codegen.data
						// 2-Don't forget to hit F5 on typespacesrc before
						// running your test
						{ "CTypeOnlyNoPackage",
								FOLDER + "/CTypeOnlyNoPackageJsr.jsr" },

						{ "org.ebayopensource.dsf.jstojava.codegen.data.AType",
								FOLDER + "/ATypeJsr.jsr" },

						{ "org.ebayopensource.dsf.jstojava.codegen.data.CType",
								FOLDER + "/CTypeJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.CTypeWithAType",
								FOLDER + "/CTypeWithATypeJsr.jsr" },

						// {"org.ebayopensource.dsf.jstojava.codegen.data.CTypeWithIType",
						// FOLDER+"/CTypeWithITypeJsr.jsr"}, - commented for
						// running tests from jars

						{ "org.ebayopensource.dsf.jstojava.codegen.data.IType",
								FOLDER + "/ITypeJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.MethodsMultiType",
								FOLDER + "/MethodsMultiTypeJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.MethodsWithOptionalTypes",
								FOLDER + "/MethodsWithOptionalTypesJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.StaticFinals",
								FOLDER + "/StaticFinalsJsr.jsr" },
						// TODO
						// {FOLDER + "/CTypeComplex",
						// FOLDER + "/CTypeComplexJsr.jsr" },

						{ "v4.js.etype.ETypeSimplest",
								FOLDER + "/ETypeSimplestJsr.jsr" },

						{ "v4.js.etype.ETypeSimple",
								FOLDER + "/ETypeSimpleJsr.jsr" },

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ETypeComplex",
						// FOLDER + "/ETypeComplexJsr.jsr" }, - commented for
						// running tests from jars
						// TODO
						// {FOLDER + "/CTypeWithMType",
						// FOLDER + "/CTypeWithMTypeJsr.jsr" },

						// TODO
						// {FOLDER + "/CTypeWithMTypeProps",
						// FOLDER + "/CTypeWithMTypePropsJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.ATypeOnly00",
								FOLDER + "/ATypeOnly00Jsr.jsr" },

						{ "vjo.a.b.CTypeOnly00", FOLDER + "/CTypeOnly00Jsr.jsr" },

						{ "vjo.g.h.ITypeOnly00", FOLDER + "/ITypeOnly00Jsr.jsr" },

						{ "vjo.x.y.CTypeEmptyComment00",
								FOLDER + "/CTypeEmptyComment00Jsr.jsr" },

						{ "vjo.a.b.CTypeConstructs00",
								FOLDER + "/CTypeConstructs00Jsr.jsr" },

						{ "vjo.a.b.CTypeConstructs01",
								FOLDER + "/CTypeConstructs01Jsr.jsr" },

						{ "vjo.a.b.CTypeConstructs02",
								FOLDER + "/CTypeConstructs02Jsr.jsr" },

						{ "vjo.a.b.CTypeConstructs03",
								FOLDER + "/CTypeConstructs03Jsr.jsr" },

						{ "vjo.a.b.CTypeConstructs04",
								FOLDER + "/CTypeConstructs04Jsr.jsr" },

						{ "vjo.a.b.CTypeConstructs05",
								FOLDER + "/CTypeConstructs05Jsr.jsr" },

						{ "vjo.a.b.CTypeConstructs06",
								FOLDER + "/CTypeConstructs06Jsr.jsr" },

						{ "vjo.a.b.CTypeConstructs07",
								FOLDER + "/CTypeConstructs07Jsr.jsr" },

						{ "vjo.a.b.CTypeConstructs08",
								FOLDER + "/CTypeConstructs08Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs00",
								FOLDER + "/CTypeMultipleArgs00Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs01",
								FOLDER + "/CTypeMultipleArgs01Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs02",
								FOLDER + "/CTypeMultipleArgs02Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs03",
								FOLDER + "/CTypeMultipleArgs03Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs04",
								FOLDER + "/CTypeMultipleArgs04Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs05",
								FOLDER + "/CTypeMultipleArgs05Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs06",
								FOLDER + "/CTypeMultipleArgs06Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs07",
								FOLDER + "/CTypeMultipleArgs07Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs08",
								FOLDER + "/CTypeMultipleArgs08Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs09",
								FOLDER + "/CTypeMultipleArgs09Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs10",
								FOLDER + "/CTypeMultipleArgs10Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs12",
								FOLDER + "/CTypeMultipleArgs12Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs13",
								FOLDER + "/CTypeMultipleArgs13Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs14",
								FOLDER + "/CTypeMultipleArgs14Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs15",
								FOLDER + "/CTypeMultipleArgs15Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs16",
								FOLDER + "/CTypeMultipleArgs16Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs17",
								FOLDER + "/CTypeMultipleArgs17Jsr.jsr" },

						{ "vjo.a.b.CTypeMultipleArgs18",
								FOLDER + "/CTypeMultipleArgs18Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethods00",
								FOLDER + "/CTypePropsMethods00Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethods01",
								FOLDER + "/CTypePropsMethods01Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethods02",
								FOLDER + "/CTypePropsMethods02Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethods03",
								FOLDER + "/CTypePropsMethods03Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethods04",
								FOLDER + "/CTypePropsMethods04Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethods05",
								FOLDER + "/CTypePropsMethods05Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethods06",
								FOLDER + "/CTypePropsMethods06Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethods07",
								FOLDER + "/CTypePropsMethods07Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethods08",
								FOLDER + "/CTypePropsMethods08Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethods09",
								FOLDER + "/CTypePropsMethods09Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethods10",
								FOLDER + "/CTypePropsMethods10Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethodsArgs00",
								FOLDER + "/CTypePropsMethodsArgs00Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethodsArgs01",
								FOLDER + "/CTypePropsMethodsArgs01Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethodsArgs02",
								FOLDER + "/CTypePropsMethodsArgs02Jsr.jsr" },

						/*
						 * embedded space in var args {FOLDER +
						 * "/CTypePropsMethodsArgs02b", FOLDER +
						 * "/CTypePropsMethodsArgs02bJsr.jsr"},
						 */

						{ "vjo.a.b.CTypePropsMethodsArgs03",
								FOLDER + "/CTypePropsMethodsArgs03Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethodsArgs04",
								FOLDER + "/CTypePropsMethodsArgs04Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethodsArgs05",
								FOLDER + "/CTypePropsMethodsArgs05Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethodsArgs06",
								FOLDER + "/CTypePropsMethodsArgs06Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethodsArgs07",
								FOLDER + "/CTypePropsMethodsArgs07Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethodsArgs08",
								FOLDER + "/CTypePropsMethodsArgs08Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethodsArgs09",
								FOLDER + "/CTypePropsMethodsArgs09Jsr.jsr" },

						{ "vjo.a.b.CTypePropsMethodsArgs10",
								FOLDER + "/CTypePropsMethodsArgs10Jsr.jsr" },

						{ "vjo.a.b.CTypePropsVars00",
								FOLDER + "/CTypePropsVars00Jsr.jsr" },

						{ "vjo.a.b.CTypePropsVars01",
								FOLDER + "/CTypePropsVars01Jsr.jsr" },

						{ "vjo.a.b.CTypePropsVars02",
								FOLDER + "/CTypePropsVars02Jsr.jsr" },

						{ "vjo.a.b.CTypePropsVars03",
								FOLDER + "/CTypePropsVars03Jsr.jsr" },

						{ "vjo.a.b.CTypePropsVars04",
								FOLDER + "/CTypePropsVars04Jsr.jsr" },

						{ "vjo.a.b.CTypePropsVars05",
								FOLDER + "/CTypePropsVars05Jsr.jsr" },

						{ "vjo.a.b.CTypePropsVars06",
								FOLDER + "/CTypePropsVars06Jsr.jsr" },

						{ "vjo.a.b.CTypePropsVars07",
								FOLDER + "/CTypePropsVars07Jsr.jsr" },

						{ "vjo.a.b.CTypePropsVars08",
								FOLDER + "/CTypePropsVars08Jsr.jsr" },

						{ "vjo.a.b.CTypePropsVars09",
								FOLDER + "/CTypePropsVars09Jsr.jsr" },

						{ "vjo.a.b.CTypePropsVars10",
								FOLDER + "/CTypePropsVars10Jsr.jsr" },

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ExtendsATypeWithAType",
						// FOLDER + "/ExtendsATypeWithATypeJsr.jsr" }, -
						// commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ExtendsATypeWithATypeImplementsBothITypes",
						// FOLDER +
						// "/ExtendsATypeWithATypeImplementsBothITypesJsr.jsr"
						// },- commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ExtendsATypeWithATypeImplementsIType",
						// FOLDER +
						// "/ExtendsATypeWithATypeImplementsITypeJsr.jsr" },-
						// commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ExtendsCTypeWithAType",
						// FOLDER + "/ExtendsCTypeWithATypeJsr.jsr" },-
						// commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ExtendsCTypeWithATypeImplementsBothITypes",
						// FOLDER +
						// "/ExtendsCTypeWithATypeImplementsBothITypesJsr.jsr"
						// },- commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ExtendsCTypeWithATypeImplementsIType",
						// FOLDER +
						// "/ExtendsCTypeWithATypeImplementsITypeJsr.jsr" },-
						// commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ExtendsCTypeWithCType",
						// FOLDER + "/ExtendsCTypeWithCTypeJsr.jsr" },-
						// commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ExtendsCTypeWithCTypeImplementsBothITypes",
						// FOLDER +
						// "/ExtendsCTypeWithCTypeImplementsBothITypesJsr.jsr"
						// },- commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ExtendsCTypeWithCTypeImplementsIType",
						// FOLDER +
						// "/ExtendsCTypeWithCTypeImplementsITypeJsr.jsr" },-
						// commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ExtendsITypeWithBothITypes",
						// FOLDER + "/ExtendsITypeWithBothITypesJsr.jsr" },-
						// commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ExtendsITypeWithIType",
						// FOLDER + "/ExtendsITypeWithITypeJsr.jsr" },-
						// commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ImplementsATypeWithBothITypes",
						// FOLDER + "/ImplementsATypeWithBothITypesJsr.jsr" },-
						// commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ImplementsATypeWithIType",
						// FOLDER + "/ImplementsATypeWithITypeJsr.jsr" },-
						// commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ImplementsCTypeWithBothITypes",
						// FOLDER + "/ImplementsCTypeWithBothITypesJsr.jsr" },-
						// commented for running tests from jars

						// {"org.ebayopensource.dsf.jstojava.codegen.data.ImplementsCTypeWithIType",
						// FOLDER + "/ImplementsCTypeWithITypeJsr.jsr" },-
						// commented for running tests from jars

						// TODO
						// {FOLDER + "/CTypeCustomType",
						// FOLDER + "/CTypeCustomTypeJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.VjoObjectUsage",
								FOLDER + "/VjoObjectUsageJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.NeedsMulti",
								FOLDER + "/NeedsMultiJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.Nested",
								FOLDER + "/NestedJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.parent.N_AType",
								FOLDER_PARENT + "/N_ATypeJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.parent.N_CType",
								FOLDER_PARENT + "/N_CTypeJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.parent.N_IType",
								FOLDER_PARENT + "/N_ITypeJsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.parent.N_IType2",
								FOLDER_PARENT + "/N_IType2Jsr.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.OverloadMethods",
								FOLDER + "/OverloadMethods.jsr" },

						{
								"org.ebayopensource.dsf.jstojava.codegen.data.CTypeJsNative",
								FOLDER + "/CTypeJsNativeJsr.jsr" }

								// TODO FIXME this test is failing due to duplicating the Otype 2x
//						{
//								"org.ebayopensource.dsf.jstojava.codegen.data.OTypeOL",
//								FOLDER + "/OTypeOLJsr.jsr" }

				});
	}

	@BeforeClass
	public static void setUp() throws Exception {
		IResourceResolver jstLibResolver = org.ebayopensource.dsf.jstojava.test.utils.JstLibResolver
				.getInstance()
				.setSdkEnvironment(
						new org.ebayopensource.dsf.jstojava.test.utils.VJetSdkEnvironment(
								new String[0], "DefaultSdk"));

		LibManager.getInstance().setResourceResolver(jstLibResolver);

		JstCache.getInstance().clear();
		LibManager.getInstance().clear();

		srcPath = "workspaceTS/src/org/ebayopensource/dsf/jstojava/codegen/";
		// add the folder into typespace
		s_groupPath = getGroupPath();
		s_controller = new BuildController();
		FileSuffix[] suffixes = { FileSuffix.vjo };
		s_controller.setSuffixes(suffixes);
		s_controller.setJstTypeLoader(getLoader());
		mgr = s_controller.loadTypes("CodeGenJsrDiff", s_groupPath,
				"workspaceTS/src");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		s_controller.clean();
	}

	private static IJstTypeLoader getLoader() {
		CodeGenTestJstTypeLoader loader = new CodeGenTestJstTypeLoader();
		FileSuffix[] suffixes = { FileSuffix.vjo };
		loader.setM_acceptedSuffixes(suffixes);
		return loader;
	}

	private static String getGroupPath() {

		URL url = CodeGenJsrDiff.class.getClassLoader().getResource(
				srcPath + "data/AType.vjo");
		if(url.getProtocol().startsWith("bundleresource")){
			url = new EclipseLocator().resolve(url);
		}
		
		String path = new File(url.getFile()).getAbsolutePath();
		path = url.getFile();
		if (path.startsWith("/"))
			path = path.substring(1);
		else if (path.startsWith("file:/"))
			path = path.substring("file:/".length());

		int end = path.indexOf("workspaceTS");
		String groupFullPath = path.substring(0, end);
		return groupFullPath;
	}

	interface Locator {
		URL resolve(URL url);
	}

	static class EclipseLocator implements Locator {
		public URL resolve(URL url) {
			try {
				return org.eclipse.core.runtime.FileLocator.resolve(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	public String generateTestString(String inputFileName) throws Exception {
		IJstType type = mgr.getTypeSpace().getGroup("CodeGenJsrDiff")
				.getEntity(inputFileName);
		StringWriter buffer = new StringWriter();
		JsrGenerator generator = new JsrGenerator(new PrintWriter(buffer),
				CodeStyle.PRETTY);
		generator.writeJsr(type);
		String typeStr = buffer.toString();
		buffer.close();
		return typeStr;
	}

	@Test
	// @Category({P1,UNIT,FAST})
	// @Description("Test generated js matches gold file.")
	public void verifyStrings() throws IOException {
		String testString = null;
		try {
			testString = generateTestString(m_input);
		} catch (FileNotFoundException fnfe) {
			System.err.println("File not found: " + m_input);
			assertTrue(false);
		} catch (Exception e) {
			System.err.println("This file " + m_input
					+ " should not throw Exception " + " with this message: "
					+ e.getMessage());
			e.printStackTrace();
			assertTrue(false);
		}

		// get gold file

		File iGold = new File(s_groupPath + srcPath + m_gold);
		if (iGold == null) {
			System.err.println("Gold file not found: " + m_gold);
			assertTrue("Gold file not found: " + m_gold, false);
		}
		String goldFullPath = iGold.getAbsolutePath();
		URL url = CodeGenJsrDiff.class.getClassLoader().getResource(
				srcPath + m_gold);
		String goldString = VjoParser.getContent(url);

		// if test string is different from gold string then print gold path
		if (!testString.equals(goldString)) {
			System.err.println("Gold file diff");
			System.err.println(goldFullPath);
		}

		assertEquals(goldString, testString);
	}

}
