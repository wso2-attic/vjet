/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.core.tests.model;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.dltk.mod.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.mod.codeassist.ICompletionEngine;
import org.eclipse.dltk.mod.codeassist.ISelectionEngine;
import org.eclipse.dltk.mod.compiler.ISourceElementRequestor;
import org.eclipse.dltk.mod.compiler.problem.IProblemFactory;
import org.eclipse.dltk.mod.compiler.problem.IProblemReporter;
import org.eclipse.dltk.mod.core.AbstractLanguageToolkit;
import org.eclipse.dltk.mod.core.CompletionRequestor;
import org.eclipse.dltk.mod.core.ICallProcessor;
import org.eclipse.dltk.mod.core.ICalleeProcessor;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IModelStatus;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ISearchableEnvironment;
import org.eclipse.dltk.mod.core.ISourceElementParser;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.environment.IEnvironment;
import org.eclipse.dltk.mod.core.search.DLTKSearchParticipant;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;
import org.eclipse.dltk.mod.core.search.IMatchLocatorParser;
import org.eclipse.dltk.mod.core.search.SearchPattern;
import org.eclipse.dltk.mod.core.search.SearchRequestor;
import org.eclipse.dltk.mod.core.search.indexing.SourceIndexerRequestor;
import org.eclipse.dltk.mod.core.search.matching.MatchLocator;
import org.eclipse.dltk.mod.internal.core.util.Messages;

public class TestLanguageToolkit extends AbstractLanguageToolkit implements
		IDLTKLanguageToolkit {
	private static TestLanguageToolkit toolkit = new TestLanguageToolkit();

	public IProblemReporter createProblemReporter(IResource resource,
			IProblemFactory factory) {
		return null;
	}

	public IProblemFactory createProblemFactory() {
		return null;
	}

	public ISourceElementParser createSourceElementParser(
			ISourceElementRequestor requestor,
			IProblemReporter problemReporter, Map options) throws CoreException {
		TestSourceElementParser t = new TestSourceElementParser();
		t.setRequestor(requestor);
		return t;
	}

	public IStatus validateSourceModule(String name) {
		if (isScriptLikeFileName(name)) {
			return IModelStatus.VERIFIED_OK;
		}
		return new Status(IStatus.ERROR, "TEST", -1, MessageFormat.format(
				Messages.convention_unit_notScriptName, new String[] { "txt",
						"Test" }), null);
	}

	public boolean languageSupportZIPBuildpath() {
		return true;
	}

	public boolean validateSourcePackage(IPath path, IEnvironment env) {
		return true;
	}

	public String getNatureId() {
		return ModelTestsPlugin.TEST_NATURE;
	}

	public ICompletionEngine createCompletionEngine(
			ISearchableEnvironment environment, CompletionRequestor requestor,
			Map options, IScriptProject project) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPartitioningID() {
		return "";
	}

	public IMatchLocatorParser createMatchParser(MatchLocator locator) {
		// TODO Auto-generated method stub
		return null;
	}

	public IStatus validateSourceModule(IResource resource) {
		return validateSourceModule(resource.getName());
	}

	public IStatus validateSourceModule(IPath resource) {
		return validateSourceModule(resource.lastSegment());
	}

	public IStatus validateSourceModuleName(String str) {
		return validateSourceModule(str);
	}

	public String getEditorID(Object inputElement) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModuleDeclaration createFullAST(ISourceModule module) {
		return new ModuleDeclaration(0);
	}

	public ISelectionEngine createSelectionEngine(
			ISearchableEnvironment environment, Map options) {
		return null;
	}

	public SourceIndexerRequestor createSourceRequestor() {
		return new SourceIndexerRequestor();
	}

	public DLTKSearchParticipant createSearchParticipant() {
		// TODO Auto-generated method stub
		return null;
	}

	public static IDLTKLanguageToolkit getDefault() {
		return toolkit;
	}

	public MatchLocator createMatchLocator(SearchPattern pattern,
			SearchRequestor requestor, IDLTKSearchScope scope,
			SubProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	public ICalleeProcessor createCalleeProcessor(IMethod method,
			IProgressMonitor monitor, IDLTKSearchScope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	public ICallProcessor createCallProcessor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getLanguageFileExtensions() {
		return null;
	}

	public IType[] getParentTypes(IType type) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLanguageName() {
		return "Test";
	}

	public IStatus validateSourceModule(IModelElement parent, String name) {
		return validateSourceModule(name);
	}

	public boolean isScriptLikeFileName(String name) {
		return name.endsWith(".txt");
	}

	public String getLanguageContentType() {
		return "org.eclipse.dltk.mod.core.test.testContentType";
	}
}
