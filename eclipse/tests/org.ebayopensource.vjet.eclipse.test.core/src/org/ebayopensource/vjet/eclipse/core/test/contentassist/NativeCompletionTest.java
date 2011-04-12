/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.contentassist;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.ui.preferences.PreferenceKey;

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;

public class NativeCompletionTest extends AbstractVjoModelTests {

	private static boolean isFirstRun = true;

	private static IProject project;

	// @Before
	public void setUp() {
		setWorkspaceSufix("1");
		project = getWorkspaceRoot().getProject(getTestProjectName());

		if (isFirstRun) {
			try {
				super.deleteResource(project);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			super.setUpSuite();
			isFirstRun = false;
		}
	}

	public void testBrowserCompliance() throws ModelException {

		ProjectScope prjScp = new ProjectScope(project);
		
		// target mozilla browser
		PreferenceKey isTargetedBrowserPredefKey = new PreferenceKey(
				VjetPlugin.PLUGIN_ID, "is_targeted_browser_Firefox/Mozilla");

		isTargetedBrowserPredefKey.setStoredValue(prjScp, "true", null);
		
		// support 1.x and later version
		PreferenceKey takeTargetedBrowserVerPredefKey = new PreferenceKey(
				VjetPlugin.PLUGIN_ID,
				"take_targeted_browser_ver_Firefox/Mozilla");

		takeTargetedBrowserVerPredefKey.setStoredValue(prjScp, "1.x and above",
				null);
		
		// no completions
		String[] names = new String[] {};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/TestDocument.js"));
		int position = lastPositionInFile("Document.inp", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		
		// support 2.x and later version
		takeTargetedBrowserVerPredefKey.setStoredValue(prjScp, "2.x and above",
				null);
		names = new String[] {"inputEncoding"};
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);

	}
	
	public void testInstanceOf() throws ModelException {
		String[] names = new String[] { "instanceof" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/Test.js"));
		int position = lastPositionInFile("this.vj$.Test.", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);

		position = lastPositionInFile("z.", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testTypeOf() throws ModelException {
		String[] names = new String[] { "typeof()" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/Test.js"));
		int position = lastPositionInFile("if(typeo", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testNan() throws ModelException {
		String[] names = new String[] { "NaN" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/Test.js"));
		int position = lastPositionInFile("test : function() {", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testMath() throws ModelException {
		String[] names = new String[] { "Math" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/TestMath.js"));
		int position = lastPositionInFile("Ma", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testMathProperties() throws ModelException {
		String[] names = new String[] { "E", "PI" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/TestMathProperties.js"));
		int position = lastPositionInFile("Math.", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);

		// defect 2008 - VJET - code completion - Math order of proposals /
		// wrong proposals

		String[] incorect = new String[] { "typeof", "decodeURI" };
		excludesNames(module, position, incorect,
				ICategoryRequestor.TEXT_CATEGORY);

	}

	public void testArray() throws ModelException {
		String[] names = new String[] { "Array" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/TestArray.js"));
		int position = lastPositionInFile("Arr", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testWatch() throws ModelException {
		String[] names = new String[] { "watch" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/TestWatch.js"));
		int position = lastPositionInFile("x.w", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testEval() throws ModelException {
		String[] names = new String[] { "eval" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/Test.js"));
		int position = lastPositionInFile("test : function() {", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * VJET - Invalid completions for eval(s).<cursor>
	 * 
	 * @throws ModelException
	 */
	// bug
	public void testEvalDefect2080() throws ModelException {
		String[] names = new String[] { "toLocalString", "toSource",
				"toString", "typeof", "typeof" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/TestEvalDefect.js"));
		int position = lastPositionInFile("eval(\"str\").t", module);
		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testInfinity() throws ModelException {
		String[] names = new String[] { "Infinity" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/Test.js"));
		int position = lastPositionInFile("test : function() {", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testString() throws ModelException {
		String[] names = new String[] { "String" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/TestString.js"));
		int position = lastPositionInFile("Str", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testObject() throws ModelException {
		String[] names = new String[] { "Object" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/TestObject.js"));
		int position = lastPositionInFile("Obj", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testDate() throws ModelException {
		String[] names = new String[] { "Date" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/TestDate.js"));
		int position = lastPositionInFile("Dat", module);
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testNumberMethods() throws ModelException {
		String[] names = new String[] {};
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("nativeCompletion/TestNumber.js"));
		int position = lastPositionInFile("-1.", module); // test on
		// -1.<cursor>
		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

}
