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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoDependencyVerifiable;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticValidator;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.BaseVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.TypeNameShouldNotBeEmptyRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostChildListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.term.ArrayLiteral;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;

public class VjoJstBlockValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPostChildListener,
		IVjoValidationPostAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(JstBlock.class);
	}
	
	private static enum VjoUniqueMethodEnum{
		ctype,
		make,
		itype,
		etype,
		mtype,
		otype,
		inherits,
		satisfies,
		expects,
		mixin,
		defs,
		values,
		options,
		props,
		protos,
		inits,
		endType,
		needs
	}
	
	private static Map<VjoUniqueMethodEnum, Set<VjoUniqueMethodEnum>> type2mtdMap;
	static{
		type2mtdMap = new HashMap<VjoUniqueMethodEnum, Set<VjoUniqueMethodEnum>>();
		
		final Set<VjoUniqueMethodEnum> cTypeSet = new HashSet<VjoUniqueMethodEnum>();
		cTypeSet.add(VjoUniqueMethodEnum.needs);
		cTypeSet.add(VjoUniqueMethodEnum.inherits);
		cTypeSet.add(VjoUniqueMethodEnum.satisfies);
		cTypeSet.add(VjoUniqueMethodEnum.mixin);
		cTypeSet.add(VjoUniqueMethodEnum.props);
		cTypeSet.add(VjoUniqueMethodEnum.protos);
		cTypeSet.add(VjoUniqueMethodEnum.endType);
		type2mtdMap.put(VjoUniqueMethodEnum.ctype, cTypeSet);
		type2mtdMap.put(VjoUniqueMethodEnum.make, cTypeSet);
		
		final Set<VjoUniqueMethodEnum> iTypeSet = new HashSet<VjoUniqueMethodEnum>();
		iTypeSet.add(VjoUniqueMethodEnum.needs);
		iTypeSet.add(VjoUniqueMethodEnum.inherits);
		iTypeSet.add(VjoUniqueMethodEnum.props);
		iTypeSet.add(VjoUniqueMethodEnum.protos);
		iTypeSet.add(VjoUniqueMethodEnum.endType);
		type2mtdMap.put(VjoUniqueMethodEnum.itype, iTypeSet);
	
		final Set<VjoUniqueMethodEnum> eTypeSet = new HashSet<VjoUniqueMethodEnum>();
		eTypeSet.add(VjoUniqueMethodEnum.needs);
		eTypeSet.add(VjoUniqueMethodEnum.satisfies);
		eTypeSet.add(VjoUniqueMethodEnum.values);
		eTypeSet.add(VjoUniqueMethodEnum.props);
		eTypeSet.add(VjoUniqueMethodEnum.protos);
		eTypeSet.add(VjoUniqueMethodEnum.endType);
		type2mtdMap.put(VjoUniqueMethodEnum.etype, eTypeSet);
		
		final Set<VjoUniqueMethodEnum> mTypeSet = new HashSet<VjoUniqueMethodEnum>();
		mTypeSet.add(VjoUniqueMethodEnum.needs);
		mTypeSet.add(VjoUniqueMethodEnum.satisfies);
		mTypeSet.add(VjoUniqueMethodEnum.expects);
		mTypeSet.add(VjoUniqueMethodEnum.props);
		mTypeSet.add(VjoUniqueMethodEnum.protos);
		mTypeSet.add(VjoUniqueMethodEnum.endType);
		type2mtdMap.put(VjoUniqueMethodEnum.mtype, mTypeSet);
		
		final Set<VjoUniqueMethodEnum> oTypeSet = new HashSet<VjoUniqueMethodEnum>();
		oTypeSet.add(VjoUniqueMethodEnum.defs);
		oTypeSet.add(VjoUniqueMethodEnum.endType);
		type2mtdMap.put(VjoUniqueMethodEnum.otype, oTypeSet);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}

	@Override
	public void onPostChildEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		final IJstNode child = event.getVisitChildNode();
		
		if(!(jstNode instanceof JstBlock)){
			return;
		}
		final JstBlock jstBlock = (JstBlock)jstNode;
		//look for unreachable code
		if(jstBlock.getParentNode() != null
				&& jstBlock.getParentNode() instanceof SwitchStmt){
			return;//switch block has multiple returns
		}
		
		final JstMethod method = lookUpMethod(jstNode);
		if(method != null){
			if(ctx.getMethodControlFlowTable().hasBlockUnreachableValidated(jstBlock)){
				return;
			}
			
            final List<IStmt> rtnStmts = ctx.getMethodControlFlowTable().lookUpStmt(method, false);
			boolean rtnFlowCompleted = false;
            for (IStmt rtnStmt : rtnStmts) {
				if(rtnStmt != null){
					rtnFlowCompleted = true;
				}
				else{
					rtnFlowCompleted = false;
					break;
				}
			}
			
			if(rtnFlowCompleted){
				boolean unreachable = false;
				ctx.getMethodControlFlowTable().setBlockUnreachableValidated(jstBlock, true);
				for(IStmt stmt: jstBlock.getStmts()){
					if(unreachable){
						final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(stmt, ctx.getGroupId(), new String[]{method.getName().getName(), stmt.toStmtText()});
						satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().STMT_SHOULD_BE_REACHABLE, ruleCtx);
					}
					if(child == stmt){
						unreachable = true;
					}
				}
			}
		}
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		
		if(!(jstNode instanceof JstBlock)){
			return;
		}
		final JstBlock jstBlock = (JstBlock)jstNode;
		//look for unreachable code
		if(jstBlock.getParentNode() != null){
			return;//switch block has multiple returns
		}
		
		/*
		 * [ctype, mtype, itype,]
		 * >>
		 * [needs, satisfies, inherits, mixin]
		 * >>
		 * [protos, props]
		 * >>
		 * [endType]
		 */
		
		for(IJstNode stmt : jstBlock.getChildren()){
			isSyntaxCorrect(this, ctx, stmt);
		}
	}
	
	public static boolean isSyntaxCorrect(final VjoSemanticValidator validator, final VjoValidationCtx ctx, IJstNode rootStmt){
		final IJstType syntaxOwnerType = ctx.getScope().getClosestTypeScopeNode();
		
		if(!(rootStmt instanceof MtdInvocationExpr)){
			//report problem, syntax not by contract
			final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(rootStmt, ctx.getGroupId(),new String[]{rootStmt.toString(), rootStmt.toString()});
			validator.satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().VJO_SYNTAX_CORRECTNESS, ruleCtx);
			return false;
		}
		else{
			final MtdInvocationExpr mtdExpr = (MtdInvocationExpr)rootStmt;
			final Map<VjoUniqueMethodEnum, Integer> mtdCountMap = new HashMap<VjoUniqueMethodEnum, Integer>();
			for(VjoUniqueMethodEnum enumValue : VjoUniqueMethodEnum.values()){
				mtdCountMap.put(enumValue, Integer.valueOf(0));
			}
			
			//bugfix, syntax type name itself should be added to namespace for collision prevention
			final Map<String, SimpleLiteral> nameSpace = new HashMap<String, SimpleLiteral>();
			final SimpleLiteral typeLiteral = new SimpleLiteral(String.class, syntaxOwnerType, syntaxOwnerType.getName());
			typeLiteral.setSource(syntaxOwnerType.getSource());
			
			nameSpace.put(getSimpleName(syntaxOwnerType.getName(), null), typeLiteral);
			
			if(!isInactiveNeedsValid(syntaxOwnerType, ctx, validator)){
				return false;
			}
			
			boolean correct = isSyntaxCorrect(validator, ctx, mtdExpr, mtdCountMap, nameSpace);
			if(correct){
				if(mtdCountMap.get(VjoUniqueMethodEnum.endType).intValue() <= 0 &&
						(mtdCountMap.get(VjoUniqueMethodEnum.ctype).intValue() > 0
								|| mtdCountMap.get(VjoUniqueMethodEnum.make).intValue() > 0
								|| mtdCountMap.get(VjoUniqueMethodEnum.itype).intValue() > 0
								|| mtdCountMap.get(VjoUniqueMethodEnum.etype).intValue() > 0
								|| mtdCountMap.get(VjoUniqueMethodEnum.mtype).intValue() > 0
								|| mtdCountMap.get(VjoUniqueMethodEnum.otype).intValue() > 0)){
					final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(rootStmt, ctx.getGroupId(),new String[]{syntaxOwnerType.getName(), rootStmt.toString()});
					validator.satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().MISSING_ENDTYPE, ruleCtx);
					correct = false;
				}
			}
			
			return correct;
		}
	}
	

	private static boolean isInactiveNeedsValid(IJstType syntaxOwnerType,
			VjoValidationCtx ctx, final VjoSemanticValidator validator) {
		List<? extends IJstType> lists = syntaxOwnerType.getInactiveImports();
		if(syntaxOwnerType instanceof JstType && lists.size()>0){
			String typeName = null;
			for (IJstType jstType : lists) {
				typeName = jstType.getName();
				if(jstType.getPackage() == null){
					final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(syntaxOwnerType, ctx.getGroupId(),new String[]{typeName});
					validator.satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().UNKNOWN_TYPE_NOT_IN_TYPE_SPACE, ruleCtx);
					ctx.addUnresolvedType(typeName);
					return false;
				}else{
					if(!ctx.getDependencyVerifier(syntaxOwnerType).verify(syntaxOwnerType, typeName)){
						if(typeName.length() > 0){//Cover empty needs situation.
							final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(syntaxOwnerType, ctx.getGroupId(),new String[]{typeName});
							validator.satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().UNKNOWN_TYPE_NOT_IN_TYPE_SPACE_INACTIVENEEDS, ruleCtx);
						}
						ctx.addUnresolvedType(typeName);
						return false;
					}
				}
			}
		}
		return true;
	}

	private static boolean isSyntaxCorrect(final VjoSemanticValidator validator,
			final VjoValidationCtx ctx,
			final MtdInvocationExpr mtdExpr, 
			final Map<VjoUniqueMethodEnum, Integer> methodCountMap, 
			final Map<String, SimpleLiteral> nameSpace){
		if(mtdExpr == null){
			return true;
		}
		
		final IExpr qualifier = mtdExpr.getQualifyExpr();		
		final IExpr identifier = mtdExpr.getMethodIdentifier();
		final IJstType syntaxOwnerType = ctx.getScope().getClosestTypeScopeNode();
		
		//end of recursion
		//vjo.ctype
		//vjo.itype
		//vjo.mtype
		//vjo.etype
		//vjo.otype
		if(qualifier instanceof JstIdentifier){
			final IJstType vjo = qualifier.getResultType();
			
			if(vjo == null){
				//early fail, can't do any syntax validation
				return false;
			}
			if(!(identifier instanceof JstIdentifier)){
				return false;
			}
			else{
				final IJstType vjoType = vjo;
				final String mtdId = ((JstIdentifier)identifier).getName();
//				final VjoSymbolTable symbolTable = ctx.getSymbolTable();
//				final IVjoSymbol mtdSymbol = symbolTable.getSymbolInScope(vjoType, mtdId, vjo instanceof JstTypeRefType ? EVjoSymbolType.STATIC_VARIABLE : EVjoSymbolType.INSTANCE_VARIABLE);
				final IJstMethod mtdSymbol = vjoType instanceof IJstRefType
					? ((IJstRefType)vjoType).getReferencedNode().getMethod(mtdId, true, true)
							: vjoType.getMethod(mtdId, false, true);
				if(mtdSymbol == null){
					return false;
				}
				else{
					try{
						final VjoUniqueMethodEnum mtdEnum = VjoUniqueMethodEnum.valueOf(mtdId);
						if(mtdEnum != null){
							methodCountMap.put(mtdEnum, Integer.valueOf(methodCountMap.get(mtdEnum).intValue() + 1));
							if(VjoUniqueMethodEnum.ctype.equals(mtdEnum)
									|| VjoUniqueMethodEnum.itype.equals(mtdEnum)
									|| VjoUniqueMethodEnum.etype.equals(mtdEnum)
									|| VjoUniqueMethodEnum.mtype.equals(mtdEnum)
									|| VjoUniqueMethodEnum.otype.equals(mtdEnum)){
								final List<IExpr> args = mtdExpr.getArgs();
								if(args.size() >= 1){
									final IExpr arg = args.get(0);
									if(arg instanceof SimpleLiteral){
										final String typeName = ((SimpleLiteral)arg).getValue();									
										if(typeName != null && typeName.length() > 0) {
											if(ctx.getJstTypeNames().size() == 0){
												//top level type
												ctx.addJstTypeName(typeName);
												return true;
											}
											//inner type name should be null, except generic template
											if(typeName.startsWith("<") && typeName.endsWith(">")
												&& (VjoUniqueMethodEnum.ctype.equals(mtdEnum) ||
													VjoUniqueMethodEnum.itype.equals(mtdEnum))) {
												//ok for inner ctype and itype to have generic param
											} else{
												//report problem
												final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(identifier, ctx.getGroupId(),new String[]{typeName, mtdExpr.toExprText()});
												validator.satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().VJO_SYNTAX_CORRECTNESS, ruleCtx);
											}
										}
									}
								}
							}
						}
					}
					catch(IllegalArgumentException ex){
						return false;
					}
				}
			}
		}
		else if(qualifier instanceof MtdInvocationExpr){
			if(!isSyntaxCorrect(validator, ctx, (MtdInvocationExpr)qualifier, methodCountMap, nameSpace)){
				//report problem
				return false;
			}
			
			final IJstType qualifierType = qualifier.getResultType();//validator.lookUpValue(ctx, qualifier);
			if(qualifierType == null){
				return false;
			}
			if(!(identifier instanceof JstIdentifier)){
				return false;
			}
			else{
				final String mtdId = ((JstIdentifier)identifier).getName();
				final IJstMethod mtdSymbol = qualifierType instanceof IJstRefType
				? ((IJstRefType)qualifierType).getReferencedNode().getMethod(mtdId, true, true)
						: qualifierType.getMethod(mtdId, false, true);
				if(mtdSymbol == null){
					return false;
				}
				
				try{
					final VjoUniqueMethodEnum mtdEnum = VjoUniqueMethodEnum.valueOf(mtdId);
					if(mtdEnum != null){
						methodCountMap.put(mtdEnum, Integer.valueOf(methodCountMap.get(mtdEnum).intValue() + 1));
					}
					
					if(VjoUniqueMethodEnum.needs.equals(mtdEnum)
							|| VjoUniqueMethodEnum.inherits.equals(mtdEnum)
							|| VjoUniqueMethodEnum.satisfies.equals(mtdEnum)
							|| VjoUniqueMethodEnum.mixin.equals(mtdEnum)
							|| VjoUniqueMethodEnum.defs.equals(mtdEnum)
							|| VjoUniqueMethodEnum.expects.equals(mtdEnum)){
						
						final List<IExpr> arguments = mtdExpr.getArgs();
						final IVjoDependencyVerifiable depTypes = ctx.getDependencyVerifier(syntaxOwnerType);
						
						if(arguments != null){
							if(arguments.size() > 0){
								final IExpr arg = arguments.get(0);
								final IExpr optionalArg = arguments.size() > 1 ? arguments.get(1) : null;
								
								if(arg instanceof SimpleLiteral){
									validateArgument(validator, ctx, nameSpace,
											syntaxOwnerType, mtdEnum, depTypes,
											arg, optionalArg);
								}
								else if(arg instanceof ArrayLiteral){
									//array of strings
									for(Iterator<IExpr> elemIt = ((ArrayLiteral)arg).getValues(); elemIt.hasNext();){
										final IExpr elemExpr = elemIt.next();
										if(SimpleLiteral.class.isAssignableFrom(elemExpr.getClass())){
											validateArgument(validator, ctx, nameSpace,
													syntaxOwnerType, mtdEnum, depTypes,
													elemExpr, optionalArg);
										}
									}
								}
								else if(arg instanceof JstArrayInitializer){
									//array of strings
									for(Iterator<IExpr> elemIt = ((JstArrayInitializer)arg).getExprs().iterator(); elemIt.hasNext();){
										final IExpr elemExpr = elemIt.next();
										if(SimpleLiteral.class.isAssignableFrom(elemExpr.getClass())){
											validateArgument(validator, ctx, nameSpace,
													syntaxOwnerType, mtdEnum, depTypes,
													elemExpr, optionalArg);
										}
									}
								}
							}
						}
					}
				}
				catch(IllegalArgumentException ex){
					//not such method
					return false;
				}
			}
		}
		else{
			//report problem
			return false;
		}
		
		return true;
	}
	
	private static void validateArgument(
			final VjoSemanticValidator validator, final VjoValidationCtx ctx,
			final Map<String, SimpleLiteral> nameSpace,
			final IJstType syntaxOwnerType, final VjoUniqueMethodEnum mtdEnum,
			final IVjoDependencyVerifiable depVerifier, final IExpr arg,
			final IExpr optionalArg) {
		//string
		String name = ((SimpleLiteral)arg).getValue();
		//bugfix, inherits/satisfying... templating types
		final int templateIndex = name.indexOf("<");
		if(templateIndex > 0){
			name = name.substring(0, templateIndex);
		}
		
		final String simpleName = getSimpleName(name, optionalArg);
		final TypeNameShouldNotBeEmptyRuleCtx notEmptyTypeNameruleCtx = new TypeNameShouldNotBeEmptyRuleCtx(arg, ctx.getGroupId(), new String[]{simpleName}, simpleName);
		validator.satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().TYPE_NAME_SHOULD_NOT_BE_EMPTY, notEmptyTypeNameruleCtx);
		
		if(nameSpace.containsKey(simpleName)){
			if(!name.equals(nameSpace.get(simpleName).getValue())){
				//report problem
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(arg, ctx.getGroupId(),new String[]{nameSpace.get(simpleName).getValue(), name});
				validator.satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().NAME_SPACE_COLLISION, ruleCtx);
//											return false;//stop validation as this type won't be imported
			}
			else{
				//report problem
				if(VjoUniqueMethodEnum.needs.equals(mtdEnum)){
					//current needs is unneeded
					final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(arg, ctx.getGroupId(),new String[]{syntaxOwnerType.getName(), name});
					validator.satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().REDUNDANT_IMPORT, ruleCtx);
				}
				else{
					IJstNode argPrev = nameSpace.get(simpleName);
					if(argPrev != null){
						if(argPrev.getParentNode() != null && argPrev.getParentNode() instanceof JstArrayInitializer){
							argPrev = argPrev.getParentNode();
						}
						if(argPrev.getParentNode() != null && argPrev.getParentNode() instanceof MtdInvocationExpr){
							final IExpr mtdInvocationExprId = ((MtdInvocationExpr)argPrev.getParentNode()).getMethodIdentifier();
							if(mtdInvocationExprId != null && mtdInvocationExprId instanceof JstIdentifier){
								if(VjoUniqueMethodEnum.needs.name().equals(((JstIdentifier)mtdInvocationExprId).getName())){
									final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(nameSpace.get(simpleName), ctx.getGroupId(),new String[]{syntaxOwnerType.getName(), name});
									validator.satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().REDUNDANT_IMPORT, ruleCtx);
								}
								else if(mtdEnum.name().equals(((JstIdentifier)mtdInvocationExprId).getName())){
									final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(arg, ctx.getGroupId(),new String[]{syntaxOwnerType.getName(), name});
									validator.satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().REDUNDANT_IMPORT, ruleCtx);
								}
							}
						}
						nameSpace.put(simpleName, (SimpleLiteral)arg);
					}
				}
			}
		}
		else{
			nameSpace.put(simpleName, (SimpleLiteral)arg);
		}
		
		validateTypeResolution(validator, ctx, syntaxOwnerType, depVerifier, arg, name);
	}
	
	private static String getSimpleName(final String name, final IExpr optionalArgExpr){
		if(name == null){
			return "";
		}
		if(optionalArgExpr != null){
			if(SimpleLiteral.class.isAssignableFrom(optionalArgExpr.getClass())){
				String alias = ((SimpleLiteral)optionalArgExpr).getValue();
				if (alias != null) {
					return alias.length() == 0 ? name : alias;
				}
			}
		}
		
		final int lastDotIndex = name.lastIndexOf('.');
		if(lastDotIndex >= 0 && lastDotIndex < name.length() - 1){
			return name.substring(lastDotIndex + 1);
		}
		else{
			return name;
		}
	}
}
