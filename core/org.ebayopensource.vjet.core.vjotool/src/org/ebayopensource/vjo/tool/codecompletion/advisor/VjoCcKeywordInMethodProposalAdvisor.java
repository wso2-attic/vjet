/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.IVjoKeywordCompletionData;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.VjoKeywordFactory;

/**
 * advise keywords which can be used inner function
 * example1:
 * function(){
 * 	xx<cursor>
 * }
 * 
 * example2:
 * function(){
 *  while() {
 * 		xx<cursor>
 *  }
 * }
 * 
 * need attributes:
 * 1. ctx.actingToken
 * 
 * @see VjoKeywordFactory#getMethodKeyworkds()
 *
 */
public class VjoCcKeywordInMethodProposalAdvisor extends AbstractVjoCcAdvisor {
	public static final String ID = VjoCcKeywordInMethodProposalAdvisor.class
			.getName();

	public void advise(VjoCcCtx ctx) {
		for (IVjoKeywordCompletionData data : VjoKeywordFactory.getMethodKeyworkds()) {

			if (data.canComplete(ctx.getActingToken())) {
				ctx.getReporter().addPropsal(data);
			}
		}

	}

}
