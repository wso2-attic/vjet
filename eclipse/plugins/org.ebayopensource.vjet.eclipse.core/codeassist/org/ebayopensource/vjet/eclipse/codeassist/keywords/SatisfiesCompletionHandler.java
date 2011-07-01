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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstSatisfiesOnTypeCompletion;
import org.ebayopensource.vjet.eclipse.core.IJSType;
import org.ebayopensource.vjet.eclipse.internal.core.util.Util;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;

/**
 * Provides package/interface completion proposals inside "satisfies" block
 * 
 * 
 */
public class SatisfiesCompletionHandler extends BaseCompletionHandler {
	public void complete(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list) {
		super.complete(module, position, completion, list);
	}

	public Class getCompletionClass() {
		return JstSatisfiesOnTypeCompletion.class;
	}
	
	@Override
	protected boolean checkType(IType type, IType currentType) {
		try {
			IJstType jstType = Util.toJstType(type);
			
			if (!(type instanceof IJSType)
					|| !((IJSType) type).isInterface() || jstType.isMixin()) {
				return false;
			}
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
		return super.checkType(type, currentType);
	}
}
