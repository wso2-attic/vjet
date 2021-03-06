/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.core.tests.model;

import java.util.Hashtable;
import java.util.List;

import junit.framework.ComparisonFailure;

import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.WorkingCopyOwner;


public abstract class AbstractModelCompletionTests extends AbstractModelTests {
	public static List COMPLETION_SUITES = null;
	protected IScriptProject PROJECT;
	protected class CompletionResult {
		public String proposals;
		public String context;
		public int cursorLocation;
		public int tokenStart;
		public int tokenEnd;
	}
	Hashtable oldOptions;
	ISourceModule wc = null;

	public AbstractModelCompletionTests(String projectName, String name) {
		super(projectName, name);
	}

	public ISourceModule getWorkingCopy(String path, String source) throws ModelException {
		return super.getWorkingCopy(path, source, this.wcOwner, null);
	}

	protected CompletionResult complete(String path, String source, String completeBehind) throws ModelException {
		return this.complete(path, source, false, completeBehind);
	}

	protected CompletionResult complete(String path, String source, boolean showPositions, String completeBehind) throws ModelException {
		return this.complete(path, source, showPositions, completeBehind, null, null);
	}

	protected CompletionResult complete(String path, String source, boolean showPositions, String completeBehind, String tokenStartBehind,
			String token) throws ModelException {
		this.wc = getWorkingCopy(path, source);
		CompletionTestsRequestor2 requestor = new CompletionTestsRequestor2(true, false, showPositions);
		String str = this.wc.getSource();
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();
		int tokenStart = -1;
		int tokenEnd = -1;
		if (tokenStartBehind != null && token != null) {
			tokenStart = str.lastIndexOf(tokenStartBehind) + tokenStartBehind.length();
			tokenEnd = tokenStart + token.length() - 1;
		}
		this.wc.codeComplete(cursorLocation, requestor, this.wcOwner);
		CompletionResult result = new CompletionResult();
		result.proposals = requestor.getResults();
		result.context = requestor.getContext();
		result.cursorLocation = cursorLocation;
		result.tokenStart = tokenStart;
		result.tokenEnd = tokenEnd;
		return result;
	}

	protected CompletionResult contextComplete(ISourceModule cu, int cursorLocation) throws ModelException {
		CompletionTestsRequestor2 requestor = new CompletionTestsRequestor2(true, false, false, false);
		cu.codeComplete(cursorLocation, requestor, this.wcOwner);
		CompletionResult result = new CompletionResult();
		result.proposals = requestor.getResults();
		result.context = requestor.getContext();
		result.cursorLocation = cursorLocation;
		return result;
	}

	protected CompletionResult snippetContextComplete(IType type, String snippet, int insertion, int cursorLocation, boolean isStatic)
			throws ModelException {
		CompletionTestsRequestor2 requestor = new CompletionTestsRequestor2(true, false, false, false);
		type.codeComplete(snippet.toCharArray(), insertion, cursorLocation, null, null, null, isStatic, requestor, this.wcOwner);
		CompletionResult result = new CompletionResult();
		result.proposals = requestor.getResults();
		result.context = requestor.getContext();
		result.cursorLocation = cursorLocation;
		return result;
	}

	public void setUpSuite() throws Exception {
		super.setUpSuite();
		this.oldOptions = DLTKCore.getOptions();
		// waitUntilIndexesReady();
	}

	protected void setUp() throws Exception {
		super.setUp();
		this.wcOwner = new WorkingCopyOwner() {
		};
	}

	public void tearDownSuite() throws Exception {
		DLTKCore.setOptions(this.oldOptions);
		this.oldOptions = null;
		if (COMPLETION_SUITES == null) {
			deleteProject("Completion");
		} else {
			COMPLETION_SUITES.remove(getClass());
			if (COMPLETION_SUITES.size() == 0) {
				deleteProject("Completion");
				COMPLETION_SUITES = null;
			}
		}
		super.tearDownSuite();
	}

	protected void tearDown() throws Exception {
		if (this.wc != null) {
			this.wc.discardWorkingCopy();
			this.wc = null;
		}
		super.tearDown();
	}

	protected void assertResults(String expected, String actual) {
		try {
			assertEquals(expected, actual);
		} catch (ComparisonFailure c) {
			System.out.println(actual);
//			System.out.println();
			throw c;
		}
	}
}
