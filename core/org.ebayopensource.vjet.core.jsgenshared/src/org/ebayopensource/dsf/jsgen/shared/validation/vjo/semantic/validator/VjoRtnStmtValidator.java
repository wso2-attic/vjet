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

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticValidator;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.BaseVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.MethodAndReturnFlowRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.util.TypeCheckUtil;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.util.JstBindingUtil;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;

public class VjoRtnStmtValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPostAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;

	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(RtnStmt.class);
        s_targetTypes.add(ThrowStmt.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
        final VjoValidationCtx ctx = event.getValidationCtx();
        final IJstNode jstNode = event.getVisitNode();

        // Modified by Eric.Ma on 20100617 for add throw statement as return
        // statement
        if (!(jstNode instanceof RtnStmt) && !(jstNode instanceof ThrowStmt)) {
            return;
        }
        final JstMethod meth = lookUpMethod(jstNode);

        if (jstNode instanceof ThrowStmt) {
        	if(!(jstNode.getParentNode() instanceof JstBlock 
        			&& jstNode.getParentNode().getParentNode() instanceof SwitchStmt)){
        		ctx.getMethodControlFlowTable().addStmt(meth, (ThrowStmt) jstNode);
        	}
        } 
        else {
            final RtnStmt rtnStmt = (RtnStmt) jstNode;
            // npe check for bug 5908, as init block could have early return
            // while no jstmethod exists
            if (meth == null) {
                return;
            }

            // Get return types from method signatures including overloaded
            // methods
            final List<IJstType> rtnTypes = JstTypeHelper.getRtnTypes(meth);
            if(!(jstNode.getParentNode() instanceof JstBlock 
        			&& jstNode.getParentNode().getParentNode() instanceof SwitchStmt)){
            	ctx.getMethodControlFlowTable().addStmt(meth, rtnStmt);
        	}

            final IExpr rtnExpr = rtnStmt.getExpression();
            final IJstNode rtnBinding = JstBindingUtil.getJstBinding(rtnExpr);
            final List<IJstType> rtnValues = new ArrayList<IJstType>();

            if (rtnBinding != null && rtnBinding instanceof JstArg) {
                rtnValues.addAll(((JstArg) rtnBinding).getTypes());
            } else {
                if (rtnExpr != null && rtnExpr.getResultType() != null) {
                    rtnValues.add(rtnExpr.getResultType());
                }
            }

            JstTypeWithArgs typeWithArgs = null;

            if (rtnExpr != null) {
                if (rtnExpr instanceof FieldAccessExpr) {
                    IExpr qualifier = ((FieldAccessExpr) rtnExpr).getExpr();

                    if (qualifier != null) {
                        IJstType resultType = qualifier.getResultType();

                        if (resultType != null && resultType instanceof JstTypeWithArgs) {
                            typeWithArgs = (JstTypeWithArgs) resultType;
                        }
                    }
                } else if (rtnExpr instanceof MtdInvocationExpr) {
                    IExpr qualifier = ((MtdInvocationExpr) rtnExpr).getQualifyExpr();

                    if (qualifier != null) {
                        IJstType resultType = qualifier.getResultType();

                        if (resultType != null && resultType instanceof JstTypeWithArgs) {
                            typeWithArgs = (JstTypeWithArgs) resultType;
                        }
                    }
                }
            }

            // by roy, in case method is void
            boolean allVoid = true;
            boolean allNoneVoid = true;
            for (IJstType rtnType : rtnTypes) {
                if (rtnType == null) {
                    allNoneVoid = false;
                } else if ("void".equals(rtnType.getSimpleName())) {
                    allNoneVoid = false;
                } else {
                    allVoid = false;
                }
            }

            if (allVoid) {
                final MethodAndReturnFlowRuleCtx ruleCtx = new MethodAndReturnFlowRuleCtx(rtnStmt, ctx.getGroupId(),
                        new String[] { meth.getName().getName() }, meth, ctx.getMethodControlFlowTable());
                satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().VOID_METHOD_SHOULD_NOT_HAVE_RETURN, ruleCtx);
                return;
            }

            // If we can not determine the return expression type or there is no
            // return expression,
            // then we should not report an error
            if ((rtnValues.size() == 0 && !allVoid) || rtnExpr == null) {
                return;
            }

            final String[] ruleCtxArgs = new String[4];
            final StringBuilder rtnValueSb = new StringBuilder();
            boolean first = true;
            for (IJstType rtnValue : rtnValues) {
                if (rtnValue != null && rtnValue.getName() != null) {
                    if (!first) {
                        rtnValueSb.append(",");
                    }
                    rtnValueSb.append(rtnValue.getName());
                }
                first = false;
            }
            // rtnValueSb.append("}");
            String v = rtnValueSb.toString();
            if ("".equals(v)) {
                v = "Undefined";
            }

            ruleCtxArgs[0] = v;
            ruleCtxArgs[1] = getTypeNames(rtnTypes);
            ruleCtxArgs[2] = meth.getName().getName();
            ruleCtxArgs[3] = rtnStmt.toStmtText();

            // return null; is OK
            if (rtnExpr instanceof SimpleLiteral
                    && "null".equals(rtnExpr.toExprText())) {
                return;
            } else if (rtnValues.size() == 0 && allNoneVoid) {
                final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(rtnStmt, ctx.getGroupId(),
                        ruleCtxArgs);
                satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().METHOD_RETURN_VALUE_SHOULD_COMPLY, ruleCtx);
                return;
            } else if (rtnValues.size() > 0 && !isAssignable(typeWithArgs, rtnTypes, rtnValues)) {
                final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(rtnStmt, ctx.getGroupId(),
                        ruleCtxArgs);
                satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().METHOD_RETURN_VALUE_SHOULD_COMPLY, ruleCtx);
            }
        }
		//unreachable statement validation is @ VjoJstMethodValidator
	}

	private static boolean isAssignable(JstTypeWithArgs typeWithArgs, final List<IJstType> rtnTypes, final List<IJstType> rtnValues) {
 		for (IJstType rtnType : rtnTypes) {
			for(IJstType rtnValue : rtnValues){
				if (rtnValue == null) {
					if (rtnType == null || 
						"void".equals(rtnType.getName())) {
						return true;
					}
					
				} else {
					if (rtnType == null) {
						return true;
					}
					
					if (rtnType instanceof JstTypeWithArgs && rtnValue instanceof JstTypeWithArgs) {
						if (TypeCheckUtil.isAssignable(null, typeWithArgs, (JstTypeWithArgs)rtnType, (JstTypeWithArgs)rtnValue)) {
							return true;
						}
					}
					else if (TypeCheckUtil.isAssignable(rtnType, rtnValue)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static String getTypeNames(List<IJstType> rtnTypes) {
	    //Modified by Eric.Ma 20100628 for return type mismatch problem. The reported comment is not make sense
	    HashSet<IJstType> sets = new HashSet<IJstType>(rtnTypes);
	    rtnTypes = new ArrayList<IJstType>(sets);
		if (sets.size() == 1) {
			IJstType type = rtnTypes.get(0);
			return type == null? "null" : type.getSimpleName();
		}
		StringBuilder sb = new StringBuilder();
		IJstType type = null;
		for (int i = 1; i <= rtnTypes.size(); i++) {
		    type = rtnTypes.get(i-1);
		    sb.append(type == null? "null" : type.getSimpleName());
            if (i == rtnTypes.size() - 1) {
                sb.append(" or ");
            }
            if (i < rtnTypes.size() - 1) {
                sb.append(" , ");
		    }
        }
		return sb.toString();
		//End of modified
	}
}
