/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCommentCompletion;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.IVjoKeywordCompletionData;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.VjoKeywordFactory;
import org.ebayopensource.vjo.tool.codecompletion.comment.VjoCcCommentUtil;

/**
 * advise keywords which can be used inner function example1: function(){
 * xx.abc;//<Typ<cursor>e }
 * 
 * example2: \/\/public void <cursor>method:function() function(){ while() { } }
 * 
 * need attributes: 1. ctx.actingToken
 * 
 * 
 * @see VjoKeywordFactory#getMethodKeyworkds()
 * 
 */
public class VjoCcKeywordInCommentProposalAdvisor extends AbstractVjoCcAdvisor {
	public static final String ID = VjoCcKeywordInCommentProposalAdvisor.class
			.getName();

	public void advise(VjoCcCtx ctx) {
		JstCommentCompletion completion = (JstCommentCompletion) ctx
				.getCompletion();
		ScopeId scope = VjoCcCommentUtil.getScope(completion);
		if (scope == ScopeIds.TYPE) { // comment on type definition
				addKeywordForType(ctx);
			// only public, abstract & final are permitted
		} else if (scope == ScopeIds.METHOD || scope == ScopeIds.PROPERTY) {
				addKeywordForPropertyAndMethod(ctx, ctx.getActingToken());
		} else if (scope == ScopeIds.VAR) {
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_TYPE);
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_SUPPRESSTYPECHECK); 
		}
	}
	
	private void addKeywordForPropertyAndMethod(VjoCcCtx ctx, String token) {
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_PUBLIC); 
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_PROTECTED); 
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_PRIVATE);  
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_FINAL); 
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_ABSTRACT); 
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_SUPPRESSTYPECHECK); 
	}

	private void addKeywordForType(VjoCcCtx ctx) {
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_PUBLIC); 
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_FINAL); 
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_ABSTRACT);
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_NEEDS);
			addKeywordProposal(ctx, VjoKeywordFactory.KWD_SUPPRESSTYPECHECK);
	}
	
	private void addKeywordProposal(VjoCcCtx ctx, IVjoKeywordCompletionData data) {
		if (data.canComplete(ctx.getActingToken()) && !VjoCcCommentUtil.containsKeyword((JstCommentCompletion)ctx.getCompletion(), data)) {
			ctx.getReporter().addPropsal(data);
		}
	}

}
