/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.parse;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import org.ebayopensource.dsf.javatojs.translate.DataTypeTranslator;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.TranslateInfo;
import org.ebayopensource.dsf.javatojs.translate.TranslationMode;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.VarTable;
import org.ebayopensource.dsf.jst.stmt.BlockStmt;
import org.ebayopensource.dsf.jst.stmt.JstBlockInitializer;

/**
 * A shollow visitor for finding direct dependency only.
 * It's not for full-translation
 */
public class TypeDependencyVisitor extends BaseTypeVisitor {

	//
	// Constructors
	//
	public TypeDependencyVisitor(final JstType type) {
		super(type, new TranslationMode().addDependency());
	}
	
	protected TypeDependencyVisitor(final JstType type, final TranslationMode mode) {
		super(type, mode);
	}
	
	//
	// Override ASTVisitor
	//
	@Override
	public void postVisit(ASTNode node) {
		
		if (node instanceof MethodDeclaration){
//			if (!isExcluded(node)){
				removeCurBlock();
				removeCurMtd();
//			}
		}
		else if (node instanceof Initializer){
			removeCurBlock();
		}
		else if (node instanceof WhileStatement
			|| node instanceof DoStatement
			|| node instanceof IfStatement
			|| node instanceof SwitchStatement
			|| node instanceof ForStatement
			|| node instanceof EnhancedForStatement
			|| node instanceof TryStatement
			|| node instanceof SynchronizedStatement){
			removeCurBlock();
		}

		super.postVisit(node);
	}
	
	@Override
	public boolean visit(PackageDeclaration pkg) {
		return false;
	}
	
	@Override
	public boolean visit(QualifiedName node) {
		
		if (node.getParent() instanceof MethodDeclaration){ // To exclude types from throws
			return false;
		}
		
		String name = node.getFullyQualifiedName();
		
		if (hasDependency(name, true)){
			return false;
		}
		
		TranslateInfo tInfo = getCtx().getTranslateInfo(getCurType().getRootType());
		IJstType jstType;
		Name qualifier = node;
		String typeName;
		JstCache jstCache = JstCache.getInstance();
		do {
			typeName = qualifier.toString();
			if (tInfo.getType(typeName, true) != null){
				return false;
			}
			jstType = jstCache.getType(typeName);
			if (jstType != null){
				tInfo.setType(typeName, jstType);
				return false;
			}
			jstType = getCtx().getProvider().getCustomTranslator().processType(typeName, node, getCurScope());
			if (jstType != null){
				tInfo.setType(typeName, jstType);
				return false;
			}
			if (qualifier instanceof SimpleName){
				break;
			}
			qualifier = ((QualifiedName)qualifier).getQualifier();
		}
		while (true);
		
		qualifier = node;
		SimpleName simpleQualifier = null;
		do {
			String qName = qualifier.toString();
			if (TranslateHelper.Type.isFullyQualifiedTypeName(qName)){
				jstType = getCtx().getProvider().getCustomTranslator()
					.processType(qName, node, getCurType());
				if (jstType == null){
					jstType = JstCache.getInstance().getType(
						getCtx().getConfig().getPackageMapping().mapTo(qName), true);
				}
				getCtx().getTranslateInfo(getCurType()).setType(qName, jstType);
				addDependency(jstType, qualifier);
				return false;
			}
			if (qualifier instanceof SimpleName){
				simpleQualifier = (SimpleName)qualifier;
				break;
			}
			qualifier = ((QualifiedName)qualifier).getQualifier();
		}
		while (true);
		
		String simpleName = simpleQualifier.toString();
		String fullName = tInfo.getImported(simpleName);
		if (fullName == null){
			fullName = TranslateHelper.Type.resolveImplicitImport(simpleName, getCurType());
		}
		if (fullName != null){
			jstType = getCtx().getProvider().getCustomTranslator()
				.processType(fullName, node, getCurScope());
			if (jstType == null){
				fullName = getCtx().getConfig().getPackageMapping().mapTo(fullName);
				jstType = JstCache.getInstance().getType(fullName, true);
			}
			tInfo.setType(simpleName, jstType);
			addDependency(jstType, simpleQualifier);
			return false;
		}
		
		return false;
	}
	
	@Override
	public boolean visit(SimpleType node) {
		
		ASTNode parent = node.getParent();
		if (parent instanceof MethodDeclaration // To exclude types from throws
				|| parent instanceof ClassInstanceCreation){ 
			return false;
		}
		
		String name = node.toString();
		
		if (hasDependency(name, true)){;
			return false;
		}
		
		TranslateInfo tInfo = getCtx().getTranslateInfo(getCurType());
		
		IJstType jstType = getCtx().getProvider().getDataTypeTranslator()
			.processType(node, getCurMtd() != null ? getCurMtd() : getCurType());
	
		if (jstType != null){
			tInfo.setType(name, jstType);
			addDependency(jstType, node);
		}

		return false;
	}
	
	@Override
	public boolean visit(SimpleName node) {
		
		if (node.getParent() instanceof MethodDeclaration){ // To exclude types from throws
			return false;
		}
		
		String name = node.getFullyQualifiedName();
		
		if (hasDependency(name, true)){;
			return false;
		}
		
		TranslateInfo tInfo = getCtx().getTranslateInfo(getCurType());

		String typeName = tInfo.getImportedStaticRefTypeName(name);
		if (typeName != null){
			typeName = getCtx().getConfig().getPackageMapping().mapTo(typeName);
			IJstType jstType = JstFactory.getInstance().createJstType(typeName, false);
			tInfo.setType(jstType.getSimpleName(), jstType);
			addDependency(jstType, null);


		}

		return false;
	}
	
	@Override
	public boolean visit(TypeLiteral node) {

		IJstType jstType = getCtx().getProvider().getDataTypeTranslator()
			.processType(node.getType(), getCurMtd() != null ? getCurMtd() : getCurType());

		addDependency(jstType, node.getType());

		return false;
	}
	
	@Override
	public boolean visit(MethodDeclaration astMtd) {
		
		boolean visitChildren = super.visit(astMtd);
		
		JstMethod jstMtd = getCurMtd();
		if (jstMtd == null){
			return false;
		}
		
		getCurType().addChild(jstMtd);

		return visitChildren;
	}
	
	@Override
	public boolean visit(VariableDeclarationExpression astExpr) {
		visitVarDecl(astExpr, astExpr.getType(), astExpr.fragments());
		return true;
	}
	
	@Override
	public boolean visit(VariableDeclarationStatement astStmt) {
		visitVarDecl(astStmt, astStmt.getType(), astStmt.fragments());
		return true;
	}
	
	@Override
	public boolean visit(SingleVariableDeclaration astNode) {

		IJstType varType = getCtx().getProvider().getDataTypeTranslator()
			.processType(astNode.getType(), getCurrentDeclaration());
		
		if (astNode.getExtraDimensions() > 0) {
			for (int i=0; i<astNode.getExtraDimensions(); i++){
				varType = JstFactory.getInstance().createJstArrayType(varType, true);
			}
		}
		if (astNode.isVarargs()) {
			varType = JstFactory.getInstance().createJstArrayType(varType, true);
		}
		
		JstBlock curBlock = getCurBlock();
		if (varType == null || curBlock == null){
			return false;
		}

		curBlock.getVarTable().addVarType(getCtx().getProvider()
				.getNameTranslator().processVarName(astNode.getName(), curBlock), varType);
		addDependency(varType, astNode.getType());

		return true;
	}
	
	@Override
	public boolean visit(MethodInvocation astNode) {
		
		DataTypeTranslator dataTypeTranslator = getCtx().getProvider().getDataTypeTranslator();
		
		Expression expr = astNode.getExpression();
		String typeName = null;
		IJstType jstType = null;
		
		if (expr instanceof SimpleName){
			typeName = expr.toString();
			if (isVar(typeName)){
				return true;
			}
			if (isPtyOrEnum(typeName)){
				return true;
			}
			jstType = dataTypeTranslator.processName((Name)expr, getCurType());
			addDependency(jstType, expr);
		}
		else if (expr instanceof ClassInstanceCreation){
			typeName = ((ClassInstanceCreation)expr).getType().toString();
			jstType = getCtx().getProvider().getDataTypeTranslator()
				.processType(((ClassInstanceCreation)expr).getType(), getCurrentDeclaration());
			addDependency(jstType, ((ClassInstanceCreation)expr).getType());
		}
		
		if (jstType != null){
			getCtx().getTranslateInfo(getCurType()).setType(typeName, jstType);
		}

		return true;
	}
	
	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		JstType jstType = TranslateHelper.Factory.createJstType(node, getCurType());
		getCurScope().addChild(jstType);
		setCurType(jstType);
		getCtx().getTranslateInfo(jstType.getRootType()).addAnonymousType(node, jstType);
		return true;
	}
	
	@Override
	public boolean visit(TypeDeclarationStatement node) {
		String typeName = node.getDeclaration().getName().getFullyQualifiedName();
		JstType jstType = TranslateHelper.Factory.createJstType(node, getCurType());
		jstType.setSimpleName(typeName);
		jstType.setParent(getScopeParent());
		getCurScope().addChild(jstType);
		setCurType(jstType);
		return true;
	}
	
	@Override
	public boolean visit(ClassInstanceCreation node) {
		if (node.getExpression() != null){
			getCtx().getTranslateInfo(getCurType()).addEmbededType(node.getType().toString());
		}
		ClassInstanceCreation cic = (ClassInstanceCreation)node;
		if (cic.getExpression() == null){
			IJstType objType = getCtx().getProvider().getDataTypeTranslator()
				.processType(cic.getType(), getCurrentDeclaration());
			addDependency(objType, cic.getType());
		}
	
		return true;
	}
	
	@Override
	public boolean visit(Initializer initializer) {
		if (skipImpl()){
			return false;
		}
		JstType ownerType = getCurType();
		JstBlockInitializer jstBlock = new JstBlockInitializer(Modifier.isStatic(initializer.getModifiers()));
		ownerType.addChild(jstBlock);
		getCtx().getTranslateInfo(ownerType).addInitializer(initializer, jstBlock);
		setCurBlock(jstBlock.getBody());
		return true;
	}
	
	@Override
	public boolean visit(WhileStatement astStmt) {
		return visitBlockStmt(astStmt);
	}
	
	@Override
	public boolean visit(DoStatement astStmt) {
		return visitBlockStmt(astStmt);
	}
	
	@Override
	public boolean visit(IfStatement astStmt) {
		return visitBlockStmt(astStmt);
	}
	
	@Override
	public boolean visit(SwitchStatement astStmt) {
		return visitBlockStmt(astStmt);
	}
	
	@Override
	public boolean visit(ForStatement astStmt) {
		return visitBlockStmt(astStmt);
	}
	
	@Override
	public boolean visit(EnhancedForStatement astStmt) {
		return visitBlockStmt(astStmt);
	}
	
	@Override
	public boolean visit(TryStatement astStmt) {
		return visitBlockStmt(astStmt);
	}
	
	@Override
	public boolean visit(SynchronizedStatement astStmt) {
		return visitBlockStmt(astStmt);
	}
	
	//
	// Private
	//
	private boolean visitBlockStmt(Statement astStmt){
		JstBlock curBlock = getCurBlock();
		if (curBlock == null){
			return false;
		}
		BlockStmt blockStmt = (BlockStmt)getCtx().getProvider().getStmtTranslator()
			.processStatement(astStmt, curBlock);
		setCurBlock(blockStmt.getBody());
		return true;
	}
	
	private boolean visitVarDecl(ASTNode astNode, Type type, List fragments) {
		IJstType varType = getCtx().getProvider().getDataTypeTranslator()
			.processType(type, getCurrentDeclaration());
		JstBlock curBlock = getCurBlock();
		if (varType == null || curBlock == null){
			return false;
		}
		VarTable varTable = curBlock.getVarTable();
		for(Object o: fragments){
			if (o instanceof VariableDeclarationFragment){
				VariableDeclarationFragment f = (VariableDeclarationFragment)o;
				// f.getExtraDimensions();
				varTable.addVarType(getCtx().getProvider().getNameTranslator()
					.processVarName(f.getName(), curBlock), varType);
			}
			else {
				getCtx().getLogger().logUnhandledNode(this, astNode, curBlock);
			}
		}
		addDependency(varType, type);
		return true;
	}
	
	private boolean isVar(String name){
		IJstNode scopeNode = getCurScope();
		String varName = getCtx().getProvider().getNameTranslator().processVarName(name);			
		if (TranslateHelper.Type.getVarType(varName, scopeNode) != null){
			return true;
		}
		
		JstMethod curMtd = getCurMtd();
		if (curMtd == null){
			return false;
		}
		JstBlock block = curMtd.getBlock(false);
		if (block != null){
			return TranslateHelper.Type.getVarType(name, block) != null;
		}

		return false;
	}
	
	private boolean isPtyOrEnum(String name){
		JstType jstType = getCurType();
		if (jstType.getProperty(name) != null){
			return true;
		}

		return jstType.getEnumValue(name) != null;
	}
}
