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
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticValidator;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.BaseVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.util.TypeCheckUtil;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;

public class VjoThrowStmtValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPostAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;

	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
        s_targetTypes.add(ThrowStmt.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
    @Override
    public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event) {
        //Ignore throw statement logic, Allow anything can be throw.
        /*final VjoValidationCtx ctx = event.getValidationCtx();
        final IJstNode jstNode = event.getVisitNode();
        if (jstNode instanceof ThrowStmt) {
            List<BaseJstNode> childrens = ((ThrowStmt) jstNode).getChildren();
            for (BaseJstNode baseJstNode : childrens) {
                if (baseJstNode instanceof ObjCreationExpr) {
                    if (!isAssigneableError(((ObjCreationExpr) baseJstNode).getResultType())) {
                        fireProblem(jstNode, ctx);
                        return;
                    }
                } else if (baseJstNode instanceof MtdInvocationExpr) {
                    if (!isAssigneableError(((MtdInvocationExpr) baseJstNode).getResultType())) {
                        fireProblem(jstNode, ctx);
                        return;
                    }
                } else if (baseJstNode instanceof JstIdentifier) {
                    if (!isAssigneableError(((JstIdentifier) baseJstNode).getType())) {
                        fireProblem(jstNode, ctx);
                        return;
                    }
                } else if (baseJstNode instanceof SimpleLiteral) {
                    if (((SimpleLiteral) baseJstNode).getResultType() == null
                            || ((SimpleLiteral) baseJstNode).getResultType().getName().equals("String")) {
                        return;
                    }
                    fireProblem(baseJstNode, ctx);
                }
                fireProblem(baseJstNode, ctx);
            }
        }*/
    }
	
	private boolean isAssigneableError(IJstType fromType){
	    if(fromType.getName().equalsIgnoreCase("object") ||  fromType.getName().equalsIgnoreCase("vjo.object")){
	        return false;
	    }
	    JstType errorType = JstCache.getInstance().getType("Error");
        return TypeCheckUtil.isAssignable(fromType, errorType);
	}
	
	private void fireProblem(IJstNode jstNode, VjoValidationCtx ctx){
	    final BaseVjoSemanticRuleCtx ruleCtx = 
            new BaseVjoSemanticRuleCtx(jstNode, ctx.getGroupId(), new String[]{});
        satisfyRule(ctx, 
            VjoSemanticRuleRepo.getInstance().THROW_TYPE_SHOULD_COMPLY, ruleCtx);
	}
}
