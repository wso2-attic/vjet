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
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;

/**
 * 
 *
 */
public interface ICompletionHandler {

	/**
	 * @param sourceModule - current {@link ISourceModule}
	 * @param position - completion position
	 * @param completion - {@link JstCompletion}
	 * @param list - {@link List} of completion proposals
	 */
	void complete(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list);

	Class getCompletionClass();

}
