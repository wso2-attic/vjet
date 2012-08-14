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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.VjoMethodControlFlowTable;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.BaseVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.InvalidIdentifierNameWithKeywordRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.MethodAndReturnFlowRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.util.TypeCheckUtil;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPreAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstMixedType;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.declaration.JstVariantType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IStmt;

/**
 * refactored @ Oct/11/2009
 * all symbol tables logic in validation is to be removed
 * 
 *
 */
public class VjoJstMethodValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPreAllChildrenListener,
		IVjoValidationPostAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(JstMethod.class);
	}

	private static final String[] VJO_KEYWORDS = {"vj$", "base", "_getBase", "constructs"};
	private static final String[] VJO_KEYWORDS_4_ETYPE = {"vj$", "base", "_getBase", "name", "ordinal", "values", "constructs"};
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}

	@Override
	public void onPreAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		if(!(jstNode instanceof JstMethod)){
			return;
		}
		
		validateBefore(ctx, (JstMethod)jstNode);
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		if(!(jstNode instanceof JstMethod)){
			return;
		}
		
		validateAfter((JstMethod)jstNode, ctx);
	}

	protected void validateAfter(final JstMethod jstMethod,
			final VjoValidationCtx ctx) {
		if(jstMethod.isAbstract()//bugfix, allow interface method empty body definition
				|| (jstMethod.getOwnerType() != null 
						&& (jstMethod.getOwnerType().isInterface() || jstMethod.getOwnerType().isOType()))){
			if(jstMethod.isFinal()){
				//report problem, method should not be both final and abstract
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(jstMethod, ctx.getGroupId(), new String[]{jstMethod.getName().getName()});
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().METHOD_OR_PROPERTY_SHOULD_NOT_BE_BOTH_FINAL_AND_ABSTRACT, ruleCtx);
			}
			
			final JstBlock blk = jstMethod.getBlock();
			if(blk != null){
				// should allow: x : function() {}
				if (blk.getStmts().size() > 0) {
					final BaseVjoSemanticRuleCtx ruleCtx = 
						new BaseVjoSemanticRuleCtx(jstMethod.getName(), ctx.getGroupId(), new String[]{jstMethod.getOwnerType().getName(), jstMethod.getName().getName()});
					satisfyRule(ctx, 
						VjoSemanticRuleRepo.getInstance().ABSTRACT_MEMBER_SHOULD_NOT_HAVE_DEFINITION, ruleCtx);
				}
			}
			//bugfix, abstract method doesn't need return
			//no annotation given likely
			return;
		}
		
		//look for return statement if function has a return type
		//add error arguments
		String[] arguments = new String[2];
		if (jstMethod.getName()!= null && jstMethod.getName().getName() != null){
			arguments[0] = jstMethod.getName().getName();
		}
		else{
			arguments[0] = "NULL";
		}
		
		final MethodAndReturnFlowRuleCtx ruleCtx = new MethodAndReturnFlowRuleCtx(jstMethod.getName(), ctx.getGroupId(), arguments, jstMethod, ctx.getMethodControlFlowTable());
		if(jstMethod.getBlock() == null){
			return;
		}
		
		final VjoMethodControlFlowTable flowTable = ctx.getMethodControlFlowTable();
		if(flowTable != null){
			// TODO support variant and mixed type testing here
			if(jstMethod.getRtnType() != null && !"void".equals(jstMethod.getRtnType().getSimpleName()) && !jstMethod.isReturnTypeOptional()){
                final List<IStmt> rtnStmts = flowTable.lookUpStmt(jstMethod);
				boolean allReturned = rtnStmts.size() > 0;
                for (IStmt stmt : rtnStmts) {
					if(stmt == null){
						//return flow issue
						allReturned = false;
						break;
					}
				}
				
				if(!allReturned){
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().NONE_VOID_METHOD_SHOULD_HAVE_RETURN, ruleCtx);
				}
			}
		}
	}

	
	protected void validateBefore(final VjoValidationCtx ctx,
			final JstMethod jstMethod){
		if(jstMethod.getName() != null){
			final String jstMethodName = jstMethod.getName().getName();
			final InvalidIdentifierNameWithKeywordRuleCtx ruleCtx = new InvalidIdentifierNameWithKeywordRuleCtx(
					jstMethod.getName(), 
					ctx.getGroupId(), 
					new String[]{jstMethodName, jstMethodName}, 
					jstMethodName, 
					jstMethod.getOwnerType() != null && jstMethod.getOwnerType().isEnum());
			satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().INVALID_IDENTIFIER_WITH_KEYWORD, ruleCtx);
		}
		
	     //Added by Eric.Ma 20100413 for handle method level generic template
        if(jstMethod.isStatic()){
            for (JstArg arg : jstMethod.getArgs()) {
                if(arg.getType() instanceof JstParamType){
                    final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(arg, ctx.getGroupId(), new String[]{arg.getType().getAlias()});
                    satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().STATIC_REFERENCE_TO_NON_STATIC_TYPE, ruleCtx);
                }
            }
        }
        //End added
		
		//bugfix 6759 & 6760
		//method couldn't be both abstract and static or both abstract and private
		if(jstMethod.getModifiers().isAbstract()){
			if(jstMethod.getModifiers().isStatic()){
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(jstMethod.getName(), ctx.getGroupId(), new String[]{jstMethod.getName().getName()});
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().METHOD_OR_PROPERTY_SHOULD_NOT_BE_BOTH_STATIC_AND_ABSTRACT, ruleCtx);
			}
			if(jstMethod.getModifiers().isPrivate()){
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(jstMethod.getName(), ctx.getGroupId(), new String[]{jstMethod.getName().getName()});
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().METHOD_OR_PROPERTY_SHOULD_NOT_BE_BOTH_PRIVATE_AND_ABSTRACT, ruleCtx);
			}
		}
		
		if (!jstMethod.isDispatcher()) {
			validateMethod(ctx, jstMethod);
		} 
		else {
			for (IJstMethod mtd : jstMethod.getOverloaded()) {
				validateMethod(ctx, (JstMethod) mtd);
			}
			validateOverloading(ctx, jstMethod, jstMethod.getOverloaded());
		}
	}

	/**
	 * validate the semantics of a method
	 * @param ctx
	 * @param jstMethod
	 */
	private void validateMethod(final VjoValidationCtx ctx,
			final JstMethod jstMethod) {
		final List<JstArg> parameters = jstMethod.getArgs();
		if(parameters != null && parameters.size() > 0){
			for(int it = 0, len = parameters.size(); it < len; it++){
				final JstArg argument = parameters.get(it);

				//further check if non-last parameter has variable length arg;
				//TODO add validation rule and related, then complete the following
				if(it < len - 1 && argument.isVariable()){
					//report problem
				}
				
				for(IJstType argType : argument.getTypes()){
					if(TypeCheckUtil.isVoid(argType)){
						final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(argument, ctx.getGroupId(), new String[]{argument.getName(), jstMethod.getName().getName()});
						satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().METHOD_ARGS_TYPE_SHOULD_NOT_BE_VOID, ruleCtx);
					}
					if(argType instanceof JstMixedType){
						JstMixedType mixedType = (JstMixedType)argType;
						for(IJstType type: mixedType.getMixedTypes()){
							
							if(getKnownType(ctx, jstMethod.getOwnerType(), type) == null){		
								if(!ctx.getMissingImportTypes().contains(type)){
									ctx.addMissingImportType(type);
								}
	
								final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(argument, ctx.getGroupId(), new String[]{type.getName()});
								satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().UNKNOWN_TYPE_MISSING_IMPORT, ruleCtx);
							}
							
							
						}
					}else if(getKnownType(ctx, jstMethod.getOwnerType(), argType) == null){
						//report problem, type unknown
						if(!ctx.getMissingImportTypes().contains(argType)){
							ctx.addMissingImportType(argType);
						}

						final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(argument, ctx.getGroupId(), new String[]{argType.getName()});
						satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().UNKNOWN_TYPE_MISSING_IMPORT, ruleCtx);
						
						//according to Justin, unresolved type should result in validation error here an here alone
						//the subsequent usage of this type shouldn't cause more errors as methods undefined etc.
						//therefore, we'll associate the missing type with VjoConstancts.Arbitary then to cut the errors.
					}
					else if (argType != null && argType instanceof JstTypeWithArgs){
						validateJstWithArgs(jstMethod.getOwnerType(), argument, argument.getName(), ctx, (JstTypeWithArgs)argType);
					}
				}
				
				final List<IJstNode> scopedVars = ctx.getMethodControlFlowTable().lookUpScopedVars(jstMethod);
				final String lhsName = argument.getName();
				if (lhsName != null) {
					boolean addScopedVar = true;
					for(IJstNode scopedVar : scopedVars){
						if(scopedVar != null){
							String scopedVarName = null;
							if(scopedVar instanceof JstIdentifier){
								scopedVarName = ((JstIdentifier)scopedVar).getName();
							}
							else if(scopedVar instanceof JstVar){
								scopedVarName = ((JstVar)scopedVar).getName();
							}
							else if(scopedVar instanceof JstArg){
								scopedVarName = ((JstArg)scopedVar).getName();
							}
							else if(scopedVar instanceof JstName){
								scopedVarName = ((JstName)scopedVar).getName();
							}
							
							if(lhsName.equals(scopedVarName)){
								//fire rules, duplicate symbols
								final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(argument, ctx.getGroupId(), new String[]{argument.getName()});
								satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().VARIABLE_ALREADY_DEFINED, ruleCtx);
								addScopedVar = false;
								break;
							}
						}
					}
					if(addScopedVar){
						ctx.getMethodControlFlowTable().addScopedVar(jstMethod, argument);
					}
				}
			}
		}
		
		final IJstType rtnType = jstMethod.getRtnType();
		if (rtnType instanceof JstVariantType) {
			validateReturnType(ctx, jstMethod, (JstVariantType)rtnType);
		}
		else if (rtnType instanceof JstMixedType) {
			validateReturnType(ctx, jstMethod, (JstMixedType)rtnType);
		}
		else {
			validateReturnType(ctx, jstMethod, rtnType);
		}
	}
	
	private void validateReturnType(final VjoValidationCtx ctx, final IJstMethod jstMethod, JstVariantType rtnType) {
		for (IJstType type: rtnType.getVariantTypes()) {
			validateReturnType(ctx, jstMethod, type);
		}
	}
	
	private void validateReturnType(final VjoValidationCtx ctx, final IJstMethod jstMethod, JstMixedType rtnType) {
		for (IJstType type: rtnType.getMixedTypes()) {
			validateReturnType(ctx, jstMethod, type);
		}
	}
	
	private void validateReturnType(final VjoValidationCtx ctx, final IJstMethod jstMethod, IJstType rtnType) {
		if(rtnType != null){
			if(getKnownType(ctx, jstMethod.getOwnerType(), rtnType) == null){
				//report problem, type unknown
				if(rtnType.getPackage().getName() == "" && !"void".equals(rtnType.getName())){
					if(!ctx.getMissingImportTypes().contains(rtnType)){
						ctx.addMissingImportType(rtnType);
					}

					final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(jstMethod.getName(), ctx.getGroupId(), new String[]{rtnType.getName()});
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().UNKNOWN_TYPE_MISSING_IMPORT, ruleCtx);
				}
			}
			
			if (rtnType != null && rtnType instanceof JstTypeWithArgs){
				validateJstWithArgs(jstMethod.getOwnerType(), jstMethod.getName(), jstMethod.getName().getName(), ctx, (JstTypeWithArgs)rtnType);
			}
		}
	}

	protected String[] getVjoKeywords(final IJstMethod jstMethod) {
		return jstMethod.getOwnerType() != null && jstMethod.getOwnerType().isEnum() ? VJO_KEYWORDS_4_ETYPE : VJO_KEYWORDS;
	}

	private void validateOverloading(final VjoValidationCtx ctx,
			final IJstMethod hostMtd, 
			final List<IJstMethod> overloads){
		//for overloading methods, we need to check their parameter list to make sure
		//there won't be ambiguous invocations
		final int len = overloads.size();
		if(len > 1){
			for(int out = 0; out < len; out++){
				final IJstMethod outMtd = overloads.get(out);
				for(int in = out + 1; in < len; in++){
					final IJstMethod inMtd = overloads.get(in);
					if(outMtd == inMtd 
							|| outMtd.getOwnerType() != inMtd.getOwnerType()){
						continue;
					}
					
					final List<JstArg> fromParameters = new ArrayList<JstArg>(outMtd.getArgs());
					final List<JstArg> toParameters = new ArrayList<JstArg>(inMtd.getArgs());
					
					if(checkAmbiguityBetween(fromParameters, toParameters, outMtd.getRtnType(), inMtd.getRtnType())){
						final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(hostMtd.getName(), ctx.getGroupId(), new String[]{hostMtd.getName().getName()});
						satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().OVERLOAD_METHOD_SHOULD_NOT_OVERLAP, ruleCtx);
						return;//report once and return early
					}
				}
			}
		}
	}
	
	/**
	 * <p>
	 *  this method detects if the 2 list of parameters possibly overlap over some arguments, and therefore ambiguous 
	 * </p>
	 * @param args0
	 * @param args1
	 * @return
	 */
	/**
	 * simplified by huzhou@ebay.com as the overloading logic is enhanced by @see TranslateHelper#createOverloads
	 * @param iJstType2 
	 * @param selfRtnType 
	 */
	public boolean checkAmbiguityBetween(final List<JstArg> selfParameters, 
			final List<JstArg> otherParameters, 
			final IJstType selfRtnType, 
			final IJstType otherRtnType){
		//end of recursion
		//check size condition
		if(selfParameters.size() == 0 && otherParameters.size() == 0){
			return true;
		}
		else if(selfParameters.size() == 0 && otherParameters.size() > 0){
			return otherParameters.get(0).isVariable();
		}
		else if(selfParameters.size() > 0 && otherParameters.size() == 0){
			return selfParameters.get(0).isVariable();
		}
		else{//selfParameters.size() > 0 && otherParameters.size() > 0
			//recursion
			//if both are variable, it's ambiguous
			//if both are the same type, we can proceed the recursion
			//if none of above, it's not ambiguous
			final JstArg selfParameter = selfParameters.remove(0);
			final JstArg otherParameter = otherParameters.remove(0);
			if(selfParameter.isVariable() && otherParameter.isVariable()){
				//if the rtn types are the same, there's no difference between 0 arg invocations at all
				//since javascript has to use only one implementation (there's no way for code to differ from 0 arguments)
				//we take such signatures as unified, and not ambiguous
				return selfRtnType != otherRtnType;
			}
			else if(typeEquals(selfParameter.getType(), otherParameter.getType())){
				return checkAmbiguityBetween(selfParameters, otherParameters, selfRtnType, otherRtnType);
			}
			else{
				return false;
			}
		}
	}
	
	private boolean typeEquals(final IJstType a, final IJstType b){
		if(a == null 
				|| b == null
				|| a.getName() == null
				|| b.getName() == null){
			return false;
		}
		
		return a.getName().equals(b.getName());
	}
}
