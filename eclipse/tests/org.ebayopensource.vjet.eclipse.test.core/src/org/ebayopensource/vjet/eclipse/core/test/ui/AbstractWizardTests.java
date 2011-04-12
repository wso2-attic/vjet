/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.ui;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRule;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.ClassBetterStartWithCapitalLetterRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.InvalidIdentifierNameRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.InvalidIdentifierNameWithKeywordRuleCtx;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.test.contentassist.TestConstants;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoWizardEngine;

public abstract class AbstractWizardTests extends AbstractVjoModelTests {

	protected void testVjoPackageWizard(IScriptFolder scriptFolder,
			String packageName) {
		try {
			VjoWizardEngine engine = new VjoWizardEngine();
			IScriptFolder pkg = (IScriptFolder) engine.createPackage(
					scriptFolder, packageName);

			assertNotNull(pkg);
			assertEquals("Wrong package name", packageName, pkg
					.getElementName());
		} catch (Exception e) {
			logError(e);
		}

	}

	protected void testVjoTypeWizard(IScriptFolder scriptFolder, String name,
			String superclass, boolean isAbstract,
			boolean isMethodStubGeneration) {
		try {
			VjoWizardEngine engine = new VjoWizardEngine();
			engine.createType(scriptFolder, name, superclass, isAbstract,
					isMethodStubGeneration, false);

			String path = scriptFolder.getElementName() + "/" + name;
			ISourceModule module = getSourceModule(getTestProjectName(), "src",
					path);
			assertNotNull(module);
			assertNotNull(module.getChildren());

			IJstType type = ((VjoSourceModule) module).getJstType();
			assertNotNull("JstType is null", type);

			// test super class
			if (superclass != null) {
				List<? extends IJstType> superClasses = type.getExtends();
				assertNotNull("No superclasses", superclass);
				assertEquals("Wrong number of superclasses", 1, superClasses
						.size());
				assertEquals(superclass, superClasses.get(0).getName());
			}
		} catch (Exception e) {
			logError(e);
		}
	}

	protected void testVjoInterfaceWizard(IScriptFolder scriptFolder,
			String name) {
		try {
			VjoWizardEngine engine = new VjoWizardEngine();
			engine.createInterface(scriptFolder, name);

			String path = scriptFolder.getElementName() + "/" + name;
			ISourceModule module = getSourceModule(getTestProjectName(), "src",
					path);
			assertNotNull(module);
		} catch (Exception e) {
			logError(e);
		}
	}

	protected void testVjoEnumWizard(IScriptFolder scriptFolder, String name) {
		try {
			VjoWizardEngine engine = new VjoWizardEngine();
			engine.createEnumeration(scriptFolder, name);

			String path = scriptFolder.getElementName() + "/" + name;
			ISourceModule module = getSourceModule(getTestProjectName(), "src",
					path);
			assertNotNull(module);
		} catch (Exception e) {
			logError(e);
		}
	}

	protected void testVjoMixinWizard(IScriptFolder scriptFolder, String name) {
		try {
			VjoWizardEngine engine = new VjoWizardEngine();
			engine.createMixin(scriptFolder, name);

			String path = scriptFolder.getElementName() + "/" + name;
			ISourceModule module = getSourceModule(getTestProjectName(), "src",
					path);
			assertNotNull(module);
		} catch (Exception e) {
			logError(e);
		}
	}

	/**
	 * Test whether the given name start with the lower case character.
	 * 
	 * @param name
	 * @return
	 */
	protected static String isLowerCaseFirstChar(String name) {

		String message = null;
		// Use the validation common util to validate the name whether
		// the name is lower case. Begin.
		IVjoSemanticRule<ClassBetterStartWithCapitalLetterRuleCtx> isLowerCaseFirstCharRule = VjoSemanticRuleRepo
				.getInstance().CLASS_BETTER_START_WITH_NONE_CAPITAL_LETTER;
		VjoSemanticProblem vjoSemanticProblem = isLowerCaseFirstCharRule
				.fire(new ClassBetterStartWithCapitalLetterRuleCtx(name));

		if (vjoSemanticProblem != null && vjoSemanticProblem.getID() != null) {
			message = vjoSemanticProblem.getID().getName();
		}
		// Use the validation common util to validate the name whether
		// the name is lower case. End.

		return message;
	}

	/**
	 * Test whether the given package name contains invalid word. Such as:
	 * blank, @, brace, empty string etc.
	 * @param name
	 * @return
	 */
	protected static boolean containInvalidCharInPackageName(String name) {
		String[] nameFields = name.split("\\.");
		for (String nameField : nameFields) {
			String message = containInvalidCharOrKeyword(nameField);
			if (message != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Test whether the given name contains invalid word. Such as: blank, @, brace,
	 * empty string etc. Validate it with the validation common logic codes.
	 * @param name
	 * @return
	 */
	protected static String containInvalidCharOrKeyword(String name) {

		String message = null;
		// Use the validation common util to validate the name whether
		// the name contain the invalid char. Begin.
		IVjoSemanticRule<InvalidIdentifierNameRuleCtx> invalidIdentifierNameRule = VjoSemanticRuleRepo
				.getInstance().INVALID_IDENTIFIER;
		VjoSemanticProblem vjoSemanticProblem = invalidIdentifierNameRule
				.fire(new InvalidIdentifierNameRuleCtx(name));

		if (vjoSemanticProblem != null && vjoSemanticProblem.getID() != null) {
			message = vjoSemanticProblem.getID().getName();
		}
		// Use the validation common util to validate the name whether
		// the name contain the invalid char. End.

		// Use the validation common util to validate whether the name
		// contain the keyword. Begin.
		IVjoSemanticRule<InvalidIdentifierNameWithKeywordRuleCtx> invalidIdentifierNameWithKeywordRule = VjoSemanticRuleRepo
				.getInstance().INVALID_IDENTIFIER_WITH_KEYWORD;
		vjoSemanticProblem = invalidIdentifierNameWithKeywordRule
				.fire(new InvalidIdentifierNameWithKeywordRuleCtx(name, false));
		if (vjoSemanticProblem != null && vjoSemanticProblem.getID() != null) {
			message = vjoSemanticProblem.getID().getName();
		}
		// Use the validation common util to validate whether the name
		// contain the keyword. End.

		return message;
	}

	private void logError(Exception e) throws AssertionError {
		VjetPlugin.getDefault().getLog().log(
				new Status(IStatus.ERROR, VjetPlugin.PLUGIN_ID, IStatus.ERROR,
						"Error during test", e));
		throw new AssertionError("Error during project creation");
	}

	protected String getProjectName() {
		return TestConstants.PROJECT_NAME_VJETPROJECT;
	}
}
