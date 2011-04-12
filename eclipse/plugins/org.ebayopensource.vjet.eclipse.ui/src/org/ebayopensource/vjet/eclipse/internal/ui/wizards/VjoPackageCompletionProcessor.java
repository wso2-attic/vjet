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

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.javascript.internal.ui.text.completion.JavaScriptCompletionProposal;
import org.eclipse.dltk.mod.ui.text.completion.CompletionProposalComparator;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.contentassist.IContentAssistSubjectControl;
import org.eclipse.jface.contentassist.ISubjectControlContentAssistProcessor;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

public class VjoPackageCompletionProcessor implements IContentAssistProcessor,ISubjectControlContentAssistProcessor {

	
	private IProjectFragment fPackageFragmentRoot;
	private CompletionProposalComparator fComparator;
	private ILabelProvider fLabelProvider;
	private static final String trigger=".";

	private char[] fProposalAutoActivationSet;

	/**
	 * Creates a <code>VjoPackageCompletionProcessor</code>.
	 * The completion context must be set via {@link #setPackageFragmentRoot(IPackageFragmentRoot)}.
	 */
	public VjoPackageCompletionProcessor() {
	    this(new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_SMALL_ICONS));
	}
	
    /**
     * Creates a <code>VjoPackageCompletionProcessor</code>.
     * The Processor uses the given <code>ILabelProvider</code> to show text and icons for the 
     * possible completions.
     * @param labelProvider Used for the popups.
     */
	public VjoPackageCompletionProcessor(ILabelProvider labelProvider) {
		fComparator= new CompletionProposalComparator();
		fLabelProvider= labelProvider;

		fProposalAutoActivationSet = trigger.toCharArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int documentOffset) {
		Assert.isTrue(false, "ITextViewer not supported"); //$NON-NLS-1$
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer, int)
	 */
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int documentOffset) {
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
		return null; //no context
	}

	/*
	 * @see ISubjectControlContentAssistProcessor#computeContextInformation(IContentAssistSubjectControl, int)
	 */
	public IContextInformation[] computeContextInformation(IContentAssistSubjectControl contentAssistSubjectControl,
			int documentOffset) {
		return null;
	}

	/*
	 * @see ISubjectControlContentAssistProcessor#computeCompletionProposals(IContentAssistSubjectControl, int)
	 */
	public ICompletionProposal[] computeCompletionProposals(IContentAssistSubjectControl contentAssistSubjectControl, int documentOffset) {
		if (fPackageFragmentRoot == null)
			return null;
		String input= contentAssistSubjectControl.getDocument().get();
		ICompletionProposal[] proposals= createPackagesProposals(documentOffset, input);
		Arrays.sort(proposals, fComparator);
		return proposals;
	}
	
	public void setPackageFragmentRoot(IProjectFragment packageFragmentRoot) {
		fPackageFragmentRoot= packageFragmentRoot;
	}

	private ICompletionProposal[] createPackagesProposals(int documentOffset, String input) {
		ArrayList proposals= new ArrayList();
		String prefix= input.substring(0, documentOffset);
		try {
			IModelElement[] packageFragments= fPackageFragmentRoot.getChildren();
			for (int i= 0; i < packageFragments.length; i++) {
				IScriptFolder pack= (IScriptFolder) packageFragments[i];
				String packName= pack.getElementName();
				if (packName.length() == 0 || ! packName.startsWith(prefix))
					continue;
				Image image= fLabelProvider.getImage(pack);
				JavaScriptCompletionProposal proposal= new JavaScriptCompletionProposal(packName, 0, input.length(),image,packName,0);
				proposals.add(proposal);
			}
		} catch (ModelException e) {
			//fPackageFragmentRoot is not a proper root -> no proposals
		}
		return (ICompletionProposal[]) proposals.toArray(new ICompletionProposal[proposals.size()]);
	}

}
