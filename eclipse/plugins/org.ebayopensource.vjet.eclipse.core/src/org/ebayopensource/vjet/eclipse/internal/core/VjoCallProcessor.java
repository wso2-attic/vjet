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
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.search.SearchQueryParameters;
import org.ebayopensource.vjet.eclipse.core.search.VjoMatch;
import org.ebayopensource.vjet.eclipse.core.search.VjoSearchEngine;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.ast.references.SimpleReference;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.ICallProcessor;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.DLTKSearchParticipant;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;
import org.eclipse.dltk.mod.core.search.SearchMatch;
import org.eclipse.dltk.mod.core.search.SearchPattern;

/**
 * This class perform searching methods callers.
 * 
 * 
 *
 */
public class VjoCallProcessor implements ICallProcessor {

	public final static int GENERICS_AGNOSTIC_MATCH_RULE = SearchPattern.R_EXACT_MATCH
			| SearchPattern.R_CASE_SENSITIVE | SearchPattern.R_ERASURE_MATCH;

//	private SearchEngine searchEngine = new SearchEngine();

	/**
	 * Search all references to method using {@link VjoSearchEngine} object.
	 */
	public Map process(final IModelElement parent, IModelElement element,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		
		final Map elements = new HashMap();

		VjoSearchRequestor requestor = new VjoSearchRequestor(elements, parent);

		SearchPattern pattern = SearchPattern.createPattern(element,
				IDLTKSearchConstants.REFERENCES | IDLTKSearchConstants.METHOD,
				GENERICS_AGNOSTIC_MATCH_RULE, scope.getLanguageToolkit());
		try {

			VjoSearchEngine engine = new VjoSearchEngine();
			SearchQueryParameters parameters = new SearchQueryParameters();
			parameters.setElement(element);
			parameters.setElementSpecification(true);
			parameters.setPattern(pattern);
			parameters.setScope(scope);
			parameters.setLimitTo(IDLTKSearchConstants.METHOD);
			List<VjoMatch> result = engine.search(parameters);
			
			for (VjoMatch vjoMatch : result) {
				requestor.acceptSearchMatch(vjoMatch);
			}

		} catch (Exception e) {
			DLTKCore.error(e.toString(), e);
		}
		return elements;
	}

	private final class VjoSearchRequestor {
		private final Map elements;
		private final IModelElement parent;

		private VjoSearchRequestor(Map elements, IModelElement parent) {
			this.elements = elements;
			this.parent = parent;
		}

		public void acceptSearchMatch(VjoMatch match) {
			if ((match.getAccuracy() != SearchMatch.A_ACCURATE)) {
				return;
			}

			/*if (match.isInsideDocComment()) {
				return;
			}*/

			if (isModelElement(match)) {

				IModelElement member = (IModelElement) match.getElement();
				ISourceModule module;
				module = (ISourceModule) member
						.getAncestor(IModelElement.SOURCE_MODULE);
				try {
					createElement(match, module);
				} catch (ModelException e) {
					DLTKCore.error(e.toString(), e);
				}
			}
		}

		private boolean isModelElement(VjoMatch match) {
			return match.getElement() != null
					&& match.getElement() instanceof IModelElement;
		}

		/**
		 * Create element from match positions and source module.
		 * 
		 * @param match {@link VjoMatch} object
		 * @param module {@link ISourceModule} object
		 * @throws ModelException
		 */
		private void createElement(VjoMatch match, ISourceModule module)
				throws ModelException {
			SimpleReference ref;
			ref = new SimpleReference(match.getOffset(), match.getOffset()
					+ match.getLength(), "");

			IType[] types = module.getAllTypes();

			if (types.length > 0) {

				int offset = match.getOffset();
				if(offset!=-1){
					IMethod parentMethod = getMethod(types, offset);
					
//					IModelElement[] e = module.codeSelect(match.getOffset(), 1);
					
//					for (int j = 0; j < e.length; ++j) {
//						if (e[j].equals(parent)) {
							elements.put(ref, parentMethod == null ? types[0]
							                                               : parentMethod);
//						}
//					}
					
				}else{
					//binary node has no offset
					IJstNode originalNode=match.getJstNode();
					Assert.isNotNull(originalNode);
				    IJstNode parent=originalNode.getParentNode();
				    while(parent!=null){
				        if(parent instanceof JstMethod){
				               break;
				        }
				        parent=originalNode.getParentNode();
				    }
				    
				    
				    if (parent != null && parent instanceof JstMethod) {
						IMethod[] dltkMethod = CodeassistUtils.getMethod(types[0], (IJstMethod) parent);
						for(IMethod m: dltkMethod){
							elements.put(ref, m == null ? types[0] : m);
						}
					}
					
					
				}
			}
		}

		private IMethod getMethod(IType[] types, int offset)
				throws ModelException {

			IMethod parentMethod = null;
			IMethod[] methods = types[0].getMethods();

			for (IMethod method : methods) {
				int start = method.getSourceRange().getOffset();
				int finish = method.getSourceRange().getLength() + start;
				if (start <= offset && finish >= offset) {
					parentMethod = method;
				}
			}

			return parentMethod;
		}
	}

	public DLTKSearchParticipant getSearchParticipant(IModelElement element) {
		IDLTKLanguageToolkit toolkit = null;
		toolkit = DLTKLanguageManager.getLanguageToolkit(element);
		if (toolkit != null) {
			DLTKSearchParticipant par = DLTKLanguageManager
					.createSearchParticipant(toolkit.getNatureId());
			if (par != null) {
				return par;
			}
		}
		return new DLTKSearchParticipant();
	}

}
