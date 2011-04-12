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

public class VjoCcThisProposalAdvisor extends AbstractVjoCcAdvisor {
	public static final String ID = VjoCcThisProposalAdvisor.class.getName();

	public void advise(VjoCcCtx ctx) {
		IVjoKeywordCompletionData data = VjoKeywordFactory.KWD_THIS;
		if (data.canComplete(ctx.getActingToken())) {
			ctx.getReporter().addPropsal(data);
		}
//		//temperorily add window here 
//		data = VjoKeywordFactory.KWD_WINDOW;
//		if (data.canComplete(ctx.getActingToken())) {
//			ctx.getReporter().addPropsal(data);
//		}
//		data = VjoKeywordFactory.KWD_DOCUMENT;
//		if (data.canComplete(ctx.getActingToken())) {
//			ctx.getReporter().addPropsal(data);
//		}

	}

}
