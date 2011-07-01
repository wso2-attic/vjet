/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.List;

import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstNeedsOnTypeCompletion;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.IScriptFolder;

/**
 * Provides package/type completion proposals inside "needs" block
 * 
 * 
 */
public class NeedsCompletionHandler extends BaseCompletionHandler {

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.codeassist.keywords.ICompletionHandler#getCompletionClass()
	 */
	public Class getCompletionClass() {
		return JstNeedsOnTypeCompletion.class;
	}

	public void complete(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list) {
		super.complete(module, position, completion, list);
	}
	
	@Override
	protected int getRelevance(int completionType) {
		switch (completionType) {
		case CompletionProposal.PACKAGE_REF:
			return 1000;
		default:
			return super.getRelevance(completionType);
		}
	}
	
	@Override
	protected String[] createPackageProposals(IScriptFolder[] paths) {
		String[] packageProposals = super.createPackageProposals(paths);
		for (int i = 0; i < packageProposals.length; i++) {
			packageProposals[i] = packageProposals[i].concat(".*");
		}
		
		return packageProposals;
	}
}
