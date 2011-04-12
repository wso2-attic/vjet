/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstKeywordCompletion;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.IVjoKeywordCompletionData;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.VjoKeywordFactory;

/**
 * Advise vjo keywords where cursor out of props and protos. example1: vjo.c<cursor>
 * example2: vj<cursor>
 * 
 * need attributes: 1.ctx.actingToken
 * 
 * 
 * @see VjoCcStaticPropMethodProposalAdvisor
 * 
 * Modified: Jack: 2009.06.23, Used for inner jst type
 */
public class VjoCcKeywordAdvisor extends AbstractVjoCcAdvisor {
	public static final String ID = VjoCcKeywordAdvisor.class.getName();

	public void advise(VjoCcCtx ctx) {
		JstCompletion completion = ctx.getCompletion();
		if (completion instanceof JstKeywordCompletion) {
			String[] compls = completion.getCompletion();
			if (compls == null) {
				return;
			}
			IVjoKeywordCompletionData data = null;
			for (int i = 0; i < compls.length; i++) {
				data = VjoKeywordFactory.getKeywordByName(compls[i]);
				if (data != null) {
					ctx.getReporter().addPropsal(data);
				}
			}
		}
	}

}
