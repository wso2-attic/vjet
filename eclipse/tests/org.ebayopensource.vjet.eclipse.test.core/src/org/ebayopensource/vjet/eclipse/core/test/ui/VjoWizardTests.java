/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.mod.internal.ui.workingsets.WorkingSetMessages;
import org.eclipse.dltk.mod.ui.wizards.ProjectWizardFirstPage;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.osgi.framework.Bundle;

import org.ebayopensource.dsf.jst.reserved.JsCoreKeywords;
import org.ebayopensource.dsf.common.FileUtils;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjetWizardMessages;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoClassCreationPage;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoClassCreationWizard;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoEnumCreationWizard;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoInterfaceCreationWizard;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoMixinCreationWizard;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoNameValidator;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoNewPackageWizardPage;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoSourceModulePage;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class VjoWizardTests extends AbstractWizardTests {

	private static boolean isFirstRun = true;

	private static IScriptProject project;

	public void setUp() throws Exception {
		super.setUp();
		if (isFirstRun) {
			IProject pr = getWorkspaceRoot().getProject(getTestProjectName());
			super.deleteResource(pr);
			project = setUpScriptProjectTo(getTestProjectName(), PROJECT_NAME);
			isFirstRun = false;
		}
	}

	public void testPackageTemplate() throws Exception {
		String packageName = "a.b.c.foo";
		IScriptFolder scriptFolder = project.getScriptFolders()[0];
		VjoNewPackageWizardPage wizard = new VjoNewPackageWizardPage();

		IProjectFragment fragment = (IProjectFragment) scriptFolder
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		wizard.setProjectFragmentRoot(fragment, false);
		wizard.setPackageText(packageName, true);
		wizard.createPackage(null);
		IScriptFolder pkg = (IScriptFolder) wizard.getNewScriptFolder();
		Assert.assertEquals("package name is not equal. Expected : "
				+ packageName + " Actual = " + pkg.getElementName(),
				packageName, pkg.getElementName());
	}

	public void testMtypeTemplate() throws Exception {
		String typeName = "M.js";
		String packageName = "test";
		IScriptFolder scriptFolder = CodeassistUtils.getScriptFolder(project,
				packageName);
		VjoMixinCreationWizard wizard = new VjoMixinCreationWizard();
		wizard.addPages();
		wizard.initializeData();
		VjoSourceModulePage page = (VjoSourceModulePage) wizard.getPages()[0];
		IProjectFragment fragment = (IProjectFragment) scriptFolder
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, true);
		page.setFileText(typeName);

		wizard.finishPage(null);
		String contents = FileUtils.readStream(((IFile) wizard
				.getCreatedElement().getResource()).getContents());
		System.out.println(contents);
		assertData("mtype.txt", contents);
	}

	public void testEtypeTemplate() throws Exception {
		String typeName = "E.js";
		String packageName = "test";
		IScriptFolder scriptFolder = CodeassistUtils.getScriptFolder(project,
				packageName);
		VjoEnumCreationWizard wizard = new VjoEnumCreationWizard();
		wizard.addPages();
		wizard.initializeData();
		VjoSourceModulePage page = (VjoSourceModulePage) wizard.getPages()[0];

		IProjectFragment fragment = (IProjectFragment) scriptFolder
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, true);
		page.setFileText(typeName);

		wizard.finishPage(null);
		String contents = FileUtils.readStream(((IFile) wizard
				.getCreatedElement().getResource()).getContents());
		System.out.println(contents);
		assertData("etype.txt", contents);
	}

	public void testItypeTemplate() throws Exception {
		String name = "I.js";
		String packageName = "test";
		IScriptFolder scriptFolder = CodeassistUtils.getScriptFolder(project,
				packageName);

		VjoInterfaceCreationWizard wizard = new VjoInterfaceCreationWizard();
		wizard.addPages();
		wizard.initializeData();
		VjoSourceModulePage page = (VjoSourceModulePage) wizard.getPages()[0];
		IProjectFragment fragment = (IProjectFragment) scriptFolder
				.getAncestor(IModelElement.PROJECT_FRAGMENT);

		page.setProjectFragmentRoot(fragment, true);
		page.setFileText(name);
		wizard.finishPage(null);
		String contents = FileUtils.readStream(((IFile) wizard
				.getCreatedElement().getResource()).getContents());
		System.out.println(contents);
		assertData("itype.txt", contents);
	}

	public void testAtypeTemplate() throws Exception {
		String typeName = "foo.js";
		String packageName = "test";
		IScriptFolder scriptFolder = CodeassistUtils.getScriptFolder(project,
				packageName);
		VjoClassCreationWizard wizard = new VjoClassCreationWizard();
		wizard.addPages();
		VjoClassCreationPage page = (VjoClassCreationPage) wizard.getPages()[0];

		IProjectFragment fragment = (IProjectFragment) scriptFolder
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, false);
		page.setFileText(typeName);
		page.initializeSuperClassField();
		String superclass = "MyFoo";
		if (superclass != null)
			page.setSuperclassFieldText(superclass);
		page.setAbstractButton(true);
		page.setMethodStubGeneration(false);
		page.setConstructorStubGeneration(false);

		wizard.finishPage(null);
		String contents = FileUtils.readStream(((IFile) wizard
				.getCreatedElement().getResource()).getContents());
		System.out.println(contents);
		assertData("atype.txt", contents);
	}

	public void testAtypeTemplateWithConstructor() throws Exception {
		String typeName = "foo.js";
		String packageName = "test";
		IScriptFolder scriptFolder = CodeassistUtils.getScriptFolder(project,
				packageName);
		VjoClassCreationWizard wizard = new VjoClassCreationWizard();
		wizard.addPages();
		VjoClassCreationPage page = (VjoClassCreationPage) wizard.getPages()[0];

		IProjectFragment fragment = (IProjectFragment) scriptFolder
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, false);
		page.setFileText(typeName);
		page.initializeSuperClassField();
		page.setAbstractButton(true);
		page.setMethodStubGeneration(false);
		page.setConstructorStubGeneration(true);

		wizard.finishPage(null);
		String contents = FileUtils.readStream(((IFile) wizard
				.getCreatedElement().getResource()).getContents());
		System.out.println(contents);
		assertData("atypeWithConstructor.txt", contents);
	}

	public void testAtypeTemplateWithConstructorMain() throws Exception {
		String typeName = "foo.js";
		String packageName = "test";
		IScriptFolder scriptFolder = CodeassistUtils.getScriptFolder(project,
				packageName);
		VjoClassCreationWizard wizard = new VjoClassCreationWizard();
		wizard.addPages();
		VjoClassCreationPage page = (VjoClassCreationPage) wizard.getPages()[0];

		IProjectFragment fragment = (IProjectFragment) scriptFolder
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, false);
		page.setFileText(typeName);
		page.initializeSuperClassField();
		page.setAbstractButton(true);
		page.setMethodStubGeneration(true);
		page.setConstructorStubGeneration(true);

		wizard.finishPage(null);
		String contents = FileUtils.readStream(((IFile) wizard
				.getCreatedElement().getResource()).getContents());
		System.out.println(contents);
		assertData("atypeWithMain.txt", contents);
	}

	public void testCtypeTemplate() throws Exception {
		String typeName = "foo.js";
		String packageName = "test";
		IScriptFolder scriptFolder = CodeassistUtils.getScriptFolder(project,
				packageName);
		VjoClassCreationWizard wizard = new VjoClassCreationWizard();
		wizard.addPages();
		VjoClassCreationPage page = (VjoClassCreationPage) wizard.getPages()[0];

		IProjectFragment fragment = (IProjectFragment) scriptFolder
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, false);
		page.setFileText(typeName);
		page.initializeSuperClassField();
		String superclass = "MyFoo";
		if (superclass != null)
			page.setSuperclassFieldText(superclass);
		page.setAbstractButton(false);
		page.setMethodStubGeneration(false);
		page.setConstructorStubGeneration(false);

		wizard.finishPage(null);
		String contents = FileUtils.readStream(((IFile) wizard
				.getCreatedElement().getResource()).getContents());
		System.out.println(contents);
		assertData("ctype.txt", contents);
	}

	public void testCtypeTemplateWithConstructor() throws Exception {
		String typeName = "foo.js";
		String packageName = "test";
		IScriptFolder scriptFolder = CodeassistUtils.getScriptFolder(project,
				packageName);
		VjoClassCreationWizard wizard = new VjoClassCreationWizard();
		wizard.addPages();
		VjoClassCreationPage page = (VjoClassCreationPage) wizard.getPages()[0];

		IProjectFragment fragment = (IProjectFragment) scriptFolder
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, false);
		page.setFileText(typeName);
		page.initializeSuperClassField();
		page.setAbstractButton(false);
		page.setMethodStubGeneration(false);
		page.setConstructorStubGeneration(true);

		wizard.finishPage(null);
		String contents = FileUtils.readStream(((IFile) wizard
				.getCreatedElement().getResource()).getContents());
		System.out.println(contents);
		assertData("ctypeWithConstructor.txt", contents);
	}

	public void testCtypeTemplateWithConstructorMain() throws Exception {
		String typeName = "foo.js";
		String packageName = "test";
		IScriptFolder scriptFolder = CodeassistUtils.getScriptFolder(project,
				packageName);
		VjoClassCreationWizard wizard = new VjoClassCreationWizard();
		wizard.addPages();
		VjoClassCreationPage page = (VjoClassCreationPage) wizard.getPages()[0];

		IProjectFragment fragment = (IProjectFragment) scriptFolder
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, false);
		page.setFileText(typeName);
		page.initializeSuperClassField();
		page.setAbstractButton(false);
		page.setMethodStubGeneration(true);
		page.setConstructorStubGeneration(true);

		wizard.finishPage(null);
		String contents = FileUtils.readStream(((IFile) wizard
				.getCreatedElement().getResource()).getContents());
		System.out.println(contents);
		assertData("ctypeWithMain.txt", contents);
	}

	public void testCtypeTemplateWithMain() throws Exception {
		String typeName = "foo.js";
		String packageName = "test";
		IScriptFolder scriptFolder = CodeassistUtils.getScriptFolder(project,
				packageName);
		VjoClassCreationWizard wizard = new VjoClassCreationWizard();
		wizard.addPages();
		VjoClassCreationPage page = (VjoClassCreationPage) wizard.getPages()[0];

		IProjectFragment fragment = (IProjectFragment) scriptFolder
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, false);
		page.setFileText(typeName);
		page.initializeSuperClassField();
		page.setAbstractButton(false);
		page.setMethodStubGeneration(true);
		page.setConstructorStubGeneration(false);

		wizard.finishPage(null);
		String contents = FileUtils.readStream(((IFile) wizard
				.getCreatedElement().getResource()).getContents());
		System.out.println(contents);
		assertData("ctypeWithMainStub.txt", contents);
	}

	// public void testCtypeTemplateSatisfierWithIType() throws Exception {
	// // Generate the Itype file
	// String typeName = "I.js";
	// String packageName = "test";
	// IScriptFolder folder = CodeassistUtils.getScriptFolder(project,
	// packageName);
	// IJstType type = null;
	// try {
	// VjoWizardEngine engine = new VjoWizardEngine();
	// engine.createInterface(folder, typeName);
	//
	// String path = folder.getElementName() + "/" + typeName;
	// ISourceModule module = getSourceModule(getTestProjectName(), "src",
	// path);
	// assertNotNull(module);
	// type = ((VjoSourceModule) module).getJstType();
	// } catch (Exception e) {
	// }
	//
	// typeName = "foo.js";
	// // packageName = "test";
	// IScriptFolder scriptFolder = CodeassistUtils.getScriptFolder(project,
	// packageName);
	// VjoClassCreationWizard wizard = new VjoClassCreationWizard();
	// wizard.addPages();
	// VjoClassCreationPage page = (VjoClassCreationPage) wizard.getPages()[0];
	//
	// IProjectFragment fragment = (IProjectFragment) scriptFolder
	// .getAncestor(IModelElement.PROJECT_FRAGMENT);
	// page.addSuperInterface(type);
	// page.setProjectFragmentRoot(fragment, false);
	// page.setFileText(typeName);
	// page.initializeSuperClassField();
	// page.setAbstractButton(false);
	// page.setMethodStubGeneration(true);
	// page.setConstructorStubGeneration(false);
	//
	// wizard.finishPage(null);
	// String contents = FileUtils.readStream(((IFile) wizard
	// .getCreatedElement().getResource()).getContents());
	// System.out.println(contents);
	// assertData("ctypeWithMainStub.txt", contents);
	// }

	public void testPackage() throws Exception {
		String packageName = "foo";
		IScriptFolder folder = project.getScriptFolders()[0]; // root folder
		testVjoPackageWizard(folder, packageName);
	}

	public void testType() throws ModelException {
		String typeName = "foo.js";
		String packageName = "test";
		// String superClass = "MyFoo";
		IScriptFolder folder = CodeassistUtils.getScriptFolder(project,
				packageName);
		testVjoTypeWizard(folder, typeName, null, false, false);
	}

	public void testInterface() throws ModelException {
		String typeName = "I.js";
		String packageName = "test";
		IScriptFolder folder = CodeassistUtils.getScriptFolder(project,
				packageName);
		testVjoInterfaceWizard(folder, typeName);
	}

	public void testEnum() throws ModelException {
		String typeName = "E.js";
		String packageName = "test";
		IScriptFolder folder = CodeassistUtils.getScriptFolder(project,
				packageName);
		testVjoEnumWizard(folder, typeName);
	}

	public void testMixin() throws ModelException {
		String typeName = "M.js";
		String packageName = "test";
		IScriptFolder folder = CodeassistUtils.getScriptFolder(project,
				packageName);
		testVjoMixinWizard(folder, typeName);
	}

	public void testTypeSuperclass() throws ModelException {
		String typeName = "TypeA.js";
		String packageName = "test";
		String superClass = "test.testTypeA";
		IScriptFolder folder = CodeassistUtils.getScriptFolder(project,
				packageName);
		// testVjoTypeWizard(folder, typeName, superClass, false, false);
		//
		// // check the source
		// ISourceModule module = getSourceModule(getTestProjectName(), "src",
		// "test/TypeA.js");
		// String source = module.getSource();
		// assertNotNull(source);
		// assertNotSame(-1, source.indexOf("needs(\"" + superClass + "\")"));
		// assertNotSame(-1, source.indexOf("inherits(\"" + superClass +
		// "\")"));
	}

	public void testTypeMethodStub() throws ModelException {
		String typeName = "TypeB.js";
		String packageName = "test";
		IScriptFolder folder = CodeassistUtils.getScriptFolder(project,
				packageName);
		// testVjoTypeWizard(folder, typeName, null, false, true);
		//
		// // check the source
		// ISourceModule module = getSourceModule(getTestProjectName(), "src",
		// "test/TypeB.js");
		// String source = module.getSource();
		// assertNotNull(source);
		// // get method stub
		// int pos = source.indexOf("main");
		// String methodStub = source.substring(pos, source.indexOf("\n", pos));
		// assertNotNull(methodStub);
		// assertEquals(methodStub.trim(),
		// "main: function() { //< public void main (String ... arguments)");
	}

	/**
	 * Test whether the given string contains the java/vjo key word.
	 */
	public void testKeywordName() {
		String keyWordNameMessage = containInvalidCharOrKeyword("this");
		assertNotNull(keyWordNameMessage);

		// Test vjo keyword --vj$
		keyWordNameMessage = null;
		keyWordNameMessage = containInvalidCharOrKeyword(VjoKeywords.VJ$);
		assertNotNull(keyWordNameMessage);

		// Test java keyword --vj$
		keyWordNameMessage = null;
		keyWordNameMessage = containInvalidCharOrKeyword("interface");
		assertNotNull(keyWordNameMessage);

		// Test JS keyword --case
		keyWordNameMessage = null;
		keyWordNameMessage = containInvalidCharOrKeyword(JsCoreKeywords.CASE);
		assertNotNull(keyWordNameMessage);
	}

	/**
	 * Test whether the given package name contains the java/vjo key word.
	 */
	public void testKeywordNameOfPackage() {
		boolean keyWordNameInPackage = containInvalidCharInPackageName("x.y.z.this");
		assertTrue(keyWordNameInPackage);

		keyWordNameInPackage = false;
		keyWordNameInPackage = containInvalidCharInPackageName("x.y.z.this.a.b.c");
		assertTrue(keyWordNameInPackage);
	}

	/**
	 * Test whether the given package name starts or ends with dot.
	 */
	public void testNameOfPackageStartOrEndWithDot() {
		IStatus testNameOfPackageStartOrEndWithDot = VjoNameValidator
				.startOrEndWithDot(".x.y.z");

		assertNotNull(testNameOfPackageStartOrEndWithDot);
		testNameOfPackageStartOrEndWithDot = VjoNameValidator
				.startOrEndWithDot("x.y.z.");
		assertNotNull(testNameOfPackageStartOrEndWithDot);
	}

	/**
	 * Test whether the given package name contains consecutive dot.
	 */
	public void testConsecutiveDotsName() {
		IStatus testConsecutiveDotsName = VjoNameValidator
				.consecutiveDotsName("a..bc");
		assertNotNull(testConsecutiveDotsName);
	}

	/**
	 * Test whether the given name start with the lower case character.
	 */
	public void testLowerCaseName() {
		String lowerCaseNameMessage = isLowerCaseFirstChar("this");
		assertNotNull(lowerCaseNameMessage);
	}

	/**
	 * On wizard page, will prompt the warning not error when type name starts
	 * with lower case.
	 */
	public void testLowerCaseNameWarning() {
		IStatus status = VjoSourceModulePage
				.getLowerCaseFirstCharWarning("this");
		assertEquals(IStatus.WARNING, status.getSeverity());
	}

	/**
	 * Test whether the given name contains invalid word. Such as: blank, @, brace,
	 * empty string etc.
	 */
	public void testInvalidTypeName() {
		// Test file name that contains the blank
		String keyWordNameMessage = containInvalidCharOrKeyword("my Name");
		assertNotNull(keyWordNameMessage);

		// Test file name that contains the invalid character--@
		keyWordNameMessage = null;
		keyWordNameMessage = containInvalidCharOrKeyword("my@Name");
		assertNotNull(keyWordNameMessage);

		// Test file name that contains the invalid character--(
		keyWordNameMessage = null;
		keyWordNameMessage = containInvalidCharOrKeyword("my(Name");
		assertNotNull(keyWordNameMessage);

		// Test file name that is empty string
		keyWordNameMessage = null;
		keyWordNameMessage = containInvalidCharOrKeyword("");
		assertNotNull(keyWordNameMessage);
	}

	/**
	 * @Test
	 * //@Description("Bug8610: Test whether the given path is unexisting and the
	 *                        returned message level.")
	 */
	public void testUnexistingProjectLocation() {
		// Test unexisting project location.
		IPath path = new Path("c:\randomFolderNameWithBlank and_");
		Object[] values = ProjectWizardFirstPage
				.validateNonExistingProjectPath(path);
		assertNotNull(values);

		assertEquals(
				"There are 2 returned values after validating the unexisting path",
				values.length, 2);

		assertEquals(
				"The warning message is not correct",
				(String) values[0],
				NewWizardMessages.ScriptProjectWizardFirstPage_Unexist_Location_message);

		assertEquals("The warning message is not correct",
				((Integer) values[1]).intValue(), IMessageProvider.WARNING);

	}

	/**
	 * @Test
	 * //@Description("Bug6386: VJET Wizard: VJO settings should be VJET
	 *                        settings.")
	 */
	public void testBug6386() {
		String title = NewWizardMessages.ScriptCapabilityConfigurationPage_title;
		String desc = NewWizardMessages.ScriptCapabilityConfigurationPage_description;

		assertEquals("VJET Settings", title);

		assertEquals("Define the VJET build settings.", desc);

	}

	/**
	 * @Test
	 * //@Description("Bug3278: 1-Search->Text->Workspace...->New...->VJET Working
	 *                        Set. 2-Click 'VJET Working Set' 3-Click Next.
	 *                        Title is 'VJET Working Set'. ")
	 */
	public void testBug3278() {

		// Test the working set description on the page.
		String title = WorkingSetMessages.ScriptWorkingSetPage_title;
		assertEquals("VJET Working Set", title);

		// Test the working set name in the
		Bundle bundle = Platform.getBundle("org.eclipse.dltk.mod.ui");
		URL url = bundle.getEntry("plugin.properties");
		InputStream inputStream = null;
		Properties properties = null;
		try {
			inputStream = url.openStream();
			properties = new Properties();
			properties.load(inputStream);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
		} catch (Exception e) {
			System.err
					.println("Error loading properties from plugin.properties");
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
		if (properties != null) {
			String workingSetName = properties
					.getProperty("ScriptWorkingSetPage.name");
			assertEquals("VJET Working Set", workingSetName);
		}
		// Bundle bundle = Platform.getBundle("org.eclipse.dltk.mod.ui");
		// URL url = bundle.getEntry("/plugin.properties");
		// if (url != null) {
		// try {
		// url = FileLocator.toFileURL(url);
		// BufferedReader reader = new BufferedReader(
		// new InputStreamReader(url.openStream()));
		// StringBuffer buffer = new StringBuffer(200);
		// String line = reader.readLine();
		// while (line != null) {
		// buffer.append(line);
		// buffer.append('\n');
		// line = reader.readLine();
		// }
		// } catch (IOException ex) {
		// DLTKUIPlugin.log(ex);
		// }
		// }
	}

	/**
	 * @Test
	 * //@Description("Bug8492: VJET Wizard: After creating project and package in
	 *                        package explorer, creating a type in
	 *                        scriptexplorer, prefixes the source folder to
	 *                        package. ")
	 */
	public void testBug8492() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();

		for (int i = 0; i < projects.length; i++) {

			IProject project = projects[i];

			String errorMessage = "";
			try {
				IProjectNature nature = project.getNature(VjoNature.NATURE_ID);
				if (!project.hasNature(VjoNature.NATURE_ID)) {
					errorMessage = NewWizardMessages.NewContainerWizardPage_warning_NotAScriptProject;
				} else {

					errorMessage = "This is a VJO project";
				}

			} catch (CoreException e) {
				DLTKCore.error(e.toString(), e);
			}
			assertEquals("This is a VJO project", errorMessage);
		}
	}

	/**
	 * @throws MalformedURLException
	 * @Test
	 * //@Description("Bug6134: VJET Prefs: VJET->BuildPath should not have error
	 *                        message.")
	 */
	// public void testBug6134() throws MalformedURLException {
	// IWorkspace workspace = ResourcesPlugin.getWorkspace();
	// IProject[] projects = workspace.getRoot().getProjects();
	//
	// for (int i = 0; i < projects.length; i++) {
	//
	// IProject project = projects[i];
	// String errorMessage = "";
	// IResource member = project.findMember(".buildpath");
	// URL url = member.getRawLocationURI().toURL();
	// if (url != null) {
	// try {
	// url = FileLocator.toFileURL(url);
	// BufferedReader reader = new BufferedReader(
	// new InputStreamReader(url.openStream()));
	// StringBuffer buffer = new StringBuffer(200);
	// String line = reader.readLine();
	// while (line != null) {
	// buffer.append(line);
	// buffer.append('\n');
	// line = reader.readLine();
	// }
	// } catch (IOException ex) {
	// DLTKUIPlugin.log(ex);
	// }
	// }
	//
	// assertEquals("This is a VJO project", errorMessage);
	// }
	// }
	/**
	 * @throws Exception
	 * @throws MalformedURLException
	 * @Test
	 * //@Description("Bug2138: 1. New JavaScript File doesn't make any sense
	 *                        let's remove it 2. New JavaScript project doesn't
	 *                        make any sense let's remove it 3. Make the order
	 *                        of the wizards be similar to Java VJO Package VJO
	 *                        Class VJO Interface VJO Enum - to be added VJO
	 *                        Mixin - to be added VJO Source Folder Folder File
	 *                        4. If you are in the JavaScript/JavaScript
	 *                        browsing perspective these should be provided in
	 *                        the new menu at the top in the order specified
	 *                        above. ")
	 */
	public void testBug2138() throws Exception {
		// Test the VJET JS perspective menu order.
		final String JS_PERPECTIVE_ID = "org.eclipse.dltk.mod.javascript.ui.JavascriptPerspective";
		verifyWizardMenusAndSequenceInPerspective(JS_PERPECTIVE_ID);

		// Test the VJET JS Browsing perspective menu order.
		String JS_BROWSING_PERPECTIVE_ID = "org.ebayopensource.vjet.eclipse.ui.JavascriptBrowsingPerspective";
		verifyWizardMenusAndSequenceInPerspective(JS_BROWSING_PERPECTIVE_ID);
	}

	public void verifyWizardMenusAndSequenceInPerspective(String perspectiveID)
			throws Exception {

		String[] expectedMenusAndSequence = new String[] {
				"org.ebayopensource.vjet.eclipse.ui.VJETProjectWizard",
				"org.ebayopensource.vjet.eclipse.ui.JsFileCreation",
				"org.ebayopensource.vjet.eclipse.ui.VjoPackageCreationWizard",
				"org.ebayopensource.vjet.eclipse.ui.VjoClassCreationWizard",
				"org.ebayopensource.vjet.eclipse.ui.VjoInterfaceCreationWizard",
				"org.ebayopensource.vjet.eclipse.ui.VjoEnumCreationWizard",
				"org.ebayopensource.vjet.eclipse.ui.VjoMixinCreationWizard",
				"org.ebayopensource.vjet.eclipse.ui.VjoOTypeCreationWizard",
				"org.ebayopensource.vjet.eclipse.ui.NewSourceFolderCreationWizard",
				"org.eclipse.ui.wizards.new.folder",
				"org.eclipse.ui.wizards.new.file",
				"org.eclipse.ui.editors.wizards.UntitledTextFileWizard" };

		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		try {
			IWorkbenchPage page = workbench.showPerspective(perspectiveID,
					window);
			IPerspectiveDescriptor pd = workbench.getActiveWorkbenchWindow()
					.getActivePage().getPerspective();
			assertEquals(pd.getId(), perspectiveID);

			// Reset this perspective to ensure that get the latest shortcuts.
			page.resetPerspective();

			String[] allNewWizards = page.getNewWizardShortcuts();
			for (int i = 0; i < allNewWizards.length; i++) {
				assertEquals("The menu" + allNewWizards[i]
						+ " is not matched with the expected menu list",
						allNewWizards[i], expectedMenusAndSequence[i]);
			}
		} catch (WorkbenchException e) {
			assertFalse(true);
		}

	}

	/**
	 * There is another windowtester test
	 * case--"org.ebayopensource.vjet.eclipse.ui.test.fragment.CTypeTest" for this bug.
	 * 
	 * @Test
	 * //@Description("Bug2856: VJET Wizard: VJO Interface and Java Interface
	 *                        diffs.")
	 */
	public void testBug2856() {
		String interfacetitle = VjetWizardMessages.InterfaceCreationWizard_title;
		String desc = VjetWizardMessages.InterfaceCreationWizard_page_description;
		String nameDesc = VjetWizardMessages.VjoSourceModulePage_file;

		assertEquals("New VJET Interface", interfacetitle);
		assertEquals("Create a new VJET interface.", desc);
		assertEquals("Na&me:", nameDesc);
	}

	/**
	 * @Test
	 * //@Description("Bug6519: VJET: VJET OType and Source Folder missing from
	 *                        VJET JS Browsing File menu ")
	 */
	public void testBug6519() throws Exception {
		// Test the VJET JS Browsing perspective menus.
		String JS_BROWSING_PERPECTIVE_ID = "org.ebayopensource.vjet.eclipse.ui.JavascriptBrowsingPerspective";
		verifyWizardMenusAndSequenceInPerspective(JS_BROWSING_PERPECTIVE_ID);
	}

	private void assertData(String file, String actual) throws IOException {
		String expected = FileUtils.getResourceAsString(file);
		Assert.assertEquals("results are not equal. Expected : " + expected
				+ " Actual = " + actual, expected, actual);
	}

}
