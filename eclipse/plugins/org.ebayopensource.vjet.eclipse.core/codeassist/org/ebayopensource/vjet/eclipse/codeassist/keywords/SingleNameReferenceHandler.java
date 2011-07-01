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

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnSingleNameReference;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;

public class SingleNameReferenceHandler implements ICompletionHandler {

	private MethodCompletionHandler handler = new MethodCompletionHandler();

	public void complete(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list) {

		if (completion.inScope(ScopeIds.METHOD)) {
			handler.complete(module, position, completion, list);
		} else

		if (completion.inScope(ScopeIds.PROTOS)
				|| completion.inScope(ScopeIds.PROPS)) {
			CompletionContext.setStaticContext(true);
			handler.complete(module, position, completion, list);
		}

	}

	public Class getCompletionClass() {
		return JstCompletionOnSingleNameReference.class;
	}

}
