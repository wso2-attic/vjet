/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.contentassist;

import java.io.File;
import java.util.LinkedList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;

import org.ebayopensource.vjet.eclipse.codeassist.VjoCompletionEngine;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class VjoSteppingCodeCompletionTest extends AbstractVjoModelTests {

	private final static String TEST_FLD = "\\projects\\VJETProject\\src\\stepping\\codeCompletion";

	public void testSteppingCodeCompletion() {
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this);

		try {
			String[] files = new File(m_fixtureManager.getSandBox().getSandBoxDir().getAbsoluteFile() + getTestFolder()).list();
			for (String item : files) {
				if(isJSFile(item))
					processTest(item, ICategoryRequestor.TYPE_CATEGORY);
			}
		} catch (Exception e) {
			VjetPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, VjetPlugin.PLUGIN_ID,
							IStatus.ERROR, "Error during test", e));
			throw new AssertionError();
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	protected void processTest(String name, String category) throws Exception {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getProjectName(), "src", new Path(name));

		String content = module.getSource();
		for (int i = 0; i < content.length(); i++) {
			basicTest(module, i, category);
		}
	}

	protected void basicTest(IJSSourceModule module, int position,
			String category) {
		assertNotSame("Invalid file content, cant find position", -1, position);

		LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();

		VjoCompletionEngine c = createEngine(results, category, module);
		c.complete((ISourceModule) module, position, 0);
	}

	protected String getTestFolder() {
		return TEST_FLD.replace("\\", File.separator);
	}
	
	protected String getProjectName() {
		return TestConstants.PROJECT_NAME_VJETPROJECT;
	}
}
