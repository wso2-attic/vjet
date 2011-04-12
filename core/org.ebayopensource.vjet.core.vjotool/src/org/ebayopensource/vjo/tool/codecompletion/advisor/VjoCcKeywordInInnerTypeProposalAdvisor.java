/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package org.ebayopensource.vjo.tool.codecompletion.advisor;
//
//import org.ebayopensource.dsf.jst.IJstNode;
//import org.ebayopensource.dsf.jst.IJstType;
//import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
//import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
//import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.VjoKeywordFactory;
//
///**
// * Advise "outer" proposal when cursor after "this.vj$." and in protos scope. Example: function(){ this.vjo$.o<cursor> }
// * 
// * Need attributes: 1. ctx.actingToken
// * 
// * ProposalData: IVjoKeywordCompletionData
// * 
// * 
// * 
// */
//public class VjoCcKeywordInInnerTypeProposalAdvisor extends
//		AbstractVjoCcAdvisor {
//	public static final String ID = VjoCcKeywordInInnerTypeProposalAdvisor.class
//			.getName();
//
//	public void advise(VjoCcCtx ctx) {
//		JstCompletion completion = ctx.getCompletion();
//		IJstType type = completion.getOwnerType();
//		if (type != null && !type.getModifiers().isStatic()) {//valida only in protos
//			IJstNode pnode = type.getParentNode();
//			if ((pnode instanceof IJstType) && pnode != type) {
//				// The current type is an inner type
//				ctx.getReporter().addPropsal(VjoKeywordFactory.KWD_OUTER);
//			}
//		}
//	}
//
//}
