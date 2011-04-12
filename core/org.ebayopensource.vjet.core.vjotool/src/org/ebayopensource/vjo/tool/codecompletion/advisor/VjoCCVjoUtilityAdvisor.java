/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.IVjoKeywordCompletionData;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.VjoKeywordFactory;

/**
 * Advise "vjo" proposal when cursor at the beginning of js file. Example: <file
 * beginning>v<cursor>
 * 
 * Need attributes: 1. ctx.actingToken
 * 
 * ProposalData: IVjoKeywordCompletionData
 * 
 * 
 * 
 */

public class VjoCCVjoUtilityAdvisor extends AbstractVjoCcAdvisor {
	public static final String ID = VjoCCVjoUtilityAdvisor.class.getName();

	public void advise(VjoCcCtx ctx) {
		IGroup<IJstType> group = ctx.getJstTypeSpaceMgr().getTypeSpace().getGroup(ctx.getGroupName());
		IJstNode vjoObject = ctx.getJstTypeSpaceMgr()
		.getTypeSpace()
		.getVisibleGlobal("vjo", 
				group);
		if(vjoObject==null){
			return;
		}
		
		IVjoKeywordCompletionData data = VjoKeywordFactory.KWD_VJO_UTILITY;
		if (data.canComplete(ctx.getActingToken())) {
			ctx.getReporter().addPropsal(data);
		}
	}

	private boolean isVjoVisible(String groupName) {

		return false;
	}

}
