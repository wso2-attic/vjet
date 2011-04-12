/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * when cursor in protos or props block, and type some code
 * example:
 * protos{
 * 	abc
 * }
 * this advisor will advise public, protected and private methods named with the 'abc'.
 * thie advisor only return the current JstType.
 * 
 * Hope UI side can recognize this advisor and generated three proposals for the proposal data.
 * 
 * need attributes:
 * ctx.actingType
 * ctx.actingToken (if token is empty, the advisor should advise nothing)
 * 
 *
 */
public class VjoCcFunctionGenProposalAdvisor extends AbstractVjoCcAdvisor {
	public static final String ID = VjoCcFunctionGenProposalAdvisor.class
			.getName();
	
	public void advise(VjoCcCtx ctx) {
		IJstType type = ctx.getActingType();
		String token = ctx.getActingToken();
		if (StringUtils.isBlankOrEmpty(token)) {
			return;
		} else {
			if (type.isInterface() &&  ctx.isInStatic()) {
					return;
			}
			appendData(ctx, type);
		}
	}

}
