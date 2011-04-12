/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import org.ebayopensource.dsf.javatojs.anno.ASupportJsForEachStmt;
import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.translate.config.JavaTranslationConvention;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.datatype.JstReservedTypes;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.ArrayAccessExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.JstInitializer;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.TextExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr.Operator;
import org.ebayopensource.dsf.jst.stmt.BlockStmt;
import org.ebayopensource.dsf.jst.stmt.BreakStmt;
import org.ebayopensource.dsf.jst.stmt.CatchStmt;
import org.ebayopensource.dsf.jst.stmt.ContinueStmt;
import org.ebayopensource.dsf.jst.stmt.DoStmt;
import org.ebayopensource.dsf.jst.stmt.ForInStmt;
import org.ebayopensource.dsf.jst.stmt.ForStmt;
import org.ebayopensource.dsf.jst.stmt.IfStmt;
import org.ebayopensource.dsf.jst.stmt.LabeledStmt;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.stmt.ThisStmt;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;
import org.ebayopensource.dsf.jst.stmt.TryStmt;
import org.ebayopensource.dsf.jst.stmt.TypeDeclStmt;
import org.ebayopensource.dsf.jst.stmt.WhileStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt.CaseStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IBoolExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.vjo.meta.VjoConvention;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class StatementTranslator extends BaseTranslator {
	
	private static final String CATCH_VAR_NAME = "e";
	
	//
	// API
	//
	public IStmt processStatement(final Statement astStmt, final BaseJstNode jstBlock){
		
		if (jstBlock == null || jstBlock.getOwnerType() == null){
			return null;
		}
		
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstBlock.getOwnerType());
		
		IStmt jstStmt = null;
		
		if (tInfo.getMode().hasImplementation()){
		
			if (astStmt instanceof VariableDeclarationStatement){
				jstStmt = toAssignExpr((VariableDeclarationStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof ConstructorInvocation){
				jstStmt = toMtdInvocation((ConstructorInvocation)astStmt, jstBlock);
			}
			else if (astStmt instanceof SuperConstructorInvocation){
				jstStmt = toMtdInvocation((SuperConstructorInvocation)astStmt, jstBlock);
			}
			else if (astStmt instanceof ExpressionStatement){
				jstStmt = toExprStmt((ExpressionStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof ForStatement){
				jstStmt = toForStmt((ForStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof EnhancedForStatement){
				jstStmt = toForEachStmt((EnhancedForStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof WhileStatement){
				jstStmt = toWhileStmt((WhileStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof DoStatement){
				jstStmt = toDoStmt((DoStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof IfStatement){
				jstStmt = toIfStmt((IfStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof SwitchStatement){
				jstStmt = toSwitchStmt((SwitchStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof SwitchCase){
				SwitchCase c = (SwitchCase)astStmt;
				jstStmt = new CaseStmt(getExprTranslator().processExpression(c.getExpression(), jstBlock));
			}
			else if (astStmt instanceof BreakStatement){
				jstStmt = toBreakStmt((BreakStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof ContinueStatement){
				jstStmt = toContinueStmt((ContinueStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof LabeledStatement){
				jstStmt = toLabeledStmt((LabeledStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof ThrowStatement){
				jstStmt = toThrowStmt((ThrowStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof TryStatement){
				jstStmt = toTryStmt((TryStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof ReturnStatement){
				jstStmt = toReturnStmt((ReturnStatement)astStmt, jstBlock);
			}
			else if (astStmt instanceof SynchronizedStatement){
				jstStmt = toBlockStmt(((SynchronizedStatement)astStmt).getBody(), jstBlock);
			}
			else if (astStmt instanceof AssertStatement){
				getLogger().logWarning(TranslateMsgId.UNSUPPORTED_NODE, 
					astStmt.getClass().getSimpleName() + " not translated", this, astStmt, jstBlock);
				jstStmt = null;
			}
			else if (astStmt instanceof EmptyStatement){
				jstStmt = null;
			}
			else if (astStmt instanceof Block){
				BlockStmt blockStmt = new BlockStmt();
				jstBlock.addChild(blockStmt);
				getOtherTranslator().processBlock((Block)astStmt, blockStmt.getBody());
				return blockStmt;
			}
			else if (astStmt instanceof TypeDeclarationStatement){
				TypeDeclarationStatement tStmt = (TypeDeclarationStatement)astStmt;
				JstType localType = getCtx().getTranslateInfo(jstBlock.getRootType()).getLocalType(tStmt);
				if (localType == null){
					getLogger().logError(TranslateMsgId.MISSING_DATA_IN_TRANSLATE_INFO, 
						"local type not found", this, tStmt, jstBlock);
					return null;
				}
				TypeDeclStmt jstTypeStmt = new TypeDeclStmt(localType);
				jstBlock.addChild(jstTypeStmt);
				getCtx().getTranslateInfo(jstTypeStmt.getType()).getMode().addImplementation();
				getTypeTranslator().processType(tStmt.getDeclaration(), jstTypeStmt.getType());
				return jstTypeStmt;
			}
			else {
				getLogger().logUnhandledNode(this, astStmt, jstBlock);
			}
		}
		else if (tInfo.getMode().hasDependency()){
			if (astStmt instanceof ForStatement){
				jstStmt = new ForStmt();
			}
			else if (astStmt instanceof EnhancedForStatement){
				jstStmt = new ForStmt();
			}
			else if (astStmt instanceof WhileStatement){
				jstStmt = new WhileStmt();
			}
			else if (astStmt instanceof DoStatement){
				jstStmt = new DoStmt();
			}
			else if (astStmt instanceof IfStatement){
				jstStmt = new IfStmt();
			}
			else if (astStmt instanceof SwitchStatement){
				jstStmt = new SwitchStmt();
			}
			else if (astStmt instanceof TryStatement){
				jstStmt = new TryStmt();
			}
			else if (astStmt instanceof SynchronizedStatement){
				jstStmt = new BlockStmt();
			}
			else if (astStmt instanceof Block){
				jstStmt = new BlockStmt();
			}
		}
		
		if (jstStmt != null){
			jstBlock.addChild(jstStmt);
		}
		
		return jstStmt;
	}
	
	//
	// Private
	//
	private JstVars toAssignExpr(final VariableDeclarationStatement astStmt, final BaseJstNode jstBlock){
		return getExprTranslator().toJstVars(astStmt.getType(), astStmt.fragments(), jstBlock);
	}
	
	private MtdInvocationExpr toMtdInvocation(final ConstructorInvocation astStmt, final BaseJstNode jstBlock){

		ThisStmt mtdCall = new ThisStmt(VjoConvention.getThisPrefix()+ "." + VjoKeywords.CONSTRUCTS);
		List<IExpr> jstArgs = new ArrayList<IExpr>();
		for (Object a: astStmt.arguments()){
			if (a instanceof Expression) {	
				jstArgs.add(getExprTranslator().processExpression((Expression)a, jstBlock));
			} else {
				jstArgs.add(new JstIdentifier(a.toString()));
			}
		}
		mtdCall.setArgs(jstArgs);
		JstType ownerType = jstBlock.getOwnerType();
		TranslateInfo tInfo = getCtx().getTranslateInfo(ownerType);
		Map<Integer,List<JstMethod>> map = tInfo.getOverloaded(VjoKeywords.CONSTRUCTS, false);
		if (!map.isEmpty()){
			Integer argCount = new Integer(astStmt.arguments().size());
			List<JstMethod> list = map.get(argCount);
			if (list == null){
				list = new ArrayList<JstMethod>();
				map.put(argCount, list);
			}
		}
		mtdCall.setConstructor(TranslateHelper.Method.getConstructor(ownerType, jstArgs));
		return mtdCall;
	}
	
	private MtdInvocationExpr toMtdInvocation(final SuperConstructorInvocation astStmt, final BaseJstNode jstBlock){

		MtdInvocationExpr mtdCall = new MtdInvocationExpr(getCtx().getConfig().getVjoConvention().getBasePrefix());
		for (Object a: astStmt.arguments()){
			if (a instanceof Expression){
				mtdCall.addArg(getExprTranslator().processExpression((Expression)a, jstBlock));
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)a, jstBlock);
			}
		}
		return mtdCall;
	}
	
	private IStmt toExprStmt(final ExpressionStatement astStmt, final BaseJstNode jstBlock){

		Expression e = astStmt.getExpression();
		return getExprTranslator().toStmt(e, jstBlock);
	}
	
	private ForStmt toForStmt(final ForStatement astStmt, final BaseJstNode jstBlock){

		ExpressionTranslator exprTranslator = getExprTranslator();
		
		ForStmt forStmt = new ForStmt();
		jstBlock.addChild(forStmt);
		JstInitializer jstInitializers = null;
		for (Object o: astStmt.initializers()){
			if (o instanceof VariableDeclarationExpression){
				VariableDeclarationExpression vde = (VariableDeclarationExpression)o;
				forStmt.setInitializer(exprTranslator.toJstVars(vde.getType(), vde.fragments(), jstBlock));
			}
			else if (o instanceof Assignment){
				if (jstInitializers == null){
					jstInitializers = new JstInitializer();
					forStmt.setInitializer(jstInitializers);
				}
				jstInitializers.addAssignment(exprTranslator.toAssignExpr((Assignment)o, jstBlock));	
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)o, jstBlock);
			}
		}
		Expression astCond = astStmt.getExpression();
		if (astCond != null && (astCond instanceof InfixExpression || astCond instanceof PrefixExpression)){
			forStmt.setCondition(getExprTranslator().toBoolExpr(astCond, jstBlock));
		} else if (astCond instanceof MethodInvocation){
			IExpr e = exprTranslator.processExpression(astCond, jstBlock);
			forStmt.setCondition(new BoolExpr(e));		
		}
		for (Object o: astStmt.updaters()){
			if (o instanceof Expression){
				forStmt.addUpdater(getExprTranslator().processExpression((Expression)o, jstBlock));
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)o, jstBlock);
			}
		}
		Statement body = astStmt.getBody();
		if (body instanceof Block){
			Block block = (Block)body;
			for (Object o: block.statements()){
				if (o instanceof Statement){
					forStmt.addStmt(processStatement((Statement)o, forStmt.getBody()));
				}
				else {
					getLogger().logUnhandledNode(this, (ASTNode)o, jstBlock);
				}
			}
		}
		else if (body instanceof Statement){
			forStmt.addStmt(processStatement((Statement)body, forStmt.getBody()));
		}
		else {
			getLogger().logUnhandledNode(this, (ASTNode)body, jstBlock);
		}
		
		return forStmt;
	}
		
	private IStmt toForEachStmt(final EnhancedForStatement astStmt, final BaseJstNode jstBlock){
		
		JavaTranslationConvention convention = 
			TranslateCtx.ctx().getConfig().getJavaTranslationConvention();
		
		ExpressionTranslator exprTranslator = getExprTranslator();		
		IExpr jstExpr = exprTranslator.processExpression(astStmt.getExpression(), jstBlock);
		JstVar jstVar = getOtherTranslator().toJstVar(astStmt.getParameter(), jstBlock.getOwnerType());
		IJstType collectionType = jstExpr.getResultType();
		
		if (isJsForEachSupportedType(collectionType)) {
			return toForInStmt(astStmt, jstBlock, jstVar, jstExpr);
		}
		ForStmt forStmt = new ForStmt();		
		jstBlock.addChild(forStmt);
		forStmt.getBody().getVarTable().addVarType(jstVar.getName(), jstVar.getType());	
		JstInitializer initializer = new JstInitializer(jstVar, null);
		forStmt.setInitializer(initializer);
		
		if (collectionType instanceof JstArray) {			
			FieldAccessExpr lengthExpr;
			ArrayAccessExpr arrayAccessExpr;
			String tempIndexName = convention.getTempIndex() + getCtx().getTranslateInfo(jstBlock.getOwnerType()).getUniqueTempIndex();
			
			JstIdentifier tmpIndex = new JstIdentifier(tempIndexName).setType(JstReservedTypes.JsNative.NUMBER);
			if (!(jstExpr instanceof JstIdentifier)) {
				JstIdentifier tmpArr = new JstIdentifier(convention.getTempArray()).setType(collectionType);
				initializer.addAssignment(new AssignExpr(tmpArr, jstExpr));
				lengthExpr = new FieldAccessExpr(
					new JstIdentifier(convention.getArrayLength(), tmpArr).setType(JstReservedTypes.JsNative.NUMBER));
				arrayAccessExpr = new ArrayAccessExpr(tmpArr, tmpIndex);
			}
			else {
				lengthExpr = new FieldAccessExpr(
					new JstIdentifier(convention.getArrayLength()).setType(JstReservedTypes.JsNative.NUMBER), jstExpr);
				arrayAccessExpr = new ArrayAccessExpr(jstExpr, tmpIndex);
			}
			initializer.addAssignment(
				new AssignExpr(tmpIndex, SimpleLiteral.getIntLiteral(0)));
			forStmt.setCondition(new BoolExpr(tmpIndex, lengthExpr, Operator.LESS));
			forStmt.addUpdater(
				new PostfixExpr(tmpIndex, PostfixExpr.Operator.INCREMENT));
			forStmt.addStmt(new AssignExpr(new JstIdentifier(jstVar.getName()), arrayAccessExpr));
		}
		else {
			//Iteratable
			JstIdentifier tmpItr = new JstIdentifier(convention.getTempIterator());
			initializer.addAssignment(new AssignExpr(tmpItr, 
				new MtdInvocationExpr(new JstIdentifier(convention.getIteratorMethod())).setQualifyExpr(jstExpr)));
			forStmt.setCondition(new BoolExpr(
				new MtdInvocationExpr(new JstIdentifier(convention.getIteratorHasNext(), tmpItr))));
			forStmt.addStmt(new AssignExpr(new JstIdentifier(jstVar.getName()),
				new MtdInvocationExpr(new JstIdentifier(convention.getIteratorNext(), tmpItr))));
		}
		
		Statement body = astStmt.getBody();
		if (body instanceof Block){
			Block block = (Block)body;
			for (Object o: block.statements()){
				if (o instanceof Statement){
					forStmt.addStmt(processStatement((Statement)o, forStmt.getBody()));
				}
				else {
					getLogger().logUnhandledNode(this, (ASTNode)o, jstBlock);
				}
			}
		}
		else if (body instanceof Statement){
			forStmt.addStmt(processStatement((Statement)body, forStmt.getBody()));
		}
		else {
			getLogger().logUnhandledNode(this, (ASTNode)body, jstBlock);
		}
		
		return forStmt;
	}
	
	private IStmt toForInStmt(
		final EnhancedForStatement astStmt,
		final BaseJstNode jstBlock,
		final JstVar jstVar,
		final IExpr jstExpr){
		
		ForInStmt forInStmt = new ForInStmt(jstVar, jstExpr);		
		jstBlock.addChild(forInStmt);
		forInStmt.getBody().getVarTable().addVarType(jstVar.getName(), jstVar.getType());
		
		Statement body = astStmt.getBody();
		if (body instanceof Block){
			Block block = (Block)body;
			for (Object o: block.statements()){
				if (o instanceof Statement){
					forInStmt.addStmt(processStatement((Statement)o, forInStmt.getBody()));
				}
				else {
					getLogger().logUnhandledNode(this, (ASTNode)o, jstBlock);
				}
			}
		}
		else if (body instanceof Statement){
			forInStmt.addStmt(processStatement((Statement)body, forInStmt.getBody()));
		}
		else {
			getLogger().logUnhandledNode(this, (ASTNode)body, jstBlock);
		}
		return forInStmt;
	}
	
	private static final String SupportJsForEachStmt = ASupportJsForEachStmt.class.getSimpleName();
	private static boolean isJsForEachSupportedType(IJstType type) {
		IJstAnnotation a = type.getAnnotation(SupportJsForEachStmt);
		return a != null;
	}
	
	private WhileStmt toWhileStmt(final WhileStatement astStmt, final BaseJstNode jstBlock){

		WhileStmt whileStmt = new WhileStmt();
		jstBlock.addChild(whileStmt);
		Expression astCond = astStmt.getExpression();
		if (astCond != null){
			whileStmt.setCondition(getExprTranslator().toBoolExpr(astCond, jstBlock));
		}
		Statement body = astStmt.getBody();
		if (body instanceof Block){
			Block block = (Block)body;
			for (Object o: block.statements()){
				whileStmt.addStmt(processStatement((Statement)o, whileStmt.getBody()));
			}
		}
		else if (body instanceof Statement){
			whileStmt.addStmt(processStatement((Statement)body, whileStmt.getBody()));
		}
		else {
			getLogger().logUnhandledNode(this, (ASTNode)body, jstBlock);
		}
		
		return whileStmt;
	}
	
	private DoStmt toDoStmt(final DoStatement astStmt, final BaseJstNode jstBlock){

		DoStmt doStmt = new DoStmt();
		jstBlock.addChild(doStmt);
		Expression astCond = astStmt.getExpression();
		if (astCond != null){
			if (astCond instanceof Expression){
				doStmt.setCondition(getExprTranslator().toBoolExpr(astCond, jstBlock));
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)astCond, jstBlock);
			}
		}
		Statement body = astStmt.getBody();
		if (body instanceof Block){
			Block block = (Block)body;
			for (Object o: block.statements()){
				if (o instanceof Statement){
					doStmt.addStmt(processStatement((Statement)o, doStmt.getBody()));
				}
				else {
					getLogger().logUnhandledNode(this, (ASTNode)o, jstBlock);
				}
			}
		}
		else {
			getLogger().logUnhandledNode(this, (ASTNode)body, jstBlock);
		}
		
		return doStmt;
	}
	
	private IfStmt toIfStmt(final IfStatement astStmt, final BaseJstNode jstNode){

		IfStmt ifStmt = new IfStmt();
		jstNode.addChild(ifStmt);
		Expression astCond = astStmt.getExpression();
		if (astCond != null){
			IExpr expr = getExprTranslator().processExpression(astCond, ifStmt.getBody());
			if (!(expr instanceof IBoolExpr)) {
				expr = new BoolExpr(expr);
			}
			ifStmt.setCondition((IBoolExpr)expr);
		}
		Statement thenBlock = astStmt.getThenStatement();
		if (thenBlock instanceof Block){
			Block block = (Block)thenBlock;
			for (Object o: block.statements()){
				if (o instanceof Statement){
					ifStmt.addThenStmt(processStatement((Statement)o, ifStmt.getBody()));
				}
				else {
					getLogger().logUnhandledNode(this, (ASTNode)o, jstNode);
				}
			}
		}
		else if (thenBlock instanceof Statement){
			ifStmt.addThenStmt(processStatement((Statement)thenBlock, ifStmt.getBody()));
		}
		else {
			getLogger().logUnhandledNode(this, thenBlock, jstNode);
		}
		
		Statement elseStmt = astStmt.getElseStatement();
		if (elseStmt != null){
			if (elseStmt instanceof IfStatement){
				ifStmt.addElseStmt(toIfStmt((IfStatement)elseStmt, ifStmt));
			}
			else if (elseStmt instanceof Block){
				Block block = (Block)elseStmt;
				for (Object o: block.statements()){
					if (o instanceof Statement){
						ifStmt.addElseStmt(processStatement((Statement)o, ifStmt.getElseBlock(true)));
					}
					else {
						getLogger().logUnhandledNode(this, (ASTNode)o, jstNode);
					}
				}
			}
			else if (elseStmt instanceof Statement){
				ifStmt.addElseStmt(processStatement((Statement)elseStmt, ifStmt.getElseBlock(true)));
			}
			else {
				getLogger().logUnhandledNode(this, elseStmt, jstNode);
			}
		}
		
		return ifStmt;
	}
	
	private SwitchStmt toSwitchStmt(final SwitchStatement astStmt, final BaseJstNode jstBlock){

		ExpressionTranslator exprTranslator = getExprTranslator();
		
		SwitchStmt switchStmt = new SwitchStmt();
		jstBlock.addChild(switchStmt);
		Expression astExpr = astStmt.getExpression();
		switchStmt.setExpr(exprTranslator.processExpression(astExpr, jstBlock));
		
		for (Object s: astStmt.statements()){
			if (s instanceof Statement){
				switchStmt.addStmt(processStatement((Statement)s, switchStmt.getBody()));
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)s, switchStmt);
			}
		}
		
		return switchStmt;
	}
	
	private LabeledStmt toLabeledStmt(final LabeledStatement astStmt, final BaseJstNode jstBlock){

		SimpleName label = astStmt.getLabel();
		LabeledStmt labeledStmt = new LabeledStmt(new JstIdentifier(label.toString()));
		jstBlock.addChild(labeledStmt);
		labeledStmt.setStmt(processStatement(astStmt.getBody(), labeledStmt));
		
		return labeledStmt;
	}
	
	private BreakStmt toBreakStmt(final BreakStatement astStmt, BaseJstNode jstBlock){

		SimpleName label = astStmt.getLabel();
		BreakStmt stmt;
		if (label == null){
			stmt = new BreakStmt();
		}
		else {
			stmt = new BreakStmt(new JstIdentifier(label.toString()));
		}
		jstBlock.addChild(stmt);
		return stmt;
	}
	
	private ContinueStmt toContinueStmt(final ContinueStatement astStmt, BaseJstNode jstBlock){

		SimpleName label = astStmt.getLabel();
		ContinueStmt stmt;
		if (label == null){
			stmt = new ContinueStmt();
		}
		else {
			stmt = new ContinueStmt(new JstIdentifier(label.toString()));
		}
		jstBlock.addChild(stmt);
		return stmt;
	}
	
	private ThrowStmt toThrowStmt(final ThrowStatement astStmt, final BaseJstNode jstBlock){

		IExpr expr = this.getExprTranslator().processExpression(astStmt.getExpression(), jstBlock);
		return new ThrowStmt(expr);
	}
	
	private TryStmt toTryStmt(final TryStatement astStmt, final BaseJstNode jstBlock){

		TryStmt tryStmt = new TryStmt();
		jstBlock.addChild(tryStmt);

		getOtherTranslator().processBlock(astStmt.getBody(), tryStmt.getBody());
		
		CatchStmt catchStmt = null;
		boolean firstTime = true;
		IfStmt ifStmt = new IfStmt();
		String tmp = "0";
		boolean isSameVarName = true; 
		for (Object o: astStmt.catchClauses()){
			if (o instanceof CatchClause){
				CatchClause cc = (CatchClause)o;
				if (!tmp.equals("0") && !tmp.equals(cc.getException().getName().toString())) {
					isSameVarName = false;
					break;
				}
				tmp = cc.getException().getName().toString();
			}
		}
		for (Object o: astStmt.catchClauses()){
			if (o instanceof CatchClause){
				CatchClause cc = (CatchClause)o;
				JstVar jstVar;
				if (isSameVarName) {
					jstVar = getOtherTranslator().toJstVar(cc.getException(), jstBlock.getOwnerType());
				} else {
					jstVar = getOtherTranslator().toJstVar(cc.getException(), jstBlock.getOwnerType(), CATCH_VAR_NAME);
				}
				if (firstTime){
					catchStmt = new CatchStmt(jstVar);
					tryStmt.addChild(catchStmt);
					tryStmt.addCatch(catchStmt);
					if (astStmt.catchClauses().size() > 1)
						catchStmt.getBody().addStmt(ifStmt);
				} 
				SingleVariableDeclaration decl = cc.getException();
				
				
				if (decl != null){
					IJstType type = getCtx().getProvider().getDataTypeTranslator()
						.processType(decl.getType(), jstBlock);
					if (type != null && catchStmt != null){
						catchStmt.getBody().getVarTable()
						.addVarType(getNameTranslator().processVarName(decl.getName(), catchStmt.getBody()), type);
						
						String typeName;
						if (type.getModifiers().isStatic() 
								|| jstBlock.getOwnerType().hasImport(type.getSimpleName())) {
							JstIdentifier jstQualifier = VjoTranslateHelper.getStaticTypeQualifier(type, jstBlock);
							typeName = jstQualifier.getName();
							if (type != jstBlock.getOwnerType()){
								typeName += "." + type.getSimpleName();
							}
						} else {
							typeName = type.getName();
						}
						JstIdentifier rightExpr = new JstIdentifier(typeName);
						rightExpr.setJstBinding(type);
						BoolExpr cond = new BoolExpr(
								new TextExpr(jstVar.getName()), 
								rightExpr, 
								BoolExpr.Operator.INSTANCE_OF);
						AssignExpr assign = null;
						boolean addCustomVar = !isSameVarName && !CATCH_VAR_NAME.equals(decl.getName().toString());
						if (addCustomVar) {
							JstVar left = new JstVar(jstBlock.getOwnerType(), decl.getName().toString());
							TextExpr right = new TextExpr(CATCH_VAR_NAME);
							assign = new AssignExpr(left, right);
						}
						if (firstTime){
							if (astStmt.catchClauses().size() > 1) {
								if (addCustomVar) {
									ifStmt.getBody().addStmt(assign);
								}
								ifStmt.setCondition(cond);
								getOtherTranslator().processBlock(cc.getBody(), ifStmt.getBody());
							} else
								getOtherTranslator().processBlock(cc.getBody(), catchStmt.getBody());
							firstTime = false;
						}else {
							IfStmt elseIfStmt = new IfStmt();
							elseIfStmt.setCondition(cond);
							ifStmt.addElseStmt(elseIfStmt);
							if (addCustomVar) {
								elseIfStmt.getBody().addStmt(assign);
							}
							getOtherTranslator().processBlock(cc.getBody(), elseIfStmt.getBody());
						}
					}
				}
			}

			else {
				getLogger().logUnhandledNode(this, (ASTNode)o, jstBlock);
			}
		}
		
		if (astStmt.getFinally() != null){
			getOtherTranslator().processBlock(astStmt.getFinally(), tryStmt.getFinallyBlock(true));
		}

		return tryStmt;
	}
	
	private RtnStmt toReturnStmt(final ReturnStatement astStmt, final BaseJstNode jstBlock){
		Expression e = astStmt.getExpression();
		RtnStmt rtnStmt = new RtnStmt(getExprTranslator().processExpression(e, jstBlock));
		jstBlock.addChild(rtnStmt);
		return rtnStmt;
	}
	
	private BlockStmt toBlockStmt(final Block astBlock, final BaseJstNode jstBlock){
		BlockStmt blockStmt = new BlockStmt();
		jstBlock.addChild(blockStmt);
		getCtx().getProvider().getOtherTranslator()
			.processBlock(astBlock, blockStmt.getBody());
		return blockStmt;
	}
}
