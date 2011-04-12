/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.handler;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCommentCompletion;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcHandler;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoAttributedProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcGlobalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcKeywordInCommentProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcPackageProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.comment.VjoCcCommentUtil;

public class VjoCcCommentHandler implements IVjoCcHandler {

	
	@Override
	public String[] handle(VjoCcCtx ctx) {
		JstCommentCompletion completion = (JstCommentCompletion) ctx
				.getCompletion();
		ctx.setActingPackageToken(completion.getToken());
		ScopeId scope = VjoCcCommentUtil.getScope(completion);
		if (VjoCcCommentUtil.isInactiveNeed(completion)) {
			if (VjoCcCommentUtil.needProposal(completion)) {
				return new String[]{VjoCcPackageProposalAdvisor.ID,  VjoCcTypeProposalAdvisor.ID};
			} else {
				return new String[0];
			}
		}
		
		if (scope == ScopeIds.GLOBAL) {
			return new String[] {VjoCcGlobalAdvisor.ID};
		}
		else if (scope == ScopeIds.TYPE) {
			return new String[]{VjoCcKeywordInCommentProposalAdvisor.ID};
		//modify by patrick
		} else if (scope == ScopeIds.METHOD || scope == ScopeIds.PROPERTY || scope == ScopeIds.VAR) {
			//end modify
			if (VjoCcCommentUtil.isAfterTypeRefKeyword(completion)) {
				return new String[] { VjoCcTypeProposalAdvisor.ID,
						VjoCcPackageProposalAdvisor.ID};
			} else {
				return new String[] { VjoCcTypeProposalAdvisor.ID, 
						VjoCcPackageProposalAdvisor.ID,
						VjoCcKeywordInCommentProposalAdvisor.ID, 
						VjoAttributedProposalAdvisor.ID};
			}
		}
		return new String[] { VjoCcTypeProposalAdvisor.ID };

	}

}
