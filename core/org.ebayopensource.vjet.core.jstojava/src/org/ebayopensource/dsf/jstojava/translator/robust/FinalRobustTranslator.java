/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;

class FinalRobustTranslator extends CompletionsFilteredRobustTranslator {




	public FinalRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}


	public boolean transform() {

		if (jst.getModifiers().isFinal()) {
			// redundant makeFinal keyword detected
			// put it into error chunk
			
			getErrorCollector().collect();

		} else {
			// remove makeFinal() element from the stack
			current = astElements.pop();

			// make it final
			jst.getModifiers().merge(JstModifiers.FINAL);

			// lookup possible empty completions
			lookupEmptyCompletion();

			// move to the next iteration
			super.transform();
		}
		return false;

	}

}
