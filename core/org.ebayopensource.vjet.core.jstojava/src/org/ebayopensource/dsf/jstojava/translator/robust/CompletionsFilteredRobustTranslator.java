/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.eclipse.mod.wst.jsdt.core.ast.IProgramElement;

abstract class CompletionsFilteredRobustTranslator extends BaseRobustTranslator
		implements ICompletionsFiltered {

	protected ICompletionsFilter filter;

	public CompletionsFilteredRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}
	
	public void setFilter(ICompletionsFilter filter){
		this.filter = filter;
	}
	
	protected final String[] getAllowedTokens() {
		if (filter == null) {
			return new String[0];
		}
		return filter.filter(super.getAllowedTokens());
	}
	
	public IRobustTranslator getSubTranslator(IProgramElement element) {
		
		IRobustTranslator sub = super.getSubTranslator(element);
		
		if(sub != null && sub instanceof ICompletionsFiltered){
			
			((ICompletionsFiltered)sub).setFilter(filter);
			
		}
		
		return sub;
	}
	
}
