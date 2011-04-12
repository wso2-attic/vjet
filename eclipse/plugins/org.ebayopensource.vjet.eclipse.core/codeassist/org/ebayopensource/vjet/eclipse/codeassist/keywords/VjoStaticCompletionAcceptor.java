/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.vjo.meta.VjoKeywords;

/**
 * This class accept or decline {@link VjoKeywordFactory#KWD_SYSTEM_ERR},{@link VjoKeywordFactory#KWD_SYSTEM_OUT}
 * and {@link VjoKeywordFactory#KWD_PRINT_STACK_TRACE} completion keywords.
 * 
 * 
 * 
 */
public class VjoStaticCompletionAcceptor extends CompletionAcceptor {

	public boolean accept(int position, JstCompletion completion) {
		String source = getSourceModule().getSourceContents();
		int positionCompare = source.lastIndexOf('.', position);

		if (source.substring(positionCompare - 4, positionCompare).trim()
				.toLowerCase().equals(VjoKeywords.VJO)) {
			return true;
		}
		return false;
	}
}
