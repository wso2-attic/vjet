/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.contentassist;

import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.mod.ui.templates.ScriptTemplateProposal;
import org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.source.ISourceViewer;

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoTemplateCompletionProposalComputer;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class TemplateCompletionTests extends AbstractVjoModelTests {

	public void setUp() throws Exception {
		super.setUp();
//		TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();
//		Collection<IJstType> types = mgr.getAllTypes();
//		for (IJstType type : types) {
//			if (type.getName() == null)
//				continue;
//			RemoveTypeEvent removeEvent = new RemoveTypeEvent(new TypeName(
//					getProjectName(), type.getName()));
//			mgr.processEvent(removeEvent);
//		}
	}

	@Override
	protected String getProjectName() {
		return TestConstants.PROJECT_NAME_VJETPROJECT;
	}

	protected int lastPositionInFile(String string, String moduleName)
			throws ModelException {
		String content = ((IJSSourceModule) getSourceModule(
				TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(
						moduleName))).getSource();
		if (string == null)
			return content.length();
		int position = content.lastIndexOf(string);
		if (position >= 0) {
			return position + string.length();
		}
		return -1;
	}

	private void basicTemplateTest(String mname, int position,
			String[] compNames) throws ModelException {
		assertNotSame("Invalid file content, cant find position", -1, position);

		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(mname));
		VjoTemplateCompletionProposalComputer computer = new VjoTemplateCompletionProposalComputer();
		ScriptEditor vEditor = (ScriptEditor) getEditor(module);
		ISourceViewer viewer = vEditor.getViewer();

		ScriptContentAssistInvocationContext context = new ScriptContentAssistInvocationContext(
				viewer, position, vEditor, VjoNature.NATURE_ID) {
			protected CompletionProposalLabelProvider createLabelProvider() {
				return null;
			}
		};

		List<ScriptTemplateProposal> completions = computer
				.computeCompletionProposals(context, null);

		compareTemplateCompletions(completions, compNames, true);
	}

	/**
	 * Test function template
	 * 
	 * @throws ModelException
	 */
	public void testFunctionTemplate() throws ModelException {
		String mname = "static/Ab.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, mname);
		try {
			String[] names = new String[] { "function - simple function", 
					"foreach - iterate over array", "for - iterate over array" };
			int position = lastPositionInFile("  f", mname);
			basicTemplateTest(mname, position, names);

		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	/**
	 * Test try block template
	 * 
	 * @throws ModelException
	 */
	public void testTryTemplate() throws ModelException {
		String mname = "static/Bb.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, mname);
		try {
			String[] names = new String[] { "try - try catch block ",
					"try - try catch finally block", "try - try finally block" };
			int position = lastPositionInFile("tr", mname);
			basicTemplateTest(mname, position, names);

		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}
	
	
	public void testWithStatement() throws ModelException {
		String mname = "static/Bb.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, mname);
		try {
			String[] names = new String[] {"with - With expression"};
			int position = lastPositionInFile("	wi", mname);
			basicTemplateTest(mname, position, names);

		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}
	
	/**
	 * Test for loop template
	 * 
	 * @throws ModelException
	 */
	public void testForTemplate() throws ModelException {
		String mname = "static/Bb.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, mname);
		try {
			String[] names = new String[] {"foreach - iterate over array", "for - iterate over array"};
			int position = lastPositionInFile("	fo", mname);
			basicTemplateTest(mname, position, names);

		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}
	
	/**
	 * Test if condition template
	 * 
	 * @throws ModelException
	 */
	public void testIfTemplate() throws ModelException {
		String mname = "static/Bb.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, mname);
		try {
			String[] names = new String[] { "if - if statement", "ifelse - if - else statement" };
			int position = lastPositionInFile("	if", mname);
			basicTemplateTest(mname, position, names);

		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}
	
	/**
	 * Test while loop template
	 * 
	 * @throws ModelException
	 */
	public void testWhileTemplate() throws ModelException {
		String mname = "static/Bb.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, mname);
		try {
			String[] names = new String[] { "while - while loop" };
			int position = lastPositionInFile("	wh", mname);
			basicTemplateTest(mname, position, names);

		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}
	
	/**
	 * Test do loop template
	 * 
	 * @throws ModelException
	 */
	public void testDoTemplate() throws ModelException {
		String mname = "static/Bb.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, mname);
		try {
			String[] names = new String[] { "do - do-while" };
			int position = lastPositionInFile("	do", mname);
			basicTemplateTest(mname, position, names);

		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}
	
}
