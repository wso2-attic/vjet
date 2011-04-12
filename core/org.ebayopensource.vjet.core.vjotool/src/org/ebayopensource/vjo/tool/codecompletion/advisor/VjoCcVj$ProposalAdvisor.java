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
//import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
//
///**
// * Advise "vj$" proposal when cursor after "this.".
// * Example:
// * 	function(){
// * 		this.v<cursor>
// * }
// * 
// * Need attributes:
// * 1. ctx.actingToken
// * 
// * ProposalData: IVjoKeywordCompletionData
// * 
// * 
// *
// */
//public class VjoCcVj$ProposalAdvisor extends AbstractVjoCcAdvisor {
//	public static final String ID = VjoCcVj$ProposalAdvisor.class.getName();
//
//	public void advise(VjoCcCtx ctx) {
//		IVjoKeywordCompletionData[] datas = new IVjoKeywordCompletionData[] {
//				VjoKeywordFactory.KWD_VJ$, VjoKeywordFactory.KWD_BASE };
//		for (int i = 0; i < datas.length; i++) {
//			if (datas[i].canComplete(ctx.getActingToken())) {
//				ctx.getReporter().addPropsal(datas[i]);
//			}
//
//		}
//
//	}
//
//}
