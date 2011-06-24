/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.core;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.ICalleeProcessor;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;
import org.eclipse.dltk.mod.core.search.SearchPattern;

/**
 * This class copy of org.eclipse.dltk.mod.javascript.internal.core.codeassist.
 * JavaScriptCalleeProcessor
 * 
 * 
 * 
 * 
 */
public class VjoCalleeProcessor implements ICalleeProcessor {

	protected static final int EXACT_RULE = SearchPattern.R_EXACT_MATCH
			| SearchPattern.R_CASE_SENSITIVE;

	// private Map fSearchResults = new HashMap();

	private IMethod method;

	public VjoCalleeProcessor(IMethod method, IProgressMonitor monitor,
			IDLTKSearchScope scope) {
		this.method = method;
	}

	public Map doOperation() {
		try {
			// String methodSource = method.getSource();
			// VjoCalleeAnalyzerVisitor analyzerVisitor=new
			// VjoCalleeAnalyzerVisitor();

			if (method.exists()) {

				// CompilationUnit cu=
				// CallHierarchy.getCompilationUnitNode(getMember(), true);
				IJstType jstType = getJstType(method);

				IJstMethod jstMethod = CodeassistUtils.getJstMethod(jstType,
						method);
				if (jstType != null && jstMethod != null) {
					VjoCalleeAnalyzerVisitor visitor = new VjoCalleeAnalyzerVisitor(
							method, jstType, new NullProgressMonitor());

					jstMethod.accept(visitor);
					return visitor.getCallees();
				}
			}

			// return fSearchResults;
		} catch (Exception e) {
			DLTKCore.error(e.toString(), e);
		}
		// return fSearchResults;
		return new HashMap(0);
	}

	private IJstType getJstType(IMethod method2) throws ModelException {
		// TODO Auto-generated method stub
		ISourceModule sourceModule = method2.getSourceModule();
		return ((IVjoSourceModule) sourceModule).getJstType();
	}

	// public IMethod[] findMethods(final String methodName, int argCount,
	// int sourcePosition) {
	// final List methods = new ArrayList();
	// ISourceModule module = this.method.getSourceModule();
	// try {
	// IModelElement[] elements = module.codeSelect(sourcePosition,
	// methodName.length());
	// for (int i = 0; i < elements.length; ++i) {
	// if (elements[i] instanceof IMethod) {
	// methods.add(elements[i]);
	// }
	// }
	// } catch (ModelException e) {
	// DLTKCore.error(e.toString(), e);
	// }
	// return (IMethod[]) methods.toArray(new IMethod[methods.size()]);
	// }

}
