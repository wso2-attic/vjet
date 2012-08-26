/*******************************************************************************
 * Copyright (c) 2000-2011 IBM Corporation and others, eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     eBay Inc - modification
 *******************************************************************************/
package org.eclipse.dltk.mod.javascript.internal.ui.text.completion;

import java.net.URL;

import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.internal.javascript.reference.resolvers.SelfCompletingReference;
import org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.jface.resource.ImageDescriptor;

public class JavaScriptCompletionProposalLabelProvider extends
		CompletionProposalLabelProvider {
	protected String createMethodProposalLabel(CompletionProposal methodProposal) {
		if (methodProposal.extraInfo instanceof SelfCompletingReference) {
			SelfCompletingReference cm = (SelfCompletingReference) methodProposal.extraInfo;
			methodProposal.setParameterNames(cm.getParameterNames());
		}
		StringBuffer nameBuffer = new StringBuffer();

		// method name
		nameBuffer.append(methodProposal.getName());

		// parameters
		nameBuffer.append('(');
		appendUnboundedParameterList(nameBuffer, methodProposal);
		nameBuffer.append(')');

		return nameBuffer.toString();
	}

	protected String createOverrideMethodProposalLabel(
			CompletionProposal methodProposal) {
		StringBuffer nameBuffer = new StringBuffer();

		// method name
		nameBuffer.append(methodProposal.getName());

		// parameters
		nameBuffer.append('(');
		appendUnboundedParameterList(nameBuffer, methodProposal);
		nameBuffer.append(')'); //$NON-NLS-1$

		return nameBuffer.toString();
	}

	/**
	 * @see org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider#createImageDescriptor(org.eclipse.dltk.mod.core.CompletionProposal)
	 */
	public ImageDescriptor createImageDescriptor(CompletionProposal proposal) {
		ImageDescriptor imageDescriptor = extraImageFromProposal(proposal);
		if (imageDescriptor != null)
			return imageDescriptor;
		return super.createImageDescriptor(proposal);
	}

	/**
	 * @param proposal
	 * @return
	 */
	private ImageDescriptor extraImageFromProposal(CompletionProposal proposal) {
		if (proposal.extraInfo instanceof SelfCompletingReference) {
			SelfCompletingReference cm = (SelfCompletingReference) proposal.extraInfo;
			URL imageUrl = cm.getImageURL();
			if (imageUrl != null)
				return decorateImageDescriptor(
						ImageDescriptor.createFromURL(imageUrl), proposal);
		}
		return null;
	}

	/**
	 * @see org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider#createFieldImageDescriptor(org.eclipse.dltk.mod.core.CompletionProposal)
	 */
	// eBay mod start
	// protected ImageDescriptor createFieldImageDescriptor(
	public ImageDescriptor createFieldImageDescriptor(
			CompletionProposal proposal) {
		// eBay mode end
		ImageDescriptor imageDescriptor = extraImageFromProposal(proposal);
		if (imageDescriptor != null)
			return imageDescriptor;
		return super.createFieldImageDescriptor(proposal);
	}

	/**
	 * @see org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider#createLocalImageDescriptor(org.eclipse.dltk.mod.core.CompletionProposal)
	 */
	protected ImageDescriptor createLocalImageDescriptor(
			CompletionProposal proposal) {
		ImageDescriptor imageDescriptor = extraImageFromProposal(proposal);
		if (imageDescriptor != null)
			return imageDescriptor;
		return super.createLocalImageDescriptor(proposal);
	}

	/**
	 * @see org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider#createMethodImageDescriptor(org.eclipse.dltk.mod.core.CompletionProposal)
	 */
	public ImageDescriptor createMethodImageDescriptor(
			CompletionProposal proposal) {
		ImageDescriptor imageDescriptor = extraImageFromProposal(proposal);
		if (imageDescriptor != null)
			return imageDescriptor;
		return super.createMethodImageDescriptor(proposal);
	}

	/**
	 * @see org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider#createPackageImageDescriptor(org.eclipse.dltk.mod.core.CompletionProposal)
	 */
	protected ImageDescriptor createPackageImageDescriptor(
			CompletionProposal proposal) {
		ImageDescriptor imageDescriptor = extraImageFromProposal(proposal);
		if (imageDescriptor != null)
			return imageDescriptor;
		return super.createPackageImageDescriptor(proposal);
	}

	/**
	 * @see org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider#createTypeImageDescriptor(org.eclipse.dltk.mod.core.CompletionProposal)
	 */
	protected ImageDescriptor createTypeImageDescriptor(
			CompletionProposal proposal) {
		ImageDescriptor imageDescriptor = extraImageFromProposal(proposal);
		if (imageDescriptor != null)
			return imageDescriptor;
		return super.createTypeImageDescriptor(proposal);
	}
}
