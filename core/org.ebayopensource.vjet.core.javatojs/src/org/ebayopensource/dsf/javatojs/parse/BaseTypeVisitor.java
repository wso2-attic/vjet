/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import org.ebayopensource.dsf.javatojs.trace.ITranslateTracer;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.TranslateInfo;
import org.ebayopensource.dsf.javatojs.translate.TranslationMode;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;

/**
 * A shollow visitor for finding dependency or APIs.
 * It's not for full-translation
 */
public abstract class BaseTypeVisitor extends ASTVisitor {
	
	private TranslateCtx m_ctx;
	private TranslationMode m_mode;
	private CompilationUnit m_cu;
	
	private ASTNode m_bigBrother = null;
	private boolean m_skipImpl = false;
	
	private Stack<JstType> m_typeStack = new Stack<JstType>();
	private Stack<JstMethod> m_mtdStack = new Stack<JstMethod>();
	private Stack<JstBlock> m_blockStack = new Stack<JstBlock>();
	
	private List<ASTNode> m_excludedNodes = new ArrayList<ASTNode>();
	
	private List<JstType> m_dependents = new ArrayList<JstType>();
	
	private ITranslateTracer m_tracer;
	
	//
	// Constructors
	//
	public BaseTypeVisitor(final JstType type, TranslationMode mode) {
		assert mode != null : "mode cannot be null";
		m_mode = mode;
		setCurType(type);
	}
	
	//
	// Override ASTVisitor
	//
	@Override
	public void preVisit(ASTNode node) {
		
		if (node instanceof AbstractTypeDeclaration){
			final String nodeName = ((AbstractTypeDeclaration)node).getName().toString();
			JstType curType = getCurType();
			String typeName = curType.getSimpleName();
			if (typeName != null && !typeName.equals(nodeName)){
				IJstType dependent = TranslateHelper.Type.getDependedType(curType, nodeName);
				if (dependent != null && dependent instanceof JstType){
					curType = (JstType)dependent;
					setCurType(curType);
				}
			}
			
			getCtx().getTranslateInfo(curType).addMode(m_mode);
		}
		else if (node instanceof AnonymousClassDeclaration){
			JstType curType = getCurType();
			getCtx().getTranslateInfo(curType).addMode(m_mode);
		}
	}
	
	@Override
	public void postVisit(ASTNode node) {
		
		if (node instanceof AbstractTypeDeclaration
			|| node instanceof AnonymousClassDeclaration){

			JstType curType = getCurType();
			if (m_mode.hasDependency()){
				getCtx().getTranslateInfo(curType).getStatus().setDependencyDone();
			}
			if (m_mode.hasDeclaration()){
				getCtx().getTranslateInfo(curType).getStatus().setDeclTranlationDone();
			}
			
			if (m_typeStack.size() > 1){
				removeCurType();
			} 
		}
	}
	
	@Override
	public boolean visit(CompilationUnit cu) {
		if (cu == null){
			return false;
		}
		m_cu = cu;
		if (m_cu.types().size() > 1){
			TypeDeclaration typeDecl;
			for (Object node: m_cu.types()){
				typeDecl = (TypeDeclaration)node;
				if (typeDecl.getName().toString().equals(getCurType().getSimpleName())){
					m_bigBrother = typeDecl;
					break;
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean visit(ImportDeclaration astImport) {
		
		JstType jstType = getCtx().getProvider().getDataTypeTranslator()
			.processImport(astImport, getCurType());
		if (jstType != null){
			addDependency(jstType, null);
		}
		
		return false;
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		return visit((AbstractTypeDeclaration)node);
	}
	
	@Override
	public boolean visit(EnumDeclaration node) {	
		return visit((AbstractTypeDeclaration)node);
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
		
		JstType curType = getCurType();
		TranslateInfo tInfo =  getCtx().getTranslateInfo(curType);					

		IJstType fldType = getCtx().getProvider().getFieldTranslator().processField(node, curType);

		boolean include = false;
		
		Iterator<VariableDeclarationFragment> ite = node.fragments().iterator();	
		String fName;
		CustomInfo fInfo = null;
		IJstType customizedAsType = null;
		while(ite.hasNext()){
			fName = ite.next().getName().getIdentifier();
			fInfo = tInfo.getFieldCustomInfo(fName);
			customizedAsType = fInfo.getAsType();
			if (!include && !fInfo.isExcluded()){
				include = true;
			}
		}

		if (!include){
			m_excludedNodes.add(node);
			return false;
		}
		
		addDependency(customizedAsType, null);

		addDependency(fldType, node.getType());

		return !m_skipImpl;
	}
	
	@Override
	public boolean visit(EnumConstantDeclaration node) {

		getCtx().getProvider().getFieldTranslator().processEnumConstsDecl(node, getCurType());
		
		if (getCtx().getTranslateInfo(getCurType()).getFieldCustomInfo(node.getName().toString()).isExcluded()){
			m_excludedNodes.add(node);
			return false;
		}

		return true;
	}
	
	@Override
	public boolean visit(MethodDeclaration astMtd) {
		
		TranslateCtx ctx = getCtx();
		JstType curType = getCurType();

		JstMethod mtd = ctx.getProvider().getMethodTranslator().processMethod(astMtd, curType);
		if (mtd == null){
			return false;
		}
		
		TranslateInfo tInfo =  getCtx().getTranslateInfo(curType);	
		MethodKey mtdKey = MethodKey.genMethodKey(astMtd);
		CustomInfo cInfo = tInfo.getMethodCustomInfo(mtdKey);
		if (cInfo.isExcluded()){
			m_excludedNodes.add(astMtd);
			return false;
		}
		
		setCurMtd(mtd);
		setCurBlock(mtd.getBlock(true));
		
		if (cInfo.isMappedToJS() || cInfo.isMappedToVJO()){
			return false;
		}
		
		IJstType customizedAsType = cInfo.getAsType();
		if (customizedAsType != null){
			addDependency(customizedAsType, null);
		}
		
		IJstType rtnType = mtd.getRtnType();
		addDependency(rtnType, astMtd.getReturnType2());
		
		IJstType argType;
		for (JstArg arg: mtd.getArgs()){
			argType = arg.getType();
			addDependency(argType, AstBindingHelper.getAstType(arg));
		}

		return !m_skipImpl;
	}
	
	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		return false;
	}
	
	@Override
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		return false;
	}
	
	@Override
	public boolean visit(MarkerAnnotation node) {
		return false;
	}
	
	@Override
	public boolean visit(NormalAnnotation node) {
		return false;
	}
	
	@Override
	public boolean visit(SingleMemberAnnotation node) {
		return false;
	}
	
	@Override
	public boolean visit(ParameterizedType node) {
		IJstType jstType = getCtx().getProvider().getDataTypeTranslator().processType(node.getType(), getCurType());
		while (jstType != null && jstType instanceof JstTypeWithArgs){
			jstType = ((JstTypeWithArgs)jstType).getType();
		}
		addDependency(jstType, node.getType());
		return false;
	}
	
	//
	// API
	//
	public JstType getType(){
		return getCurType();
	}
	
	public TranslationMode getMode(){
		return m_mode;
	}
	
	public List<JstType> getDependency(){
		return Collections.unmodifiableList(m_dependents);
	}
	
	public boolean hasDependency(final String name, boolean isJavaName){
		if (name == null){
			return false;
		}
		String jstName = name;
		if (isJavaName){
			jstName = getCtx().getConfig().getPackageMapping().mapTo(name);
		}
		for (JstType t: m_dependents){
			if (jstName.equals(t.getName())){
				return true;
			}
		}
		return false;
	}
	
	//
	// Protected 
	//
	protected boolean skipImpl(){
		return m_skipImpl;
	}
	
	protected BaseJstNode getScopeParent(){
		if (getCurBlock() != null){
			return getCurBlock();
		}
		if (getCurMtd() != null){
			return getCurMtd();
		}
		return getCurType();
	}
	
	protected BaseJstNode getCurrentDeclaration(){
		if (getCurMtd() != null){
			return getCurMtd();
		}
		return getCurType();
	}
	
	protected BaseJstNode getCurScope(){
		if (getCurBlock() != null){
			return getCurBlock();
		}
		if (getCurMtd() != null){
			return getCurMtd();
		}
		return getCurType();
	}
	
	protected void setCurType(JstType type){
		if (type == getCurType()){
			return;
		}
		getCtx().getTranslateInfo(type).addMode(m_mode);
		m_typeStack.push(type);
	}
	
	protected JstType getCurType(){
		if (m_typeStack.isEmpty()){
			return null;
		}
		return m_typeStack.peek();
	}
	
	protected JstType removeCurType(){
		if (!m_typeStack.isEmpty()){
			return m_typeStack.pop();
		}
		return null;
	}
	
	protected void setCurMtd(JstMethod mtd){
		m_mtdStack.push(mtd);
	}
	
	protected JstMethod getCurMtd(){
		if (m_mtdStack.isEmpty()){
			return null;
		}
		return m_mtdStack.peek();
	}
	
	protected void removeCurMtd(){
		if (!m_mtdStack.isEmpty()){
			m_mtdStack.pop();
		}
	}
	
	protected void setCurBlock(JstBlock block){
		m_blockStack.push(block);
	}
	
	protected JstBlock getCurBlock(){
		if (m_blockStack.isEmpty()){
			return null;
		}
		return m_blockStack.peek();
	}
	
	protected void removeCurBlock(){
		if (!m_blockStack.isEmpty()){
			m_blockStack.pop();
		}
	}
	
	protected void addDependency(IJstType jstType, final ASTNode astNode){
		if (jstType == null 
			|| m_dependents.contains(jstType)
			|| jstType.getOuterType() != null){
			return;
		}
		
		JstType curType = getCurType();
		
		// Not this type
		if (curType.getName() != null && curType.getName().equals(jstType.getName())){
			return;
		}
		
		// Not outer of this type
		JstType outer = curType.getOuterType();
		while (outer != null){
			if (outer.getName() != null && outer.getName().equals(jstType.getName())){
				return;
			}
			outer = outer.getOuterType();
		}
		
		// Not mapped
		Name astName = null;
		if (astNode instanceof SimpleType){
			astName = ((SimpleType)astNode).getName();
		}
		else if (astNode instanceof Name){
			astName = (Name)astNode;
		}
		if ((astName instanceof SimpleName) && TranslateHelper.Type.isMapped(jstType, ((SimpleName)astName))){
			return;
		}
		
		if (jstType instanceof JstType){
			m_dependents.add((JstType)jstType);
			return;
		}
		
		if (jstType instanceof JstTypeWithArgs){
			addDependency(((JstTypeWithArgs)jstType).getType(), astNode);
			addDependency(((JstTypeWithArgs)jstType).getArgType(), astNode);
			return;
		}
	}
	
	protected ITranslateTracer getTracer(){
		if (m_tracer == null){
			m_tracer = getCtx().getTraceManager().getTracer();
		}
		return m_tracer;
	}
	
	protected TranslateCtx getCtx(){
		if (m_ctx == null){
			m_ctx = TranslateCtx.ctx();
		}
		return m_ctx;
	}

	//
	// Private
	//
	private boolean visit(AbstractTypeDeclaration node) {
		TranslateCtx ctx = getCtx();
		JstType curType = getCurType();
		
		if (m_cu.types().size() > 1 && m_cu.types().contains(node) && node != m_bigBrother){
			JstType rootType = m_typeStack.get(0);
			curType = TranslateHelper.Factory.createJstType(node, rootType);
			curType.setPackage(rootType.getPackage());
			rootType.addSiblingType(curType);
			setCurType(curType);
		}
		
		JstType jstType = curType;
		
		ctx.getProvider().getTypeTranslator().processType(node, curType);
		
		if (TranslateHelper.Type.isEmbededType(node)){
			String name = node.getName().toString();
//			ctx.getTranslateInfo(curType).addEmbededType(name);
			if (!name.equals(jstType.getSimpleName())){
				jstType = curType.getEmbededType(name);
				if (jstType == null){
					jstType = ctx.getProvider().getTypeTranslator().processEmbededType(node, curType);
				}
			}
		}
		
		if (jstType == null){
			return false;
		}

		if (ctx.isExcluded(jstType)){
			m_excludedNodes.add(node);
			return false;
		}
		
		m_skipImpl = ctx.isJavaOnly(jstType) || ctx.isJSProxy(jstType);
        
        List<IJstTypeReference> extend = curType.getExtendsRef();
        if (!extend.isEmpty()){
        	IJstTypeReference jstTypeRef = extend.get(0);
        	addDependency(jstTypeRef.getReferencedType(), AstBindingHelper.getAstType(jstTypeRef));
        	return true;
        }
        IJstType baseType = ctx.getTranslateInfo(curType).getBaseType();
        if (baseType != null && baseType instanceof JstType){
        	Type astBase = null;
        	if (node instanceof TypeDeclaration){
        		astBase = ((TypeDeclaration)node).getSuperclassType();
        	}
        	addDependency(baseType, astBase);
        }
	
		return true;
	}
}