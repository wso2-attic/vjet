/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;

/**
 * This class accept or decline {@link VjoKeywordFactory#KWD_BREACK} completion keyword.
 * 
 * 
 *
 */
public class BreakCompletionAcceptor extends CompletionAcceptor {

	public boolean accept(int position, JstCompletion completion) {
		return completion.inScope(ScopeIds.SWITCH)
				|| completion.inScope(ScopeIds.LOOP);
	}

}
