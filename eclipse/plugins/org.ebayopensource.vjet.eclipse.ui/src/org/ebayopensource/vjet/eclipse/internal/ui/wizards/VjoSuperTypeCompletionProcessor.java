/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.search.TypeSearcher;
import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoProposalLabelProvider;
import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoProposalLabelUtil;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.mod.javascript.internal.ui.text.completion.JavaScriptCompletionProposal;
import org.eclipse.dltk.mod.ui.text.completion.CompletionProposalComparator;
import org.eclipse.jface.contentassist.IContentAssistSubjectControl;
import org.eclipse.jface.contentassist.ISubjectControlContentAssistProcessor;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;

public class VjoSuperTypeCompletionProcessor implements
		IContentAssistProcessor, ISubjectControlContentAssistProcessor {

	private CompletionProposalComparator fComparator;
	private static final String trigger = ".";

	private char[] fProposalAutoActivationSet;

	/**
	 * Creates a <code>VjoPackageCompletionProcessor</code>. The Processor
	 * uses the given <code>ILabelProvider</code> to show text and icons for
	 * the possible completions.
	 * 
	 * @param labelProvider
	 *            Used for the popups.
	 */
	public VjoSuperTypeCompletionProcessor() {
		fComparator = new CompletionProposalComparator();
		fProposalAutoActivationSet = trigger.toCharArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer,
	 *      int)
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int documentOffset) {
		Assert.isTrue(false, "ITextViewer not supported"); //$NON-NLS-1$
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer,
	 *      int)
	 */
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int documentOffset) {
		Assert.isTrue(false, "ITextViewer not supported"); //$NON-NLS-1$
		return null;
	}

	/*
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getCompletionProposalAutoActivationCharacters()
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		return fProposalAutoActivationSet;
	}

	/*
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationAutoActivationCharacters()
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	/*
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
	 */
	public String getErrorMessage() {
		return null;
	}

	/*
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
	 */
	public IContextInformationValidator getContextInformationValidator() {
		return null; // no context
	}

	/*
	 * @see ISubjectControlContentAssistProcessor#computeContextInformation(IContentAssistSubjectControl,
	 *      int)
	 */
	public IContextInformation[] computeContextInformation(
			IContentAssistSubjectControl contentAssistSubjectControl,
			int documentOffset) {
		return null;
	}

	/*
	 * @see ISubjectControlContentAssistProcessor#computeCompletionProposals(IContentAssistSubjectControl,
	 *      int)
	 */
	public ICompletionProposal[] computeCompletionProposals(
			IContentAssistSubjectControl contentAssistSubjectControl,
			int documentOffset) {
		String input = contentAssistSubjectControl.getDocument().get();
		ICompletionProposal[] proposals = createSuperTypeProposals(
				documentOffset, input);
		Arrays.sort(proposals, fComparator);
		return proposals;
	}

	private ICompletionProposal[] createSuperTypeProposals(int documentOffset,
			String input) {
		ArrayList proposals = new ArrayList();
		String prefix = input.substring(0, documentOffset);

		TypeSearcher s_typeSearcher = TypeSearcher.getInstance();
		List<IJstType> list = s_typeSearcher.search(prefix);
		for (IJstType jstType : list) {

			// Filter the interface and native class.
			if (!jstType.isClass() || CodeassistUtils.isNativeType(jstType)) {
				continue;
			}

			Image image = null;
			JstModifiers modifies = jstType.getModifiers();
			int flag = VjoProposalLabelUtil.getDltkModifyFlag(modifies);
			image = VjoProposalLabelProvider
					.getTypeImageDescriptor(flag, false);

			JavaScriptCompletionProposal proposal = new JavaScriptCompletionProposal(
					jstType.getName(), 0, input.length(), image, jstType
							.getName(), 0);
			proposals.add(proposal);
		}

		return (ICompletionProposal[]) proposals
				.toArray(new ICompletionProposal[proposals.size()]);
	}

}
