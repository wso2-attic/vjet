/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * Advise constructor when curson in protos block. The advised proposal data will contains the owner jsttype.
 * UI side need analyse the jst type in proposal data, and generate proposal to support code gen
 * example:
 * .protos{
 * 	c<cursor>
 * }
 * 
 * need attributes:
 * 1. ctx.actingToken
 * 2. ctx.actingType
 * 
 * 
 *
 */
public class VjoCcConstructorGenProposalAdvisor extends AbstractVjoCcAdvisor {
	public static final String ID = VjoCcConstructorGenProposalAdvisor.class
			.getName();

	public void advise(VjoCcCtx ctx) {
		IJstType type = ctx.getActingType();
		String token = ctx.getActingToken();
		IJstMethod method = type.getConstructor();
		if ((method == null || CodeCompletionUtils.isSynthesizedElement(method))
				&& VjoCcAdvisorConstances.CONSTRUCTOR.startsWith(token)) {
			appendData(ctx, type);
			if (token.equals(VjoCcAdvisorConstances.CONSTRUCTOR)) {
				ctx.exactMatch(ID);
			}
		}
	}

}
