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
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * advise only the owner type itself(Used in function).
 * 
 *  need attributes:
 *  1.ctx.actingToken 
 *  2.ctx.actingType
 * 
 *
 */
public class VjoCcOwnerTypeProposalAdvisor extends VjoCcTypeProposalAdvisor {
	public static final String ID = VjoCcOwnerTypeProposalAdvisor.class.getName();

	@Override
	public void advise(VjoCcCtx ctx) {
		IJstType otype = ctx.getActingType();
		String token = ctx.getActingToken();
		if (StringUtils.isBlankOrEmpty(token)) {
			if(!otype.isFakeType()){
				appendData(ctx, otype, true);
			}
			appendOuterType(ctx);
//			appendInnerType(ctx);
		} else {
			super.advise(ctx);
		}
	}

	private void appendOuterType(VjoCcCtx ctx) {
		
		IJstType otype = ctx.getActingType();
		if (otype == null) {
			return;
		}
		IJstNode node = otype.getParentNode();
		while (node != null && (node instanceof IJstType)) {
			appendData(ctx, (IJstType) node, true);
			node = node.getParentNode();
		}
	}

	/**
	 * 
	 * @param ctx
	 * @deprecated inner type should not be proposed directly
	 */
//	private void appendInnerType(VjoCcCtx ctx) {
//		// Inner types
//		IJstType otype = ctx.getActingType();
//		List<? extends IJstType> types = otype.getStaticEmbededTypes();
//		if (types != null && !types.isEmpty()) {
//			Iterator<? extends IJstType> it2 = types.iterator();
//			while (it2.hasNext()) {
//					appendData(ctx, it2.next(), true);
//			}
//		}
//		types = otype.getInstanceEmbededTypes();
//		if (types != null && !types.isEmpty()) {
//			Iterator<? extends IJstType> it2 = types.iterator();
//			while (it2.hasNext()) {
//				appendData(ctx, it2.next(), true);
//			}
//		}
//	}
//	
}
