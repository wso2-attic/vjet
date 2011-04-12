/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.internal.ui.text.completion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.ui.text.completion.ContentAssistInvocationContext;
import org.eclipse.dltk.mod.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposalComputer;
import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.IContextInformation;

/**
 * 
 * 
 */
public abstract class AbstractVjoCompletionProposalComputer extends
		ScriptCompletionProposalComputer {

	@Override
	public List computeContextInformation(
			ContentAssistInvocationContext context, IProgressMonitor monitor) {
		System.out.println("Offset: " + context.getInvocationOffset());

		if (DLTKCore.DEBUG) {
			System.out
					.println("TclTypeCompletionProposalComputer.computeContextInformation()");
		}
		// if (context instanceof ScriptContentAssistInvocationContext) {
		// ScriptContentAssistInvocationContext scriptContext=
		// (ScriptContentAssistInvocationContext) context;
		//			
		// int contextInformationPosition=
		// guessContextInformationPosition(scriptContext);
		// List result= addContextInformations(scriptContext,
		// contextInformationPosition, monitor);
		// return result;
		// }
		// return Collections.EMPTY_LIST;

		List types = computeCompletionProposals(context, monitor);
		if (DLTKCore.DEBUG) {
			System.out.println("!!! Proposals: " + types.size());
		}
		Iterator iter = types.iterator();

		List list = new ArrayList();
		while (iter.hasNext()) {
			Object next = iter.next();
			if (!(next instanceof IScriptCompletionProposal))
				continue;
			IScriptCompletionProposal proposal = (IScriptCompletionProposal) next;
			IContextInformation contextInformation = proposal
					.getContextInformation();
			if (contextInformation == null) {
				continue;
			}
			if (DLTKCore.DEBUG) {
				System.out.println("Proposal: " + proposal + ", info: "
						+ contextInformation.getInformationDisplayString());
			}
			list.add(contextInformation);
		}
		return list;
	}

	@Override
	protected final ScriptCompletionProposalCollector createCollector(
			ScriptContentAssistInvocationContext context) {
		return new AbstractVjoCompletionProposalCollector(context
				.getSourceModule()) {

			// implemented
			// org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor.getCategory()

			public String getCategory() {
				return getRequestorCategory();
			}

		};
	}

	protected abstract String getRequestorCategory();
}
