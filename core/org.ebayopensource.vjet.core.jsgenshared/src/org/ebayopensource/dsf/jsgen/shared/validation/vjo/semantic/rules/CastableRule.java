/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules;
//
//import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
//import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblemFactory;
//import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticRule;
//import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.CastableRuleCtx;
//import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.util.TypeCheckUtil;
//import org.ebayopensource.dsf.jst.IJstType;
//import org.ebayopensource.dsf.jst.token.IExpr;
//import org.ebayopensource.dsf.logger.LogLevel;
//import org.ebayopensource.dsf.logger.Logger;
//
//public class CastableRule extends VjoSemanticRule<CastableRuleCtx> {
//
//	private static final Logger s_logger = Logger.getInstance(CastableRule.class);
//	
//	public VjoSemanticProblem doFire(CastableRuleCtx ctx) {
//		final IExpr expr = ctx.getExpr();
//		final IJstType toType = ctx.getToType();
//		
//		if(expr == null || toType == null){
//			s_logger.log(LogLevel.WARN, "can't test castability as lhs or rhs doesn't have a concrete type info");
//		}
//		else{
//			if(!TypeCheckUtil.isCastable(expr.getResultType(), toType)){
//				final VjoSemanticProblem problem = VjoSemanticProblemFactory.getInstance().createProblem(ctx.getArguments(), ctx.getGroupId(), getProblemId(), getErrMsg(), ctx.getNode(), this);
//				return problem;
//			}
//		}
//		
//		return null;
//	}
//}
