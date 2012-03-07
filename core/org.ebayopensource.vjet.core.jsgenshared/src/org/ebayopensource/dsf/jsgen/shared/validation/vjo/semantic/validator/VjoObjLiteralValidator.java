/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticValidator;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.BaseVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.util.TypeCheckUtil;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.NV;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;

public class VjoObjLiteralValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPostAllChildrenListener {
	
	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(ObjLiteral.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		
		if(!(jstNode instanceof ObjLiteral)){
			return;
		}
		
		final ObjLiteral objLiteral = (ObjLiteral)jstNode;
		final Set<String> nameSet = new HashSet<String>();
		for(NV nv : objLiteral.getNVs()){
			if(nameSet.contains(nv.getName())){
				//report problem;
				//add error arguments
				String[] arguments = new String[2];
				arguments[0] = nv.getName() != null ? nv.getName() : "NULL";
				arguments[1] = objLiteral.toExprText();
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(nv.getIdentifier(), ctx.getGroupId(), arguments);
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().OBJECT_LITERAL_SHOULD_HAVE_UNIQUE_KEY, ruleCtx);
			}
			nameSet.add(nv.getName());
			
			if(isJavaKeyword(nv.getName())){
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(nv, ctx.getGroupId(), new String[]{nv.getName(), nv.getName()});
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().VJO_SYNTAX_CORRECTNESS, ruleCtx);
			}
			
			//validate name/value type matching
			//added by huzhou@ebay.com to validate the type matching of NV pairs
			final JstIdentifier name = nv.getIdentifier();
			final IExpr value = nv.getValue();
			validateNVTypes(ctx, nv, name, value);
		}
		
		validateComplexType(ctx, objLiteral, objLiteral.toExprText(), objLiteral.getResultType());
	}

	private void validateNVTypes(final VjoValidationCtx ctx, NV nv,
			final JstIdentifier name, final IExpr value) {
		if(name != null 
				&& name.getType() != null
				&& value != null
				&& value.getResultType() != null){
			if(!TypeCheckUtil.isAssignable(name.getType(), value.getResultType())){
				final String[] arguments = {name.getType().getName(), value.toExprText(), nv.toString()};
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(name, ctx.getGroupId(), arguments);
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().ASSIGNABLE, ruleCtx);
			}
		}
	}

}
