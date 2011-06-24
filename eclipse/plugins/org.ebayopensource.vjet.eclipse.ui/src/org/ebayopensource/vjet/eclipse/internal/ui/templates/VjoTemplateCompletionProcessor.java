/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.templates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.IVjoCompletionData;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.VjoKeywordFactory;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.IJSInitializer;
import org.eclipse.dltk.mod.internal.core.VjoSourceType;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.templates.ScriptTemplateAccess;
import org.eclipse.dltk.mod.ui.templates.ScriptTemplateCompletionProcessor;
import org.eclipse.dltk.mod.ui.templates.TemplateInformationControlCreator;
import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.dltk.mod.ui.text.completion.VjoTemplateProposal;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.IWorkbenchPartOrientation;

public class VjoTemplateCompletionProcessor extends
		ScriptTemplateCompletionProcessor {

	public VjoTemplateCompletionProcessor(
			ScriptContentAssistInvocationContext cntx) {
		super(cntx);
	}

	@Override
	protected String getContextTypeId() {

		return VjoTemplateContextType.VJO_CONTEXT_TYPE_ID;
	}

	@Override
	protected char[] getIgnore() {

		return new char[] {};
	}

	@Override
	protected ScriptTemplateAccess getTemplateAccess() {

		return VjoTemplateAccess.getInstance();
	}

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {

		ICompletionProposal[] proposals = super.computeCompletionProposals(
				viewer, offset);

		try {
			ISourceModule module = getContext().getSourceModule();
			if (!CodeassistUtils.isVjoSourceModule(module)) {
				return new ICompletionProposal[0];
			}
			IScriptProject project = module.getScriptProject();
			if (project == null || !project.exists()) {
				return proposals;
			}
			boolean isMethodScope = (module != null);
			if (isMethodScope) {
				isMethodScope = !project.isOnBuildpath(module);
				if (!isMethodScope) {
					IModelElement e = module.getElementAt(offset);
					isMethodScope = (e instanceof IMethod) ||
						(e instanceof VjoSourceType) ||
						(e instanceof IJSInitializer);
				}
			}

			if (isMethodScope) {
				// show all templates
				return proposals;
			} else {

				// do not show templates like for, if, etc*

				List<ICompletionProposal> scopeFiltered = new ArrayList<ICompletionProposal>();
				for (ICompletionProposal completionProposal : proposals) {

					String displayName = completionProposal.getDisplayString();

					if (!(displayName.startsWith(VjoKeywordFactory.KWD_DO
							.getName())
							|| displayName.startsWith(VjoKeywordFactory.KWD_FOR
									.getName())
							|| displayName.startsWith(VjoKeywordFactory.KWD_IF
									.getName())
							|| displayName
									.startsWith(VjoKeywordFactory.KWD_SWITCH
											.getName())
							|| displayName.startsWith(VjoKeywordFactory.KWD_TRY
									.getName()) || displayName
							.startsWith(VjoKeywordFactory.KWD_WHILE.getName()))) {

						scopeFiltered.add(completionProposal);

					}
				}

				return scopeFiltered.toArray(new ICompletionProposal[] {});
			}

		} catch (ModelException me) {
			// TODO log this error
			me.printStackTrace();
		}

		return proposals;
	}

	// TODO call super instead
	// public ICompletionProposal[] computeCompletionProposals(ITextViewer
	// viewer,
	// int offset) {
	//
	// ICompletionProposal[] templates = super.computeCompletionProposals(
	// viewer, offset);
	//
	// VjoKeywordCompletionResult keywordResults = SimpleKeywordCompletionEngine
	// .getJustObtainedResult();
	// ICompletionProposal[] result;
	// if (keywordResults != null) {
	// result = filter(templates, keywordResults.getKeywords());
	// } else {
	// result = new ICompletionProposal[0];
	// }
	//
	// return result;
	// }

	private ICompletionProposal[] filter(ICompletionProposal[] allTemplates,
			List<IVjoCompletionData> allowedKeywords) {

		List<ICompletionProposal> filteredTemplates = new ArrayList<ICompletionProposal>();

		for (int iter = 0; iter < allTemplates.length; iter++) {

			Iterator<IVjoCompletionData> keywordIterator = allowedKeywords
					.iterator();
			while (keywordIterator.hasNext()) {

				IVjoCompletionData keyword = keywordIterator.next();
				if (allTemplates[iter].getDisplayString().toLowerCase()
						.startsWith(keyword.toString())
						&& !keyword.isComplementedPart()) {
					filteredTemplates.add(allTemplates[iter]);
					break;
				}

			}

		}

		return (ICompletionProposal[]) filteredTemplates
				.toArray(new ICompletionProposal[] {});
	}

	protected IInformationControlCreator getInformationControlCreator() {
		int orientation = Window.getDefaultOrientation();
		IEditorPart editor = getContext().getEditor();
		if (editor == null)
			editor = DLTKUIPlugin.getActivePage().getActiveEditor();
		if (editor instanceof IWorkbenchPartOrientation)
			orientation = ((IWorkbenchPartOrientation) editor).getOrientation();
		IDLTKLanguageToolkit toolkit = null;
		toolkit = DLTKLanguageManager.getLanguageToolkit(getContext()
				.getLanguageNatureID());
		if ((toolkit == null) && (editor instanceof ScriptEditor))
			toolkit = ((ScriptEditor) editor).getLanguageToolkit();
		return new TemplateInformationControlCreator(orientation, toolkit);
	}

	protected ICompletionProposal createProposal(Template template,
			TemplateContext context, IRegion region, int relevance) {
		TemplateProposal proposal = new VjoTemplateProposal(template, context,
				region, getImage(template), relevance);
		proposal.setInformationControlCreator(getInformationControlCreator());
		return proposal;
	}
}
